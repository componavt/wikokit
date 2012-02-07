
package wikokit.base.wikt.multi.en;

import wikokit.base.wikt.multi.en.WTranslationEn;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.word.WTranslation;
import wikokit.base.wikt.word.WTranslationEntry;

import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.util.WikiText;
import wikokit.base.wikt.util.WikiWord;


public class WTranslationEnTest {

    public static String airplane_text, airplane_with_header,
            orange, orange_division1, orange_division2, orange_with_header,
            absent_translation_block;

    public WTranslationEnTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {

         // 1 translation box with 8 translations
        airplane_text = "{{trans-top|powered aircraft}}\n" +
            "{{trans-top|powered aircraft}}\n" +
            "* [[Afrikaans]]: {{t-|af|vliegtuig|xs=Afrikaans}}\n" +     // 0
            "* [[Alabama]]: [[piɬɬawakáyka]]\n" +
            "* [[Breton]]: [[karr-nij]] {{m}} kirri-nij {{p}}, [[nijerez]] {{f}}, nijerezioù {{p}}\n" +
            "{{trans-mid}}\n" +
            "* Bulgarian: {{t+|bg|самолет|m|tr=samolet}}\n" +
            "* Esperanto: {{t-|eo|aviadilo|xs=Esperanto}}, {{t-|eo|flugmaŝino|xs=Esperanto}}\n" +
            "* Russian: {{t+|ru|самолёт|m|tr=samoljót}}, {{t+|ru|аэроплан|m|tr=aeroplán}} {{qualifier|obsolete}}\n" +
            "* [[Sotho]]: {{t|st|sefofane|xs=Sotho}}\n" +
            "* Turkish: [[uçak]], [[tayyare]] (''obsolete'')\n" +      // 7
            "{{trans-bottom}}\n" +
            "";

        airplane_with_header = "====Translations====\n" +
                                airplane_text + "\n" +
                                "====See also====\n";

        orange_division1 = "{{trans-top|fruit of the orange tree}}\n" +
                "* French: {{t|fr|orange|f}}\n" + // 5 languages, though 6 lines
                "* German: {{t|de|Orange|f}}\n" +
                "{{trans-mid}}\n" +
                "* Japanese: {{t|ja|オレンジ|tr=orenji}}\n" +
                "* Russian: {{t|ru|апельсин|m|tr=apelsin}}\n" +
                "* Serbian\n" +
                "*: Cyrillic: {{t|sr|наранџа|f}}, {{t|sr|поморанџа|f}}\n" +
                "*: Roman: {{t|sr|narandža|f}}, {{t|sr|pomorandža|f}}\n" +
                "{{trans-bottom}}\n";

        orange_division2 = "{{trans-top|colour of an orange}}\n" + // 3 translation
                "* German: {{t|de|Orange|n}}\n" +
                "* Hebrew: {{t|he|כתום|tr=katom}}\n" +
                "{{trans-mid}}\n" +
                "* Latvian: {{t|lt|oranžs}}\n" +
                "{{trans-bottom}}\n" +
                "\n";

        orange = orange_division1 +
                "\n" +
                orange_division2 +
                "\n";

        orange_with_header = "====Translations====\n" +
                             orange + "\n" +
                             "====See also====\n";
                             
        absent_translation_block =  "====Synonyms====\n" +
                                    "====Related terms====\n" +
                                    "===See also===";
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParse_2_meanings() {
        System.out.println("parse__2_meanings");
        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "orange";
        LanguageType lang_section = LanguageType.ru; // English word

        POSText pt = new POSText(POS.noun, orange_with_header);

        WTranslation[] result = WTranslationEn.parse(wikt_lang, lang_section, page_title, pt);
        assertEquals(2, result.length );

        assertTrue(result[0].getHeader().equalsIgnoreCase( "fruit of the orange tree" ));
        assertTrue(result[1].getHeader().equalsIgnoreCase( "colour of an orange" ));

        // {{trans-top|colour of an orange}}
        // * German: {{t|de|Orange|n}}
        WikiText[] wt_fruit = result[1].getTranslationIntoLanguage(LanguageType.de);
        assertEquals(1, wt_fruit.length );
        assertTrue(wt_fruit[0].getVisibleText().equalsIgnoreCase( "Orange" ) );

        WikiWord[] ww_fruit = wt_fruit[0].getWikiWords();
        assertEquals(1, ww_fruit.length );
        assertTrue(ww_fruit[0].getWordLink().   equalsIgnoreCase( "orange" ) );
        assertTrue(ww_fruit[0].getWordVisible().equalsIgnoreCase( "orange" ) );
    }

    /** At least one translation block should exists
     * (for English words in English Wiktionary).
     */
    @Test
    public void testParseTranslation_absentTranslationBlock () {
        System.out.println("parse_absentTranslationBlock");
        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "some word";
        LanguageType lang_section = LanguageType.en; // English word

        String s = absent_translation_block;
        POSText pt = new POSText(POS.noun, s);

        WTranslation[] result = WTranslationEn.parse(wikt_lang, lang_section, page_title, pt);
        assertEquals(0, result.length );
    }

    // if there is only header without translations, then result is null.
    @Test
    public void testParse_only_header_wo_translations() {
        System.out.println("parse_only_header_wo_translations");
        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "orange";

        String str = "{{trans-top|fruit of the orange tree}}";

        WTranslation result = WTranslationEn.parseOneTranslationBox(wikt_lang, page_title,
                                str);
        assertNull(result);

        // + empty lines
        str = str + "\n";

        result = WTranslationEn.parseOneTranslationBox(wikt_lang, page_title,
                                str);
        assertNull(result);
    }


    @Test
    public void testParseOneTranslationBox() {
        System.out.println("parseOneTranslationBox");
        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "orange";

        WTranslation result = WTranslationEn.parseOneTranslationBox(wikt_lang, page_title,
                orange_division1);
        assertTrue(null != result);

        assertTrue(result.getHeader().equalsIgnoreCase("fruit of the orange tree"));

        WTranslationEntry[] trans_all = result.getTranslations();
        assertEquals(6, trans_all.length);

        {
            // 1. English orange_division1
            // orange_division1 = "{{trans-top|fruit of the orange tree}}\n" +
            //    "* French: {{t|fr|orange|f}}\n" + // 5 languages, though 6 lines
            //    ...

            WTranslationEntry trans_fr = trans_all[0];
            assertEquals(LanguageType.fr, trans_fr.getLanguage());

            WikiText[] ww_en = trans_fr.getWikiPhrases();
            assertEquals(1, ww_en.length);

            assertTrue(ww_en[0].getVisibleText().equalsIgnoreCase( "orange" ) );
        }
    }

    // test several translation into one language:
    @Test
    public void testParseOneTranslationBox_several_translations_into_one_language() {
        System.out.println("parseOneTranslationBox_several_translations_into_one_language");
        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "orange";

        WTranslation result = WTranslationEn.parseOneTranslationBox(wikt_lang, page_title,
                orange_division1);
        assertTrue(null != result);

        WTranslationEntry[] trans_all = result.getTranslations();
        assertEquals(6, trans_all.length);
        
        // 1. English orange_division1
        /* orange_division1 = "{{trans-top|fruit of the orange tree}}\n" +
            ...
            "* Serbian\n" +
            "*: Cyrillic: {{t|sr|наранџа|f}}, {{t|sr|поморанџа|f}}\n" +
            "*: Roman: {{t|sr|narandža|f}}, {{t|sr|pomorandža|f}}\n" +
            "{{trans-bottom}}\n";*/

        // *: Cyrillic: {{t|sr|наранџа|f}}, {{t|sr|поморанџа|f}}
        WTranslationEntry trans_sr4 = trans_all[4];
        assertEquals(LanguageType.sr, trans_sr4.getLanguage());

        WikiText[] ww_sr = trans_sr4.getWikiPhrases();
        assertEquals(2, ww_sr.length);

        // *: Roman: {{t|sr|narandža|f}}, {{t|sr|pomorandža|f}}
        WTranslationEntry trans_sr5 = trans_all[5];
        assertEquals(LanguageType.sr, trans_sr5.getLanguage());
        
        ww_sr = trans_sr5.getWikiPhrases();
        assertEquals(2, ww_sr.length);
        
        assertTrue(ww_sr[0].getVisibleText().equalsIgnoreCase( "narandža" ) );
    }
}