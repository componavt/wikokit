/*
 * WordNetParserTest.java
 * JUnit based test
 *
 */

package rfc2229;

import junit.framework.*;
import java.util.List;
import java.util.ArrayList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author andrew
 */
public class WordNetParserTest extends TestCase {
    
    public WordNetParserTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(WordNetParserTest.class);
        
        return suite;
    }

    /**
     * Test of getSynonyms method, of class rfc2229.WordNetParser.
     */
    public void testGetSynonyms() {
        System.out.println("getSynonyms");
      
        String text_saccharify = "\n" +
             "saccharify \n" +
             "    v 1: sweeten with sugar; \"sugar your tea\" [syn: sugar, one more sugar]\n" +
             "    2: convert into a simple soluble fermentable sugar by\n" +
             "        hydrolyzing a sugar derivative or complex carbohydrate\n" +
             "    [also: saccharified], and [blah: blah-blah-blah]";
        
        List<String> expResult = new ArrayList<String>();
        expResult.add("sugar");
        expResult.add("one more sugar");
        expResult.add("saccharified");
        
        List<String> result = WordNetParser.getSynonyms(text_saccharify);
        assertEquals(expResult, result);
    }
    
    /** Tests of extraction of unique words, i.e. without repetitions. */
    public void testGetSynonyms_AntonymAndUniqueness() {
        System.out.println("getSynonyms_AntonymAndUniqueness");
        
        String text = "\n" +
            "transparence\n" +
            " n 1: permitting the free passage of electromagnetic radiation\n" +
            "      [syn: transparency] [ant: opacity]\n" +
            " 2: the quality of being clear and transparent [syn: transparency, transparentness]\n" +
            " test repetition with the tag also [also: transparency, transparentness]";
        
        List<String> expResult = new ArrayList<String>();
        expResult.add("transparency");
        expResult.add("transparentness");
        
        List<String> result = WordNetParser.getSynonyms(text);
        assertEquals(expResult, result);
    }
    
    /** Real life examples: lines with newlines \r\n.
     * "saccharify\r\n     v 1: sweeten with sugar; \"sugar your tea\" [syn: {sugar}]\r\n     2: convert into a simple soluble fermentable sugar by\r\n        hydrolyzing a sugar derivative or complex carbohydrate\r\n     [also: {saccharified}]\r\n.\r\n"
     */
    public void testGetSynonyms_newlines() {
        System.out.println("getSynonyms_newlines");
        
        String text = "saccharify\r\n     v 1: sweeten with sugar; \"sugar your tea\" [syn: {sugar}]\r\n     " +
            "2: convert into a simple soluble fermentable sugar by\r\n        hydrolyzing a sugar derivative " +
            "or complex carbohydrate\r\n     [also: {saccharified\r\n}]\r\n.\r\n";
        
        List<String> expResult = new ArrayList<String>();
        expResult.add("sugar");
        expResult.add("saccharified");
        
        List<String> result = WordNetParser.getSynonyms(text);
        assertEquals(expResult, result);
    }
     
     
}
