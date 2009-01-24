/*
 * ArticleTest.java
 * JUnit based test
 */

package wikipedia.kleinberg;

import wikipedia.util.*;
import wikipedia.sql.PageTable;
import wikipedia.sql.PageNamespace;
import wikipedia.sql.Connect;

import junit.framework.*;
import java.util.*;


public class ArticleTest extends TestCase {
    
    Article                     node;
    int   []  articles_id   = { 18991,    10484,    3321,     1121};    
    String[] articles_title = {"title1", "title2", "title3", "title4"};
    
    Map<Integer, Article>       map_id_to_article;
    Map<String, Article>        map_title_article;
    
    public Connect  connect, connect_ru;
    SessionHolder   session;
    int             categories_max_steps;
    
    public ArticleTest(String testName) {
        super(testName);
    }

    protected void setUp() throws java.lang.Exception {
        node = new Article();
        
        float[] x_values = {12.f, 2.f, 7.f, 7.f};
        float[] y_values = {20.f, 60.f, 10.f, 10.f};
        int i;
        
        map_id_to_article = new HashMap<Integer, Article>();
        map_title_article = new HashMap<String, Article>();
        
        for (i=0; i<x_values.length; i++) {
            Article source_node = new Article();
            source_node.page_id  = articles_id [i];
            source_node.x       = x_values      [i];
            source_node.y       = y_values      [i];
            map_id_to_article.put(source_node.page_id, source_node);
            
            source_node.page_title = articles_title[i];
            map_title_article.put(articles_title[i], source_node);
        }
        
        connect = new Connect();
        connect.Open(Connect.WP_HOST, Connect.WP_DB, Connect.WP_USER, Connect.WP_PASS);
        
        connect_ru = new Connect();
        connect_ru.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,Connect.WP_RU_USER,Connect.WP_RU_PASS);

        session = new SessionHolder();
        session.initObjects();
        categories_max_steps = 99;
    }

    protected void tearDown() throws java.lang.Exception {
        connect.Close();
        connect_ru.Close();
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(ArticleTest.class);
        
        return suite;
    }

    /**
     * Test of joinUnique method, of class wikipedia.Article.
     */
    public void testJoinUnique() {
        System.out.println("testJoinUnique");
        Article[] nodes = new Article[2];
        nodes[0] = new Article();
        nodes[1] = new Article();
        nodes[0].page_id = 12;
        nodes[1].page_id = 13;
        Article[] addend = new Article[3];
        addend[0] = new Article();
        addend[1] = new Article();
        addend[2] = new Article();
        addend[0].page_id = 13;
        addend[1].page_id = 7;
        addend[2].page_id = 13;
        Article[] result = node.joinUnique(nodes, addend);
        assertEquals(result.length, 3);
        assertEquals(result[0].page_id, 12);
        assertEquals(result[1].page_id, 13);
        assertEquals(result[2].page_id, 7);
        
        result = node.joinUnique(nodes, null);
        assertEquals(result.length, 2);
        
        result = node.joinUnique(null, addend);
        assertEquals(result.length, 3);
    }
    
    
    public void testSetType() {
        System.out.println("testSetType");
        // Rule: type value can decrease, but it do not increase.
        Article[] nodes = new Article[3];
        nodes[0] = new Article();
        nodes[1] = new Article();
        nodes[2] = new Article();
        nodes[0].type = NodeType.AUTHORITY;
        node.SetType (nodes, NodeType.ROOT);
        
        assertEquals(nodes[0].type, NodeType.AUTHORITY);
        assertEquals(nodes[1].type, NodeType.ROOT);
        assertEquals(nodes[2].type, NodeType.ROOT);
    }
    
    public void testNormalizeNewXNewY () {
        System.out.println("testNormalizeNewXNewY");
        Iterator<Article>  it;
        
        // copy x,y to x_new,y_new
        it = map_id_to_article.values().iterator();
        while (it.hasNext()) {
            Article node = it.next();
            node.x_new += node.x;
            node.y_new += node.y;
        }
        
        node.NormalizeNewXNewY(map_id_to_article);
        float[] total_error = node.UpdateXY(map_id_to_article);
        
        float           links_in_number, sum_x, sum_y, eps;
        
        // get sum x and sum y for each node in map_id_to_article
        sum_x = 0.f;
        sum_y = 0.f;
        it = map_id_to_article.values().iterator();
        while (it.hasNext()) {
            Article node = it.next();
            sum_x += node.x;
            sum_y += node.y;
        }
        
        eps = 0.001f;
        assertTrue(Math.abs(1 - sum_x) < eps);
        assertTrue(Math.abs(1 - sum_y) < eps);
    }
    
    
    public void testCreateMapTitleToArticle () {
        System.out.println("createMapTitleToArticle");

        String[]  titles = {"cat", "dog", "hello"};
        
        Article[] nodes = new Article[titles.length];
        int i = 0;
        for(String t:titles) {
            Article a  = new Article();
            a.page_title = t;
            a.page_id    = i+1;
            nodes[i++] = a;
        }
        
        Map<String, Article> m = Article.createMapTitleToArticleWithoutRedirects(nodes);
        assertEquals(titles.length, m.size());
    }
    
    
    public void testcreateArticleWithCategories  () {
        System.out.println("testCreateArticleWithCategories ");
        Article a;
        int     id;
        String  title;
                
        session.Init(connect, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        title = "Emotion";
        a = Article.createArticleWithCategories(session, title, -1);
        assertEquals(null, a);
        
        id = PageTable.getIDByTitle(connect, title);    // 9460
        a = Article.createArticleWithCategories(session, title, id);
        assertFalse(null == a);
        assertEquals(title, a.page_title);
    }
    
    /** Tests adding of categories to Article.id_categories[] in Russian Wikipedia. */
    public void testcreateArticleWithCategories_ru () {
        System.out.println("testCreateArticleWithCategories_ru ");
        Article a;
        int     id;
        String  title;
                
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        // Робот has two categories: Робототехника | Роботы
        title = "Робот";
        id = PageTable.getIDByTitle(connect_ru, title);
        assertTrue(id > 0);
        
        a = Article.createArticleWithCategories(session, title, id);
        
        assertFalse(null == a);
        assertFalse(null == a.id_categories);
        assertTrue(a.id_categories.length >= 2); // categories: Робототехника | Роботы | Википедия:Избранные статьи ar-wiki
    }
    
    
    public void testContainTitle () {
        System.out.println("testContainTitle");
        String title = "Foo";
        
        Article[] n = new Article[20];
        Article a = new Article();
        a.page_title = title;
        n[2] = a;
        
        boolean b = Node.ContainTitle(n, title);
        assertTrue(b);
    }
    
    public void testContainID () {
        System.out.println("testContainID");
        int id = 7;
        
        Article[] n = new Article[20];
        Article a = new Article();
        a.page_id = id;
        n[2] = a;
        
        boolean b = Node.ContainID(n, id);
        assertTrue(b);
    }
    
    public void testGetTitles() {
        System.out.println("testGetTitles");
        
        Article[] a = new Article[2];
        a[0] = new Article();
        a[1] = new Article();
        a[0].page_title = "Title1";
        a[1].page_title = "Title2";
        String[] compare = {"Title1", "Title2"};
        
        String[] s = Node.getTitles(null);
        assertEquals(null, s);
        
        s = Node.getTitles(a);
        assertEquals(2, s.length);
        assertEquals("Title1", s[0]);
        assertEquals("Title2", s[1]);
    }
    
    public void testGetIdExistedInMap() {
        System.out.println("testGetIdExistedInMap");
        
        Set<String> set_titles = new HashSet<String>();
        Map<String, Article> local_map_title_article = new HashMap<String, Article>();
        int[] result_id;
        
        // null test
        result_id = Article.getIdExistedInMap(set_titles, local_map_title_article);
        assertEquals(0, result_id.length);
        
        // simple test: get 'articles_id' from 'map_title_article' by 'set_titles'
        for(String s:articles_title)
            set_titles.add(s);
        result_id = Article.getIdExistedInMap(set_titles, map_title_article);
        assertEquals(articles_id.length, result_id.length);
        
        // complex test: 'set_titles' has title which is absent in 'map_title_article' 
        set_titles.add("absent_title");
        result_id = Article.getIdExistedInMap(set_titles, map_title_article);
        assertEquals(articles_id.length, result_id.length);
    }
    
    
    public void testfillInterWikiTitle_ru() {
        System.out.println("testfillInterWikiTitle_ru_en");
        
        //                      articles               category
        //                                              "Категория:Числа"
        String[] source_ru =  {"Робот",     "Слово",    "Числа"};
        String[] compare_en = {"En:Robot",  "En:Word",  "En:Category:Numbers"};
        String[] compare_eo = {"Eo:Roboto", "Eo:Vorto", "Eo:Kategorio:Nombroj"};
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        Article[] a = new Article[2];
        a[0] = new Article();
        a[1] = new Article();
        Category c = new Category();
        
        a[0].page_id = PageTable.getIDByTitleNamespace(connect_ru, source_ru[0], PageNamespace.MAIN);
        a[1].page_id = PageTable.getIDByTitleNamespace(connect_ru, source_ru[1], PageNamespace.MAIN);
           c.page_id = PageTable.getIDByTitleNamespace(connect_ru, source_ru[2], PageNamespace.CATEGORY);
        
        a[0].fillInterWikiTitle (session, "En", PageNamespace.MAIN);
        a[1].fillInterWikiTitle (session, "En", PageNamespace.MAIN);
           c.fillInterWikiTitle (session, "En", PageNamespace.MAIN); // Attention! Not NS_CATEGORY, since En:.. - is a simple link, not a category
        assertEquals(compare_en[0], a[0].iwiki_title);
        assertEquals(compare_en[1], a[1].iwiki_title);
        assertEquals(compare_en[2],    c.iwiki_title);
        
        a[0].fillInterWikiTitle (session, "Eo", PageNamespace.MAIN);
        a[1].fillInterWikiTitle (session, "Eo", PageNamespace.MAIN);
           c.fillInterWikiTitle (session, "Eo", PageNamespace.MAIN);
        assertEquals(compare_eo[0], a[0].iwiki_title);
        assertEquals(compare_eo[1], a[1].iwiki_title);
        assertEquals(compare_eo[2],    c.iwiki_title);
    }
}

    
    
    