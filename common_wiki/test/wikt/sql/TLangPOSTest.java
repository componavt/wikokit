
package wikt.sql;

import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;
import wikt.constant.POS;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

public class TLangPOSTest {

    public Connect   ruwikt_parsed_conn;
    
    public TLangPOSTest() {
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
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);

        TLang.recreateTable(ruwikt_parsed_conn);    // once upon a time: create Wiktionary parsed db
        TLang.createFastMaps(ruwikt_parsed_conn);   // once upon a time: use Wiktionary parsed db

        TPOS.recreateTable(ruwikt_parsed_conn);     // once upon a time: create Wiktionary parsed db
        TPOS.createFastMaps(ruwikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
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
        
        page_title = ruwikt_parsed_conn.enc.EncodeFromJava("test_lang_pos");
        int page_id = 562;
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        
        TPage page = null;
        page = TPage.get(conn, page_title);
        if(null == page) {
            page = TPage.insert(conn, page_title, word_count, wiki_link_count, is_in_wiktionary);
            assertTrue(null != page);
        }

        int lang_id = TLang.getIDFast(conn, LanguageType.os); //227;
        TLang lang = TLang.getTLangFast(conn, lang_id);
        assertEquals(LanguageType.os, lang.getLanguage());

        int etymology_n = 0;
        String lemma = "";

        TPOS pos = TPOS.getPOSFast(conn, POS.noun);
        TLangPOS lang_pos = TLangPOS.insert(conn, page, lang, pos, etymology_n, lemma);
        assertTrue(null != lang_pos);

        TLangPOS[] array_lang_pos = TLangPOS.get(conn, page);
        // TLangPOS[] array_lang_pos = TLangPOS.get(conn, page, lang, pos);   todo?

        assertTrue(null != array_lang_pos);
        assertEquals(1, array_lang_pos.length);

        TLangPOS.delete(conn, page);
        
        array_lang_pos = TLangPOS.get(conn, page);  // , lang, pos);
        assertEquals(0, array_lang_pos.length);
    }

    //TLangPOS getByID (Connect connect,int id)
    @Test
    public void testGetByID() {
        System.out.println("getByID_ru");
        String page_title;
        Connect conn = ruwikt_parsed_conn;

        page_title = ruwikt_parsed_conn.enc.EncodeFromJava("test_lang_pos_getByID");


        // insert page, get page_id
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;

        TPage page = null;
        page = TPage.get(conn, page_title);
        if(null == page) {
            TPage.insert(conn, page_title, word_count, wiki_link_count, is_in_wiktionary);
            page = TPage.get(conn, page_title);
        }
        //TPage page = new TPage(page_id, page_title, word_count, wiki_link_count, is_in_wiktionary);


        int lang_id = TLang.getIDFast(conn, LanguageType.os); //227;
        TLang lang = TLang.getTLangFast(conn, lang_id);

        int etymology_n = 0;
        String lemma = "";

        TPOS pos = TPOS.getPOSFast(conn, POS.noun);
        TLangPOS.insert(conn, page, lang, pos, etymology_n, lemma);

        // let's found ID:
        TLangPOS[] array_lang_pos = TLangPOS.get(conn, page);
        assertTrue(null != array_lang_pos);
        assertEquals(1, array_lang_pos.length);
        int id = array_lang_pos[0].getID();

        // test
        TLangPOS lang_pos = TLangPOS.getByID(conn, id);
        assertTrue(null != lang_pos);
        assertEquals(page.getID(), lang_pos.getPage().getID());

        TLangPOS.delete(conn, page);
        TPage.delete(conn, page_title);
    }
}