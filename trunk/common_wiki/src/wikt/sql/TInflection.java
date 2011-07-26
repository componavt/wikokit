/* TInflection.java - SQL operations with the table 'inflection' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.sql;

import wikipedia.language.Encodings;
import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
import java.sql.*;

/** An operations with the table 'inflection' in MySQL wiktionary_parsed database.
 */
public class TInflection {

    /** Unique identifier in the table 'inflection'. */
    private int id;

    /** Inflected word form. */
    private StringBuffer inflected_form;

    /** Frequency of an inflected word. */
    private int freq;

    public TInflection(int _id,String _inflected_form,int _freq) {
        id              = _id;
        inflected_form  = new StringBuffer(_inflected_form);
        freq            = _freq;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets a frequency of an inflected word. */
    public int getFreq() {
        return freq;
    }

    /** Gets an inflected word. */
    public String getInflectedForm() {
        return inflected_form.toString();
    }

    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'inflection'.
     *
     * @param text      text (without wikification).
     * @return inserted record, or null if insertion failed
     */
    public static TInflection getOrInsert (Connect connect,String inflected_form,int freq) {

        TInflection i = TInflection.get(connect, inflected_form);
        if(null == i)
            i = TInflection.insert(connect, inflected_form, freq);
        return i;
    }
    
    /** Inserts record into the table 'inflection'.<br><br>
     *
     * INSERT INTO inflection (freq,inflected_form) VALUES (1,"apple");
     * 
     * @param text      text (without wikification).
     * @return inserted record, or null if insertion failed
     */
    public static TInflection insert (Connect connect,String inflected_form,int freq) {
        
        if(inflected_form.length() == 0)
            return null;
        
        StringBuilder str_sql = new StringBuilder();
        TInflection inflexio = null;
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("INSERT INTO inflection (freq,inflected_form) VALUES (");
                str_sql.append(freq);
                str_sql.append(",\"");
                String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, inflected_form);
                str_sql.append(safe_text);
                str_sql.append("\")");
                s.executeUpdate (str_sql.toString());

                s = connect.conn.createStatement ();
                ResultSet rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
                try {
                    if (rs.next ())
                        inflexio = new TInflection(rs.getInt("id"), inflected_form, freq);
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (TInflection.insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return inflexio;
    }

    /** Selects row from the table 'inflection' by a text.<br><br>
     *  SELECT id FROM inflection WHERE inflected_form="apple";
     * @param  text  text (without wikification).
     * @return null if text is absent
     */
    public static TInflection get (Connect connect,String inflected_form) {

        if(inflected_form.length() == 0)
            return null;
        
        StringBuilder str_sql = new StringBuilder();
        TInflection inflexio = null;
        
        try {
            Statement s = connect.conn.createStatement ();
            try {
                String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, inflected_form);
                str_sql.append("SELECT id,freq FROM inflection WHERE inflected_form=\"");
                str_sql.append(safe_title);
                str_sql.append("\"");
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                        inflexio = new TInflection( rs.getInt("id"),
                                                    inflected_form,
                                                    rs.getInt("freq"));
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TInflection.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return inflexio;
    }

    /** Selects row from the table 'inflection' by ID<br><br>
     * SELECT freq,inflected_form FROM inflection WHERE id=1;
     * @return null if data is absent
     */
    public static TInflection getByID (Connect connect,int id) {

        if(id <= 0)
            return null;
        
        StringBuilder str_sql = new StringBuilder();
        TInflection inflexio = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT freq,inflected_form FROM inflection WHERE id=");
                str_sql.append(id);
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        String inflected_form = Encodings.bytesToUTF8(rs.getBytes("inflected_form"));
                        inflexio = new TInflection( id,
                                                    inflected_form,
                                                    rs.getInt("freq"));
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TInflection.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return inflexio;
    }

    /** Deletes row from the table 'inflection' by a value of ID.<br><br>
     * DELETE FROM inflection WHERE id=1;
     * @param  id  unique ID in the table `inflection`
     */
    public static void delete (Connect connect,TInflection wiki_text) {

        if(null == wiki_text) {
            System.err.println("Error (TInflection.delete()):: null argument wiki_text.");
            return;
        }
        StringBuilder str_sql = new StringBuilder();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("DELETE FROM inflection WHERE id=");
                str_sql.append(wiki_text.getID());
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TInflection.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
}
