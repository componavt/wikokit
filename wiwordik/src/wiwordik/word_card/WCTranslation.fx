/* WCTranslation.fx - A part of word card corresponds to a translation part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik.word_card;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;
import wikt.constant.POS;
import wikt.constant.Relation;

import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.Group;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.ext.swing.SwingScrollPane;
import javafx.ext.swing.SwingList;
import javafx.ext.swing.SwingListItem;

import java.lang.*;

/** Translations consists of one meaning block translated to all languages.
 *
 * @see wikt.sql.TTranslation and wikt.word.WTranslation
 */

public class WCTranslation {

    def DEBUG : Boolean = false;

    /** Translation section (box) title, i.e. additional comment,
     * e.g. "fruit" or "apple tree" for "apple".
     * A summary of the translated meaning.
     */
    var meaning_summary : String;

    var swing_list_group : SwingListItem[];

    //A list with three items
    var translations_scrollpane = SwingScrollPane{ 
        //height: 165
        scrollable: true
        view:
            SwingList{items: bind swing_list_group
            //[
                //SwingListItem{text:"De: a;lksdjf"},
                //SwingListItem{text:"It: ;alskdf"},
                //SwingListItem{text:"Pl: kdsla;kj"},
            //]
            }
    };

    public var group: VBox = VBox {
        spacing: 2
        content: [
            Text {
                content: bind meaning_summary
                //wrappingWidth: 380
                //font: Font {  size: 14  }
                //fill: Color.GRAY
            }
            translations_scrollpane
        ]
    };

    /** Creates a translation part of word card, it corresponds to one meaning.
     *
     * @return true if there are any translations in this translation block.
    **/
    public function create (conn : Connect,
                            _ttranslation : TTranslation,
                            _lang : TLang
                           ) : Boolean {
                           
        meaning_summary = _ttranslation.getMeaningSummary();
        
        def trans_entries : TTranslationEntry[] = TTranslationEntry.getByLanguageAndTranslation (conn,
                                        _ttranslation, _lang);

        for(e in trans_entries) {
            //if(r.getRelationType() == _relation_type)

            def l : LanguageType = e.getLang().getLanguage();
            def language_name_value = "{l.getName()} ({l.getCode()})";

            def list : String = "{language_name_value}{e.getWikiText().getText()}";
            insert SwingListItem{text: list } into swing_list_group;
        }


        /*def len : Integer = list.length();    // at least one relation exists.
        if(len > 0) {
            relation_type  = _relation_type.toString();
            relation_words = list.substring(0, len - 2);
            return true;
        }*/

        return true;
    }
}
