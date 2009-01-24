/*
 * StringMap.java - contains functions for redirects retrieve.
 *
 * Copyright (c) 2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.data;

import wikipedia.kleinberg.SessionHolder;
import wikipedia.sql.Links;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Iterator;

/** Set of functions related to the structure Map &lt;String, Set&lt;String>>
 */
public class StringMap {
    
        
    /** Fills maps m_in and m_out by data from 1. ArticleIdAndTitle, 
     * 2. map from identifier (to) pointed to identifiers (from). */ 
    public static void fill_m_in_m_out (Map<String,Set<String>> m_out, Map<String,Set<String>> m_in,
                                        ArticleIdAndTitle[] aid, ArticleIdAndTitle[] addon, 
                                        Map<Integer, List<Integer>> m_id_to__id_from)
    {
        // create map from id to title by ArticleIdAndTitle
        Map<Integer, String> m_id_title = ArticleIdAndTitle.createMapIdTitle(aid);
        for(ArticleIdAndTitle a:addon)
            m_id_title.put(a.id, a.title);
        
        for(Integer id_to:m_id_to__id_from.keySet()) {
            for(Integer id_from:m_id_to__id_from.get(id_to)) {
                Links.addTitlesToMaps(m_id_title.get(id_from),
                                      m_id_title.get(id_to  ),
                                      m_out, m_in);
            }
        }
    }
    
    /** Removes the string from the map. */ 
    public static void removeString (String s, Map<String,Set<String>> m_out, Map<String,Set<String>> m_in)
    {
        Set<String> out = m_out.remove(s);
        if(null != out) {
            for(String o:out) {
                m_in.get(o).remove(s);
            }
        }
        
        Set<String> in = m_in.remove(s);
        if(null != in) {
            for(String i:in) {
                m_out.get(i).remove(s);
            }
        }
    }
    
    
    /** Filters titles in map by user preferences, e.g. skip titles with spaces. 
     */ 
    private static void skipTitleInMap (SessionHolder session, Map<String,Set<String>> m)
    {
        for (Iterator<String> it = m.keySet().iterator(); it.hasNext();) {
            String s = it.next();
            if(session.skipTitle(s)) {
                session.removed_articles.addTitle(s);
                it.remove();
            } else {
                for (Iterator<String> it2 = m.get(s).iterator(); it2.hasNext();) {
                    String t = it2.next();
                    if(session.skipTitle(t)) {
                        session.removed_articles.addTitle(t);
                        it2.remove();
                    }
                }
                if(0 == m.get(s).size()) {
                    session.removed_articles.addTitle(s);
                    it.remove();
                }
            }
        }
    }
    
    /** Filters titles in m_in, m_out by user preferences, e.g. skip titles 
     * with spaces. 
     */ 
    public static void skipTitles (SessionHolder session,
            Map<String,Set<String>> m_out, Map<String,Set<String>> m_in)
    {   
        if(!session.skipTitlesWithSpaces())
            return;
        
        skipTitleInMap(session, m_out);
        skipTitleInMap(session, m_in );        
    }
    
    
    /** Replace replaceable string 'del' by replacement string 'add' */
    private static void _replaceStringInMaps ( String del, String add,
            Map<String,Set<String>> m_out, Map<String,Set<String>> m_in)
    {
        Set<String> s_out = null;
        
        if(m_out.containsKey(del)) {
            s_out = m_out.remove(del);
            for(Object o:s_out) {
                String s = (String)o;
                if(m_in.containsKey(s)) {
                    Set<String> ss = m_in.get(s);
                    if(ss.contains(del)) {
                        ss.remove(del);
                        if(!s.equals(add))
                            ss.add(add);
                    }
                }
                if(0 == m_in.get(s).size())
                    m_in.remove(s);
            }
            
            // skip loop edge (add,add)
            if(s_out.contains(add))
                s_out.remove(add);
            if(s_out.size() > 0) {
                if( m_out.containsKey(add))
                    m_out.get(add).addAll(s_out);
                else  
                    m_out.put(add, s_out);
            }
        }
    }
    
    /** Replace replaceable string 'del' by replacement string 'add' */
    public static void replaceTitleInMaps ( String del, String add,
            Map<String,Set<String>> m_out, Map<String,Set<String>> m_in)
    {
        _replaceStringInMaps (del, add, m_out, m_in);
        _replaceStringInMaps (del, add, m_in, m_out);
    }
    
}
