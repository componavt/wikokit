
package wikt.multi.ru;

import wikt.util.Definition;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class WQuoteRuTest {

    public WQuoteRuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getDefinitionBeforeFirstQuote method, of class WQuoteRu.
     */
    @Test
    public void testGetDefinitionBeforeFirstQuote() {
        System.out.println("getDefinitionBeforeFirstQuote");
        String text, expResult, result, page_title;

        page_title = "самолёт";
        text =      "# летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом {{пример|Самолёт-истребитель.}} {{пример|Военный cамолёт.}} {{пример|Эскадрилья самолётов.}}";
        expResult =   "летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом";

        text = Definition.stripNumberSign(page_title, text);
        result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        assertEquals(expResult, result);
    }

    // test definition without quote, e.g. " definition "
    @Test
    public void testGetDefinitionBeforeFirstQuote_without_example() {
        System.out.println("getDefinitionBeforeFirstQuote_without_example");
        String text, expResult, result, page_title;

        page_title = "самолёт";
        text =      "# летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом ";
        expResult =   "летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом";

        text = Definition.stripNumberSign(page_title, text);
        result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        assertEquals(expResult, result);
    }

}