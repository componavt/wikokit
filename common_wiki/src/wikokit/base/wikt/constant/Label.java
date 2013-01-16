/* ContextLabel.java - contexual information for definitions, or Synonyms,
 *                     or Translations.
 * 
 * Copyright (c) 2008-2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.constant;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/** Contexual information for definitions, such as archaic, by analogy, 
 * chemistry, etc.
 *
 * See http://en.wiktionary.org/wiki/Template_talk:context
 */
public abstract class Label {
       
    /** Two (or more) letter label code, e.g. 'устар.', 'п.'. */
    private final String label;
    
    /** Label name, e.g. 'устарелое', 'переносное значение'. */
    private final String name;
    
    /** Category associated with this label. */
    private final String category;
    
    private static Map<String, String> label2name     = new HashMap<String, String>();
    private static Map<String, String> label2category = new HashMap<String, String>();
    
    /** If there are more than one context label (synonyms): <synonymic_label, source_main_unique_label> */
    private static Map<String, String> multiple_synonym2label = new HashMap<String, String>();
    
    protected Label(String label,String name,String category) { 
        this.label      = label; 
        
        if(label.length() == 0 || name.length() == 0 || category.length() == 0)
            System.out.println("Error in ContextLabel.ContextLabel(): one of parameters is empty! label="+label+"; name=\'"+name+"\'; category=\'"+category+"\'.");
        
        this.name       = name; 
        this.category   = category; 
        label2name.     put(label, name);
        label2category. put(label, category);
        
        // check the uniqueness of the label full code and the category
        String name_prev = label2name.get(label);
        String category_prev = label2category.get(label);
        
        if(null != name_prev)
            System.out.println("Error in ContextLabel.ContextLabel(): duplication of label! label="+label+
                    " name='"+name+"'. Check the maps label2name and multiple_label2name.");

        if(null != category_prev)
            System.out.println("Error in ContextLabel.ContextLabel(): duplication of category! category="+label+
                    " language='"+category_prev+ "'. Check the maps label2name and multiple_label2name.");
        
        checksPrefixSuffixSpace(label);
        checksPrefixSuffixSpace(name);
        checksPrefixSuffixSpace(category);
    }
    
    /** Checks weather exists the context label 'label'. */
    public static boolean has(String label) {
        return   label2name.containsKey(label) ||
        multiple_synonym2label.containsKey(label);
    }
    
    /** Checks whitespace characters in the prefix or suffix of a string.
     * Prints "error" message if there is any.
     */
    private static void checksPrefixSuffixSpace(String s) {

        if(s.charAt(0) == ' ' || s.charAt(s.length()-1) == ' ')
            System.out.println("Error in ContextLabel.checksPrefixSuffixSpace(): there are leading spaces, string='"+s+"'.");
    }
    
    /** Gets label name by context label. */
    public static String get(String label) throws NullPointerException
    {
        String name;

        if(null != (name = label2name.get(label)))
            return  name;

        if(null != (name = multiple_synonym2label.get(label)))
            return  name;

        throw new NullPointerException("Null ContextLabel.get(), label="+ label);
    }
    
    /** Adds synonymic context label for the main (source) label.
     * @param label source main unique label
     * @param synonymic_label synonym of label
     */
    public static String addNonUniqueLabel(String synonymic_label, String label) {

        checksPrefixSuffixSpace(synonymic_label);
        if(synonymic_label.length() > 255) {
            System.out.println("Error in ContextLabel.addNonUniqueLabel(): the synonymic label='"+synonymic_label+
                    "' is too long (.length() > 255)!");
            return null;
        }

        if(label2name.containsKey(synonymic_label)) {
            System.out.println("Error in ContextLabel.addNonUniqueLabel(): the synonymic label '"+synonymic_label+
                    "' is already presented in the map label2name!");
            return null;
        }
        
        if(multiple_synonym2label.containsKey(synonymic_label)) {
            System.out.println("Error in LanguageType.addNonUniqueName(): the synonymic label '"+synonymic_label+
                    "' is already presented in the map multiple_synonym2label!");
            return null;
        }
        
        multiple_synonym2label.put(synonymic_label, label);
        return label;
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

}
