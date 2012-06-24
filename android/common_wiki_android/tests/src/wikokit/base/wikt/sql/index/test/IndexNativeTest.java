package wikokit.base.wikt.sql.index.test;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.index.IndexNative;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class IndexNativeTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    SQLiteDatabase db;
    
    TPage page;
    String page_title;
    TLangPOS tlang_pos;
    TLangPOS[] array_lang_pos;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        ruwikt_conn = new Connect(context, LanguageType.ru);
        ruwikt_conn.openDatabase();
        db = ruwikt_conn.getDB();
        TLang.createFastMaps(db);
        TPOS.createFastMaps (db);    // once upon a time: use Wiktionary parsed db
        
        page_title = "колокольчик";
        page = TPage.get(db, page_title);
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

    public void testCountNumberPOSWithDefinition() {
        int n_native_POS = IndexNative.countNumberPOSWithDefinition(db);
        assertTrue(n_native_POS > 50000); // number of Russian words in Russian Wiktionary
    }

    public void testGet() {
        
        IndexNative i = IndexNative.get(db, page_title);
        
        assertNotNull(i);
        assertTrue(i.getID() > 0);
        assertTrue(i.hasRelation());

        String s = i.getPageTitle();
        assertEquals(s, page_title);
    }

}
