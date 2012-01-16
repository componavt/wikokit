/* KWConstants.java - Constants and global variables used in kiwidict.
 *
 * Copyright (c) 2011-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict; 

import wikipedia.language.LanguageType;


public class KWConstants {

    // GUI
    
    /** Width of word card. */
    ///public static int wordcard_width = 380; // old: wrapping_width
    ///public static int wordcard_min_width = 220;
    
    /** Width of word card. */
    ///public static int wordcard_height = 600;
    ///public static int wordcard_min_height = 120;

    public static Boolean DEBUGUI = false;

    /** Number of words visible in the list */
    public static int n_words_list = 31;
    
    /** Number of languages sorted by size (in dropdown list) */
    // // public static int n_language_list_by_size = 200; // 100;
    // see LangChoice.fillChoiceBoxByLanguages
    
    // ===========
    // Wiktionary parsed database
    // ===========

    /** Database name and files name. */
    //String str_url = "http://wikokit.googlecode.com/files/wiwordik-ru.jnlp";
    public static String DB_URL = "http://wikokit.googlecode.com/files/ruwikt20110521_android_sqlite.zip";
    //ruwikt20110521_android_sqlite.zip
    public static String DB_DIR = "kiwidict";
    
    //public static String DB_NAME = "enwikt"; //"enwikt_mean_semrel_sqlite";
    public static String DB_NAME = "ruwikt20110521_android_sqlite.zip"; // Russian Wiktionary
    
    public static int MAX_NUMBER_DB_PARTS = 341; // Russian Wiktionary
    
    
    
    public static String kiwidict_version = "0.091";

    /** Skips #REDIRECT words if true. */
    public static boolean b_skip_redirects = false;
    
    //////////////////////////////
    // Release / publish parameters

    //var native_lang : LanguageType;
    //public static LanguageType native_lang = LanguageType.ru;
    //public static LanguageType native_lang = LanguageType.en;
    public static String native_lang_code = "ru"; // "en"
    
    /** If true, then SQLite database extracted from the .jar and stored
     * to the directory user.dir (Add .jar with SQLite database to the project).
     * If false, then SQLite database from the project local folder ./sqlite/
     */
    public static Boolean IS_RELEASE = true;

    //            eo Parameters //
    //////////////////////////////
}
