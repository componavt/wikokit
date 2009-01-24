/*
 * DCELTest.java
 * JUnit based test
 * @author Andrew Krizhanovsky /mail: aka at mail.iias.spb.su/ 2005 LGPL
 */

package wikipedia.kleinberg;

import wikipedia.sql.*;
import wikipedia.util.*;
import junit.framework.*;
import java.sql.*;
import java.util.*;


public class DCELTest extends TestCase {
    
    public DCELTest(String testName) {
        super(testName);
    }

    protected void setUp() throws java.lang.Exception {
    }

    protected void tearDown() throws java.lang.Exception {
        /*connect.Close();
        connect_ru.Close();*/
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(DCELTest.class);
        
        return suite;
    }

    /**
     * Test of CountArcAndVertices method, of class wikipedia.DCEL.
     */
    public void testCountArcs() {

        Article[] source_nodes = new Article[2];
        source_nodes[0] = new Article();
        source_nodes[1] = new Article();
        
        source_nodes[0].links_in = new int[7];
        source_nodes[1].links_in = new int[5];
        
        HashMap<Integer, Article> hash_node = new HashMap<Integer, Article>();
        hash_node.put(0, source_nodes[0]);
        hash_node.put(1, source_nodes[1]);
        
        int i = DCEL.CountLinksIn(hash_node);
        assertEquals(i, 12);
    }
    
    
    
}


