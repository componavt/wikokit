/* QueryTextString.java - Words filter by a presence of meaning,
 * semantic relations, translations.
 *
 * Copyright (c) 2009-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.search_window;

import wikokit.base.wikt.sql.TPage;
import wikokit.kiwidict.DownloadAndInstallActivity;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.KiwidictActivity;
import wikokit.kiwidict.R;
import wikokit.kiwidict.lang.LangChoice;
import wikokit.kiwidict.util.GUI;
import wikokit.kiwidict.word_card.WCActivity;
import wikokit.kiwidict.wordlist.WordList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QueryTextString extends Activity {

    //Activity main_activity; // KiwidictActivity
    
    public String word0;
    SQLiteDatabase db;

    // meaningless for Android application with only one open window
    // public String word_value_last_open_card = ""; // value of last word, with which the wordcard was open, to prevent double opening of the same word card
    
    
    String word_value_old = word0;
    String word_value = ""; // var word_value: String = bind word_Text.rawText;

    WordList word_list;
    LangChoice lang_choice;
    
    public EditText word_textfield;
    private Drawable x_clear_button_mode;
    private Button enter_search_button;
    
    
    private boolean updateWordList_enable = true;
    
    public boolean isAbleUpdateWordList() {
        return updateWordList_enable;
    }
    
    public void enableUpdateWordList(boolean updateWordList_enable) {
        this.updateWordList_enable = updateWordList_enable;
    }
    
    
    //public QueryTextString (Activity _main_activity) {
    //    main_activity = _main_activity;
    //}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
    }
    
    
    /** Set parameters of the class.
     * @param _word0 tips for the reader: recommendation and tutorial
     * @param _word_list    list of words in the dictionary (ListView)
     * @param _lang_choice  checkboxes and text field with language codes
     */
    public void initialize( String _word0, 
                            SQLiteDatabase _db,
                            WordList _word_list,
                            LangChoice _lang_choice,
                            Activity _context
                           ) {
        word0           = _word0;
        db = _db;
        word_list       = _word_list;
        lang_choice     = _lang_choice;
        
        // add small "x" to clear text in search field
        x_clear_button_mode = _context.getResources().getDrawable(R.drawable.fairytale_button_cancel);
        x_clear_button_mode.setBounds(0, 0, x_clear_button_mode.getIntrinsicWidth(), 
                                            x_clear_button_mode.getIntrinsicHeight());
        
        word_textfield.setTextSize(TypedValue.COMPLEX_UNIT_SP, KWConstants.text_size_medium);
        setWordValue(word0); // after x_clear_button_mode
        updateDeleteXMark(word0);
        
        addListener();
        
        // search button
        enter_search_button = (Button)_context.findViewById(R.id.enter_search_button);
        enter_search_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                openCardForEnterEvent(v.getContext());
            }
        });

    }
    
    public void addListener() {
        
        word_textfield.setOnTouchListener(new OnTouchListener() {
            
            public boolean onTouch(View v, MotionEvent event) {
                if (word_textfield.getCompoundDrawables()[2] == null) {
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (event.getX() > word_textfield.getWidth() // area of x_clear_button_mode button
                        - word_textfield.getPaddingRight() - x_clear_button_mode.getIntrinsicWidth()) {
                    
                    setWordValue("");
                    updateDeleteXMark("");
                }
                return false;
            }
        });

        
        // add a listener to keep track user input
        word_textfield.addTextChangedListener(new TextWatcher()
        {
            public void  afterTextChanged (Editable s){ 
                // Toast.makeText(context, "afterTextChanged " + word_textfield.getText(), Toast.LENGTH_LONG).show(); 
            } 
            public void  beforeTextChanged  (CharSequence s, int start, int count, int after){ 
                // Toast.makeText(context, "beforeTextChanged " + word_textfield.getText(), Toast.LENGTH_LONG).show();
            } 
            public void  onTextChanged (CharSequence s, int start, int before, int count) 
            { 
                //Toast.makeText(context, "onTextChanged " + word_textfield.getText(), Toast.LENGTH_LONG).show();
                
                word_value_old = word_value;
                word_value = word_textfield.getText().toString().trim();
                updateDeleteXMark(word_value);
                
                // 1. update list of words if the input word was changed
                if(0 != word_value.compareToIgnoreCase(word_value_old))
                    updateWordList();
            }
        });
        
        
        // listen to the "Enter"
        word_textfield.setOnKeyListener(new OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            // 2. open word card if `Enter` is pressed
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                && (keyCode == KeyEvent.KEYCODE_ENTER))    // && word_value_last_open_card != word_value) {
            {
                openCardForEnterEvent(v.getContext());
                
                // display a floating message
                //Toast.makeText(context, word_textfield.getText(), Toast.LENGTH_LONG).show();
                return true;
     
            } /*else if ((event.getAction() == KeyEvent.ACTION_DOWN)
                && (keyCode == KeyEvent.KEYCODE_9)) {
     
                // display a floating message
                Toast.makeText(context,
                    "Number 9 is pressed!", Toast.LENGTH_LONG).show();
                return true;
            }*/
     
            return false;
        }
     });
    }
    
    /* 2. open word card for `Enter` */
    public void openCardForEnterEvent(Context _context) {
      //String word_in_list = word_list.getSelectedOrFirstWordInList();
        String word_in_list = word_list.getFirstWordInList();
        
        //String new_word = "";
        
        // does exist the word entered by user?
        TPage _tpage = TPage.get(db, word_value);
        if(null == _tpage) { // does not exist, takes from the list  
            if(word_in_list.length()==0)
                return;     // word list is empty
            
            //new_word = word_in_list;
            _tpage = TPage.get(db, word_in_list);
        } /*else {
            new_word = word_value;
        }*/
        /*
        if( word_value_last_open_card.length() == 0 ||  // if 0 word cards are opened
            ( new_word.length() > 0 && // there is a word to search in Wiktionary
              !word_value_last_open_card.equals(new_word) // if user trying to open the same card
             )
          ) {
            word_value_last_open_card = new_word;
            //System.out.println("yes: openWordCard(); word_value_last_open_card="+word_value_last_open_card+"; new_word="+ new_word);
          */  
            
            Bundle b = new Bundle();
            b.putInt("page_id", _tpage.getID()); // pass parameter: TPage _tpage by page_id
            Intent i = new Intent(_context, WCActivity.class);
            i.putExtras(b);
            _context.startActivity(i);
        //}
    }
    
    /** Gets text value of editable TextField. */
    public String getWordValue() {
        word_value = word_textfield.getText().toString();
        return word_value;
    }
    
    /** Sets value of EditBox. */
    public void setWordValue( String _word_value ){
        //System.err.println("QueryTextString.setWordValue(), _word_value=" + _word_value);

        word_textfield.setText(_word_value); // word_Text.text = _word_value;
        word_value = _word_value;

        //updateWordList();
    }
    
    /** Appear, disappear small "x" to clear text in search field. */
    public void updateDeleteXMark( String _word_value ){

        if(null != _word_value && _word_value.length() > 0) {
            word_textfield.setCompoundDrawables(null, null, x_clear_button_mode, null);
            //System.out.println("Yes, setCompoundDrawables(), x_clear_button_mode = " + x_clear_button_mode.toString());
        } else {
            word_textfield.setCompoundDrawables(null, null, null, null);
        }
    }

    /** Remembers old (previous) text value of EditBox. */
    public void setWordValueOld( String _word_value_old ){
        word_value_old = _word_value_old;
    }

    /** Remembers previous text value of EditBox. */
    public void saveWordValue(){
        if(null == word_value || word_value.length() == 0) {
            word_value_old = "";
        } else {
            word_value_old = word_value.trim();
        }
    }
    

    /** Interface to the real function updateWordList(). */
    public void updateWordList() {
        
        if(!isAbleUpdateWordList())
            return;
        
        if(null == word_list) {
            System.out.println("Error: QueryTextString.updateWordList(): word_list is empty, non-initialized");
            return;
        }
        word_list.updateWordList(   word_list.getSkipRedirects(),
                                    word_value);
        //word_value_old = word_value.trim(); //saveWordValue();
    }
    
}
