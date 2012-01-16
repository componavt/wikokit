/* WCTranslationEntryItem.java - A part of the word card corresponds 
 * to a translation of one meaning to one language in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;

import javafx.scene.layout.HBox;
//import javafx.scene.CustomNode;
import javafx.scene.paint.Color;
import javafx.scene.Node;

import javafx.scene.text.Text;
import javafx.scene.text.Font;


/** Class holds data of translation box entry,
 * each item (of list) has a language name, code, and translation itself.
 */
public class TranslationEntryItem { // extends CustomNode {

    /* language name */
    public String lang_name;

    /* language code */
    public String lang_code;

    /* translation itself */
    public String text;

    /* height of the entry */
    //public var temp_height: Integer = this.HBox.layoutBounds.;

    boolean selected = false;
    public Color bgColor = Color.WHITE;

    Color fgColor  = Color.BLACK;



    /** Gets height of one entry in the list view. */ 
   final static int font_size = 14;

   public static float getHeight() {
        return 2.f * (font_size - 1);
        //return hbox.height
    }

    
    public HBox hbox = new HBox();

 
    /** Gets the "Language name (language code): translation".
    **/
    public String getLangCodeTranslation () {
        StringBuilder s = new StringBuilder();
        s.append(lang_name);
        s.append(" (");
        s.append(lang_code);
        s.append("): ");
        s.append(text); // translation_text
        
        return s.toString();
    }
    
    /** Creates text nodes with the text: "Language name (language code): translation"
    **/
    public void create (String _lang_name, String _lang_code, String _translation_text) {
        
        Font _font = Font.font("dialog", font_size);
        
        lang_name = _lang_name;
        lang_code = _lang_code;
        text = _translation_text;
        
        // "{lang_name} ({lang_code}): "
        StringBuilder s = new StringBuilder();
        s.append(lang_name);
        s.append(" (");
        s.append(lang_code);
        s.append("): ");
        Text lang_name_code = new Text();
        lang_name_code.setText(s.toString());
        lang_name_code.setFill(fgColor);
        lang_name_code.setFont(_font);
        
        Text translation_text = new Text();
        translation_text.setText(text);
        translation_text.setFill(fgColor);
        translation_text.setFont(_font);
        
        //System.out.println("TranslationEntryItem.create(): lang_name="+lang_name+"; lang_code=" + lang_code + 
        //        "; text="+text);
        
        hbox.setSpacing(5);
        hbox.getChildren().addAll(lang_name_code);
        hbox.getChildren().addAll(translation_text);
    }
    
    
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
