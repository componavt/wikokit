/* TGraphCreator.java - Creates graph from Wiktionary parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wigraph;

import wikipedia.sql.Connect;
import wikt.sql.TRelation;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout.LengthFunction;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.ObservableGraph;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import edu.uci.ics.jung.graph.util.Graphs;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/** JUNG graph creator. Takes data from Wiktionary parsed database.
 */
public class GraphCreator {
    
    private final static String[] NULL_STRING_ARRAY = new String[0];
    
    public static Graph createGraph(String[] v, int[][] edges) {
        if(null == v) {
            return null;
        }

        // V (String) is a word
        // E (int) is an ID in the table 'relation'
        //Graph<String, Integer> ig = Graphs.<String, Integer>synchronizedGraph(new SparseGraph<String, Integer>());
        Graph<String, Integer> ig = new SparseGraph<String, Integer>();
        
        //Vertex[] v = new Vertex[labels.length];
        for (int i = 0; i < v.length; i++) {
            ig.addVertex(v[i]);
            //System.out.println("added node " + v[i]);
        }
        
        if(null != edges) {
            for(int i=0; i<edges.length; i++) {
                ig.addEdge(i, v[edges[i][0]], v[edges[i][1]]);
                //ig.addEdge(new Double(Math.random()), v[edges[i][0]], v[edges[i][1]], EdgeType.DIRECTED);
            }
        }
        return ig;
    }

    /** Creates graph from words relations.
     *
     * @param edges words relations,
     * e.g. car -> carriage, car -> automobile (synonyms)
     *      car -> vehicle (hyperohym)
     */
    public static Graph createGraph(Map<String,List<String>> edges, List<String> unique_words) {
        if(null == edges || 0 == edges.size()) {
            return null;
        }
        
        // prepare list of unique words - vertices
        /*System.out.println("  (preparing list of unique words - vertices)...");
        List<String> unique_words = new ArrayList();
        unique_words.addAll(edges.keySet());
        
        for(List<String> list_s : edges.values())
            for(String s : list_s) {
                if(!unique_words.contains(s))
                    unique_words.add(s);
            }*/
            
        // V vertex (String) is a word
        // E edge is an unique ID (skip: ID in the table 'relation')
        
        Graph<String, Integer> g = new SparseGraph<String, Integer>();

        //Vertex[] v = new Vertex[labels.length];
        System.out.println("  (adding vertices)...");
        for(String s : unique_words)
            g.addVertex(s);

        System.out.println("  (adding edges)...");
        int i = 0;
        for(String v1 : edges.keySet()) {
            List<String> list_v2 = edges.get(v1);
            for(String v2 : list_v2)
                g.addEdge(i++, v1, v2);
        }
        
        return g;
    }

    /** Filters source vertices, remains only vertices which are belong to the graph 'g'.
     * 
     * @param g
     * @param test_vertices
     * @return vertices which are belong to the graph 'g'
     */
    public static String[] getOnlyVertexInGraph(Graph<String, Integer> g,String[] source_vertices) {
        if(null == source_vertices || 0 == source_vertices.length)
            return NULL_STRING_ARRAY;

        List<String> vertices = new ArrayList<String>();

        for(String s : source_vertices)
            if(g.containsVertex(s))
                vertices.add(s);

        return (String[])vertices.toArray(NULL_STRING_ARRAY);
    }

    public static final String[] labels_samolyot = {
            // 0-8 vertices
            // from "самолёт" to others
            "самолёт",          // 0
            "аэроплан",         // 1    synonym
            "воздушный лайнер", // 2    synonym
            "авиация",          // 3    hyperonym
            "транспорт",        // 4    hyperonym

            // from "транспорт" to others
            "шлюз",             // 5    synonym
            "гейт",             // 6    synonym

            // from "авиация" to others
            "воздухоплавание",  // 7    synonym
        };
    public static final int[][] edges_samolyot = {
        {0,1}, {0,2}, {0,3}, {0,4}, // from "самолёт" to others
        {4,5}, {4,6},               // from "транспорт" to others
        {3,7}                      // from "авиация" to others
    };
}
