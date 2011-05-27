/* WCLanguage.fx - A part of word card corresponds to a language part
 * of a page (entry) in Wiktionary. It contains POS's sub-parts.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik.word_card;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;

import java.lang.*;
import javafx.scene.Scene;
import wiwordik.WConstants;

/** Language part of a Wiktionary page. It contains POS's sub-parts.
 *
 * @see wikt.word.WLanguage
 */

public class WCLanguage {

    /** Language of the word. */
    public var lang : LanguageType;

    /** Parts of speech of current word. */
    var pos : WCPOS[];

    // todo: ToggleButton, ToggleGroup
    // experiments: toggle language buttons "ru", "en", "uk"

    var language_name_value : String = "lang (code)";

    var pos_group : VBox = VBox {
            spacing: 20

            //opacity:  0.5
            //width: bind card_scene.width - 30
            width: WConstants.wrapping_width - 30
        };

    public var group: VBox = VBox {
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
    };


    /** Creates a language part of card (parts of wiki pages),
     * consists of several POS blocks.
    **/
    public function create(conn : Connect,
                           card_scene: Scene,
                            //_tpage : TPage,
                            _lang : LanguageType,
                            lang_pos_array : TLangPOS[]
                           ) {

        language_name_value = "{_lang.getName(WConstants.native_lang)} ({_lang.getCode()})";
        // System.out.println("WCLanguage.create(). language_name_value = {language_name_value}");

        for (_lang_pos in lang_pos_array) {

            if(_lang_pos.getLang().getLanguage() == _lang) {
                //

                def _pos : WCPOS = new WCPOS();
                _pos.create(conn, _lang_pos);

                insert _pos into pos;                       // logic
                insert _pos.group into pos_group.content;   // visual
            }
        }
    }
}
