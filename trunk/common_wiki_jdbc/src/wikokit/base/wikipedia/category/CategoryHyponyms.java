/*
 * CategoryHyponyms.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikokit.base.wikipedia.category;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.PageTableBase;
import wikokit.base.wikipedia.sql.Categorylinks;
import wikokit.base.wikipedia.util.StringUtil;

import java.util.List;
import java.util.ArrayList;


/** Routines to get subcategories and articles which belongs to categories.
 */
public class CategoryHyponyms {
    
    //private final static String[]    NULL_STRING_ARRAY    = new String [0];
    private final static List<String>  NULL_STRING_LIST     = new ArrayList<String>(0);
    
    
    /** Gets articles of the category by category title. 
     *  Skip redirect articles.
     */
    public static List<String> getArticlesOfCategory(Connect connect,
                                     String   category_title) {
        List<Integer> articles_subcategory_id = 
                Categorylinks.getArticlesIDSubcategoryIDByCategoryTitle(
                                     connect, category_title);
        return getArticlesOfCategory(connect, articles_subcategory_id);
    }
    
    private static List<String> getArticlesOfCategory(Connect connect,
                                    List<Integer> articles_subcategory_id) {
        
        if(null == articles_subcategory_id || 0 == articles_subcategory_id.size()) {
            return NULL_STRING_LIST;
        }
        
        List<String> articles = new ArrayList<String>();
        for(Integer id:articles_subcategory_id) {
            String t = PageTableBase.getArticleTitleNotRedirectByID(connect, id);
            if(null != t) {
                articles.add(t);
            }
        }
        return articles;
    }
    
    /** Gets titles of subcategories by the title of category. */
    public static List<String> getSubcategoriesOfCategory(Connect connect,
                                    String category_title) {
        
        List<Integer> articles_subcategory_id = 
                Categorylinks.getArticlesIDSubcategoryIDByCategoryTitle(
                                     connect, category_title);
        return getSubcategoriesOfCategory(connect, articles_subcategory_id);
    }
    
    private static List<String> getSubcategoriesOfCategory(Connect connect,
                                    List<Integer> articles_subcategory_id) {
        
        if(null == articles_subcategory_id || 0 == articles_subcategory_id.size()) {
            return NULL_STRING_LIST;
        }
        
        List<String> subcategories = new ArrayList<String>();
        for(Integer id:articles_subcategory_id) {
            String t = PageTableBase.getCategoryTitleByID(connect, id);
            if(null != t) {
                subcategories.add(t);
            }
        }
        return subcategories;
    }
    
    
    /* Gets all titles of articles which belong to the category or its 
     * subcategories.
     * Skip redirect articles. Disambig?
     * Number of articles is limited by max_articles.     
     */
    public static List<String> getArticlesOfSubCategories (Connect connect, String category_title) {
        List<String> pt = // result list of page titles
            new ArrayList<String> ();
        
        // list of parsed subcategories, it helps to skip repetition (evil of category cycles)
        List<String> done = new ArrayList<String>();
        
        // stack of subcategories
        List<String> stack = new ArrayList<String>();
        stack.add(category_title);
        
        while (stack.size() > 0) {
            String category = stack.remove(0);
            if (!done.contains(category)) {
                done.add(category);
                                
                List<Integer> articles_subcategory_id = 
                                    Categorylinks.getArticlesIDSubcategoryIDByCategoryTitle(
                                    connect, category);
                
                List<String> articles = getArticlesOfCategory(connect, articles_subcategory_id);
                pt = StringUtil.addOR(articles, pt);
                
                List<String> subcategories = getSubcategoriesOfCategory(connect, articles_subcategory_id);
                stack.addAll(subcategories);
            }
        }
        return pt;
    }
    
    
    
}
