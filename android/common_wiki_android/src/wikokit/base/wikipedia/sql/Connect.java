/* Connect.java - connection to the Android SQLite database functions, 
 * the list of databases.
 *
 * Copyright (c) 2005-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikipedia.sql;

//import java.io.File;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

//import wikipedia.language.Encodings;
import wikokit.base.wikipedia.language.LanguageType;

//import wikipedia.util.FileWriter;
import wikokit.base.wikt.db.*;

/** Connection to a database functions, the list of available databases.
 * 
 * @see com.touchgraph.wikibrowser.parameter.Constants in TGWikiBrowser subproject
 */
public class Connect extends SQLiteOpenHelper {
    
    private SQLiteDatabase db;
    private Context context;
    
    /** Language of Wiktionary/Wikipedia edition,
     * e.g. Russian in Russian Wiktionary. */
    private static LanguageType native_lang;
    
    /** File path to the SQLite database file. */
    private     String  sqlite_filepath;

    // database parameters
    public final static String DB_DIR;
    
    public final static String RU_DB_URL, RU_DB_URL2;
    public final static String EN_DB_URL, EN_DB_URL2;
    
    public final static int RU_DB_ZIPFILE_SIZE_MB, RU_DB_FILE_SIZE_MB;
    public final static int EN_DB_ZIPFILE_SIZE_MB, EN_DB_FILE_SIZE_MB;
    
    public final static String ru_wikt_version;
    public final static String en_wikt_version;
    
    /** Database name, folder and files names. */
    static {
        DB_DIR = "kiwidict";
        
        /** Main parameter, you are winner :) */
        native_lang = LanguageType.en;
        //native_lang = LanguageType.ru;
        
        // Russian Wiktionary (parsed Wiktionary Android SQLite database)
        ru_wikt_version = "ruwikt20110521";
        RU_DB_URL = "http://wikokit.googlecode.com/files/ruwikt20110521_android_sqlite.zip";
        RU_DB_URL2 = null;
        
        RU_DB_ZIPFILE_SIZE_MB = 90; // size of zipped file in MBytes
        RU_DB_FILE_SIZE_MB = 239;
        
        
        // English Wiktionary (parsed Wiktionary Android SQLite database)
        en_wikt_version =  "enwikt20111008"; // "test_small"; // "enwikt20111008";
        EN_DB_URL  = "http://wikokit.googlecode.com/files/todo test_small_part1_android_sqlite.zip";
        EN_DB_URL2 = "http://wikokit.googlecode.com/files/todo test_small_part2_android_sqlite.zip";
        
        EN_DB_ZIPFILE_SIZE_MB = 1; // size of zipped file in MBytes
        EN_DB_FILE_SIZE_MB = 1;
    }
    
    /** Gets language of Wiktionary/Wikipedia edition, main or native language,
     * e.g. Russian in Russian Wiktionary.
     */
    public static LanguageType getNativeLanguage() {
        return native_lang;
    }
    
    /** Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public Connect(	Context context, 
                    LanguageType _native_lang)
                    /*String  _db_url,
				    String  _db_zipfile,
				    int  	_db_zipfile_size_mb,
				    String  _db_file,
				    int  	_db_file_size_mb,
				    String  _db_dir)*/
    {                               // DATABASE_VERSION
    	super(context, getDBFilename(_native_lang), null, 1);
        this.context = context;
        this.native_lang = _native_lang;
        
        /*db_url = _db_url;
        db_url2 = _db_url2;
	    db_zipfile = _db_zipfile;
	    db_zipfile_size_mb = _db_zipfile_size_mb;
	    db_file = _db_file;
	    db_file_size_mb = _db_file_size_mb;
	    db_dir = _db_dir;
	    */
        //enc        = new Encodings();
        //page_table = new PageTableBase();
    }
    
    public Connect(Context context)

    {                               // DATABASE_VERSION
        super(context, getDBFilename(), null, 1);
        this.context = context;
    }

    public SQLiteDatabase getDB() {
    	return db;
    }
    

    /** Gets database name (MySQL), or file name (SQLite). */
    public String getDBName() {
    	return sqlite_filepath;
    }
    
    public void openDatabase() {
    	// createDatabase();

	 	try {	// Open the database
	 		String path = FileUtil.getFilePathAtExternalStorage(DB_DIR, getDBFilename());
	        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		} catch(SQLException sqle) {
			throw sqle;
		}
	}
    
    /** Downloads and unzips database to SD card.
     * 
     * Attention: This function should be used only for debug.
     * In real applications (with GUI) some progress bar should be used.
     * 
     * @see DownloadProgressThread.java and UnzippingProgressThread.java in kiwidict project.
     */
    public boolean createDatabase(){

    	if(!FileUtil.hasEnoughMemory(getDatabaseZIPFileSizeMB() + getDatabaseFileSizeMB()))
    		return false;
    	
    	// 1. check unzipped file, if it exists - ok.
    	// 2. else check zipped file, if it exists - unzip it.
    	// 3. else download and unzip database to SD card

    	if( FileUtil.isFileExist(DB_DIR, getDBFilename()) ) {	// 1.
    		return isDatabaseAvailable();	// do nothing - database already exist
    	}

    	String db_zipfile = getDBZipFilename();
    	if( FileUtil.isFileExist(DB_DIR, db_zipfile) ) {// 2.
    		Zipper.unpackZipToExternalStorage(DB_DIR, db_zipfile);
    		return isDatabaseAvailable();
    	}

    	// 3. else download and unzip database to SD card
    	FileUtil.createDirIfNotExists(DB_DIR);
    	Downloader.downloadToExternalStorage(getDatabaseURL(), DB_DIR, db_zipfile);
    	String url2 = getDatabaseURL2();
    	if(null != url2) {
    	    String db_zipfile2 = getDBZipFilename2();
    	    Downloader.downloadToExternalStorage(url2, DB_DIR, db_zipfile2);
    	}
    	
    	Zipper.unpackZipToExternalStorage(DB_DIR, db_zipfile);
    	return isDatabaseAvailable();
    }
    
    
    /**
     * Check if the database already exist to avoid re-downloading and unzipping 
     * the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean isDatabaseAvailable() {

    	SQLiteDatabase check_db = null;

    	try{
    		String path = FileUtil.getFilePathAtExternalStorage(DB_DIR, getDBFilename());
    		check_db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}

    	if(check_db != null) {
    		System.out.println("checkDataBase: Database openes, not null.");
    		check_db.close();
    	}

    	return check_db != null;
    }
    
    @Override
	public synchronized void close() {
    	    if(db != null)
    	    	db.close();
    	    super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}


    

	
	
	/** Gets DB_ZIPFILE, e.g. "ruwikt20110521_android_sqlite.zip",
	 * or "ruwikt20110521_part1_android_sqlite.zip" if there are two parts. */
    public static String getDBZipFilename() {
        
        String part1 = "_part1";
        if(null == getDatabaseURL2())
            part1 = "";
        
        if(LanguageType.ru == native_lang) {
            return ru_wikt_version + part1 + "_android_sqlite.zip";
            
        } else if(LanguageType.en == native_lang) {
            return en_wikt_version + part1 + "_android_sqlite.zip";
        }
        return null;
    }
    
    /** Gets second zipped file (with second part), e.g. "ruwikt20110521_part2_android_sqlite.zip". */
    public static String getDBZipFilename2() {
        if(null == getDatabaseURL2())
            return null;
        
        if(LanguageType.ru == native_lang) {
            return ru_wikt_version + "_part2_android_sqlite.zip";
        } else if(LanguageType.en == native_lang) {
            return en_wikt_version + "_part2_android_sqlite.zip";
        }
        return null;
    }
    
    /** Gets database filename, e.g. "ruwikt20110521_android.sqlite". */
    public static String getDBFilename() {
        if(LanguageType.ru == native_lang) {
            return ru_wikt_version + "_android.sqlite";
        } else if(LanguageType.en == native_lang) {
            return en_wikt_version + "_android.sqlite";
        }
        return null;
    }
    public static String getDBFilename(LanguageType _native_lang) {
        native_lang = _native_lang;
        return getDBFilename();
    }
    
    public static String getDBFilenamePart1() {
        if(null == getDatabaseURL2())
            return getDBFilename();
        
        if(LanguageType.ru == native_lang) {
            return ru_wikt_version + "_part1_android.sqlite";
        } else if(LanguageType.en == native_lang) {
            return en_wikt_version + "_part1_android.sqlite";
        }
        return null;
    }
    
    public static String getDBFilenamePart2() {
        if(null == getDatabaseURL2())
            return null;
        
        if(LanguageType.ru == native_lang) {
            return ru_wikt_version + "_part2_android.sqlite";
        } else if(LanguageType.en == native_lang) {
            return en_wikt_version + "_part2_android.sqlite";
        }
        return null;
    }
    
    /** Gets database URL. */
    public static String getDatabaseURL() {
        if(LanguageType.ru == native_lang) {            
            return RU_DB_URL;
        } else if(LanguageType.en == native_lang) {
            return EN_DB_URL;
        }
        return null;
    }
    /** Gets database URL (second part of the huge database). */
    public static String getDatabaseURL2() {
        if(LanguageType.ru == native_lang) {            
            return RU_DB_URL2;
        } else if(LanguageType.en == native_lang) {
            return EN_DB_URL2;
        }
        return null;
    }
    
    /** Gets dump's version of the Wiktionary. */
    public static String getWiktionaryDumpVersion() {
        if(LanguageType.ru == native_lang) {            
            return ru_wikt_version;
        } else if(LanguageType.en == native_lang) {
            return en_wikt_version;
        }
        return null;
    }

    /** Gets the size of the dump file in megabytes. */
    public static int getDatabaseFileSizeMB() {
        if(LanguageType.ru == native_lang) {            
            return RU_DB_FILE_SIZE_MB;
        } else if(LanguageType.en == native_lang) {
            return EN_DB_FILE_SIZE_MB;
        }
        return 0;
    }

    /** Gets the size of the archived dump file in megabytes. */
    public static int getDatabaseZIPFileSizeMB() {
        if(LanguageType.ru == native_lang) {            
            return RU_DB_ZIPFILE_SIZE_MB;
        } else if(LanguageType.en == native_lang) {
            return EN_DB_ZIPFILE_SIZE_MB;
        }
        return 0;
    }

    /** Opens SQLite connection.
    *
    * @param _sqlite_filepath File path to the SQLite file,
    *                      e.g. "jdbc:sqlite:C:/w/bin/ruwikt20090707.sqlite"
    * @param _lang language of Wiktionary/Wikipedia edition, main or 
    *               native language, e.g. Russian in Russian Wiktionary.
    */
   /*public void OpenSQLite(String _sqlite_filepath, LanguageType _lang, boolean brelease) {

       lang    = _lang;
       sqlite_filepath = _sqlite_filepath;
       //OpenSQLite(brelease, sqlite_filepath);
   }*/

   /** Gets filepath to the SQLite database.
    * 
    * @param brelease If true, then SQLite database will be extracted 
    * from the jar-file and stored to the directory user.dir 
    * (Add jar-file with SQLite database to the project);
    * If false, then SQLite database has path ./sqlite/SQLiteFile
    * 
    * @param sqlite_filename file name of the SQLite database
    * @return path to SQLite file in .jar or in local folder of the project
    */
   /*private String getFilepathToSQLiteDatabase(boolean brelease, String sqlite_filename) {

       String result_filepath;

           if( brelease ) {
               String dbfile_in_jar = sqlite_filename;
               String target_dir = System.getProperty("user.home") + File.separator + ".wiwordik";
               result_filepath = target_dir+File.separator + dbfile_in_jar;

               if(!FileWriter.existsFile(result_filepath)) {
                   Object resource = this;
                   boolean success = true;
                   try {                    // creates ~/.wiwordik/
                       success = FileWriter.retrieveBinaryFileFromJar(dbfile_in_jar, target_dir, resource);
                   } catch (Exception ex) {
                       Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   if(!success)
                       System.err.println("Error in Connect::OpenSQLite() Couldn't retrieve SQLite database from .jar file " + dbfile_in_jar);
               }
           } else {
               result_filepath = "sqlite"+File.separator + sqlite_filename;
           }
       return result_filepath;
   }*/
   
}