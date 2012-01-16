/* WCQuote.java - A part of word card corresponds to quotations part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
 
package wiwordik.word_card;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikt.sql.quote.TQuote;

import javafx.scene.layout.VBox;
import java.lang.*;

/** One WCQuote contains all quotes (phrase/sentences) that illustrates one meaning
 *  of Wiktionary word.
 *
 * @see wikt.word.WQuote
 */
 public class WCQuote {

     public VBox group = new VBox();

    /** Creates a part of card (parts of wiki pages) with list of quotes
     * related to one meaning (sense).
     *
     * @return true if there are any quotes for this meaning.
    **/
    public boolean create ( Connect conn,
                            TMeaning _tmeaning
                          )
    {
        group.setSpacing(5);
                
        // def rels : TRelation[] = TRelation.get(conn, _tmeaning);
        TQuote[] quotes = TQuote.get(conn, _tmeaning);
        if (quotes.length == 0)
            return false;

        String list;
        for(TQuote q : quotes) {
            // list = "{list}{q.getText()} || ";
            
            WCQuoteOneSentence _1quote = new WCQuoteOneSentence();
            _1quote.create(conn, q);
            
            // only visual part, skip logic
            group.getChildren().addAll(_1quote.group);
        }

        return true;
    }
}
