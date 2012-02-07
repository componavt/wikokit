
package wikokit.base.wikt.sql.index;

import wikokit.base.wikt.sql.index.IndexNative;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.language.LanguageType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class IndexNativeTest {

    public Connect   ruwikt_parsed_conn;

    public IndexNativeTest() {
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
    }

    @Test
    public void testInsert() {
        System.out.println("insert_ru");
        String page_title;
        Connect conn = ruwikt_parsed_conn;
        
        page_title = ruwikt_parsed_conn.enc.EncodeFromJava("test_tybloko");
//        int page_id = 13;
        boolean has_relation = true;

        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;

        TPage p = null;
        p = TPage.get(conn, page_title);
        if(null != p)
            TPage.delete(conn, page_title);

        TPage p0 = TPage.insert(conn, page_title, word_count, wiki_link_count,
                                is_in_wiktionary, redirect_target);
        assertNotNull(p0);

        // IndexNative i_old = IndexNative.get(conn, page_title);
        IndexNative i = IndexNative.insert(conn, p0, has_relation);

        assertNotNull(i);
        assertTrue(i.getID() > 0);
        assertTrue(i.hasRelation());
        
        String s = i.getPageTitle();
        assertEquals(s, page_title);

        // delete temporary DB record

        // delete record in index_native
        IndexNative.delete(conn, p0);

        i = IndexNative.get(conn, page_title);
        assertNull(i);

        // delete record in tpage
        TPage.delete(conn, page_title);
    }

}