
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

public class TWikiTextWordsTest {

    public Connect   ruwikt_parsed_conn;
    
    public TWikiTextWordsTest() {
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
    public void testGetID() {
        System.out.println("getID");
        TWikiTextWords instance = null;
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class TWikiTextWords.
     */
    @Test
    public void testInsert() {
        System.out.println("insert_ru");
        
        TWikiText wiki_text = null;
        TPageInflection page_inflection = null;

        String text = "test_TWikiText_insert_ru";
        Connect conn = ruwikt_parsed_conn;
        
        TWikiTextWords expResult = null;
        TWikiTextWords result = TWikiTextWords.insert(conn, wiki_text, page_inflection);
        assertEquals(expResult, result);

        /*
        // insert page, get wiki_text.id
        TWikiText p = null, p2=null;
        p = TWikiText.get(conn, text);
        if(null != p) {
            TWikiText.delete(conn, p);
        }
        // p == p2
        p = TWikiText.insert(conn, text);
        p2 = TWikiText.get(conn, text);

        assertTrue(p != null);
        assertTrue(p2 != null);
        assertTrue(p.getID() > 0);
        assertEquals(p.getID(), p2.getID());

        TWikiText.delete(conn, p);              // delete temporary DB record

        p = TWikiText.getByID(conn, p.getID()); // check deletion
        assertTrue(p == null);
        p2 = TWikiText.getByID(conn, p2.getID());
        assertTrue(p2 == null);
         */
    }

    /**
     * Test of getByWikiText method, of class TWikiTextWords.
     */
    @Test
    public void testGetByWikiText() {
        System.out.println("getByWikiText");
        Connect connect = null;
        TWikiText wiki_text = null;
        TWikiTextWords[] expResult = null;
        TWikiTextWords[] result = TWikiTextWords.getByWikiText(connect, wiki_text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getByID method, of class TWikiTextWords.
     */
    @Test
    public void testGetByID() {
        System.out.println("getByID");
        Connect connect = null;
        int id = 0;
        TWikiTextWords expResult = null;
        TWikiTextWords result = TWikiTextWords.getByID(connect, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}