/* TQuotSource.java - source of quotation,
 * SQL operations with the table 'quot_source' in Wiktionary parsed database.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql.quote;

import java.sql.*;
import wikokit.base.wikipedia.language.Encodings;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.PageTableBase;

/** Source of quotation and
 * operations with the table 'quot_source' in MySQL Wiktionary parsed database.
 *
 * enwikt: quotation template,
 *
 * ruwikt: template points to corpus used as a source for the quotation ("источник").
 */
public class TQuotSource {

    /** Inique identifier of the source. */
    private int id;

    /** Source of the quote. */
    private String text;

    public TQuotSource(int _id,String _text)
    {
        id          = _id;
        text        = _text;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets name of the source from database. */
    public String getText() {
        return text;
    }

    /** Inserts record into the table 'quot_source'.<br><br>
     * INSERT INTO quot_source (text) VALUES ("Lib");
     *
     * @param _text name of the source, it is not empty or NULL
     * @return inserted record, or null if insertion failed
     */
    public static TQuotSource insert (Connect connect,String _text) {

        if(null == _text || 0 == _text.length()) {
            System.out.println("Error (TQuotSource.insert()):: null argument: .");
            return null;
        }
        StringBuilder str_sql = new StringBuilder();
        String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _text);
        str_sql.append("INSERT INTO quot_source (text) VALUES (\"");
        str_sql.append(safe_text);
        str_sql.append("\")");
        TQuotSource result = null;
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }

            s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
                try {
                    if (rs.next ())
                        result = new TQuotSource(rs.getInt("id"), _text);
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.out.println("SQLException (TQuotSource.insert):: _text='"+_text+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        }
        return result;
    }

    /** Get's a record from the table 'quot_source' by the source's name.<br><br>.
     * SELECT id FROM quot_source WHERE text="Lib";
     *
     * @param _text name of the source
     * @return NULL if data is absent
     */
    public static TQuotSource get (Connect connect, String _text) {

        if(null == _text || 0 == _text.length()) {
            System.out.println("Error (TQuotSource.get()):: null argument: name of a source.");
            return null;
        }
        StringBuilder str_sql = new StringBuilder();
        String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _text);
        str_sql.append("SELECT id FROM quot_source WHERE text=\"");
        str_sql.append(safe_text);
        str_sql.append("\"");
        TQuotSource result = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                        result = new TQuotSource(rs.getInt("id"), _text);
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (TQuotSource.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result;
    }
    
    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'quot_source'.
     *
     * @param _source source of the quote
     */
    public static TQuotSource getOrInsert (Connect connect,String _source) {

        if(null == _source || 0 == _source.length())
            return null;

        TQuotSource s = TQuotSource.get(connect, _source);
        if(null == s)
            s = TQuotSource.insert(connect, _source);
        return s;
    }

    /** Selects row from the table 'quot_source' by ID.<br><br>
     * SELECT text FROM quot_source WHERE id=1
     *
     * @return null if data is absent
     */
    public static TQuotSource getByID (Connect connect,int id) {
        
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("SELECT text FROM quot_source WHERE id=");
        str_sql.append(id);
        TQuotSource quot_source = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        byte[] bb = rs.getBytes("text");
                        String _text = null == bb ? null : Encodings.bytesToUTF8(bb);

                        quot_source = new TQuotSource(id, _text);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (TQuotSource.getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return quot_source;
    }

    /** Deletes row from the table 'quot_source' by a value of ID.<br><br>
     * DELETE FROM quot_source WHERE id=4;
     */
    public void delete (Connect connect) {

        StringBuilder str_sql = new StringBuilder();
        str_sql.append("DELETE FROM quot_source WHERE id=");
        str_sql.append( id );
        try {
            Statement s = connect.conn.createStatement ();
            try {
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (TQuotSource.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }

}
