/*
 * RelatedPage.java
 *
 * Copyright (c) 2005-2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikipedia.sql_idf;

import wikipedia.util.StringUtil;
        
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import java.util.Set;

/** Routines to work with the table related_page in wiki idf database.
 * This table caches related pages found by the algorithm.
 */
public class RelatedPage {
    
    /** identifier */
    //private int     rp_id;
    
    /** unique page identifier */
    private Page page;
    
    /** delimiter of titles of related pages in related_page.related_titles */
    private static final String delimiter = "|";
    
    /** titles of related pages */
    private String   related_titles;
    //private Set<String> related_titles;
    
    /** true if exists page.page_id && related_page.page_id 
     * (though page_title may be presented in page.page_title) */
    private boolean is_in_table_related_page;
    
    /** true if exists page.page_id */
    private boolean is_in_table_page;
    
    
    public void RelatedPage() {
        //rp_id = 0;
        page    = null;
        related_titles = null;
        is_in_table_page = false;
        is_in_table_related_page = false;
    }
    
    /** Gets concatenated titles of related pages */
    public String getRelatedTitlesAsString() {
        return related_titles;
    }
    
    /** Gets titles of related pages */
    public String[] getRelatedTitlesAsArray() {
        return StringUtil.split(delimiter, related_titles);
    }
    
    public boolean isInTable_RelatedPage(java.sql.Connection conn,String page_title) {
        
        getRelatedPages(conn, page_title);
        return is_in_table_related_page;
    }
    
    /** Adds/updates list of related pages.
     *
     *  @param  related_list  list of related pages, concatenated by delimiter.
     */
    public void add (java.sql.Connection conn, String page_title, String[] related_list) {
        
        add(conn, page_title, StringUtil.join(delimiter, related_list));
    }
    
    /** Adds (inserts or updates) list of related pages.
     *
     *  @param  related_list  list of related pages, concatenated by delimiter.
     */
    public void add (java.sql.Connection conn, String page_title, String related_list) {
        
        Page p = Page.getOrInsert(conn, page_title, 0);
        
        if(isInTable_RelatedPage(conn, page_title)) {
            update(conn, p.getPageID(), related_list);
        } else {
            insert(conn, p.getPageID(), related_list);
        }
    }
    
    /** Inserts list of titles of related pages.
     *
     * SQL example:
     * INSERT INTO related_page (page_id,related_titles) VALUES (1,"pear|plum");
     *
     * @param  page_title   title of main page
     * @param  related_list list of pages related to main, concatenated by delimiter
     */
    public static void insert (java.sql.Connection conn, int page_id, String related_list) {
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
    
    /** Updates list of titles of related pages.
     * SQL: UPDATE related_page SET related_titles="pear|plum|banana" WHERE page_id=1;
     */
    public static void update (java.sql.Connection conn, int page_id, String related_list) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try 
        {
            s = conn.createStatement ();
            // UPDATE related_page SET related_titles="pear|plum|banana" WHERE page_id=1;
            str_sql.append("UPDATE related_page SET related_titles=\"");
            
            String safe_title = StringUtil.spaceToUnderscore(
                                StringUtil.escapeChars(related_list));
            str_sql.append(safe_title);
            str_sql.append("\" WHERE page_id=");
            str_sql.append(page_id);
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (sql_idf RelatedPage.java update()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
    
    
    /** Deletes list of related pages.
     *
     *  @param  page_title  the page's title
     *  @return NULL if related pages absent in the table, if search algorithm 
     *          didn't find anything then result Set is empty.
     */
    public static void delete (java.sql.Connection conn,String page_title) {
        Page p = Page.get(conn, page_title);
        if(null == p || 0 == p.getPageID()) {
            return;
        }
        delete(conn, p.getPageID());
    }
    
    /** Gets list of related pages.
     *
     *  @param  page_title  the page's title
     *  @return NULL if related pages absent in the table, if search algorithm 
     *          didn't find anything then result Set is empty.
     */
    public void getRelatedPages (java.sql.Connection conn,String page_title) {
        //this.page_title = page_title;
        page = Page.get(conn, page_title);
        if(null == page || 0 == page.getPageID()) {
            is_in_table_page = false;
        } else {
            is_in_table_page = true;
            get(conn, page.getPageID()); // ???
            // todo
            // ... ???
        }
    }
    
    
    /** Selects titles of related pages from the table related_page 
     * by the page_id, fills this->rp_id, ->related_titles
     *
     *  SQL:
     *  SELECT rp_id, related_titles FROM related_page WHERE page_id=1;
     *
     *  @param  page_id  the page's identifier
     */
    private void get (java.sql.Connection conn,int page_id) {
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = conn.createStatement ();
            
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
        }
    }
    
    
    /** Deletes titles of related pages from the table related_page 
     *
     *  SQL:
     *  DELETE FROM related_page WHERE page_id=1;
     *
     *  @param  page_id  the page's identifier
     */
    private static void delete (java.sql.Connection conn,int page_id) {
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = conn.createStatement ();
            
            str_sql.append("DELETE FROM related_page WHERE page_id=");
            str_sql.append(page_id);
            s.execute (str_sql.toString());
            
        } catch(SQLException ex) {
            System.err.println("SQLException (sql_idf RelatedPage.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    
}
