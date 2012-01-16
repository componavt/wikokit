/* Main.fx - visualization of parsed Wiktionary database (wikt_parsed).
 *
 * Copyright (c) 2008-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik; 

// import wiwordik.search_window.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikt.sql.TLang;
import wikt.sql.TPOS;
import wikt.sql.TRelationType;
import wiwordik.search_window.*;
import wiwordik.util.TipsTeapot;

// Todo. Errors in parser:
// дуб (илл -> syn)
// тупица, река, самолёт (-) i.e. page.is_in_wiktionary = false
//
// todo: list of languages - combobox with ListView:
// http://jojorabbitjavafxblog.wordpress.com/2011/06/23/javafx-2-0-custom-combobox/

public class Main extends Application { 

    static Connect wikt_parsed_conn  = new Connect();
    
    static void initDatabase() {

        if(WConstants.IS_SQLITE) {
            // SQLite                                   //Connect.testSQLite();
            if(LanguageType.ru == WConstants.native_lang) {
                wikt_parsed_conn.OpenSQLite(Connect.RUWIKT_SQLITE, LanguageType.ru, WConstants.IS_RELEASE);
            } else {
                wikt_parsed_conn.OpenSQLite(Connect.ENWIKT_SQLITE, LanguageType.en, WConstants.IS_RELEASE);
            }
        } else {
            // MySQL
            if(LanguageType.ru == WConstants.native_lang) {
                wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);
            } else {
                wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, LanguageType.en);
            }
        }
        System.out.println("initDatabase: DBName=" + wikt_parsed_conn.getDBName());    

        TLang.createFastMaps(wikt_parsed_conn);   // once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps(wikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
        TRelationType.createFastMaps(wikt_parsed_conn);
    }


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


//def tips = new TipsTeapot();
//tips.generateRandomTip();
static TipsTeapot tip = TipsTeapot.generateRandomTip();

static String word0 = tip.getQuery(); //"*с?рё*";


static LangChoice    lang_choice    = new LangChoice();
static LangChoiceBox lang_choicebox = new LangChoiceBox();
static DebugPanel debug_panel = new DebugPanel();

static WordList word_list = new WordList();

static FilterMeanSemRelTrans filter_mean_sem_transl = new FilterMeanSemRelTrans();
static QueryTextString query_text_string = new QueryTextString();
        
/*
def query_text_string : QueryTextString = QueryTextString {
     word0: word0;
     wikt_parsed_conn: wikt_parsed_conn
}
query_text_string.initialize(word_list, lang_choice);*/


static void initGUI() {

    query_text_string.initialize(word0, wikt_parsed_conn, word_list, lang_choice);

    lang_choice.initialize( word_list, query_text_string, lang_choicebox,
                            tip.getSourceLangCodes(), WConstants.native_lang);
    
    lang_choicebox.initialize(word_list, query_text_string, lang_choice, WConstants.native_lang);

    word_list.initialize(   wikt_parsed_conn,
                            query_text_string, lang_choice, filter_mean_sem_transl,
                            WConstants.native_lang,
                            //word0,
                            WConstants.n_words_list);

    word_list.setSkipRedirects(WConstants.b_skip_redirects);

    filter_mean_sem_transl.initialize(word_list, lang_choice, query_text_string);
    debug_panel.initialize();

    word_list.updateWordList(   WConstants.b_skip_redirects,
                                word0
                            );
    query_text_string.saveWordValue();

    word_list.copyWordsToStringArray( word_list.getPageArray() );
}
    
/*
function updateWordList() {

    word_list.updateWordList( lang_choice.getLangDestSelected(),
                               b_skip_redirects,
                               query_text_string.getWordValue()
                            );
    query_text_string.saveWordValue();
}


var filter_MRT_hbox: HBox = HBox {
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



var outputPanel_VBox1: VBox = VBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [query_text_string.word_Text,
              word_list.word_ListView]
    spacing: 10
};

var result_VBox2: VBox = VBox {
    padding: Insets { left: 0 top: 4 bottom: 1}
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [  // wiki_page_Label,
                lang_choice.lang_source_HBox,
                filter_MRT_hbox,
                lang_choice.lang_dest_hbox,
                debug_panel.debug_HBox
             ] //, wc.card]
    spacing: 10
};


var horizontal_Panel: HBox = HBox {
    padding: Insets { left: 4 top: 4 bottom: 10}
    width: bind scene.width;
    height: bind scene.height;
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [outputPanel_VBox1, result_VBox2]
    spacing: 10
};

var scene: Scene = Scene {
    content: [
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
    
//    fill: Color.TRANSPARENT
}

// Application User Interface
var stage: Stage = Stage {
    title: "Wiwordik {WConstants.wiwordik_version}.{LanguageType.size()} ({wikt_parsed_conn.getDBName()})"
    //    resizable: false
    visible: true
    
    //    style: StageStyle.TRANSPARENT
    scene: bind scene
    width: 640
    height: 480
    // content: "Wiktionary browser"
}*/


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    //HBox horizontal_panel;
    VBox panel_vbox1;
    VBox control_vbox;
            
    @Override
    public void start(Stage primaryStage) {
        initDatabase();
        initGUI();
        
        // title: "Wiwordik {WConstants.wiwordik_version}.{LanguageType.size()} ({wikt_parsed_conn.getDBName()})"
        // e.g.: "Wiwordik 0.08.1094 (enwikt20110618.sqlite)"
        primaryStage.setTitle("Wiwordik " + WConstants.wiwordik_version + 
                "." + LanguageType.size() +
                " (" + wikt_parsed_conn.getDBName() + ")");
        
        Group root = new Group();
        Scene scene = new Scene(root); //, 640, 480);

        panel_vbox1 = new VBox();
        panel_vbox1.setSpacing(5);
        panel_vbox1.setStyle("-fx-background-color: #FFA500");
            //content: [query_text_string.word_Text, word_list.word_ListView]
        
        // VBox with checkbox control elements         // two columns variant: word list - left, all settings - right part of window
        control_vbox = new VBox();  //    top right bottom left
        control_vbox.setPadding(new Insets(0,  0,   2,     3));
        control_vbox.setSpacing(10);
        
        control_vbox.getChildren().addAll(lang_choicebox.choicebox);
        control_vbox.getChildren().addAll(lang_choice.lang_source_hbox);
        control_vbox.getChildren().addAll(filter_mean_sem_transl.filter_MRT_hbox);
        // todo control_vbox.getChildren().addAll(lang_choice.lang_dest_hbox); // todo: add SQL functions: get TPage words with translation to some language
        control_vbox.getChildren().addAll(debug_panel.debug_checkbox);
        
        lang_choicebox.choicebox.getSelectionModel().selectFirst();
        
        /* content: [  // wiki_page_Label,
                    lang_choice.lang_source_hbox, + 
                    filter_MRT_hbox,
                    lang_choice.lang_dest_hbox,
                    debug_panel.debug_HBox
                 ]*/
        /*
        horizontal_panel = new HBox(); // top right bottom left
        horizontal_panel.setPadding(new Insets(4,  4,    10,    4));
        horizontal_panel.setSpacing(10);
        horizontal_panel.setStyle("-fx-background-color: #337799");
            // content: [outputPanel_VBox1, result_VBox2]   now panel_vbox1, panel_vbox2
        horizontal_panel.setFillHeight(true);
        horizontal_panel.getChildren().addAll(panel_vbox1, panel_vbox2);
        root.getChildren().add(horizontal_panel);*/
        
        
        panel_vbox1.getChildren().addAll(query_text_string.word_textfield);
        panel_vbox1.getChildren().addAll(control_vbox);
        panel_vbox1.getChildren().addAll(word_list.word_listview);
        
        root.getChildren().add(panel_vbox1);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
        // Let's the height of list of words will be maximum (possible)
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, 
                    Number old_scene_height, Number new_scene_height)
            {
                double height =
                        query_text_string.word_textfield.getHeight() + 
                        control_vbox.getHeight() + 
                        panel_vbox1.getSpacing() * 2; // 3 elements with 2 intervals
                
                word_list.word_listview.setPrefHeight(
                        (double)new_scene_height - height);
            }
            });
        
        // Let's the width of list of words will be maximum (possible)
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, 
                    Number old_scene_width, Number new_scene_width)
            {
                panel_vbox1.setPrefWidth(       // horizontal_panel.setPrefWidth
                        (double)new_scene_width);
            }
            });
        
    }
}