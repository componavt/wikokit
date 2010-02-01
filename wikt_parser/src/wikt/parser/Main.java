/* Main.java - main file for Wiktionary parsing.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.parser;

import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;


/** Wiktionary parser creates MySQL database (like WordNet) 
 * from Wiktionary MySQL dump file.
 * 
 * The wikt_parsed database should be created in advance, run script...
 * (See comments in WiktParser.java)
 * This program will fill this database by data from Wiktionary.
 */
public class Main {
    
    /**
     * @param args the command line arguments   todo
     * 
     * Run from the command-line, with a list of arguments:
     * <P><B>
     * java -jar "./wikt_parser/dist/wikt_parser.jar"
     * </B><BR>
     */
    public static void main(String[] args) {
        
        LanguageType wiki_lang;
        String  category_name;
        
        // Connect to Wiktionary database
        Connect wikt_conn = new Connect();
        
        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();
        
        /*
        // simple
        wiki_lang = LanguageType.simple;
        wikt_conn.Open(Connect.WP_HOST,Connect.WP_SIMPLE_DB,   Connect.WP_USER,    Connect.WP_PASS);
        wikt_parsed_conn.Open(IDF_SIMPLE_HOST, IDF_SIMPLE_DB, IDF_SIMPLE_USER, IDF_SIMPLE_PASS);
                                    // Category:Main page       - failed - too much articles
                                            // "Literature"     812 docs - OK
                                            // "Folklore"       29 docs
                                            // "American_poets" 9 docs  - OK
        */
        
        // russian
        /* wiki_lang = LanguageType.ru;
        wikt_conn.Open       (Connect.RUWIKT_HOST,        Connect.RUWIKT_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, wiki_lang);
        wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, wiki_lang);
        */ 
        
        // russian
        wiki_lang = LanguageType.en;
        wikt_conn.Open       (Connect.ENWIKT_HOST,        Connect.ENWIKT_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, wiki_lang);
        wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, wiki_lang);

        category_name = "Викисловарь:Избранные статьи";
            // "Викисловарь:Избранные статьи";
            // "Слово дня";
            // "Статья недели", "Слово дня"
            // "Кандидаты в избранные статьи", "Статьи со ссылками на Википедию"
            // "Статьи с звучащими примерами произношения", "Статьи с иллюстрациями", 
        
        WiktParser w = new WiktParser();
//        w.runSubCategories(wiki_lang, wikt_conn, wikt_parsed_conn, category_name);
        
        int n_start_from = 0;
        PageTableAll.parseAllPages(wiki_lang, wikt_conn, wikt_parsed_conn, n_start_from);
        
        wikt_conn.Close();
        wikt_parsed_conn.Close();
    }
    
    

}
