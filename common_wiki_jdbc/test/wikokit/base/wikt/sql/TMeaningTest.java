
package wikokit.base.wikt.sql;

import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TWikiText;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.constant.POS;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.sql.Connect;

public class TMeaningTest {

    public Connect   ruwikt_parsed_conn;
    
    public TMeaningTest() {
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

        page_title = conn.enc.EncodeFromJava("test_TMeaning_insert_ru");

        // insert page, get meaning_id
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;

        TPage page = null;
        page = TPage.get(conn, page_title);
        if(null == page) {
            TPage.insert(conn, page_title, word_count, wiki_link_count, 
                         is_in_wiktionary, redirect_target);
            page = TPage.get(conn, page_title);
        }
        
        int lang_id = TLang.getIDFast(LanguageType.os); //227;
        TLang lang = TLang.getTLangFast(lang_id);
        
        int etymology_n = 0;
        String lemma = "";

        TPOS pos = TPOS.get(POS.noun);
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

        int meaning_n = 1;
        TWikiText wiki_text = null;

        TMeaning m0 = TMeaning.insert(conn, lang_pos, meaning_n, wiki_text);
        assertTrue(null != m0);

        // testGet
        TMeaning[] mm = TMeaning.get(conn, lang_pos);
        assertTrue(null != mm);
        assertEquals(1, mm.length);
        int meaning_id = mm[0].getID();

        // testGetByID
        TMeaning m = TMeaning.getByID(conn, meaning_id);
        assertTrue(null != m);
        TLangPOS tlp = m.getLangPOS(conn);
        assertTrue(null != tlp);
                                            TLangPOS mm_tlp = mm[0].getLangPOS(conn);
                                            assertTrue(null != mm_tlp);
                                            assertTrue(null != mm_tlp.getPage());
        assertEquals(tlp.getPage().getID(), mm[0].getLangPOS(conn).getPage().getID());
        
        TLangPOS.delete(conn, page);
        TPage.delete(conn, page_title);
        TMeaning.delete(conn, m);
    }
}