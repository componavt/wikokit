/* LangChoice.java - Selection of source and target (destination) languages.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.search_window;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.sql.TLang;

/** GUI element LangChoice contains checkboxes and text field with
 * language codes. Source and target languages.
 */
public class LangChoice {

    /** Language codes for words filtering, e.g. "ru en fr" */
    TLang[] source_lang;
    //var lang_source_value: String = bind lang_source_Text.rawText;
    
    private final static TLang[] NULL_TLANG_ARRAY = new TLang[0];
    
    LanguageType native_lang;
    WordList word_list;
    QueryTextString query_text_string;
    LangChoiceBox lang_choicebox;

    /** Language codes for words filtering by translation, e.g. "ru en fr" */
    TLang[] dest_lang;

    /** Whether list only articles which have these language codes */
    boolean lang_source_checkbox_value = false;

    /** Whether list only articles (in native language) which have translations
     * into these language codes */
    boolean lang_dest_checkbox_value = false;

    CheckBox lang_source_checkbox = new CheckBox();// "Source language"
    TextField lang_source_text = new TextField();
    
    CheckBox lang_dest_checkbox  = new CheckBox();
    TextField lang_dest_text = new TextField();
    
    public HBox lang_source_hbox = new HBox();
    public HBox lang_dest_hbox = new HBox();
    
    /** Gets language codes for words filtering, e.g. "ru en fr". */
    public TLang[] getSourceLang() {
        return source_lang;
    }

    /** Gets number of source languages for words filtering, e.g. 3 for "ru en fr". */
    public int getNumberSourceLang() {
        if(null == source_lang)
            return 0;
        return source_lang.length;
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
        word_list.updateWordList(   word_list.getSkipRedirects(),
                                    query_text_string.getWordValue()
                                );
        query_text_string.saveWordValue();
    }
    
    public LangChoice () {
        source_lang = NULL_TLANG_ARRAY;
        dest_lang   = NULL_TLANG_ARRAY;
    }
    
    /** Set parameters of the class.
     * @param _word_list    list of words in the dictionary (ListView)
     * @param _query_text_string field with a user search text query
     * @param _native_lang 
     */
    public void initialize(WordList _word_list,
                           QueryTextString _query_text_string,
                           LangChoiceBox _lang_choicebox,
                           String source_lang_codes,
                           LanguageType _native_lang
                          ) {
        native_lang     = _native_lang;
        word_list       = _word_list;
        query_text_string = _query_text_string;
        lang_choicebox  = _lang_choicebox;
        
        // GUI
        lang_source_checkbox.setText("Source language");
        Tooltip tp = new Tooltip();
        tp.setText( "Words filtering\n" + 
                    "by language code\n" + 
                    "(e.g. de, fr)");
        lang_source_checkbox.setTooltip(tp);
        
        lang_dest_checkbox.setText("Translation language");
        Tooltip tp2 = new Tooltip();
        tp2.setText( "List words only with translation\n" + 
                    "to the languages defined\n" + 
                    "by language code (e.g. de, fr)");
        lang_dest_checkbox.setTooltip(tp2);
                
        lang_source_text.setText(source_lang_codes);
        lang_source_text.setDisable(true); //disable: bind not lang_source_CheckBox_value
        lang_dest_text.setDisable(true);
        
        lang_source_hbox.setSpacing(10);
        lang_source_hbox.getChildren().addAll(lang_source_checkbox);
        lang_source_hbox.getChildren().addAll(lang_source_text);
        
        lang_dest_hbox.setSpacing(10);
        lang_dest_hbox.getChildren().addAll(lang_dest_checkbox);
        lang_dest_hbox.getChildren().addAll(lang_dest_text);
        
        
        // If user clicks CheckBox and select filtering language codes, e.g.: uk de fr
        lang_source_checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val)
            {
                boolean b_selected = lang_source_checkbox.isSelected();
                                
                if(b_selected) {    // disable destination (target, translation) language check box and text field
                    lang_dest_checkbox.setSelected(false);
                    lang_dest_text.setDisable(true);
                }
                
                lang_source_text.setDisable(!b_selected);
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
        
        /** User can edit text line with source filtering language codes, e.g.: uk de fr */
        lang_source_text.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                //System.out.println("lang_source_text.handle");
                updateWordListIfLangSourceChanged();
            }
        });
        
        
        // If user clicks destination CheckBox (only words with translations to the select filtering language codes, e.g.: uk de fr)
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
        
        
        /** User can edit text line with desination (i.e. with translation) filtering language codes, e.g.: uk de fr */
        lang_dest_text.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                updateWordListIfLangDestChanged();
            }
        });
    }
    
    public void updateWordListIfLangSourceChanged () {    
        //System.out.println("LangChoice::updateWordListIfLangSourceChanged()");
        
        String s = lang_source_text.getText();
        // if list of source languages is the same then skip any changes
        if(TLang.isEquals(source_lang, s))
            return;

        source_lang = TLang.parseLangCode(s);
        
        // update ChoiceBox, let's select in dropdown menu the same language as user types in  text field
        if(source_lang.length > 0) {
            if(source_lang[0].getLanguage() == native_lang) {
                source_lang = NULL_TLANG_ARRAY; // without filter, all languages
                // todo: list only native words
            } else {
                lang_choicebox.selectLanguageInChoiceBox( source_lang[0].getLanguage() );
            }
        }
        
        updateWordList();
    }
    
    public void updateWordListIfLangDestChanged () {
    
        String s = lang_dest_text.getText();
        // if list of dest languages is the same then skip any changes
        if(TLang.isEquals(dest_lang, s))
            return;

        dest_lang = TLang.parseLangCode(s);
        updateWordList();
    }
    
    
    
  
  /*  var lang_dest_checkbox: CheckBox = CheckBox {
        text: "Translation language"

        onMouseReleased: function(e:MouseEvent) {

                if (lang_dest_checkbox_value != lang_dest_checkbox.selected) {
                    lang_dest_checkbox_value  = lang_dest_checkbox.selected;

                    if(not lang_dest_checkbox.selected) {
                        dest_lang = null; // without filer, all languages
                        updateWordList();
                        lang_source_CheckBox.disable = false;
                    } else {
                        lang_source_CheckBox.disable = true;
                        //System.out.println("CheckBox 1. lang_dest_text={lang_dest_text.rawText}, source_lang.size={source_lang.size()}");

                        // if list of dest languages is the same then skip any changes
                        if(TLang.isEquals(dest_lang, lang_dest_text.rawText))
                            return;

                        dest_lang = TLang.parseLangCode(lang_dest_text.rawText);
                        //System.out.println("CheckBox 2. OK. It's changed. dest_lang.size={source_lang.size()}");

                        updateWordList();
                    }
                }
            }
      }
    }

    var lang_dest_text: TextBox = TextBox {
        disable: bind not lang_dest_checkbox_value
        blocksMouse: true
        columns: 12
        selectOnFocus: true
        text: "en de fr os uk"

        onKeyTyped: function(e:KeyEvent){

            //System.out.println("TextBox 1. lang_dest_text={lang_dest_text.rawText}, dest_lang.size={dest_lang.size()}");

            // if list of dest languages is the same then skip any changes
            if(TLang.isEquals(dest_lang, lang_dest_text.rawText))
                return;

            dest_lang = TLang.parseLangCode(lang_dest_text.rawText);
            //System.out.println("TextBox 2. OK. It's changed. dest_lang.size={dest_lang.size()}");

            updateWordList();
        }
    }

    public var lang_source_HBox: HBox = HBox {
        content: [
                lang_source_CheckBox, lang_source_Text
        ]
        spacing: 10
    };

    public var lang_dest_hbox: HBox = HBox {
        content: [
                lang_dest_checkbox, lang_dest_text
        ]
        spacing: 10
    };
*/
    

}
