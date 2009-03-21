/* TWikiText.java - SQL operations with the table 'wiki_text' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.language.Encodings;
import wikt.util.WikiText;
import wikt.util.WikiWord;

import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
import java.sql.*;

/** An operations with the table 'wiki_text' in MySQL wiktionary_parsed database.
 *
 * The question: Are the value '.text' UNIQUE in the table wiki_text?
 * E.g.
 * text1 = [[ум]], интеллект
 * text2 = ум, [[интеллект]]
 * text3 = [[ум]], [[интеллект]]
 * Decision: add only text3 to the table, becaus it has max wiki_words=2.
 * ?Automatic recommenations to wikify text1 & text2 in Wiktionary?
 */
public class TWikiText {

    /** Unique identifier in the table 'wiki_text'. */
    private int id;

    /** Text (without wikification). */
    private StringBuffer text;

    //private final static TMeaning[] NULL_TMEANING_ARRAY = new TMeaning[0];
    
    public TWikiText(int _id,String _text) {
        id              = _id;
        text            = new StringBuffer(_text);
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets text (without wikification) from database */
    public String getText() {
        return text.toString();
    }
    
    /** If table 'wiki_text' has this text, then return ID of this record;
     * else inserts records into tables:
     * 'wiki_text',
     * 'wiki_text_words',
     * 'page_inflecton',
     * 'inflection',
     * 'page'.<br><br>
     *
     * @param wiki_text      text with wiki words.
     * @return inserted record, or null if insertion failed
     */
    public static TWikiText storeToDB (Connect connect,WikiText wiki_text) {

        if(null == wiki_text) return null;

        String visible_text = wiki_text.getVisibleText();

        if(visible_text.length() == 0) return null;

        TWikiText  twiki_text = TWikiText.get(connect, visible_text);
        if(null != twiki_text)
            return twiki_text;

        twiki_text = TWikiText.insert(connect, visible_text);
        assert(null != twiki_text);

        WikiWord[] wiki_words = wiki_text.getWikiWords();
        for(WikiWord ww : wiki_words)
            TWikiTextWords.storeToDB (connect, twiki_text, ww);
            
        return twiki_text;
    }

    /** Inserts record into the table 'wiki_text'.<br><br>
     * INSERT INTO wiki_text (text) VALUES ("apple");
     * @param text      text (without wikification).
     * @return inserted record, or null if insertion failed
     */
    public static TWikiText insert (Connect connect,String text) {

        if(text.length() == 0)
            return null;

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TWikiText wiki_text = null;
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO wiki_text (text) VALUES (\"");
            String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, text);
            str_sql.append(safe_text);
            str_sql.append("\")");
            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            rs = s.getResultSet ();
            if (rs.next ())
                wiki_text = new TWikiText(rs.getInt("id"), text);

        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return wiki_text;
    }

    /** Selects row from the table 'wiki_text' by a text.<br><br>
     *  SELECT id FROM wiki_text WHERE text="apple";
     * @param  text  text (without wikification).
     * @return null if text is absent
     */
    public static TWikiText get (Connect connect,String text) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TWikiText   wiki_text = null;
        
        try {
            s = connect.conn.createStatement ();
            String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, text);
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
        }
        return wiki_text;
    }
    
    /** Selects row from the table 'wiki_text' by ID<br><br>
     * SELECT id FROM wiki_text WHERE id=1;
     * @return null if data is absent
     */
    public static TWikiText getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TWikiText wiki_text = null;

        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT text FROM wiki_text WHERE id=");
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
        }
        return wiki_text;
    }

    /** Deletes row from the table 'wiki_text' by a value of ID.<br><br>
     * DELETE FROM wiki_text WHERE id=1;
     * @param  id  unique ID in the table `wiki_text`
     */
    public static void delete (Connect connect,TWikiText wiki_text) {

        if(null == wiki_text) {
            System.err.println("Error (wikt_parsed TWikiText.delete()):: null argument wiki_text.");
            return;
        }
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM wiki_text WHERE id=");
            str_sql.append(wiki_text.getID());
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
}
