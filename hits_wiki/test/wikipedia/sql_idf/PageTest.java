
package wikipedia.sql_idf;

import wikipedia.sql.Connect;

import java.sql.Connection;
import java.util.*;
import junit.framework.TestCase;

public class PageTest extends TestCase {
    
    public Connect   idfruwiki_conn;
    public Connect   idfsimplewiki_conn;
    
    public List<TermPage> tp_list1, tp_list2;
    Term t1, t2;
    String lemma1, lemma2;

    public PageTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        idfruwiki_conn = new Connect();
        idfruwiki_conn.Open(Connect.IDF_RU_HOST, Connect.IDF_RU_DB, Connect.IDF_RU_USER, Connect.IDF_RU_PASS);
        
        idfsimplewiki_conn = new Connect();
        idfsimplewiki_conn.Open(Connect.IDF_SIMPLE_HOST, Connect.IDF_SIMPLE_DB, Connect.IDF_SIMPLE_USER, Connect.IDF_SIMPLE_PASS);
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        lemma1 = "GREEN";
        lemma2 = "TEA";
        t1 = Term.get(conn, lemma1);
        t2 = Term.get(conn, lemma2);
        tp_list1 = TermPage.getPagesByTermID(conn, t1);
        tp_list2 = TermPage.getPagesByTermID(conn, t2);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of fillPages method, of class Page.
     */
    public void testFillPages_simple() {
        System.out.println("fillPages_simple");
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        
        Page.fillPages(conn, tp_list1);
        Page.fillPages(conn, tp_list2);
        
        List<TermPage> intersection = TermPage.intersectPageTitles(tp_list1, tp_list2);
        assertTrue(intersection.size() > 0);
    }

}
