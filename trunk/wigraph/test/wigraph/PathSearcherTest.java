
package wigraph;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PathSearcherTest {

    public PathSearcherTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testGetShortestPath() {
        System.out.println("getShortestPath");
        Graph g;
        DijkstraShortestPath<String,Integer> alg;
        
        String[] word_path;
        String word1 = "воздухоплавание";
        String word2 = "аэроплан";

        // test absent words
        String absent_vertex = "absent vertex";
        g = GraphCreator.createGraph(GraphCreator.labels_samolyot, GraphCreator.edges_samolyot);
        g.addVertex(absent_vertex);     // special vertex for debug
        alg = new DijkstraShortestPath(g);

        word_path = PathSearcher.getShortestPath(g, alg, word1, absent_vertex);
        assertNotNull(word_path);
        assertEquals(0, word_path.length);

        // test unreachable words
        String isolated_vertex = "isolated vertex";
        g = GraphCreator.createGraph(GraphCreator.labels_samolyot, GraphCreator.edges_samolyot);
        g.addVertex(isolated_vertex);     // special vertex for debug
        alg = new DijkstraShortestPath(g);
        
        word_path = PathSearcher.getShortestPath(g, alg, word1, isolated_vertex);
        assertNotNull(word_path);
        assertEquals(0, word_path.length);

        // result should be: "воздухоплавание" - "авиация" - "самолёт" - "аэроплан"
        g.removeVertex(isolated_vertex);
        alg = new DijkstraShortestPath(g);
        word_path = PathSearcher.getShortestPath(g, alg, word1, word2);
        String[] should_be_path = {"воздухоплавание", "авиация", "самолёт", "аэроплан"};

        assertNotNull(word_path);
        assertEquals(word_path.length, should_be_path.length);
        for(int i=0; i<word_path.length; i++ )
            assertEquals(word_path[i], should_be_path[i]);

        // System.out.println("The shortest unweighted path from" + word1 + " to " + word2 + " is:");
    }

    @Test
    public void testCalcPathLenRelatedness () {
        System.out.println("calcPathLenRelatedness");
        Graph g;
        DijkstraShortestPath<String,Integer> alg;

        g = GraphCreator.createGraph(GraphCreator.labels_samolyot, GraphCreator.edges_samolyot);
        alg = new DijkstraShortestPath(g);
        
        String[] word_set1 = {"воздухоплавание", "авиация"};
        String[] word_set2 = {"аэроплан", "самолёт"};

        float should_be_length = 1f / ((4 + 3 + 3 + 3) / 4.f);
        
        DistanceData dd = PathSearcher.calcPathLenRelatedness(g, alg, word_set1, word_set2);
        assertTrue(Math.abs(dd.average - should_be_length) < 0.1f);
        assertTrue(Math.abs(dd.max - 0.5f) < 0.1f);
        assertTrue(Math.abs(dd.min - 0.25f) < 0.1f);
    }

}