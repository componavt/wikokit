
package wikokit.base.wikt.util;

import wikokit.base.wikt.util.WikiText;
import wikokit.base.wikt.util.WikiWord;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class WikiTextTest {

    public WikiTextTest() {
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
    public void testCreateSplitByComma() {
        System.out.println("createSplitByComma");

        String page_title = "колокольчик";
        String text = "[[little]] [[bell]], [[handbell]], [[doorbell]]";

        WikiText[] phrases = WikiText.createSplitByComma(page_title, text);

        assertEquals(3, phrases.length);
        assertTrue(phrases[0].getVisibleText().equalsIgnoreCase( "little bell" ) );
        WikiWord[] ww0 = phrases[0].getWikiWords();
        assertEquals(2, ww0.length);
        assertTrue(ww0[0].getWordVisible().equalsIgnoreCase( "little" ) );
        assertTrue(ww0[0].getWordLink().   equalsIgnoreCase( "little" ) );

        assertTrue(phrases[1].getVisibleText().equalsIgnoreCase( "handbell" ) );
        assertTrue(phrases[2].getVisibleText().equalsIgnoreCase( "doorbell" ) );
    }
    
    @Test
    public void testCreateOnePhrase() {
        System.out.println("CreateOnePhrase");

        String page_title = "колокольчик";
        String text = "[[little]] [[bell]], [[handbell]], [[doorbell]]";

        WikiText phrase = WikiText.createOnePhrase(page_title, text);

        WikiWord[] ww = phrase.getWikiWords();
        assertEquals(4, ww.length);
        assertTrue(ww[0].getWordVisible().equalsIgnoreCase( "little" ) );
        assertTrue(ww[0].getWordLink().   equalsIgnoreCase( "little" ) );

        assertTrue(phrase.getVisibleText().equalsIgnoreCase( "little bell, handbell, doorbell" ) );
        assertTrue(phrase.getWikifiedText().equalsIgnoreCase( "[[little]] [[bell]], [[handbell]], [[doorbell]]" ) );
    }

    @Test
    public void testCreateSplitByComma_comma_in_brackets() {
        System.out.println("createSplitByComma_comma_in_brackets");

        String page_title = "test";
        String text = "[[little]] [[bell]] (very little, little), [[handbell]], [[doorbell]]";

        WikiText[] phrases = WikiText.createSplitByComma(page_title, text);

        assertEquals(3, phrases.length);
        assertTrue(phrases[0].getVisibleText().equalsIgnoreCase( "little bell" ) );
    }
    
    // todo test leading spaces
    // ...

    // checks assertion that: "wikified text is NULL if "text" hasn't any wikification".
    @Test
    public void testCreateOnePhrase_absent_wikification() {
        System.out.println("CreateOne_absent_wikification");

        String page_title = "test page title";
        String text = "wikification is absent";

        WikiText phrase = WikiText.createOnePhrase(page_title, text);

        WikiWord[] ww0 = phrase.getWikiWords();
        assertEquals(0, ww0.length);

        assertTrue(phrase.getVisibleText().equalsIgnoreCase( "wikification is absent" ) );
        assertNull(phrase.getWikifiedText() );
    }
    
    // // if there are commas ,,, in the text, then nothiing is changed 
    // checks assertion that: "wikified text is NULL if "text" hasn't any wikification".
    @Test
    public void testCreateSplitByComma_absent_wikification_with_commas() {
        System.out.println("createSplitByComma_absent_wikification_with_commas");

        String page_title = "test page title2";
        String text = "no wikification again, and again";
        
        WikiText[] phrases = WikiText.createSplitByComma(page_title, text);

        assertEquals(2, phrases.length);
        assertTrue(phrases[0].getVisibleText().equalsIgnoreCase( "no wikification again" ) );
        assertNull(phrases[0].getWikifiedText());
        
        WikiWord[] ww0 = phrases[0].getWikiWords();
        assertEquals(0, ww0.length);
    }
}