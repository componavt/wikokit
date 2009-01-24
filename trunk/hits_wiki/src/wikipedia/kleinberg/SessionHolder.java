/*
 * SessionHolder.java - The class holds all input parameters of the current session, 
 * i.e. input parameters for the big functions, e.g. CreateBaseSet, or Calculate, etc.
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import wikipedia.sql.*;
import wikipedia.data.RemovedArticles;
import wikipedia.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;




/**  Container of all input parameters of the current session, 
 * i.e. input parameters for the big functions, e.g. CreateBaseSet, or 
 * Calculate, etc.
 */
public class SessionHolder {
    
    public Connect              connect;
    
    public DumpToGraphViz       dump;
    public int                  source_article_id;
    public String               source_page_title;
    
    /** initial set of categories in black list */
    public CategoryBlackList    category_black_list;
    
    /** ID and titles of removed articles */
    public RemovedArticles      removed_articles;
    
    public Map<Integer, Category> category_nodes;   /** <page_id of category, category object> */
            
    /** whether to skip articles with more than one word in title 
     * (titles with spaces or underscore characters) */
    private boolean skip_titles_with_spaces;
    
    /** whether to select random articles in order to create base set */
    private boolean     b_rand;
    
    
    /** whether to add interwiki data to the node, e.g. "En:Something" or "Ru:Нечто" */
    private boolean     b_iwiki_title;
    
    /** interwiki language to be added */
    private String       iwiki_lang;
    
    
    /** Creates a new instance of SessionHolder */
    public SessionHolder() {
        category_nodes          = new HashMap<Integer, Category>();
        removed_articles        = new RemovedArticles();
        connect                 = new Connect();
        skip_titles_with_spaces = true;
        b_rand                  = true;
        
        b_iwiki_title           = false;
        iwiki_lang              = "Eo";
    }
    public void initObjects(){
        category_black_list     = new CategoryBlackList(this);
    }
    
    public void Init(Connect new_connect, List<String> black_list, int categories_max_steps) {
        connect = new_connect;
        category_black_list.init(black_list, categories_max_steps);
        clear();
    }
    
    public void clear() {
        removed_articles.clear();
        category_nodes.clear();
    }
    
    /** Sets value: whether to skip articles with more than one word in title 
     * (titles with spaces or underscore characters) 
     * @return old value
     */
    public boolean skipTitlesWithSpaces(boolean b) {
        boolean old = skip_titles_with_spaces;
        skip_titles_with_spaces = b;
        return old;
    }
    /** Gets value: whether to skip articles with more than one word in title. */
    public boolean skipTitlesWithSpaces() {
        return skip_titles_with_spaces;
    }
    /** Checks whether to skip the title 'str'.
     * @param this.skip_titles_with_spaces
     * @return true if title contains: 1) space (underscore) or 2) it is empty
     */
    public boolean skipTitle(String str) {
        if(skip_titles_with_spaces && (null == str || str.contains("_") || str.contains(":"))) {
            return true;
        }
        return false;
    }
    
    
    /** Sets value: whether to select random articles in order to create base set */
    public void randomPages(boolean b) {
        b_rand = b;
    }
    /** Gets value: whether to select random articles in order to create base set. */
    public boolean randomPages() {
        return b_rand;
    }

    
    
    /** Sets value: whether to add interwiki data to the node. */
    public void setIWiki(boolean b) {
        b_iwiki_title = b;
    }
    /** Gets value: whether to add interwiki data to the node. */
    public boolean getIWiki() {
        return b_iwiki_title;
    }
    
    
    
    /** Sets value: interwiki language.
     * @return true if str is valid two letters word.
     */
    public boolean setIWikiLang(String str) {
        if(null == str || 2 != str.length()) {
            System.out.println("Error: iwiki lang should contain 2 letters, skip: " + str);
            return false;
        }
        iwiki_lang = StringUtil.UpperFirstLowerSecondLetter(str);
        return true;
    }
    /** Gets value: whether to add interwiki data to the node. */
    public String getIWikiLang() {
        return iwiki_lang;
    }
}
