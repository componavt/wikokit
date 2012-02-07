/* WCRelation.java - A part of word card corresponds to a semantic relations part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;

import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TRelation;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.constant.Relation;

import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.lang.*;
import wiwordik.WConstants;


/** Semantic relations consists of word's synonyms, antonyms, etc..
 *
 * @see wikt.api.WTRelation and wikt.sql.TRelation
 */
public class WCRelation {

    /** Syn, Ant, etc. */
    String relation_type;

    /** List of synonyms, or antonyms, etc. */
    String relation_words;
    
    HBox group = new HBox(); 

    //   "synonyms: synonym_word1, word2, word3"
    Text text_relation_type, text_column, text_relation_words;
    
/* todo    
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
*/
    
    /** Creates text field with a list of synonyms (another text field with antonyms, etc.).
     */
    public void createRelationText(String _relation_type, String _relation_words) {
        
        text_relation_type = new Text(_relation_type);
        text_relation_type.setUnderline(true);
        text_relation_type.setFill(Color.GREY);
        
        text_column = new Text(": ");
        text_column.setFill(Color.GREY);
        
        text_relation_words = new Text(relation_words);
        text_relation_words.setWrappingWidth(WConstants.wordcard_width - 80);
    }

    /** Creates a part of card (parts of wiki pages) with semantic relations.
     *
     * _max_meaning_number total number of different meanings for the current
     *                      POS-language sub-entry
     *
     * @return true if there are any synonyms, antonyms, etc. in this relation block.
    **/
    public boolean create ( Connect conn,
                            Relation _relation_type,
                            TMeaning _tmeaning
                           ) {

        TRelation[] rels = TRelation.get(conn, _tmeaning);

        StringBuilder list = new StringBuilder();
        for(TRelation r : rels) {
            if(r.getRelationType() == _relation_type) {
                list.append(r.getWikiText().getText());
                list.append(", ");
            }
        }

        int len = list.length();    // at least one relation exists.
        if(len > 0) {
            relation_type  = _relation_type.toString();
            relation_words = list.substring(0, len - 2);
            
            createRelationText(relation_type, relation_words);
            
            // HBox [text_relation_type, text_column, text_relation_words]
            group.setSpacing(0);
            group.getChildren().addAll(text_relation_type);
            group.getChildren().addAll(text_column);
            group.getChildren().addAll(text_relation_words);
            
            return true;
        }
        return false;
    }
}
