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
import android.widget.ListView;
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
import wikokit.kiwidict.lang.LangChoice;
import wikokit.kiwidict.lang.LangOnItemSelectedListener;
import wikokit.kiwidict.lang.LangSpinnerAdapter;
import wikokit.kiwidict.lang.LanguageSpinner;
import wikokit.kiwidict.search_window.QueryTextString;
import wikokit.kiwidict.util.TipsTeapot;
import wikokit.kiwidict.wordlist.WordList;


public class KiwidictActivity extends Activity {

	private Connect wikt_conn; // private DataBaseHelper db_helper;
	private static SQLiteDatabase db;
	//private GameLogic game_logic;
	
	DownloadAndInstallActivity install_activity = new DownloadAndInstallActivity();
	
	//TextView word;
	
	static WordList word_list;
	static LangChoice  lang_choice    = new LangChoice();
	static QueryTextString query_text_string = new QueryTextString();
	
	static TipsTeapot tip = TipsTeapot.generateRandomTip();
	static String word0 = tip.getQuery(); //"*с?рё*";
    
	public KiwidictActivity () {
	    word_list = new WordList(this);
	}
	
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
        
        db = wikt_conn.getDB();
        TLang.createFastMaps(db);// once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps (db);
        TRelationType.createFastMaps(db);   //System.out.println("initDatabase: DBName=" + wikt_parsed_conn.getDBName());    
    }
	
	void initGUI() {

// todo	    query_text_string.initialize(word0, db, word_list, lang_choice);

	    //lang_choice.initialize( word_list, query_text_string, lang_choicebox,
	    //                        tip.getSourceLangCodes(), WConstants.native_lang);
	    
	    //lang_choicebox.initialize(word_list, query_text_string, lang_choice, WConstants.native_lang);

	    
	    //getApplicationContext();
	    ListView word_listview = (ListView) findViewById(R.id.word_listview_id);
	    word_list.initialize(db,
                query_text_string,
                lang_choice,
                //FilterMeanSemRelTrans _filter_mean_sem_transl,
                KWConstants.native_lang,
                                            //_word0              : String,
                KWConstants.n_words_list,
                word_listview);
	    //setListAdapter(word_list.adapter);
	    //WordListArrayAdapter

	    word_list.setSkipRedirects(KWConstants.b_skip_redirects);

	    //filter_mean_sem_transl.initialize(word_list, lang_choice, query_text_string);
	    //debug_panel.initialize();

	    word_list.updateWordList(  KWConstants.b_skip_redirects,
	                               word0 );
	                            
	    //query_text_string.saveWordValue();

	    word_list.copyWordsToStringArray( word_list.getPageArray() );
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
        initGUI();
        
        LanguageSpinner lspinner = new LanguageSpinner();
        String[] ar_spinner = lspinner.fillByAllLanguages();
        
        Spinner lang_spinner = (Spinner) findViewById(R.id.lang_spinner_id);

        
        //ArrayAdapter<String> spinner_adapter = new ArrayAdapter(this,
        //                                    android.R.layout.simple_spinner_item, ar_spinner);
        LangSpinnerAdapter 
        spinner_adapter = new LangSpinnerAdapter(this, android.R.layout.simple_spinner_item, ar_spinner, lspinner);
        //spinner_adapter.setLanguageSpinner(lspinner);
        
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        lang_spinner.setAdapter(spinner_adapter);
        lang_spinner.setOnItemSelectedListener(new LangOnItemSelectedListener(lspinner));
        
        //word_list.onCreate(savedInstanceState);
        
        
        
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