package wikipedia.sql_idf;

import wikipedia.sql.Connect;
import wikipedia.util.PrintfFormat;

import java.util.*;
import junit.framework.TestCase;

public class WikIDFAPITest extends TestCase {
    
    public Connect   idfruwiki_conn;
    public Connect   idfsimplewiki_conn;
    
    public WikIDFAPITest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        idfruwiki_conn = new Connect();
        idfruwiki_conn.Open(Connect.IDF_RU_HOST, Connect.IDF_RU_DB, Connect.IDF_RU_USER, Connect.IDF_RU_PASS);
        
        idfsimplewiki_conn = new Connect();
        idfsimplewiki_conn.Open(Connect.IDF_SIMPLE_HOST, Connect.IDF_SIMPLE_DB, Connect.IDF_SIMPLE_USER, Connect.IDF_SIMPLE_PASS);
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        idfruwiki_conn.Close();
        idfsimplewiki_conn.Close();
        super.tearDown();
    }

    /**
     * Test of getTerms method, of class WikIDFAPI.
     */
    public void testGetTerms_simple() {
        System.out.println("getTerms_simple");
        String page_title = "";
        List<TermPage> result = null;
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        
        // null test
        result = WikIDFAPI.getTerms(conn, page_title);
        assertEquals(0, result.size());
        
        // article "Evolution" should have more than 42(8) unique words in wikidf database
        page_title = "Green_tea";
        Page p = wikipedia.sql_idf.Page.get(conn, page_title);
        if(null == p || 0 == p.getPageID()) {
            System.out.println("\nSkipped. WikIDF DB is empty. The test is valid only for parsed Simple WP");
            return;
        }
        result = WikIDFAPI.getTerms(conn, page_title);
        assertTrue(result.size() >= 28);
        
        System.out.println("\nLemmas (doc_freq number of docs with term) : term_freq (frequency in the article) \"" + page_title + "\":");
        for(TermPage tp:result) {
            System.out.println(tp.getTerm().getLemma() + 
                    " (" + tp.getTerm().getDocFreq() + ") " + 
                    " : " + tp.getTermFreq());
        }
    }

    public void testGetTermsSortedByIDF_simple() {
        System.out.println("getTermsSortedByIDF_simple");
        String page_title = "";
        List<TermPage> result = null;
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        
        int n_pages = wikipedia.sql_idf.Page.countPages(conn);
        
        // null test
        result = WikIDFAPI.getTermsSortedByTF_IDF(conn, page_title, n_pages);
        assertEquals(0, result.size());
        
        // article "Evolution" should have more than 42(8) unique words in wikidf database
        page_title = "Green_tea";
        Page p = wikipedia.sql_idf.Page.get(conn, page_title);
        if(null == p || 0 == p.getPageID()) {
            System.out.println("\nSkipped. WikIDF DB is empty. The test is valid only for parsed Simple WP");
            return;
        }
        result = WikIDFAPI.getTermsSortedByTF_IDF(conn, page_title, n_pages);
        assertTrue(result.size() >= 28);
        
        // print terms (lemmas) of the article "Evolution"
        System.out.println("\nPage: \"" + page_title + "\"");
        System.out.println("TF*IDF : lemma : term_freq (term frequency in the article) : doc_freq (number of docs with term)");
        System.out.println("Corpus has " + n_pages + " pages.");
        double prev_tf_idf = 10000000;
        for(TermPage tp:result) {
            double tf_idf = tp.getTF_IDF();
            assertTrue(tf_idf <= prev_tf_idf); // check the sorting by TF-IDF
            prev_tf_idf = tf_idf;
            
            System.out.println(
                    tf_idf +
                    " : " + tp.getTerm().getLemma() + 
                    " : "   + tp.getTermFreq() + 
                    " : " + tp.getTerm().getDocFreq());
        }
    }
    
    public void testGetTermsSortedByIDF_ru() {
        System.out.println("getTermsSortedByIDF_ru");
        String page_title = "";
        List<TermPage> result = null;
        java.sql.Connection conn = idfruwiki_conn.conn;
        
        int n_pages = wikipedia.sql_idf.Page.countPages(conn);
        
        // article "Через_тернии_к_звёздам_(фильм)" should have more than ??? unique words in wikidf database
        page_title = "Через_тернии_к_звёздам_(фильм)";
        Page p = wikipedia.sql_idf.Page.get(conn, page_title);
        if(null == p || 0 == p.getPageID()) {
            System.out.println("\nSkipped. WikIDF DB is empty. The test is valid only for parsed Russian WP");
            return;
        }
        result = WikIDFAPI.getTermsSortedByTF_IDF(conn, page_title, n_pages);
        assertTrue(result.size() >= 87);
        
        // print terms (lemmas) of the article
        System.out.println("\nPage: \"" + page_title + "\"");
        System.out.println("TF*IDF : lemma : term_freq (term frequency in the article) : doc_freq (number of docs with term)");
        System.out.println("Corpus has " + n_pages + " pages.");
        double prev_tf_idf = 10000000;
        for(TermPage tp:result) {
            double tf_idf = tp.getTF_IDF();
            assertTrue(tf_idf <= prev_tf_idf); // check the sorting by TF-IDF
            prev_tf_idf = tf_idf;
            
            System.out.println(
                    new PrintfFormat("%.2lg").sprintf(tf_idf) + "\t" +
                    tp.getTerm().getLemma() + "\t" +
                    tp.getTermFreq() + "\t" +
                    tp.getTerm().getDocFreq());
        }
    }
    
    public void testGetPages_simple() {
        System.out.println("getPages_simple");
        List<TermPage> result = null;
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        
        String lemma = "GREEN";
        List<TermPage> list1 = WikIDFAPI.getPages(conn, lemma);
        List<TermPage> list2 = WikIDFAPI.getPages(conn, "TEA");
        List<TermPage> intersection = TermPage.intersectPageTitles(list1, list2);
        Collections.sort(intersection, TermPage.TF_ORDER);
        
        System.out.println("\nPages which contain: " + lemma + " AND TEA:");
        System.out.println("term_freq (two terms frequency in the page) : page title : number of words (page length)");
        for(TermPage tp:intersection) {
            System.out.println(
                    tp.getTermFreq() + 
                    " : " + tp.getPageTitle() + 
                    " : " + tp.getPageWordCount());
        }
    }
    
    public void testGetPages_ru() {
        System.out.println("getPages_ru");
        java.sql.Connection conn = idfruwiki_conn.conn;
        
        String lemma1 = "чародей";
        String lemma2 = "ВОЛШЕБНИК";
        List<TermPage> list1 = WikIDFAPI.getPages(conn, lemma1);
        List<TermPage> list2 = WikIDFAPI.getPages(conn, lemma2);
        List<TermPage> intersection = TermPage.intersectPageTitles(list1, list2);
        Collections.sort(intersection, TermPage.TF_ORDER);
        
        System.out.println("\nPages which contain: " + lemma1 + " AND " + lemma2 + ":");
        System.out.println("term_freq (two terms frequency in the page) : page title : number of words (page length)");
        for(TermPage tp:intersection) {
            System.out.println(
                    new PrintfFormat("%d").sprintf(tp.getTermFreq()) + "\t" +
                    tp.getPageTitle() + "\t" +
                    tp.getPageWordCount());
        }
    }
    
}


