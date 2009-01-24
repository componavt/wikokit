/*
 * EdgeTest.java
 * JUnit based test
 */

package wikipedia.clustering;

import junit.framework.*;
import wikipedia.kleinberg.*;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;


public class EdgeTest extends TestCase {

    Map<Integer, Article>   articles;
    Map<Integer, Category>  categories;
    List<ClusterCategory>   clusters;
    List<Edge>              edges;
    ClusterCategory         c_all, c_religious, c_science, c_art;

    public EdgeTest(String testName) {
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
        Map<Integer, ClusterCategory> category_id_to_cluster = ClusterCategory.mapCategoryIdToCluster(clusters);
        c_all       = category_id_to_cluster.get(1);
        c_religious = category_id_to_cluster.get(2);
        c_science   = category_id_to_cluster.get(3);
        c_art       = category_id_to_cluster.get(4);
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(EdgeTest.class);
        
        return suite;
    }

    /**
     * Test of init method, of class wikipedia.clustering.Edge.
     */
    public void testInit() {
        System.out.println("testInit");
        
        // init() done in this.setUp() in function Preprocessing.createEdgesBetweenClusters()
        assertTrue(1 == c_art.edges.length);
        Edge e_art_all = c_art.edges[0];
        assertTrue(e_art_all.containsVertex(c_art));
        assertTrue(e_art_all.containsVertex(c_all));
        assertTrue(5 == e_art_all.getWeight());
        assertTrue(Arrays.asList(c_art.edges).contains(e_art_all));
        assertTrue(Arrays.asList(c_all.edges).contains(e_art_all));
    }

    /**
     * Test of updateWeight method, of class wikipedia.clustering.Edge.
     */
    public void testUpdateWeight() {
        System.out.println("testUpdateWeight");
        c_all.weight = 5;
        c_art.weight = 7;
        Edge e_art_all = c_art.edges[0];
        e_art_all.updateWeight();
        assertEquals(12, e_art_all.getWeight());
    }

    /**
     * Test of containsVertices method, of class wikipedia.clustering.Edge.
     */
    public void testContainsVertices() {
        System.out.println("testContainsVertices");
        Edge e_art_all = c_art.edges[0];
        
        List<Cluster> clusters_art_all = new ArrayList<Cluster>();
        clusters_art_all.add(c_all);
        clusters_art_all.add(c_art);
        assertTrue(e_art_all.containsVertices(clusters_art_all));
    }

    /**
     * Test of Merge method, of class wikipedia.clustering.Edge.
     */
    public void testMerge() {
        System.out.println("testMerge");
        
        Edge e_science_all  = c_science.edges[0];
        Edge e_art_all      = c_art.edges[0];
        
        Cluster science_all = e_science_all.getVertex1();
        
        // preconditions
        assertEquals(3, science_all.edges.length);
        
        e_science_all.Merge();
        // check
        // *) Add all edges adjacent to c (with check: skip edge's repetition), update edges[].c1 and c2
        assertEquals(2, science_all.edges.length);
        
        // *) Update weight of all edges adjacent to c1
        assertEquals(6, science_all.weight);
        assertEquals(9, c_religious.edges[0].getWeight());
        assertEquals(9, c_art.edges[0].getWeight());
        
        Cluster science_all_art = e_art_all.getVertex1();
        e_art_all.Merge();
        // *) Add all edges adjacent to c (with check: skip edge's repetition), update edges[].c1 and c2
        assertEquals(1, science_all_art.edges.length);
        
        // *) Update weight of all edges adjacent to c1
        assertEquals(9, science_all_art.weight);
        assertEquals(12, c_religious.edges[0].getWeight());
        
        assertTrue(c_religious.edges[0].containsVertex(science_all_art));
    }

    /**
     * Test of replaceVertex method, of class wikipedia.clustering.Edge.
     */
    public void testReplaceVertex() {
        System.out.println("testReplaceVertex");
        
        Edge e_art_all = c_art.edges[0];
        List<Cluster> clusters_art_all = new ArrayList<Cluster>();
        clusters_art_all.add(c_all);
        clusters_art_all.add(c_art);
        assertTrue(e_art_all.containsVertices(clusters_art_all));
        
        e_art_all.replaceVertex(c_all, c_science);
        assertTrue(e_art_all.containsVertex(c_art));
        assertTrue(e_art_all.containsVertex(c_science));
    }
    
    
    public void testRemoveEdge() {
        System.out.println("testRemoveEdge");
        
        Edge s0 = new Edge();
        Edge s1 = new Edge();
        Edge s2 = new Edge();
        Edge s_absent = new Edge();
        
        Edge[] source_edges = new Edge[4];
        source_edges [0] = s0;
        source_edges [1] = s1;
        source_edges [2] = s2;
        source_edges [3] = s2;
        
        source_edges = Edge.RemoveEdge(source_edges, s_absent);
        assertEquals(4, source_edges.length);
        
        source_edges = Edge.RemoveEdge(source_edges, null);
        assertEquals(4, source_edges.length);
        
        source_edges = Edge.RemoveEdge(source_edges, s0);
        assertEquals(3, source_edges.length);
        
        source_edges = Edge.RemoveEdge(source_edges, s2);
        assertEquals(1, source_edges.length);
        assertEquals(s1, source_edges[0]);
    }
        
    
}
