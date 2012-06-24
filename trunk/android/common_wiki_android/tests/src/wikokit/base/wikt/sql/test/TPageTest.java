package wikokit.base.wikt.sql.test;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TLang;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TPageTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    SQLiteDatabase db;
    
    protected void setUp() throws Exception {
        super.setUp();
        ruwikt_conn = new Connect(context, LanguageType.ru);
        ruwikt_conn.openDatabase();
        db = ruwikt_conn.getDB();
        TLang.createFastMaps(db);
        TPOS.createFastMaps (db);    // once upon a time: use Wiktionary parsed db
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGetByID() {
        System.out.println("getByID_ru");
        String page_title;
        
        SQLiteDatabase db = ruwikt_conn.getDB();
        
        //boolean is_in_wiktionary = true;
        //String redirect_target = null;
        
        page_title = "lead";
        TPage p = null;
        p = TPage.get(db, page_title);
        assertTrue(p != null);
        assertTrue(p.getID() > 0);
        
        TPage p2 = TPage.getByID(db, p.getID());
        assertTrue(p2 != null);
        assertEquals(p.getPageTitle(), p2.getPageTitle());
    }

    public void testGetByPrefix() {
        System.out.println("getByPrefix");
        int limit;
        String prefix;
        SQLiteDatabase db = ruwikt_conn.getDB();
        
        prefix = "airplane%";
        /*int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;*/
        
        TPage p[] = null;
        boolean b_skip_redirects = true;
        boolean b_meaning = false;
        boolean b_sem_rel = false;
        TLang   source_lang[] = new TLang [0];
        
        limit = 0;
        p = TPage.getByPrefix(db, prefix, limit, b_skip_redirects,
                source_lang,
                b_meaning, b_sem_rel);
        assertEquals(p.length, 0);

        limit = 1;
        p = TPage.getByPrefix(db, prefix, limit, b_skip_redirects,
                source_lang,
                b_meaning, b_sem_rel);
        assertEquals(p.length, 1);

     // let's test internal call: tp.lang_pos = TLangPOS.getRecursive(db, tp);
        TLangPOS[] array_tlang_pos = p[0].getLangPOS();
        assertNotNull(array_tlang_pos);
        assertTrue(array_tlang_pos.length > 0); // entry "airplane" contains something
        TMeaning[] array_tmeaning = array_tlang_pos[0].getMeaning();
        assertNotNull(array_tmeaning);
        assertTrue(array_tmeaning.length > 0);
        
        limit = -1;
        p = TPage.getByPrefix(db, prefix, limit, b_skip_redirects,
                source_lang,
                b_meaning, b_sem_rel);
        assertTrue(p.length > 1);// airplane and "airplane ticket"
    }
    
    public void testGetPageTitles() {
        System.out.println("getPageTitles");
        
        int limit;
        String prefix;
        SQLiteDatabase db = ruwikt_conn.getDB();
        
        prefix = "airplane%";
        
        TPage p[] = null;
        boolean b_skip_redirects = true;
        boolean b_meaning = false;
        boolean b_sem_rel = false;
        TLang   source_lang[] = new TLang [0];
        
        limit = -1;
        p = TPage.getByPrefix(db, prefix, limit, b_skip_redirects,
                source_lang,
                b_meaning, b_sem_rel);
        assertTrue(p.length > 1);// airplane and "airplane ticket"
        
        String[] page_titles = TPage.getPageTitles(p);
        assertTrue(page_titles.length > 1);
        assertEquals(page_titles.length, p.length);
    }

}
