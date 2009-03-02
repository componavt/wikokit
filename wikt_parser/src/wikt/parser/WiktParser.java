/* WiktParser.java - second main file for Wiktionary parsing.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.parser;

import java.util.*;
//import java.io.*;

import wikt.word.*;
import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;
import wikipedia.category.CategoryHyponyms;
import wikt.mrd.Keeper;
import wikt.sql.TLang;

/** Top level functions for Wiktionary parsing.
 *
 */
public class WiktParser {
    private static final boolean DEBUG = true;

/* 
Clear the export of MySQL Workbench Visual
 %s/`mydb`.//g
 del "CREATE SCHEMA..."
 
add first two lines:
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;

add last line:
SET character_set_client = @saved_cs_client;
 
mysql>
  

    use ruwikt20080620_parsed
or  use enwikt20080525_parsed
source ./wikt_parser/doc/wikt_parsed_empty.sql
 * 
 * ruwikt20080620_parsed
 */
    
    /** Parses the set of Wiktionary pages, 
     * stores result to wikt_parsed database.
     * 
     * @param   wiki_lang defines parsed wiki language, it is needed, e.g.,
     * in order to recognize categories for the selected language, 
     * e.g. English (Category) or Esperanto (Kategorio).<br>
     * 
     * @param   wikt_conn       connection to Wiktionary database
     * @param   wikt_parsed_conn connection to database that contains results
     * of parsing
     * 
     * @param   category_name   articles of this category and subcategories 
     * are parsed
     * 
     * 
     * ?????????
     * DECLARE cur1 CURSOR FOR SELECT page_namespace, page_title, page_is_redirect FROM page WHERE page_id=5865;
     * OPEN cur1;
     * FETCH cur1 INTO var1, var2, var3
     * CLOSE cur1;
     */
    public void runSubCategories(
                    LanguageType wiki_lang,
                    Connect wikt_conn,
                    Connect wikt_parsed_conn,
                    String category_name
                    )
// w.runSubCategories(wiki_lang, wikt_conn, wikt_parsed_conn);
    { 
        long    t_start, t_end;
        float   t_work;
        t_start = System.currentTimeMillis();

        TLang.recreateTable(wikt_parsed_conn);

        // 1. get wiki-text from MySQL database
        // variant A. Get all articles
        // todo
        
        // variant B. Get all articles which belongs to the category or its 
        //            subcategories. Skip redirects. Disambig?
        //            (1. Finds all, return. 2. The iterator returns the next 
        //            article which is not parsed (it's absent in idf database.)
        
        //int max_docs = 9000;
        int cur_doc = 0;
        System.out.println("Parsing of documents:");
        //String[] pt3 = {"яблоко", "ангел", "самолёт", "order", "lead"};
                                            // Category:Main page       - failed - too much articles
                                            // "Literature"     812 docs - OK
                                            // "Folklore"       29 docs
                                            // "American_poets" 9 docs  - OK
        List<String> pt = CategoryHyponyms.getArticlesOfSubCategories(wikt_conn, category_name); //"Яблоки"
        System.out.println("Total documents: " + pt.size());
        for(String page_title:pt) {
            //page_title = "яблоко";         // ангел  самолёт коса
            //page_title = "Bolesław_Prus";
            //if(++ cur_doc > max_docs) {
            //if(++ cur_doc > 1) {
            //    break;
            //}
            
            //page_title = pt3[cur_doc]; // "Will_o'_the_wisp"; // "Momotarō";    // id=68417
            if(DEBUG) {
                System.out.print(" "+cur_doc+": "+page_title + " ");
            }
            
            // gets Wiktionary article text
            StringBuffer str = new StringBuffer( //StringUtil.escapeCharDollar(
                    PageTableBase.getArticleText(wikt_conn, page_title));
            
            if(0 == str.length()) {
                System.out.println("Error in WiktParser.runSubCategories(): The article with the title '"+
                        page_title + "' has no text in Wiktionary.");
                return;
            }
              
            // parses wiki text 'str', stores to the object 'word'
            WordBase word = new WordBase(page_title, wiki_lang, str);
            
            if(word.isEmpty()) {
                System.out.println("Warning in WiktParser.runSubCategories(): The article with the title '"+
                        page_title + "' after convert wiki to text: has no text.");
                return;
            }
            
            // store results to tables: pos_term, meaning, synonyms...
            Keeper.storeToDB(wikt_parsed_conn, word);
        }
                
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        System.out.println("\n\nTime sec:" + t_work + 
                "\ndocuments: " + pt.size());
    }

}
