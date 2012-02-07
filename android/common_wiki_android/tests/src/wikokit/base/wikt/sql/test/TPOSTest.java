package wikokit.base.wikt.sql.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.*;
import wikokit.base.wikt.constant.POS;

public class TPOSTest extends TestCase {

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
        System.out.println("getID");
        SQLiteDatabase db = ruwikt_conn.getDB();

        // once upon a time: create Wiktionary parsed db
        // skip for SQLite: TPOS.recreateTable(ruwikt_parsed_conn);

        // once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps(db);

        // and every usual day
        int noun_id = TPOS.getIDFast(POS.noun);

        TPOS tpos = TPOS.get(db, POS.noun);
        assertEquals(tpos.getID(), noun_id);
    }

}
