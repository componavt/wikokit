package wikokit.kiwidict;

import android.R;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;
//import wikokit.kiwidict.GameLogic;
import wikokit.kiwidict.db.DataBaseHelper;
//import wikokit.kiwidict.db.MSRLang;


public class KiwidictActivity extends Activity {

	private DataBaseHelper db_helper;
	private SQLiteDatabase db;
	//private GameLogic game_logic;
	
	
	/** The language selected by user for learning. */
	//private MSRLang xx_lang;
	
	/** All languages in the database. */
	//private MSRLang[] langs;
	
	//TextView word;
	
//   static Connect wikt_parsed_conn  = new Connect();
    
   void initDatabase() {
    	
        db_helper = new DataBaseHelper(this);
        db_helper.openDatabase();				
    //    db = db_helper.getDB();
        
        
        // SQLite                                   //Connect.testSQLite();
        /*if(LanguageType.ru == KWConstants.native_lang) {
            wikt_parsed_conn.OpenSQLite(Connect.RUWIKT_SQLITE, LanguageType.ru, KWConstants.IS_RELEASE);
        } else {
            wikt_parsed_conn.OpenSQLite(Connect.ENWIKT_SQLITE, LanguageType.en, KWConstants.IS_RELEASE);
        }
    
        System.out.println("initDatabase: DBName=" + wikt_parsed_conn.getDBName());    

        TLang.createFastMaps(wikt_parsed_conn);   // once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps(wikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
        TRelationType.createFastMaps(wikt_parsed_conn);
        */
    }
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        initDatabase();
        
        int z = 0;
        z ++;
        // langs = MSRLang.getAllLang(db);
    }
}