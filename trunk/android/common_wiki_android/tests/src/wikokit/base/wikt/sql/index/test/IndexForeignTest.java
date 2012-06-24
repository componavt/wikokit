package wikokit.base.wikt.sql.index.test;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.index.IndexForeign;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class IndexForeignTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    SQLiteDatabase db;
    
    TPage page;
    String native_page_title;
    TLangPOS tlang_pos;
    TLangPOS[] array_lang_pos;
    
    protected void setUp() throws Exception {
        super.setUp();
        /*ruwikt_conn = new Connect(
                context,
                Connect.RU_DB_URL,
                Connect.RU_DB_ZIPFILE,
                Connect.RU_DB_ZIPFILE_SIZE_MB,
                Connect.RU_DB_FILE,
                Connect.RU_DB_FILE_SIZE_MB,
                Connect.DB_DIR
                );*/
        
        ruwikt_conn = new Connect(context, LanguageType.ru);
        
        ruwikt_conn.openDatabase();
        db = ruwikt_conn.getDB();
        TLang.createFastMaps(db);
        TPOS.createFastMaps (db);    // once upon a time: use Wiktionary parsed db
        
        native_page_title = "колокольчик";
        page = TPage.get(db, native_page_title);
        assertNotNull(page);
        
        // let's found ID:
        // array_lang_pos = TLangPOS.get(db, page);
        /* array_lang_pos = TLangPOS.getRecursive(db, page);
        
        assertNotNull(array_lang_pos);
        assertTrue   (array_lang_pos.length > 0);
        tlang_pos = array_lang_pos[0];
        int lang_pos_id = tlang_pos.getID();
        assertTrue(lang_pos_id > 0);*/
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testCount() {
        System.out.println("count_ru");
        int count;
        
        LanguageType foreign_lang = LanguageType.en;
        String foreign_word = "water";
        
        // 1 record (foreign_word, native_page_title) where native_page_title = null
        count = IndexForeign.count(db, foreign_word, null, foreign_lang);
        assertEquals(1, count); // there is only one entry "water" with non empty definition
        
        // 1 record (foreign_word, "вода")
        count = IndexForeign.count(db, foreign_word, "вода", foreign_lang);
        assertEquals(1, count); // there is one translation "water" in the entry "вода"
    }

    
    public void testCountNumberOfForeignPOS() {
        System.out.println("countNumberOfForeignPOS");
        int count = IndexForeign.countNumberOfForeignPOS (db, LanguageType.os);
        assertTrue(count > 100);
    }

    public void testCountTranslations() {
        
        System.out.println("countTranslations");
        int count = IndexForeign.countTranslations (db, LanguageType.os);
        assertTrue(count > 300);
    }

    public void testGetByPrefixForeign() {
        
        LanguageType native_lang, foreign_lang;
        IndexForeign[] index_foreign;
        
        native_lang = LanguageType.ru;
        foreign_lang = LanguageType.en;
        
        boolean b_meaning = false;
        boolean b_sem_rel = false;
        
        // 1 record
        int n_limit =1;
        
        String prefix_foreign_word = "water-%";
        index_foreign = IndexForeign.getByPrefixForeign(db,
                                        prefix_foreign_word, n_limit,
                                        native_lang, foreign_lang,
                                        b_meaning, b_sem_rel);
        assertEquals(1, index_foreign.length);
        
        // many records
        n_limit = -1;
        index_foreign = IndexForeign.getByPrefixForeign(db,
                                        prefix_foreign_word, n_limit,
                                        native_lang, foreign_lang,
                                        b_meaning, b_sem_rel);
        assertTrue(index_foreign.length > 10);
        
        //TPage native_page = index_foreign[0].getNativePage();
        //assertNotNull(native_page_title);
        //String s = native_page.getPageTitle();
        //assertEquals(s, native_page_title);
    }

}
