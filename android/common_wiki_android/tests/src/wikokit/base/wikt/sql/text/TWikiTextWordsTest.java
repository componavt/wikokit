package wikokit.base.wikt.sql.text;

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
        fail("Not yet implemented");
    }

    public void testGetByWikiTextAndPageAndInflection() {
        fail("Not yet implemented");
    }

    public void testGetByID() {
        fail("Not yet implemented");
    }

    public void testGetOneByWikiText() {
        fail("Not yet implemented");
    }

    public void testGetPageForOneWordWikiText() {
        
        TWikiTextWords word = TWikiTextWords.getByPage(db, page)
        assertNotNull(word);

        //[[word in normal form|inflected_form]]

        TPage one_wiki_word = TWikiTextWords.getPageForOneWordWikiText(db, wiki_text);
        assertNotNull(one_wiki_word);

        assertEquals(page_title, one_wiki_word.getPageTitle());
    }

    public void testGetOneWordWikiTextByPage() {
        fail("Not yet implemented");
    }

}
