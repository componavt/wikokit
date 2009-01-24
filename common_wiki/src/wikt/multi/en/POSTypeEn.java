/* POSTypeEn.java - English names of parts of speech.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.en;

import wikt.constant.POSType;
import wikt.constant.POS;

import java.util.Map;
import java.util.HashMap;

/** English names of parts of speech.
 * 
 * See http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained/POS_headers
 */
public class POSTypeEn extends POSType {
    
    /*private POSTypeEn(String name_in_text, String name, POS type) {
        super(name_in_text, name, type);
    }*/
    
    /** POS name encountered in the Wiktionary, e.g.: {{acronym}} or  Acronym, 
     * since there are ==={{acronym}}=== and ===Acronym===
     */
    private final String name_in_text;
    
    /** POS */
    private final POS type; 
    
    private static Map<String, POS> name2type = new HashMap<String, POS>();
    
    /** Initialization for POSTypeEn, POSTypeRu, etc. */
    private POSTypeEn(String name_in_text, POS type) {
        //super(name_in_text, type);
        
        this.name_in_text   = name_in_text;
        this.type           = type;         // english.english;
        name2type.put(name_in_text, type); // english.english);
    }
    
    public String getName() { return type.toString(); }
    
    /** Checks weather exists the language code 'code'. */
    public static boolean has(String code) {
        return name2type.containsKey(code);
    }
    
    
    // The classical parts of speech are:
    public static final POSType noun            = new POSTypeEn("Noun",         POS.noun);
    public static final POSType verb            = new POSTypeEn("Verb",         POS.verb);
    public static final POSType adverb          = new POSTypeEn("Adverb",       POS.adverb);
    public static final POSType adjective       = new POSTypeEn("Adjective",    POS.adjective);
    public static final POSType pronoun         = new POSTypeEn("Pronoun",      POS.pronoun);
    public static final POSType conjunction     = new POSTypeEn("Conjunction",  POS.conjunction);
    public static final POSType interjection    = new POSTypeEn("Interjection", POS.interjection);
    public static final POSType preposition     = new POSTypeEn("Preposition",  POS.preposition);
    
    
    // Additional commonly used grammatical headers are:
    public static final POSType proper_noun     = new POSTypeEn("Proper noun",  POS.proper_noun);
    public static final POSType article         = new POSTypeEn("Article",      POS.article);
    public static final POSType prefix          = new POSTypeEn("Prefix",       POS.prefix);
    public static final POSType suffix          = new POSTypeEn("Suffix",       POS.suffix);
    
    // may be the same:  Idiom, Phrase, Noun, Verb
    public static final POSType phrase          = new POSTypeEn("Phrase",       POS.phrase);
    // see e.g. "your mileage may vary" ==Idiom==
    //          "rain cats and dogs"    ===Verb===  # {{idiom}}          // idiomatic is ContextLabel
    //          "grain of salt"         ===Noun===  # {{idiom}}          // idiomatic is ContextLabel
    public static final POSType idiom           = new POSTypeEn("Idiom",        POS.idiom);
    
    
    // other descriptors that identify the usage of the entry, but which are not (strictly speaking) parts of speech:
    public static final POSType acronym_template= new POSTypeEn("{{acronym}}",  POS.acronym); // ==={{acronym}}===
    public static final POSType acronym         = new POSTypeEn("Acronym",      POS.acronym); // ===Acronym===
    
    public static final POSType abbreviation    = new POSTypeEn("{{abbreviation}}", POS.abbreviation);        // ==={{abbreviation}}===
    public static final POSType initialism      = new POSTypeEn("{{initialism}}",   POS.initialism);
    
    public static final POSType symbol          = new POSTypeEn("Symbol",       POS.symbol);
    public static final POSType letter          = new POSTypeEn("Letter",       POS.letter);
    
    
    // other headers in use
    public static final POSType particle        = new POSTypeEn("Particle",     POS.particle);  // 	(language) particles, CJKV languages, and some others; see tok, ne.
}
