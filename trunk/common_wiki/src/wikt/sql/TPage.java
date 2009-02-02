/* TPage.java - SQL operations with the table 'page' in Wiktionary parsed database.
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikt.sql;

import java.sql.*;

/** The operations with the table 'page' in MySQL wiktionary_parsed database. */
public class TPage {

    /** Unique page identifier. */
    private int id;

    /** Title of the wiki page, word. */
    private String page_title;

    /** Size of the page in words. */
    private int word_count;
    
    /** Size of the page as a number of wikified words at the page
     * (number of out-links). */
    private int wiki_link_count;

    /** true, if the page_title exists in Wiktionary
     * false, if the page_title exists only as a [[|wikified word]] */
    private boolean is_in_wiktionary;
    
    public void TPage() {
        page_title      = "";
        word_count      = 0;
        wiki_link_count = 0;
        is_in_wiktionary = false;
    }
    
    /** Inserts list of titles of related pages.
     *
     * INSERT INTO page (page_title,word_count,wiki_link_count) VALUES ("apple",1,2);
     *
     * @param page_title   title of wiki page
     * @param word_count   size of the page in words
     * @param wiki_link_count number of wikified words at the page
     * @param is_in_wiktionary true, if the page_title exists in Wiktionary
     */
    public static void insert (java.sql.Connection conn,String page_title,int word_count,int wiki_link_count,
            boolean is_in_wiktionary) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try
        {
            s = conn.createStatement ();
            str_sql.append("INSERT INTO related_page (page_id,related_titles) VALUES (");
            str_sql.append(page_id);
            str_sql.append(",\"");

            String safe_title = StringUtil.spaceToUnderscore(
                                StringUtil.escapeChars(related_list));
            str_sql.append(safe_title);
            str_sql.append("\")");
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (sql_idf RelatedPage.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }



}
