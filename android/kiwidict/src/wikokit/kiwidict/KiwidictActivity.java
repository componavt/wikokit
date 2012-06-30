package wikokit.kiwidict;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.constant.POSLocal;
import wikokit.base.wikt.constant.RelationLocal;
import wikokit.base.wikt.multi.ru.name.POSRu;
import wikokit.base.wikt.multi.ru.name.RelationRu;
import wikokit.base.wikt.multi.en.name.POSEn;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TRelationType;
import wikokit.kiwidict.enwikt.R;
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
	
	DownloadAndInstallActivity install_activity = new DownloadAndInstallActivity();
	
	static QueryTextString query_text_string;
	static ProgressBar spinning_wheel;
	
	static WordList word_list;
	static LangChoice  lang_choice    = new LangChoice();
	static LanguageSpinner lspinner = new LanguageSpinner();
	
	static TipsTeapot tip = TipsTeapot.generateRandomTip();
	static String word0 = tip.getQuery(); //"*с?рё*";
    
	public KiwidictActivity () {
	}
	
	/**
     * Check if the database already exist to avoid re-downloading and unzipping 
     * the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public static boolean isDatabaseAvailable() {

        SQLiteDatabase check_db = null;
        try{
            String path = wikokit.base.wikt.db.FileUtil.getFilePathAtExternalStorage(Connect.DB_DIR, Connect.getDBFilename());
            check_db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            //database does't exist yet. 
        }

        if(check_db != null) {
            //System.out.println("checkDataBase: Database openes, not null.");
            check_db.close();
        }

        return check_db != null;
    }
	
	void openDatabase() {
	    
        wikt_conn = new Connect(this);  // context
    	
        if(!wikt_conn.openDatabase()) {
            Toast.makeText(this, "Error: database is not available.", Toast.LENGTH_LONG).show();
            return;
        }
        
        db = wikt_conn.getDB();
        KWConstants.setDatabase(db);
        TLang.createFastMaps(db);// once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps (db);
        TRelationType.createFastMaps(db);   //System.out.println("initDatabase: DBName=" + wikt_parsed_conn.getDBName());
        RelationLocal _ = RelationRu.synonymy;
        
        // fun initialization: inheritance vs. static fields
        LanguageType native_lang = Connect.getNativeLanguage();
        if(LanguageType.ru == native_lang) {
            POSLocal $ = POSRu.noun;
        } else if(LanguageType.en == native_lang) {
            POSLocal $ = POSEn.noun;
        }         
    }
	
	void initGUI() {

	    query_text_string = new QueryTextString();
	    query_text_string.word_textfield = (EditText) findViewById(R.id.editText_word);
	    
	    spinning_wheel = (ProgressBar)findViewById(R.id.spinning_wheel);
	    
	    word_list = new WordList(this);
	    
	    CheckBox _lang_source_checkbox = (CheckBox) findViewById(wikokit.kiwidict.enwikt.R.id.lang_source_checkbox);
	    EditText _lang_source_text = (EditText) findViewById(R.id.lang_source_text);
	    lang_choice.initialize( word_list, query_text_string, lspinner,
	                            tip.getSourceLangCodes(), Connect.getNativeLanguage(),
	                            // GUI
	                            _lang_source_checkbox, _lang_source_text);
	    
	    
	    
	    ListView word_listview = (ListView) findViewById(R.id.word_listview_id);
	    
	    query_text_string.initialize(word0, db, word_list, lang_choice, this);
	    word_list.initialize(db,
                query_text_string, spinning_wheel,
                lang_choice,
                //FilterMeanSemRelTrans _filter_mean_sem_transl,
                Connect.getNativeLanguage(),
                                            //_word0              : String,
                KWConstants.n_words_list,
                word_listview,
                this);

//	    word_list.setSkipRedirects(KWConstants.b_skip_redirects);

	    //filter_mean_sem_transl.initialize(word_list, lang_choice, query_text_string);
	    //debug_panel.initialize();

	    word_list.updateWordList(  KWConstants.b_skip_redirects,
	                               word0 );
	                            
	    //query_text_string.saveWordValue();

	    word_list.copyWordsToStringArray( word_list.getPageArray() );
	    
	    
	    // language dropdown selection menu (spinner) GUI + logic
	    String[] ar_spinner = lspinner.fillByAllLanguages();
        
	    LangOnItemSelectedListener lang_item_listener = new LangOnItemSelectedListener(lspinner, lang_choice);
	    
        Spinner lang_spinner_widget = (Spinner) findViewById(R.id.lang_spinner_id);
        lspinner.initialize(lang_spinner_widget, lang_item_listener);
        
        //ArrayAdapter<String> spinner_adapter = new ArrayAdapter(this,
        //                                    android.R.layout.simple_spinner_item, ar_spinner);
        LangSpinnerAdapter 
        spinner_adapter = new LangSpinnerAdapter(this, android.R.layout.simple_spinner_item, ar_spinner, lspinner);
        
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        lang_spinner_widget.setAdapter(spinner_adapter);
        lang_spinner_widget.setOnItemSelectedListener(lang_item_listener);
        
        
        lspinner.postInit(tip.getSourceLangCodes());
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
        	
        	// --------------------------------
            // GUI
            initGUI();
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
			
			// --------------------------------
            // GUI
            initGUI();
		}
	}
    
    
}