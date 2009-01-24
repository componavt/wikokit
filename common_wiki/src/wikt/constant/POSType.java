/* POSType.java - kinds or types (i.e. names) of parts of speech.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.constant;

//import java.util.Map;
//import java.util.HashMap;

/** Strictly defined names of parts of speech.
 */
//interface POSType {
   public abstract class POSType {
        
    /** POS name encountered in the Wiktionary, e.g.: {{acronym}} or  Acronym, 
     * since there are ==={{acronym}}=== and ===Acronym===
     */
//    private final String name_in_text;
    
    /** POS name, e.g. acronym */
    //private final String name;
    
    /** English translation of POS name, e.g. acronym */
    //private final String english;
    
    /** POS */
//    private final POS type; 
    
    //private static Map<String, String>      text2name = new HashMap<String, String>();
//    private static Map<String, POS> name2type = new HashMap<String, POS>();
    
    /// public String getName();
//    public String getName() { return type.toString(); }

    /** Initialization for POS */
    /*protected POSType(String name) { 
        this.name           = name;
        this.type           = null;
        this.name_in_text   = "";   // abstract "noun" in any Wiktionary
        name2type.put(name, null);  // Map is used here as List for the presence checking
    }*/
    
    /** Initialization for POSTypeEn, POSTypeRu, etc. */
    //abstract protected POSType(String name_in_text, POS type);
/*    protected POSType(String name_in_text, String name, POSType english) { 
        this.name_in_text   = name_in_text;
        this.type           = type;         // english.english;
        name2type.put(name_in_text, type); // english.english);
    }
*/    
    /** Checks weather exists the language code 'code'. */
//    abstract public boolean has(String code);
    /*public static boolean has(String code) {
        return name2type.containsKey(code);
    }*/
}
