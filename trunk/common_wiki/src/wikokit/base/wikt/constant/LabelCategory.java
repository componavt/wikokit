/* LabelCategory.java - Categories of context labels (templates), context labels
 * are presented in the sections: definitions, Synonyms, and Translations.
 * 
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.constant;

/** Categories of context labels (templates).
 * @see http://en.wiktionary.org/wiki/Category:Context_labels
 */
public class LabelCategory {
    
    /** Label category name, e.g. "Regional" context labels. */
    private final String name;
    
    
    private LabelCategory(String name) {
        this.name = name;

        if(name.length() == 0)
            System.out.println("Error in LabelCategory.LabelCategory(): The label category name is empty.");
    }
    
    /** Gets label category name. */
    public String getName() {
        return name;
    }
    
    public String toString() { return name; }
    
    public static final LabelCategory
            unknown, // category for labels added automatically by parser
            
            empty, // base category, or context labels without any 
            
            grammatical, 
            period,
            qualifier,
            regional, 
            usage,
            
            topical,
            computing,
            games,
            mathematics,
            music,
            mythology,
            religion,
            science,
            sports,
            
            invisible; // special category for form-of templates (ruwikt), e.g. "{{=|word}}" => "the same as [[word]]" - there is nothing to be printed for this template
                
    
    static {
        unknown     = new LabelCategory("Unknown");
        
        empty       = new LabelCategory("Empty");
        
        grammatical = new LabelCategory("Grammatical");
        period      = new LabelCategory("Period");
        qualifier   = new LabelCategory("Qualifier");
        regional    = new LabelCategory("Regional");
        usage       = new LabelCategory("Usage");
        
        topical     = new LabelCategory("Topical");
        computing   = new LabelCategory("Computing");
        games       = new LabelCategory("Games");
        mathematics = new LabelCategory("Mathematics");
        music       = new LabelCategory("Music");
        mythology   = new LabelCategory("Mythology");
        religion    = new LabelCategory("Religion");
        science     = new LabelCategory("Science");
        sports      = new LabelCategory("Sports");
        
        invisible   = new LabelCategory("Invisible");
    }
}
