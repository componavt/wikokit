/* RelationTableAll.java - relations' statistics in the database of the parsed Wiktionary.
 *
 * Copyright (c) 2005-2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.stat;

import wikt.constant.Relation;
import wikipedia.language.LanguageType;

import wikipedia.sql.*;
import wikt.sql.*;

import java.sql.*;

import java.util.Map;
import java.util.HashMap;


/** Relations' statistics in the database of the parsed Wiktionary.
 */
public class RelationTableAll {
    private static final boolean DEBUG = true;

    /** Counts number of semantic relations for each language
     * by selecting all relations from the database of the parsed Wiktionary.<br><br>
     * SELECT  FROM relation;
     * 
     * @param connect connection to the database of the parsed Wiktionary
     * @return map of maps with number of synonyms (etc.) in English (etc.)
     */
    public static Map<LanguageType, Map<Relation,Integer>> countRelationsPerLanguage (Connect wikt_parsed_conn) {
        // lang -> relations -> count

        Statement   s = null;
        ResultSet   rs= null;
        long    t_start;
        float   t_work;

        int n_unknown_lang_pos = 0; // relations which belong to words with unknown language and POS

        int n_total = Statistics.Count(wikt_parsed_conn, "relation");
        //System.out.println("Total relations: " + n_total);
        t_start = System.currentTimeMillis();

        Map<LanguageType, Map<Relation,Integer>> m_lang_rel_n = new HashMap<LanguageType, Map<Relation,Integer>>();
        
        try {
            s = wikt_parsed_conn.conn.createStatement ();
            StringBuffer str_sql = new StringBuffer();
            str_sql.append("SELECT id,meaning_id,wiki_text_id,relation_type_id FROM relation");
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            int n_cur = 0;
            while (rs.next ())
            {
                n_cur ++;
                //int          id =                                     rs.getInt("id");
                TMeaning      m = TMeaning.getByID(wikt_parsed_conn,    rs.getInt("meaning_id"));
                //TWikiText    wt = TWikiText.getByID(wikt_parsed_conn, rs.getInt("wiki_text_id"));
                TRelationType tr = TRelationType.getRelationFast(       rs.getInt("relation_type_id"));

                if(null != m && null != tr) {
                    TLangPOS lang_pos = m.getLangPOS(wikt_parsed_conn);
                    Relation r        = tr.getRelation();
                    assert(null != r);

                    if(null != lang_pos) {
                        TLang tlang = lang_pos.getLang();                        //TPOS tpos   = lang_pos.getPOS();  // future statistics
                        
                        if(null != tlang ) {
                            LanguageType lang = lang_pos.getLang().getLanguage();

                            Map<Relation,Integer>     rel_n = null;
                            int                           n;
                            if(m_lang_rel_n.containsKey(lang) ) {
                                      rel_n =  m_lang_rel_n.get(lang);          // assert(null != rel_n);
                                   if(rel_n.containsKey(        r)) {
                                          n =         rel_n.get(r);
                                                      rel_n.put(r, n + 1);
                                   } else             rel_n.put(r, 1    );
                            } else {
                                                      rel_n = new HashMap<Relation,Integer>();
                                                      rel_n.put(r, 1);
                               m_lang_rel_n.put(lang, rel_n);
                            }
                            
                            if(DEBUG && 0 == n_cur % 1000) {   // % 100
                                //if(n_cur > 33)
                                  //  break;
                                long    t_cur, t_remain;

                                t_cur  = System.currentTimeMillis() - t_start;
                                t_remain = (long)((n_total - n_cur) * t_cur/(60f*1000f*(float)(n_cur)));
                                           // where time for 1 page = t_cur / n_cur
                                           // in min, since /(60*1000)
                                t_cur = (long)(t_cur/(60f*1000f));
                                //t_cur = t_cur/(60f*1000f));

                                TPage tpage = lang_pos.getPage();
                                if(null != tpage) {
                                    System.out.println(n_cur + ": " + tpage.getPageTitle() +
                                        ", duration: "  + t_cur +   // t_cur/(60f*1000f) +
                                        " min, remain: " + t_remain +
                                        " min");
                                }
                            }
                        }
                    }

                } else
                    n_unknown_lang_pos ++;
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (RelationTableAll.countRelationsPerLanguage()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        long  t_end;
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        System.out.println("\nTime sec:" + t_work +
            "\nTotal relations: " + n_total +
            "\n\nUnknown<ref>'''Unknown''' - relations which belong to words with unknown language and POS</ref>: " +
            n_unknown_lang_pos);
                
        return m_lang_rel_n;
    }

    public static void main(String[] args) {

        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();

        // Russian
        wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);

        // English
        //wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, LanguageType.en);

        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        TRelationType.createFastMaps(wikt_parsed_conn);

        String db_name = wikt_parsed_conn.getDBName();
        System.out.println("\n== Statistics of semantic relations in the Wiktionary parsed database ==");
        WTStatistics.printHeader (db_name);

        Map<LanguageType, Map<Relation,Integer>> m = RelationTableAll.countRelationsPerLanguage(wikt_parsed_conn);
        wikt_parsed_conn.Close();
        System.out.println("\nLanguages with semantic relations: " + m.size());
        System.out.println();

        //WTStatisticsGoogleWiki.printRelationsPerLanguage(m);
        WTStatistics.printRelationsPerLanguage(db_name, m);
        WTStatistics.printFooter();
    }

}
