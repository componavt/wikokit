

package wikt.api;

import wikt.sql.*;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikipedia.util.StringUtil;

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
    public void testGetDirectTranslation_ru() {
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
    public void testGetBackwardTranslation_ru() {
        System.out.println("getBackwardTranslation_ru");
        Connect connect = enwikt_parsed_conn;
        LanguageType foreign_lang;
        String word;
        String[] transl_words;

        // From language to the same language: it's impossible
        foreign_lang = LanguageType.en;
        transl_words = WTTranslation.getBackwardTranslation(connect, foreign_lang, "some word");
        assertEquals(0, transl_words.length);

        // English to Old English: æppel (ang):
        // "apple" -> [[æppel]] ''m''
        word = "æppel";
        foreign_lang = LanguageType.ang;

        transl_words = WTTranslation.getBackwardTranslation(connect, foreign_lang, word);
        assertTrue(transl_words.length > 0);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "apple"));

        // English to German:
        // 1) "apple" (fruit) -> [[Apfel]] m
        // 2) "apple" (wood) -> [[Apfelholz]] n
        word = "Apfel";
        foreign_lang = LanguageType.de;

        transl_words = WTTranslation.getBackwardTranslation(connect, foreign_lang, word);
        assertTrue(transl_words.length >= 2);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "Apfel"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "Apfelholz"));
    }

    /** Translates the word from source to target language.
     * String [] translate (Connect connect,LanguageType source_lang,LanguageType target_lang,String word);
     */
/*    @Test
    public void testTranslate_from_native_into_foreign() {
        System.out.println("translate_from_native_into_foreign_ru");
        Connect connect = ruwikt_parsed_conn;
        LanguageType source_lang, target_lang;

        String[] transl_words;

        // Test translation for the word which is absent in Wiktionary
        source_lang = LanguageType.en;
        target_lang = LanguageType.ru;
        transl_words = WTTranslation.translate(connect, source_lang, target_lang, "The word absent in Wiktionary");
        assertEquals(0, transl_words.length);


        source_lang = LanguageType.ru;
        String word = "самолёт";

        // Russian to German: [[Flugzeug]] {{n}}
        target_lang = LanguageType.de;

        transl_words = WTTranslation.translate(connect, source_lang, target_lang, word);
        assertTrue(transl_words.length > 0);
        assertEquals(1, transl_words.length);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "Flugzeug"));

        // Russian to English: [[airplane]], [[plane]], [[aircraft]]
        target_lang = LanguageType.en;

        transl_words = WTTranslation.translate(connect, source_lang, target_lang, word);
        assertTrue(transl_words.length >= 3);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "airplane"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "plane"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "aircraft"));
    }
*/
    /** Translates the word from source to target language (S).
        String [] translate (Connect connect,String source_lang,String target_lang,String word)
     */
/*    @Test
    public void testTranslate_4args_1_Strings_ru() {
        System.out.println("translate_Strings_ru");
        Connect connect = ruwikt_parsed_conn;
        String source_lang, target_lang, word;
        String[] transl_words;
        source_lang = "ru";
        word = "самолёт";

        // Russian to German: [[Flugzeug]] {{n}}
        target_lang = "de";

        transl_words = WTTranslation.translate(connect, source_lang, target_lang, word);
        assertTrue(transl_words.length > 0);
        assertEquals(1, transl_words.length);
        assertEquals("Flugzeug", transl_words[0]);

        // Russian to English: [[airplane]], [[plane]], [[aircraft]]
        target_lang = "en";

        transl_words = WTTranslation.translate(connect, source_lang, target_lang, word);
        assertTrue(transl_words.length > 0);
        assertEquals(3, transl_words.length);
    }*/

    /** Translates from Foreign (German, English) into Native (Russian) language. */
/*    @Test
    public void testTranslate_fromForeignToNative_getMeaningOfForeignWord_ru() {
        System.out.println("translate_fromForeignToNative_getMeaningOfForeignWord_ru");
        Connect connect = ruwikt_parsed_conn;
        //String source_lang, target_lang, word;
        String[] transl_words;

        // Russian to German: "самолёт" -> [[Flugzeug]] {{n}}
        // German to Russian: Flugzeug -> [[самолёт]], [[аэроплан]]

        transl_words = WTTranslation.translate(connect, "de", "ru", "Flugzeug"); // from German to Russian
        assertTrue(transl_words.length >= 2);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "самолёт"));
        // assertTrue(StringUtil.containsIgnoreCase(transl_words, "самолёт, аэроплан"));

        // Russian to English: "самолёт" -> [[airplane]], [[plane]], [[aircraft]]
        // English to Russian: airplane -> # {{амер.}} [[аэроплан]]
        // en ("airplane") -> ru should be: "самолёт", "аэроплан"

        transl_words = WTTranslation.translate(connect, "en", "ru", "airplane");
        assertTrue(transl_words.length >= 2);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "самолёт"));
        // assertTrue(StringUtil.containsIgnoreCase(transl_words, "{{амер.}} самолёт, аэроплан"));

        // English to Russian: car -> ...

        transl_words = WTTranslation.translate(connect, "en", "ru", "car");
        assertTrue(transl_words.length >= 5);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "автомобиль"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "вагон"));
    }*/

    /** Translates the word from foreign into another foreign
     */
 /*   @Test
    public void testTranslate_from_foreign_into_foreign_via_ru() {
        System.out.println("translate_from_foreign_into_foreign_via_ru");
        Connect connect = ruwikt_parsed_conn;
        LanguageType source_lang, target_lang;

        String[] transl_words;
        source_lang = LanguageType.en;
        String word = "airplane";

        // English to German:
        // airplane -> самолёт -> [[Flugzeug]] {{n}}
        // plane    -> ?? ->
        // aircraft -> ?? ->
        target_lang = LanguageType.de;

        transl_words = WTTranslation.translate(connect, source_lang, target_lang, word);
        assertTrue(transl_words.length > 0);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "Flugzeug"));
    }*/



}