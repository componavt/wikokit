/*
 * MetricSpearmanTest.java
 * JUnit based test
 */

package wikipedia.experiment;

//import wikipedia.experiment.MetricSpearman;
import wikipedia.language.Encodings;
import wikipedia.util.StringUtil;
import wikipedia.util.StringUtilRegular;

import wikipedia.kleinberg.Article;
import wikipedia.kleinberg.SessionHolder;
import wikipedia.kleinberg.DumpToGraphViz;
import wikipedia.kleinberg.Authorities;
import wikipedia.kleinberg.LinksBaseSet;
import wikipedia.sql.Connect;
import wikipedia.kleinberg.DCEL;

import junit.framework.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
//import java.util.HashMap;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Date;

public class MetricSpearmanTest extends TestCase {

    //Article                 node;
    //Map<Integer, Article>  nodes;
    
    Authorities             auth;
    Connect                 connect, connect_ru;
    DumpToGraphViz          dump;
    SessionHolder           session;
    
    public MetricSpearmanTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect = new Connect();
        //connect.Open("enwiki?useUnicode=true&characterEncoding=UTF-8", "javawiki", "");
        //connect.Open("localhost", "enwiki", "javawiki", "");
        connect.Open(Connect.WP_HOST,       Connect.WP_DB,    Connect.WP_USER,    Connect.WP_PASS);
        
        connect_ru = new Connect();
        //connect_ru.Open("localhost", "ruwiki?useUnicode=false&characterEncoding=ISO8859_1", "javawiki", ""); //Java:MySQL ISO8859_1:latin1
        //connect_ru.Open("localhost", "ruwiki?autoReconnect=true&useUnbufferedInput=false&useUnicode=false&characterEncoding=ISO8859_1", "javawiki", "");
        connect_ru.Open(Connect.WP_RU_HOST, Connect.WP_RU_DB, Connect.WP_RU_USER, Connect.WP_RU_PASS);
        
        String f = System.getProperty("user.home") + "/.synarcher/test_kleinberg/graphviz/";
        auth            = new Authorities();
        dump            = new DumpToGraphViz();
        dump.file_dot.SetDir(f);
        
        /*
        dump.file_bat.SetDir(f);
        dump.file_bat.SetFilename("bat_ruwiki.bat");
        dump.file_bat.Open(true, "Cp866");
        
        dump.file_sh.SetDir(f);
        dump.file_sh.SetFilename("bat_ruwiki.sh");
        dump.file_sh.Open(true, "Cp1251");
        */
        session = new SessionHolder();
        session.initObjects();
        session.dump = dump;
    }

    protected void tearDown() throws Exception {
        connect.Close();
        connect_ru.Close();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(MetricSpearmanTest.class);
        
        return suite;
    }

    /**
Example of comparing of list of different length:
All words:      S1       S2         Общие слова S1*     S2*     |S2*-S1*|
1. Автожир      Автожир	 Турболёт   Автожир     1       3       2
2. Планер       Турболёт Фюзеляж    Турболёт    2       1       1
3. Турболёт     Планер   Автожир    Планер      3       4       1
4. Фюзеляж               Планер                                 4 (Значение метрики)
5. Космонавт             Космонавт
     */
    public void testCompare_StringArrays_test1() {
        System.out.println("compare_StringArrays_test1");
        
        String[] synonyms1      = {"Автожир", "Турболёт", "Планер"};
        String[] synonyms2      = {"Турболёт", "Фюзеляж", "Автожир", "Планер", "Космонавт"};
        
        MetricSpearman metr = new MetricSpearman();
        
        int result = metr.compare(synonyms1, null);
        assertEquals(-1, result);
        
        // equal lists
        result = metr.compare(synonyms1, synonyms1);
        assertEquals(0, result);
        
        result = metr.compare(synonyms1, synonyms2);
        assertEquals(4, result);
    }
    
        /**
Example of comparing of list of different length:
All words:      S1       S2         Общие слова S1*     S2*     |S2*-S1*|
1. Автожир      Турболёт Автожир    Автожир     1       3       2
2. Планер       Планер   Фюзеляж    Турболёт    2       1       1
3. Турболёт              Турболёт   Планер      3       4       1
4. Фюзеляж               Космонавт                              4 (Значение метрики)
5. Космонавт             Планер     
     */
    public void testCompare_StringArrays_test2() {
        System.out.println("compare_StringArrays_test2");
        
        String[] synonyms1      = {"Турболёт", "Планер"};
        String[] synonyms2      = {"Автожир", "Фюзеляж", "Турболёт", "Космонавт", "Планер"};
        String[] synonyms3      = {"Фюзеляж", "Космонавт"};
        
        MetricSpearman metr = new MetricSpearman();
        
        int result = metr.compare(synonyms1, synonyms2);
        assertEquals(5, result);

        result = metr.compare(synonyms1, synonyms3);
        assertEquals(4, result);
    }
    
    
    public void testCompare_StringArrays_test3() {
        System.out.println("compare_StringArrays_test2");
        
        String[] synonyms1      = {"Турболёт", "Планер"};
        String[] synonyms2      = {"Планер"};
        String[] synonyms3      = {"WordAbsentInList1", "AnotherWordAbsentInList1"};
        
        // assert: distance(synonyms1,synonyms2) < distance(synonyms1,synonyms3)
        
        MetricSpearman metr = new MetricSpearman();
        
        int dist12 = metr.compare(synonyms1, synonyms2);
        int dist13 = metr.compare(synonyms1, synonyms3);
        assertTrue(dist12 < dist13);
    }

    public void testCompare_ArticleList() {
        System.out.println("compare_ArticleList");
        
        String[] synonyms1      = {"Автожир", "Турболёт", "Планер"};
        String[] synonyms2      = {"Турболёт", "Фюзеляж", "Автожир", "Планер", "Космонавт"};
        List<Article> list1 = new ArrayList<Article>();
        List<Article> list2 = new ArrayList<Article>();
        
        for(String s:synonyms1) {
            Article a = new Article();
            a.page_title = s;
            list1.add(a);
        }
        for(String s:synonyms2) {
            Article a = new Article();
            a.page_title = s;
            list2.add(a);
        }
        
        MetricSpearman metr = new MetricSpearman();
    
        int result = metr.compare(list1, null);
        assertEquals(-1, result);
        
        // equal lists
        result = metr.compare(list1, list1);
        assertEquals(0, result);
        
        result = metr.compare(list1, list2);
        assertEquals(4, result);
    }

    
    public void testCalcSpearmanFootrule() {
        System.out.println("testCalcSpearmanFootrule");
        
        
        String[] synonyms1  = {"Автожир", "Турболёт", "Планер"};
        String[] synonyms2  = {"Космонавт", "Планер", "Автожир", "Турболёт"};
        String[] synonyms3  = {"Автожир", "Космонавт", "Турболёт", "Планер"};
        String[] synonyms4  = {"Автожир", "Космонавт", "Планер", "Турболёт"};
        String[] synonyms_word1  = {"word1"};
        String[] synonyms_word2  = {"word2"};
        
        MetricSpearman m = new MetricSpearman();
        
        double dist11 = m.calcSpearmanFootrule(synonyms1, synonyms1);
        double dist12 = m.calcSpearmanFootrule(synonyms1, synonyms2);
        double dist21 = m.calcSpearmanFootrule(synonyms2, synonyms1);
        double dist13 = m.calcSpearmanFootrule(synonyms1, synonyms3);
        double dist14 = m.calcSpearmanFootrule(synonyms1, synonyms4);
        
        double eps = 0.01;
        assertTrue(m.equals(1, dist11, eps));
        assertTrue(m.equals(0, dist12, eps));
        assertTrue(m.equals(0, dist21, eps));
        assertTrue(m.equals(1, dist13, eps));
        assertTrue(m.equals(0.5, dist14, eps));
        
        double dist_word11 = m.calcSpearmanFootrule(synonyms_word1, synonyms_word1);
        double dist_word12 = m.calcSpearmanFootrule(synonyms_word1, synonyms_word2);
        assertTrue(m.equals(1, dist_word11, eps));
        assertTrue(m.equals(0, dist_word12, eps));
    }
    
    //String findStringWithPosition (String[] big, String[] small) {
    public void testFindStringWithPosition() {
        System.out.println("testFindStringWithPosition");
        
        String[] short1  = {"Автожир", "Турболёт"};
        String[] long1  = {"Космонавт", "Планер", "Автожир", "Турболёт"};
        String should_be = "Автожир2,Турболёт3";
        String token = ",";
        
        String res = MetricSpearman.findStringWithPosition(long1, short1, token);
        assertEquals(should_be, res);
    }
    
    
    public void testCompareSynonyms() {
        int     root_set_size, increment, n_synonyms;
        String  filename;
        long    t_start, t_end;
        float   t_work, t_max;  // time of one cycle's work
        String[] all_words = null;
        boolean b_russian;
        float   eps_error;      // error to stop the iteration
        String  directory;
        Encodings e = connect_ru.enc;
        //if(true) {return;}
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                   DateFormat.LONG,
                                   new Locale("en","US"));
        String today = formatter.format(new Date());
        int categories_max_steps = 10; //99;
        
        // Russian
        String[] ru_words = {"Самолёт", "Сюжет", "Истина", "Жаргон"};   // 
        String[][] ru_words_standards = { 
            //"Самолёт"
            {"Планер", "Турболёт", "Автожир", "Экранолёт", "Экраноплан", "Конвертоплан"},
            //"Сюжет"
            {"Интрига", "Переживание", "Конфликт", "Трагедия", "Коллизия", "Противоречие"},
            //"Истина"
            {"Факт", "Действительность", "Реальность", "Правда", "Бог", "Знание", "Вера", "Авторитет", "Догмат"},
            // "Жаргон"
            {"Сленг", "Просторечие", "Матерщина", "Диалект", "Арго", "Эвфемизм"}
        };
        
        // English
        //String[] en_words = {"Science"};  // Sugar Parallelogram Sycamore
        String[] en_words = {"Parallelogram", "Sycamore", "Innuendo", // rare words
                             "Disappear", "Sugar", "Science",           //};// words_Blondel + "parallelogram"
                             "Astronaut", "Mechanism", "Universal", "Universe" };
        
        b_russian = true;
        if (b_russian) {
            all_words = ru_words;
            //directory = "data/synonyms_ru/";  //String filename = "ruwiki_synonyms.txt";
            directory = System.getProperty("user.home") + "/.synarcher/test_kleinberg/ru/";
            session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        } else {
            all_words = en_words;
            //directory = "data/synonyms_en/";  //String filename = "enwiki_synonyms.txt";
            directory = System.getProperty("user.home") + "/.synarcher/test_kleinberg/en/";
            session.Init(connect, session.category_black_list.en, categories_max_steps);
        }
        session.skipTitlesWithSpaces(false); // 1
        //session.skipTitlesWithSpaces(true); // 2
        
        session.dump = null;
        n_synonyms = 100;
        eps_error  = 0.001f;
        
        // Kleinberg default values
        /*root_set_size = 300;  increment = 50;*/
        // Simple test values
        /*root_set_size = 1;    increment = 2; */
        int root_start, root_end, root_add;
        int inc_start, inc_end, inc_add;
        
        
        boolean b_params_experiment = false;
        if(b_params_experiment) {
            // best parameters experiment
            // fast
            //root_start = 10; root_end = 20; root_add = 10; inc_start = 10;  inc_end=20; inc_add=5;
            // long
            root_start = 10; root_end = 510; root_add = 50; inc_start = 10;  inc_end=60; inc_add=10;
        } else {
            // skipSpaces experiment
            root_start = 200; root_end = 200; root_add = 50;  inc_start = 17;  inc_end=17; inc_add=20;
        }
        
        //t_max = 10f;    // sec          // en:43 minutes 48 seconds     ru:3 min
        //t_max = 80f;    // sec
        //t_max = 201f;    // english 11 min
        //t_max = 300f;       // en depth-first search:  1 hours 40 min
                            // en breadth-first search:1 hours 14 min 
        t_max = 3000f;   // english 4 hours 10 min

        //dump.enable_file_dot = false;
        dump.file.SetDir(directory);
        WORDS_CYCLE:
        //for(int i=0; i<all_words.length; i++) {
        for(int i=0; i<0; i++) {
            //System.out.println ("The word Latin1ToUTF8 '"+Encodings.Latin1ToUTF8(all_words[i])+"' is processing...");
            System.out.println ("The word '" + all_words[i] + "' is processing...");
            
            t_start = System.currentTimeMillis();
            String article = all_words[i];
            //dump.file_dot.SetFilename(article + ".dot");
            //dump.file_dot.Open(false, "UTF8");

            String article_fn = StringUtilRegular.encodeRussianToLatinitsa(all_words[i], Encodings.enc_java_default, Encodings.enc_int_default);
            dump.file.SetFilename( article_fn + "_metric_spf.txt");
            dump.file.Open(true, "Cp1251");
            dump.file.Print("\n\ntime max:" + t_max + 
                           "  start:" + today + " \n");
            if(b_params_experiment) {
                dump.file.Print("\n" +
                    "root_set_size\t" + "increment\t" +
                   // intersect.length + "\t" + dist_f + "\t" + dist_i + "\t" + 
                    "intersect_words\t" + "Spearman_footrule\t" + "G\t" +
                    "time(sec)\t" + 
                    "iter\t" +
                    "vertices\t" + 
                    "edges\t" + 
                    "n_synonyms\t" + "synonyms.size()\t" + "synonyms\t" + 
                    "skipTitlesWithSpaces\t" +
                    "total_steps_while_categories_removing\n");
            }
            dump.file.Flush();
            
            ROOT_CYCLE:
            for(root_set_size =  root_start; 
                root_set_size <= root_end;  
                root_set_size += root_add) {
                for(increment =  inc_start;
                    increment <= inc_end;
                    increment += inc_add) {

                    //root_set_size= 10; increment = 50; // FIXME temp

                    filename = article + ".txt";
                    if(!b_params_experiment) {
                        dump.file.Print("\n\n***\n*\n* New_Iteration *** *** ***\n*\n***\n");
                        dump.file.Flush();
                        dump.enable_file_dot = false;
                    }

                    session.clear();
                    List<String> rated_synonyms = new ArrayList<String>();
                    Map<Integer, Article>  base_nodes = LinksBaseSet.CreateBaseSet(article, rated_synonyms, 
                            session, root_set_size, increment);
                    if(!b_params_experiment) {
                        dump.file.PrintNL("Total_steps_while_categories_removing:"+
                                session.category_black_list.getTotalCategoriesPassed());
                        dump.file.Flush();
                    }
                    
                    List<Article> synonyms = auth.Calculate(base_nodes, eps_error, n_synonyms, session);
                    
                    t_end  = System.currentTimeMillis();
                    t_work = (t_end - t_start)/1000f; // in sec
                    
                    if (null != synonyms) {
                        // compare with standard list
                        String[] synonyms_title = Article.getTitles((Article[])synonyms.toArray(new Article[0]));
                        int dist_i = MetricSpearman.compare(synonyms_title, ru_words_standards[i]);
                        double dist_f = MetricSpearman.calcSpearmanFootrule(synonyms_title, ru_words_standards[i]);
                        String[] intersect = StringUtil.intersect(synonyms_title, ru_words_standards[i]);
                        
                        if(b_params_experiment) {
                            dump.file.Print(
                                    root_set_size + "\t" + increment + "\t" + 
                                    intersect.length + "\t" + dist_f + "\t" + dist_i + "\t" + 
                                    t_work                          + "\t" +
                                    auth.iter                       + "\t" +
                                    base_nodes.values().size()      + "\t" +
                                    DCEL.CountLinksIn(base_nodes)   + "\t" +
                                    n_synonyms                      + "\t" + synonyms.size() + "\t" + 
                                    //StringUtil.join(",",synonyms_title)  + "\t" + 
                                    MetricSpearman.findStringWithPosition(synonyms_title, ru_words_standards[i], ",")  + "\t" + 
                                    session.skipTitlesWithSpaces()  + "\t" +
                                    session.category_black_list.getTotalCategoriesPassed() + "\n");                            
                        } else {
                            dump.file.Print("\n\ntime sec:" + t_work + 
                                        " iter:" + auth.iter +
                                        " vertices:" + base_nodes.values().size() +
                                        " edges:" + DCEL.CountLinksIn(base_nodes) +
                                        "\nroot_set_size:"+root_set_size+" increment:"+increment +
                                        "\n Metric Spearman footrule:" + dist_f +
                                        "\n Metric G:" + dist_i +
                                        "\nn_synonyms:"+n_synonyms +
                                        "\nsynonyms.size():"+synonyms.size() +
                                        "\nStringWithPosition" + MetricSpearman.findStringWithPosition(synonyms_title, ru_words_standards[i], ",")  +
                                        "\nskipTitlesWithSpaces:"+session.skipTitlesWithSpaces()+
                                        "\ntotal_steps_while_categories_removing:"+
                                        session.category_black_list.getTotalCategoriesPassed() + "\n");
                            dump.file.Flush();
                            auth.AppendSynonyms(article, synonyms, "|", dump);
                            dump.file.Print("\n");
                            // add
                            // intersect.length + "\t" + dist_f + "\t" + dist_i + "\t" + 
                        }
                        dump.file.Flush();
                    }
                    
                    if(t_work > t_max) {
//                        break ROOT_CYCLE;
                    }
//                    break WORDS_CYCLE;
                }
            }
        }
    }
    
}
