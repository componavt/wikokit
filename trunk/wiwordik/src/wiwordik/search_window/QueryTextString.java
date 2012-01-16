/* QueryTextString.fx - Words filter by a presence of meaning,
 * semantic relations, translations.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wiwordik.search_window;

//import wiwordik.word_card.WC;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import wikipedia.sql.Connect;

import javafx.scene.input.KeyEvent;
import wikt.sql.TPage;
import wiwordik.word_card.WC;
//import javafx.scene.control.TextBox;



/** Text edit field with a user search text query.
 */
public class QueryTextString {

    public String word0;
    public Connect wikt_parsed_conn;

    String word_value_last_open_card = ""; // value of last word, with which the wordcard was open, to prevent double opening of the same word card
    String word_value_old = word0;
    String word_value = ""; // var word_value: String = bind word_Text.rawText;

    WordList word_list;
    LangChoice lang_choice;

    public final TextField word_textfield = new TextField();

    /** Set parameters of the class.
     * @param _word0 tips for the reader: recommendation and tutorial
     * @param _word_list    list of words in the dictionary (ListView)
     * @param _lang_choice  checkboxes and text field with language codes
     */
    public void initialize( String _word0, 
                            Connect _wikt_parsed_conn,
                            WordList _word_list,
                            LangChoice _lang_choice
                           ) {
        word0           = _word0;
        wikt_parsed_conn = _wikt_parsed_conn;
        word_list       = _word_list;
        lang_choice     = _lang_choice;
        
        /*
        word_Text.focusedProperty().addListener(new ChangeListener<Boolean>(){
            
            // focus changed
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            }
        });*/
        
        /** Updates vertical list of words, open card for `Enter` */
        word_textfield.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                //System.out.println("KEY_RELEASED, word_value="+word_value);
                //event.consume();
                
                word_value_old = word_value;
                word_value = word_textfield.getText().trim();
                
                // 1. update list of words if the input word was changed
                if(0 != word_value.compareToIgnoreCase(word_value_old))
                    updateWordList();
                
                // 2. open word card for `Enter`
                if (event.getCode() == KeyCode.ENTER) { // && word_value_last_open_card != word_value) {
                    
                    String word_in_list = word_list.getSelectedOrFirstWordInList();
                    String new_word = "";
                    
                    // does exist the word entered by user?
                    TPage _tpage = TPage.get(wikt_parsed_conn, word_value);
                    if(null == _tpage) { // does not exist, takes from the list  
                        if(word_in_list.length()==0)
                            return;     // word list is empty
                        
                        new_word = word_in_list;
                        _tpage = TPage.get(wikt_parsed_conn, word_in_list);
                    } else {
                        new_word = word_value;
                    }
                    
                    if( word_value_last_open_card.length() == 0 ||  // if 0 word cards are opened
                        ( new_word.length() > 0 && // there is a word to search in Wiktionary
                          !word_value_last_open_card.equals(new_word) // if user trying to open the same card
                         )
                      ) {
                        word_value_last_open_card = new_word;
                        //System.out.println("yes: openWordCard(); word_value_last_open_card="+word_value_last_open_card+"; new_word="+ new_word);
                        
                        WC wc = new WC();
                        wc.createCXLangList (wikt_parsed_conn, _tpage);
                        
                        // ??? no: word_list.openWordCard();
                    }
                }
            }
        });
    }

    /** Gets text value of editable TextField. */
    public String getWordValue() {
        word_value = word_textfield.getText();
        return word_value;
    }

    /** Sets value of EditBox. */
    public void setWordValue( String _word_value ){
        //System.err.println("QueryTextString.setWordValue(), _word_value=" + _word_value);

        word_textfield.setText(_word_value); // word_Text.text = _word_value;
        word_value = _word_value;

        ////updateWordList();
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
        if(null == word_list) {
            System.out.println("Error: QueryTextString.updateWordList(): word_list is empty, non-initialized");
            return;
        }
        word_list.updateWordList(   word_list.getSkipRedirects(),
                                    word_value);
        //word_value_old = word_value.trim(); //saveWordValue();
    }
}
