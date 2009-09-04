/* TMeaning.java - SQL operations with the table 'meaning' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

//import wikipedia.language.Encodings;
//import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
import java.sql.*;

import java.util.List;
import java.util.ArrayList;

/** An operations with the table 'meaning' in MySQL wiktionary_parsed database.
 *
 * @see wikt.word.WPOS
 */
public class TMeaning {

    /** Unique identifier in the table 'meaning'. */
    private int id;

    
    /** Link to the table 'lang_pos', which defines language and POS.
     * If lang_pos != null, then lang_pos_id is not used; lazy DB access.
     */
    private TLangPOS lang_pos;          // int lang_pos_id;

    /** Link to the table 'lang_pos', which defines language and POS. 
     * If lang_pos != null, then lang_pos_id is not used.
     */
    private int lang_pos_id;
    

    /** Meaning (sense) number. */
    private int meaning_n;
    
    
    /** Wikified text describing this meaning.
     * If wiki_text != null, then wiki_text_id is not used; lazy DB access.
     */
    private TWikiText wiki_text;        // int wiki_text_id

    /** ID of wikified text in a table 'wiki_text.
     * If wiki_text != null, then wiki_text_id is not used; lazy DB access.
     */
    private int wiki_text_id;

    private final static TMeaning[] NULL_TMEANING_ARRAY = new TMeaning[0];

    /** Constructor.
     * @param _id
     * @param _lang_pos
     * @param _lang_pos_id
     * @param _meaning_n
     * @param _wiki_text
     * TLangPOS _lang_pos or int _lang_pos_id should be valid,
     * if lang_pos != null, then _lang_pos_id is not used.
     */
    public TMeaning(int _id,TLangPOS _lang_pos,int _lang_pos_id,
                    int _meaning_n,
                    TWikiText _wiki_text,int _wiki_text_id) {
        id          = _id;
        lang_pos    = _lang_pos;
        lang_pos_id = _lang_pos_id;
        meaning_n   = _meaning_n;
        wiki_text   = _wiki_text;
        wiki_text_id= _wiki_text_id;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets meaning (sense) number from database */
    public int getMeaningNumber() {
        return meaning_n;
    }


    /** Gets language and POS ID (for this meaning) from the database' table 'lang_pos'. */
    public TLangPOS getLangPOS(Connect connect) {

        if(null != lang_pos)
            return lang_pos;

        lang_pos = TLangPOS.getByID(connect, lang_pos_id);  // lazy DB access
        return lang_pos;
    }

    /** Gets text (without wikification) */
    public TWikiText getWikiText() {
        return wiki_text;
    }

    
    /** Inserts record into the table 'meaning'.<br><br>
     * INSERT INTO meaning (lang_pos_id,meaning_n,wiki_text_id) VALUES (1,2,3);
     * @param lang_pos  ID of language and POS of wiki page which will be added
     * @param meaning_n meaning (sense) number
     * @param wiki_text wikified text (definitions), it could be null (since an article can contain synonyms without a definition of meaning)
     * @return inserted record, or null if insertion failed
     */
    public static TMeaning insert (Connect connect,TLangPOS lang_pos,
            int meaning_n, TWikiText wiki_text) {

        if(null == lang_pos) {
            System.err.println("Error (wikt_parsed TMeaning.insert()):: null argument lang_pos");
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TMeaning meaning = null;
        int         wiki_text_id = 0;
        try
        {
            s = connect.conn.createStatement ();
            if(null != wiki_text)
                str_sql.append("INSERT INTO meaning (lang_pos_id,meaning_n,wiki_text_id) VALUES (");
            else
                str_sql.append("INSERT INTO meaning (lang_pos_id,meaning_n) VALUES (");
            str_sql.append(lang_pos.getID());
            str_sql.append(",");
            str_sql.append(meaning_n);
            if(null != wiki_text)
            {
                str_sql.append(",");
                str_sql.append(wiki_text.getID());
                wiki_text_id = wiki_text.getID();
            }
            str_sql.append(")");
            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            if (rs.next ())
                meaning = new TMeaning(rs.getInt("id"), lang_pos, lang_pos.getID(),
                                        meaning_n, wiki_text, wiki_text_id);

        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TMeaning.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return meaning;
    }

    /** Selects rows from the table 'meaning' by the lang_pos_id.
     * SELECT id,meaning_n,wiki_text_id FROM meaning WHERE lang_pos_id=1;
     * @return empty array if data is absent
     */
    public static TMeaning[] get (Connect connect,TLangPOS lang_pos) {

        if(null == lang_pos) {
            System.err.println("Error (wikt_parsed TMeaning.get()):: null argument lang_pos");
            return null;
        }
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        List<TMeaning> list_meaning = null;

        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id,meaning_n,wiki_text_id FROM meaning WHERE lang_pos_id=");
            str_sql.append(lang_pos.getID());
            //str_sql.append(" ORDER BY id");
            rs = s.executeQuery (str_sql.toString());
            while (rs.next ())
            {
                int       id            = rs.getInt("id");
                int       meaning_n     = rs.getInt("meaning_n");
                int       wiki_text_id  = rs.getInt("wiki_text_id");
                TWikiText wiki_text     = wiki_text_id < 1 ? null : TWikiText.getByID(connect, wiki_text_id);
                if(null == list_meaning)
                           list_meaning = new ArrayList<TMeaning>();
                list_meaning.add(new TMeaning(id, lang_pos, lang_pos.getID(),
                                              meaning_n, wiki_text, wiki_text_id));
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TMeaning.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        if(null == list_meaning)
            return NULL_TMEANING_ARRAY;
        return ((TMeaning[])list_meaning.toArray(NULL_TMEANING_ARRAY));
    }

    /** Selects rows from the table 'meaning' by ID<br><br>
     * SELECT lang_pos_id,meaning_n,wiki_text_id FROM meaning WHERE id=1;
     * @return empty array if data is absent
     */
    public static TMeaning getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TMeaning meaning = null;
        
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT lang_pos_id,meaning_n,wiki_text_id FROM meaning WHERE id=");
            str_sql.append(id);
            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                TLangPOS lang_pos = TLangPOS.getByID(connect,   rs.getInt("lang_pos_id"));
                int meaning_n     =                             rs.getInt("meaning_n");
                int wiki_text_id  =                             rs.getInt("wiki_text_id");
                TWikiText wiki_text = wiki_text_id < 1 ? null : TWikiText.getByID(connect, wiki_text_id);
                if(null != lang_pos) {
                    meaning = new TMeaning(id, lang_pos, lang_pos.getID(), meaning_n, wiki_text, wiki_text_id);
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TMeaning.java getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return meaning;
    }

    /** Deletes row from the table 'meaning' by a value of ID.<br>
     *  DELETE FROM meaning WHERE id=1;
     * @param  id  unique ID in the table `meaning`
     */
    public static void delete (Connect connect,TMeaning meaning) {

        if(null == meaning) {
            System.err.println("Error (wikt_parsed TMeaning.delete()):: null argument meaning");
            return;
        }
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM meaning WHERE id=");
            str_sql.append(meaning.getID());
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TMeaning.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
}
