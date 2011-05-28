/* POSTypeEn.java - English names of parts of speech.
 * 
 * Copyright (c) 2008-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.multi.en;

import wikt.constant.POSType;
import wikt.constant.POS;

import java.util.Map;
import java.util.HashMap;

/** English names of parts of speech.
 * 
 * @see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained/POS_headers
 */
public class POSTemplatesEn extends POSType {
    
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
    private POSTemplatesEn(String name_in_text, POS type) {
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

    /** Gets part of speech by its abbreviation */
    public static POS get(String code) {
        return name2type.get(code);
    }
    
    // The classical parts of speech are:
    public static final POSType noun            = new POSTemplatesEn("noun",         POS.noun);
    public static final POSType verb            = new POSTemplatesEn("verb",         POS.verb);
    public static final POSType adverb          = new POSTemplatesEn("adverb",       POS.adverb);
    public static final POSType adjective       = new POSTemplatesEn("adjective",    POS.adjective);
    public static final POSType pronoun         = new POSTemplatesEn("pronoun",      POS.pronoun);
    public static final POSType conjunction     = new POSTemplatesEn("conjunction",  POS.conjunction);
    public static final POSType interjection    = new POSTemplatesEn("interjection", POS.interjection);
    public static final POSType preposition     = new POSTemplatesEn("preposition",  POS.preposition);
    public static final POSType prepositional_phrase = new POSTemplatesEn("prepositional phrase",  POS.prepositional_phrase);
    
    // Additional commonly used grammatical headers are:
    public static final POSType proper_noun     = new POSTemplatesEn("proper noun",  POS.proper_noun);
    public static final POSType article         = new POSTemplatesEn("article",      POS.article);
    public static final POSType prefix          = new POSTemplatesEn("prefix",       POS.prefix);
    public static final POSType suffix          = new POSTemplatesEn("suffix",       POS.suffix);
    
    // may be the same:  Idiom, Phrase, Noun, Verb
    public static final POSType phrase          = new POSTemplatesEn("phrase",       POS.phrase);
    // see e.g. "your mileage may vary" ==Idiom==
    //          "rain cats and dogs"    ===Verb===  # {{idiom}}          // idiomatic is ContextLabel
    //          "grain of salt"         ===Noun===  # {{idiom}}          // idiomatic is ContextLabel
    public static final POSType idiom           = new POSTemplatesEn("idiom",        POS.idiom);
    
    
    // other descriptors that identify the usage of the entry, but which are not (strictly speaking) parts of speech:
    public static final POSType acronym_template= new POSTemplatesEn("{{acronym}}",  POS.acronym); // ==={{acronym}}===
    public static final POSType acronym         = new POSTemplatesEn("acronym",      POS.acronym); // ===Acronym===
    
    public static final POSType abbreviation    = new POSTemplatesEn("{{abbreviation}}", POS.abbreviation);        // ==={{abbreviation}}===
    public static final POSType initialism      = new POSTemplatesEn("{{initialism}}",   POS.initialism);
    
    public static final POSType symbol          = new POSTemplatesEn("symbol",       POS.symbol);
    public static final POSType letter          = new POSTemplatesEn("letter",       POS.letter);

    // debated POS level 3 headers
    public static final POSType number          = new POSTemplatesEn("numeral",      POS.numeral);
    public static final POSType numeral         = new POSTemplatesEn("number",       POS.numeral);
    public static final POSType cardinal_number = new POSTemplatesEn("cardinal number", POS.numeral);
    public static final POSType ordinal_number  = new POSTemplatesEn("ordinal number", POS.numeral);
    public static final POSType cardinal_numeral = new POSTemplatesEn("cardinal numeral", POS.numeral);
    public static final POSType ordinal_numeral = new POSTemplatesEn("ordinal numeral", POS.numeral);
    
    // other headers in use
    public static final POSType particle        = new POSTemplatesEn("particle",     POS.particle);  // 	(language) particles, CJKV languages, and some others; see tok, ne.
    public static final POSType participle      = new POSTemplatesEn("participle",   POS.participle);

    // Non-standard, deprecated headers
    public static final POSType verb_form       = new POSTemplatesEn("verb form",    POS.verb);


    
}
