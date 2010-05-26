/* ParsedDB.java - Statistics of the database of the parsed Wiktionary.
 *
 * Copyright (c) 2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.stat;

import wikipedia.language.LanguageType;

import wikipedia.sql.*;
import wikt.sql.*;

import java.sql.*;

/** Base parameters (number of records) of the database of the parsed Wiktionary.
 *
 * An example of the result table:
 * @see http://code.google.com/p/wikokit/wiki/Database_statistics
 *
 * The result could be inserted into the Wiktionary page.
 * @see todo
 */
public class ParsedDB {
    private static final boolean DEBUG = true;

    /** Prints a row in a wiki table with data:
     * (1) the name of the table, 'table_name';
     * (2) number of records in the table;
     * (3) the description.
     */
    public static void printRowWithTableSize(Connect conn,
                                String table_name,String description) {

        int size = Statistics.Count(conn, table_name);
        System.out.print("\n|-\n|| " + table_name + " || " + size + " || " + description);
    }
    
    public static void main(String[] args) {

        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();

        // Russian
        LanguageType native_lang = LanguageType.ru;
        wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);

        // English
     // LanguageType native_lang = LanguageType.en;
     // wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, LanguageType.en);
        
        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        TRelationType.createFastMaps(wikt_parsed_conn);

        String db_name = wikt_parsed_conn.getDBName();
        System.out.println("\n== Parameters of the created (parsed) Wiktionary database ==");
        
        WikiPrinterStat.printHeader (db_name);

        System.out.println("\n\"Table\" is a name of the table in the database.");
        System.out.println("\n\"Size\" is a number of records in the table.");

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: left;\"");
        System.out.print("! Table || Size || Table description ");

        //int page_size = Statistics.Count(wikt_parsed_conn, "page");
        //System.out.print("\n|-\n|| page || " + page_size + " || Number of words / entries");
        printRowWithTableSize(wikt_parsed_conn, "page", "Number of words / entries");
        printRowWithTableSize(wikt_parsed_conn, "relation", "Number of semantic relations, e.g. synonyms, antonyms, etc.");
        printRowWithTableSize(wikt_parsed_conn, "lang_pos", "Number of pairs: language & part of speech, one Wiktionary page can contain several such pairs.");
        printRowWithTableSize(wikt_parsed_conn, "wiki_text", "Number of meaning definitions + number of semantic relations phrases (divided by comma, semicolon) + number of wikified translations.");
        printRowWithTableSize(wikt_parsed_conn, "wiki_text_words", "Number of wikified words (in meaning definitions + in semantic relations + in translations).");
        printRowWithTableSize(wikt_parsed_conn, "meaning", "Number of meanings, one word can have several meanings / definitions.");
        printRowWithTableSize(wikt_parsed_conn, "inflection", "	It is extracted from wikified word definitions, e.g. <nowiki>[[</nowiki>'''inflection'''<nowiki>|normal form]]</nowiki>");
        printRowWithTableSize(wikt_parsed_conn, "translation", "Number of translation section boxes (at best: one translation box corresponds to one meaning).");
        printRowWithTableSize(wikt_parsed_conn, "translation_entry", "Number of different translations (pairs of translations).");

        // lang_pos with meaning || Number of words (pairs: language & part of speech) with non-empty meanings, definitions. It includes word forms.
        // todo may be

        System.out.println("\n|}");

        WikiPrinterStat.printFooter();

        wikt_parsed_conn.Close();
    }

}
