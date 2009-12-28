/* LangChoice.fx - Selection of source and target (destination) languages.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik.search_window;

import wikt.sql.*;
import wikt.constant.*;

import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;


/** GUI element LangChoice contains checkboxes and text field with
 * language codes. Source and target languages.
 */
public class LangChoice {

    /** Language codes for words filtering, e.g. "ru en fr" */
    var source_lang : TLang[];
    //var lang_source_value: String = bind lang_source_Text.rawText;

    /** Language codes for words filtering by translation, e.g. "ru en fr" */
    var dest_lang : TLang[];

    /** Whether list only articles which have these language codes */
    var lang_source_CheckBox_value: Boolean = false;

    /** Whether list only articles (in native language) wich have translations
     * into these language codes */
    var lang_dest_CheckBox_value: Boolean = false;

    var word_list : WordList;
    var query_text_string : QueryTextString;

    /** Gets language codes for words filtering, e.g. "ru en fr". */
    public function getSourceLang() : TLang[] {
        return source_lang;
    }

    /** Gets number of source languages for words filtering, e.g. 3 for "ru en fr". */
    public function getNumberSourceLang() : Integer {
        if(null == source_lang)
            return 0;
        return sizeof source_lang;
    }

    /** Checks whether the check box "Destination language" is selected. */
    public function getLangDestSelected () : Boolean {
        return lang_dest_CheckBox_value;
    }

    /** Inteface to the real function updateWordList(). */
    public function updateWordList() {
        if(null == word_list) {
                println("Error: LangChoice.updateWordList(): word_list is empty, non-initialized");
                return;
        }
        word_list.updateWordList(   lang_dest_CheckBox_value,
                                    word_list.getSkipRedirects(),
                                    query_text_string.getWordValue()
                                );
        query_text_string.saveWordValue();
    }
    
    var lang_source_CheckBox: CheckBox = CheckBox {
        text: "Source language"

        onMouseReleased: function(e:MouseEvent) {
                
                if (lang_source_CheckBox_value != lang_source_CheckBox.selected) {
                    lang_source_CheckBox_value  = lang_source_CheckBox.selected;

                    if(not lang_source_CheckBox.selected) {
                        source_lang = null; // without filter, all languages
                        updateWordList();
                    } else {
                        //System.out.println("CheckBox 1. lang_source_Text={lang_source_Text.rawText}, source_lang.size={source_lang.size()}");

                        // if list of source languages is the same then skip any changes
                        if(TLang.isEquals(source_lang, lang_source_Text.rawText))
                            return;

                        source_lang = TLang.parseLangCode(lang_source_Text.rawText);
                        //System.out.println("CheckBox 2. OK. It's changed. source_lang.size={source_lang.size()}");

                        updateWordList();
                    }
                }
            }
      }

    var lang_dest_CheckBox: CheckBox = CheckBox {
        text: "Translation language"

        onMouseReleased: function(e:MouseEvent) {

                if (lang_dest_CheckBox_value != lang_dest_CheckBox.selected) {
                    lang_dest_CheckBox_value  = lang_dest_CheckBox.selected;

                    if(not lang_dest_CheckBox.selected) {
                        dest_lang = null; // without filer, all languages
                        updateWordList();
                        lang_source_CheckBox.disable = false;
                    } else {
                        lang_source_CheckBox.disable = true;
                        //System.out.println("CheckBox 1. lang_dest_Text={lang_dest_Text.rawText}, source_lang.size={source_lang.size()}");

                        // if list of dest languages is the same then skip any changes
                        if(TLang.isEquals(dest_lang, lang_dest_Text.rawText))
                            return;

                        dest_lang = TLang.parseLangCode(lang_dest_Text.rawText);
                        //System.out.println("CheckBox 2. OK. It's changed. dest_lang.size={source_lang.size()}");

                        updateWordList();
                    }
                }
            }
      }

    var lang_source_Text: TextBox = TextBox {
        disable: bind not lang_source_CheckBox_value
        blocksMouse: true
        columns: 12
        selectOnFocus: true
        text: "ru en de fr os uk"

        onKeyTyped: function(e:KeyEvent){

            //System.out.println("TextBox 1. lang_source_Text={lang_source_Text.rawText}, source_lang.size={source_lang.size()}");

            // if list of source languages is the same then skip any changes
            if(TLang.isEquals(source_lang, lang_source_Text.rawText))
                return;

            source_lang = TLang.parseLangCode(lang_source_Text.rawText);
            //System.out.println("TextBox 2. OK. It's changed. source_lang.size={source_lang.size()}");

            updateWordList();

           /* {
    //        System.out.println("updateWordList(), word_value={lang_source_value}, word_value.trim()={lang_source_value.trim()}, lang_source_value={lang_source_value}");

            page_array = TPage.getByPrefix(wikt_parsed_conn, word_value.trim(),
                                n_words_list, b_skip_redirects,
                                source_lang,  meaning_CheckBox_value,
                                                    sem_rel_CheckBox_value);
            page_array_string = copyWordsToStringArray();
            word_value_old = word_value.trim();
            }
    */
                //System.out.println("e.code={e.code}, e.char={e.char}, word_value={lang_source_value}, word_value.trim()={lang_source_value.trim()}");
                //System.out.print("page_array_string: ");
                //for (p in page_array_string) {
                //    System.out.print("{p}, ");
                //}
        }
    }

    var lang_dest_Text: TextBox = TextBox {
        disable: bind not lang_dest_CheckBox_value
        blocksMouse: true
        columns: 12
        selectOnFocus: true
        text: "en de fr os uk"

        onKeyTyped: function(e:KeyEvent){

            //System.out.println("TextBox 1. lang_dest_Text={lang_dest_Text.rawText}, dest_lang.size={dest_lang.size()}");

            // if list of dest languages is the same then skip any changes
            if(TLang.isEquals(dest_lang, lang_dest_Text.rawText))
                return;

            dest_lang = TLang.parseLangCode(lang_dest_Text.rawText);
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

    public var lang_dest_HBox: HBox = HBox {
        content: [
                lang_dest_CheckBox, lang_dest_Text
        ]
        spacing: 10
    };

    /** Set parameters of the class.
     * @param _word_list    list of words in the dictionary (ListView)
     * @param _query_text_string field with a user search text query
     */
    public function initialize(_word_list          : WordList,
                                _query_text_string  : QueryTextString,
                                source_lang_codes   : String
                                ) {
        word_list       = _word_list;
        query_text_string = _query_text_string;
        lang_source_Text.text = source_lang_codes;
    }

}
