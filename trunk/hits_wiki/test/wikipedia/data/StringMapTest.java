/*
 * StringMapTest.java
 * JUnit based test
 */

package wikipedia.data;

import wikipedia.sql.Connect;
import wikipedia.sql.PageTable;
import wikipedia.sql.PageNamespace;
import wikipedia.sql.Links;
import wikipedia.kleinberg.SessionHolder;

import junit.framework.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;


public class StringMapTest extends TestCase {

    public Connect  connect, connect_ru;
    
    static String t1,    t_redirect,    t2;
    static int    t1_id, t_redirect_id, t2_id;
    static String s1,    s_redirect,    s2,    s1b,     s_redirect2,    s3;
    static int    s1_id, s_redirect_id, s2_id, s1b_id,  s_redirect2_id, s3_id;
    
    static long    t_start, t_end;
    static float   t_work;
    
    static Map<String,Set<String>> m_out = new HashMap<String,Set<String>>();
    static Map<String,Set<String>> m_in  = new HashMap<String,Set<String>>();
    
    public StringMapTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect = new Connect();
        connect.Open(Connect.WP_HOST,       Connect.WP_DB,    Connect.WP_USER,    Connect.WP_PASS);
        
        connect_ru = new Connect();
        connect_ru.Open(Connect.WP_RU_HOST, Connect.WP_RU_DB, Connect.WP_RU_USER, Connect.WP_RU_PASS);
        
        
        // Redirects: t1 -> t_redirect -> t2 
        // Джемини  -> MIT -> Массачусетсский_технологический_институт
        // Польская_Википедия -> Бот_(программа) -> Робот_(программа)
        //            CAPTCHA ->             Bot -> Робот_(программа)
        t1          = connect.enc.EncodeFromJava("Джемини");
        t_redirect  = connect.enc.EncodeFromJava("MIT");
        t2          = connect.enc.EncodeFromJava("Массачусетсский_технологический_институт");
        
        s1          = connect.enc.EncodeFromJava("Польская_Википедия");
        s_redirect  = connect.enc.EncodeFromJava("Бот_(программа)");
        
        s1b         = connect.enc.EncodeFromJava("CAPTCHA");
        s_redirect2 = connect.enc.EncodeFromJava("Bot");
        
        s2          = connect.enc.EncodeFromJava("Робот_(программа)");
        s3          = connect.enc.EncodeFromJava("Википедия");
        
        m_out.clear(); m_in.clear();
        Links.addTitlesToMaps(t1, t_redirect, m_out, m_in);
        Links.addTitlesToMaps(s1, s_redirect, m_out, m_in);
        Links.addTitlesToMaps(s1b,s_redirect2, m_out, m_in);
        
        t1_id         = PageTable.getIDByTitleNamespace(connect_ru, t1, PageNamespace.MAIN);
        t_redirect_id = PageTable.getIDByTitleNamespace(connect_ru, t_redirect, PageNamespace.MAIN);
        t2_id         = PageTable.getIDByTitleNamespace(connect_ru, t2, PageNamespace.MAIN);
        
        s1_id         = PageTable.getIDByTitleNamespace(connect_ru, s1, PageNamespace.MAIN);
        s_redirect_id = PageTable.getIDByTitleNamespace(connect_ru, s_redirect, PageNamespace.MAIN);
        s1b_id        = PageTable.getIDByTitleNamespace(connect_ru, s1b,PageNamespace.MAIN);
        s_redirect2_id= PageTable.getIDByTitleNamespace(connect_ru, s_redirect2,PageNamespace.MAIN);
        s2_id         = PageTable.getIDByTitleNamespace(connect_ru, s2, PageNamespace.MAIN);
        s3_id         = PageTable.getIDByTitleNamespace(connect_ru, s3, PageNamespace.MAIN);
    }

    protected void tearDown() throws Exception {
        connect.Close();
        connect_ru.Close();
    }
    
    /** Fills maps m_in and m_out by data from 1. ArticleIdAndTitle, 
     * 2. map from identifier (to) pointed to identifiers (from). */ 
    /*
    public static void fill_m_in_m_out (Map<String,Set<String>> m_out, Map<String,Set<String>> m_in,
                                        ArticleIdAndTitle[] aid, 
                                        Map<Integer, List<Integer>> m_id_to__id_from)*/
    public void testFill_m_in_m_out() {
        System.out.println("fill_m_in_m_out");
        
        String w1, w2, w3;
        int id1, id2, id3;
        w1 = "word1";       id1 = 1;
        w2 = "word2";       id2 = 2;
        w3 = "words3";      id3 = 3;
        
        ArticleIdAndTitle[] aid = new ArticleIdAndTitle[2];
        aid[0] = new ArticleIdAndTitle (id1, w1);
        aid[1] = new ArticleIdAndTitle (id2, w2);
        
        ArticleIdAndTitle[] addon = new ArticleIdAndTitle[1];
        addon[0] = new ArticleIdAndTitle (id3, w3);
        
        // w1      ->      w2
        // w1  ->  w3  ->  w2
        Map<Integer, List<Integer>> m_id_to__id_from = new HashMap<Integer, List<Integer>> ();
        
        // empty test
        m_out.clear(); m_in.clear();
        StringMap.fill_m_in_m_out(m_out, m_in, 
                        aid, addon, 
                        m_id_to__id_from);
        
        assertEquals(0, m_out.size());
        assertEquals(0, m_in.size());
        
        // test:        w3 <- w1
        List<Integer>   id3__id_from = new ArrayList<Integer>();
                        id3__id_from.add(id1);
        m_id_to__id_from.put(id3, id3__id_from);
        m_out.clear(); m_in.clear();
        StringMap.fill_m_in_m_out(m_out, m_in, aid, addon, m_id_to__id_from);
        
        assertEquals(1, m_out.size());
        assertEquals(1, m_in.size());
        
        // test:        w3 <- w1, 
        //              w2 <- [w1, w3]
        List<Integer>   id2__id_from = new ArrayList<Integer>();
                        id2__id_from.add(id1);
                        id2__id_from.add(id3);
        m_id_to__id_from.put(id3, id3__id_from);
        m_id_to__id_from.put(id2, id2__id_from);
        m_out.clear(); m_in.clear();
        StringMap.fill_m_in_m_out(m_out, m_in, aid, addon, m_id_to__id_from);
        
        // w1      ->      w2
        // w1  ->  w3  ->  w2
        assertEquals(2, m_out.size());
        assertEquals(2, m_in.size());
    }
    
    public void testSkipTitles() {
        System.out.println("skipTitles");
        
        SessionHolder   session;        
        session = new SessionHolder();
        session.initObjects();
        //int categories_max_steps = 99;
        //session.Init(connect_ru, null, categories_max_steps);
        
        String w1, w2, w3;
        w1 = "word1";
        w2 = "word2";
        w3 = "many_words_with_spaces";
        
        m_out.clear(); m_in.clear();
        Links.addTitlesToMaps(w1, w2, m_out, m_in);
        Links.addTitlesToMaps(w1, w3, m_out, m_in);
        Links.addTitlesToMaps(w3, w2, m_out, m_in);
        
        // empty test
        session.skipTitlesWithSpaces(false);
        StringMap.skipTitles(session, m_out, m_in);
        
        assertEquals(2, m_out.size());
        assertEquals(2, m_in.size());
        
        // test: skip w3
        session.skipTitlesWithSpaces(true);
        StringMap.skipTitles(session, m_out, m_in);
        
        assertEquals(1, m_out.size());
        assertEquals(1, m_in.size());
        
        assertTrue(session.removed_articles.hasTitle(w3));
        assertFalse(session.removed_articles.hasTitle(w1));
        assertFalse(session.removed_articles.hasTitle(w2));
    }
    
    /** Removes the string from maps. */ 
    //public static void removeString (String s, Map<String,Set<String>> m_out, Map<String,Set<String>> m_in)
    public void testRemoveString() {
        System.out.println("removeString");
        
        String w1, w2, w3;
        w1 = "word1";
        w2 = "word2";
        w3 = "word3333";
        
        m_out.clear(); m_in.clear();
        Links.addTitlesToMaps(w1, w2, m_out, m_in);
        Links.addTitlesToMaps(w1, w3, m_out, m_in);
        Links.addTitlesToMaps(w3, w2, m_out, m_in);
        
        // empty test
        StringMap.removeString("string which is absent in maps", m_out, m_in);
        assertEquals(2, m_out.size());
        assertEquals(2, m_in.size());
        
        // test: remove w3
        StringMap.removeString(w3, m_out, m_in);
        assertEquals(1, m_out.size());
        assertEquals(1, m_in.size());
        /*
        // test speed 2
        t_start = System.currentTimeMillis();
        
        String text = "text";
        for(int i=0; i<1000000; i++) {
            StringMap.removeString(w3, m_out, m_in);
        }
        
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        
        System.out.println("removeString() total time: " + t_work + "sec.");
        */
    }
    
    public void testReplaceTitleInMaps_one_link_ru() {
        System.out.println("replaceTitleInMaps_one_link_ru");
        
        m_out.clear(); m_in.clear();
        Links.addTitlesToMaps(t1, t_redirect, m_out, m_in);
        
        // replace t_redirect by t2
        StringMap.replaceTitleInMaps (t_redirect, t2, m_out, m_in);
        
        assertEquals(1, m_out.size());
        assertEquals(1, m_in.size());
        assertEquals(1, m_out.get(t1).size());
        assertTrue(m_in.containsKey(t2));
        assertEquals(1, m_in. get(t2).size());
        assertTrue(m_in.containsKey(t2));
        assertTrue(m_out.get(t1).contains( t2 ));
        assertTrue(              m_in.get( t2 ).contains(t1));
    }
    
    public void testReplaceTitleInMaps_two_links_ru() {
        System.out.println("replaceTitleInMaps_two_links_ru");
        
        m_out.clear(); m_in.clear();
        
        Links.addTitlesToMaps(s1, s_redirect,  m_out, m_in);
        Links.addTitlesToMaps(s1b,s_redirect2, m_out, m_in);
        
        // Facts : 
        // s1                    s_redirect         s2
        // Польская_Википедия -> Бот_(программа) -> Робот_(программа)
        // s1b                   s_redirect2        s2
        // CAPTCHA            -> Bot             -> Робот_(программа)
        
        // Source: Польская_Википедия -> Бот_(программа)
        //         CAPTCHA            -> Bot
        
        // Result: Польская_Википедия -> Робот_(программа)
        //         CAPTCHA            -> Робот_(программа)
        
        // replace t_redirect by t2
        StringMap.replaceTitleInMaps (s_redirect,  s2, m_out, m_in);
        StringMap.replaceTitleInMaps (s_redirect2, s2, m_out, m_in);
        
        assertEquals(2, m_out.size());
        assertEquals(1, m_in.size());
        assertEquals(1, m_out.get(s1).size());
        assertEquals(1, m_out.get(s1b).size());
        assertTrue(m_in.containsKey(s2));
        assertEquals(2, m_in. get(s2).size());
        
        assertTrue(m_out.get(s1).contains( s2 ));
        assertTrue(              m_in.get( s2 ).contains(s1));
        
        assertTrue(m_out.get(s1b).contains( s2 ));
        assertTrue(               m_in.get( s2 ).contains(s1b));
    }
    
    public void testReplaceTitleInMaps_remove_redirect_links_ru() {
        System.out.println("replaceTitleInMaps_remove_redirect_links_ru");
        
        // test with 1 link
        m_out.clear(); m_in.clear();
        Links.addTitlesToMaps(s_redirect, s2, m_out, m_in);
        
        // Facts (source) : 
        //          s_redirect         s2
        //          Бот_(программа) -> Робот_(программа)
        //
        // Replace Бот_(программа) by Робот_(программа)
        // Result: nothing
        
        // replace s_redirect by s2
        StringMap.replaceTitleInMaps (s_redirect,  s2, m_out, m_in);
        assertEquals(0, m_out.size());
        assertEquals(0, m_in.size());
        
        // test with 2 links
        m_out.clear(); m_in.clear();
        Links.addTitlesToMaps(s_redirect,  s2, m_out, m_in);
        Links.addTitlesToMaps(s_redirect2, s2, m_out, m_in);
        
        // Facts (source) : 
        //          s_redirect         s2
        //          Бот_(программа) -> Робот_(программа)
        //          s_redirect2        s2
        //          Bot             -> Робот_(программа)
        //
        // Replace Бот_(программа) by Робот_(программа)
        //         Bot             by Робот_(программа)
        // Result: nothing
        
        // replace s_redirect by s2
        StringMap.replaceTitleInMaps (s_redirect,  s2, m_out, m_in);
        StringMap.replaceTitleInMaps (s_redirect2,  s2, m_out, m_in);
        
        assertEquals(0, m_out.size());
        assertEquals(0, m_in.size());
    }
    
    public void testReplaceTitleInMaps_resolve_redirect_links_ru() {
        System.out.println("replaceTitleInMaps_resolve_redirect_links_ru");
        
        m_out.clear(); m_in.clear();
        Links.addTitlesToMaps(s1, s_redirect, m_out, m_in);
        Links.addTitlesToMaps(s_redirect, s2, m_out, m_in);
        Links.addTitlesToMaps(s1,         s3, m_out, m_in);
        
        // Facts (source) : 
        // s1                    s_redirect         s2
        // Польская_Википедия -> Бот_(программа) -> Робот_(программа)
        // Польская_Википедия -> Википедия (s3)
        //
        // Replace s_redirect by s2
        // Result: s1 -> s2, s1 -> s3
        
        // replace s_redirect by s2
        StringMap.replaceTitleInMaps (s_redirect,  s2, m_out, m_in);
        assertEquals(1, m_out.size());
        assertEquals(2, m_in.size());
        
        assertTrue(m_out.containsKey(s1));
        assertTrue(m_in. containsKey(s2));
        assertTrue(m_in. containsKey(s3));
        
        assertEquals(2, m_out.get(s1).size());
        assertEquals(1, m_in.get(s2).size());
        assertEquals(1, m_in.get(s3).size());
        
        assertTrue(m_out.get(s1).contains( s2 ));
        assertTrue(              m_in.get( s2 ).contains(s1));
        assertTrue(m_out.get(s1).contains( s3 ));
    }
}
