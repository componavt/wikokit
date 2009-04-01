
package wikt.sql;

import wikipedia.language.LanguageType;
import wikt.constant.POS;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

public class TTranslationTest {

    public Connect   ruwikt_parsed_conn;
    String  samolyot_text, kolokolchik_text, kolokolchik_text_1_translation_box,
            kosa_text_1_translation_box_without_header;

    TPage page;
    String  page_title;
    TLangPOS lang_pos;
    TMeaning meaning;
    String meaning_summary;

    public TTranslationTest() {
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

        kolokolchik_text = "text before \n" +
            "===Перевод===\n" +
            "{{перев-блок|звонок|\n" +
            "|en=[[little]] [[bell]], [[handbell]], [[doorbell]]\n" +
            "|de=[[Glöckchen]], [[Schelle]], [[Klingel]]\n" +
            "|os=[[мыр-мыраг]], [[хъуытаз]] {{m}}\n" +
            "|fr=[[sonnette]], [[clochette]], [[clarine]]; (у скота) [[sonnaille]]\n" +
            "}}\n" +
            "{{перев-блок|оркестровый инструмент|\n" +
            "|en=[[glockenspiel]]\n" +
            "}}\n" +
            "\n" +
            "{{перев-блок|цветок\n" +
            "|en=[[bluebell]], [[bellflower]], [[campanula]]\n" +
            "|os=[[дзæнгæрæг]], [[къæрцгæнæг]]\n" +
            "|fr=[[campanule]], [[clochette]]\n" +
            "}}\n" +
            "\n" +
            "===Библиография===\n" +
            "*\n" +
            "\n{{categ|category1|category2|lang=}}" +
            "\n" +
            "[[Категория:Музыкальные инструменты]]\n";

        kolokolchik_text_1_translation_box = "{{перев-блок|цветок\n" +
            "|en=[[bluebell]], [[bellflower]], [[campanula]]\n" +
            "|os=[[дзæнгæрæг]], [[къæрцгæнæг]]\n" +
            "|fr=[[campanule]], [[clochette]]\n" +
            "}}\n";

        kosa_text_1_translation_box_without_header = "{{перев-блок\n" +
            "|en=[[braid]], [[plait]], [[pigtail]], [[queue]]\n" +
            "|de=[[Zopf]] {{m}} -es, Zöpfe\n" +
            "|fr=[[natte]] {{f}}; [[couette]] {{f}}, [[tresse]] <i>f</i>\n" +
            "}}\n";

        Connect conn = ruwikt_parsed_conn;

        page_title = conn.enc.EncodeFromJava("test_TTranslation");

        // insert page, get meaning_id
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;

        page = null;
        page = TPage.get(conn, page_title);
        if(null == page) {
            TPage.insert(conn, page_title, word_count, wiki_link_count, is_in_wiktionary);
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
        lang_pos = TLangPOS.getByID(conn, id);
        assertTrue(null != lang_pos);
        assertEquals(page.getID(), lang_pos.getPage().getID());

        int meaning_n = 1;
        TWikiText wiki_text = null;

        meaning = TMeaning.insert(conn, lang_pos, meaning_n, wiki_text);
        assertNotNull(meaning);

        meaning_summary = "meaning_summary__test_TTranslation";
/*
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "колокольчик";
        LanguageType lang_section = LanguageType.ru; // Russian word

        POSText pt = new POSText(POS.noun, kolokolchik_text);

        WTranslation[] result = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);
*/
        
    }

    @After
    public void tearDown() {
        Connect conn = ruwikt_parsed_conn;
        
        TLangPOS.delete(conn, page);
        TPage.delete(conn, page_title);
        TMeaning.delete(conn, meaning);
        
        conn.Close();
    }

    
    @Test
    public void testInsert() {
        System.out.println("insert");
        Connect conn = ruwikt_parsed_conn;
        
        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning);
        assertNotNull(trans);
        
        TLangPOS _lang_pos = trans.getLangPOS();
        assertNotNull(_lang_pos);
        TPage _tpage = _lang_pos.getPage();
        assertNotNull(_tpage);
        assertEquals(page_title, _tpage.getPageTitle());

        assertEquals(meaning_summary, trans.getMeaningSummary());

        TMeaning _meaning = trans.getMeaning();
        assertNotNull(_meaning);

        TTranslation.delete(conn, trans);
    }

    @Test
    public void testInsertWithMeaningNULL() {
        System.out.println("insert__meaning_null");
        Connect conn = ruwikt_parsed_conn;

        meaning = null;
        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning);
        assertNotNull(trans);

        TLangPOS _lang_pos = trans.getLangPOS();
        assertNotNull(_lang_pos);
        TPage _tpage = _lang_pos.getPage();
        assertNotNull(_tpage);
        assertEquals(page_title, _tpage.getPageTitle());

        assertEquals(meaning_summary, trans.getMeaningSummary());
        
        assertNull(trans.getMeaning());
        TTranslation.delete(conn, trans);
    }

    /**
     * Test of getByID method, of class TTranslation.
     */
    @Test
    public void testGetByID() {
        System.out.println("getByID");
        Connect conn = ruwikt_parsed_conn;

        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning);
        assertNotNull(trans);

        TTranslation trans2 = TTranslation.getByID(conn, trans.getID());
        assertEquals(trans.getMeaningSummary(), trans2.getMeaningSummary());

        TTranslation.delete(conn, trans);
    }
}