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
    private static String DB_PATH = "/data/data/wikokit.kiwidict/databases/";

    
    private SQLiteDatabase db;
    private Context context;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

    	super(context, KWConstants.DB_NAME, null, 1);
        this.context = context;
    }
    
    public SQLiteDatabase getDB() {
    	return db;
    }
    
    public void openDatabase() {
    	createDataBase();
        /*try {
        	createDataBase();
        } catch (IOException ioe) {
        	throw new Error("Unable to create database");
        }*/

	 	try {	// Open the database
	        String my_path = DB_PATH + KWConstants.DB_NAME;
	        db = SQLiteDatabase.openDatabase(my_path, null, SQLiteDatabase.OPEN_READONLY);
		} catch(SQLException sqle) {
			throw sqle;
		}
	}
    
    
   /**
     * Creates an empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase(){ // throws IOException{

    	boolean db_exist = checkDataBase();
/*
    	if(db_exist){ 
    		//do nothing - database already exist
    	}else{
*/
    		//By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();

        	FileUtil.createDirIfNotExists(KWConstants.DB_DIR);
        	
        	//try {
        		String file_path = "ruwikt20110521_android_sqlite.zip"; 
        		Downloader.download(KWConstants.DB_URL, file_path);
        		
        		// todo: unzip file_path
        		
                //File out_db_path = context.getDir("data", 0); // output folder with result database
                //File out_db_file = new File(out_db_path, "enwikt_mean_semrel_sqlite");
                
//                File out_db_file = new File(DB_PATH, KWConstants.DB_NAME);
//                JoinerFiles.joinDatabaseChunks(context, out_db_file);
        		
    			//copyDataBase();
    		/*} catch (IOException e) {
        		throw new Error("Error copying database");
        	}*/
    	}
//    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

    	SQLiteDatabase check_db = null;

    	try{
    		String path = DB_PATH + KWConstants.DB_NAME;
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
    private void copyDataBase() throws IOException{

    	//Open your local db as the input stream
    	InputStream my_input = context.getAssets().open( KWConstants.DB_NAME );

    	// Path to the just created empty db
    	String out_filename = DB_PATH + KWConstants.DB_NAME;

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

        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return mean_semrel.query(....)" so it'd be easy
       // to you to create adapters for your views.

}