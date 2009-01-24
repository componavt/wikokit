/* 
 * TermPage.java
 * 
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.sql_idf;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

/** Routines to work with the table term_page in wiki idf database.
 */
public class TermPage {
    
    /** term identifier */
    private Term    term;
    //public int    term_id;
    
    /** document identifier */
    private Page    page;
    //public int    page_id;
    
    /** frequency of term (with term_id) in the document (with page_id) */
    private int     term_freq;
    
    /** term frequency * inverse document frequency */
    private double     tf_idf;
    
    private void init() {
        term_freq = 0;
        tf_idf    = 0;
    }
    
    /** Creates a new instance of TermPage */
    public TermPage() {
        term = new Term();
        page = new Page();
        init();
    }
    
    /** To share one page for all terms within the same page */
    public TermPage(Page _page) {
        term = new Term();
        page = _page;
        init();
    }
    
    /** To share one term for all pages which contain the same term */
    public TermPage(Term _term) {
        term = _term;
        page = new Page();
        init();
    }
    
    private final static List<TermPage>     NULL_TERMPAGE_LIST   = new ArrayList<TermPage>(0);
    private final static Map<Integer, TermPage> NULL_INTEGER_TERMPAGE_MAP   = new HashMap<Integer, TermPage>(0);
    private final static Map<Integer, Term> NULL_INTEGERTERM_MAP = new HashMap<Integer, Term> (0);
    private final static Map<Integer, Page> NULL_INTEGERPAGE_MAP = new HashMap<Integer, Page> (0);
    
    
    /** Gets term */
    public Term getTerm()   { return term;  }
    /** Sets term */
    public void setTerm(Term _term)
                            { term = _term; }
    /** Gets term identifier */
    public int getTermID()  { return term.getTermID();  }
    /** Sets term identifier */
    public void setTermID(int _term_id)
                            { term.setTermID(_term_id); }
    /** Gets frequency of the term (with term_id) in the document (with page_id) */
    public int getTermFreq(){ return term_freq;         }
    /** Sets frequency of the term (with term_id) in the document (with page_id) */
    public void setTermFreq(int _term_freq)
                            { term_freq = _term_freq;   }
    
    /** Gets value of term frequency * inverse document frequency */
    public double getTF_IDF(){ return tf_idf;           }
    /** Sets value of term frequency * inverse document frequency */
    public void setTF_IDF(double _tf_idf)
                             { tf_idf = _tf_idf;        }
    
    /** Gets page */
    public Page getPage()       { return page;                  }
    /** Gets page identifier */
    public int getPageID()      { return page.getPageID();      }
    /** Gets the title of the page */
    public String getPageTitle(){ return page.getPageTitle();   }
    /** Gets a number of words in the article */
    public int getPageWordCount(){ return page.getWordCount();  }
    
     /** Gets term frequency by page ID and term ID from the table term_page.
     *
     *  SQL:
     *  SELECT term_freq FROM term_page WHERE term_id=1 AND page_id=1;
     *
     *  @return  term frequency, 0 if it is absent in the table
     */
    public static int getTermFreqInDocument (java.sql.Connection conn,int term_id,int page_id) {
        int term_freq = 0;
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = conn.createStatement ();
            // term_id, lemma, doc_freq
            
            str_sql.append("SELECT term_freq FROM term_page WHERE term_id=");
            str_sql.append(term_id);
            str_sql.append(" AND page_id=");
            str_sql.append(page_id);
            s.executeQuery (str_sql.toString());
            
            rs = s.getResultSet ();
            if (rs.next ())
            {
                term_freq = rs.getInt("term_freq");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikidf TermPage.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return term_freq;
    }
    
    
    /** Gets list of term ID by page ID from the table term_page.
     * Only one field of Term.term_id is filled here, 
     * others (lemma, doc_freq) are empty.
     * 
     *  SQL:
     *  SELECT term_id, term_freq FROM term_page WHERE page_id=29243
     *
     *  @return  terms, or empty array if they are absent
     */
    public static List<TermPage> getTermsByPageID (java.sql.Connection conn,int page_id) {
        
        List<TermPage> result = NULL_TERMPAGE_LIST;
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = conn.createStatement ();
            // term_id, term_freq
            //SELECT term_freq FROM term_page WHERE term_id
            str_sql.append("SELECT term_id, term_freq FROM term_page WHERE page_id=");
            str_sql.append(page_id);
            s.executeQuery (str_sql.toString());
            
            rs = s.getResultSet ();
            if (rs.next ())
            {
                result = new ArrayList<TermPage>();
                wikipedia.sql_idf.Page p = new Page();
                p.setPageID(page_id);
                // p.setPageTitle(_page_title);
                do {
                    TermPage tp = new TermPage(p);
                    tp.term.setTermID( rs.getInt("term_id") );
                    tp.term_freq = rs.getInt("term_freq");
                    result.add(tp);
                } while (rs.next ());
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikidf TermPage.java getTerms()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return result;
    }
    
    
    /** Gets list of pages which contain the term t.<br><br>
     * <PRE>
     *  A. SELECT * FROM term_page WHERE term_id=67657
     *          page_id, term_freq (sort by)
     *
     *  B. SELECT * FROM page WHERE page_id IN (8772, ... )
     *          page_title, word_count
     *</PRE>
     *  @return  pages, or empty array if they are absent
     */
    public static List<TermPage> getPagesByTermID (java.sql.Connection conn,Term t) {
        
        if(null == t) {
            return NULL_TERMPAGE_LIST;
        }
        
        int doc_freq = t.getDocFreq();
        int term_id  = t.getTermID();
        
        if(doc_freq <= 0 || term_id <= 0) {
            return NULL_TERMPAGE_LIST;
        }
        
        List<TermPage> result = NULL_TERMPAGE_LIST;
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = conn.createStatement ();
            str_sql.append("SELECT page_id, term_freq FROM term_page WHERE term_id=");
            str_sql.append(term_id);
            s.executeQuery (str_sql.toString());
            
            rs = s.getResultSet ();
            if (rs.next ())
            {
                result = new ArrayList<TermPage>(doc_freq); // fine, the number of docs is known in advance
                do {
                    TermPage tp = new TermPage(t);
                    tp.page.setPageID( rs.getInt("page_id") );
                    tp.term_freq =     rs.getInt("term_freq");
                    result.add(tp);
                } while (rs.next ());
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikidf TermPage.java getPagesByTermID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return result;
    }
    
    /** Counts number of pages which contain the term with term_id, 
     * i.e. number of rows in the table term_page with term_id.<br>
     *
     *  SQL:
     *  SELECT COUNT(*) FROM term_page WHERE term_id=22;<br><br>
     *
     *  Attention: this value &lt;= doc_freq_max, see Term.incLemmaDocFreq.
     *  So if it's bigger then see value in table term.doc_freq
     */
    public static int countPagesWithTerm (java.sql.Connection conn,int term_id) {
        int doc_freq = 0;
        
        Statement   s = null;
        ResultSet   rs= null;
        int         size = 0;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = conn.createStatement ();
            
            str_sql.append("SELECT COUNT(*) AS doc_freq FROM term_page WHERE term_id=");
            str_sql.append(term_id);
            s.executeQuery (str_sql.toString());
            
            rs = s.getResultSet ();
            if (rs.next ())
            {
                doc_freq = rs.getInt("doc_freq");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikidf TermPage.java countPagesWithTerm()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        
        return doc_freq;
    }
    
    
    /** Deletes records with the term_id and page_id from the table term_page.
     * 
     * SQL example:
     * DELETE FROM term_page WHERE term_id=1 AND page_id=1;
     */
    public static void delete (java.sql.Connection conn,int term_id,int page_id) {
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        
        try 
        {
            s = conn.createStatement ();
            str_sql.append("DELETE FROM term_page WHERE term_id=");
            str_sql.append(term_id);
            str_sql.append(" AND page_id=");
            str_sql.append(page_id);
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikidf TermPage.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
    /** Deletes records with the term_id from the table term_page.
     * 
     * SQL example:
     * DELETE FROM term_page WHERE term_id=1;
     */
    public static void deleteByTermID (java.sql.Connection conn,int term_id) {
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        
        try 
        {
            s = conn.createStatement ();
            str_sql.append("DELETE FROM term_page WHERE term_id=");
            str_sql.append(term_id);
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikidf TermPage.java deleteByTermID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
    
    /** Inserts/updates term (term_id) frequency in document (page_id) in 
     * the table term_page.<br><br>
     * 
     * SQL example:
     * INSERT INTO term_page (term_id,page_id,term_freq) VALUES (1,2,7);
     * UPDATE term_page SET term_freq=7 WHERE term_id=1 AND page_id=2;
     * 
     * @param new_term_freq new value of the term frequency in the document
     */
    public static void updateTermFreq ( java.sql.Connection conn,
                                        int term_id,
                                        int page_id,
                                        int new_term_freq) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        int         term_freq_cur = -1;
        
        try 
        {
            s = conn.createStatement ();
            term_freq_cur = getTermFreqInDocument(conn, term_id, page_id);
            if (term_freq_cur == 0)
            {
                str_sql.append("INSERT INTO term_page (term_id,page_id,term_freq) VALUES (");
                str_sql.append(term_id);
                str_sql.append(",");
                str_sql.append(page_id);
                str_sql.append(",");
                str_sql.append(new_term_freq);
                str_sql.append(")");
            	
            } else  // UPDATE term_page SET term_freq=7 WHERE term_id=1 AND page_id=2;
            {
                str_sql.append("UPDATE term_page SET term_freq=");
                str_sql.append(new_term_freq);
                str_sql.append(" WHERE term_id=");
                str_sql.append(term_id);
                str_sql.append(" AND page_id=");
                str_sql.append(page_id);
            }
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikidf Term.java incDocFreq()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
    /** Intersects page titles of the two lists, term frequencies are summarized. 
     * @return common items of two lists
     */
    public static List<TermPage> intersectPageTitles (List<TermPage> l1, List<TermPage> l2) {
        
        List<TermPage> result = new ArrayList<TermPage>();
                
        Map<Integer,TermPage> m_page_id_to_l1 = TermPage.createMapPageIdToTermPage(l1);
        for(TermPage tp2:l2) {
            int id2 = tp2.getPageID();
            if(m_page_id_to_l1.containsKey(id2)) {
                int term_freq1 = m_page_id_to_l1.get(id2).getTermFreq();
                int term_freq2 = tp2.                     getTermFreq();
                tp2.setTermFreq( term_freq1 + term_freq2 );
                result.add(tp2);
            }
        }
        
        return result;
    }
    
    /** Creates map from terms' identifiers to terms */
    public static Map<Integer, Term> createMapIdTerm (List<TermPage> tp_list) {
        
        if(null == tp_list || 0 == tp_list.size())
            return NULL_INTEGERTERM_MAP;
        
        Map<Integer, Term> m = new HashMap<Integer, Term> ();
        
        for(TermPage tp:tp_list)
            m.put(tp.getTermID(), tp.getTerm());
        
        return m;
    }
    
    /** Creates map from pages' identifiers to these pages */
    public static Map<Integer, Page> createMapIdPage (List<TermPage> tp_list) {
        
        if(null == tp_list || 0 == tp_list.size())
            return NULL_INTEGERPAGE_MAP;
        
        Map<Integer, Page> m = new HashMap<Integer, Page> ();
        
        for(TermPage tp:tp_list)
            m.put(tp.getPageID(), tp.getPage());
        
        return m;
    }
    
    /** Creates map from pages' identifiers to these TermPages */
    public static Map<Integer, TermPage> createMapPageIdToTermPage (List<TermPage> tp_list) {
        
        if(null == tp_list || 0 == tp_list.size())
            return NULL_INTEGER_TERMPAGE_MAP;
        
        Map<Integer, TermPage> m = new HashMap<Integer, TermPage> ();
        
        for(TermPage tp:tp_list)
            m.put(tp.getPageID(), tp);
        
        return m;
    }
    
    /** Calculates TF*IDF for the terms of this page, writes to ->tf_idf. */
    protected static void calcTF_IDF (List<TermPage> tp_list, int n_total_pages) {
        
        for(TermPage tp:tp_list) {
            double idf = Math.log((double)(n_total_pages) / tp.getTerm().getDocFreq());
            tp.tf_idf = tp.term_freq * idf;
        }
    }
    
    /** Ordering: the items with highest value of TF*IDF will be in the begin of list */
    static final Comparator<TermPage> TF_IDF_ORDER = new Comparator<TermPage>() {
        public int compare(TermPage n1, TermPage n2) {
            if (n1.tf_idf > n2.tf_idf)
                return -1;
            return 1;
        }
    };

    /** Ordering: the items with highest value of TF will be in the begin of list */
    public static final Comparator<TermPage> TF_ORDER = new Comparator<TermPage>() {
        public int compare(TermPage n1, TermPage n2) {
            if (n1.term_freq > n2.term_freq)
                return -1;
            return 1;
        }
    };

}
