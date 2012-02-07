/* PageTableAll.java - parses all pages in the Wiktionary table 'page' (source database).
 *
 * Copyright (c) 2005-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiktparsed.mean_semrel.parser;

//import wikt.parser.*;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TRelation;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikipedia.sql.Statistics;
import wikokit.base.wikipedia.sql.UtilSQL;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.language.LanguageType;
import wiktparsed.mean_semrel.parser.sql.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import wikokit.base.wikt.api.WTMeaning;
import wikokit.base.wikt.constant.Relation;

/** Worker with all pages in the WP table 'page'.
 */
public class PageWithSemrel {
    private static final boolean DEBUG = false;
    
    
    /** Inner class which contains list of synonyms, antonyms, ... joined by 
     * some delimiter.
     */
    private static class Semrel {
                
        /** Relations stored to the table mean_semrel_XX. */
        private final Relation[] ar_relations = {
            Relation.synonymy,  Relation.antonymy,
            Relation.hypernymy, Relation.hyponymy,
            Relation.holonymy,  Relation.meronymy,
            Relation.troponymy, Relation.coordinate_term
        };
        
        private static Map<Relation, StringBuffer> m_relations;
        
        Semrel() {    
            m_relations = new HashMap<Relation, StringBuffer>();
            
            for(Relation r : ar_relations) {
                m_relations.put(r, new StringBuffer());
            }
        }
        
        public void init() {
            for(Relation r : ar_relations) {
                m_relations.get(r).setLength(0);
            }
        }
        
        /** Gets maps: relation (e.g. synonymy) to a list of synonyms, antonyms, ... */
        public Map<Relation, StringBuffer> getMapRelationToText() {
            return m_relations;
        }
        
        /** Adds (joins) synonym word (from TRelation) to synonyms, 
         * antonym word to antonyms, etc; for example, synonyms += delimiter + word;
         */
        private void add(TRelation tr, String delimiter)
        {
            String           word = tr.getWikiText().getText();
            String delimiter_word = delimiter + word;
            
            Relation r = tr.getRelationType();
            
            if(!m_relations.containsKey(r))
                return; // e.g. skip "See also"
            
            if( 0 == m_relations.get(r).length()) {
                m_relations.get(r).append(          word);
            } else {
                m_relations.get(r).append(delimiter_word);
            }
        }
        
        /** Check whether exist any relations.
         */
        private boolean hasRelation() {
            
            for(Relation r : ar_relations) {
                if (m_relations.get(r).length() > 0)
                    return true;
            }
            return false;
        }
    }
    
    
    /** Selects only pages with non-empty meaning (definition) 
     * and semantic relations from Wiktionary parsed database, 
     * stores to the wikt_mean_semrel database.
     *
     * @param native_lang   native language in the Wiktionary,
     *                       e.g. Russian language in Russian Wiktionary,
     * @param n_start_from number of first Wiktionary entry to be parsed
     * @param delimiter symbol between words in the table fields "synonyms", "antonyms", etc.
     * @param min_meaning threshold - minimum number of records in mean_semrel_XX, 
     *                  the lesser tables (mean_semrel_XX) and records (lang.XX) will be deleted
     */
    public static void parse(
            // LanguageType native_lang,
            Connect wikt_parsed_conn,
            Connect mean_semrel_conn,
            int n_start_from,
            String delimiter,
            int min_meaning)
    {
        long    t_start;
        float   t_work;
        
        int n_total = Statistics.Count(wikt_parsed_conn, "lang_pos");
        System.out.println("Total lang_pos: " + n_total);
        t_start = System.currentTimeMillis();
        
        if(0 == n_start_from) // create wikt_mean_semrel 
            SemrelParser.clearDatabase(wikt_parsed_conn, mean_semrel_conn);
        else
            SemrelParser.initWithoutClearDatabase(wikt_parsed_conn, mean_semrel_conn);
        
        try {
            Statement s = wikt_parsed_conn.conn.createStatement ();
            try {
                if(DEBUG) { 
                    s.executeQuery ("SELECT id FROM lang_pos LIMIT 1000000");
                } else {
                    s.executeQuery ("SELECT id FROM lang_pos");
                }
                ResultSet rs = s.getResultSet ();
                try {
                    Semrel semrel = new Semrel();
                    int n_cur = 0;
                    while (rs.next ())
                    {
                        //if (n_cur >= 1)
                        //    break;
                        n_cur ++;
                        if(n_start_from >= 0 && n_start_from > n_cur)
                            continue; // skip first [0, n_start_from] records
                        
                        int id = rs.getInt("id");
                        TLangPOS lang_pos_not_recursive = TLangPOS.getByID (wikt_parsed_conn, id);// fields are not filled recursively
                        if(null == lang_pos_not_recursive)
                            continue;
                        LanguageType xx_lang = lang_pos_not_recursive.getLang().getLanguage();

                        TPage tpage = lang_pos_not_recursive.getPage();
                        String page_title = tpage.getPageTitle();

                        int n_meaning = WTMeaning.countMeanings(wikt_parsed_conn, lang_pos_not_recursive);
                        if(0 == n_meaning)
                            continue;

                        //POS p = lang_pos_not_recursive.getPOS().getPOS();
                        
                        TMeaning[] mm = TMeaning.get(wikt_parsed_conn, lang_pos_not_recursive);
                        for(TMeaning m : mm) {
                            
                            String meaning_text = m.getWikiTextString();
                            if(0 == meaning_text.length())
                                continue;

                            TRelation[] rels = TRelation.get(wikt_parsed_conn, m);
                            if(0 == rels.length)
                                continue;

                            semrel.init();
                            for(TRelation r : rels)
                                semrel.add(r, delimiter);
                            
                            if(!semrel.hasRelation())
                                continue;
                            
                            // save to database relations and meaning text
                            MSRMeanSemrelXX.insert (
                                        xx_lang, mean_semrel_conn,
                                        page_title, meaning_text,
                                        semrel.getMapRelationToText(), rels.length);
                        }
                        
                        if(0 == n_cur % 10000) {   // % 100 1000
                            //if(n_cur<10900)
                            //    continue;
                            long    t_cur, t_remain;

                            t_cur  = System.currentTimeMillis() - t_start;
                            t_remain = (long)((n_total - n_cur) * t_cur/(60f*1000f*(float)(n_cur)));
                                       // where time for 1 page = t_cur / n_cur 
                                       // in min, since /(60*1000)
                            t_cur = (long)(t_cur/(60f*1000f));
                            //t_cur = t_cur/(60f*1000f));

                            System.out.println(n_cur + ": " + page_title + 
                                    ", duration: "  + t_cur +   // t_cur/(60f*1000f) + 
                                    " min, remain: " + t_remain +
                                    " min");
                        }
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (PageWithSemrel.parse()): " + ex.getMessage());
        }

        // post-processing 1
        MSRLang.calcMeanSemrelStatistics(mean_semrel_conn);
       
        // post-processing 2
        // delete mean_semrel_XX, if it is empty table, i.e count(*) < 10
        MSRLang.deleteEmptyRecordsAndTables(mean_semrel_conn, min_meaning);
        UtilSQL.dropTable(mean_semrel_conn, "mean_semrel_letter_ru");
        
        long  t_end;
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        System.out.println("\n\nTime sec:" + t_work + 
                "\nTotal pages: " + n_total);
    }
}
