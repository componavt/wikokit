package wordik.magneto;

import wordik.magneto.db.DataBaseHelper;
import wordik.magneto.db.MSRLang;
import wordik.magneto.db.MSRMeanSemrelXX;
import wordik.magneto.R;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MagnetowordikActivity extends Activity {
	
	private DataBaseHelper db_helper;
	private SQLiteDatabase db;
	private GameLogic game_logic;
	
	/** The language selected by user for learning. */
	private MSRLang xx_lang;
	
	/** All languages in the database. */
	private MSRLang[] langs;
	
	TextView word, word_def, word_www;	// header (entry), definition (meaning), hyperlink to word in enwikt
	TextView[] tv_words;
	TextView tv_mark; 	// estimation and rating of user work
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        db_helper = new DataBaseHelper(this);
        db_helper.openDatabase();				
        db = db_helper.getDB();
        
        langs = MSRLang.getAllLang(db);
        String ar_spinner[] = MSRLang.getLangCodeStatistics(langs);

        Spinner lang_spinner = (Spinner) findViewById(R.id.spinner_id);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
        									android.R.layout.simple_spinner_item, ar_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lang_spinner.setAdapter(adapter);
        lang_spinner.setOnItemSelectedListener(new LangOnItemSelectedListener());
        
        word = (TextView) findViewById(R.id.textView_word);
        SpannableString content = new SpannableString("Main word");
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		word.setText(content);
        
        word_def = (TextView) findViewById(R.id.textView_word_def);
        word_def.setTypeface(null, Typeface.BOLD_ITALIC);
        word_def.setText("The definition for underlined word.");
        
        word_www = (TextView) findViewById(R.id.textView_word_www);
        //word_def.setTypeface(null, Typeface.BOLD_ITALIC);
        //word_def.setText("The definition for underlined word.");
        
        
        tv_mark = (TextView) findViewById(R.id.textView_mark);
        tv_mark.setText("\n1. Select language you want to learn.\n" + 
        		"2. Select one of three words as synonym (antonym) for underlined word.");
        
        tv_words = new TextView[3];
        tv_words [0] = (TextView) findViewById(R.id.textView_word_left);
        tv_words [1] = (TextView) findViewById(R.id.textView_word_top);
        tv_words [2] = (TextView) findViewById(R.id.textView_word_right);
        
        tv_words [0].setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		game_logic.clickAndCheckSynonym( (String) tv_words [0].getText());
        }});
        
        tv_words [1].setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		game_logic.clickAndCheckSynonym( (String) tv_words [1].getText());
        }});
        
        tv_words [2].setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		game_logic.clickAndCheckSynonym( (String) tv_words [2].getText());
        }});
        
        game_logic = new GameLogic(this, db, tv_mark);
    }
    
    /** Restart game (visual part). */
	public void reStart() {
		// underline main word
		//word.setText(game_logic.getMainWord());
		SpannableString content = new SpannableString(game_logic.getMainWord());
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		word.setText(content);
		
    	word_def.setText(game_logic.getMainWordMeaning());
    	
    	// URL ----------------
    	//SpannableString ss = new SpannableString("www");

		//ss.setSpan(new StyleSpan(Typeface.ITALIC), 0, 6,
		//Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		//ss.setSpan(new URLSpan(game_logic.getMainWordURL()), 13, 17,
		//Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    	
    	String ss = "<a href=\""+ game_logic.getMainWordURL() + "\">www</a>  ";
    	
    	word_www.setText( Html.fromHtml(ss));
    	word_www.setMovementMethod(LinkMovementMethod.getInstance());
    	// -------------- eo URL
    	
    	String[] others_words = game_logic.getOtherWords();
    	tv_words [0].setText(others_words[0]);
    	tv_words [1].setText(others_words[1]);
    	tv_words [2].setText(others_words[2]);
	}
    
    public class LangOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        	if(0 == pos) // header, not a language
        		return;
        	
        	xx_lang = langs[pos-1];
        	game_logic.setLang(xx_lang);
        	Toast.makeText(parent.getContext(), "You will learn " + xx_lang.getName() + " words.",
        		  //". Thanks to Wiktionary we have " + xx_lang.getNumberOfSemanticRelations() + " semantic relations to play the game.",
        		  // parent.getItemAtPosition(pos).toString(), 
        		  Toast.LENGTH_LONG).show();
        	
        	game_logic.reStart();
        	reStart();
        	tv_mark.setText("");
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
}