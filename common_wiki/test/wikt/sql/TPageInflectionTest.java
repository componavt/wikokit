
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

public class TPageInflectionTest {

    public Connect   ruwikt_parsed_conn;

    String page_title, inflected_form;
    TPage page;
    TInflection infl;
    
    public TPageInflectionTest() {
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

        Connect conn = ruwikt_parsed_conn;
        
        page_title = conn.enc.EncodeFromJava("test_TPageInflection_insert_ru");
        inflected_form = "test_TPageInflection_insert_ru";

        // insert page, get meaning_id
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;

        page = null;
        page = TPage.get(conn, page_title);
        if(null == page) {
            page = TPage.insert(conn, page_title, word_count, wiki_link_count, 
                                is_in_wiktionary, redirect_target);
            assertTrue(null != page);
        }

        // insert inflection
        int freq = 1;
        infl = null;
        infl = TInflection.get(conn, inflected_form);
        if(null == infl) {
            infl = TInflection.insert(conn, inflected_form, freq);
            assertTrue(null != infl);
        }
    }

    @After
    public void tearDown() {
        Connect conn = ruwikt_parsed_conn;
        TPage.delete(conn, page_title);
        TInflection.delete(conn, infl);
        
        ruwikt_parsed_conn.Close();
    }
    
    @Test
    public void testInsert() {
        System.out.println("insert_ru");
        Connect conn = ruwikt_parsed_conn;
        
        int term_freq = 1;
        TPageInflection page_infl = TPageInflection.insert(conn, page, infl, term_freq);
        assertTrue(null != page_infl);

        // test get by ID
        TPageInflection page_infl2 = TPageInflection.getByID(conn, page_infl.getID());
        assertTrue(null != page_infl2);
        assertEquals(page.getID(), page_infl2.getPage().getID());

        TPageInflection.delete(conn, page_infl);
    }

    @Test
    public void testGet() {
        System.out.println("get_ru");
        Connect conn = ruwikt_parsed_conn;

        TPageInflection page_infl, page_infl2;
        int term_freq = 1;

        // test get by page and inflection
        page_infl = TPageInflection.get(conn, page, infl);
        assertTrue(null == page_infl);

        page_infl  = TPageInflection.insert(conn, page, infl, term_freq);
        page_infl2 = TPageInflection.get(conn, page, infl);
        assertTrue(null != page_infl);
        assertTrue(null != page_infl2);
        assertEquals(page.getID(), page_infl2.getPage().getID());
        
        TPageInflection.delete(conn, page_infl);
    }

}