/* TLangPOS.java - SQL operations with the table 'lang_pos' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.language.Encodings;
import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
import java.sql.*;

import java.util.List;
import java.util.ArrayList;


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
    private int etymology_id;           //private TEtymology etimology;     // int etymology_id
    // see WPOSRu.splitToPOSSections in WPOSRuTest.java

    /** A lemma of word described at the page 'Page'. */
    private String lemma;
    
    private final static TLangPOS[] NULL_TLANGPOS_ARRAY = new TLangPOS[0];

    public TLangPOS(int _id,TPage _page,TLang _lang,TPOS _pos,int _etymology_id,String _lemma) {
        id              = _id;
        page            = _page;
        lang            = _lang;
        pos             = _pos;
        etymology_id    = _etymology_id;
        lemma           = _lemma;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets page from database */
    public TPage getPage() {
        return page;
    }

    /** Inserts record into the table 'page'.
     *
     * INSERT INTO lang_pos (page_id,lang_id,pos_id,etymology_n,lemma) VALUES (1,2,3,4,"apple");
     *
     * @param TPage     ID of title of wiki page which will be added
     * @param lang      language of a word at a page
     * @param pos       part of speech of a word
     * @param etymology_n enumeration for homographs
     * @param lemma     e.g. "run" for the page_title="running"
     */
    public static void insert (Connect connect,TPage page,TLang lang,TPOS pos,
            int etymology_n,String lemma) {

        if(null == page || null == lang || null == pos) {
            System.err.println("Error (wikt_parsed TLangPOS.insert()):: null arguments, page="+page+", lang="+lang+", pos="+pos);
            return;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO lang_pos (page_id,lang_id,pos_id,etymology_n,lemma) VALUES (");
            str_sql.append(page.getID());
            str_sql.append(",");
            str_sql.append(lang.getID());
            str_sql.append(",");
            str_sql.append(pos.getID());
            str_sql.append(",");
            str_sql.append(etymology_n);

            if(null != lemma && lemma.length() > 0)
            {
                str_sql.append(",\"");
                String safe_lemma = PageTableBase.convertToSafeStringEncodeToDB(connect, lemma);
                str_sql.append(safe_lemma);
                str_sql.append("\")");
            } else
                str_sql.append(",\"\")");
            
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLangPOS.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    /** Selects rows from the table 'lang_pos' by the page_id 
     *
     * SELECT id,lang_id,pos_id,etymology_n,lemma FROM lang_pos WHERE page_id=562;
     *
     * @return empty array if data is absent
     */
    public static TLangPOS[] get (Connect connect,TPage page) {
        // Todo?
        // 
        // (lang_id?, pos_id?) : add selection by: language and POS? :
        // * @param  lang language of Wiktionary article, if lang==null then language are not used in order to filter data
        // * @param  pos part of speech of Wiktionary article, if pos==null then POS are not used in order to filter data
        //public static TLangPOS[] get (Connect connect,TPage page,TLang lang,TPOS pos) {
        //String safe_title = PageTableBase.convertToSafeStringEncodeToDB(connect, page_title);

        if(null == page) {
            System.err.println("Error (wikt_parsed TLangPOS.get()):: null argument page.");
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        List<TLangPOS> list_lp = null;

        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id,lang_id,pos_id,etymology_n,lemma FROM lang_pos WHERE page_id=");
            str_sql.append(page.getID());
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            while (rs.next ())
            {
                int     id      =                             rs.getInt("id");
                TLang   lang    = TLang.getTLangFast(connect, rs.getInt("lang_id"));
                TPOS    pos     = TPOS. getTPOSFast (connect, rs.getInt("pos_id"));
                int etymology_n =                             rs.getInt("etymology_n");
                String lemma    = Encodings.bytesToUTF8(      rs.getBytes("lemma"));

                if(null != lang && null != pos) {
                    if(null == list_lp)
                               list_lp = new ArrayList<TLangPOS>();
                    list_lp.add(new TLangPOS(id, page, lang, pos, etymology_n, lemma));
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLangPOS.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        
        if(null == list_lp)
            return NULL_TLANGPOS_ARRAY;
        return ((TLangPOS[])list_lp.toArray(NULL_TLANGPOS_ARRAY));
    }

    /** Selects row from the table 'lang_pos' by ID
     * @return empty array if data is absent
     * SELECT page_id,lang_id,pos_id,etymology_n,lemma FROM lang_pos WHERE id=8;
     */
    public static TLangPOS getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TLangPOS lang_pos = null;
        
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT page_id,lang_id,pos_id,etymology_n,lemma FROM lang_pos WHERE id=");
            str_sql.append(id);
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
            {
                TPage   page    = TPage.getByID     (connect, rs.getInt("page_id"));
                TLang   lang    = TLang.getTLangFast(connect, rs.getInt("lang_id"));
                TPOS    pos     = TPOS. getTPOSFast (connect, rs.getInt("pos_id"));
                int etymology_n =                             rs.getInt("etymology_n");
                String lemma    = Encodings.bytesToUTF8(      rs.getBytes("lemma"));
                
                if(null != lang && null != pos) {
                    lang_pos = new TLangPOS(id, page, lang, pos, etymology_n, lemma);
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLangPOS.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return lang_pos;
    }
    
    /** Deletes all rows from the table 'lang_pos', by the page_id.
     *
     * DELETE FROM lang_pos WHERE page_id=1;
     *
     * @param  id  unique ID in the table `lang_pos`
     */
    public static void delete (Connect connect,TPage page) {

        if(null == page) {
            System.err.println("Error (wikt_parsed TLangPOS.delete()):: null argument page.");
            return;
        }
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM lang_pos WHERE page_id=");
            str_sql.append(page.getID());
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLangPOS.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }



}
