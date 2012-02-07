
package wikokit.base.wikt.multi.en;

import wikokit.base.wikt.multi.en.WEtymologyEn;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikt.util.LangText;

import wikokit.base.wikipedia.language.LanguageType;

public class WEtymologyEnTest {

    public WEtymologyEnTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSplitToEtymologySections() {
        System.out.println("splitToEtymologySections");
        
        String source_text, result1, result2;
        LangText source_lt;
        LangText[] result;

        // case 0: empty, there is no "===Etymology===" section
        //
        // ===Noun===
        // ===Verb===
        source_text = "===Noun===\n" +
                      "===Verb===";

        source_lt = new LangText(LanguageType.en);
        source_lt.text = new StringBuffer(source_text);
        result = WEtymologyEn.splitToEtymologySections("etym_word0_en", source_lt);
        assertEquals(1, result.length);
        assertEquals(LanguageType.en, result[0].getLanguage());
        assertEquals(source_text, result[0].text.toString());

        // case 1: simple
        // Etymology is a level 3 header in English Wiktionary:
        // 
        // ===English===
        // ===Etymology=== (level 3 in English Wiktionary)
        // ===Noun===
        // ===Verb===
        source_text = "===Etymology===\n" +
                "===Noun===\n" +
                "===Verb===";

        source_lt = new LangText(LanguageType.en);
        source_lt.text = new StringBuffer(source_text);
        result = WEtymologyEn.splitToEtymologySections("etym_word3_en", source_lt);
        assertEquals(1, result.length);
        assertEquals(LanguageType.en, result[0].getLanguage());
        assertEquals(source_text, result[0].text.toString());

        // one more simple case.
        //
        // ==Finnish==
        // ===Etymology===
        // ===Noun===
        source_text = "some text\n" +
                "===Etymology===\n" +
                "===Noun===";

        source_lt = new LangText(LanguageType.fi);
        source_lt.text = new StringBuffer(source_text);
        result = WEtymologyEn.splitToEtymologySections("etym_word4_en", source_lt);
        assertEquals(1, result.length);
        assertEquals(LanguageType.fi, result[0].getLanguage());
        assertEquals(source_text, result[0].text.toString());

        // complex case.
        //
        // ===Etymology 1===        (level 3)
        // ====Pronunciation====
        // ====Noun====
        // ===Etymology 2===        (level 3)
        // ====Pronunciation====
        // ====Noun====
        // ====Verb====
        
        source_text = "===Alternative spellings===\n" +
                "===Etymology===\n" +
                "====Pronunciation====\n" +
                "====Noun====\n" +
                "\n" +
                "===Etymology===\n" +
                "====Pronunciation====\n" +
                "====Noun====\n" +
                "====Verb====";

        result1 = "===Alternative spellings===\n" +
                "====Pronunciation====\n" +
                "====Noun====\n" +
                "\n";
                
        result2 = "====Pronunciation====\n" +
                "====Noun====\n" +
                "====Verb====";

        source_lt = new LangText(LanguageType.en);
        source_lt.text = new StringBuffer(source_text);
        result = WEtymologyEn.splitToEtymologySections("etym_word5_en", source_lt);
        assertEquals(2, result.length);
        assertEquals(LanguageType.en, result[0].getLanguage());
        assertEquals(result1, result[0].text.toString());

        assertEquals(LanguageType.en, result[1].getLanguage());
        assertEquals(result2, result[1].text.toString());
    }

}