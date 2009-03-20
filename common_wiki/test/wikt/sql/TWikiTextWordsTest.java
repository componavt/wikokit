
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

public class TWikiTextWordsTest {

    public Connect   ruwikt_parsed_conn;

    String page_title, inflected_form;
    TWikiText wiki_text;
    TInflection infl;
    TPage page;
    TPageInflection page_infl;
    
    public TWikiTextWordsTest() {
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

        Connect conn = ruwikt_parsed_conn;
        page_title = conn.enc.EncodeFromJava("test_TWikiTextWords_insert_ru");
        inflected_form = "test_TWikiTextWords_insert_ru";

        // insert page, get meaning_id
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;

        page = TPage.get(conn, page_title);
        if(null == page) {
            page = TPage.insert(conn, page_title, word_count, wiki_link_count, is_in_wiktionary);
            assertTrue(null != page);
        }

        // insert inflection
        int freq = 1;
        infl = TInflection.get(conn, inflected_form);
        if(null == infl) {
            infl = TInflection.insert(conn, inflected_form, freq);
            assertTrue(null != infl);
        }

        // page_inflection
        int term_freq = 1;
        page_infl = TPageInflection.get(conn, page, infl);
        if(null == page_infl) {
            page_infl = TPageInflection.insert(conn, page, infl, term_freq);
            assertTrue(null != page_infl);
        }

        // insert wiki_text
        String text = "test_TWikiTextWords_insert_ru";
        wiki_text = TWikiText.get(conn, text);
        if(null == wiki_text) {
            wiki_text = TWikiText.insert(conn, text);
            assertTrue(null != wiki_text);
        }
    }

    @After
    public void tearDown() {
        Connect conn = ruwikt_parsed_conn;
        
        TPage.delete(conn, page_title);
        TInflection.delete(conn, infl);
        TPageInflection.delete(conn, page_infl);
        TWikiText.delete(conn, wiki_text);

        ruwikt_parsed_conn.Close();
    }
    
    @Test
    public void testInsert() {
        System.out.println("insert_ru");
        Connect conn = ruwikt_parsed_conn;
        
        // words
        TWikiTextWords   word = null;
        TWikiTextWords[] words = TWikiTextWords.getByWikiText(conn, wiki_text);
        if(null == words || words.length == 0) {
            // 1 word
            word = TWikiTextWords.insert(conn, wiki_text, page, page_infl);
            assertTrue(null != word);
            // word, word, word ... s
            words = TWikiTextWords.getByWikiText(conn, wiki_text);
            assertTrue(null != words);
            assertEquals(1,    words.length);
        } else
            word = words[0];
        
        // get by id
        TWikiTextWords word2 = TWikiTextWords.getByID(conn, word.getID());
        assertTrue(null != word2);
        assertEquals(word. getWikiText().getText(),
                     word2.getWikiText().getText());
                     
        
        TWikiTextWords.delete(conn, word);
    }

    @Test   // test null value of page_inflection_id
    public void testInsert_where_page_inflection_is_NULL() {
        System.out.println("insert_where_page_inflection_is_NULL_ru");
        Connect conn = ruwikt_parsed_conn;

        // words
        TWikiTextWords   word = null;
        TWikiTextWords[] words = TWikiTextWords.getByWikiText(conn, wiki_text);
        if(null == words || words.length == 0) {
            // 1 word
            word = TWikiTextWords.insert(conn, wiki_text, page, null); // page_infl = null;
            assertTrue(null != word);
            // word, word, word ... s
            words = TWikiTextWords.getByWikiText(conn, wiki_text);
            assertTrue(null != words);
            assertEquals(1,    words.length);
        } else
            word = words[0];

        // get by id
        TWikiTextWords word2 = TWikiTextWords.getByID(conn, word.getID());
        assertTrue(null != word2);
        assertEquals(word. getWikiText().getText(),
                     word2.getWikiText().getText());


        TWikiTextWords.delete(conn, word);
    }

    //TWikiTextWords getByWikiTextAndPageAndInflection (Connect connect,
            //TWikiText wiki_text,TPage page,TPageInflection page_inflection)

    @Test
    public void testInsert_getByWikiTextAndPageAndInflection() {
        System.out.println("getByWikiTextAndPageAndInflection_ru");
        Connect conn = ruwikt_parsed_conn;
        TWikiTextWords word0, word1;

        // words
        word0 = TWikiTextWords.getByWikiTextAndPageAndInflection(conn, wiki_text, page, page_infl);
        if(null == word0) {
            word1 = TWikiTextWords.insert(conn, wiki_text, page, page_infl);
            assertTrue(null != word1);

            word0 = TWikiTextWords.getByWikiTextAndPageAndInflection(conn, wiki_text, page, page_infl);
        }
        assertTrue(null != word0);

        TWikiTextWords.delete(conn, word0);
    }

    @Test
    public void testInsert_getByWikiTextAndPageAndInflection_check_null() {
        System.out.println("getByWikiTextAndPageAndInflection_check_null_where_page_inflection_is_NULL_ru");
        Connect conn = ruwikt_parsed_conn;
        TWikiTextWords word0, word1;

        // words
        word0 = TWikiTextWords.getByWikiTextAndPageAndInflection(conn, wiki_text, page, null);
        if(null == word0) {
            word1 = TWikiTextWords.insert(conn, wiki_text, page, null);
            assertTrue(null != word1);

            word0 = TWikiTextWords.getByWikiTextAndPageAndInflection(conn, wiki_text, page, null);
            assertTrue(null != word0);
        }
        TWikiTextWords.delete(conn, word0);
    }

}