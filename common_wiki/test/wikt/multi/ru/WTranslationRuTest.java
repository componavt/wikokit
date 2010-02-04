
package wikt.multi.ru;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikt.util.POSText;
import wikt.word.WTranslation;
import wikt.word.WTranslationEntry;
import wikt.util.WikiWord;
import wikt.util.WikiText;
import wikt.constant.POS;

public class WTranslationRuTest {

    public static String samolyot_text, samolyot_with_header,
            kolokolchik_text, kolokolchik_text_1_translation_box,
            kosa_text_1_translation_box_without_header,
            unfinished_template, translation_without_pipe,
            page_end, empty_translation_with_category, absent_translation_block;

    public WTranslationRuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
                
        // 1 translation box with 21 translations
        samolyot_text = "{{перев-блок||\n" +
            "|en=[[airplane]], [[plane]], [[aircraft]]\n" +     // 0
            "|bg=[[самолет]], [[аероплан]]\n" +
            "|hu=[[repülőgép]]\n" +
            "|da=[[flyvemaskine]], [[fly]]\n" +
            "|is=[[flugvél]](f)(-ar,-ar)\n" +
            "|es=[[avión]] {{m}}\n" +
            "|it=[[aereo]]\n" +
            "|ko=[[비행기]]\n" +                                 // 7
            "|de=[[Flugzeug]] {{n}}\n" +
            "|nl=[[vliegtuig]]\n" +
            "|no=[[fly]]\n" +
            "|os=[[хæдтæхæг]]\n" +
            "|pl=[[samolot]]\n" +
            "|tr=[[uçak]], [[tayyare]]\n" +
            "|uk=[[літак]], [[аероплан]]\n" +
            "|fi=[[lentokone]]\n" +
            "|fr=[[avion]] {{m}}\n" +
            "|cs=[[letadlo]]\n" +
            "|eo=[[aeroplano]], [[avio]], [[aviadilo]]\n" +
            "|et=[[lennuk]]\n" + //"|ja=[[飛行機]] (ひこうき, хйко:ки)\n" +               // 20
            "}}";

        samolyot_with_header = "=== Перевод ===\n" +
                                samolyot_text;

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

        unfinished_template = "\n" +
            "{{unfinished\n" +
            "|m=\n" +
            "|p=1\n" +
            "|s=\n" +
            "|e=\n" +
            "}}";

        page_end = "\n" +
            "*{{theza|самолёт}}\n" +
            "\n" +
            "{{длина слова|7}}\n" +
            "\n" +
            "[[Категория:Авиация]]\n" +
            "\n" +
            "[[ar:самолёт]]";

        empty_translation_with_category = "=== Перевод ===\n" +
                "{{перев-блок||\n" +
                "|en=\n" +
                "}}\n" +
                "\n" +
                "[[Категория:Россиянки]]\n" + unfinished_template;

        translation_without_pipe = "=== Перевод ===\n" +
                "{{перев-блок\n" +
                "|en=[[test_translation]]\n" +
                "}}\n";

        absent_translation_block = "=== Перевод ===\n" +
                "*\n" +
                "\n" +
                "[[Категория:Россиянки]]\n" + unfinished_template;
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testParse_3_meanings() {
        System.out.println("parse__3_meanings");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "колокольчик";
        LanguageType lang_section = LanguageType.ru; // Russian word
        
        POSText pt = new POSText(POS.noun, kolokolchik_text);

        WTranslation[] result = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);
        assertEquals(3, result.length );
        
        assertTrue(result[0].getHeader().equalsIgnoreCase( "звонок" ));
        assertTrue(result[1].getHeader().equalsIgnoreCase( "оркестровый инструмент" ));
        assertTrue(result[2].getHeader().equalsIgnoreCase( "цветок" ));

        // "{{перев-блок|оркестровый инструмент|\n" +
        //     "|en=[[glockenspiel]]\n" +
        WikiText[] wt_orchestra = result[1].getTranslationIntoLanguage(LanguageType.en);
        assertEquals(1, wt_orchestra.length );
        assertTrue(wt_orchestra[0].getVisibleText().   equalsIgnoreCase( "glockenspiel" ) );

        WikiWord[] ww_orchestra = wt_orchestra[0].getWikiWords();
        assertEquals(1, ww_orchestra.length );
        assertTrue(ww_orchestra[0].getWordLink().   equalsIgnoreCase( "glockenspiel" ) );
        assertTrue(ww_orchestra[0].getWordVisible().equalsIgnoreCase( "glockenspiel" ) );
    }
    
    @Test
    public void testParse_1_meaning() {
        System.out.println("parse_1_meaning");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "самолёт";
        LanguageType lang_section = LanguageType.ru; // Russian word

        POSText pt = new POSText(POS.noun, samolyot_with_header);

        WTranslation[] result = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);
        assertEquals(1, result.length );

        assertEquals(0, result[0].getHeader().length()); // only one meaning, the translation box has no header
        
        //   "|tr=[[uçak]], [[tayyare]]\n"
        WikiText[] wt_samolyot = result[0].getTranslationIntoLanguage(LanguageType.tr);
        assertEquals(2, wt_samolyot.length );
        assertTrue(wt_samolyot[0].getVisibleText().equalsIgnoreCase( "uçak" ) );
        assertTrue(wt_samolyot[1].getVisibleText().equalsIgnoreCase( "tayyare" ) );
    }

    // kolokolchik_text_1_translation_box
    /*цветок
        |en=[[bluebell]], [[bellflower]], [[campanula]]" +
        |os=[[дзæнгæрæг]], [[къæрцгæнæг]]" +
        |fr=[[campanule]], [[clochette]]*/
    @Test
    public void testParseOneTranslationBox_test2() {
        System.out.println("parseOneTranslationBox_test2");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "колокольчик";

        WTranslation result = WTranslationRu.parseOneTranslationBox(wikt_lang, page_title, kolokolchik_text_1_translation_box);
        assertTrue(null != result);
        assertTrue(result.getHeader().equalsIgnoreCase( "цветок" ) );

        WTranslationEntry[] trans_all = result.getTranslations();
        assertEquals(3, trans_all.length);

        {
            // 2. Osetian
            // |os=[[дзæнгæрæг]], [[къæрцгæнæг]]
            WTranslationEntry trans_os = trans_all[1];
            assertEquals(LanguageType.os, trans_os.getLanguage());

            WikiText[] wt_os = trans_os.getWikiPhrases();
            assertEquals(2, wt_os.length);

            assertTrue(wt_os[0].getVisibleText().equalsIgnoreCase( "дзæнгæрæг" ) );
            assertTrue(wt_os[1].getVisibleText().equalsIgnoreCase( "къæрцгæнæг" ) );
        }
    }

    /* translation_without_pipe
     * "{{перев-блок" instead of "{{перев-блок||"
     */
    @Test
    public void testParse_without_pipe() {
        System.out.println("without_pipe");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "test_translation";
        LanguageType lang_section = LanguageType.ru; // Russian word

        POSText pt = new POSText(POS.noun, translation_without_pipe);

        WTranslation[] result = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);
        assertEquals(1, result.length );

        assertEquals(0, result[0].getHeader().length()); // only one meaning, the translation box has no header

        //   "|en=[[test_translation]]\n"
        WikiText[] wt_samolyot = result[0].getTranslationIntoLanguage(LanguageType.en);
        assertEquals(1, wt_samolyot.length );
        assertTrue(wt_samolyot[0].getVisibleText().equalsIgnoreCase( "test_translation" ) );
    }

    // tests skipping of unfinished_template
    @Test
    public void testParseTranslation_unfinished_template() {
        System.out.println("parse_1_meaning");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "самолёт";
        LanguageType lang_section = LanguageType.ru; // Russian word

        String s = samolyot_with_header + unfinished_template;
        POSText pt = new POSText(POS.noun, s);

        WTranslation[] result = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);
        assertEquals(1, result.length );

        assertEquals(0, result[0].getHeader().length()); // only one meaning, the translation box has no header

        //   "|tr=[[uçak]], [[tayyare]]\n"
        WikiText[] wt_samolyot = result[0].getTranslationIntoLanguage(LanguageType.tr);
        assertEquals(2, wt_samolyot.length );
        assertTrue(wt_samolyot[0].getVisibleText().equalsIgnoreCase( "uçak" ) );
        assertTrue(wt_samolyot[1].getVisibleText().equalsIgnoreCase( "tayyare" ) );
    }

    // tests skipping of unfinished_template
    @Test
    public void testParseTranslation_without_unfinished_template() {
        System.out.println("parse_1_meaning");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "самолёт";
        LanguageType lang_section = LanguageType.ru; // Russian word

        String s = samolyot_with_header + page_end;
        POSText pt = new POSText(POS.noun, s);

        WTranslation[] result = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);
        assertEquals(1, result.length );

        assertEquals(0, result[0].getHeader().length()); // only one meaning, the translation box has no header
        
        // the last translation entry shoud be without "[[en:..]]", etc.
        //   "|et=[[lennuk]]\n"
        WikiText[] wt_samolyot_et = result[0].getTranslationIntoLanguage(LanguageType.et);
        assertEquals(1, wt_samolyot_et.length);
        assertTrue(wt_samolyot_et[0].getVisibleText().equalsIgnoreCase( "lennuk" ) );
    }

    /* tests categories before unfinished_template, e.g.
        {{перев-блок||
        |en=
        }}

        [[Категория:Россиянки]]

        {{unfinished|p=1}}{{длина слова|11}}*/
    @Test
    public void testParseTranslation_categories_before_unfinished_template() {
        System.out.println("parse_categories_before_unfinished_template");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "самолёт";
        LanguageType lang_section = LanguageType.ru; // Russian word

        String s = empty_translation_with_category;
        POSText pt = new POSText(POS.noun, s);

        WTranslation[] result = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);
        assertEquals(0, result.length );
    }

    /** At least one translation block should exists (for Russian words in Russian Wiktionary).
     *
        ===Перевод===
        *

        [[Категория:Травы]]
        {{unfinished|p=1|s=1|e=1}}
     */
    @Test
    public void testParseTranslation_absentTranslationBlock () {
        System.out.println("parse_absentTranslationBlock");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "самолёт";
        LanguageType lang_section = LanguageType.ru; // Russian word

        String s = absent_translation_block;
        POSText pt = new POSText(POS.noun, s);

        WTranslation[] result = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);
        assertEquals(0, result.length );
    }


    @Test
    public void testParseOneTranslationBox_utf8_korean() {
        System.out.println("parseOneTranslationBox_utf8_korean");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "самолёт";
        
        WTranslation result = WTranslationRu.parseOneTranslationBox(wikt_lang, page_title, samolyot_text);
        assertTrue(null != result);

        assertEquals(0, result.getHeader().length()); // 1 meaning, the translation has no header (summary meaning)

        WTranslationEntry[] trans_all = result.getTranslations();
        assertEquals(20, trans_all.length);

        {
            // 1. English
            // "|en=[[airplane]], [[plane]], [[aircraft]]\n" +
            WTranslationEntry trans_en = trans_all[0];
            assertEquals(LanguageType.en, trans_en.getLanguage());

            WikiText[] ww_en = trans_en.getWikiPhrases();
            assertEquals(3, ww_en.length);

            assertTrue(ww_en[0].getVisibleText().equalsIgnoreCase( "airplane" ) );
            assertTrue(ww_en[1].getVisibleText().equalsIgnoreCase( "plane" ) );
            assertTrue(ww_en[2].getVisibleText().equalsIgnoreCase( "aircraft" ) );
        }
        {
            // 2. Korean
            //"|ko=[[비행기]]\n"
            
            WTranslationEntry trans_ko = trans_all[7];
            assertEquals(LanguageType.ko, trans_ko.getLanguage());
            
            WikiText[] ww_ko = trans_ko.getWikiPhrases();
            assertEquals(1, ww_ko.length);

            assertTrue(ww_ko[0].getVisibleText().equalsIgnoreCase( "비행기" ) );
        }
    }

    /** To parse additional comments, e.g. {{f}}, {{n}},
     * also visible != word link, e.g.
     * "|ja=[[飛行機]] (ひこうき, хйко:ки)\n"
     */
    @Test
    public void testParseOneTranslationBox_comma_in_comments() {
        System.out.println("parseOneTranslationBox_comma_in_comments");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "самолёт";
        
        WTranslation result = WTranslationRu.parseOneTranslationBox(wikt_lang, page_title, samolyot_text);
        assertTrue(null != result);
        WTranslationEntry[] trans_all = result.getTranslations();
        assertEquals(21, trans_all.length);   
        {
            // 1. Japanese
            // "|ja=[[飛行機]] (ひこうき, хйко:ки)\n" +
            // work link = 飛行機
            // word visible = 飛行機 (ひこうき, хйко:ки)
            WTranslationEntry trans_ja = trans_all[20];
            assertEquals(LanguageType.ja, trans_ja.getLanguage());
            
            WikiText[] wt_ja = trans_ja.getWikiPhrases();
            assertEquals(1, wt_ja.length);

            assertTrue(wt_ja[0].getVisibleText().equalsIgnoreCase( "飛行機" ) );
        }

        // todo parse additional comments, e.g. {{f}}, {{n}},
        // ...
    }
    
    // test one translation box without the header (article: "коса")
    @Test
    public void testParseOneTranslationBox_without_header() {
        System.out.println("parseOneTranslationBox_comma_in_comments");
        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "коса";

        WTranslation result = WTranslationRu.parseOneTranslationBox(wikt_lang, page_title, kosa_text_1_translation_box_without_header);
        assertTrue(null != result);
        WTranslationEntry[] trans_all = result.getTranslations();
        assertEquals(3, trans_all.length);
        {
            // {{перев-блок
            // |en=[[braid]], [[plait]], [[pigtail]], [[queue]]
            // |de=[[Zopf]] {{m}} -es, Zöpfe
            // work link = Zopf
            // word visible = Zopf m -es, Zöpfe
            
            // |fr=[[natte]] {{f}}; [[couette]] {{f}}, [[tresse]] <i>f</i>\n
            
            WTranslationEntry trans_de = trans_all[1];
            assertEquals(LanguageType.de, trans_de.getLanguage());

            WikiText[] wt_de = trans_de.getWikiPhrases();
            assertEquals(2, wt_de.length);
            
            WikiWord[] ww_de = wt_de[0].getWikiWords();
            assertTrue(null != ww_de);
            assertEquals(1, ww_de.length);
            assertTrue(ww_de[0].getWordLink().equalsIgnoreCase( "Zopf" ) );

            assertTrue(wt_de[0].getVisibleText().equalsIgnoreCase( "Zopf {{m}} -es" ) );
                                                                // "Zopf m. -es" will be better, but later
        }
    }
}