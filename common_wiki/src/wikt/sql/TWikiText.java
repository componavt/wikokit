/* TWikiText.java - SQL operations with the table 'wiki_text' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.language.Encodings;
import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
//import wikipedia.sql.UtilSQL;
//import wikipedia.sql.Statistics;
import java.sql.*;

/** An operations with the table 'wiki_text' in MySQL wiktionary_parsed database.
 */
public class TWikiText {

    /** Unique identifier in the table 'wiki_text'. */
    private int id;

    /** Text (without wikification). */
    private StringBuffer text;

    //private final static TMeaning[] NULL_TMEANING_ARRAY = new TMeaning[0];
    
    public TWikiText(int _id,String _text) {
        id              = _id;
        text            = new StringBuffer(_text);
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }


    /** Inserts record into the table 'wiki_text'.
     * INSERT INTO wiki_text (text) VALUES ("apple");
     * @param text      wiki text
     */
    public static void insert (Connect connect,String text) {

        if(text.length() == 0)
            return;

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO wiki_text (text) VALUES (\"");
            String safe_text = PageTableBase.convertToSafeStringEncodeToDB(connect, text);
            str_sql.append(safe_text);
            str_sql.append("\")");
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
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
    /*public static TLangPOS[] get (Connect connect,TPage page) {
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
    }*/

    /** Selects row from the table 'wiki_text' by ID<br>
     * SELECT id FROM wiki_text WHERE id=1;
     * @return empty array if data is absent
     */
    public static TWikiText getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TWikiText wiki_text = null;

        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id FROM wiki_text WHERE id=");
            str_sql.append(id);
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
            {
                String text = Encodings.bytesToUTF8(rs.getBytes("text"));
                wiki_text = new TWikiText(id, text);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return wiki_text;
    }

    /** Deletes row from the table 'wiki_text' by a value of ID.
     * DELETE FROM wiki_text WHERE id=1;
     * @param  id  unique ID in the table `lang_pos`
     */
    public static void delete (Connect connect,TWikiText wiki_text) {

        if(null == wiki_text) {
            System.err.println("Error (wikt_parsed TWikiText.delete()):: null argument wiki_text.");
            return;
        }
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM wiki_text WHERE id=");
            str_sql.append(wiki_text.getID());
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
}
