
package wikt.sql.quote;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;

/**
 *
 * @author andrew
 */
public class TQuotAuthorTest {

    public Connect   ruwikt_parsed_conn;

    public TQuotAuthorTest() {
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
    public void testInsertOnlyName() {
        System.out.println("insertName_ru");
        Connect conn = ruwikt_parsed_conn;
        String _name = "test_Isaac Asimov";

        TQuotAuthor[] result_empty = TQuotAuthor.get(conn, _name);
        assertEquals(0, result_empty.length);

        // 0. insert only name
        TQuotAuthor result0 = TQuotAuthor.insertName(conn, _name);
        assertNotNull(result0);
        assertTrue(result0.getID() > 0);

        assertEquals(0, result0.getName().compareToIgnoreCase(_name));
        assertEquals(0, result0.getWikilink().length());

        // delete and check removing
        result0.delete(conn);
        result_empty = TQuotAuthor.get(conn, _name);
        assertEquals(0, result_empty.length);


        // 1. _wikilink is NULL
        String _wikilink1 = null;

        TQuotAuthor result1 = TQuotAuthor.insertNameWikilink(conn, _name, _wikilink1);
        assertNotNull(result1);
        assertTrue(result1.getID() > 0);

        assertEquals(0, result1.getName().compareToIgnoreCase(_name));
        assertEquals(0, result1.getWikilink().length());

        TQuotAuthor result1_get = TQuotAuthor.get(conn, _name, _wikilink1);
        assertEquals(0, result1.getName().compareToIgnoreCase( result1_get.getName() ));

        result1.delete(conn);
        result_empty = TQuotAuthor.get(conn, _name);
        assertEquals(0, result_empty.length);

        
        // 2. _wikilink == "", it is empty
        String _wikilink2 = "";

        TQuotAuthor result2 = TQuotAuthor.insertNameWikilink(conn, _name, _wikilink2);
        assertNotNull(result2);
        assertTrue(result2.getID() > 0);

        assertEquals(0, result2.getName().compareToIgnoreCase(_name));
        assertNotNull(result2.getWikilink());
        assertEquals(0, result2.getWikilink().length());

        result2.delete(conn);

        TQuotAuthor result2_again = TQuotAuthor.get(conn, _name, _wikilink1);
        assertNull(result2_again);
    }

    @Test
    public void testInsertNameWikilink() {
        System.out.println("insertNameWikilink_ru");

        Connect conn = ruwikt_parsed_conn;
        String _name1 = "test_Isaac1";
        String _wikilink1 = "";
        String _wikilink2 = "test_wikilink2";
        String _wikilink3 = "test_3_Азимов, Айзек";

        // insert 3 records
        TQuotAuthor result1 = TQuotAuthor.insertNameWikilink(conn, _name1, _wikilink1);
        assertNotNull(result1);
        assertTrue(result1.getID() > 0);

        TQuotAuthor result2 = TQuotAuthor.insertNameWikilink(conn, _name1, _wikilink2);
        TQuotAuthor result3 = TQuotAuthor.insertNameWikilink(conn, _name1, _wikilink3);

        TQuotAuthor[] array_result = TQuotAuthor.get(conn, _name1);
        assertEquals(3, array_result.length);

        // delete 3 records
        result1.delete(conn);
        result2.delete(conn);
        result3.delete(conn);

        // check deletion
        array_result = TQuotAuthor.get(conn, _name1);
        assertEquals(0, array_result.length);
    }

}