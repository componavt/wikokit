/*
 * CategorySetTest.java
 * JUnit based test
 *
 * Created on 24 May 2005
 */

package wikipedia.kleinberg;

import wikipedia.language.Encodings;
import wikipedia.sql.*;
import wikipedia.util.*;
import wikipedia.clustering.*;

import junit.framework.*;
import java.util.*;


public class CategorySetTest extends TestCase {

    Article                 node;
    Authorities             auth;
    //HashMap<Integer, Article>  nodes;
    Connect                 connect, connect_ru;
    DumpToGraphViz          dump;
    SessionHolder           session;

    Map<Integer, Article>   articles;
    Map<Integer, Category>  categories;
    //List<ClusterCategory>   clusters;
    //List<Edge>              edges;
    ClusterCategory         c_all, c_religious, c_science, c_art;
    
    public CategorySetTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect = new Connect();
        connect.Open(Connect.WP_HOST, Connect.WP_DB, Connect.WP_USER, Connect.WP_PASS);
        
        connect_ru = new Connect();
        connect_ru.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,Connect.WP_RU_USER,Connect.WP_RU_PASS);
        
        auth = new Authorities();
        dump = new DumpToGraphViz();
        
        dump.file_dot.setFileInHomeDir("graphviz", "bat_ruwiki.dot", "Cp866",true);
        dump.file_bat.setFileInHomeDir("graphviz", "bat_ruwiki.bat", "Cp866",true);
        dump.file_sh.setFileInHomeDir("graphviz", "bat_ruwiki.sh", "Cp1251", true);
        
        node = new Article();
        /*
        nodes = new HashMap<Integer, Article>();
        Article[] source_nodes = new Article[4];
        source_nodes[0] = new Article();
        source_nodes[1] = new Article();
        source_nodes[2] = new Article();
        source_nodes[3] = new Article();
        String[]    titles = {"Настольная игра", "Го (игра)", "Шашки", "Шахматы"};
        float[]       xx = {12.f, 2.f, 7.f, 7.f};
        //
        // Article          -> Categories
        // "Настольная игра"-> Категория: Настольные игры
        // "Го (игра)"      -> Категории: Википедия:Избранные статьи | Го | Слова японского происхождения
        // "Шашки"          -> Категории: Незавершённые статьи | Шашки
        // "Шахматы"        -> Категория: Шахматы
        
        for(int i=0; i<titles.length; i++) {
            source_nodes[i].page_title = titles[i];
            source_nodes[i].page_id = PageTable.getIDByTitle(connect_ru, titles[i]);   // 18991 10484 3321 1121
            source_nodes[i].x = xx[i];
        }
        
        // put source_nodes to nodes
        for(int i=0; i<source_nodes.length; i++) {
            nodes.put(source_nodes[i].page_id, source_nodes[i]);
        }*/
        
        session = new SessionHolder();
        session.initObjects();
        session.dump = dump;
        
        CreateCategoryArticleGraph c = new CreateCategoryArticleGraph ();
        articles    = c.articles;
        categories  = c.categories;
    }

    protected void tearDown() throws Exception {
        connect.Close();
        connect_ru.Close();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CategorySetTest.class);
        
        return suite;
    }

    /** Test filling of category.id_articles[]. See pix at CreateCategoryArticleGraph.java */
    public void testFillLinksFromCategoryToArticles() {
        System.out.println("testFillLinksFromCategoryToArticles");
        CategorySet.fillLinksFromCategoryToArticles(articles, categories);
        
        // category 'all' (see CreateCategoryArticleGraph)
        assertTrue(1 == categories.get(1).id_articles.length);
        assertTrue(11 == categories.get(1).id_articles[0]);
        
        // category 'science'
        assertTrue(3 == categories.get(3).id_articles.length);
        Set<Integer> s = new HashSet<Integer>();
        s.add(11);  s.add(12);  s.add(13);  // these are science_articles ID
        assertTrue(s.contains(categories.get(3).id_articles[0]));
        assertTrue(s.contains(categories.get(3).id_articles[1]));
        assertTrue(s.contains(categories.get(3).id_articles[2]));
    }

    // Simple check that clustering algorithm works properly.
    // See first graph in CreateCategoryArticleGraph as source graph
    public void testGetCategoryClusters () {
        System.out.println("testGetCategoryClusters ");
        int max_cluster_weight;
        session.source_page_title = "4categories";

        max_cluster_weight = 7;
        List<ClusterCategory> clusters1 = CategorySet.getCategoryClusters (categories, articles, max_cluster_weight);
        //CategorySet.dumpClusterCategoryArticle(session, articles, clusters1, "clusters_3_max_cluster_weight_7");
        assertTrue(3 == clusters1.size());
        
        max_cluster_weight = 11;
        List<ClusterCategory> clusters2 = CategorySet.getCategoryClusters (categories, articles, max_cluster_weight);
        //CategorySet.dumpClusterCategoryArticle(session, articles, clusters2, "clusters_2_max_cluster_weight_11");
        assertTrue(2 == clusters2.size());
        
        max_cluster_weight = 12;
        List<ClusterCategory> clusters3 = CategorySet.getCategoryClusters (categories, articles, max_cluster_weight);
        //CategorySet.dumpClusterCategoryArticle(session, articles, clusters3, "clusters_1_max_cluster_weight_12");
        assertTrue(1 == clusters3.size());
    }
    
    // see second graph in CreateCategoryArticleGraph as source graph (by init2())
    public void testGetCategoryClusters2 () {
        System.out.println("testGetCategoryClusters2");
        int max_cluster_weight;
        session.source_page_title = "6categories";
        
        CreateCategoryArticleGraph c = new CreateCategoryArticleGraph ();
        c.init2();
        articles    = c.articles;
        categories  = c.categories;

        max_cluster_weight = 2;
        List<ClusterCategory> clusters0 = CategorySet.getCategoryClusters (categories, articles, max_cluster_weight);
        //CategorySet.dumpClusterCategoryArticle(session, articles, clusters0, "clusters_5_max_cluster_weight_2");
        assertTrue(5 == clusters0.size());

        max_cluster_weight = 4;
        List<ClusterCategory> clusters1 = CategorySet.getCategoryClusters (categories, articles, max_cluster_weight);
        //CategorySet.dumpClusterCategoryArticle(session, articles, clusters1, "clusters_3_max_cluster_weight_4");
        assertTrue(3 == clusters1.size() || 4 == clusters1.size());
        
        max_cluster_weight = 6;
        List<ClusterCategory> clusters2 = CategorySet.getCategoryClusters (categories, articles, max_cluster_weight);
        //CategorySet.dumpClusterCategoryArticle(session, articles, clusters2, "clusters_2_max_cluster_weight_6");
        assertTrue(2 == clusters2.size() || 3 == clusters2.size());
        
        max_cluster_weight = 10;
        List<ClusterCategory> clusters4 = CategorySet.getCategoryClusters (categories, articles, max_cluster_weight);
        //CategorySet.dumpClusterCategoryArticle(session, articles, clusters4, "clusters_1_max_cluster_weight_10");
        assertTrue(1 == clusters4.size());
    }
    
    
            
    /**
     * Test of Create method, of class wikipedia.kleinberg.CategorySet.
     */
    public void testCreate_ru() {
        System.out.println("testCreate_ru");
        int     root_set_size, increment;
        int     n_synonyms;    //, iter;
        float   eps_error;              // error to stop the iteration
        String  article, directory;
        // root_set_size = 100;    increment = 30; // science: time 5 min
        // root_set_size = 150;    increment = 50; // 6 min time 
        // root_set_size = 200;    increment = 50; // Kleinberg default values; Science:11 min (? 25 min + failed)
        root_set_size = 1;   increment = 1;   // Simple test values
        //root_set_size= 10; increment = 50;
        int categories_max_steps = 99;
        Encodings e = connect_ru.enc;
        
        article = e.EncodeFromJava("Шахматы");   // Контрабас, Плазма, Робот Плазма
        session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        //directory = "data/synonyms_ru/";
        
        /*
        article = "Science";    //  4 min, if root_set_size = 10; increment = 50;
                                // 39 minutes or 2 366,359 sec  vertices:12407  edges:410156 if root_set_size = 200; increment = 50;
        session.Init(connect, session.category_black_list.en, categories_max_steps);
        directory = "data/synonyms_en/";
        */
        //session.dump = null;
        
        dump.file.setFileInHomeDir("graphviz", article + ".txt", "Cp1251",true);
        
        /*dump.file.SetFilename(Encodings.UTF8ToCp1251(article) + ".txt1");
        dump.file.Open(true, "Cp1251");
        dump.file.SetFilename(Encodings.FromTo(article,"UTF8","ISO8859_1") + ".txt2");
        dump.file.Open(true, "Cp1251");
        dump.file.SetFilename(Encodings.FromTo(article,"ISO8859_1","UTF8") + ".txt3"); // error
        dump.file.Open(true, "Cp1251");
        dump.file.SetFilename(Encodings.FromTo(article,"Cp1251","UTF8") + ".txt4"); // error
        dump.file.Open(true, "Cp1251");
        dump.file.SetFilename(Encodings.FromTo(article,"UTF8","Cp1251") + ".txt5"); // error
        dump.file.Open(true, "Cp1251");
        dump.file.SetFilename(Encodings.FromTo(article,"Cp1251","ISO8859_1") + ".txt6"); // error
        dump.file.Open(true, "Cp1251");
        dump.file.SetFilename(Encodings.FromTo(article,"ISO8859_1","Cp1251") + ".txt7"); // error
        dump.file.Open(true, "Cp1251");*/
        
        List<String> rated_synonyms = new ArrayList<String>();
        Map<Integer, Article>  base_nodes = LinksBaseSet.CreateBaseSet(article, rated_synonyms, 
                session, root_set_size, increment);
        
        Article s = base_nodes.get(session.source_article_id);
        assertTrue(null != s);
        
        CategorySet.prepareCategories(session, base_nodes);
        
        int max_cluster_weight = 20;
        List<ClusterCategory> clusters = CategorySet.getCategoryClusters (session.category_nodes, base_nodes, max_cluster_weight);
        CategorySet.dumpClusterCategoryArticle(session, base_nodes, clusters, "02_clusters_max_weight_"+max_cluster_weight);
        CategorySet.dumpClusterCategorywithListArticles(session, base_nodes, clusters, "03_list_articles_max_weight_"+max_cluster_weight);
        
        /*
        n_synonyms  = 300;
        eps_error   = 0.001f;
        List<Article> synonyms = auth.Calculate(base_nodes, eps_error, n_synonyms, session);
        */
    }
    
}
