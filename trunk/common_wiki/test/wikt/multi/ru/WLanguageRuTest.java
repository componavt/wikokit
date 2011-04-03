
package wikt.multi.ru;

import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;
import wikt.util.LangText;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class WLanguageRuTest {
    
    Connect     connect_ruwikt; // , connect_enwikt, connect_simplewikt;

    public WLanguageRuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        connect_ruwikt = new Connect();
        connect_ruwikt.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);
    }

    @After
    public void tearDown() {
        connect_ruwikt.Close();
    }

    /**
     * Test of splitToLanguageSections method, of class WLanguageRu.
     * test words: самолёт, stitch, султан, бор, тафта, кит, акушер
     */
    @Test
    public void testSplitToLanguageSections() {
        System.out.println("splitToLanguageSections");
        //StringBuffer[] expResult = null;
        //assertEquals(expResult, result);
        String source_text;
        LangText[] result;
        StringBuffer s;
        
        // simple two-letter code: ru
        source_text = "Before {{-ru-}} Russian";
        result = WLanguageRu.splitToLanguageSections("test_word1", new StringBuffer(source_text));
        assertEquals(1, result.length);
        //assertTrue(result[0].text.toString().equalsIgnoreCase("Before  Russian"));
        assertEquals("Before  Russian", result[0].text.toString());
        
        // simple two-letter code: ru bg tg
        source_text = "Before {{-ru-}} Russian {{-bg-}} Bulgarian {{-tg-}} Tajik After";
        result = WLanguageRu.splitToLanguageSections("test_word1", new StringBuffer(source_text));
        assertEquals(3, result.length);
        
        // more than two-letter code: ain slovio zh
        source_text = "{{-ru-}} Russian {{-ain-}} Ainu {{-slovio-la-}} Slovio {{-zh-}} Chinese";
        result = WLanguageRu.splitToLanguageSections("test_word2", new StringBuffer(source_text));
        assertEquals(4, result.length); // fifth language is unknown
        
        // unknown letter code (only one): should be omitted (with text)
        source_text = "Before {{-unknown-}} Unknown lang";
        result = WLanguageRu.splitToLanguageSections("test_word1", new StringBuffer(source_text));
        assertEquals(0, result.length);

        // without letter code: let's think that this is a Russian word
        source_text = "Before without lang tag";
        result = WLanguageRu.splitToLanguageSections("test_word1", new StringBuffer(source_text));
        assertEquals(1, result.length);
        
        // unknown letter code (last): should be omitted (with text)
        source_text = "{{-ru-}} Russian {{-slovio-}} Slovio {{-unknown-}} Unknown lang";
        result = WLanguageRu.splitToLanguageSections("test_word2", new StringBuffer(source_text));
        assertEquals(2, result.length);
        
        // unknown letter code (in the middle): should be omitted (with text)
        source_text = "{{-ru-}} Russian {{-unknown-}} Unknown lang {{-slovio-}} Slovio";
        result = WLanguageRu.splitToLanguageSections("test_word2", new StringBuffer(source_text));
        assertEquals(2, result.length);
        
        // only one language (Russian)
        s = new StringBuffer(
                    PageTableBase.getArticleText(connect_ruwikt, "самолёт"));
        result = WLanguageRu.splitToLanguageSections("самолёт", s);
        assertEquals(1, result.length);
        
        // only one language (English)
        s = new StringBuffer(PageTableBase.getArticleText(connect_ruwikt, "roast"));
        result = WLanguageRu.splitToLanguageSections("roast", s);
        assertEquals(1, result.length);
        
        // two languages: ru I & II + uk I & II
        s = new StringBuffer(PageTableBase.getArticleText(connect_ruwikt, "султан"));
        result = WLanguageRu.splitToLanguageSections("султан", s);
        assertEquals(2, result.length);
        
        // complex: 2 Russian homonyms and three other languages
        s = new StringBuffer(PageTableBase.getArticleText(connect_ruwikt, "бор"));
        result = WLanguageRu.splitToLanguageSections("бор", s);
        assertTrue(result.length >= 3); // now 4, in db 3
        
        s = new StringBuffer(PageTableBase.getArticleText(connect_ruwikt, "тафта"));
        result = WLanguageRu.splitToLanguageSections("тафта", s);
        assertEquals(3, result.length);
        
        s = new StringBuffer(PageTableBase.getArticleText(connect_ruwikt, "кит"));
        result = WLanguageRu.splitToLanguageSections("кит", s);
        assertEquals(4, result.length);
        
        // three
        s = new StringBuffer(PageTableBase.getArticleText(connect_ruwikt, "акушер"));
        result = WLanguageRu.splitToLanguageSections("акушер", s);
        assertEquals(3, result.length);
        
        // translingual, INTernational: sin (trigonometric)
        s = new StringBuffer(PageTableBase.getArticleText(connect_ruwikt, "sin"));
        result = WLanguageRu.splitToLanguageSections("sin", s);
        assertEquals(11, result.length);
        
        s = new StringBuffer(PageTableBase.getArticleText(connect_ruwikt, "0"));
        result = WLanguageRu.splitToLanguageSections("0", s);
        assertEquals(1, result.length);
    }

    
    // {{заголовок|ka|add=}} - yes, this is language delimiter
    // {{заголовок|be|add=I}}- no, this is not a laguage, but a POS delimiter
    @Test
    public void testSplitToLanguageSections_with_special_header() {
        System.out.println("splitToLanguageSections_with_special_header");
        //StringBuffer[] expResult = null;
        //assertEquals(expResult, result);
        String source_text;
        LangText[] result;

        // simple two-letter code: Georgia "|ka|"
        source_text = "Before {{заголовок|ka|add=}} Georgia";
        result = WLanguageRu.splitToLanguageSections("test_word1", new StringBuffer(source_text));
        assertEquals(1, result.length);
        //assertTrue(result[0].text.toString().equalsIgnoreCase("Before  Russian"));
        assertEquals("Before  Georgia", result[0].text.toString());

        // simple two-letter code: Georgia "|ka}}"
        source_text = "Before {{заголовок|ka}} Georgia";
        result = WLanguageRu.splitToLanguageSections("test_word1", new StringBuffer(source_text));
        assertEquals(1, result.length);
        //assertTrue(result[0].text.toString().equalsIgnoreCase("Before  Russian"));
        assertEquals("Before  Georgia", result[0].text.toString());

        // {{заголовок|be|add=I}}
        // no, this is not a laguage, but a POS delimiter (next level)
        source_text = "Before {{заголовок|be|add=I}} shah";
        result = WLanguageRu.splitToLanguageSections("shah", new StringBuffer(source_text));
        assertEquals(0, result.length);

        // only one Belarusian language with two POS
        // {{-be-}}
        // {{заголовок|be|add=I}}
        // {{заголовок|be|add=II}}
        source_text = "{{-be-}} skip me {{заголовок|be|add=I}} Belarusian 1 {{заголовок|be|add=II}} second";
        result = WLanguageRu.splitToLanguageSections("shah", new StringBuffer(source_text));
        assertEquals(1, result.length);

        // two = Russian and Belarusian (with two POS)
        // {{-ru-}}
        // {{-be-}}
        // {{заголовок|be|add=I}}
        // {{заголовок|be|add=II}}
        source_text = "{{-ru-}} Ru {{-be-}} skip me {{заголовок|be|add=I}} Belarusian 1 {{заголовок|be|add=II}} second";
        result = WLanguageRu.splitToLanguageSections("shah", new StringBuffer(source_text));
        assertEquals(2, result.length);
    }

    // = {{-ru-}} =
    // {{заголовок|add=I}}  - no, this is not a laguage, but a POS delimiter
    // {{заголовок|add=II}} - also language delimiter
    @Test
    public void testSplitToLanguageSections_with_special_header_without_language_code() {
        System.out.println("splitToLanguageSections_with_special_header_without_language_code");
        String source_text;
        LangText[] result;

        // only one Russian language with two POS
        // = {{-ru-}} =
        // {{заголовок|add=I}}
        // {{заголовок|add=II}}
        source_text = "= {{-ru-}} = skip me {{заголовок|add=I}} Russian 1 {{заголовок|add=II}} second";
        result = WLanguageRu.splitToLanguageSections("vsduti", new StringBuffer(source_text));
        assertEquals(1, result.length);
    }



    // {{заголовок|ka|add=}} - yes, this is language delimiter
    // {{заголовок|be|add=I}}- no, this is not a laguage, but a POS delimiter
    // {{заголовок|de|add=|aare}} - also language delimiter
    @Test
    public void testSplitToLanguageSections_with_special_header_also() {
        System.out.println("splitToLanguageSections_with_special_header_also");
        
        String source_text;
        LangText[] result;

        // {{заголовок|de|add=|aare}} - also language delimiter
        // simple two-letter code: German "|de|"
        source_text = "Before {{заголовок|de|add=|aare}} German";
        result = WLanguageRu.splitToLanguageSections("test_word_Aare", new StringBuffer(source_text));
        assertEquals(1, result.length);

        assertEquals(LanguageType.de, result[0].getLanguage());
    }

    // {{-de-|schwalbe}}
    @Test
    public void testSplitToLanguageSections_header_with_parameter() {
        System.out.println("splitToLanguageSections_header_with_parameter");
        //StringBuffer[] expResult = null;
        //assertEquals(expResult, result);
        String source_text;
        LangText[] result;
        StringBuffer s;

        // simple two-letter code: Georgia "|ka|"
        source_text = "Before {{-de-|schwalbe}} Eine Rauchschwalbe";
        result = WLanguageRu.splitToLanguageSections("test_word1", new StringBuffer(source_text));
        assertEquals(1, result.length);
        //assertTrue(result[0].text.toString().equalsIgnoreCase("Before  Russian"));
        assertEquals("Before  Eine Rauchschwalbe", result[0].text.toString());
    }
}