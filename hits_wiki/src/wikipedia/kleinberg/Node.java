/*
 * Node.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import wikipedia.sql.Links;
import wikipedia.sql.PageNamespace;
import java.util.*;


abstract class Node {
    
    /** id of the article or the category (from the table page) */
    public int          page_id;
    
    /** title of the article or category */
    public String       page_title;
    
    /** title of one of the interwiki of the article or category, e.g. "En:Something" or "Ru:Нечто" */
    public String       iwiki_title;
    
    /** Articles: id of articles which refer to the article.
     * Categories: id of categories which refer to the category */
    //public ArrayList<Integer>   links_in;
    public int[]        links_in;
    
    /** Articles: id of articles which are referred by the article.
     * Categories: id of categories which are referred by the category */
    //public ArrayList<Integer>   links_out;
    public int[]        links_out;
    
    abstract String GraphVizNode();
    abstract String GraphVizLinksOut();
    
    public void init() {
    }
    
    
    public int GetLinksInLength() {
        if(null == links_in)
            return 0;
        return links_in.length;
    }
    public int GetLinksOutLength() {
        if(null == links_out)
            return 0;
        return links_out.length;
    }

    /** Returns true, if the array contain node with the title. */
    public static boolean ContainTitle(Node[] nodes,String title) {
        for(Node n : nodes) {
            if(null != n && 0 == title.compareTo(n.page_title)) {
                return true;
            }
        }
        return false;
    }
    
    /** Returns true, if the array contain node with the id. */
    public static boolean ContainID(Node[] nodes,int id) {
        for(Node n : nodes) {
            if(null != n && id == n.page_id) {
                return true;
            }
        }
        return false;
    }
    
    /** Returns array of titles. Returns null for empty nodes.*/
    public static String[] getTitles(Node[] nodes) {
        if(null == nodes || 0 == nodes.length) {
            return null;
        }
        String[] s = new String[nodes.length];
        for(int i=0; i<nodes.length; i++) {
            s[i] = nodes[i].page_title;
        }
        return s;
    }
    
    
    /** Creates the map from node titles to the node IDs */
    public static <T> Map<String, Integer> createMapFromTitleToID(Map<Integer, T>  nodes) {
        Map<String, Integer> m = new HashMap<String, Integer>();
        
        for(T t:nodes.values()) {
            Node n = (Node)t;
            m.put(n.page_title, n.page_id);
        }
        return m;
    }
    
    public String getTitleAndIWiki() {
        
        if(null == iwiki_title || 0 == iwiki_title.length()) {
            return page_title;
        }
        
        return page_title + ":" + iwiki_title;
    }
    
    /** Fills inter wiki title (->iwiki_title) for the specified foreign language.
     *
     * @params lang Two letters identifier, e.g. "En", "Ru", etc.
     * @param namespace only pages with this namespace will be selected, value 
     * defined in PageTable.NS_MAIN, etc.
     *
     * Todo: to optimize Links.GetTitleToByIDFrom(), to submit array of id[] 
     * instead of one id (now) and get arrays of Nodes with filled ->iwiki
     */
    public void fillInterWikiTitle (SessionHolder session,String lang,PageNamespace namespace) {
        
        if(0 == page_id)
            return;
        
        int[] id_from = new int[1];
        id_from[0] = page_id;
        
        String[] ss = Links.getTitleToByIDFrom(session, id_from, namespace);
        for(String s:ss) {
            if(s.length() > 3 &&
               s.charAt(0) == lang.charAt(0) && 
               s.charAt(1) == lang.charAt(1) && 
               s.charAt(2) == 58) 
            {
                iwiki_title = s;
                break;
            }
        }
    }
}
