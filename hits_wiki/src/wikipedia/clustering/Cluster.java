/*
 * Cluster.java The cluster-vertex class.

 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 *
 */

package wikipedia.clustering;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Description
 *
 */
abstract class Cluster {
            
    /** Edges tied this cluster with others */
    //protected ArrayList<Edge>   edges;
    protected Edge[]            edges;
    
    /** Number of merged edges by clusters, i.e. 
     * number of edges between vertices of the cluster. */
    protected int               edges_merged;
   
    /** The weight (size) of the cluster */
    protected int               weight; 
    
    /** Creates a new instance of Cluster */
    public Cluster() {
    }
    
    abstract String graphVizCluster();
    
    public String toString() {
	String s = getClass().getName();
        if(null != edges) {
            s += "; edges.len="+edges.length;
        }
        s += "; edges_merged="+edges_merged;
        s += "; weight=" + weight;
        return s;
    }
    
    /** Return the string like (for the graphviz .bat file): 
     *  :: clusters:11
     */
    public static String getStatistics (List<ClusterCategory> clusters) {
        return new String(":: clusters:" + clusters.size());
    }
        
    public void addEdge(Edge e) {
        Edge[] n;
        if(null == edges) {
            n = new Edge[1];
        } else {
            if(Arrays.asList(edges).contains(e))
                return;
            n = new Edge[edges.length + 1];
            for(int i=0; i<edges.length; i++)
                n[i] = edges[i];
        }
        n[n.length-1] = e;
        edges = n;
    }
    
    /** Add cluster c to this cluster. 
     * 1) Increase size of the cluster, 
     * 2) Increase number of merged edges, 
     * 3) Add all edges adjacent to c (with check: skip edge's repetition), update edges[].c1 and c2
     * 4) Remove edge (this, c)
     * 5) Update or remove edges of merged (deleted) cluster.
     * @return list of removed edges (belong to the cluster c)
     */
    public List<Edge> addCluster(Cluster c) {
        weight += c.weight;
        
        List<Edge>      remove_edges        = new ArrayList<Edge>();
        
        List<Cluster>   adjacent_clusters   = getAdjacentVertices();
        assert(!adjacent_clusters.contains(this));
        adjacent_clusters.remove(c);

        // remove edge (this, c)
        Edge edge_merged = getEdgeToCluster(c);
        edges = Edge.RemoveEdge(edges, edge_merged);
        
        // remove edge (c, this)
        assert(edge_merged == c.getEdgeToCluster(this));
        c.edges = Edge.RemoveEdge(c.edges, edge_merged);
        
        // update this.edges taking edges from merged cluster c
        {
            Edge[] c_edges = c.getAdjacentEdges();
            if(null != c_edges && 0 < c_edges.length) {
                ArrayList<Edge> all = new ArrayList<Edge>();
                if(null != edges)
                    all.addAll(Arrays.asList(edges));
                for(int i=0; i<c_edges.length; i++) {
                    Edge e = c_edges[i];
                    if (e!=edge_merged) {           // skip edge (c, this)
                        if(!e.containsVertices(adjacent_clusters)) {
                            all.add(e);
                        } 
                    }
                }
                edges = (Edge[])all.toArray(Edge.NULL_EDGE_ARRAY);
            }
        }
        
        // update edges.v1 and .v2 for clusters adjacent to c
        List<Cluster>   c_clusters   = c.getAdjacentVertices();
        if(null != c_clusters) {
            for(int i=0; i<c_clusters.size(); i++) {
                Edge r = c_clusters.get(i).updateEdgesOfMergedCluster(c, this);
                if (null != r) {
                    remove_edges.add(r);
                }
            }
        }
        
        edges_merged += remove_edges.size();
        return remove_edges;
    }
    
    /** Update or remove edges of merged (deleted) cluster.
     * Since adjacent cluster c_delete was added to cluster c (c_delete -> c),
     * then 1) if there are two edges (this, c_delete) and (this, c) then remove (this, c_delete).
     *      2) replace edges (this, c_delete) by (this, c);
     * @return list of removed edges or null
     */
    public Edge updateEdgesOfMergedCluster(Cluster c_delete, Cluster c) {
        if(null == edges)
            return null;
        assert (c_delete != c);
        Edge this_to_c = null;
        Edge this_to_c_delete = null;
        
        // search edge to c, and edge to c_delete
        for(int i=0; i<edges.length; i++) {
            Edge e = edges[i];
            
            if (e.containsVertex(c)) {
                this_to_c = e;
            }
            if (e.containsVertex(c_delete)) {
                this_to_c_delete = e;
            }
        }
            
        if (null != this_to_c && null != this_to_c_delete) {// 1) 
            edges = Edge.RemoveEdge(edges, this_to_c_delete);
            
            return this_to_c_delete;
        } else {
            if (null != this_to_c_delete) {                 // 2) 
                this_to_c_delete.replaceVertex(c_delete, c);
            }
        }
        return null;
    }
    
    /** Check whether the edge contains the sought cluster */
    public boolean containsAdjacent(Cluster sought) {
        if (sought == this)
            return true;
        for(int i=0; i<edges.length; i++) {
            if(edges[i].containsVertex(sought))
                return true;
        }
        return false;
    }
    
    /** Get list of adjacent clusters */
    public List<Cluster> getAdjacentVertices() {
        List<Cluster> result = new ArrayList<Cluster>();
        if(null != edges && 0 < edges.length) {
            for(int i=0; i<edges.length; i++) {
                Cluster c1 = edges[i].getVertex1();
                Cluster c2 = edges[i].getVertex2();
                result.add( ((c1 == this) ? c2 : c1) ) ;
            }
        }
        return result;
    }

    /** Get edge from adjacent edges with vertices (this, c) */
    public Edge getEdgeToCluster(Cluster c) {
        if(null == edges)
            return null;
        for(int i=0; i<edges.length; i++) {
            Edge e = edges[i];
            Cluster c1 = e.getVertex1();
            Cluster c2 = e.getVertex2();
            if (c1 == c || c2 == c)
                return e;
        }
        // unreachable code
        assert("Error in Cluster.getEdgeToCluster(): the non-adjacent cluster requested as adjacent" == "");
        return null;
    }
    
    /** Update weight of all adjacent edges */
    public void updateEdgesWeight() {
        if(null != edges) {
            for(int i=0; i<edges.length; i++) {
                edges[i].updateWeight();
            }
        }
    }

    /** Get array of edges adjacent to the vertex (cluster) */
    public Edge[] getAdjacentEdges() {
        return edges;
    }
    
    /** Get array of adjacent edges with the exception of the edge e */
    /*public List<Edge> getAdjacentEdgesWithoutEdge(Edge e_exception) {
        List<Edge> result = new ArrayList<Edge>();
        for(int i=0; i<edges.size(); i++) {
            Edge e = edges.get(i);
            if (e != e_exception)
                result.add(e);
        }
        return result;
    }*/

}








