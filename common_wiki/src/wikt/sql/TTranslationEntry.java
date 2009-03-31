/* TTranslationEntry - SQL operations with the table 'translation_entry'
 * in Wiktionary parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.sql.Connect;
import java.sql.*;


/** SQL operations with the table 'translation_entry' in Wiktionary parsed database.
 *
 * @see wikt.word.WTranslationEntry
 */
public class TTranslationEntry {

    /** Unique identifier in the table 'translation_entry'. */
    private int id;

    /** Link to the table 'translation', which links to language, POS, and (may be) meaning.
     */
    private TTranslation translation;   // int translation_id;

    /** Translation into 'lang'.
     */
    private TLang lang;                 // int lang_id;

    /** Text of translation (Wikified text). */
    private TWikiText wiki_text;

    /** Inserts record into the table 'meaning'.<br><br>
     * INSERT INTO translation (lang_pos_id,meaning_summary,meaning_id) VALUES (1,'hello',3);
     * @param lang_pos  ID of language and POS of wiki page which will be added
     * @param meaning_id defines meaning (sense) in table 'meaning', it could be null (todo check)
     * @param meaning_summary
     * @return inserted record, or null if insertion failed
     */
    public static TTranslationEntry insert (Connect connect,TTranslation trans,
            TLang lang,TWikiText wiki_text) {
/*
        if(null == lang_pos) {
            System.err.println("Error (wikt_parsed TTranslationEntry.insert()):: null argument lang_pos");
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TTranslationEntry t_entry = null;
        int         wiki_text_id = 0;
        /*try
        {
            s = connect.conn.createStatement ();
            if(null != wiki_text)
                str_sql.append("INSERT INTO meaning (lang_pos_id,meaning_n,wiki_text_id) VALUES (");
            // INSERT INTO translation (lang_pos_id,meaning_summary,meaning_id) VALUES (1,'hello',3);
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
            s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            rs = s.getResultSet ();
            if (rs.next ())
                meaning = new TMeaning(rs.getInt("id"), lang_pos, lang_pos.getID(),
                                        meaning_n, wiki_text, wiki_text_id);
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TTranslation.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return trans;*/
        return null;
    }

    /** Selects rows from the table 'translation' by ID<br><br>
     * SELECT lang_pos_id,meaning_summary,meaning_id FROM translation WHERE id=1;
     * @return empty array if data is absent
     */
    public static TTranslationEntry getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TTranslationEntry t_entry = null;
/*
        try {
            s = connect.conn.createStatement ();
            // SELECT lang_pos_id,meaning_summary,meaning_id FROM translation WHERE id=1;
            str_sql.append("SELECT lang_pos_id,meaning_n,wiki_text_id FROM meaning WHERE id=");
            str_sql.append(id);
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
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
        return trans;*/
  return null;
    }

    /** Deletes row from the table 'translation' by a value of ID.<br>
     *  DELETE FROM translation WHERE id=1;
     * @param  id  unique ID in the table `meaning`
     */
    public static void delete (Connect connect,TTranslationEntry t_entry) {

        if(null == t_entry) {
            System.err.println("Error (wikt_parsed TTranslationEntry.delete()):: null argument 'translation_entry'");
            return;
        }
/*
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM translation WHERE id=");
            str_sql.append(trans.getID());
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TMeaning.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
*/    }

}
