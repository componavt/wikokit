
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

public class TPageTest {

    public Connect   ruwikt_parsed_conn, ruwikt_sqlite;

    public TPageTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);

        // ruwikt_parsed_conn_sqlite
        //ruwikt_sqlite = new Connect();
        //ruwikt_sqlite.OpenSQLite(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }

    @Test
    public void testInsert() {
        System.out.println("insert_ru");
        String page_title;
        Connect conn = ruwikt_parsed_conn;

        //page_title = "test_тыблоко";
        page_title = ruwikt_parsed_conn.enc.EncodeFromJava("test_tybloko");
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;
        
        TPage p = null;
        p = TPage.get(conn, page_title);
        if(null != p) {
            TPage.delete(conn, page_title);
        }

        TPage p0 = TPage.insert(conn, page_title, word_count, wiki_link_count, 
                                is_in_wiktionary, redirect_target);
        assertTrue(null != p0);
        p = TPage.get(conn, page_title);
        assertTrue(null != p);
        assertEquals(p0.getID(), p.getID());

        assertTrue(p != null);
        assertTrue(p.getID() > 0);
        assertEquals(p.getWordCount(),      word_count);
        assertEquals(p.getWikiLinkCount(),  wiki_link_count);
        assertEquals(p.isInWiktionary(),    is_in_wiktionary);
        
        // delete temporary DB record
        TPage.delete(conn, page_title);
        
        p = TPage.get(conn, page_title);
        assertTrue(p == null);
    }

    //TPage  (Connect connect,int id) {
    @Test
    public void testGetByID() {
        System.out.println("getByID_ru");
        String page_title;
        Connect conn = ruwikt_parsed_conn;
        
        page_title = ruwikt_parsed_conn.enc.EncodeFromJava("test_tybloko");
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;
        
        TPage p = null;
        p = TPage.get(conn, page_title);
        if(null != p) {
            TPage.delete(conn, page_title);
        }
        
        TPage.insert(conn, page_title, word_count, wiki_link_count, 
                     is_in_wiktionary, redirect_target);
        p = TPage.get(conn, page_title);
        
        assertTrue(p != null);
        assertTrue(p.getID() > 0);
        
        TPage p2 = TPage.getByID(conn, p.getID());
        assertTrue(p2 != null);
        assertEquals(p.getPageTitle(), p2.getPageTitle());
        
        // delete temporary DB record
        TPage.delete(conn, page_title);
        
        p = TPage.get(conn, page_title);
        assertTrue(p == null);
    }

    @Test // tests Insert() Get() and GetByID() with redirects
    public void testRedirect() {
        System.out.println("redirect_ru");
        String page_title;
        Connect conn = ruwikt_parsed_conn;

        page_title = ruwikt_parsed_conn.enc.EncodeFromJava("test_neletnwi");
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = ruwikt_parsed_conn.enc.EncodeFromJava("test_нелётный");

        TPage p = null;
        p = TPage.get(conn, page_title);
        if(null != p) {
            TPage.delete(conn, page_title);
        }
        
        TPage.insert(conn, page_title, word_count, wiki_link_count,
                     is_in_wiktionary, redirect_target);
        p = TPage.get(conn, page_title);

        assertTrue(p != null);
        assertTrue(p.getID() > 0);

        TPage p2 = TPage.getByID(conn, p.getID());
        assertTrue(p2 != null);
        assertEquals(p.getPageTitle(), p2.getPageTitle());

        // test the valid redirect
        assertTrue(p2.isRedirect());
        String result_redirect = p2.getRedirect();
        assertTrue(result_redirect.equalsIgnoreCase(redirect_target));
        
        // delete temporary DB record
        TPage.delete(conn, page_title);

        p = TPage.get(conn, page_title);
        assertTrue(p == null);
    }
    
    @Test
    public void test_getByPrefix() {
        System.out.println("getByPrefix");
        int limit;
        String prefix;
        Connect conn = ruwikt_parsed_conn;

        String word1 = "zzzz1";
        String word2 = "zzzz2";
        String word3 = "zzzz3_with_redirect";
        
        prefix = ruwikt_parsed_conn.enc.EncodeFromJava("zzzz");
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;
        
        TPage p[] = null;
        //TPage.insert(conn, page_title, word_count, wiki_link_count, is_in_wiktionary);
        TPage.insert(conn, word1, word_count, wiki_link_count, is_in_wiktionary, redirect_target);
        TPage.insert(conn, word2, word_count, wiki_link_count, is_in_wiktionary, redirect_target);
        redirect_target = ruwikt_parsed_conn.enc.EncodeFromJava("test_redirect_target");
        TPage.insert(conn, word3, word_count, wiki_link_count, is_in_wiktionary, redirect_target);

        // tests
        limit = 0;
        boolean b_skip_redirects = true;
        boolean b_meaning = false;
        boolean b_sem_rel = false;
        TLang   source_lang[] = new TLang [0];
        p = TPage.getByPrefix(conn, prefix, limit, b_skip_redirects,
                source_lang,
                b_meaning, b_sem_rel);
        assertEquals(p.length, 0);

        limit = 1;
        p = TPage.getByPrefix(conn, prefix, limit, b_skip_redirects,
                source_lang,
                b_meaning, b_sem_rel);
        assertEquals(p.length, 1);

        limit = -1;
        p = TPage.getByPrefix(conn, prefix, limit, b_skip_redirects,
                source_lang,
                b_meaning, b_sem_rel);
        assertEquals(p.length, 2);

        limit = 3;
        b_skip_redirects = false;
        p = TPage.getByPrefix(conn, prefix, limit, b_skip_redirects,
                source_lang,
                b_meaning, b_sem_rel);
        assertEquals(p.length, 3);

        assertTrue(!p[0].isRedirect());
        assertTrue(!p[1].isRedirect());
        assertTrue( p[2].isRedirect());
        
        assertEquals(p[0].getWordCount(),      word_count);
        assertEquals(p[0].getWikiLinkCount(),  wiki_link_count);
        assertEquals(p[0].isInWiktionary(),    is_in_wiktionary);
        
        // delete temporary DB record
        TPage.delete(conn, word1);
        TPage.delete(conn, word2);
        TPage.delete(conn, word3);
    }
}