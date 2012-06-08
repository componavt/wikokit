/* WCMeaning.java - A part of word card corresponds to a Definition (meaning) part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.word_card;

import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TTranslation;
import wikokit.base.wikt.sql.TWikiText;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.util.GUI;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/** Meaning consists of word's definitions, semantic relations and translations.
*
* @see wikt.api.WTMeaning and 
*/
public class WCMeaning {
    
    /** Quotes of this meaning of current word. */ 
    // WCQuote[] quote;
    
    
    /** Gets text with a definition, meaning, sense description. 
    *
    * @param _max_meaning_number total number of different meanings for the current
    *                      POS-language sub-entry
    */
   public String createDefinitionText(TMeaning _tmeaning, int _max_meaning_number) {
       String s_debug = "";
       String result = "";
       
       // Meaning (sense) number.
       int meaning_n = _tmeaning.getMeaningNumber();
       
       if(KWConstants.DEBUGUI)
           s_debug = "; meaning.id=" + _tmeaning.getID() + 
                   "; meaning _n/max=" + (meaning_n+1) + "/" + _max_meaning_number;
       
       // 1.a Definition
       // numbering logic: if only one definition then without number 1.
       String s_number = "";
       if(_max_meaning_number > 1)
           s_number = String.format("%d. ", meaning_n + 1);

       TWikiText twiki_text = _tmeaning.getWikiText();
       if(null != twiki_text)
           result = s_number + twiki_text.getText() + s_debug;
       
       return result;
   }
    
    
    /** Creates a Meaning part of card (parts of wiki pages),
     * builds visual block with this language.
     *
     * @param _max_meaning_number total number of different meanings for the current
     *                      POS-language sub-entry
     *                      
     * @see Dynamic Layout Basics http://android.attemptone.com/layouts/dynamic-layout-basics/
    **/
    public LinearLayout create( SQLiteDatabase db,
                        Activity _context,
                        TMeaning _tmeaning,
                        Integer _max_meaning_number,
                        TLang _lang,
                        TTranslation[] _ttranslations
                      )
    {
        LayoutParams mpwc = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
        
        // meaning_gap between meanings
        LayoutParams mpwc_top_margin = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
        mpwc_top_margin.setMargins(0, GUI.ConvertPixelsToDP(_context, KWConstants.meaning_gap), 0, 0); // .setMargins(left, top, right, bottom);

        LinearLayout result_layout = new LinearLayout(_context);
        
        result_layout.setOrientation(LinearLayout.VERTICAL);
        result_layout.setGravity(Gravity.TOP);
        result_layout.setLayoutParams(mpwc);
        
        
        // 1. definition
        TextView text_definition = new TextView(_context); 
        text_definition.setLayoutParams(mpwc);
        // text_definition.setTextIsSelectable(true); // "Since: API Level 11". API 11 is 3.0 (Honeycomb)
        
        text_definition.setTextSize(TypedValue.COMPLEX_UNIT_SP, KWConstants.text_size_normal);
        text_definition.setTextColor(KWConstants.definition_color);

        text_definition.setLayoutParams(mpwc_top_margin);
        text_definition.setGravity(Gravity.TOP);
        
        String _def_text = createDefinitionText(_tmeaning, _max_meaning_number);
        text_definition.setText(_def_text);
        result_layout.addView(text_definition);
        
        
        
        
        // 1.b Quote
        WCQuote _quote = new WCQuote();
        LinearLayout ll_quote = _quote.create(_context, db, _tmeaning);
        if(null != ll_quote)
            result_layout.addView(ll_quote);
            

        // 2. Semantic relations.
        WCRelation _relation = new WCRelation();        
        LinearLayout ll_relation = _relation.create(_context, db, _tmeaning);
        if(null != ll_relation)
            result_layout.addView(ll_relation);
        

        // Meaning (sense) number.
        Integer meaning_n = _tmeaning.getMeaningNumber();

        // 3. Translations.
/*        translation = null;
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
        */
        
        return result_layout;
    }

}
