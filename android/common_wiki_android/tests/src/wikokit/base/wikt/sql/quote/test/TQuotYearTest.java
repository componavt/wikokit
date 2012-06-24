package wikokit.base.wikt.sql.quote.test;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.quote.TQuotYear;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TQuotYearTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    SQLiteDatabase db;
    
    protected void setUp() throws Exception {
        super.setUp();
        ruwikt_conn = new Connect(context, LanguageType.ru);
        ruwikt_conn.openDatabase();
        db = ruwikt_conn.getDB();
        TLang.createFastMaps(db);
        TPOS.createFastMaps (db);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGetByID() {
        String page_title;
        TQuotYear y, y_by_id;
        int i_from;
        
        // роза: И. А. Гончаров, «Пепиньерка», 1842 г.
        page_title = "роза";
        i_from = 1824;
        y = TQuotYear.get(db, i_from, page_title);
        assertNotNull(y);
         
        y_by_id = TQuotYear.getByID(db, y.getID());
        assertNotNull(y_by_id);
        assertEquals(y.getFrom(), y_by_id.getFrom());
    }

    public void testGetOneYear() {
        
        String page_title;
        TQuotYear y;
        int i_from;
        
        // zero sources, negative year - failed
        page_title = "роза";
        i_from = -1;
        y = TQuotYear.get(db, i_from, page_title);
        assertNull(y);
        
        // normal: "роза" 1824 year: 
        // роза: И. А. Гончаров, «Пепиньерка», 1842 г.
        i_from = 1824;
        y = TQuotYear.get(db, i_from, page_title);
        assertNotNull(y);
    }
    
    public void testGetYearRange() {
        
        int from, to;
        String page_title;
        TQuotYear y;
        
        // zero sources, negative year - failed
        page_title = "роза";
        from = -1;
        to = 0;
        y = TQuotYear.get(db, from, to, page_title);
        assertNull(y);
        
        // normal: "варить" 1970-1977: 
        // варить: Юрий Рытхэу, «Числа Какота», 1970-1977
        page_title = "варить";
        from = 1970;
        to = 1977;
        y = TQuotYear.get(db, from, to, page_title);
        assertNotNull(y);
    }

    
}
