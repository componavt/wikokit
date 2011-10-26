/* ParsedDB.java - Statistics of the database of the parsed Wiktionary.
 *
 * Copyright (c) 2010-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.stat;

import wikt.stat.printer.general;
import wikipedia.language.LanguageType;

import wikipedia.sql.*;
import wikt.sql.*;

/** Base parameters (number of records) of the database of the parsed Wiktionary.
 *
 * An example of the result table:
 * @see http://code.google.com/p/wikokit/wiki/Database_statistics
 *
 * The result could be inserted into the Wiktionary page.
 * @see todo
 */
public class ParsedDB {
    //private static final boolean DEBUG = true;

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
        //wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);

        // English
        wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, LanguageType.en);
        
        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        TRelationType.createFastMaps(wikt_parsed_conn);

        String db_name = wikt_parsed_conn.getDBName();
        System.out.println("\n== Parameters of the created (parsed) Wiktionary database ==");
        
        general.printHeader (db_name);
        String empty_line = "\n|-\n|| || ||";

        System.out.println("\n'''Table''' is a name of the table in the database.");
        System.out.println("\n'''Size''' is a number of records in the table.");

        System.out.println("\nThe table filled automatically by [http://code.google.com/p/wikokit/source/browse/trunk/wikt_parser/src/wikt/stat/ParsedDB.java wikt.stat.ParsedDB] of the ''wiwordik'' project.\n");

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: left;\"");
        System.out.print("! Table || Size || Table description ");

        //int page_size = Statistics.Count(wikt_parsed_conn, "page");
        //System.out.print("\n|-\n|| page || " + page_size + " || Number of words / entries");
        printRowWithTableSize(wikt_parsed_conn, "page", "Number of words / entries");
        printRowWithTableSize(wikt_parsed_conn, "relation", "Number of semantic relations, e.g. synonyms, antonyms, etc.");
        printRowWithTableSize(wikt_parsed_conn, "lang_pos", "Number of pairs: language & part of speech, one Wiktionary page can contain several such pairs.");
        printRowWithTableSize(wikt_parsed_conn, "wiki_text", "Number of meanings / definitions + number of semantic relations phrases (divided by comma, semicolon) + number of wikified translations.");
        printRowWithTableSize(wikt_parsed_conn, "wiki_text_words", "Number of wikified words (in meanings / definitions + in semantic relations + in translations).");
        printRowWithTableSize(wikt_parsed_conn, "meaning", "Number of meanings, one word can have several meanings / definitions.");
        printRowWithTableSize(wikt_parsed_conn, "inflection", "It is extracted from wikified word definitions, e.g. <nowiki>[[normal form|</nowiki>'''inflection'''<nowiki>]]</nowiki>");

        System.out.print(empty_line);
        printRowWithTableSize(wikt_parsed_conn, "quote", "Number of quotations and examples, one meaning can have several quotes.");
        printRowWithTableSize(wikt_parsed_conn, "quot_translation", "Number of translations of quotes (quote in foreign languages can have translation).");
        printRowWithTableSize(wikt_parsed_conn, "quot_transcription", "Number of transcriptions of quotes.");
        printRowWithTableSize(wikt_parsed_conn, "quot_ref", "Number of unique quote references (author, title, year,...).");
        printRowWithTableSize(wikt_parsed_conn, "quot_author", "Number of authors of quotes.");
        printRowWithTableSize(wikt_parsed_conn, "quot_year", "Number of unique years (and range of years) of quotes.");
        printRowWithTableSize(wikt_parsed_conn, "quot_publisher", "Number of publishers of quotes.");
        printRowWithTableSize(wikt_parsed_conn, "quot_source", "Number of sources of quotes.");

        System.out.print(empty_line);
        printRowWithTableSize(wikt_parsed_conn, "translation", "Number of translation section boxes (at best: one translation box corresponds to one meaning).");
        printRowWithTableSize(wikt_parsed_conn, "translation_entry", "Number of different translations (pairs of translations).");

        // lang_pos with meaning || Number of words (pairs: language & part of speech) with non-empty meanings, definitions. It includes word forms.
        // todo may be

        System.out.println("\n|}");        

        general.printFooter();

        wikt_parsed_conn.Close();
    }

}
