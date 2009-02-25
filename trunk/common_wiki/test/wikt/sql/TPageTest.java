
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import wikipedia.sql.Connect;

public class TPageTest {

    public Connect   ruwikt_parsed_conn;

    public TPageTest() {
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
    public void testInsert() {
        System.out.println("insert_ru");
        String page_title;
        Connect conn = ruwikt_parsed_conn;

        //page_title = "test_тыблоко";
        page_title = ruwikt_parsed_conn.enc.EncodeFromJava("test_тыблоко");
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        
        TPage p = null;
        p = TPage.get(conn, page_title);
        if(null != p) {
            TPage.delete(conn, page_title);
        }

        TPage.insert(conn, page_title, word_count, wiki_link_count, is_in_wiktionary);
        p = TPage.get(conn, page_title);

        assertTrue(p != null);
        assertTrue(p.getID() > 0);
        assertEquals(p.getWordCount(),      word_count);
        assertEquals(p.getWikiLinkCount(),  wiki_link_count);
        assertEquals(p.isInWiktionary(),    is_in_wiktionary);
        
        // delete temporary DB record
        TPage.delete(conn, page_title);
        
        p = TPage.get(conn, page_title);
        assertTrue(p == null);
    }

    
}