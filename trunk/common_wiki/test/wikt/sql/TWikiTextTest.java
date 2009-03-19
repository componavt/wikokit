
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

public class TWikiTextTest {

    public Connect   ruwikt_parsed_conn;
    
    public TWikiTextTest() {
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

        String text = "test_TWikiText_insert_ru";
        Connect conn = ruwikt_parsed_conn;

        // insert page, get wiki_text.id
        TWikiText p = null, p2=null, p3=null;
        p = TWikiText.get(conn, text);
        if(null != p) {
            TWikiText.delete(conn, p);
        }
        // p == p2
        p = TWikiText.insert(conn, text);
        p2 = TWikiText.get(conn, text);
        p3 = TWikiText.getByID(conn, p.getID());

        assertTrue(p != null);
        assertTrue(p2 != null);
        assertTrue(p3 != null);
        assertTrue(p.getID() > 0);
        assertEquals(p.getID(), p2.getID());
        assertEquals(p.getText(), p3.getText());

        TWikiText.delete(conn, p);              // delete temporary DB record

        p = TWikiText.getByID(conn, p.getID()); // check deletion
        assertTrue(p == null);
        p2 = TWikiText.getByID(conn, p2.getID());
        assertTrue(p2 == null);
    }

}