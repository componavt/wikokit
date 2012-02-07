
package wikokit.base.wikt.util;

//import wikt.util.WikiWord;
import wikokit.base.wikt.util.WikiWord;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class WikiWordTest {

    public WikiWordTest() {
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

    @Test
    public void testParseDoubleBrackets_en() {
        System.out.println("parseDoubleBrackets_en");
        StringBuffer expResult, result, wiki_text;
        String       page_title;

        // test 1
        page_title = "sweet";
        wiki_text = new StringBuffer("Having a [[pleasant]] taste, especially one relating to the basic taste sensation induced by [[sugar]].");
        expResult = new StringBuffer("Having a pleasant taste, especially one relating to the basic taste sensation induced by sugar.");

        result = WikiWord.parseDoubleBrackets(page_title, wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );

        // test 2
        // (with vertical pipe) remove brackets
        page_title = "god";
        wiki_text = new StringBuffer("A [[supernatural]], typically [[immortal]] being with [[superior]] [[power]]s.");
        expResult = new StringBuffer("A supernatural, typically immortal being with superior powers.");

        result = WikiWord.parseDoubleBrackets(page_title, wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }

    @Test
    public void testParseDoubleBrackets_ru() {
        System.out.println("parseDoubleBrackets_ru");
        StringBuffer expResult, result, wiki_text;
        String       page_title;

        // test 1
        page_title = "самолёт";
        wiki_text = new StringBuffer ("летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом");
        expResult = new StringBuffer("летательный аппарат тяжелее воздуха с жёстким крылом и собственным мотором");

        result = WikiWord.parseDoubleBrackets(page_title, wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );

        // test 2
        // (with vertical pipe) remove brackets
        page_title = "гликолиз";
        wiki_text = new StringBuffer("[[ферментативный]] [[процесс]] [[последовательный|последовательного]] [[расщепление|расщепления]] [[глюкоза|глюкозы]] в [[клетка]]х, [[сопровождающийся]] [[синтез]]ом [[АТФ]]");
        expResult = new StringBuffer ("ферментативный процесс последовательного расщепления глюкозы в клетках, сопровождающийся синтезом АТФ");

        result = WikiWord.parseDoubleBrackets(page_title, wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }

    @Test
    public void testGetWikiWords_en() {
        System.out.println("getWikiWords_en");
        StringBuffer    wiki_text;
        String          page_title;
        WikiWord[]      ww, ww_result;
        
        // test 1 simple
        page_title = "sweet";
        wiki_text = new StringBuffer("Having a [[pleasant]] [[taste|tasting]], ... one [[sugar]]xyz.");
        //expResult = new StringBuffer("Having a pleasant taste, especially one relating to the basic taste sensation induced by sugar.");
        ww = new WikiWord[3];
        ww[0] = new WikiWord("pleasant","pleasant",  null);
        ww[1] = new WikiWord("taste",   "tasting",   null);
        ww[2] = new WikiWord("sugar",   "sugarxyz",  null);
        
        ww_result = WikiWord.getWikiWords(page_title, wiki_text);
        assertEquals(ww.length, ww_result.length);
        for(int i=0; i<ww.length; i++) {
            assertTrue(ww[i].getWordLink().   toString().equalsIgnoreCase( ww_result[i].getWordLink().toString() ) );
            assertTrue(ww[i].getWordVisible().toString().equalsIgnoreCase( ww_result[i].getWordVisible().toString() ) );
        }

        // test 2
        // (with vertical pipe) remove brackets
        page_title = "god";
        wiki_text = new StringBuffer("A [[supernatural|test1]], typically [[immortal]] being with [[superior]] [[power]]s.");
        ww = new WikiWord[4];
        ww[0] = new WikiWord("supernatural","test1",  null);
        ww[1] = new WikiWord("immortal",    "immortal",   null);
        ww[2] = new WikiWord("superior",    "superior",  null);
        ww[3] = new WikiWord("power",       "powers",  null);

        ww_result = WikiWord.getWikiWords(page_title, wiki_text);
        assertEquals(ww.length, ww_result.length);
        for(int i=0; i<ww.length; i++) {
            assertTrue(ww[i].getWordLink().   toString().equalsIgnoreCase( ww_result[i].getWordLink().toString() ) );
            assertTrue(ww[i].getWordVisible().toString().equalsIgnoreCase( ww_result[i].getWordVisible().toString() ) );
        }
    }

}