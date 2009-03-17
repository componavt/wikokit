/* TWikiTextWords.java - SQL operations with the table 'wiki_text' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.sql.Connect;
import java.sql.*;

/** An operations with the table 'wiki_text_words' in MySQL wiktionary_parsed database.
 */
public class TWikiTextWords {

    /** Unique identifier in the table 'wiki_text_words'. */
    private int id;

    /** Wiki text (without wikification). */
    private TWikiText wiki_text;

    /** Link from wikified word to a title of wiki article (page) and an inflectional form. */
    private TPageInflection page_inflection;

    public TWikiTextWords(int _id,TWikiText _wiki_text,TPageInflection _page_inflection) {
        id              = _id;
        wiki_text       = _wiki_text;
        page_inflection = _page_inflection;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Inserts record into the table 'wiki_text_words'.<br><br>
     * INSERT INTO wiki_text_words (wiki_text_id,page_inflection_id) VALUES (1,1);
     * @param wiki_text         text (without wikification).
     * @param page_inflection   wikified word from wiki_text
     * @return inserted record, or null if insertion failed
     */
    public static TWikiTextWords insert (Connect connect,TWikiText wiki_text,TPageInflection page_inflection) {

        if(null == wiki_text || null == page_inflection)
            return null;

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TWikiTextWords words = null;
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO wiki_text_words (wiki_text_id,page_inflection_id) VALUES (");
            str_sql.append(wiki_text.getID());
            str_sql.append(",");
            str_sql.append(page_inflection.getID());
            str_sql.append(")");
            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            rs = s.getResultSet ();
            if (rs.next ())
                words = new TWikiTextWords(rs.getInt("id"), wiki_text, page_inflection);
                
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiTextWords.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return words;
    }

    /** Selects row from the table 'wiki_text' by a text.
     *
     *  SELECT id FROM wiki_text WHERE text="apple";
     *
     * @param  text  text (without wikification).
     * @return null if text is absent
     */
    public static TWikiTextWords[] getByWikiText (Connect connect,TWikiText wiki_text) {

        if(null == wiki_text)
            return null;
            
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TWikiTextWords[] words = null;
        /*
        try {
            s = connect.conn.createStatement ();
            String safe_title = PageTableBase.convertToSafeStringEncodeToDB(connect, text);
            str_sql.append("SELECT id FROM wiki_text WHERE text=\"");
            str_sql.append(safe_title);
            str_sql.append("\"");
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
            {
                int id = rs.getInt("id");
                wiki_text = new TWikiText(id, text);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }*/
        return words;
    }

    /** Selects row from the table 'wiki_text' by ID<br>
     * SELECT id FROM wiki_text WHERE id=1;
     * @return empty array if data is absent
     */
    public static TWikiTextWords getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TWikiTextWords word = null;
        /*
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id FROM wiki_text WHERE id=");
            str_sql.append(id);
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
            {
                String text = Encodings.bytesToUTF8(rs.getBytes("text"));
                wiki_text = new TWikiText(id, text);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }*/
        return word;
    }

    /** Deletes row from the table 'wiki_text_words' by a value of ID.
     * DELETE FROM wiki_text_words WHERE id=1;
     * @param  id  unique ID in the table `wiki_text_words`
     */
    public static void delete (Connect connect,TWikiTextWords word) {

        if(null == word) {
            System.err.println("Error (wikt_parsed TWikiTextWords.delete()):: null argument word.");
            return;
        }
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM wiki_text_words WHERE id=");
            str_sql.append(word.getID());
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiTextWords.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
}
