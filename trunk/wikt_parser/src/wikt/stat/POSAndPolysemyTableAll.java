/* POSAndPolysemyTableAll.java - Parts of speech statistics and data about
 * polysemy in the database of the parsed Wiktionary.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.stat;

import wikt.stat.printer.POSAndPolysemyPrinter;
import wikt.stat.printer.CommonPrinter;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikipedia.sql.Statistics;

import wikt.sql.*;
import wikt.constant.POS;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import wikt.api.WTMeaning;


/** Parts of speech statistics and data about
 * polysemy in the database of the parsed Wiktionary.
 *
 * @see for inspiration: http://wordnet.princeton.edu/wordnet/man/wnstats.7WN.html
 */
public class POSAndPolysemyTableAll {
    private static final boolean DEBUG = false;

    
    /** Let's constrain the maximum number of meanings/definitions for one word */
    private static final int max_meanings = 100;
    private static final int[] mean_histogram = new int[max_meanings];
    
    // histogram for each language
    private static final Map<LanguageType, Integer[]> m_lang_histogram = new HashMap<LanguageType, Integer[]>();

    /** Inner POSStat class for each POS. */
    public static class POSStat {
        POSStat() {
            uniques_strings = 0;
            word_sense_pairs = 0;
            monosemous = 0;
            polysemous_words = 0;
            polysemous_senses = 0;
            
            max_senses1 = 0;    max_senses2 = 0;    max_senses3 = 0;
            page_title1 = "";   page_title2 = "";   page_title3 = "";
        }

        /** Total number of LangPOS for this POS (?sum of all languages). */
        private int uniques_strings;

        /** Total number of all meanings of LangPOS for this POS (?sum of all languages). */
        private int word_sense_pairs;

        /** Number of monosemous words and senses for this POS (i.e. LangPOS with one meaning). */
        private int monosemous;

        /** Number of polysemous words (i.e. LangPOS has more than one meaning) for this POS. */
        private int polysemous_words;

        /** Number of polysemous senses (i.e += number of meanings of LangPOS) for this POS. */
        private int polysemous_senses;

        /** Value of maximum number of senses for this POS (for page_title1,2,3). */
        private int max_senses1;
        private int max_senses2;
        private int max_senses3;
        
        /** Word (3 words) with maximum number of senses with this POS. */
        private String page_title1;
        private String page_title2;
        private String page_title3;

        public int getNumberOfUniquesStrings() {
            return uniques_strings;
        }
        public int getNumberOfWordSensePairs() {
            return word_sense_pairs;
        }
        public int getMonosemous() {
            return monosemous;
        }
        public int getPolysemousWords() {
            return polysemous_words;
        }
        public int getPolysemousSenses() {
            return polysemous_senses;
        }
        public float calcAveragePolysemyIncludingMonosemousWords() {
            if(0 == uniques_strings)
                return -1f;
            return new Float(word_sense_pairs) / new Float(uniques_strings);
        }
        public float calcAveragePolysemyExcludingMonosemousWords() {
            if(0 == polysemous_words)
                return -1f;
            return new Float(word_sense_pairs - monosemous) / new Float(polysemous_words);
        }

        /** Maximum number of senses (meanings, definitions) for this POS. */
        public int getMaxSenses() {
            return max_senses1;
        }

        /** Word with maximum number of senses (meanings, definitions) for this POS. */
        public String getWikifiedWordWithMaxSenses() {
            
            String result = "";
            
            if(page_title1.length() > 0)
                result += "[[" + page_title1 + "]], ";
            
            if(page_title2.length() > 0)
                result += "[[" + page_title2 + "]], ";
            
            if(page_title3.length() > 0)
                result += "[[" + page_title3 + "]]";
            
            return result;
        }
        
        /** Increment statistics data.
         * @param n_meaning         number of meanings for this POS
         */
        public void addPOS(int n_meaning, String current_page_title) {
            uniques_strings += 1;
            word_sense_pairs += n_meaning;

            if(1 == n_meaning) {
                monosemous += 1;
            } else {
                polysemous_words += 1;
                polysemous_senses += n_meaning;
            }

            if(n_meaning > max_senses1) {
                max_senses1 = n_meaning;
                page_title1 = current_page_title;
            } else if(n_meaning > max_senses2) {
                max_senses2 = n_meaning;
                page_title2 = current_page_title;
            } else if(n_meaning > max_senses3) {
                max_senses3 = n_meaning;
                page_title3 = current_page_title;
            }
        }
    }

    /** List of the words with the maximum number of meanings,
     * or the maximum number of types of semantic relations. */
    //private static final List<TLangPOS> words_with_many_meanings = new ArrayList<TLangPOS>();

    /** Number of meanings for each POS, sum by all languages,
     * e.g. noun = English nouns + Russian nouns + ..., etc. **/
    private static final Map<POS,POSStat> m_pos_sum_all_lang = new HashMap(POS.size());

    
    /** Counts number of different POS, cycle for each LangPOS.
     * .<br><br>
     *
     * SELECT * FROM lang_pos;
     *
     * @param connect connection to the database of the parsed Wiktionary
     *
     * @return histogram with number of semantic relations, i.e.
     * [0] = number of words (one language, one part of speech) without any semantic relations,
     * [1] = number of words with one relation, etc.
     */
    public static Map<LanguageType, Map<POS,POSStat>> countPOS (Connect wikt_parsed_conn,
                    LanguageType native_lang) {
        // lang_pos -> meaning -> count

        Statement   s = null;
        ResultSet   rs= null;
        long    t_start;

        // mean_histogram [0]
        // int n_empty_meaning = 0;// total number of unique noun, verb, etc.  without definitions
        
        int n_unknown_pos__in_rich_words = 0; // number of words (with relations) with unknown POS
        int n_langpos_with_empty_meaning = 0;// total number of unique noun, verb, etc. with empty definitions
        int n_nonempty_meaning = 0;// total number of words (unique noun, verb, etc.) with nonempty definitions
        int n_total = Statistics.Count(wikt_parsed_conn, "lang_pos");
        t_start = System.currentTimeMillis();

        Map<LanguageType, Map<POS,POSStat>> m_lang_pos_pos_stat = new HashMap<LanguageType, Map<POS,POSStat>>();
        //                Map<POS,POSStat> m_pos_sum_all_lang

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
                n_langpos_with_empty_meaning ++;

                TPage tpage = lang_pos_not_recursive.getPage();
                String page_title = tpage.getPageTitle();

                int n_meaning = WTMeaning.countMeanings(wikt_parsed_conn, lang_pos_not_recursive);
                
                if(n_meaning < max_meanings) {
                    mean_histogram [n_meaning] ++;
                    
                    Integer[] h;
                    if(m_lang_histogram.containsKey(lang) ) {
                        h = m_lang_histogram.get(lang);
                    } else {
                        h = new Integer[max_meanings]; 
                        for(int i=0;i<max_meanings;i++)
                            h[i] = 0;
                    }
                    h[n_meaning] ++;
                    m_lang_histogram.put(lang, h);
                }
                
                if(0 == n_meaning)
                    continue;
                n_nonempty_meaning ++;

                POS p = lang_pos_not_recursive.getPOS().getPOS();
                if(POS.unknown == p) {
                    n_unknown_pos__in_rich_words ++;
                } else {

                    {   // all languages statistics
                        POSStat ps = m_pos_sum_all_lang.get(p);
                        if(null == ps)
                            ps = new POSStat();
                                                  ps.addPOS(n_meaning, page_title);
                        m_pos_sum_all_lang.put(p, ps);
                    }

                    {   // POS statistics of this language
                        POSStat ps2 = null;
                        Map<POS,POSStat> m_pos_stat = m_lang_pos_pos_stat.get(lang);
                        if(null == m_pos_stat) {
                            m_pos_stat = new HashMap<POS,POSStat>();
                        } else {
                            ps2 = m_pos_stat.get(p);
                        }
                        if(null == ps2)
                            ps2 = new POSStat();
                                                                        ps2.addPOS(n_meaning, page_title);
                                                      m_pos_stat.put(p, ps2);
                        m_lang_pos_pos_stat.put(lang, m_pos_stat);
                    }
                }

                /* list of rich words... todo
                boolean b_added = false;
                if((native_lang == lang && n_meaning >= threshold_meanings_native) ||
                   (native_lang != lang && n_meaning >= threshold_meanings_foreign))
                {
                    b_added = true;
                    words_with_many_meanings.add(lang_pos_not_recursive);// List of the words with the maximum number of semantic relations.
                }*/
                
                if(0 == n_cur % 1000) {   // % 100
                    if(DEBUG && n_cur > 1999)
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
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (RelationTableAll.countRelationsHistogram()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        System.out.println("\nNumber of words (with meanings) with unknown POS: " + n_unknown_pos__in_rich_words);

        System.out.println("\nThe total of all unique noun, verb, etc. (+ with empty definitions): " + n_langpos_with_empty_meaning);
        System.out.println("\nNumber of empty definitions: " + mean_histogram [0]);
        System.out.println("\nNumber of words (unique noun, verb, etc.) with nonempty definitions: " + n_nonempty_meaning);

        System.out.println("\nNumber of records in the table lang_pos: " + n_total);
        return m_lang_pos_pos_stat;
    }


    public static void main(String[] args) {

        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();
        LanguageType native_lang;
        
        boolean b_english = false;

        // English
        if(b_english) {
            native_lang = LanguageType.en;
            wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, LanguageType.en);
        } else {
            // Russian
            native_lang = LanguageType.ru;
            wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);
        }
        
        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        //initLangEntries();
        
        String db_name = wikt_parsed_conn.getDBName();
        CommonPrinter.printHeader (db_name);

        System.out.println("Number of entries for each part of speech (POS).");
        System.out.println("See about Part of Speech (POS) headers:");
        System.out.println("* [http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained/POS_headers#Standard_non-POS_level_3_headers Wiktionary:Entry layout explained/POS headers]");
        System.out.println("* [http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A7%D0%B0%D1%81%D1%82%D0%B8_%D1%80%D0%B5%D1%87%D0%B8 Приложение:Части речи]");
        System.out.println("* [http://ru.wiktionary.org/wiki/%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD%D1%8B_%D1%81%D0%BB%D0%BE%D0%B2%D0%BE%D0%B8%D0%B7%D0%BC%D0%B5%D0%BD%D0%B5%D0%BD%D0%B8%D0%B9 Категория:Шаблоны словоизменений]");

        System.out.println("\n= Meanings =");
        Map<LanguageType, Map<POS,POSStat>> m_lang_pos =
                POSAndPolysemyTableAll.countPOS(wikt_parsed_conn, native_lang);
        
        int max_meanings_to_print = 70;
        POSAndPolysemyPrinter.printHistogramPerlanguage(mean_histogram, max_meanings_to_print,
                                                        m_lang_histogram);
        
        System.out.println("\n= Part of speech =");
        
        System.out.println("\n== Total (all entries) ==");
        boolean print_templates_and_short_names = true;
        POSAndPolysemyPrinter.printPOS(native_lang, m_pos_sum_all_lang, print_templates_and_short_names);

        print_templates_and_short_names = false;
        
        // English order
        if(b_english) {
            System.out.println("\n== English entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.en), print_templates_and_short_names);
            
            System.out.println("\n== Russian entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.ru), print_templates_and_short_names);

            System.out.println("\n== Finnish entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.fi), print_templates_and_short_names);

            System.out.println("\n== Ukrainian entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.uk), print_templates_and_short_names);

            System.out.println("\n== French entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.fr), print_templates_and_short_names);

            System.out.println("\n== German entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.de), print_templates_and_short_names);

            System.out.println("\n== Serbian entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.sr), print_templates_and_short_names);

            System.out.println("\n== Tatar entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.tt), print_templates_and_short_names);

            System.out.println("\n== Esperanto entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.eo), print_templates_and_short_names);
        } else {
            // Russian order
            System.out.println("\n== Russian entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.ru), print_templates_and_short_names);

            System.out.println("\n== Ukrainian entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.uk), print_templates_and_short_names);

            System.out.println("\n== English entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.en), print_templates_and_short_names);

            System.out.println("\n== French entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.fr), print_templates_and_short_names);

            System.out.println("\n== German entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.de), print_templates_and_short_names);

            System.out.println("\n== Serbian entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.sr), print_templates_and_short_names);

            System.out.println("\n== Tatar entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.tt), print_templates_and_short_names);

            System.out.println("\n== Belarusian entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.be), print_templates_and_short_names);
            
            System.out.println("\n== Esperanto entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.eo), print_templates_and_short_names);
            
            System.out.println("\n== Bashkir entries ==");
            POSAndPolysemyPrinter.printPOS(native_lang, m_lang_pos.get(LanguageType.ba), print_templates_and_short_names);
        }
        
        CommonPrinter.printFooter();

        wikt_parsed_conn.Close();
    }
}
