/* WCQuote.java - A part of word card corresponds to quotations part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2011-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.word_card;

import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.quote.TQuote;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.util.GUI;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/** One WCQuote contains all quotes (phrase/sentences) that illustrates one meaning
 *  of Wiktionary word.
 *
 * @see wikt.word.WQuote
 */
public class WCQuote {

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
        LayoutParams mpwc = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
        
        // quote_gap between quotes
        LayoutParams mpwc_top_margin = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
        mpwc_top_margin.setMargins(0, GUI.ConvertPixelsToDP(_context, KWConstants.quote_gap), 0, 0); // .setMargins(left, top, right, bottom);
        
        LinearLayout result_layout = new LinearLayout(_context);
        result_layout.setOrientation(LinearLayout.VERTICAL); 
        result_layout.setLayoutParams(mpwc);
        
        // logic  
        TQuote[] quotes = TQuote.get(db, _tmeaning);
        if (quotes.length == 0)
            return null;

        
        for(TQuote q : quotes) {

            WCQuoteOneSentence _1quote = new WCQuoteOneSentence();
            LinearLayout ll_quote = _1quote.create(_context, db, q);
            
            result_layout.addView(ll_quote);
        }

        return result_layout;
    }
    
}
