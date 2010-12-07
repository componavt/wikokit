
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

//import wikipedia.sql.UtilSQL;
import wikipedia.language.LanguageType;
//import wikt.constant.ContextLabel;
//import wikt.util.WikiWord;
//import wikt.word.WQuote;
import wikt.word.WMeaning;
import wikt.multi.ru.WMeaningRu;

public class TWikiTextTest {

    public Connect   ruwikt_parsed_conn;
    
    public TWikiTextTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);
    }

    @After
    public void tearDown() {
        Connect conn = ruwikt_parsed_conn;

        ruwikt_parsed_conn.Close();
    }
    
    @Test
    public void testStoreToDB() {
        System.out.println("storeToDB_ru_en");
        
        String text = "test_TWikiText_storeToDB_ru";
        Connect conn = ruwikt_parsed_conn;

        // wiki_text
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;

        wikt_lang       = LanguageType.ru; // Russian Wiktionary
        page_title      = "airplane";
        lang_section    = LanguageType.en; // English word

        String _definition = "A programmable_test calculation_tests_test";
        String line =  "# A [[programmable_test]] [[calculation_test]]s_test";
        WMeaning wmeaning = WMeaningRu.parseOneDefinition(wikt_lang, page_title, lang_section, line);
        assertNotNull(wmeaning);
        assertTrue(wmeaning.getDefinition().equalsIgnoreCase(_definition));
        
        TWikiText twiki_text = TWikiText.storeToDB (conn, wmeaning.getWikiText());
        assertNotNull(twiki_text);

        // check that two pages should appear in table 'page': "programmable" and "calculation"
        TPage page_programmable = TPage.get(conn, "programmable_test");
        TPage page_calculation = TPage.get(conn, "calculation_test");
        assertNotNull(page_programmable);
        assertNotNull(page_calculation);

        // check that 1 record should appear in table 'wiki_text'
        TWikiText wiki_text = TWikiText.get(conn, _definition);
        assertNotNull(wiki_text);

        // check that 1 record should appear in tables 'inflection', 'page_inflection'
        TInflection infl_calculations = TInflection.get(conn, "calculation_tests_test");
        assertNotNull(infl_calculations);

        TPageInflection pti_calc = TPageInflection.get(conn, page_calculation, infl_calculations);
        assertNotNull(pti_calc);

        // check that 2 record should appear in table 'wiki_text_words'
        TWikiTextWords w_calc = TWikiTextWords.getByWikiTextAndPageAndInflection(conn, wiki_text, page_calculation, pti_calc);
        TWikiTextWords w_prog = TWikiTextWords.getByWikiTextAndPageAndInflection(conn, wiki_text, page_programmable, null);
        assertNotNull(w_calc);
        assertNotNull(w_prog);


        // delete temporary records

        // wiki_text = "A programmable_test calculation_tests_test"
        // Attention: words of this wiki_text should be deleted before the line "TWikiText.deleteWithWords(conn, w_text);"
        TWikiText.deleteWithWords(conn, twiki_text);
        
        String[] pages_test = {"programmable_test", "calculation_test"};
        for(String p: pages_test) {
            TWikiText w_text = TWikiText.get(conn, p);
            if(null != w_text)
                TWikiText.deleteWithWords(conn, w_text);
            TPage.delete(conn, p);
        }

        // inflection = "calculation_tests_test"
        TInflection.delete(conn, infl_calculations);
        TPageInflection.delete(conn, pti_calc);
    }

    @Test
    public void testInsert() {
        System.out.println("insert_ru");

        String text = "test_TWikiText_insert_ru";
        Connect conn = ruwikt_parsed_conn;

        // insert page, get wiki_text.id
        TWikiText p = null, p2=null, p3=null;
        p = TWikiText.get(conn, text);
        if(null != p) {
            TWikiText.delete(conn, p);
        }
        // p == p2
        p = TWikiText.insert(conn, text);
        p2 = TWikiText.get(conn, text);
        p3 = TWikiText.getByID(conn, p.getID());

        assertTrue(p != null);
        assertTrue(p2 != null);
        assertTrue(p3 != null);
        assertTrue(p.getID() > 0);
        assertEquals(p.getID(), p2.getID());
        assertEquals(p.getText(), p3.getText());

        TWikiText.delete(conn, p);              // delete temporary DB record

        p = TWikiText.getByID(conn, p.getID()); // check deletion
        assertTrue(p == null);
        p2 = TWikiText.getByID(conn, p2.getID());
        assertTrue(p2 == null);
    }

}