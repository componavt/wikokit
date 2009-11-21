
package wikt.sql.index;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class IndexForeignTest {

    public Connect   ruwikt_parsed_conn;
    
    public IndexForeignTest() {
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
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of generateTables method, of class IndexForeign.
     */
    @Test
    public void testGenerateTables() {
        System.out.println("generateTables");

        IndexForeign.generateTables(ruwikt_parsed_conn);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}