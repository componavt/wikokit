/*
 * Article.java - The class holds information about the article node and links-in and links-out
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import wikipedia.data.ArticleIdAndTitle;
import wikipedia.util.*;
import java.util.*;

public class Article extends Node {
    
    public NodeType     type;
    
    public  final static Article[]           NULL_ARTICLE_ARRAY           = new Article[0];
    private final static int    []           NULL_INTEGER_ARRAY           = new int    [0];
    //public final static String[]           NULL_STRING_ARRAY            = new String[0];
    
    
    /** x[p] := sum of y[q], for all q pointing to p, i.e. authority weight of p */
    public float        x;      //public ArrayList<Integer>       links_in;
    public float        x_new;
    
    /** y[q] := sum of x[q], for all q pointed to by p; i.e. hub weight of p */
    public float        y;
    public float        y_new;      //public ArrayList<Integer>       links_out;
    
    //public int          count_l_from;   // SELECT COUNT(*) FROM links WHERE l_from=page_id
    //public int          count_l_to;     // count_l_from > links_in.length
    
    /** The categories data will be printed to GraphViz .dot file if it is true */
    public static boolean bdraw_categories = false;
    
    /** id of categories for this article */
    //public ArrayList<Integer>   id_categories;
    public int[]    id_categories;
    
    
    /** true for redirect page, it corresponds to page.page_is_redirect in MySQL.
     * This is not NodeType, since redirect page can be (or not) rated as synonym
     */
    //public boolean is_redirect;
    
    /** Titles (and id) of redirect pages, analog of "What links here" in WP */
    public List<ArticleIdAndTitle> redirect;
    
    // is redirect:
    // todo
    // see:
    // 1. createArticleWithCategories: id < 0
    // 2. CategoryBlackList.DeleteUsingBlackList: id = PageTable.getIDByTitleNamespace
    // 3. Links.getAllLinksFromNodes: id_from < 0
    // 4. CategoryBlackList.inBlackList:0 < cl_from
    
    
    public Article() {
        super.init();
        redirect = ArticleIdAndTitle.NULL_ARTICLEIDANDTITLE_LIST;
        type= NodeType.DEFAULT;     // unknown and initial type's value
        x   = 1.f;
        y   = 1.f;
    }

    
    /** Creates map &lt;page_id, reference-to-class-Article> */
    public static Map<Integer, Article> createMapIdToArticleWithoutRedirects(Article[] nodes) {
        Map<Integer, Article> m = new HashMap<Integer, Article>();          // nodes.length);
        
        for(int i=0; i<nodes.length; i++) {
            if(nodes[i].page_id > 0) {
                m.put(nodes[i].page_id, nodes[i]);
            }
        }
        return m;
    }
    
    
    /** Creates map &lt;page_title, reference-to-class-Article> for articles 
     * with positive id, i.e. non-redirects.
     */
    public static Map<String, Article> createMapTitleToArticleWithoutRedirects(Article[] nodes) {
        // -XX:+HeapDumpOnOutOfMemoryError -mn256m -mx512m
        Map<String, Article> m = new HashMap<String, Article>();     // nodes.length
        
        for(int i=0; i<nodes.length; i++) {
            if(nodes[i].page_id > 0) {
                m.put(nodes[i].page_title, nodes[i]);
            }
        }
        return m;
    }
    
    
    
    // GraphViz functions -------------------------------------------
    //
    /** Create line: "W1 [label=\"1287\\nРобот\n\\nx=12.0\ny=3.0\"];\n" */
    public String GraphVizNode() {
        String s_form = "";
        switch (type.toInt()) {
            case -2: s_form = ",shape=box,style=filled,fillcolor=yellow"; break;        // hubs
            case -1: s_form = ",shape=polygon,sides=4,peripheries=3"; break;            // source
            case  0: s_form = ",shape=invtriangle,style=filled,fillcolor=grey"; break;  // selected synonyms
            case  1: s_form = ",shape=box"; break;
        }
        
        return new String("W" + page_id + " [label=\"" + 
                page_id + "\\n" + page_title +            // id and the name 
                "\\nx=" + x + "\\ny=" + y + "\"" +      // x and y values
                s_form +                                // node's shape form
                "];\n");
    }
    
    /**
     * Create edges 1) for links_out like "W1 -> W2;\n"
     *          2) for id_categories like "W1 -> C2;\n", W - main articles (nodes), C - category articles
     */
    public String GraphVizLinksOut() {
        String  result = "", bold_edge = "";
        int     i;
        if(null != links_out) {
            if (NodeType.HUB == type)
                bold_edge = " [style=bold]";
            for(i=0; i<links_out.length; i++) {
                result += "W" + page_id + " -> " + "W" + links_out[i] + bold_edge + ";\n";
            }
        }
        if( bdraw_categories && null != id_categories)
        {
            for(i=0; i<id_categories.length; i++) {
                result += "W" + page_id + " -> " + "C" + id_categories[i] + " [style=dotted]" + ";\n";
            }
        }
        return result;
    }
    // eo GraphViz functions -----------------------------------------
    
    
    /** Joins unique articles from nodes and addend. */
    public static Article[] joinUnique(Article[] nodes, Article[] addend) {
        int     size; // number of unique nodes in 
        int     i, j;
        
        if (null == addend || 0 == addend.length)
            return nodes;
        if (null == nodes)
            return addend;
                
        boolean[] b_addend = new boolean[addend.length];
        
        // calculate size of new array
        for (j=0; j<addend.length; j++) {
            b_addend[j] = true;
            next_addend:
            for (i=0; i<nodes.length; i++) {
                if (  addend[j].page_id == nodes[i].page_id) {
                    b_addend[j] = false;
                    break next_addend;
                }
            }
        }
        size = nodes.length;
        for (j=0; j<b_addend.length; j++) {
            size += (b_addend[j] ? 1 : 0);
        }
        
        // fill new array
        Article[] result = new Article[size];
        for (i=0; i<nodes.length; i++) {
            result[i] = nodes[i];
        }
        int add_index = nodes.length;
        for (i=0; i<addend.length; i++) {
            if(b_addend[i])
                result[add_index ++] = addend[i];
        }
        return result;
    }

    
    /**
     * Types: 0 - source article, 1 - root, 2 - base set, 3 - uknnown and initial value
     * Rule: type value can decrease, but it do not increase.
     */
    public static void SetType (Article[] nodes, NodeType type) {
        int     i;
        if (null == nodes)
            return;
        for (i=0; i<nodes.length; i++) {
            if (nodes[i].type.toInt() > type.toInt())
                nodes[i].type = type;
        }
    }
    
    
    /** x[p] := sum of y[q], for all q pointing to p
     *  @param links_in
     *  @return Return absolute value of difference between old and new value
     */
    public void CalculateNewX(Map<Integer, Article> nodes, int n_nodes) {
        x_new = 0.f;
        if(null != links_in) {
            for (int i=0; i<links_in.length; i++) {
                x_new += nodes.get( links_in[i] ).y;
            }
        }
    }
    
    /** y[q] := sum of x[q], for all q pointed to by p
     *  @param links_out
     */
    public void CalculateNewY(Map<Integer, Article> nodes, int n_nodes) {
        y_new = 0.f;
        if(null != links_out) {
            for (int i=0; i<links_out.length; i++) {
                y_new += nodes.get( links_out[i] ).x;
            }
        }
    }
    
    /*
     public static void NormalizeXY (HashMap<Integer, Article> nodes) {
        float           links_in_number, sum_x, sum_y;
        Iterator<Article>  it;
        
        // get sum x and sum y for each node in nodes
        sum_x = 0.f;
        sum_y = 0.f;
        it = nodes.values().iterator();
        while (it.hasNext()) {
            Article node = it.next();
            sum_x += node.x;
            sum_y += node.y;
        }
        
        // Normalize x and y values via sum_x and sum_y
        it = nodes.values().iterator();
        while (it.hasNext()) {
            Article node = it.next();
            node.x = node.x / sum_x;
            node.y = node.y / sum_y;
        }
    }
     */
    public static void NormalizeNewXNewY (Map<Integer, Article> nodes) {
        float           links_in_number, sum_x, sum_y;
        Iterator<Article>  it;
        
        // get sum x and sum y for each node in nodes
        sum_x = 0.f;
        sum_y = 0.f;
        it = nodes.values().iterator();
        while (it.hasNext()) {
            Article node = it.next();
            sum_x += node.x_new;
            sum_y += node.y_new;
        }
        
        // Normalize x_new and y_new values via sum_x and sum_y
        it = nodes.values().iterator();
        while (it.hasNext()) {
            Article node = it.next();
            node.x_new = node.x_new / sum_x;
            node.y_new = node.y_new / sum_y;
        }
    }
    
    /*
    public void UpdateXY() {
        x = x_new;
        y = y_new;
    }*/
    
    // get x and y total change (error)
    public float[] UpdateXY(Map<Integer, Article> nodes) {
        
        float[] total_error = new float[2];
        total_error[0] = 0f;    // x total incrementation
        total_error[1] = 0f;    // y
        
        Iterator<Article> it = nodes.values().iterator();
        while (it.hasNext()) {
            Article node = it.next();
            total_error[0] += Math.abs(node.x - node.x_new);
            total_error[1] += Math.abs(node.y - node.y_new);
            node.x = node.x_new;
            node.y = node.y_new;
        }
        return total_error;
    }
    

    static final Comparator<Article> X_ORDER = new Comparator<Article>() {
        public int compare(Article n1, Article n2) {
            if (n1.x > n2.x)
                return -1;
            return 1;
        }
    };
    
    static final Comparator<Article> Y_ORDER = new Comparator<Article>() {
        public int compare(Article n1, Article n2) {
            if (n1.y > n2.y)
                return -1;
            return 1;
        }
    };
    
    
    /** Gets ID array of articles from the 'map_title_article'  by 'titles' of 
     * articles.
     *
     * @param titles                titles of the articles
     * @param map_title_article     map from title of the article to the article
     *
     * @return empty array if titles are absent in the 'map_title_article'
     */
    public static int[] getIdExistedInMap (Set<String> titles, Map<String, Article> map_title_article) {
    
        if(0 == titles.size())
            return NULL_INTEGER_ARRAY;
        
        // counts number of titles presented in map_title_article;
        int size = 0;
        for(String t:titles) {
            if(map_title_article.containsKey(t))
                size ++;
        }
        if(0 == size)
            return NULL_INTEGER_ARRAY;
        
        int[] result = new int [size];
        size = 0;
        for(String t:titles) {
            if(map_title_article.containsKey(t))
                result[size ++] = map_title_article.get(t).page_id;
        }
        
        return result;
    }
        
    
    
    /** Creates an article with ->id_categories[]. If there are problems then
     * returns null.
     *
     * @param title    title of the article to be created
     * @param id       ID of the article to be created
     */
    public static Article createArticleWithCategories (SessionHolder session, String title, int id) {
        Article a = null;
        
        // skips titles with spaces
        if(session.skipTitle(title) || id < 0) {
            if (null != session.dump) {
                session.dump.file.PrintNL(
                        String.format("Removed:%-20s It contains skipped characters (e.g. spaces/underscores).",
                        title));
                session.dump.file.Flush();
            }                                
            return null;
        }
        
        List<String> titles_level_1_cats = new ArrayList<String>();
        String black_category = session.category_black_list.inBlackList(id, titles_level_1_cats, session.source_article_id);
        if (null == black_category) {
            a = new Article();
            a.page_id = id;
            a.page_title = title;
            a.id_categories = CategoryBlackList.getFirstLevelCategoriesID (session, id);
            
        } else {
            if (null != session.dump) {
                //  + " id:" + id +
                session.dump.file.PrintNL(
                        String.format("Removed:%-20s steps:%3d  black-list category:%s",
                        title, session.category_black_list.getPassedSteps(), black_category));
                //session.dump.file.PrintNL( "Removed:" + title + " steps:" + session.category_black_list.steps + " black-list category:" + black_category);
                session.dump.file.Flush();
            }
        }
        return a;
    }
    
    /** Select randomly n_limit elements from source
     */
    /*public static Article[] getRandNodeArray(Article[] source, int n_limit) {
        int     i, counter;
        if (null == source || 0 == n_limit)
            return null;
        if (n_limit < 0 || n_limit >= source.length)
            return source;
        
        boolean[] b_rand = RandShuffle.getRandArray (n_limit, source.length);
        
        Article[]  result_nodes = new Article[n_limit];
        counter = 0;
        for (i=0; i<b_rand.length; i++) {
            if (b_rand[i]) {
                result_nodes[counter ++] = source[i];
            }
        }
        return result_nodes;
    }*/
    
}


