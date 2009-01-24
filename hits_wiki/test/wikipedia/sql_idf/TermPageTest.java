
package wikipedia.sql_idf;

import wikipedia.sql.Connect;

import junit.framework.*;
import java.util.*;

public class TermPageTest extends TestCase {
    
    public Connect   idfruwiki_conn;
    public Connect   idfsimplewiki_conn;
    
    public TermPageTest(String testName) {
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
     * Test of get method, of class wikipedia.sql_idf.TermPage.
     */
    public void testGet() {
        System.out.println("get_simple");
        
        int page_id = 0;
        int doc_freq_max = 10;
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        String page_title = "page_title_1";
        String lemma = "test_term.get";
        
        int corpus_freq = -999;
        int term_freq = -999;
        Term t = null;
        
        Term.delete(conn, lemma);
        t = Term.get (conn, lemma);
        assertEquals(null, t);
        
        // empty p
        Page.delete(conn, page_title);
        Page p = Page.get(conn, page_title);
        assertEquals(null, p);
        
        // valid p
        int word_count = 0;
        p = Page.getOrInsert(conn, page_title, word_count);
        page_id = p.getPageID();
        assertFalse(0 == page_id);        
        
        // 7 add 
        int add_term_freq = 7;
        wikipedia.sql_idf.Term.incLemmaDocFreq(conn, lemma, 
                                add_term_freq, p, doc_freq_max);
        
        t = Term.get (conn, lemma);
        int term_id = t.getTermID();
        term_freq = TermPage.getTermFreqInDocument(conn, term_id, page_id);
        corpus_freq = Term.get(conn, lemma).getCorpusFreq();
        assertEquals(7, term_freq);
        assertEquals(add_term_freq, corpus_freq);
        
        // 14 add 
        wikipedia.sql_idf.Term.incLemmaDocFreq(conn, lemma, 
                                add_term_freq, p, doc_freq_max);
        
        term_freq = TermPage.getTermFreqInDocument(conn, term_id, page_id);
        corpus_freq = Term.get(conn, lemma).getCorpusFreq();
        assertEquals(add_term_freq, term_freq);
        assertEquals(2*add_term_freq, corpus_freq);
        
        // 21 add 
        wikipedia.sql_idf.Term.incLemmaDocFreq(conn, lemma, 
                                add_term_freq, p, doc_freq_max);
        
        term_freq = TermPage.getTermFreqInDocument(conn, term_id, page_id);
        corpus_freq = Term.get(conn, lemma).getCorpusFreq();
        assertEquals(add_term_freq, term_freq);
        assertEquals(3*add_term_freq, corpus_freq);
        
        // delete created entries
        Term.delete     (conn, lemma);
        Page.delete(conn, page_title);
        
        TermPage.delete(conn, term_id, page_id);
        term_freq = TermPage.getTermFreqInDocument(conn, term_id, page_id);
        assertEquals(0, term_freq);
    }
    
    public void testGetTerms() {
        System.out.println("getTerms_simple");
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        String page_title = "Japanese_tea_ceremony";
        
        Page p = Page.get(conn, page_title);
        if(null == p) {
            System.out.println("\nSkipped. WikIDF DB is empty. The test is valid only for parsed Simple WP");
            return;
        }
        
        int page_id = p.getPageID();
        List<TermPage> list = TermPage.getTermsByPageID(conn, page_id);
        assertTrue(null != list);
        assertTrue(list.size() >= 43);
    }
    
    public void testGetPagesByTermID_simple() {
        System.out.println("getPagesByTermID_simple");
        List<TermPage> list;
        Term t;
        String lemma;
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        
        t = new Term();
        t.setTermID(-1);
        list = TermPage.getPagesByTermID(conn, t);
        assertTrue(null != list);
        assertTrue(0 == list.size());
        
        lemma = "TEA";
        t = Term.get(conn, lemma);
        list = TermPage.getPagesByTermID(conn, t);
        if(0 == list.size()) {
            System.out.println("\nSkipped. WikIDF DB is empty. The test is valid only for parsed Simple WP");
            return;
        }
        assertTrue(list.size() >= 98); // 98 pages mentioned the "tea"
    }
    
    //updateTermFreq
    public void testUpdateTermFreq() {
        System.out.println("updateTermFreq_simple");
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        
        int page_id = 2;
        int term_id = 3;
        
        int term_freq;
        
        term_freq = TermPage.getTermFreqInDocument(conn, term_id, page_id);
        assertEquals(0, term_freq);
        
        // insert 
        int new_term_freq = 7;
        TermPage.updateTermFreq (conn, term_id, page_id, new_term_freq);
        term_freq = TermPage.getTermFreqInDocument(conn, term_id, page_id);
        assertEquals(7, term_freq);
        
        // update
        new_term_freq = 8;
        TermPage.updateTermFreq (conn, term_id, page_id, new_term_freq);
        term_freq = TermPage.getTermFreqInDocument(conn, term_id, page_id);
        assertEquals(8, term_freq);
        
        // delete
        TermPage.delete(conn, term_id, page_id);
        term_freq = TermPage.getTermFreqInDocument(conn, term_id, page_id);
        assertEquals(0, term_freq);
    }
    
    
    public void testCreateMapIdTerm() {
        System.out.println("createMapIdTerm");
        
        Map<Integer, Term> result = TermPage.createMapIdTerm(null);
        assertEquals(0, result.size());
        
        List<TermPage> tp = new ArrayList<TermPage>(2);
        Term t1 = new Term();   t1.setTermID(1);
        Term t2 = new Term();   t2.setTermID(2);
        tp.add(new TermPage());
        tp.add(new TermPage());
        tp.get(0).setTerm(t2);
        tp.get(1).setTerm(t1);
        
        result = TermPage.createMapIdTerm(tp);                    
        assertEquals(result.size(), 2);
        assertEquals(result.get(1), t1);
        assertEquals(result.get(2), t2);
    }
}
