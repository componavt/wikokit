/* TTranslationEntry - SQL operations with the table 'translation_entry'
 * in Wiktionary parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.sql.Connect;
import java.sql.*;

import java.util.List;
import java.util.ArrayList;

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

    private final static TTranslationEntry[] NULL_TTRANSLATIONENTRY_ARRAY = new TTranslationEntry[0];

    public TTranslationEntry(int _id,TTranslation _translation,TLang _lang,TWikiText _wiki_text) {
        id          = _id;
        translation = _translation;
        lang        = _lang;
        wiki_text  = _wiki_text;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets wikified text from the database' table 'wiki_text'. */
    public TWikiText getWikiText() {
        return wiki_text;
    }

    /** Gets translation from the database' table 'translation'. */
    public TTranslation getTranslation() {
        return translation;
    }

    /** Inserts record into the table 'meaning'.<br><br>
     * INSERT INTO translation_entry (translation_id,lang_id,wiki_text_id) VALUES (1,2,3);
     * @param lang_pos  ID of language and POS of wiki page which will be added
     * @param meaning_id defines meaning (sense) in table 'meaning', it could be null (todo check)
     * @param meaning_summary
     * @return inserted record, or null if insertion failed
     */
    public static TTranslationEntry insert (Connect connect,TTranslation trans,
            TLang lang,TWikiText wiki_text) {

        if(null == trans || null == lang || null == wiki_text) {
            System.err.println("Error (wikt_parsed TTranslationEntry.insert()):: null arguments: trans = "+trans+
                    "; lang="+ lang +"; wiki_text=" + wiki_text);
            return null;
        }
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TTranslationEntry trans_entry = null;
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO translation_entry (translation_id,lang_id,wiki_text_id) VALUES (");
            str_sql.append(trans.getID());
            str_sql.append(",");
            str_sql.append(lang.getID());
            str_sql.append(",");
            str_sql.append(wiki_text.getID());
            str_sql.append(")");
            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            rs = s.getResultSet ();
            if (rs.next ())
                trans_entry = new TTranslationEntry(rs.getInt("id"), trans, lang, wiki_text);
            
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TTranslation.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return trans_entry;
    }

    /** Selects rows from the table 'translation_entry' by ID.<br><br>
     * SELECT translation_id,lang_id,wiki_text_id FROM translation_entry WHERE id=1;
     * @return empty array if data is absent
     */
    public static TTranslationEntry getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TTranslationEntry trans_entry = null;
        
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT translation_id,lang_id,wiki_text_id FROM translation_entry WHERE id=");
            str_sql.append(id);
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
            {   
                TTranslation trans = TTranslation.getByID(connect,  rs.getInt("translation_id"));
                TLang        lang  = TLang.getTLangFast(            rs.getInt("lang_id"));
                TWikiText    wiki_text = TWikiText.getByID(connect, rs.getInt("wiki_text_id"));

                if(null != trans && null != lang && null != wiki_text)
                    trans_entry = new TTranslationEntry(id, trans, lang, wiki_text);
                else
                    System.err.println("Error (wikt_parsed TTranslationEntry.insert()):: null arguments: trans = "+trans+
                    "; lang="+ lang +"; wiki_text=" + wiki_text);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TMeaning.java getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return trans_entry;
    }
    
    /** Selects rows from the table 'translation_entry' by translation ID and language ID.<br><br>
     * SELECT id,wiki_text_id FROM translation_entry WHERE translation_id=1 AND lang_id=2;
     * @return empty array if data is absent
     */
    public static TTranslationEntry[] getByLanguageAndTranslation (Connect connect,
                                        TTranslation trans, TLang lang) {

        if(null == trans || null == lang) {
            System.err.println("Error (TTranslationEntry.getByLanguageAndTranslation()):: null arguments, trans="+trans+", lang="+lang);
            return null;
        }
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        List<TTranslationEntry> list_trans = null;
        
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id,wiki_text_id FROM translation_entry WHERE translation_id=");
                         // SELECT id,wiki_text_id FROM translation_entry WHERE translation_id=1 AND lang_id=2;
            str_sql.append(trans.getID());
            str_sql.append(" AND lang_id=");
            str_sql.append(lang.getID());
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            while(rs.next ())
            {
                int id  = rs.getInt("id");
                TWikiText wiki_text = TWikiText.getByID(connect, rs.getInt("wiki_text_id"));

                if(null != wiki_text) {
                    if(null == list_trans)
                               list_trans = new ArrayList<TTranslationEntry>();
                    list_trans.add(new TTranslationEntry(id, trans, lang, wiki_text));
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TMeaning.java getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        if(null == list_trans)
            return NULL_TTRANSLATIONENTRY_ARRAY;
        return ((TTranslationEntry[])list_trans.toArray(NULL_TTRANSLATIONENTRY_ARRAY));
    }

    /** Selects rows from the table 'translation_entry' by wiki text ID and language ID.<br><br>
     * SELECT id,translation_id FROM translation_entry WHERE wiki_text_id=3 AND lang_id=2;
     * @return empty array, if data is absent
     */
    public static TTranslationEntry[] getByWikiTextAndLanguage (Connect connect,
                                        TWikiText wiki_text, TLang lang) {
                                        
        if(null == wiki_text || null == lang) {
            System.err.println("Error (TTranslationEntry.getByWikiTextAndLanguage()):: null arguments, wiki_text="+wiki_text+", lang="+lang);
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        List<TTranslationEntry> list_entry = null;

        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id,translation_id FROM translation_entry WHERE wiki_text_id=");
                         // SELECT id,translation_id FROM translation_entry WHERE wiki_text_id=3 AND lang_id=2;
            str_sql.append(wiki_text.getID());
            str_sql.append(" AND lang_id=");
            str_sql.append(lang.getID());
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            while (rs.next ())
            {
                TTranslation trans = TTranslation.getByID(connect, rs.getInt("translation_id"));
                if(null != trans) {
                    if(null == list_entry)
                        list_entry = new ArrayList<TTranslationEntry>();
                    list_entry.add(new TTranslationEntry(rs.getInt("id"), trans, lang, wiki_text));
                }
            }
            
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TMeaning.java getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        if(null == list_entry)
            return NULL_TTRANSLATIONENTRY_ARRAY;
        return (TTranslationEntry [])list_entry.toArray(NULL_TTRANSLATIONENTRY_ARRAY);
    }

    /** Deletes row from the table 'translation_entry' by a value of ID.<br>
     *  DELETE FROM translation_entry WHERE id=1;
     * @param  id  unique ID in the table `translation_entry`
     */
    public static void delete (Connect connect,TTranslationEntry trans_entry) {

        if(null == trans_entry) {
            System.err.println("Error (wikt_parsed TTranslationEntry.delete()):: null argument 'translation_entry'");
            return;
        }
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM translation_entry WHERE id=");
            str_sql.append(trans_entry.getID());
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TTranslationEntry.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

}
