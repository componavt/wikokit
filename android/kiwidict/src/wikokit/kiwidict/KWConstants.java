/* KWConstants.java - Constants and global variables used in kiwidict.
 *
 * Copyright (c) 2011-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict; 

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.TypedValue;
import wikokit.base.wikipedia.language.LanguageType;

//import wikokit.kiwidict.db.DataBaseHelper;

public class KWConstants {

    // GUI
    
    /** Width of word card. */
    ///public static int wordcard_width = 380; // old: wrapping_width
    ///public static int wordcard_min_width = 220;
    
    /** Width of word card. */
    ///public static int wordcard_height = 600;
    ///public static int wordcard_min_height = 120;

    

    /** Number of words visible in the list */
    public static int n_words_list = 31;
    
    /** Number of languages sorted by size (in dropdown list) */
    // // public static int n_language_list_by_size = 200; // 100;
    // see LangChoice.fillChoiceBoxByLanguages
    
    public final static int  text_size_normal, text_size_medium, text_size_large;
    public final static int pos_color, definition_color, quote_color, quote_translatioin_color,
                            quote_reference_color, relation_background_color;
    
    public final static int meaning_gap, quote_gap, relation_gap; // distance between
    
    public final static String DB_DIR;
    
    
    public final static String DB_URL;
    public final static String DB_ZIPFILE;
    public final static int    DB_ZIPFILE_SIZE_MB;
    public final static String DB_FILE;
    public final static int    DB_FILE_SIZE_MB;
    
    private static SQLiteDatabase db;
    public static SQLiteDatabase getDatabase() {return db;}
    public static void           setDatabase(SQLiteDatabase _db) {db = _db;}
    
    
    public final static String wikt_version, kiwidict_version, kiwidict_name;

    /** Skips #REDIRECT words if true. */
    public final static boolean b_skip_redirects;
    
    
    //////////////////////////////
    // Release / publish parameters

    public static Boolean DEBUGUI;
    
    //var native_lang : LanguageType;
    public static LanguageType native_lang;
    
    /** If true, then SQLite database extracted from the .jar and stored
     * to the directory user.dir (Add .jar with SQLite database to the project).
     * If false, then SQLite database from the project local folder ./sqlite/
     */
    public static Boolean IS_RELEASE;

    //            eo Parameters //
    //////////////////////////////
    
    // some variables to be shared between activities
    //public static DataBaseHelper db_helper;
    
    
    static {
        /** Number of words visible in the list */
        n_words_list = 31;
        
        // ===========
        // GUI
        // ===========

        // TypedValue.COMPLEX_UNIT_SP - Scaled Pixels
        text_size_normal = 15;
        text_size_medium = 20;
        text_size_large = 25;
        
        pos_color       = Color.RED;
        definition_color = Color.WHITE;
        quote_color     = Color.GREEN;
        quote_translatioin_color = Color.rgb(210, 105, 30); // chocholate color;
        quote_reference_color = Color.rgb(178, 190, 181);   // Ash gray
        relation_background_color = Color.rgb(72, 60, 50);  // Taupe
        
        meaning_gap = 18;
        quote_gap   = 8;
        relation_gap = 6;


        
        // ===========
        // Wiktionary parsed database
        // ===========
        kiwidict_version = "0.094";
        
        
        
        // Russian Wiktionary
        kiwidict_name = "kiwidict-ru";
        wikt_version = "ruwikt20110521";
        
        //public static String DB_URL = "http://wikokit.googlecode.com/files/WikPaSPARQL_20110618.7z";          // temp
        DB_URL = "http://wikokit.googlecode.com/files/ruwikt20110521_android_sqlite.zip";
        
        DB_ZIPFILE_SIZE_MB = 90; // size of zipped file in MBytes
        DB_FILE_SIZE_MB = 239;
        
        // English Wiktionary
        // todo
        // ...
        /*
        kiwidict_name = "kiwidict";
        
        */
        
        /** Database name, folder and files names. */
        DB_DIR = "kiwidict";
        
        DB_ZIPFILE = wikt_version + "_android_sqlite.zip";
        DB_FILE = wikt_version + "_android.sqlite";
        
        // public static int MAX_NUMBER_DB_PARTS = 341; // Russian Wiktionary
        
        // English
        //NAME = "enwikt"; //"enwikt_mean_semrel_sqlite";
        //public static String DB_NAME = "enwikt"; //"enwikt_mean_semrel_sqlite";

        
        /** Skips #REDIRECT words if true. */
        b_skip_redirects = false;

        
        //////////////////////////////
        // Release / publish parameters
        
        DEBUGUI = false;
        
        native_lang = LanguageType.ru;
        //native_lang = LanguageType.en;
        //old public static String native_lang_code = "ru"; // "en"
        
        /** If true, then SQLite database extracted from the .jar and stored
        * to the directory user.dir (Add .jar with SQLite database to the project).
        * If false, then SQLite database from the project local folder ./sqlite/
        */
        IS_RELEASE = true;
        
        //            eo Parameters //
        //////////////////////////////
        
    }
}
