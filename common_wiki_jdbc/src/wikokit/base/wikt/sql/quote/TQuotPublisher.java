/* TQuotPublisher.java - publisher of quotation,
 * SQL operations with the table 'quot_ref' in Wiktionary parsed database.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql.quote;

import java.sql.*;
import wikokit.base.wikipedia.language.Encodings;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.PageTableBase;

/** Publisher of quotation and
 * operations with the table 'quot_publisher' in MySQL Wiktionary parsed database.
 *
 * (издание in ruwikt)
 */
public class TQuotPublisher {

    /** Inique identifier of the publisher. */
    private int id;

    /** Publisher's name of the quote. */
    private String text;

    public TQuotPublisher(int _id,String _text)
    {
        id          = _id;
        text        = _text;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets publisher's name from database. */
    public String getText() {
        return text;
    }

    /** Inserts record into the table 'quot_publisher'.<br><br>
     * INSERT INTO quot_publisher (text) VALUES ("Cignet Classic");
     *
     * @param _text name of the publisher, it is not empty or NULL
     * @return inserted record, or null if insertion failed
     */
    public static TQuotPublisher insert (Connect connect,String _text) {

        if(null == _text || 0 == _text.length()) {
            System.out.println("Error (TQuotPublisher.insert()):: null argument: .");
            return null;
        }
        String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _text);
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("INSERT INTO quot_publisher (text) VALUES (\"");
        str_sql.append(safe_text);
        str_sql.append("\")");
        TQuotPublisher result = null;
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
                        result = new TQuotPublisher(rs.getInt("id"), _text);
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.out.println("SQLException (TQuotPublisher.insert):: _text='"+_text+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        }
        return result;
    }

    /** Get's a record from the table 'quot_publisher' by the publisher's name.<br><br>
     * SELECT id FROM quot_publisher WHERE text="Cignet Classic";
     *
     * @param _text name of the publisher
     * @return NULL if data is absent
     */
    public static TQuotPublisher get (Connect connect, String _text) {

        if(null == _text || 0 == _text.length()) {
            System.out.println("Error (TQuotPublisher.get()):: null argument: publisher's name.");
            return null;
        }
        String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _text);
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("SELECT id FROM quot_publisher WHERE text=\"");
        str_sql.append(safe_text);
        str_sql.append("\"");
        TQuotPublisher result = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                        result = new TQuotPublisher(rs.getInt("id"), _text);
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (TQuotPublisher.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result;
    }

    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'quot_publisher'.
     *
     * @param _publisher publisher's name
     */
    public static TQuotPublisher getOrInsert (Connect connect,String _publisher) {

        if(null == _publisher || 0 == _publisher.length())
            return null;

        TQuotPublisher p = TQuotPublisher.get(connect, _publisher);
        if(null == p)
            p = TQuotPublisher.insert(connect, _publisher);
        return p;
    }

    /** Selects row from the table 'quot_publisher' by ID.<br><br>
     *
     * SELECT text FROM quot_publisher WHERE id=1
     *
     * @return null if data is absent
     */
    public static TQuotPublisher getByID (Connect connect,int id) {
        
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("SELECT text FROM quot_publisher WHERE id=");
        str_sql.append(id);
        TQuotPublisher quot_publisher = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        byte[] bb = rs.getBytes("text");
                        String _text = null == bb ? null : Encodings.bytesToUTF8(bb);
                        quot_publisher = new TQuotPublisher(id, _text);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (TQuotPublisher.getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return quot_publisher;
    }

    /** Deletes row from the table 'quot_publisher' by a value of ID.<br><br>
     * DELETE FROM quot_publisher WHERE id=4;
     */
    public void delete (Connect connect) {

        StringBuilder str_sql = new StringBuilder();
        str_sql.append("DELETE FROM quot_publisher WHERE id=");
        str_sql.append( id );
        try {
            Statement s = connect.conn.createStatement ();
            try {
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (TQuotPublisher.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
}
