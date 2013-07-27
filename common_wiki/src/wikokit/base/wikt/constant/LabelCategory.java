/* LabelCategory.java - Categories of context labels (templates), context labels
 * are presented in the sections: definitions, Synonyms, and Translations.
 * 
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.constant;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** Categories of context labels (templates).
 * @see http://en.wiktionary.org/wiki/Category:Context_labels
 */
public class LabelCategory {
    
    /** Label category name, e.g. "Regional" context labels. */
    private final String name;
    
    /** Root (LabelCategory.empty) has .parent_category == null. */
    private final LabelCategory parent_category;
    
    protected final static Map<String, LabelCategory> name2category = new HashMap<String, LabelCategory>();
    protected final static Map<LabelCategory, String> category2name = new HashMap<LabelCategory, String>();
    
    private LabelCategory(String name, LabelCategory parent_category) {
        this.name = name;
        this.parent_category = parent_category;

        init(this);
        
        if(name.length() == 0)
            System.out.println("Error in LabelCategory.LabelCategory(): The label category name is empty.");
    }
    
    protected void init(LabelCategory label_category) {
    
        if(null == label_category)
            System.out.println("Error in LabelCategory.init(): label_category is null, category name="+name+".");
        
        checksPrefixSuffixSpace(name);
        
        // check the uniqueness of the label short name and full name
        LabelCategory cat_prev_by_name = name2category.get(name);

        if(null != cat_prev_by_name)
            System.out.println("Error in LabelCategory.init(): duplication of category_label names! name='"+name+ "'. Check the maps name2category.");
        
        name2category.put(name, label_category);
        category2name.put(label_category, name);
    };
    
    /** Checks whitespace characters in the prefix or suffix of a string.
     * Prints "error" message if there is any.
     */
    protected static void checksPrefixSuffixSpace(String s) {

        if(s.charAt(0) == ' ' || s.charAt(s.length()-1) == ' ')
            System.out.println("Error in LabelCategory.checksPrefixSuffixSpace(): there are leading spaces, string='"+s+"'.");
    }
    
    /** Gets label category name. */
    public String getName() {
        return name;
    }
    
    /** Gets parent label category. */
    public LabelCategory getParent() {
        return parent_category;
    }
    
    /** Gets ID of parent label category from the table 'label_category'. */
    //public LabelCategory getParent() {
    //    return parent_category;
    //}
    
    public String toString() { return name; }
    
    public static boolean hasName(String name) {
        return name2category.containsKey(name);
    }

    /** Gets category label by name of this category. */
    public static LabelCategory getByName(String name) throws NullPointerException
    {
        LabelCategory label_category;

        if(null != (label_category = name2category.get(name)))
            return  label_category;

        throw new NullPointerException("Null LabelCategory.getByName(), label category name="+ name);
    }
    
    /** Gets all label categories. */
    public static Collection<LabelCategory> getAllLabelCats() {
        return name2category.values();
    }
    
    /** Counts number of labels categories. */
    public static int size() {
        return name2category.size();
    }
    
    /** Gets all names of labels (short name). */
    public static Set<String> getAllNames() {
        return name2category.keySet();
    }
    
    public static final LabelCategory
            root, // base (root) category of context labels
            
            //unknown,  // category for labels added automatically by parser
                        // if label_category is unknown then label.category_id == NULL
            
            invisible,  // special category for form-of templates (ruwikt), e.g. "{{=|word}}" => "the same as [[word]]" - there is nothing to be printed for this template
            
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
            sports;
                
    
    static {
        root       = new LabelCategory("root", null); // root
        
        // unknown     = new LabelCategory("unknown", LabelCategory.root);
        invisible   = new LabelCategory("invisible", LabelCategory.root);
        
        grammatical = new LabelCategory("grammatical", LabelCategory.root);
        period      = new LabelCategory("period", LabelCategory.root);
        qualifier   = new LabelCategory("qualifier", LabelCategory.root);
        regional    = new LabelCategory("regional", LabelCategory.root);
        usage       = new LabelCategory("usage", LabelCategory.root);
        
        topical     = new LabelCategory("topical", LabelCategory.root);
        computing   = new LabelCategory("computing", LabelCategory.topical);
        games       = new LabelCategory("games", LabelCategory.topical);
        mathematics = new LabelCategory("mathematics", LabelCategory.topical);
        music       = new LabelCategory("music", LabelCategory.topical);
        mythology   = new LabelCategory("mythology", LabelCategory.topical);
        religion    = new LabelCategory("religion", LabelCategory.topical);
        science     = new LabelCategory("science", LabelCategory.topical);
        sports      = new LabelCategory("sports", LabelCategory.topical);
    }
}
