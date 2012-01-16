/* WConstants.fx - Constants and global variables used in Wiwordik.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik; 

import wikipedia.language.LanguageType;


public class WConstants {

    // GUI
    
    /** Width of word card. */
    public static int wordcard_width = 380; // old: wrapping_width
    public static int wordcard_min_width = 220;
    
    /** Width of word card. */
    public static int wordcard_height = 600;
    public static int wordcard_min_height = 120;

    public static Boolean DEBUGUI = false;

    /** Number of words visible in the list */
    public static int n_words_list = 31;
    
    /** Number of languages sorted by size (in dropdown list) */
    // public static int n_language_list_by_size = 200; // 100;
    // see LangChoice.fillChoiceBoxByLanguages
    
    // ===========
    // Wiktionary parsed database
    // ===========

    public static String wiwordik_version = "0.10";

    /** Skips #REDIRECT words if true. */
    public static boolean b_skip_redirects = false;
    
    //////////////////////////////
    // Release / publish parameters

    //var native_lang : LanguageType;
    //public static LanguageType native_lang = LanguageType.ru;
    public static LanguageType native_lang = LanguageType.en;
    
    /** If true, then SQLite database extracted from the .jar and stored
     * to the directory user.dir (Add .jar with SQLite database to the project).
     * If false, then SQLite database from the project local folder ./sqlite/
     */
    public static Boolean IS_RELEASE = false;

    /** true (SQLite), false (MySQL) */
    public static Boolean IS_SQLITE = false;

    //            eo Parameters //
    //////////////////////////////
}
