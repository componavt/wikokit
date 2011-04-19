/* TQuotRef.java - quotation reference information (year, author, etc.),
 * SQL operations with the table 'quot_ref' in Wiktionary parsed database.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql.quote;

import java.sql.*;
import wikipedia.language.Encodings;
import wikipedia.sql.Connect;
import wikipedia.sql.PageTableBase;

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

    /** Gets author(s) of quote from database. */
    public TQuotAuthor getAuthor() {
        return author;
    }

    /** Gets publisher of quote from database. */
    public TQuotPublisher getPublisher() {
        return publisher;
    }

    /** Gets source of quote from database. */
    public TQuotSource getSource() {
        return source;
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
    private static TQuotRef get (Connect connect,
                                TQuotYear y, TQuotAuthor a,
                                String _title, String _title_wikilink,
                                TQuotPublisher p, TQuotSource src)
    {
        String year_id = (null == y) ? " IS NULL" : "=" + y.getID();
        String author_id = (null == a) ? " IS NULL" : "=" + a.getID();
        String publisher_id = (null == p) ? " IS NULL" : "=" + p.getID();
        String source_id = (null == src) ? " IS NULL" : "=" + src.getID();

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TQuotRef result = null;

        String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _title);
        String safe_title_wikilink = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _title_wikilink);

        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id FROM quot_ref WHERE year_id");
            str_sql.append(year_id);
            str_sql.append(" AND author_id");
            str_sql.append(author_id);
            str_sql.append(" AND title=\"");
            str_sql.append(safe_title);
            str_sql.append("\" AND title_wikilink=\"");
            str_sql.append(safe_title_wikilink);
            str_sql.append("\" AND publisher_id");
            str_sql.append(publisher_id);
            str_sql.append(" AND source_id");
            str_sql.append(source_id);
            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                result = new TQuotRef(rs.getInt("id"), _title, _title_wikilink,
                                      y, // TQuotYear _year
                                      a, // TQuotAuthor _author,
                                      p, // TQuotPublisher _publisher,
                                      src); // TQuotSource _source
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (TQuotRef.get):: _author='"+a.getName()+"'; _title='"+_title+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return result;
    }

    /** Selects row from the table 'quot_ref' by ID.<br><br>
     *
     * SELECT year_id,author_id,title,title_wikilink,publisher_id,source_id FROM quot_ref WHERE id=1;
     *
     * @return null if data is absent
     */
    public static TQuotRef getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TQuotRef quot_ref = null;

        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT year_id,author_id,title,title_wikilink,publisher_id,source_id FROM quot_ref WHERE id=");
            str_sql.append(id);
            rs = s.executeQuery (str_sql.toString());
            int i;
            if (rs.next ())
            {
                i = rs.getInt("year_id");
                TQuotYear _year = (0 == i) ? null : TQuotYear.getByID(connect, i);

                i = rs.getInt("author_id");
                TQuotAuthor _author = (0 == i) ? null : TQuotAuthor.getByID(connect, i);

                byte[] bb = rs.getBytes("title");
                String _title = null == bb ? null : Encodings.bytesToUTF8(bb);

                bb = rs.getBytes("title_wikilink");
                String _title_wikilink = null == bb ? null : Encodings.bytesToUTF8(bb);

                i = rs.getInt("publisher_id");
                TQuotPublisher _publisher = (0 == i) ? null : TQuotPublisher.getByID(connect, i);

                i = rs.getInt("source_id");
                TQuotSource _source = (0 == i) ? null : TQuotSource.getByID(connect, i);

                quot_ref = new TQuotRef(id, _title, _title_wikilink,
                                      _year,        // TQuotYear
                                      _author,      // TQuotAuthor
                                      _publisher,   // TQuotPublisher
                                      _source);     // TQuotSource
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotRef.getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
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
    private static TQuotRef insertByID (Connect connect,
                                TQuotYear y, TQuotAuthor a,
                                String _title, String _title_wikilink,
                                TQuotPublisher p, TQuotSource src)
    {
        String year_id = (null == y) ? "NULL" : "" + y.getID();
        String author_id = (null == a) ? "NULL" : "" + a.getID();
        String publisher_id = (null == p) ? "NULL" : "" + p.getID();
        String source_id = (null == src) ? "NULL" : "" + src.getID();
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TQuotRef result = null;

        String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _title);
        String safe_title_wikilink = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _title_wikilink);

        try
        {
            s = connect.conn.createStatement ();
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
            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            if (rs.next ())
                result = new TQuotRef(rs.getInt("id"), _title, _title_wikilink,
                                      y, // TQuotYear _year
                                      a, // TQuotAuthor _author,
                                      p, // TQuotPublisher _publisher,
                                      src); // TQuotSource _source

        }catch(SQLException ex) {
            System.err.println("SQLException (TQuotRef.insert):: _author='"+a.getName()+"'; _title='"+_title+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return result;
    }

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
    public static TQuotRef insert (Connect connect,String _author,String _author_wikilink,
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
    }

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
    public static TQuotRef insertWithYears (Connect connect,String page_title,
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
    }

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
    public static TQuotRef getOrInsert (Connect connect,String _author,String _author_wikilink,
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
    }

    public static TQuotRef getOrInsertWithYears (Connect connect,String page_title,
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
    }

    /** Deletes row from the table 'quot_ref' by a value of ID.<br><br>
     * DELETE FROM quot_ref WHERE id=4;
     */
    public void delete (Connect connect) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM quot_ref WHERE id=");
            str_sql.append( id );
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuotRef.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

}
