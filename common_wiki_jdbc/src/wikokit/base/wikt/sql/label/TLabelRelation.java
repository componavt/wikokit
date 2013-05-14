/* TLabelRelation.java - SQL operations with the table 'label_relation' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.sql.label;

import wikokit.base.wikt.sql.TRelation;

/** An operations with the table 'label_relation' in MySQL Wiktionary_parsed database.
 * label_relation - binds together context labels and semantic relation number.
 */
public class TLabelRelation {
    
    /** Context label (label_id). */
    private TLabel label;
    
    /** One semantic relation of a word (relation_id). */
    private TRelation relation;
    
}