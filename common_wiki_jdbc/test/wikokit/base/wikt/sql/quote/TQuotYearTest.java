
package wikokit.base.wikt.sql.quote;

import wikokit.base.wikt.sql.quote.TQuotYear;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;

public class TQuotYearTest {

    public Connect   ruwikt_parsed_conn;
    
    public TQuotYearTest() {
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
    public void testInsert_ru_one_year() {
        System.out.println("insert_ru");
        int from;
        String page_title = "the testing test";
        TQuotYear result, get;
        Connect conn = ruwikt_parsed_conn;

        // negative - failed
        from = -1;

        result = TQuotYear.insert(conn, from);
        assertNull(result);

        // normal: 91956 year
        from = 91956;
        result = TQuotYear.insert(conn, from);
        assertNotNull(result);

        get = TQuotYear.get(conn, from, page_title);
        assertNotNull(get);

        // delete record
        result.delete(conn);
        get = TQuotYear.get(conn, from, page_title);
        assertNull(get);
    }

    @Test
    public void testInsert_ru_range_of_years() {
        System.out.println("insert_ru_range_of_years");

        int from, to;
        TQuotYear result, get;
        Connect conn = ruwikt_parsed_conn;
        String page_title = "the testing test";

        // negative - failed
        from = 1881;
        to   = 1880;

        result = TQuotYear.insert(conn, from, to);
        assertNull(result);

        // normal range: 91880-91881 year
        from = 91880;
        to   = 91881;
        result = TQuotYear.insert(conn, from, to);
        assertNotNull(result);

        get = TQuotYear.get(conn, from, page_title); // (from, from) is absent
        assertNull(get);

        get = TQuotYear.get(conn, from, to, page_title);
        assertNotNull(get);

        // delete record
        result.delete(conn);
        get = TQuotYear.get(conn, from, page_title);
        assertNull(get);
    }

}