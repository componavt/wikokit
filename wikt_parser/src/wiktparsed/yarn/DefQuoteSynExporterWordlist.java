/* DefQuoteSynExporterWordlist.java - exports definition, quotations and synonyms
 * from the database of the parsed Wiktionary in YARN format.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wiktparsed.yarn;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.api.WTMeaning;
import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TRelation;
import wikokit.base.wikt.sql.TRelationType;
import wikokit.base.wikt.sql.quote.TQuote;
import wikt.stat.printer.CommonPrinter;

/** YARN format exporter
 *
 * 
 * @see YARN format https://github.com/xoposhiy/yarn/commit/65411750ee8f867c79cdd77bcbaf8024df2c9d63
 */
public class DefQuoteSynExporterWordlist {
    private static final boolean DEBUG = false;
    //private static final FileWriter file;
    
    /** map for the first part of YARN file: lexicon. Map from word to "nID" */
    private static final Map<String, Integer> m_noun_word_to_id = new HashMap<String, Integer>();
    
    
    /** Prints words, definitions, quotations and synonyms for each part_of_speech ("poses") in Wiktonary.
     * .<br><br>
     *
     * SELECT * FROM lang_pos;
     *
     * @param connect connection to the database of the parsed Wiktionary
     */
    public static void printYARNwithWordlist (Connect wikt_parsed_conn,
                            LanguageType native_lang, 
                            Set<POS> exported_pos, List<String> export_words) {
        // lang_pos -> meaning (definition)
        //             meaning -> relation (synonym)
        //             meaning -> quote

        Statement s = null;
        ResultSet rs= null;
        long      t_start;
        StringBuilder  sb_words = new StringBuilder();
        StringBuilder  sb_synsets = new StringBuilder();
        
        sb_words.append  ("  <words>\n");
        sb_synsets.append("  <synsets>\n");
    
        /** Current incremental ID of word entry (YARN file first part - lexicon) */
        int current_word_id = 0;
        
        int current_synset_id = 0;
        int n_total = export_words.size();
        t_start = System.currentTimeMillis();
        
        int n_cur = 0;
outerloop:        
        for(String page_title : export_words) {
            n_cur ++;

            TPage tpage = TPage.get(wikt_parsed_conn, page_title);
            if(null == tpage) {
                System.out.print("* [[" + page_title + "]]\n"); // see http://ru.wiktionary.org/w/index.php?title=Участник:AKA_MBG/Todo
                continue;
            }
            TLangPOS[] tlang_pos_array = TLangPOS.get (wikt_parsed_conn, tpage);

            for(TLangPOS lang_pos_not_recursive : tlang_pos_array) {

                //TLangPOS lang_pos_not_recursive = TLangPOS.getByID (wikt_parsed_conn, id);// fields are not filled recursively
                if(null == lang_pos_not_recursive)
                    continue;
                LanguageType lang = lang_pos_not_recursive.getLang().getLanguage();
                if(lang != LanguageType.ru) // this is our language :) 
                    continue;

                // TPage tpage = lang_pos_not_recursive.getPage();
                // String page_title = tpage.getPageTitle();

                int n_meaning = WTMeaning.countMeanings(wikt_parsed_conn, lang_pos_not_recursive);
                if(0 == n_meaning)
                    continue;       // only meanings with nonempty definitions

                POS p = lang_pos_not_recursive.getPOS().getPOS();
                if(!exported_pos.contains(p))   // this is our POS :) it should be exported
                    continue;

                current_word_id ++;
                String xml_word = DefQuoteSynExporter.getWordEntryXMLWithoutDuplicates (wikt_parsed_conn, 
                                    p, current_word_id, page_title, page_title, native_lang, m_noun_word_to_id);
                sb_words.append( xml_word );

                if(DEBUG)
                    System.out.print("\n" + page_title + ", meanings:" + n_meaning);
                    //System.out.print(", pos:" + p.toString());

                TMeaning[] mm = TMeaning.get(wikt_parsed_conn, lang_pos_not_recursive);
                for(TMeaning m : mm) {

                    String meaning_text = m.getWikiTextString();
                    if(0 == meaning_text.length())
                        continue;

                    if(DEBUG)
                        System.out.print("\n    def: " + meaning_text);

                    TQuote[] quotes = TQuote.get (wikt_parsed_conn, m);
                    
                    current_synset_id ++;
                    StringBuilder xml_synset = new StringBuilder( DefQuoteSynExporter.
                                                getSynsetEntryBegin (p, current_synset_id, page_title, m_noun_word_to_id, quotes));

                    TRelation[] rels = TRelation.get(wikt_parsed_conn, m);
                    if(0 == rels.length)
                        continue;

                    for(TRelation tr : rels)
                    {
                        Relation r = tr.getRelationType();
                        if(Relation.synonymy != r)
                            continue;

                        String word = tr.getWikiText().getText(); // synonym
                        if(0 == word.compareToIgnoreCase("&nbsp")) // "&nbsp" instead of synonym :(
                            continue;

                        // if this synonym is absent in the dictionary, it should be added
                        if(-1 == DefQuoteSynExporter.getWordEntryID (p, word, m_noun_word_to_id)) {
                            current_word_id ++;
                            xml_word = DefQuoteSynExporter.getWordEntryXMLWithoutDuplicates (wikt_parsed_conn, 
                                       p, current_word_id, word, page_title, native_lang, m_noun_word_to_id);
                            sb_words.append( xml_word );
                        }

                        xml_synset.append( DefQuoteSynExporter.getSynonymWordRef (p, word, m_noun_word_to_id) );
                        if(DEBUG)
                            System.out.print("\n        syn: " + word);
                    }

                    sb_synsets.append( xml_synset );

                    String def = DefQuoteSynExporter.getDefinition (page_title, meaning_text, native_lang);
                    sb_synsets.append( def );

                    sb_synsets.append("    </synsetEntry>\n");
                }

                if(0 == n_cur % 1000) {   // % 100
                    if(DEBUG && n_cur > 333)
                        break outerloop;

                    long    t_cur, t_remain;

                    t_cur  = System.currentTimeMillis() - t_start;
                    t_remain = (long)((n_total - n_cur) * t_cur/(60f*1000f*(float)(n_cur)));
                    t_cur = (long)(t_cur/(60f*1000f));

                    System.out.println(n_cur + ": " +
                        ", duration: "  + t_cur +   // t_cur/(60f*1000f) +
                        " min, remain: " + t_remain +
                        " min");
                }

            } // eo for(TLangPOS
        } // eo for(export_words
        
        System.out.println("\n");
        sb_words.append("  </words>\n");
        System.out.println(sb_words.toString());
        
        sb_synsets.append("  </synsets>\n");
        System.out.println(sb_synsets.toString());

        // System.out.println("<!-- Number of exported meanings with nonempty definitions: " + current_word_id + " -->");
        // System.out.println("<!-- Total number of records in the table lang_pos: " + n_total + " -->");
    }

    public static List<String> readLines (String file_name) {
        
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get( file_name ), Charset.forName("UTF-8"));
            //for(String line:lines){
            //    System.out.println(line);
            //}
        } catch (IOException ex) {
            Logger.getLogger(DefQuoteSynExporterWordlist.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lines;
    }

    public static void main(String[] args) {

        // set of parts of speech to be exported
        Set<POS> exported_pos = new HashSet<POS>();
        exported_pos.add(POS.noun);
        
        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();
        LanguageType native_lang;
        
        // Russian
        native_lang = LanguageType.ru;
        wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);
        
        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        TRelationType.createFastMaps(wikt_parsed_conn);
        
        System.out.println("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
        CommonPrinter.printHeaderXML (wikt_parsed_conn.getDBName());
        System.out.println("<yarn>");
        
        List<String> export_words = readLines ("c:/w/bin/yarn/s_list_utf8.txt");
        
        printYARNwithWordlist (wikt_parsed_conn, native_lang, exported_pos, export_words);
        
        System.out.println("</yarn>");
        
        wikt_parsed_conn.Close();
    }    
}
