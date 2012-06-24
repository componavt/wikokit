package wikokit.base.wikt.sql.test;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TInflection;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TPageInflection;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TPageInflectionTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    SQLiteDatabase db;
    TPage page;
    TInflection infl;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        ruwikt_conn = new Connect(context, LanguageType.ru);
        ruwikt_conn.openDatabase();
        db = ruwikt_conn.getDB();
        
        // let's take existing inflection from the parsed Russian Wiktionary
        // HOWTO select inflected form and page title for the positive test:
        // select * from page_inflection LIMIT 33;
        // select * from page_inflection, inflection WHERE inflection.id = inflection_id AND inflected_form LIKE "bo%" LIMIT 33;
        /*
        inflected_form | id     | page_title
        ---------------+--------+-----------
        bowels         | 331605 | bowel
        bov            |  70132 | bovo
        bonguste       |  69427 | bongusta
        bonvolu        |  69508 | bonvoli
        boys           |  70185 | boy
        bonds          |  69392 | bond */
        
        // bonvolu -> bonvoli
        String inflected_form = "bonvolu"; // in Russian Wiktionary
        infl = TInflection.get(db, inflected_form);
        assertNotNull(infl);
        
        String page_title = "bonvoli";
        page = TPage.get(db, page_title);
        assertNotNull(page);
        
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGet() {
        System.out.println("get_ru");
        
        // test get by page and inflection
        TPageInflection page_infl = TPageInflection.get(db, page, infl);
        assertNotNull(page_infl);
    }

    public void testGetByID() {
        
        // test get by page and inflection
        TPageInflection page_infl, page_infl2;
        int term_freq = 1;
        
        page_infl = TPageInflection.get(db, page, infl);
        assertNotNull(page_infl);
        
        page_infl2 = TPageInflection.getByID(db, page_infl.getID());
        assertNotNull(page_infl2);
        
        TPage p = page_infl2.getPage();
        assertNotNull(p);
        
        assertEquals(page.getID(), p.getID());
    }

}
