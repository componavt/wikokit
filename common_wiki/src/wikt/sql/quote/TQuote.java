/* TQuotation.java - SQL operations with the table 'quotation' in Wiktionary parsed database.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql.quote;

import java.sql.*;
import wikipedia.language.LanguageType;

import wikipedia.sql.Connect;
import wikipedia.sql.PageTableBase;
import wikt.sql.TLang;
import wikt.sql.TMeaning;

/** Operations with the table 'quotation' in MySQL Wiktionary parsed database. */
public class TQuote {

    /** Quotation unique identifier. */
    private int id;

    /** One sense of a word, field quote.meaning_id in database. */
    private TMeaning meaning;

    /** Language of the quotation, field quote.lang_id. */
    private TLang lang;

    /** Quotation text, field quote.text in database. */
    private String text;

    /** quotation reference information (year, author, etc.), field quote.ref_id in database. */
    private TQuotRef quot_ref;

    public TQuote(int _id, TMeaning _meaning, TLang _lang, String _text,
                  TQuotRef _quot_ref)
    {
        id          = _id;
        meaning     = _meaning;
        lang       = _lang;
        text   = _text;
        quot_ref = _quot_ref;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets meaning for this quotation from database. */
    public TMeaning getMeaning() {
        return meaning;
    }

    /** Gets language of the quotation. */
    public TLang getLanguage() {
        return lang;
    }

    /** Gets quotation text. */
    public String getText() {
        return text;
    }

    /** Gets reference data about quote from database. */
    public TQuotRef getReference() {
        return quot_ref;
    }

    /** Gets translation of the quotation from database. */
    public String getTranslation(Connect connect) {
        TQuotTranslation t = TQuotTranslation.getByID(connect, id);
        
        if(null != t)
            return t.getText();    
        return "";
    }

    /** Gets translation of the quotation from database. */
    public String getTransription(Connect connect) {
        TQuotTranscription t = TQuotTranscription.getByID(connect, id);

        if(null != t)
            return t.getText();
        return "";
    }


    /** Inserts record into the table quote.<br><br>
     *
     * INSERT INTO quote (meaning_id,lang_id,text,ref_id) VALUES (1,286,"",NULL)
     *
     * @param _meaning meaning of a word corresponding to the quote
     * @param _lang language of the quote
     * @param _text quotation itself
     * @param _quot_ref bibliography and reference data
     * @return inserted record, or null if insertion failed
     */
    public static TQuote insert (Connect connect, TMeaning _meaning, TLang _lang, String _text,
                  TQuotRef _quot_ref)
    {
        if(null == _text || _text.length() == 0) {
            System.err.println("Error (TQuote.insert()):: quotation text is empty.");
            return null;
        }
        if(null == _meaning) {
            System.err.println("Error (TQuote.insert()):: _meaning is null.");
            return null;
        }
        if(null == _lang) {
            System.err.println("Error (TQuote.insert()):: _lang is null.");
            return null;
        }
        String quot_ref_id = (null == _quot_ref) ? "NULL" : "" + _quot_ref.getID();
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TQuote result = null;
        String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _text);
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO quote (meaning_id,lang_id,text,ref_id) VALUES (");
            str_sql.append(_meaning.getID());
            str_sql.append(",");
            str_sql.append(_lang.getID());
            str_sql.append(",\"");
            str_sql.append(safe_text);
            str_sql.append("\",");
            str_sql.append(quot_ref_id);
            str_sql.append(")");
            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            if (rs.next ())
                result = new TQuote(rs.getInt("id"),
                                      _meaning, _lang, _text, _quot_ref);

        }catch(SQLException ex) {
            System.err.println("SQLException (TQuotRef.insert):: _text='"+_text+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return result;
    }

    /** Inserts quote and reference (ref. without years) records into the tables:
     * quote, quot_ref, quot_year, quot_author, quot_publisher, and quot_source.<br><br>
     *
     * @param _text quotation itself
     * @param _meaning meaning of a word corresponding to the quote
     * @param _lang language of the quote
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
    public static TQuote insertWithReference (Connect connect,
                                String _text, TMeaning _meaning, TLang _lang,
                                
                                // reference data:
                                String _author,String _author_wikilink,
                                String _title, String _title_wikilink,
                                String _publisher, String _source)
    {
        TQuotRef quot_ref = TQuotRef.getOrInsert(connect, _author, _author_wikilink,
                                    _title, _title_wikilink, _publisher, _source);

        return TQuote.insert(connect, _meaning, _lang, _text, quot_ref);
    }

    /** Inserts quote and reference (ref. without years) records into the tables:
     * quote, quot_ref, quot_year, quot_author, quot_publisher, and quot_source.<br><br>
     *
     * @param _text quotation itself
     * @param _meaning meaning of a word corresponding to the quote
     * @param _lang language of the quote
     *
     * @param _author author's name,
     * @param _author_wikilink link to author's name in Wikipedia (format: [[w:name|]]),
     * @param _title title of the work
     * @param _title_wikilink link to a book in Wikipedia (format: [[w:title|]]),
     *                        it could be empty ("")
     * @param _publisher quote book publisher
     * @param _source quote source
     * * @param _from start date of a writing book with the quote
     * @param _to finish date of a writing book with the quote
     * @return inserted record, or null if insertion failed
     */
    public static TQuote insertWithYears (Connect connect,
                                String _text, TMeaning _meaning, TLang _lang,

                                // reference data:
                                String _author,String _author_wikilink,
                                String _title, String _title_wikilink,
                                String _publisher, String _source,
                                int _from, int _to)
    {
        TQuotRef quot_ref = TQuotRef.getOrInsertWithYears(connect, _author, _author_wikilink,
                                    _title, _title_wikilink, _publisher, _source,
                                    _from, _to);

        return TQuote.insert(connect, _meaning, _lang, _text, quot_ref);
    }

    /** Inserts quote (with translation, transcription) and reference
     * (ref. without years) records into the tables: quote, quot_translation,
     * quot_transcription, quot_ref, quot_year, quot_author, quot_publisher,
     * and quot_source.<br><br>
     *
     * @param _text quotation itself
     * @param _translation translation of quotation
     * @param _transcription transcription of quotation
     * @param _meaning meaning of a word corresponding to the quote
     * @param _lang language of the quote
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
    public static TQuote insertWithTranslationTranscription (Connect connect,
                                String _text, String _translation, String _transcription,
                                TMeaning _meaning, TLang _lang,

                                // reference data:
                                String _author,String _author_wikilink,
                                String _title, String _title_wikilink,
                                String _publisher, String _source)
    {
        TQuotRef quot_ref = TQuotRef.getOrInsert(connect, _author, _author_wikilink,
                                    _title, _title_wikilink, _publisher, _source);

        TQuote q = TQuote.insert(connect, _meaning, _lang, _text, quot_ref);
        int quote_id = q.getID();

        TQuotTranslation.insert(connect, quote_id, _translation);
        TQuotTranscription.insert(connect, quote_id, _transcription);
        return q;
    }

    /** Inserts quote (with translation, transcription) and reference
     * (with years) records into the tables: quote, quot_translation,
     * quot_transcription, quot_ref, quot_year, quot_author, quot_publisher,
     * and quot_source.<br><br>
     *
     * @param _text quotation itself
     * @param _translation translation of quotation
     * @param _transcription transcription of quotation
     * @param _meaning meaning of a word corresponding to the quote
     * @param _lang language of the quote
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
    public static TQuote insertWithYearsTranslationTranscription (Connect connect,
                                String _text, String _translation, String _transcription,
                                TMeaning _meaning, TLang _lang,

                                // reference data:
                                String _author,String _author_wikilink,
                                String _title, String _title_wikilink,
                                String _publisher, String _source,
                                int _from, int _to)
    {
        TQuotRef quot_ref = TQuotRef.getOrInsertWithYears(connect, _author, _author_wikilink,
                                    _title, _title_wikilink, _publisher, _source,
                                    _from, _to);

        TQuote q = TQuote.insert(connect, _meaning, _lang, _text, quot_ref);
        int quote_id = q.getID();

        TQuotTranslation.insert(connect, quote_id, _translation);
        TQuotTranscription.insert(connect, quote_id, _transcription);
        return q;
    }

    /** Deletes row from the table 'quote' by a value of ID.<br><br>
     * DELETE FROM quote WHERE id=4;
     */
    public void delete (Connect connect) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM quote WHERE id=");
            str_sql.append( id );
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuote.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
}
