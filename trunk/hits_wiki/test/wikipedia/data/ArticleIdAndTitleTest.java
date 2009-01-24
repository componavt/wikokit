/*
 * ArticleIdAndTitleTest.java
 * JUnit based test
 */

package wikipedia.data;

import junit.framework.*;

import wikipedia.kleinberg.Article;
import wikipedia.kleinberg.SessionHolder;

import wikipedia.sql.Connect;
import wikipedia.sql.PageTable;
import wikipedia.sql.PageNamespace;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class ArticleIdAndTitleTest extends TestCase {
    
    static int    t_redirect_id, t2_id,          t3_id;
    static String t_redirect,    t2_absent_page, t3;
    
    static String title_from, title_to;
    static int       id_from,    id_to;
    
    static Article[] articles_t_redirect_t3;

    public  Connect  connect, connect_ru;
    public  SessionHolder    session;
    
    public ArticleIdAndTitleTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect = new Connect();
        connect.Open(Connect.WP_HOST, Connect.WP_DB, Connect.WP_USER, Connect.WP_PASS);
        
        connect_ru = new Connect();
        connect_ru.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,Connect.WP_RU_USER,Connect.WP_RU_PASS);
        
        session = new SessionHolder();
        session.initObjects();
        int categories_max_steps = 99;
        
        t_redirect  = connect.enc.EncodeFromJava("MIT");
        t2_absent_page = "The title of absent page";
        t3  = connect.enc.EncodeFromJava("Planetes");
        
        t_redirect_id = PageTable.getIDByTitleNamespace(connect_ru, t_redirect, PageNamespace.MAIN);
        t2_id = PageTable.getIDByTitleNamespace(connect_ru, t2_absent_page, PageNamespace.MAIN);
        assertEquals(0, t2_id);
        t3_id          = PageTable.getIDByTitleNamespace(connect_ru, t3, PageNamespace.MAIN);
        
        session.connect = connect_ru;
        title_from = session.connect.enc.EncodeFromJava("Трансформеры");     // Transformers (toyline)
        title_to   = session.connect.enc.EncodeFromJava("Робот");            // Robot
        id_from = PageTable.getIDByTitleNamespace(connect_ru, title_from, PageNamespace.MAIN);
        id_to   = PageTable.getIDByTitleNamespace(connect_ru, title_to,   PageNamespace.MAIN);
        
        
        articles_t_redirect_t3 = new Article[2];
        articles_t_redirect_t3[0] = new Article();
        articles_t_redirect_t3[0].page_id     = t_redirect_id;
        articles_t_redirect_t3[0].page_title  = t_redirect;
        articles_t_redirect_t3[1] = new Article();
        articles_t_redirect_t3[1].page_id     = t3_id;
        articles_t_redirect_t3[1].page_title  = t3;
    }

    protected void tearDown() throws Exception {
        connect.Close();
        connect_ru.Close();
    }

    /**
     * Test of create method, of class wikipedia.data.ArticleIdAndTitle.
     */
    public void testCreate() {
        System.out.println("create");
        
        ArticleIdAndTitle[] result = ArticleIdAndTitle.create(null);
        result = ArticleIdAndTitle.create(articles_t_redirect_t3);
        
        assertEquals(result.length, 2);
        assertEquals(result[0].id,    t_redirect_id);
        assertEquals(result[0].title, t_redirect);
        assertEquals(result[1].id,    t3_id);
        assertEquals(result[1].title, t3);
    }
    
    
    public void testJoin() {
        System.out.println("join");
        
        ArticleIdAndTitle[] aid = new ArticleIdAndTitle[2];
        aid[0] = new ArticleIdAndTitle(t2_id, t2_absent_page);
        aid[1] = new ArticleIdAndTitle(t3_id, t3);
        
        Map<Integer, Article> map_id_article_exist = new HashMap<Integer, Article> ();
        map_id_article_exist.put(t_redirect_id, articles_t_redirect_t3[0]);
        map_id_article_exist.put(t3_id,         articles_t_redirect_t3[1]);
        
        ArticleIdAndTitle[] result = ArticleIdAndTitle.join(aid, map_id_article_exist);
        
        // result = aid      + map_id_article_exist
        // result = [t2, t3] + [t1, t_redirect]     = [t2, t3, t1, t_redirect]
        assertEquals(4, result.length);
    }
    
    /**
     * Test of createByTitle method, of class wikipedia.data.ArticleIdAndTitle.
     */
    public void testCreateByTitle() {
        System.out.println("createByTitle");
        
        assertTrue(null != connect);
        Map<String,Set<String>> m_out = new HashMap<String,Set<String>>();
        Map<String,Set<String>> m_in  = new HashMap<String,Set<String>>();
        
        ArticleIdAndTitle[] result = ArticleIdAndTitle.createByTitle(connect, m_out, m_in, null);
        assertEquals(0, result.length);
    
        String[] titles = {t_redirect, t2_absent_page, t3};
        result = ArticleIdAndTitle.createByTitle(connect, m_out, m_in, titles);
        assertEquals(2, result.length);
        assertEquals(result[0].title, t_redirect);
        assertEquals(result[1].title, t3);
    }
    
    public void testCreateById_ru() {
        System.out.println("createById_ru");
        
        ArticleIdAndTitle[] result = ArticleIdAndTitle.createById(connect, null);
        assertEquals(0, result.length);
    
        Integer[] source_id     = {   id_from,    id_to};
        //String[]  result_titles = {title_from, title_to};
        
        result = ArticleIdAndTitle.createById(connect_ru, source_id);
        assertEquals(2, result.length);
        assertEquals(title_from,    result[0].title);
        assertEquals(title_to,      result[1].title);
    }
    
    public void testCreateById_redirects_ru() {
        System.out.println("createById_redirects_ru");
        
        if(t_redirect_id < 0)
            t_redirect_id = -t_redirect_id;
        
        Integer[] source_id     = { t_redirect_id, t2_id,          t3_id};
        Integer[] result_id     = { t_redirect_id,                 t3_id};
        //        source_titles    t_redirect,    t2_absent_page,  t3;
        String[]  result_titles = {t_redirect,                     t3};
        
        ArticleIdAndTitle[] result = ArticleIdAndTitle.createById(connect_ru, source_id);
        
        assertEquals(2, result.length);
        assertEquals(t_redirect,    result[0].title);
        assertTrue  (                result[0].id < 0);
        
        assertEquals(t3,             result[1].title);
        assertEquals(t3_id,          result[1].id   );
    }
    
    
    public void testCreateMapIdTitle() {
        System.out.println("createMapIdTitle");
        
        Map<Integer, String> result = ArticleIdAndTitle.createMapIdTitle(null);
        assertEquals(0, result.size());
        
        ArticleIdAndTitle[] aid = new ArticleIdAndTitle[2];
        aid[0] = new ArticleIdAndTitle(1, t_redirect);
        aid[1] = new ArticleIdAndTitle(3, t3);
        
        result = ArticleIdAndTitle.createMapIdTitle(aid);                    
        assertEquals(result.size(), 2);
        assertEquals(result.get(1), t_redirect);
        assertEquals(result.get(3), t3);
    }
    
    public void testGetTitles() {
        System.out.println("getTitles");
        
        List<String> result = ArticleIdAndTitle.getTitles (null);
        assertEquals(0, result.size());
        
        ArticleIdAndTitle[] aid = new ArticleIdAndTitle[2];
        aid[0] = new ArticleIdAndTitle(0, t_redirect);
        aid[1] = new ArticleIdAndTitle(0, t3);
        
        result = ArticleIdAndTitle.getTitles (aid);                    
        assertEquals(result.size(), 2);
        assertEquals(result.get(0), t_redirect);
        assertEquals(result.get(1), t3);
    }

    
    public void testSkipTitles() {
        System.out.println("testSkipTitles");
        
        //session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        //session.randomPages(false);
        
        ArticleIdAndTitle[] result;
        
        // null test 1
        result = ArticleIdAndTitle.skipTitles (session, null);
        assertEquals(null, result);
        
        // test with skipTitlesWithSpaces = false
        ArticleIdAndTitle[] aid = new ArticleIdAndTitle[2];
        aid[0] = new ArticleIdAndTitle(0, "word");
        aid[1] = new ArticleIdAndTitle(0, "several_words_with_spaces");
        
        result = ArticleIdAndTitle.skipTitles (session, aid);
        assertEquals(2, result.length);
        
        // /////////////////////////////////////
        // test with skipTitlesWithSpaces = true
        session.skipTitlesWithSpaces(true);
        result = ArticleIdAndTitle.skipTitles (session, aid);
        assertEquals(1, result.length);
        
        // null test 2
        result = ArticleIdAndTitle.skipTitles (session, null);
        assertEquals(0, result.length);
    }
    
    public void testSkipTitles_redirects__and__removed_articles() {
        System.out.println("testSkipTitles_redirects__and__removed_articles");
        ArticleIdAndTitle[] result;
        
        ArticleIdAndTitle[] aid = new ArticleIdAndTitle[2];
        aid[0] = new ArticleIdAndTitle(10, "word");
        aid[1] = new ArticleIdAndTitle(11, "several_words_with_spaces");
        
        List<ArticleIdAndTitle> redirect = new ArrayList<ArticleIdAndTitle>(2);
        redirect.add( new ArticleIdAndTitle(12, "redirect1") );
        redirect.add( new ArticleIdAndTitle(13, "redirect2_with_several_words_with_spaces") );
        aid[1].redirect = redirect;
        
        // /////////////////////////////////////
        // test with skipTitlesWithSpaces = false
        
        session.skipTitlesWithSpaces(false);
        session.removed_articles.clear();
        result = ArticleIdAndTitle.skipTitles (session, aid);
        assertEquals(0, session.removed_articles.sizeId());
        assertEquals(0, session.removed_articles.sizeTitle());
        
        // /////////////////////////////////////
        // test with skipTitlesWithSpaces = true
        
        session.skipTitlesWithSpaces(true);
        result = ArticleIdAndTitle.skipTitles (session, aid);
        assertEquals(3, session.removed_articles.sizeId());
        assertEquals(3, session.removed_articles.sizeTitle());
        assertTrue(session.removed_articles.hasTitle("several_words_with_spaces"));
        assertTrue(session.removed_articles.hasTitle("redirect1"));
        assertTrue(session.removed_articles.hasTitle("redirect2_with_several_words_with_spaces"));
    }
     
}
