
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

        TPOS p = null;
        System.err.println("One warnings may be...");
        p = TPOS.get(conn, POS.noun);
        if(null == p) {
            TPOS.insert(conn, POS.noun);
        }
        
        // blockhead test
        p = TPOS.get(conn, null);
        assertTrue(null == p);

        p = TPOS.get(conn, POS.noun);
        if(null != p) {
            TPOS.delete(conn, POS.noun);
        }
        
        int id = p.getID();
        assertTrue(id > 0);
    }
    
    @Test
    public void testRecreateTable() {
        System.out.println("recreateTable, fill table `part_of_speech`");
        TPOS.recreateTable(ruwikt_parsed_conn);
    }

}