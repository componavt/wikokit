/* TQuotPublisher.java - publisher of quotation,
 * SQL operations with the table 'quot_ref' in SQLite Android 
 * Wiktionary parsed database.
 * 
 * Copyright (c) 2011-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql.quote;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


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
    /*public static TQuotPublisher insert (Connect connect,String _text) {

        if(null == _text || 0 == _text.length()) {
            System.err.println("Error (TQuotPublisher.insert()):: null argument: .");
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
            System.err.println("SQLException (TQuotPublisher.insert):: _text='"+_text+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        }
        return result;
    }*/

    /** Get's a record from the table 'quot_publisher' by the publisher's name.<br><br>
     * SELECT id FROM quot_publisher WHERE text="Lenta.ru";
     *
     * @param _text name of the publisher
     * @return NULL if data is absent
     */
    public static TQuotPublisher get (SQLiteDatabase db, String _text) {

        TQuotPublisher result = null;
        
        if(null == _text || 0 == _text.length()) {
            System.err.println("Error (TQuotPublisher.get()):: null argument: publisher's name.");
            return null;
        }
        
        // SELECT id FROM quot_publisher WHERE text="Lenta.ru";
        Cursor c = db.query("quot_publisher", 
                new String[] { "id" }, 
                "text=\"" + _text + "\"", 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            int i_id = c.getColumnIndexOrThrow("id");            
            int _id = c.getInt(i_id);
            
            result = new TQuotPublisher(_id, _text);
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return result;
    }

    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'quot_publisher'.
     *
     * @param _publisher publisher's name
     */
    /*public static TQuotPublisher getOrInsert (Connect connect,String _publisher) {

        if(null == _publisher || 0 == _publisher.length())
            return null;

        TQuotPublisher p = TQuotPublisher.get(connect, _publisher);
        if(null == p)
            p = TQuotPublisher.insert(connect, _publisher);
        return p;
    }*/

    /** Selects row from the table 'quot_publisher' by ID.<br><br>
     *
     * SELECT text FROM quot_publisher WHERE id=1
     *
     * @return null if data is absent
     */
    public static TQuotPublisher getByID (SQLiteDatabase db,int _id) {
        
        TQuotPublisher quot_publisher = null;
        
        if(_id <= 0)
            return null;
        
        // SELECT text FROM quot_publisher WHERE id=1
        Cursor c = db.query("quot_publisher", 
                new String[] { "text" }, 
                "id=" + _id, 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            int i_text = c.getColumnIndexOrThrow("text");
            String _text = c.getString(i_text);
            
            quot_publisher = new TQuotPublisher(_id, _text);
        }
        if (c != null && !c.isClosed()) {
           c.close();
        }
        return quot_publisher;
    }

    /** Deletes row from the table 'quot_publisher' by a value of ID.<br><br>
     * DELETE FROM quot_publisher WHERE id=4;
     */
    /*public void delete (Connect connect) {

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
            System.err.println("SQLException (TQuotPublisher.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }*/
}
