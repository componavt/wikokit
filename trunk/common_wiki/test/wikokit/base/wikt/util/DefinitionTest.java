
package wikokit.base.wikt.util;

import wikokit.base.wikt.util.Definition;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DefinitionTest {

    public DefinitionTest() {
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
     * Test of stripNumberSign method, of class Definition.
     */
    @Test
    public void testStripNumberSign_en() {
        System.out.println("stripNumberSign_en");
        String text, expResult, result, page_title;

        page_title = "sithence";
        text =   "# {{archaic}} Seeing that, [[since]].\n" +
                        "#*'''1603''', John Florio, translating Michel de Montaigne, ''Essays'', Folio Society 2006, vol. 1 p. 186-7:\n" +
                        "#*:'''Sithence''' it must continue so short a time, and begun so late [...], there was no time to be lost.";
        expResult = "{{archaic}} Seeing that, [[since]].\n" +
                        "#*'''1603''', John Florio, translating Michel de Montaigne, ''Essays'', Folio Society 2006, vol. 1 p. 186-7:\n" +
                        "#*:'''Sithence''' it must continue so short a time, and begun so late [...], there was no time to be lost.";

        result = Definition.stripNumberSign(page_title, text);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    @Test
    public void testStripNumberSign_ru() {
        System.out.println("stripNumberSign_ru");
        String text, expResult, result, page_title;
        
        page_title = "самолёт";

        text      = "# летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом {{пример|Самолёт-истребитель.}} {{пример|Военный cамолёт.}} {{пример|Эскадрилья самолётов.}}";
        expResult =   "летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом {{пример|Самолёт-истребитель.}} {{пример|Военный cамолёт.}} {{пример|Эскадрилья самолётов.}}";

        result = Definition.stripNumberSign(page_title, text);
        assertTrue(expResult.equalsIgnoreCase(result));

        // test with repetition # #; strip end spaces.
        text      = "# word1 # word2  ";
        expResult = "word1 # word2";
        result = Definition.stripNumberSign(page_title, text);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    // do not strip number sign in the redirect internal link, e.g.:
    // [[#Русский|сервер]]
    @Test
    public void testDontStripRedirect_ru() {
        System.out.println("stripNumberSign_ru");
        String text, expResult, result, page_title;

        page_title = "test_word";

        text      = "[[#Русский|сервер]]";
        expResult = "[[#Русский|сервер]]";

        result = Definition.stripNumberSign(page_title, text);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

}