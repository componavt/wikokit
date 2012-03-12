package wikokit.base.wikt.sql.quote.test;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.quote.TQuotAuthor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TQuotAuthorTest extends TestCase {

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
        TPOS.createFastMaps (db);    // once upon a time: use Wiktionary parsed db
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGetFirst() {
        // zero authors
        String _name = "bla-bla-bla";
        TQuotAuthor result_empty = TQuotAuthor.getFirst(db, _name);
        assertNull(result_empty);
        
        // many or 1 name???
        _name = "Voltaire";
        TQuotAuthor result = TQuotAuthor.getFirst(db, _name);
        
        assertNotNull(result);
        assertTrue(result.getID() > 0);
    }

    public void testGetArray() {
        
        // zero authors
        String _name = "bla-bla-bla";
        TQuotAuthor[] result_empty = TQuotAuthor.get(db, _name);
        assertEquals(0, result_empty.length);

        // many or 1 name???
        _name = "Voltaire";
        TQuotAuthor[] result = TQuotAuthor.get(db, _name);
        
        assertNotNull(result);
        assertTrue(result.length > 0);
        assertTrue(result[0].getID() > 0);
    }

    // SELECT id,wikilink FROM quot_author WHERE name="Mahatma Gandhi" AND wikilink="" LIMIT 1;
    public void testGetOne() {
        
        // zero authors
        String _name = "bla-bla-bla";
        TQuotAuthor result_empty = TQuotAuthor.get(db, _name, null);
        assertEquals(null, result_empty);

        // there is quotation for this name
        _name = "Mahatma Gandhi";
        TQuotAuthor result = TQuotAuthor.get(db, _name, null);
        assertNotNull(result);
        assertEquals(_name, result.getName());
    }

    public void testGetByID() {
        // zero
        int id = -1;
        TQuotAuthor result = TQuotAuthor.getByID(db, id);
        assertNull(result);
        
        // there are quotations
        String _name = "Voltaire";
        result = TQuotAuthor.get(db, _name, null);
        assertNotNull(result);
        
        TQuotAuthor result_by_id = TQuotAuthor.getByID(db, result.getID());
        assertNotNull(result_by_id);
        assertEquals(_name, result_by_id.getName());
    }

}
