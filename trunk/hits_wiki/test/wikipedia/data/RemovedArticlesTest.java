/*
 * RemovedArticlesTest.java
 * JUnit based test
 */

package wikipedia.data;

import junit.framework.*;
import java.util.ArrayList;
import java.util.List;


public class RemovedArticlesTest extends TestCase {
    
    RemovedArticles removed_articles = new RemovedArticles();
            
    public RemovedArticlesTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of addTitle method, of class wikipedia.data.RemovedArticles.
     */
    public void testAddTitle() {
        System.out.println("addTitle");
        
        String s1 = "string1";
        String s2 = "string2";
        
        removed_articles.addTitle(s1);
        removed_articles.addTitle(s2);
        removed_articles.addTitle(s2);
        
        assertEquals(2, removed_articles.sizeTitle());
    }

    /**
     * Test of addId method, of class wikipedia.data.RemovedArticles.
     */
    public void testAddId() {
        System.out.println("addId");
        
        assertEquals(0, removed_articles.sizeId());
        removed_articles.addId(11);
        removed_articles.addId(12);
        removed_articles.addId(12);
        assertEquals(2, removed_articles.sizeId());
    }
    
}
