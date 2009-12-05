/* FilterMeanSemRelTrans.fx - Words filter by a presence of meaning,
 * semantic relations, translations.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik.search_window;

import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

/** Words filter by a presence of meaning, semantic relations, translations,
 * etc. (CheckBoxes).
 */
public class FilterMeanSemRelTrans {

    /** Whether list only articles with definitions */
    var meaning_CheckBox_value: Boolean = false;

    /** Whether list only articles with semantic relations */
    var sem_rel_CheckBox_value: Boolean = false;
    
    var word_list : WordList;
    var lang_choice : LangChoice;
    var query_text_string : QueryTextString;


    /** Set parameters of the class.
     * @param _word_list    list of words in the dictionary (ListView)
     * @param _lang_choice  checkboxes and text field with language codes
     * @param _query_text_string field with a user search text query
     */
    public function initialize( //_wikt_parsed_conn  : Connect,
                                _word_list          : WordList,
                                _lang_choice        : LangChoice,
                                _query_text_string  : QueryTextString
                                ) {
        //wikt_parsed_conn = _wikt_parsed_conn;
        word_list       = _word_list;
        lang_choice     = _lang_choice;
        query_text_string = _query_text_string;

    }

    public function setWordList( _word_list : WordList) {
        word_list = _word_list;
    }

    /** Whether to filter words by a presence of word's definition. */
    public function filterByMeaning() : Boolean {
        return meaning_CheckBox_value;
    }

    /** Whether to filter words by a presence of semantic relations. */
    public function filterBySemanticRelation() : Boolean {
        return sem_rel_CheckBox_value;
    }

    /** Inteface to the real function updateWordList(). */
    function updateWordList() {
        if(null == word_list) {
                println("Error: LangChoice.updateWordList(): word_list is empty, non-initialized");
                return;
        }
        word_list.updateWordList(   lang_choice.getLangDestSelected(), // lang_dest_CheckBox_value,
                                    word_list.getSkipRedirects(),
                                    query_text_string.getWordValue()
                                );
        query_text_string.saveWordValue();
    }
    
    public var meaning_CheckBox: CheckBox = CheckBox {
        text: "Meaning"

        onMouseReleased: function(e:MouseEvent) {

            if (meaning_CheckBox_value != meaning_CheckBox.selected) {
                meaning_CheckBox_value  = meaning_CheckBox.selected;

                updateWordList();
            }
        }
    }

    public var sem_rel_CheckBox: CheckBox = CheckBox {
        text: "Semantic Relation"

        onMouseReleased: function(e:MouseEvent) {

            if (sem_rel_CheckBox_value != sem_rel_CheckBox.selected) {
                sem_rel_CheckBox_value  = sem_rel_CheckBox.selected;

                updateWordList();
            }
        }
    }

}
