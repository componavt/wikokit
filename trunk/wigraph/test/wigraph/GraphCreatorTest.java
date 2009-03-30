
package wigraph;

import wikt.sql.TRelation;
import wikt.sql.TLang;
import wikt.sql.TPOS;
import wikipedia.sql.Connect;

import edu.uci.ics.jung.graph.Graph;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphCreatorTest {

    public Connect   ruwikt_parsed_conn;
    
    public GraphCreatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);

        // It is supposed that Wiktionary parsed database has been created
        TLang.createFastMaps(ruwikt_parsed_conn);   // once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps(ruwikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }

    @Test
    public void testCreateGraph_Map() {
        System.out.println("createGraph_two_kind_of_arguments");

        Map<String,List<String>> m_words;
        Graph g;
        //Graph g = GraphCreator.createGraph(GraphCreator.labels_samolyot, GraphCreator.edges_samolyot);

        // compare two functions 'createGraph' with different argument (array and map)
        g = GraphCreator.createGraph(GraphCreator.labels_samolyot, GraphCreator.edges_samolyot);
        String graph_from_array = g.toString();
        System.out.println("The graph from array = " + g.toString());
        
        // create Map<String,List<String>> from String[]
        int edge_len = GraphCreator.edges_samolyot.length;
        m_words = new HashMap<String,List<String>> ();
        for(int i=0; i<edge_len; i++) {
            String word1 = GraphCreator.labels_samolyot [ GraphCreator.edges_samolyot[i][0] ];
            String word2 = GraphCreator.labels_samolyot [ GraphCreator.edges_samolyot[i][1] ];
            List<String> list_v2 = m_words.get(word1);
            if(null == list_v2) {
                list_v2 = new ArrayList<String>();
                list_v2.add(word2);
                m_words.put(word1, list_v2);
            } else {
                if(!list_v2.contains(word2))
                    list_v2.add(word2);
            }
        }

        List<String> unique_words = new ArrayList();
        for(String s : GraphCreator.labels_samolyot)
            unique_words.add(s);

        g = GraphCreator.createGraph(m_words, unique_words);
        assertNotNull(g);
        String graph_from_map = g.toString();
        System.out.println("The graph from map   = " + g.toString());
        assertEquals(graph_from_array, graph_from_map);
        

        // not really test, but checking that it works:
        
        // edge creation 1
        // for each TRelation: get page<->wiki_text + type of relation
        /*m_words = TRelation.getAllWordPairs(ruwikt_parsed_conn);
        assertNotNull(m_words);
        g = GraphCreator.createGraph(m_words);
        */
        
        // edge creation 2
        // for each TMeaning (which have wiki_text with wikified words):
        // get page<->wikified word, where type of relation = wiki
        
        // todo
        // ...
    }
}