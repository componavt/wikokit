
package wikipedia.sql_idf;

import wikipedia.sql.Connect;

import junit.framework.*;
import java.util.*;


public class TermTest extends TestCase {
    
    public Connect   idfruwiki_conn;
    public Connect   idfsimplewiki_conn;
    
    public List<TermPage> tp_list;
    Term t1, t2;
    String lemma1, lemma2, page_title_t12;
    int doc_freq_t = 7;
    
    public TermTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        idfruwiki_conn = new Connect();
        idfruwiki_conn.Open(Connect.IDF_RU_HOST, Connect.IDF_RU_DB, Connect.IDF_RU_USER, Connect.IDF_RU_PASS);
        
        idfsimplewiki_conn = new Connect();
        idfsimplewiki_conn.Open(Connect.IDF_SIMPLE_HOST, Connect.IDF_SIMPLE_DB, Connect.IDF_SIMPLE_USER, Connect.IDF_SIMPLE_PASS);
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        tp_list = new ArrayList<TermPage>(2);
        lemma1 = "SHOE";
        lemma2 = "BRUSH";
        
        page_title_t12 = "page_title_SHOE_BRUSH";
        
        Page.insert(conn, page_title_t12, 2);    // int word_count = 2;
        Page p = Page.get(conn, page_title_t12);
        
        Term.incDocFreq(conn, null, lemma1, doc_freq_t, 0);
        Term.incDocFreq(conn, null, lemma2, doc_freq_t, 0);
        
        t1 = Term.get(conn, lemma1);
        t2 = Term.get(conn, lemma2);
        
        tp_list.add(new TermPage());
        tp_list.add(new TermPage());
        tp_list.get(0).setTerm(t2);
        tp_list.get(1).setTerm(t1);
    }

    protected void tearDown() throws Exception {
        Term.delete(idfsimplewiki_conn.conn, lemma1);
        Term.delete(idfsimplewiki_conn.conn, lemma2);
        Page.delete(idfsimplewiki_conn.conn, page_title_t12);
        
        idfruwiki_conn.Close();
        idfsimplewiki_conn.Close();
    }

    /**
     * Test of incLemmaDocFreq method, of class sql_idf.Term.
     */
    public void testIncLemmaDocFreq_one_element_simple() {
        System.out.println("incLemmaDocFreq_one_element_simple");
        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        String page_title = "testIncLemmaDocFreq_one_element_simple";
        String lemma = "test_term.get";
        
        int doc_freq_max = 10;
        int term_freq = -999;
        int word_count = 0;
        Term t = null;
        
        Term.delete(conn, lemma);
        t = Term.get    (conn, lemma);
        assertEquals(null, t);
        
        Page.delete(conn, page_title);
        Page p = Page.get(conn, page_title);
        assertEquals(null, p);
        
        p = Page.getOrInsert(conn, page_title, word_count);
        int page_id = p.getPageID();
        assertTrue(page_id != 0);
        
        int add_term_freq = 7;
        wikipedia.sql_idf.Term.incLemmaDocFreq(conn, lemma, add_term_freq, p, doc_freq_max);
        
        t = Term.get    (conn, lemma);
        int term_id = t.getTermID();
        p = Page.getOrInsert(conn, page_title, word_count);
        page_id = p.getPageID();
        term_freq = TermPage.getTermFreqInDocument(conn, term_id, page_id);
        assertEquals(add_term_freq, term_freq);
        assertEquals(word_count, p.getWordCount());
        
        word_count = 33;
        p = Page.getOrInsert(conn, page_title, word_count);
        p.storeWordCount(conn, word_count);
        
        wikipedia.sql_idf.Term.incLemmaDocFreq(conn, lemma, add_term_freq, p, doc_freq_max);
        
        term_freq = TermPage.getTermFreqInDocument(conn, term_id, page_id);
        assertEquals(add_term_freq, term_freq);
        assertEquals(word_count, p.getWordCount());
        
        // delete
        //TermPage.delete(conn, term_id, page_id);
        Term.delete(conn, lemma);
        Page.delete(conn, page_title);
        TermPage.delete(conn, term_id, page_id);
    }

    
    public void testGet_ru() {
        System.out.println("get_ru");
        Term t = null;
        
        String lemma = "test_term.get";
        int inc_corpus_freq = 55;
        Term.delete     (idfruwiki_conn.conn, lemma);
        t = Term.get    (idfruwiki_conn.conn, lemma);
        assertEquals(null, t);
        
        Term.incDocFreq (idfruwiki_conn.conn, t, lemma, 1, inc_corpus_freq); // INSERT, t==null
        t = Term.get    (idfruwiki_conn.conn, lemma);
        assertFalse(null == t);
        assertEquals(1,                 t.getDocFreq());
        assertEquals(inc_corpus_freq,   t.getCorpusFreq());
        
        inc_corpus_freq = 5;
        Term.incDocFreq (idfruwiki_conn.conn, t, lemma, 3, inc_corpus_freq);
        t = Term.get    (idfruwiki_conn.conn, lemma);
        Term.incDocFreq (idfruwiki_conn.conn, t, lemma, 6, inc_corpus_freq);
        t = Term.get    (idfruwiki_conn.conn, lemma);
        assertFalse(null == t);
        assertEquals(10,     t.getDocFreq());
        assertEquals(65,    t.getCorpusFreq());
        
        Term.delete     (idfruwiki_conn.conn, lemma);
        t = Term.get    (idfruwiki_conn.conn, lemma);
        assertEquals(null, t);
    }
    
      
    public void testFillTerms_simple() {
        System.out.println("fillTerms_simple");        
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        int df1, df2;
        
        t1.setDocFreq(0);
        t2.setDocFreq(0);
        df1 = t1.getDocFreq();
        df2 = t2.getDocFreq();
        assertEquals(0, df1);
        assertEquals(0, df2);
        Term.fillTerms (conn, tp_list);
        // tp_list lemmas: "SHOE" "BRUSH"
        df1 = t1.getDocFreq();
        df2 = t2.getDocFreq();
        assertEquals(doc_freq_t, df1);
        assertEquals(doc_freq_t, df2);
      }

    /**
     * Test of delete method, of class sql_idf.Term.
     */
    public void testDelete_simple() {
        System.out.println("delete_simple");
        Term t = null;
        java.sql.Connection conn = idfsimplewiki_conn.conn;

        // todo put something, get()!=null
        String lemma = "test_term.delete_simple";
        int inc_corpus_freq = 55;
        t = Term.get    (conn, lemma);
        assertEquals(null, t);
        
        int doc_freq = Term.incDocFreq (conn, t, lemma, 1, inc_corpus_freq);
        assertEquals(1, doc_freq);
        t = Term.get    (conn, lemma);
        assertFalse(null == t);
        assertEquals(1, t.getDocFreq());
        
        
        wikipedia.sql_idf.Term.delete(conn, lemma);
        t = Term.get    (conn, lemma);
        assertEquals(null, t);
    }
    
    /** Checks constraints "doc_freq_max" in incLemmaDocFreq */
    public void testIncLemmaDocFreq__doc_freq_max__simple() {
        System.out.println("incLemmaDocFreq__doc_freq_max__simple");
        String page_title1, page_title2, page_title3;
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        
        int doc_freq_max = 2;
        int n_rows_with_term_id, words_in_doc;
        
        words_in_doc = 3;
        
        t1.setDocFreq(0);
        t1.storeToDatabase(conn);
        
        t2.setDocFreq(0);
        t2.storeToDatabase(conn);
        
        // 1. success: 1 <= doc_freq_max
        page_title1 = "test_term.page_title1";
        Page p1 = Page.getOrInsert(conn, page_title1, words_in_doc);
        Term.incLemmaDocFreq(conn, t1.getLemma(), words_in_doc, p1, doc_freq_max);
        
        n_rows_with_term_id = TermPage.countPagesWithTerm(conn, t1.getTermID());
        assertEquals(1, n_rows_with_term_id);
        t1 = Term.get(conn, t1.getLemma());
        assertEquals(1, t1.getDocFreq());
        assertEquals(words_in_doc, t1.getCorpusFreq());
        
        // 2. success: 2 <= doc_freq_max
        page_title2 = "test_term.page_title2";
        Page p2 = Page.getOrInsert(conn, page_title2, words_in_doc+1);
        Term.incLemmaDocFreq(conn, t1.getLemma(), 2*words_in_doc, p2, doc_freq_max);
        
        n_rows_with_term_id = TermPage.countPagesWithTerm(conn, t1.getTermID());
        assertEquals(2, n_rows_with_term_id);
        t1 = Term.get(conn, t1.getLemma());
        assertEquals(2, t1.getDocFreq());
        assertEquals((1+2)*words_in_doc, t1.getCorpusFreq());
        
        
        // 3. fail: 3 > doc_freq_max => 2==n_rows_with_term_id
        String lemma = "t3 term_test";
        page_title3 = "test_term.page_title3";
        Page p3 = Page.getOrInsert(conn, page_title3, words_in_doc*3);
        Term.incLemmaDocFreq(conn, t1.getLemma(), 3*words_in_doc, p3, doc_freq_max);
        
        n_rows_with_term_id = TermPage.countPagesWithTerm(conn, t1.getTermID());
        assertEquals(2, n_rows_with_term_id);   // 2, not 3
        t1 = Term.get(conn, t1.getLemma());
        assertEquals(3, t1.getDocFreq());       // term.doc_freq = 3, real number of docs with term
        assertEquals((1+2+3)*words_in_doc, t1.getCorpusFreq());
        
        // delete
        wikipedia.sql_idf.Page.delete(conn, page_title1);
        wikipedia.sql_idf.Page.delete(conn, page_title2);
        wikipedia.sql_idf.Page.delete(conn, page_title3);
        
        wikipedia.sql_idf.TermPage.deleteByTermID(conn, t1.getTermID());
    }
}

