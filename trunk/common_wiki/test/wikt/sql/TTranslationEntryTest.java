
package wikt.sql;

import wikipedia.language.LanguageType;
import wikt.constant.POS;
import wikipedia.sql.UtilSQL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

public class TTranslationEntryTest {

    public Connect   ruwikt_parsed_conn;
    
    String  samolyot_text, kolokolchik_text, kolokolchik_text_1_translation_box,
            kosa_text_1_translation_box_without_header;

    TPage page;
    String  page_title;
    TLang lang;
    TLangPOS lang_pos;
    TMeaning meaning;
    TWikiText wiki_text;
    String meaning_summary, str_wiki_text;

    public TTranslationEntryTest() {
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
        lang = TLang.getTLangFast(lang_id);
        assertTrue(null != lang);

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
        assertNotNull(lang_pos);
        assertEquals(page.getID(), lang_pos.getPage().getID());

        str_wiki_text = "test_TWikiText_TTranslationEntry";
        wiki_text = TWikiText.insert(conn, str_wiki_text);
        if(null == wiki_text)
            wiki_text = TWikiText.get(conn, str_wiki_text);
        assertNotNull(wiki_text);
        
        int meaning_n = 1;
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
        TWikiText.delete(conn, wiki_text);

        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "page");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "relation");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "meaning");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text_words");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "translation");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "translation_entry");

        conn.Close();
    }

    @Test
    public void testInsert() {
        System.out.println("insert");
        Connect conn = ruwikt_parsed_conn;

        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning);
        assertNotNull(trans);

        TTranslationEntry trans_entry = TTranslationEntry.insert(conn, trans, lang, wiki_text);
        assertNotNull(trans_entry);
        
        TLangPOS _lang_pos = trans.getLangPOS();
        assertNotNull(_lang_pos);
        TPage _tpage = _lang_pos.getPage();
        assertNotNull(_tpage);
        assertEquals(page_title, _tpage.getPageTitle());

        assertEquals(meaning_summary, trans.getMeaningSummary());

        TMeaning _meaning = trans.getMeaning();
        assertNotNull(_meaning);

        TTranslation.delete(conn, trans);
        TTranslationEntry.delete(conn, trans_entry);
    }

    @Test
    public void testGetByLanguageAndTranslation() {
        System.out.println("getByLanguageAndTranslation");
        Connect conn = ruwikt_parsed_conn;

        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning);
        assertNotNull(trans);

        TTranslationEntry trans_entry = TTranslationEntry.insert(conn, trans, lang, wiki_text);
        assertNotNull(trans_entry);
        
        TTranslationEntry[] trans_entries = TTranslationEntry.getByLanguageAndTranslation (conn,
                                        trans, lang);                                
        assertNotNull(trans_entries);
        assertEquals(1, trans_entries.length);

        TWikiText twt = trans_entries[0].getWikiText();
        assertNotNull(twt);
        assertEquals(str_wiki_text, twt.getText());
        
        TTranslation.delete(conn, trans);
        TTranslationEntry.delete(conn, trans_entry);
    }

    //TTranslationEntry getByWikiTextAndLanguage (Connect connect,
      //                                  TWikiText wiki_text, TLang lang)

    @Test
    public void testGetByWikiTextAndLanguage() {
        System.out.println("getByWikiTextAndLanguage");
        Connect conn = ruwikt_parsed_conn;

        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning);
        assertNotNull(trans);

        TTranslationEntry trans_entry = TTranslationEntry.insert(conn, trans, lang, wiki_text);
        assertNotNull(trans_entry);
        
        TTranslationEntry trans_entry2 = TTranslationEntry.getByWikiTextAndLanguage(conn, wiki_text, lang);
        assertNotNull(trans_entry2);
        
        TWikiText twt = trans_entry2.getWikiText();
        assertNotNull(twt);
        assertEquals(str_wiki_text, twt.getText());
        
        TTranslation.delete(conn, trans);
        TTranslationEntry.delete(conn, trans_entry);
    }
    
    @Test
    public void testGetByID() {
        System.out.println("insert");
        Connect conn = ruwikt_parsed_conn;

        TTranslation trans = TTranslation.insert(conn, lang_pos, meaning_summary, meaning);
        assertNotNull(trans);

        TTranslationEntry trans_entry = TTranslationEntry.insert(conn, trans, lang, wiki_text);
        assertNotNull(trans_entry);

        TTranslationEntry trans_entry2 = TTranslationEntry.getByID(conn, trans_entry.getID());
        assertNotNull(trans_entry2);

        assertNotNull(trans_entry.getWikiText());
        TWikiText twt=trans_entry.getWikiText();
        assertEquals(str_wiki_text, twt.getText());

        TTranslation.delete(conn, trans);
        TTranslationEntry.delete(conn, trans_entry);
    }
}