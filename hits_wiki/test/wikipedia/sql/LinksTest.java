package wikipedia.sql;

import wikipedia.language.Encodings;
import wikipedia.kleinberg.*;
import wikipedia.data.ArticleIdAndTitle;
import wikipedia.util.*;

import junit.framework.*;
import java.util.*;

public class LinksTest extends TestCase {
    
    public Connect  connect, connect_ru;
    Links           links;
    Article[]       source_nodes, t1_tredirect_nodes, redirect_nodes2, z2_array, z_redirect_z2, y_redirect_y2, z_redirect_z2_y_redirect_y2;
    SessionHolder   session;
    int             categories_max_steps;
    
    static String t1,    t_redirect,    t2,    tr1, tr2;
    static int    t1_id, t_redirect_id, t2_id;
    static String s1,    s_redirect,    s2;
    static int    s1_id, s_redirect_id, s2_id;
    
    static String z1,    z_redirect,    z2;
    static int    z1_id, z_redirect_id, z2_id;
    static String y1,    y_redirect,    y2;
    static int    y1_id, y_redirect_id, y2_id;
    
    static String title_from, title_to;
    static int       id_from,    id_to;
    static Article[] a1_to;
        
    private long    t_start, t_end;
    private float   t_work;
    
    public List<String>     ru_local_blacklist;
    private final String[]  black_array_ru = {"Персоналии_по_алфавиту"};
    
    // m_out - local map<title of article, list of titles links_out>
    // m_in  - local map<title of article, list of titles links_in>
    private Map<String,Set<String>> m_out = new HashMap<String,Set<String>>();
    private Map<String,Set<String>> m_in  = new HashMap<String,Set<String>>();
        
    public LinksTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws java.lang.Exception {
        connect = new Connect();
        //connect.Open("localhost", "enwiki?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false", "javawiki", "");
        connect.Open(Connect.WP_HOST,       Connect.WP_DB,    Connect.WP_USER,    Connect.WP_PASS);
        
        connect_ru = new Connect();
        //connect_ru.Open("localhost", "ruwiki?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false", "javawiki", ""); //Java:MySQL ISO8859_1:latin1
        connect_ru.Open(Connect.WP_RU_HOST, Connect.WP_RU_DB, Connect.WP_RU_USER, Connect.WP_RU_PASS);
        
        tr1 = connect.enc.EncodeFromJava("Трансформеры");
        tr2 = connect.enc.EncodeFromJava("Робот_(значения)");
        
        source_nodes = new Article[2];
        source_nodes[0] = new Article();
        source_nodes[1] = new Article();
        
        source_nodes[0].page_title = tr1;
        source_nodes[1].page_title = tr2;
                
        source_nodes[0].page_id = PageTable.getIDByTitleNamespace(connect_ru, tr1, PageNamespace.MAIN);    //18991;
        source_nodes[1].page_id = PageTable.getIDByTitleNamespace(connect_ru, tr2, PageNamespace.MAIN);//22233;
        
        t1          = connect.enc.EncodeFromJava("Джемини");
        t_redirect  = connect.enc.EncodeFromJava("MIT");
        t2          = connect.enc.EncodeFromJava("Массачусетсский_технологический_институт");
        
        t1_id         = PageTable.getIDByTitleNamespace(connect_ru, t1, PageNamespace.MAIN);
        t_redirect_id = PageTable.getIDByTitleNamespace(connect_ru, t_redirect, PageNamespace.MAIN);
        t2_id         = PageTable.getIDByTitleNamespace(connect_ru, t2, PageNamespace.MAIN);
        
        // Джемини -> MIT -> Массачусетсский технологический институт
        t1_tredirect_nodes = new Article[2];
        t1_tredirect_nodes[0] = new Article();
        t1_tredirect_nodes[0].page_title = t1;
        t1_tredirect_nodes[0].page_id = t1_id;
        
        t1_tredirect_nodes[1] = new Article();
        t1_tredirect_nodes[1].page_title = t_redirect;
        t1_tredirect_nodes[1].page_id = t_redirect_id;
        
        s1          = connect.enc.EncodeFromJava("Польская_Википедия");
        s_redirect  = connect.enc.EncodeFromJava("Бот_(программа)");
        s2          = connect.enc.EncodeFromJava("Робот_(программа)");
        
        s1_id         = PageTable.getIDByTitleNamespace(connect_ru, s1, PageNamespace.MAIN);
        s_redirect_id = PageTable.getIDByTitleNamespace(connect_ru, s_redirect, PageNamespace.MAIN);
        s2_id         = PageTable.getIDByTitleNamespace(connect_ru, s2, PageNamespace.MAIN);
        
        // rare words:
        // Линеал z1            -> ЯПФ z_redirect               -> Ярусно-параллельная_форма_графа z2
        // Мультивселенная y1   -> Кот_Шредингера y_redirect    -> Кот_Шрёдингера y2
        z1          = connect.enc.EncodeFromJava("Линеал");
        z_redirect  = connect.enc.EncodeFromJava("ЯПФ");
        z2          = connect.enc.EncodeFromJava("Ярусно-параллельная_форма_графа");
        
        z1_id         = PageTable.getIDByTitleNamespace(connect_ru, z1, PageNamespace.MAIN);
        z_redirect_id = PageTable.getIDByTitleNamespace(connect_ru, z_redirect, PageNamespace.MAIN);
        z2_id         = PageTable.getIDByTitleNamespace(connect_ru, z2, PageNamespace.MAIN);
        
        y1          = connect.enc.EncodeFromJava("Мультивселенная");
        y_redirect  = connect.enc.EncodeFromJava("Кот_Шредингера");
        y2          = connect.enc.EncodeFromJava("Кот_Шрёдингера");
        
        y1_id         = PageTable.getIDByTitleNamespace(connect_ru, y1, PageNamespace.MAIN);
        y_redirect_id = PageTable.getIDByTitleNamespace(connect_ru, y_redirect, PageNamespace.MAIN);
        y2_id         = PageTable.getIDByTitleNamespace(connect_ru, y2, PageNamespace.MAIN);
        
        // z2_array
        z2_array = new Article[1];
        z2_array[0] = new Article();
        z2_array[0].page_id     = z2_id;
        z2_array[0].page_title  = z2;
                
        // z_redirect_z2, y_redirect_y2, z2_y2
        z_redirect_z2 = new Article[2];
        z_redirect_z2[0] = new Article();
        z_redirect_z2[0].page_id     = z_redirect_id;
        z_redirect_z2[0].page_title  = z_redirect;
        z_redirect_z2[1] = new Article();
        z_redirect_z2[1].page_id     = z2_id;
        z_redirect_z2[1].page_title  = z2;
        
        y_redirect_y2 = new Article[2];
        y_redirect_y2[0] = new Article();
        y_redirect_y2[0].page_id     = y_redirect_id;
        y_redirect_y2[0].page_title  = y_redirect;
        y_redirect_y2[1] = new Article();
        y_redirect_y2[1].page_id     = y2_id;
        y_redirect_y2[1].page_title  = y2;
        
        z_redirect_z2_y_redirect_y2 = new Article[4];
        z_redirect_z2_y_redirect_y2[0] = new Article();
        z_redirect_z2_y_redirect_y2[0].page_id     = z_redirect_id;
        z_redirect_z2_y_redirect_y2[0].page_title  = z_redirect;
        
        z_redirect_z2_y_redirect_y2[1] = new Article();
        z_redirect_z2_y_redirect_y2[1].page_id     = z2_id;
        z_redirect_z2_y_redirect_y2[1].page_title  = z2;
        
        z_redirect_z2_y_redirect_y2[2] = new Article();
        z_redirect_z2_y_redirect_y2[2].page_id     = y_redirect_id;
        z_redirect_z2_y_redirect_y2[2].page_title  = y_redirect;
        
        z_redirect_z2_y_redirect_y2[3] = new Article();
        z_redirect_z2_y_redirect_y2[3].page_id     = y2_id;
        z_redirect_z2_y_redirect_y2[3].page_title  = y2;
        
        session = new SessionHolder();
        session.initObjects();
        categories_max_steps = 99;
        
        
        // a1_to = Робот
        // Трансформеры -> Робот
        session.connect = connect_ru;
        title_from = session.connect.enc.EncodeFromJava("Трансформер");     // Transformers (toyline)
        title_to   = session.connect.enc.EncodeFromJava("Робот");            // Robot
        id_from = PageTable.getIDByTitleNamespace(connect_ru, title_from, PageNamespace.MAIN);
        id_to   = PageTable.getIDByTitleNamespace(connect_ru, title_to,   PageNamespace.MAIN);
        
        a1_to = new Article[1];
        a1_to[0] = new Article();
        a1_to[0].page_id   = id_to;
        a1_to[0].page_title = title_to;
        
        
        ru_local_blacklist = new ArrayList<String>();
        for(int i=0; i<black_array_ru.length; i++) {
            ru_local_blacklist.add(session.connect.enc.EncodeFromJava(black_array_ru[i]));
        }
        
    }
    
    protected void tearDown() throws java.lang.Exception {
        connect.Close();
        connect_ru.Close();
    }
    
    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(LinksTest.class);
        
        return suite;
    }

    
    
    public void testGetLinksSQL_ru() {
        System.out.println("testGetLinksSQL_ru");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        String title_from = "Трансформеры"; // Transformers (toyline)
        String title_to   = "Робот";        // Robot
        title_from = session.connect.enc.EncodeFromJava(title_from);
        title_to   = session.connect.enc.EncodeFromJava(title_to);
        
        int id_to = PageTable.getIDByTitleNamespace(connect_ru, title_to, PageNamespace.MAIN);
        
        
        String str_sql_count_size, str_sql;
        
        str_sql_count_size  = "SELECT COUNT(pl_from) AS size FROM pagelinks WHERE pl_title='Gettext' AND pl_namespace = 0";
        str_sql             = "SELECT pl_from FROM pagelinks WHERE pl_title='Gettext' AND pl_namespace = 0";
        int n_limit = 2;
        
        Article[] result = Links.getLinksSQL(session, str_sql_count_size, str_sql, n_limit);
        assertTrue(result.length <= 2);
    }
    
    /** test that redirects of articles in 'map_id_article_exist' is updated
     * in createArticlesByIdAndTitleTo()
     */
    public void testCreateArticlesByIdAndTitle_ru() {
        System.out.println("testCreateArticlesByIdAndTitle_ru");
        
        String x1, x_redirect, x2;
        x1          = "cat";
        x_redirect  = "redirect_to_dog";
        x2          = "dog";
        
        int x1_id, x_redirect_id, x2_id;
        x1_id         =  101;
        x_redirect_id = -102;
        x2_id         =  103;
        
        Article[] article_exist = new Article[1];
        article_exist[0]            = new Article();
        article_exist[0].page_title = x2;
        article_exist[0].page_id    = x2_id;
        Map<Integer, Article> map_id_article_exist = Article.createMapIdToArticleWithoutRedirects (article_exist);
        
        // x1 -> x_redirect -> x2
        ArticleIdAndTitle[] aid_array = new ArticleIdAndTitle[2];
        aid_array[0] = new ArticleIdAndTitle(x_redirect_id, x_redirect);
        aid_array[1] = new ArticleIdAndTitle(x2_id,         x2);
   
        Links.addTitlesToMaps(x1, x_redirect, m_out, m_in);
        Links.addTitlesToMaps(x_redirect, x2, m_out, m_in);
        
        assertEquals(0, map_id_article_exist.get(x2_id).redirect.size());
        
        List<Article> result = Links.createArticlesByIdAndTitle(
            session, aid_array,
            map_id_article_exist,
            -1,
            m_out, m_in);
        
        // result = aid_array[x_redirect, x2] - map_id_article_exist{x2} = x_redirect
        assertEquals(1,          result.size());
        assertEquals(x_redirect, result.get(0).page_title);
        
        // test that redirects of articles in 'map_id_article_exist' is updated
        // i.e. test copy: 
        //assertEquals(1,          map_id_article_exist.get(x2_id).redirect.size());
        //assertEquals(x_redirect, map_id_article_exist.get(x2_id).redirect.get(0).title);
    }
    
    
    public void testGetFromByTitleTo_ArticleIdAndTitle_ru() {
        System.out.println("getFromByTitleTo_ArticleIdAndTitle_ru");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(true);
        session.randomPages(false);
        
        // a1_to = Робот
        // Трансформеры -> Робот
        ArticleIdAndTitle[] to = new ArticleIdAndTitle[1];
        to[0]       = new ArticleIdAndTitle();
        to[0].title = title_to;
        to[0].id    =    id_to;
        
        //int root_set_size = -1;
        int n_limit = 4;
        
        ArticleIdAndTitle[] aid = Links.getFromByTitleTo(session, title_to, PageNamespace.MAIN, n_limit);
        assertTrue( null != aid );
        assertEquals(4,     aid.length);
        
        aid = Links.getFromByTitleTo(session, title_to, PageNamespace.MAIN, -1);
        assertTrue(aid.length > 4);
        assertTrue(ArticleIdAndTitle.getTitles(aid).contains(title_from));
    }
    
    // test Qur'an
    public void testGetFromByTitleTo_apostrophe_en() {
        System.out.println("getFromByTitleTo_apostrophe_en");
        
        session.Init(connect, null, categories_max_steps);
        session.skipTitlesWithSpaces(true);
        session.randomPages(false);
        
        String title = connect.enc.EncodeFromJava("Qur'an");
        int     id   = PageTable.getIDByTitleNamespace(connect, title,   PageNamespace.MAIN);
        
        ArticleIdAndTitle[] to = new ArticleIdAndTitle[1];
        to[0]       = new ArticleIdAndTitle();
        to[0].title = title;
        to[0].id    =    id;
        
        int n_limit = 4;
        
        ArticleIdAndTitle[] aid = Links.getFromByTitleTo(session, title, PageNamespace.MAIN, n_limit);
        assertTrue( null != aid );
        assertEquals(4,     aid.length);
    }
    
    
    public void testGetFromByTitleTo_ArticleIdAndTitle_check_randomness_todo() {
        System.out.println("getFromByTitleTo_ArticleIdAndTitle_check_randomness_todo");
        
        // todo
        // ...
        fail("todo");
    }
    
    /**
     * Test of GetLFromByLTo method, of class wikipedia.Links.
     */
    public void testGetLFromByLTo_ru() {
        System.out.println("getLFromByLTo_ru");
        //  page_id  page_title
        //  10484   Робот
// new Mediawiki 1.5
//  page.page_title === pagelinks.pl_title
//  SELECT page_title FROM page WHERE page_id=10484 AND page_namespace = 0;
//  SELECT pl_from FROM pagelinks WHERE pl_title IN (SELECT page_title FROM page WHERE page_id=10484 AND page_namespace = 0) AND pl_namespace = 0;
// old 1.4
//  SELECT l_from FROM links WHERE l_to=10484;
//  SELECT page_id FROM cur WHERE page_namespace = 0 AND page_id IN (SELECT l_from FROM links WHERE l_to=10484);
        //
        //  OUT: 22233, 18991   6.50 sec
        //  id  Трансформеры    ->  id  Робот
        //  id  Робот_(значения)->  id Робот
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(true);
        session.randomPages(false);
        
        // a1_to = Робот
        // Трансформеры -> Робот
        
        //int root_set_size = -1;
        int inc = -1;           // 4;
        int base_set_size = -1; // 1;
        
        m_out.clear();
        m_in.clear();
        
        Article[] nodes = Links.getLFromByLTo(session, a1_to, inc, base_set_size, m_out, m_in);
        assertTrue(0 <= nodes.length); // 13 35
        assertTrue(Article.ContainTitle(nodes, 
                title_from));
        
        Map<String, Article> m = Article.createMapTitleToArticleWithoutRedirects(nodes);
        for(Article n:nodes) {
            assertTrue(m.containsKey(n.page_title));
        }
        
 }
    
    public void testGetLFromByLToNodes_ru() {
        System.out.println("GetLFromByLToNodes_ru");
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        // SELECT pl_from FROM pagelinks WHERE pl_title IN (SELECT page_title FROM page WHERE page_id=10484 AND page_namespace = 0) AND pl_namespace = 0;
        // new 1.5
        // SELECT page_title FROM page WHERE (page_id=18991 OR page_id=22233) AND page_namespace = 0;
        // out: РўСЂР°РЅСЃС„РѕСЂРјРµСЂС‹, Р РѕР±РѕС‚_(Р·РЅР°С‡РµРЅРёСЏ)
        //
        // SELECT pl_from FROM pagelinks WHERE pl_title IN (SELECT page_title FROM page WHERE (page_id=18991 OR page_id=22233) AND page_namespace = 0) AND pl_namespace = 0;
        // speed up by spliting:
        // SELECT DISTINCT pl_from FROM pagelinks WHERE (pl_title='РўСЂР°РЅСЃС„РѕСЂРјРµСЂС‹' OR pl_title='Р РѕР±РѕС‚_(Р·РЅР°С‡РµРЅРёСЏ)') AND pl_namespace = 0;
        // out: 25 rows in set (0.06 sec)
        //
        // old 1.4
        //  SELECT page_id FROM cur WHERE page_namespace = 0 AND page_id IN (SELECT l_from FROM links WHERE l_to IN (18991, 22233));
        //
        //  OUT: 16482, 10484
        //
        //
        //        title_from:                             title_to:
        //  NNNNN Разряд_(персонаж_мультфильма) ->  18991 Трансформеры
        //  41606 Сайкил                        ->  18991 Трансформеры
        //  NNNNN Робот                         ->  18991 Трансформеры
        //
        //  10484 Робот                         ->  NNNNN Робот_(значения)
        //  
        
        String title_from1 = session.connect.enc.EncodeFromJava("Разряд_(персонаж_мультфильма)");
        String title_from2 = session.connect.enc.EncodeFromJava("Робот");
        String title_from3 = session.connect.enc.EncodeFromJava("Сайкил");
        String title_to1 = session.connect.enc.EncodeFromJava("Трансформеры");          // Transformers (toyline)
        String title_to2 = session.connect.enc.EncodeFromJava("Робот_(значения)");      // Robot (disambiguation)
        //String title_to2 = session.connect.enc.EncodeFromJava("Самолёт");      
        
        int id_from1 = PageTable.getIDByTitleNamespace(connect_ru, title_from1, PageNamespace.MAIN);
        int id_from2 = PageTable.getIDByTitleNamespace(connect_ru, title_from2, PageNamespace.MAIN);
        int id_from3 = PageTable.getIDByTitleNamespace(connect_ru, title_from3, PageNamespace.MAIN);
        
        int id_to1 = PageTable.getIDByTitleNamespace(connect_ru, title_to1, PageNamespace.MAIN);
        int id_to2 = PageTable.getIDByTitleNamespace(connect_ru, title_to2, PageNamespace.MAIN);
        
        Article[] n_to = new Article[2];
        n_to[0] = new Article();
        n_to[0].page_id     = id_to1;
        n_to[0].page_title  = title_to1;
        
        n_to[1] = new Article();
        n_to[1].page_id = id_to2;
        n_to[1].page_title  = title_to2;
        
        m_out.clear();
        m_in.clear();
        
        Article[] nodes = links.getLFromByLTo(session, n_to, -1, -1, m_out, m_in);
        assertTrue(27 <= nodes.length); // 27 31
        
        // check that links to Robot contains three articles with id_from1, 2, and 3
        assertTrue(Article.ContainID(nodes, id_from1));
        assertTrue(Article.ContainID(nodes, id_from2));
        assertTrue(Article.ContainID(nodes, id_from3));
    }
    
    
    /** Checks correct treating of redirect pages for getLFromByLTo by 
     * testing with rare words.
     */
    public void testGetLFromByLToNodes_z_redirect_z2_ru() {
        System.out.println("testGetLFromByLTo_redirects_ru");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        // rare words
        // Линеал z1            -> ЯПФ z_redirect               -> Ярусно-параллельная_форма_графа z2
        // Мультивселенная y1   -> Кот_Шредингера y_redirect    -> Кот_Шрёдингера y2
        
        
        // Source:                 ЯПФ z_redirect               -> Ярусно-параллельная_форма_графа z2
        // Result: Линеал z1    ->                                 Ярусно-параллельная_форма_графа z2
        
        // z_redirect_z2, y_redirect_y2, z_redirect_z2_y_redirect_y2
        m_out.clear(); m_in.clear();
        assertEquals(0, z_redirect_z2[1].redirect.size());       // z_redirect_z2[1].page_title  = z2;
        Article[] nodes = Links.getLFromByLTo(session, z_redirect_z2, -1, -1, m_out, m_in);
        
        assertEquals(1,          z_redirect_z2[1].redirect.size());
        assertEquals(z_redirect, z_redirect_z2[1].redirect.get(0).title);
        
        // checks nodes
        Article a1;
        Map<String, Article> from_nodes = Article.createMapTitleToArticleWithoutRedirects(nodes);
        assertTrue(from_nodes.containsKey(z1));             // +
        assertFalse(from_nodes.containsKey(z_redirect));    // -
        a1 = from_nodes.get(z1);
        assertTrue(a1                != null);
        assertEquals(0, a1.redirect.size());
        
        // checks m_out and m_in
        assertTrue(m_in.containsKey(z2));
        Set<String> s = m_in.get(z2);
        assertTrue(s.contains(z1));
        assertFalse(s.contains(z_redirect));
        
        assertFalse(m_out.containsKey(z_redirect));
        assertFalse(m_in. containsKey(z_redirect));
        
        assertTrue(m_out.containsKey(z1));
        s =        m_out.get(z1);
        assertTrue(s.contains(z2));
        assertFalse(s.contains(z_redirect));
    }
    
    /** Checks correct treating of redirect pages for getLFromByLTo by 
     * testing with rare words.
     */
    public void testGetLFromByLToNodes_z2_ru() {
        System.out.println("testGetLFromByLToNodes_z2_ru");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        // Source:                 ЯПФ z_redirect               -> Ярусно-параллельная_форма_графа z2
        // Result: Линеал z1    ->                                 Ярусно-параллельная_форма_графа z2
        
        // z2_array
        m_out.clear(); m_in.clear();
        assertEquals(0, z2_array[0].redirect.size());     // z2_array[0].page_title  = z2;
        Article[] nodes = Links.getLFromByLTo(session, z2_array, -1, -1, m_out, m_in);
        
        assertEquals(1,          z2_array[0].redirect.size());
        assertEquals(z_redirect, z2_array[0].redirect.get(0).title);
        
        // checks nodes
        Article a1;
        Map<String, Article> from_nodes = Article.createMapTitleToArticleWithoutRedirects(nodes);
        assertTrue(from_nodes.containsKey(z1));             // +
        assertFalse(from_nodes.containsKey(z_redirect));    // -
        a1 = from_nodes.get(z1);
        assertEquals(0, a1.redirect.size());
        
        // checks m_out and m_in
        assertTrue(m_in.containsKey(z2));
        Set<String> s = m_in.get(z2);
        assertTrue(s.contains(z1));
        assertFalse(s.contains(z_redirect));
        
        assertFalse(m_out.containsKey(z_redirect));
        assertFalse(m_in. containsKey(z_redirect));
        
        assertTrue(m_out.containsKey(z1));
        s =        m_out.get(z1);
        assertTrue(s.contains(z2));
        assertFalse(s.contains(z_redirect));
    }
    
    public void testGetLFromByLToNodes_y_redirect_y2_ru() {
        System.out.println("testGetLFromByLTo_redirects_ru");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        // Source:                       Кот_Шредингера y_redirect -> Кот_Шрёдингера y2
        // Result: Мультивселенная y1 ->                              Кот_Шрёдингера y2
        
        // z_redirect_z2, y_redirect_y2, z_redirect_z2_y_redirect_y2
        m_out.clear(); m_in.clear();
        Article[] nodes = Links.getLFromByLTo(session, y_redirect_y2, -1, -1, m_out, m_in);
        
        // checks nodes
        Map<String, Article> from_nodes = Article.createMapTitleToArticleWithoutRedirects(nodes);
        assertTrue(from_nodes.containsKey(y1));             // +
        assertFalse(from_nodes.containsKey(y_redirect));    // -
        Article a1 = from_nodes.get(y1);
        assertTrue(a1                != null);
        
        // y_redirect_y2 has redirect y_redirect
        assertTrue(y_redirect_y2[1].redirect.size() > 0);
        
        // checks m_out and m_in
        assertTrue(m_in.containsKey(y2));
        Set<String> s = m_in.get(y2);
        assertTrue(s.contains(y1));
        assertFalse(s.contains(y_redirect));
        
        assertFalse(m_out.containsKey(y_redirect));
        assertFalse(m_in. containsKey(y_redirect));
        
        assertTrue(m_out.containsKey(y1));
        s =        m_out.get(y1);
        assertTrue(s.contains(y2));
        assertFalse(s.contains(y_redirect));
    }
    public void testGetLFromByLToNodes_z_redirect_z2_y_redirect_y2_ru() {
        System.out.println("testGetLFromByLTo_redirects_ru");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        // rare words
        // Линеал z1            -> ЯПФ z_redirect               -> Ярусно-параллельная_форма_графа z2
        // Мультивселенная y1   -> Кот_Шредингера y_redirect    -> Кот_Шрёдингера y2
        
        // Source:                       ЯПФ z_redirect            -> Ярусно-параллельная_форма_графа z2
        //                               Кот_Шредингера y_redirect -> Кот_Шрёдингера y2
        // Result: Линеал z1          ->                              Ярусно-параллельная_форма_графа z2
        //         Мультивселенная y1 ->                              Кот_Шрёдингера y2
        
        // z_redirect_z2, y_redirect_y2, z_redirect_z2_y_redirect_y2
        m_out.clear(); m_in.clear();
        Article[] nodes = Links.getLFromByLTo(session, z_redirect_z2_y_redirect_y2, -1, -1, m_out, m_in);
        
        // ///////////////////////////
        // test z_redirect_z2
        
        // checks nodes
        Map<String, Article> from_nodes = Article.createMapTitleToArticleWithoutRedirects(nodes);
        assertTrue(from_nodes.containsKey(z1));             // +
        assertFalse(from_nodes.containsKey(z_redirect));    // -
        
        assertEquals(null, from_nodes.get(z2));
        Article a_z2 = z_redirect_z2_y_redirect_y2[1];
        assertTrue(a_z2.redirect.size() > 0);
        
        // checks m_out and m_in
        assertTrue(m_in.containsKey(z2));
        Set<String> s = m_in.get(z2);
        assertTrue(s.contains(z1));
        assertFalse(s.contains(z_redirect));
        
        assertFalse(m_out.containsKey(z_redirect));
        assertFalse(m_in. containsKey(z_redirect));
        
        assertTrue(m_out.containsKey(z1));
        s =        m_out.get(z1);
        assertTrue(s.contains(z2));
        assertFalse(s.contains(z_redirect));
        
        // ///////////////////////////
        // test y_redirect_y2
        // checks nodes
        from_nodes = Article.createMapTitleToArticleWithoutRedirects(nodes);
        assertTrue(from_nodes.containsKey(y1));             // +
        assertFalse(from_nodes.containsKey(y_redirect));    // -
        
        assertEquals(null, from_nodes.get(y2));
        Article a_y2 = z_redirect_z2_y_redirect_y2[3];
        assertTrue(a_y2.redirect.size() > 0);
        
        // checks m_out and m_in
        assertTrue(m_in.containsKey(y2));
        s = m_in.get(y2);
        assertTrue(s.contains(y1));
        assertFalse(s.contains(y_redirect));
        
        assertFalse(m_out.containsKey(y_redirect));
        assertFalse(m_in. containsKey(y_redirect));
        
        assertTrue(m_out.containsKey(y1));
        s =        m_out.get(y1);
        assertTrue(s.contains(y2));
        assertFalse(s.contains(y_redirect));
    }
    
    
    // check filtering by category blacklist:
    // Given:
    // Article "Жуковский,_Николай_Егорович" has category "Персоналии_по_алфавиту"
    // Article "Жуковский,_Николай Егорович" -> Article "Самолёт"
    // To check:
    // filter "Жуковский,_Николай_Егорович" by category "Персоналии_по_алфавиту"
    //
    public void testGetLFromByLToNodes_blacklist_ru() {
        System.out.println("testGetLFromByLToNodes_blacklist_ru check filtering by category blacklist");
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        String title_from = session.connect.enc.EncodeFromJava("Жуковский,_Николай_Егорович");
        String title_to = session.connect.enc.EncodeFromJava("Самолёт");
        int id_from = PageTable.getIDByTitleNamespace(connect_ru, title_from, PageNamespace.MAIN);
        int id_to   = PageTable.getIDByTitleNamespace(connect_ru, title_to,   PageNamespace.MAIN);
        
        Article[] n = new Article[1];
        n[0] = new Article();
        n[0].page_id    = id_to;
        n[0].page_title = title_to;
        
        m_out.clear();
        m_in.clear();
        
        Article[] nodes1 = links.getLFromByLTo(session, n, -1, -1, m_out, m_in);
        assertTrue(Article.ContainID(nodes1, id_from));
        
        m_out.clear();
        m_in.clear();
        
        //int n_limit = 1;
        //session.category_black_list.setMaxSteps(n_limit);
        session.Init(connect, ru_local_blacklist, categories_max_steps);
        Article[] nodes2 = links.getLFromByLTo(session, n, -1, -1, m_out, m_in);
        assertFalse(Article.ContainID(nodes2, id_from));
    }
    
    /** String stupid concatenation: t_work=30.659
     *  String append by StringBuffer: t_work=0.112
     *//*
    public void testGetTitleToByIDFrom_stringConcatenation_speedup() {
        System.out.println("testGetTitleToByIDFrom_stringConcatenation_speedup");
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        int len = 100;
        int[] id_from = new int[len];
        for(int i=0;i<len;i++) {
            id_from[i] = i;
        }
        
        t_start = System.currentTimeMillis();
        String[] r = Links.GetTitleToByIDFrom(session, id_from, PageNamespace.MAIN);
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        System.out.println("t_work="+t_work);
    }*/
    
    
    public void testGetTitleToOneByIDFrom_ru() {
        System.out.println("testGetTitleToOneByIDFrom_ru");
        String result;
        
        // Redirects: 
        // t1       -> t_redirect -> t2 
        // Джемини  -> MIT    -> Массачусетсский_технологический_институт 
        // 62186    -> -52141 -> 52137
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        result = Links.getTitleToOneByIDFrom(session, t_redirect_id);
        assertEquals(result, t2);
        
        //return null, if ... (2) title should be skipped
        session.skipTitlesWithSpaces(true);
        result = Links.getTitleToOneByIDFrom(session, t_redirect_id);
        assertEquals(result, null);
    }
    
    public void testGetTitleToByIDFrom_ru() {
        System.out.println("testGetTitleToByIDFrom_ru");
        //  Трансформеры      ->
        //  Робот_(значения)  ->
        //  -> (page_id)     8110    8647        263         16482                          10484   9856                10578
        //     (page_title)  2005    Анимация    Internet    Разряд_(персонаж_мультфильма)  Робот   Робот_(программа)   Танец
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        String[] title_from={"Трансформеры", "Робот_(значения)"};
        title_from[0] = session.connect.enc.EncodeFromJava(title_from[0]);
        title_from[1] = session.connect.enc.EncodeFromJava(title_from[1]);
        
        int[] id_from = new int[2];
        id_from[0] = PageTable.getIDByTitleNamespace(connect_ru, title_from[0], PageNamespace.MAIN);
        id_from[1] = PageTable.getIDByTitleNamespace(connect_ru, title_from[1], PageNamespace.MAIN);
        
        String[] r = Links.getTitleToByIDFrom(session, id_from, PageNamespace.MAIN);
        assertTrue(27 <= r.length); // 17
        
        int[] id_null=null;
        String[] r_empty = Links.getTitleToByIDFrom(session, id_null, PageNamespace.MAIN);
        assertTrue(0 == r_empty.length);
        
        int[] id_from2={0};
        String[] r2 = Links.getTitleToByIDFrom(session, id_from2, PageNamespace.MAIN);
        assertTrue(0 == r2.length);
    }
    
    /** Checks omitting of articles' titles with spaces (underscores) */
    public void testGetTitleToByIDFrom_ru_skipTitlesWithSpaces() {
        System.out.println("testGetTitleToByIDFrom_ru_skipTitlesWithSpaces");
        //  Трансформеры      ->
        //  Робот_(значения)  ->
        //  -> (page_id)     8110    8647        263         16482                          10484   9856                10578
        //     (page_title)  2005    Анимация    Internet    Разряд_(персонаж_мультфильма)  Робот   Робот_(программа)   Танец
        session.Init(connect_ru, null, categories_max_steps);
        session.randomPages(false);
        
        String[] title_from={"Трансформер", "Робот_(значения)"};
        title_from[0] = session.connect.enc.EncodeFromJava(title_from[0]);
        title_from[1] = session.connect.enc.EncodeFromJava(title_from[1]);
        
        int[] id_from = new int[2];
        id_from[0] = PageTable.getIDByTitleNamespace(connect_ru, title_from[0], PageNamespace.MAIN);
        id_from[1] = PageTable.getIDByTitleNamespace(connect_ru, title_from[1], PageNamespace.MAIN);
        
        session.skipTitlesWithSpaces(false);
        String[] r1_with_spaces = Links.getTitleToByIDFrom(session, id_from, PageNamespace.MAIN);
        
        List<String> l1_with_spaces = new ArrayList<String>(); // list l1
        for(String s:r1_with_spaces) { l1_with_spaces.add(s);}
        
        String sr1 = session.connect.enc.EncodeFromJava("Робот_(программа)");
        assertTrue(l1_with_spaces.contains(sr1));
        //assertTrue(32 <= r.length); // 17
        
        session.skipTitlesWithSpaces(true);
        String[] r2_without_spaces = Links.getTitleToByIDFrom(session, id_from, PageNamespace.MAIN);
        assertTrue(r1_with_spaces.length > r2_without_spaces.length);
        
        List<String> l2_without_spaces = new ArrayList<String>();
        for(String s:r2_without_spaces) { l2_without_spaces.add(s);}
        assertFalse(l2_without_spaces.contains(sr1));
    }
    
    //Article[] base_nodes2 = links.GetLToByLFrom(connect, root_nodes);
    public void testGetLToByLFrom_ru() {
        System.out.println("testGetLToByLFrom_ru");
        // new 1.5
        // SELECT pl_title FROM pagelinks WHERE pl_from IN (18991, 22233) AND pl_namespace = 0;
        //        out: 17 rows in set (0.24 sec), e.g.: Р‘Р°СЂС…Р°РЅ_(Soundwave), РљРѕРјРёРєСЃ
        // foreach pl_title:
        //      PageTable p.GetIDByTitle(pl_title);
        //
        // old 1.4
        //  SELECT page_id FROM cur WHERE page_namespace = 0 AND page_id IN (SELECT l_to FROM links WHERE l_from IN (18991, 22233));
        //  OUT: 16482, 10484
        //  18991 Трансформеры      ->
        //  22233 Робот_(значения)  ->
        //  -> (page_id)     8110    8647        263         16482                          10484   9856                10578
        //     (page_title)  2005    Анимация    Internet    Разряд_(персонаж_мультфильма)  Робот   Робот_(программа)   Танец
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        m_out.clear(); m_in.clear();
        Article[] nodes = links.getLToByLFrom(session, source_nodes, -1, m_out, m_in);
        assertTrue(18 <= nodes.length); // 11 18 21
        
        List<Integer>   id    = new ArrayList<Integer>(nodes.length);
        List<String>    title = new ArrayList<String> (nodes.length);
        for(Article a:nodes) {
            id.   add(a.page_id);
            title.add(a.page_title);
        }
        assertFalse(id.contains(0));

        // s1                 -> s_redirect      -> s2 
        // Польская_Википедия -> Бот_(программа) -> Робот_(программа)
        assertTrue(id.contains(s2_id));             // + targed redirect
        assertTrue(title.contains(s2));
        
        assertFalse(id.contains(s_redirect_id));    // - redirect itself
        assertFalse(title.contains(s_redirect));
        
        
        Article[] nodes2 = links.getLToByLFrom(session, source_nodes, 3, m_out, m_in);
        assertTrue(3 >= nodes2.length);
        
        Article[] nodes3 = links.getLToByLFrom(session, source_nodes, 0, m_out, m_in);
        assertTrue(1 == nodes3.length);
    }
    
    /** Checks correct treating of redirect pages, e.g.:
     * Redirect Джемини -> MIT -> Массачусетсский_технологический_институт
     * Redirect Польская_Википедия -> Бот_(программа) -> Робот_(программа)
     */
    public void testGetLToByLFrom_ru_redirects() {
        System.out.println("testGetLToByLFrom_ru_redirects");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        m_out.clear(); m_in.clear();
        Article[] nodes = links.getLToByLFrom(session, t1_tredirect_nodes, -1, m_out, m_in);
        assertTrue(0 <= nodes.length); // 11 25
        
        // Redirects: t1 -> t_redirect -> t2
        // Джемини       -> MIT        -> Массачусетсский_технологический_институт
                
        // checks nodes
        Article a2;
        Map<String, Article> t1_to_nodes = Article.createMapTitleToArticleWithoutRedirects(nodes);
        assertTrue(t1_to_nodes.containsKey(t2)); // +
        assertFalse(t1_to_nodes.containsKey(t_redirect)); // -
        a2 = t1_to_nodes.get(t2);
        assertTrue(a2.redirect.size() > 0);
        
        // checks m_out and m_in
        assertTrue(m_out.containsKey(t1));
        Set<String> out1 = m_out.get(t1);
        assertTrue(out1.contains(t2));
        assertFalse(out1.contains(t_redirect));
        
        assertFalse(m_out.containsKey(t_redirect));
        assertFalse(m_in. containsKey(t_redirect));
        
        assertTrue(m_in.containsKey(t2));
        Set<String> in2 =  m_in.get(t2);
        assertTrue(in2.contains(t1));
        assertFalse(in2.contains(t_redirect));
        
        // Redirect "Бот_(программа)" -> "Робот_(программа)"
        // todo
    }

    /** Checks correct resolving of redirect pages, with skipSpaces enabled.
     */
    public void testGetLToByLFrom_ru_redirects__with_skipSpaces() {
        System.out.println("testGetLToByLFrom_ru_redirects__with_skipSpaces");
        
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(true);
        session.randomPages(false);
        
        m_out.clear(); m_in.clear();
        Article[] nodes = links.getLToByLFrom(session, t1_tredirect_nodes, -1, m_out, m_in);
        assertTrue(0 <= nodes.length); // 11 25
        
        // Redirects: t1 -> t_redirect -> t2
        // Джемини       -> MIT        -> Массачусетсский_технологический_институт
                
        // checks nodes
        Map<String, Article> t1_to_nodes = Article.createMapTitleToArticleWithoutRedirects(nodes);
        assertFalse(t1_to_nodes.containsKey(t2));           // -
        assertFalse(t1_to_nodes.containsKey(t_redirect));   // -
        
        assertTrue(session.removed_articles.hasTitle(t_redirect));
        assertTrue(session.removed_articles.hasTitle(t2));
        assertTrue(session.removed_articles.hasId(t_redirect_id));
        assertTrue(session.removed_articles.hasId(t2_id));
        
        // checks m_out and m_in
        assertTrue(m_out.containsKey(t1));
        Set<String> out1 = m_out.get(t1);
        assertFalse(out1.contains(t2));
        assertFalse(out1.contains(t_redirect));
        
        assertFalse(m_out.containsKey(t_redirect));
        assertFalse(m_in. containsKey(t_redirect));
        
        assertFalse(m_in.containsKey(t2));
    }

    
    /** Checks correct treating of redirect pages, when source node is redirect
     * itself:
     * Redirect MIT -> Массачусетсский технологический институт
     * Redirect "Бот_(программа)" -> "Робот_(программа)"
     */
    public void testGetLToByLFrom_ru_redirects__source_node_todo() {
        System.out.println("testGetLToByLFrom_ru_redirects__source_node_todo");
        fail("todo");
    }

    /** Checks that redirect pages (linked to pages with categories in blacklist) 
     * are in blacklist too, e.g.:
     * 1965 -> 1965_год, when blacklist has category: Века
     */
    public void testGetLToByLFrom_redirects_with_BlackList_ru_todo() {
        System.out.println("testGetLToByLFrom_redirects_with_BlackList_ru_todo");
        fail("todo");
    }

    
    /** Check the Remark of GetLToByLFrom(): "Returns only articles which id are 
     * absent in the blacklist."
     */
    public void testGetLToByLFrom_with_BlackList_ru() {
        System.out.println("testGetLToByLFrom_with_BlackList_ru");
        List<String>   category_black_list_ru = new ArrayList<String>();
        
        // Плектр (article) -> Домра (article) -> "Щипковые инструменты" (category)
        // "Домра" has category "Щипковые_музыкальные_инструменты"
        // 1. "Щипковые_музыкальные_инструменты" is not in the blacklist
        category_black_list_ru.add("Foo");
        
        session.Init(connect_ru, category_black_list_ru, categories_max_steps);
        session.randomPages(false);
        Encodings e = session.connect.enc;
        
        String title    = e.EncodeFromJava("Плектр");    // Plectrum, category:Щипковые_музыкальные_инструменты
        String title2   = e.EncodeFromJava("Домра");    // , category:Щипковые_музыкальные_инструменты
        String category_title = e.EncodeFromJava("Щипковые_музыкальные_инструменты");
        
        Article[] source_nodes = new Article[1];
        source_nodes[0] = new Article();
        source_nodes[0].page_id     = PageTable.getIDByTitle(connect_ru, title);
        source_nodes[0].page_title  = title;
        
        m_out.clear(); m_in.clear();
        Article[] nodes = links.getLToByLFrom(session, source_nodes, -1, m_out, m_in);
        if(0 <= nodes.length) {
            List<String> titles = new ArrayList<String>(nodes.length);
            for(Article a:nodes) {
                titles.add(a.page_title);
            }
            assertTrue(titles.contains(title2));
        }
                
        // 2. "Щипковые_музыкальные_инструменты" is in the blacklist
        category_black_list_ru.add(category_title);
        //category_black_list_ru.add(latin1_music_instruments);
        session.Init(connect_ru, category_black_list_ru, categories_max_steps);
        session.randomPages(false);
        
        m_out.clear(); m_in.clear();
        Article[] nodes2 = links.getLToByLFrom(session, source_nodes, -1, m_out, m_in);
        List<String> titles = new ArrayList<String>(nodes2.length);
        for(Article a:nodes2) {
            titles.add(a.page_title);
        }
        assertTrue(0 == titles.size() || !titles.contains(title2));
    }
    
    /** Checks that "The article is omitted if ... (3) article id is in pl_from[].
     */
    public void testGetLToByLFrom_omit_pl_from_ru() {
        System.out.println("testGetLToByLFrom_omit_pl_from_ru");
        List<String>   category_black_list_ru = new ArrayList<String>();
        
        session.Init(connect_ru, null, categories_max_steps);
        session.randomPages(false);
        Encodings e = session.connect.enc;
        
        String title    = e.EncodeFromJava("Робот");
        String title2   = e.EncodeFromJava("Андроид");
                
        int root_set_size = 200;
        int increment       = 1;
        Article[] nodes;
        {
            Article[] a1 = new Article[2];
            a1[0] = new Article();
            a1[0].page_id    = PageTable.getIDByTitle(session.connect, title);
            a1[0].page_title = title;
            
            a1[1] = new Article();
            a1[1].page_id    = PageTable.getIDByTitle(session.connect, title2);
            a1[1].page_title = title2;
            
            Article[] root_nodes = a1;            
            
            // 2.2
            m_out.clear(); m_in.clear();
            Article[] base_nodes2 = Links.getLToByLFrom(session, root_nodes, -1, m_out, m_in);
            nodes = base_nodes2;
        }
        
        assertTrue(0 <= nodes.length);
        List<String> titles = new ArrayList<String>(nodes.length);
        for(Article a:nodes) {
            titles.add(a.page_title);
        }
        
        assertFalse(titles.contains(title));
        assertFalse(titles.contains(title2));
    }

    /** Tests the case, when m_in or m_out contains articles which are absent 
     * in 'map_title_article'.
     */
    public void testGetAllLinksFromNodes_ru() {
        System.out.println("testGetAllLinksFromNodes_ru");
        
        int categories_max_steps = 99;
        session.Init(connect_ru, null, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        Article t2_node = new Article();
        t2_node.page_title = t2;
        t2_node.page_id = t2_id;
        
        // Redirects: t1 -> t_redirect -> t2
        // Джемини       -> MIT        -> Массачусетсский_технологический_институт
        Map<String, Article> map_t1_t2_to_nodes = Article.createMapTitleToArticleWithoutRedirects(t1_tredirect_nodes);
                             map_t1_t2_to_nodes.put(t2, t2_node);
        
        Links.addTitlesToMaps(t1, t_redirect, m_out, m_in); // oooops, it is absent in map_t1_t2_to_nodes
        Links.addTitlesToMaps(t1, t2,         m_out, m_in); // yes, it is presented in map_t1_t2_to_nodes
        
        Links.getAllLinksFromNodes(session, map_t1_t2_to_nodes, new Article[0], m_out, m_in);
        
        // only t1 -> t2 should be treated
        // only t1 -> t_redirect should be skipped
        Article t1_node = t1_tredirect_nodes[0];
        assertTrue  (null != t1_node.links_out);
        assertEquals(1,     t1_node.links_out.length);
        assertEquals(null,  t1_node.links_in);
        
        assertEquals(null,  t2_node.links_out);
        assertEquals(1,     t2_node.links_in.length);
        
        // t_redirect has no links_out and links_in
        assertEquals(null,   t1_tredirect_nodes[1].links_out);
        assertEquals(null,   t1_tredirect_nodes[1].links_in);
    }

    /*
    // Let's count the number of links in the graph: Трансформеры -> Робот.
    // =1.
    public void testGetAllLinks_ru() {
        System.out.println("testGetAllLinks_ru");
        
        int categories_max_steps = 99;
        session.Init(connect_ru, null, categories_max_steps);
        session.randomPages(false);
        
        //int increment = 1;
        //Article[] nodes = links.GetLFromByLTo(session, source_nodes, increment);
        //Article[] nodes = links.GetLToByLFrom(session, source_nodes);
        
        String s;
        s = session.connect.enc.EncodeFromJava("Трансформеры");
        source_nodes[0].page_title = s;
        source_nodes[0].page_id = PageTable.getIDByTitleNamespace(connect_ru, s, PageNamespace.MAIN);
        
        s = session.connect.enc.EncodeFromJava("Робот");
        source_nodes[1].page_title = s;
        source_nodes[1].page_id = PageTable.getIDByTitleNamespace(connect_ru, s, PageNamespace.MAIN);
        
        DCEL dcel = new DCEL();
        Article node = new Article();
        
        Map<Integer, Article> map_id_article    = node.createMapIdToArticleWithoutRedirects   (source_nodes);
        Map<String,  Article> map_title_article = node.createMapTitleToArticleWithoutRedirects(source_nodes);
        
        int links_begin  = dcel.CountLinksIn(map_id_article);
        Links.getAllLinks(session, map_title_article);
        
        // Трансформеры -> Робот
        assertTrue(1 <= source_nodes[0].links_out.length); // Трансформеры -> 
        assertTrue(1 <= source_nodes[1].links_in. length); // -> Робот
                
        int links_end  = DCEL.CountLinksIn(map_id_article);
        assertTrue(links_end >= 1 + links_begin);
    }*/
    
    
    /** Example Russian 'domra' (id=749):
     *  SELECT COUNT(*) FROM links WHERE l_from=749;
     * Out 10.
    public void testCountLinksFrom() {
        System.out.println("testCountLinksFrom");
        int article_id = 749;
        int i = links.CountLinksFrom(connect_ru, article_id);
        assertTrue(10 <= i);
    }*/
    
    /** Example Russian 'domra' (id=749):
        *  SELECT COUNT(*) FROM links WHERE l_from=749;    6
        *  SELECT COUNT(*) FROM links WHERE l_to=749;      3
    public void testCountLinks() {
        int article_id = 749;
        int count_l_from = links.CountLinks(connect_ru, "l_from", article_id);
        int count_l_to   = links.CountLinks(connect_ru, "l_to", article_id);
        assertTrue(6 <= count_l_from);
        assertTrue(3 <= count_l_to);
        assertTrue(1 == 0);
    }*/
    
}

/** Gets article's frequency in the table pagelinks: count(pl_from).
     * Example Russian 'domra' (id=749):
     * SELECT COUNT(*) as size FROM pagelinks WHERE pl_from=749;
     */
    /*public static int CountLinksFrom(Connect connect, int article_id) {
        Statement s = null;
        ResultSet rs= null;
        int size = 0;
        String str_sql = null;
        try {
            s = connect.conn.createStatement();
            str_sql = "SELECT COUNT(pl_from) AS size FROM pagelinks WHERE pl_from=" + article_id;
            s.executeQuery(str_sql);
            rs = s.getResultSet();
            if (rs.next()) {
                size = rs.getInt("size");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (Links.java CountLinks()): sql='" + str_sql + "' " + ex.getMessage());
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
        return size;
    }*/
    
    /* Calculates and set up number of links for each node in hashmap.
    public static void CountLinks(Connect connect, HashMap<Integer, Article> nodes) {
        Iterator<Integer> it = nodes.keySet().iterator();
        int         size, i;
        
        while (it.hasNext()) {
            int id = it.next();                // article's id (page_id)
            Article n = nodes.get(id);
            n.count_l_from = n.links_out.length;
            n.count_l_to   = n.links_in .length;
            //n.count_l_from  = CountLinksFrom(connect, id);
            //n.count_l_to    = CountLinksTo  (connect, id);
        }
    }*/
    
    // Return links list from the table links
    /*public static int[] GetIntFromLinks(Connect connect, String field, String str_where_sql) {
        Statement s = null;
        ResultSet rs= null;
        int[]   result = null;
        int     size, i = 0;
        String str_sql = null;
        try {
            s = connect.conn.createStatement();
            str_sql = "SELECT COUNT(" + field + ") AS size FROM links " + str_where_sql;
            s.executeQuery(str_sql);
            rs = s.getResultSet();
            if (rs.next()) {
                size = rs.getInt("size");
                if (0 < size) {
                    result = new int[size];
                    
                    s.executeQuery("SELECT " + field + " FROM links " + str_where_sql);
                    rs = s.getResultSet();
                    
                    while (rs.next()) {
                        result[i++] = rs.getInt(field);
                    }
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (Links.java GetIntFromLinks): sql='" + str_sql + "' " + ex.getMessage());
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
        return result;
    }*/

    /**
     * Get links which refers to the article l_to.
     * (Get only articles links, i.e. page_namespace = 0)
     * SQL: SELECT page_id, page_title FROM page WHERE page_namespace=0 AND page_id IN (SELECT l_from FROM links WHERE l_to=N)
     * Number of return links limited to n_limit
     * ? Are unique value are returned?
     */
    /*public static Article[] GetLFromByLToIN(SessionHolder session, int l_to, int n_limit) {
        String      str_in, str_from;
        String      str_sql_count_size, str_sql;
     
            // 1. Calculate number of links
            //  too complex & slow request:
            //    "SELECT COUNT(page_id) AS size FROM page " +
            //                "WHERE page_namespace=0 AND " +
            //                             "page_id IN (SELECT l_from FROM links WHERE l_to="+l_to+")");
            // 1.a
            // Prepare SQL IN(...)
        int[] i_links_all = GetIntFromLinks(session.connect, "l_from", "WHERE l_to="+l_to);
        if (null == i_links_all)
            return null;
        int[] i_links = session.category_black_list.DeleteUsingBlackList (i_links_all, -1);
     
        String str_links = StringUtil.join(",", i_links);
     
        str_in = " IN(" + str_links
                   + ") ";
     
        // 1.b
        str_from = "FROM page WHERE page_namespace=0 AND page_id" + str_in;
     
        str_sql_count_size  = "SELECT COUNT(page_id) AS size "   + str_from;
        str_sql             = "SELECT page_id, page_title "       + str_from;
        return GetLinksSQL(session, str_sql_count_size, str_sql, n_limit);
    }*/

    /**
     *  Get articles (page_id and page_title) which refer to some of articles.
     *  Remark1: Get only articles links, i.e. page_namespace = 0
     *  TODO Remark2: select and return nodes which are not the source nodes l_to[]
     *
     *  SELECT l_from FROM links WHERE l_to IN (page_id of Rp) UNIQUE LIMIT t
     *  Ex.: Article[] base_nodes = links.GetLFromByLTo(connect, l_to);
     *  ? Are unique value are returned?
     */
    /*public static Article[] GetLFromByLTo(SessionHolder session, Article[] l_to) {
        Article[]      l_from = null;
        String      str_in, str_sub_in;
        String      str_from, str_sql_count_size, str_sql;
     
        // Prepare SQL IN(...) via l_to[].page_id
        str_sub_in = "l_to IN (";
        for (int i=0; i<l_to.length-1; i++) {
            str_sub_in += l_to[i].page_id + ",";
        }
        str_sub_in += l_to[ l_to.length-1 ].page_id; // skip last comma
        str_sub_in += ")";
     
        // 1. Calculate number of links
        //s.executeQuery ("SELECT COUNT(page_id) AS size FROM page " +
        //                  "WHERE page_namespace=0 AND " +
        //                        "page_id IN (SELECT l_from FROM links WHERE "+str_sub_in);
        int[] i_links_all = GetIntFromLinks(session.connect, "l_from", "WHERE "+str_sub_in);
        if (null == i_links_all)
            return null;
        int[] i_links = session.category_black_list.DeleteUsingBlackList (i_links_all, -1);
     
        String str_links = StringUtil.join(",", i_links);
        str_in = " IN(" + str_links
                   + ") ";
        str_from = "FROM page WHERE page_namespace=0 AND page_id" + str_in;
     
        str_sql_count_size  = "SELECT COUNT(page_id) AS size "   + str_from;
        str_sql             = "SELECT page_id, page_title "       + str_from;
        return GetLinksSQL(session, str_sql_count_size, str_sql, -1);
    }*/
    

    // Article[] base_nodes = links.GetLToByLFrom(connect, root_nodes);
    /**
     * Gets articles (page_id and page_title) which refer to some of articles.
     * Remark1: Gets only articles (not categories), i.e. page_namespace = 0
     * TODO Remark2: select and return nodes which are not the source nodes l_from[]
     *
     * @param n_limit   max number of returned articles, negative value means no limit
     * Todo: select first n links in article (not first n links in table)
     *
     * ? Are unique value are returned?
     */
    /*
    public static Article[] GetLToByLFrom(SessionHolder session, int l_from, int n_limit) {
        String      str_from;
        String      str_sql_count_size, str_sql;
        Article[]   result = null;
        Article     node = new Article();
        
        // 1. Calculate number of links
        //  too complex & slow request:
        //     "SELECT COUNT(page_id) AS size FROM page " +
        //                 "WHERE page_namespace=0 AND " +
        //                       "page_id IN (SELECT l_to FROM links WHERE l_from="+l_from+")");
        
        // Execute subrequest SQL IN(...)
        int[] i_links_all = GetIntFromLinks(session.connect, "l_to", "WHERE l_from="+l_from);
        if (null == i_links_all)
            return null;
        int[] i_links = session.category_black_list.DeleteUsingBlackList(i_links_all, -1);
        
        for(int i=0; i<i_links.length; i++) {
            str_from = "FROM page WHERE page_namespace=0 AND page_id=" + i_links[i];
            
            str_sql_count_size  = "SELECT COUNT(page_id) AS size "   + str_from;
            str_sql             = "SELECT page_id, page_title "       + str_from;
            
            Article[] add = GetLinksSQL(session, str_sql_count_size, str_sql, -1);
            result = node.JoinUnique(result, add);
            
            if(n_limit>=0 && result.length>= n_limit) {
                break;
            }
        }
        return result;
    }
    */
    /*
    public static Article[] GetLToByLFrom(SessionHolder session, Article[] l_from) {
        int         i;
        Article     node = new Article();
        Article[]   result_nodes = null;
        
        for (i=0; i<l_from.length; i++) {
            Article[] add = GetLToByLFrom(session, l_from[i].page_id, -1);
            result_nodes = node.JoinUnique(result_nodes, add);
        }
        return result_nodes;
    }*/    


    
    /*public static void GetTitleToByIDFromQuery(ResultSet rs, Statement s,StringBuffer sb) {
        try {
            s.executeQuery(sb.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (PageTable.java GetTitleByID()): " + ex.getMessage());
        }
    }*/