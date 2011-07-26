/* TQuotTranslation.java - SQL operations with the table 'quot_translation'
 * in Wiktionary parsed database.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.sql.quote;

import java.sql.*;

import wikipedia.sql.PageTableBase;
import wikipedia.language.Encodings;
import wikipedia.sql.Connect;

/** Operations with the table 'quot_translation' in MySQL Wiktionary parsed database. */
public class TQuotTranslation {

    /** Quotation unique identifier, reference to the table 'quote'. */
    private int quote_id;

    /** Quote translation, field quot_translation.text in database. */
    private String text;

    public TQuotTranslation(int _quote_id,String _text)
    {
        quote_id    = _quote_id;
        text        = _text;
    }

    /** Gets unique ID of quote from database */
    public int getID() {
        return quote_id;
    }

    /** Gets translation's text from database. */
    public String getText() {
        return text;
    }

    /** Inserts record into the table 'quot_translation'.<br><br>
     * INSERT INTO quot_translation (quote_id, text) VALUES (7, "test_apple");
     *
     * @param quote_id  quotation unique identifier, reference to the table 'quote'
     * @param text      quote translation
     * @return inserted record, or null if insertion failed
     */
    public static TQuotTranslation insert (Connect connect,int quote_id, String text) {

        if(null == text || text.length() == 0)
            return null;
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("INSERT INTO quot_translation (quote_id, text) VALUES (");
        str_sql.append(quote_id);
        str_sql.append(",\"");
        String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, text);
        str_sql.append(safe_text);
        str_sql.append("\")");
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (TQuotTranslation.insert()):: text='"+text+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        }
        return new TQuotTranslation(quote_id, text);
    }

    /** Selects row from the table 'quot_translation' by ID.<br><br>
     * SELECT text FROM quot_translation WHERE quote_id=1;
     * @return null if data is absent
     */
    public static TQuotTranslation getByID (Connect connect,int quote_id) {
        
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("SELECT text FROM quot_translation WHERE quote_id=");
        str_sql.append(quote_id);
        TQuotTranslation result = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        String text = Encodings.bytesToUTF8(rs.getBytes("text"));
                        result = new TQuotTranslation(quote_id, text);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotTranslation.getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result;
    }

    /** Deletes row from the table 'quot_translation' by a value of ID.<br><br>
     * DELETE FROM quot_translation WHERE quote_id=4;
     */
    public void delete (Connect connect) {

        StringBuilder str_sql = new StringBuilder();
        str_sql.append("DELETE FROM quot_translation WHERE quote_id=");
        str_sql.append( quote_id );
        try {
            Statement s = connect.conn.createStatement ();
            try {
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotTranslation.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
}
