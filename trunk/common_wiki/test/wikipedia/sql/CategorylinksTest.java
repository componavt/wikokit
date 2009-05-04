/*
 * CategorylinksTest.java
 * JUnit based test
 */

package wikipedia.sql;

//import wikipedia.kleinberg.SessionHolder;
import wikipedia.language.Encodings;
import wikipedia.language.LanguageType;
import wikipedia.util.*;

import junit.framework.*;
import java.sql.*;
import java.util.*;


public class CategorylinksTest extends TestCase {
    
    public Connect          connect, connect_ru, connect_simple;
    
    static String   cat1,    art1,    subcategory1;
    static int      cat1_id, art1_id, subcategory1_id;
    
    public CategorylinksTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect_simple = new Connect();
        connect_simple.Open(Connect.WP_HOST,Connect.WP_SIMPLE_DB,   Connect.WP_USER,    Connect.WP_PASS, LanguageType.simple);
        
        connect = new Connect();
        connect.Open(Connect.WP_HOST, Connect.WP_DB, Connect.WP_USER, Connect.WP_PASS, LanguageType.en);
        
        connect_ru = new Connect();
        connect_ru.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,Connect.WP_RU_USER,Connect.WP_RU_PASS, LanguageType.ru);
        
        // simple WP
        cat1 = connect_simple.enc.EncodeFromJava("Folklore");
        art1 = connect_simple.enc.EncodeFromJava("Ghost_light");
        subcategory1 = connect_simple.enc.EncodeFromJava("Superstitions");
        
        cat1_id = PageTableBase.getIDByTitleNamespace(connect_simple, cat1, PageNamespace.CATEGORY); // 41712
        art1_id = PageTableBase.getIDByTitleNamespace(connect_simple, art1, PageNamespace.MAIN);     // 50387
        subcategory1_id = PageTableBase.getIDByTitleNamespace(connect_simple, subcategory1, PageNamespace.CATEGORY);
    }

    protected void tearDown() throws Exception {
        connect_simple.Close();
        connect.Close();
        connect_ru.Close();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CategorylinksTest.class);
        
        return suite;
    }

    /** Assert (The table categorylinks should not be empty.)
     */ 
    public void testCountCategoryLinksEn () {
        int links = Categorylinks.countCategoryLinks (connect);
        assertTrue (links >= 542368); // 542368
    }
    
    public void testCountCategoryLinksRu () {
        int links_ru = Categorylinks.countCategoryLinks (connect_ru);
        assertTrue (links_ru >= 218694);  // 218694 // 37000
    }
    
    /** Assert: category "Folklore" has the article "Ghost_light" in Simple WP. */
    public void testGetArticlesIDSubcategoryIDByCategoryTitle_simple() {
        System.out.println("testGetArticlesIDSubcategoryIDByCategoryTitle_simple");
        
        String category_title = "Folklore";
        List<Integer> res = Categorylinks.getArticlesIDSubcategoryIDByCategoryTitle(connect_simple,
                                            category_title);
        assertTrue(null != res);
        assertTrue(  0  <  res.size());
        
        boolean b_contains_article      = false;
        boolean b_contains_subcategory  = false;
        for(int id:res) {
            String s = PageTableBase.getTitleByID(connect_simple, id);
            if(s.equalsIgnoreCase(art1)) {
                b_contains_article = true;
            }
            if(s.equalsIgnoreCase(subcategory1)) {
                b_contains_subcategory = true;
            }
        }
        assertTrue(b_contains_article);
        assertTrue(b_contains_subcategory);        
    }
    
    /** Assert: category "Астрономия" has the article "Спутник" in Russian WP. */
    public void testGetArticlesIDSubcategoryIDByCategoryTitle_ru() {
        System.out.println("testGetArticlesIDSubcategoryIDByCategoryTitle_ru");
        
        String article_title = connect_ru.enc.EncodeFromJava("Спутник");
        String category_title = connect_ru.enc.EncodeFromJava("Астрономия");
        
        List<Integer> res = Categorylinks.getArticlesIDSubcategoryIDByCategoryTitle(connect_ru,
                                                        category_title);
        assertTrue(null != res);
        assertTrue(  0  <  res.size());
        
        boolean b = false;
        for(int id:res) {
            String s = PageTableBase.getTitleByID(connect_ru, id);
            if(s.equalsIgnoreCase(article_title)) {
                b = true;
                break;
            }
        }
        assertTrue(b);
    }
    
    /** Test of GetCategoryTitleByArticleID method, of class wikipedia.Categorylinks.
     * SQL: SELECT cl_to FROM categorylinks WHERE cl_from = 14946;  // cur_id of "Контрабас" (page_id=12097)
     *
     * Table categorylinks
     *          cl_from     cl_to                   SQL
     *          12097       Питание                 SELECT cl_to FROM categorylinks WHERE cl_from = 12097;
     *          14946       Смычковые_инструменты   SELECT cl_to FROM categorylinks WHERE cl_from = 14946;
     *          22614       Поэты_Азербайджана | Cleanup  SELECT cl_to FROM categorylinks WHERE cl_from = 22614;
     *
     * Table page
     *          page_id     page_title              SQL
     *          12097       Контрабас               SELECT page_id FROM page WHERE page_title = "Контрабас";
     *          22614       Смычковые_инструменты   SELECT page_id FROM page WHERE page_title = "Смычковые_инструменты";
     */
    public void testGetCategory_ru() {
        System.out.println("testGetCategory_ru");
        int         page_id, i, j;
        String[]    page_title          = {"Контрабас",   "1917_год",   "Литература"};
        String[][]  should_be_category = 
        {{"Контрабас", "Незавершённые_статьи_о_музыке"}, {"1917_год"}, {"Литература", "Незавершённые_статьи_о_литературе"}};
        
        int categories_max_steps = 99;
        Encodings e = connect_ru.enc;
        
        for(i=0; i<page_title.length; i++) {
            page_id = connect_ru.page_table.getIDByTitle(connect_ru, 
                    e.EncodeFromJava(page_title[i]));
                    //String latin1 = Encodings.UTF8ToLatin1(page_title[i]);
                    //page_id = connect_ru.page_table.GetIDByTitle(connect_ru, latin1);
            
            String[] categories = Categorylinks.GetCategoryTitleByArticleID(connect_ru, page_id);
            assertFalse (null == categories);
            for (j=0; j<categories.length; j++) {
                assert(0==categories[j].compareTo (should_be_category[i][j]));
            }
        }
        
        // english  //Directed_acyclic_graph
        // session.connect = connect;
    }
    
    /*
    public void testgetFirstLevel_ru() {
        System.out.println("testgetFirstLevel_ru");
        int     n_limit, page_id;
        String  page_title, categories[], result, latin1, category, category2;
        List<Integer> first_level_categories;
        Encodings e = connect.enc;
        
        // Russian
        // "Домра" has categories:
        // "Щипковые музыкальные инструменты"
        // "Музыкальные инструменты народов России"
        List<String> domra_categories = new ArrayList<String>();
        domra_categories.add( e.EncodeFromJava("Музыкальные_инструменты_народов_России"));
        domra_categories.add( e.EncodeFromJava("Щипковые_музыкальные_инструменты") );
        
        page_title = e.EncodeFromJava("Домра");
        page_id = PageTableBase.getIDByTitle(connect_ru, page_title);
        first_level_categories  = Categorylinks.getFirstLevel(connect_ru, page_id);
       
        assertTrue(2 == first_level_categories.size());
        category = PageTableBase.getTitleByID(connect_ru, first_level_categories.get(0));
        assertTrue(domra_categories.contains(category));
        category = PageTableBase.getTitleByID(connect_ru, first_level_categories.get(1));
        assertTrue(domra_categories.contains(category));
    }*/
    
}
