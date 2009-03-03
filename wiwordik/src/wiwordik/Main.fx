/* Main.fx - visualization data to MRD Wiktionary database (wikt_parsed).
 *
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik;

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

import javafx.scene.control.TextBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;
import javafx.ext.swing.SwingComboBox;
import javafx.ext.swing.SwingComboBoxItem;

// Application Bounds
var sceneWidth: Number = bind scene.width;
var sceneHeight: Number = bind scene.height;

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

var adjacent_words : String[] = ["Red", "Yellow", "Green"];

SwingComboBox {
    items: [
        SwingComboBoxItem {
            text: "File"
            selected: true
        }
    ]
}

var textDeltaBounds: Rectangle = Rectangle {
    x: 2
    y: 2
    width: 14
    height: 5
};
var wordText: TextBox = TextBox {
    blocksMouse: true
    columns: 7
    selectOnFocus: false
    text: "95054"
    clip: Rectangle {
        x: bind textDeltaBounds.x
        y: bind textDeltaBounds.y
        width: bind (wordText.width - textDeltaBounds.width)
        height: bind (wordText.height - textDeltaBounds.height)
    }
    action: function() {
//        searchCoffeeShops(wordText.text.trim());

    }
    /*onKeyPressed: function(e:KeyEvent) {
        if(e.code == KeyCode.VK_UP) {
            bgImage.requestFocus();
        } else if(e.code == KeyCode.VK_RIGHT) {
            if("{__PROFILE__}" == "mobile") {
                searchButton.requestFocus();
            }
        }
    }*/
}



var scene: Scene = Scene {
    content: Group {
        content: bind [
            OutputPanel,
            wordText
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



