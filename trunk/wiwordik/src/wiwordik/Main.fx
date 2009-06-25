/* Main.fx - visualization of parsed Wiktionary database (wikt_parsed).
 *
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik;

import java.lang.*;

import wikt.api.*;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;
import javafx.ext.swing.SwingComboBox;
import javafx.ext.swing.SwingComboBoxItem;
import javafx.ext.swing.SwingList;
import javafx.ext.swing.SwingListItem;


// ===========
// Wiktionary parsed database
// ===========

def wikt_parsed_conn = new Connect();

function init() {

    wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);

    TLang.createFastMaps(wikt_parsed_conn);   // once upon a time: use Wiktionary parsed db
    TPOS.createFastMaps(wikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
}
init();
def native_lang : LanguageType = wikt_parsed_conn.getNativeLanguage();

// Application Bounds
//var sceneWidth: Number = bind scene.width;
//var sceneHeight: Number = bind scene.height;

// ===========
// Statistics
// ===========
/*
var OutputPanel: HBox = HBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [OutputLabel, OutputGroup ] //, clearButton]
    spacing: 10
};

var OutputLabel = Text {
    y: 8
    font: Font { 
        name: "sansserif",
        size: 12
    }
    fill: Color.BLACK
    content: "Back door:"
    textOrigin: TextOrigin.TOP
}
var OutputGroup = Group {
    content: [ OutputText ] //, OutputTextBorder ]

};
var OutputText: TextBox = TextBox {
    blocksMouse: true
    columns: 20
    selectOnFocus: false
    text: "Messages"
}*/



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

//                                any (first) 10 words, since "" == prefix
var page_array: TPage[] = TPage.getByPrefix(wikt_parsed_conn, "", 10);

var page_array_string: String[];
copyWordsToStringArray();

var page_listItems: SwingListItem[] = SwingListItem{};

var lang_pos_array : TLangPOS[];

/** Current TPage corresponds to selected word. */
var tpage : TPage;

/** Word's definition, semantic relations, and quotations. */
var definition_text_value : String = "word's definition, semantic relations, and quotations";

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


/*var word_List = SwingList {
    //translateX: 113
    width: 222
    height: 333
    // selectedIndex: 1

    items: bind page_listItems
}*/

/** Prints meanings for each language.

    Wiktionary language (e.g. Russian in Russian Wiktionary)
    should be in first place in enumeration of definitions in different languages.
*/
function getDefinitionsForLanguage(
                page_title : String,            // source word
                _lang_pos_array : TLangPOS[])
                : String {

    var lang_pos_def_collection : String[];     // collection of definitions for each Lang and POS

    for (i in [0.. sizeof _lang_pos_array-1]) {
        def lang_pos : TLangPOS = _lang_pos_array[i];
        //TMeaning.get(arg0, arg1) //lang_pos.
        //definition_Text.content = _lang_pos_array.size().toString(); // number of lang-POS pairs

        // simple
        def lang : LanguageType = lang_pos.getLang().getLanguage();
        def pos : POS = lang_pos.getPOS().getPOS();

        var s : String = "{lang.getName()} ({lang.getCode()}):{pos.toString()}\n";

        def definitions : String[] = WTMeaning.getDefinitionsByPageLang(wikt_parsed_conn, lang, page_title);

        def synonyms : String[] = WTRelation.getForEachMeaningByPageLang(wikt_parsed_conn, lang, page_title, Relation.synonymy);
        // ["", "synonyms 2"];

        for (j in [0.. sizeof definitions-1]) {
            s = s.concat("  {j+1}. {definitions[j]}\n");

            if(sizeof synonyms > j and synonyms[j].length() > 0)
                s = s.concat("    Syn.: {synonyms[j]}\n");
                // todo: Text {fill: Color.BLUE; content: synonyms[j]}
        }

        if(lang == native_lang) {
            insert s before lang_pos_def_collection[0];
        } else {
            insert s into lang_pos_def_collection;
        }
    }

    var res : String;
    if (1 == sizeof lang_pos_def_collection) { // let's skip numbering "1)" if there is only one lang
        res = lang_pos_def_collection[0];
    } else {
        for (i in [0.. sizeof lang_pos_def_collection-1]) {
            def s : String = lang_pos_def_collection[i];
            res = "{res}{i+1}) {s}\n \n"; // "\n \n" because the bug in JavaFX http://javafx-jira.kenai.com/browse/JFXC-3299
        }
    }
    return res;
}



/** Word is a selected word in the list, an index is a number of the word.
*/
function getDataForSelectedWordByTPage (_tpage : TPage) {
    //word : String, index:Integer) {

    def word : String = _tpage.getPageTitle();
    page_title_Label.text = word;

    //var _tpage : TPage = page_array[index];
    //var page_id : Integer = tpage.getID();
    
    // complex
    lang_pos_array = TLangPOS.get(wikt_parsed_conn, _tpage);     // var lang_pos_array : TLangPOS[]
                         
                         //TLangPOS.getByID(wikt_parsed_conn, page_id);
                                        //word_value;
    // Prints meanings for each language
    definition_text_value = getDefinitionsForLanguage(word, lang_pos_array);
}

/** Word is given by user, language is uknown, so prints all languages.
 *
 * If a word (printed by user) is absent in dictionary, then print first
 * word from the list of nearest words.
**/
function getDataByWord(word : String) {

    page_title_Label.text = word;

    tpage = TPage.get(wikt_parsed_conn, word);

    if(null == tpage and sizeof page_array > 0) // If a word is absent in dictionary...
        tpage = page_array[0];

    // Prints meanings for each language
    if(null != tpage) {
        lang_pos_array = TLangPOS.get (wikt_parsed_conn, tpage);
        definition_text_value = getDefinitionsForLanguage(tpage.getPageTitle(), lang_pos_array);
    } else {
        definition_text_value = "";
    }

    // System.out.println("\n\n. getDataByWord(). definition_Text.content = {definition_text_value}.");
}

var word_ListView: ListView = ListView {

    // items: bind page_array_string
    
    items: bind for(_tpage in page_array) { _tpage.getPageTitle() }

    layoutInfo: LayoutInfo { width: 150 }

    
    onMouseClicked: function (me: MouseEvent) {
        var l = word_ListView;
        if (me.clickCount >= 2 and l.selectedItem != "" and l.selectedItem != null) {
            
            // get data for "page_array[l.selectedIndex]"
            getDataForSelectedWordByTPage( page_array[ l.selectedIndex ]);
            
            //var word_value = (l.selectedItem).toString();
            //getDataForSelectedWord(word_value, l.selectedIndex);
        }
    }

    /*if (me.clickCount >= 2 and chooseProject ) {
        //showTaskDetails(dataHandler.getProjectTask(list.selectedItem.text));
        task.category = (list.selectedItem).toString();
        controller.showTaskDetails(task);
    } else if (me.clickCount >= 2 and list.selectedItem != "" and list.selectedItem != null) {
        var pc:ProjectModel =
            dataHandler.getProjectCategory((list.selectedItem).toString());
        showProgress(pc.name);
    }*/
}


/*var textDeltaBounds: Rectangle = Rectangle {
    x: 2
    y: 2
    width: 14
    height: 5
};*/

var word_value: String = bind word_Text.rawText;
var word_Text: TextBox = TextBox {
    blocksMouse: true
    columns: 25
    //wrappingWidth: 200
    selectOnFocus: true
    text: ""
    //text: bind input_word // page_array[0].getPageTitle()
    //text: page_array[0].getPageTitle()
    /*clip: Rectangle {
    x: bind textDeltaBounds.x
     y: bind textDeltaBounds.y
     width: bind (word_Text.width - textDeltaBounds.width)
     height: bind (word_Text.height - textDeltaBounds.height)
     }*/

    action: function() { // "Enter"
        getDataByWord(word_value.trim());
    }
    
    onKeyTyped: function(e:KeyEvent) {
        page_array = TPage.getByPrefix(wikt_parsed_conn, word_value.trim(), 20);
        page_array_string = copyWordsToStringArray();

        //System.out.println("e.code = {e.code}, e.char {e.char}, word_value = {word_value}");
        //System.out.print("page_array_string: ");
        //for (p in page_array_string) {
        //    System.out.print("{p}, ");
       // }
    }
}
//word_value = page_array[0].getPageTitle();
//var input_word : String = page_array[0].getPageTitle();
//var input_word bind word_Text.text;


var page_title_Label: Label = Label {
            //x: 10  y: 30
            font: Font { size: 16 }
            //content: "слово"
            text: "page_title"
        }

//var definition_Text: TextBox = TextBox {
var definition_Text: Text = Text {
            //x: 10  y: 30
            //font: Font { size: 24 }
            //fill: Color.BLUE

            content: bind definition_text_value // "line 1\n\nline2\n \nline3"
            wrappingWidth: 380
            
            //text: bind definition_text_value
            //columns: 44
            //height: 55
            //style: "border-radius:20;"

            font: Font {
                name: "sansserif"
                size: 12
            }

            /*clip: Rectangle {
            //x: bind textDeltaBounds.x
             //y: bind textDeltaBounds.y
             width: 200 //width: bind (word_Text.width - textDeltaBounds.width)
             height: 200 //height: bind (word_Text.height - textDeltaBounds.height)
             }*/
        }


var outputPanel_VBox1: VBox = VBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [word_Text, word_ListView ]    // word_List, word_ComboBox,
    spacing: 10
};

var result_VBox2: VBox = VBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [page_title_Label, definition_Text]
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
        /*clip: Rectangle {
            width: bind sceneWidth
            height: bind sceneHeight
            arcWidth: 20
            arcHeight: 20
        }*/
    }
    fill: Color.TRANSPARENT

}

// Application User Interface
var stage: Stage = Stage {
    title: "Wiwordik 0.01 ({wikt_parsed_conn.getDBName()})"
    //    resizable: false
    visible: true
    //    style: StageStyle.TRANSPARENT
    scene: bind scene
    width: 640
    height: 480
    // content: "Wiktionary browser"
}



