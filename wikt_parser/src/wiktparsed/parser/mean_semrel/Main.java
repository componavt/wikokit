/* Main.java - main file for Wiktionary (Meaning + Semantic relations) parsing.
 * 
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiktparsed.parser.mean_semrel;

import wikt.parser.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;


/** Parser (second generation parser)
 * 1. takes Wiktionary parsed database (wikt_parsed)
 * 2. extracts meaning with related semantic relations
 * 3. saves to the simplified Wiktionary parsed database (Second generation database).
 * 
 * The wikt_parsed database should be created in advance, 
 * see http://code.google.com/p/wikokit/wiki/File_wikt_parsed_empty_sql
 * (See comments in WiktParser.java)
 * 
 * The Wiktionary parsed database (filled by data from the Wiktionary) should be created before.
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
                
        // Connect to Wiktionary database
        Connect wikt_conn = new Connect();
        
        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();

        if(args.length != 2) {
            System.out.println("Wiktionary parser.\n" +
            "Usage:\n  run_wikt_parser.bat language_code n_start_from\n" +
                    "Arguments:\n" +
                    "  language_code - language code of MySQL Wiktionary database to be parsed\n" +
                    "  n_start_from - number of records in database to start from\n" +
                    "Examples: run_wikt_parser.bat en 0\n"
                    );
            return;
        }

        String s = args[0];
        if(!LanguageType.has(s)) {
            System.out.println("Error. Unknown language code '" + s + "'. Stop.");
            return;
        }
        LanguageType wiki_lang = LanguageType.get(s);
        System.out.println("OK. language code is '" + s + "'");

        int n_start_from = Integer.parseInt(args[1]);
        System.out.println("OK. n_start_from=" + n_start_from);

        // Russian
        if(LanguageType.ru == wiki_lang) {
            wikt_conn.Open       (Connect.RUWIKT_HOST,        Connect.RUWIKT_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, wiki_lang);
            wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, wiki_lang);
        } else {
            // English
            if(LanguageType.en == wiki_lang) {
                wikt_conn.Open       (Connect.ENWIKT_HOST,        Connect.ENWIKT_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, wiki_lang);
                wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, wiki_lang);
            } else {
                System.out.println("This language code ('" + s + "') is not supported yet. Stop.");
                return;
            }
        }        
        
        //String category_name = "Викисловарь:Избранные статьи";
        
        WiktParser w = new WiktParser();
//        w.runSubCategories(wiki_lang, wikt_conn, wikt_parsed_conn, category_name);
        

        PageTableAll.parseAllPages(wiki_lang, wikt_conn, wikt_parsed_conn, n_start_from);
        
        wikt_conn.Close();
        wikt_parsed_conn.Close();
    }
    
    

}
