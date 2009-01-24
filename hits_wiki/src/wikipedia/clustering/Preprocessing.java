/*
 * Preprocessing.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.clustering;

import wikipedia.kleinberg.*;
import java.util.*;

/**
 * Class Description
 *
 */
public class Preprocessing {
    
    /** Creates a new instance of Preprocessing */
    public Preprocessing() {
    }
    
    /** Build initial clusters of categories. 
     * In the beginning every cluster consists of one node (category).
     * Assign to each cluster the following values (using information about categories in the cluster):
     *      1) c.n_article - number of articles which refer to the cluster (categories in the cluster); 
     *      2) c.weight = 1 + n_article  
     *      3) c.category_id[0] = id of added category (first and alone as yet)
     */          
    public static List<ClusterCategory> createInitialClusters (Map<Integer, Article> articles,
                                                               Map<Integer, Category> categories) {
        List<ClusterCategory> clusters = new ArrayList<ClusterCategory>(Arrays.asList( new ClusterCategory[categories.size()] ));
        int i=0;
        for(Iterator it = categories.values().iterator(); it.hasNext();) {
            ClusterCategory c = new ClusterCategory();
            c.init( (Category)it.next() );
            clusters.set(i++, c);
        }
        return clusters;
    }

    /** Create initial edges. 
     * Create edge e between clusters c1 and c2 for each edge between categories. 
     * Calculate weight for edge e: e.weight = c1.weight + c2.weight
     */
    public static List<Edge> createEdgesBetweenClusters (List<ClusterCategory> clusters,
                                                         Map<Integer, Category> categories) {
        List<Edge> edges = new ArrayList<Edge>();
        
        // this map helps to skip repeated (id_from, id_to) then edges will be unique
        Map<Integer, Integer>         category_id_from_to = new HashMap<Integer, Integer>();
        
        // speed-up search of clusters using this local map
        Map<Integer, ClusterCategory> category_id_to_cluster = ClusterCategory.mapCategoryIdToCluster(clusters);
        
        for(Category cat:categories.values()) {
            int id_from = cat.page_id;
            
            assert(null != category_id_to_cluster.get(id_from));
            if(null != cat.links_out && 
               null != category_id_to_cluster.get(id_from))
            {
                for(int id_to:cat.links_out) {         // links_out: id of categories which are referred by the category
                    // add only unique edge (id_from, id_to)
                    if ( (!category_id_from_to.containsKey(id_from) ||
                          id_to != category_id_from_to.get(id_from)   )
                                            &&
                         (!category_id_from_to.containsKey  (id_to) ||
                          id_from != category_id_from_to.get(id_to)   ) 
                       ) {
                  
                        assert(null != category_id_to_cluster.get(id_to));
                        // skip absent categories:
                        if(null != category_id_to_cluster.get(id_to))
                        {
                            category_id_from_to.put(id_from, id_to);
                            Edge e = new Edge();
                            e.init(id_from, id_to, category_id_to_cluster);
                            edges.add(e);
                        }
                    }
                }
            }
        }
        return edges;
    }
}

