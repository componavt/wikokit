/*
 * Edge.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.clustering;

import wikipedia.kleinberg.*;
import java.util.Map;
import java.util.List;
import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Class Description
 *
 */
public class Edge {

    public final static Edge[]   NULL_EDGE_ARRAY  = new Edge[0];
    
    /** Cluster-vertex 1 of the edge */
    private Cluster c1;
    
    /** Cluster-vertex 2 of the edge */
    private Cluster c2;
    
    /** The weight of the edge equals to the sum of clusters c1 and c2 */
    private int weight;
    
    /** Creates a new instance of Edge */
    public Edge() {
    }
    
    public String toString() {
	String s = getClass().getName() + "";
        s += " weight=" + weight;
        if(null != c1) {
            s += " c1:" + c1.toString();
        }
        if(null != c2) {
            s += " c1:" + c2.toString();
        }
        return s;
    }
    
    public void init(int category_id_from, int category_id_to, Map<Integer, ClusterCategory> category_id_to_cluster) {
        //assert(category_id_to_cluster.containsKey(category_id_from));
        //assert(category_id_to_cluster.containsKey(category_id_to));
        c1 = category_id_to_cluster.get(category_id_from);
        c2 = category_id_to_cluster.get(category_id_to);
        updateWeight();
        c1.addEdge(this);
        c2.addEdge(this);
    }
    
    public void updateWeight(){
        /*int w1 = 0, w2 = 0;
        if(null == c1) {
            System.out.println("Edge.updateWeight() error c1 is null, where edge is " + toString());
        } else {
            w1 = c1.weight;
        }
        if(null == c2) {
            System.out.println("Edge.updateWeight() error c2 is null, where edge is " + toString());
        } else {
            w2 = c2.weight;
        }
        weight = w1 + w2;*/
        weight = c1.weight + c2.weight;
    }
    
    public boolean containsVertex(Cluster sought) {
        if (sought == c1 || sought == c2)
            return true;
        return false;
    }
    
    /** Return true if clusters contain c1 or c2 */
    public boolean containsVertices(List<Cluster> clusters) {
        for(int i=0; i<clusters.size(); i++) {
            if (clusters.get(i) == c1 || clusters.get(i) == c2)
                return true;
        }
        return false;
    }
    
    public static final Comparator<Edge> WEIGHT_ORDER = new Comparator<Edge>() {
        public int compare(Edge e1, Edge e2) {
            if (e1.weight <= e2.weight)
                return -1;
            return 1;
        }
    };
    
    public int getWeight() {
        return weight;
    }
    
    public Cluster getVertex1() {
        return c1;
    }
    
    public Cluster getVertex2() {
        return c2;
    }
    
    /** Merge cluster c2 to cluster c1.
     * The following actions are performed<BR>
     * I) by cluster-vertex:
     *      1) Increase size of the cluster, 
     *      2) Increase number of merged edges, 
     *      3) Add all edges adjacent to c (with check: skip edge's repetition), update edges[].c1 and c2
     *      4) Remove edge (this, c)
     *      5) Update or remove edges of merged (deleted) cluster.
     *      6) Increase number of articles
     *      7) Add categories of added cluster<BR>
     * II) by edge: update weight of all edges adjacent to c1
     * Remark, after this func execution the cluster c2 should be deleted, returned edges should be deleted.
     *
     *  @return     array of edges to be removed
     */
    public List<Edge> Merge() {
        List<Edge> remove_edges = c1.addCluster(c2);
        c1.updateEdgesWeight();
        
        remove_edges.add(this);
        return remove_edges;    // remove these edges
    }
    
    /** Replace old vertex by new */
    public void replaceVertex(Cluster c_old, Cluster c_new) {
        if(c_old == c_new)
            return;
        assert(c_old == c1 || c_old == c2);
        if (c1 == c_old)
            c1 = c_new;
        else
            c2 = c_new;
    }
    
    /** Remove edge e from the array edges, return new array. */
    public static Edge[] RemoveEdge(Edge[] edges,Edge e) {
        if(null == edges || 0 == edges.length || e == null)
            return edges;
        
        if(1 >= edges.length) {
            if(edges[0] == e) {
                return null;
            } else {
                return edges;
            }
        }
        
        // new edges = edges - e
        List<Edge> n = new ArrayList<Edge>();
        for(int i=0; i<edges.length; i++) {
            if(e != edges[i])
                n.add(edges[i]);
        }
        return (Edge[])n.toArray(NULL_EDGE_ARRAY);
    }
    
}




