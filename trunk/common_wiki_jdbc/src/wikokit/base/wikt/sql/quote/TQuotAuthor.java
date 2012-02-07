/* TQuotAuthor.java - author of quotation,
 * SQL operations with the table 'quot_author' in Wiktionary parsed database.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql.quote;

import wikokit.base.wikipedia.sql.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import wikokit.base.wikipedia.language.Encodings;
import wikokit.base.wikipedia.sql.PageTableBase;

/** Author of quotation and
 * operations with the table 'quot_author' in MySQL Wiktionary parsed database.
 *
 * Remark: quot_author table has UNIQUE compound KEY (name, wikilink),
 * and wikilink field could be NULL.
 */
public class TQuotAuthor {
    
    /** Inique identifier of the author. */
    private int id;

    /** Author's name of the quote. */
    private String name;

    /** A wikilink to author's name in Wikipedia (format: [[w:name|]]),
     * it could not be NULL, though it can be empty (""). */
    private String wikilink;

    private final static TQuotAuthor[] NULL_TQUOTAUTHOR_ARRAY = new TQuotAuthor[0];

    public TQuotAuthor(int _id,String _name,String _wikilink)
    {
        id          = _id;
        name        = _name;
        wikilink    = _wikilink;
    }
    
    public TQuotAuthor(int _id,String _name)
    {
        id          = _id;
        name        = _name;
        wikilink    = "";
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets author's name from database. */
    public String getName() {
        return name;
    }

    /** Gets wikilink to Wikipedia (format: [[w:name|]]) for the author. */
    public String getWikilink() {
        return wikilink;
    }
    
    /** Inserts record into the table 'quot_author'.<br><br>
     *
     * @param _name author's name, it is not empty or NULL
     * @param _wikilink  link to author's name in Wikipedia (format: [[w:name|]]),
     *                   _wikilink can be empty or NULL
     * @return inserted record, or null if insertion failed
     */
    public static TQuotAuthor insertName (Connect connect,String _name) {

        return insertNameWikilink(connect, _name, "");
    } 
      

    /** Inserts record into the table 'quot_author'.<br><br>
     * INSERT INTO quot_author (name,wikilink) VALUES ("Isaac Asimov", "Isaac Asimov");
     *
     * @param _name author's name, it is not empty or NULL
     * @param _wikilink  link to author's name in Wikipedia (format: [[w:name|]]),
     *                   it could be empty ("")
     * @return inserted record, or null if insertion failed
     */
    public static TQuotAuthor insertNameWikilink (Connect connect,String _name,String _wikilink) {

        if(null == _name || 0 == _name.length()) {
            System.err.println("Error (TQuotAuthor.insertNameAndWikilink()):: null argument: author's name.");
            return null;
        }
        if(null == _wikilink)
            _wikilink = "";
        StringBuilder str_sql = new StringBuilder();
        String safe_name     = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _name);
        String safe_wikilink = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _wikilink);
        str_sql.append("INSERT INTO quot_author (name,wikilink) VALUES (\"");
        str_sql.append(safe_name);
        str_sql.append("\", \"");

        str_sql.append(safe_wikilink);
        str_sql.append("\")");

        TQuotAuthor result = null;
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                s.executeUpdate (str_sql.toString());
                s = connect.conn.createStatement ();
                ResultSet rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
                try {
                    if (rs.next ())
                        result = new TQuotAuthor(rs.getInt("id"), _name, _wikilink);
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }

        }catch(SQLException ex) {
            System.err.println("SQLException (TQuotAuthor.insertName):: _name='"+_name+"'; _wikilink='"+_wikilink+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        }
        return result;
    }

    /** Get's first record from the table 'quot_author' by the author's name.<br><br>.
     * SELECT id,wikilink FROM quot_author WHERE name="Azimov" LIMIT 1;
     *
     * @param _name author's name
     * @return NULL if data is absent
     */
    public static TQuotAuthor getFirst (Connect connect,String _name) {

        if(null == _name || 0 == _name.length()) {
            System.err.println("Error (TQuotAuthor[] TQuotAuthor.get()):: null argument: author's name.");
            return null;
        }
        StringBuilder str_sql = new StringBuilder();
        String safe_name = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _name);
        str_sql.append("SELECT id,wikilink FROM quot_author WHERE name=\"");
        str_sql.append(safe_name);
        str_sql.append("\" LIMIT 1");

        TQuotAuthor result = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        int    _id = rs.getInt("id");
                        byte[] bb = rs.getBytes("wikilink");
                        String _wikilink = null == bb ? "" : Encodings.bytesToUTF8(bb);
                        result = new TQuotAuthor(_id, _name, _wikilink);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotAuthor.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result;
    }

    /** Get's array of records from the table 'quot_author' by the author's name.<br><br>.
     * SELECT id,wikilink FROM quot_author WHERE name="Azimov";
     *
     * @param _name author's name
     * @return NULL if data is absent
     */
    public static TQuotAuthor[] get (Connect connect,String _name) {

        if(null == _name || 0 == _name.length()) {
            System.err.println("Error (TQuotAuthor[] TQuotAuthor.get()):: null argument: author's name.");
            return null;
        }
        StringBuilder str_sql = new StringBuilder();
        String safe_name = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _name);
        str_sql.append("SELECT id,wikilink FROM quot_author WHERE name=\"");
        str_sql.append(safe_name);
        str_sql.append("\";");

        List<TQuotAuthor> list_authors = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    while (rs.next ())
                    {
                        if(null == list_authors)
                                   list_authors = new ArrayList<TQuotAuthor>();
                        int    _id = rs.getInt("id");
                        byte[] bb = rs.getBytes("wikilink");
                        String _wikilink = null == bb ? "" : Encodings.bytesToUTF8(bb);
                        list_authors.add(new TQuotAuthor(_id, _name, _wikilink));
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotAuthor.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        if(null == list_authors)
            return NULL_TQUOTAUTHOR_ARRAY;
        return (TQuotAuthor[])list_authors.toArray(NULL_TQUOTAUTHOR_ARRAY);
    }

    /** Get's first suitable row from the table 'quot_author' by the author's name and wikilink.<br><br>.
     * SELECT id,wikilink FROM quot_author WHERE name="Azimov" AND wikilink="" LIMIT 1;
     *
     * @param _name author's name
     * @param _wikilink author's name, it could be NULL or empty ("")
     *
     * @return NULL if data is absent
     */
    public static TQuotAuthor get (Connect connect, String _name, String _wikilink) {

        if(null == _name || 0 == _name.length()) {
            System.err.println("Error (TQuotAuthor TQuotAuthor.get()):: null argument: author's name.");
            return null;
        }
        if(null == _wikilink)
            _wikilink = "";
        StringBuilder str_sql = new StringBuilder();
        String safe_name     = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _name);
        String safe_wikilink = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _wikilink);
        str_sql.append("SELECT id FROM quot_author WHERE name=\"");
        str_sql.append(safe_name);
        str_sql.append("\" AND wikilink=\"");
        str_sql.append(safe_wikilink);
        str_sql.append("\" LIMIT 1");

        TQuotAuthor result = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                        result = new TQuotAuthor(rs.getInt("id"), _name, _wikilink);
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotAuthor.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result;
    }

    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'quot_author'.
     *
     * @param _author author's name
     * @param _author_wikilink  link to author's name in Wikipedia (format: [[w:name|]]),
     *                          it could be empty ("")
     */
    public static TQuotAuthor getOrInsert (Connect connect,String _author,String _author_wikilink) {

        if(null == _author || 0 == _author.length())
            return null;

        TQuotAuthor a = TQuotAuthor.getFirst(connect, _author);
        if(null == a)
            a = TQuotAuthor.insertNameWikilink(connect, _author, _author_wikilink);
        return a;
    }

    /** Selects row from the table 'quot_author' by ID.<br><br>
     *
     * SELECT name,wikilink FROM quot_author WHERE id=1
     *
     * @return null if data is absent
     */
    public static TQuotAuthor getByID (Connect connect,int id) {
        
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("SELECT name,wikilink FROM quot_author WHERE id=");
        str_sql.append(id);

        TQuotAuthor quot_author = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        byte[] bb = rs.getBytes("name");
                        String _name = null == bb ? null : Encodings.bytesToUTF8(bb);

                        bb = rs.getBytes("wikilink");
                        String _wikilink = null == bb ? null : Encodings.bytesToUTF8(bb);

                        quot_author = new TQuotAuthor(id, _name, _wikilink);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotAuthor.getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return quot_author;
    }

    /** Deletes row from the table 'quot_author' by a value of ID.<br><br>
     * DELETE FROM quot_author WHERE id=4;
     */
    public void delete (Connect connect) {

        StringBuilder str_sql = new StringBuilder();
        str_sql.append("DELETE FROM quot_author WHERE id=");
        str_sql.append( id );
        try {
            Statement s = connect.conn.createStatement ();
            try {
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotAuthor.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
    
}
