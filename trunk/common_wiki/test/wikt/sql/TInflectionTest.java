
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

public class TInflectionTest {

    public Connect   ruwikt_parsed_conn;
    
    public TInflectionTest() {
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
        ruwikt_parsed_conn.Close();
    }

   
    @Test
    public void testInsert() {
        System.out.println("insert_ru");
        
        int freq = 1;
        String inflected_form = "test_TInflection_insert_ru";
        Connect conn = ruwikt_parsed_conn;
        
        // insert inflection, get inflection.id
        TInflection p = null, p2=null, p3=null;
        p = TInflection.get(conn, inflected_form);
        if(null != p) {
            TInflection.delete(conn, p);
        }

        // p == p2
        p = TInflection.insert(conn, inflected_form, freq);
        p2 = TInflection.get(conn, inflected_form);
        p3 = TInflection.getByID(conn, p.getID());
        
        assertTrue(p != null);
        assertTrue(p2 != null);
        assertTrue(p3 != null);
        assertTrue(p.getID() > 0);
        assertEquals(p.getID(), p2.getID());
        assertEquals(p.getInflectedForm(), p3.getInflectedForm());
        assertEquals(freq, p.getFreq());

        TInflection.delete(conn, p);            // delete temporary DB record

        p = TInflection.getByID(conn, p.getID()); // check deletion
        assertTrue(p == null);
        p2 = TInflection.getByID(conn, p2.getID());
        assertTrue(p2 == null);
    }
}