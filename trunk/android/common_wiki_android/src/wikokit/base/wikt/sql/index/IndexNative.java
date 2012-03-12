/* IndexNative.java - SQL operations with the table 'index_native' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql.index;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import wikokit.base.wikt.sql.*;


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

    /** Counts number of parts of speech (or lang_pos?) in the table 'index_native'
     * (words in native language) with non-empty definitions. <br><br>
     *
     * SELECT COUNT(*) AS size from index_native;
     */
    public static int countNumberPOSWithDefinition (SQLiteDatabase db)
    {
        int size = 0;
        
        final String sql = "SELECT COUNT(*) FROM index_native";
        
        Cursor c = db.rawQuery(sql, null); // new String[] { "" + _meaning.getID() });
        if (c.moveToFirst())            
            size = c.getInt(0);

        if (c != null && !c.isClosed()) {
            c.close();
        }
        
        return size;
    }


    /** Inserts record into the table 'index_native'.<br><br>
     * INSERT INTO index_native (page_id,page_title,has_relation) VALUES (12,"water12",TRUE);
     *
     * @param page          Wiktionary page with title in native language
     * @param has_relation  true, if there is any semantic relation in this Wiktionary article
     */
    /*public static IndexNative insert ( Connect connect, //String page_title,
                                        TPage   page,
                                        boolean has_relation) {
        if(null == page) {
            System.err.println("Error (IndexNative.insert()):: null argument: page="+page);
            return null;
        }
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("INSERT INTO index_native (page_id,page_title,has_relation) VALUES (");
        str_sql.append(page.getID());
        str_sql.append(",\"");
        String page_title = page.getPageTitle();
        String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                connect, page_title);
        str_sql.append(safe_title);
        str_sql.append("\",");
        str_sql.append(has_relation);
        str_sql.append(")");
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (IndexNative.insert()):: page_title="+page_title+
                    "; sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        IndexNative index_native = null;
        index_native = new IndexNative(page, has_relation);
        return index_native;
    }*/
    
    /** Selects row from the table 'index_native' by the page_title.<br><br>
     *
     *  SELECT page_id,has_relation FROM index_native WHERE page_title="apple";
     *
     * @param  page_title  title of Wiktionary article
     * @return null if page_title is absent
     */
    public static IndexNative get (SQLiteDatabase db,String page_title) {

        TPage      tp = TPage.get(db, page_title);
        if(null == tp)
            return null;

        IndexNative _in = null;
        
        // old: SELECT page_id,has_relation FROM index_native WHERE page_title="car"
        // new: SELECT         has_relation FROM index_native WHERE page_title="car"
        Cursor c = db.query("index_native", 
                new String[] { "has_relation"}, 
                "page_title=\"" + page_title + "\"", 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            int i_has_relation = c.getColumnIndexOrThrow("has_relation");
            boolean has_relation = 0 != c.getInt(i_has_relation);
            _in = new IndexNative(tp, has_relation);
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        
        return _in;
    }


    /** Deletes row from the table 'index_native' by the page (e.g. by page_title).<br><br>
     *
     *  DELETE FROM index_native WHERE page_title="apple";
     *
     * @param  page Wiktionary article
     */
    /*public static void delete (Connect connect,TPage page) {

        StringBuilder str_sql = new StringBuilder();
        String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                    connect, page.getPageTitle());
        str_sql.append("DELETE FROM index_native WHERE page_title=\"");
        str_sql.append(safe_title);
        str_sql.append("\"");
        try {
            Statement s = connect.conn.createStatement ();
            try {
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (IndexNative.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }*/
}
