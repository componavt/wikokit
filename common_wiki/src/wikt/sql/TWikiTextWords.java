/* TWikiTextWords.java - SQL operations with the table 'wiki_text' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikt.util.WikiWord;

import wikipedia.sql.Connect;
import java.sql.*;

import java.util.List;
import java.util.ArrayList;

/** An operations with the table 'wiki_text_words' in MySQL wiktionary_parsed database.
 */
public class TWikiTextWords {

    /** Unique identifier in the table 'wiki_text_words'. */
    private int id;

    /** Wiki text (without wikification). */
    private TWikiText wiki_text;

    /** Link from wikified word to a title of wiki article (page), lemma. */
    private TPage page;
    
    /** Link from wikified word to a title of wiki article (page) and an inflectional form. 
     * It could be null, e.g. for "[[device]]".
     * It is not null for "[[run|running]].
     */
    private TPageInflection page_inflection;

    private final static TWikiTextWords[] NULL_TWIKITEXTWORDS_ARRAY = new TWikiTextWords[0];

    public TWikiTextWords(int _id,TWikiText _wiki_text,TPage _page,TPageInflection _page_inflection) {
        id              = _id;
        wiki_text       = _wiki_text;
        page            = _page;
        page_inflection = _page_inflection;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets wiki text from database */
    public TWikiText getWikiText() {
        return wiki_text;
    }

    
    /** If this word is absent in the table 'wiki_text_words' then
     * inserts records into tables:
     * 'wiki_text_words',
     * 'page_inflecton',
     * 'inflection',
     * 'page'.
     * 
     * Tables 'page_inflecton' and 'inflection' will be filled 
     * only if word_visible != word_link of wiki_word.
     *
     * @param wiki_text      text with wiki words.
     * @return inserted record, or null if insertion failed
     */
    public static void storeToDB (Connect connect,TWikiText twiki_text,WikiWord wiki_word) {

        if(null == wiki_word) return;
        
        String word_visible = wiki_word.getWordVisible();
        String word_link    = wiki_word.getWordLink();
        
        if(word_link.length() == 0) return;

        // fill table 'page'
        int word_count = 0;
        int wiki_link_count = 0;
        boolean is_in_wiktionary = false; // don't know, may be
        TPage page = TPage.getOrInsert(connect, word_link, word_count, wiki_link_count, is_in_wiktionary);
        assert(page != null);

        TPageInflection page_infl;
        if(0 != word_link.compareTo(word_visible)) {
            int freq = 1;       // fill also tables 'page_inflecton' and 'inflection'
            TInflection infl = TInflection.getOrInsert(connect, word_visible, freq);
            assert(null != infl);

            int term_freq = 1;
            page_infl = TPageInflection.getOrInsert(connect, page, infl, term_freq);
            assert(null != page_infl);
        } else
            page_infl = null;   // skip tables 'page_inflecton' and 'inflection'

        // fill table 'wiki_text_words'
        TWikiTextWords w = TWikiTextWords.getOrInsert(connect, twiki_text, page, page_infl);
        assert(w != null);
    }
    
    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'wiki_text_words'.
     *
     
     * @return inserted record, or null if insertion failed
     */
    public static TWikiTextWords getOrInsert (Connect connect,TWikiText wiki_text,
                                        TPage page,TPageInflection page_inflection) {

        TWikiTextWords w = TWikiTextWords.getByWikiTextAndPageAndInflection(connect, wiki_text, page, page_inflection);
        if(null == w)
            w = TWikiTextWords.insert(connect, wiki_text, page, page_inflection);
        return w;
    }

    /** Inserts record into the table 'wiki_text_words'.<br><br>
     *
     * INSERT INTO wiki_text_words (wiki_text_id,page_id,page_inflection_id) VALUES (1,1,1);
     *
     * @param wiki_text         text (without wikification)
     * @param page              link of wikified word
     * @param page_inflection   wikified word from wiki_text, it could be null
     * @return inserted record, or null if insertion failed
     */
    public static TWikiTextWords insert (Connect connect,TWikiText wiki_text,
                                        TPage page,TPageInflection page_inflection) {
        if(null == wiki_text || null == page)
            return null;

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TWikiTextWords words = null;
        try
        {
            s = connect.conn.createStatement ();
            if(null != page_inflection)
                str_sql.append("INSERT INTO wiki_text_words (wiki_text_id,page_id,page_inflection_id) VALUES (");
            else
                str_sql.append("INSERT INTO wiki_text_words (wiki_text_id,page_id) VALUES (");
            str_sql.append(wiki_text.getID());
            str_sql.append(",");
            str_sql.append(page.getID());
            if(null != page_inflection) {
                str_sql.append(",");
                str_sql.append(page_inflection.getID());
            }
            str_sql.append(")");
            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            rs = s.getResultSet ();
            if (rs.next ())
                words = new TWikiTextWords(rs.getInt("id"), wiki_text, page, page_inflection);
                
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiTextWords.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return words;
    }

    /** Selects records from 'wiki_text_words' table by an ID of wiki text.<br><br>
     *
     * SELECT id,page_id,page_inflection_id FROM wiki_text_words WHERE wiki_text_id=1;
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
        List<TWikiTextWords> list_words = null;
        
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id,page_id,page_inflection_id FROM wiki_text_words WHERE wiki_text_id=");
            str_sql.append(wiki_text.getID());
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            while (rs.next ())
            {
                int pi = rs.getInt("page_inflection_id");
                TPageInflection page_infl = 0 == pi ? null : TPageInflection.getByID(connect, pi);

                TPage page = TPage.getByID(connect, rs.getInt("page_id"));
                
                if(null != page) {
                    if(null == list_words)
                        list_words = new ArrayList<TWikiTextWords>();

                    list_words.add(new TWikiTextWords(rs.getInt("id"), wiki_text, page, page_infl));
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiTextWords.java getByWikiText()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        if(null == list_words)
            return NULL_TWIKITEXTWORDS_ARRAY;
        return ((TWikiTextWords[])list_words.toArray(NULL_TWIKITEXTWORDS_ARRAY));
    }
    
    /** Selects one record from 'wiki_text_words' table by wiki text ID 
     * and page ID and page_inflection.id .<br><br>
     *
     * SELECT id FROM wiki_text_words WHERE wiki_text_id=9 AND page_id=9 AND page_inflection_id=9;
     * SELECT id FROM wiki_text_words WHERE wiki_text_id=9 AND page_id=9 AND page_inflection_id IS NULL;
     * @param  text  text (without wikification).
     * @return null if text is absent
     */
    public static TWikiTextWords getByWikiTextAndPageAndInflection (Connect connect,
            TWikiText wiki_text,TPage page,TPageInflection page_inflection) {

        if(null == wiki_text || null == page)
            return null;

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TWikiTextWords word = null;

        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id FROM wiki_text_words WHERE wiki_text_id=");
            str_sql.append(wiki_text.getID());
            str_sql.append(" AND page_id=");
            str_sql.append(page.getID());
            if(null != page_inflection) {
                str_sql.append(" AND page_inflection_id=");
                str_sql.append(page_inflection.getID());
            } else
                str_sql.append(" AND page_inflection_id IS NULL");
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
                word = new TWikiTextWords(rs.getInt("id"), wiki_text, page, page_inflection);

        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiTextWords.java getByWikiText()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return word;
    }

    /** Selects row from the table 'wiki_text' by ID<br><br>
     *
     * SELECT wiki_text_id,page_id,page_inflection_id FROM wiki_text_words WHERE id=1;
     *
     * @return null if data is absent
     */
    public static TWikiTextWords getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TWikiTextWords word = null;
        
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT wiki_text_id,page_id,page_inflection_id FROM wiki_text_words WHERE id=");
            str_sql.append(id);
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if(rs.next ())
            {
                TWikiText       wiki_text = TWikiText.      getByID(connect, rs.getInt("wiki_text_id"));
                TPage page = TPage.getByID(connect, rs.getInt("page_id"));

                int pi = rs.getInt("page_inflection_id");
                TPageInflection page_infl = 0 == pi ? null : TPageInflection.getByID(connect, pi);
                
                if(null != wiki_text && null != page)
                    word = new TWikiTextWords(id, wiki_text, page, page_infl);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiTextWords.java getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
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
