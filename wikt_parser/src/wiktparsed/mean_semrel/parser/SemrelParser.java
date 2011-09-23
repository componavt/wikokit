/* WiktParser.java - second main file for Wiktionary parsing.
 * 
 * Copyright (c) 2008-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiktparsed.mean_semrel.parser;

import wiktparsed.mean_semrel.parser.sql.*;

import wikipedia.language.LanguageType;

import wikipedia.sql.Connect;

//import wikt.sql.TLang;
import wikt.sql.TPOS;
import wikt.sql.TRelationType;


/** Top level functions for Wiktionary parsing.
 */
public class SemrelParser {
    private static final boolean DEBUG = true;

    /* Recreate and fill the table wikt_mean_semrel.lang by data from LanguageType.java
     * Recreates tables mean_semrel_XX for each language code XX.
    */
    public static void clearDatabase (Connect wikt_parsed_conn, Connect mean_semrel_conn, LanguageType native_lang) {
        
        MSRLang.recreateTable(mean_semrel_conn);
        MSRLang.createFastMaps(mean_semrel_conn);

        TPOS.createFastMaps(wikt_parsed_conn);
        TRelationType.createFastMaps(wikt_parsed_conn);

        MSRMeanSemrelXX.generateTables(mean_semrel_conn, native_lang);
    }

    public static void initWithoutClearDatabase (Connect wikt_parsed_conn, Connect mean_semrel_conn, LanguageType native_lang) {
        MSRLang.createFastMaps(mean_semrel_conn);

        TPOS.createFastMaps(wikt_parsed_conn);
        TRelationType.createFastMaps(wikt_parsed_conn);
    }
    
    /** Parses one article.
     *
     * @param native_lang   native language in the Wiktionary,
     *                       e.g. Russian language in Russian Wiktionary
     * @param wikt_conn
     * @param wikt_parsed_conn
     * @param page_title
     */
    public static void parseWiktionaryEntry(
                    LanguageType native_lang,
                    Connect wikt_conn,
                    Connect wikt_parsed_conn,
                    String page_title
                    )
    {
        System.exit(0);
        // todo
        /*
        // gets Wiktionary article text
        StringBuffer str = new StringBuffer( //StringUtil.escapeCharDollar(
                PageTableBase.getArticleText(wikt_conn, page_title));

        if(0 == str.length()) {
            //System.out.println("Error in WiktParser.parseWiktionaryEntry(): The article with the title '"+
            //        page_title + "' has no text in Wiktionary.");
            return;
        }

        // converts "text_with_underscore" into the "text without underscore"
        page_title = page_title.replace("_", " ");

        // parses wiki text 'str', stores to the object 'word'
        WordBase word = new WordBase(page_title, native_lang, str);

        if(word.hasOnlyTemplatesWithoutDefinitions())
            return;

        if(word.isEmpty()) {
            System.out.println("Warning in WiktParser.parseWiktionaryEntry(): The article with the title '"+
                    page_title + "' after convert wiki to text: has no text.");
            return;
        }

        // store results to tables: pos_term, meaning, synonyms...
        Keeper.storeToDB(wikt_parsed_conn, word, native_lang);
        
        str.setLength(0);
        str = null;
         * 
         */
    }
}
