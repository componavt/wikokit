
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

import wikipedia.language.LanguageType;
import wikt.constant.ContextLabel;
import wikt.util.WikiWord;
import wikt.word.WQuote;
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
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }
    
    @Test
    public void testStoreToDB() {
        System.out.println("storeToDB_ru");
        
        String text = "test_TWikiText_storeToDB_ru";
        Connect conn = ruwikt_parsed_conn;

        // wiki_text
        LanguageType wikt_lang       = LanguageType.ru; // Russian Wiktionary
        LanguageType lang_section;
        String page_title;

        page_title      = "самолёт";
        lang_section    = LanguageType.ru; // Russian word

        //ContextLabel[] _labels = new ContextLabel[0];   //_labels[0] = LabelRu.p;
        /*WikiWord[] ww = new WikiWord[4];
        ww[0] = new WikiWord("programmable","programmable", null);
        ww[3] = new WikiWord("calculation", "calculations", null);
        WQuote[] _quote = null;*/
        //WMeaning expResult = new WMeaning(page_title, _labels, _definition_wiki, _quote);

        String _definition = "A programmable calculations";
        String line =  "# A [[programmable]] [[calculation]]s";
        WMeaning wmeaning = WMeaningRu.parseOneDefinition(wikt_lang, page_title, lang_section, line);

        assertNotNull(wmeaning);
        assertTrue(wmeaning.getDefinition().equalsIgnoreCase(_definition));

        // public static TWikiText storeToDB (Connect connect,WikiText wiki_text) {
        TWikiText twiki_text = TWikiText.storeToDB (conn, wmeaning.getWikiText());
        assertNotNull(twiki_text);

        // check that wiki text should appear: "programmable" and "calculation"
        TPage page1 = TPage.get(conn, "programmable");
        TPage page2 = TPage.get(conn, "calculation");
        assertNotNull(page1);
        assertNotNull(page2);

        // check that two pages should appear: "programmable" and "calculation"
        TWikiText wiki_text = TWikiText.get(conn, _definition);
        assertNotNull(wiki_text);
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