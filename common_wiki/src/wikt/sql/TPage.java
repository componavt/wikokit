/* TPage.java - SQL operations with the table 'page' in Wiktionary parsed database.
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.sql.Connect;
import wikipedia.util.StringUtil;

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
        id              = 0;
        page_title      = "";
        word_count      = 0;
        wiki_link_count = 0;
        is_in_wiktionary = false;
    }
    
    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets number of words, size of the page in words. */
    public int getWordCount() {
        return word_count;
    }

    /** Gets number of out-links, size of the page as a number of wikified words. */
    public int getWikiLinkCount() {
        return wiki_link_count;
    }

    /** Returns true, if the page_title exists in Wiktionary. */
    public boolean isInWiktionary() {
        return is_in_wiktionary;
    }
    

    /** Inserts list of titles of related pages.
     *
     * INSERT INTO page (page_title,word_count,wiki_link_count,is_in_wiktionary) VALUES ("apple",1,2,TRUE);
     *
     * @param page_title   title of wiki page
     * @param word_count   size of the page in words
     * @param wiki_link_count number of wikified words at the page
     * @param is_in_wiktionary true, if the page_title exists in Wiktionary
     */
    public static void insert (Connect connect,String page_title,int word_count,int wiki_link_count,
            boolean is_in_wiktionary) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO page (page_title,word_count,wiki_link_count,is_in_wiktionary) VALUES (");
            
            String safe_title = StringUtil.spaceToUnderscore(
                                StringUtil.escapeChars(page_title));
            str_sql.append(safe_title);
            str_sql.append("\",");
            str_sql.append(word_count);
            str_sql.append(",");
            str_sql.append(wiki_link_count);
            str_sql.append(",");
            str_sql.append(is_in_wiktionary);
            str_sql.append(")");

            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPage.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    /** Selects row from the table 'page' by the page_title.
     *
     *  SELECT id,word_count,wiki_link_count,is_in_wiktionary FROM page WHERE page_title="apple";
     *
     * @param  page_title  title of Wiktionary article
     * @return null if page_title is absent
     */
    public static TPage get (Connect connect,String page_title) {

        Statement   s = null;
        ResultSet   rs= null;

        // page_title = _page_title
        /*
        try {
            s = conn.createStatement ();

        Statement s = connect.conn.createStatement ();
        page_title = PageTableBase.convertToSafeStringEncodeToDB (connect, page_title);

        StringBuffer str_sql = new StringBuffer();


            //str_sql.append("SELECT rp_id, related_titles FROM related_page WHERE page_id=");
            str_sql.append("SELECT related_titles FROM related_page WHERE page_id=");
            str_sql.append(page_id);
            s.executeQuery (str_sql.toString());

            rs = s.getResultSet ();
            if (rs.next ())
            {
                is_in_table_related_page = true;
                //rp_id  = rs.getInt("rp_id");
                related_titles = rs.getString("related_titles");
                //result.page_id = page_id;
            } else {
                is_in_table_related_page = false;
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (sql_idf RelatedPage.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }*/

        return null;
    }

    /** Deletes row from the table 'page' by the page_title.
     *
     *  DELETE FROM page WHERE page_title="apple";
     *
     * @param  page_title  title of Wiktionary article
     */
    public static void delete (java.sql.Connection conn,String page_title) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        /*try {
            s = conn.createStatement ();

            str_sql.append("DELETE FROM related_page WHERE page_id=");
            str_sql.append(page_id);
            s.execute (str_sql.toString());

        } catch(SQLException ex) {
            System.err.println("SQLException (sql_idf RelatedPage.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }*/
    }
    
}
