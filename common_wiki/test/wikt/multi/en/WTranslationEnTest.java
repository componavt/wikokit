
package wikt.multi.en;

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

import wikt.constant.POS;
import wikt.util.WikiText;
import wikt.util.WikiWord;


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

        // {{trans-top|fruit of the orange tree}}
        // * French: {{t|fr|orange|f}}
        WikiText[] wt_fruit = result[1].getTranslationIntoLanguage(LanguageType.fr);
        assertEquals(1, wt_fruit.length );
        assertTrue(wt_fruit[0].getVisibleText().   equalsIgnoreCase( "orange" ) );

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
        assertEquals(5, trans_all.length);

        {
            // 1. English orange_division1
            /* orange_division1 = "{{trans-top|fruit of the orange tree}}\n" +
                "* French: {{t|fr|orange|f}}\n" + // 5 languages, though 6 lines
                "* German: {{t|de|Orange|f}}\n" +
                "{{trans-mid}}\n" +
                "* Japanese: {{t|ja|オレンジ|tr=orenji}}\n" +
                "* Russian: {{t|ru|апельсин|m|tr=apelsin}}\n" +
                "* Serbian\n" +
                "*: Cyrillic: {{t|sr|наранџа|f}}, {{t|sr|поморанџа|f}}\n" +
                "*: Roman: {{t|sr|narandža|f}}, {{t|sr|pomorandža|f}}\n" +
                "{{trans-bottom}}\n";*/

            WTranslationEntry trans_fr = trans_all[0];
            assertEquals(LanguageType.fr, trans_fr.getLanguage());

            WikiText[] ww_en = trans_fr.getWikiPhrases();
            assertEquals(1, ww_en.length);

            assertTrue(ww_en[0].getVisibleText().equalsIgnoreCase( "orange" ) );
        }
        {/*
            // 2. Russian:
            // * Russian: {{t|ru|апельсин|m|tr=apelsin}}

            WTranslationEntry trans_ko = trans_all[7];
            assertEquals(LanguageType.ko, trans_ko.getLanguage());

            WikiText[] ww_ko = trans_ko.getWikiPhrases();
            assertEquals(1, ww_ko.length);

            assertTrue(ww_ko[0].getVisibleText().equalsIgnoreCase( "비행기" ) );
          */
        }
    }
    
}