
package wikt.util;

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
    public void testCreate() {
        System.out.println("create");

        String page_title = "колокольчик";
        String text = "[[little]] [[bell]], [[handbell]], [[doorbell]]";

        WikiText[] phrases = WikiText.create(page_title, text);

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
    public void testCreate_comma_in_brackets() {
        System.out.println("create_comma_in_brackets");

        String page_title = "test";
        String text = "[[little]] [[bell]] (very little, little), [[handbell]], [[doorbell]]";

        WikiText[] phrases = WikiText.create(page_title, text);

        assertEquals(3, phrases.length);
        assertTrue(phrases[0].getVisibleText().equalsIgnoreCase( "little bell" ) );
        /*WikiWord[] ww0 = phrases[0].getWikiWords();
        assertEquals(2, ww0.length);
        assertTrue(ww0[0].getWordVisible().equalsIgnoreCase( "little" ) );
        assertTrue(ww0[0].getWordLink().   equalsIgnoreCase( "little" ) );

        assertTrue(phrases[1].getVisibleText().equalsIgnoreCase( "handbell" ) );
        assertTrue(phrases[2].getVisibleText().equalsIgnoreCase( "doorbell" ) );*/
    }

}