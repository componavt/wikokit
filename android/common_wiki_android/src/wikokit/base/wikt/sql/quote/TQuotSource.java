/* TQuotSource.java - source of quotation,
 * SQL operations with the table 'quot_source' in SQLite Android 
 * Wiktionary parsed database.
 *
 * Copyright (c) 2011-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql.quote;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    /*public static TQuotSource insert (Connect connect,String _text) {

        if(null == _text || 0 == _text.length()) {
            System.err.println("Error (TQuotSource.insert()):: null argument: .");
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
            System.err.println("SQLException (TQuotSource.insert):: _text='"+_text+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        }
        return result;
    }*/

    /** Get's a record from the table 'quot_source' by the source's name.<br><br>.
     * SELECT id FROM quot_source WHERE text="Lib";
     *
     * @param _text name of the source
     * @return NULL if data is absent
     */
    public static TQuotSource get (SQLiteDatabase db, String _text) {

        TQuotSource result = null;
        
        if(null == _text || 0 == _text.length()) {
            System.err.println("Error (TQuotSource.get()):: null argument: name of a source.");
            return null;
        }
        
        // SELECT id FROM quot_source WHERE text="Lib";
        Cursor c = db.query("quot_source", 
                new String[] { "id" }, 
                "text=\"" + _text + "\"", 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            int i_id = c.getColumnIndexOrThrow("id");            
            int _id = c.getInt(i_id);
            
            result = new TQuotSource(_id, _text);
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return result;
    }
    
    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'quot_source'.
     *
     * @param _source source of the quote
     */
    /*public static TQuotSource getOrInsert (Connect connect,String _source) {

        if(null == _source || 0 == _source.length())
            return null;

        TQuotSource s = TQuotSource.get(connect, _source);
        if(null == s)
            s = TQuotSource.insert(connect, _source);
        return s;
    }*/

    /** Selects row from the table 'quot_source' by ID.<br><br>
     * SELECT text FROM quot_source WHERE id=1
     *
     * @return null if data is absent
     */
    public static TQuotSource getByID (SQLiteDatabase db,int _id) {
        
        TQuotSource quot_source = null;
        
        if(_id <= 0)
            return null;
        
        // SELECT text FROM quot_source WHERE id=1
        Cursor c = db.query("quot_source", 
                new String[] { "text" }, 
                "id=" + _id, 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            int i_text = c.getColumnIndexOrThrow("text");
            String _text = c.getString(i_text);
            
            quot_source = new TQuotSource(_id, _text);
        }
        if (c != null && !c.isClosed()) {
           c.close();
        }
        return quot_source;
    }

    /** Deletes row from the table 'quot_source' by a value of ID.<br><br>
     * DELETE FROM quot_source WHERE id=4;
     */
    /*public void delete (Connect connect) {

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
            System.err.println("SQLException (TQuotSource.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }*/

}
