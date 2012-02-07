/* WCMeaning.java - A part of word card corresponds to a Definition (meaning) part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;


import wikokit.base.wikt.sql.TWikiText;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TTranslation;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.constant.Relation;
import wiwordik.WConstants;

import javafx.scene.text.Text;
import javafx.scene.text.Font;

import javafx.scene.layout.VBox;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.FontWeight;


/** Meaning consists of word's definitions, semantic relations and translations.
 *
 * @see wikt.api.WTMeaning and 
 */
public class WCMeaning {
    
    String  definition_value;
    Text    text_definition;

    WCQuote[] quote;
    VBox quote_group = new VBox();

    /** (2) Semantic relations: synonymy, antonymy, etc.
     * The map from semantic relation (e.g. synonymy) to array of WRelation
     * (one WRelation contains a list of synonyms for one meaning).
     */
    WCRelation[] relation;   //private Map<Relation, WRelation[]> relation;    
    VBox relation_group = new VBox();
    private final static WCRelation[] NULL_WCRELATION_ARRAY = new WCRelation[0];

    // (3) Translation
    WCTranslation translation;
    VBox translation_group = new VBox();
    
    /** Meanings, Relations, Translations group */
    public VBox group_mrt = new VBox();
/* todo    public var group_mrt: VBox = VBox {
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
*/
    
    /** Creates text field with a definition, meaning, sense description. 
     *
     * @param _max_meaning_number total number of different meanings for the current
     *                      POS-language sub-entry
     */
    public void createDefinitionText(TMeaning _tmeaning, Integer _max_meaning_number) {
        String s_debug = "";
        if(WConstants.DEBUGUI)
            s_debug = "; meaning.id={_tmeaning.getID()}; meaning _n/max={meaning_n+1}/{_max_meaning_number}";

        // Meaning (sense) number.
        Integer meaning_n = _tmeaning.getMeaningNumber();
        
        // 1.a Definition
        // numbering logic: if only one definition then without number 1.
        String s_number = "";
        if(_max_meaning_number > 1)
            s_number = String.format("%d. ", meaning_n + 1);

        TWikiText twiki_text = _tmeaning.getWikiText();
        if(null != twiki_text)
            definition_value = s_number + twiki_text.getText() + s_debug;
        
        text_definition = new Text(definition_value);
        text_definition.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 14));
        text_definition.setWrappingWidth(WConstants.wordcard_width - 35);
    }
    
    /** (1) Fills VBox relation_group and (2) gets array WCRelation[] relation.
     */
    public WCRelation[] getRelationsfillsVBox ( Connect conn, TMeaning _tmeaning)
    {
        List<WCRelation> list_rel = new ArrayList();
        
        Relation[] relation_types = Relation.getAllRelationsOrderedArray();
        for(Relation r_type : relation_types) {

            WCRelation _relation = new WCRelation();
            if(_relation.create(conn, r_type, _tmeaning)) {
                list_rel.add(_relation);                                // logic
                relation_group.getChildren().addAll(_relation.group);   // visual
            }
        }
        return ((WCRelation[])list_rel.toArray(NULL_WCRELATION_ARRAY)); // logic
    }

    /** Creates a Meaning part of card (parts of wiki pages),
     * builds visual block with this language.
     *
     * @param _max_meaning_number total number of different meanings for the current
     *                      POS-language sub-entry
    **/
    public void create( Connect conn,
                        TMeaning _tmeaning,
                        Integer _max_meaning_number,
                        TLang _lang,
                        TTranslation[] _ttranslations
                      )
    {
        createDefinitionText(_tmeaning, _max_meaning_number);

        // 1.b Quote
        WCQuote _quote = new WCQuote();
        boolean bquote = _quote.create(conn, _tmeaning);
        if(bquote) {
            // logic: insert _quote into quote
            quote = new WCQuote[1];
            quote [0] = _quote;
            
            // visual
            quote_group.getChildren().addAll(_quote.group);
        }

        // 2. Semantic relations.
        relation = getRelationsfillsVBox (conn, _tmeaning);
        

        // Meaning (sense) number.
        Integer meaning_n = _tmeaning.getMeaningNumber();

        // 3. Translations.
        translation = null;
        if(_ttranslations.length > meaning_n) {
            TTranslation tt = _ttranslations[meaning_n];// only one translation block, for the current meaning

            WCTranslation _translation = new WCTranslation();
            if(_translation.create(conn, tt, _lang)) {   // if there are any translation entries in the block
                translation = _translation;                                 // logic
                translation_group.getChildren().addAll(_translation.group);  // visual
            }
        }
        
        // Visualize results
        quote_group.setSpacing(10);
        relation_group.setSpacing(2);
        translation_group.setSpacing(1);
        
        group_mrt.getChildren().addAll(text_definition);
        
        if(bquote)
            group_mrt.getChildren().addAll(quote_group);
        
        if(relation.length > 0)         // if there are any synonyms, antonyms, etc.
            group_mrt.getChildren().addAll(relation_group);
        
        if(null != translation)
            group_mrt.getChildren().addAll(translation_group);
        
        group_mrt.setSpacing(8);
    }
}
