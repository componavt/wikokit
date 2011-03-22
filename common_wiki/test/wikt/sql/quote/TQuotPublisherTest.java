
package wikt.sql.quote;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;

public class TQuotPublisherTest {

    public Connect   ruwikt_parsed_conn;

    public TQuotPublisherTest() {
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
        String _name1 = "test1_Тихоокеанская звезда";
        String _name3 = "test3_Химия и жизнь";

        // insert 3 records
        TQuotPublisher result1 = TQuotPublisher.insert(conn, _name1);
        assertNotNull(result1);
        assertTrue(result1.getID() > 0);

        // let's try insert again
        TQuotPublisher result2 = TQuotPublisher.insert(conn, _name1);
        assertNull(result2);

        TQuotPublisher result3 = TQuotPublisher.insert(conn, _name3);
        TQuotPublisher get3 = TQuotPublisher.get(conn, _name3);
        assertNotNull(result1);
        assertEquals(get3.getID(), result3.getID());

        // delete 3 records
        result1.delete(conn);
        result3.delete(conn);

        // check deletion
        result1 = TQuotPublisher.get(conn, _name1);
        assertNull(result1);
    }

}