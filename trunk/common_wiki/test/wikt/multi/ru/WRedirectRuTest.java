
package wikt.multi.ru;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class WRedirectRuTest {

    public WRedirectRuTest() {
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
     * Test of getRedirect method, of class WRedirectRu.
     */
    @Test
    public void testGetRedirect() {
        System.out.println("getRedirect");
        String page_title = "source_entry";
        StringBuffer source;
        String expResult, result;

        // 0. null - not a redirect
        source = new StringBuffer("usual article without a redirect");
        expResult = null;
        result = WRedirectRu.getRedirect(page_title, source);
        assertEquals(expResult, result);

        // 1. too short text - not a redirect
        source = new StringBuffer("abcd");
        expResult = null;
        result = WRedirectRu.getRedirect(page_title, source);
        assertEquals(expResult, result);

        // 2. redirect #REDIRECT
        source = new StringBuffer("#REDIRECT [[burn one's fingers]]");
        expResult = "burn one's fingers";
        result = WRedirectRu.getRedirect(page_title, source);
        assertTrue(expResult.equalsIgnoreCase(result));

        // 3. small case: #redirect [[pagename]])
        source = new StringBuffer("#redirect [[pagename]])");
        expResult = "pagename";
        result = WRedirectRu.getRedirect(page_title, source);
        assertTrue(expResult.equalsIgnoreCase(result));

        // 4. any text after the redirect
        source = new StringBuffer("#REDIRECT [[burn one's fingers]] some text \nmore text\n");
        expResult = "burn one's fingers";
        result = WRedirectRu.getRedirect(page_title, source);
        assertTrue(expResult.equalsIgnoreCase(result));

        // 5. Russian redirect #ПЕРЕНАПРАВЛЕНИЕ"
        source = new StringBuffer("#ПЕРЕНАПРАВЛЕНИЕ [[нелётный]]");
        expResult = "нелётный";
        result = WRedirectRu.getRedirect(page_title, source);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

}