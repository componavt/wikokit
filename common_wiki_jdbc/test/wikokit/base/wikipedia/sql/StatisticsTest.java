/*
 * StatisticsTest.java
 * JUnit based test
 */

package wikokit.base.wikipedia.sql;

import wikokit.base.wikipedia.sql.Statistics;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.language.LanguageType;
import junit.framework.*;

public class StatisticsTest extends TestCase {
    
    public Connect  connect, connect_simple;
    //Links           links;
    //Article[]       source_nodes;
    
    public StatisticsTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect = new Connect();
        connect.Open("localhost", "enwiki?useUnicode=true&characterEncoding=UTF-8", "javawiki", "",LanguageType.en);
        
        connect_simple = new Connect();
        connect_simple.Open(Connect.WP_HOST,Connect.WP_SIMPLE_DB,   Connect.WP_USER,    Connect.WP_PASS,LanguageType.simple);
        
        /*links   = new Links();
        
        source_nodes = new Article[2];
        source_nodes[0] = new Article();
        source_nodes[1] = new Article();
        source_nodes[0].cur_id = 18991;
        source_nodes[1].cur_id = 22233;
         */
    }

    protected void tearDown() throws Exception {
        connect.Close();
        connect_simple.Close();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(StatisticsTest.class);
        
        return suite;
    }

    
    public void testCount_ru() {
        System.out.println("testCount_ru");
        
        // page table
        //int page_size = Statistics.Count(connect_ru, "page");
        //assertTrue(156570 <= page_size);    // 120'700  50'460
        
        // links table
        //int cur_size = Statistics.Count(connect_ru, "pagelinks");
        //assertTrue(2495547 <= cur_size);    // 468'771
        
        // categorylinks table
        //cur_size = Statistics.Count(connect_ru, "categorylinks");
        //assertTrue(275625 <= cur_size);     // 60'426 
        
        // image table
        //cur_size = Statistics.Count(connect_ru, "image");
        //assertTrue(4727 <= cur_size);
        
        // imagelinks table
        //cur_size = Statistics.Count(connect_ru, "imagelinks");
        //assertTrue(21155 <= cur_size);
    }

    public void testCountArticles() {
        System.out.println("testCountArticles");
        
        int page_size = Statistics.CountArticles(connect_simple);
        assertTrue(27634 <= page_size);
    }
    
    public void testCountArticlesNonRedirects() {
        System.out.println("testCountArticlesNonRedirects");
        
        int page_size = Statistics.CountArticlesNonRedirects(connect_simple);
        assertTrue(19235 <= page_size);
    }     
             
    public void testCountCategories() {
        System.out.println("testCountArticles");
        
        int page_size = Statistics.CountCategories(connect_simple);
        assertTrue(5866 <= page_size);  // 7876
    }
    
}
