package wikokit.base.wikt.sql.test;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TWikiText;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TWikiTextTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    
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
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGetID() {
        System.out.println("getID_ru");
        SQLiteDatabase db = ruwikt_conn.getDB();
        
        String text = "pronouncing dictionary";
        
     // insert page, get wiki_text.id
        TWikiText p = null, p2 = null;
        p = TWikiText.get(db, text);
        assertNotNull(p);
        assertTrue(p.getID() > 0);
        
        p2 = TWikiText.getByID(db, p.getID());
        assertNotNull(p2);
        
        assertEquals(p.getID(), p2.getID());
        assertEquals(p.getText(), p2.getText());        
    }
}
