/*
 * Category.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import wikipedia.sql.PageTable;
import java.util.*;

/**
 * A structure for categories of articles.
 */

public class Category extends Node {
    
    /** Only links_in is valid */
    //public static final ArrayList<Integer> links_out = null;
    
    /** id of articles which refer to this category */
    public int[]   id_articles;
    
    private final static List<Category> NULL_CATEGORY_LIST  = new ArrayList<Category>(0);
    
    /** Creates a new instance of Category */
    public Category() {
        super.init();
        assert (null == links_out);
    }
    
    /** Gets number of articles which refer to this category */
    public int getIdArticlesLength() {
        if(null != id_articles)
            return id_articles.length;
        return 0;
    }
    
    /** Sorts categories by .getIdArticlesLength(), get first 'n_limit' entries.
     * @param categories the map <page_id of category, category object>
     * @param n_limit number of top categories will return
     */
    public static List<Category> sortByIdArticlesLength(Map<Integer, Category> categories,int n_limit)
    {
        if(null == categories || 0 == categories.size())
            return NULL_CATEGORY_LIST;
                
        List<Category> sorted = new ArrayList<Category>(categories.size());
        sorted.addAll( categories.values() );
        Collections.sort(sorted, ID_ARTICLES_LEN_ORDER);
        
        if(n_limit >= sorted.size()) {
            return sorted;
        }
        return sorted.subList(0, n_limit);
    }
    
    static final Comparator<Category> ID_ARTICLES_LEN_ORDER = new Comparator<Category>() {
        public int compare(Category n1, Category n2) {
            if (n1.getIdArticlesLength() > n2.getIdArticlesLength())
                return -1;
            return 1;
        }
    };
        
    
    /** Create line: "C1 [label=\"1287\\nРобот\n\\nx=12.0\ny=3.0\"];\n" */
    public String GraphVizNode() {
        //String s_form = ",shape=box";
        String s_form = ",shape=polygon,sides=4,distortion=.7";
                
        return new String("C" + page_id + " [label=\"" + 
                page_id + "\\n" + page_title +  "\"" +    // id and the name 
                s_form +                                // node's shape form
                "];\n");
    }

    /**
     * Create edges 1) for links between categories (links_out and links_in like "C1 -> C2;\n"
     *              2) for id_articles like "C1 -> W2;\n", C - category node, W - articles node 
     */
    public String GraphVizLinksIn() {
        String  result = "", bold_edge = "";
        int     i;
        if(null != links_in) {
            for(i=0; i<links_in.length; i++) {
                result += "C" + links_in[i] + " -> " + "C" + page_id + " [style=dotted,color=green]" + ";\n";
            }
        }
        if(null != links_out) {
            for(i=0; i<links_out.length; i++) {
                result += "C" + page_id + " -> " + "C" + links_out[i] + " [style=dotted,color=red]" + ";\n";
            }
        }

        /*
        for(i=0; i<id_articles.size(); i++) {
            result += "C" + page_id + " -> " + "W" + id_articles.get(i) + " [style=dotted,color=blue]" + ";\n";
        }*/
        
        return result;
    }
    
    /** Create edge (only for links_in) like "C1 -> C2;\n" */
    public String GraphVizLinksOut() {
        return GraphVizLinksIn();
    }
    
    
    public static int[] getIDByTitle(wikipedia.sql.Connect connect, List<String> titles) {
        List<Integer> l = new ArrayList<Integer>();
        
        for(String a : titles) {
            Integer id = PageTable.getCategoryIDByTitle(connect, a);
            if (0 == id) 
                continue;
            l.add(id);
        }
        return convertListInteger(l);
    }
    
    /** Converts list Integer to array int[]. */
    public static int[] convertListInteger(List<Integer> l) {
        
        if(null == l || 0 == l.size())
            return null;
        
        int[] a = new int[l.size()];
        for(int i=0; i<l.size(); i++)
            a[i] = l.get(i);
        
        return a;
    }
}
