/*
 * CategorySet.java - Altorithms to manipulate category map.
 * This map is a network (or DCEL) of categories (parents) of articles.
 * 
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import wikipedia.language.Encodings;
import wikipedia.util.StringUtilRegular;
import wikipedia.clustering.*;
import java.util.*;

public class CategorySet {
    
    /** Creates a new instance of CategorySet */
    public CategorySet() {
    }
    
    /** Finds id of articles which refer to the category.  
     * Fill the field int[] Category.id_articles.
     * Information from articles.id_categories are used.
     */
    public static void fillLinksFromCategoryToArticles(Map<Integer, Article> articles,
                                                     Map<Integer, Category>  categories) {
        int     j;
        
        // 1. Fill map m with ArrayList<Integer>.
        //    The local map from category id to list of articles id
        Map<Integer,List<Integer>> m = new HashMap<Integer,List<Integer>>();
        
        for(Iterator it = articles.values().iterator(); it.hasNext();) {
            Article a = (Article)it.next();
            
            for(j=0; null != a.id_categories && j<a.id_categories.length; j++) {
                Category c = categories.get(a.id_categories[j]);
                if(null != c) {
                    List<Integer> l;
                    if(m.containsKey(c.page_id)) {
                        l = m.get(c.page_id);
                    } else {
                        // initialize map
                        l = new ArrayList<Integer>();
                        m.put(c.page_id, l);
                    }

                    // check uniqueness of articles id and add it to the category
                    if(!l.contains(a.page_id))
                        l.add(a.page_id);
                }
            }
        }
        
        // 2. Create int[] id_articles of categories
        for(Iterator it = m.keySet().iterator(); it.hasNext();) {
            // id is category_id
            Integer id = (Integer)it.next();
            List<Integer> l = m.get(id);
            Category c = categories.get(id);
            
            c.id_articles = new int[l.size()];
            for(j=0; j<l.size(); j++) {
                c.id_articles[j] = l.get(j);
            }
        }
    }
        
    /**
     *  Categories already partly created and stored in session.category_nodes.
     * In order to have connected DCEL, it is need to create (here) .links_out 
     * field, and create links from categories to the articles.
     */
    public static void prepareCategories (SessionHolder session, 
                        Map<Integer, Article> articles) {
        
        // add categories to the source article,
        // i.e. add to articles(session.source_article_id) the category
        int id = session.source_article_id;
        if(null != articles.get(id)) {
            
            List<String> titles_level_1_cats = new ArrayList<String>();
            session.category_black_list.inBlackList(id, titles_level_1_cats, session.source_article_id);
            articles.get(id).id_categories = Category.getIDByTitle(session.connect, titles_level_1_cats);
            
            /* todo del
            ArrayList<Integer> first_level_categories = new ArrayList<Integer>();
            session.category_black_list.inBlackList(id, first_level_categories);
            
            //articles.get(id).id_categories = first_level_categories;
            int[] a = new int[first_level_categories.size()];
            for(int i=0; i<first_level_categories.size(); i++)
                a[i] = first_level_categories.get(i);
            articles.get(id).id_categories = a;
             */
        }
        
        CreateLinksOutByLinksIn (session.category_nodes);
        
        if (null != session.dump) { 
            String  s = StringUtilRegular.encodeRussianToLatinitsa(session.source_page_title, Encodings.enc_java_default, Encodings.enc_int_default);
            session.dump.DotOpen(s + "_01_category.dot");
            
            Article.bdraw_categories = true;
            session.dump.Dump(articles, "Article  nodes");
            
            session.dump.Dump(session.category_nodes, "Category nodes");
            session.dump.BatEnd();
        }
    }
    
    
    /** 
     * Create clusters of categories.
     *  @param max_cluster_weight   the maximum allowed clusters' weight (size)
     */
    public static List<ClusterCategory> getCategoryClusters (
                                Map<Integer, Category> categories,
                                Map<Integer, Article> articles, int max_cluster_weight) {
        // Preprocessing.
        fillLinksFromCategoryToArticles(articles, categories);
        List<ClusterCategory> clusters  = Preprocessing.createInitialClusters      (articles, categories);
        List<Edge>            edges     = Preprocessing.createEdgesBetweenClusters (clusters, categories);

        // Clustering algorithm
        Collections.sort(edges, Edge.WEIGHT_ORDER);
        int i=0;
        while(0<edges.size() && max_cluster_weight >= edges.get(0).getWeight()) {
            Edge e = edges.get(0);
            /*if(i++ == 113) {
                int z = 1;
            }*/
//System.out.println ("i="+i);
            // merge e.c2 to e.c1
            List<Edge> remove_edges = e.Merge();
            
            // remove edges and remove cluster c2
            clusters.remove( e.getVertex2() );
            edges.removeAll( remove_edges );
            
            // re-sort edges: 
            Collections.sort(edges, Edge.WEIGHT_ORDER);
        }
        return clusters;
    }
    
    /** Articles are presented as separated nodes */
    public static void dumpClusterCategoryArticle(SessionHolder session, Map<Integer, Article> articles,
                                                List<ClusterCategory> clusters, String filename_suffix) {
        if (null != session.dump) { 
            String  s = StringUtilRegular.encodeRussianToLatinitsa(session.source_page_title, Encodings.enc_java_default, Encodings.enc_int_default);
            session.dump.DotOpen(s + "_" + filename_suffix + ".dot");
            session.dump.DumpCluster(null, null, clusters, "Clusters");
            session.dump.Dump(articles,                 "Article  nodes");
            session.dump.Dump(session.category_nodes,   "Category nodes");
            session.dump.BatEnd();
        }
    }
    
    /** Articles are presented within cluster box */
    public static void dumpClusterCategorywithListArticles(SessionHolder session, Map<Integer, Article> articles,
                                                List<ClusterCategory> clusters, String filename_suffix) {
        if (null != session.dump) { 
            String  s = StringUtilRegular.encodeRussianToLatinitsa(session.source_page_title, Encodings.enc_java_default, Encodings.enc_int_default);
            session.dump.DotOpen(s + "_" + filename_suffix + ".dot");
            session.dump.DumpCluster(articles, session.category_nodes, clusters,  "Clusters");
            //session.dump.Dump(articles,                 "Article  nodes");
            session.dump.Dump(session.category_nodes,   "Category nodes");
            session.dump.BatEnd();
        }
    }
    
    
    /** 
     * Create DCEL of categories.
     * Dump it to GraphViz dot file.
     *  @param session.categories_max_steps  the maximum allowed number of passed categories (in iterative search)
     *  @param max_levels                    the maximum allowed level of extracted categories
     *  @return the hashmap of nodes which are categories
     */
    /*public HashMap<Integer, Article> Create(SessionHolder session, HashMap<Integer, Article> article_nodes, int max_levels) {
        
        if (null == base_nodes)
            return null;
        
        HashMap<Integer, Article> category_nodes = null;
        
        // REFORMULATE
        // for each article_nodes
        // get list of categories (limited)
        // with links 1) from category to article and vice versa
        //            2) from category to (and from) category
        // two kind of links: bottom-link (cl_from) and up-link (cl_to)
        
        
        return category_nodes;
         
        return null;
    }

    /**
     * Goal
     *  - save only categories, which ties different articles,
     *  - remove set of categories, which belong to only one category.
     * Algorithm
     *  Input: DCEL = Category + Articles
     *  1. FOR each article A
     *  2.      FOR each category C of article A
     *  3.          IF C is not marked THEN
     *  4.              start depth-first search from C, store categories to the set S;
     *  5.              IF the article B was encountered AND B<>A THEN mark categories S
     *  6.              ELSE remove categories S
     *  7.          ENDIF
     *  8.      ENDFOR
     *  9.  ENFOR
     */
    /*public HashMap<Integer, Category> removeDanglingVertices (SessionHolder session, 
                                                        HashMap<Integer, Category> categories)
    {
        
        return null;
    }*/

    
    /** 
     * It is supposed that the map categories have valid links in the list link_in, 
     * these lists are used here to create the lists link_out.
     */
    public static void CreateLinksOutByLinksIn (Map<Integer, Category> categories)
    {
        for(Iterator it = categories.values().iterator(); it.hasNext();) {
            Category c = (Category)it.next();
            if(null != c.links_in) {
                c.links_out = new int[c.links_in.length];
                System.arraycopy(c.links_in, 0, c.links_out, 0, c.links_in.length);
            }

            /*for(Iterator it2 = c.links_in.iterator(); it2.hasNext();) {
                Integer id = (Integer)it2.next();
                assert (categories.containsKey(id));
                Category updating = categories.get(id);
                if (!updating.links_out.contains(c.page_id))
                    updating.links_out.add(c.page_id);
            }*/

        }
    }
    
    
    
    /** //
     * Set value Article.id_categories[] for each article nodes
     */
    /*public void SetCategory (SessionHolder session, HashMap<Integer, Article> article_nodes) {
        
    }*/
    
}
