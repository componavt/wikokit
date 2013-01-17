/* TLabelMeaning.java - SQL operations with the table 'label_meaning' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.sql.label;

import wikokit.base.wikt.sql.TMeaning;

/** An operations with the table 'label_meaning' in MySQL Wiktionary_parsed database.
 * label_meaning - binds together context labels and meaning number.
 */
public class TLabelMeaning {
    
    /** Context label (label_id). */
    private TLabel label;
    
    /** One sense of a word (meaning_id). */
    private TMeaning meaning;
    
}
