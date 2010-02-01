
package wikt.multi.en;

import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;
import wikt.constant.POS;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikt.util.LangText;
import wikt.util.POSText;

public class WPOSEnTest {

    Connect     connect_enwikt; // connect_simplewikt;

    public WPOSEnTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        connect_enwikt = new Connect();
        connect_enwikt.Open(Connect.ENWIKT_HOST,Connect.ENWIKT_DB,Connect.ENWIKT_USER,Connect.ENWIKT_PASS,LanguageType.ru);
    }

    @After
    public void tearDown() {
        connect_enwikt.Close();
    }

    @Test
    public void testSplitToPOSSections() {
        System.out.println("splitToPOSSections");
        String s1, s2, s3, s4, s1_result, s2_result;

        String source_text, result1, result2;
        LangText source_lt;
        LangText[] etymology_sections;
        POSText[] result;
        String page_title;

        // case -1: empty => unknown
        //
        // ===Verb===
        source_text = "===It's not a Part Of Speech Name===\n" +
                      "text which do not describe POS";
        source_lt = new LangText(LanguageType.en);
        source_lt.text = new StringBuffer(source_text);

        page_title = "pos_en_word-1";
        etymology_sections = WEtymologyEn.splitToEtymologySections(page_title, source_lt);
        result = WPOSEn.splitToPOSSections(page_title, etymology_sections);

        assertEquals(1, result.length);
        assertEquals(POS.unknown, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(source_text));

        // case 0: verb
        //
        // ===Verb===
        source_text = "===Verb===\n" +
                      "===It's not a Part Of Speech Name===";

        source_lt = new LangText(LanguageType.en);
        source_lt.text = new StringBuffer(source_text);

        page_title = "pos_en_word0";
        etymology_sections = WEtymologyEn.splitToEtymologySections(page_title, source_lt);
        result = WPOSEn.splitToPOSSections(page_title, etymology_sections);

        assertEquals(1, result.length);
        assertEquals(POS.verb, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(source_text));

        // case 1: noun and verb
        //
        // ===Noun===
        // {{en-noun}}
        // ===Verb===
        s1 = "===Noun===\n" +
             "{{en-noun}}\n";
        s2 = "===Verb===";
        source_text = s1 + s2;
        
        s1_result = "{{en-noun}}\n";
        s2_result = "";

        source_lt = new LangText(LanguageType.en);
        source_lt.text = new StringBuffer(source_text);

        page_title = "pos_en_word1";
        etymology_sections = WEtymologyEn.splitToEtymologySections(page_title, source_lt);
        result = WPOSEn.splitToPOSSections(page_title, etymology_sections);
       
        assertEquals(2, result.length);
        assertEquals(POS.noun, result[0].getPOSType());
        assertEquals(POS.verb, result[1].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(s1_result));
        assertTrue(result[1].getText().toString().equalsIgnoreCase(s2_result));

        // todo case with two etymologies
        // case 2: noun and verb
        //
        // ===Etymology 1===
        // ====Pronunciation====
        // ====Noun====
        // {{en-noun}}
        // ====Usage notes====
        // ====Synonyms====
        //
        // ===Verb===
        // ===Etymology 2===
        // ====Pronunciation====
        // ====Noun====
        // ====Verb====
        // Conjugation

        s1 = "===Etymology 1===\n" +
             "====Pronunciation====\n" +
             "====Noun====\n";
        s2 = "{{en-noun}}\n" +
             "====Usage notes====\n" +
             "====Synonyms====\n" +
             "\n";
        s3 = "===Verb===\n" +
             "===Etymology 2===\n" +
             "====Pronunciation====\n" +
             "====Noun====\n" +
             "====Verb====\n";
        s4 = "Conjugation\n" +
             "\n";

        source_text = s1 + s2 + s3 + s4;
        
        source_lt = new LangText(LanguageType.en);
        source_lt.text = new StringBuffer(source_text);

        page_title = "pos_en_word3";
        etymology_sections = WEtymologyEn.splitToEtymologySections(page_title, source_lt);
        result = WPOSEn.splitToPOSSections(page_title, etymology_sections);

        assertEquals(4, result.length);
        assertEquals(POS.noun, result[0].getPOSType());
        assertEquals(POS.verb, result[1].getPOSType());
        assertEquals(POS.noun, result[2].getPOSType());
        assertEquals(POS.verb, result[3].getPOSType());
        
        assertTrue(result[0].getText().toString().equalsIgnoreCase(s2));
        assertTrue(result[1].getText().toString().equalsIgnoreCase(""));

        assertTrue(result[2].getText().toString().equalsIgnoreCase(""));
        assertTrue(result[3].getText().toString().equalsIgnoreCase(s4));
    }

    // POS of foreign words in English Wiktionary.
    @Test
    public void testSplitToPOSSections_foreign() {
        System.out.println("splitToPOSSections_foreign");
        String s1, s2, s1_result, s2_result;
        
        String source_text;
        LangText source_lt;
        LangText[] etymology_sections;
        POSText[] result;
        String page_title;

        // case 1: "Verb form" and "Participle" in Russian word article
        //
        s1 = "===Verb form===\n" +
             "'''испо́льзуем''' (ispól'zujem)\n";
        s2 = "===Participle===\n" +
             "# [[is used]]";
        source_text = s1 + s2;

        s1_result = "'''испо́льзуем''' (ispól'zujem)\n";
        s2_result = "# [[is used]]";

        source_lt = new LangText(LanguageType.ru);
        source_lt.text = new StringBuffer(source_text);

        page_title = "pos_ru_word-1";
        etymology_sections = WEtymologyEn.splitToEtymologySections(page_title, source_lt);
        result = WPOSEn.splitToPOSSections(page_title, etymology_sections);

        assertEquals(2, result.length);
        assertEquals(POS.verb, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(s1_result));
        
        assertEquals(POS.verb, result[0].getPOSType());
        assertTrue(result[1].getText().toString().equalsIgnoreCase(s2_result));
    }

}