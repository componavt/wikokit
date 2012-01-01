
package wikt.sql.index;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class IndexForeignTest {

    public Connect   ruwikt_parsed_conn;
    String native_page_title;

    
    public IndexForeignTest() {
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

        native_page_title = ruwikt_parsed_conn.enc.EncodeFromJava("ru_water12");
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;

        TPage p = null;
        p = TPage.get(conn, native_page_title);
        if(null != p) {
            TPage.delete(conn, native_page_title);
        }

        //TPage p0 =
        TPage.insert(conn, native_page_title, word_count, wiki_link_count,
                                is_in_wiktionary, redirect_target);
    }

    @After
    public void tearDown() {
        Connect conn = ruwikt_parsed_conn;

        // delete temporary DB record
        TPage.delete(conn, native_page_title);
    }


    // deletes all index tables
    //@Test
    //public void testGenerateTables() {
    //    System.out.println("generateTables");

    //    LanguageType native_lang = LanguageType.ru;
        //IndexForeign.generateTables(ruwikt_parsed_conn, native_lang); - 
    //}

    @Test
    public void testInsert() {
        System.out.println("insert_ru");
        Connect conn = ruwikt_parsed_conn;
        TPage native_page;
        String s, foreign_word, prefix_foreign_word;
        LanguageType native_lang, foreign_lang;
        IndexForeign[] index_foreign;
        boolean foreign_has_definition;

        // 0. test that foreign!=native => insert 0 records

        int n_limit =-1;
        foreign_has_definition = true;
        foreign_word = "water12";
        native_lang = LanguageType.en;
        foreign_lang = LanguageType.en;
        IndexForeign.insert(conn, foreign_word, foreign_has_definition,
                            native_page_title, native_lang, foreign_lang);
        
        boolean b_meaning = false;
        boolean b_sem_rel = false;
        
        prefix_foreign_word = "water1";
        index_foreign = IndexForeign.getByPrefixForeign(conn, 
                                    prefix_foreign_word, n_limit,
                                    native_lang, foreign_lang,
                                    b_meaning, b_sem_rel);
        
        assertEquals(0, index_foreign.length);

        // 1. test insert 1 records (foreign_word, native_page_title)
        native_lang = LanguageType.ru;
        foreign_lang = LanguageType.en;
        IndexForeign.insert(conn, foreign_word, foreign_has_definition,
                            native_page_title, native_lang, foreign_lang);
                            
        index_foreign = IndexForeign.getByPrefixForeign(conn,
                                        prefix_foreign_word, n_limit,
                                        native_lang, foreign_lang,
                                        b_meaning, b_sem_rel);
        assertEquals(1, index_foreign.length);
        native_page = index_foreign[0].getNativePage();
        assertNotNull(native_page_title);
        s = native_page.getPageTitle();
        assertEquals(s, native_page_title);

        // delete temporary DB record
        IndexForeign.delete(conn, foreign_word, native_page_title,
                             native_lang, foreign_lang);


        // 2. test insert only foreign_word (without native)
        // todo


        // delete temporary DB record

        // delete record in index_native
  //      IndexForeign.delete(conn, foreign_word, native_page_title, foreign_lang);

//        index_foreign = IndexForeign.getByNative(conn, native_page_title, foreign_lang);
//        assertEquals(0, index_foreign.length);

        // 3. test insert into table with complex code, e.g. index_slovio-la
        native_lang = LanguageType.ru;
        foreign_lang = LanguageType.slovio_la;
        IndexForeign.insert(conn, foreign_word, foreign_has_definition,
                            native_page_title, native_lang, foreign_lang);
                            
        index_foreign = IndexForeign.getByPrefixForeign(conn,
                                        prefix_foreign_word, n_limit,
                                        native_lang, foreign_lang,
                                        b_meaning, b_sem_rel);
        assertEquals(1, index_foreign.length);
        native_page = index_foreign[0].getNativePage();
        assertNotNull(native_page_title);
        s = native_page.getPageTitle();
        assertEquals(s, native_page_title);

        // delete temporary DB record
        IndexForeign.delete(conn, foreign_word, native_page_title,
                             native_lang, foreign_lang);

    }

    @Test
    public void testHasAndInsertIfAbsent() {
        System.out.println("has and insertIfAbsent");
        Connect conn = ruwikt_parsed_conn;
        //TPage native_page;
        String s, foreign_word; //prefix_foreign_word;
        LanguageType native_lang, foreign_lang;
        //IndexForeign[] index_foreign;
        boolean foreign_has_definition, b_has;

        int count;
        foreign_has_definition = true;
        foreign_word = "water12";
        native_lang = LanguageType.en;
        foreign_lang = LanguageType.en;
        IndexForeign.insert(conn, foreign_word, foreign_has_definition,
                            native_page_title, native_lang, foreign_lang);
        
        // 1. successfull insertion of 1 record (foreign_word, native_page_title)
        native_lang = LanguageType.ru;
        foreign_lang = LanguageType.en;
        IndexForeign.insertIfAbsent(conn, foreign_word, foreign_has_definition,
                                native_page_title, native_lang, foreign_lang);
                            
        b_has = IndexForeign.has( conn, foreign_word, native_page_title,
                                          foreign_lang);
        assertTrue(b_has);

        b_has = IndexForeign.has( conn, foreign_word, null,
                                          foreign_lang);
        assertFalse(b_has);

        count = IndexForeign.count(conn, foreign_word, native_page_title, foreign_lang);
        assertEquals(1, count);

        // 2. failed insertion of the same record (foreign_word, native_page_title)
        IndexForeign.insertIfAbsent(conn, foreign_word, foreign_has_definition,
                                native_page_title, native_lang, foreign_lang);

        b_has = IndexForeign.has( conn, foreign_word, native_page_title,
                                          foreign_lang);
        assertTrue(b_has);

        b_has = IndexForeign.has( conn, foreign_word, null,
                                          foreign_lang);
        assertFalse(b_has);

        count = IndexForeign.count(conn, foreign_word, native_page_title, foreign_lang);
        assertEquals(1, count);

        // delete temporary DB record
        IndexForeign.delete(conn, foreign_word, native_page_title,
                             native_lang, foreign_lang);
    }

}