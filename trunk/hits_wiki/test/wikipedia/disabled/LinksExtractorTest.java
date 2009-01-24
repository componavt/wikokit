/*
 * LinksExtractorTest.java
 * JUnit based test
 *
 * Created on 12 April 2005
 */

package wikipedia.disabled;

import wikipedia.sql.*;
import wikipedia.sql.maintenance.LinksOutExtractorText;
import wikipedia.util.*;

import junit.framework.*;


public class LinksExtractorTest extends TestCase {
    
    Connect                 connect, connect_ru;
    private PageTable        page_table;
    private LinksOutExtractorText  links_extractor;
    
    public LinksExtractorTest(String testName) {
        super(testName);
    }

    
    protected void setUp() throws java.lang.Exception {
        page_table      = new PageTable ();
        links_extractor = new LinksOutExtractorText();
        connect         = new Connect();
        connect.Open("localhost", "enwiki?useUnicode=true&characterEncoding=UTF-8", "javawiki", "");
        
        connect_ru  = new Connect();
        connect_ru.Open("localhost", "ruwiki?useUnicode=false&characterEncoding=ISO8859_1", "javawiki", ""); //Java:MySQL ISO8859_1:latin1
    }

    protected void tearDown() throws java.lang.Exception {
        connect.Close();
        connect_ru.Close();
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(LinksExtractorTest.class);
        
        return suite;
    }

    /**
     * Test of getLinks method, of class wikipedia.LinksExtractor.
     */
    public void testGetLinks() {
        String[] links;
        String article;
        article = "a [[coma]], [[mind]] or in the future, [[artificial consciousness|machines]]. ";
        links = links_extractor.getLinks(article);
        assertEquals( 3, links.length);
        assertEquals( "artificial consciousness", links[0]);
        assertEquals( "coma",                   links[1]);
        assertEquals( "mind",                links[2]);
        
        /*String[] links2;
        article = page_table.SelectPageText(connect, "Consciousness");
        links2 = links_extractor.getLinks(article);
        assertEquals( 16, links.length);*/
    }

    /**
     * Test of getLinks method, of class wikipedia.LinksExtractor.
     * All links should unique, repetition were removed
     */
    public void testGetLinksUnique() {
        String[] links;
        String article;
        article = "to [[teach]], [[teach]] and again [[teach]]; [[logic|Prolog]] and [[logic|CLIPS]]";
        links = links_extractor.getLinks(article);
        assertEquals( 2, links.length);
        assertEquals( "logic", links[0]);
        assertEquals( "teach", links[1]);
    }
}
