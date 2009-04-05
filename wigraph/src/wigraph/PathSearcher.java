/* PathSearcher.java - searches path on a graph.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wigraph;

import java.util.List;

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

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.util.Pair;

/** Path searcher on a graph.
 */
public class PathSearcher {

    private final static String[] NULL_STRING_ARRAY = new String[0];

    /** Finds path from word1 to word2.
     *
     * @param g     source graph with defined words and relations between words
     * @param word1 source word
     * @param word2 destination word
     * @return ordered array of words from word1 to word2, or empty array if there is no path
     */
    public static String[] getShortestPath(Graph<String, Integer> g, DijkstraShortestPath<String,Integer> alg, String word1, String word2) {

//System.out.println("getShortestPath: word1=" + word1 + "; word2=" + word2);

        if(!g.containsVertex(word1)) {
            System.out.println("Warning: (PathSearcher.getShortestPath) graph has no word1=" + word1);
            return NULL_STRING_ARRAY;
        }
        if(!g.containsVertex(word2)) {
            System.out.println("Warning: (PathSearcher.getShortestPath) graph has no word2=" + word2);
            return NULL_STRING_ARRAY;
        }
        //if(null == TPage.get(ruwikt_parsed_conn, word1)) { word2
        //}

        List<Integer> l_edges = alg.getPath(word1, word2);
        if(l_edges.size() == 0)
            return NULL_STRING_ARRAY;

        String[] l_words = new String[l_edges.size() + 1];

        String v_prev, v;
        l_words[0] = word1;
        v = word1;
        int i=1;
        for(Integer edge_num : l_edges) {
            v_prev = v;
            v = g.getOpposite(v_prev, edge_num);
            l_words[i++] = v;
        }
        return l_words;
    }

    /** Calculates an average shortest path from words set 1 to word set 2,
     * i.e. average length of shortest paths from each word of a set 1 to each word of a set 2.
     *
     * @param g    source graph with defined words and relations between words
     * @param set1 source word set
     * @param set2 destination word set
     * @return an average shortest path
     */
    public static DistanceData calcPathLenRelatedness(Graph<String, Integer> g,
            DijkstraShortestPath<String,Integer> alg, String[] word_set1, String[] word_set2) {

        float average;
        float min = Float.MAX_VALUE;
        float max = 0f;

        int n_pairs = 0;
        int path_len = 0;
        for(String word1 : word_set1) {
            for(String word2 : word_set2) {
                String[] path = PathSearcher.getShortestPath(g, alg, word1, word2);
                if(0 != path.length) {
                    n_pairs ++;
                    path_len += path.length;

                    float inverse = 1f / path.length;
                    if(inverse > max)
                        max = inverse;
                    if(inverse < min)
                        min = inverse;
                } else
                    max = 1f;
            }
        }
        average = 0f;
        if(n_pairs > 0)
            average = (float)n_pairs/path_len;

        return new DistanceData(average, min, max);
    }    
}
