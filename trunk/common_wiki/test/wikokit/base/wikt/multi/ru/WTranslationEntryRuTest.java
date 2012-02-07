
package wikokit.base.wikt.multi.ru;

import wikokit.base.wikt.multi.ru.WTranslationEntryRu;
import wikokit.base.wikt.util.WikiText;
import wikokit.base.wikt.util.WikiWord;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikt.word.WTranslationEntry;

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

    // // translation box with empty spaces
/*        samolyot_with_spaces = "=== Перевод ===\n" +
            "{{перев-блок||\n" +
            "| en = [[airplane]]\n" +     // 0
            "}}";
*/
    /* samolyot_with_spaces
     * "| en = [[airplane]]" instead of
       "|en=[[airplane]]""
     */
    @Test
    public void testParse_parameters_with_spaces() {
        System.out.println("parameters_with_spaces");

        String page_title = "самолёт";
        String text = " en = [[airplane]]";
        WTranslationEntry result = WTranslationEntryRu.parse(page_title, text);

        assertEquals(1, result.getWikiPhrases().length);

        WikiText[] phrases = result.getWikiPhrases();

        assertTrue(phrases[0].getVisibleText().equalsIgnoreCase( "airplane" ) );
        WikiWord[] ww0 = phrases[0].getWikiWords();
        assertEquals(1, ww0.length);
        assertTrue(ww0[0].getWordVisible().equalsIgnoreCase( "airplane" ) );
        assertTrue(ww0[0].getWordLink().   equalsIgnoreCase( "airplane" ) );
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
        assertNull(result);

        text = "en= ";
        result = WTranslationEntryRu.parse(page_title, text);
        assertNull(result);

        // without any warning, since there is no any translations after=
        text = "unknown language=";
        result = WTranslationEntryRu.parse(page_title, text);
        assertEquals(null, result);
    }

    // Warning test: two unknown language codes, but only one Warning message
    // should be printed!
    @Test
    public void testParse_two_unknown_language_codes() {
        System.out.println("parse_two_unknown_language_codes: Attention! Should be only one warning about unknown language code!");

        String page_title = "колокольчик";
        String text = "en=[[little]] [[bell]], [[handbell]], [[doorbell]]";

        // unknown language code "unknown"
        text = "unknown=some text";
        WTranslationEntry result = WTranslationEntryRu.parse(page_title, text);
        assertEquals(null, result);
        
        result = WTranslationEntryRu.parse(page_title, text);
        assertNull(result);
    }
}