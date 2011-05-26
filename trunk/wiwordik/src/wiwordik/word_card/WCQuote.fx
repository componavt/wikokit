/* WCQuote.fx - A part of word card corresponds to quotations part
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

    public var group: VBox = VBox {
        spacing: 5
    };

    /** Creates a part of card (parts of wiki pages) with list of quotes
     * related to one meaning (sense).
     *
     * @return true if there are any quotes for this meaning.
    **/
    public function create (conn : Connect,
                            _tmeaning : TMeaning
                           ) : Boolean {

        // def rels : TRelation[] = TRelation.get(conn, _tmeaning);
        def quotes : TQuote[] = TQuote.get(conn, _tmeaning);
        if (quotes.size() == 0)
            return false;

        var list : String;
        for(q in quotes) {
            // list = "{list}{q.getText()} || ";
            
            def _1quote : WCQuoteOneSentence = new WCQuoteOneSentence();
            _1quote.create(conn, q);
            //insert _quote into quote;                // logic
            insert _1quote.group into group.content;   // visual
        }

        return true;
    }
}
