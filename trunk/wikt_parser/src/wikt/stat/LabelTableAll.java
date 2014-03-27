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
import wikokit.base.wikt.api.WTMeaning;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelCategory;
import wikokit.base.wikt.constant.LabelCategoryLocal;
import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.multi.en.name.LabelEn;
import wikokit.base.wikt.multi.ru.name.LabelCategoryRu;
import wikokit.base.wikt.multi.ru.name.LabelRu;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.label.TLabel;
import wikokit.base.wikt.sql.label.TLabelCategory;
import wikokit.base.wikt.sql.label.TLabelMeaning;
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
    
    
    /** Counts number of labels, category_labels (m_category_n),...
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
            
            if(LabelEn.isLabelFoundByParser( _label.getLinkedLabelEn()))
                continue;
            
            // _label added by hand, so != null
            LabelEn linked_label_en = _label.getLinkedLabelEn();
            LabelCategory label_category = linked_label_en.getCategory();
            
            /*
            LabelEn linked_label_en = _label.getLinkedLabelEn();
            if(null == linked_label_en)
                continue;
            
            LabelCategory label_category = linked_label_en.getCategory();
            if(LabelEn.isLabelFoundByParser( label_category))
                continue;*/

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
            
            if(!LabelEn.isLabelFoundByParser( _label.getLinkedLabelEn()))
                continue;
            
            /*LabelEn linked_label_en = _label.getLinkedLabelEn();
            if(null != linked_label_en && !isLabelFoundByParser( linked_label_en.getCategory() )) // label was added by hand
                continue;
            */
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
    
    /** Prints statistics about only 
     * (1) regional context labels added by hand (LabelCategory = regional) and
     * (2) regional context labels found by parser (LabelCategory = "regional automatic").
     */
    private static void printRegionalLabels (
                        Map<Label, ObjectWithWords> m_source_n)
    {
        System.out.println("\n=== Regional labels ===");

        System.out.println("\nRegional labels added by hand, category = \"regional\".");
        System.out.println("\nRegional labels found by parser, category = \"regional automatic\".");
        
        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.println("! Short name !! Category !! Counter !! words");

        // print values
        int counter = 0;
        int total = 0;
        for(Label _label : m_source_n.keySet()) {
            ObjectWithWords s_w = m_source_n.get(_label);            
        
            LabelCategory lc = _label.getCategory();
            
            /*LabelEn linked_label_en = _label.getLinkedLabelEn();
            if(null == linked_label_en)
                continue;
            
            LabelCategory label_category = linked_label_en.getCategory();
            */
            // print only regional labels
            if(lc != LabelCategory.regional && 
               lc != LabelCategory.regional_automatic)
                  continue;
            // at this line: label_category != null;
            
            counter ++;
            total = total + s_w.counter;
                                                   // replace since there are problems in wiki tables
            String short_name = _label.getShortName().replace("+", "<nowiki>+</nowiki>");
            
            System.out.println("|-");
            System.out.print(
                    "|" + short_name + 
                    "||" + lc + 
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
        counter --; // Unique regional labels without [empty 'regional' label, i.e. whithout regions]
        System.out.println("\nUnique regional labels used in definitions: " + counter );
        System.out.println("\nTotal regional labels used in definitions: " + total );
    }
    
    /** Calculates number of categories of labels (only added by hand), read data from m_source_n,
     * prints result to table "Label categories"
     */
    private static void calcAndPrintAddedByHandLabelCategories (
                        Map<Label, ObjectWithWords> m_source_n,
                        LanguageType wikt_lang)
    {

        /** Total number of label categories: <label category, total number). */
        Map<LabelCategory, Integer> m_category_n = new HashMap<LabelCategory, Integer>();
        
        // 1. sum labels for each category
        for(Label _label : m_source_n.keySet()) {
            ObjectWithWords s_w = m_source_n.get(_label);
            
            LabelCategory lc = _label.getCategory();
            if(null == lc)
                continue;
            /*if(null == lc) {
                
                // all except: |regional automatic||regional automatic||regional||182
                LabelEn linked_label_en = _label.getLinkedLabelEn();
                if(null == linked_label_en)
                    continue;

                lc = linked_label_en.getCategory();
                if(null == lc)
                    continue;
            }*/
            
            /* case 3: empty list
            LabelCategory lc = LabelEn.getCategoryByLabel(_label);
            if(null == lc)
                continue;*/
            
            if(m_category_n.containsKey(lc) ) {
                    int n = m_category_n.get(lc);
                    m_category_n.put(lc, n + s_w.counter);
                } else
                    m_category_n.put(lc, s_w.counter);
        }
        
        // 2. print table
        System.out.println("\n=== Label categories ===");
        System.out.println("\nNumber of labels for each label's category.");
        System.out.println("\nThese labels were added by hand only, since labels added automatically don't have categories (except \"regional automatic\" labels in Russian Wiktionary).\n");
        
        // + translation of label category into Russian
        String add_header = ""; 
        if(wikt_lang == LanguageType.ru)
            add_header = "! in Russian !";
        
        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.println(add_header+"! Category !! Parent category !! Number");
        
        int total = 0;
        for(LabelCategory _cat : m_category_n.keySet()) {
            int n = m_category_n.get( _cat );
            
            String add_translation = ""; 
            if(wikt_lang == LanguageType.ru)
                add_translation = "|"+LabelCategoryRu.getName(_cat)+"|";

            System.out.println("|-");
            System.out.println(
                    add_translation +
                    "|" + _cat.getName() + 
                    "||" + _cat.getParent().getName() + 
                    "||" + n );
            total += n;
        }
        System.out.println("|}");
        System.out.println("\nTotal labels added by hand (with categories): " + total );
    }
    
    
    /** Maximum number of meanings in one article (language - POS level) */
    static final int MAX_MEANINGS = 100;
    
    // ar_labels_meanings_words - all words, including {noun, verb, adverb, adjective} + pronoun, conjunction, etc.
    private static String[][] ar_labels_meanings_words          = new String[MAX_MEANINGS][MAX_MEANINGS];
    private static String[][] ar_labels_meanings_words_noun     = new String[MAX_MEANINGS][MAX_MEANINGS];
    private static String[][] ar_labels_meanings_words_verb     = new String[MAX_MEANINGS][MAX_MEANINGS];
    private static String[][] ar_labels_meanings_words_adverb   = new String[MAX_MEANINGS][MAX_MEANINGS];
    private static String[][] ar_labels_meanings_words_adjective = new String[MAX_MEANINGS][MAX_MEANINGS];
    
    private static int[][] ar_labels_meanings          = new int[MAX_MEANINGS][MAX_MEANINGS];
    private static int[][] ar_labels_meanings_noun     = new int[MAX_MEANINGS][MAX_MEANINGS];
    private static int[][] ar_labels_meanings_verb     = new int[MAX_MEANINGS][MAX_MEANINGS];
    private static int[][] ar_labels_meanings_adverb   = new int[MAX_MEANINGS][MAX_MEANINGS];
    private static int[][] ar_labels_meanings_adjective = new int[MAX_MEANINGS][MAX_MEANINGS];
    
    /** Counts number of meanings with labels, writes result to two-dimensional array,
     * fills by example words array ar_labels_meanings_words.
     * (1) Counts all words, writes to ar_labels_meanings and ar_labels_meanings_words.
     * (2) Counts ar_labels_meanings_noun, _verb, _adverb, _adjective only for one language: only_lang
     * <br><br>
     *
     * @param connect   connection to the database of the parsed Wiktionary
     * // skip @param only_pos  only this POS words will be counted, 
     * //                     if only_pos is NULL then all words will be counted
     * @param only_lang only this language words will be counted, 
     *                  if only_lang is NULL then words of all languages will be counted
     * @return integer two-dimensional array, where [X][Y] = Z means that 
     *                      X - number of meanings with labels; 
     *              Y - total number of meanings; 
     *      Z - number of words with Y meanings, where X meanings have one or more labels (X <= Y)
     */
    public static void countNumberOfMeaningsWithLabels ( Connect wikt_parsed_conn,
                                                         LanguageType only_lang) {
        // lang_pos -> meaning -> label_meaning 
        
        Statement s = null;
        ResultSet rs= null;
        long      t_start;

        int n_total = Statistics.Count(wikt_parsed_conn, "lang_pos");
        t_start = System.currentTimeMillis();

        try {
            s = wikt_parsed_conn.conn.createStatement ();
            s.executeQuery ("SELECT id FROM lang_pos");
            rs = s.getResultSet ();
            int n_cur = 0;
            while (rs.next ())
            {
                n_cur ++;
                int id = rs.getInt("id");
                TLangPOS lang_pos_not_recursive = TLangPOS.getByID (wikt_parsed_conn, id);// fields are not filled recursively
                if(null == lang_pos_not_recursive)
                    continue;
                LanguageType lang = lang_pos_not_recursive.getLang().getLanguage();
                
                TPage tpage = lang_pos_not_recursive.getPage();
                String page_title = tpage.getPageTitle();

                int n_meaning = WTMeaning.countMeanings(wikt_parsed_conn, lang_pos_not_recursive);
                if(0 == n_meaning)
                    continue;       // only meanings with nonempty definitions

                POS p = lang_pos_not_recursive.getPOS().getPOS();
                //if(null != only_pos && only_pos != p)   // only our POS should be counted :)
                //    continue;

                if(DEBUG)
                    System.out.print("\n" + page_title + ", meanings:" + n_meaning);
                    //System.out.print(", pos:" + p.toString());

                int meanings_with_labels = 0;
                TMeaning[] mm = TMeaning.get(wikt_parsed_conn, lang_pos_not_recursive);
                for(TMeaning m : mm) {
                    
                    String meaning_text = m.getWikiTextString();
                    if(0 == meaning_text.length())
                        continue;
                    
                    if(DEBUG)
                        System.out.print("\n    def: " + meaning_text);
                    
                    Label[] labels = TLabelMeaning.get(wikt_parsed_conn, m);
                    
                    if(null != labels && labels.length > 0)
                        meanings_with_labels ++;
                }
                ar_labels_meanings       [meanings_with_labels] [n_meaning] ++;
                ar_labels_meanings_words [meanings_with_labels] [n_meaning] = page_title;
                
                if(only_lang == lang) {   // calculates labels for 4 POS only for one language
                    switch(p.toString()) {
                        case "noun":
                            ar_labels_meanings_noun       [meanings_with_labels] [n_meaning] ++;
                            ar_labels_meanings_words_noun [meanings_with_labels] [n_meaning] = page_title;
                            break;
                        case "verb":
                            ar_labels_meanings_verb       [meanings_with_labels] [n_meaning] ++;
                            ar_labels_meanings_words_verb [meanings_with_labels] [n_meaning] = page_title;
                            break;
                        case "adverb":
                            ar_labels_meanings_adverb     [meanings_with_labels] [n_meaning] ++;
                            ar_labels_meanings_words_adverb [meanings_with_labels] [n_meaning] = page_title;
                            break;
                        case "adjective":
                            ar_labels_meanings_adjective  [meanings_with_labels] [n_meaning] ++;
                            ar_labels_meanings_words_adjective [meanings_with_labels] [n_meaning] = page_title;
                            break;
                    }
                }

                if(0 == n_cur % 1000) {   // % 100
                    if(DEBUG && n_cur > 2999)
                        break;

                    long    t_cur, t_remain;

                    t_cur  = System.currentTimeMillis() - t_start;
                    t_remain = (long)((n_total - n_cur) * t_cur/(60f*1000f*(float)(n_cur)));
                    t_cur = (long)(t_cur/(60f*1000f));

                    System.out.println(n_cur + ": " +
                        ", duration: "  + t_cur +   // t_cur/(60f*1000f) +
                        " min, remain: " + t_remain +
                        " min");
                }
                
            } // eo while
        } catch(SQLException ex) {
            System.err.println("SQLException (LabelTableAll.countLabelsForEachLangPOS()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        
        // return ar_labels_meanings;
    }
    
    
    private static void printMeaningsLabelsTableNumbersAndTableWords (
                                            int[][] ar, 
                                            String[][] ar_words) {
        
        // 1. Calculate maximum number of meanings, 
        // i.e. calculate maximum array index N with non-zero value in ar [N][N]
        int max_non_zero_meaning = 0;
        int max_non_zero_labels = 0;
        for(int i=0; i<MAX_MEANINGS; i++) {
            for(int j=0; j<MAX_MEANINGS; j++) {
                if(ar[i][j] > 0 && j > max_non_zero_meaning)
                    max_non_zero_meaning = j;
                
                if(ar[i][j] > 0 && i > max_non_zero_labels)
                    max_non_zero_labels = i;
            }
        }
        int MAX = max_non_zero_meaning;
        
        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");        
        System.out.print("! Y \\ X "); // top-left cell
        
        // print horizontal header - number of meanings of words
        for(int i=0; i<MAX+1; i++)
            System.out.print("!!" + i);
        System.out.println("");
        
        int total = 0;
        for(int i=0; i<MAX+1; i++) {
            
            // print vertical header - number of meanings with labels            
            System.out.println("|-");
            System.out.print(  "|" + i);
            
            for(int j=0; j<MAX+1; j++) {
                System.out.print(
                    "||" + ar[i][j] );
                total += ar[i][j];
            }
            System.out.println("");
        }
        System.out.println("|}");
        
        System.out.println("\nTotal number of entries (POS-level) with labels: " + total );
        System.out.println("\nMaximum number of meanings (with labels): "+MAX);
        System.out.println("\nMaximum number of meanings marked by labels: "+max_non_zero_labels);
        
        // part 2.
        System.out.println("\n\nThe same table with example words: ");
        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");        
        System.out.print("! Y \\ X "); // top-left cell
        
        // print horizontal header - number of meanings of words
        for(int i=0; i<MAX+1; i++)
            System.out.print("!!" + i);
        System.out.println("");
        
        for(int i=0; i<MAX+1; i++) {
            
            // print vertical header - number of meanings with labels            
            System.out.println("|-");
            System.out.print(  "|" + i);
            
            for(int j=0; j<MAX+1; j++) {
                String s = ar_words[i][j];
                s = null == s ? "" : "[[" + s + "]]" ;
                System.out.print("||" + s);
            }
            System.out.println("");
        }
        System.out.println("|}");
    }
    
    /** Prints number of meanings with labels in form of table.<br><br>
     */
    private static void printNumberOfMeaningsWithLabels (
                        int[][] ar_labels_meanings,         String[][] ar_labels_meanings_words,
                        int[][] ar_labels_meanings_noun,    String[][] ar_labels_meanings_words_noun,
                        int[][] ar_labels_meanings_verb,    String[][] ar_labels_meanings_words_verb,
                        int[][] ar_labels_meanings_adverb,  String[][] ar_labels_meanings_words_adverb,
                        int[][] ar_labels_meanings_adjective, String[][] ar_labels_meanings_words_adjective,
                        LanguageType native_lang)
    {
        System.out.println("\n=== Number of meanings with labels ===");
        
        System.out.println("\nTable contains two-dimensional integer array, where [X][Y] = Z means that \n"
                + ":: X (horizontal) - total number of meanings; \n" +
                  ":: Y (vertical) - number of meanings with labels; \n" +
                  ":: Z (value in cell) - number of words with Y meanings, where X meanings have one or more labels (X <= Y)");
        
        System.out.println("E.g. \"[[:ru:abdomen#Английский]]\" has 3 meanings, where 2 meanings are marked by labels, then [3][2] ++ (increments value in this cell of the table).");

        System.out.println("\n==== All ====");
        System.out.println("\n\nThese two tables (numbers and words) contains information about all languages and POS.");
        printMeaningsLabelsTableNumbersAndTableWords(ar_labels_meanings, ar_labels_meanings_words);
        
        System.out.println("\n==== "+native_lang.getName()+", noun ====");
        System.out.println("\n\nThis table takes into account only "+native_lang.getName()+" words, POS = noun.");
        printMeaningsLabelsTableNumbersAndTableWords(ar_labels_meanings_noun, ar_labels_meanings_words_noun);
        
        System.out.println("\n==== "+native_lang.getName()+", verb ====");
        System.out.println("\n\nThis table takes into account only "+native_lang.getName()+" words, POS = verb.");
        printMeaningsLabelsTableNumbersAndTableWords(ar_labels_meanings_verb, ar_labels_meanings_words_verb);
        
        System.out.println("\n==== "+native_lang.getName()+", adverb ====");
        System.out.println("\n\nThis table takes into account only "+native_lang.getName()+" words, POS = adverb.");
        printMeaningsLabelsTableNumbersAndTableWords(ar_labels_meanings_adverb, ar_labels_meanings_words_adverb);
        
        System.out.println("\n==== "+native_lang.getName()+", adjective ====");
        System.out.println("\n\nThis table takes into account only "+native_lang.getName()+" words, POS = adjective.");
        printMeaningsLabelsTableNumbersAndTableWords(ar_labels_meanings_adjective, ar_labels_meanings_words_adjective);
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
        
        LabelCategoryLocal temp0 = LabelCategoryRu.computing; // let's initialize maps in LabelCategoryRu class
        TLabelCategory.createFastMaps(wikt_parsed_conn);
        
        Label temp1 = LabelEn.Acadia; // let's initialize maps in LabelEn class
        Label temp2 = LabelRu.Yoruba; //                  ... in LabelRu class
        TLabel.createFastMaps(wikt_parsed_conn, native_lang);

        String db_name = wikt_parsed_conn.getDBName();
        System.out.println("\n== Statistics of context labels in the Wiktionary parsed database ==");
        System.out.println("\n''Last updated: summer 2013.''");
        CommonPrinter.printHeader (db_name);
/*
        int n_label_meaning = Statistics.Count(wikt_parsed_conn, "label_meaning");
        System.out.println("\nTotal labels used in definitions (meanings): " + n_label_meaning );
        
        int n_meaning = Statistics.countDistinct(wikt_parsed_conn, "label_meaning", "meaning_id");
        System.out.println("\nTotal definitions with labels: " + n_meaning );
        
        Map<LanguageType, Integer> m = LabelTableAll.countLabels(wikt_parsed_conn);
        */
        
        // part 2        
        LabelTableAll.countNumberOfMeaningsWithLabels(wikt_parsed_conn, native_lang);
        wikt_parsed_conn.Close();

        System.out.println();
  //      CommonPrinter.printSomethingPerLanguage(native_lang, m);

        /** Number of using labels in meanings (definitions) */
/*        LabelTableAll.printLabelsAddedByHand(m_label_n);
        LabelTableAll.printLabelsFoundByParser(m_label_n);
        LabelTableAll.printRegionalLabels(m_label_n);

        LabelTableAll.calcAndPrintAddedByHandLabelCategories(m_label_n, native_lang);
*/        
        // part 2 (print)
        LabelTableAll.printNumberOfMeaningsWithLabels(
                        ar_labels_meanings,         ar_labels_meanings_words,
                        ar_labels_meanings_noun,    ar_labels_meanings_words_noun,
                        ar_labels_meanings_verb,    ar_labels_meanings_words_verb,
                        ar_labels_meanings_adverb,  ar_labels_meanings_words_adverb,
                        ar_labels_meanings_adjective, ar_labels_meanings_words_adjective, 
                        native_lang);
        
        CommonPrinter.printFooter();
    }
    
    
}
