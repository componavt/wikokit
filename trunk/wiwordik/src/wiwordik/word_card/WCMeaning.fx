/* WCMeaning.fx - A part of word card corresponds to a Definition (meaning) part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;


import wikt.sql.*;
import wikipedia.sql.Connect;
import wikt.constant.Relation;
import wiwordik.WConstants;

import javafx.scene.text.Text;
import javafx.scene.text.Font;

import javafx.scene.layout.VBox;

import java.lang.*;


/** Meaning consists of word's definitions, semantic relations and translations.
 *
 * @see wikt.api.WTMeaning and 
 */
public class WCMeaning {
    
    var definition_value : String;

    //var temp_height : Integer = 50;

    var quote : WCQuote[];

    var quote_group : VBox = VBox { spacing: 10 };

    /** (2) Semantic relations: synonymy, antonymy, etc.
     * The map from semantic relation (e.g. synonymy) to array of WRelation
     * (one WRelation contains a list of synonyms for one meaning).
     */
    var relation : WCRelation[];   //private Map<Relation, WRelation[]> relation;

    var relation_group : VBox = VBox { spacing: 10 };

    // (3) Translation
    var translation : WCTranslation[];  //private WTranslation[] translation;
    var translation_group : VBox = VBox {
        spacing: 1
        //height: bind temp_height
    };

    /** Meanings, Relations, Translations group */
    public var group_mrt: VBox = VBox {
        spacing: 8

        content: [
            Text {
                content: bind definition_value
                wrappingWidth: WConstants.wrapping_width - 35
                //wrappingWidth: bind group_mrt.parent.boundsInLocal.width - 30
                //wrappingWidth: bind group_mrt.parent.boundsInParent.width - 30
                //wrappingWidth: 380`
                font: Font {  size: 14  }
                //fill: Color.PLUM
            }
            quote_group,
            relation_group,
            translation_group]
    };


    /** Creates a Meaning part of card (parts of wiki pages),
     * builds visual block with this language.
     *
     * @param _max_meaning_number total number of different meanings for the current
     *                      POS-language sub-entry
    **/
    public function create(conn : Connect,
                            _tmeaning : TMeaning,
                            _max_meaning_number : Integer,
                            _lang : TLang,
                            _ttranslations : TTranslation[]
                           ) {

        /** Meaning (sense) number. */
        def meaning_n : Integer = _tmeaning.getMeaningNumber();

        var s_debug : String;
        if(WConstants.DEBUGUI)
            s_debug = "; meaning.id={_tmeaning.getID()}; meaning _n/max={meaning_n+1}/{_max_meaning_number}";

        // 1.a Definition
        // numbering logic: if only one definition then without number 1.
        var s_number;
        if(_max_meaning_number > 1)
            s_number = "{meaning_n + 1}. ";

        def twiki_text : TWikiText = _tmeaning.getWikiText();
        if(null != twiki_text)
            definition_value = "{s_number}{twiki_text.getText()}{s_debug}";

        // 1.b Quote
        def _quote : WCQuote = new WCQuote();
        if(_quote.create(conn, _tmeaning)) {
            // + line if there are any synonyms, antonyms, etc.
            insert _quote into quote;                       // logic
            insert _quote.group into quote_group.content;   // visual
        }

        // 2. Semantic relations.
        def relation_types : Relation[] = Relation.getAllRelationsOrderedArray();
        for(r_type in relation_types) {

            def _relation : WCRelation = new WCRelation();
            if(_relation.create(conn, r_type, _tmeaning)) {
                // + line if there are any synonyms, antonyms, etc.
                insert _relation into relation;                       // logic
                insert _relation.group into relation_group.content;   // visual
            }
        }


        // 3. Translations.
        if(sizeof _ttranslations > meaning_n) {

            // only one translation block, for the current meaning
            def tt : TTranslation = _ttranslations[meaning_n];

            def _translation : WCTranslation = new WCTranslation();
            if(_translation.create(conn, tt, _lang)) {
                // + if there are any translation entries in the block
                insert _translation into translation;                       // logic
                insert _translation.group into translation_group.content;   // visual
                //temp_height = 25;
            } else {
                //temp_height = 5;
            }
        }
    }
    
}
