/* TLangPOS.java - SQL operations with the table 'lang_pos' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.sql.Connect;

import java.sql.*;

/** An operations with the table 'lang_pos' in MySQL wiktionary_parsed database.
 *
 * @see wikt.word.WPOS
 */
public class TLangPOS {

    /** Unique identifier in the table lang_pos. */
    private int id;

    /** Title of the wiki page, word. */
    private TPage page;                 // int page_id;

    /** Language. */
    private TLang lang;                 // int lang_id

    /** Part of speech. */
    private TPOS pos;                   // int pos_id
    
    /** Etymology number. */
    //private TEtymology etimology;     // int etymology_id
    // see WPOSRu.splitToPOSSections in WPOSRuTest.java

    /** A lemma of word described at the page 'Page'. */
    private String lemma;

    //private final static TPage[] NULL_TPAGE_ARRAY = new TPage[0];

    /*public TPage(int _id,String _page_title,int _word_count,int _wiki_link_count,boolean _is_in_wiktionary) {
        id              = _id;
        page_title      = _page_title;
        word_count      = _word_count;
        wiki_link_count = _wiki_link_count;
        is_in_wiktionary = _is_in_wiktionary;
    }*/

    /** Inserts record into the table 'page'.
     *
     * INSERT INTO page (page_title,word_count,wiki_link_count,is_in_wiktionary) VALUES ("apple",1,2,TRUE);
     *
     * @param page_title   title of wiki page
     * @param word_count   size of the page in words
     * @param wiki_link_count number of wikified words at the page
     * @param is_in_wiktionary true, if the page_title exists in Wiktionary
     */
    public static void insert (Connect connect,TPage page,TLang lang,TPOS pos) {
            // todo:
            // int etymology_n,
            // String lemma) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        /*try
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
        }*/
    }

    /** Selects row from the table 'page' by the page_title.
     *
     *  SELECT id,word_count,wiki_link_count,is_in_wiktionary FROM page WHERE page_title="apple";
     *
     * @param  page_title  title of Wiktionary article
     * @return null if page_title is absent
     */
    public static TPage get (Connect connect,String page_title) {

        /*Statement   s = null;
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
        return tp;*/
                return null;
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
        /*try {
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
        }*/
    }



}
