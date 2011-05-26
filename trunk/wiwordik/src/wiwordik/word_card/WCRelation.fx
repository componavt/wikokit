/* WCRelation.fx - A part of word card corresponds to a semantic relations part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikt.constant.Relation;

import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.lang.*;


/** Semantic relations consists of word's synonyms, antonyms, etc..
 *
 * @see wikt.api.WTRelation and wikt.sql.TRelation
 */
public class WCRelation {

    /** Syn, Ant, etc. */
    var relation_type : String;

    /** List of synonyms, or antonyms, etc. */
    var relation_words : String;

    public var group: HBox = HBox {
        spacing: 0
        content: [
            Text {
                content: bind relation_type
                //wrappingWidth: 380
                //font: Font {  size: 14  }
                fill: Color.GRAY
                underline: true
            }
            Text {
                content: ": "
                fill: Color.GRAY
            }

            Text {
                content: bind relation_words
                //wrappingWidth: 380
                //font: Font {  size: 14  }
                //fill: Color.GRAY
                wrappingWidth: 300
            }
        ]
    };


    /** Creates a part of card (parts of wiki pages) with semantic relations.
     *
     * _max_meaning_number total number of different meanings for the current
     *                      POS-language sub-entry
     *
     * @return true if there are any synonyms, antonyms, etc. in this relation block.
    **/
    public function create (conn : Connect,
                            _relation_type : Relation,
                            _tmeaning : TMeaning
                           ) : Boolean {

        def rels : TRelation[] = TRelation.get(conn, _tmeaning);

        var list : String;
        for(r in rels) {
            if(r.getRelationType() == _relation_type)
                list = "{list}{r.getWikiText().getText()}, ";
        }

        def len : Integer = list.length();    // at least one relation exists.
        if(len > 0) {
            relation_type  = _relation_type.toString();
            relation_words = list.substring(0, len - 2);
            return true;
        }

        return false;
    }
    
}
