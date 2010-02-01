
package wikt.multi.en;

import wikipedia.sql.Connect;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikt.util.LangText;

public class WLanguageEnTest {

    Connect     connect_enwikt; // connect_simplewikt;

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
        connect_enwikt = new Connect();
        connect_enwikt.Open(Connect.ENWIKT_HOST,Connect.ENWIKT_DB,Connect.ENWIKT_USER,Connect.ENWIKT_PASS,LanguageType.ru);
    }

    @After
    public void tearDown() {
        connect_enwikt.Close();
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

}