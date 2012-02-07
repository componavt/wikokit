
package wikokit.base.wikt.sql.quote;

import wikokit.base.wikt.sql.quote.TQuotTranslation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;

public class TQuotTranslationTest {

    public Connect   ruwikt_parsed_conn;
    
    public TQuotTranslationTest() {
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
        Connect connect = ruwikt_parsed_conn;

        int quote_id = 777777;
        String text = "test_test_test";

        TQuotTranslation result = TQuotTranslation.insert(connect, quote_id, text);
        assertNotNull(result);

        TQuotTranslation get = TQuotTranslation.getByID(connect, quote_id);
        assertNotNull( get );
        assertEquals(0, result.getText().compareTo( get.getText() ));

        result.delete(connect);
    }

    

}