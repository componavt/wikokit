
package wikt.sql;

//import wikipedia.sql.Connect;
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

    TPOS pos;
    TLang lang;
    TPage page;
    String page_title;
    
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
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);

        TLang.recreateTable(ruwikt_parsed_conn);    // once upon a time: create Wiktionary parsed db
        TLang.createFastMaps(ruwikt_parsed_conn);   // once upon a time: use Wiktionary parsed db

        TPOS.recreateTable(ruwikt_parsed_conn);     // once upon a time: create Wiktionary parsed db
        TPOS.createFastMaps(ruwikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
        
        Connect conn = ruwikt_parsed_conn;
        page_title = ruwikt_parsed_conn.enc.EncodeFromJava("test_lang_pos");
        
        // insert page, get page_id
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;

        page = TPage.get(conn, page_title);
        if(null == page) {
            TPage.insert(conn, page_title, word_count, wiki_link_count, 
                         is_in_wiktionary, redirect_target);
            page = TPage.get(conn, page_title);
            assertTrue(null != page);
        }

        // lang & pos
        int lang_id = TLang.getIDFast(LanguageType.os); //227;
        lang = TLang.getTLangFast(lang_id);
        assertTrue(null != lang);
        assertEquals(LanguageType.os, lang.getLanguage());

        pos = TPOS.get(POS.noun);
        assertTrue(null != pos);
    }
    
    @After
    public void tearDown() {
        TPage.delete(ruwikt_parsed_conn, page_title);
        ruwikt_parsed_conn.Close();
    }
    
    @Test
    public void testInsert() {
        System.out.println("insert_ru");
        Connect conn = ruwikt_parsed_conn;

        int etymology_n = 0;
        String lemma = "";
        
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

    @Test
    public void testInsertTwiceLangHeader_and_getUniqueByPagePOSLangEtymology() {
        System.out.println("insertTwiceLangHeader_and_getUniqueByPagePOSLangEtymology");
        Connect conn = ruwikt_parsed_conn;

        int etymology_n = 0;
        String lemma = "";

        TLangPOS lang_pos = TLangPOS.insert(conn, page, lang, pos, etymology_n, lemma);
        assertTrue(null != lang_pos);

        TLangPOS lang_pos_twice = TLangPOS.insert(conn, page, lang, pos, etymology_n, lemma);
        assertTrue(null != lang_pos_twice);

        TLangPOS.delete(conn, page);
    }

    @Test   //TLangPOS getByID (Connect connect,int id)
    public void testGetByID() {
        System.out.println("getByID_ru");
        Connect conn = ruwikt_parsed_conn;

        int etymology_n = 0;
        String lemma = "";
        
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
    }

    // SELECt lang_id FROM lang_pos WHERE page_id=674672 GROUP by lang_id;
    //public static TLang[] getLanguages (Connect connect,TPage page) {

    @Test   //TLang[] getLanguages (Connect connect,TPage page)
    public void testGetLanguages() {
        System.out.println("getLanguages_ru");
        Connect conn = ruwikt_parsed_conn;

        int etymology_n = 0;
        String lemma = "";

        TLangPOS lang_pos = TLangPOS.insert(conn, page, lang, pos, etymology_n, lemma);
        assertTrue(null != lang_pos);

        TPage tpage = lang_pos.getPage();
        assertTrue(null != tpage);

        // test
        TLang[] languages = TLangPOS.getLanguages(conn, tpage);
        assertTrue(null != languages);
        assertEquals(1, languages.length);

        TLangPOS.delete(conn, page);
    }
}