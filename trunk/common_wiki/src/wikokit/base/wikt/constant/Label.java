/* Label.java - contexual information for definitions, or Synonyms,
 *                     or Translations.
 * 
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.constant;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.multi.en.name.LabelEn;
import wikokit.base.wikt.multi.ru.name.LabelRu;


/** Contextual information for definitions, such as archaic, by analogy, 
 * chemistry, etc.
 * 
 * This contextual information is located in the sections: semantic relations 
 * and translations in Russian Wiktionary.
 * 
 * This class describes context labels of English Wiktionary. Context labels 
 * of other wiktionaries (e.g. Russian Wiktionary) are described in 
 * LabelRu, German Wikt in LabelDe (todo), French Wikt in LabelFr (todo).
 *
 * @see (Scheme of Label abstract classes hierarchy - Wiktionary parser) wikokit\wikt_parser\doc\screenshots\Label_abstract_classes_hierarchy_IMG_20130205.jpg  
 * @see http://en.wiktionary.org/wiki/Template_talk:context
 * @see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 * @see http://en.wiktionary.org/wiki/Template:context
 */
public abstract class Label {
    
    /** Label itself, e.g. {{obsolete}}, {{slang}}. */
    protected String short_name;
    
    /** Label name, e.g. 'New Zealand' for {{NZ}}. */
    protected String name;
    
    /** Weather the label was added manually to the code of wikokit, or was gathered automatically by parser. */
    protected boolean added_by_hand;
    
    /** Constructor for labels added by hand, @see list in LabelEn, LabelRu, etc. */
    protected Label(String short_name, String name, boolean added_by_hand) {
    
        if(short_name.length() == 0 || name.length() == 0)
            System.out.println("Error in Label.Label(): one of parameters is empty! label="+short_name+"; name=\'"+name+"\'.");
        
        this.short_name    = short_name; 
        this.name          = name;
        this.added_by_hand = added_by_hand;
    }
    
    /** Constructor for new context labels which are extracted by parser 
     * from the template {{context|new label}} and added automatically,
     * these new labels are not listed in the LabelEn.
     * 
     * @param short_name name of the found of context label
     */
    public Label(String short_name) {
    
        if(short_name.length() == 0)
            System.out.println("Error in Label.Label(String short_name): label short_name is empty (\"\")!");
        
        this.short_name    = short_name; 
        this.name          = "";
        this.added_by_hand = false; // added automatically, e.g. some label extracted from {{context|some label}}
    }
    
    /** Checks whitespace characters in the prefix or suffix of a string.
     * Prints "error" message if there is any.
     */
    protected static void checksPrefixSuffixSpace(String s) {

        if(s.charAt(0) == ' ' || s.charAt(s.length()-1) == ' ')
            System.out.println("Error in Label.checksPrefixSuffixSpace(): there are leading spaces, string='"+s+"'.");
    }
    
    /** Gets English Wiktionary context label (LabelEn) associated with this label (e.g. LabelRu, LabelFr, etc.). */
    abstract public LabelEn getLinkedLabelEn();
    
    @Override
    public String toString() { return short_name; }
    
    /** Gets label itself (short name). */
    public String getShortName() {
        return short_name;
    }
    
    /** Gets label itself (short name) in English. 
     *  This functions is needed for comparison (equals()) with LabelLocal labels.
     */
    abstract public String getShortNameEnglish();

    /** Gets label full name. */
    public String getName() {
        return name;
    }
    
    /** Checks weather the label was added manually to the code of wikokit, or was gathered automatically by parser. */
    public boolean getAddedByHand() {
        return added_by_hand;
    }
    
    /** Gets (full) name of context label by label object.
     * 
     * It is supposed that (1) number of context labels in enwikt > in ruwikt,
     * (2) context labels in enwikt cover (include) labels in all other wiktionaries.
     * So, first 
     * 
     * @param label
     * @return 
     */
    /*public static String getName (Label label) {

        String s = label2name.get(label);
        if(null == s) // e.g. LabelRu don't has "dated_sense" label
            return label.getName(); // if there is no translation into local language, then English name

        return s;
    }*/
    
    /** Gets all labels. */
    // ? abstract public static Collection<Label> getAllLabels();
    
    /** Counts number of labels. */
    // ?? abstract public static int size();
    
    /** Gets all names of labels (short name). */
    // ??? abstract public static Set<String> getAllLabelShortNames();
    

    /** @return true if short name of two labels are the same. */
    static public boolean equals (Label one, Label two) {
        //return one.short_name.equals( two.short_name );
        return one.getShortNameEnglish().equals( two.getShortNameEnglish() );
    }
    
    /** The set of unknown labels, which were found during parsing.
     * It should be only one message for one unknown label (for concise logging).
     */
    private static Set<String> unknown_label = new HashSet<String>();

    /** Checks weather exists the unknown label 'label'. */
    public static boolean hasUnknownLabel(String label) {
        return unknown_label.contains(label);
    }

    /** Adds unknown language code 'code'. */
    public static boolean addUnknownLabel(String label) {
        return unknown_label.add(label);
    }
    
    
    /** Gets all labels. */
    public static Collection<Label> getAllLabels(LanguageType lang_code) {
        
        Collection<Label> result;
        LanguageType l = lang_code;
        
        if(l  == LanguageType.en) {
            result = LabelEn.getAllLabels();
        } else if(l == LanguageType.ru) {
            result = LabelRu.getAllLabels();

        //} //else if(code.equalsIgnoreCase( "simple" )) {
            
            // todo 
            // ...
            
        } else {
            throw new NullPointerException("Exception in Label.getAllLabels(): Null LanguageType");
        }
        
        return result;
    }
    
    /** Counts number of labels. */
    public static int size(LanguageType lang_code) {
        
        int result;
        LanguageType l = lang_code;
        
        if(l  == LanguageType.en) {
            result = LabelEn.size();
        } else if(l == LanguageType.ru) {
            result = LabelRu.size();

        //} //else if(code.equalsIgnoreCase( "simple" )) {
            
            // todo 
            // ...
            
        } else {
            throw new NullPointerException("Exception in Label.size(): Null LanguageType");
        }
        
        return result;
    }
    
    /** Gets all names of labels (short name). */
    public static Set<String> getAllLabelShortNames(LanguageType lang_code) {
        
        Set<String> result;
        LanguageType l = lang_code;
        
        if(l  == LanguageType.en) {
            result = LabelEn.getAllLabelShortNames();
        } else if(l == LanguageType.ru) {
            result = LabelRu.getAllLabelShortNames();

        //} //else if(code.equalsIgnoreCase( "simple" )) {
            
            // todo 
            // ...
            
        } else {
            throw new NullPointerException("Exception in Label.getAllLabelShortNames(): Null LanguageType");
        }
        
        return result;
    }
    
    /** Checks weather exists the Label (short name) by its name, checks synonyms also. */
    public static boolean hasShortName(String short_name, LanguageType lang_code) {
        
        boolean result;
        LanguageType l = lang_code;
        
        if(l  == LanguageType.en) {
            result = LabelEn.hasShortName(short_name);
        } else if(l == LanguageType.ru) {
            result = LabelRu.hasShortName(short_name);

        //} else if(l == LanguageType.??) {
            
            // todo 
            // ...
            
        } else {
            throw new NullPointerException("Exception in Label.hasShortName(): Null LanguageType");
        }
        
        return result;
    }
    
    /** Gets label by short name of the label. */
    public static Label getByShortName(String short_name, LanguageType lang_code) {
        
        Label result;
        LanguageType l = lang_code;
        
        if(l  == LanguageType.en) {
            result = LabelEn.getByShortName(short_name);
        } else if(l == LanguageType.ru) {
            result = LabelRu.getByShortName(short_name);

        //} else if(l == LanguageType.??) {
            
            // todo 
            // ...
            
        } else {
            throw new NullPointerException("Exception in Label.getByShortName(): Null LanguageType");
        }
        
        return result;
    }
}
