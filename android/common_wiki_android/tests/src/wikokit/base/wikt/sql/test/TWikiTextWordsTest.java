package wikokit.base.wikt.sql.test;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TWikiText;
import wikokit.base.wikt.sql.TWikiTextWords;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TWikiTextWordsTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    SQLiteDatabase db;
    TWikiText wiki_text;
    String page_title; // , inflected_form, str_wiki_text;
    TPage page;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        ruwikt_conn = new Connect(
                context,
                Connect.RU_DB_URL,
                Connect.RU_DB_ZIPFILE,
                Connect.RU_DB_ZIPFILE_SIZE_MB,
                Connect.RU_DB_FILE,
                Connect.RU_DB_FILE_SIZE_MB,
                Connect.DB_DIR
                );
        ruwikt_conn.openDatabase();
        db = ruwikt_conn.getDB();
        
        page_title = "весной";
        page = TPage.get(db, page_title);
        assertNotNull(page);
        
        // get wiki_text
        // select * from wiki_text where text LIKE "%aha%" LIMIT 33;
        String str_wiki_text = "baharda";
        wiki_text = TWikiText.get(db, str_wiki_text);
        assertNotNull(wiki_text);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGetByWikiText() {
        page_title = "pin one's hopes";
        page = TPage.get(db, page_title);
        assertNotNull(page);
        
        TWikiTextWords[] words = TWikiTextWords.getByPage(db, page);
        assertNotNull(words);
        assertTrue(words.length > 0);

        TWikiText wiki_text = words[0].getWikiText();
        assertNotNull(wiki_text);
        
        TWikiTextWords[] ww = TWikiTextWords.getByWikiText(db, wiki_text);
        assertNotNull(ww);
        assertEquals(ww.length, 1);
        
        // [[pin one's hopes|pin one's hopes (on)]]
        // [[page_title|wiki_text]]
    }

    public void testGetByWikiTextAndPageAndInflection() {
        fail("Not yet implemented");
    }

    // 
    public void testGetByPage() {
        TWikiTextWords[] words = TWikiTextWords.getByPage(db, page);
        assertNotNull(words);
        assertTrue(words.length > 0);
    }
    
    public void testGetByID() {
        TWikiTextWords[] words = TWikiTextWords.getByPage(db, page);
        assertNotNull(words);
        assertTrue(words.length > 0);
        
        // get by id
        TWikiTextWords word2 = TWikiTextWords.getByID(db, words[0].getID());
        assertNotNull(word2);
    }

    public void testGetOneByWikiText() {
        fail("Not yet implemented");
    }

    public void testGetPageForOneWordWikiText() {
        
        //[[word in normal form|inflected_form]]
        TPage one_wiki_word = TWikiTextWords.getPageForOneWordWikiText(db, wiki_text);
        assertNotNull(one_wiki_word);
        assertEquals(page_title, one_wiki_word.getPageTitle());
    }

    public void testGetOneWordWikiTextByPage() {
        
        TWikiTextWords[] words = TWikiTextWords.getByPage(db, page);
        assertNotNull(words);
        assertTrue(words.length > 0);

        /*
        //[[page_title|inflected_form]]
        // SELECT page_title,text FROM wiki_text,wiki_text_words,page WHERE text LIKE "%bro%" AND wiki_text.id=wiki_text_id AND page_id=page.id  LIMIT 33;
        // +-----------------------------------+-----------------------------------+
        | page_title                        | text                              |
        +-----------------------------------+-----------------------------------+
        | pin one's hopes                   | pin one's hopes (on)              |*/

        page_title = "pin one's hopes";
        page = TPage.get(db, page_title);
        assertNotNull(page);
        
        String str_wiki_text = "pin one's hopes (on)";
        wiki_text = TWikiText.get(db, str_wiki_text);
        assertNotNull(wiki_text);
        
        TWikiText[] array_wiki_texts = TWikiTextWords.getOneWordWikiTextByPage (db, page);
        assertNotNull(array_wiki_texts);
        assertEquals(1, array_wiki_texts.length);
        assertEquals(str_wiki_text, array_wiki_texts[0].getText());
    }

}
