package wikokit.base.wikt.sql.test;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TPage;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TMeaningTest extends TestCase {

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
        
        page_title = "lead";
        page = TPage.get(db, page_title);
        assertNotNull(page);
        
        // let's found ID:
        // array_lang_pos = TLangPOS.get(db, page);
        array_lang_pos = TLangPOS.getRecursive(db, page);
        
        assertNotNull(array_lang_pos);
        assertTrue   (array_lang_pos.length > 0);
        tlang_pos = array_lang_pos[0];
        int lang_pos_id = tlang_pos.getID();
        assertTrue(lang_pos_id > 0);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGet() {
        System.out.println("get_ru");
        
        TMeaning[] mm_copy1 = tlang_pos.getMeaning();
        assertNotNull(mm_copy1);
        assertTrue(mm_copy1.length > 0);
        int meaning_id1 = mm_copy1[0].getID();
        
        // start of test "get"
        TMeaning[] mm_copy2 = TMeaning.get(db, tlang_pos);
        assertNotNull(mm_copy2);
        assertTrue(mm_copy2.length > 0);
        assertEquals(mm_copy2.length, mm_copy2.length);
        int meaning_id2 = mm_copy2[0].getID();
        assertEquals(meaning_id1, meaning_id2);
    }

    public void testGetByID() {
        System.out.println("getByID_ru");
        
        TMeaning[] mm_copy1 = tlang_pos.getMeaning();
        assertNotNull(mm_copy1);
        assertTrue(mm_copy1.length > 0);
        int meaning_id1 = mm_copy1[0].getID();
        
        // start of test "getByID"
        TMeaning m = TMeaning.getByID(db, meaning_id1);
        assertNotNull(m);
        TLangPOS tlp = m.getLangPOS(db);
        assertNotNull(tlp);
                                            TLangPOS mm_tlp = mm_copy1[0].getLangPOS(db);
                                            assertNotNull(mm_tlp);
                                            assertNotNull(mm_tlp.getPage());
        assertEquals(tlp.getPage().getID(), mm_tlp.getPage().getID());
    }

}
