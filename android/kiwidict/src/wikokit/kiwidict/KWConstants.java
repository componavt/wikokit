/* KWConstants.java - Constants and global variables used in kiwidict.
 *
 * Copyright (c) 2011-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict; 

import wikokit.kiwidict.db.DataBaseHelper;

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

    /** Database name, folder and files names. */
    public final static String DB_DIR = "kiwidict";
    
    
    // Russian Wiktionary
    //public static String DB_URL = "http://wikokit.googlecode.com/files/WikPaSPARQL_20110618.7z";			// temp
    public final static String DB_URL = "http://wikokit.googlecode.com/files/ruwikt20110521_android_sqlite.zip";
    public final static String DB_ZIPFILE = "ruwikt20110521_android_sqlite.zip";
    public final static int    DB_ZIPFILE_SIZE_MB = 90; // size of zipped file in MBytes
    public final static String DB_FILE = "ruwikt20110521_android.sqlite";
    public final static int    DB_FILE_SIZE_MB = 239; 
    // public static int MAX_NUMBER_DB_PARTS = 341; // Russian Wiktionary
    
    // English
    //public static String DB_NAME = "enwikt"; //"enwikt_mean_semrel_sqlite";
    
    
    
    
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
    
    // some variables to be shared between activities
    //public static DataBaseHelper db_helper;
}
