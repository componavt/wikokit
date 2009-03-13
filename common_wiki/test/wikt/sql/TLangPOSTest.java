
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
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }

    /**
     * Test of insert method, of class TLangPOS.
     */
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
        
        TPage page = new TPage(page_id, page_title, word_count, wiki_link_count, is_in_wiktionary);

        TLang.createFastMaps(conn);
        int lang_id = TLang.getIDFast(conn, LanguageType.os); //227;
        TLang lang = TLang.getTLangFast(conn, lang_id);
        assertEquals(LanguageType.os, lang.getLanguage());
        
TPOS pos = null; // TPOS.getTPOSFast(conn, POS.noun);
        TLangPOS.insert(conn, page, lang, pos);

        TLangPOS[] array_lang_pos = TLangPOS.get(conn, page, lang, pos);
        assertTrue(null != array_lang_pos);
        assertEquals(1, array_lang_pos.length);

        TLangPOS.delete(conn, page);
        
        array_lang_pos = TLangPOS.get(conn, page, lang, pos);
        assertEquals(0, array_lang_pos.length);
    }
}