/*
 * CategoryBlackList.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import wikipedia.data.ArticleIdAndTitle;
import wikipedia.sql.*;

import java.util.*;

class LinkId {
    public List<Integer> dest = new ArrayList<Integer>();
};

/** The categories in the black list (e.g. in black_array_ru or black_array_en)
 * help to mark articles which have small possibilitiy to be synonyms
 * for common words, e.g. categories "Years", "Geography", etc.
 */
public class CategoryBlackList {
    
    private SessionHolder   session;
    
    private final static String[]   NULL_STRING_ARRAY   = new String[0];
    private final static int[]      NULL_INT_ARRAY      = new int[0];
    
    /** Russian */
    private final String[]  black_array_ru = {"Страны", "Века", "Календарь", 
            "География", "География России", "Края_России", "Области России", "Города России", 
            "Столицы", "Города",
            "Персоналии", "Правители России", "Астрономы_России",
    };
    public List<String>     ru;
    
    private final String[]  skip_array_ru = {"Stub"};
    public List<String>     skip_ru;
    
    /** English */
    private final String[]  black_array_en = {"Years", "Calendars", "Geography", "Colleges_and_universities", "Scientists", "Psychologists", "Philosophers"};
    public List<String>     en;
    private final String[]  skip_array_en  = {"Stub"};
    public List<String>     skip_en;
    
    private List<String>    black_list;     /** list of categories' names which should be skipped, omitted, e.g. 'All', 'Country', 'Time',
                                             * the value of list 'ru' or 'en' should be assigned to
                                             */
/*    private List<String>    skip_list;*/      /** This categories should be simply skipped, because they have no meaning
                                             * e.g. "Stub"
                                             */

    /** The number of passed (treated) categories after searching for categories 
     * with black list.*/
    private int             passed_steps;
    
    /** The maximum allowed number of passed (treated) categories (searching for
     * categories with black list). It is search constrain, alternative to 
     * parameter 'categories_max_steps'. */
    private int             max_steps;
    
    /** Number of categories passed after removing via black-list */
    private int             total_categories_passed;
    
    
    /* current set of black list of categories, it depends on black_list and CategoryBlackList.max_steps */
    private Set<String>          category_titles_black_list;
    /* current set of white list of categories, it depends on black_list and CategoryBlackList.max_steps */
    private Set<String>          category_titles_white_list;
    
    
    /** Creates a new instance of CategoryBlackList */
    public CategoryBlackList(SessionHolder session) {
    
        this.session = session;
        black_list   = null;
        
        // copy black_array_ru to black_list_ru
        ru = new ArrayList<String>();
        for(int i=0; i<black_array_ru.length; i++) {
            //String s = Encodings.UTF8ToLatin1(black_array_ru[i]);
            //ru.add(black_array_ru[i]);
            
            ru.add(session.connect.enc.EncodeFromJava(black_array_ru[i]));
            //ru.add(Encodings.FromTo(black_array_ru[i], Encodings.enc_java_default, Encodings.enc_int_default));
        }
        
        en = new ArrayList<String>();
        for(int i=0; i<black_array_en.length; i++) {
            //String s = Encodings.UTF8ToLatin1(black_array_en[i]);
            en.add(black_array_en[i]);
        }
        
        category_titles_black_list = new HashSet<String>();
        category_titles_white_list = new HashSet<String>();
    }

    public void init(List<String> new_black_list, int categories_max_steps) {
        black_list = new_black_list;
        max_steps = categories_max_steps;
        total_categories_passed = 0;
        
        category_titles_black_list.clear();
        if(null != black_list && 0 < black_list.size()) {
            category_titles_black_list.addAll(black_list);
        }
        category_titles_white_list.clear();
    }
    
    public void setBlackList(List<String> black_list) {
        this.black_list = black_list;
    }
    public List<String> getBlackList() {
        return black_list;
    }
    
    
    public void setMaxSteps(int max_steps) {
        this.max_steps = max_steps;
    }
    
    /** Gets the number of passed (treated) categories after searching for 
     * categories with black list.*/
    public int getPassedSteps() {
        return passed_steps;
    }
    
    /** Gets total number of passed (treated) categories after removing via 
     * black-list. */
    public int getTotalCategoriesPassed() {
        return total_categories_passed;
    }
    
    
    /** Fills session.category_nodes, if blacklist is empty.
     *
     * Without this func, the table of categories is empty when blacklist is 
     * null.
     */
    public void fillCategoryNodesIfBlackListEmpty (List<Article> articles) {
        if (null != black_list)
            return;
        
        int save_max_steps = max_steps;
        max_steps = 1;
          
        for(Article a:articles) {
            //String[] categories = 
            getCategoryUpIteratively (a.page_id, black_list);
        }
        
        max_steps = save_max_steps;
    }
    
    
    /** Gets first level categories of the article with id='cl_from'. 
     *
     * !Be careful with session.skipTitlesWithSpaces(), see example in 
     * inBlackList().
     */
    public static String[] getFirstLevelCategories (SessionHolder session,int cl_from) {
        String[] add = null;
        if(0 < cl_from) {
            add = Categorylinks.GetCategoryTitleByArticleID(session.connect, cl_from);
        } else {
            // redirect page has negative id
            int cl_2 = Links.getIdToByIDFrom(session, cl_from, PageNamespace.MAIN);
            if(0 != cl_2) {
                add = Categorylinks.GetCategoryTitleByArticleID(session.connect, cl_2);
            }
        }
        return add;
    }
    
    /** Gets first level categories' IDs of the article with 'id'. */
    public static int[] getFirstLevelCategoriesID (SessionHolder session,int id)
    {
        boolean save_skipTitlesWithSpaces = session.skipTitlesWithSpaces(false);
        String[] add = CategoryBlackList.getFirstLevelCategories (session, id);
        session.skipTitlesWithSpaces(save_skipTitlesWithSpaces);
        
        if (null == add)
            return NULL_INT_ARRAY;
        return Category.getIDByTitle(session.connect, Arrays.asList(add));
    }
    
    /** Returns true, if category blacklist already containts title. */
    public boolean inBlackListAlready (String title) {
        return category_titles_black_list.contains(title);
    }
    
    /** Compare categories (and parents) with the blacklist.
     * If the category (or parent) is found, which is presented in blacklist then 
     * the name of this category will be returned, else - the null.
     *
     * @param cl_from       id of from page, if it is < 0 then 
     *                      this is redirect page
     *
     * @param  first_level_categories is titles of first level categories
     *                      if !=null then it will be filled by
     *                      categories of first level, i.e. by id of categories
     *                      which are nearest to the article.
     *
     *                      If there are no categories in black-list 
     *                      then first_level_categories will contain all 
     *                      categories of the first level of the article.
     *                      Else when it is encountered an element from 
     *                      the blacklist, and last element in it is 
     *                      the name of element from the blacklist.
     *
     * @param source_article_id id of source article, return null 
     *                      if id of source page == id of from page
     *
     * !Side effects:
     *      This function sets value for the variable "passed_steps" (number of passed categories).
     */
    public String inBlackList (int cl_from, List<String> first_level_categories, int source_article_id) {
  //public String inBlackList (int cl_from, List<Integer> first_level_categories) {
        if (cl_from == source_article_id)   // Suppose that source article is not in the blacklist
            return null;                            // in order to escape problems in getAllHubsSortedByY() 
                                                    // started calculations from the source article
        if (null == black_list)
            return null;
        
        
        boolean save_skipTitlesWithSpaces = session.skipTitlesWithSpaces(false);
        String[] add = getFirstLevelCategories (session, cl_from);
        
        if (null == add) {
            session.skipTitlesWithSpaces(save_skipTitlesWithSpaces);
            return null;
        }
        
        
        
        if (null != first_level_categories)
            first_level_categories.addAll(Arrays.asList(add));
        
        // test simple: whether the first level categories belong to the current black list?
        for(int i=0; i<add.length; i++) {
            if (category_titles_black_list.contains( add[i] )) {
                session.skipTitlesWithSpaces(save_skipTitlesWithSpaces);
                return add[i];
            }
        }

        // test complex: check categories recursively in black list
        for(String a : add) {
            if (category_titles_white_list.contains(a))
                continue;
            
            int id = PageTable.getCategoryIDByTitle(session.connect, a);
            if (0 == id) 
                continue;
            
            String[] categories = getCategoryUpIteratively (id, black_list);

            if (null != categories && 0 < categories.length) {
                // test whether the last element is presented in blacklist
                String last = categories[ categories.length - 1 ];
                if (black_list.contains( last )) {
                    category_titles_black_list.add( a );
                    session.skipTitlesWithSpaces(save_skipTitlesWithSpaces);
                    return last;
                }
                category_titles_white_list.add(a);
            }
            //if (null != first_level_categories)
            //    first_level_categories.add(id);
        }
        session.skipTitlesWithSpaces(save_skipTitlesWithSpaces);
        return null;
    }
    
    
    /** 
     * Get list of categories: categories, parents of categories, etc.
     * List is limited by max_steps.
     * 
     * @param cl_from       the id of article which categories will be sought
     *                      (should be >=0, else it is id of redirect page)
     * @param id_categories id of categories of first level (!Attention:
     *                        the function will update this variable),
     *                      if ==null then function don't update it
     *
     * @return If local_black_list == null then it returns all categories (<= max_steps).
     *         If local_black_list != null then the function stops the search 
     *          when it encounters an element from this blacklist, and last element in String[] 
     *          is the name of element from this blacklist.
     *
     * !Side effect: session.category_nodes is updated.
     */

    public String[] getCategoryUpIteratively(int cl_from, List<String> local_black_list) { 
                                           //List<Integer> first_level_categories) {
        passed_steps = 0;
        
        boolean save_skipTitlesWithSpaces = session.skipTitlesWithSpaces(false);
                
        if(0 > cl_from) {
            // redirect page has negative id
            cl_from = Links.getIdToByIDFrom(session, cl_from, PageNamespace.MAIN);
        }
        
        if(0 == cl_from) {
            session.skipTitlesWithSpaces(save_skipTitlesWithSpaces);
            return null;
        }
        
        Connect connect = session.connect;
        
        //List<Integer>         done_id       = new ArrayList<Integer>();
        List<String>    categories          = new ArrayList<String>();
        List<Integer>   categories_id       = new ArrayList<Integer>();
        HashMap<Integer, Category>  local_map_category = new HashMap<Integer, Category>();
        
        // map from category id to list of parents categories id
        Map<Integer, LinkId> local_links_in = new HashMap<Integer, LinkId>();
        
        categories_id.add(cl_from);
        //int level = 0;
        boolean found_in_black_list = false;
        
        CATEGORIES_CYCLE:
        while (0 < categories_id.size()) {
                //level ++;
                
                //int page_id = categories_id.remove( categories_id.size() - 1 );    // depth-first search
                int page_id = categories_id.remove( 0 );                             // breadth-first search
                
                //done_id.add(page_id);
                String[] add = Categorylinks.GetCategoryTitleByArticleID(connect, page_id);
                if (null == add)
                    continue;
                
                // add new categories to the stack
                for(int i=0; i<add.length; i++) {
                    if (passed_steps ++ >= max_steps)
                        break CATEGORIES_CYCLE;
                    
                    if (!categories.contains(add[i]))   // this is double check (first check is done_id.contains).
                         categories.add(     add[i]);   // It is need because the same category can have differ id.
                    
                    //String latin1 = Encodings.FromTo(add[i], "UTF8", "ISO8859_1");
                    //String latin1 = session.enc.FromUserToDB(add[i]);
                    String latin1 = add[i];
                    
                    if (null != local_black_list && local_black_list.contains(latin1)) {
                        found_in_black_list = true;
                        break CATEGORIES_CYCLE;         // last element of categories contain string from blacklist
                    }
                    int candidate_id = connect.page_table.getCategoryIDByTitle(connect, latin1);
                    
                    //if (0 != candidate_id && !done_id.contains(candidate_id) && !categories_id.contains(candidate_id)) {
                    if (0 != candidate_id) {
                        categories_id.add(candidate_id);
                        //if (1 == level && null != first_level_categories)
                        //    first_level_categories.add(candidate_id);
                        
                        Category c;
                        boolean c_new = false;
                        if(local_map_category.containsKey(candidate_id)) {
                            c  = local_map_category.get(candidate_id);
                        }
                        else if(session.category_nodes.containsKey(candidate_id)) {
                            c = session.category_nodes.get(candidate_id);
                        }
                        else {
                            c = new Category();
                            c_new = true;
                            c.page_id    = candidate_id;
                            c.page_title = add[i];
                        }

                        LinkId l;
                        //if(1 != level) {                    // skip link to non-categories (first article)
                            l = local_links_in.get(candidate_id);
                            if (null == l) {
                                l = new LinkId();
                                local_links_in.put(candidate_id, l);
                            }
                            if (!l.dest.contains(page_id))
                                l.dest.add(page_id);
                        //}
                            
                        if(c_new)
                            local_map_category.put(c.page_id, c);
                    }
                }
        }
        total_categories_passed += passed_steps;
        
        if (!found_in_black_list) {
            // add vertices
            session.category_nodes.putAll(local_map_category);
            
            // add arcs: session.category_nodes (local_links_in.key) .links_in.add (local_links_in.value)
            for(Integer id_source : local_links_in.keySet()) {
                LinkId l = (LinkId)local_links_in.get(id_source);
                assert(session.category_nodes.containsKey(id_source));
                Category c = session.category_nodes.get(id_source);

                // c.links_in[] = all = unique_value( c.links_in[] + l.dest)
                List<Integer> all = new ArrayList<Integer>();
                if(null != c.links_in) {
                    for(int i=0; i<c.links_in.length; i++) {
                        all.add(c.links_in[i]);
                    }
                }
                for(Integer id_dest : l.dest) {
                    if (!all.contains(id_dest))
                        all.add(id_dest);
                }
                c.links_in = new int[all.size()];
                int i=0;
                for(Integer a : all) {
                    c.links_in[i++] = a;
                }
            }
        }
        
        session.skipTitlesWithSpaces(save_skipTitlesWithSpaces);        
        if (0 == categories.size())
            return null;
        else
            return (String[])categories.toArray(NULL_STRING_ARRAY);
    }
    
    
    
    /** Returns only pairs (id, title) of articles which are absent in blacklist */
    public ArticleIdAndTitle[] DeleteUsingBlackList(ArticleIdAndTitle[] aid_source) {
        
        if (null == black_list)
            return aid_source;
        
        boolean save_skipTitlesWithSpaces = session.skipTitlesWithSpaces(false);
        
        List<ArticleIdAndTitle> aid = new ArrayList<ArticleIdAndTitle>(aid_source.length);  // or less size
        for(ArticleIdAndTitle it:aid_source) {
            
            if(session.removed_articles.hasTitle(it.title))
                continue;
            
            String  black_category = inBlackList (it.id, null, session.source_article_id);
            if (null == black_category) {
                aid.add(it);
            } else {
                session.removed_articles.addTitle(it.title);
                
                if (null != session.dump) {
                    session.dump.file.PrintNL(
                            String.format("Removed:%-20s steps:%3d  blacklist category:%s (String[] DeleteUsingBlackList)", 
                            it.title, session.category_black_list.passed_steps, black_category));
                    session.dump.file.Flush();
                }
            }
        }
        
        session.skipTitlesWithSpaces(save_skipTitlesWithSpaces);
        return (ArticleIdAndTitle[])aid.toArray(ArticleIdAndTitle.NULL_ARTICLEIDANDTITLE_ARRAY);
    }
    
    /** Returns only id of articles which are absent in blacklist.
     * Result should contains no more than n_limit elements.
     * Algorithm: 
     * 1) Random permutation of elements in id (if b_rand is true)
     * 2) Take good id (absented in blacklist) till n_limit elements will be gathered.
     * Remark: if n_limit is -1 then return all id which are absent in blacklist.
     *  GetRandNodeArray(result, n_limit);
     */
    public ArticleIdAndTitle[] DeleteUsingBlackList (boolean b_rand, ArticleIdAndTitle[] aid_source, int n_limit) {
        
        if (null == black_list)
            return aid_source;
        
        List<ArticleIdAndTitle> result = new ArrayList<ArticleIdAndTitle>(aid_source.length);
        List<ArticleIdAndTitle> aid_list = Arrays.asList(aid_source);
        if(b_rand) {
            // Random permutation of elements in id
            Collections.shuffle(aid_list);          //id = RandShuffle.permuteRandomly(id_source);
        }
        
        boolean save_skipTitlesWithSpaces = session.skipTitlesWithSpaces(false);
        
        BLACK_CATEGORY_CYCLE:
        //for(i=0; i<id.length; i++) {
        for(ArticleIdAndTitle aid:aid_list) {
            if(session.removed_articles.hasId(aid.id))
                continue;
                
            String      black_category = inBlackList (aid.id, null, session.source_article_id);
            if (null == black_category) {
                result.add( aid );
                if (n_limit!=-1 && result.size() >= n_limit)
                    break BLACK_CATEGORY_CYCLE;
            } else {
                session.removed_articles.addId(aid.id);
                    
                if (null != session.dump) {
                    //String title = PageTable.getTitleByID(session.connect, id[i]);
                    String title = aid.title;
                    // + " id:" + id[i] + 
                    session.dump.file.PrintNL( String.format("Removed:%-20s steps:%3d  blacklist category:%s (int[] DeleteUsingBlackList)", 
                                                                     title, session.category_black_list.passed_steps, black_category));
                    session.dump.file.Flush();
                }
            }
        }
        
        session.skipTitlesWithSpaces(save_skipTitlesWithSpaces);
        
        return (ArticleIdAndTitle[])result.toArray(ArticleIdAndTitle.NULL_ARTICLEIDANDTITLE_ARRAY);
    }
}
