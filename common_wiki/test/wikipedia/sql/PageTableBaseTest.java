package wikipedia.sql;

import wikipedia.language.Encodings;
import wikipedia.language.LanguageType;
import junit.framework.*;

import wikipedia.util.*;

import java.sql.*;
import java.io.*;
import java.util.*;

public class PageTableBaseTest extends TestCase {

    public  Connect  ruwikt_conn;
    // connect, connect_simple, connect_ru,

    //public  SessionHolder    session;
    static String[] iwiki_example = {"ru:", "fa:وب جهان‌گستر","br:World Wide","lt:Žiniatinklis","lv:Globālais tīmeklis","th:เวิลด์ไวด์เว็บ","tr:Dünya Çapında Ağ"};

    static String   cat1,    art1,    art_redirect1;
    static int      cat1_id, art1_id, art_redirect1_id;
    
    public PageTableBaseTest(String testName) {
        super(testName);
    }
    
    /* "Робот", "Р РѕР±РѕС‚", "Ðîáîò", "Ð Ð¾Ð±Ð¾Ñ"
     cp1251:
        cp1251 Робот
        utf8 Р РѕР±РѕС‚
        latin1 їїїїї
     utf8
        cp1251 Робот
        utf8 Р РѕР±РѕС‚
        latin1 їїїїї
     latin1
        cp1251 Ðîáîò
        utf8 Ð Ð¾Ð±Ð¾Ñ
        latin1 ¿¿¿¿¿
     **/

    protected void setUp() throws Exception {
        //connect = new Connect();
        //connect.Open(Connect.WP_HOST, Connect.WP_DB, Connect.WP_USER, Connect.WP_PASS,LanguageType.en);
        
        //connect_ru = new Connect();
        //connect_ru.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,Connect.WP_RU_USER,Connect.WP_RU_PASS,LanguageType.ru); //Java:MySQL ISO8859_1:latin1

        ruwikt_conn = new Connect();
        ruwikt_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);

        /*connect_simple = new Connect();
        //connect_simple.Open(Connect.WP_HOST,Connect.WP_SIMPLE_DB,Connect.WP_USER,Connect.WP_PASS,LanguageType.simple);
        
        // simple WP
        cat1 = connect_simple.enc.EncodeFromJava("Folklore");
        art1 = connect_simple.enc.EncodeFromJava("Ghost_light");
        art_redirect1 = connect_simple.enc.EncodeFromJava("Doppelganger");
        
        cat1_id = PageTableBase.getIDByTitleNamespace(connect_simple, cat1, PageNamespace.CATEGORY); // 41712
        art1_id = PageTableBase.getIDByTitleNamespace(connect_simple, art1, PageNamespace.MAIN);     // 50387
        art_redirect1_id = PageTableBase.getIDByTitleNamespace(connect_simple, art_redirect1, PageNamespace.MAIN);
        */
        //session = new SessionHolder();
        //session.initObjects();
    }

    protected void tearDown() throws Exception {
        //connect.Close();
        //connect_ru.Close();
        //connect_simple.Close();
        ruwikt_conn.Close();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(PageTableBaseTest.class);
        
        return suite;
    }

/*
    public void testWildcardToDatabaseChars(){
        System.out.println("test_wildcardToDatabaseChars");
        String source, result, empty;

        StringUtil.escapeChars(null);

        empty   = "";
        result  = PageTableBase.convertWildcardToDatabaseChars(connect, empty);
        assertEquals(1, result.length()); // "%"

        // If the text does not contain wildcard characters then text .= '%'.
        source  =               "word";
        result  = PageTableBase.convertWildcardToDatabaseChars(connect, source);
        assertEquals(result,    "word%");

        // only one char is wildcard
        source  =               "first*second";
        result  = PageTableBase.convertWildcardToDatabaseChars(connect, source);
        assertEquals(result,    "first%second");

        // last char is wildcard (two wildchars)
        source  =               "first*second?";
        result  = PageTableBase.convertWildcardToDatabaseChars(connect, source);
        assertEquals(result,    "first%second_");

        // the char before last is wildcard
        source  =               "*first_secon?d";
        result  = PageTableBase.convertWildcardToDatabaseChars(connect, source);
        assertEquals(result,    "%first_secon_d");

        // empty request = %, i.e. any record
        source  =               "";
        result  = PageTableBase.convertWildcardToDatabaseChars(connect, source);
        assertEquals(result,    "%");

        // non ASCII (e.g. Cyrillic) then '?' should be doubled
        source  =               "я?";
        result  = PageTableBase.convertWildcardToDatabaseChars(connect, source);
        assertEquals(result,    "Ñ__");
    }*/

    /*
    public void testGetArticleText_simple() {
        System.out.println("getArticleText_simple");
        String s, title;
        
        assertTrue(null != connect_simple);
        
        title = connect_simple.enc.EncodeFromJava("Żagań");
        s = PageTableBase.getArticleText(connect_simple, title);
        assertTrue( null != s);
        assertTrue( s.length() > 12);
        assertEquals( "#REDIRECT [[", s.substring(0, 12));
    }

    public void testGetArticleText_simple_Momotaro() {
        System.out.println("getArticleText_simple_Momotaro");
        String s;
        
        // 1. Java -> page_title
        //s = "";  // page_title = "Momotarō";    
        s = connect_simple.enc.EncodeFromJava("Momotarō");
        
        // 2. id -> page_title
        //int id=68417;
        //s = PageTableBase.getTitleByID(session.connect, id);    
        
        s = PageTableBase.getArticleText(connect_simple, s);
        assertTrue (0 < s.length());
        String str = s.substring(0, 10);
        String should_be = "[[Image:Mo";
        assertEquals(str, should_be);
    }*/
    
    /*
    public void testSelectCurText_en() {
        System.out.println("testSelectCurText_en");
        String s;
        assertTrue(null != connect);
        s = PageTableBase.getArticleText(connect, "Gratitude");
        assertTrue( "{{otheruses}}", s.length() > 0);
        assertEquals( "{{otheruses}}", s.substring(0, 13));
    }
    public void testSelectCurText_ru() {
        System.out.println("testSelectCurText_ru");
        Encodings e = connect_ru.enc;
        
        //String s = PageTableBase.getArticleText(connect_ru, "Фут");
        
        //String s = Encodings.FromTo("Фут", session.enc.GetDBSource(), session.enc.GetUserDest());
        //String s = e.EncodeToUser("Фут");
        String s = "Фут";
        s = connect_ru.enc.EncodeFromJava(s);
        s = PageTableBase.getArticleText(connect_ru, s);
     */
        /*
            String id0 = PageTableBase.getArticleText(connect_ru,                  "Фут");
            String id1 = PageTableBase.getArticleText(connect_ru, Encodings.FromTo("Фут","UTF8","ISO8859_1"));
        String id2 = PageTableBase.getArticleText(connect_ru, Encodings.FromTo("Фут","ISO8859_1","UTF8"));   // + Debian
        String id3 = PageTableBase.getArticleText(connect_ru, Encodings.FromTo("Фут","Cp1251","UTF8"));      // + Mandriva
            String id4 = PageTableBase.getArticleText(connect_ru, Encodings.FromTo("Фут","UTF8","Cp1251"));
            String id5 = PageTableBase.getArticleText(connect_ru, Encodings.FromTo("Фут","Cp1251","ISO8859_1"));
            String id6 = PageTableBase.getArticleText(connect_ru, Encodings.FromTo("Фут","ISO8859_1","Cp1251"));
        */
        /*
        assertTrue (0 < s.length());
        String str = s.substring(0, 9);
        
        //String should_be = Encodings.FromTo("'''Фут'''", e.GetDB(), e.GetUser());
        String should_be = e.EncodeFromJava("'''Фут'''");
        
        assertEquals(str, should_be);
    }*/

    /**
     * Test of GetIDByTitleNamespace method, of class wikipedia.sql.PageTableBase.
     * Steps: id_source -> (title, namespace) -> id_result
     * 1) gets title by id_source
     * 2) gets id_result by title (as category or article by setting namespace parameter)
     * 3) assert id_source == id_result
     */ 
    /*public void testGetIDByTitle() {
        System.out.println("GetIDByTitle");
        
        int categories_max_steps = 99;
        session.Init(connect_ru, null, categories_max_steps);  // session.category_black_list.black_list_ru
        
        // assertEquals( 10563, PageTableBase.getIDByTitle(connect_ru,"Орбитальная станция")); // 10563 11553
        // SELECT cur_id, cur_namespace, cur_title FROM cur WHERE cur_text LIKE '%Space station%';
        
        // Number             0        1        2          3            4       5         6                      7         8       9         10      11              12          13       14              15         16 
        int[] id = {8668,    17111,   2142,      9829,        8541,   22248,    10639,    6550,  22976,     17345,  7125,          15421,       19276,   3455,           33921,     8100};
        
        //String[] titles   = {"Робот", "Рычаг", "Самолёт", "Фуникулёр", "Глаз", "Окуляр", "Орбитальная станция", "Плазма", "Фут", "Свёртка", "Ноты", "Предложение", "Преферанс", "Взрыв", "Самосознание", "Свобода", "Оптимизм"};
        //String[] titles ={"Презентация_(значения)", "424_до_н._э.", "Гегель", "Алексенко,_Владимир_Аврамович", "Архонт", "Гейгенс", null, "Южный_океан", "История_Португалии", "Абу-Грейб", "26",     "Изотон", "Duerer-paulus.jpg", "Патканов", "1384", "Двигатель_Ванкеля", "Население"};
        //String[]    titles = {"Робот", "Р РѕР±РѕС‚", "Ðîáîò", "Ð Ð¾Ð±Ð¾Ñ"};
        
        int id_null = PageTableBase.getIDByTitle(connect_ru, "This title is absent, I hope");
        assertEquals( id_null, 0);
        
        int id_redirect = PageTableBase.getIDByTitle(connect_ru, "1917");
        assert( 0 > id_redirect);
                
        for(int id_source : id) {
            String title = PageTableBase.getTitleByID(session.connect, id_source);
            PageNamespace ns  = PageTableBase.getNamespaceByID(session, id_source);
            assertFalse (-1 == ns);
            
            //int id_result = PageTableBase.getIDByTitleNamespace(connect_ru, session.enc.FromDBToUser(title), ns);
            int id_result = PageTableBase.getIDByTitleNamespace(connect_ru, title, ns);
            //int id_result = PageTableBase.getIDByTitleNamespace(connect_ru, Encodings.FromTo(title, "UTF8", "Cp1251"), ns);
            //+                            Encodings.FromTo(title, "UTF8", "ISO8859_1")); // Debian
            
            
            int id0 = PageTableBase.getIDByTitle(connect_ru,                  title);
            int id1 = PageTableBase.getIDByTitle(connect_ru, Encodings.FromTo(title,"UTF8","ISO8859_1"));
            int id2 = PageTableBase.getIDByTitle(connect_ru, Encodings.FromTo(title,"ISO8859_1","UTF8"));
            int id3 = PageTableBase.getIDByTitle(connect_ru, Encodings.FromTo(title,"Cp1251","UTF8"));
            int id4 = PageTableBase.getIDByTitle(connect_ru, Encodings.FromTo(title,"UTF8","Cp1251"));
            int id5 = PageTableBase.getIDByTitle(connect_ru, Encodings.FromTo(title,"Cp1251","ISO8859_1"));
            int id6 = PageTableBase.getIDByTitle(connect_ru, Encodings.FromTo(title,"ISO8859_1","Cp1251"));
            
            //assertEquals( id_source, id_result);
            assert(id_source == id_result || id_source == - id_result);
        }
    }*/
    
    
    /** iwiki titles should be skipped without interaction with database.
     *
     * check: SQLException (PageTableBase.java GetIDByTitleNamespace): 
     * sql='SELECT page_id FROM page WHERE page_namespace=0 AND page_title='He:××××ª'' 
     * Illegal mix of collations
     **/
    /*public void testGetIDByTitle_iwiki() {
        System.out.println("testGetIDByTitle_iwiki");
        
        for(String title : iwiki_example) {
            int id_result = PageTableBase.getIDByTitleNamespace(connect_ru, title, PageNamespace.MAIN);
            assertEquals(0, id_result);
        }
    }*/
    
    /** Checks treatment of titles with different symbols, e.g. apostrophe. */
    /*public void testGetIDByTitle_simple() {
        System.out.println("testGetIDByTitle_simple");
        int id; 
        
        String title= "Will_o'_the_wisp";
        id = PageTableBase.getIDByTitle(connect_simple, title);
        assertFalse(0 == id);
        
        String text = PageTableBase.getArticleText(connect_simple, title);
        assertTrue(text.length() > 0);
    }*/
    
    
    /**
     * Test of GetTitleByID method, of class wikipedia.sql.PageTableBase.
     * Steps: (title_source, namespace) -> id -> title_result
     * 1) gets id by title_source and namespace
     * 2) gets title_result by id
     * 3) assert title_source == title_result
     */
    /*public void testGetTitleByID() {
        System.out.println("GetTitleByID");
        // SELECT cur_id, cur_namespace, cur_title FROM cur WHERE cur_id=10484;
        
        //   Number                      0                         1                2          3                              4         5          6     7              8                     9            10        11        12                   13          14       15                  16
        //int[]                    id = {10484,                    21165,          2606,      11959,                          10332,    28044,     -1,   13054,         7852,                 28804,       21460,    8591,     19047,               24219,      4283,    42126,              9776};
        //String[]    should_be_titles ={"Презентация_(значения)", "424_до_н._э.", "Гегель", "Алексенко,_Владимир_Аврамович", "Архонт", "Гейгенс", null, "Южный_океан", "История_Португалии", "Абу-Грейб", "26",     "Изотон", "Duerer-paulus.jpg", "Патканов", "1384", "Двигатель_Ванкеля", "Население"};

        // Number                    0        1        2          3            4       5         6      7       8          9       10             11           12       13              14        15         16 
        //int[]             id = {8668,    17111,   2142,      9829,        8541,   22248,    -1,       10639,  6550,      22976,  17345,         7125,        15421,   19276,          3455,     33921,     8100};
        String[] titles_source = {"Робот", "Рычаг", "Самолёт", "Фуникулёр", "Глаз", "Окуляр", "Плазма", "Фут", "Свёртка", "Ноты", "Предложение", "Преферанс", "Взрыв", "Самосознание", "Свобода", "Оптимизм"};
        
        //String[]  should_be_titles ={"Робот", "Рычаг", "Самолёт", "Фуникулёр", "Глаз", "Окуляр", "Орбитальная станция", "Плазма", "Фут", "Свёртка", "Ноты", "Предложение", "Преферанс", "Взрыв", "Самосознание", "Свобода", "Оптимизм"};
        //Integer[]   id = new Integer[titles.length];
        Integer     i;
                
        String null_title = PageTableBase.getTitleByID(connect_ru, 0);
        assertEquals(null, null_title);
        
        //for(i=0; i<titles_source.length; i++){
        for(String t : titles_source) {
            //t = Encodings.FromTo(t, "Cp1251",    "UTF8");
            t = connect_ru.enc.EncodeFromJava(t);
            int id = PageTableBase.getIDByTitleNamespace(connect_ru, t, PageNamespace.MAIN);
            
            
            //int id1 = PageTableBase.getIDByTitleNamespace(session.connect, Encodings.FromTo(t, "UTF8",      "ISO8859_1"), PageNamespace.MAIN);
            //int id2 = PageTableBase.getIDByTitleNamespace(session.connect, Encodings.FromTo(t, "ISO8859_1", "UTF8"), PageNamespace.MAIN);
            //++ int id3 = PageTableBase.getIDByTitleNamespace(session.connect, Encodings.FromTo(t, "Cp1251",    "UTF8"), PageNamespace.MAIN);
            //int id4 = PageTableBase.getIDByTitleNamespace(session.connect, Encodings.FromTo(t, "UTF8",      "Cp1251"), PageNamespace.MAIN);
            //int id5 = PageTableBase.getIDByTitleNamespace(session.connect, Encodings.FromTo(t, "Cp1251",    "ISO8859_1"), PageNamespace.MAIN);
            //int id6 = PageTableBase.getIDByTitleNamespace(session.connect, Encodings.FromTo(t, "ISO8859_1", "Cp1251"), PageNamespace.MAIN);
            
            
            if(0 == id) {
                System.out.println("..pedia doesn't contain a page with title '" + t + "'");
            }
            assertFalse(0 == id);
            
            String title_result = PageTableBase.getTitleByID(connect_ru, id);
            //t = session.enc.FromDBToUser(t);
            
            //String s = title_result;
            //String s1 = Encodings.FromTo(s, "UTF8",     "ISO8859_1");
            //String s2 = Encodings.FromTo(s, "ISO8859_1","UTF8");
            //String s3 = Encodings.FromTo(s, "Cp1251",   "UTF8");
            //String s4 = Encodings.FromTo(s, "UTF8",     "Cp1251");
            //String s5 = Encodings.FromTo(s, "Cp1251",   "ISO8859_1");
            //String s6 = Encodings.FromTo(s, "ISO8859_1","Cp1251");
            
            //++                                          //"ISO8859_1","UTF8");
            //String s7 = session.enc.FromDBToUser(s);
            //String s8 = Encodings.FromTo(s, session.enc.GetDB(), session.enc.GetUser());
            
            assertEquals(t, title_result);
        }
    }*/

    /*
    public void testGetArticleTitleNotRedirectByID_simple() {
        System.out.println("GetArticleTitleNotRedirectByID_simple");
        String result;
        
        // 1. empty test
        result = PageTableBase.getArticleTitleNotRedirectByID(connect_simple, 0);
        assertEquals(null, result);
        
        // 2. valid test
        result = PageTableBase.getArticleTitleNotRedirectByID(connect_simple, art1_id);
        assertEquals(art1, result);
        
        // 3. non-valid (null result): redirect article
        result = PageTableBase.getArticleTitleNotRedirectByID(connect_simple, art_redirect1_id);
        assertEquals(null, result);
        
        // 4. non-valid (null result): category page
        result = PageTableBase.getArticleTitleNotRedirectByID(connect_simple, cat1_id);
        assertEquals(null, result);
    }
    
    
    public void testGetCategoryTitleByID_simple() {
        System.out.println("GetCategoryTitleByID_simple");
        String result;
        
        // 1. valid test
        result = PageTableBase.getCategoryTitleByID(connect_simple, cat1_id);
        assertEquals(cat1, result);
        
        // 2. non-valid (null result): redirect article
        result = PageTableBase.getCategoryTitleByID(connect_simple, art_redirect1_id);
        assertEquals(null, result);
        
        // 3. non-valid (null result): article page
        result = PageTableBase.getCategoryTitleByID(connect_simple, art1_id);
        assertEquals(null, result);
    }*/
 
    /*public void testGetNamespaceByID_en() {
        System.out.println("GetNamespaceByID_en");
        
        String title = "20th century"; // "hydrocarbon" - the name of article and category at the same time in Russian Wikipedia
        //title = Encodings.FromTo(title, "Cp1251",    "UTF8");
        title = connect.enc.EncodeFromJava(title);
        
        int category_id = PageTableBase.getCategoryIDByTitle (connect, title);
        int article_id = PageTableBase.getIDByTitleNamespace(connect, title, PageNamespace.MAIN);

        assertFalse(0 == category_id); // 635683 
        assertFalse(0 == article_id);  // 28763
        
        PageNamespace b_category = PageTableBase.getNamespaceByID (connect, category_id);
        PageNamespace b_article = PageTableBase.getNamespaceByID (connect, article_id);
        
        assertEquals(b_category, PageNamespace.CATEGORY);
        assertEquals(b_article, PageNamespace.MAIN);
    }*/
    
    /*
    public void testGetNamespaceByID_ru() {
        System.out.println("GetNamespaceByID_ru");
        
        String title = "Углеводороды"; // "hydrocarbon" - the name of article and category at the same time in Russian Wikipedia
        //title = Encodings.FromTo(title, "Cp1251",    "UTF8");
        title = connect_ru.enc.EncodeFromJava(title);
        
        int category_id = PageTableBase.getCategoryIDByTitle (connect_ru, title);
        int article_id = PageTableBase.getIDByTitleNamespace(connect_ru, title, PageNamespace.MAIN);

        assertFalse(0 == category_id);
        assertFalse(0 == article_id);
        
        PageNamespace b_category = PageTableBase.getNamespaceByID (connect_ru, category_id);
        PageNamespace b_article = PageTableBase.getNamespaceByID (connect_ru, article_id);
        
        assertEquals(b_category, PageNamespace.CATEGORY);
        assertEquals(b_article, PageNamespace.MAIN);
    }*/


    //
    public void testConvertToSafeStringEncodeToDBWunderscore() {
        System.out.println("convertToSafeStringEncodeToDBWunderscore");

        String empty = "";
        String result;

        result = PageTableBase.convertToSafeStringEncodeToDB(ruwikt_conn, empty);
        assertEquals(0, result.length());
    }
}