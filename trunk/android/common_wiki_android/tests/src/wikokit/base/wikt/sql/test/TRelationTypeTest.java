package wikokit.base.wikt.sql.test;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TRelationType;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TRelationTypeTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    SQLiteDatabase db;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        ruwikt_conn = new Connect(context, LanguageType.ru);
        ruwikt_conn.openDatabase();
        db = ruwikt_conn.getDB();
        
        // once upon a time: use Wiktionary parsed db
        //TLang.createFastMaps(db);
        //TPOS.createFastMaps (db);
        TRelationType.createFastMaps(db);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGet() {

        int synonymy_id = TRelationType.getIDFast(Relation.synonymy);

        TRelationType rel_type = TRelationType.get(db, Relation.synonymy);
        assertEquals(rel_type.getID(), synonymy_id);
    }

}
