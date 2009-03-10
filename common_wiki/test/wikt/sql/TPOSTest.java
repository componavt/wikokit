
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;
import wikt.constant.POS;

public class TPOSTest {

    public Connect   ruwikt_parsed_conn;
    
    public TPOSTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInsert() {
        System.out.println("insert_ru");
        Connect conn = ruwikt_parsed_conn;

        TPOS.insert(conn, POS.noun);
        TPOS p = null;
        
        // blockhead test
        p = TPOS.get(conn, null);
        assertTrue(null == p);

        p = TPOS.get(conn, POS.noun);
        if(null != p) {
            TPOS.delete(conn, POS.noun);
        }

        //System.err.println("One warnings should be...");
        TPOS.insert(conn, POS.verb);
        p = TPOS.get(conn, POS.verb);

        assertTrue(p != null);

        int id = p.getID();
        assertTrue(id > 0);
    }
    
    @Test
    public void testRecreateTable() {
        System.out.println("recreateTable, fill table `part_of_speech`");
        TPOS.recreateTable(ruwikt_parsed_conn);
    }

}