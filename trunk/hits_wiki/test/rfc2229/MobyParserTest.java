/*
 * MobyParserTest.java
 * JUnit based test
 */

package rfc2229;

import junit.framework.*;
import wikipedia.util.StringUtil;

/**
 *
 * @author andrew
 */
public class MobyParserTest extends TestCase {
    
    public MobyParserTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(MobyParserTest.class);
        
        return suite;
    }

    /**
     * Test of getWords method, of class rfc2229.MobyParser.
     */
    public void testGetWords() {
        System.out.println("getWords");
        
        String text = "24 (3 in test) Moby Thesaurus words for \"mulch\":" +
           "backset, cultivate, culture";
           
        String[] expResult = {"backset", "cultivate", "culture"};
        String[] result = MobyParser.getWords(text);
        for(int i=0; i<result.length; i++) {
            assertEquals(expResult[i], result[i]);
        }
    }
    
    public void testGetWords_with_newlines() {
        System.out.println("getWords_with_newlines");
        
        // check the dot search
        String      t = ":work\r\n\r\n\r\n\r\n.\r\nblah-bla-bla";
        String[]    t_result = MobyParser.getWords(t);
        assertEquals(1,     t_result.length);
        assertEquals("work",t_result[0]);
        
        // check duplicates
        t = ":duplicate, duplicate \n, work\r\n\r\n\r\n\r\n.\r\nblah-bla-bla";
        t_result = MobyParser.getWords(t);
        assertEquals(2, t_result.length);
        
        // complex check
        //"24 Moby Thesaurus words for \"mulch\":\r\n   backset, cultivate, culture, cut, delve, dig, dress, fallow,\r\n   fertilize, force, harrow, hoe, list, plow, prune, rake, spade,\r\n   thin, thin out, till, till the soil, weed, weed out, work\r\n\r\n\r\n\r\n.\r\n"
        String text = "24 (3 in test) Moby Thesaurus words for \"mulch\":" +
           "\r\n   backset, fallow,\r\n   fertilize, culture,\r\n   thin, work\r\n\r\n\r\n\r\n.\r\n";
        
        String[] expResult = {"backset", "fallow", "fertilize", "culture", "thin", "work"};
        String[] result = MobyParser.getWords(text);
        for(int i=0; i<result.length; i++) {
            assertEquals(expResult[i], result[i]);
        }
    }
}
