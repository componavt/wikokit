/* TQuotYear.java - year of quotation,
 * SQL operations with the table 'quot_year' in Wiktionary parsed database.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql.quote;

import java.sql.*;
import wikipedia.sql.Connect;

/** Year of quotation and
 * operations with the table 'quot_year' in MySQL Wiktionary parsed database. */
public class TQuotYear {

    /** Inique identifier of the year(s). */
    private int id;

    /** Start date of a writing book with the quote. */
    private int from;

    /** End date of a writing book with the quote,
     * if quote contains only one date, then to = from. */
    private int to;

    public TQuotYear(int _id,int _from,int _to)
    {
        id      = _id;
        from    = _from;
        to      = _to;
    }

    public TQuotYear(int _id,int _from)
    {
        id      = _id;
        from    = _from;
        to      = _from;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets start date of a writing book with the quote. */
    public int getFrom() {
        return from;
    }

    /** Gets finish date of a writing book with the quote. */
    public int getTo() {
        return to;
    }


    /** Inserts record into the table 'quot_year'.<br><br>
     * INSERT INTO quot_year (`from`,`to`) VALUES (1956,1956);
     *
     * @param _from start date of a writing book with the quote
     * @param _to finish date of a writing book with the quote
     * @return inserted record, or null if the insertion failed
     */
    public static TQuotYear insert (Connect connect,int _from) {
        return insert(connect, _from, _from);
    }

    /** Inserts record into the table 'quot_year'.<br><br>
     * INSERT INTO quot_year (`from`,`to`) VALUES (1956,1988);
     *
     * @param _from start date of a writing book with the quote
     * @param _to finish date of a writing book with the quote
     * @return inserted record, or null if the insertion failed
     */
    public static TQuotYear insert (Connect connect,int _from,int _to) {

        if(-1 == _from || -1 == _to) // it means that there is no info about years
            return null;

        if(_from < 0 || _to < 0 || _from > _to) {
            System.out.println("Warning (TQuotYear.insert()):: invalid years: from='"+_from+"', to='"+_to+"'.");
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TQuotYear result = null;
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO quot_year (`from`,`to`) VALUES (");
            str_sql.append(_from);
            str_sql.append(",");
            str_sql.append(_to);
            str_sql.append(")");

            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            if (rs.next ())
                result = new TQuotYear(rs.getInt("id"), _from, _to);

        }catch(SQLException ex) {
            System.err.println("SQLException (TQuotYear.insert):: _from="+_from+"; _to="+_to+"; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return result;
    }

    /** Get's a record from the table 'quot_year' by a date of a book with a quote.<br><br>
     * SELECT id FROM quot_year WHERE `from`=1956 AND `to`=1956;
     *
     * @param _text name of the source
     * @return NULL if data is absent
     */
    public static TQuotYear get (Connect connect,int _from, String page_title) {
        return get(connect, _from, _from, page_title);
    }

    /** Get's a record from the table 'quot_year' by a date of a book with a quote.<br><br>
     * SELECT id FROM quot_year WHERE `from`=1956 AND `to`=1988;
     *
     * @param page_title word which are described in this article
     * @return NULL if data is absent
     */
    public static TQuotYear get (Connect connect,int _from,int _to, String page_title) {

        if(_from < 0 || _to < 0 || _from > _to) {
            System.out.println("Warning (TQuotYear.get()):: entry '" + page_title + "', invalid years: from='"+_from+"', to='"+_to+"'.");
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TQuotYear result = null;
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id FROM quot_year WHERE `from`=");
            str_sql.append(_from);
            str_sql.append(" AND `to`=");
            str_sql.append(_to);

            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                int    _id = rs.getInt("id");
                result = new TQuotYear(_id, _from, _to);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotYear.get()):: entry '" + page_title + "', years: _from="+_from+"; _to="+_to+"; sql='" + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return result;
    }

    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'quot_year'.
     *
     * @param _from start date of a writing book with the quote
     * @param _to finish date of a writing book with the quote
     * @param page_title word which are described in this article
     */
    public static TQuotYear getOrInsert (Connect connect,int _from,int _to, String page_title) {

        if(-1 == _from || -1 == _to) // it means that there is no info about years
            return null;
        
        if(_from < 0 || _to < 0 || _from > _to) {
            System.out.println("Warning (TQuotYear.getOrInsert()):: invalid years: from='"+_from+"', to='"+_to+"', for the word '" + page_title + "'.");
            return null;
        }
        
        TQuotYear y = TQuotYear.get(connect, _from, _to, page_title);
        if(null == y)
            y = TQuotYear.insert(connect, _from, _to);
        return y;
    }

    /** Deletes row from the table 'quot_year' by a value of ID.<br><br>
     * DELETE FROM quot_year WHERE id=4;
     */
    public void delete (Connect connect) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM quot_year WHERE id=");
            str_sql.append( id );
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotYear.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }


}
