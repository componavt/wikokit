package wordik.magneto;

import java.util.Random;

import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;
import wordik.magneto.db.MSRLang;
import wordik.magneto.db.MSRMeanSemrelXX;

public class GameLogic {

	private MagnetowordikActivity magnet_activity;
	private SQLiteDatabase db;
	private TextView tv_mark;
	
	/** The language selected by user for learning. */
	private MSRLang xx_lang;
	
	/** The main word, that user have to guess. */
	private MSRMeanSemrelXX semrel_main_word;
	
	/** Other words, one of which is a synonym (or antonym, etc.) to the main word.
	 * I hope, that other words do not contain main_word. */
	private String[] other_words;
	private int other_len = 3; // number of other words
	
	private int success = 0;
	private int failure = 0;
	
    private Random generator = new Random();
	
	public GameLogic(MagnetowordikActivity _magnet_activity, SQLiteDatabase _db, TextView _tv_mark) {
		magnet_activity = _magnet_activity;
		db = _db;
		tv_mark = _tv_mark;
		other_words = new String[other_len];
	}
	
	/** Restart game. */
	public void reStart() {
		MSRMeanSemrelXX[] mm = MSRMeanSemrelXX.getRandom (db, xx_lang, 4);
		semrel_main_word = mm[0];
		
		// gets 3 random words, except : semrel_main_word
		for(int i=0; i < other_len; i++)
			other_words [i] = mm [i + 1].getPageTitle();
		
		String syn_word = semrel_main_word.getRandomSynonym();
		
		// let's one of other words is a synonym, or antonym, etc.
		other_words[ generator.nextInt( other_len ) ] = syn_word; 
	}
	
	/** Sets learning language. */
	public void setLang (MSRLang _xx_lang) {
		xx_lang = _xx_lang;
	}
	
	/** Gets header of main word (entry). */
	public String getMainWord() {
		return semrel_main_word.getPageTitle();
	}
	
	/** Gets link to main word (entry) in the Wiktionary. */
	public String getMainWordURL() {
		return  "http://en.wiktionary.org/wiki/" + 
				// substitute space by underscore in URL
				semrel_main_word.getPageTitle().replace(' ', '_');
	}
	
	/** Gets header of main word (entry). */
	public String getMainWordMeaning() {
		return semrel_main_word.getMeaning();
	}
	
	/** Gets other random words. */
	public String[] getOtherWords() {
		return other_words;
	}
	
	/** Checks whether the clicked word is a synonym for the main word. */
	public void clickAndCheckSynonym (String candidate) {
		if(null == semrel_main_word)
			return;
		
		if( semrel_main_word.hasRelatedWord(candidate) ) {
			success ++;
			
			Toast.makeText(magnet_activity, 
					"Yes!", Toast.LENGTH_LONG).show();
			
			String relation = semrel_main_word.getRelation(candidate);
			String mark = "\n" + semrel_main_word.getPageTitle() + " - " + relation + " - " + candidate +
                "\n\n" + " - " + failure + " + " + success + " = " + (success - failure);
			reStart();
			magnet_activity.reStart();
			
			tv_mark.setText(mark);
		} else {
			failure ++;
			
			Toast.makeText(magnet_activity, 
					"No", Toast.LENGTH_LONG).show();
			//tv_mark.setText("\nNo!");
		}
	}
	
}
