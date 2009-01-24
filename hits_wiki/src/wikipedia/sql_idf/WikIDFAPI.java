/* WikIDFAPI.java - API of TF-IDF database of wiki.
 * 
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.sql_idf;

import java.util.*;

/** API of TF-IDF database of wiki. It's a higher level than 
 * the requests to a separate database tables (page, term).
 */
public class WikIDFAPI {
    
    private final static List<TermPage> NULL_TERMPAGE_LIST  = new ArrayList<TermPage>(0);
    private final static List<Page>     NULL_PAGE_LIST      = new ArrayList    <Page>(0);
    
    /** Gets all terms for the page titled page_title.
     */
    public static List<TermPage> getTerms (java.sql.Connection conn, String page_title) {
        
        // 1. SELECT * FROM page WHERE page_title="Japanese_tea_ceremony"
        // 2. SELECT * FROM term_page WHERE page_id=29243
        //          term_id
        // 3. SELECT * FROM term WHERE term.term_id IN (559092, 607182, 515136)
        //          lemma, doc_freq
        
        Page p = wikipedia.sql_idf.Page.get(conn, page_title);
        if(null == p || 0 == p.getPageID())
            return NULL_TERMPAGE_LIST;
        
        List<TermPage>       tp_list = TermPage.getTermsByPageID(conn, p.getPageID());
        Term.fillTerms(conn, tp_list);
        
        return tp_list;
    }
    
    /** Gets all terms for the page titled page_title, 
     * terms are sorted by IDF: first are the most rare (in corpus) words.
     * 
     * @param n_total_pages number of pages in the wiki corpus
     */
    public static List<TermPage> getTermsSortedByTF_IDF (java.sql.Connection conn, String page_title,
                                                      int n_total_pages) {
        
        List<TermPage> tp_list = WikIDFAPI.getTerms(conn, page_title);
        if(tp_list.size() > 1) {
            
            TermPage.calcTF_IDF(tp_list, n_total_pages);
            Collections.sort(tp_list, TermPage.TF_IDF_ORDER);
        }
        
        return tp_list;
    }
    
    /** Gets all pages which contain the term (lemma), pages are sorted by TF (term frequency).
     */
    public static List<TermPage> getPages (java.sql.Connection conn, String lemma) {
        
        // 1. SELECT * FROM term WHERE lemma="PROVINE"
        //          term_id, doc_freq, corpus_freq
        // set size(doc_freq)
        
        // 2. SELECT * FROM term_page WHERE term_id=67657
        //          page_id, term_freq (sort by)
        
        // 3. SELECT * FROM page WHERE page_id IN (8772, ... )
        //          page_title, word_count
        
        Term t = Term.get(conn, lemma);
        List<TermPage> tp_list = TermPage.getPagesByTermID(conn, t);
        if(tp_list.size() > 0) {
            Page.fillPages(conn, tp_list);
        }
        
        return tp_list;
    }
}
