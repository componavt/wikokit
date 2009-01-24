/*
 * ArticleIdAndTitle.java - storage and routines for array of articles id 
 * and corresponded titles
 *
 * Copyright (c) 2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.data;

import wikipedia.kleinberg.Article;
import wikipedia.kleinberg.SessionHolder;

import wikipedia.sql.Connect;
import wikipedia.sql.PageNamespace;
import wikipedia.sql.PageTable;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/** The aritle identifier (id[i]) and corresponding title (title.get(i)). The 
 * simplified version of class Article, it can be used as analog of redirect
 * page and so on.
 *
 * Todo: move to this package: Article, Category, Node, NodeType from wikipedia.kleinberg
 */
public class ArticleIdAndTitle {
    
    /** Identifier of the article   */
    public int          id;
    
    /** Title of the article        */
    public String     title;
    
    // Titles of redirect pages, analog of "What links here" in WP 
    /** Titles (and id) of redirect pages, analog of "What links here" in WP */
    public List<ArticleIdAndTitle> redirect;
    //public List<String> redirect;
    
    public  final static      ArticleIdAndTitle[] NULL_ARTICLEIDANDTITLE_ARRAY = new ArticleIdAndTitle[0];
    public  final static List<ArticleIdAndTitle>  NULL_ARTICLEIDANDTITLE_LIST  = new ArrayList<ArticleIdAndTitle>();
    private final static List<String>             NULL_STRING_LIST             = new ArrayList<String>();
    //private final static String[]               NULL_STRING_ARRAY            = new String[0];
    private final static Map<Integer, String>     NULL_INTEGERSTRING_MAP       = new HashMap<Integer, String> ();
    
    public ArticleIdAndTitle() {}
    public ArticleIdAndTitle(int id, String title) {
        this.id = id;
        this.title = title;
        this.redirect = NULL_ARTICLEIDANDTITLE_LIST;
    }
    
    /** Fills identifires and titles of the set of articles */
    public static ArticleIdAndTitle[] create(Article[] articles) {
        
        if(null == articles || 0 == articles.length)
            return NULL_ARTICLEIDANDTITLE_ARRAY;
        
        ArticleIdAndTitle[] aid_result = new ArticleIdAndTitle [articles.length];
        
        int i = 0;
        for(Article a:articles) {
            aid_result[i++] = new ArticleIdAndTitle(a.page_id, a.page_title);
        }
        return aid_result;
    }
    
    //title__with_blacklist__redirects = ArticleIdAndTitle.join(title__with_blacklist__redirects, map_id_article_exist);
    
    /** Adds id and title of articles to the ArticleIdAndTitle[]. It didn't 
     * check uniqueness of ID of added elements.
     *
     * @return source + map_id_article_exist
     */
    public static ArticleIdAndTitle[] join(ArticleIdAndTitle[] source, Map<Integer, Article> map_id_article_exist) {
        
        if(null == map_id_article_exist || 0 == map_id_article_exist.size() || 
                0 == source.length)
            return source;
        
        ArticleIdAndTitle[] result = new ArticleIdAndTitle[source.length + map_id_article_exist.size()];
        int i=0;
        for(ArticleIdAndTitle a:source)
            result [i++] =    a;
        
        for(Article a:map_id_article_exist.values())
            result[i++] = new ArticleIdAndTitle(a.page_id, a.page_title);
                    
        return result;
    }
    
    
    private static List<ArticleIdAndTitle> _aidcr = new ArrayList<ArticleIdAndTitle>();
    
    /** Gets array of ArticleIdAndTitle from static variable aidcr, clear aidcr.
     */
    private static ArticleIdAndTitle[] getArrayFromList_aidcr ()
    {
        ArticleIdAndTitle[] aid = NULL_ARTICLEIDANDTITLE_ARRAY;
        if(_aidcr.size() > 0) {
            aid = new ArticleIdAndTitle [_aidcr.size()];
            for(int i=0; i<_aidcr.size(); i++)
                aid [i] = _aidcr.get(i);

            _aidcr.clear();
        }
        return aid;   
    }
    
    /** Gets IDs by pages' titles from db, stores to ArticleIdAndTitle[].
     * 
     * The article is skipped if it is absent in the table page, 
     * i.e. it is skipped the red references to yet non-existed pages. The 
     * skipped articles are removed from m_out, m_in.
     */
    public static ArticleIdAndTitle[] createByTitle (Connect connect, 
                                            Map<String,Set<String>> m_out,  
                                            Map<String,Set<String>> m_in, 
                                            String[] titles)
    {    
        if(null == titles || 0 == titles.length)
            return NULL_ARTICLEIDANDTITLE_ARRAY;
        
        for(String t:titles) {
            int id = PageTable.getIDByTitleNamespace(connect, t, PageNamespace.MAIN);
            if(0 != id) {
                _aidcr.add(new ArticleIdAndTitle(id, t));
            } else {
                StringMap.removeString(t, m_out, m_in);
            }
        }
        
        return getArrayFromList_aidcr();
    }
    
    
    /** Gets titles by pages' ID from db, stores to ArticleIdAndTitle[].
     */
    public static ArticleIdAndTitle[] createById (Connect connect, Integer[] id_array)
    {    
        if(null == id_array || 0 == id_array.length)
            return NULL_ARTICLEIDANDTITLE_ARRAY;
        
        ArticleIdAndTitle cur;
        for(Integer id:id_array) {
            if(0 != id) {
                cur = PageTable.getByID(connect, id);                // String t = PageTable.getTitleByID(connect, id);
                if(null != cur) {
                    _aidcr.add(new ArticleIdAndTitle(cur.id, cur.title));
                } else {
                    System.out.println("Error: null value in ArticleIdAndTitle.createById()");
                }
            }
        }
        
        return getArrayFromList_aidcr();
    }
    
    
    /** Creates map from identifier to title */
    public static Map<Integer, String> createMapIdTitle (ArticleIdAndTitle[] aid) {
        
        if(null == aid || 0 == aid.length)
            return NULL_INTEGERSTRING_MAP;
        
        Map<Integer, String> m = new HashMap<Integer, String> ();
                
        for(ArticleIdAndTitle a:aid)
            m.put(a.id, a.title);
        
        return m;
    }
    
    
    /** Gets titles from array ArticleIdAndTitle */
    public static List<String> getTitles (ArticleIdAndTitle[] aid) {
        
        if(null == aid || 0 == aid.length)
            return NULL_STRING_LIST;
        
        List<String> t = new ArrayList<String>(aid.length);
        for(ArticleIdAndTitle a:aid) {
            t.add(a.title);
        }        
        return t;
    }
    
    
    /** Filters titles by user preferences, e.g. skip titles with spaces. */
    public static ArticleIdAndTitle[] skipTitles (SessionHolder session, ArticleIdAndTitle[] source) {
        
        if(!session.skipTitlesWithSpaces())
            return source;
        
        if(null == source || 0 == source.length)
            return NULL_ARTICLEIDANDTITLE_ARRAY;
        
        List<ArticleIdAndTitle> l = new ArrayList<ArticleIdAndTitle> (source.length);
        for(ArticleIdAndTitle s:source) {
            if(!session.skipTitle(s.title)) {
                l.add(s);
            } else {
                session.removed_articles.addTitle(s.title);
                session.removed_articles.addId   (s.id);
                for(ArticleIdAndTitle r:s.redirect) {
                    session.removed_articles.addTitle(r.title);
                    session.removed_articles.addId   (r.id);
                }
                s.redirect.clear();
                s.redirect = null;
            }
        }
        
        return (ArticleIdAndTitle[])l.toArray(NULL_ARTICLEIDANDTITLE_ARRAY);
    }
}    