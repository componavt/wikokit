/*
 * Page.java
 *
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.sql_idf;

import wikipedia.util.StringUtil;

import java.util.*;
import java.sql.*;

/** Routines to work with the table page in wiki idf database.
 */
public class Page {
    
    /** page (article) identifier */
    private int      page_id;
    
    /** page title */
    private String   page_title;
    
    /** number of words in the article */
    private int      word_count;
    
    
    /** Gets page's identifier */
    public int  getPageID() { return page_id;           }
    /** Sets page's identifier */
    public void setPageID(int _page_id)
                            { page_id = _page_id;       }
    /** Gets the title of the page */
    public String getPageTitle(){ return page_title;    }
    /** Sets the title of the page  */
    private void  setPageTitle( String  _page_title)
                            { page_title = _page_title; }
    
    /** Gets a number of words in the article */
    public int  getWordCount() { return word_count;        }
    
    /** Stores to DB (updates) the number of words in the article, 
     * but the row with the page_title should be in the 'page' table already.
     */
    protected void storeWordCount(java.sql.Connection conn, int _word_count) {
        word_count = _word_count;
        update (conn, page_title, _word_count);
    }
    /** Sets the number of words in the article. */
    protected void setWordCount(int _word_count) {
        word_count = _word_count;
    }
    
    /** Selects page_id, word_count from the table page by the page_title, 
     * or inserts title and count into the table if the record is absent.
     *
     *  @param  page_title  the page's title (used for get and insert)
     *  @param  word_count  number of words in the article (used for insert)
     *  @return page_id     the page's identifier (<> 0)
     */
    public static Page getOrInsert (java.sql.Connection conn,String page_title,int word_count) {
        
        Page p = get(conn, page_title);
        if(null == p || 0 == p.getPageID()) {
            Page.insert (conn, page_title, word_count);
            p = Page.get(conn, page_title);
        }
        return p;
    }
    
    /** Selects page_id, word_count from the table page by the page_title.
     *
     *  SQL:
     *  SELECT page_id, word_count FROM page WHERE page_title='bla-bla-bla';
     *
     *  @param  page_title  the page's title
     *  @return page_id     the page's identifier, 0 if it's absent.
     */
    public static Page get (java.sql.Connection conn,String page_title) {
        Page result = null;
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = conn.createStatement ();
            
            str_sql.append("SELECT page_id,word_count FROM page WHERE page_title=\"");
            
            String safe_title = StringUtil.spaceToUnderscore(
                                StringUtil.escapeChars(page_title));
            //safe_title = connect.enc.EncodeToDB(safe_title);
            
            str_sql.append(safe_title);
            str_sql.append("\"");
            s.executeQuery (str_sql.toString());
            
            rs = s.getResultSet ();
            if (rs.next ())
            {
                result = new Page();
                result.page_title   = page_title;
                result.page_id      = rs.getInt("page_id");
                result.word_count   = rs.getInt("word_count");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikidf Page.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        
        return result;
    }
    
    
    /** Deletes the record with page_title.
     */
    public static void delete (java.sql.Connection conn,String page_title) {
        Page p = get(conn, page_title);
        if(null == p || 0 == p.page_id) {
            return;
        }
        delete(conn, p.page_id);
    }
    
    /** SQL:
     *  DELETE FROM page WHERE page_id=1;
     */
    private static void delete (java.sql.Connection conn,int page_id) {
        
        Statement   s = null;
        ResultSet   rs= null;
        int         size = 0;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = conn.createStatement ();
            
            str_sql.append("DELETE FROM page WHERE page_id=");
            str_sql.append(page_id);
            s.execute (str_sql.toString());
            
        } catch(SQLException ex) {
            System.err.println("SQLException (sql_idf Page.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
    /** Inserts page title into the table page, only if it is absent in the table.
     * 
     * SQL example:
     * INSERT INTO page (page_title,word_count) VALUES ("apple",222);
     *
     *  @param page_title       page title
     *  @param word_count       number of words in the article
     */
    public static void insert (java.sql.Connection conn,String page_title,int word_count) {
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try 
        {
            s = conn.createStatement ();
            str_sql.append("INSERT INTO page (page_title,word_count) VALUES (\"");
            
            String safe_title = StringUtil.spaceToUnderscore(
                                StringUtil.escapeChars(page_title));
            //safe_title = connect.enc.EncodeToDB(safe_title);
            
            str_sql.append(safe_title);
            str_sql.append("\",");
            str_sql.append(word_count);
            str_sql.append(")");
            //System.out.println(str_sql.toString() + "; unsafe page_title=" + page_title + "; safe_title=" + safe_title);

            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikipedia.sql_idf Page.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
    
    /** Updates the value of number of words (word_count) in the table page
     * identified by the page_title.<br><br>
     * 
     * SQL example:
     * UPDATE page SET word_count=222 WHERE page_title="apple";
     *
     *  @param page_title       the page title
     *  @param word_count       new value of number of words on the page
     */
    private static void update (java.sql.Connection conn,String page_title,int word_count) {
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try
        {
            s = conn.createStatement ();
            // UPDATE page SET word_count=222 WHERE page_title="apple";
            str_sql.append("UPDATE page SET word_count=");
            str_sql.append(word_count);
            str_sql.append(" WHERE page_title=\"");
            
            String safe_title = StringUtil.spaceToUnderscore(
                                StringUtil.escapeChars(page_title));
            //safe_title = connect.enc.EncodeToDB(safe_title);
            
            str_sql.append(safe_title);
            str_sql.append("\"");
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikipedia.sql_idf Page.java update()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
    
    /** Gets number of pages: <br>
     * SELECT COUNT(*) AS size FROM page
     */
    public static int countPages(java.sql.Connection conn) {
        Statement s = null;
        ResultSet rs= null;
        int size = 0;
        String str_sql = null;
        try {
            s = conn.createStatement ();
            str_sql = "SELECT COUNT(*) AS size FROM page";
            s.executeQuery (str_sql);
            rs = s.getResultSet ();
            if (rs.next ())
            {
                size = rs.getInt("size");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (Statistics.java CountPageForNamespace()): sql='" + str_sql + "' " + ex.getMessage());
        } finally {
            if (rs != null) {
                try { rs.close();
                } catch (SQLException sqlEx) { }
                rs = null;
                }
                if (s != null) {
                    try { s.close();
                    } catch (SQLException sqlEx) { }
                    s = null;
                }
        }
        return size;
    }
    
    /** Selects data into tp_list (title of page and number of words) 
     * from the table page by the page_id.
     *<PRE>
     *  SQL:
     *  SELECT page_id, page_title, word_count FROM page 
     *  WHERE page_id IN (732, 1707, 9912)
     *</PRE>
     *  @param lemma    lemma
     *  @return Term    object with initialized fields: term_id, doc_freq, corpus_freq
     *  @see Term.fillTerms similar function (the contrary)
     */
    public static void fillPages (java.sql.Connection conn,List<TermPage> tp_list) {
        
        if(null == tp_list || 0 == tp_list.size())
            return;
                
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer sb = new StringBuffer();
        try {
            s = conn.createStatement ();
            sb.append("SELECT page_id,page_title,word_count FROM page WHERE page_id IN (");
            
            // Prepare SQL IN(...) via tp_list[].term.term_id
            int len = tp_list.size()-1;
            for (int i=0; i<len; i++) {
                sb.append(tp_list.get(i).getPageID());
                sb.append(",");
            }
            sb.append(tp_list.get(len).getPageID()); // skip last comma
            sb.append(")");
            s.executeQuery (sb.toString());
            
            rs = s.getResultSet ();
            if (rs.next ())
            {   
                Map<Integer,Page> m_id_to_page = TermPage.createMapIdPage(tp_list);
                
                do {
                    Page p = m_id_to_page.get(rs.getInt("page_id")); 
                    p.setPageTitle( rs.getString("page_title"));
                    p.setWordCount( rs.getInt(   "word_count"));
                } while (rs.next());
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikidf Page.java fillPages()):: sql='" + sb.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
}
