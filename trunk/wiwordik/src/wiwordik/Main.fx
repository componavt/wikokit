/* Main.fx - visualization of parsed Wiktionary database (wikt_parsed).
 *
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik;

import wiwordik.word_card.WC;

import java.lang.*;

import wikt.sql.*;
import wikt.constant.*;

import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
//import javafx.ext.swing.SwingTextField;

import java.io.InputStream;
import java.lang.Exception;
import javafx.io.http.HttpRequest;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Interpolator;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextOrigin;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.LayoutInfo;

import javafx.scene.text.Text;
import javafx.scene.text.TextOrigin;

import javafx.scene.control.TextBox;
import javafx.scene.control.ListView;
import javafx.scene.control.CheckBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;
import javafx.ext.swing.SwingComboBox;
import javafx.ext.swing.SwingComboBoxItem;
import javafx.ext.swing.SwingList;
import javafx.ext.swing.SwingListItem;

def DEBUG : Boolean = false;

// Todo. Errors in parser:
// дуб (илл -> syn)
// тупица, река, самолёт (-) i.e. page.is_in_wiktionary = false

// ===========
// Wiktionary parsed database
// ===========

var wikt_parsed_conn : Connect = new Connect();

function init() {

    // MySQL
    wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);

    // SQLite                                   //Connect.testSQLite();
    //wikt_parsed_conn.OpenSQLite(Connect.RUWIKT_SQLITE, LanguageType.ru);

    TLang.createFastMaps(wikt_parsed_conn);   // once upon a time: use Wiktionary parsed db
    TPOS.createFastMaps(wikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
    TRelationType.createFastMaps(wikt_parsed_conn);
}
init();

// Application Bounds
//var sceneWidth: Number = bind scene.width;
//var sceneHeight: Number = bind scene.height;

// ===========
//
// ===========

//var adjacent_words : String[] = ["Red", "Yellow", "Green"];

/*class Model {
    var action: String;
    var selected_word_index: Integer on replace oldValue {
        action = adjacent_words[selected_word_index];
    }
}*/

/** todo: language selection
var word_ComboBox = SwingComboBox {
    //translateX: 113
    width: 222

    selectedIndex: 1
    items: for (p in page_array)
        SwingComboBoxItem {
            text: p.getPageTitle()
        }
}*/
/*
class WordListModel {
    //var words: String[];
    var words: SwingListItem[]
    var selected_word_index: Integer on replace oldValue {
        delete words;
        //var i=0;
        for (p in page_array) {
            //words[i++] = p.getPageTitle()
            insert p.getPageTitle() into words
        }
        //words = adjacent_words[selected_word_index];
    }
}*/

/** Skips #REDIRECT words if true. */
var b_skip_redirects : Boolean = false;

/** Number of words in visible in the list */
var n_words_list : Integer = 21;

/** Whether list only articles with definitions */
var meaning_CheckBox_value: Boolean = false;

/** Whether list only articles with semantic relations */
var sem_rel_CheckBox_value: Boolean = false;

var word0: String = "*с?рё*";

/** Words extracted by several letters (prefix). */ 
var page_array: TPage[] = TPage.getByPrefix(wikt_parsed_conn, word0, n_words_list,
                                // any (first) N words, since "" == prefix
                            b_skip_redirects,   meaning_CheckBox_value,
                                                sem_rel_CheckBox_value);

var page_array_string: String[];
copyWordsToStringArray();

var page_listItems: SwingListItem[] = SwingListItem{};

/** Current word card: contains TPage, corresponds to selected word. */
var wc; // = WC {}

//page_listItems[0].text = "nelik"; // new SwingListItem{ text: "testishe" };
//insert SwingListItem{ text: "testishe" } into page_listItems;

/** Copies data from TPagе[].text page_array to SwingListItem[]  page_listItems
*/
function copyWordsToListItems() {
    delete page_listItems;
    for (i in [0.. sizeof page_array-1])
        insert SwingListItem{ text: page_array[i].getPageTitle() } into page_listItems;
}
copyWordsToListItems();

/** Copies data from page_array to page_array_string
*/
function copyWordsToStringArray() {
    //var list: SwingListItem[] = SwingListItem{};

    var result : String[];
    for (p in page_array) {
        insert p.getPageTitle() into result;
        //System.out.println("copyWordsToStringArray. p.title = {p.getPageTitle()}");
    }
    
    return result;
}


/** Copies data from TPagе[].text page_array to SwingListItem[]  page_listItems
*/
function updateWordList() {

    page_array = TPage.getByPrefix(wikt_parsed_conn, word_value.trim(), 
                        n_words_list, b_skip_redirects, meaning_CheckBox_value,
                                                        sem_rel_CheckBox_value);
    page_array_string = copyWordsToStringArray();
    word_value_old = word_value.trim();
}

/*var word_List = SwingList {
    //translateX: 113
    width: 222
    height: 333
    // selectedIndex: 1

    items: bind page_listItems
}*/

var word_ListView: ListView = ListView {

    // items: bind page_array_string
    
    items: bind for(_tpage in page_array) { _tpage.getPageTitle() }

    layoutInfo: LayoutInfo { width: 150 }

    onKeyPressed: function (e: KeyEvent) {

        var l = word_ListView;
        if (l.selectedItem != "" and l.selectedItem != null)
            word_Text.text = (word_ListView.selectedItem).toString();
    }

    onMouseClicked: function (me: MouseEvent) {
        var l = word_ListView;
        if (l.selectedItem != "" and l.selectedItem != null) {
            word_Text.text = (l.selectedItem).toString();
            word_value_old = (l.selectedItem).toString();

            if (me.clickCount >= 2) {

                var wc = WC {}

                // get data for "page_array[l.selectedIndex]"
                wc.getDataForSelectedWordByTPage(wikt_parsed_conn, page_array[ l.selectedIndex ]);

                wc.createCXLangList(wikt_parsed_conn, page_array[ l.selectedIndex ]);

                //getDataForSelectedWord(word_value, l.selectedIndex);
            }
        }
    }
}

var word_value_old = word0;
var word_value: String = bind word_Text.rawText;
var word_Text: TextBox = TextBox {
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

        wc.createCXLangListByWord(wikt_parsed_conn, word_value.trim(), page_array);
    }
    
    onKeyTyped: function(e:KeyEvent){

        if(0 == word_value.trim().compareToIgnoreCase(word_value_old))
            return;
        
        var l = word_ListView;
        if (l.selectedItem != "" and l.selectedItem != null)
            word_Text.text = (l.selectedItem).toString();

        updateWordList()

        //System.out.println("e.code={e.code}, e.char={e.char}, word_value={word_value}, word_value.trim()={word_value.trim()}");
        //System.out.print("page_array_string: ");
        //for (p in page_array_string) {
        //    System.out.print("{p}, ");
        //}
    }
}
//word_value = page_array[0].getPageTitle();
//var input_word : String = page_array[0].getPageTitle();
//var input_word bind word_Text.text;


// todo to separate file 'lang_source.fx'

/** Whether list only articles wich have these language codes */
var lang_source_CheckBox_value: Boolean = false;

var lang_source_CheckBox: CheckBox = CheckBox {
        text: "Source language"

        onMouseReleased: function(e:MouseEvent) {

                if (lang_source_CheckBox_value != lang_source_CheckBox.selected) {
                    lang_source_CheckBox_value  = lang_source_CheckBox.selected;

                    updateWordList();
                }
            }
      }

var lang_source_value: String = bind lang_source_Text.rawText;
var lang_source_Text: TextBox = TextBox {
    disable: bind not lang_source_CheckBox_value
    blocksMouse: true
    columns: 12
    selectOnFocus: true
    text: "ru en de fr os uk"
   
    onKeyTyped: function(e:KeyEvent){

        // splitToLangCodes()
        // updateWordList()

        System.out.println("e.code={e.code}, e.char={e.char}, word_value={lang_source_value}, word_value.trim()={lang_source_value.trim()}");
        //System.out.print("page_array_string: ");
        //for (p in page_array_string) {
        //    System.out.print("{p}, ");
        //}
    }
}

var lang_source_HBox: HBox = HBox {
    content: [
            lang_source_CheckBox, lang_source_Text
    ]
    spacing: 10
};
// --------- eo separate file





var meaning_CheckBox: CheckBox = CheckBox {
                text: "Meaning"

                onMouseReleased: function(e:MouseEvent) { 

                    if (meaning_CheckBox_value != meaning_CheckBox.selected) {
                        meaning_CheckBox_value  = meaning_CheckBox.selected;
                        
                        updateWordList();
                    }
                }
          }

var sem_rel_CheckBox: CheckBox = CheckBox {
                text: "Semantic Relation"

                onMouseReleased: function(e:MouseEvent) {

                    if (sem_rel_CheckBox_value != sem_rel_CheckBox.selected) {
                        sem_rel_CheckBox_value  = sem_rel_CheckBox.selected;

                        updateWordList();
                    }
                }
          }

var h_filter_MRT: HBox = HBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    // text: "CheckBox:"
    content: [
            meaning_CheckBox, sem_rel_CheckBox,

        //var wc = WC {}
        //wc.getDataByWord(wikt_parsed_conn, word_value.trim(), page_array);

        //wc.createCXLangListByWord(wikt_parsed_conn, word_value.trim(), page_array);


              //  onKeyTyped: function(e:KeyEvent){

                            //System.out.println("e.code={e.code}, e.char={e.char}, word_value={word_value}, word_value.trim()={word_value.trim()}");

        //page_array = TPage.getByPrefix(wikt_parsed_conn, word_value.trim(), n_words_list, b_skip_redirects);
        //page_array_string = copyWordsToStringArray();

        //System.out.println("e.code={e.code}, e.char={e.char}, word_value={word_value}, word_value.trim()={word_value.trim()}");
        //System.out.print("page_array_string: ");
        //for (p in page_array_string) {
        //    System.out.print("{p}, ");
        //}

            CheckBox { text: "Translation" selected: false }
          ]

    spacing: 10
};


var wiki_page_Label: Label = Label {
            //x: 10  y: 30
            font: Font { size: 16 }
            text: "Wiktionary page"
}

var outputPanel_VBox1: VBox = VBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [word_Text, word_ListView]
    spacing: 10
};

var result_VBox2: VBox = VBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [  // wiki_page_Label,
                lang_source_HBox,
                h_filter_MRT
             ] //, wc.card]
    spacing: 10
};

var horizontal_Panel: HBox = HBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [outputPanel_VBox1, result_VBox2]
    spacing: 10
};

var scene: Scene = Scene {
    content: Group {
        content: bind [
            horizontal_Panel // ,
            //word_ComboBox //,
            //word_Text
        // bgImage, titleBar, titleText, divider, shopDetailsGroup, backButton, nextButton, closeButton,
        // zipSearchPanel, serviceProviderText
        ]
        //clip: Rectangle {
        //    width: bind sceneWidth
        //    height: bind sceneHeight
        //    arcWidth: 20
        //    arcHeight: 20
        //}
    }
    fill: Color.TRANSPARENT
}

// Application User Interface
var stage: Stage = Stage {
    title: "Wiwordik 0.02 ({wikt_parsed_conn.getDBName()})"
    //    resizable: false
    visible: true
    //    style: StageStyle.TRANSPARENT
    scene: bind scene
    width: 640
    height: 480
    // content: "Wiktionary browser"
}



