/* WCTranslation.fx - A part of word card corresponds to a translation part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

import java.lang.*;

import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.LayoutInfo;

/** Translations consists of one meaning block translated to all languages.
 *
 * @see wikt.sql.TTranslation and wikt.word.WTranslation
 */
public class WCTranslation {

    /** Translation section (box) title, i.e. additional comment,
     * e.g. "fruit" or "apple tree" for "apple".
     * A summary of the translated meaning.
     */
    var meaning_summary : String;

    /** Duplication of listview_trans.items[] ??? */
    var trans_entry_items : TranslationEntryItem[]; //TTranslationEntry[];
    var trans_entry_items_size : Integer;

    //var list_group : ListCell [];
//    var scroll_height : Integer;
//    var scroll_width : Integer;
    //def font_size : Integer = 14;

/*function getTransEntryCellFactory(format:String):ListCell {
//function getTransEntryCellFactory() {

        // System.out.println("WCTranslation.create() _lang={_lang.getLanguage().toString()}; sizeof trans_entries={sizeof trans_entries}");
        for(e in trans_entries) {
            //if(r.getRelationType() == _relation_type)

            def l : LanguageType = e.getLang().getLanguage();
            def language_name_value = "{l.getName()} ({l.getCode()})";

            def s : String = "{language_name_value}: {e.getWikiText().getText()}";
            //insert SwingListItem{text: s } into swing_list_group;
            //insert ListCell{item: s } into list_group ;
        }
    //ListCell { }
}*/

    /** @param n = trans_entry_items_size */
    function getTranslationBoxHeight(n:Integer) : Float {
        if(0 == n)
            return 0;

        // assert: listview_trans.items.size == n > 0
        if(listview_trans.items.size() < 1)
            return 0; // this line is not reachable

        def tei : TranslationEntryItem = listview_trans.items[0] as TranslationEntryItem;
        def h : Float = tei.getHeight();
        //System.out.println("WCTranslation:create: h = {h}");
        
        if(n >= 0 and n <=9)
            return n*h;

        return 7*h;     // too much entries
    }

    def listview_trans: ListView = ListView {
        layoutInfo: LayoutInfo { height: bind getTranslationBoxHeight(trans_entry_items_size) }
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
            listview_trans
        ]
    }; // VBox

           
           //     font: Font {  size: font_size }
 /*ListView {
    //items: [1.23, -3.33, -4.83, 5.32, -6.32]
    items: bind trans_entries
    //cellFactory: function() {getTransEntryCellFactory()}
    //cellFactory: getTransEntryCellFactory("sss")
    cellFactory: function() {
        //var cell: ListCell = ListCell {

        //var cell:ListCell; //[]; // = ListCell {}

        // System.out.println("WCTranslation.create() _lang={_lang.getLanguage().toString()}; sizeof trans_entries={sizeof trans_entries}");
        //for(e in trans_entries) {
            def e: TTranslationEntry = trans_entries[trans_entries_counter ++];
            //if(r.getRelationType() == _relation_type)

            def l : LanguageType = e.getLang().getLanguage();
            def language_name_value = "{l.getName()} ({l.getCode()})";

            def s : String = "{language_name_value}: {e.getWikiText().getText()}";
            //insert SwingListItem{text: s } into swing_list_group;
            //insert ListCell{item: s } into list_group ;

            //insert ListCell {node: Label { text: s }} into cell;
        //}
        return ListCell {node: Label { text: s }};
*/
/*        def cell:ListCell = ListCell {
            //styleClass: bind if ((cell.item as Number) < 0) then "negative" else "list-cell"
            //node: Label { text: bind if (cell.empty) then "" else "{cell.item}" }
            //node: Label { text: bind if (cell.empty) then "" else "{cell.item}" }
            
            node: Label { text: "temp empty" }*/
    
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

        trans_entry_items = [];
        for(e in trans_entries) {
            //if(r.getRelationType() == _relation_type)

            def l : LanguageType = e.getLang().getLanguage();
            def lang_name_value: String = l.getName();
            def lang_code_value: String = l.getCode();
            def translation_text: String = e.getWikiText().getText();

            //def s : String = "{language_name_value}: {e.getWikiText().getText()}";
            //insert ListCell{item: s } into list_group ;
            
            insert TranslationEntryItem { lang_name: lang_name_value
                                          lang_code: lang_code_value
                                          text: translation_text     }
                   into trans_entry_items;
        }
        insert trans_entry_items into listview_trans.items;
        trans_entry_items_size = trans_entry_items.size();
        //System.out.println("WCTranslation:create: sizeof listview_trans.items = {sizeof listview_trans.items}");
        
        def len : Integer = sizeof trans_entries;
        return len > 0;
    }
}
