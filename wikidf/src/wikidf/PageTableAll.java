/*
 * PageTableAll.java - worker with all pages in the WP table 'page'.
 *
 * Copyright (c) 2005-2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikidf;

import wikipedia.language.LanguageType;
import wikipedia.language.Encodings;
import wikipedia.sql.*;

import java.sql.*;

import gate.*;
import gate.util.*;

/** Worker with all pages in the WP table 'page'.
 */
public class PageTableAll {
    private static final boolean DEBUG = true;
    
    /** pages which caused crash of program (Russian Wikipedia) - for fast debug */
    private static final String[] debug_pages = {"Борланд,_Вес", "Atom", "BSD_DPL", "Sitemaps", 
    "WML", "XML", "XPath", "Апостроф_(диакритический_знак)", "Восход_и_заход_Солнца",
    "Знак_ударения", "Бангладеш", "Административные_единицы_Китая_до_уезда", "Аум_Синрикё"};
    
    /** Selects all pages (not categories, not redirects), stores to the IDF db.
     * SQL:
     * SELECT page_title FROM page WHERE page_namespace=0 AND page_is_redirect=0;
     *
     */
    public static void parseAllPages(Connect wp_conn,
            LanguageType wiki_lang, boolean b_remove_not_expand_iwiki,
            Connect idf_conn,Corpus corpus,StandAloneRussianPOSTagger prs,
            int doc_freq_max)
    throws GateException
    {
        Statement   s = null;
        ResultSet   rs= null;
        long    t_start;
        float   t_work;
        
        int n_total = Statistics.CountArticlesNonRedirects(wp_conn);
        System.out.println("Total pages: " + n_total);
        t_start = System.currentTimeMillis();
        
        try {
            s = wp_conn.conn.createStatement ();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT page_title FROM page WHERE page_namespace=0 AND page_is_redirect=0");
            s.executeQuery(sb.toString());      //GetTitleByIDQuery(rs, s, sb);
            rs = s.getResultSet ();
            
            int n_cur = 0;
            while (rs.next ())
            {
                Encodings e = wp_conn.enc;
                //title = Encodings.bytesTo(rs.getBytes("page_title"), e.GetDBEnc());
                String db_str = Encodings.bytesTo(rs.getBytes("page_title"), e.GetDBEnc());
                String page_title = e.EncodeFromDB(db_str);
                //title = Encodings.bytesTo(rs.getBytes("page_title"), enc.GetUser()); // ISO8859_1 UTF8
                //title = Encodings.bytesTo(rs.getBytes("page_title"), "ISO8859_1"); // 
                
                // test problem pages:
                /*if (n_cur < debug_pages.length)
                    page_title = wp_conn.enc.EncodeFromJava(debug_pages[n_cur]); //"Борланд,_Вес"
                else 
                    break;*/
                //page_title = wp_conn.enc.EncodeFromJava("MTR");    // Sanskrit
                
                if(DEBUG && 0 == ++n_cur % 100) {   // % 100
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
                Keeper.parseFromWP(
                    wp_conn, page_title, 
                    wiki_lang, b_remove_not_expand_iwiki,
                    idf_conn, corpus, prs,
                    doc_freq_max);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (parseAllPages.java PageTableAll()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        
        long  t_end;
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        System.out.println("\n\nTime sec:" + t_work + 
                "\nTotal pages: " + n_total);
    }
}
