
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

public class TTranslationTest {

    public Connect   ruwikt_parsed_conn;

    public TTranslationTest() {
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

        TLang.recreateTable(ruwikt_parsed_conn);    // once upon a time: create Wiktionary parsed db
        TLang.createFastMaps(ruwikt_parsed_conn);   // once upon a time: use Wiktionary parsed db

        TPOS.recreateTable(ruwikt_parsed_conn);     // once upon a time: create Wiktionary parsed db
        TPOS.createFastMaps(ruwikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }

    /**
     * Test of insert method, of class TTranslation.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        Connect connect = null;
        TLangPOS lang_pos = null;
        String meaning_summary = "";
        TMeaning meaning = null;
        TTranslation expResult = null;
        TTranslation result = TTranslation.insert(connect, lang_pos, meaning_summary, meaning);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getByID method, of class TTranslation.
     */
    @Test
    public void testGetByID() {
        System.out.println("getByID");
        Connect connect = null;
        int id = 0;
        TTranslation expResult = null;
        TTranslation result = TTranslation.getByID(connect, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class TTranslation.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Connect connect = null;
        TTranslation trans = null;
        TTranslation.delete(connect, trans);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}