
package wikt.sql.quote;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;

public class TQuotSourceTest {

    public Connect   ruwikt_parsed_conn;

    public TQuotSourceTest() {
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
        ruwikt_parsed_conn.Close();
    }

    @Test
    public void testInsert() {
        System.out.println("insert_ru");

        Connect conn = ruwikt_parsed_conn;
        String _name1 = "test_Lib1";
        String _name3 = "test_Lib3";

        // insert 3 records
        TQuotSource result1 = TQuotSource.insert(conn, _name1);
        assertNotNull(result1);
        assertTrue(result1.getID() > 0);

        // let's try insert again
        TQuotSource result2 = TQuotSource.insert(conn, _name1);
        assertNull(result2);

        TQuotSource result3 = TQuotSource.insert(conn, _name3);
        TQuotSource get3 = TQuotSource.get(conn, _name3);
        assertNotNull(result1);
        assertEquals(get3.getID(), result3.getID());

        // delete 3 records
        result1.delete(conn);
        result3.delete(conn);

        // check deletion
        result1 = TQuotSource.get(conn, _name1);
        assertNull(result1);
    }

}