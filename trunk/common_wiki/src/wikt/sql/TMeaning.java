/* TMeaning.java - SQL operations with the table 'meaning' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikt.sql.*;

/** An operations with the table 'meaning' in MySQL wiktionary_parsed database.
 *
 * @see wikt.word.WPOS
 */
public class TMeaning {

    /** Unique identifier in the table 'meaning'. */
    private int id;

    /** Link to the table 'lang_pos', which defines language and POS. */
    private TLangPOS lang_pos;          // int lang_pos_id;

    /** Meaning (sense) number. */
    private int meaning_n;

    /** Wikified text describing this meaning. */
    private TWikiText wiki_text;        // int wiki_text_id

    

    

}
