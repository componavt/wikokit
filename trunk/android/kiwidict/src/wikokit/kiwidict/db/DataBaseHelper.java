package wikokit.kiwidict.db;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;

import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.db.JoinerFiles;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper; 


public class DataBaseHelper extends SQLiteOpenHelper {

	//The Android's default system path of your application database.
    //private static String DB_PATH = "/data/data/wikokit.kiwidict/databases/";

    
    private SQLiteDatabase db;
    private Context context;

    /** Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
                                                  // DATABASE_VERSION
    	super(context, KWConstants.DB_ZIPFILE, null, 1);
        this.context = context;
    }
    
    public SQLiteDatabase getDB() {
    	return db;
    }
    
    public void openDatabase() {
    	// createDatabase();

	 	try {	// Open the database
	 		String path = FileUtil.getFilePathAtExternalStorage(KWConstants.DB_DIR, KWConstants.DB_FILE);
	        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		} catch(SQLException sqle) {
			throw sqle;
		}
	}
    
    
   /** Downloads, unzips and checks that the database is available. */
    /*public boolean createDatabase(){
    	
    	this.getReadableDatabase();
    	
    	// 1. check unzipped file, if it exists - ok.
    	// 2. else check zipped file, if it exists - unzip it.
    	// 3. else download and unzip database to SD card
    	
    	if( FileUtil.isFileExist(KWConstants.DB_DIR, KWConstants.DB_FILE) ) {	// 1.
    		return isDatabaseAvailable();	// do nothing - database already exist
    	}
    	
    	if( FileUtil.isFileExist(KWConstants.DB_DIR, KWConstants.DB_ZIPFILE) ) {// 2.
    		Zipper.unpackZipToExternalStorage(KWConstants.DB_DIR, KWConstants.DB_ZIPFILE);
    		return isDatabaseAvailable();
    	}

    	// 3. else download and unzip database to SD card
    	FileUtil.createDirIfNotExists(KWConstants.DB_DIR);
    	Downloader.downloadToExternalStorage(KWConstants.DB_URL, KWConstants.DB_DIR, KWConstants.DB_ZIPFILE);
    	Zipper.unpackZipToExternalStorage(KWConstants.DB_DIR, KWConstants.DB_ZIPFILE);
    	return isDatabaseAvailable();
    }*/

    /**
     * Check if the database already exist to avoid re-downloading and unzipping 
     * the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean isDatabaseAvailable() {

    	SQLiteDatabase check_db = null;

    	try{
    		String path = FileUtil.getFilePathAtExternalStorage(KWConstants.DB_DIR, KWConstants.DB_FILE);
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

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    /*private void copyDataBase() throws IOException{

    	//Open your local db as the input stream
    	InputStream my_input = context.getAssets().open( KWConstants.DB_ZIPFILE );

    	// Path to the just created empty db
    	String out_filename = DB_PATH + KWConstants.DB_ZIPFILE;
    	//String path = FileUtil.getFilePathAtExternalStorage(KWConstants.DB_DIR, KWConstants.DB_FILE);

    	//Open the empty db as the output stream
    	OutputStream my_output = new FileOutputStream(out_filename);

    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = my_input.read(buffer))>0){
    		my_output.write(buffer, 0, length);
    	}

    	//Close the streams
    	my_output.flush();
    	my_output.close();
    	my_input.close();
    }*/

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

        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return mean_semrel.query(....)" so it'd be easy
       // to you to create adapters for your views.

}