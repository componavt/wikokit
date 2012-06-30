/* LangChoice.java - Selection of source and target (destination) languages.
 *
 * Copyright (c) 2009-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

/** GUI element LangChoice contains checkboxes and text field with
 * language codes. Source and target languages.
 */

package wikokit.kiwidict.lang;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.sql.TLang;
import wikokit.kiwidict.search_window.QueryTextString;
import wikokit.kiwidict.wordlist.WordList;

/** GUI element LangChoice contains checkboxes and editable text field with
 * language codes. Source and target languages.
 */
public class LangChoice {

    /** Language codes for words filtering, e.g. "ru en fr" */
    TLang[] source_lang;
    
    LanguageType native_lang;
    WordList word_list;
    QueryTextString query_text_string;
    LanguageSpinner lspinner;   /* outdated: LangChoiceBox lang_choicebox; */

    
    
    /** Source language checkbox */
    CheckBox lang_source_checkbox;
    /** Source language text with list of codes */
    EditText lang_source_text;
    
    
    /** Language codes for words filtering by translation, e.g. "ru en fr" */
    TLang[] dest_lang;

    /** Whether list only articles which have these language codes */
    boolean lang_source_checkbox_value = false;

    /** Whether list only articles (in native language) which have translations
     * into these language codes */
    boolean lang_dest_checkbox_value = false;
    
    /** Gets language codes for words filtering, e.g. "ru en fr". */
    public TLang[] getSourceLang() {
        return source_lang;
    }

    private final static TLang[] NULL_TLANG_ARRAY = new TLang[0];
    
    /** Gets number of source languages for words filtering, e.g. 3 for "ru en fr". */
    public int getNumberSourceLang() {
        if(null == source_lang)
            return 0;
        return source_lang.length;
    }
    
    /** Is native language selected by user? */
    public boolean isNativeLanguageActive() {
        if(null == source_lang)
            return false;
        
        if(source_lang.length > 0 && null != source_lang[0])
            return source_lang[0].getLanguage() == native_lang;
        
        return false;
    }
    

    /** Checks whether the check box "Destination language" is selected. */
    public boolean getDestLangSelected () {
        return lang_dest_checkbox_value;
    }

    /** Interface to the real function updateWordList(). */
    public void updateWordList() {
        if(null == word_list) {
                System.out.println("Error: LangChoice.updateWordList(): word_list is empty, non-initialized");
                return;
        }
        word_list.updateWordList( word_list.getSkipRedirects(),
                                  query_text_string.getWordValue()
                                );
        query_text_string.saveWordValue();
    }
    
    public LangChoice () {
        source_lang = NULL_TLANG_ARRAY;
        dest_lang   = NULL_TLANG_ARRAY;
    }
    
    public void enableLangSource() {
        lang_source_text.setEnabled(true);
        //lang_source_text.setFocusable(true);
        lang_source_text.setFocusableInTouchMode(true);
    }
    
    public void disableLangSource() {
        lang_source_text.setEnabled(false);
        lang_source_text.setFocusable(false);
        //lang_source_text.setFocusableInTouchMode(false);
        
        lang_source_checkbox.setChecked(false);
        lang_source_checkbox_value = false;
    }    
    
    
    /** Set parameters of the class.
     * @param _word_list    list of words in the dictionary (ListView)
     * @param _query_text_string field with a user search text query
     * @param _native_lang 
     */
    public void initialize(WordList _word_list,
                           QueryTextString _query_text_string,
                           LanguageSpinner _lspinner, //outdated: LangChoiceBox _lang_choicebox,
                           String source_lang_codes,
                           LanguageType _native_lang,
                           
                           // GUI
                           CheckBox _lang_source_checkbox,
                           EditText _lang_source_text
                          ) {
        native_lang     = _native_lang;
        word_list       = _word_list;
        query_text_string = _query_text_string;
        lspinner  = _lspinner;
        
        // GUI
        lang_source_checkbox = _lang_source_checkbox;
        lang_source_text = _lang_source_text;
        
        lang_source_text.setText(source_lang_codes);
        disableLangSource();
        
        addListener();
       
        source_lang = TLang.parseLangCode(source_lang_codes);
        
        /* todo someday
        lang_dest_checkbox.setText("Translation language");
        Tooltip tp2 = new Tooltip();
        tp2.setText( "List words only with translation\n" + 
                    "to the languages defined\n" + 
                    "by language code (e.g. de, fr)");
        lang_dest_checkbox.setTooltip(tp2);
        
        enable(lang_dest_text, false);
        */
        
        
        // If user clicks CheckBox and select filtering language codes, e.g.: uk de fr
        lang_source_checkbox.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                
                boolean b_selected = ((CheckBox) v).isChecked();
                
                //todo destination lang code
                // if(b_selected) {    // disable destination (target, translation) language check box and text field
                //    lang_dest_checkbox.setSelected(false);
                //    lang_dest_text.setDisable(true);
                //}
                
                //lang_source_text.setDisable(!b_selected);
                
                if(b_selected)
                    enableLangSource();
                else
                    disableLangSource();
                
                if (lang_source_checkbox_value != b_selected) {
                    lang_source_checkbox_value  = b_selected;
                    //System.out.println("lang_source_checkbox changed");

                    if(!b_selected) {
                        source_lang = NULL_TLANG_ARRAY; // without filter, all languages
                        updateWordList();
                    } else {
                        updateWordListIfLangSourceChanged();
                    }
                }
            }
        });
               
/*        // If user clicks destination CheckBox (only words with translations to the select filtering language codes, e.g.: uk de fr)
        lang_dest_checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val)
            {
                boolean b_selected = lang_dest_checkbox.isSelected();
                
                lang_dest_text.setDisable(!b_selected);
                
                if(b_selected) {    // disable source language check box and text field
                    lang_source_checkbox.setSelected(false);
                    lang_source_text.setDisable(true);
                }
                
                if (lang_dest_checkbox_value != b_selected) {
                    lang_dest_checkbox_value  = b_selected;

                    if(!b_selected) {
                        dest_lang = NULL_TLANG_ARRAY; // without filter, with translations to all languages
                        updateWordList();
                    } else {
                        updateWordListIfLangDestChanged();
                    }
                }
            }
        });
*/        
        
        /** User can edit text line with desination (i.e. with translation) filtering language codes, e.g.: uk de fr */
/*        lang_dest_text.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                updateWordListIfLangDestChanged();
            }
        });
        */
    }
    
    /** User can edit text line with source filtering language codes, e.g.: uk de fr */
    public void addListener() {
        
        // add a listener to keep track user input
        lang_source_text.addTextChangedListener(new TextWatcher()
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
                //System.out.println("lang_source_text.handle");
                updateWordListIfLangSourceChanged();
            }
         });
    }
    
    private boolean language_source_is_passive = false;
    
    public boolean isLanguageSourcePassive() {
        return language_source_is_passive;
    }
    
    public void setLanguageSourceActive( boolean language_source_is_active) {
        language_source_is_passive = !language_source_is_active;
    }
    
    public void updateWordListIfLangSourceChanged () {    
        //System.out.println("LangChoice::updateWordListIfLangSourceChanged()");
        
        if(language_source_is_passive)
            return;
        
        String s = lang_source_text.getText().toString();
        // if list of source languages is the same then skip any changes
        if(TLang.isEquals(source_lang, s))
            return;

        source_lang = TLang.parseLangCode(s);
        
        // update ChoiceBox, let's select in dropdown menu the same language as user types in  text field
        if(source_lang.length > 0) {
            /*if(source_lang[0].getLanguage() == native_lang) {
                
                lspinner.selectAllLanguagesInDropdownMenu();
                source_lang = NULL_TLANG_ARRAY; // without filter, all languages
                // todo: list only native words
                // lspinner.selectLanguageInDropdownMenu( source_lang[0].getLanguage() );
                // ...
                
            } else {*/
                lspinner.selectLanguageInDropdownMenu( source_lang[0].getLanguage() );
            //}
        } else {
            //lspinner.selectAllLanguagesInDropdownMenu(); // let's select search within "All languages" words 
            lspinner.selectLanguageInDropdownMenu(native_lang); // let's native lang by default - it will be more faster than "All lang"
        }
        
        updateWordList();
    }
    
    public void updateWordListIfLangDestChanged () {
  /*
   * todo
   * 
        String s = lang_dest_text.getText();
        // if list of dest languages is the same then skip any changes
        if(TLang.isEquals(dest_lang, s))
            return;

        dest_lang = TLang.parseLangCode(s);
        updateWordList();
   */
    }
}
