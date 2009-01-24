
package wikt.multi.ru;

import wikt.util.WikiText;
import wikt.util.WikiWord;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikt.word.WTranslationEntry;

public class WTranslationEntryRuTest {

    public WTranslationEntryRuTest() {
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
    public void testParse_comma() {
        System.out.println("parse_comma");
        String page_title = "колокольчик";
        String text = "en=[[little]] [[bell]], [[handbell]], [[doorbell]]";

        WTranslationEntry result = WTranslationEntryRu.parse(page_title, text);
        
        assertEquals(3, result.getWikiPhrases().length);

        WikiText[] phrases = result.getWikiPhrases();

        assertTrue(phrases[0].getVisibleText().equalsIgnoreCase( "little bell" ) );
        WikiWord[] ww0 = phrases[0].getWikiWords();
        assertEquals(2, ww0.length);
        assertTrue(ww0[0].getWordVisible().equalsIgnoreCase( "little" ) );
        assertTrue(ww0[0].getWordLink().   equalsIgnoreCase( "little" ) );

        assertTrue(phrases[1].getVisibleText().equalsIgnoreCase( "handbell" ) );
        assertTrue(phrases[2].getVisibleText().equalsIgnoreCase( "doorbell" ) );

        text = "en=";
        result = WTranslationEntryRu.parse(page_title, text);
        assertEquals(null, result);

        text = "en= ";
        result = WTranslationEntryRu.parse(page_title, text);
        assertEquals(null, result);

        text = "unknown language=";
        result = WTranslationEntryRu.parse(page_title, text);
        assertEquals(null, result);
    }
}