/*
 * PreprocessingTest.java
 * JUnit based test
 *
 * Created on 22 June 2005
 */

package wikipedia.clustering;

import junit.framework.*;
import wikipedia.kleinberg.*;
import java.util.*;

public class PreprocessingTest extends TestCase {
    
    Map<Integer, Article>   articles;
    Map<Integer, Category>  categories;
            
    public PreprocessingTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        CreateCategoryArticleGraph c = new CreateCategoryArticleGraph ();
        articles    = c.articles;
        categories  = c.categories;
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(PreprocessingTest.class);
        
        return suite;
    }

    /**
     * Test of createInitialClusters method, of class wikipedia.clustering.Preprocessing.
     */
    public void testCreateInitialClusters() {
        System.out.println("testCreateInitialClusters");
        List<ClusterCategory> clusters  = Preprocessing.createInitialClusters      (articles, categories);
        assertTrue(clusters.size() == categories.size());
    }

    /**
     * Test of createEdgesBetweenClusters method, of class wikipedia.clustering.Preprocessing.
     */
    public void testCreateEdgesBetweenClusters() {
        System.out.println("testCreateEdgesBetweenClusters");
        
        List<ClusterCategory> clusters = Preprocessing.createInitialClusters (articles, categories);
        List<Edge> edges = Preprocessing.createEdgesBetweenClusters (clusters, categories);
        
        Map<Integer, ClusterCategory> category_id_to_cluster = ClusterCategory.mapCategoryIdToCluster(clusters);
        Cluster c_all       = category_id_to_cluster.get(1);
        Cluster c_religious = category_id_to_cluster.get(2);
        Cluster c_science   = category_id_to_cluster.get(3);
        Cluster c_art       = category_id_to_cluster.get(4);
        assertTrue(c_all.containsAdjacent(c_all));
        assertTrue(c_all.containsAdjacent(c_religious));
        assertTrue(c_all.containsAdjacent(c_science));
        assertTrue(c_all.containsAdjacent(c_art));
        assertFalse(c_religious.containsAdjacent(c_science));
    }
}
