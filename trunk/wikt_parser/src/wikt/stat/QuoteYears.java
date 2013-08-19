/* QuoteYears.java - statistics of quotes' years
 * in the database of the parsed Wiktionary.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.stat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.Statistics;
import wikokit.base.wikt.sql.*;
import wikokit.base.wikt.sql.quote.TQuotRef;
import wikokit.base.wikt.sql.quote.TQuotYear;
import wikt.stat.printer.CommonPrinter;

/** Statistics of quotes' years in the database of the parsed Wiktionary.
 */
public class QuoteYears {
    private static final boolean DEBUG = false;
    
    private static int MAX_EXAMPLE_WORDS = 3;
    
    /** SELECT `from`,`to` FROM quot_year ORDER BY `from` LIMIT 17; */
    private static int MIN_YEAR_RU = 0; // 1076; // воинъ Изборник Святослава
    
    private static int MAX_YEAR = 2013;
    
    
    /** Inner class which contains quote example,
     * the distance is stored in 'dist'.
     */
    private static class OneYearQuote {
        
        /** Example of several words, which have quotes, which were written in this year. */
        private List<String> example_words;
        
        /** Number of quotes written in this year. */
        public int counter;
        
        OneYearQuote() {
            example_words = null;
            counter = 0;
        }
        
        /** Adds one quote which was written in this year. */
        public void add(String page_table) {
            
            if(null == example_words)
                example_words = new ArrayList<String>(MAX_EXAMPLE_WORDS);
                
            if( example_words.size() < MAX_EXAMPLE_WORDS) {
                example_words.add(page_table);
            }
        }
        
        /** Gets concatenation of example_words joined by separator. */
        public String getConcatWords(String separator) {
            if(null == example_words)
                return "";
            
            StringBuilder result = new StringBuilder();
            for(String w : example_words)
                result.append(w);
            
            return result.toString();
        }
    }

    
    /** Counts number of quotes with years,...
     * by selecting all records from the table 'quote' from the database of the parsed Wiktionary.<br><br>
     * SELECT * FROM quote;
     *
     * @param connect   connection to the database of the parsed Wiktionary
     * @return map      from the language into a number of translation boxes
     *                  which contain synonyms, antonyms, etc. in English (etc.)
     */
    public static OneYearQuote[] countYears (Connect wikt_parsed_conn, int min_year, int max_year, LanguageType native_lang) {

        OneYearQuote[] years_all_lang = new OneYearQuote[max_year - min_year];
        int year;
        for (year = min_year; year < max_year; year ++)
            years_all_lang [year - min_year] = new OneYearQuote();
        
        Statement   s = null;
        ResultSet   rs= null;
        long        t_start;

        int n_unknown_lang_pos = 0; // translations into unknown languages

        int n_total = Statistics.Count(wikt_parsed_conn, "quote");
        int n_total_with_years = 0;
        int n_one_year = 0;
        int n_range = 0;
        int n_quot_years_native_lang = 0; // number of quotations with years in native language entries
        
        t_start = System.currentTimeMillis();

        try {
            s = wikt_parsed_conn.conn.createStatement ();
            StringBuilder str_sql = new StringBuilder();
            if(DEBUG)   //      SELECT id, meaning_id, lang_id, text, ref_id FROM quote LIMIT 3
                str_sql.append("SELECT id, meaning_id, lang_id, ref_id FROM quote LIMIT 7000");
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

                if(null == m) {
                    System.out.println("Warning (QuoteYears.countYears()): there is quote with id=" +id+ " with NULL meaning_id!");
                    continue;
                }

                TLangPOS lang_pos = m.getLangPOS(wikt_parsed_conn);
                if(null != lang_pos) {
                    TPage tpage = lang_pos.getPage();
                    String page_title = tpage.getPageTitle();

                    if(null != quot_ref) {
                        TQuotYear tquot_year = quot_ref.getYear();
                        if(null != tquot_year) {
                            n_total_with_years ++;
                            
                            LanguageType lang = tlang.getLanguage();
                            if(lang == native_lang)
                                n_quot_years_native_lang ++;
                            
                            int _from = tquot_year.getFrom();
                            int _to   = tquot_year.getTo();
                            
                            if(_from == _to) {
                                n_one_year ++;
                            } else {
                                n_range ++;
                            }
                            
                            //System.out.println(" _from = " + _from + "; _to = " + _to + "; min_year = " + min_year);
                            if (min_year <= _from) {
                                for (year =_from; year <_to+1; year ++) {
                                    years_all_lang [year - min_year].counter ++;
                                }
                                years_all_lang [_from - min_year].add(page_title);
                            } else {
                                System.out.println("Error: _from < min_year in page_title="+ page_title +": _from = " + _from + "; _to = " + _to + "; min_year = " + min_year);
                            }
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
            System.out.println("SQLException (QuoteTableAll.countQuotes()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        
        

        double quot_years_native_lang_percent = ((double) (Math.round(n_quot_years_native_lang * 10000f / n_total_with_years ))) / 100;

        //long  t_end;
        //float   t_work;
        //t_end  = System.currentTimeMillis();
        //t_work = (t_end - t_start)/1000f; // in sec
        System.out.println(//"\nTime sec:" + t_work +
            "\nTotal quotes: " + n_total +
            "\n\nUnique ranges of years (number of records in the table ''quot_year''): " + Statistics.Count(wikt_parsed_conn, "quot_year") +    
            "\n\nTotal quotes with years: " + n_total_with_years +
                
            "\n\nThere are "+ n_one_year +" one year quotations, e.g. 1986 year. " +
            "\n\nThere are "+ n_range +" quotations with ranges of years, e.g. 1986-1989 years. " +
                
            "\n\nThere are "+ n_quot_years_native_lang +" ("+ quot_years_native_lang_percent +" %) quotations with years for entries in native language ("+native_lang.getName()+"). " +
                    
            // "\n\nThere are quotes in " + m_lang_n.size() + " languages." +
            "\n\nUnknown<ref>'''Unknown''' - words which have quotes but have unknown language code and POS</ref>: "
                            + n_unknown_lang_pos);
        

        return years_all_lang;
    }
    
    /** Prints statistics about quote years in the Wiktionary.
     */
    private static void printQuoteYears (OneYearQuote[] years_all_lang, int min_year, int max_year)
    {
        // print header line
        System.out.println("\n=== Quote years ===");
        
        //System.out.println("\n'''Number of entries''' is a number of (Language & POS level) entries per language. E.g. the Wiktionary article \"[[:en:rook|rook]]\" contains three English and two Dutch entries of Part Of Speech level.");
        //System.out.println("\n'''Total''' is a total number of relations, i.e. synonyms + antonyms + etc...\n");

        /** Number of quotes for each source: <source name, example_words and counter). */

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.print(" ! Year || Number of quotes || Examples ");

        // print values
        for (int year = min_year; year < max_year; year ++) {
            OneYearQuote y = years_all_lang [year - min_year];
        
            System.out.print("\n|-\n| " + year +
                    " || " + y.counter + " || " );

            if(null != y.example_words) {
                StringBuilder s = new StringBuilder();
                boolean b_first = true;
                List<String> words = y.example_words;
                
                for(String w : words) {
                    if(b_first) {
                       b_first = false;
                    } else {
                       s.append(", ");
                    }
                    s.append("[[" + w + "]]");
                }
                System.out.print(s.toString());
            }   
            //System.out.print(" || ");
        }
        System.out.println("\n|}");
    }
    
    
    // TODO + skip strange dates: SKIP if (to - from) > 50 years;
    
    public static void main(String[] args) {

        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();
        int min_year = 0;

        // Russian
        LanguageType native_lang = LanguageType.ru;
        wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);
        min_year = MIN_YEAR_RU;

        // English
        //LanguageType native_lang = LanguageType.en;
        //wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, LanguageType.en);
        // min_year = MIN_YEAR_EN; todo

        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        //TRelationType.createFastMaps(wikt_parsed_conn);

        String db_name = wikt_parsed_conn.getDBName();
        System.out.println("\n== Statistics of years in quotes in the Wiktionary parsed database ==");
        CommonPrinter.printHeader (db_name);

        
        //OneYearQuote[] years_all_lang = new OneYearQuote[MAX_YEAR - min_year];
        OneYearQuote[] years_all_lang = QuoteYears.countYears(wikt_parsed_conn, min_year, MAX_YEAR, native_lang );
        wikt_parsed_conn.Close();

        System.out.println();

        /** Number of quotes for each source: <source name, example_words and counter). */
        QuoteYears.printQuoteYears(years_all_lang, min_year, MAX_YEAR);


        //System.out.println("\nThere are quotes in " + m.size() + " languages.");
        CommonPrinter.printFooter();
    }
}
