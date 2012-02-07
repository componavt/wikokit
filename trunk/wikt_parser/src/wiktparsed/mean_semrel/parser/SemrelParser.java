/* WiktParser.java - second main file for Wiktionary parsing.
 * 
 * Copyright (c) 2008-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiktparsed.mean_semrel.parser;

import wiktparsed.mean_semrel.parser.sql.*;

import wikokit.base.wikipedia.language.LanguageType;

import wikokit.base.wikipedia.sql.Connect;

//import wikt.sql.TLang;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TRelationType;


/** Top level functions for Wiktionary parsing.
 */
public class SemrelParser {
    private static final boolean DEBUG = true;

    /* Recreate and fill the table wikt_mean_semrel.lang by data from LanguageType.java
     * Recreates tables mean_semrel_XX for each language code XX.
    */
    public static void clearDatabase (Connect wikt_parsed_conn, Connect mean_semrel_conn) {
        
        MSRLang.recreateTable(mean_semrel_conn);
        MSRLang.createFastMaps(mean_semrel_conn);

        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        TRelationType.createFastMaps(wikt_parsed_conn);

        MSRMeanSemrelXX.generateTables(mean_semrel_conn);
    }

    public static void initWithoutClearDatabase (Connect wikt_parsed_conn, Connect mean_semrel_conn) {
        MSRLang.createFastMaps(mean_semrel_conn);

        TPOS.createFastMaps(wikt_parsed_conn);
        TRelationType.createFastMaps(wikt_parsed_conn);
    }
}
