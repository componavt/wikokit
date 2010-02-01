/* PageTableAll.java - parses all pages in the Wiktionary table 'page' (source database).
 *
 * Copyright (c) 2005-2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.parser;

import wikipedia.language.LanguageType;
import wikipedia.language.Encodings;
import wikipedia.sql.*;
import wikt.sql.TLang;

import java.sql.*;

/** Worker with all pages in the WP table 'page'.
 */
public class PageTableAll {
    private static final boolean DEBUG = true;
    
    /** pages which caused crash of program (Russian Wikipedia) - for fast debug */
    //private static final String[] debug_pages = {"-ейш-", "-лык", "-io-"};  //
    private static final String[] debug_pages = {
        "airplane", // "police", "clock", "chess",
        "адджындзинад",
        "Свирь",
        "Aare",
        "ridiculous",
        "picogray",
        "бор",
        "герб",     // &#160; in definition
        "губить",   // &#160; in semantic relations (synonym)

        "good morning", "good_morning",
        "навлечь", // Warning in WTranslationRu.parse(): The Russian word 'навлечь' has section === Перевод === but there is no any translation box "{{перев-блок|".

        "Aleksandrio", // todo - skipped (non explicit) section "Meaning"

        "centi-", "redeo", "hic",   // todo - check or get before insertion --
        "агиохронотопоним", "при-", // TWikiText.java storeToDB())::
                                    // two very long wiki_text has the same 100 first symbols. Insertion failed

        "ы", // Russian letter
        "Abessinia", "Arabian", "Asianus", "Avernus", "Guatemala", "baba", // unknown language code 'null'
        "a", // -lang-
        "FDR", // abbrev
        "барак",
        "мзда",
        "колокольчик", "car", "яблоко", "самолёт", "Flugzeug",  "airplane", // used in unit tests
        "нелетный", "улепетывание", // redirect
        "всё-равно",// soft-error redirect, template "{{wrongname|}}" = "{{misspelling of|}}"
        "маня",     // soft redirect, prints the word normal form (lemma)
        "негритянка",
        "borda", "one", "vai", // -lang-
        "шах",  // TMeaning.insert()):: null argument lang_pos
        "злато", "зограф", "кан", "кар", "карта",
        "журавль", "игнатовец", "мурашкинец", 
        "punainen", "alt", "unter", "that", "tester",
        "unser", "um", "twin", "tuus", "tu", "top", "tomo", "toki", "title",
        "tire", "telo", "taŭro", "swift", "swim", "swallow", "svedese", "suno", "sun",
        "strawberry", "strand", "spät", "spring", "some", "-тә", "tyre",
        "-iti-", "-лык", "-io-", "zwölf", "Википедия",

        // uncomment for next dump
        // "кулёма",   // skip <ref>

    };
    
    /** Selects all pages (not categories, not redirects), 
     * stores to the Wiktionary parsed DB.
     *
     * @param native_lang   native language in the Wiktionary,
     *                       e.g. Russian language in Russian Wiktionary,
     * @param n_start_from number of first Wiktionary entry to be parsed <br><br>
     * 
     * SELECT page_title FROM page WHERE page_namespace=0 AND page_is_redirect=0;
     */
    public static void parseAllPages(
            LanguageType native_lang,
            Connect wikt_conn,
            Connect wikt_parsed_conn,
            int n_start_from)
    {
        Statement   s = null;
        ResultSet   rs= null;
        long    t_start;
        float   t_work;
        
        int n_total = Statistics.CountArticlesNonRedirects(wikt_conn);
        System.out.println("Total pages: " + n_total);
        t_start = System.currentTimeMillis();

        WiktParser.clearDatabase(wikt_parsed_conn, native_lang);
        
        try {
            s = wikt_conn.conn.createStatement ();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT page_title FROM page WHERE page_namespace=0 AND page_is_redirect=0");
            s.executeQuery(sb.toString());      //GetTitleByIDQuery(rs, s, sb);
            rs = s.getResultSet ();
            
            int n_cur = 0;
            while (rs.next ())
            {
                n_cur ++;
                if(n_start_from >= 0 && n_start_from > n_cur)
                    continue;

                Encodings e = wikt_conn.enc;
                //title = Encodings.bytesTo(rs.getBytes("page_title"), e.GetDBEnc());
                String db_str = Encodings.bytesTo(rs.getBytes("page_title"), e.GetDBEnc());
                String page_title = e.EncodeFromDB(db_str);
                //title = Encodings.bytesTo(rs.getBytes("page_title"), enc.GetUser()); // ISO8859_1 UTF8
                //title = Encodings.bytesTo(rs.getBytes("page_title"), "ISO8859_1"); // 
                
                // test problem pages:
                if (n_cur < debug_pages.length)
                    page_title = wikt_conn.enc.EncodeFromJava(debug_pages[n_cur-1]);
                    //page_title = wikt_conn.enc.EncodeFromJava("one"); // будуаръ centi- всё-равно
                else 
                    break;
                //page_title = wikt_conn.enc.EncodeFromJava("MTR");    // Sanskrit
                
                if(DEBUG && 0 == n_cur % 1000) {   // % 100 1000
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

                WiktParser.parseWiktionaryEntry(native_lang, wikt_conn, wikt_parsed_conn, page_title);

if (n_cur >= 1)
  break;
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (parseAllPages.java PageTableAll()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        // post-processing
        TLang.calcIndexStatistics(wikt_parsed_conn, native_lang);
        
        long  t_end;
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        System.out.println("\n\nTime sec:" + t_work + 
                "\nTotal pages: " + n_total);
    }
}
