/*
 * AuthoritiesTest.java
 * JUnit based test
 */

package wikipedia.kleinberg;

import wikipedia.language.Encodings;
import wikipedia.sql.*;
import wikipedia.util.*;
import wikipedia.clustering.*;

import junit.framework.*;
import java.util.*;
import java.text.*;

public class AuthoritiesTest extends TestCase {

    Article                 node;
    Authorities             auth;
    Map<Integer, Article>   nodes;
    Connect                 connect, connect_ru;
    DumpToGraphViz          dump;
    SessionHolder           session;
    
    public AuthoritiesTest(String testName) {
        super(testName);
    }

    protected void setUp() throws java.lang.Exception {
        connect = new Connect();
        connect.Open(Connect.WP_HOST, Connect.WP_DB, Connect.WP_USER, Connect.WP_PASS);
        
        connect_ru = new Connect();
        connect_ru.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,Connect.WP_RU_USER,Connect.WP_RU_PASS);
        
        //String f = System.getProperty("synarcher") + "/.synarcher/test_kleinberg/graphviz/";
        auth            = new Authorities();
        dump            = new DumpToGraphViz();
        //dump.file_dot.SetDir("data/graphviz/");
        //dump.file_dot.SetDir(f);
        
        dump.file_dot.setFileInHomeDir("graphviz", "test.dot", "Cp866",true);
        dump.file_bat.setFileInHomeDir("graphviz", "bat_ruwiki.bat", "Cp866",true);
        dump.file_sh.setFileInHomeDir("graphviz", "bat_ruwiki.sh", "Cp1251", true);
        
        node = new Article();
        
        nodes = new HashMap<Integer, Article>();
        Article[] source_nodes = new Article[4];
        source_nodes[0] = new Article();
        source_nodes[1] = new Article();
        source_nodes[2] = new Article();
        source_nodes[3] = new Article();
        source_nodes[0].page_id = 18991;
        source_nodes[0].x = 
12.f;   source_nodes[1].page_id = 10484;
        source_nodes[1].x = 
2.f;    source_nodes[2].page_id = 3321;
        source_nodes[2].x = 
7.f;    source_nodes[3].page_id = 1121;
        source_nodes[3].x = 
7.f;
        // put source_nodes to nodes
        for(int i=0; i<source_nodes.length; i++) {
            nodes.put(source_nodes[i].page_id, source_nodes[i]);
        }
        
        session = new SessionHolder();
        session.initObjects();
        session.dump = dump;
    }

    protected void tearDown() throws java.lang.Exception {
        connect.Close();
        connect_ru.Close();
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(AuthoritiesTest.class);
        return suite;
    }

    
    /**
     * Test of getLargestXNodes method, of class wikipedia.Authorities.
     */
/*    public void testGetLargestXNodes() {
        int n_largest_authority = 3;
        Article[] nodes_sorted = auth.getLargestXNodes(null, n_largest_authority);
        assertEquals(null, nodes_sorted);
        
        nodes_sorted = auth.getLargestXNodes(nodes, n_largest_authority);
        assertEquals(nodes_sorted.length, n_largest_authority);
        assertEquals(nodes_sorted[0].x, 12.f);
        assertEquals(nodes_sorted[1].x, 7.f);
        assertEquals(nodes_sorted[2].x, 7.f);
    }
*/
    
    public void testGetTitles() {
        System.out.println("GetTitles");
        
        List<Article> nodes = new ArrayList<Article>();
        Article       a1    = new Article();
        a1.page_title = "Title1";
        
        String          delimiter = ",";
        String sres = auth.getTitles(nodes, delimiter);
        assertEquals(null, sres);
        
        nodes.add(a1);
        sres = auth.getTitles(nodes, delimiter);
        assertEquals(a1.page_title, sres);
    }
    
    public void testSynonymsToString() {
        System.out.println("SynonymsToString");
        
        List<Article> nodes = new ArrayList<Article>();
        String      s, delimiter = ",";
        
        // test null
        s = auth.SynonymsToString("", null, delimiter);
        s = auth.SynonymsToString("source_synonym", nodes, delimiter);
        assertEquals(s, "source_synonym");
        
        // test "Title1"
        Article a1    = new Article();
        a1.page_title = "Title1";
        nodes.add(a1);
        
        s = auth.SynonymsToString("source_synonym", nodes, delimiter);
        assertEquals(s, "source_synonym,Title1");
        
        // test "Title2"
        Article a2    = new Article();
        a2.page_title = "Title2";
        nodes.add(a2);
        
        s = auth.SynonymsToString("source_synonym", nodes, delimiter);
        assertEquals(s, "source_synonym,Title1,Title2");
    }
    
    
    public void testgetAllHubs() {
        int     root_set_size, increment, n_synonyms;
        float   eps_error;              // error to stop the iteration
        String  article, article_fn;
        Encodings e = connect_ru.enc;
        
        //root_set_size = 300;  increment = 50;
        root_set_size = 10;    increment = 2;
        //root_set_size = -1;    increment = 2;
        
        article = e.EncodeFromJava("Контрабас");   // Интернационализация Контрабас Преферанс
        article_fn = StringUtilRegular.encodeRussianToLatinitsa(article, Encodings.enc_java_default, Encodings.enc_int_default);
        int categories_max_steps = 5;
        
        // skip Persons
        List<String> black_ru = new ArrayList<String>();
        //black_ru.addAll(session.category_black_list.ru);
        //black_ru.add("Русские_музыканты");
        //page_title = Encodings.FromTo("Жданов,_Василий_Александрович", "UTF8", "ISO8859_1");
        //page_title = "Жданов,_Василий_Александрович";
        //black_ru.add("Музыканты_России");
        
        session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        session.randomPages(false);
        session.skipTitlesWithSpaces(false);
        //session.dump = null;
        
        if (null != session.dump) {
            String fs = System.getProperty("file.separator");
            String sub_dir = "test_kleinberg" + fs + "ru" + fs;
            dump.file.setFileInHomeDir(sub_dir, article_fn + ".txt", "Cp1251",true);
        }

        List<String> rated_synonyms = new ArrayList<String>();
        Map<Integer, Article>  base_nodes = LinksBaseSet.CreateBaseSet(article, rated_synonyms, session, root_set_size, increment);
        eps_error = 0.001f;
        int iter = auth.Iterate(base_nodes, eps_error, session);
        
        List<Article> hubs = auth.getAllHubsSortedByY(
                                            base_nodes, session.source_article_id);
        if (null != session.dump) {
            dump.file.PrintNL( "\nhubs (sorted by Y) pointed to the source article:\n" +
                                auth.getTitles(hubs, " | ") );
            dump.file.Flush();
        }
        // assertTrue(3 <= hubs.size());
        //assertTrue(hubs.get(0).page_id == 1400);
        //assertTrue(hubs.get(1).page_id == 14894);
        
        {
            n_synonyms = 5; // 4
            List<Article> l_synonyms = auth.getAuthoritiesSortedByX(base_nodes, hubs, n_synonyms);
            assertTrue(l_synonyms != null && 0 < l_synonyms.size());
            String synonyms_titles = auth.getTitles(l_synonyms, " | ");
                   //synonyms_titles = Encodings.FromTo(synonyms_titles, "ISO8859_1", "UTF8");
            
            //String compare_text = e.EncodeFromJava("Музыка | Фортепиано | Ксилофон | Мелодия");
                                // "Музыка | Фортепиано | Ксилофон | Ноты"
                                // "Ксилофон | Маримба | Вибрафон | Колокольчики"
                                // "Гитара | Скрипка | Электроника_(наука) | Валторна";
            // Джаз | Виолончель | Сайкобилли | Жданов | Бас-гитара
            // Музыка | Фортепиано | Гармония | Скрипка | Пианист

            // String s1 = Encodings.FromTo(compare_text, "UTF8", "ISO8859_1");
            List<String> lsyn = new ArrayList<String>();
            /*lsyn.add( e.EncodeFromJava("Джаз")      );
            lsyn.add( e.EncodeFromJava("Виолончель"));
            lsyn.add( e.EncodeFromJava("Сайкобилли"));
            lsyn.add( e.EncodeFromJava("Жданов")    );
            lsyn.add( e.EncodeFromJava("Бас-гитара"));*/
            //
            // all: Гармония Пианист Скрипка Музыка Викисклад Фортепиано Мелодия Импровизация
            // now: Импровизация | Викисклад | Система_Хорнбостеля_—_Закса | Музыка | Скрипка
            lsyn.add( e.EncodeFromJava("Импровизация"));//+
            lsyn.add( e.EncodeFromJava("Викисклад") );//+
            lsyn.add( e.EncodeFromJava("Система_Хорнбостеля_—_Закса") );//+
            lsyn.add( e.EncodeFromJava("Музыка")    );//+
            lsyn.add( e.EncodeFromJava("Ноты"));// Ноты Скрипка
            
            assertEquals(5, l_synonyms.size());
            /*for(int i=0;i<l_synonyms.size();i++) {
                assertTrue(lsyn.contains( l_synonyms.get(i).page_title ));
            }*/
        }
        
        n_synonyms = 20;
        List<Article> synonyms = auth.getAuthoritiesSortedByX(base_nodes, hubs, n_synonyms);
        // first order 4 nodes with x = 0.0814: Huitar (id=14671), "1917" (id=706), "XX_век" (id=692), Fiddle (id=11939)
        // second order 7 nodes:    x = 0.074893 Гобой (14350), Электроника (9500), Синтезатор_(музыкальный_инструмент) (10536), 
        //                          Домра (749), Шум (11733), Альт (14945), Валторна (29706)

        /* Music tools:
         *      First order:  Гитара | Скрипка | 1917 | XX_век | 
         *      Second order: Валторна | Альт | Гобой | Шум | Синтезатор_(музыкальный_инструмент) | Электроника | Домра | 
         * Sicobilli:
         *      First order:  Бас-гитара | Великобритания | Европа | 
                Second order: Панк-рок | Япония
         */
        String synonyms_titles = auth.getTitles(synonyms, " | ");
        if (null != session.dump) {
            dump.file.PrintNL( "hubs (sorted by Y) pointed to the source article:\n" + synonyms_titles);
            dump.file.Flush();
        }
        //String compare_text = "Гитара | Скрипка | 1917 | XX_век | Валторна | Альт | Гобой | Шум | Синтезатор_(музыкальный_инструмент) | Электроника | Домра | Бас-гитара | Великобритания | Европа | Панк-рок | Япония";
        String compare_text = "Гитара | Скрипка | Валторна | Альт | Гобой | Шум | Синтезатор_(музыкальный_инструмент) | Электроника | Домра | Бас-гитара | Панк-рок";
        //                  x= 0.104    0.104     0.0939    .0939   .0939  .0939  .0939                                 .0939         .0939   0.014        0.012
        
        //assertEquals(0, synonyms_titles.compareTo(compare_text));
        //assertEquals(11, synonyms.size());
    }
    
    /**
     * Test of Calculate method, of class wikipedia.Authorities.
     */
    public void testCalculate() {
        int     root_set_size, increment;
        int     n_synonyms;    //, iter;
        float   eps_error;              // error to stop the iteration
        String  article, directory;
        //root_set_size = 100;    increment = 30; // science: time 5 min
        // root_set_size = 150;    increment = 50; // 6 min time 
        //root_set_size = 200;    increment = 50; // Kleinberg default values; Science:11 min (? 25 min + failed)
        root_set_size = 1;   increment = 2;   // Simple test values
        int categories_max_steps = 99;
        
        article = "Контрабас";   // Контрабас, Плазма
        session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        //directory = "data/synonyms_ru/";
        directory = System.getProperty("user.home") + "/.synarcher/test_kleinberg/ru/";
/*
        article = "Science";    // 39 minutes or 2 366,359 sec  vertices:12407  edges:410156 if root_set_size = 200; increment = 50;
        session.Init(connect, session.category_black_list.en);
        directory = "data/synonyms_en/";
*/        
/*        //session.dump = null;
        dump.file.SetDir(directory);
        dump.file.SetFilename(article + ".txt");
        dump.file.Open(true, "Cp1251");

        HashMap<Integer, Article>  base_nodes = LinksBaseSet.CreateBaseSet(article, session, root_set_size, increment);

        n_synonyms  = 300;
        eps_error   = 0.001f;
        List<Article> synonyms = auth.Calculate(base_nodes, eps_error, n_synonyms, session);
*/        
        //public ArrayList<Integer> categories;
    }
    
    
    public void testAppendSynonyms() {
        int     root_set_size, increment, n_synonyms;
        long    t_start, t_end;
        float   t_work, t_max;  // time of one cycle's work
        String[] all_words = null;
        boolean b_russian;
        float   eps_error;      // error to stop the iteration
        String  directory, article_fn;
        Encodings e = connect_ru.enc;

        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                   DateFormat.LONG,
                                   new Locale("en","US"));
        String today = formatter.format(new Date());
        int categories_max_steps = 10; //99;
        
        // Russian
        String[] ru_words = {"Сюжет"}; // Контрабас Робот Фуникулёр Контрабас
        /*String[] ru_words = {"Робот", "Рычаг", "Фуникулёр", 
                "Глаз", "Окуляр", "Орбитальная станция", "Плазма", "Фут", 
                "Свёртка", "Ноты", "Предложение", "Преферанс", "Взрыв", 
                "Самосознание", "Свобода", "Оптимизм", "Контрабас"};
        */
        // English
        //String[] en_words = {"Science"};  // Sugar Parallelogram Sycamore
        String[] en_words = {"Parallelogram", "Sycamore", "Innuendo", // rare words
                             "Disappear", "Sugar", "Science",           //};// words_Blondel + "parallelogram"
                             "Astronaut", "Mechanism", "Universal", "Universe" };
        
        b_russian = true;
        if (b_russian) {
            all_words = ru_words;
            //directory = "data/synonyms_ru/";  //String filename = "ruwiki_synonyms.txt";
            //directory = System.getProperty("user.home") + "/.synarcher/test_kleinberg/ru/";
            directory = "ru";
            session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        } else {
            all_words = en_words;
            //directory = "data/synonyms_en/";  //String filename = "enwiki_synonyms.txt";
            //directory = System.getProperty("user.home") + "/.synarcher/test_kleinberg/en/";
            directory = "en";
            session.Init(connect, session.category_black_list.en, categories_max_steps);
        }
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        //session.dump = null;
        
        n_synonyms = 100;
        eps_error  = 0.001f;
        
        // Kleinberg default values
        /*root_set_size = 300;  increment = 50;*/
        // Simple test values
        /*root_set_size = 1;    increment = 2; */
        int root_start, root_end, root_add;
        int inc_start, inc_end, inc_add;
        root_start = 10;
        root_end = 310;
        root_add = 50;
        inc_start = 10;  inc_end=60; inc_add=20;
        t_max = 10f;    // sec          // en:43 minutes 48 seconds     ru:3 min
        //t_max = 80f;    // sec
        //t_max = 201f;    // english 11 min
        //t_max = 300f;       // en depth-first search:  1 hours 40 min
                            // en breadth-first search:1 hours 14 min 
        //t_max = 3000f;   // english 4 hours 10 min

        
        //dump.enable_file_dot = false;
        //directory = System.getProperty("user.home") + "/.synarcher/test_kleinberg/en/";
        
        String fs = System.getProperty("file.separator");
        String sub_dir = "test_kleinberg" + fs + directory + fs;
        dump.file.setFileInHomeDir(sub_dir, "empty.txt", "Cp1251",true);
        
        WORDS_CYCLE:
        for(int i=0; i<all_words.length; i++) {
            //System.out.println ("The word Latin1ToUTF8 '"+Encodings.Latin1ToUTF8(all_words[i])+"' is processing...");
            System.out.println ("The word '" + all_words[i] + "' is processing...");
            
            t_start = System.currentTimeMillis();
            String article = all_words[i];
            //dump.file_dot.SetFilename(article + ".dot");
            //dump.file_dot.Open(false, "UTF8");
            
            article_fn = StringUtilRegular.encodeRussianToLatinitsa(article, Encodings.enc_java_default, Encodings.enc_int_default);
            dump.file.SetFilename(article_fn + ".txt");
            dump.file.Open(true, "Cp1251");
            dump.file.Print("\n\ntime max:" + t_max + 
                           "  start:" + today + " add categories_max_steps to Categorylinks.InBlackList()\n");
            dump.file.Flush();
            
            ROOT_CYCLE:
            for(root_set_size =  root_start; 
                root_set_size <= root_end;  
                root_set_size += root_add) {
                for(increment =  inc_start;
                    increment <= inc_end;
                    increment += inc_add) {

                    //root_set_size= 10; increment = 50; // FIXME temp
                    dump.file.SetFilename(article_fn + ".txt");
                    dump.file.Open(true, "Cp1251");
                    dump.file.Print("\n\n***\n*\n* New_Iteration *** *** ***\n*\n***\n\n");
                    dump.file.Flush();
                    dump.enable_file_dot = false;

                    session.clear();
                    List<String> rated_synonyms = new ArrayList<String>();
                    Map<Integer, Article>  base_nodes = LinksBaseSet.CreateBaseSet(article, rated_synonyms, session, root_set_size, increment);
                    dump.file.PrintNL("Total_steps_while_categories_removing:"+
                            session.category_black_list.getTotalCategoriesPassed());
                    dump.file.Flush();
                    
                    List<Article> synonyms = auth.Calculate(base_nodes, eps_error, n_synonyms, session);
                    
                    
                    
                    t_end  = System.currentTimeMillis();
                    t_work = (t_end - t_start)/1000f; // in sec
                    
                    if (null != synonyms) {
                        dump.file.Print("\n\ntime sec:" + t_work + 
                                        " iter:" + auth.iter +
                                        " vertices:" + base_nodes.values().size() +
                                        " edges:" + DCEL.CountLinksIn(base_nodes) +
                                        "\nroot_set_size:"+root_set_size+" increment:"+increment +
                                        "\nn_synonyms:"+n_synonyms +
                                        " total_steps_while_categories_removing:"+
                                        session.category_black_list.getTotalCategoriesPassed() + "\n");
                        dump.file.Flush();
                        auth.AppendSynonyms(article, synonyms, "|", dump);
                        dump.file.Print("\n");
                        dump.file.Flush();
                        
                        // print best hubs as triangles
                        dump.Dump(base_nodes, "");
                        // append dot command to bat file
                        dump.file_bat.Print( dump.GetStatisticsHashMap(base_nodes) + dump.GetDotCommand("jpeg", true) );
                        dump.file_sh. Print( dump.GetStatisticsHashMap(base_nodes) + dump.GetDotCommand("jpeg", false) );
                        dump.file_bat.Flush();
                        dump.file_sh. Flush();
                        
                        // cluster synonyms
                        Map<Integer, Article> map_synonyms = Article.createMapIdToArticleWithoutRedirects((Article[])synonyms.toArray(new Article[0]));
                        dump.enable_file_dot = true;
                        CategorySet.prepareCategories(session, map_synonyms);
                        int max_cluster_weight = 20;
                        List<ClusterCategory> clusters = CategorySet.getCategoryClusters (session.category_nodes, map_synonyms, max_cluster_weight);
                        CategorySet.dumpClusterCategoryArticle(session, map_synonyms, clusters, "02_clusters_max_weight_"+max_cluster_weight+"_root_set_size_"+root_set_size+"_increment_"+increment);
                        CategorySet.dumpClusterCategorywithListArticles(session, map_synonyms, clusters, "03_list_articles_max_weight_"+max_cluster_weight+"_root_set_size_"+root_set_size+"_increment_"+increment);
                    }
                    
                    if(t_work > t_max) {
                        break ROOT_CYCLE;
                    }
                    break WORDS_CYCLE;
                }
            }
        }

    }
}




