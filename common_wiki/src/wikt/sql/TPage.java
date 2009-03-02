/* TPage.java - SQL operations with the table 'page' in Wiktionary parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.sql.Connect;
import wikipedia.sql.PageTableBase;
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
    
    public TPage(int _id,String _page_title,int _word_count,int _wiki_link_count,boolean _is_in_wiktionary) {
        id              = _id;
        page_title      = _page_title;
        word_count      = _word_count;
        wiki_link_count = _wiki_link_count;
        is_in_wiktionary = _is_in_wiktionary;
    }

    /*public void init() {
        id              = 0;
        page_title      = "";
        word_count      = 0;
        wiki_link_count = 0;
        is_in_wiktionary = false;
    }*/
    
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
    

    /** Inserts record into the table 'page'.
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
            str_sql.append("INSERT INTO page (page_title,word_count,wiki_link_count,is_in_wiktionary) VALUES (\"");
            
            String safe_title = PageTableBase.convertToSafeStringEncodeToDB(connect, page_title);
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
        StringBuffer str_sql = new StringBuffer();
        TPage       tp = null;
        
        try {
            s = connect.conn.createStatement ();

            String safe_title = PageTableBase.convertToSafeStringEncodeToDB(connect, page_title);
                                
            str_sql.append("SELECT id,word_count,wiki_link_count,is_in_wiktionary FROM page WHERE page_title=\"");
            str_sql.append(safe_title);
            str_sql.append("\"");

            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
            {
                int id              = rs.getInt("id");
                int word_count      = rs.getInt("word_count");
                int wiki_link_count = rs.getInt("wiki_link_count");
                boolean is_in_wiktionary = rs.getBoolean("is_in_wiktionary");
                
                tp = new TPage(id, page_title, word_count, wiki_link_count, is_in_wiktionary);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPage.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return tp;
    }

    /** Deletes row from the table 'page' by the page_title.
     *
     *  DELETE FROM page WHERE page_title="apple";
     *
     * @param  page_title  title of Wiktionary article
     */
    public static void delete (Connect connect,String page_title) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = connect.conn.createStatement ();

            String safe_title = PageTableBase.convertToSafeStringEncodeToDB(connect, page_title);

            str_sql.append("DELETE FROM page WHERE page_title=\"");
            str_sql.append(safe_title);
            str_sql.append("\"");
            
            s.execute (str_sql.toString());

        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPage.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
}
