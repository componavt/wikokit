/* QuoteTableAll.java - quotes' statistics in the database of the parsed Wiktionary.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.stat;

import uk.ac.shef.wit.simmetrics.similaritymetrics.*;

import wikipedia.language.LanguageType;
//import wikipedia.language.Encodings;

import wikipedia.sql.*;
import wikt.sql.*;
import wikt.sql.quote.*;

import java.sql.*;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/** Quotes' statistics in the database of the parsed Wiktionary.
 */
public class QuoteTableAll {
    private static final boolean DEBUG = false;
    
    /** Number of quotes per language. */
    private static Map<LanguageType, Integer> m_lang_n = new HashMap<LanguageType, Integer>();

    /** Number of quotes for each source: <source name, example_words and counter). */
    private static Map<String, ObjectWithWords> m_source_n = new HashMap<String, ObjectWithWords>();

    /** Number of quotes for each author: <author name, example_words and counter). */
    private static Map<String, ObjectWithWords> m_author_n = new HashMap<String, ObjectWithWords>();

    /** The linear list of authors (the same as m_author_n.keys()),
     * it is required to store the order of adding of authors (for sorting and clustering). */
    //private static List<String> l_author_n = new ArrayList<String>();

    private static Map<String, String> author_to_cluster = new HashMap<String, String>();

    private static int MAX_EXAMPLE_WORDS = 3;

    private static AbstractStringMetric metric = new JaroWinkler();
    private static float CLUSTER_THRESHOLD = 0.87F;


    /** Inner class which contains the string which is nearest to some word,
     * the distance is stored in 'dist'.
     */
    private static class NearestWord {
        NearestWord(float _dist, String _nearest_name) {
            dist = _dist;
            nearest_name = _nearest_name;
            checked = false;
        }

        /** Distance to 'nearest_name'. */
        float dist;

        /** String which is nearest to some word. */
        String nearest_name;
        
        boolean checked;
    }

    /** Inner class which contains an object with a (small, example) list of words using this object.
     * An object is a source, or author, or...
     */
    private static class ObjectWithWords {
        ObjectWithWords(String _object_name, String _object_wikilink) {
            object_name = _object_name;
            object_wikilink = _object_wikilink;
            example_words = new ArrayList<String>();
            counter = 0;

            nearest_word = null;
        }

        /** Object's name, e.g. source of the quote, or author name, etc. */
        public String object_name;

        /** Object's second name, e.g. quot_ref.title_wikilink, or quot_author.wikilink */
        public String object_wikilink;

        /** Example of several entries which refer to this source. */
        public List<String> example_words;

        /** Counter of using this Source, or Author, or Title in Wiktionary entries. */
        public int counter;

        /** Distance from this->object_name to nearest another object_name. */
        NearestWord nearest_word;

        
        /** Calculates shortest distance between source word and the set of words 'words'.
         * Returns object NearestWord with shortest distance and the nearest word.
         *
         * Remark: nearest word has maximum distance.
         */
        private static NearestWord calcDistance(String source_word, Set<String> words)
        {

            float max_dist = 0;
            String nearest_word = "";
            boolean b_first = true;

            if(0 == source_word.length() || 0 == words.size()) {
                System.out.println("Warning (QuoteTableAll.ObjectWithWords.calcDistance()): source_word or words are empty!");
                return new NearestWord (max_dist, nearest_word);
            }

            for(String w : words) {
                float dist = metric.getSimilarity(source_word, w);
                
                if( b_first ) {
                    b_first = false;
                    max_dist = dist;
                    nearest_word = w;
                } else {
                    if(dist > max_dist) {
                        max_dist = dist;
                        nearest_word = w;
                        if(DEBUG && max_dist > CLUSTER_THRESHOLD)
                            System.out.println("(QuoteTableAll.ObjectWithWords.calcDistance()): max dist("+source_word+
                                            ", "+ w +")= "+max_dist);
                    }
                }
            }
            return new NearestWord (max_dist, nearest_word);
        }


        /** Adds new quote object (source, or author...) to the map m;
         * if there is space (< MAX_EXAMPLE_WORDS), then add example word for this object.
         */
        private static void add(String page_title, 
                                String _object_name, String _object_wikilink, // TQuotSource tsource, // String _source,
                                Map<String, ObjectWithWords> m,
                                Map<String, String> word_to_cluster)
                                //List<String> ordered_list)
        {
            if(0 == _object_name.length()) {
                System.out.println("Warning (QuoteTableAll.ObjectWithWords.add()): page=" +page_title+ " with empty _object_name!");
                return;
            }
            
            ObjectWithWords s_w = m.get(_object_name);

            if(null == s_w) {
                s_w = new ObjectWithWords(_object_name, _object_wikilink);
                s_w.counter = 1;
                s_w.example_words = new ArrayList<String>();

                if(!s_w.example_words.contains(page_title))
                    s_w.example_words.add(page_title);
                
                /*if(null != ordered_list) {
                    ordered_list.add(_object_name);
                    s_w.nearest_word = calcDistance( _object_name, m.keySet());
                }*/
                if(null != word_to_cluster) {
                    NearestWord nw = calcDistance( _object_name, m.keySet());
                    s_w.nearest_word = nw;
                    if(nw.dist > CLUSTER_THRESHOLD) {

                        String cluster_name = "";
                        String a = _object_name;
                        String b = nw.nearest_name;
                        if(!word_to_cluster.containsKey(a) && !word_to_cluster.containsKey(b)) {
                            cluster_name = _object_name;
                        } else if(word_to_cluster.containsKey(a)) {
                            cluster_name = word_to_cluster.get(a);
                        } else if(word_to_cluster.containsKey(b)) {
                            cluster_name = word_to_cluster.get(b);
                        }
                        word_to_cluster.put(a, cluster_name);
                        word_to_cluster.put(b, cluster_name);
                        //System.out.println("(QuoteTableAll.ObjectWithWords.add()): page=" +page_title+ " cluster:"+cluster_name+
                        //        " + two words: '"+a+"' (len="+a.length()+") and '"+b+"' (len="+b.length()+"); equals="+a.equalsIgnoreCase(b));
                    }
                }
                m.put(_object_name, s_w);

            } else {

                s_w.counter += 1;

                if(s_w.example_words.size() < MAX_EXAMPLE_WORDS) {
                    if(!s_w.example_words.contains(page_title))
                        s_w.example_words.add(page_title);
                }
            }
        }
    }

    /** Collects words from one cluster to one list.
     */
    private static Map<String, List<String>> collectWordsToCluster(Map<String, String> word_to_cluster)
    {
        Map<String, List<String>> cluster_to_words = new HashMap<String, List<String>>();

        for(String word : word_to_cluster.keySet()) {
            String cluster = word_to_cluster.get(word);

            List<String> words = cluster_to_words.get(cluster);
            if(null == words) {
                words = new ArrayList<String>();
                words.add(word);
                cluster_to_words.put(cluster, words);
            } else {
                words.add(word);
            }
        }
        return cluster_to_words;
    }

    /** Counts number of quotes, authors, sources,...
     * by selecting all records from the table 'quote' from the database of the parsed Wiktionary.<br><br>
     * SELECT * FROM quote;
     *
     * @param connect   connection to the database of the parsed Wiktionary
     * @return map      from the language into a number of translation boxes
     *                  which contain synonyms, antonyms, etc. in English (etc.)
     */
    public static Map<LanguageType, Integer> countQuotes (Connect wikt_parsed_conn) {
        // translation -> lang -> count

        Statement   s = null;
        ResultSet   rs= null;
        long    t_start;

        int n_unknown_lang_pos = 0; // translations into unknown languages

        int n_total_with_authors = 0;
        int n_total_with_sources = 0;
        int n_total = Statistics.Count(wikt_parsed_conn, "quote");
        //System.out.println("Total quotes: " + n_total);
        t_start = System.currentTimeMillis();

        try {
            s = wikt_parsed_conn.conn.createStatement ();
            StringBuilder str_sql = new StringBuilder();
            if(DEBUG)   //      SELECT id, meaning_id, lang_id, text, ref_id FROM quote LIMIT 3
                str_sql.append("SELECT id, meaning_id, lang_id, ref_id FROM quote LIMIT 7000");    // 10000
            else
                str_sql.append("SELECT id, meaning_id, lang_id, ref_id FROM quote");
            
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            int n_cur = 0;
            int i;
            while (rs.next ())
            {
                n_cur ++;
                int         id =                                    rs.getInt("id");
                TMeaning    m = TMeaning.getByID(wikt_parsed_conn,  rs.getInt("meaning_id"));
                TLang       tlang = TLang.getTLangFast(             rs.getInt("lang_id"));

                i = rs.getInt("ref_id");
                TQuotRef quot_ref = (0 == i) ? null : TQuotRef.getByID(wikt_parsed_conn, i);

                LanguageType lang = tlang.getLanguage();
                if(m_lang_n.containsKey(lang) ) {
                    int n = m_lang_n.get(lang);
                    m_lang_n.put(lang, n + 1);
                } else
                    m_lang_n.put(lang, 1);

                if(null == m) {
                    System.out.println("Warning (QuoteTableAll.countQuotes()): there is quote with id=" +id+ " with NULL meaning_id!");
                    continue;
                }

                TLangPOS lang_pos = m.getLangPOS(wikt_parsed_conn);
                if(null != lang_pos) {
                    TPage tpage = lang_pos.getPage();
                    String page_title = tpage.getPageTitle();

                    if(null != quot_ref) {
                        TQuotSource tquot_source = quot_ref.getSource();
                        if(null != tquot_source) {
                            n_total_with_sources ++;
                            ObjectWithWords.add(page_title, tquot_source.getText(), "", m_source_n, null);
                        }

                        TQuotAuthor tquot_author = quot_ref.getAuthor();
                        if(null != tquot_author) {
                            n_total_with_authors ++;
                            ObjectWithWords.add(page_title, tquot_author.getName(), tquot_author.getWikilink(), 
                                                m_author_n, author_to_cluster); // l_author_n);
                        }
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
            System.err.println("SQLException (QuoteTableAll.countQuotes()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        //long  t_end;
        //float   t_work;
        //t_end  = System.currentTimeMillis();
        //t_work = (t_end - t_start)/1000f; // in sec
        System.out.println(//"\nTime sec:" + t_work +
            "\nTotal quotes: " + n_total +
            "\n\nTotal quotes with sources: " + n_total_with_sources +
            "\n\nThere are "+ m_source_n.size() +" unique sources " +
            "\n\nTotal quotes with authors: " + n_total_with_authors +
            "\n\nThere are "+ m_author_n.size() +" unique author names " +
            "\n\nThere are quotes in " + m_lang_n.size() + " languages." +
            "\n\nUnknown<ref>'''Unknown''' - words which have quotes but have unknown language code and POS</ref>: "
            + n_unknown_lang_pos);

        return m_lang_n;
    }

    /** Prints statistics about quote sources in Wiktionary.
     */
    private static void printQuoteSource (
                        Map<String, ObjectWithWords> m_source_n)
    {
        // print header line
        System.out.println("\n=== Quote sources ===");

        //System.out.println("\n'''Number of entries''' is a number of (Language & POS level) entries per language. E.g. the Wiktionary article \"[[:en:rook|rook]]\" contains three English and two Dutch entries of Part Of Speech level.");
        //System.out.println("\n'''Total''' is a total number of relations, i.e. synonyms + antonyms + etc...\n");

        /** Number of quotes for each source: <source name, example_words and counter). */

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.print(" ! Source name || Number of quotes || Examples ");

        // print values
        for(String _source : m_source_n.keySet()) {
            ObjectWithWords s_w = m_source_n.get(_source);

                //System.out.print("|| " + lang.getName() + " || " + lang.getCode());
            System.out.print("\n|-\n! " + _source +
                    " || " + s_w.counter + " || " );

            List<String> words = s_w.example_words;
            for(String w : words)
                System.out.print("[[" + w + "]], ");

            System.out.print(" || ");
        }
        System.out.println("\n|}");
    }

    /** Prints statistics about quote sources in Wiktionary.
     * Split names to to clusters.
     */
    private static void printQuoteAuthor (
                        Map<String, ObjectWithWords> m_author_n,
                        Map<String, List<String>> cluster_to_words) // Map<String, String> word_to_cluster)
                        //List<String> l_author_n)
    {
        System.out.println("\n=== Quote authors ===");

        /** Number of quotes for each source: <source name, example_words and counter). */

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.print(" ! Author name || Author wikilink || Number of quotes || Examples ");

        // print values from end to start, since last elements has nearest element links
        //Collections.reverse(l_author_n);

        // 1) print clusters of words
        int cluster_counter = 1;
        for(String cluster : cluster_to_words.keySet()) {
            List<String> ww = cluster_to_words.get(cluster);

            // | colspan=\"4\" style=\"text-align: center;\" | Cluster
            System.out.print("\n|-\n| colspan=\"4\" style=\"text-align: center;\" | Cluster " + (cluster_counter ++));

            for(String _name : ww) {
                ObjectWithWords s_w = m_author_n.get(_name);
                s_w.nearest_word.checked = true;

                //if(s_w.example_words.size() > 2) {

                    System.out.print("\n|-\n! " + _name +
                            " || " + s_w.object_wikilink +
                            " || " + s_w.counter + " || " );

                    List<String> words = s_w.example_words;
                    for(String w : words)
                        System.out.print("[[" + w + "]], ");
                //}
            }
        }

        // 2) print remaining words (not in any cluster)

        System.out.print("\n|-\n| colspan=\"4\" style=\"text-align: center;\" | Not in clusters ");

        //for(String _name : l_author_n) {
        for(String _name : m_author_n.keySet()) {
            ObjectWithWords s_w = m_author_n.get(_name);
            if(s_w.nearest_word.checked)
                continue;
            s_w.nearest_word.checked = true;

            // if(s_w.example_words.size() > 2) {

                System.out.print("\n|-\n! " + _name +
                        " || " + s_w.object_wikilink +
                        " || " + s_w.counter + " || " );

                List<String> words = s_w.example_words;
                for(String w : words)
                    System.out.print("[[" + w + "]], ");
            //}
        }
        System.out.println("\n|}");
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
        //TRelationType.createFastMaps(wikt_parsed_conn);

        String db_name = wikt_parsed_conn.getDBName();
        System.out.println("\n== Statistics of quotes in the Wiktionary parsed database ==");
        WikiPrinterStat.printHeader (db_name);

        Map<LanguageType, Integer> m = QuoteTableAll.countQuotes(wikt_parsed_conn);
        wikt_parsed_conn.Close();

        Map<String, List<String>> cluster_to_authors = collectWordsToCluster(author_to_cluster); // author_to_cluster
        //private static Map<String, String> author_to_cluster = new HashMap<String, String>();

        System.out.println();
        //int total_quotes =
        WikiPrinterStat.printSomethingPerLanguage(native_lang, m);
        //System.out.println("Total quotes: " + total_quotes);

        /** Number of quotes for each source: <source name, example_words and counter). */
        QuoteTableAll.printQuoteSource(m_source_n);

        QuoteTableAll.printQuoteAuthor(m_author_n, cluster_to_authors); // l_author_n);

        //System.out.println("\nThere are quotes in " + m.size() + " languages.");
        WikiPrinterStat.printFooter();
    }

}
