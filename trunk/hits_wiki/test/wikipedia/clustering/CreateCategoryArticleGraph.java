/*
 * CreateCategoryArticleGraph.java
 *
 * Copyright (c) 2005 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.clustering;
import wikipedia.kleinberg.*;
import java.util.*;

/**
 * Class Description
 *
 */
public class CreateCategoryArticleGraph {
    
    public Map<Integer, Article>   articles;
    public Map<Integer, Category>  categories;
    
    private int []    category_id;
    private String [] category_title;
    private int []    article_id;
    private String [] article_title;
    private int [][] edge_category2category;
    private int [][] edge_category2article;
    
    /** Creates a new instance of CreateCategoryArticleGraph */
    public CreateCategoryArticleGraph() {
        init();
    }
    
    /** Test tree with categories and nodes
    4 category started from C_
    5 articles started from A_
     
               /--- C_All id=1------------------
              /        |    |                   \
     C_Religious id=2  |  C_Science id=3        C_Art id=4-.
        |          \   |   /      |    \          |         \
        |           A_Linux id=11 |     A_Smartphone id=13  |
        |                         |                         |
     A_God id=10                A_Palm id=12     A_Tolstoj id=14
      */      
    public void init () {
        int []    category_id_local    = {1,      2,           3,        4};
        String [] category_title_local = {"All", "Religious", "Science", "Art"};
        category_id     = category_id_local;
        category_title  = category_title_local;
        
        int []    article_id_local    = {10,     11,      12,     13,           14};
        String [] article_title_local = {"God", "Linux", "Palm", "Smartphone", "Tolstoj"};
        article_id      = article_id_local;
        article_title   = article_title_local;
        
        int [][] edge_category2category_local = { {1, 2}, {1,3}, {1,4} };
        int [][] edge_category2article_local = { {1, 11}, 
            {2,10}, {2,11},
            {3,11}, {3,12}, {3, 13},
            {4,13}, {4,14}
        };
        edge_category2category = edge_category2category_local;
        edge_category2article  = edge_category2article_local;
        setupArticlesAndCategories();
    }
    
    
    
    /** Test tree with categories and nodes
    4 articles started from A_
    6 category started from C_(W), where W - is the cluster(category) weight
    c.weight = 1 + n_article
    -W- - the weight of the clusters to be merged
    
     Merge 0: (initial) 6 clusters:
     
        (1)C8-2-C9(1)
           | 3 3 |\
           3  X  3 3
           |/  \ |   \
(2)C0-4-(2)C1-4-C2(2) C3(2)
   |       |     |     |
   A10     A11   A12   A13
     
     Merge 1: (merges clusters with minimal result weight): 5 clusters:
     
           C8C9(2)
           |     |\
           4     4 4
           |     |   \
(2)C0-4-(2)C1-4-C2(2) C3(2)
   |       |     |     |
   A10     A11   A12   A13
     
     Merge 2: 3 clusters:
     
                C8C9C3A13(4)    or              C8C9C1A11(4)
               /|                               /|\   
             8  6                             6  6 .__6__.
           /    |                           /    |        \
C0A10C1A11(4)-6-C2A12(2)             C0A10(2)-6-C2A12(2)  C3A13(2)
     
     Merge 3:                                   \|/
     
     
     
     
    */
    public void init2 () {
        int []    category_id_local    = {  8,    9,    0,    1,    2,    3};
        String [] category_title_local = {"C8", "C9", "C0", "C1", "C2", "C3"};
        category_id     = category_id_local;
        category_title  = category_title_local;
        
        int []    article_id_local    = {  10,    11,    12,    13};
        String [] article_title_local = {"A10", "A11", "A12", "A13"};
        article_id      = article_id_local;
        article_title   = article_title_local;
        
        int [][] edge_category2category_local = {
            {8, 9}, {8, 1}, {8,2},
            {9, 1}, {9,2}, {9,3},
            {1, 0}, {1,2} };
        int [][] edge_category2article_local = { 
            {0, 10}, 
            {1,11}, {2,12},
            {3,13}
        };
        edge_category2category = edge_category2category_local;
        edge_category2article  = edge_category2article_local;
        setupArticlesAndCategories();
    }
    
    public void setupArticlesAndCategories() {
        int i, s;
        Iterator it;
        
        categories = new HashMap<Integer, Category>();
        for (i=0; i<category_id.length; i++) {
            Category c = new Category();
            c.page_id    = category_id    [i];
            c.page_title = category_title [i];
            categories.put(c.page_id, c);
        }
        
        articles = new HashMap<Integer, Article>();
        for (i=0; i<article_id.length; i++) {
            Article a = new Article();
            a.page_id    =  article_id    [i];
            a.page_title =  article_title [i];
            articles.put(a.page_id, a);
        }
        
        // fill id_categories of articles
        {
            Map<Integer,List<Integer>> m = new HashMap<Integer,List<Integer>>();
            // 1. fill m
            for (i=0; i<edge_category2article.length; i++) {
                s = edge_category2article[i][1];
                if(!m.containsKey(s))
                    m.put(s, new ArrayList<Integer>());
                m.get(s).add(edge_category2article[i][0]);
            }
            // 2. copy m to a.id_categories
            for(it = m.keySet().iterator(); it.hasNext();) {
                Integer id = (Integer)it.next();
                List<Integer> l = m.get(id);
                Article a = articles.get(id);

                a.id_categories = new int[l.size()];
                for(i=0; i<l.size(); i++) {
                    a.id_categories[i] = l.get(i);
                }
            }
        }
        
        
        // fill links_in and links_out of categories
        
        Map<Integer,List<Integer>> m_out = new HashMap<Integer,List<Integer>>();
        Map<Integer,List<Integer>> m_in  = new HashMap<Integer,List<Integer>>();
        
        for (i=0; i<edge_category2category.length; i++) {
            //Category c;
            //c = categories.get( edge_category2category[i][0] ); // source category
            //c .  links_out.add( edge_category2category[i][1] );
            
            s = edge_category2category[i][0];
            if(!m_out.containsKey(s))
                m_out.put(s, new ArrayList<Integer>());
            m_out.get(s).add(edge_category2category[i][1]);
            
            //c = categories.get( edge_category2category[i][1] ); // destination category
            //c  .  links_in.add( edge_category2category[i][0] );
            
            s = edge_category2category[i][1];
            if(!m_in.containsKey(s))
                m_in.put(s, new ArrayList<Integer>());
            m_in.get(s).add(edge_category2category[i][0]);
        }
        for(it = m_out.keySet().iterator(); it.hasNext();) {
            Integer id = (Integer)it.next();
            List<Integer> l = m_out.get(id);
            Category c = categories.get(id);
            
            c.links_out = new int[l.size()];
            for(i=0; i<l.size(); i++) {
                c.links_out[i] = l.get(i);
            }
        }
        for(it = m_in.keySet().iterator(); it.hasNext();) {
            Integer id = (Integer)it.next();
            List<Integer> l = m_in.get(id);
            Category c = categories.get(id);
            
            c.links_in = new int[l.size()];
            for(i=0; i<l.size(); i++) {
                c.links_in[i] = l.get(i);
            }
        }
        
    }

}
