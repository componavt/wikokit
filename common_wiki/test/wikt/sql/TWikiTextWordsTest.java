
package wikt.sql;

import wikt.util.WikiWord;
import wikipedia.sql.UtilSQL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

public class TWikiTextWordsTest {

    public Connect   ruwikt_parsed_conn;

    String page_title, inflected_form, str_wiki_text;
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
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);

        Connect conn = ruwikt_parsed_conn;

        // [[test_TWikiTextWords_insert_ru|test_TWikiTextWords_inflected_form]]
        page_title = conn.enc.EncodeFromJava("test_TWikiTextWords");
        inflected_form = "test_TWikiTextWords_inflected_form";

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
        str_wiki_text = "test_TWikiTextWords_insert_ru";
        wiki_text = TWikiText.get(conn, str_wiki_text);
        if(null == wiki_text) {
            wiki_text = TWikiText.insert(conn, str_wiki_text);
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

        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "page");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "inflection");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "page_inflection");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text_words");
        
        ruwikt_parsed_conn.Close();
    }

    @Test
    public void testStoreToDB() {
        System.out.println("storeToDB_ru");
        Connect conn = ruwikt_parsed_conn;
        TWikiTextWords twtw;
        StringBuffer s_wiki_text = new StringBuffer("Having a [[pleasant]] [[taste|tasting]], ... one [[sugar]]xyz.");
        WikiWord[] wiki_words = WikiWord.getWikiWords(page_title, s_wiki_text);

        //ww[0] = new WikiWord("pleasant","pleasant",  null);
        //ww[1] = new WikiWord("taste",   "tasting",   null);
        //ww[2] = new WikiWord("sugar",   "sugarxyz",  null);

        for(WikiWord word : wiki_words) {
            TWikiTextWords.storeToDB (conn, wiki_text, word);
            
            String s_page       = word.getWordLink();
            String s_inflection = word.getWordVisible();

            TPage p = TPage.get(conn, s_page);
            assertNotNull(p);

            TPageInflection p_infl;
            if(0 != s_page.compareTo(s_inflection)) {
                TInflection i = TInflection.get(conn, s_inflection);
                assertNotNull(i);

                p_infl = TPageInflection.get(conn, p, i);
                assertNotNull(p_infl);
            } else
                p_infl = null;

            twtw = TWikiTextWords.getByWikiTextAndPageAndInflection(conn, wiki_text, p, p_infl);
            assertNotNull(twtw);
        }
    }
    
    @Test
    public void testGetPageForOneWordWikiText() {
        System.out.println("getPageForOneWordWikiText");
        Connect conn = ruwikt_parsed_conn;
        TWikiTextWords word;

        word = TWikiTextWords.insert(conn, wiki_text, page, page_infl);
        assertNotNull(word);
        
        //[[test_TWikiTextWords_insert_ru|test_TWikiTextWords_inflected_form]]

        TPage one_wiki_word = TWikiTextWords.getPageForOneWordWikiText(conn, wiki_text);
        assertNotNull(one_wiki_word);

        assertEquals(page_title, one_wiki_word.getPageTitle());

        TWikiTextWords.delete(conn, word);
    }
    
    @Test
    public void testGetOneWordWikiTextByPage () {
        System.out.println("getOneWordWikiTextByPage");
        Connect conn = ruwikt_parsed_conn;
        TWikiTextWords word;

        word = TWikiTextWords.insert(conn, wiki_text, page, page_infl);
        assertNotNull(word);

        //[[test_TWikiTextWords_insert_ru|test_TWikiTextWords_inflected_form]]

        TWikiText[] wiki_texts = TWikiTextWords.getOneWordWikiTextByPage (conn,page);
        assertNotNull(wiki_texts);
        assertEquals(1, wiki_texts.length);
        assertEquals(str_wiki_text, wiki_texts[0].getText());

        TWikiTextWords.delete(conn, word);
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