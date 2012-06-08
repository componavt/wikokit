/* WCRelation.java - A part of word card corresponds to a semantic relations part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.word_card;

import java.util.List;

import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TRelation;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.util.GUI;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class WCRelation {
    
    /** Creates a part of card (parts of wiki pages) with list of quotes
     * related to one meaning (sense).
     *
     * @return null if there are no any quotes for this meaning.
    **/
    public LinearLayout create ( Activity _context,
                            SQLiteDatabase db,
                            TMeaning _tmeaning
                          )
    {
        // GUI
        LayoutParams mpwc_top_margin = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
        mpwc_top_margin.setMargins(0, GUI.ConvertPixelsToDP(_context, KWConstants.relation_gap), 0, 0); // .setMargins(left, top, right, bottom);
     
        LinearLayout result_layout = new LinearLayout(_context);
        result_layout.setOrientation(LinearLayout.VERTICAL); 
        result_layout.setLayoutParams(mpwc_top_margin);
    
        // all relations for this meaning
        TRelation[] rels = TRelation.get(db, _tmeaning);
        
        //for each relation type
        Relation[] relation_types = Relation.getAllRelationsOrderedArray();
        boolean b_at_least_one_relation_exists = false;
        for(Relation r_type : relation_types) {

            WCRelationOneType _r1 = new WCRelationOneType(); 
            //LinearLayout ll_r1 = _r1.create(_context, db, rels, r_type);
            TextView rel_text = _r1.create(_context, db, rels, r_type);
            if(null != rel_text) {
                b_at_least_one_relation_exists = true;
                result_layout.addView(rel_text);
            }
        }
        
        if(b_at_least_one_relation_exists)
            return result_layout;
        return null;
    }
}
