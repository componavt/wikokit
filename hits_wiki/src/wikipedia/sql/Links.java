/*
 * Links.java - SQL operations with wikipedia.links table.
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.sql;

import wikipedia.language.Encodings;
import wikipedia.kleinberg.SessionHolder;
import wikipedia.kleinberg.Article;
import wikipedia.kleinberg.NodeType;
//import wikipedia.kleinberg.Category;
import wikipedia.kleinberg.CategoryBlackList;

import wikipedia.util.*;
import wikipedia.util_rand.*;

import wikipedia.data.ArticleIdAndTitle;
import wikipedia.data.Redirect;
import wikipedia.data.StringMap;

//import org.apache.commons.collections.ArrayStack;

import java.sql.*;
import java.util.Arrays;
import java.util.Map;
//import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
//import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


/** Works with the SQL table pagelinks.
 * The link or edge with articles  (pl_from, pl_namespace.pl_title)
 *
 * ************
 * TABLE pagelinks (
 * -- Key to the page_id of the page containing the link.
 * pl_from int(8)
 *
 * -- Key to page_namespace/page_title of the target page.
 * -- The target page may or may not exist, and due to renames
 * -- and deletions may refer to different page records as time
 * -- goes by.
 * pl_namespace int NOT NULL default '0',
 * pl_title varchar(255) binary NOT NULL default '')
 *
 * (see mediawiki/maintenance/FiveUpgrade.inc function upgradeLinks()).
 * ************
 *
 */
public class Links {
    
    private Links() {}
    
    private final static String[]       NULL_STRING_ARRAY  = new String [0];
    private final static Article[]      NULL_ARTICLE_ARRAY = new Article[0];
    private final static Integer[]      NULL_INTEGER_ARRAY = new Integer[0];
    //private final static List<Integer>  NULL_INTEGER_LIST  = new ArrayList<Integer>(0);
    
    
    // ********************************************
    // From stuff
    // 
    
    /** Creates Article[], fills ->id and ->title using tables page, pagelinks.
     * 
     * @param n_limit limits the number of returned nodes
     * It is not considered if n_limit <= 0, i.e. it will return all results.
     * <pre>
     * Parameters' example:
     *  str_from = "FROM page WHERE page_namespace=0 AND page_id" + str_in;
     *  str_sql_count_size  = "SELECT COUNT(page_id) AS size "  + str_from;
     *  str_sql             = "SELECT page_id, page_title "     + str_from;</pre>
     */
    public static Article[] getLinksSQL(SessionHolder session, String str_sql_count_size, String str_sql, int n_limit) {
        Article[]   l_from = null;
        int         size = 0, min;
        
        int l_from_counter = 0;
        try {
            Statement s = session.connect.conn.createStatement();
            s.executeQuery(str_sql_count_size);
            ResultSet rs = s.getResultSet();
            if (rs.next()) {
                size = rs.getInt("size");
                
                if (0 < size) {
                    
                    if (n_limit <= 0) {
                        min = size;     // takes all elements
                    } else {
                        min = Math.min(n_limit, size);
                    }
                    
                    s.executeQuery(str_sql);
                    rs = s.getResultSet();
                    
                    // gets all id, make permutation, takes first 'min' elements
                    int[] id_all = new int[size];
                    for (int i=0; rs.next(); i++){
                        id_all[i] = rs.getInt("pl_from");
                    }
                    
                    int[] id_res = id_all;
                    if(session.randomPages() && n_limit < size)
                    {    
                        id_res = RandShuffle.permuteRandomly(id_all);
                    }
                    
                    l_from = new Article[min];
                    for(int id:id_res) {
                        String title = PageTable.getTitleByID(session.connect, id);
                        if(null == title || 0 == title.length())
                            continue;
                        
                        Article a = Article.createArticleWithCategories(session, title, id);
                        if (null != a)
                            l_from [l_from_counter++] = a;
                        
                        if(l_from_counter >= min)
                            break;
                    }
                }
            }
            rs.close();
            s.close();
        } catch(SQLException ex) {
            System.err.println("SQLException (Links.java GetLinksSQL()): sql='" + str_sql + "' " + ex.getMessage());
        }
        
        if(null == l_from || 0 == l_from.length) {
            l_from = null;
            return NULL_ARTICLE_ARRAY;
        }
        
        
        Article[] l_from_result;
        
        // cut off last elements of l_from (if they are null)
        if(l_from.length == l_from_counter) {
            l_from_result = l_from;
        } else {
            l_from_result = new Article[l_from_counter];
            System.arraycopy(l_from, 0, l_from_result, 0, l_from_counter);
            l_from = null;
        }
        return l_from_result;
    }
    
    // (session, sb_sql_count_size.toString(), sb_sql.toString(), n_limit);
    
    /** Creates array of ID, takes it from the table pagelinks. Shuffles it, 
     * if it is user preference.
     */
    public static Integer[] getLinksSQL_AsID(SessionHolder session, String str_sql_count_size, String str_sql, int n_limit) {
        Integer[] result = NULL_INTEGER_ARRAY;
        
        try {
            Statement s = session.connect.conn.createStatement();
            s.executeQuery(str_sql_count_size.toString());
            ResultSet rs = s.getResultSet();
            if (rs.next()) {
                int size = rs.getInt("size");
                
                if (0 < size) {
                    
                    int min; 
                    if (n_limit <= 0) {
                        min = size;     // takes all elements
                    } else {
                        min = Math.min(n_limit, size);
                    }
                    
                    s.executeQuery(str_sql.toString());
                    rs = s.getResultSet();
                    
                    // gets all id, make permutation, takes first 'min' elements
                    int[] id_all = new int[size];
                    for (int i=0; rs.next(); i++){
                        id_all[i] = rs.getInt("pl_from");
                    }
                    
                    int[] id_res = id_all;
                    if(session.randomPages() && n_limit < size)
                    {    
                        id_res = RandShuffle.permuteRandomly(id_all);
                    }
                    
                    result = new Integer [min];
                    int counter = 0;
                    
                    for(int id:id_res) {
                        result [counter++] = id;
                        
                        if(counter >= min)
                            break;
                    }
                }
            }
            rs.close();
            s.close();
        } catch(SQLException ex) {
            System.err.println("SQLException (Links.java GetLinksSQL()): sql='" + str_sql + "' " + ex.getMessage());
        }
        
        return result;
    }
    
    
    /** Gets ids of source pages by id of destination page (What links here).
     *  @param n_limit    maximum number of articles id to be returned
     *  @param id_to      id of destination page
     *<pre>
     * page.page_title === pagelinks.pl_title
     * 1] SELECT page_title FROM page WHERE page_id=10484 AND page_namespace = 0;
     * 2] SELECT pl_from FROM pagelinks WHERE pl_title IN (SELECT page_title FROM 
     * page WHERE page_id=10484 AND page_namespace = 0) AND pl_namespace = 0;
     *
     * Remark:
     * It is supposed that articles have unique names, i.e. only first id of 1] will be used in 2].
     *</pre>
     * old 1.4
     * Optimization of GetLFromByLToIN:
     * replace IN() by several explicit equations "="
     * ( Time speed up - about three times - for "Parallelogram", "Sycamore",
     * Test Parameters: root_set_size:10 increment:10 iter:20)
     */
    public static Article[] getLFromByLTo_deprecated(SessionHolder session, int id_to, String title_to, 
                                int n_limit,
                                Map<String,Set<String>> m_out, Map<String,Set<String>> m_in) {
        String      str_in, str_from;
        String      str_sql_count_size, str_sql;
        Article[]   result = null;
        Article     node = new Article();
        
        System.out.println("Warning: deprecated function getLFromByLTo! Replace it by getIDToByTitleFrom().");
        /*
        // 1]
        String title_to = PageTable.getTitleByID(session.connect, id_to);
        if(null == title_to || 0 == title_to.length()) {
            return NULL_ARTICLE_ARRAY;
        }
        */
        //title_to = Encodings.FromTo(title_to, "UTF8", "ISO8859_1");
        String title_to_db = session.connect.enc.EncodeToDB(title_to);
        
        // 2] SELECT pl_from FROM pagelinks WHERE pl_title IN 
        //      (SELECT page_title FROM page WHERE page_id=10484 AND page_namespace = 0) AND pl_namespace = 0;
        str_from = "FROM pagelinks WHERE pl_title='" + title_to_db + "' AND pl_namespace = 0";
        str_sql_count_size  = "SELECT COUNT(pl_from) AS size "  + str_from;
        str_sql             = "SELECT pl_from "                 + str_from;
        result = getLinksSQL(session, str_sql_count_size, str_sql, n_limit);
        
        for(Article a:result) {
            addTitlesToMaps(a.page_title, title_to, m_out, m_in);
        }
        
        /* old: mediawiki 1.4
         
        // 1. Calculate number of links
        //  too complex & slow request:
        //     "SELECT COUNT(page_id) AS size FROM page " +
        //                 "WHERE page_namespace=0 AND " +
        //                       "page_id IN (SELECT l_from FROM links WHERE l_to="+l_to+")");
         
        // Execute subrequest SQL IN(...)
        int[] i_links_all = GetIntFromLinks(session.connect, "l_from", "WHERE l_to="+l_to);
        if (null == i_links_all)
            return null;
        int[] i_links = session.category_black_list.DeleteUsingBlackList (i_links_all, n_limit);
         
        for(int i=0; i<i_links.length; i++) {
            str_from = "FROM page WHERE page_namespace=0 AND page_id=" + i_links[i];
         
            str_sql_count_size  = "SELECT COUNT(page_id) AS size "   + str_from;
            str_sql             = "SELECT page_id, page_title "       + str_from;
         
            Article[] add = GetLinksSQL(session, str_sql_count_size, str_sql, n_limit);
            result = node.JoinUnique(result, add);
        }*/
        
        return result; //RandShuffle.getRandNodeArray(result, n_limit);
    }
    
    
    private static StringBuffer sb_sql_count_size = new StringBuffer(350);
    private static StringBuffer sb_sql = new StringBuffer(350);
    
    /** Gets array of ArticleIdAndTitle of pages which point to page with the 
     * title 'title_to', number of return array is limited by 'n_limit'.
     *
     * @param increment
     */
    public static ArticleIdAndTitle[] getFromByTitleTo (SessionHolder session,
            String title_to,
            PageNamespace namespace,
            int n_limit)
    {   
        // SELECT page_id, page_title, page_is_redirect FROM page,pagelinks 
        // WHERE page_id=pl_from AND pl_title='Робот' AND pl_namespace = 0 LIMIT 4;
        sb.setLength(0);
        sb.append("SELECT page_id, page_title, page_is_redirect FROM page,pagelinks WHERE page_id=pl_from AND pl_title='");
        sb.append( session.connect.enc.EncodeToDB(
                StringUtil.spaceToUnderscore(
                StringUtil.escapeChars(title_to))
                ) 
        );
        sb.append( "' AND pl_namespace=");
        sb.append(                     namespace.toInt() );
        
        if(-1 != n_limit) {
            sb.append( " LIMIT ");
            sb.append( n_limit  );
        }
      
        List<ArticleIdAndTitle> aid = new ArrayList<ArticleIdAndTitle>();
        //return getLinksSQL_AsID(session, sb_sql_count_size.toString(), sb_sql.toString(), n_limit);
        try {
            Statement s = session.connect.conn.createStatement();
            s.executeQuery(sb.toString());
            ResultSet rs = s.getResultSet();
            //if (rs.next()) {
                    
                    Encodings e = session.connect.enc;
                    // gets all id, make permutation, takes first 'min' elements
                    while (rs.next()){
                        int page_id = rs.getInt("page_id");
                        if(1 == rs.getInt("page_is_redirect")) {
                            page_id = - page_id;
                        }
                        
                        String db_str = Encodings.bytesTo(rs.getBytes("page_title"), e.GetDBEnc());
                        String page_title = e.EncodeFromDB(db_str);
                        
                        aid.add(new ArticleIdAndTitle(page_id, page_title));
                    }
            //}
            rs.close();
            s.close();
        } catch(SQLException ex) {
            System.err.println("SQLException (Links.getFromByTitleTo() ArticleIdAndTitle[]): sql='" + sb.toString() + "' " + ex.getMessage());
        }
        
        return aid.toArray(ArticleIdAndTitle.NULL_ARTICLEIDANDTITLE_ARRAY);
    }
    
    
    /** Gets list of identifiers of pages which point to page with the 
     * title 'title_to'.
     *
     * @param increment
     */
    public static Integer[] getIDFromByTitleTo_hide (SessionHolder session,
            String title_to, // int id_to,
            PageNamespace namespace,
            int n_limit)
    { 
        List<Integer> ids = new ArrayList<Integer>();
        
        String title_to_db = session.connect.enc.EncodeToDB(title_to);
        
        // 2] SELECT pl_from FROM pagelinks WHERE pl_title IN 
        //      (SELECT page_title FROM page WHERE page_id=10484 AND page_namespace = 0) AND pl_namespace = 0;
        sb.setLength(0);
        sb.append("FROM pagelinks WHERE pl_title='");
        sb.append(                               title_to_db );
        sb.append(               "' AND pl_namespace = 0");
        
        sb_sql_count_size.setLength(0);
        sb_sql_count_size.append("SELECT COUNT(pl_from) AS size ");
        sb_sql_count_size.append(sb);
        
        sb_sql.setLength(0);
        sb_sql.append("SELECT pl_from ");
        sb_sql.append(sb);
        
        return getLinksSQL_AsID(session, sb_sql_count_size.toString(), sb_sql.toString(), n_limit);
    }
    
    
    /** Gets identifiers by ArticleIdAndTitle[] 'to', fills the map 
     * map_id_to__id_from.
     */
    public static List<Integer> getLFromIDByLTo_WithBlackList_hide(
            SessionHolder session, 
            ArticleIdAndTitle[] to,Map<Integer, Article> map_id_article_to,
            Map<Integer, List<Integer>> map_id_to__id_from, //Map<String,Set<String>> m_out, Map<String,Set<String>> m_in, 
            int increment)
    {   
        Integer[] id_from__with_blacklist;
        List<Integer> l = new ArrayList<Integer>();
        
        for(ArticleIdAndTitle aid:to) {
            Article a = map_id_article_to.get(aid.id);
            
            // The source article is omitted, since GetLToByLFrom() was called for it already (root set contains the source article)
            if(NodeType.ID_SOURCE_ARTICLE != a.type) {
                id_from__with_blacklist = getIDFromByTitleTo_hide (session, a.page_title, //a.page_id, 
                                        PageNamespace.MAIN,
                                        // from_titles,     // skip from_titles, i.e. remove pl_from from the result
                                        //m_out, m_in, 
                                        increment); 
                
                List<Integer> li = Arrays.asList(id_from__with_blacklist);
                l.addAll(li);
                map_id_to__id_from.put(aid.id, li);
            }
        }
        return l;
    }

    /** Gets ArticleIdAndTitle[] by 'to', fills the map 
     * map_id_to__id_from.
     */
    public static ArticleIdAndTitle[] getLFromByLTo_WithBlackList(
            SessionHolder session, 
            ArticleIdAndTitle[] to,//Map<Integer, Article> map_id_article_to,
            Map<String,Set<String>> m_out, Map<String,Set<String>> m_in, //Map<Integer, List<Integer>> map_id_to__id_from,
            int increment)
    {   
        List<ArticleIdAndTitle> result = new ArrayList<ArticleIdAndTitle>();
        Set<Integer> unique_result_id = new HashSet<Integer>();
        
        for(ArticleIdAndTitle cur_to:to) {
            //Article a = map_id_article_to.get(cur_to.id);
            
            // The source article is omitted, since GetLToByLFrom() was called for it already (root set contains the source article)
            //if(NodeType.ID_SOURCE_ARTICLE != a.type) {
                ArticleIdAndTitle[] 
                from                = getFromByTitleTo (session, cur_to.title, //cur_to.id, 
                                        PageNamespace.MAIN, // from_titles,     // skip from_titles, i.e. remove pl_from from the result  //m_out, m_in, 
                                        increment); 
                
                for(ArticleIdAndTitle cur_from:from) {
                    Links.addTitlesToMaps(cur_from.title, cur_to.title, m_out, m_in);
                    
                    if(!unique_result_id.contains(cur_from.id)) {
                        unique_result_id.add     (cur_from.id);
                               result   .add     (cur_from);
                    }
                    
                    // redirects - get 'who points to the redirect page'
                    if(cur_from.id < 0) {
                        ArticleIdAndTitle[] from2 = getFromByTitleTo (session, cur_from.title, PageNamespace.MAIN, increment); 
                        
                        for(ArticleIdAndTitle c:from2) {
                            Links.addTitlesToMaps(c.title, cur_to.title, m_out, m_in);
                            
                            if(!unique_result_id.contains(c.id)) {
                                unique_result_id.add     (c.id);
                                       result   .add     (c);
                }   }   }   }
            //}
        }
        unique_result_id.clear();
        return result.toArray(ArticleIdAndTitle.NULL_ARTICLEIDANDTITLE_ARRAY);
    }
    
    /**
     *  @param increment  - number of articles which could be added to the base set
     *                  (they refer to one of the pages in the root base)
     ** @param n_limit   max number of returned articles, negative value means no limit
     */
    public static Article[] getLFromByLTo(SessionHolder session, Article[] l_to, int increment, int n_limit,
                                Map<String,Set<String>> m_out, Map<String,Set<String>> m_in)
    {
        ArticleIdAndTitle[] to = ArticleIdAndTitle.create(l_to);
        // now to[].id may contains id from blacklist, let's delete them
        
        to = session.category_black_list.DeleteUsingBlackList(session.randomPages(), to, -1);
        if(0 == to.length) {
            return NULL_ARTICLE_ARRAY;
        }
        if(null == to[0].title) {
            System.out.println("Error in Links.getLFromByLTo(): to[0].title is null");
            return NULL_ARTICLE_ARRAY;
        }
        
        Map<Integer, Article> map_id_article_to = Article.createMapIdToArticleWithoutRedirects (l_to);
//      Map<Integer, List<Integer>> map_id_to__id_from = new HashMap<Integer, List<Integer>> ();
        ArticleIdAndTitle[] from__with_blacklist__redirects;
        /*
        List<Integer> l = getLFromIDByLTo_WithBlackList(session, to, map_id_article_to,
                                                      map_id_to__id_from,                   //m_out,  m_in, 
                                                      increment);
        Integer[] 
        id_from__with_blacklist__redirects  = (Integer[])l.toArray(NULL_INTEGER_ARRAY);
           from__with_blacklist__redirects  = ArticleIdAndTitle.createById(session.connect,
        id_from__with_blacklist__redirects);
        */
        from__with_blacklist__redirects = getLFromByLTo_WithBlackList(session, to,                  // map_id_article_to,
                                                      m_out,  m_in,                                 // map_id_to__id_from,
                                                      increment);
//        StringMap.fill_m_in_m_out(m_out, m_in, 
//                    from__with_blacklist__redirects, to,
//                    map_id_to__id_from);
        
        List<Article> from_articles = createArticlesResolveRedirects(session, from__with_blacklist__redirects, 
                                                    map_id_article_to, n_limit, m_out, m_in);
//        l.clear();
//        l = null;
//        map_id_to__id_from.clear();
        map_id_article_to.clear();
        to = null;
        
        return (Article[])from_articles.toArray(NULL_ARTICLE_ARRAY);
    }
    
    // ********************************************
    // To stuff
    // 
    
    /** Gets id of page which is referred by page with id_from.
     * It can be used to retrieve id of real page by id of redirect page 
     * @return 0 if there is problem
     */
    public static int getIdToByIDFrom(SessionHolder session, int id_from, PageNamespace namespace) {
        
        int[] i = new int[1];
        i[0] = id_from < 0 ? -id_from : id_from ;

        String[] s = Links.getTitleToByIDFrom(session, i, PageNamespace.MAIN);
        if(null != s && 0 < s.length) {
            return PageTable.getIDByTitleNamespace(session.connect, s[0], namespace);
        }
        return 0;
    }
    
            
    private static StringBuffer sb = new StringBuffer(350);
//static int max_titles_len = 0;
//static int max_pl_title_len = 0;
    
    
    /** Selects one pl_title from pagelinks by pl_from. It is needed for 
     * redirect pages, which links only to one page. 
     * @return null, if (1) it is absent (e.g. it's redirect to category) or 
     *                  (2) title should be skipped
     */
    public static String getTitleToOneByIDFrom(SessionHolder session, int pl_from)
    {
        if (0==pl_from)
            return null;
        
        // special treatment of id of redirect page
        if(pl_from < 0)
            pl_from = -pl_from;
        
        Statement s = null;
        ResultSet rs= null;
        String title = null;
        
        sb.setLength(0);
        sb.append("SELECT pl_title FROM pagelinks WHERE pl_from=");
        sb.append(pl_from);
        sb.append(" LIMIT 1");
        
        try {
            s = session.connect.conn.createStatement();
            //str_sql = SELECT pl_title FROM pagelinks WHERE pl_from=52141 LIMIT 1;
            
            s.executeQuery(sb.toString());
            rs = s.getResultSet();

            if (rs.next()) {
                Encodings e = session.connect.enc;
                String db_str = Encodings.bytesTo(rs.getBytes("pl_title"), e.GetDBEnc());
                String utf8_str = e.EncodeFromDB(db_str);
                if(!session.skipTitle(utf8_str)) {
                    title = utf8_str;
                }
            }            
        } catch(SQLException ex) {
            System.err.println("SQLException (Links.java getTitleToOneByIDFrom): sql='" + sb.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {
                try { rs.close();
                } catch (SQLException sqlEx) { }
                rs = null;
            }
            if (s != null) {
                try { s.close();
                } catch (SQLException sqlEx) { }
                s = null;
            }
        }
        return title;
    }
    
    
    /** Returns titles of destination (to) pages by id of source pages (pl_from), table pagelinks are used. 
     * SQL: SELECT pl_title FROM pagelinks WHERE pl_from IN (18991, 22233) AND pl_namespace = 0;
     *  @param namespace only pages with this namespace will be selected, value defined in PageTable.NS_MAIN, etc.
     *  Return empty array if pl_from={0};
     *
     * SELECT MAX(LENGTH(pl_title)) FROM pagelinks WHERE pl_namespace = 0;
     * ruwiki: 255, real application: 92,52
     *
     * Test size of max_titles_len
     * Robot=11651
     * Russina=8811
     * Todo replace titles ArrayList<String>() by huge static array StringBuffer[][256];
     */
    public static String[] getTitleToByIDFrom(SessionHolder session, int[] pl_from, PageNamespace namespace)
    {
        if (null==pl_from || (1==pl_from.length && 0==pl_from[0])) {
            return NULL_STRING_ARRAY;
        }
        
        Statement s = null;
        ResultSet rs= null;
        List<String> titles = new ArrayList<String>();
        
        sb.setLength(0);
        sb.append("SELECT pl_title FROM pagelinks WHERE pl_from IN (");
        
        // Prepare SQL IN(...) via pl_from[].page_id
        for (int i=0; i<pl_from.length-1; i++) {
            sb.append(pl_from[i]);
            sb.append(",");
        }
        sb.append(pl_from[ pl_from.length-1 ]); // skip last comma
        sb.append(") AND pl_namespace=");
        sb.append(                    namespace.toInt());
        
        int     size, i = 0;
        //String str_sql = null;
        try {
            s = session.connect.conn.createStatement();
            //str_sql = "SELECT pl_title FROM pagelinks WHERE " + sb.toString() + " AND pl_namespace="+namespace;
            
            //System.out.print("GetTitleToByIDFrom sql="+sb.toString());
            s.executeQuery(sb.toString());
            //GetTitleToByIDFromQuery(rs, s, sb);
            //System.out.println(" OK.");
            
            rs = s.getResultSet();

            while (rs.next()) {
                Encodings e = session.connect.enc;
                String db_str = Encodings.bytesTo(rs.getBytes("pl_title"), e.GetDBEnc());
                String utf8_str = e.EncodeFromDB(db_str);
                if(!session.skipTitle(utf8_str)) {
                    titles.add( utf8_str );
                    //titles.add(connect.enc.EncodeFromDB(rs.getString("pl_title")));
                }
                
                /*if(max_pl_title_len < utf8_str.length()) {
                    max_pl_title_len = utf8_str.length();
                    System.out.println("GetTitleToByIDFrom max_pl_title_len="+max_pl_title_len);
                }*/
            }
/*if(max_titles_len < titles.size()) {
                max_titles_len = titles.size();
                System.out.println("GetTitleToByIDFrom max_titles_len="+max_titles_len);
}*/
            
        } catch(SQLException ex) {
            System.err.println("SQLException (Links.java GetTitleToByIDFrom): sql='" + sb.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {
                try { rs.close();
                } catch (SQLException sqlEx) { }
                rs = null;
            }
            if (s != null) {
                try { s.close();
                } catch (SQLException sqlEx) { }
                s = null;
            }
        }
        return (String[])titles.toArray(NULL_STRING_ARRAY);
    }
    
    /** @param skip_titles list of titles to be skipped */
    public static String[] getTitleToByIDFrom(SessionHolder session,
            String title_from, int pl_from, 
            PageNamespace namespace,
            //List<String> skip_titles, 
            Map<String,Set<String>> m_out, Map<String,Set<String>> m_in)
    {
        if (0==pl_from) {
            return NULL_STRING_ARRAY;
        }
        
        Statement s = null;
        ResultSet rs= null;
        List<String> titles = new ArrayList<String>();
        
        sb.setLength(0);
        sb.append("SELECT pl_title FROM pagelinks WHERE pl_from=");
        sb.append(pl_from < 0 ? -pl_from : pl_from);
        sb.append(" AND pl_namespace=");
        sb.append(                    namespace.toInt());
        
        int     size, i = 0;
        //String str_sql = null;
        try {
            s = session.connect.conn.createStatement();
            //str_sql = "SELECT pl_title FROM pagelinks WHERE " + sb.toString() + " AND pl_namespace="+namespace;
            
            //System.out.print("GetTitleToByIDFrom 1 sql="+sb.toString());
            s.executeQuery(sb.toString());
            //GetTitleToByIDFromQuery(rs, s, sb);
            //System.out.println(" OK.");
            
            rs = s.getResultSet();

            while (rs.next()) {
                Encodings e = session.connect.enc;
                String db_str = Encodings.bytesTo(rs.getBytes("pl_title"), e.GetDBEnc());
                String utf8_str = e.EncodeFromDB(db_str);
                
                //if(!session.skipTitle(utf8_str)) {
                    //if(!skip_titles.contains(utf8_str)) {
                        titles.add( utf8_str );
                        //titles.add(connect.enc.EncodeFromDB(rs.getString("pl_title")));
                    //}
                    addTitlesToMaps(title_from, utf8_str, m_out, m_in);
                //}
                
                /*if(max_pl_title_len < utf8_str.length()) {
                    max_pl_title_len = utf8_str.length();
                    System.out.println("GetTitleToByIDFrom max_pl_title_len="+max_pl_title_len);
                }*/
            }
/*if(max_titles_len < titles.size()) {
                max_titles_len = titles.size();
                System.out.println("GetTitleToByIDFrom max_titles_len="+max_titles_len);
}*/
            
        } catch(SQLException ex) {
            System.err.println("SQLException (Links.java GetTitleToByIDFrom 1): sql='" + sb.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {
                try { rs.close();
                } catch (SQLException sqlEx) { }
                rs = null;
            }
            if (s != null) {
                try { s.close();
                } catch (SQLException sqlEx) { }
                s = null;
            }
        }
        return (String[])titles.toArray(NULL_STRING_ARRAY);
    }
    
    
    /** Gets all title_to (with blacklist) by articles from ArticleIdAndTitle array.
     *  @param xxx  - xxx
     */
    public static List<String> getLToByLFrom_WithBlackList(SessionHolder session, 
            ArticleIdAndTitle[] from,
            Map<String,Set<String>> m_out, Map<String,Set<String>> m_in)
    {
        String[] title_to__with_blacklist;
        List<String> l = new ArrayList<String>();
        for(ArticleIdAndTitle aid:from) {
            
            // The source article is omitted, since GetLToByLFrom() was called for it already (root set contains the source article)
            if(session.source_article_id != aid.id) { //if(NodeType.ID_SOURCE_ARTICLE != a.type) {
                
                title_to__with_blacklist = getTitleToByIDFrom(session, 
                                        aid.title, aid.id, 
                                        PageNamespace.MAIN,
                                        m_out, m_in); 
                l.addAll(Arrays.asList(title_to__with_blacklist));
            }
        }
        return l;
    }
    
    // _m_out - local map<title of article, list of titles links_out>
    // _m_in  - local map<title of article, list of titles links_in>
    //private static Map<String,Set<String>> _m_out = new HashMap<String,Set<String>>();
    //private static Map<String,Set<String>> _m_in  = new HashMap<String,Set<String>>();
    
    
    /**  Creates destination articles (fills ->id, ->title, ->links_in, 
     * ->links_out, ->redirect) by the source articles id, number of adding 
     * articles restricted by n_limit. 
     *
     * @param n_limit   max number of returned articles, negative value means no limit
     * Todo: select first n links in article (not first n links in table)
     *
     * @param m_out    map &lt;title of article, list of titles links_out>
     * @param m_in     map &lt;title of article, list of titles links_in>
     *
     * The tables page and pagelinks are used.
     * The scheme: pagelinks.pl_from -> pl_title=page_title -> page.page_id.
     * SQL
     * SELECT pl_title FROM pagelinks WHERE pl_from IN (18991, 22233) AND pl_namespace = 0;
     *      out: 17 rows in set (0.24 sec), e.g.: Бархан_(Soundwave), Комикс
     * foreach pl_title:
     *   PageTable p.GetIDByTitle(pl_title);
     *
     * Remark:
     * The article is omitted 
     * if (1) article id is absent in the table page, 
     * or (2) article's categories are in the blacklist,
     * or (3) article title is in pl_from[].
     * The source article is omitted, since this func was called for it already.
     */
    public static Article[] getLToByLFrom(SessionHolder session, Article[] pl_from, int n_limit,
            Map<String,Set<String>> m_out, 
            Map<String,Set<String>> m_in)
    {
        ArticleIdAndTitle[] from = ArticleIdAndTitle.create(pl_from);
        // now from[].id may contains id from blacklist, let's delete them
        
        from = session.category_black_list.DeleteUsingBlackList(session.randomPages(), from, -1);
        if(0 == from.length) {
            return NULL_ARTICLE_ARRAY;
        }
        if(null == from[0].title) {
            System.out.println("Error in Links.getLToByLFrom(): from[0].title is null");
            return NULL_ARTICLE_ARRAY;
        }
        
        Map<Integer, Article> map_id_article_from = Article.createMapIdToArticleWithoutRedirects(pl_from);
        //_m_in. clear();     // _m_out.clear();
        
        List<String> l = getLToByLFrom_WithBlackList(session, from, //map_id_article_from, 
                                                     m_out,  m_in);
        
        ArticleIdAndTitle[] to__with_blacklist__redirects;
        String[] 
        title_to__with_blacklist__redirects__zero_id = (String[])l.toArray(NULL_STRING_ARRAY);
              to__with_blacklist__redirects          = ArticleIdAndTitle.createByTitle (session.connect, m_out, m_in, 
        title_to__with_blacklist__redirects__zero_id);
        
        List<Article> dest_articles = createArticlesResolveRedirects (session, to__with_blacklist__redirects, 
                                                    map_id_article_from, n_limit, m_out, m_in);
        l.clear();
        l = null;
        map_id_article_from.clear();
        from = null;
        
        return (Article[])dest_articles.toArray(NULL_ARTICLE_ARRAY);
    }
    /** Replaces redirects by target articles in m_in, m_out; creates articles. */
    public static List<Article> createArticlesResolveRedirects(
            SessionHolder session, ArticleIdAndTitle[] aid__with_blacklist__redirects,
            Map<Integer, Article> map_id_article_exist,
            int n_limit,
            Map<String,Set<String>> m_out, Map<String,Set<String>> m_in)
    {
              ArticleIdAndTitle[] aid, aid__with_blacklist;
              
              aid__with_blacklist__redirects = ArticleIdAndTitle.join(aid__with_blacklist__redirects, map_id_article_exist);
              aid__with_blacklist            = Redirect.resolveByIdAndTitles(session, m_out, m_in,
              aid__with_blacklist__redirects);
              
              aid__with_blacklist  = ArticleIdAndTitle.skipTitles(session, aid__with_blacklist);
                                            StringMap.skipTitles(session, m_out, m_in);
              aid                  = session.category_black_list.DeleteUsingBlackList(aid__with_blacklist);
              
              return createArticlesByIdAndTitle(session, aid, map_id_article_exist, //_m_in, 
                                                    n_limit, m_out, m_in);
    }
    
    /** Creates list of articles for each element in 'aid_array' if it is not 
     * presented in 'map_id_article_exist'.
     * Updates .redirects of articles in 
     * 'map_id_article_exist' by adding .redirects from 'aid_array'.
     * Result list is limited by 'n_limit' value.
     *
     * @param aid_to    array of (id,title_to) the articles will be created with
     * @param map_id_article_exist map from ID to an existing article, 
     *                  they could be updated, but they should not be duplicated
     *                  in the returned list
     *
     * @param n_limit   max number of returned articles, negative value means no limit
     * @param m_out     map &lt;title of article, list of titles links_out>
     * @param m_in      map &lt;title of article, list of titles links_in>
     */
    public static List<Article> createArticlesByIdAndTitle(
            SessionHolder session, ArticleIdAndTitle[] aid_array,
            Map<Integer, Article> map_id_article_exist,
            //Map<String,Set<String>> local_m_in,   // documentation: Adds local_m_in map to m_in, m_out.
            int n_limit,
            Map<String,Set<String>> m_out, Map<String,Set<String>> m_in)
    {
        // todo sort aid_array: articles with id > 0 move to begin, i.e. redirect to end, in order to skip additional retrieving of redirect pages from DB.
        // ...
        
        List<Article> articles = new ArrayList<Article>();
        
        for(ArticleIdAndTitle aid:aid_array) {
            int    id = aid.id;
            
            if(0 != id) {
                Article a;
                if(map_id_article_exist.containsKey(id)) {
                    a = map_id_article_exist.get(id);
                    Redirect.addRedirect(a, aid.redirect);
                
                } else {
if(id < 0) {
    System.out.println("Error: Article is created for the redirect aid with id="+id+"; title="+aid.title);
}
                    a = new Article();
                    a.page_id    = id;
                    a.page_title = aid.title;       //a.page_title = Encodings.FromTo(s, "ISO8859_1", "UTF8");
                    a.redirect   = aid.redirect;
                    a.id_categories = CategoryBlackList.getFirstLevelCategoriesID (session, id);
                    articles.add(a);

                    //                      -->
                    //                    /        -->
                    // m_out (title_from) ---->       \
                    //                    \       ----> m_in (title_to)
                    //                      -->       /
                    //                             -->
                    //                                    title_to is known

                    /*if(local_m_in.containsKey(to)) {
                        for(String _title_from:local_m_in.get(to)) {
                            addTitlesToMaps(_title_from, to, m_out, m_in);
                        }
                    }*/
                    if(n_limit>=0 && articles.size() >= n_limit) {
                        break;
                    }
                }
            }
        }
        return articles;
    }
    
    
    /** Adds title_from and title_to to maps.
     */
    public static void addTitlesToMaps(
            String title_from, String title_to,
            Map<String,Set<String>> m_out, 
            Map<String,Set<String>> m_in
            )
    {
        if(!m_out.containsKey(title_from))
            m_out.put(title_from, new HashSet<String>());
        m_out.get(title_from).add(title_to);

        if(!m_in.containsKey(title_to))
            m_in.put(title_to, new HashSet<String>());
        m_in.get(title_to).add(title_from);
    }
    
    
    private static final int[] _one = new int[1];
    /** Gets all links from nodes_from to vertices in map_title_article by function ..LtoByLFrom()
     *
     * @param m_out  map &lt;title of article, list of titles links_out>
     * @param m_in   map &lt;title of article, list of titles links_in>
     *
     * @see great superb picture at LinksBaseSet.CreateBaseSet() describing nodes_from
     */ 
    public static void getAllLinksFromNodes(SessionHolder session, 
            Map<String, Article> map_title_article, Article[] nodes_from,
            Map<String,Set<String>> m_out, 
            Map<String,Set<String>> m_in)
    {   
        // gets (foreach article) id of destination pages (pl_from, _to(pl_namespace.pl_title))
        for(Article a:nodes_from) {
            int     id_from     = a.page_id;
            String  title_from  = a.page_title;
            
            _one[0] = id_from < 0 ? -id_from : id_from ;
            String[] titles_to = getTitleToByIDFrom(session, _one, PageNamespace.MAIN);
            
            if(null != titles_to) {
                for(String t:titles_to) {
                    if(map_title_article.containsKey(t)) {
                        addTitlesToMaps(title_from, t, m_out, m_in);
                    }
                }
            }
        }
        
        // copy Lists m_out, m_in to links_out[], links_in[]
        for(String t:m_out.keySet()) {
            if(map_title_article.containsKey(t)) {
                Article a = map_title_article.get(t);
                a.links_out = Article.getIdExistedInMap(m_out.get(t), map_title_article);
            }
        }
        for(String t:m_in.keySet()) {
            if(map_title_article.containsKey(t)) {
                Article a = map_title_article.get(t);
                a.links_in = Article.getIdExistedInMap(m_in.get(t), map_title_article);
            }
        }
    }
    
    
    /**
     * Gets all links which link the articles in the hashmap.
     * Write them to articles.links_in[] and links_out[].
     * 
     * old mediawiki 1.4
     * Wrong and slow SQL:
     * SELECT l_to,l_from FROM links WHERE l_to IN (18991) OR l_from IN (18991);    0.9, 0.75 second: 0.3
     *
     * Right and fast SQL:
     * SELECT l_to,l_from FROM links WHERE l_to IN (18991) AND l_from IN (18991);   0.17      second: 0.00
     *
     * SELECT l_to,l_from FROM links WHERE l_to IN (18991);                         0.02
     *//*
    public static void getAllLinks(SessionHolder session, Map<String, Article> map_title_article) {
        
        // m_out - local map<id of article, list of links_out>
        // m_in  - local map<id of article, list of links_in>
        Map<String,Set<String>> m_out = new HashMap<String,Set<String>>();
        Map<String,Set<String>> m_in  = new HashMap<String,Set<String>>();
        
        // gets (foreach article) id of destination pages (pl_from, _to(pl_namespace.pl_title))
        for(Article a_from:map_title_article.values()) {
            int     id_from     = a_from.page_id;
            String  title_from  = a_from.page_title;
            
            int[] i = new int[1];
            i[0] = id_from < 0 ? -id_from : id_from ;
            String[] titles_to = getTitleToByIDFrom(session, i, PageNamespace.MAIN);
            i = null;
            
            if(null != titles_to) {
                for(String t:titles_to) {
                    if(map_title_article.containsKey(t)) {
                        addTitlesToMaps(title_from, t, m_out, m_in);
                    }
                }
            }
        }
        
        // copy Lists m_out, m_in to links_out[], links_in[]
        for(String t:m_out.keySet()) {
            Set<String> ss = m_out.get(t);
            Article a = map_title_article.get(t);
            
            a.links_out = new int[ss.size()];
            int i=0; for(String s:ss) {
                a.links_out[i++] = map_title_article.get(s).page_id;
            }
        }
        for(String t:m_in.keySet()) {
            Set<String> ss = m_in.get(t);
            Article a = map_title_article.get(t);
            
            a.links_in = new int[ss.size()];
            int i=0; for(String s:ss) {
                a.links_in[i++] = map_title_article.get(s).page_id;
            }
        }
        m_out.clear();
        m_in.clear();
        m_in  = null;
        m_out = null;
    }*/
}
