
package wikt.sql;

import wikipedia.language.LanguageType;
import wikt.constant.POS;

import wikt.util.POSText;
import wikt.multi.ru.WTranslationRu;

import wikt.word.WTranslation;
import wikt.word.WTranslationEntry;

import wikipedia.sql.UtilSQL;

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
    TPOS pos;
    TLangPOS lang_pos;
    TMeaning meaning;
    String meaning_summary;
    WTranslation[] wtrans_all;

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
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,
                                LanguageType.ru);

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

        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        page_title = "колокольчик_test"; // page_title = conn.enc.EncodeFromJava("test_TTranslation");
        LanguageType lang_section = LanguageType.ru; // Russian word

        POSText pt = new POSText(POS.noun, kolokolchik_text);
        wtrans_all = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);


        // insert page, get meaning_id
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;

        page = null;
        page = TPage.get(conn, page_title);
        if(null == page) {
            TPage.insert(conn, page_title, word_count, wiki_link_count, 
                         is_in_wiktionary, redirect_target);
            page = TPage.get(conn, page_title);
        }

        int lang_id = TLang.getIDFast(lang_section); //227;
        TLang lang = TLang.getTLangFast(lang_id);

        int etymology_n = 0;
        String lemma = "";

        pos = TPOS.get(POS.noun);
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
    }

    @After
    public void tearDown() {
        Connect conn = ruwikt_parsed_conn;
        
        TLangPOS.delete(conn, page);
        TPage.delete(conn, page_title);
        TMeaning.delete(conn, meaning);

        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "page");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "relation");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "lang_pos");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "meaning");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text_words");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "translation");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "translation_entry");
        
        conn.Close();
    }

    @Test
    public void testStoreToDB () {
        System.out.println("storeToDB");
        Connect conn = ruwikt_parsed_conn;

        for(WTranslation wtrans : wtrans_all) {
            TTranslation.storeToDB(conn, lang_pos, meaning, wtrans);
        }
        
        // gets translation from Russian into English (in Russian Wiktionary): 
        // gets wikified words from text in the section == Translation ==
        // page -> lang_pos -> meaning
        // page -> lang_pos -> translation
        //         language -> translation
        //
        //  "{{перев-блок|звонок|\n" +
        //      "|en=[[little]] [[bell]], [[handbell]], [[doorbell]]\n" +
        //  "{{перев-блок|оркестровый инструмент|\n" +
        //      "|en=[[glockenspiel]]\n" +
        //  "{{перев-блок|цветок\n" +
        //      "|en=[[bluebell]], [[bellflower]], [[campanula]]\n" +
        TLang source_lang = TLang.get(LanguageType.ru);
        TLang target_lang = TLang.get(LanguageType.en);
        TPage[] en_translations = TTranslation.fromPageToTranslations(conn, source_lang, page, target_lang); // page = "колокольчик"
        assertNotNull(en_translations);
        assertEquals(6, en_translations.length); // 6: handbell, doorbell, glockenspiel, bluebell, bellflower, campanula
                                                 // except 1 wiki phrase which consists of two wiki words: "[[little]] [[bell]]"
        
        
        // gets translation from English into Russian (in Russian Wiktionary):
        // page -> wiki_text_words -> wiki_text -> ? meaning     -> lang_pos -> page
        //                                      -> ? translation -> lang_pos -> page
        // звонок
        // fr=[[sonnette]]
        TPage fr_page = TPage.get(conn, "sonnette");
        assertNotNull(fr_page);

        // there is no English translation for French word "sonette"
        target_lang = TLang.get(LanguageType.en);
        TPage[] ru_source = TTranslation.fromTranslationsToPage(conn, source_lang, fr_page, target_lang);
        assertNotNull(ru_source);
        assertEquals(0, ru_source.length);

        // there is 1 French translation for French word "sonette"
        target_lang = TLang.get(LanguageType.fr);
        ru_source = TTranslation.fromTranslationsToPage(conn, source_lang, fr_page, target_lang);
        assertNotNull(ru_source);
        assertEquals(1, ru_source.length);
        assertEquals(page_title, ru_source[0].getPageTitle());
    }

    @Test
    public void testFromTranslationsToPage_strings () {
        System.out.println("fromTranslationsToPage_strings");
        Connect conn = ruwikt_parsed_conn;

        for(WTranslation wtrans : wtrans_all) {
            TTranslation.storeToDB(conn, lang_pos, meaning, wtrans);
        }
        
        //  "{{перев-блок|звонок|\n" +
        //      "|en=[[little]] [[bell]], [[handbell]], [[doorbell]]\n" +
        //  "{{перев-блок|оркестровый инструмент|\n" +
        //      "|en=[[glockenspiel]]\n" +
        //  "{{перев-блок|цветок\n" +
        //      "|en=[[bluebell]], [[bellflower]], [[campanula]]\n" +
        LanguageType source_lang = LanguageType.ru;
        LanguageType target_lang = LanguageType.en;

        // there is 1 translation Russian ("колокольчик") -> English ("doorbell")
        String en_translation = "doorbell";
        String[] ru_source = TTranslation.fromTranslationsToPage(conn, source_lang, en_translation, target_lang);
        assertNotNull(ru_source);
        assertEquals(1, ru_source.length);
        assertEquals(page_title, ru_source[0]);
    }

    @Test
    public void testFromTranslationsToPage_UpperCaseConflict () {
        System.out.println("fromTranslationsToPage_strings");
        Connect conn = ruwikt_parsed_conn;

        // 1. let's check conflict: "plane" and "Plane"
        String de_page_title = "Plane";
        String redirect_target = null;

        TPage de_page = TPage.get(conn, de_page_title);
        assertNull(de_page);
        de_page = TPage.insert(conn, de_page_title, 0, 0, false, redirect_target);
        assertNotNull(de_page);


        // 2. let's check more than one translations: "самолёт" -> "plane" and "план" -> "plane"
        TLang lang = TLang.get(LanguageType.ru);
        int etymology_n = 0;
        String lemma = "";
//        WTranslation[] wtrans_all_;
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        LanguageType lang_section = LanguageType.ru; // Russian word

        {
            page_title = "самолёт";
            page = TPage.insert(conn, page_title, 0, 0, false, redirect_target);
            assertNotNull(page);
            lang_pos = TLangPOS.insert(conn, page, lang, pos, etymology_n, lemma);
            assertNotNull(lang_pos);

            String samolyot_text = "text before \n" +
                "===Перевод===\n" +
                "{{перев-блок||\n" +
                "|en=[[airplane]], [[plane]], [[aircraft]]\n" +
                "|bg=[[самолет]], [[аероплан]]\n" +
                "}}\n";
            POSText pt = new POSText(POS.noun, samolyot_text);
            wtrans_all = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);

            for(WTranslation wtrans : wtrans_all) {
                TTranslation.storeToDB(conn, lang_pos, meaning, wtrans);
            }
        }

        {
            page_title = "план";
            page = TPage.insert(conn, page_title, 0, 0, false, redirect_target);
            assertNotNull(page);
            lang_pos = TLangPOS.insert(conn, page, lang, pos, etymology_n, lemma);
            assertNotNull(lang_pos);

            String plan_text = "text before \n" +
                "===Перевод===\n" +
                "{{перев-блок|схема, чертёж|\n" +
                "|en=[[map]], [[plane]], [[scheme]]\n" +
                "}}\n" +
                "\n" +
                "{{перев-блок|программа|\n" +
                "|en=[[plan]], [[draft]], [[scheme]], [[contrivance]], [[road map]]\n" +
                "}}\n";

            POSText pt = new POSText(POS.noun, plan_text);
            wtrans_all = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);

            for(WTranslation wtrans : wtrans_all) {
                TTranslation.storeToDB(conn, lang_pos, meaning, wtrans);
            }
        }

        //  "|en=[[airplane]], [[plane]], [[aircraft]]\n" +
        //      самолёт
        //  "|en=[[map]], [[plane]], [[scheme]]\n" +
        //      план
        LanguageType source_lang = LanguageType.ru;
        LanguageType target_lang = LanguageType.en;

        // there is 2 translation Russian ("самолёт", "план") -> English ("plane")
        String en_translation = "plane";
        String[] ru_source = TTranslation.fromTranslationsToPage(conn, source_lang, en_translation, target_lang);
        assertNotNull(ru_source);
        assertEquals(2, ru_source.length);
        assertTrue( ru_source[0].equals("самолёт") ||
                    ru_source[1].equals("самолёт")    );
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

    @Test
    public void testGetByID_WithMeaningNULL() {
        System.out.println("getByID_WithMeaningNULL");
        Connect conn = ruwikt_parsed_conn;

        meaning = null;
        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning);
        assertNotNull(trans);

        TTranslation trans2 = TTranslation.getByID(conn, trans.getID());
        assertNull(trans2.getMeaning());

        TTranslation.delete(conn, trans);
    }

    @Test
    public void testGetByLangPOS () {
        System.out.println("getByLangPOS");
        Connect conn = ruwikt_parsed_conn;

        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning);
        assertNotNull(trans);

        TTranslation[] trans_all = TTranslation.getByLangPOS (conn, lang_pos);
        assertNotNull(trans_all);
        assertEquals(1, trans_all.length);

        TTranslation.delete(conn, trans);
    }
}