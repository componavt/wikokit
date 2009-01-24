package wikipedia.kleinberg;

import wikipedia.language.Encodings;
import junit.framework.*;
import wikipedia.clustering.*;
import wikipedia.util.*;
import wikipedia.sql.*;
import java.util.*;

public class DumpToGraphVizTest extends TestCase {
    
    public Connect  connect, connect_ru;
    SessionHolder   session;
    DumpToGraphViz  dump;
    int             categories_max_steps;
    
    Article[]       source_nodes;
    String          t1;
    int             t1_id;
    
    public DumpToGraphVizTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect = new Connect();
        //connect.Open("localhost", "enwiki?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false", "javawiki", "");
        connect.Open(Connect.WP_HOST,       Connect.WP_DB,    Connect.WP_USER,    Connect.WP_PASS);
        
        connect_ru = new Connect();
        //connect_ru.Open("localhost", "ruwiki?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false", "javawiki", ""); //Java:MySQL ISO8859_1:latin1
        connect_ru.Open(Connect.WP_RU_HOST, Connect.WP_RU_DB, Connect.WP_RU_USER, Connect.WP_RU_PASS);
        
        dump            = new DumpToGraphViz();
        dump.file_dot.setFileInHomeDir("graphviz", "empty.txt", "Cp1251",true);
        dump.file_bat.setFileInHomeDir("graphviz", "bat_ruwiki.bat", "Cp866",true);
        dump.file_sh.setFileInHomeDir("graphviz", "bat_ruwiki.sh", "Cp1251",true);
        
        session = new SessionHolder();
        session.initObjects();
        categories_max_steps = 99;
        session.dump = dump;
        
        t1           = connect.enc.EncodeFromJava("Джемини");
        t1_id        = PageTable.getIDByTitleNamespace(connect_ru, t1, PageNamespace.MAIN);
        source_nodes = new Article[1];
        source_nodes[0] = new Article();
        source_nodes[0].page_title  = t1;
        source_nodes[0].page_id     = t1_id;
    }

    protected void tearDown() throws Exception {
        connect.Close();
        connect_ru.Close();
    }


    // test with 
    // 1. empty blacklist that number of treated categories is zero.
    // 2. dump is enabled
    // parameters: root_set_size=2, inc=1, article=Джемини
    public void testPrintSynonyms_empty_blacklist_categories_ru() {
        System.out.println("PrintSynonyms");
        
        int     root_set_size, increment;
        String  article, article_fn;
        
        // Kleinberg default values
        root_set_size = 2; //200;  
        increment     = 1; //50;
        
        int categories_max_steps = 10;
        session.Init(connect_ru, null, categories_max_steps);
        session.randomPages(false);
        session.skipTitlesWithSpaces(true);
        session.dump = dump;
        
        Encodings e = connect_ru.enc;
        article = e.EncodeFromJava("Джемини");
        
        //article_fn = StringUtilRegular.encodeRussianToLatinitsa(article, Encodings.enc_java_default, Encodings.enc_int_default);
        //dump.connect = connect_ru;
        //dump.file_dot.setFileInHomeDir("graphviz", article_fn + ".dot", "UTF8",true);
        
        List<Article> nodes_list = new ArrayList<Article>();
        nodes_list.add(source_nodes[0]);
        
        session.dump.PrintSynonyms(session, nodes_list);
        
        assertEquals(11, session.category_black_list.getTotalCategoriesPassed());
    }
    
}
