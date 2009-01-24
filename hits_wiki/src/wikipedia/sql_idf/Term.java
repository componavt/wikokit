/*
 * Term.java
 *
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.sql_idf;

//import wikipedia.sql.Connect;

import java.util.*;
import java.sql.*;


/** Routines to work with the table term in wiki idf database.
 */
public class Term {
    
    /** term identifier */
    private int     term_id;
    
    /** lemma (term) */
    private String  lemma;
    
    /** documents frequency - number of documents which contain the term */
    private int     doc_freq;
    
    /** corpus frequency - the frequency of the term in the corpus */
    private int     corpus_freq;
    
    private final static int LEMMA_LEN_MAX = 253;
    private final static int LEMMA_LEN_WARNING = 100;
    
    
    /** Gets term identifier */
    public int  getTermID() { return term_id;       }
    /** Sets term identifier */
    public void setTermID(int _term_id)
                            { term_id = _term_id;   }
    /** Gets lemma */
    public String getLemma(){ return lemma; }
    /** Sets lemma */
    public void   setLemma( String    _lemma)   
                            { lemma = _lemma; }
    /** Gets the document frequency - number of documents which contain the term */
    public int  getDocFreq() { return doc_freq;}
    
    /** Sets the document frequency - number of documents which contain the term */
    public void setDocFreq(int _doc_freq) { 
        doc_freq = _doc_freq;
    }
    /** Stores (updates) the fields 'doc_freq' and 'corpus_freq' to the table 'term',
     * but the row with the 'lemma' should exist in the 'term' table already.
     */
    public void storeToDatabase(java.sql.Connection conn) { 
        update (conn, lemma, doc_freq, corpus_freq);
    }
    
    /** Gets the frequency of the term in the corpus*/
    public int  getCorpusFreq() { return corpus_freq;}
    /** Sets the frequency of the term in the corpus */
    public void setCorpusFreq(int _corpus_freq) { corpus_freq = _corpus_freq;}
    
    
    /** Selects data from the table term by the lemma.
     *
     *  SQL:
     *  SELECT term_id,doc_freq,corpus_freq FROM term WHERE lemma="test";
     *
     *  @param lemma     lemma
     *  @return Term    object with initialized fields: term_id, doc_freq, corpus_freq
     */
    public static Term get (java.sql.Connection conn,String lemma) {
        Term result = null;
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = conn.createStatement ();
            
            str_sql.append("SELECT term_id,doc_freq,corpus_freq FROM term WHERE lemma=\"");
            str_sql.append(lemma);
            str_sql.append("\"");
            s.executeQuery (str_sql.toString());
            
            rs = s.getResultSet ();
            if (rs.next ())
            {
                result = new Term();
                result.term_id      = rs.getInt("term_id");
                result.doc_freq     = rs.getInt("doc_freq");
                result.corpus_freq  = rs.getInt("corpus_freq");
                result.lemma = lemma;
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikidf Term.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return result;
    }
    
    
    /** Selects data into tp_list (lemma of term and number of documents with term) 
     * from the table term by the term_id.
     *<PRE>
     *  SQL:
     *  SELECT term_id, lemma, doc_freq, corpus_freq FROM term 
     *  WHERE term.term_id IN (559092, 607182, 515136)
     *</PRE>
     *  @param lemma    lemma
     *  @return Term    object with initialized fields: term_id, doc_freq, corpus_freq
     *  @see Page.fillPages similar function (the contrary)
     */
    public static void fillTerms (java.sql.Connection conn,List<TermPage> tp_list) {
        
        if(null == tp_list || 0 == tp_list.size())
            return;
                
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer sb = new StringBuffer();
        try {
            s = conn.createStatement ();
            sb.append("SELECT term_id,lemma,doc_freq,corpus_freq FROM term WHERE term_id IN (");
            
            // Prepare SQL IN(...) via tp_list[].term.term_id
            int len = tp_list.size()-1;
            for (int i=0; i<len; i++) {
                sb.append(tp_list.get(i).getTermID());
                sb.append(",");
            }
            sb.append(tp_list.get(len).getTermID()); // skip last comma
            sb.append(")");
            s.executeQuery (sb.toString());
            
            rs = s.getResultSet ();
            if (rs.next ())
            {   
                Map<Integer,Term> m_id_to_term = TermPage.createMapIdTerm(tp_list);
                
                do {
                    Term t = m_id_to_term.get(rs.getInt("term_id")); 
                    t.setDocFreq    (rs.getInt("doc_freq"));
                    t.setCorpusFreq (rs.getInt("corpus_freq"));
                    t.setLemma      (rs.getString("lemma"));
                } while (rs.next());
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikidf Term.java fillTerms()):: sql='" + sb.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
    
    /** Increments lemma document frequency, since lemmas were found in the 
     * wiki-document titled page_title. 
     * (pl. 'lemmata', since the map lemma_tf contains several lemmas)
     *
     *  @param lemma_tf     map from lemma to term frequency (in document)
     *  
     *  @param doc_freq_max  the database limit for the table term_page. 
     *  If number of documents which contain a lemma (e.g. lemma "website") 
     *  > doc_freq_max, 
     *  then 
     *  (1) table term_page.(term_id and page_id) contains only ID of first 
     *      doc_freq_max documents which contain the term;
     *  (2) term.doc_freq increments in any case, 
     *  i.e. term.doc_freq can be > doc_freq_max
     */
    public static void incLemmataDocFreq( java.sql.Connection connect_idf,
                                        //List<String> list_lemma, 
                                        Map<String, Integer> lemma_tf,
                                        String page_title,
                                        int doc_freq_max)
    {
        if(null == lemma_tf || 0 == lemma_tf.keySet().size())
            return;
        
        // count number of words in the article
        int word_count = 0;
        for(Integer n:lemma_tf.values()) {
            word_count += n;
        }
        
        Page p = Page.getOrInsert(connect_idf, page_title, word_count);
        p.storeWordCount(connect_idf, word_count);
        
        for(String l:lemma_tf.keySet()) {
            incLemmaDocFreq(connect_idf, l, lemma_tf.get(l), p, doc_freq_max);
        }
    }
    
    
    /** Increments lemma document frequency (+1) and sets term frequency for 
     * the document (=inc_corpus_freq), since lemma was found the same number in 
     * the wiki-document titled page_title, corpus_freq += inc_corpus_freq.<br><br>
     * 
     * 1. Adds page_title to the table page (if it's not exist).<br>
     * 2. Adds lemma to the table term  (if it's not exist).<br>
     * 3. Adds (term_id of lemma, page_id of page_title) to the table term_page,
     *    if number of (term_id, *) < doc_freq_max.
     * 
     * @param inc_corpus_freq the frequency of the term in the corpus should be
     *                      incremented by this value
     */
    public static void incLemmaDocFreq( java.sql.Connection connect_idf,
                                        String lemma, int inc_corpus_freq,
                                        Page    page,
                                        int doc_freq_max)
    {
        if (lemma.length() > LEMMA_LEN_WARNING) {
            System.out.println("Warning: wikidf.Term.incLemmaDocFreq() page_title="+
                    page.getPageTitle() + " has very long ("+lemma.length()+" characters) lemma="+lemma);
            if (lemma.length() > LEMMA_LEN_MAX) {
                return;
            }
        }
        
        Term t = get(connect_idf, lemma);
        
        int doc_freq = incDocFreq(connect_idf, t, lemma, 1, inc_corpus_freq); // inc_doc_freq=1, +1 document
        if( doc_freq <= doc_freq_max && null != page) {
            if(null == t) {
                t = get(connect_idf, lemma);
            }
            //??? int inc_corpus_freq_prev = TermPage.getTermFreqInDocument(connect_idf, t.term_id, page_id);
            //??? TermPage.updateTermFreq( connect_idf, t.term_id, page_id, inc_corpus_freq_prev + inc_corpus_freq);
            TermPage.updateTermFreq(connect_idf, t.term_id, page.getPageID(), inc_corpus_freq);
        }
    }
    
    /** Iserts/updates term record (=lemma, +=inc_doc_freq++, +=inc_corpus_freq). <br><br>
     * 1) Increments number of documents with term (+inc_doc_freq). <br>
     * 2) Increments number of frequency of the term in corpus (+inc_corpus_freq).<br><br>
     * 
     * SQL example:
     * INSERT INTO term (lemma, doc_freq, corpus_freq) VALUES ("apple", 1, 20);
     * UPDATE term SET doc_freq=5,corpus_freq=23 WHERE lemma="apple";
     * 
     * Test database content by
     * select * from term,page,term_page WHERE page.page_id=term_page.page_id AND term.term_id=term_page.term_id;
     *
     *  @param lemma        the lemma of term (word normalized form)
     * 
     *  @param inc_doc_freq increment the number of documents which contain the term 
     * 
     *  @param inc_corpus_freq increment the frequency of the term in the corpus
     *
     *  @return incremented value of document frequency for the lemma
     */
    public static int incDocFreq (java.sql.Connection conn, Term term_cur, 
                                  String lemma, 
                                  int inc_doc_freq, int inc_corpus_freq) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        int         _doc_freq = 0;
        
        if (null != term_cur)
        {            
            // UPDATE term SET doc_freq=5,corpus_freq=23 WHERE lemma="apple";
            _doc_freq = term_cur.doc_freq + inc_doc_freq;
            int cf = term_cur.corpus_freq + inc_corpus_freq;
            update (conn, lemma, _doc_freq, cf);
            
        } else 
        {
            try // null == term_cur
            {
                s = conn.createStatement ();
            
                str_sql.append("INSERT INTO term (lemma,doc_freq,corpus_freq) ");
                str_sql.append("VALUES (\"");
                str_sql.append(lemma);
                str_sql.append("\",");   
                str_sql.append(inc_doc_freq);
                str_sql.append(",");
                str_sql.append(inc_corpus_freq);
                str_sql.append(")");

                _doc_freq = 1;
            
                s.executeUpdate (str_sql.toString());
            }catch(SQLException ex) {
                System.err.println("SQLException (wikidf Term.java incDocFreq()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
            } finally {
                if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
                if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
            }
        }
        return _doc_freq;
    }
    
    
    /** Updates fields 'doc_freq' and 'corpus_freq' of the term identified by lemma.<br><br>
     
     * SQL example:
     * UPDATE term SET doc_freq=5,corpus_freq=23 WHERE lemma="apple";
     *
     *  @param lemma        the lemma of the term which fields will be updated
     *  @param doc_freq     new value of the number of documents which contain the term 
     *  @param corpus_freq  new value of frequency of the term in the corpus
     */
    private static void update (java.sql.Connection conn, String lemma, 
                                  int doc_freq, int corpus_freq)
    {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        
        try 
        {
            s = conn.createStatement ();
            str_sql.append("UPDATE term SET doc_freq=");
            str_sql.append(doc_freq);
            str_sql.append(        ",corpus_freq=");
            str_sql.append(                      corpus_freq);
            str_sql.append(" WHERE lemma=\"");
            str_sql.append(lemma);
            str_sql.append("\"");
            
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikidf Term.java update()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    

    /** Deletes records with the lemma from the table term.
     * 
     * SQL example:
     * DELETE FROM term WHERE lemma="apple";
     *
     *  @param lemma        the lemma of term (word normalized form)
     */
    public static void delete (java.sql.Connection conn,String lemma) {
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        
        try 
        {
            s = conn.createStatement ();
            // DELETE FROM term WHERE lemma="apple";
            str_sql.append("DELETE FROM term WHERE lemma=\"");
            str_sql.append(lemma);
            str_sql.append("\"");
            
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikidf Term.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

}
