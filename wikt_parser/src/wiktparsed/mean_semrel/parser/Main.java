/* Main.java - main file for Wiktionary (Meaning + Semantic relations) parsing.
 * 
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiktparsed.mean_semrel.parser;

//import wikt.parser.*;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.language.LanguageType;


/** Parser (second generation parser)
 * 1. takes Wiktionary parsed database (wikt_parsed)
 * 2. extracts meaning with related semantic relations
 * 3. saves to the wikt_mean_semrel database.
 * 
 * The wikt_parsed database should be created in advance, 
 * see http://code.google.com/p/wikokit/wiki/File_mean_semrel_empty_sql
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
                
        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();
        
        // Connect to mean_semrel database
        Connect mean_semrel_conn = new Connect();

        if(args.length != 4) {
            System.out.println("Wiktionary parser.\n" +
            "Usage:\n  run_wikt_mean_semrel_parser.bat language_code n_start_from\n" +
                    "Arguments:\n" +
                    "  language_code - language code of MySQL Wiktionary database to be parsed\n" +
                    "  n_start_from - number of records in database to start from\n" +
                    "  delimiter - symbol between words in the table fields \"synonyms\", \"antonyms\", etc.\n" +
                    "  min_meaning - threshold, i.e. minimum number of records in mean_semrel_XX,\n" +
                    "    the lesser tables (mean_semrel_XX) and records (lang.XX) will be deleted,\n" +
                    "Examples: run_wikt_mean_semrel_parser.bat en 0 \"|\" 10\n"
                    );
            return;
        }
        
        String s = args[0];
        if(!LanguageType.has(s)) {
            System.out.println("Error. Unknown language code '" + s + "'. Stop.");
            return;
        }
        System.out.println("The wikt_mean_semrel database will be created from wikt_parsed database.\n");
        
        LanguageType wiki_lang = LanguageType.get(s);
        System.out.println("OK. language code is '" + s + "'");

        int n_start_from = Integer.parseInt(args[1]);
        System.out.println("OK. n_start_from=" + n_start_from);
        
        String delimiter = args[2];// e.g. "|" - symbol between words in the table fields "synonyms", "antonyms", etc.
        System.out.println("OK. delimiter is '" + delimiter + "'");
        
        // threshold, e.g. 10 - minimum number of records in mean_semrel_XX, 
        // the lesser tables (mean_semrel_XX) and records (lang.XX) will be deleted
        int min_meaning = Integer.parseInt(args[3]);
        System.out.println("OK. min_meaning=" + min_meaning);

        // Russian
        if(LanguageType.ru == wiki_lang) {
//            wikt_conn.Open       (Connect.RUWIKT_HOST,        Connect.RUWIKT_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, wiki_lang);
//            wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, wiki_lang);
        } else {
            // English
            if(LanguageType.en == wiki_lang) {
                wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, wiki_lang);
                
                mean_semrel_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_MEAN_SEMREL, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, wiki_lang);
            } else {
                System.out.println("This language code ('" + s + "') is not supported yet. Stop.");
                return;
            }
        }        
        
        //String category_name = "Викисловарь:Избранные статьи";
        
        SemrelParser p = new SemrelParser();
//       p.runSubCategories(wiki_lang, wikt_conn, wikt_parsed_conn, category_name);
        
        PageWithSemrel.parse(//wiki_lang, 
                    wikt_parsed_conn, mean_semrel_conn, n_start_from, 
                    delimiter, min_meaning);
        
        wikt_parsed_conn.Close();
        mean_semrel_conn.Close();
    }
    
    

}
