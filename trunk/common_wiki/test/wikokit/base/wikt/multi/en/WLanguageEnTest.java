
package wikokit.base.wikt.multi.en;

import wikokit.base.wikt.multi.en.WLanguageEn;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.util.LangText;

public class WLanguageEnTest {

    public WLanguageEnTest() {
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
    public void testSplitToLanguageSections() {
        System.out.println("splitToLanguageSections");        
        String source_text;
        LangText[] result;
        
        // ==English==
        source_text = "Before \n==English==\n word";
        result = WLanguageEn.splitToLanguageSections("test_word1_en", new StringBuffer(source_text));
        assertEquals(1, result.length);
        assertEquals(LanguageType.en, result[0].getLanguage());
        assertEquals("Before \nword", result[0].text.toString());
        
        // ==English== and ==Swedish==
        source_text = "Before \n==English==\n english-word \n==Swedish==\n swedish-word";
        result = WLanguageEn.splitToLanguageSections("test_word2_en", new StringBuffer(source_text));
        assertEquals(2, result.length);
        assertEquals(LanguageType.en, result[0].getLanguage());
        assertEquals("Before \nenglish-word \n", result[0].text.toString());
        
        assertEquals(LanguageType.sv, result[1].getLanguage());
        assertEquals("swedish-word", result[1].text.toString());
    }

    /** Sometimes language names are wikified :(, see e.g. "nu" */
    @Test
    public void testSplitToLanguageSections_wikified_lang_name() {
        System.out.println("splitToLanguageSections_wikified_lang_name");
        String source_text;
        LangText[] result;

        // ==[[Ewe]]==
        source_text = "Before \n==[[Ewe]]==\n word";
        result = WLanguageEn.splitToLanguageSections("test_word1_en", new StringBuffer(source_text));
        assertEquals(1, result.length);
        assertEquals(LanguageType.ewe, result[0].getLanguage());
        assertEquals("Before \nword", result[0].text.toString());

        // ==[[Ewe]]== and ==[[Catalan]]==
        source_text = "Before \n==[[Ewe]]==\n ewe-word \n==[[Catalan]]==\n catalan-word";
        result = WLanguageEn.splitToLanguageSections("test_word2_en", new StringBuffer(source_text));
        assertEquals(2, result.length);
        assertEquals(LanguageType.ewe, result[0].getLanguage());
        assertEquals("Before \newe-word \n", result[0].text.toString());

        assertEquals(LanguageType.ca, result[1].getLanguage());
        assertEquals("catalan-word", result[1].text.toString());
    }

}