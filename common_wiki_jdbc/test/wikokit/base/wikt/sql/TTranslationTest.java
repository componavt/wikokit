
package wikokit.base.wikt.sql;

import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TTranslation;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TWikiText;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.constant.POS;

import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.multi.ru.WTranslationRu;

import wikokit.base.wikt.word.WTranslation;
//import wikt.word.WTranslationEntry;

//import wikipedia.sql.UtilSQL;
import wikokit.base.wikt.sql.index.IndexForeign;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.sql.Connect;

public class TTranslationTest {

    public Connect   ruwikt_parsed_conn;
    String  samolyot_text, kolokolchik_text, kolokolchik_text_1_translation_box,
            kosa_text_1_translation_box_without_header;

    LanguageType native_lang;
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

        native_lang = LanguageType.ru;
        TLang.recreateTable(ruwikt_parsed_conn);    // once upon a time: create Wiktionary parsed db
        TLang.createFastMaps(ruwikt_parsed_conn);   // once upon a time: use Wiktionary parsed db

        TPOS.recreateTable(ruwikt_parsed_conn);     // once upon a time: create Wiktionary parsed db
        TPOS.createFastMaps(ruwikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
        
        kolokolchik_text = "text before \n" +
            "===Перевод===\n" +
            "{{перев-блок|звонок_test|\n" +
            "|en=[[little]] [[bell_test]], [[handbell_test]], [[doorbell_test]]\n" +
            "|de=[[Glöckchen_test]], [[Schelle_test]], [[Klingel_test]]\n" +
            "|os=[[мыр-мыраг_test]], [[хъуытаз_test]] {{m}}\n" +
            "|fr=[[sonnette_test]], [[clochette_test]], [[clarine_test]]; (у скота) [[sonnaille_test]]\n" +
            "}}\n" +
            "{{перев-блок|оркестровый инструмент_test|\n" +
            "|en=[[glockenspiel_test]]\n" +
            "}}\n" +
            "\n" +
            "{{перев-блок|цветок_test\n" +
            "|en=[[bluebell_test]], [[bellflower_test]], [[campanula_test]]\n" +
            "|os=[[дзæнгæрæг_test]], [[къæрцгæнæг_test]]\n" +
            "|fr=[[campanule_test]], [[clochette_test]]\n" +
            "}}\n" +
            "\n" +
            "===Библиография===\n" +
            "*\n" +
            "\n{{categ|category1|category2|lang=}}" +
            "\n" +
            "[[Категория:Музыкальные инструменты]]\n";
            
        kolokolchik_text_1_translation_box = "{{перев-блок|цветок\n" +
            "|en=[[bluebell_test]], [[bellflower_test]], [[campanula_test]]\n" +
            "|os=[[дзæнгæрæг_test]], [[къæрцгæнæг_test]]\n" +
            "|fr=[[campanule_test]], [[clochette_test]]\n" +
            "}}\n";

        /*kosa_text_1_translation_box_without_header = "{{перев-блок\n" +
            "|en=[[braid_test]], [[plait_test]], [[pigtail_test]], [[queue_test]]\n" +
            "|de=[[Zopf_test]] {{m}} -es, Zöpfe\n" +
            "|fr=[[natte_test]] {{f}}; [[couette_test]] {{f}}, [[tresse_test]] <i>f</i>\n" +
            "}}\n";*/

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

        int lang_id = TLang.getIDFast(lang_section);
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

        String[] pages_test = {
            "handbell_test", "doorbell_test", "glockenspiel_test", "bluebell_test", "bellflower_test", "campanula_test", // en
            "Glöckchen_test", "Schelle_test", "Klingel_test", // de
            "мыр-мыраг_test", "дзæнгæрæг_test", "къæрцгæнæг_test", // os
            "sonnette_test", "clarine_test", "campanule_test", "clochette_test", // fr
            
            // phrases (more than one wiki word)
            "little bell_test", "bell_test", // // [[little]] [[bell_test]],
            "(у скота) sonnaille_test", "sonnaille_test", // (у скота) [[sonnaille_test]]
            "хъуытаз_test {{m}}", "хъуытаз_test", // [[хъуытаз_test]] {{m}}

            // translation header
            "звонок_test", // {{перев-блок|звонок_test
            "оркестровый инструмент_test", // {{перев-блок|оркестровый инструмент_test
            "цветок_test", // {{перев-блок|цветок_test
        };

        // delete temporary DB record
        // index_de
        IndexForeign.delete(conn, "Glöckchen_test", "колокольчик_test", native_lang, LanguageType.de);
        IndexForeign.delete(conn, "Schelle_test", "колокольчик_test", native_lang, LanguageType.de);
        IndexForeign.delete(conn, "Klingel_test", "колокольчик_test", native_lang, LanguageType.de);

        // index_en
        IndexForeign.delete(conn, "little bell_test", "колокольчик_test", native_lang, LanguageType.en);
        IndexForeign.delete(conn, "handbell_test", "колокольчик_test", native_lang, LanguageType.en);
        IndexForeign.delete(conn, "doorbell_test", "колокольчик_test", native_lang, LanguageType.en);
        IndexForeign.delete(conn, "glockenspiel_test", "колокольчик_test", native_lang, LanguageType.en);
        IndexForeign.delete(conn, "bluebell_test", "колокольчик_test", native_lang, LanguageType.en);
        IndexForeign.delete(conn, "bellflower_test", "колокольчик_test", native_lang, LanguageType.en);
        IndexForeign.delete(conn, "campanula_test", "колокольчик_test", native_lang, LanguageType.en);

        // index_fr
        IndexForeign.delete(conn, "sonnette_test", "колокольчик_test", native_lang, LanguageType.fr);
        IndexForeign.delete(conn, "clochette_test", "колокольчик_test", native_lang, LanguageType.fr);
        IndexForeign.delete(conn, "clarine_test", "колокольчик_test", native_lang, LanguageType.fr);
        IndexForeign.delete(conn, "(у скота) sonnaille_test", "колокольчик_test", native_lang, LanguageType.fr);
        IndexForeign.delete(conn, "campanule_test", "колокольчик_test", native_lang, LanguageType.fr);

        // index_os
        IndexForeign.delete(conn, "мыр-мыраг_test", "колокольчик_test", native_lang, LanguageType.os);
        IndexForeign.delete(conn, "хъуытаз_test {{m}}", "колокольчик_test", native_lang, LanguageType.os);
        IndexForeign.delete(conn, "дзæнгæрæг_test", "колокольчик_test", native_lang, LanguageType.os);
        IndexForeign.delete(conn, "къæрцгæнæг_test", "колокольчик_test", native_lang, LanguageType.os);

        page_title = "колокольчик_test";
        page = TPage.get(conn, page_title);

        TLangPOS[] ar_lang_pos = TLangPOS.get(conn, page);
        for(TLangPOS lp : ar_lang_pos) {
            TTranslation[] tt = TTranslation.getByLangPOS(conn, lp);

            for(TTranslation t : tt)
                TTranslation.deleteWithEntries(conn, t);
        }

        for(String p: pages_test) {
            TWikiText wiki_text = TWikiText.get(conn, p);   // 1. get WikiText by pages_test
            if(null != wiki_text)
                TWikiText.deleteWithWords(conn, wiki_text);
            TPage.delete(conn, p);
        }

        TLangPOS.delete(conn, page);
        TPage.delete(conn, page_title);
        TMeaning.delete(conn, meaning);
        
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "page");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "relation");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "lang_pos");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "meaning");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text_words");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "translation");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "translation_entry");
        
        //conn.Close();
    }

    @Test
    public void testStoreToDB () {
        System.out.println("storeToDB");
        Connect conn = ruwikt_parsed_conn;

        for(WTranslation wtrans : wtrans_all) {
            TTranslation.storeToDB( conn, native_lang, page_title,
                                    lang_pos, meaning, wtrans);
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
        TPage fr_page = TPage.get(conn, "sonnette_test");
        assertNotNull(fr_page);

        // there is no English translation for French word "sonnette"
        target_lang = TLang.get(LanguageType.en);
        TPage[] ru_source = TTranslation.fromTranslationsToPage(conn, source_lang, fr_page, target_lang);
        assertNotNull(ru_source);
        assertEquals(0, ru_source.length);

        // there is 1 French translation for French word "sonnette"
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
            TTranslation.storeToDB( conn, native_lang, page_title,
                                    lang_pos, meaning, wtrans);
        }
        
        //  "{{перев-блок|звонок|\n" +
        //      "|en=[[little]] [[bell]], [[handbell]], [[doorbell_test]]\n" +
        //  "{{перев-блок|оркестровый инструмент|\n" +
        //      "|en=[[glockenspiel]]\n" +
        //  "{{перев-блок|цветок\n" +
        //      "|en=[[bluebell]], [[bellflower]], [[campanula]]\n" +
        LanguageType source_lang = LanguageType.ru;
        LanguageType target_lang = LanguageType.en;

        // there is 1 translation Russian ("колокольчик") -> English ("doorbell_test")
        String en_translation = "doorbell_test";
        String[] ru_source = TTranslation.fromTranslationsToPage(conn, source_lang, en_translation, target_lang);
        assertNotNull(ru_source);
        assertEquals(1, ru_source.length);
        assertEquals(page_title, ru_source[0]);
    }

    @Test
    public void testFromTranslationsToPage_UpperCaseConflict () {
        System.out.println("fromTranslationsToPage_strings");
        Connect conn = ruwikt_parsed_conn;
        String redirect_target = null;

        // 1. let's check conflict: "plane" and "Plane"
        {
            String de_page_title = "Plane_test";

            TPage de_page = TPage.get(conn, de_page_title);
            assertNull(de_page);
            de_page = TPage.insert(conn, de_page_title, 0, 0, false, redirect_target);
            assertNotNull(de_page);

            TPage.delete(conn, de_page_title);
        }

        // 2. let's check more than one translations: "самолёт" -> "plane" and "план" -> "plane"
        TLang lang = TLang.get(LanguageType.ru);
        int etymology_n = 0;
        String lemma = "";
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        LanguageType lang_section = LanguageType.ru; // Russian word

        // block I
        {
            page_title = "самолёт_test";
            page = TPage.insert(conn, page_title, 0, 0, false, redirect_target);
            assertNotNull(page);
            lang_pos = TLangPOS.insert(conn, page, lang, pos, etymology_n, lemma);
            assertNotNull(lang_pos);

            String samolyot_text = "text before \n" +
                "===Перевод===\n" +
                "{{перев-блок||\n" +
                "|en=[[airplane_test]], [[plane_test]], [[aircraft_test]]\n" +
                "|bg=[[самолет_test]], [[аероплан_test]]\n" +
                "}}\n";
            POSText pt = new POSText(POS.noun, samolyot_text);
            wtrans_all = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);

            for(WTranslation wtrans : wtrans_all) {
                TTranslation.storeToDB( conn, native_lang, page_title,
                                        lang_pos, meaning, wtrans);
            }
        }

        // block II
        {
            page_title = "план_test";
            page = TPage.insert(conn, page_title, 0, 0, false, redirect_target);
            assertNotNull(page);
            lang_pos = TLangPOS.insert(conn, page, lang, pos, etymology_n, lemma);
            assertNotNull(lang_pos);

            String plan_text = "text before \n" +
                "===Перевод===\n" +
                "{{перев-блок|схема, чертёж_test|\n" +
                "|en=[[map_test]], [[plane2_test]], [[scheme_test]]\n" +
                "}}\n" +
                "\n" +
                "{{перев-блок|программа_test|\n" +
                "|en=[[plan_test]], [[draft_test]], [[scheme_test]], [[contrivance_test]], [[road map_test]]\n" +
                "}}\n";

            POSText pt = new POSText(POS.noun, plan_text);
            wtrans_all = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);

            for(WTranslation wtrans : wtrans_all) {
                TTranslation.storeToDB( conn, native_lang, page_title,
                                        lang_pos, meaning, wtrans);
            }
        }
/*
        //  "|en=[[airplane]], [[plane]], [[aircraft]]\n" +
        //      самолёт
        //  "|en=[[map]], [[plane]], [[scheme]]\n" +
        //      план
        LanguageType source_lang = LanguageType.ru;
        LanguageType target_lang = LanguageType.en;

        // there are 2 translations: Russian ("самолёт", "план") -> English ("plane")
        String en_translation = "plane_test";
        String[] ru_source = TTranslation.fromTranslationsToPage(conn, source_lang, en_translation, target_lang);
        assertNotNull(ru_source);
        assertEquals(2, ru_source.length);
        assertTrue( (ru_source[0].equals("самолёт_test") || ru_source[1].equals("самолёт_test")) &&
                    (ru_source[0].equals("план_test")    || ru_source[1].equals("план_test"))       );
*/
        // INSERT INTO "translation_entry" VALUES (869,42,262,1382);

        // delete temporary DB record of block I
        {
            page_title = "самолёт_test";
            page = TPage.get(conn, page_title);

            IndexForeign.delete(conn, "airplane_test", "самолёт_test", native_lang, LanguageType.en);
            IndexForeign.delete(conn, "plane_test", "самолёт_test", native_lang, LanguageType.en);
            IndexForeign.delete(conn, "aircraft_test", "самолёт_test", native_lang, LanguageType.en);

            IndexForeign.delete(conn, "самолет_test", "самолёт_test", native_lang, LanguageType.bg);
            IndexForeign.delete(conn, "аероплан_test", "самолёт_test", native_lang, LanguageType.bg);

            TLangPOS[] ar_lang_pos = TLangPOS.get(conn, page);
            for(TLangPOS lp : ar_lang_pos) {
                TTranslation[] tt = TTranslation.getByLangPOS(conn, lp);

                for(TTranslation t : tt)
                    TTranslation.deleteWithEntries(conn, t);
            }

            String[] pages_test = {
            "самолёт_test",  // ru
            "аероплан_test", "самолет_test",    // bg
            "airplane_test", "plane_test", "aircraft_test", // en
            };
            for(String p: pages_test) {
                TWikiText wiki_text = TWikiText.get(conn, p);   // 1. get WikiText by pages_test
                if(null != wiki_text)
                    TWikiText.deleteWithWords(conn, wiki_text);
                TPage.delete(conn, p);
            }

            TLangPOS.delete(conn, page);
            TPage.delete(conn, page_title);
        }

        // delete temporary DB record of block II
        {
            page_title = "план_test";
            page = TPage.get(conn, page_title);

            IndexForeign.delete(conn, "map_test", "план_test", native_lang, LanguageType.en);
            IndexForeign.delete(conn, "plane2_test", "план_test", native_lang, LanguageType.en);
            IndexForeign.delete(conn, "scheme_test", "план_test", native_lang, LanguageType.en);
            IndexForeign.delete(conn, "plan_test", "план_test", native_lang, LanguageType.en);
            IndexForeign.delete(conn, "draft_test", "план_test", native_lang, LanguageType.en);
            IndexForeign.delete(conn, "contrivance_test", "план_test", native_lang, LanguageType.en);
            IndexForeign.delete(conn, "road map_test", "план_test", native_lang, LanguageType.en);
            
            TLangPOS[] ar_lang_pos = TLangPOS.get(conn, page);
            for(TLangPOS lp : ar_lang_pos) {
                TTranslation[] tt = TTranslation.getByLangPOS(conn, lp);

                for(TTranslation t : tt)
                    TTranslation.deleteWithEntries(conn, t);
            }

            String[] pages_test = {
                "scheme_test", "plan_test", "plane2_test", "draft_test", "contrivance_test",
                "map_test", "road map_test",
                "схема, чертёж_test", "программа_test" }; // translation headers
            for(String p: pages_test) {
                TWikiText wiki_text = TWikiText.get(conn, p);   // 1. get WikiText by pages_test
                if(null != wiki_text)
                    TWikiText.deleteWithWords(conn, wiki_text);
                TPage.delete(conn, p);
            }

            TLangPOS.delete(conn, page);
            TPage.delete(conn, page_title);
        }
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

        TMeaning meaning_local = null;
        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning_local);
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

        TMeaning meaning_local = null;
        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning_local);
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

    @Test
    public void testGetByMeaning () {
        System.out.println("getByMeaning");
        Connect conn = ruwikt_parsed_conn;

        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning);
        assertNotNull(trans);

        TTranslation ttrans = TTranslation.getByMeaning(conn, meaning);
        assertNotNull(ttrans);
        assertTrue(ttrans.getID() > 0);

        TTranslation.delete(conn, trans);
    }
}