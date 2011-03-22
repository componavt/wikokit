/* TQuotPublisher.java - publisher of quotation,
 * SQL operations with the table 'quot_ref' in Wiktionary parsed database.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql.quote;

import java.sql.*;
import wikipedia.sql.Connect;
import wikipedia.sql.PageTableBase;

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
            System.err.println("Error (TQuotPublisher.insert()):: null argument: .");
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TQuotPublisher result = null;
        String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _text);
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO quot_publisher (text) VALUES (\"");
            str_sql.append(safe_text);
            str_sql.append("\")");

            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            if (rs.next ())
                result = new TQuotPublisher(rs.getInt("id"), _text);

        }catch(SQLException ex) {
            System.err.println("SQLException (TQuotPublisher.insert):: _text='"+_text+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return result;
    }

    /** Get's a record from the table 'quot_publisher' by the source's name.<br><br>
     * SELECT id FROM quot_publisher WHERE text="Cignet Classic";
     *
     * @param _text name of the source
     * @return NULL if data is absent
     */
    public static TQuotPublisher get (Connect connect, String _text) {

        if(null == _text || 0 == _text.length()) {
            System.err.println("Error (TQuotPublisher.get()):: null argument: name of a source.");
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _text);
        TQuotPublisher result = null;

        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id FROM quot_publisher WHERE text=\"");
            str_sql.append(safe_text);
            str_sql.append("\"");

            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                int    _id = rs.getInt("id");
                result = new TQuotPublisher(_id, _text);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotPublisher.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
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

    /** Deletes row from the table 'quot_publisher' by a value of ID.<br><br>
     * DELETE FROM quot_publisher WHERE id=4;
     */
    public void delete (Connect connect) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM quot_publisher WHERE id=");
            str_sql.append( id );
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotPublisher.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
}
