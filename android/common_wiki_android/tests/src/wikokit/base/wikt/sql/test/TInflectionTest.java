package wikokit.base.wikt.sql.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TInflection;

import junit.framework.TestCase;

public class TInflectionTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    
    protected void setUp() throws Exception {
        super.setUp();
        ruwikt_conn = new Connect(context, LanguageType.ru);
        ruwikt_conn.openDatabase();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGet() {
        System.out.println("get_ru");
        SQLiteDatabase db = ruwikt_conn.getDB();
        
        String inflected_form = "test_TInflection_insert_ru";
        
        // let's not find unknown inflection
        TInflection p = TInflection.get(db, inflected_form);
        assertNull(p);
        
        // let's find existing inflection
        inflected_form = "bonvolu"; // in Russian Wiktionary
        TInflection p2 = TInflection.get(db, inflected_form);
        assertNotNull(p2);
        
        int freq = p2.getFreq();
        assertTrue(freq > 0);
    }

    public void testGetByID() {
        SQLiteDatabase db = ruwikt_conn.getDB();
        
        // let's find existing inflection
        String inflected_form = "bonvolu"; // in Russian Wiktionary
        TInflection p = TInflection.get(db, inflected_form);
        assertNotNull(p);
        
        TInflection p2 = TInflection.getByID(db, p.getID());
        assertNotNull(p2);
        assertEquals(p.getID(), p2.getID());
    }

}
