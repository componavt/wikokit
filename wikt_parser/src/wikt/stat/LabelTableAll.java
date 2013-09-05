/* LabelTableAll.java - context labels statistics in the database of the parsed Wiktionary.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikt.stat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.Statistics;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelCategory;
import wikokit.base.wikt.multi.en.name.LabelEn;
import wikokit.base.wikt.multi.ru.name.LabelRu;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.label.TLabel;
import wikokit.base.wikt.sql.label.TLabelCategory;
import wikt.stat.printer.CommonPrinter;

/** Context labels statistics in the database of the parsed Wiktionary. */
public class LabelTableAll {
    
    private static final boolean DEBUG = false;
    
    /** Number of labels per language. */
    private static Map<LanguageType, Integer> m_lang_n = new HashMap<LanguageType, Integer>();

    /** Number of meanings for each label: <label, example_words and counter). */
    private static Map<Label, ObjectWithWords> m_label_n = new HashMap<Label, ObjectWithWords>();
    
    private static int MAX_EXAMPLE_WORDS = 3;
    
    
    /** Inner class which contains an object with a (small, example) list of words using this object.
     * An object is a label with several words with this label
     */
    private static class ObjectWithWords {
        ObjectWithWords(Label _label) {
            label = _label;
            example_words = new ArrayList<String>();
            counter = 0;
        }

        /** Object's name, e.g. labels in meanings, or labels in relations, or labels in translations. */
        public Label label;

        /** Example of several entries which use this label. */
        public List<String> example_words;

        /** Counter of using this label in Wiktionary entries. */
        public int counter;


        /** Adds new label to the map m;
         * if there is space (< MAX_EXAMPLE_WORDS), then add example word for this object.
         */
        private static void add(String page_title, 
                                Label _label,
                                Map<Label, ObjectWithWords> m)
        {
            if(null == _label) { // 0 == _object_name.length()) {
                System.out.println("Warning (LabelTableAll.ObjectWithWords.add()): page=" +page_title+ " with empty _object_name!");
                return;
            }
            
            ObjectWithWords s_w = m.get(_label);

            if(null == s_w) {
                s_w = new ObjectWithWords(_label);
                s_w.counter = 1;
                s_w.example_words = new ArrayList<String>();

                if(!s_w.example_words.contains(page_title))
                    s_w.example_words.add(page_title);
                
                m.put(_label, s_w);

            } else {

                s_w.counter += 1;

                if(s_w.example_words.size() < MAX_EXAMPLE_WORDS) {
                    if(!s_w.example_words.contains(page_title))
                        s_w.example_words.add(page_title);
                }
            }
        }
    } // eo class ObjectWithWords
    
    
    /** Counts number of quotes, authors, sources,...
     * by selecting all records from the table 'quote' from the database of the parsed Wiktionary.<br><br>
     * SELECT * FROM quote;
     *
     * @param connect   connection to the database of the parsed Wiktionary
     * @return map      from the language into a number of translation boxes
     *                  which contain synonyms, antonyms, etc. in English (etc.)
     */
    public static Map<LanguageType, Integer> countLabels (Connect wikt_parsed_conn) {
        // label_meaning -> meaning -> lang_pos -> lang -> count

        Statement   s = null;
        ResultSet   rs= null;
        long    t_start;

        int n_unknown_lang_pos = 0; // translations into unknown languages

        int n_total = Statistics.Count(wikt_parsed_conn, "label");
        t_start = System.currentTimeMillis();

        Map<Integer, Label> id2label = TLabel.getAllID2Labels();
        // System.out.println("id2label size=" + id2label.size());
        /*for (Map.Entry<Integer, Label> entry : id2label.entrySet()) {
            Integer _label_id = entry.getKey();
            Label   _label = entry.getValue();
        }*/
        
        // SELECT label_id, meaning_id FROM label_meaning 
        // select * from label where category_id IS NULL and counter>0
        
        // SELECT label_id, meaning_id FROM label_meaning, label WHERE id=label_id AND category_id IS NULL and counter>0 LIMIT 3;
        
        try {
            s = wikt_parsed_conn.conn.createStatement();
            StringBuilder str_sql = new StringBuilder();
            if(DEBUG)
                str_sql.append("SELECT label_id, meaning_id FROM label_meaning LIMIT 5000");    // 10000 37000
                //str_sql.append("SELECT label_id, meaning_id FROM label_meaning WHERE label_id=465");
            else
                str_sql.append("SELECT label_id, meaning_id FROM label_meaning");
            
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            int n_cur = 0;
            while (rs.next ())
            {
                n_cur ++;
                int label_id = rs.getInt("label_id");                
                
                Label   label = id2label.get( label_id );
                TMeaning    m = TMeaning.getByID(wikt_parsed_conn,  rs.getInt("meaning_id"));
                
                TLangPOS lang_pos = m.getLangPOS(wikt_parsed_conn);
                TLang   tlang =     lang_pos.getLang();

                LanguageType lang = tlang.getLanguage();
                if(m_lang_n.containsKey(lang) ) {
                    int n = m_lang_n.get(lang);
                    m_lang_n.put(lang, n + 1);
                } else
                    m_lang_n.put(lang, 1);

                if(null == m) {
                    System.out.println("Warning (LabelTableAll.countLabels()): there is label with label_id=" +label_id+ " with NULL meaning_id!");
                    continue;
                }

                if(null != lang_pos) {
                    TPage tpage = lang_pos.getPage();
                    String page_title = tpage.getPageTitle();

                    if(null != label) {
                        ObjectWithWords.add(page_title, label, m_label_n);
                    }
                    
                    if(DEBUG && 0 == n_cur % 1000) {   // % 100
                        //if(n_cur > 333)
                          //  break;
                        long    t_cur, t_remain;

                        t_cur  = System.currentTimeMillis() - t_start;
                        t_remain = (long)((n_total - n_cur) * t_cur/(60f*1000f*(float)(n_cur)));
                                   // where time for 1 page = t_cur / n_cur
                                   // in min, since /(60*1000)
                        t_cur = (long)(t_cur/(60f*1000f));
                        //t_cur = t_cur/(60f*1000f));

                        if(null != tpage) {
                            System.out.println(n_cur + ": " + tpage.getPageTitle() +
                                ", duration: "  + t_cur +   // t_cur/(60f*1000f) +
                                " min, remain: " + t_remain +
                                " min");
                        }
                    }
                } else
                    n_unknown_lang_pos ++;
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (LabelTableAll.countLabels()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        //long  t_end;
        //float   t_work;
        //t_end  = System.currentTimeMillis();
        //t_work = (t_end - t_start)/1000f; // in sec
        
        int n_labels_by_hand         = TLabel.countLabelsAddedByHand  (wikt_parsed_conn);
        int n_labels_found_by_parser = TLabel.countLabelsFoundByParser(wikt_parsed_conn);
        
        System.out.println(//"\nTime sec:" + t_work +
            "\nTotal unique labels: " + n_total +
            "\n\nUnique labels added by hand: "   + n_labels_by_hand +
            "\n\nUnique labels found by parser: " + n_labels_found_by_parser +
//            "\n\nTotal meanings with labels: " + n_total_with_authors +
//            "\n\nTotal meanings : " + n_total_with_authors +
            "\n\nThere are labels for words in " + m_lang_n.size() + " languages." +
            "\n\nUnknown<ref>'''Unknown''' - number of words with labels (but language code and POS are unknown)</ref>: "
            + n_unknown_lang_pos);

        return m_lang_n;
    }

    /** Prints statistics about context labels added by hand.
     */
    private static void printLabelsAddedByHand (
                        Map<Label, ObjectWithWords> m_source_n)
    {
        System.out.println("\n=== Labels added by hand ===");

        //System.out.println("\n'''Number of entries''' is a number of (Language & POS level) entries per language. E.g. the Wiktionary article \"[[:en:rook|rook]]\" contains three English and two Dutch entries of Part Of Speech level.");
        //System.out.println("\n'''Total''' is a total number of relations, i.e. synonyms + antonyms + etc...\n");

        /** Number of quotes for each source: <source name, example_words and counter). */

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.println("! English !! Template !! Short name !! Name !! Category !! Counter !! words");

        // print values
        for(Label _label : m_source_n.keySet()) {
            ObjectWithWords s_w = m_source_n.get(_label);
            
            LabelEn linked_label_en = _label.getLinkedLabelEn();
            if(null == linked_label_en)
                continue;
            
            LabelCategory label_category = linked_label_en.getCategory();
            if(null == label_category)
                continue;

            System.out.println("|-");
            System.out.print(
                    "|" + _label.getShortNameEnglish() +
                    "||{{"  + _label.getShortName() + 
                    "}}||" + _label.getShortName() + 
                    "||" + _label.getName() + 
                    "||" + label_category.getName() +
                    "||" + s_w.counter + "||" );

            StringBuilder sb = new StringBuilder();
            List<String> words = s_w.example_words;
            for(String w : words)
                sb.append("[[").append(w).append("]], ");
            if(sb.length() > 3)
                sb.delete(sb.length()-2, sb.length());
            System.out.println( sb.toString() );
        }
        System.out.println("|}");
    }
    
    /** Prints statistics about context labels found by parser.
     */
    private static void printLabelsFoundByParser (
                        Map<Label, ObjectWithWords> m_source_n)
    {
        System.out.println("\n=== Labels found by parser ===");

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.println("! Short name !! Length !! Counter !! words");

        // print values
        for(Label _label : m_source_n.keySet()) {
            ObjectWithWords s_w = m_source_n.get(_label);
            
            LabelEn linked_label_en = _label.getLinkedLabelEn();
            if(null != linked_label_en)
                continue;
            
            /*LabelCategory label_category = linked_label_en.getCategory();
            if(null == label_category)
                continue;
            
            LabelCategory label_category = _label.getLinkedLabelEn().getCategory();
            if(null != label_category)
                continue;*/

                                                   // replace since there are problems in wiki tables
            String short_name = _label.getShortName().replace("+", "<nowiki>+</nowiki>");
            
            System.out.println("|-");
            System.out.print(
                    "|" + short_name + 
                    "||" + _label.getShortName().length() + 
                    "||" + s_w.counter + "||" );

            StringBuilder sb = new StringBuilder();
            List<String> words = s_w.example_words;
            for(String w : words)
                sb.append("[[").append(w).append("]], ");
            if(sb.length() > 3)
                sb.delete(sb.length()-2, sb.length());
            System.out.println( sb.toString() );
        }
        System.out.println("|}");
    }
    
    /** Prints statistics about only regional context labels found by parser
     * (LabelCategory = regional).
     */
    private static void printRegionalLabelsFoundByParser (
                        Map<Label, ObjectWithWords> m_source_n)
    {
        System.out.println("\n=== Regional labels found by parser ===");

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.println("! Short name !! Length !! Counter !! words");

        // print values
        for(Label _label : m_source_n.keySet()) {
            ObjectWithWords s_w = m_source_n.get(_label);
            
            LabelEn linked_label_en = _label.getLinkedLabelEn();
            if(null != linked_label_en)
                continue;
            
            LabelCategory label_category = linked_label_en.getCategory();
            if(null == label_category)
                continue;
            
            /*LabelCategory label_category = _label.getLinkedLabelEn().getCategory();
            if(null != label_category)
                continue;*/
            
            // print only regional labels
            if(label_category != LabelCategory.regional)
                continue;

                                                   // replace since there are problems in wiki tables
            String short_name = _label.getShortName().replace("+", "<nowiki>+</nowiki>");
            
            System.out.println("|-");
            System.out.print(
                    "|" + short_name + 
                    "||" + _label.getShortName().length() + 
                    "||" + s_w.counter + "||" );

            StringBuilder sb = new StringBuilder();
            List<String> words = s_w.example_words;
            for(String w : words)
                sb.append("[[").append(w).append("]], ");
            if(sb.length() > 3)
                sb.delete(sb.length()-2, sb.length());
            System.out.println( sb.toString() );
        }
        System.out.println("|}");
    }
    
    
    public static void main(String[] args) {

        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();

        // Russian
        LanguageType native_lang = LanguageType.ru;
        wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);

        // English
        //LanguageType native_lang = LanguageType.en;
        //wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, LanguageType.en);

        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        // ? TRelationType.createFastMaps(wikt_parsed_conn);
        TLabelCategory.createFastMaps(wikt_parsed_conn);
        
        Label temp1 = LabelEn.Acadia; // let's initialize maps in LabelEn class
        Label temp2 = LabelRu.Yoruba; //                  ... in LabelRu class
        TLabel.createFastMaps(wikt_parsed_conn, native_lang);

        String db_name = wikt_parsed_conn.getDBName();
        System.out.println("\n== Statistics of context labels in the Wiktionary parsed database ==");
        CommonPrinter.printHeader (db_name);

        int n_label_meaning = Statistics.Count(wikt_parsed_conn, "label_meaning");
        System.out.println("\nTotal labels used in definitions (meanings): " + n_label_meaning );
        
        Map<LanguageType, Integer> m = LabelTableAll.countLabels(wikt_parsed_conn);
        wikt_parsed_conn.Close();

        System.out.println();
        CommonPrinter.printSomethingPerLanguage(native_lang, m);

        /** Number of using labels in meanings (definitions) */
        LabelTableAll.printLabelsAddedByHand(m_label_n);
        LabelTableAll.printLabelsFoundByParser(m_label_n);
        LabelTableAll.printRegionalLabelsFoundByParser(m_label_n);
        
        //System.out.println("\nThere are quotes in " + m.size() + " languages.");
        CommonPrinter.printFooter();
    }
    
    
}
