/* WCLanguage.fx - A part of word card corresponds to a language part
 * of a page (entry) in Wiktionary. It contains POS's sub-parts.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.text.FontWeight;
import wiwordik.WConstants;

/** Language part of a Wiktionary page. It contains POS's sub-parts.
 *
 * @see wikt.word.WLanguage
 */

public class WCLanguage {

    /** Language of the word. */
    public LanguageType lang;

    /** Parts of speech of current word. */
    WCPOS[] pos;
    
    private final static WCPOS[] NULL_WCPOS_ARRAY = new WCPOS[0];

    // todo: ToggleButton, ToggleGroup
    // experiments: toggle language buttons "ru", "en", "uk"

    Text text_lang;
    VBox pos_group = new VBox();
    /* var pos_group : VBox = VBox {
            spacing: 20

            //opacity:  0.5
            //width: bind card_scene.width - 30
            width: WConstants.wrapping_width - 30
        };*/

    VBox group = new VBox();
    /*public var group: VBox = VBox {
        spacing: 5
        content: [
            Text {
                content: bind language_name_value // "I think it's a place for a language block. Yes?"
                font: Font {  embolden: true size: 18 name:"Times New Roman" }
                underline: true
                //overline: true
                //textOrigin: TextOrigin.TOP
                //fill: Color.BLANCHEDALMOND
            }

            pos_group]
    };*/

    
    /** Creates text with a name of language, e.g. "Russian", or "English". */
    public void createTextLang(LanguageType _lang) {
        //     "lang (code)"
        String language_name_value = _lang.getName(WConstants.native_lang) + " (" + _lang.getCode() + ")";
        // System.out.println("WCLanguage.create(). language_name_value = {language_name_value}");
        text_lang = new Text(language_name_value);
        text_lang.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        text_lang.setUnderline(true);
    }
    
    /** Creates a language part of card (parts of wiki pages),
     * consists of several POS blocks.
    **/
    public void create(Connect  conn,
                       Scene    card_scene,
                            //_tpage : TPage,
                       LanguageType _lang,
                       TLangPOS[] lang_pos_array
                       ) 
    {
        createTextLang(_lang);
        
        List<WCPOS> list_pos = new ArrayList<>();
        for (TLangPOS _lang_pos : lang_pos_array) {

            if(_lang_pos.getLang().getLanguage() == _lang) {

                WCPOS _pos = new WCPOS();
                _pos.create(conn, _lang_pos);
                list_pos.add(_pos);             // logic: insert _pos into pos
                
                pos_group.getChildren().addAll(_pos.group);  // visual
            }
        }
        pos = ((WCPOS[])list_pos.toArray(NULL_WCPOS_ARRAY)); // logic
        
        pos_group.setSpacing(20); // todo?: width: WConstants.wrapping_width - 30
        group.setSpacing(5);
        group.getChildren().addAll(text_lang, pos_group);
    }
}
