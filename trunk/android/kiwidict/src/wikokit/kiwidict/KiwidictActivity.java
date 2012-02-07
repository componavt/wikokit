package wikokit.kiwidict;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

//import wikipedia.language.LanguageType;
//import wikokit.kiwidict.GameLogic;
import wikokit.kiwidict.db.DataBaseHelper;
import wikokit.kiwidict.db.FileUtil;
//import wikokit.kiwidict.db.MSRLang;


public class KiwidictActivity extends Activity {

	private DataBaseHelper db_helper;
	private SQLiteDatabase db;
	//private GameLogic game_logic;
	
	DownloadAndInstallActivity install_activity = new DownloadAndInstallActivity();
	
	/** The language selected by user for learning. */
	//private MSRLang xx_lang;
	
	/** All languages in the database. */
	//private MSRLang[] langs;
	
	//TextView word;
	
   //static Connect wikt_parsed_conn  = new Connect();
    
	void openDatabase() {
    	
		db_helper.openDatabase();
		db = db_helper.getDB();
        
        // SQLite                                   //Connect.testSQLite();
        /*if(LanguageType.ru == KWConstants.native_lang) {
            wikt_parsed_conn.OpenSQLite(Connect.RUWIKT_SQLITE, LanguageType.ru, KWConstants.IS_RELEASE);
        } else {
            wikt_parsed_conn.OpenSQLite(Connect.ENWIKT_SQLITE, LanguageType.en, KWConstants.IS_RELEASE);
        }
    	*/
        //System.out.println("initDatabase: DBName=" + wikt_parsed_conn.getDBName());    

       // try {
        	//Connect wikt_parsed_conn  = new Connect();
        	String path = FileUtil.getFilePathAtExternalStorage(KWConstants.DB_DIR, KWConstants.DB_FILE);
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
        setContentView(R.layout.main);
        
        db_helper = new DataBaseHelper(this);
        
        if( !db_helper.isDatabaseAvailable() ) {
        	Intent i = new Intent(this, DownloadAndInstallActivity.class);
        	startActivityForResult(i, 0);
        } else {
        	openDatabase();
        }
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