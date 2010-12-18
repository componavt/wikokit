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
//import wikt.constant.POS;
//import wikt.constant.Relation;

import javafx.scene.text.Text;
import javafx.scene.text.Font;
//import javafx.scene.Group;

import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;

import javafx.ext.swing.SwingScrollPane;
//import javafx.scene.control.ScrollView;

//import javafx.scene.control.ListView;
//import javafx.scene.layout.LayoutInfo;

import javafx.ext.swing.SwingList;
import javafx.ext.swing.SwingListItem;

import java.lang.*;

/** Translations consists of one meaning block translated to all languages.
 *
 * @see wikt.sql.TTranslation and wikt.word.WTranslation
 */

public class WCTranslation {

    def DEBUG : Boolean = true;

    /** Translation section (box) title, i.e. additional comment,
     * e.g. "fruit" or "apple tree" for "apple".
     * A summary of the translated meaning.
     */
    var meaning_summary : String;

    var swing_list_group : SwingListItem[];
    var scroll_height : Integer;
    var scroll_width : Integer;

    def font_size : Integer = 14;

    public var group: VBox = VBox {
        spacing: 2
        content: [
            Text {
                content: bind meaning_summary
                //wrappingWidth: 380
                //font: Font {  size: 14  }
                //fill: Color.GRAY
            }
            
            SwingScrollPane{
                height: bind scroll_height  // 165
                width: bind scroll_width  // 165
                scrollable: true
                font: Font {  size: font_size }
                
                view:
                    SwingList{items: bind swing_list_group
                    //SwingList{items: [
                    //    SwingListItem{text:"De: a;lksdjf"}, ]
                    }
            }

            /*ScrollView {
                height: bind scroll_height  // 165
                width: bind scroll_width  // 165
                //scrollable: true
                //font: Font {  size: font_size }
                fitToWidth: true

                //view:
                node:
                    SwingList{items: bind swing_list_group
                    //SwingList{items: [
                    //    SwingListItem{text:"De: a;lksdjf"}, ]
                    }
            }*/
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
        
        def trans_entries : TTranslationEntry[] = TTranslationEntry.getByTranslation (conn, _ttranslation, );

        // System.out.println("WCTranslation.create() _lang={_lang.getLanguage().toString()}; sizeof trans_entries={sizeof trans_entries}");

        for(e in trans_entries) {
            //if(r.getRelationType() == _relation_type)

            def l : LanguageType = e.getLang().getLanguage();
            def language_name_value = "{l.getName()} ({l.getCode()})";

            def s : String = "{language_name_value}: {e.getWikiText().getText()}";
            insert SwingListItem{text: s } into swing_list_group;
        }

        def len : Integer = sizeof trans_entries;
        if(1 == len) {
            scroll_height = (font_size +7);
        } else if(len < 5) {
            scroll_height = (font_size +6)*len;
        } else if(len < 7) {
            scroll_height = (font_size +5)*len;
        } else {
            //scroll_height = Math.min(150, Math.max(font_size, (sizeof swing_list_group) * (font_size +5)))
            scroll_height = (font_size +5)*6;
        }
        scroll_width = 300;
        
        return len > 0;
    }
}
