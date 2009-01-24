/*
 * RedirectTest.java
 * JUnit based test
 */

package wikipedia.data;

import junit.framework.*;

import wikipedia.sql.Connect;
import wikipedia.sql.PageTable;
import wikipedia.sql.PageNamespace;
import wikipedia.sql.Links;

import wikipedia.kleinberg.SessionHolder;
import wikipedia.kleinberg.Article;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class RedirectTest extends TestCase {

    public Connect  connect, connect_ru;
    ArticleIdAndTitle[] aid_t1_tredirect;
    ArticleIdAndTitle[] aid_s1_sredirect;
    
    //Article[]       source_nodes, redirect_nodes1, redirect_nodes2;
    SessionHolder   session;
    int             categories_max_steps;
    static String t1,    t_redirect,    t2;
    static int    t1_id, t_redirect_id, t2_id;
    static String s1,    s_redirect,    s2,    s1b,     s_redirect2,    s3;
    static int    s1_id, s_redirect_id, s2_id, s1b_id,  s_redirect2_id, s3_id;
    
    static Map<String,Set<String>> m_out = new HashMap<String,Set<String>>();
    static Map<String,Set<String>> m_in  = new HashMap<String,Set<String>>();
    
    public RedirectTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect = new Connect();
        connect.Open(Connect.WP_HOST,       Connect.WP_DB,    Connect.WP_USER,    Connect.WP_PASS);
        
        connect_ru = new Connect();
        connect_ru.Open(Connect.WP_RU_HOST, Connect.WP_RU_DB, Connect.WP_RU_USER, Connect.WP_RU_PASS);
        
        
        // Redirects: t1 -> t_redirect -> t2 
        // Джемини  -> MIT -> Массачусетсский_технологический_институт
        // Польская_Википедия -> Бот_(программа) -> Робот_(программа)
        //            CAPTCHA ->             Bot -> Робот_(программа)
        t1          = connect.enc.EncodeFromJava("Джемини");
        t_redirect  = connect.enc.EncodeFromJava("MIT");
        t2          = connect.enc.EncodeFromJava("Массачусетсский_технологический_институт");
        
        s1          = connect.enc.EncodeFromJava("Польская_Википедия");
        s_redirect  = connect.enc.EncodeFromJava("Бот_(программа)");
        
        s1b         = connect.enc.EncodeFromJava("CAPTCHA");
        s_redirect2 = connect.enc.EncodeFromJava("Bot");
        
        s2          = connect.enc.EncodeFromJava("Робот_(программа)");
        s3          = connect.enc.EncodeFromJava("Википедия");
        
        m_out.clear(); m_in.clear();
        Links.addTitlesToMaps(t1, t_redirect, m_out, m_in);
        Links.addTitlesToMaps(s1, s_redirect, m_out, m_in);
        Links.addTitlesToMaps(s1b,s_redirect2, m_out, m_in);
        
        t1_id         = PageTable.getIDByTitleNamespace(connect_ru, t1, PageNamespace.MAIN);
        t_redirect_id = PageTable.getIDByTitleNamespace(connect_ru, t_redirect, PageNamespace.MAIN);
        t2_id         = PageTable.getIDByTitleNamespace(connect_ru, t2, PageNamespace.MAIN);
        
        s1_id         = PageTable.getIDByTitleNamespace(connect_ru, s1, PageNamespace.MAIN);
        s_redirect_id = PageTable.getIDByTitleNamespace(connect_ru, s_redirect, PageNamespace.MAIN);
        s1b_id        = PageTable.getIDByTitleNamespace(connect_ru, s1b,PageNamespace.MAIN);
        s_redirect2_id= PageTable.getIDByTitleNamespace(connect_ru, s_redirect2,PageNamespace.MAIN);
        s2_id         = PageTable.getIDByTitleNamespace(connect_ru, s2, PageNamespace.MAIN);
        s3_id         = PageTable.getIDByTitleNamespace(connect_ru, s3, PageNamespace.MAIN);
        
        String[] _t = {t1, t_redirect};
        aid_t1_tredirect = ArticleIdAndTitle.createByTitle(connect_ru, m_out, m_in, _t);
        
        String[] _s = {s1, s_redirect, s_redirect2};
        aid_s1_sredirect = ArticleIdAndTitle.createByTitle(connect_ru, m_out, m_in, _s);
        
        session = new SessionHolder();
        session.initObjects();
        categories_max_steps = 99;
    }

    protected void tearDown() throws Exception {
        connect.Close();
        connect_ru.Close();
    }

    
    public void testAddRedirect_ArticleIdAndTitle_ru() {
        System.out.println("testAddRedirect_ArticleIdAndTitle_ru");
        
        Redirect.addRedirect(null, 1111, "string");
        
        ArticleIdAndTitle aid = new ArticleIdAndTitle(1, "title1");
        assertEquals(aid.redirect.size(), 0);
        
        Redirect.addRedirect(aid, 1, "title1");
        assertEquals(aid.redirect.size(), 1);
        
        Redirect.addRedirect(aid, 2, "title2");
        assertEquals(aid.redirect.size(), 2);
        
        // again title1, skip it
        Redirect.addRedirect(aid, 1, "title1");
        assertEquals(aid.redirect.size(), 2);
    }
    
    
    public void testAddRedirect_Article_ru() {
        System.out.println("testAddRedirect_Article_ru");
        
        Article a = new Article();
        
        Redirect.addRedirect(a, ArticleIdAndTitle.NULL_ARTICLEIDANDTITLE_LIST);
        assertEquals(0, a.redirect.size());
        
        List<ArticleIdAndTitle> r_list = new ArrayList<ArticleIdAndTitle>();
        ArticleIdAndTitle r1 = new ArticleIdAndTitle(1, "title1");
        r_list.add(r1);
        Redirect.addRedirect(a, r_list);
        assertEquals(1, a.redirect.size());
        
        List<ArticleIdAndTitle> r_list2 = new ArrayList<ArticleIdAndTitle>();
        ArticleIdAndTitle r2 = new ArticleIdAndTitle(2, "title2");
        r_list2.add(r1);
        r_list2.add(r2);
        Redirect.addRedirect(a, r_list2);
        assertEquals(2, a.redirect.size());
    }
    
        
    public void testHasRedirect_ru() {
        System.out.println("testHasRedirect_ru");
        
        assertFalse(Redirect.hasRedirect(null));
        assertFalse(Redirect.hasRedirect(ArticleIdAndTitle.NULL_ARTICLEIDANDTITLE_ARRAY));
        
        assertTrue(Redirect.hasRedirect(aid_t1_tredirect));
        assertTrue(Redirect.hasRedirect(aid_s1_sredirect));
    }
    
    /**
     * Test of resolveByIdAndTitles method, of class wikipedia.data.Redirect.
     */
    public void testResolveByIdAndTitles_ru() {
        System.out.println("resolveByIdAndTitles_ru");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        // Redirects: 
        // t1       -> t_redirect -> t2
        // Джемини  -> MIT    -> Массачусетсский_технологический_институт 
        // 62186    -> -52141 -> 52137
        
        ArticleIdAndTitle[] result = Redirect.resolveByIdAndTitles(session, m_out, m_in, aid_t1_tredirect);
        assertEquals(2, result.length);
        
        // t1 remain
        assertEquals(result[0].title,t1);
        assertEquals(result[0].redirect.size(), 0);
        
        // t_redirect replaced by t2
        assertEquals(result[1].title, t2);
        assertEquals(result[1].id,    t2_id);
        
        assertTrue(result[1].redirect != null);
        assertEquals(result[1].redirect.size(), 1);
        ArticleIdAndTitle redirect1 = result[1].redirect.get(0);
        assertEquals(     redirect1.title, t_redirect);
        assertEquals(     redirect1.id,    t_redirect_id);
    }
    
    
    
    
    
    /** Checks updating of links in m_out, m_in 
     */
    public void testResolveByIdAndTitles_m_out_m_in_ru() {
        System.out.println("testResolveByIdAndTitles_m_out_m_in_ru");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        m_out.clear(); m_in.clear();
        Links.addTitlesToMaps(t1, t_redirect, m_out, m_in);
        
        // test 1 with skip spaces: 
        //         t1       -> t_redirect -> t2
        // Facts : Джемини  -> MIT    -> Массачусетсский_технологический_институт
        // Source: Джемини  -> MIT    
        // Result: Джемини  -> Массачусетсский_технологический_институт
        
        assertEquals(1, m_out.size());
        assertEquals(1, m_in.size());
        assertEquals(1, m_out.get(t1).size());
        assertEquals(1, m_in. get(t_redirect).size());
        assertTrue(m_in.containsKey(t_redirect));
        assertTrue(m_out.get(t1).contains( t_redirect ));
        assertTrue(              m_in.get( t_redirect ).contains(t1));
        
        ArticleIdAndTitle[] result = Redirect.resolveByIdAndTitles(session, m_out, m_in, aid_t1_tredirect);
        
        assertEquals(1, m_out.size());
        assertEquals(1, m_in.size());
        assertEquals(1, m_out.get(t1).size());
        assertTrue(m_in.containsKey(t2));
        assertEquals(1, m_in. get(t2).size());
        assertTrue(m_in.containsKey(t2));
        assertTrue(m_out.get(t1).contains( t2 ));
        assertTrue(              m_in.get( t2 ).contains(t1));
    }

    /** Tests treating of several redirects to one page */
    public void testResolveByIdAndTitles_several_redirects_ru() {
        System.out.println("testResolveByIdAndTitles_several_redirects_ru");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        // test 2
        // s1                 -> s_redirect      -> s2 
        // Польская_Википедия -> Бот_(программа) -> Робот_(программа)
        //                                   Bot -> Робот_(программа)
        ArticleIdAndTitle[] result = Redirect.resolveByIdAndTitles(session, m_out, m_in, aid_s1_sredirect);
        assertEquals(2, result.length);
        
        // s1 remain
        assertEquals(s1, result[0].title);
        assertEquals(0,  result[0].redirect.size());
        
        // s_redirect and s_redirect2 were replaced by s2
        assertEquals(result[1].title, s2);
        assertEquals(result[1].id,    s2_id);
        
        assertTrue(result[1].redirect != null);
        assertEquals(result[1].redirect.size(), 2);
        
        ArticleIdAndTitle redirect1 = result[1].redirect.get(0);
        assertEquals(     redirect1.title, s_redirect);
        assertEquals(     redirect1.id,    s_redirect_id);
        
        ArticleIdAndTitle redirect2 = result[1].redirect.get(1);
        assertEquals(     redirect2.title, s_redirect2);
        assertEquals(     redirect2.id,    s_redirect2_id);
    }
    
    
}

    // redirects philoshophy (without the page 'redirect')
    //
    // 1a. SELECT page_id, page_is_redirect FROM page WHERE page_namespace=0 AND page_title='MIT';
    //           52141      1
    // 1b. SELECT page_id, page_is_redirect FROM page WHERE page_namespace=0 AND page_title='Массачусетсский_технологический_институт';
    //           52137      0
    // 2a. SELECT * FROM pagelinks WHERE pl_from=52141;
    //           Массачусетсский_технологический_институт
    // 2b. SELECT * FROM pagelinks WHERE pl_from=52137;
    //          ~ 106 rows...
    // 3a. who links to 'MIT':
    //     SELECT * FROM pagelinks WHERE pl_title='MIT';
    