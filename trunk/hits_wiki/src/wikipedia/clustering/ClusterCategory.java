/*
 * ClusterCategory.java
 *
 * Copyright (c) 2005 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.clustering;
import wikipedia.kleinberg.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Class Description
 *
 */
public class ClusterCategory extends Cluster {
    
    /** Number of articles which covered by the cluster.
     * Cluster consist of categories, categories link to the articles.
     */
    protected int                  n_articles;

    /** Vertices-categories of the cluster */
    //protected ArrayList<Integer>  categories_id;
    protected int[]     categories_id;
    
    /** Creates a new instance of ClusterCategory */
    public ClusterCategory() {
        //categories_id  = new ArrayList<Integer>();
    }
    
    public void init(Category category) {
        // number of articles in the cluster
        n_articles = (null == category.id_articles) ? 0 : category.id_articles.length;
        
        weight = 1 + n_articles;
        
        // add new one category to the cluster
        //categories_id.add( category.page_id );       
        categories_id = new int[1];
        categories_id[0] = category.page_id;
        
    }

    /** Create map from category id to cluster */
    public static Map<Integer, ClusterCategory> mapCategoryIdToCluster(List<ClusterCategory> clusters) {
        Map<Integer, ClusterCategory> category_id_to_cluster = new HashMap<Integer, ClusterCategory>();
        for(int i=0; i<clusters.size(); i++) {
            ClusterCategory cluster = clusters.get(i);
            for(int j=0; j<cluster.categories_id.length; j++) {
                // one category belongs only to one cluster
                assert(!category_id_to_cluster.containsKey( cluster.categories_id[j] ));
                category_id_to_cluster.put( cluster.categories_id[j], cluster);
            }
        }
        return category_id_to_cluster;
    }
    
    /** Add cluster c to this cluster. 
     * 1) Increase size of the cluster, 
     * 2) Increase number of merged edges, 
     * 3) Add all edges adjacent to c (with check: skip edge's repetition), update edges[].c1 and c2
     * 4) Remove edge (this, c)
     * 5) Update or remove edges of merged (deleted) cluster.
     * 6) Increase number of articles
     * 7) Add categories of added cluster
     * @return list of removed edges (belong to the cluster c)
     */
    public List<Edge> addCluster(Cluster c_source) {
        int i;
        ClusterCategory c = (ClusterCategory)c_source;
                
        // 4) Increase number of articles
        n_articles += c.n_articles;
                
        // 5) Add categories of added cluster (without repetitions)
        // all[] categories = categories_id[] + c.categories_id[]
        int[] all = new int[categories_id.length + c.categories_id.length];
        for(i=0; i<categories_id.length; i++) {
            all[i] = categories_id[i];
        }
        for(i=0; i<c.categories_id.length; i++) {
            assert (!Arrays.asList(categories_id).contains(c.categories_id[i])); // categories belong to different clusters
            all[categories_id.length + i] = c.categories_id[i];
        }
        categories_id = all;

        return super.addCluster(c);
    }
    
    // GraphViz functions
    public String getClusterName() {
        return "cluster" + getClusterNumber();
    }
    /** The rule is used that every category belongs only to one cluster */
    public int getClusterNumber() {
        return categories_id[0];
    }
    
    /** Dump cluster of categories to GraphViz.
     * Create text: 
     * subgraph cluster10175 {  // Cluster name is "cluster" + category_id[0]
     *  node [style=filled];
     *  label = "cluster10175: weight=12";
     *  color=blue;
     *  C10175;                 // these are id of categories
     *  C8100;                  //   which belong to the cluster
     *
     *  // articles list
     *  A10175 [label="Articles list\n10578 ArticleName1 x=1.0 y=1.0\n" +
     *                               "98927 ArticleName2 x=0.0 y=1.0", shape=box];
     * }\n
     */
    public String graphVizCluster() {return "";};
    public String graphVizCluster(Map<Integer, Article> articles, Map<Integer, Category> categories)
    {
        String s =  "subgraph " + getClusterName() + " {\n" +
                    "  node [style=filled];\n" +
                    "  label = \"" + getClusterName() + ": weight="+ weight +"\";\n" +
                    "  color=blue;\n";
        
        for (int i=0; i<categories_id.length; i++) {
            s += "  C" + categories_id[i] + ";\n";
        }
        
        if(null != articles && null != categories) {
            // dump articles list
            // GetUniqueArticles
            s += "  A" + getClusterNumber() +  " [label=\"Articles in the cluster: N\n";
            for (int i=0; i<categories_id.length; i++) {
                Category c = categories.get( categories_id[i] );

                if(null != c.id_articles) {
                    for(int j=0; j<c.id_articles.length; j++) {
                        Article a = articles.get( c.id_articles[j] );
                        if(null != a) {
                            s += a.page_id + " " + a.page_title + " x=" + a.x + " y=" + a.y + "\n";
                        }
                    }
                }
            }
            s += "\",shape=box];\n";
        }
        s += " }\n";
        return s;
    }
    
    /** Print edges between clusters with the help of links between categories (which belong to these clusters).
     *  Create text: "C8100 -> C10175 [ltail=cluster8100,lhead=cluster10175];\n"
     */
    public String graphVizClusterEdges() {
        String s = "";
        List<Cluster> adjacent_clusters = getAdjacentVertices();
        if(null != adjacent_clusters) {
            for(int i=0; i<adjacent_clusters.size(); i++) {
                int n = ((ClusterCategory)adjacent_clusters.get(i)).getClusterNumber();
                s += "C" + getClusterNumber() + " -> C" + n + 
                        " [ltail=cluster" + getClusterNumber() + ",lhead=cluster" + n + ",dir=none];\n";
            }
        }
        return s;
    }
}



