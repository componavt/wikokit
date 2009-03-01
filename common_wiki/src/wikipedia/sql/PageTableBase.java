/*
 * PageTableBase.java - SQL operations with the table page in wikipedia
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikipedia.sql;

import wikipedia.language.Encodings;
import wikipedia.util.*;

import java.sql.*;

/** The operations with the page table in MySQL wikipedia */
public class PageTableBase {
    
    public PageTableBase() {
    }
    
    private static final PageTableData page_data = new PageTableData();
    private static class PageTableData {
        public int              page_id;
        public PageNamespace    page_namespace;         //public byte page_namespace;
        public String           page_title;
        public boolean          page_is_redirect;
        
        public PageTableData () {
            init();
        }
        public void init() {
            page_id = 0;
            page_namespace = null;
            page_title = null;
            page_is_redirect = false;
        }
        public void cleanup() {
            page_namespace = null;
            page_title = null;
        }
    }

    /** Gets the article text by article title.
     * SQL:
     * SELECT old_text FROM page,text WHERE page.page_title='article_title' AND page_namespace=0 AND page.page_latest=text.old_id;
     * 
SELECT old_text FROM page,text WHERE page.page_title=CONVERT('Żagań' USING utf8) AND page_namespace=0 AND page.page_latest=text.old_id limit 1;
     +
SELECT old_text FROM page,text WHERE CONVERT(page.page_title USING utf8)='Żagań' AND page_namespace=0 AND page.page_latest=text.old_id limit 1;
     +
SELECT old_text FROM page,text WHERE page.page_title=CONVERT('Żagań' USING latin1) AND page_namespace=0 AND page.page_latest=text.old_id limit 1;
     +
     * COLLATE utf8_general_ci
SELECT old_text FROM page,text WHERE page.page_title='Żagań' AND page_namespace=0 AND page.page_latest=text.old_id COLLATE latin1_bin;

SELECT * FROM page WHERE page.page_title='Żagań';
SELECT * FROM page WHERE page.page_title='Momotarō';
     * Problem:
     * SQLException in PageTableBase.getArticleText():: sql='SELECT old_text FROM page,text 
     * WHERE page.page_title='Å»agaÅ' AND page_namespace=0 AND page.page_latest=text.old_id' 
     * Illegal mix of collations (latin1_bin,IMPLICIT) and (utf8_general_ci,COERCIBLE) for operation '='
     */
    public static String getArticleText(Connect connect, String page_title) 
    {
        String old_text = "";
        String str_sql = null;

        if(null==connect || null==connect.conn)
            return old_text;

          try {
            Statement s = connect.conn.createStatement ();
            page_title = convertToSafeStringEncodeToDB (connect, page_title);
            
            // Get text
            //page_title = Encodings.FromTo(page_title, "UTF8", "ISO8859_1");
            //page_title = Encodings.FromTo(page_title, "UTF8", "ISO8859_1");
            //page_title = Encodings.FromTo(page_title, "Cp1251","ISO8859_1");
            str_sql = "SELECT old_text FROM page,text WHERE page.page_title='"+page_title+"' AND page_namespace=0 AND page.page_latest=text.old_id";
            s.executeQuery (str_sql);

            // Get title
            //s.executeQuery ("SELECT page_title FROM page WHERE page_id = 10332");    // Out: Глаз
            //s.executeQuery ("SELECT page_title FROM page WHERE page_id = 1180710");  // Out: Gratitude

            ResultSet rs = s.getResultSet ();
            if (rs.next ())
            {
                // Get title
                //title = bytesToUTF8(rs.getBytes("page_title"));
                //page_text = "";
                
                // Get text
                // page_text = rs.getString("old_text");    // may be work - unreadable chars
                // page_text = Latin1ToUTF8(rs.getString("old_text")); //error: ??? - question marks
                old_text = Encodings.bytesToUTF8(rs.getBytes("old_text"));
            }
            rs.close ();
            s.close ();

        } catch(SQLException ex) {
            System.err.println("SQLException in PageTable.getArticleText():: sql='" + str_sql + 
                    "' " + ex.getMessage());
        }
        return old_text;
    }
    
    public static String convertToSafeStringEncodeToDB(Connect connect, String s) {
        
        String safe_title = StringUtil.spaceToUnderscore(
                            StringUtil.escapeChars(s));
        return connect.enc.EncodeToDB(safe_title);
    }
    
    private static StringBuffer sb = new StringBuffer(255);
    
    /** Gets id of articles via Title:Namespace.
     *  Namespace could be MAIN (article), CATEGORY, ... .
     *  @return -id if article is the redirect page.
     *  @return 0 if article id is absent in the table page, or interwiki.
     */
    public static int getIDByTitleNamespace(Connect connect, String page_title, PageNamespace namespace)
    {
        int     page_id = 0;

        if(null==connect || null==connect.conn)
            return page_id;

        if(StringUtil.isInterWiki(page_title)) {
            //System.out.println("isInterWiki = "+page_title);
            return 0;
        }
        
        sb.setLength(0);
        try {
            Statement s = connect.conn.createStatement ();
            String safe_title = convertToSafeStringEncodeToDB (connect, page_title);
            
            // Get ID
            //str_sql = "SELECT page_id FROM page WHERE page_namespace=" + namespace + " AND page_title='"+safe_title+"'";
            //str_sql = "SELECT page_id, page_is_redirect FROM page WHERE page_namespace=" + namespace + " AND page_title='"+safe_title+"'";
            sb.append("SELECT page_id, page_is_redirect FROM page WHERE page_namespace=");
            sb.append(namespace.toInt());
            sb.append(" AND page_title='");
            sb.append(                  safe_title);
            sb.append(                              "'");
            
            s.executeQuery (sb.toString());
            
            ResultSet rs = s.getResultSet ();
            if (rs.next ())
            {
                // Get title and redirectness
                page_id          = rs.getInt("page_id");
                
                if(1 == rs.getInt("page_is_redirect")) {
                    page_id = - page_id;
                }
            }
            rs.close ();
            s.close ();

        } catch(SQLException ex) {
            System.err.println("SQLException (PageTable.java GetIDByTitleNamespace): sql='" + sb.toString() + "' " + ex.getMessage());
        }

        return page_id;
    }
    
    /** Gets page's id by page's title (article, non-category). 
     * @return 0 if article id is absent in the table page.
     * @return -id if article is the redirect page.
     */
    public static int getIDByTitle(Connect connect, String page_title) 
    //public static int GetIDByTitle(Connect connect, byte[] page_title) 
    {
        return getIDByTitleNamespace(connect, page_title, PageNamespace.MAIN); 
    }
    
    //public static int GetCategoryIDByTitle(Connect connect, byte[] cur_title) 
    public static int getCategoryIDByTitle(Connect connect, String page_title) 
    {
        return getIDByTitleNamespace(connect, page_title, PageNamespace.CATEGORY); 
    }
    
    
    /** Gets row from the table 'page' by identifier.
     * @return ??? null if article's title is absent in the table page.
     *
     * Attention: the value of returned object PageTableData will be changed 
     * during the next function call.
     */
    public static PageTableData getPageTableDataByID(Connect connect, int id) {
        
        Statement   s = null;
        ResultSet   rs= null;
        
        // special treatment of id of redirect page
        if(id < 0) {
            id = -id;
        }

        if(null==connect || null==connect.conn)
            return null;
        
        page_data.init();
        page_data.page_id = id;
        try {
            s = connect.conn.createStatement ();
            sb.setLength(0);
            sb.append("SELECT page_namespace, page_title, page_is_redirect FROM page WHERE page_id=");
            sb.append(id);
            s.executeQuery(sb.toString());      //GetTitleByIDQuery(rs, s, sb);
            rs = s.getResultSet ();
            if (rs.next ())
            {
                Encodings e = connect.enc;
                //title = Encodings.bytesTo(rs.getBytes("page_title"), e.GetDBEnc());
                String db_str = Encodings.bytesTo(rs.getBytes("page_title"), e.GetDBEnc());
                page_data.page_title = e.EncodeFromDB(db_str);
                        
                //title = Encodings.bytesTo(rs.getBytes("page_title"), enc.GetUser()); // ISO8859_1 UTF8
                //title = Encodings.bytesTo(rs.getBytes("page_title"), "ISO8859_1"); // 
                
                page_data.page_namespace = PageNamespace.get(rs.getInt("page_namespace"));
                page_data.page_is_redirect = 1 == rs.getInt("page_is_redirect");
                
                //System.out.println("id="+id+"; title="+page_data.page_title+
                //        "; ns="+page_data.page_namespace.toInt()+
                //        "; is_redirect="+page_data.page_is_redirect);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (PageTable.java getTitleByID()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return page_data;
    }
    
    /** Gets page's or category title by identifier.
     * @return null if article's title is absent in the table page.
     */
    public static String getTitleByID(Connect connect, int id) {
        PageTableData d = getPageTableDataByID(connect, id);

        if(null == d)
            return "";

        String s = d.page_title;
        d.cleanup();
        return s;
    }
    
    /** Gets page's title by identifier, the page is not redirect.
     * @return null if article's title is absent in the table 'page' or it is 
     * a redirect page.
     */
    public static String getArticleTitleNotRedirectByID (Connect connect, int id) {
        PageTableData d = getPageTableDataByID(connect, id);

        if(null == d)
            return null;

        if(0 != d.page_id && !d.page_is_redirect &&
           d.page_namespace == PageNamespace.MAIN) {
            String s = d.page_title;
            d.cleanup();
            return s;
        } else {
            d.cleanup();
            return null;
        }
    }
    
    
    /** Gets page's title by identifier, the page is not redirect.
     * @return null if article's title is absent in the table 'page' or it is 
     * a redirect page.
     */
    public static String getCategoryTitleByID (Connect connect, int id) {
        PageTableData d = getPageTableDataByID(connect, id);
        if(null == d)
            return null;

        if(0 != d.page_id &&
                d.page_namespace == PageNamespace.CATEGORY)
        {   
            String s = d.page_title;
            d.cleanup();
            return s;
        } else {
            d.cleanup();
            return null;
        }
    }
    
    
    /*
    public static void GetTitleByIDQuery(ResultSet rs, Statement s,StringBuffer sb) {
        try {
            s.executeQuery(sb.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (PageTableBase.java GetTitleByID()): " + ex.getMessage());
        }
    }*/
    
    
    
    
    
    /** Gets type of the page. 
     * @return Returns namespace MAIN for the article, CATEGORY for the category, 
     * else null (old -1).
     */
    public static PageNamespace getNamespaceByID (Connect c, int id) {
        PageNamespace ns = null;    //byte ns = -1;
        Statement   s = null;
        ResultSet   rs= null;
        try {
            s = c.conn.createStatement ();
            s.executeQuery ("SELECT page_namespace FROM page WHERE page_id=" + id);
            rs = s.getResultSet ();
            if (rs.next ())
            {
                int i_ns = rs.getInt("page_namespace");
                if (PageNamespace.isValid(i_ns)) {
                    ns = PageNamespace.get(i_ns);
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (PageTable.java GetNamespaceByID()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return ns;
    }
    
}
