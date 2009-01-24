/*
 * Redirect.java - contains functions for redirects retrieve.
 * At this moment it doesn't work with the table 'redirect'.
 *
 * Copyright (c) 2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.data;

import wikipedia.sql.Connect;
import wikipedia.sql.PageTable;
import wikipedia.sql.Links;

import wikipedia.kleinberg.SessionHolder;
import wikipedia.kleinberg.Article;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


/** Set of functions related to WP redirects.
 */
public class Redirect {
    
    private final static String[]            NULL_STRING_ARRAY            = new String[0];
    private final static ArticleIdAndTitle[] NULL_ARTICLEIDANDTITLE_ARRAY = new ArticleIdAndTitle[0];
    // ArticleIdAndTitle[] to__with_blacklist = Redirect.createByIdAndTitles (session.connect, to__with_blacklist__with_redirects);
    
    // todo: 
    // get all pages which are redirects for this page.
    // another task: select * from redirect WHERE rd_from=52141;
    
    
     /** Adds to ArticleIdAndTitle 'aid' pairs title & id of redirect-page 
      * (without duplicates) which refer to the ArticleIdAndTitle 'aid' page.
      */
    public static void addRedirect (ArticleIdAndTitle aid, int id, String title) {
        
        if(null == aid)
            return;
        
        for(ArticleIdAndTitle r:aid.redirect) {
            if(r.id == id) {// checks that aid.redirect doesn't contain id (and title of coarse)
                return;     // duplicate is found
            }
        }
        
        if(0 == aid.redirect.size())
            aid.redirect = new ArrayList<ArticleIdAndTitle>(1);
        
        aid.redirect.add(new ArticleIdAndTitle(id, title));
    }
    
    
    /** Adds to article 'a': title & id of redirect-page (without duplicates) 
     * which refer to the article.
     */
    public static void addRedirect (Article a, List<ArticleIdAndTitle> redirect) {
        
        if(0 == redirect.size())
            return;
        
        if(0 == a.redirect.size()) {
            a.redirect = redirect; 
        } else {
            
            // a_redirect_titles - list of redirects which articles have already
            List<String> a_redirect_titles = ArticleIdAndTitle.getTitles(
                    (ArticleIdAndTitle[])a.redirect.toArray(NULL_ARTICLEIDANDTITLE_ARRAY));
            
            for(ArticleIdAndTitle r:redirect) {
                if(!a_redirect_titles.contains(r.title)) {
                    a.redirect.add(r);
                }
            }
        }
    }
    
    /** Checks: does 'aid' has any redirects, then returns true. */
    public static boolean hasRedirect (ArticleIdAndTitle[] aid) {
        
        if(null == aid || 0 == aid.length)
            return false;
        
        for(ArticleIdAndTitle a:aid) {
            if (a.id < 0) {
                return true;
            }
        }
        return false;
    }
    
    
    /** Gets id & title of page (page to) which is referred by the redirect page 
     * with 'id_from' (it is really redirect, since id_from < 0). 
     * 
     * @param m map from article ID to ArticleIdAndTitle for treated 
     * articles, they are target articles i.e. linked by redirect pages
     *
     * @return null, if 
     * 1. id_from has positive value or
     * 2. title_to should be skipped by user preferences (e.g. it has spaces).
     * In case (2.) title_to, id_to and title_from, id_from will be stored to
     * session.removed_articles_title and _id for future references.
     */
    public static ArticleIdAndTitle getByRedirect (SessionHolder session,int id_from,String title_from,
                                                   Map<String, ArticleIdAndTitle> m) {
        
        if(id_from > 0)
            return null;
        
        if(session.removed_articles.hasId(-id_from))
            return null;
        
        boolean save_skipTitlesWithSpaces = session.skipTitlesWithSpaces(false);
        String  title_to = Links.getTitleToOneByIDFrom(session, -id_from);
        session.skipTitlesWithSpaces(save_skipTitlesWithSpaces);
        
        if(null == title_to)
            return null;
        
        if(m.containsKey(title_to))
            return m.get(title_to);
        
        int     id_to = PageTable.getIDByTitle(session.connect, title_to);
        
        ArticleIdAndTitle a_to = null;
        /*if(session.skipTitle(title_to)) {
            session.removed_articles.addTitle(title_to);
            session.removed_articles.addTitle(title_from);
            session.removed_articles.addId(-id_from);
            session.removed_articles.addId(id_to);
        } else {*/
            a_to = new ArticleIdAndTitle(
                            id_to,
                            title_to);
            m.put(title_to, a_to);
        //}
        
        return a_to;
    }
    
    /** Map from article's title to ArticleIdAndTitle object */
    private static Map<String, ArticleIdAndTitle> _mr = new HashMap<String, ArticleIdAndTitle>();
    
    /** Resolves redirects: moves ArticleIdAndTitle with negative id 
     * (i.e. redirects) to .redirect, gets IDs by redirects' pages' titles 
     * from db, adds to the result ArticleIdAndTitle[]
     */
    public static ArticleIdAndTitle[] resolveByIdAndTitles (SessionHolder session, 
                                        Map<String,Set<String>> m_out, Map<String,Set<String>> m_in,
                                        ArticleIdAndTitle[] aid_source) {
        
        if(null == aid_source || 0 == aid_source.length)
            return NULL_ARTICLEIDANDTITLE_ARRAY;
        
        if(!hasRedirect(aid_source)) {
            return aid_source;
        }
                                                                              // may be less, may be more
        List<ArticleIdAndTitle> aid = new ArrayList<ArticleIdAndTitle> (aid_source.length);
        
        // 1. adds article with positive ID
        _mr.clear();
        for(ArticleIdAndTitle a:aid_source) {
            if (a.id > 0) {
                aid.add(a);
                _mr.put(a.title, a);
            }
        }
        
        for(ArticleIdAndTitle redirect:aid_source) {
            if (redirect.id < 0) {
                // there is redirect from a.title to a_to (title)
                // get id by redirect link from -a.id to id of redirect-target page

                ArticleIdAndTitle a_to = getByRedirect (session, redirect.id, redirect.title, _mr);

                if(null != a_to) {
                    addRedirect(a_to, redirect.id, redirect.title);
                    StringMap.replaceTitleInMaps (redirect.title, a_to.title, m_out, m_in);
                    if(!aid.contains(a_to))
                        aid.add(a_to);
                }
            } // } else if (a.id == 0) - skips the red references to yet non-existed pages
        }
        
        _mr.clear();
        return (ArticleIdAndTitle [])aid.toArray(NULL_ARTICLEIDANDTITLE_ARRAY);
    }
    
    
    
                
        
}


