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
    private     LanguageType lang;

    /** File path to the SQLite database file. */
    private     String  sqlite_filepath;

    // database parameters
    private	String  db_dir;
    private	String  db_url;
    private String  db_zipfile;
    private int  	db_zipfile_size_mb;
    private String  db_file;
    private int  	db_file_size_mb;
    
    public final static String DB_DIR = "kiwidict";
    
    // Russian Wiktionary (parsed Wiktionary Android SQLite database)
    public final static String RU_DB_URL = "http://wikokit.googlecode.com/files/ruwikt20110521_android_sqlite.zip";
    public final static String RU_DB_ZIPFILE = "ruwikt20110521_android_sqlite.zip";
    public final static int    RU_DB_ZIPFILE_SIZE_MB = 90; // size of zipped file in MBytes
    public final static String RU_DB_FILE = "ruwikt20110521_android.sqlite";
    public final static int    RU_DB_FILE_SIZE_MB = 239; 
    
    
    // English Wiktionary database
    // English Wiktionary parsed database
    //public final static String ENWIKT_SQLITE = "C:/w/bin/enwikt20101030.sqlite";
    public final static String ENWIKT_SQLITE = "enwikt20111008.sqlite";
    
    /** Gets language of Wiktionary/Wikipedia edition, main or native language,
     * e.g. Russian in Russian Wiktionary.
     */
    public LanguageType getNativeLanguage() {
        return lang;
    }
    
    /** Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public Connect(	Context context, 
		    		String  _db_url,
				    String  _db_zipfile,
				    int  	_db_zipfile_size_mb,
				    String  _db_file,
				    int  	_db_file_size_mb,
				    String  _db_dir)
    {                               // DATABASE_VERSION
    	super(context, _db_file, null, 1);
        this.context = context;
        
        db_url = _db_url;
	    db_zipfile = _db_zipfile;
	    db_zipfile_size_mb = _db_zipfile_size_mb;
	    db_file = _db_file;
	    db_file_size_mb = _db_file_size_mb;
	    db_dir = _db_dir;
	    
        //enc        = new Encodings();
        //page_table = new PageTableBase();
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
	 		String path = FileUtil.getFilePathAtExternalStorage(db_dir, db_file);
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

    	if(!FileUtil.hasEnoughMemory(db_zipfile_size_mb + db_file_size_mb))
    		return false;
    	
    	// 1. check unzipped file, if it exists - ok.
    	// 2. else check zipped file, if it exists - unzip it.
    	// 3. else download and unzip database to SD card

    	if( FileUtil.isFileExist(db_dir, db_file) ) {	// 1.
    		return isDatabaseAvailable();	// do nothing - database already exist
    	}

    	if( FileUtil.isFileExist(db_dir, db_zipfile) ) {// 2.
    		Zipper.unpackZipToExternalStorage(db_dir, db_zipfile);
    		return isDatabaseAvailable();
    	}

    	// 3. else download and unzip database to SD card
    	FileUtil.createDirIfNotExists(db_dir);
    	Downloader.downloadToExternalStorage(db_url, db_dir, db_zipfile);
    	Zipper.unpackZipToExternalStorage(db_dir, db_zipfile);
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
    		String path = FileUtil.getFilePathAtExternalStorage(db_dir, db_file);
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