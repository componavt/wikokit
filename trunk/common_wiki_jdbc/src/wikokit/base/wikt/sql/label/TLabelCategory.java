/* TLabelCategory.java - SQL operations with the table 'label_category' 
 * in Wiktionary parsed database.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.sql.label;

import java.util.Map;
import wikokit.base.wikt.constant.LabelCategory;

/** An operations with the table 'label_category' in MySQL Wiktionary_parsed database.
 * label_category - categories of context labels (templates).
 * 
 * This class defines also special types of context labels, e.g.
 * id == 0 - labels found by parser, else labels were entered by this software developers. 
 */
public class TLabelCategory {
    
    /** Unique identifier in the table 'label_category'. */
    private int id;
    
    /** Category of the label: the name. */
    private LabelCategory label_category;
    
                /** Context label category name (i.e. type of context label). */
                //private String name;
    
    /** Parent category of context label category (parent_category_id), 
     * e.g. label category "Computing context labels‎" 
     * has parent category "Topical context labels‎". */
    private LabelCategory parent_category;
    
    
    /** Map from language to id.*/
    private static Map<LabelCategory, Integer> label_category2id;

    private final static TLabelCategory[] NULL_TLABEL_CATEGORY_ARRAY = new TLabelCategory[0];
    
    public TLabelCategory(int _id, LabelCategory _label_category, LabelCategory _parent) {
        id      = _id;
        label_category  = _label_category;
        parent_category = _parent;
    }
}
