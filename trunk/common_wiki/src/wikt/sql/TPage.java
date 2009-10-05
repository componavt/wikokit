/* TPage.java - SQL operations with the table 'page' in Wiktionary parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.sql.Connect;
import wikipedia.sql.PageTableBase;
import wikipedia.language.Encodings;
//import wikipedia.util.StringUtil;

import java.util.List;
import java.util.ArrayList;

import java.sql.*;

/** An operations with the table 'page' in MySQL wiktionary_parsed database. */
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

    /** Hard redirect defined by #REDIRECT
     * @see TLangPOS.redirect_type and .lemma - a soft redirect. */
    private boolean is_redirect;

    /** Redirected page, i.e. target or destination page.
     * It is null for usual entries.
     *
     * Hard redirect defined by #REDIRECT",
     * @see TLangPOS.redirect_type and .lemma - a soft redirect.
     */
    private String  redirect_target;

    private final static TPage[] NULL_TPAGE_ARRAY = new TPage[0];

    public TPage(int _id,String _page_title,int _word_count,int _wiki_link_count,
                 boolean _is_in_wiktionary,
                 String _redirect_target)
    {
        id              = _id;
        page_title      = _page_title;
        word_count      = _word_count;
        wiki_link_count = _wiki_link_count;
        is_in_wiktionary = _is_in_wiktionary;

        is_redirect     = null != _redirect_target;
        redirect_target = _redirect_target;
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

    /** Gets title of the wiki page, word. */
    public String getPageTitle() {
        return page_title;
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

    /** Returns true, if the page_title is a #REDIRECT in Wiktionary.
     * @see TLangPOS.redirect_type and .lemma - a soft redirect.
     */
    public boolean isRedirect() {
        return is_redirect;
    }
    
    /** Gets a redirected page, i.e. target or destination page.
     * It is null for usual entries.
     */
    public String getRedirect() {
        return redirect_target;
    }

    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'page'.<br><br>
     * 
     * @param page_title   title of wiki page
     * @param word_count   size of the page in words
     * @param wiki_link_count number of wikified words at the page
     * @param is_in_wiktionary true, if the page_title exists in Wiktionary
     * @param redirect_target redirected (target, destination) page,
     *                         it is null for usual entries
     */
    public static TPage getOrInsert (Connect connect,String _page_title,
                            int _word_count,int _wiki_link_count,
                            boolean _is_in_wiktionary,String _redirect_target) {
        
        TPage p = TPage.get(connect, _page_title);
        if(null == p)
            p = TPage.insert(connect, _page_title, _word_count, _wiki_link_count,
                            _is_in_wiktionary, _redirect_target);
        else {
            if( p.is_in_wiktionary != _is_in_wiktionary) {
                TPage.setIsInWiktionary(connect, _page_title, _is_in_wiktionary);
                p.is_in_wiktionary = _is_in_wiktionary;
            }
        }
        return p;
    }

    /** Inserts record into the table 'page'.<br><br>
     * INSERT INTO page (page_title,word_count,wiki_link_count,is_in_wiktionary) VALUES ("apple",1,2,TRUE);
     * 
     * or with redirect:
     * INSERT INTO page (page_title,word_count,wiki_link_count,is_in_wiktionary,is_redirect,redirect_target) VALUES ("apple",1,2,TRUE,TRUE,"test_neletnwi");
     * @param page_title   title of wiki page
     * @param word_count   size of the page in words
     * @param wiki_link_count number of wikified words at the page
     * @param is_in_wiktionary true, if the page_title exists in Wiktionary
     * @param redirect_target redirected (target, destination) page,
     *                         it is null for usual entries
     */
    public static TPage insert (Connect connect,String page_title,int word_count,int wiki_link_count,
            boolean is_in_wiktionary,String redirect_target) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TPage page = null;
        boolean is_redirect = null != redirect_target && redirect_target.length() > 0;
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO page (page_title,word_count,wiki_link_count,is_in_wiktionary");

            if(is_redirect)
                str_sql.append(",is_redirect,redirect_target");

            str_sql.append(") VALUES (\"");
            String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, page_title);
            str_sql.append(safe_title);
            str_sql.append("\",");
            str_sql.append(word_count);
            str_sql.append(",");
            str_sql.append(wiki_link_count);
            str_sql.append(",");
            str_sql.append(is_in_wiktionary);

            if(is_redirect) {// ,TRUE,"test_neletnwi"
                str_sql.append(",TRUE,\"");
                str_sql.append(PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect,
                               redirect_target));
                str_sql.append("\"");
            }

            str_sql.append(")");
            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            if (rs.next ())
                page = new TPage(rs.getInt("id"), page_title, word_count, wiki_link_count, 
                                 is_in_wiktionary, redirect_target);
                
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPage.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return page;
    }

    /** Update the field 'is_in_wiktionary' in the table 'page',
     * record is identiied by 'page_title'.<br><br>
     * UPDATE page SET is_in_wiktionary=1 WHERE page_title="centi-";
     *
     * @param page_title   unique title of an wiki page
     * @param is_in_wiktionary true, if the page_title exists in Wiktionary
     */
    public static void setIsInWiktionary (Connect connect,String page_title,
                                            boolean is_in_wiktionary)
    {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try
        {
            s = connect.conn.createStatement ();
            if(is_in_wiktionary)
                str_sql.append("UPDATE page SET is_in_wiktionary=1");
            else
                str_sql.append("UPDATE page SET is_in_wiktionary=0");

            str_sql.append(" WHERE page_title=\"");
            String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, page_title);
            str_sql.append(safe_title);
            str_sql.append("\"");
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPage.setIsInWiktionary()):: page_title='"+page_title+"'; sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    /** Selects row from the table 'page' by the page_title.
     *
     *  SELECT id,word_count,wiki_link_count,is_in_wiktionary,is_redirect,redirect_target FROM page WHERE page_title="apple";
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

            String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, page_title);
                                
            str_sql.append("SELECT id,word_count,wiki_link_count,is_in_wiktionary,is_redirect,redirect_target FROM page WHERE page_title=\"");
            str_sql.append(safe_title);
            str_sql.append("\"");

            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                int id              = rs.getInt("id");
                int word_count      = rs.getInt("word_count");
                int wiki_link_count = rs.getInt("wiki_link_count");
                //boolean is_in_wiktionary = rs.getBoolean("is_in_wiktionary");
                boolean is_in_wiktionary = 0 != rs.getInt("is_in_wiktionary");
                
                boolean is_redirect = 0 != rs.getInt("is_redirect");
                String redirect_target = is_redirect ? Encodings.bytesToUTF8(rs.getBytes("redirect_target")) : null;

                tp = new TPage(id, page_title, word_count, wiki_link_count,
                               is_in_wiktionary, redirect_target);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPage.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return tp;
    }
    
     /** Selects row from the table 'page' by the page ID.
      *
      * SELECT page_title,word_count,wiki_link_count,is_in_wiktionary,is_redirect,redirect_target FROM page WHERE id=1;
      *
      * @param  id  ID of Wiktionary article's title in the table 'page'
      * @return null if page_title is absent
      */
    public static TPage getByID (Connect connect,int id) {
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TPage       tp = null;
        
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT page_title,word_count,wiki_link_count,is_in_wiktionary,is_redirect,redirect_target FROM page WHERE id=");
            str_sql.append(id);
            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                String page_title   = Encodings.bytesToUTF8(rs.getBytes("page_title"));
                int word_count      = rs.getInt("word_count");
                int wiki_link_count = rs.getInt("wiki_link_count");
                boolean is_in_wiktionary = 0 != rs.getInt("is_in_wiktionary");
                boolean is_redirect = 0 != rs.getInt("is_redirect");
                String redirect_target = is_redirect ? Encodings.bytesToUTF8(rs.getBytes("redirect_target")) : null;

                tp = new TPage(id, page_title, word_count, wiki_link_count,
                               is_in_wiktionary, redirect_target);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPage.java getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return tp;
    }

    /** Selects row from the table 'page', WHERE page_title starts from 'prefix',
     * result list is constrained by 'limit'.
     *
     * skip #REDIRECT
     * SELECT id,page_title,word_count,wiki_link_count,is_in_wiktionary FROM page WHERE page_title LIKE 'zzz%' AND is_redirect is NULL LIMIT 1;
     *
     * any entries, with #REDIRECT too
     * SELECT id,page_title,word_count,wiki_link_count,is_in_wiktionary,is_redirect,redirect_target FROM page WHERE page_title LIKE 'S%' LIMIT 1;
     *
     * skip empty articles, i.e. is_in_wiktionary==FALSE
     * SELECT id,page_title,word_count,wiki_link_count,is_in_wiktionary FROM page WHERE page_title LIKE 'zzz%' AND is_in_wiktionary=1 LIMIT 1;
     *
     * @param  limit    constraint of the number of rows returned, if it's negative then a constraint is omitted
     * @param  prefix   the begining of the page_titles
     * @param  b_skip_redirects return articles without redirects if true
     * @return null if page_title is absent
     */
    public static TPage[] getByPrefix (Connect connect,String prefix,
                                        int limit, boolean b_skip_redirects)
    {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        List<TPage> tp_list = null;
        
        if(0==limit)
            return NULL_TPAGE_ARRAY;
            
        try {
            s = connect.conn.createStatement ();

            String safe_prefix = prefix;
            if(connect.isMySQL())
                safe_prefix = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, prefix);
            
            str_sql.append("SELECT id,page_title,word_count,wiki_link_count,is_in_wiktionary,is_redirect,redirect_target FROM page WHERE page_title LIKE \"");
            str_sql.append(safe_prefix);
            str_sql.append("%\"");

            if(b_skip_redirects)
                str_sql.append(" AND is_redirect is NULL");

            // temp: skip empty articles
            //str_sql.append(" AND is_in_wiktionary=1");

            if(limit > 0) {
                str_sql.append(" LIMIT ");
                str_sql.append(limit);
            }
            //System.out.print("safe_prefix=" + safe_prefix);
            
            rs = s.executeQuery (str_sql.toString());
            while (rs.next ())
            {
                if(null == tp_list)
                    tp_list = new ArrayList<TPage>();

                int id              = rs.getInt("id");
                int word_count      = rs.getInt("word_count");
                int wiki_link_count = rs.getInt("wiki_link_count");
                boolean is_in_wiktionary = rs.getBoolean("is_in_wiktionary");
                String page_title   = Encodings.bytesToUTF8(rs.getBytes("page_title"));

                boolean is_redirect = 0 != rs.getInt("is_redirect");
                String redirect_target = is_redirect ? Encodings.bytesToUTF8(rs.getBytes("redirect_target")) : null;

                if (b_skip_redirects)
                    assert(null == redirect_target);
                
                TPage tp = new TPage(id, page_title, word_count, wiki_link_count,
                               is_in_wiktionary, redirect_target);
                tp_list.add(tp);

                //System.out.println(" title=" + page_title +
                //        "; redirect_target=" + redirect_target +
                //        "; id=" + id +
                //        "; is_redirect=" + is_redirect +
                //        " (TPage.getByPrefix)");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPage.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        if(null == tp_list)
            return NULL_TPAGE_ARRAY;

        return ((TPage[])tp_list.toArray(NULL_TPAGE_ARRAY));
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

            String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, page_title);

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
