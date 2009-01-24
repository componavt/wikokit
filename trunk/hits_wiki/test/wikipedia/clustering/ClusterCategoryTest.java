/*
 * ClusterCategoryTest.java
 * JUnit based test
 */

package wikipedia.clustering;

import junit.framework.*;
import wikipedia.kleinberg.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class ClusterCategoryTest extends TestCase {

    Map<Integer, Article>   articles;
    Map<Integer, Category>  categories;
    Map<Integer, ClusterCategory> category_id_to_cluster;
    List<ClusterCategory>   clusters;
    List<Edge>              edges;
    ClusterCategory         c_all, c_religious, c_science, c_art;
    
    public ClusterCategoryTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        CreateCategoryArticleGraph c = new CreateCategoryArticleGraph ();
        articles    = c.articles;
        categories  = c.categories;
        CategorySet.fillLinksFromCategoryToArticles(articles, categories);
        clusters = Preprocessing.createInitialClusters (articles, categories);
        edges = Preprocessing.createEdgesBetweenClusters (clusters, categories);
        
        // set initial clusters
        category_id_to_cluster = ClusterCategory.mapCategoryIdToCluster(clusters);
        c_all       = category_id_to_cluster.get(1);
        c_religious = category_id_to_cluster.get(2);
        c_science   = category_id_to_cluster.get(3);
        c_art       = category_id_to_cluster.get(4);
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ClusterCategoryTest.class);
        
        return suite;
    }

    /**
     * Test of init method, of class wikipedia.clustering.ClusterCategory.
     */
    public void testInit() {
        System.out.println("testInit");
        
        // init() done in this.setUp()
        assertTrue(2 == c_all.weight);
        assertTrue(3 == c_religious.weight);
        
        assertTrue(1 == c_all.n_articles);
        assertTrue(2 == c_religious.n_articles);
        
        assertTrue(1 == c_all.categories_id.length);
        assertTrue(1 == c_all.categories_id[0]);
    }

    public void testMapCategoryIdToCluster() {
        System.out.println("testMapCategoryIdToCluster");
        
        // map done in setUp(), test results here
        assertTrue(1 == c_art.categories_id.length);
        assertTrue(4 == c_art.categories_id[0]);
        
        for(Category cat:categories.values()) {
            int id_from = cat.page_id;
            assertTrue(null != category_id_to_cluster.get(id_from));
            if(null != cat.links_out) {
                for(int id_to:cat.links_out) {         // links_out: id of categories which are referred by the category
                    // assert that there are no absent categories
                    assertTrue(null != category_id_to_cluster.get(id_to));
                }
            }
        }
    }

    /**
     * Test of addCluster method, of class wikipedia.clustering.ClusterCategory.
     */
    public void testAddCluster() {
        System.out.println("testAddClusterWeight");
        
        // preconditions
        
        // weight
        assertTrue(2 == c_all.weight);
        assertTrue(3 == c_religious.weight);
        
        // n_article
        assertTrue(1 == c_all.n_articles);
        assertTrue(2 == c_religious.n_articles);
        
        // categories_id
        assertTrue(1 == c_all.categories_id.length);
        
        c_all.addCluster(c_religious);
        
        // postconditions
        assertTrue(5 == c_all.weight);
        assertTrue(3 == c_all.n_articles);
        assertTrue(2 == c_all.categories_id.length);
        assertTrue(1 == c_all.categories_id[0]);
        assertTrue(2 == c_all.categories_id[1]);
    }

    public void testContainsAdjacent() {
        System.out.println("testContainsAdjacent");
        assertTrue(c_all.containsAdjacent(c_art));
        assertTrue(c_art.containsAdjacent(c_all));
        assertFalse(c_art.containsAdjacent(c_science));
    }
        
    public void testGetAdjacentVertices() {
        System.out.println("testGetAdjacentVertices");
        List<Cluster> c_all_adjacent = c_all.getAdjacentVertices();
        assertTrue(3 == c_all_adjacent.size());
    }

    public void testGetEdgeToCluster() {
        System.out.println("testGetEdgeToCluster");
        Edge e_art_all = c_art.edges[0];
        assertEquals(e_art_all, c_all.getEdgeToCluster(c_art));
    }
    
    public void testGraphVizCluster() {
        System.out.println("testGraphVizCluster");
        String should_be = "subgraph cluster1 {\n" +
                    "  node [style=filled];\n" +
                    "  label = \"cluster1: weight=5\";\n" +
                    "  color=blue;\n" +
                    "  C1;\n" +
                    "  C2;\n" +
                    "  A1 [label=\"Articles in the cluster: N\n" +
                    "11 Linux x=1.0 y=1.0\n" +
                    "10 God x=1.0 y=1.0\n\",shape=box];\n" +
                    " }\n";

        c_all.addCluster(c_religious);
        assertTrue(5 == c_all.weight);
        
        String s = c_all.graphVizCluster(articles, categories);
        assertEquals(should_be, s);
    }
    
    public void testGetStatistics() {
        System.out.println("testGetStatistics");

        String should_be = ":: clusters:4";
        String s = ClusterCategory.getStatistics (clusters);
        assertEquals(should_be, s);
    }

    public void testAddEdge() {
        System.out.println("testAddEdge");

        Edge s0 = new Edge();
        Edge s1 = new Edge();
        Edge s2 = new Edge();
        Edge s_absent = new Edge();
        
        Edge[] source_edges = new Edge[3];
        source_edges [0] = s0;
        source_edges [1] = s1;
        source_edges [2] = s2;
        
        ClusterCategory cc = new ClusterCategory();
        
        cc.addEdge(s0);
        assertEquals(1, cc.edges.length);
        
        cc.addEdge(s1);
        assertEquals(2, cc.edges.length);
        
        cc.addEdge(s2);
        assertEquals(3, cc.edges.length);
    }
}







