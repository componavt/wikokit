/* Main.fx - visualization of parsed Wiktionary database (wikt_parsed).
 *
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik;

import wiwordik.search_window.*;
import wiwordik.util.TipsTeapot;

import java.lang.*;

import wikt.sql.*;

import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

import javafx.stage.Stage;
import javafx.scene.text.Font;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;

def DEBUG : Boolean = false;

// Todo. Errors in parser:
// дуб (илл -> syn)
// тупица, река, самолёт (-) i.e. page.is_in_wiktionary = false

// ===========
// Wiktionary parsed database
// ===========

var wikt_parsed_conn : Connect = new Connect();
var native_lang : LanguageType;

function init() {

    native_lang = LanguageType.ru;
    //native_lang = LanguageType.en;

    // MySQL
    if(LanguageType.ru == native_lang) {
        wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);
    } else {
        wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, LanguageType.en);
    }

    // SQLite                                   //Connect.testSQLite();
//    wikt_parsed_conn.OpenSQLite(Connect.RUWIKT_SQLITE, LanguageType.ru);

    TLang.createFastMaps(wikt_parsed_conn);   // once upon a time: use Wiktionary parsed db
    TPOS.createFastMaps(wikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
    TRelationType.createFastMaps(wikt_parsed_conn);
}
init();

// Application Bounds
//var sceneWidth: Number = bind scene.width;
//var sceneHeight: Number = bind scene.height;

//var adjacent_words : String[] = ["Red", "Yellow", "Green"];

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


/** Skips #REDIRECT words if true. */
var b_skip_redirects : Boolean = false;

/** Number of words visible in the list */
def n_words_list : Integer = 21;

//def tips = new TipsTeapot();
//tips.generateRandomTip();
def tip : TipsTeapot = TipsTeapot.generateRandomTip();

def word0: String = tip.getQuery(); //"*с?рё*";

var lang_choice = LangChoice{};

var word_list = WordList{};

var filter_mean_sem_transl = FilterMeanSemRelTrans{};

def query_text_string = QueryTextString {
     word0: word0;
     wikt_parsed_conn: wikt_parsed_conn
}



query_text_string.initialize(word_list, lang_choice);

lang_choice.initialize(word_list, query_text_string, tip.getSourceLangCodes());

word_list.initialize(   wikt_parsed_conn,
                        query_text_string, lang_choice, filter_mean_sem_transl,
                        native_lang,
                        //word0,
                        n_words_list);

word_list.setSkipRedirects(b_skip_redirects);

filter_mean_sem_transl.initialize(word_list, lang_choice, query_text_string);

word_list.updateWordList(   lang_choice.getLangDestSelected(),
                            b_skip_redirects,
                            word0
                        );
query_text_string.saveWordValue();

//word_list.copyWordsToStringArray( word_list.getPageArray() );
//word_list.copyWordsToListItems();

function updateWordList() {

    word_list.updateWordList( lang_choice.getLangDestSelected(),
                               b_skip_redirects,
                               query_text_string.getWordValue()
                            );
    query_text_string.saveWordValue();
}

/** Checks the list of source languages. If it has been changed, then
 *  then the word list is changes.
 */
/*function checkSourceLangListIsChanged() {
   // System.out.println("1. lang_source_Text={lang_source_Text.rawText}, source_lang.size={source_lang.size()}");

    // if list of source languages is the same then skip any changes
    if(TLang.isEquals(source_lang, lang_source_Text.rawText))
        return;

    source_lang = TLang.parseLangCode(lang_source_Text.rawText);
    //System.out.println("2. OK. It's changed. source_lang.size={source_lang.size()}");

    updateWordList();
}*/


//word_value = page_array[0].getPageTitle();
//var input_word : String = page_array[0].getPageTitle();
//var input_word bind word_Text.text;

var h_filter_MRT: HBox = HBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    // text: "CheckBox:"
    content: [
            filter_mean_sem_transl.meaning_CheckBox,
            filter_mean_sem_transl.sem_rel_CheckBox,

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
    content: [query_text_string.word_Text, word_list.word_ListView]
    spacing: 10
};

var result_VBox2: VBox = VBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [  // wiki_page_Label,
                lang_choice.lang_source_HBox,
                h_filter_MRT,
                lang_choice.lang_dest_HBox
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
    title: "Wiwordik 0.04 ({wikt_parsed_conn.getDBName()})"
    //    resizable: false
    visible: true
    //    style: StageStyle.TRANSPARENT
    scene: bind scene
    width: 640
    height: 480
    // content: "Wiktionary browser"
}



