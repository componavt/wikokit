/* TQuotRef.java - quotation reference information (year, author, etc.),
 * SQL operations with the table 'quot_ref' in SQLite Android  
 * Wiktionary parsed database.
 *
 * Copyright (c) 2011-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql.quote;

import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TWikiText;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/** Quotation reference information (year, author, etc.) and
 * operations with the table 'quot_ref' in MySQL Wiktionary parsed database. */
public class TQuotRef {

    /** Quotation reference unique identifier. */
    private int id;

    /** Title of the work. */
    private String title;

    /** A wikilink to a book in Wikipedia (format: [[w:title|]]),
     * it could not be NULL, though it can be empty (""). */
    private String wikilink;

    /** Quote date, field quot_ref.year_id in database. */
    private TQuotYear year;

    /** Quote author, field quot_ref.author_id in database. */
    private TQuotAuthor author;

    /** Quote publisher, field quot_ref.publisher_id in database. */
    private TQuotPublisher publisher;

    /** Quote source, field quot_ref.source_id in database. */
    private TQuotSource source;

    public TQuotRef(int _id, String _title, String _wikilink,
            TQuotYear _year, TQuotAuthor _author,
            TQuotPublisher _publisher, TQuotSource _source)
    {
        id          = _id;
        title       = _title;
        wikilink    = _wikilink;

        year   = _year;
        author = _author;
        publisher = _publisher;
        source = _source;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets book's title from database. */
    public String getTitle() {
        return title;
    }
    

    /** Gets wikilink to Wikipedia (format: [[w:title|]]) for the book. */
    public String getWikilink() {
        return wikilink;
    }

    /** Gets date of quote from database. */
    public TQuotYear getYear() {
        return year;
    }

    /** Gets date (Start-End) of quote from database. */
    public String getYearsRange() {
        if (null == year)
            return "";

        int _from = year.getFrom();
        int _to = year.getTo();

        if(-1 == _from && -1 == _to) // it means that there is no info about years
            return "";

        if(-1 == _to || _from == _to) // if YYYY-YYYY, then return "YYYY"
            return "" + _from;

        return "" + _from + "-" + _to;
    }

    /** Gets author(s) of quote from database. */
    public TQuotAuthor getAuthor() {
        return author;
    }

    /** Gets name of author(s) of quote from database. */
    public String getAuthorName() {
        return (null != author) ? author.getName() : "";
    }

    /** Gets publisher of quote from database. */
    public TQuotPublisher getPublisher() {
        return publisher;
    }

    /** Gets name of publisher of quote from database. */
    public String getPublisherName() {
        return (null != publisher) ? publisher.getText() : "";
    }

    /** Gets source of quote from database. */
    public TQuotSource getSource() {
        return source;
    }

    /** Gets name of author(s) of quote from database. */
    public String getSourceName() {
        return (null != source) ? source.getText() : "";
    }


    /** @return true if all input strings are NULL or empty.
    */
    private static boolean isEmptyString (String a, String b, String c,
                                            String d, String e, String f)
    {
        if (null != a && a.length() > 0) return false;
        if (null != b && b.length() > 0) return false;
        if (null != c && c.length() > 0) return false;
        if (null != d && d.length() > 0) return false;
        if (null != e && e.length() > 0) return false;
        if (null != f && f.length() > 0) return false;

        return true;
    }

    /** Gets record from the table quot_ref.<br><br>
     *
     * SELECT id FROM quot_ref WHERE year_id is NULL AND author_id=7 AND title="test" AND title_wikilink="" AND publisher_id is NULL AND source_id=4
     *
     * @param author_id ID of author's name or "NULL",
     * @param _title title of the work
     * @param _title_wikilink link to a book in Wikipedia (format: [[w:title|]]),
     *                        it could be empty ("")
     * @param publisher_id ID of publisher or "NULL"
     * @param source_id ID of source or "NULL"
     * @return inserted record, or null if insertion failed
     */
    public static TQuotRef get (SQLiteDatabase db,
                                TQuotYear y, TQuotAuthor a,
                                String _title, String _title_wikilink,
                                TQuotPublisher p, TQuotSource src)
    {
        TQuotRef result = null;
        
        String year_id = (null == y) ? " IS NULL" : "=" + y.getID();
        String author_id = (null == a) ? " IS NULL" : "=" + a.getID();
        String publisher_id = (null == p) ? " IS NULL" : "=" + p.getID();
        String source_id = (null == src) ? " IS NULL" : "=" + src.getID();

        StringBuilder str_sql_where = new StringBuilder();
        str_sql_where.append("year_id");
        str_sql_where.append(year_id);
        str_sql_where.append(" AND author_id");
        str_sql_where.append(author_id);
        str_sql_where.append(" AND title=\"");
        str_sql_where.append(_title);
        str_sql_where.append("\" AND title_wikilink=\"");
        str_sql_where.append(_title_wikilink);
        str_sql_where.append("\" AND publisher_id");
        str_sql_where.append(publisher_id);
        str_sql_where.append(" AND source_id");
        str_sql_where.append(source_id);
        
        // SELECT id FROM quot_ref WHERE year_id is NULL AND author_id=7 AND title="test" AND title_wikilink="" AND publisher_id is NULL AND source_id=4
        Cursor c = db.query("quot_ref", 
                new String[] { "id"}, 
                str_sql_where.toString(), 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            int i_id = c.getColumnIndexOrThrow("id");
            int _id = c.getInt(i_id);
            
            result = new TQuotRef(_id, _title, _title_wikilink,
                    y, // TQuotYear _year
                    a, // TQuotAuthor _author,
                    p, // TQuotPublisher _publisher,
                    src); // TQuotSource _source
            
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return result;
    }

    /** Selects row from the table 'quot_ref' by ID.<br><br>
     * SELECT year_id,author_id,title,title_wikilink,publisher_id,source_id FROM quot_ref WHERE id=1;
     *
     * @return null if data is absent
     */
    public static TQuotRef getByID (SQLiteDatabase db,int _id) {
        
        TQuotRef quot_ref = null;
        
        if(_id < 0) {
            System.err.println("Error (TQuotRef.getByID()):: ID is negative.");
            return null;
        }
        
        // SELECT year_id,author_id,title,title_wikilink,publisher_id,source_id FROM quot_ref WHERE id=1;
        Cursor c = db.query("quot_ref", 
                new String[] { "year_id", "author_id", "title", "title_wikilink", "publisher_id", "source_id"}, 
                "id=" + _id, 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            int i = c.getInt( c.getColumnIndexOrThrow("year_id") );
            TQuotYear _year = (0 == i) ? null : TQuotYear.getByID(db, i);
            
            i = c.getInt( c.getColumnIndexOrThrow("author_id") );
            TQuotAuthor _author = (0 == i) ? null : TQuotAuthor.getByID(db, i);

            String _title = c.getString( c.getColumnIndexOrThrow("title") );
            
            String _title_wikilink = c.getString( c.getColumnIndexOrThrow("title_wikilink") );
            
            i = c.getInt( c.getColumnIndexOrThrow("publisher_id") );
            TQuotPublisher _publisher = (0 == i) ? null : TQuotPublisher.getByID(db, i);
            
            i = c.getInt( c.getColumnIndexOrThrow("source_id") );
            TQuotSource _source = (0 == i) ? null : TQuotSource.getByID(db, i);

            quot_ref = new TQuotRef(_id, _title, _title_wikilink,
                                  _year,        // TQuotYear
                                  _author,      // TQuotAuthor
                                  _publisher,   // TQuotPublisher
                                  _source);     // TQuotSource
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return quot_ref;
    }

    /** Inserts (without years) records into the tables: quot_ref, quot_year, quot_author,
     * quot_publisher, and quot_source.<br><br>
     *
     * INSERT INTO quot_ref (author_id,title,title_wikilink,publisher_id,source_id) VALUES (1,"","",NULL,NULL);
     *
     * @param author_id ID of author's name or "NULL",
     * @param _title title of the work
     * @param _title_wikilink link to a book in Wikipedia (format: [[w:title|]]),
     *                        it could be empty ("")
     * @param publisher_id ID of publisher or "NULL"
     * @param source_id ID of source or "NULL"
     * @return inserted record, or null if insertion failed
     */
    /*private static TQuotRef insertByID (Connect connect,
                                TQuotYear y, TQuotAuthor a,
                                String _title, String _title_wikilink,
                                TQuotPublisher p, TQuotSource src)
    {
        String year_id = (null == y) ? "NULL" : "" + y.getID();
        String author_id = (null == a) ? "NULL" : "" + a.getID();
        String publisher_id = (null == p) ? "NULL" : "" + p.getID();
        String source_id = (null == src) ? "NULL" : "" + src.getID();
        
        String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _title);
        String safe_title_wikilink = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _title_wikilink);
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("INSERT INTO quot_ref (year_id,author_id,title,title_wikilink,publisher_id,source_id) VALUES (");
        str_sql.append(year_id);
        str_sql.append(",");
        str_sql.append(author_id);
        str_sql.append(",\"");
        str_sql.append(safe_title);
        str_sql.append("\",\"");
        str_sql.append(safe_title_wikilink);
        str_sql.append("\",");
        str_sql.append(publisher_id);
        str_sql.append(",");
        str_sql.append(source_id);
        str_sql.append(")");
        TQuotRef result = null;
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
                        result = new TQuotRef(rs.getInt("id"), _title, _title_wikilink,
                                              y, // TQuotYear _year
                                              a, // TQuotAuthor _author,
                                              p, // TQuotPublisher _publisher,
                                              src); // TQuotSource _source
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (TQuotRef.insert):: _author='"+a.getName()+"'; _title='"+_title+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        }
        return result;
    }*/

    /** Inserts (without years) records into the tables: quot_ref, quot_year, quot_author,
     * quot_publisher, and quot_source.<br><br>
     *
     * INSERT INTO quot_ref (author_id,title,title_wikilink,publisher_id,source_id) VALUES (1,"","",NULL,NULL);
     *
     * @param _author author's name,
     * @param _author_wikilink link to author's name in Wikipedia (format: [[w:name|]]),
     * @param _title title of the work
     * @param _title_wikilink link to a book in Wikipedia (format: [[w:title|]]),
     *                        it could be empty ("")
     * @param _publisher quote book publisher
     * @param _source quote source
     * @return inserted record, or null if insertion failed
     */
    /*public static TQuotRef insert (Connect connect,String _author,String _author_wikilink,
                                String _title, String _title_wikilink,
                                String _publisher, String _source)
    {
        if(isEmptyString(_author, _author_wikilink, _title, _title_wikilink, _publisher, _source)) {
            System.err.println("Error (TQuotRef.insert()):: all arguments are empty.");
            return null;
        }

        TQuotAuthor a = TQuotAuthor.getOrInsert(connect, _author, _author_wikilink);
        TQuotPublisher p = TQuotPublisher.getOrInsert(connect, _publisher);
        TQuotSource src = TQuotSource.getOrInsert(connect, _source);

        TQuotYear y = null;
        return insertByID (connect, y, a,
                                _title, _title_wikilink,
                                p, src);
    }*/

    /** Inserts (with years) records into the tables: quot_ref, quot_year, quot_author,
     * quot_publisher, and quot_source.<br><br>
     *
     * INSERT INTO quot_ref (year_id,author_id,title,title_wikilink,publisher_id,source_id) VALUES (9,1,"","",NULL,NULL);
     *
     * @param page_title word which are described in this article
     *
     * @param _author author's name,
     * @param _author_wikilink link to author's name in Wikipedia (format: [[w:name|]]),
     * @param _title title of the work
     * @param _title_wikilink link to a book in Wikipedia (format: [[w:title|]]),
     *                        it could be empty ("")
     * @param _publisher quote book publisher
     * @param _source quote source
     * @param _from start date of a writing book with the quote
     * @param _to finish date of a writing book with the quote
     * @return inserted record, or null if insertion failed
     */
    /*public static TQuotRef insertWithYears (Connect connect,String page_title,
                                String _author,String _author_wikilink,
                                String _title, String _title_wikilink,
                                String _publisher, String _source,
                                int _from,int _to)
    {
        if(isEmptyString(_author, _author_wikilink, _title, _title_wikilink, _publisher, _source)) {
            System.err.println("Error (TQuotRef.insertWithYears()):: all arguments are empty.");
            return null;
        }

        TQuotYear y = TQuotYear.getOrInsert(connect, _from, _to, page_title);
        TQuotAuthor a = TQuotAuthor.getOrInsert(connect, _author, _author_wikilink);
        TQuotPublisher p = TQuotPublisher.getOrInsert(connect, _publisher);
        TQuotSource src = TQuotSource.getOrInsert(connect, _source);

        return insertByID (connect, y, a,
                                _title, _title_wikilink,
                                p, src);
    }*/

    /** Gets ID of a record or inserts (without years) records (if they are absent)
     * into the tables: quot_ref, quot_year, quot_author, quot_publisher,
     * and quot_source.
     *
     * @param _author author's name,
     * @param _author_wikilink link to author's name in Wikipedia (format: [[w:name|]]),
     * @param _title title of the work
     * @param _title_wikilink link to a book in Wikipedia (format: [[w:title|]]),
     *                        it could be empty ("")
     * @param _publisher quote book publisher
     * @param _source quote source
     * @return found or inserted record, or null.
     */
    /*public static TQuotRef getOrInsert (Connect connect,String _author,String _author_wikilink,
                                String _title, String _title_wikilink,
                                String _publisher, String _source)
    {
        if(isEmptyString(_author, _author_wikilink, _title, _title_wikilink, _publisher, _source)) {
            // System.err.println("Error (TQuotRef.getOrInsert()):: all arguments are empty.");
            return null;
        }

        TQuotAuthor a = TQuotAuthor.getOrInsert(connect, _author, _author_wikilink);
        TQuotPublisher p = TQuotPublisher.getOrInsert(connect, _publisher);
        TQuotSource src = TQuotSource.getOrInsert(connect, _source);
        TQuotYear y = null;

        TQuotRef quot_ref = TQuotRef.get(connect, y, a,
                                _title, _title_wikilink,
                                p, src);
        if(null == quot_ref)
            quot_ref = insertByID (connect, y, a,
                                _title, _title_wikilink,
                                p, src);
        return quot_ref;
    }*/

    /*public static TQuotRef getOrInsertWithYears (Connect connect,String page_title,
                                String _author,String _author_wikilink,
                                String _title, String _title_wikilink,
                                String _publisher, String _source,
                                int _from,int _to)
    {
        if(isEmptyString(_author, _author_wikilink, _title, _title_wikilink, _publisher, _source)) {
            // System.err.println("Error (TQuotRef.getOrInsertWithYears()):: all arguments are empty.");
            return null;
        }

        TQuotAuthor a = TQuotAuthor.getOrInsert(connect, _author, _author_wikilink);
        TQuotPublisher p = TQuotPublisher.getOrInsert(connect, _publisher);
        TQuotSource src = TQuotSource.getOrInsert(connect, _source);
        TQuotYear y = TQuotYear.getOrInsert(connect, _from, _to, page_title);

        TQuotRef quot_ref = TQuotRef.get(connect, y, a,
                                _title, _title_wikilink,
                                p, src);
        if(null == quot_ref)
            quot_ref = insertByID (connect, y, a,
                                _title, _title_wikilink,
                                p, src);
        return quot_ref;
    }*/

    /** Deletes row from the table 'quot_ref' by a value of ID.<br><br>
     * DELETE FROM quot_ref WHERE id=4;
     */
    /*public void delete (Connect connect) {

        StringBuilder str_sql = new StringBuilder();
        str_sql.append("DELETE FROM quot_ref WHERE id=");
        str_sql.append( id );
        try {
            Statement s = connect.conn.createStatement ();
            try {
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotRef.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }*/

}
