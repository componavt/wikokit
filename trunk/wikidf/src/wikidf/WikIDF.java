/* WikIDF.java - main file for WP parsing.
 * 
 * Copyright (c) 2005-2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikidf;

//import wikidf.db.Term;
        
import wikipedia.sql.Connect;

import wikipedia.category.CategoryHyponyms;
import wikipedia.language.LanguageType;
//import wikipedia.util.StringUtil;
        
import java.util.*;
import java.io.*;

import gate.*;
import gate.util.*;

/**
 * 
 * This class calculates Inverse Document Frequency (IDF) for wiki-texts.
 * Wiki is stored in MySQL database. The result are stored in another MySQL
 * database (titled idfenwiki, or idfsimplewiki, or idfruwiki).
 * 
use idfsimplewiki
or use idfruwiki
source ./wikidf/doc/idfwiki_empty.sql

DELETE FROM page;
DELETE FROM related_page;
DELETE FROM term;
DELETE FROM term_page;


1) LEMMA -> PAGE
SELECT * FROM term WHERE lemma="НИЙЯ" LIMIT 0,5

2) page_title -> page_id -> list of lemma
SELECT * FROM page WHERE page_title="Через_тернии_к_звёздам_(фильм)" LIMIT 0,5
SELECT lemma FROM term, term_page WHERE page_id=193323 AND term.term_id=term_page.term_id

 */
public class WikIDF  {
    private static final boolean DEBUG = true;
    
    Connect connect_simple; //connect_ru;
    
    
    
    
    /** Calculated TF-IDF for set of Wikipedia documents, 
     * stores to wiki idf database.
     *
     * @param   dict_lang defines languages of dictionary for lemmatizing 
     * (at LemServer in RuPOSTagger), e.g. English, German or Russian.<br>
     *
     * @param   wiki_lang defines parsed wiki language, it is needed to remove 
     * category for the selected language, e.g. English (Category) or Esperanto 
     * (Kategorio).<br>
     *
     * @param   b_remove_not_expand_iwiki if true then it removes interwiki, 
     * e.g. "[[et:Talvepalee]] text" -> " text"; else it expands interwiki by 
     * removing interwiki brackets and language code, 
     * e.g. "[[et:Talvepalee]] text" -> "Talvepalee text".<br>
     *
     * @param doc_freq_max  the limit for the table term_page. If number 
     *  of documents which contain a lemma (e.g. lemma "website") > doc_freq_max, 
     *  then 
     *  (1) table term_page.(term_id and page_id) contains only ID of first 
     *      doc_freq_max documents which contain the term;
     *  (2) but term.doc_freq can be > doc_freq_max
     * 
     * DECLARE cur1 CURSOR FOR SELECT page_namespace, page_title, page_is_redirect FROM page WHERE page_id=5865;
     * OPEN cur1;
     * FETCH cur1 INTO var1, var2, var3
     * CLOSE cur1;
     */
    public void runSubCategories(DictLanguage dict_lang,
                    LanguageType wiki_lang,
                    boolean b_remove_not_expand_iwiki,
                    int doc_freq_max)
    throws GateException, IOException 
    {
        //String[] texts = new String[2];
        //texts[0] = "file:/mnt/win_e/all/projects/java/aot/gate/russian/embedRPOST/data/en/signatures_en.txt"; // long
        //texts[1] = "file:/mnt/win_e/projects/java/aot/rupostagger/data/ru/ABS_zmldks_short.txt";  // short
        //texts[0] = "file:/mnt/win_e/projects/java/aot/rupostagger/data/en/Winter_Palace.txt"; // simplewiki
        //args = texts;
        
        long    t_start, t_end;
        float   t_work;
        t_start = System.currentTimeMillis();
        
        // initialise the GATE library
        Out.prln("Initialising GATE...");
        Gate.init();
  
        // Load ANNIE plugin
        File gateHome = Gate.getGateHome();
        File pluginsHome = new File(gateHome, "plugins");
        Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "ANNIE").toURI().toURL());
        Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "RussianPOSTagger").toURI().toURL());
        Out.prln("...GATE initialised");  
 
        // initialise ANNIE (this may take several minutes)
        StandAloneRussianPOSTagger prs = new StandAloneRussianPOSTagger();
        prs.initPRs(dict_lang);
    
        // Connect to Wikipedia database
        connect_simple = new Connect();
        connect_simple.Open(Connect.WP_HOST,Connect.WP_SIMPLE_DB,   Connect.WP_USER,    Connect.WP_PASS);
        
        
        // Connect to wiki IDF database
        Connect idf_conn = new Connect();
        //idf_conn.Open(IDF_HOST, IDF_DB, IDF_USER, IDF_PASS);
        idf_conn.Open(Connect.IDF_SIMPLE_HOST, Connect.IDF_SIMPLE_DB, Connect.IDF_SIMPLE_USER, Connect.IDF_SIMPLE_PASS);
        
        // create a GATE corpus
        Corpus corpus = (Corpus) Factory.createResource("gate.corpora.CorpusImpl");
        
        // 1. get wiki-text from MySQL database
        // variant A. Get all articles
        // todo
        
        // variant B. Get all articles which belongs to the category or its 
        //            subcategories. Skip redirects. Disambig?
        //            (1. Finds all, return. 2. The iterator returns the next 
        //            article which is not parsed (it's absent in idf database.)
        
        //int max_docs = 9000;
        int cur_doc = 0;
        Out.prln("Parsing of documents:");
        //String[] pt3 = {"Saadi", "Omar_Khayyám", "Amir_Khosrow"};
                                            // Category:Main page       - failed - too much articles
                                            // "Literature"     812 docs - OK
                                            // "Folklore"       29 docs
                                            // "American_poets" 9 docs  - OK
        List<String> pt = CategoryHyponyms.getArticlesOfSubCategories(connect_simple, "American_poets");
        Out.prln("Total documents: " + pt.size());
        for(String page_title:pt) {
            //page_title = "List_of_Buffy_the_Vampire_Slayer_episodes";
            //page_title = "Bolesław_Prus";
            //if(++ cur_doc > max_docs) {
            //if(++ cur_doc > 1) {
            //    break;
            //}
            
            //page_title = pt3[cur_doc]; // "Will_o'_the_wisp"; // "Momotarō";    // id=68417
            if(DEBUG) {
                Out.prln("");
                Out.pr(" "+cur_doc+": "+page_title + " ");
            }
            
            Keeper.parseFromWP(
                connect_simple, page_title, 
                wiki_lang, b_remove_not_expand_iwiki,
                idf_conn, corpus, prs,
                doc_freq_max);
        }
        
        prs.deletePRs();
        
        idf_conn.Close();
        connect_simple.Close();
        
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        System.out.println("\n\nTime sec:" + t_work + 
                "\ndocuments: " + pt.size());        
    }
    
    /** Parses all pages in Wikipedia. */
    public void runAll(DictLanguage dict_lang,
                    LanguageType wiki_lang,
                    boolean b_remove_not_expand_iwiki,
                    int doc_freq_max,
                    Connect connect_wp, Connect idf_conn)
    throws GateException, IOException 
    {   
        // initialise the GATE library
        Out.prln("Initialising GATE...");
        Gate.init();
  
        // Load ANNIE plugin
        File gateHome = Gate.getGateHome();
        File pluginsHome = new File(gateHome, "plugins");
        Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "ANNIE").toURI().toURL());
        Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "RussianPOSTagger").toURI().toURL());
        Out.prln("...GATE initialised");  
 
        // initialise ANNIE (this may take several minutes)
        StandAloneRussianPOSTagger prs = new StandAloneRussianPOSTagger();
        prs.initPRs(dict_lang);
    
        // create a GATE corpus
        Corpus corpus = (Corpus) Factory.createResource("gate.corpora.CorpusImpl");
        
        // 1. get wiki-text from MySQL database
        // variant A. Get all articles
        
        //int max_docs = 9000;
        Out.prln("Parsing of documents:");
            
        PageTableAll.parseAllPages(
                connect_wp, 
                wiki_lang, b_remove_not_expand_iwiki,
                idf_conn, corpus, prs,
                doc_freq_max);
        
        prs.deletePRs();
    }
    
    
    /**
     * Run from the command-line, with a list of arguments:
     * <P><B>
     * java -Dgate.home=/opt/GATE-4.0 -Dgate.plugins.home=/opt/GATE-4.0/plugin -jar "/mnt/win_e/projects/java/aot/rupostagger/wikidf/dist/wikidf.jar"
     * </B><BR>
     * 
     */
    public static void main(String args[])
    throws GateException, IOException 
    {   
        DictLanguage dict_lang;
        LanguageType wiki_lang;
        
        // Connect to Wikipedia database
        Connect connect_wp = new Connect();
        // Connect to wiki IDF database
        Connect idf_conn = new Connect();
        /*
        // simple
        dict_lang = DictLanguage.get("ENGLISH");
        wiki_lang = LanguageType.simple;
        connect_wp.Open(Connect.WP_HOST,Connect.WP_SIMPLE_DB,   Connect.WP_USER,    Connect.WP_PASS);
        idf_conn.Open(IDF_SIMPLE_HOST, IDF_SIMPLE_DB, IDF_SIMPLE_USER, IDF_SIMPLE_PASS);
        */
        // russian
        dict_lang = DictLanguage.get("RUSSIAN");
        wiki_lang = LanguageType.ru;
        connect_wp.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,   Connect.WP_USER,    Connect.WP_PASS);
        idf_conn.Open(Connect.IDF_RU_HOST, Connect.IDF_RU_DB, Connect.IDF_RU_USER, Connect.IDF_RU_PASS);
        
        boolean b_remove_not_expand_iwiki = true;
        int     doc_freq_max = 1000; // 100
        
        WikIDF w = new WikIDF();
        //w.runSubCategories(dict_lang, wiki_lang, b_remove_not_expand_iwiki, doc_freq_max);
        w.runAll(dict_lang, wiki_lang, b_remove_not_expand_iwiki, doc_freq_max, 
                connect_wp, idf_conn);
        
        idf_conn.Close();
        connect_wp.Close();
    }
} // class StandAloneAnnie