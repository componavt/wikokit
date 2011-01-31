/* WCTranslation.fx - A part of word card corresponds to a translation part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik.word_card;

import javafx.scene.layout.HBox;
import javafx.scene.CustomNode;
import javafx.scene.paint.Color;
import javafx.scene.Node;

import javafx.scene.text.Text;
import javafx.scene.text.Font;


/** Class holds data of translation box entry,
 * each item (of list) has a language name, code, and translation itself.
 */
public class TranslationEntryItem extends CustomNode {

    /* language name */
    public var lang_name: String;

    /* language code */
    public var lang_code: String;

    /* translation itself */
    public var text: String;

    /* height of the entry */
    //public var temp_height: Integer = this.HBox.layoutBounds.;


    def font_size : Integer = 14;
    public var font: Font = Font { //name: "dialog"
        size: font_size
    }

    public var selected :Boolean = false;
    public var bgColor : Color = Color.WHITE;

//    public var height = bind bgRect.height;
    var fgColor : Color = Color.BLACK;
    //var prevBgColor : Color;

    var lang_name_code = Text {
        font: bind font;
        content: bind "{lang_name} ({lang_code}): ";
        fill: bind fgColor;
    };

    var translation_text = Text {
        font: bind font;
        content: bind text
        fill: bind fgColor;
    };

    //var bgRect = Rectangle {
    //    x: 0
    //    y: 0
    //    width: bind listView.width
    //    height: bind this.itemText.boundsInLocal.height
    //    fill: bind bgColor;
    //}

    /** Gets height of one entry in the list view. */
    public function getHeight() : Float {
        return 2* ((font_size as Float) - 1)
        //return hbox.height
    }

    def hbox: HBox = HBox {
            //  padding: Insets { left: 5 top: 1 bottom: 10}
            spacing: 5
            content: [lang_name_code, translation_text
        ]}

    override function create():Node { hbox }
/*
    override var onMousePressed = function(e : MouseEvent) {
        if ( selected == false) {
            selected = true;
            prevBgColor = bgColor;
            fgColor = Color.WHITE;
            bgColor = SELECTED_COLOR; // Blue-ish color
        }
        else {
            selected = false;
            fgColor = Color.BLACK;
            bgColor = prevBgColor;
        }
    }*/
}
