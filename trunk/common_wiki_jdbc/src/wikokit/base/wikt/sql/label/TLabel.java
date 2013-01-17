/* TLabel.java - SQL operations with the table 'label' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.sql.label;

/** An operations with the table 'label' (context labels) in MySQL Wiktionary_parsed database.
 */
public class TLabel {
    
    /** Unique identifier in the table 'label'. */
    private int id;
    
    /** Context label short name. */
    private String label;
    
    /** Context label full name. */
    private String full_name;
    
    /** Category of context label (category_id). */
    private TLabelCategory label_category;
    
    /** Number of definitions with this context label. */
    private int counter;
    
}
