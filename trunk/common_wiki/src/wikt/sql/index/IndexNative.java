/* IndexNative.java - SQL operations with the table 'index_native' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql.index;

import wikt.sql.*;
import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;

import java.sql.*;

/** The table 'index_native' - wordlist of words in main (native) language
 * with non empty definitions (table in Wiktionary parsed database).
 */
public class IndexNative {

    /** Unique page identifier. */
    //private int page_id;

    //private String page_title;
    
    /** Wiktionary page in native language (page_title and page_id).
     * Copy of page.page_title of this Wiktionary article, see TPage.page_title
     */
    private TPage page;
    

    /** true, if there is any semantic relation in this Wiktionary article */
    private boolean has_relation;

    public IndexNative(TPage _page, boolean _has_relation)
    {
        page            = _page;
        has_relation    = _has_relation;
    }

    /** Gets unique ID (page.id) from database */
    public int getID() {
        if(null != page)
            return page.getID();
        return 0;
    }

    /** Gets title of the wiki page, word. */
    public String getPageTitle() {
        if(null != page)
            return page.getPageTitle();
        return "";
    }
    
    /** Returns true, if this Wiktionary page describes any semantic relation. */
    public boolean hasRelation() {
        return has_relation;
    }


    /** Inserts record into the table 'index_native'.<br><br>
     * INSERT INTO index_native (page_id,page_title,has_relation) VALUES (12,"water12",TRUE);
     *
     * @param page          Wiktionary page with title in native language
     * @param has_relation  true, if there is any semantic relation in this Wiktionary article
     */
    public static IndexNative insert ( Connect connect, //String page_title,
                                        TPage   page,
                                        boolean has_relation) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        IndexNative index_native = null;

        try
        {
            String page_title = page.getPageTitle();

            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO index_native (page_id,page_title,has_relation) VALUES (");
            
            str_sql.append(page.getID());
            str_sql.append(",\"");
            String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                    connect, page_title);
            str_sql.append(safe_title);
            str_sql.append("\",");
            str_sql.append(has_relation);
            str_sql.append(")");
            s.executeUpdate (str_sql.toString());
            
            index_native = new IndexNative(page, has_relation);
        }catch(SQLException ex) {
            System.err.println("SQLException (IndexNative.insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return index_native;
    }
    
    /** Selects row from the table 'index_native' by the page_title.<br><br>
     *
     *  SELECT page_id,has_relation FROM index_native WHERE page_title="apple";
     *
     * @param  page_title  title of Wiktionary article
     * @return null if page_title is absent
     */
    public static IndexNative get (Connect connect,String page_title) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        
        TPage       tp = TPage.get(connect, page_title);
        if(null == tp)
            return null;

        IndexNative _in = null;
        try {
            s = connect.conn.createStatement ();

            String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, page_title);

            str_sql.append("SELECT page_id,has_relation FROM index_native WHERE page_title=\"");
            str_sql.append(safe_title);
            str_sql.append("\"");

            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                boolean has_relation = 0 != rs.getInt("has_relation");

                _in = new IndexNative(tp, has_relation);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (IndexNative.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return _in;
    }


    /** Deletes row from the table 'index_native' by the page (e.g. by page_title).<br><br>
     *
     *  DELETE FROM index_native WHERE page_title="apple";
     *
     * @param  page Wiktionary article
     */
    public static void delete (Connect connect,TPage page) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = connect.conn.createStatement ();

            String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                    connect, page.getPageTitle());

            str_sql.append("DELETE FROM index_native WHERE page_title=\"");
            str_sql.append(safe_title);
            str_sql.append("\"");

            s.execute (str_sql.toString());

        } catch(SQLException ex) {
            System.err.println("SQLException (IndexNative.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

}
