/*
 * RandShuffleTest.java
 * JUnit based test
 */

package wikipedia.util_rand;

import wikipedia.util_rand.RandShuffle;
import wikipedia.kleinberg.*;
import junit.framework.*;

public class RandShuffleTest extends TestCase {

    Article[]          source_nodes;
    SessionHolder   session;
    
    public RandShuffleTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        source_nodes = new Article[2];
        source_nodes[0] = new Article();
        source_nodes[1] = new Article();
        source_nodes[0].page_id = 18991;
        source_nodes[1].page_id = 22233;
        session = new SessionHolder();
        session.initObjects();
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(RandShuffleTest.class);
        
        return suite;
    }

    /*
    public void testGetTrueArray() {
        boolean[] b_source;
                
        b_source = RandShuffle.getTrueArray (0);
        assertEquals(null, b_source);
        
        int n_size = 3;
        b_source = RandShuffle.getTrueArray (n_size);
        
        int really_true = 0;
        for (int i=0; i<b_source.length; i++) {
            if (b_source[i]) {
                really_true  ++;
            } 
        }
        assertEquals(really_true, n_size);
    }*/
    
    
    public void testGetRandBoolArray() {
        boolean[] b_source;
                
        b_source = RandShuffle.getRandArray (0, 0);
        
        int n_true  = 3;
        int n_total = 10;
        b_source = RandShuffle.getRandArray (n_true, n_total);
        
        int i;
        int really_true = 0;
        int really_false = 0;
        for (i=0; i<b_source.length; i++) {
            if (b_source[i]) {
                really_true  ++;
            } else {
                really_false ++;
            }
        }
        assertEquals(really_true,            n_true);
        assertEquals(really_false, n_total - n_true);
    }
    
    /*
    public void testGetRandNodeArray() {
        Article[] nodes0 = Article.getRandNodeArray(source_nodes, -1);
        assertEquals(source_nodes.length, nodes0.length);
        
        Article[] nodes1 = Article.getRandNodeArray(source_nodes, 1);
        assertEquals(1, nodes1.length);
        
        Article[] nodes2 = Article.getRandNodeArray(source_nodes, 2);
        assertEquals(2, nodes2.length);
        
        Article[] nodes3 = Article.getRandNodeArray(source_nodes, 2);
        assertEquals(2, nodes3.length);
    }*/
    
    /*public void testGetRandIntArray() {
        int[] source = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] result;
        
        result = RandShuffle.getRandIntArray(source, 0);
        assertEquals(null, result);
        
        result = RandShuffle.getRandIntArray(source, 1);
        assertEquals(1, result.length);
        
        result = RandShuffle.getRandIntArray(source, 5);
        assertEquals(5, result.length);
        
        result = RandShuffle.getRandIntArray(source, 50);
        assertEquals(10, result.length);
    }*/
    
    public void testPermuteRandomly() {
        int[] source = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] result;
        
        result = RandShuffle.permuteRandomly(source);
        assertEquals(10, result.length);        
    }
}
