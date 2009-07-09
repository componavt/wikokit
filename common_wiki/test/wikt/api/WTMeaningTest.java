
package wikt.api;

import wikt.sql.*;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikipedia.util.StringUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class WTMeaningTest {

    public Connect   ruwikt_parsed_conn;
    
    public WTMeaningTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    // Attention: these tests are valid only with created "Wiktionary parsed database".
    
    @Before
    public void setUp() {
        System.out.println("Attention: these tests are valid only with created 'Wiktionary parsed database'.");
        ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,
                                LanguageType.ru);

        TLang.createFastMaps(ruwikt_parsed_conn);   // once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps(ruwikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }

    /**
     * Test of getDefinitionsByPage method, of class WTMeaning.
     */
    @Test
    public void testGetDefinitionsByPageLang() {
        System.out.println("getDefinitionsByPageLang");

        Connect connect = ruwikt_parsed_conn;
        String word = "car";

        // English: car
        //# колесное транспортное средство
        //# [[автомобиль]]
        //# ''амер.'' легковой [[автомобиль]]
        //# [[кабина]] [[лифт]]а
        //# [[вагон]]

        String[] definitions = WTMeaning.getDefinitionsByPageLang(connect, word, LanguageType.en);
        assertTrue(definitions.length >= 5);
        assertTrue(StringUtil.containsIgnoreCase(definitions, "автомобиль"));
    }

}