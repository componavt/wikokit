/* Keeper.java - interface between GATE's Lemmatizer and WikIDF database.
 * 
 * Copyright (c) 2005-2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikidf;

import wikipedia.language.LanguageType;
import wikipedia.sql.*;
import wikipedia.sql_idf.*;

import java.util.*;

import gate.*;
import gate.util.*;

/** Interface between GATE's Lemmatizer and WikIDF database.
 */
public class Keeper {
    private static final boolean DEBUG = false;
    
    public static class SortedAnnotationList extends Vector {
        public SortedAnnotationList() {
            super();
        }
 
        public boolean addSortedExclusive(Annotation annot) {
            Annotation currAnot = null;
 
            // overlapping check
            for (int i=0; i<size(); ++i) {
                currAnot = (Annotation) get(i);
                if(annot.overlaps(currAnot)) {
                    return false;
                }
            }
 
            long annotStart = annot.getStartNode().getOffset().longValue();
            long currStart;
            // insert
            for (int i=0; i < size(); ++i) {
                currAnot = (Annotation) get(i);
                currStart = currAnot.getStartNode().getOffset().longValue();
                if(annotStart < currStart) {
                    insertElementAt(annot, i);
                    /*
                    Out.prln("Insert start: "+annotStart+" at position: "+i+" size="+size());
                    Out.prln("Current start: "+currStart);
                    */
                    return true;
                }
            }
 
            int size = size();
            insertElementAt(annot, size);
            //Out.prln("Insert start: "+annotStart+" at size position: "+size);
            return true;
        } // addSortedExclusive
    } // SortedAnnotationList
    
    
    
    //private static List<String> lemma = new ArrayList<String>();
    /** map from term to term frequency in the document */
    private static Map<String, Integer> m_lemma_tf = new HashMap<String, Integer>();
    
    /** Stores lemmas, page_title to the database 'idf_conn'.
     *
     *  @param idf_conn     connection to idf database
     *  @param page_title   the title of wiki page
     */
    public static void storeIDFToDB(Corpus corpus,String page_title,Connect idf_conn,int doc_freq_max)
    {
        m_lemma_tf.clear();
        Iterator iter = corpus.iterator();

        if(iter.hasNext()) { // 1 document
            Document doc = (Document) iter.next();
            AnnotationSet defaultAnnotSet = doc.getAnnotations();
            Set annotTypesRequired = new HashSet();
            annotTypesRequired.add("Paradigm");

            // 1. pw annotation set: Paradigm (Wordforms are below)
            AnnotationSet pw = defaultAnnotSet.get(annotTypesRequired);
            //FeatureMap features = doc.getFeatures();
            
            if (null != pw) {
                //Out.prln("OrigContent existing. Generate list of lemmas...");
 
                Iterator it = pw.iterator();
                Annotation currAnnot;
                SortedAnnotationList sortedAnnotations = new SortedAnnotationList();
 
                while(it.hasNext()) { // add one annotation: Paradigm
                    currAnnot = (Annotation) it.next();
                    sortedAnnotations.addSortedExclusive(currAnnot);
                }
                
                StringBuffer lemma_cur = new StringBuffer();
                
                // annotation tags
                if(DEBUG) {
                    Out.pr("Unsorted annotations: "+pw.size());
                    Out.pr("; Sorted: "+sortedAnnotations.size());
                }
                for(int i=sortedAnnotations.size()-1; i>=0; --i) {
                    currAnnot = (Annotation) sortedAnnotations.get(i);
                    
                    FeatureMap f = currAnnot.getFeatures();

                    //String gc = (String)f.get("gram_codes");
                    //String pid = (String)f.get("paradigm_id");

                    //String word = "";
                    //if(f.containsKey("word")) {
                    //    word = (String)f.get("word");
                    //    Out.prln("word : "+word);
                    //}

                    if(f.containsKey("lemma")) {
                        lemma_cur.setLength(0);
                        lemma_cur.append( (String)f.get("lemma") );
                        //if(!lemma.contains(lemma_cur.toString())) {
                        //    lemma.add(lemma_cur.toString());
                        //}
                        
                        if(!m_lemma_tf.containsKey(lemma_cur.toString())) {
                            m_lemma_tf.put(lemma_cur.toString(), 1);
                        } else {
                            int term_freq = m_lemma_tf.get(lemma_cur.toString());
                            m_lemma_tf.put(lemma_cur.toString(), term_freq+1);
                        }
                        
                        //if(DEBUG) {
                        //    Out.prln("lemma : "+lemma_cur.toString());
                        //}
                    }
                } // for
            } // if
            else {
                Out.prln("The text has no known words. The text is empty, or check the language of dictionary.");
            }
            
            
            // 2. Wordform (new words whithout paradigms in Lemmatizer DB)
            annotTypesRequired.clear();
            annotTypesRequired.add("Wordform");
            pw = defaultAnnotSet.get(annotTypesRequired);
            
            if (null != pw) {
                Iterator it = pw.iterator();
 
                while(it.hasNext()) { // add one annotation: Wordform
                    Annotation currAnnot = (Annotation) it.next();
                    FeatureMap f = currAnnot.getFeatures();
                    
                    if(!f.containsKey("paradigm_id") && f.containsKey("word")) {
                        StringBuffer lemma_cur = new StringBuffer();
                        lemma_cur.setLength(0);
                        lemma_cur.append( (String)f.get("word") );

                        if(!m_lemma_tf.containsKey(lemma_cur.toString())) {
                            m_lemma_tf.put(lemma_cur.toString(), 1);
                        } else {
                            int term_freq = m_lemma_tf.get(lemma_cur.toString());
                            m_lemma_tf.put(lemma_cur.toString(), term_freq+1);
                        }
                    }
                } // while
            } // if
            
            if(DEBUG) {
                Out.prln("Number of unique lemma: "+m_lemma_tf.size());
                Out.prln("unique lemma; term frequency (in document) ");
                for(String s:m_lemma_tf.keySet()) {
                    Out.prln(s + "; " + m_lemma_tf.get(s));
            }   }
            
            Term.incLemmataDocFreq(idf_conn.conn, m_lemma_tf, 
                                page_title, doc_freq_max);
        }
    }
    

    /** Takes text (by page_title) from Wikipedia, parses it, stores lemmas 
     * to the IDF database.
     *
     *  @param wp_conn      connection to Wikipedia database
     * 
     *  @param page_title   title of the page in WP
     * 
     *  @param wiki_lang    defines parsed wiki language, it is needed to remove 
     * category for the selected language, e.g. English (Category) or Esperanto 
     * (Kategorio).<br>
     * 
     * * @param   b_remove_not_expand_iwiki if true then it removes interwiki, 
     * e.g. "[[et:Talvepalee]] text" -> " text"; else it expands interwiki by 
     * removing interwiki brackets and language code, 
     * e.g. "[[et:Talvepalee]] text" -> "Talvepalee text".<br>
     *
     *  @param idf_conn     connection to IDF database
     * 
     *  @param doc_freq_max  the limit for the table term_page. If number 
     *  of documents which contain a lemma (e.g. lemma "website") > doc_freq_max, 
     *  then 
     *  (1) table term_page.(term_id and page_id) contains only ID of first 
     *      doc_freq_max documents which contain the term;
     *  (2) but term.doc_freq can be > doc_freq_max<br>
     */
    public static void parseFromWP (
            Connect wp_conn, String page_title, 
            LanguageType wiki_lang, boolean b_remove_not_expand_iwiki,
            Connect idf_conn,Corpus corpus,StandAloneRussianPOSTagger prs,
            int doc_freq_max)
    throws GateException
    {
            //StringBuffer str = new StringBuffer();
            //StringBuffer str = new StringBuffer("sss test");
            //str = getWikiText(); // temp stub
            StringBuffer str = new StringBuffer(
                    //StringUtil.escapeCharDollar(
                    PageTable.getArticleText(wp_conn, page_title));
            
            if(0 == str.length()) {
                Out.prln("Error in Keeper.parseFromWP(): The article with the title '"+
                        page_title + "' has no text in Wikipedia.");
                return;
            }
            str = wikipedia.text.WikiParser.convertWikiToText(str, wiki_lang, b_remove_not_expand_iwiki);
            assert(null != str && 0 < str.length());
            
            if(0 == str.length()) {
                Out.prln("Warning in Keeper.parseFromWP(): The article with the title '"+
                        page_title + "' after convert wiki to text: has no text.");
                return;
            }
            
            //for(int i = 0; i < args.length; i++) {
                //URL u = new URL(args[i]);
                FeatureMap params = Factory.newFeatureMap();
                //params.put("sourceUrl", u);
                params.put("stringContent", str.toString());
                params.put("preserveOriginalContent", new Boolean(false));
                params.put("collectRepositioningInfo", new Boolean(false));
                //Out.prln("Creating doc for " + u);
                Document doc = (Document)
                    Factory.createResource("gate.corpora.DocumentImpl", params);
                corpus.add(doc);
            //} // for each of args

            // tell the pipeline about the corpus and run it
            prs.setCorpus(corpus);
            prs.execute();

            // store results to tables: term, page, term_page 
            Keeper.storeIDFToDB(corpus, page_title, idf_conn, doc_freq_max);
            
            corpus.remove(doc);
            Factory.deleteResource(doc);
    }
    
    
    // temp stub
    private static StringBuffer getWikiText() {
        // example from http://en.wikipedia.org/wiki/Winter_Palace
        return new StringBuffer("The '''Winter Palace''' ([[Russian language|Russian]]: \u0417\u0438\u043c\u043d\u0438\u0439 \u0414\u0432\u043e\u0440\u0435\u0446) is a place in [[Saint Petersburg|St. Petersburg]], [[Russia]], where [[Tsar]]s (Russian kings) could stay during [[winter]]. It was between the shores of the [[Neva River]] and the [[Palace Square]] and built between [[1754]] and [[1762]]." +
        "{{stub}} " +
        "[[Category:Russia]] " +
        "[[zh:\u51ac\u5bab]]");
    }
    
}
