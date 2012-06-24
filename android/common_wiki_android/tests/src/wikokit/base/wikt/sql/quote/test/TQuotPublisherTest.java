package wikokit.base.wikt.sql.quote.test;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.quote.TQuotAuthor;
import wikokit.base.wikt.sql.quote.TQuotPublisher;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TQuotPublisherTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    SQLiteDatabase db;
    
    protected void setUp() throws Exception {
        super.setUp();
        ruwikt_conn = new Connect(context, LanguageType.ru);
        ruwikt_conn.openDatabase();
        db = ruwikt_conn.getDB();
        TLang.createFastMaps(db);
        TPOS.createFastMaps (db);    // once upon a time: use Wiktionary parsed db
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGet() {
        
        // zero publishers
        String _name = "bla-bla-bla";
        TQuotPublisher p = TQuotPublisher.get (db, _name);
        assertNull(p);

        // there is quotation for this publisher
        _name = "Lenta.ru";
        p = TQuotPublisher.get(db, _name);
        assertNotNull(p);
        assertEquals(_name, p.getText());
    }

    public void testGetByID() {
        // zero
        int id = -1;
        TQuotPublisher p = TQuotPublisher.getByID(db, id);
        assertNull(p);
        
        // there is a publisher
        String _name = "Lenta.ru";
        p = TQuotPublisher.get(db, _name);
        assertNotNull(p);
        
        TQuotPublisher result_by_id = TQuotPublisher.getByID(db, p.getID());
        assertNotNull(result_by_id);
        assertEquals(_name, result_by_id.getText());
    }

}
