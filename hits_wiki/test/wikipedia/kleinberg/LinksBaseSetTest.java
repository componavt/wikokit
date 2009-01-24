/*
 * LinksBaseSetTest.java
 * JUnit based test
 */

package wikipedia.kleinberg;

import wikipedia.language.Encodings;
import wikipedia.sql.*;
import wikipedia.util.*;

import junit.framework.*;
import java.util.*;

public class LinksBaseSetTest extends TestCase {
    
    public Connect  connect, connect_ru;
    Links           links;
    LinksBaseSet    links_baseset;
    DumpToGraphViz  dump;
    SessionHolder   session;
            
    public LinksBaseSetTest(String testName) {
        super(testName);
    }

    protected void setUp() throws java.lang.Exception {
        connect = new Connect();
        connect.Open(Connect.WP_HOST, Connect.WP_DB, Connect.WP_USER, Connect.WP_PASS);
        
        connect_ru = new Connect();
        connect_ru.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,Connect.WP_RU_USER,Connect.WP_RU_PASS);
        
        links_baseset   = new LinksBaseSet();
        
        dump            = new DumpToGraphViz();
        dump.file_dot.setFileInHomeDir("graphviz", "empty.txt", "Cp1251",true);
        dump.file_bat.setFileInHomeDir("graphviz", "bat_ruwiki.bat", "Cp866",true);
        dump.file_sh.setFileInHomeDir("graphviz", "bat_ruwiki.sh", "Cp1251",true);
        
        session = new SessionHolder();
        session.initObjects();
        session.dump = dump;
    }

    protected void tearDown() throws java.lang.Exception {
        connect.Close();
        connect_ru.Close();
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(LinksBaseSetTest.class);
        
        return suite;
    }

    /**
     * Test of CreateBaseSet method, of class wikipedia.LinksBaseSet.
     */
    public void testCreateBaseSet() {
        /*String  article;
        
        article = "Робот";      //          vertices:9      edges:11
        //article = "Самолёт";  // too big: vertices:1383   edges:34192
        //article = "Фуникулёр";  // medium size
        
        dump.file.SetFilename(article + "_all.dot");
        dump.file.Open(false, "UTF8");
        
        dump.connect = connect_ru;
        Map<Integer, Article> base_nodes = links_baseset.CreateBaseSet(connect_ru, article, dump);
        if (article.equals("Робот")) {
            assertEquals(base_nodes.size(), 9);
            assertTrue("Robot should refer to Dance", base_nodes.containsKey(10578));
            assertTrue("Robot should be referred to Transformers", base_nodes.containsKey(18991));
        }*/
        
        /*
         String[] all_words = {"Робот", "Рычаг", "Фуникулёр", 
                "Глаз", "Окуляр", "Орбитальная станция", "Плазма", "Фут", 
                "Свёртка", "Ноты", "Предложение", "Преферанс", "Взрыв", 
                "Самосознание", "Свобода", "Оптимизм"};
        
        // no refer: "Окуляр", "Свёртка",
        String[] big_words   = {"Фуникулёр", "Фут", "Глаз", "Ноты", "Предложение", 
                                "Взрыв", "Самосознание", "Свобода"};
        String[] small_words = {"Робот", "Рычаг", "Плазма", 
                                "Преферанс", "Оптимизм"};

        String[] new_words2 = {"Кварк", "Похмелье", "Кегль", 
                                "Киноварь", "Компилятор"};
        String[] new_words = {"Контрабас", "Конунг", "Константа", 
                                "Спин", "Спорт", "Дзюдо"};
        
        for(int i=0; i<new_words.length; i++) {
            links_baseset.CreateBaseSet(connect_ru, new_words[i]);
        }*/
    }
/*    
    public void testCreateBaseSetEnglish() {
        //links_baseset.CreateBaseSet(connect, "Innuendo");
        //links_baseset.CreateBaseSet(connect, "Parallelogram");
        
        String[] words_Blondel = {"disappear", "parallelogram", "sugar", "science"};
        String[] rare_words = {"Parallelogram", "Sycamore", "Innuendo"};
  
        for(int i=0; i<rare_words.length; i++) {
            links_baseset.CreateBaseSet(connect, rare_words[i]);
        }
    }
*/    
    
    // test parametrized CreateBaseSet(,..., int root_set_size, int increment)
    public void testCreateBaseSetParametrized_ru() {
        System.out.println("testCreateBaseSetParametrized_ru");
        int     root_set_size, increment;
        String  article, article_fn;
        
        // Kleinberg default values
        root_set_size = 19; //200;  18, 19 - failed
        increment = 50; //50;
        
        // Simple test values
        //root_set_size = 1;
        //increment = 1;
        
        int categories_max_steps = 5;
        session.Init(connect_ru, null, categories_max_steps);
        session.randomPages(false);
        session.skipTitlesWithSpaces(true);
        
        
        Encodings e = connect_ru.enc;
        
        article = e.EncodeFromJava("Робот");
        article_fn = StringUtilRegular.encodeRussianToLatinitsa(article, Encodings.enc_java_default, Encodings.enc_int_default);

        
        /*article = "Parallelogram";      // 77,953 sec   Hot MySQL: 48,344 sec
        //article = "Science";            // 76,094 sec   Hot MySQL: 50,094 sec
        dump.connect = connect_ru;*/
        
        dump.file_dot.setFileInHomeDir("graphviz", article_fn + ".dot", "UTF8",true);
        
        List<String> synonyms = new ArrayList<String>();
        Map<Integer, Article>  base_nodes = LinksBaseSet.CreateBaseSet(article, synonyms, session, root_set_size, increment);
        
        assertTrue(base_nodes.containsKey(session.source_article_id));
        Article s = base_nodes.get(session.source_article_id);
        assertTrue(null != s);
        
        String android = e.EncodeFromJava("Андроид");
        int android_id = PageTable.getIDByTitleNamespace(connect_ru, android, PageNamespace.MAIN);
        
        String search_engine = e.EncodeFromJava("Поисковая_система");
        int search_engine_id = PageTable.getIDByTitleNamespace(connect_ru, search_engine, PageNamespace.MAIN);
        
        // 1) "Андроид" is presented and "Поисковая_система" is absent in base_nodes;
        assertTrue (base_nodes.containsKey(      android_id));
        assertFalse(base_nodes.containsKey(search_engine_id));
        
        
        // 2) assert that "Андроид" and "Киборг" (submitted as synonyms) are in base_nodes;
        synonyms.add(android);
        synonyms.add(search_engine);
        base_nodes = LinksBaseSet.CreateBaseSet(article, synonyms, session, root_set_size, increment);
        assertTrue(base_nodes.containsKey(      android_id));
        assertTrue(base_nodes.containsKey(search_engine_id));
        
        Article a_android       = base_nodes.get(      android_id);
        Article a_search_engine = base_nodes.get(search_engine_id);
        assertEquals(NodeType.RATED_SYNONYMS, a_android.      type);
        assertEquals(NodeType.RATED_SYNONYMS, a_search_engine.type);
        
        
        //dump.connect = connect_ru;
        //links_baseset.CreateBaseSet(dump.connect, "Parallelogram", dump, 2, 2);
        /*
        String[] all_words = {"Робот", "Рычаг", "Фуникулёр", 
                "Глаз", "Окуляр", "Орбитальная станция", "Плазма", "Фут", 
                "Свёртка", "Ноты", "Предложение", "Преферанс", "Взрыв", 
                "Самосознание", "Свобода", "Оптимизм"};
        
        // no refer: "Окуляр", "Свёртка",
        String[] big_words   = {"Фуникулёр", "Фут", "Глаз", "Ноты", "Предложение", 
                                "Взрыв", "Самосознание", "Свобода"};
        String[] small_words = {"Робот", "Рычаг", "Плазма", 
                                "Преферанс", "Оптимизм"};

        String[] new_words2 = {"Кварк", "Похмелье", "Кегль", 
                                "Киноварь", "Компилятор"};
        String[] new_words = {"Контрабас", "Конунг", "Константа", 
                                "Спин", "Спорт", "Дзюдо"};
         
        for(int i=0; i<new_words.length; i++) {
            links_baseset.CreateBaseSet(dump.connect, new_words[i]);
        }*/
    }
    
    
    public void testCreateBaseSet_ForAbsentWord() {
        System.out.println("testCreateBaseSet_ForAbsentWord");
        int     root_set_size, increment;
        String  article;
        
        // Kleinberg default values
        /*root_set_size = 200;
        increment = 50;
        */
        
        // Simple test values
        root_set_size = 1;
        increment = 1;
        
        List<String> synonyms = new ArrayList<String>();
        //synonyms.add("syn1");
        //synonyms.add("syn2");
        
        article = "AbsentWord";
        int categories_max_steps = 99;
        session.Init(connect_ru, null, categories_max_steps);
        //session.skipTitlesWithSpaces(true);
        session.randomPages(false);
        
        /*article = "Parallelogram";      // 77,953 sec   Hot MySQL: 48,344 sec
        //article = "Science";            // 76,094 sec   Hot MySQL: 50,094 sec
        dump.connect = connect;*/
        
        dump.file_dot.SetFilename(article + ".dot");
        dump.file_dot.Open(false, "UTF8");
        
        Map<Integer, Article>  base_nodes = LinksBaseSet.CreateBaseSet(article, synonyms, session, root_set_size, increment);
        assertTrue(null == base_nodes);
    }
    
}

    /*
    public HashMap<Integer, Article> CreateBaseSet(Connect connect, String page_title, SessionHolder session) {
        Article         node = new Article();
        Links           link = new Links();
        DCEL            dcel = new DCEL();

        int             increment, root_set_size;
        root_set_size   = 200;
        //increment       = 50;
        
        // 1.
        String latin1_article = Encodings.UTF8ToLatin1(page_title);
        int p = connect.page_table.GetIDByTitle(connect, latin1_article);
        session.source_article_id = p;
        Article[] root_nodes = links.GetLFromByLTo(session, p, root_set_size);
        if(null == root_nodes) {
            if (null != session.dump) {
                String bat_text = "\n:: " + session.dump.file_dot.GetFilename() +".dot \t Warning: no page refers to this page.\n";
                session.dump.file_bat.Print(bat_text);
                session.dump.file_bat.Flush();
            }
            return null;    // nobody refers to the p page
        }
        node.SetType(root_nodes, 1);
        //file_dot.WriteNew(path + "1_0_ruwiki.dot", session.dump.Dump(root_nodes), "UTF8");
        
        // 2.1
        Article[] base_nodes1 = links.GetLFromByLTo(session, root_nodes, -1);
        //file_dot.WriteNew(path + "2_1_ruwiki.dot", session.dump.Dump(base_nodes1), "UTF8");
        
        // 2.2
        Article[] base_nodes2 = links.GetLToByLFrom(session, root_nodes);
        //file_dot.WriteNew(path + "2_2_ruwiki.dot", session.dump.Dump(base_nodes2), "UTF8");
        
        Article[] base_nodes           = node.JoinUnique(base_nodes1, base_nodes2);
        Article[] base_and_root_nodes  = node.JoinUnique(base_nodes, root_nodes);
        //file_dot.WriteNew(path + "2_2_unique.dot", session.dump.Dump(base_and_root_nodes), "UTF8");
        node.SetType(base_and_root_nodes, 2);
        
        // 2.3
        HashMap<Integer, Article> hash_node = node.CreateHashMap(base_and_root_nodes);
        hash_node.get(p).type = -1;  // set type for the very source article
        link.GetAllLinks(connect, hash_node);
        if (null != session.dump) {
            session.dump.Dump(hash_node);
        
            // append 1) statistics and 2) dot command to bat file
            String bat_text = "\n:: "+session.dump.file_dot.GetFilename()+"\t vertices:" + hash_node.values().size() +
                                               "\t edges:"    + dcel.CountLinksIn(hash_node) +
                              "\ndot.exe -Tjpeg " + session.dump.file_dot.GetFilename() + " -v -o " + session.dump.file_dot.GetFilenameWoExt() + ".jpeg\n";
            session.dump.file_bat.Print(bat_text);
            session.dump.file_bat.Flush();
        }

        return hash_node;
    }*/
    
