/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wikt.multi.ru;

import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
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
        connect_ruwikt.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);
    }

    @After
    public void tearDown() {
        connect_ruwikt.Close();
    }

    /**
     * Test of splitToLanguageSections method, of class WLanguageRu.
     * test words: самолёт, stitch, султан бор тафта кит акушер
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
        assertEquals(3, result.length);
        
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

    
    // {{заголовок|ka|add=}}
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