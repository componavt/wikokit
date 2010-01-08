
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

public class WTTranslationTest {

    public Connect   ruwikt_parsed_conn;

    public WTTranslationTest() {
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
        ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,
                                LanguageType.ru);

        TLang.createFastMaps(ruwikt_parsed_conn);   // once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps(ruwikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }

    /** Translates the word from the main (native) language to a target language.
     * Direct translation, i.e. get TranslationBox, get TranslationEntry from it.
     */
    @Test
    public void testGetDirectTranslation_ru() {
        System.out.println("getDirectTranslation_ru");
        Connect connect = ruwikt_parsed_conn;
        LanguageType target_lang;
        String word = "колокольчик";
        String[] transl_words;

        // From native to native language: it's impossible
        target_lang = LanguageType.ru;
        transl_words = WTTranslation.getDirectTranslation(connect, target_lang, word);
        assertEquals(0, transl_words.length);

        // Russian to Italian: звонок: [[campanello]]; цветок: [[campanella]]
        target_lang = LanguageType.it;

        transl_words = WTTranslation.getDirectTranslation(connect, target_lang, word);
        assertTrue(transl_words.length >= 2);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "campanello"));
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "campanella"));

        // Russian to Spanish: звонок: [[campanilla]]; (у скота) [[cencerro]]; цветок: [[campanilla]], [[campanula]]
        target_lang = LanguageType.es;

        transl_words = WTTranslation.getDirectTranslation(connect, target_lang, word);
        assertTrue(transl_words.length >= 3); // 3 unique words: campanilla, cencerro, campanula
    }

    
    /** Translates the word from a foreign language to the native language.
     * Backward translation, i.e. get title of page which has a TranslationBox with the given word.
     */
    @Test
    public void testGetBackwardTranslation_ru() {
        System.out.println("getBackwardTranslation_ru");
        Connect connect = ruwikt_parsed_conn;
        LanguageType foreign_lang;
        String word;
        String[] transl_words;

        // From language to the same language: it's impossible
        foreign_lang = LanguageType.ru;
        transl_words = WTTranslation.getBackwardTranslation(connect, foreign_lang, "some word");
        assertEquals(0, transl_words.length);

        // Russian to Italian:
        // "яблоко" -> [[mela]] ''f''
        word = "mela";
        foreign_lang = LanguageType.it;

        transl_words = WTTranslation.getBackwardTranslation(connect, foreign_lang, word);
        assertTrue(transl_words.length > 0);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "яблоко"));

        // Russian to German:
        // "яблоко" -> [[Apfel]] <i>m</i> -s, Äpfel
        word = "Apfel";
        foreign_lang = LanguageType.de;

        transl_words = WTTranslation.getBackwardTranslation(connect, foreign_lang, word);
        assertTrue(transl_words.length > 0);
        assertTrue(StringUtil.containsIgnoreCase(transl_words, "яблоко"));
    }

    /** Translates the word from source to target language.
     * String [] translate (Connect connect,LanguageType source_lang,LanguageType target_lang,String word);
     */
    @Test
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

    /** Translates the word from source to target language (S).
        String [] translate (Connect connect,String source_lang,String target_lang,String word)
     */
    @Test
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
    }

    /** Translates from Foreign (German, English) into Native (Russian) language. */
    @Test
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
    }

    /** Translates the word from foreign into another foreign
     */
    @Test
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
    }

    

}