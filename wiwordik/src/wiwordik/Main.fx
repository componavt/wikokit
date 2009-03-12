/* Main.fx - visualization of parsed Wiktionary database (wikt_parsed).
 *
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik;

import java.lang.*;

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

import javafx.scene.control.TextBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;
import javafx.ext.swing.SwingComboBox;
import javafx.ext.swing.SwingComboBoxItem;
import javafx.ext.swing.SwingList;
import javafx.ext.swing.SwingListItem;

import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;
import wikt.sql.TPage;

// ===========
// Wiktionary parsed database
// ===========

def wikt_parsed_conn = new Connect();
wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS);

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

//                               any 10 words, since "" == prefix
var page_array = TPage.getByPrefix(wikt_parsed_conn, "", 10);

var word_List = SwingList {
    //translateX: 113
    width: 222
    // selectedIndex: 1
    items: bind for (p in page_array) SwingListItem{ text: p.getPageTitle() }
}

/*var textDeltaBounds: Rectangle = Rectangle {
    x: 2
    y: 2
    width: 14
    height: 5
};*/

var word_Text: TextBox = TextBox {
    blocksMouse: true
    columns: 7
    selectOnFocus: false
    text: page_array[0].getPageTitle()
    /*clip: Rectangle {
    x: bind textDeltaBounds.x
     y: bind textDeltaBounds.y
     width: bind (word_Text.width - textDeltaBounds.width)
     height: bind (word_Text.height - textDeltaBounds.height)
     }*/
    action: function() {
//        searchCoffeeShops(word_Text.text.trim());
    }
    
    onKeyTyped: function(e:KeyEvent) {
        // System.out.println("e.code = {e.code}, word_Text.text = {word_Text.text}");
        page_array = TPage.getByPrefix(wikt_parsed_conn, word_Text.text.trim(), 10);
    }
}

var OutputPanel: VBox = VBox {
    //translateX: bind (sceneWidth - zipSearchPanel.boundsInLocal.width)/2.0
    //translateY: bind (sceneHeight - 52)
    content: [word_Text, word_List ]    // word_ComboBox, 
    spacing: 10
};

var scene: Scene = Scene {
    content: Group {
        content: bind [
            OutputPanel // ,
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
    title: "Wiwordik 0.01"
    //    resizable: false
    visible: true
    //    style: StageStyle.TRANSPARENT
    scene: bind scene
    width: 240
    height: 320
    // content: "Wiktionary browser"

}



