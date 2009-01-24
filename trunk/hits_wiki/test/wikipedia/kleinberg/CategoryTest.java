/*
 * CategoryTest.java
 * JUnit based test
 */

package wikipedia.kleinberg;

import junit.framework.*;
import wikipedia.sql.PageTable;
import java.util.*;

public class CategoryTest extends TestCase {
    
    public CategoryTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    
    public void testSortByIdArticlesLength() {
        System.out.println("sortByIdArticlesLength");
        
        Map<Integer, Category> map = new HashMap<Integer, Category>();
        
        Category c3 = new Category();
        c3.id_articles   = new int[3];
        c3.page_title    = "three";
        
        Category c2 = new Category();
        c2.id_articles   = new int[2];
        c2.page_title    = "two";
        
        Category c1 = new Category();
        c1.id_articles   = new int[1];
        c1.page_title    = "one";
        
        map.put(1, c1);
        map.put(2, c2);
        map.put(3, c3);
        
        List<Category> result = Category.sortByIdArticlesLength(null,100);
        assertEquals(0, result.size());
        
        result = Category.sortByIdArticlesLength(map, 3);
        assertEquals(3, result.size());
        assertEquals(3, result.get(0).getIdArticlesLength());
        assertEquals(2, result.get(1).getIdArticlesLength());
        assertEquals(1, result.get(2).getIdArticlesLength());
        
        result = Category.sortByIdArticlesLength(map, 5);
        assertEquals(3, result.size());
        
        result = Category.sortByIdArticlesLength(map,1);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getIdArticlesLength());
    }
    
    /**
     * Test of GraphVizNode method, of class wikipedia.kleinberg.Category.
     */
/*    public void testGraphVizNode() {
        System.out.println("GraphVizNode");
        
        Category instance = new Category();
        
        String expResult = "";
        String result = instance.GraphVizNode();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    /**
     * Test of GraphVizLinksIn method, of class wikipedia.kleinberg.Category.
     */
  /*  public void testGraphVizLinksIn() {
        System.out.println("GraphVizLinksIn");
        
        Category instance = new Category();
        
        String expResult = "";
        String result = instance.GraphVizLinksIn();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    /**
     * Test of GraphVizLinksOut method, of class wikipedia.kleinberg.Category.
     */
/*    public void testGraphVizLinksOut() {
        System.out.println("GraphVizLinksOut");
        
        Category instance = new Category();
        
        String expResult = "";
        String result = instance.GraphVizLinksOut();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    /**
     * Test of getIDByTitle method, of class wikipedia.kleinberg.Category.
     *
     * see sql.PageTableTest.testGetNamespaceByID_en()
     */
    //public void testGetIDByTitle() {}

    /**
     * Test of convertListInteger method, of class wikipedia.kleinberg.Category.
     */
    public void testConvertListInteger() {
        System.out.println("convertListInteger");
        
        List<Integer> l = new ArrayList<Integer>();
        Category.convertListInteger(l);
        l.add(1);
        l.add(2);
        l.add(3);
        
        Category.convertListInteger(null);
        int[] result = Category.convertListInteger(l);
        assertEquals(3, result.length);
    }
    
}
