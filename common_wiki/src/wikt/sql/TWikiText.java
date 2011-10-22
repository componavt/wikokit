/* TWikiText.java - SQL operations with the table 'wiki_text' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
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
 * ?Automatic recommendations to wikify text1 & text2 in Wiktionary?
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
    
    /** If table 'wiki_text' has this text, then return ID of this record,
     * if it is absent then add it.
     * 
     * In any case, let's try to insert records into the tables:
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
        if(null == twiki_text)
            twiki_text = TWikiText.insert(connect, visible_text);
        
        if(null == twiki_text) { // if two very long wiki_text has the same 100 first symbols
            System.err.println("Error: (wikt_parsed TWikiText.storeToDB()):: two very long wiki_text has the same 100 first symbols. Insertion failed. wiki_text='" + wiki_text.getVisibleText());
            return null;
        }

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

        StringBuilder str_sql = new StringBuilder();
        TWikiText wiki_text = null;
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("INSERT INTO wiki_text (text) VALUES (\"");
                String safe_text = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, text);
                str_sql.append(safe_text);
                str_sql.append("\")");
                s.executeUpdate (str_sql.toString());

                s = connect.conn.createStatement ();
                ResultSet rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
                try {
                    if (rs.next ())
                        wiki_text = new TWikiText(rs.getInt("id"), text);
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.insert()):: text='"+text+"'; sql='" + str_sql.toString() + "' error=" + ex.getMessage());
        }
        return wiki_text;
    }

    /** Selects row from the table 'wiki_text' by a text.<br><br>
     *  SELECT id FROM wiki_text WHERE text="apple";
     * @param  text  text (without wikification).
     * @return null if text is absent
     */
    public static TWikiText get (Connect connect,String text) {

        StringBuilder str_sql = new StringBuilder();
        TWikiText wiki_text = null;
        
        try {
            Statement s = connect.conn.createStatement ();
            try {
                String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, text);
                str_sql.append("SELECT id FROM wiki_text WHERE text=\"");
                str_sql.append(safe_title);
                str_sql.append("\"");
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        int id = rs.getInt("id");
                        wiki_text = new TWikiText(id, text);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return wiki_text;
    }
    
    /** Selects row from the table 'wiki_text' by ID<br><br>
     * SELECT id FROM wiki_text WHERE id=1;
     * @return null if data is absent
     */
    public static TWikiText getByID (Connect connect,int id) {
        
        StringBuilder str_sql = new StringBuilder();
        TWikiText wiki_text = null;

        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT text FROM wiki_text WHERE id=");
                str_sql.append(id);
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        String text = Encodings.bytesToUTF8(rs.getBytes("text"));
                        wiki_text = new TWikiText(id, text);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return wiki_text;
    }


    /** Deletes row from the table 'wiki_text' and deletes related rows
     * from the table 'wiki_text_words'.<br><br>
     */
    public static void deleteWithWords (Connect connect, TWikiText wiki_text) {

        if(null == wiki_text) {
            System.err.println("Error (wikt_parsed TWikiText.deleteWithWords()):: null argument wiki_text.");
            return;
        }

        // 2. delete WikiTextWords by WikiText.ID
        TWikiTextWords[] ww = TWikiTextWords.getByWikiText(connect, wiki_text);
        for(TWikiTextWords w : ww)
            TWikiTextWords.delete(connect, w);

        // 3. delete WikiText
        TWikiText.delete(connect, wiki_text);
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
           
        StringBuilder str_sql = new StringBuilder();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("DELETE FROM wiki_text WHERE id=");
                str_sql.append(wiki_text.getID());
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiText.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }

    /** Deletes row from the table 'wiki_text' by a value of a wiki_text string.<br><br>
     * DELETE FROM wiki_text WHERE wiki_text="wiki_text";
     * @param  wiki_text  wiki text (without wikification)
     */
    public static void delete (Connect connect,String wiki_text) {

        if(0 == wiki_text.length()) {
            System.err.println("Error (wikt_parsed TWikiText.delete()):: empty string wiki_text.");
            return;
        }
        
        StringBuilder str_sql = new StringBuilder();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("DELETE FROM wiki_text WHERE text=\"");
                str_sql.append( PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                connect, wiki_text));
                str_sql.append('"');
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TWikiText.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
}
