/* QueryTextString.fx - Words filter by a presence of meaning,
 * semantic relations, translations.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */
package wiwordik.search_window;

import wiwordik.word_card.WC;
import wikipedia.sql.Connect;

import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextBox;



/** Text edit field with a user search text query.
 */
public class QueryTextString {

    public var word0: String;
    public var wikt_parsed_conn : Connect;

    var word_value_old = word0;

    var word_value: String = bind word_Text.rawText;

    var word_list : WordList;
    var lang_choice : LangChoice;

    

    /** Set parameters of the class.
     * @param _word_list    list of words in the dictionary (ListView)
     * @param _lang_choice  checkboxes and text field with language codes
     */
    public function initialize( //_wikt_parsed_conn  : Connect,
                                _word_list          : WordList,
                                _lang_choice        : LangChoice
                                ) {
        //wikt_parsed_conn = _wikt_parsed_conn;
        word_list       = _word_list;
        lang_choice     = _lang_choice;
    }

    /** Gets text value of EditBox. */
    public function getWordValue() : String {
        return word_value;
    }

    /** Sets value of EditBox. */
    public function setWordValue( _word_value : String ){

        //println("QueryTextString.setWordValue(), _word_value={_word_value}");

        word_Text.text = _word_value;
        //word_value = _word_value;

        //println("QueryTextString.setWordValue(), word_value={word_value}");
        // updateWordList();
    }

    /** Remembers old (previous) text value of EditBox. */
    public function setWordValueOld( _word_value_old : String ){
        word_value_old = _word_value_old;
    }

    /** Remembers previous text value of EditBox. */
    public function saveWordValue(){
        word_value_old = word_value.trim();
    }
    



    /** Inteface to the real function updateWordList(). */
    public function updateWordList() {
        if(null == word_list) {
                println("Error: QueryTextString.updateWordList(): word_list is empty, non-initialized");
                return;
        }
        word_list.updateWordList(   lang_choice.getLangDestSelected(), // lang_dest_CheckBox_value,
                                    word_list.getSkipRedirects(),
                                    word_value);
        word_value_old = word_value.trim(); //saveWordValue();
    }
    


    public var word_Text: TextBox = TextBox {
        blocksMouse: true
        columns: 25
        //wrappingWidth: 200
        selectOnFocus: true
        text: word0
        //text: bind input_word // page_array[0].getPageTitle()
        //text: page_array[0].getPageTitle()
        /*clip: Rectangle {
        x: bind textDeltaBounds.x
         y: bind textDeltaBounds.y
         width: bind (word_Text.width - textDeltaBounds.width)
         height: bind (word_Text.height - textDeltaBounds.height)
         }*/

        action: function() { // "Enter"
            var wc = WC {}
            //wc.getDataByWord(wikt_parsed_conn, word_value.trim(), page_array);

            wc.createCXLangListByWord(wikt_parsed_conn, word_value.trim(),
                                        word_list.getPageArray());
        }

        onKeyTyped: function(e:KeyEvent){

            if(0 == word_value.trim().compareToIgnoreCase(word_value_old))
                return;

            var l = word_list.word_ListView;
            if (l.selectedItem != "" and l.selectedItem != null)
                word_Text.text = (l.selectedItem).toString();

            updateWordList();

            //System.out.println("e.code={e.code}, e.char={e.char}, word_value={word_value}, word_value.trim()={word_value.trim()}");
            //System.out.print("page_array_string: ");
            //for (p in page_array_string) {
            //    System.out.print("{p}, ");
            //}
        }
    }

}
