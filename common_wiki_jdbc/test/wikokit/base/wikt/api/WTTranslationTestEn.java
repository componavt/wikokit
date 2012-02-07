

package wikokit.base.wikt.api;

import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.api.WTTranslation;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.util.StringUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class WTTranslationTestEn {

    public Connect   enwikt_parsed_conn;

    public WTTranslationTestEn() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    // Attention: these tests are valid only with created "Wiktionary parsed database".

    @Before
    public void setUp() {
        System.out.println("Attention: these tests are valid only with created 'Wiktionary parsed database'.");
        enwikt_parsed_conn = new Connect();
        enwikt_parsed_conn.Open(Connect.ENWIKT_HOST,Connect.ENWIKT_PARSED_DB,Connect.ENWIKT_USER,Connect.ENWIKT_PASS,
                                LanguageType.en);

        TLang.createFastMaps(enwikt_parsed_conn);   // once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps(enwikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
    }

    @After
    public void tearDown() {
        enwikt_parsed_conn.Close();
    }

    /** Translates the word from the main (native) language to a target language.
     * Direct translation, i.e. get TranslationBox, get TranslationEntry from it.
     */
    @Test
    public void testGetDirectTranslation_en() {
        System.out.println("getDirectTranslation_en");
        Connect connect = enwikt_parsed_conn;
        LanguageType target_lang;
        String word = "sententious";
        String[] transl_words;

        // From native to native language: it's impossible
        target_lang = LanguageType.en;
        transl_words = WTTranslation.getDirectTranslation(connect, target_lang, word);
        assertEquals(0, transl_words.length);

        // English to Spanish:  (1) using as few words as possible: [[conciso]];
        //                      (2) tending to use aphorisms or maxims: [[sentencioso]]
        target_lang = LanguageType.es;

        transl_words = WTTranslation.getDirectTranslation(connect, target_lang, word);
        assertTrue(transl_words.length >= 2);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "conciso"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "sentencioso"));

        // Russian to Swedish: obsolete: full of meaning: [[konsis]]
        target_lang = LanguageType.sv;
        
        transl_words = WTTranslation.getDirectTranslation(connect, target_lang, word);
        assertTrue(transl_words.length >= 1); // at least 1 word: konsis
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "konsis"));
    }


    /** Translates the word from a foreign language to the native language.
     * Backward translation, i.e. get title of page which has a TranslationBox with the given word.
     */
    @Test
    public void testGetBackwardTranslation_en() {
        System.out.println("getBackwardTranslation_en");
        Connect connect = enwikt_parsed_conn;
        LanguageType foreign_lang;
        String word;
        String[] transl_words;

        // From language to the same language: it's impossible
        foreign_lang = LanguageType.en;
        transl_words = WTTranslation.getBackwardTranslation(connect, foreign_lang, "some word");
        assertEquals(0, transl_words.length);

        // English to French: abjurer (fr):
        // "abjure" -> [[abjurer]] ''m''
        word = "abjurer";
        foreign_lang = LanguageType.fr;

        transl_words = WTTranslation.getBackwardTranslation(connect, foreign_lang, word);
        assertTrue(transl_words.length > 0);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "abjure"));

        // English to German:
        // 1a) "amend" (to make better) -> [[verbessern]], [[ausbessern]]
        // 1b) "improve" (to make something better) -> [[verbessern]]
        // 1c) "ameliorate" (to become better) -> [[verbessern]]

        word = "verbessern";
        foreign_lang = LanguageType.de;

        transl_words = WTTranslation.getBackwardTranslation(connect, foreign_lang, word);
        assertTrue(transl_words.length >= 3);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "amend"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "improve"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "ameliorate"));
    }

    /** Translates the word from source to target language.
     * String [] translate (Connect connect,LanguageType source_lang,LanguageType target_lang,String word);
     */
    @Test
    public void testTranslate_from_native_into_foreign() {
        System.out.println("translate_from_native_into_foreign_en");
        Connect connect = enwikt_parsed_conn;
        LanguageType source_lang, target_lang;

        String[] transl_words;

        // Test translation for the word which is absent in Wiktionary
        source_lang = LanguageType.ru;
        target_lang = LanguageType.en;
        transl_words = WTTranslation.translate(connect, source_lang, target_lang, "The word absent in Wiktionary");
        assertEquals(0, transl_words.length);


        source_lang = LanguageType.en;
        String word = "expand";

        // English to French: [[agrandir]]
        target_lang = LanguageType.fr;

        transl_words = WTTranslation.translate(connect, source_lang, target_lang, word);
        assertTrue(transl_words.length >= 2);
        //assertEquals(1, transl_words.length);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "agrandir"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "élaborer"));
        
        
        word = "maudlin";
        // English to Spanish: [[lloraduelos]], [[mujerzuela]], [[llorona]], [[llorica]]
        target_lang = LanguageType.es;

        transl_words = WTTranslation.translate(connect, source_lang, target_lang, word);
        assertTrue(transl_words.length >= 4);

        assertTrue(StringUtil.containsIgnoreCase(transl_words, "lloraduelos"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "mujerzuela"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "llorona"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "llorica"));
    }

    /** Translates from Foreign (German, Russian) into Native (English) language. */
    @Test
    public void testTranslate_fromForeignToNative_getMeaningOfForeignWord_en() {
        System.out.println("translate_fromForeignToNative_getMeaningOfForeignWord_en");
        Connect connect = enwikt_parsed_conn;
        String[] transl_words;

        // English to German: airplane -> [[Flugzeug]] {{n}}
        // German to English: Flugzeug -> [[airplane]]
        transl_words = WTTranslation.translate(connect, "de", "en", "Flugzeug"); // from German to English
        assertTrue(transl_words.length >= 1);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "airplane"));

        // English to Icelandic: [[airplane]] -> [[flugvél]]
        // Icelandic to English: flugvél -> # [[airplane]]
        transl_words = WTTranslation.translate(connect, "is", "en", "flugvél");
        assertTrue(transl_words.length >= 1);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "airplane"));

        // Finnish to English: karavaani -> caravan
        transl_words = WTTranslation.translate(connect, "fi", "en", "karavaani");
        assertTrue(transl_words.length >= 1);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "caravan"));
    }

    /** Translates the word from foreign into another foreign
     */
    @Test
    public void testTranslate_from_foreign_into_foreign_via_en() {
        System.out.println("translate_from_foreign_into_foreign_via_en");
        Connect connect = enwikt_parsed_conn;
        LanguageType source_lang, target_lang;

        String[] transl_words;
        source_lang = LanguageType.fi;
        String word = "karavaani";

        // Finnish to Bulgarian:
        // karavaani -> caravan -> [[керван]], [[каравана]]
        target_lang = LanguageType.bg;

        transl_words = WTTranslation.translate(connect, source_lang, target_lang, word);
        assertTrue(transl_words.length >= 2);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "керван"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "каравана"));
    }



}