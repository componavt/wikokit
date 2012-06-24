package wikokit.base.wikt.sql.test;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TPage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TLangPOSTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    
    protected void setUp() throws Exception {
        super.setUp();
        ruwikt_conn = new Connect(context, LanguageType.ru);
        ruwikt_conn.openDatabase();
        TLang.createFastMaps(ruwikt_conn.getDB());
        TPOS.createFastMaps (ruwikt_conn.getDB());    // once upon a time: use Wiktionary parsed db
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGetLanguages() {
        SQLiteDatabase db = ruwikt_conn.getDB();
        
        String page_title = "rude"; // en, fr, ina - 3 languages 
        TPage page = TPage.get(db, page_title);
        assertNotNull(page);
        
        // test
        TLang[] languages = TLangPOS.getLanguages(db, page);
        assertTrue(null != languages);
        assertTrue(languages.length >= 3);
    }

    public void testGet() {
        System.out.println("get_ru");
        SQLiteDatabase db = ruwikt_conn.getDB();

        String page_title = "lead";
        TPage page = TPage.get(db, page_title);
        assertNotNull(page);

        // let's found ID:
        TLangPOS[] array_lang_pos = TLangPOS.get(db, page);
        assertNotNull(array_lang_pos);
        assertTrue   (array_lang_pos.length > 0);
        int lang_pos_id = array_lang_pos[0].getID();
        assertTrue   (lang_pos_id > 0);
    }
    
    public void testGetByID() {
        System.out.println("getByID_ru");
        SQLiteDatabase db = ruwikt_conn.getDB();

        String page_title = "lead";
        TPage page = TPage.get(db, page_title);
        assertNotNull(page);

        // let's found ID:
        TLangPOS[] array_lang_pos = TLangPOS.get(db, page);
        assertNotNull(array_lang_pos);
        assertTrue   (array_lang_pos.length > 0);
        int id = array_lang_pos[0].getID();
        assertTrue   (id > 0);
        
        TLangPOS pos_by_id = TLangPOS.getByID(db, id);
        assertNotNull(pos_by_id);
        assertEquals( pos_by_id.getPOS().toString(), array_lang_pos[0].getPOS().toString());
    }

}
