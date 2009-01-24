/*
 * LinksBaseSet.java - Create Root Set and Base Set of links
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import wikipedia.language.Encodings;
import wikipedia.sql.*;
import wikipedia.util.*;
import wikipedia.data.ArticleIdAndTitle;
import wikipedia.data.Redirect;

import java.util.*;

    /*
     * Algorithm text
     *     *
     * "Thus, we assemble a root set Rp consisting of t pages that point to p; we grow this into a
     * base set Sp as before; and the result is a subgraph Gp in which we can search for hubs and
     * authorities." Kleinberg p.15
     *
     * Algorithm pseudocode
     *
     * Input: p is page_title
     * Goal: find synonyms (related terms) of p
     * Actions:
     * 1. Get page_id of articles which refer to p. (This is Root Set Rp, but without the p.)
     *              SELECT l_from FROM links WHERE l_to = page_id of p           UNIQUE
     *              detail:  SELECT page_id, page_title FROM page WHERE page_namespace=0 AND page_id IN (SELECT l_from FROM links WHERE l_to=N
     * 2. Expanding the root set into a base set Sp.
     *   2.1 Base set Sp = Rp + articles which refer to any of Rp
     *              SELECT l_from FROM links WHERE l_to IN (page_id of Rp) UNIQUE LIMIT t \Rp
     *   2.2 Base set Sp += articles which are referred by any of Rp.
     *              SELECT l_to FROM links WHERE l_from IN (page_id of Rp)
     *   2.3 Get (all other) links inside Sp.
     *              (SELECT l_to   FROM links WHERE l_from IN (page_id of Sp\Rp)) \ Rp
     *              (SELECT l_from FROM links WHERE l_to   IN (page_id of Sp\Rp)) \ Rp
     *  
     */ 
public class LinksBaseSet {

    public LinksBaseSet() {
    }
    
    /** Map from article's title to ArticleIdAndTitle object - temp here */
    private static Map<String, ArticleIdAndTitle> _mr = new HashMap<String, ArticleIdAndTitle>(0);
    
    /** Parametrized creation of base set via parameters t, d.
     *  @param t    root_set_size - number of articles in the root set, 
     *              negative value means no limit. There are different methodics 
     *              to select the articles to the root set, e.g., randomly.
     *
     *  @param d    increment - number of articles which could be added to the base set
     *                  (they refer to one of the pages in the root base)
     *
     *  @param synonyms list of synonyms (for the word page_title) rated by user(s)
     *
     *<pre>  Algorithm of creation base set (see comments for the function above):
     *      
     *  Set Sp := Rp
     *  For each page p from Rp
     *  Let G+(p) denote the set of all pages p points to.
     *  Let G-(p) denote the set of all pages pointing to p.
     *  Add all pages in G+(p) to Sp.
     *  If |G-(p)| <= d then
     *      Add all pages in G-(p) to Sp.
     *  Else
     *      Add an arbitrary set of d pages from G-(p) to Sp.
     *  End
     *  Return Sp</pre>
     *
     *
     *  Speed up the very slow function Links.GetAllLinks
     *  (i suppose that there is symmetry in links, e.g., a->b => b<-a):
     *
     *  (todo: sets priority of reciprocity links,  e.g., a->b && b->a > a->b || b->a):
     *
     *                                   root_nodes1
     *                                 /             \
     *             (increment links)  |    a1 ->      | (all links)
     * base_nodes1 ------------------>|  + synonyms   |-------------> base_nodes2
     *                                |  LToByLFrom-> |
     *             <-- LFromByLTo <--    (all links)    --> LToByLFrom -->
     *
     * assert /after drawing this picture, I am Picasso/ that I == II, but II should be faster:
     * I.  Links.getAllLinks(session, map_title_article);
     * II. Links.getAllLinksFromNodes(session, from:base_nodes1);
     */
    public static Map<Integer, Article> CreateBaseSet(
            String page_title,List<String> synonyms,
            SessionHolder session,
            int root_set_size, int increment)
    {
        //System.out.println("CreateBaseSet: Connection conn="+session.connect.conn);
        
        // 1.
        //String latin1_article = Encodings.UTF8ToLatin1(page_title);
        //String latin1_article = Encodings.FromTo(page_title, "UTF8", "ISO8859_1");
        int p = PageTable.getIDByTitle(session.connect, page_title); // latin1_article
        
        if (p < 0) {
            // redirect
            ArticleIdAndTitle a_to = Redirect.getByRedirect (session, p, page_title, _mr);
             
            if(null == a_to) {
                return null;
            }
            p           = a_to.id;
            page_title  = a_to.title;
        }
                
        
        //System.out.println("CreateBaseSet: int p="+p);
        if(0 == p)      {  // page is absent
            return null;}
        
        // m_out - local map<title of article, list of titles links_out> // from article to articles
        // m_in  - local map<title of article, list of titles links_in>
        // are used in getAllLinksFromNodes to find all links between articles
        Map<String,Set<String>> m_out = new HashMap<String,Set<String>>();
        Map<String,Set<String>> m_in  = new HashMap<String,Set<String>>();
        
        Article[] a1 = new Article[1];
        a1[0] = new Article();
        
        a1[0].page_id    = p;
        a1[0].page_title = session.source_page_title  = page_title;
        a1[0].type       = NodeType.ID_SOURCE_ARTICLE;  // set type for the very source article
        
        session.source_article_id  = 0; // for first calls of getLToByLFrom() 
        Article[] root_nodes = Links.getLToByLFrom(session, a1, root_set_size, m_out, m_in);
        session.source_article_id  = p; 
        
        // old
        //Article[] root_nodes = Links.GetLFromByLTo(session, p, root_set_size);
        // new todo: select first n links in article

        // add synonyms (rated by user) to root_nodes
        if(0 < synonyms.size()) {
            List<Article> a_rated_synonyms = new ArrayList<Article>();
        
            Set<String> root_titles = new HashSet<String>(root_nodes.length);
            for(Article a:root_nodes) {
                root_titles.add(a.page_title);
            }
            for(String s:synonyms) {
                if(!root_titles.contains(s)) {
                    Article a = new Article();
                    a.page_title = s;
                    a.page_id    = PageTable.getIDByTitle(session.connect, s);
                    a_rated_synonyms.add(a);
                }
            }
            root_nodes = Article.joinUnique(root_nodes, 
                    (Article[])a_rated_synonyms.toArray(Article.NULL_ARTICLE_ARRAY));
        }
        
        if(null == root_nodes || 0 == root_nodes.length) {
            if (null != session.dump && null != session.dump.file_dot.GetFilename()) {
                String bat_text = "\n:: " + session.dump.file_dot.GetFilename() +".dot \t Warning: no page refers to this page.\n";
                session.dump.file_bat.Print(bat_text);
                session.dump.file_bat.Flush();
            }
            return null;    // nobody refers to the p page
        }
        Article.SetType(root_nodes, NodeType.ROOT);
        root_nodes = Article.joinUnique(root_nodes, a1);
if (null != session.dump) { session.dump.DumpDotBat(root_nodes, page_title + "1_0_root_nodes.dot"); }

        // 2.1
        //Article[] base_nodes1 = Links.getLFromByLTo(session, root_nodes, increment, m_out, m_in);
        int n_limit2 = -1;
        Article[] base_nodes1 = Links.getLFromByLTo(session, root_nodes, increment, n_limit2, m_out, m_in);
if (null != session.dump) { session.dump.DumpDotBat(base_nodes1, page_title + "2_1_base_nodes1_GetLFromByLTo.dot"); }
        
        // 2.2
        Article[] base_nodes2 = Links.getLToByLFrom(session, root_nodes, -1, m_out, m_in);
if (null != session.dump) { session.dump.DumpDotBat(base_nodes2, page_title + "2_2_1_base_nodes2_GetLToByLFrom.dot"); }
        
        Article[] base_nodes           = Article.joinUnique(base_nodes1, base_nodes2);
        Article[] base_and_root_nodes  = Article.joinUnique(base_nodes, root_nodes);
if (null != session.dump) { session.dump.DumpDotBat(base_and_root_nodes, page_title + "2_2_2_base_and_root_nodes.dot"); }
        Article.SetType(base_and_root_nodes, NodeType.BASE);
        
        // 2.3
        Map<Integer, Article> map_id_article    = Article.createMapIdToArticleWithoutRedirects   (base_and_root_nodes);
        Map<String,  Article> map_title_article = Article.createMapTitleToArticleWithoutRedirects(base_and_root_nodes);
        
        for(String s:synonyms) {
            if(map_title_article.containsKey(s)) { // redirects has not article
                map_title_article.get(s).type = NodeType.RATED_SYNONYMS;
            }
        }
            
        // set type for the very source article
        // todo skip
        //map_id_article.get(p).type = NodeType.ID_SOURCE_ARTICLE;
        assert(map_id_article.get(p).type == NodeType.ID_SOURCE_ARTICLE);
                
        Links.getAllLinksFromNodes(session, map_title_article, base_nodes1, m_out, m_in);
        //Links.getAllLinks(session, map_title_article);
        if (null != session.dump) {
            String  article_fn = StringUtilRegular.encodeRussianToLatinitsa(page_title, Encodings.enc_java_default, Encodings.enc_int_default);
            session.dump.DumpDotBat(map_id_article, article_fn + ".dot");
        }
        
        m_out.clear();
        m_in.clear();
        m_in  = null;
        m_out = null;

        return map_id_article;
    }
}
