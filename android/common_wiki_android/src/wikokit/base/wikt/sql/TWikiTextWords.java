/* TWikiTextWords.java - SQL operations with the table 'wiki_text' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql;

import wikokit.base.wikt.util.WikiWord;
//import wikokit.base.wikipedia.sql.Connect;

import java.util.List;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    private final static TWikiText[]      NULL_TWIKITEXT_ARRAY      = new TWikiText[0];
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

    /** Gets page (article entry) from the table 'page'. */
    public TPage getPage() {

        // wiki_text_word.page_id -> page.id
        if(null != page)
            return page;

        // wiki_text_word.page_inflection_id -> page_inflection.id -> page.id
        if(null != page_inflection)
            return page_inflection.getPage();

        return null;
    }

    /** Clears (deletes) object fields. */
    public void freeUp() {
        if(null != wiki_text) {
//            wiki_text.freeUp();
            wiki_text = null;
        }

        if(null != page) {
            page = null;
//            page.freeUp();
        }
        
        page_inflection = null;
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
    /*public static void storeToDB (Connect connect,TWikiText twiki_text,WikiWord wiki_word) {

        if(null == wiki_word) return;
        
        String word_visible = wiki_word.getWordVisible();
        String word_link    = wiki_word.getWordLink();
        
        if(word_link.length() == 0) return;

        // fill table 'page'
        int word_count = 0;
        int wiki_link_count = 0;
        boolean is_in_wiktionary = false; // don't know, may be (!todo the check)
        String redirect_target = null;
        TPage page = TPage.getOrInsert(connect, word_link, word_count, wiki_link_count, 
                                       is_in_wiktionary, redirect_target);
        assert(page != null);

        TPageInflection page_infl;
        if(0 != word_link.compareTo(word_visible)) {
            int freq = 1;       // fill also tables 'page_inflecton' and 'inflection'
            TInflection infl = TInflection.getOrInsert(connect, word_visible, freq);
            assert(null != infl);

            int term_freq = 1;
            page_infl = TPageInflection.getOrInsert(connect, page, infl, term_freq);
            assert(null != page_infl);
            infl = null;
        } else
            page_infl = null;   // skip tables 'page_inflecton' and 'inflection'

        // fill table 'wiki_text_words'
        TWikiTextWords w = TWikiTextWords.getOrInsert(connect, twiki_text, page, page_infl);
        assert(w != null);

        w = null;
        page = null;
        page_infl = null;
    }*/
    
    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'wiki_text_words'.
     *
     
     * @return inserted record, or null if insertion failed
     */
    /*public static TWikiTextWords getOrInsert (Connect connect,TWikiText wiki_text,
                                        TPage page,TPageInflection page_inflection) {

        TWikiTextWords w = TWikiTextWords.getByWikiTextAndPageAndInflection(connect, wiki_text, page, page_inflection);
        if(null == w)
            w = TWikiTextWords.insert(connect, wiki_text, page, page_inflection);
        return w;
    }*/

    /** Inserts record into the table 'wiki_text_words'.<br><br>
     *
     * INSERT INTO wiki_text_words (wiki_text_id,page_id,page_inflection_id) VALUES (1,1,1);
     *
     * @param wiki_text         text (without wikification)
     * @param page              link of wikified word
     * @param page_inflection   wikified word from wiki_text, it could be null
     * @return inserted record, or null if insertion failed
     */
    /*public static TWikiTextWords insert (Connect connect,TWikiText wiki_text,
                                        TPage page,TPageInflection page_inflection) {
        if(null == wiki_text || null == page)
            return null;

        StringBuilder str_sql = new StringBuilder();
        TWikiTextWords words = null;
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
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
            } finally {
                s.close();
            }

            s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
                try {
                    if (rs.next ()) {
                        words = new TWikiTextWords(rs.getInt("id"), wiki_text, page, page_inflection);
                        //System.out.println("TWikiTextWords.insert()):: wiki_text='" + wiki_text.getText() + "'; id=" + rs.getInt("id") + "; page='" + page.getPageTitle() + "'");
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (TWikiTextWords.insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return words;
    }*/

    /** Selects records from 'wiki_text_words' table by an ID of wiki text.<br><br>
     *
     * SELECT id,page_id,page_inflection_id FROM wiki_text_words WHERE wiki_text_id=1;
     * 
     * @param  text  text (without wikification).
     * @return empty array if there are no wikified words
     */
    public static TWikiTextWords[] getByWikiText (SQLiteDatabase db,TWikiText _wiki_text) {

        if(null == _wiki_text)
            return NULL_TWIKITEXTWORDS_ARRAY;
        List<TWikiTextWords> list_words = null;
        
        // SELECT id,page_id,page_inflection_id FROM wiki_text_words WHERE wiki_text_id=1
        Cursor c = db.query("wiki_text_words", 
                new String[] { "id", "page_id", "page_inflection_id"}, 
                "wiki_text_id=" + _wiki_text.getID(), 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            do {
                int i_id = c.getColumnIndexOrThrow("id");
                int i_page_id = c.getColumnIndexOrThrow("page_id");
                int i_page_inflection_id = c.getColumnIndexOrThrow("page_inflection_id");
                
                int  _id                = c.getInt(i_id);
                int  page_id            = c.getInt(i_page_id);
                TPage _page = TPage.getByID(db, page_id);
                        
                int  pi = c.getInt(i_page_inflection_id);
                TPageInflection page_infl = 0 == pi ? null : TPageInflection.getByID(db, pi);
                
                if(null != _page) {
                    if(null == list_words)
                        list_words = new ArrayList<TWikiTextWords>();

                    list_words.add(new TWikiTextWords(_id, _wiki_text, _page, page_infl));
                }
                
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        if(null == list_words)
            return NULL_TWIKITEXTWORDS_ARRAY;
        return ((TWikiTextWords[])list_words.toArray(NULL_TWIKITEXTWORDS_ARRAY));
    }
    
    /** Selects records from 'wiki_text_words' table by an ID of page.<br><br>
     * SELECT id,wiki_text_id,page_inflection_id FROM wiki_text_words WHERE page_id=1;
     * 
     * @param  page  wikified word which belong to some wiki text
     * @return empty array if there are no wiki texts with this word
     */
    public static TWikiTextWords[] getByPage (SQLiteDatabase db,TPage page) {

        if(null == page) {
            System.err.println("Error (TWikiTextWords.getByPage()):: null argument page.");
            return NULL_TWIKITEXTWORDS_ARRAY;
        }
        List<TWikiTextWords> list_words = null;
        
        // SELECT id,wiki_text_id,page_inflection_id FROM wiki_text_words WHERE page_id=1;
        Cursor c = db.query("wiki_text_words", 
                new String[] { "id", "wiki_text_id", "page_inflection_id"}, 
                "page_id=" + page.getID(),
                null, null, null, null);
        
        if (c.moveToFirst()) {
            do {
                int i_id = c.getColumnIndexOrThrow("id");
                int i_wiki_text_id = c.getColumnIndexOrThrow("wiki_text_id");
                int i_page_inflection_id = c.getColumnIndexOrThrow("page_inflection_id");

                int  _id            = c.getInt(i_id);
                int  wiki_text_id   = c.getInt(i_wiki_text_id);
                TWikiText _wiki_text = wiki_text_id < 1 ? null : TWikiText.getByID(db, wiki_text_id);
                
                int  pi = c.getInt(i_page_inflection_id);
                TPageInflection page_infl = 0 == pi ? null : TPageInflection.getByID(db, pi);
                
                if(null != _wiki_text) {
                    if(null == list_words)
                        list_words = new ArrayList<TWikiTextWords>();
                    list_words.add(new TWikiTextWords(_id, _wiki_text, page, page_infl));
                }   
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
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
    public static TWikiTextWords getByWikiTextAndPageAndInflection (SQLiteDatabase db,
            TWikiText _wiki_text,TPage _page,TPageInflection _page_inflection) {

        if(null == _wiki_text || null == _page)
            return null;
        
        TWikiTextWords word = null;
        
        String s = "";
        if(null != _page_inflection) {
            s = " AND page_inflection_id=" + _page_inflection.getID();
        } else
            s = " AND page_inflection_id IS NULL";

     // SELECT id FROM wiki_text_words WHERE wiki_text_id=9 AND page_id=9 AND page_inflection_id=9;
     // SELECT id FROM wiki_text_words WHERE wiki_text_id=9 AND page_id=9 AND page_inflection_id IS NULL;
        Cursor c = db.query("wiki_text_words",
                new String[] { "id"}, 
                "wiki_text_id=" + _wiki_text.getID() + 
                " AND page_id=" + _page.getID() + 
                s, 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            int i_id = c.getColumnIndexOrThrow("id");
            int  _id = c.getInt(i_id);
            
            word = new TWikiTextWords(_id, _wiki_text, _page, _page_inflection);
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return word;
    }

    /** Selects row from the table 'wiki_text' by ID<br><br>
     *
     * SELECT wiki_text_id,page_id,page_inflection_id FROM wiki_text_words WHERE id=1;
     *
     * @return null if data is absent
     */
    public static TWikiTextWords getByID (SQLiteDatabase db,int _id) {
        
        if(_id < 0) {
            System.err.println("Error (TMeaning.getByID()):: ID is negative.");
            return null;
        }
        TWikiTextWords word = null;
        
        // SELECT wiki_text_id,page_id,page_inflection_id FROM wiki_text_words WHERE id=1;
        Cursor c = db.query("wiki_text_words", 
                new String[] { "wiki_text_id", "page_id", "page_inflection_id"}, 
                "id=" + _id, 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            int i_wiki_text_id = c.getColumnIndexOrThrow("wiki_text_id");
            int i_page_id = c.getColumnIndexOrThrow("page_id");
            int i_page_inflection_id = c.getColumnIndexOrThrow("page_inflection_id");

            int  wiki_text_id   = c.getInt(i_wiki_text_id);
            TWikiText _wiki_text = wiki_text_id < 1 ? null : TWikiText.getByID(db, wiki_text_id);
            
            int  page_id = c.getInt(i_page_id);
            TPage _page = TPage.getByID(db, page_id);

            int  pi = c.getInt(i_page_inflection_id);
            TPageInflection page_infl = 0 == pi ? null : TPageInflection.getByID(db, pi);

            if(null != _wiki_text && null != _page)
                word = new TWikiTextWords(_id, _wiki_text, _page, page_infl);
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return word;
    }

    /** Selects only first row (LIMIT 1) from the table 'wiki_text' by wiki_text_id<br><br>
     *
     * SELECT id,page_id,page_inflection_id FROM wiki_text_words WHERE wiki_text_id=1 LIMIT 1;
     *
     * @return null if data is absent
     */
    public static TWikiTextWords getOneByWikiText (SQLiteDatabase db,TWikiText _wiki_text) {
        
        if(null == _wiki_text) {
            System.err.println("Error (TWikiTextWords.getOneByWikiText()):: null argument wiki_text.");
            return null;
        }
        
        TWikiTextWords word = null;
     // SELECT id,page_id,page_inflection_id FROM wiki_text_words WHERE wiki_text_id=1 LIMIT 1
        Cursor c = db.query("wiki_text_words", 
                new String[] { "id", "page_id", "page_inflection_id" }, 
                "wiki_text_id=" + _wiki_text.getID(), 
                null, null, null, null,
                "1"); // limit 1
        
        if (c.moveToFirst()) {
            int i_id = c.getColumnIndexOrThrow("id");
            int i_page_id = c.getColumnIndexOrThrow("page_id");
            int i_page_inflection_id = c.getColumnIndexOrThrow("page_inflection_id");

            int _id = c.getInt(i_id);
            int page_id = c.getInt(i_page_id);
            TPage _page = TPage.getByID(db, page_id);

            int pi = c.getInt(i_page_inflection_id);
            TPageInflection page_infl = 0 == pi ? null : TPageInflection.getByID(db, pi);

            if(null != _page)
                word = new TWikiTextWords(_id, _wiki_text, _page, page_infl);
        }
        if (c != null && !c.isClosed())
            c.close();
        return word;
    }

    
    /** Selects records from 'page' table (via 'wiki_text_words' and
     * 'page_inflection' tables) by wiki text with one condition:
     * only one word should be wikified in this wiki text.<br><br>
     *
     * This could be used in order to find translations for this article.<br><br>
     *
     * E.g. to find articles which contain the translation [[little]].
     * If there are three translations on this page:<br>
     *      (1) == translation == [[little]] [[bell]]<br>
     *      (2) == translation == [[little bell]]<br>
     *      (3) == translation == [[little]]<br>
     * then translation (1) is not suitable (two wiki words), but (2) and (3) are OK.
     *
     * @param  wikified text.
     * @return null if text is absent
     */
    public static TPage getPageForOneWordWikiText (SQLiteDatabase db,TWikiText wiki_text) {
        
        if(null == wiki_text) {
            System.err.println("Error (TWikiTextWords.getPageForOneWordWikiText()):: null argument wiki_text.");
            return null;
        }
        
        TWikiTextWords[] words = TWikiTextWords.getByWikiText(db, wiki_text);
        if(1 == words.length)
            return words[0].getPage();
        
        return null;
    }

    /** Gets wiki texts which have only one wikified word.
     *
     * @param page a wikified word in the sought wiki phrases
     * @return empty array if such texts are absent
     */
    public static TWikiText[] getOneWordWikiTextByPage (SQLiteDatabase db,TPage page) {

        if(null == page) {
            System.err.println("Error (TWikiTextWords.getOneWordWikiTextByPage()):: null argument page.");
            return NULL_TWIKITEXT_ARRAY;
        }

        // 1. 'page_inflection'
        // todo ...

        // 2. 'wiki_text_words'
        List<TWikiText> list_texts = null;
        
        TWikiTextWords[] words = TWikiTextWords.getByPage(db, page);
        for(TWikiTextWords w : words) {
            
            TWikiText wiki_text = w.getWikiText();
            
            if(null != wiki_text) {
                TWikiTextWords[] ww = TWikiTextWords.getByWikiText(db, wiki_text);
                if(1 == ww.length) {
                    if(null == list_texts)
                               list_texts = new ArrayList<TWikiText>();
                    list_texts.add(wiki_text);
                }
            }
        }
        if(null == list_texts)
            return NULL_TWIKITEXT_ARRAY;
        return ((TWikiText[])list_texts.toArray(NULL_TWIKITEXT_ARRAY));
    }

    
    /** Deletes row from the table 'wiki_text_words' by a value of ID.
     * DELETE FROM wiki_text_words WHERE id=1;
     * @param  id  unique ID in the table `wiki_text_words`
     */
    /*public static void delete (Connect connect,TWikiTextWords word) {

        if(null == word) {
            System.err.println("Error (wikt_parsed TWikiTextWords.delete()):: null argument word.");
            return;
        }
        
        StringBuilder str_sql = new StringBuilder();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("DELETE FROM wiki_text_words WHERE id=");
                str_sql.append(word.getID());
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
            //System.out.println("TWikiTextWords.delete()):: wiki_text='" + word.getWikiText().getText() + "'; id=" + word.getID());
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TWikiTextWords.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }*/
}
