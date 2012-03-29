package wikokit.kiwidict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
/*import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;*/

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.db.FileUtil;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TRelationType;
import wikokit.base.wikt.sql.lang.LanguageSplitter;
import wikokit.kiwidict.lang.LangOnItemSelectedListener;
import wikokit.kiwidict.lang.LangSpinnerAdapter;
import wikokit.kiwidict.lang.LanguageSpinner;


public class KiwidictActivity extends Activity {

	private Connect wikt_conn; // private DataBaseHelper db_helper;
	//private SQLiteDatabase db;
	//private GameLogic game_logic;
	
	DownloadAndInstallActivity install_activity = new DownloadAndInstallActivity();
	
	/** The language selected by user for learning. */
	//private MSRLang xx_lang;
	
	/** All languages in the database. */
	//private MSRLang[] langs;
	
	//TextView word;
    
	/**
     * Check if the database already exist to avoid re-downloading and unzipping 
     * the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public static boolean isDatabaseAvailable() {

        SQLiteDatabase check_db = null;
        try{
            String path = wikokit.base.wikt.db.FileUtil.getFilePathAtExternalStorage(KWConstants.DB_DIR, KWConstants.DB_FILE);
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
	
	void openDatabase() {
    	
		//db_helper.openDatabase();
		//db = db_helper.getDB();
        
        // SQLite                                   //Connect.testSQLite();
        if(LanguageType.ru == KWConstants.native_lang) {
            //wikt_parsed_conn.OpenSQLite(Connect.RUWIKT_SQLITE, LanguageType.ru, KWConstants.IS_RELEASE);
            
            wikt_conn = new Connect(
                    this, // context,
                    Connect.RU_DB_URL,
                    Connect.RU_DB_ZIPFILE,
                    Connect.RU_DB_ZIPFILE_SIZE_MB,
                    Connect.RU_DB_FILE,
                    Connect.RU_DB_FILE_SIZE_MB,
                    Connect.DB_DIR
                    );
        } else {
            //wikt_parsed_conn.OpenSQLite(Connect.ENWIKT_SQLITE, LanguageType.en, KWConstants.IS_RELEASE);
            // todo
            // ...
        }
    	
        wikt_conn.openDatabase();
        
        SQLiteDatabase db = wikt_conn.getDB();
        TLang.createFastMaps(db);// once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps (db);
        TRelationType.createFastMaps(db);
        
        //System.out.println("initDatabase: DBName=" + wikt_parsed_conn.getDBName());    

       // try {
        	//Connect wikt_parsed_conn  = new Connect();
        	//String path = FileUtil.getFilePathAtExternalStorage(KWConstants.DB_DIR, KWConstants.DB_FILE);
            //String db_str = "jdbc:sqldroid:" + path;
        	//String db_str = "jdbc:sqlite:" + path;

            //Class.forName("SQLite.JDBCDriver").newInstance();
            //Class.forName("org.sqldroid.SqldroidDriver").newInstance();

            //wikt_parsed_conn.conn = DriverManager.getConnection(db_str);

        
        //TLang.createFastMaps(wikt_parsed_conn);	// once upon a time: use Wiktionary parsed db
        //TPOS.createFastMaps(db);    // once upon a time: use Wiktionary parsed db
        //TRelationType.createFastMaps(db);
		
        // langs = MSRLang.getAllLang(db);
        
        	//conn.close();
     /*   } catch (java.sql.SQLException e) {
        	e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }*/
    }
	
	/** Called when the activity is first created.
	 * Download and unzip file with the database. 
	 **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        //db_helper = new DataBaseHelper(this);
        //if( !db_helper.isDatabaseAvailable() ) {
        //if( !DataBaseHelper.isDatabaseAvailable()) {
        if( !isDatabaseAvailable() ) {
        	Intent i = new Intent(this, DownloadAndInstallActivity.class);
        	startActivityForResult(i, 0);
        } else {
        	openDatabase();
        }
        
        // --------------------------------
        // GUI
        
        LanguageSpinner lspinner = new LanguageSpinner();
        String[] ar_spinner = lspinner.fillByAllLanguages();
        
        Spinner lang_spinner = (Spinner) findViewById(R.id.spinner_id);
        
        //ArrayAdapter<String> spinner_adapter = new ArrayAdapter(this,
        //                                    android.R.layout.simple_spinner_item, ar_spinner);
        LangSpinnerAdapter 
        spinner_adapter = new LangSpinnerAdapter(this, android.R.layout.simple_spinner_item, ar_spinner, lspinner);
        //spinner_adapter.setLanguageSpinner(lspinner);
        
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        lang_spinner.setAdapter(spinner_adapter);
        lang_spinner.setOnItemSelectedListener(new LangOnItemSelectedListener(lspinner));
        
        
    }
    
    
    /** After downloading and installing dictionary activity. */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data); 
		if (resultCode == Activity.RESULT_CANCELED) { 
			//Toast.makeText(this, "activity canceled", Toast.LENGTH_SHORT).show();
			// database failed, exit.
			finish();
			
		} else if (resultCode == Activity.RESULT_OK) { 
			//Toast.makeText(this, "activity ok", Toast.LENGTH_SHORT).show();
			
			// ok, open database.
			openDatabase();
		}
	}
    
    
}