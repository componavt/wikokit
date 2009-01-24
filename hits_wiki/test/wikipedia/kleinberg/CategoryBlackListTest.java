/*
 * CategoryBlackListTest.java
 * JUnit based test
 */

package wikipedia.kleinberg;

import wikipedia.language.Encodings;
import junit.framework.*;

import wikipedia.sql.*;
import wikipedia.util.*;
import wikipedia.data.ArticleIdAndTitle;

import java.sql.*;
import java.util.*;

public class CategoryBlackListTest extends TestCase {
    
    public Connect          connect, connect_ru;
    public SessionHolder    session;
           int              categories_max_steps;
           
    static Map<String,Set<String>> m_out = new HashMap<String,Set<String>>();
    static Map<String,Set<String>> m_in  = new HashMap<String,Set<String>>();

    public CategoryBlackListTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect = new Connect();    
        connect.Open(Connect.WP_HOST, Connect.WP_DB, Connect.WP_USER, Connect.WP_PASS);
        
        connect_ru = new Connect();
        connect_ru.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,Connect.WP_RU_USER,Connect.WP_RU_PASS);
        
        session = new SessionHolder();
        session.initObjects();
        categories_max_steps = 99;
    }

    protected void tearDown() throws Exception {
        connect.Close();
        connect_ru.Close();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CategoryBlackListTest.class);
        
        return suite;
    }
    
    public void testGetCategoryUpIteratively_ru() {
        System.out.println("getCategoryUpIteratively_ru");
        int     n_limit, page_id;
        String  page_title, categories[];
        
        session.connect = connect_ru;
        Encodings e = session.connect.enc;
        
        page_title = e.EncodeFromJava("XX_век");  // "XX_век" has category "XX_век" (XX century)
        page_id = PageTable.getIDByTitle(connect_ru, page_title);
        n_limit = 1;
        
        session.category_black_list.setMaxSteps(n_limit);
        categories = session.category_black_list.getCategoryUpIteratively(page_id, null);

        assertEquals(1, categories.length);
        //assertEquals(0, categories[0].compareTo("XX_век"));
        
        //assertEquals(0, categories[0].compareTo(session.enc.FromDBToUser("XX_век")));
        assertEquals(0, categories[0].compareTo(e.EncodeFromJava("XX_век")));
        
        page_title = "1917_год";
        page_id = PageTable.getIDByTitle(connect_ru, page_title);
        n_limit = 20;
        
        session.category_black_list.setMaxSteps(n_limit);
        categories = session.category_black_list.getCategoryUpIteratively(page_id, null);
        
        if(null == categories || 11 >= categories.length) {
            System.out.println("\nDid you run the script maintenance/refreshLinks.php?\n");
        }
        assertTrue(11 < categories.length);
        
        
        // check: 
        // result: 1917 XX_век Века Календарь History Время Страны_и_народы Всё
        //      Календарь:  History | Время 
        //      History:    Всё | Страны_и_народы
        //      Время:      Всё
        //      Страны и народы: Всё
        
        session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        
        page_title = e.EncodeFromJava("Жданов,_Василий_Александрович"); // Russian artist
        page_id = PageTable.getIDByTitle(connect_ru, page_title);
        n_limit = 20;
        
        session.category_black_list.setMaxSteps(n_limit);
        categories = session.category_black_list.getCategoryUpIteratively(page_id, null);
        
        assertTrue(19 <=                // 10
                session.category_black_list.getTotalCategoriesPassed()); 
    }
    
    // 1917 -> 1917_год
    public void testGetCategoryUpIteratively_Redirect_ru() {
        System.out.println("testGetCategoryUpIteratively_Redirect_ru");
        int     n_limit, page_id;
        String  page_title, categories[];
        
        session.connect = connect_ru;
        session.skipTitlesWithSpaces(true);
        session.randomPages(false);
        Encodings e = session.connect.enc;
        
        page_title = "1917";
        page_id = PageTable.getIDByTitle(connect_ru, page_title);
        
        n_limit = 20;
        session.category_black_list.setMaxSteps(n_limit);
        
        categories = session.category_black_list.getCategoryUpIteratively(page_id, null);
        
        assertTrue(null != categories && 11 < categories.length);
    }
    
    
    /** Test GetCategoryUpIteratively for the case when parents categories are repeated.
     * Test session.category_nodes after the function calling.
     * E.g. Вырожденный газ -> Астрофизика -> Астрономия -> Космос | Естественные науки -> Науки -> ... -> 
     *                         Астрофизика -> Физика     ->          Естественные науки -> Науки | Природа -> ... -> 
     *      Вырожденный газ -> Астрофизика | Квантовые явления | Незавершённые статьи по физике
     *
     * E.g. Астрофизика ->  Физика | Астрономия
     *                      Физика      ->  Естественные науки ->   Науки | Природа
     *                      Астрономия  ->  Естественные науки | Космос
     *                                      Космос ->   Природа  ->  Всё
     *                                                  Науки -> Наука -> Всё
     * Example by JavE:
     
      Аккреция(article)
           |
       Астрофизика(category)
         .-'.
       .'    `.
    .-'        `.
Физика (8101)  Астрономия (8124)
     `.       .' ``-..__
 +--------------------+ ``--.._
 | Естественные науки(13791)  Космос (id=16935)
 +--------------------+   .---'
        .-'`--._      _.-'
    _.-'        `-..-'
 Науки (7935)  Природа (12522)
 `---.._          .'
 Наука (10553) .-'
     ``--.._ .'
          Всё (16350)
     
     */
    public void testGetCategoryUpIterativelyComplex_ru() {
        System.out.println("GetCategoryUpIterativelyComplex_ru");
        int     n_limit, page_id;
        String  page_title, categories[];
        Encodings e = session.connect.enc;
        
        // check: 
        // result: Вырожденный газ
        //      Календарь:  History | Время 
        //      History:    Всё | Страны_и_народы
        //      Время:      Всё
        //      Страны и народы: Всё
        
        session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        // Вырожденный_газ Астрофизика Аккреция
        page_title = e.EncodeFromJava("Аккреция");
        page_id = PageTable.getIDByTitle(connect_ru, page_title);
        n_limit = 99;
        
        session.category_black_list.setMaxSteps(n_limit);
        categories = session.category_black_list.getCategoryUpIteratively(page_id, null);
        
        // assert: 2 <= session.category_nodes ("Науки").links_in.size() 
        String category_name = e.EncodeFromJava("Науки");
        
        int category_id = PageTable.getCategoryIDByTitle(connect_ru, category_name);
        assertTrue(session.category_nodes.containsKey(category_id));
        Category c = session.category_nodes.get(category_id);
        assertTrue(2 == c.links_in.length);
        
        // There are two categories: "Физика" (8101) and "Астрономия" (8124) which are 
        // refer (link_in) to the category "Естественные науки" (page_id=13791)
        String cn = e.EncodeFromJava("Естественные_науки");
        int cn_id = PageTable.getCategoryIDByTitle(connect_ru, cn);
        assertTrue(session.category_nodes.containsKey(cn_id));
        Category c2 = session.category_nodes.get(cn_id);
        assertTrue(2 == c2.links_in.length);
    }
    
    public void testInBlackList_FirstLevelCategories_en () {
        System.out.println("testInBlackList_FirstLevelCategories_en");
        int     n_limit, page_id;
        String  page_title, categories[], result, latin1, category, category2;
        ArrayList<Integer> categories_sucrose, categories_rice;
        
        // English
        // "Sucrose" categories: Disaccharides|Sweeteners
        session.Init(connect, session.category_black_list.en, categories_max_steps);
        session.randomPages(false);
        session.skipTitlesWithSpaces(false);
        
        page_title = "Sucrose";
        latin1 = Encodings.FromTo(page_title, "UTF8", "ISO8859_1");
        page_id = PageTable.getIDByTitle(connect, latin1);
        
        List<String> titles_level_1_cats = new ArrayList<String>();
        result = session.category_black_list.inBlackList(page_id, titles_level_1_cats, session.source_article_id);
        int[] id_level_1_cats = Category.getIDByTitle(session.connect, titles_level_1_cats);
        
        assertEquals(2, id_level_1_cats.length);
        category = PageTable.getTitleByID(session.connect, id_level_1_cats[0]);
        category2 = PageTable.getTitleByID(session.connect, id_level_1_cats[1]);
        assertEquals(category, "Disaccharides");
        assertEquals(category2, "Sweeteners");
        
        
        // "Rice" has 10 categories:           Domesticated animals | Endangered species | Critically endangered species | Staple foods | Cereals | Grains | Grasses | Model organisms | Rice | Tropical agriculture
        // "Rice" has 7 live links categories: Domesticated animals | Endangered species | Critically endangered species |              | Cereals | Grains | Grasses | Model organisms |      |
        page_title = "Rice";
        latin1 = Encodings.FromTo(page_title, "UTF8", "ISO8859_1");
        page_id = PageTable.getIDByTitle(connect, latin1);
        
        titles_level_1_cats.clear();
        result = session.category_black_list.inBlackList(page_id, titles_level_1_cats, session.source_article_id);
        id_level_1_cats = Category.getIDByTitle(session.connect, titles_level_1_cats);
        
        assertTrue(10 <= id_level_1_cats.length); // 5 7 10
    }

    public void testInBlackList_FirstLevelCategories_ru () {
        System.out.println("testInBlackList_FirstLevelCategories_ru");
        int     n_limit, page_id;
        String  page_title, categories[], result, latin1, category, category2;
        ArrayList<Integer> first_level_categories;
        Encodings e = session.connect.enc;
        
        // Russian
        // "Домра" has categories:
        // "Щипковые музыкальные инструменты"
        // "Музыкальные инструменты народов России"
        List<String> domra_categories = new ArrayList<String>();
        domra_categories.add( e.EncodeFromJava("Музыкальные_инструменты_народов_России"));
        domra_categories.add( e.EncodeFromJava("Щипковые_музыкальные_инструменты") );
        
        session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        page_title = e.EncodeFromJava("Домра");
        page_id = PageTable.getIDByTitle(connect_ru, page_title);
        
        List<String> titles_level_1_cats = new ArrayList<String>();
        result = session.category_black_list.inBlackList(page_id, titles_level_1_cats, session.source_article_id);
        int[] id_level_1_cats = Category.getIDByTitle(session.connect, titles_level_1_cats);
       
        assertTrue(2 == id_level_1_cats.length);
        category = PageTable.getTitleByID(session.connect, id_level_1_cats[0]);
        assertEquals(category, "Музыкальные_инструменты_народов_России");
        
        category2 = PageTable.getTitleByID(session.connect, id_level_1_cats[1]);
        assertEquals(category2, "Щипковые_музыкальные_инструменты");
    }
    
    
    public void testInBlackList_en () {
        System.out.println("testInBlackList_en");
        int     n_limit, page_id;
        String  page_title, categories[], result, latin1;
        
        String category, article;
        Encodings e = session.connect.enc;
        
        // Let's "Centuries" will be in black-list categories, 
        // then it will be returned by inBlackList()
        //
        category  = "Centuries";        // Years  Centuries
        page_title = "20th_century";
        List<String> my_list = new ArrayList<String>();
        my_list.add(category);
        categories_max_steps = 99;
        session.Init(connect, my_list, categories_max_steps);
        session.randomPages(false);
        
        latin1 = Encodings.FromTo(page_title, "UTF8", "ISO8859_1");
        page_id = PageTable.getIDByTitle(connect, latin1);
        result = session.category_black_list.inBlackList(page_id, null, session.source_article_id);
        assertEquals(result, category);
        
        // 284 is the result of "depth-first search" and 5 - "breadth-first search"
        //assertTrue(284 == session.category_black_list.passed_steps || 5 == session.category_black_list.passed_steps);
    }
    
    public void testInBlackList_ru () {
        System.out.println("testInBlackList_ru");
        int     n_limit, page_id;
        String  page_title, categories[], result, latin1;
        Encodings e = session.connect.enc;

        session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
                
        page_title = e.EncodeFromJava("XX_век");
        page_id = PageTable.getIDByTitle(connect_ru, page_title);
        result = session.category_black_list.inBlackList(page_id, null, session.source_article_id);
        
        //assertEquals(result, e.FromDBToUser("Века"));
        assertEquals(result, e.EncodeFromJava("Века"));
        
        assertEquals(1, session.category_black_list.getPassedSteps());
        assertEquals(1, session.category_black_list.getTotalCategoriesPassed());
        
        result = session.category_black_list.inBlackList(-999, null, session.source_article_id);
        assertEquals(result, null);
        
        session.Init(connect_ru, null, categories_max_steps);
        result = session.category_black_list.inBlackList(page_id, null, session.source_article_id);
        assertEquals(result, null);

        session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        page_title = e.EncodeFromJava("Домра");
        page_id = PageTable.getIDByTitle(connect_ru, page_title);
        result = session.category_black_list.inBlackList(page_id, null, session.source_article_id);
        assertEquals(result, e.EncodeFromJava("Страны"));
    }
    
    /** Checks that redirect pages are filtered also.
        Redirect page itself does not have any categories.
     */
    public void testInBlackList_RedirectPage_ru () {
        System.out.println("testInBlackList_RedirectPage_ru");
        int     n_limit, page_id;
        String  page_title, categories[], result, latin1;
        Encodings e = connect_ru.enc;
        
        session.Init(connect_ru, session.category_black_list.ru, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        // test "2000" has category "2000 год" -> "XX век" -> "Века"
        page_title = e.EncodeFromJava("2000");
        page_id = PageTable.getIDByTitle(connect_ru, page_title);
        result = session.category_black_list.inBlackList(page_id, null, session.source_article_id);
        assertEquals(result, e.EncodeFromJava("Века"));
    }

    public void testDeleteUsingBlackList_String_ru () {
        System.out.println("test String[] DeleteUsingBlackList_ru (String[])");
        
        Encodings e = connect_ru.enc;
        
        List<String> local_black_list_ru = new ArrayList<String>();
        local_black_list_ru.add( e.EncodeFromJava("Века")   );
        local_black_list_ru.add( e.EncodeFromJava("Страны") );
        
        //      session.category_black_list.ru
        session.Init(connect_ru, local_black_list_ru, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        String[] titles_with_blacklist = {"XX_век", "Домра", "Гитара", "Контрабас"};
        
        ArticleIdAndTitle[] categories_with_blacklist = ArticleIdAndTitle.createByTitle (session.connect, m_out, m_in, titles_with_blacklist);
        ArticleIdAndTitle[] cleaned_categories = session.category_black_list.DeleteUsingBlackList(categories_with_blacklist);
        
        String[] cleaned_categories_str = new String[cleaned_categories.length];
        int i = 0;
        for(ArticleIdAndTitle c:cleaned_categories) {
            cleaned_categories_str[i++] = c.title;
        }
        
        // "Гитара", "Контрабас" remained, "XX_век" and "Домра" were removed
        //assertTrue(cleaned_categories[0] == id[1] || cleaned_categories[0] == id[2]); 
        assertTrue(2 == cleaned_categories.length);
                
        assertTrue(StringUtil.containsIgnoreCase(cleaned_categories_str, "Гитара"));
        assertTrue(StringUtil.containsIgnoreCase(cleaned_categories_str, "Контрабас"));
    }
    
    
    public void testDeleteUsingBlackList_String_Redirect_ru () {
        System.out.println("test String[] DeleteUsingBlackList_ru (String[]) with redirect page");
        Encodings e = connect_ru.enc;
        
        List<String> local_black_list_ru = new ArrayList<String>();
        local_black_list_ru.add( e.EncodeFromJava("Века")   );
        local_black_list_ru.add( e.EncodeFromJava("Страны") );
        
        //      session.category_black_list.ru
        session.Init(connect_ru, local_black_list_ru, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        String[] titles_with_blacklist = {"1917", "Броузер"};
        ArticleIdAndTitle[] categories_with_blacklist = ArticleIdAndTitle.createByTitle (session.connect, m_out, m_in, titles_with_blacklist);
        ArticleIdAndTitle[] cleaned_categories = session.category_black_list.DeleteUsingBlackList(categories_with_blacklist);
        
        //String[] cleaned_categories_str= (String[])cleaned_categories.title.toArray(new String[0]);
        List<String> l = ArticleIdAndTitle.getTitles (cleaned_categories);
        String[] cleaned_categories_str= (String[])l.toArray(new String[0]);
        
        // "Броузер" remained, "" were removed
        assertEquals(cleaned_categories_str.length, 1);
        assertTrue(StringUtil.containsIgnoreCase(cleaned_categories_str, "Броузер"));
    }
    
    
    public void testDeleteUsingBlackList_ru () {
        System.out.println("testDeleteUsingBlackList_ru");
        Encodings e = connect_ru.enc;
        
        List<String> local_black_list_ru = new ArrayList<String>();
        //local_black_list_ru.add( Encodings.FromTo("Века", "UTF8", "ISO8859_1"));
        //local_black_list_ru.add( Encodings.FromTo("Страны", "UTF8", "ISO8859_1"));
        local_black_list_ru.add( e.EncodeFromJava("Века")   );
        local_black_list_ru.add( e.EncodeFromJava("Страны") );
        
        //      session.category_black_list.ru
        session.Init(connect_ru, local_black_list_ru, categories_max_steps);
        session.skipTitlesWithSpaces(false);
        session.randomPages(false);
        
        String[] titles = {"XX_век", "Домра", "Гитара"};
        Article[] articles = new Article[3];
        
        for(int i=0; i<titles.length; i++) {
            Article a = new Article();
            a.page_title = e.EncodeFromJava( titles[i] );
            a.page_id = PageTable.getIDByTitle(connect_ru, a.page_title);
            articles[i] = a;
        }
        
        ArticleIdAndTitle[] aid_with_blacklist = ArticleIdAndTitle.create( articles );
        boolean b_rand = false;
        ArticleIdAndTitle[] cleaned_categories = session.category_black_list.DeleteUsingBlackList(b_rand, aid_with_blacklist, -1);
        assertEquals(cleaned_categories.length, 1);
        
        // "Гитара" remained, "XX_век" and "Домра" were removed
        //assertTrue(cleaned_categories[0] == id[1] || cleaned_categories[0] == id[2]); 
        assertTrue(cleaned_categories[0].id == articles[2].page_id);
        
        cleaned_categories = session.category_black_list.DeleteUsingBlackList(b_rand, aid_with_blacklist, 1);
        assertEquals(cleaned_categories.length, 1);
    }
    
}
