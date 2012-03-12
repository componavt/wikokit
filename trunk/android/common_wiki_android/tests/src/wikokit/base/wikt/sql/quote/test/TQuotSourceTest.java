package wikokit.base.wikt.sql.quote.test;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.quote.TQuotSource;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TQuotSourceTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    SQLiteDatabase db;
    
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
        TLang.createFastMaps(db);
        TPOS.createFastMaps (db);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGet() {
        // zero sources
        String _name = "bla-bla-bla";
        TQuotSource s = TQuotSource.get (db, _name);
        assertNull(s);

        // there is quotation for this publisher
        _name = "Lib";
        s = TQuotSource.get(db, _name);
        assertNotNull(s);
        assertEquals(_name, s.getText());
    }

    public void testGetByID() {
        // zero
        int id = -1;
        TQuotSource s = TQuotSource.getByID(db, id);
        assertNull(s);
        
        // there is a publisher
        String _name = "Lib";
        s = TQuotSource.get(db, _name);
        assertNotNull(s);
        
        TQuotSource result_by_id = TQuotSource.getByID(db, s.getID());
        assertNotNull(result_by_id);
        assertEquals(_name, result_by_id.getText());
    }

}
