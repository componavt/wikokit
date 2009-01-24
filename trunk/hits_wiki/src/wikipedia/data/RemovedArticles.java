/*
 * RemovedArticles.java - stores ID and titles of removed articles.
 *
 * Copyright (c) 2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.data;

import java.util.ArrayList;
import java.util.List;


/** The storage of ID and titles of removed articles with purpose to speed-up 
 * the search.
 */
public class RemovedArticles {
    
    /** id of articles removed due to 
     * 1. category parents belonged to category_black_list or
     * 2. since title of article is skipped by user preferences, e.g. skip spaces
     */
    private List<Integer>        id;
    
    /** title of articles removed due to 
     * 1. category parents belonged to category_black_list, 
     *    @see CategoryBlackList.DeleteUsingBlackList or
     * 2. title of article is skipped by user preferences, e.g. skip spaces
     */
    private List<String>         title;
    
    public RemovedArticles() {
        id      = new ArrayList<Integer>();
        title   = new ArrayList<String>();
    }
    
    /** Clears list of ID and titles */
    public void clear() {
        id.clear();
        title.clear();
    }
    
    /** Checks: was this title removed */
    public boolean hasTitle(String s) {
        return title.contains(s);
    }
    
    /** Checks: was this ID removed */
    public boolean hasId(int i) {
        return id.contains(i);
    }
    
    /** Adds removed title (without duplicates) */
    public void addTitle(String s) {
        if(!title.contains(s))
            title.add(s);
    }
    
    /** Adds removed ID (without duplicates) */
    public void addId(int i) {
        if(!id.contains(i))
            id.add(i);
    }
    
    /** Gets number of titles */
    public int sizeTitle() {
        return title.size();
    }
    
    /** Gets number of ID */
    public int sizeId() {
        return id.size();
    }
    
}
