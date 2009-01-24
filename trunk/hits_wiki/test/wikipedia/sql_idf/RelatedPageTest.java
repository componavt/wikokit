
package wikipedia.sql_idf;

import wikipedia.sql.Connect;

import junit.framework.*;

public class RelatedPageTest extends TestCase {
    
    public Connect   idfruwiki_conn;
    public Connect   idfsimplewiki_conn;
    
    public RelatedPageTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        idfruwiki_conn = new Connect();
        idfruwiki_conn.Open(Connect.IDF_RU_HOST, Connect.IDF_RU_DB, Connect.IDF_RU_USER, Connect.IDF_RU_PASS);
        
        idfsimplewiki_conn = new Connect();
        idfsimplewiki_conn.Open(Connect.IDF_SIMPLE_HOST, Connect.IDF_SIMPLE_DB, Connect.IDF_SIMPLE_USER, Connect.IDF_SIMPLE_PASS);
    }

    protected void tearDown() throws Exception {
        idfruwiki_conn.Close();
        idfsimplewiki_conn.Close();
    }

    /**
     * Test of add method, of class wikipedia.sql_idf.RelatedPage.
     */
    public void testAdd_simple() {
        System.out.println("add_simple");
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        String page_title = "test ' and \"";
        String new_related_list    = "all tests are related|test2|test3";
        String result_related_list = "all_tests_are_related|test2|test3";
        String     related_list = null;
        
        RelatedPage.delete(conn, page_title);
        Page.delete(conn, page_title);
        
        RelatedPage rp = new RelatedPage ();
        if(rp.isInTable_RelatedPage(conn, page_title)) {
            // unreachable statement
            related_list = rp.getRelatedTitlesAsString();
        } else {
            rp.add(conn, page_title, new_related_list);
        }
        assertEquals(null, related_list);
        
        if(rp.isInTable_RelatedPage(conn, page_title)) {
            related_list = rp.getRelatedTitlesAsString();
        }
        assertTrue(related_list.equalsIgnoreCase(result_related_list));
        
        // test delete
        RelatedPage.delete(conn, page_title);
        assertFalse(rp.isInTable_RelatedPage(conn, page_title));
        Page.delete(conn, page_title);
    }
    
    /**
     * Test that empty list of related pages is added.
     */
    public void testAdd_empty_simple() {
        System.out.println("add_empty_simple");
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        String page_title = "test ' and \"";
        String    new_related_list = "";
        String result_related_list = "";
        String        related_list = null;
        
        RelatedPage.delete(conn, page_title);
        Page.delete(conn, page_title);
        
        RelatedPage rp = new RelatedPage ();
        if(rp.isInTable_RelatedPage(conn, page_title)) {
            // unreachable statement
            related_list = rp.getRelatedTitlesAsString();
        } else {
            rp.add(conn, page_title, new_related_list);
        }        
        
        if(rp.isInTable_RelatedPage(conn, page_title)) {
            related_list = rp.getRelatedTitlesAsString();
        }
        assertEquals(0, related_list.length());
        assertTrue(related_list.equalsIgnoreCase(result_related_list));
        
        // delete
        RelatedPage.delete(conn, page_title);
        Page.delete(conn, page_title);
    }
    
    /**
     * Test that empty list of related pages is added.
     */
    public void testAdd_null_simple() {
        System.out.println("add_null_simple");
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        String page_title = "test ' and \"";
        String    new_related_list = null;
        String result_related_list = "";
        String        related_list = null;
        
        RelatedPage.delete(conn, page_title);
        Page.delete(conn, page_title);
        
        RelatedPage rp = new RelatedPage ();
        if(rp.isInTable_RelatedPage(conn, page_title)) {
            // unreachable statement
            related_list = rp.getRelatedTitlesAsString();
        } else {
            rp.add(conn, page_title, new_related_list);
        }        
        
        if(rp.isInTable_RelatedPage(conn, page_title)) {
            related_list = rp.getRelatedTitlesAsString();
        }
        assertEquals(0, related_list.length());
        assertTrue(related_list.equalsIgnoreCase(result_related_list));
        
        // delete
        RelatedPage.delete(conn, page_title);
        Page.delete(conn, page_title);
    }
    
    
    /**
     * Test add(title) when title exists in page, but it's absent in related_page.
     * It should be chosen INSERT instead of UPDATE to the table related_page.
     */
    public void testAdd_insert_or_update_simple() {
        System.out.println("insert_or_update_simple");
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        String page_title = "test_insert_or_update_simple";
        String related_list = "test_related_pages_for_test_insert_or_update_simple";
        String related_list_result = "";
        int word_count = 84;
        
        RelatedPage.delete(conn, page_title);
        Page.delete(conn, page_title);
        
        Page p = Page.getOrInsert(conn, page_title, word_count);
        
        int page_id = p.getPageID();
        assertTrue(page_id > 0);
        assertEquals(word_count, p.getWordCount());
        
        RelatedPage rp = new RelatedPage ();
        boolean b = rp.isInTable_RelatedPage(conn, page_title);
        assertFalse (b);
    
        // main test:
        rp.add(conn, page_title, related_list);
        
        b = rp.isInTable_RelatedPage(conn, page_title);
        assertTrue (b);

        related_list_result = rp.getRelatedTitlesAsString();
        
        assertTrue(related_list_result.length() > 0);
        assertTrue(related_list.equalsIgnoreCase(related_list_result));
        
        // delete
        RelatedPage.delete(conn, page_title);
        Page.delete(conn, page_title);
    }
    
    /**
     * Test of getRelatedTitlesAsString method, of class wikipedia.sql_idf.RelatedPage.
     */
    public void testGetRelatedTitlesAsString_simple() {
        System.out.println("getRelatedTitlesAsString_simple");
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        RelatedPage rp = new RelatedPage();
        
        String page_title = "absent title";
        String expResult = "";
        String related_list = null;;
        
        if(rp.isInTable_RelatedPage(conn, page_title)) {
            // unreachable statement
            related_list = rp.getRelatedTitlesAsString();
        }
        assertEquals(related_list, null);
    }

    /**
     * Test of getRelatedTitlesAsArray method, of class wikipedia.sql_idf.RelatedPage.
     */
    public void testGetRelatedTitlesAsArray() {
        System.out.println("getRelatedTitlesAsArray");
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        String page_title = "test ' and \"";
        String new_related_list    = "tests '|test2 \"|test3";
        
        RelatedPage.delete(conn, page_title);
        Page.delete(conn, page_title);
        
        RelatedPage rp = new RelatedPage ();
        rp.add(conn, page_title, new_related_list);
        
        String[] list = null;
        if(rp.isInTable_RelatedPage(conn, page_title)) {
            list = rp.getRelatedTitlesAsArray();
        }
        assertTrue(3 == list.length);
        
        // delete
        RelatedPage.delete(conn, page_title);
        Page.delete(conn, page_title);
    }
    
}
