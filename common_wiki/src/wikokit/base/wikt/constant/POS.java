/* POS.java - list of parts of speech used in all wiktionaries.
 * 
 * Copyright (c) 2008-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.constant;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.multi.ru.name.POSRu;
import wikokit.base.wikt.multi.ru.POSTemplateRu;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import wikokit.base.wikt.multi.en.POSTemplateEn;
import wikokit.base.wikt.multi.en.name.POSEn;

/** Strictly defined names of parts of speech
 * used in all wiktionaries.
 */
public class POS {

    /** POS name, e.g. acronym */
    private final String name;
    
    @Override
    public String toString() { return name; }

    /** Gets name of POS in the language l.
     * If there is no translation then returns POS name in English */
    public String toString(LanguageType l) {

        String s = "";

        if(l  == LanguageType.ru) {
            s = POSRu.getName(this);

        } else if(l == LanguageType.en) {
            //s = name;
            s = POSEn.getName(this);
        } else {
            throw new NullPointerException("POS.toString(LanguageType l): Null LanguageType");
        }

        if(0 == s.length()) // English name is better than nothing
            s = name;

        return s;
    }

    /** Gets short name of POS in the language l. */
    public String getShortName (LanguageType l) {

        String s = "";

        if(l == LanguageType.ru) {
            s = POSRu.getShortName(this);
        } else if(l == LanguageType.en) {
            s = POSEn.getShortName(this);
        } else {
            throw new NullPointerException("POS.getShortName(LanguageType l): Null LanguageType");
        }
       
        return s;
    }

    /** Gets (token separated) abbreviations or templates used (by parser) in order
     * to recognize this part of speech. */
    public String getTemplates(String token, LanguageType l) {

        String s = "";

        if(l  == LanguageType.ru) {
            s = POSTemplateRu.getTemplates(token, this);

        } else if(l == LanguageType.en) {
            s = POSTemplateEn.getTemplates(token, this);

        } else {
            throw new NullPointerException("Null LanguageType");
        }

        return s;
    }

    
    /* Set helps to check the presence of elements */
    private static Map<String, POS> name2type = new HashMap<String, POS>();
    //private static Set<String>  name_set = new HashSet<String>();
    
    private POS(String _name) {
        //super(name);  // old
        
        name = _name;
        name2type.put(_name, this);
    }
    
    /** Checks weather exists the part of speech by its name. */
    public static boolean has(String _name) {
        return name2type.containsKey(_name);
    }
    
    /** Gets part of speech by its name */
    public static POS get(String _name) {
        return name2type.get(_name);
        //return name_set.get(_name);
    }

    /** Counts number of POS. */
    public static int size() {
        return name2type.size();
    }
    
    /** Gets all POS. */
    public static Collection<POS> getAllPOS() {
        return name2type.values();
    }

    /** Gets all POS names. */
    public static Set<String> getAllPOSNames() {
        return name2type.keySet();
    }

    
    /** The POS is unknown :( */
    public static final POS unknown         = new POS("unknown");
    
    // The classical parts of speech are:
    public static final POS noun            = new POS("noun");
    public static final POS verb            = new POS("verb");
    public static final POS adverb          = new POS("adverb");
    public static final POS adjective       = new POS("adjective");
    public static final POS pronoun         = new POS("pronoun");
    public static final POS conjunction     = new POS("conjunction");
    public static final POS interjection    = new POS("interjection");
    public static final POS preposition     = new POS("preposition");
    
    
    // Additional commonly used grammatical headers are:
    public static final POS proper_noun     = new POS("proper noun");
    public static final POS article         = new POS("article");
    public static final POS prefix          = new POS("prefix");
    public static final POS suffix          = new POS("suffix");
    
    public static final POS phrase          = new POS("phrase");
    public static final POS idiom           = new POS("idiom");
    public static final POS prepositional_phrase = new POS("prepositional phrase");

    // debated POS level 3 headers
    public static final POS numeral         = new POS("numeral");

    // other descriptors that identify the usage of the entry, but which are not (strictly speaking) parts of speech:
    public static final POS acronym         = new POS("acronym");
    
    public static final POS abbreviation    = new POS("abbreviation");
    public static final POS initialism      = new POS("initialism");
    public static final POS contraction     = new POS("contraction");
    
    public static final POS symbol          = new POS("symbol");
    public static final POS letter          = new POS("letter");
    
    // other headers in use
    public static final POS particle        = new POS("particle");
    public static final POS participle      = new POS("participle");
    public static final POS determiner      = new POS("determiner");
    public static final POS infix           = new POS("infix");
    public static final POS interfix        = new POS("interfix");
    public static final POS affix           = new POS("affix");
    public static final POS circumfix       = new POS("circumfix");
    public static final POS counter         = new POS("counter");
    
    public static final POS kanji           = new POS("kanji");
    public static final POS kanji_reading   = new POS("kanji reading");
    public static final POS hanja_reading   = new POS("hanja reading");
    public static final POS hiragana_letter = new POS("hiragana letter");
    public static final POS katakana_letter = new POS("katakana letter");
    
    public static final POS pinyin = new POS("pinyin");
    public static final POS han_character = new POS("han character");
    public static final POS hanzi = new POS("hanzi");
    public static final POS hanja = new POS("hanja");

    public static final POS proverb = new POS("proverb");
    public static final POS expression = new POS("expression");
    public static final POS possessive_adjective = new POS("possessive adjective");
    public static final POS postposition = new POS("postposition");
    public static final POS gerund = new POS("gerund");
    public static final POS pronominal_adverb = new POS("pronominal adverb");
    public static final POS adnominal = new POS("adnominal");
    public static final POS root = new POS("root");
    public static final POS pinyin_syllable = new POS("pinyin syllable");
    public static final POS syllable = new POS("syllable");
    public static final POS hiragana_character = new POS("hiragana character");
    public static final POS katakana_character = new POS("katakana character");
    public static final POS jyutping_syllable = new POS("jyutping syllable");
    public static final POS gismu = new POS("gismu");
    public static final POS lujvo = new POS("lujvo");
    public static final POS brivla = new POS("brivla");
    public static final POS classifier = new POS("classifier");
    public static final POS predicative = new POS("predicative");
    public static final POS measure_word = new POS("measure word");
    public static final POS correlative = new POS("correlative");
    public static final POS preverb = new POS("preverb");
    public static final POS prenoun = new POS("prenoun");
    public static final POS noun_stem = new POS("noun stem");
    public static final POS noun_class = new POS("noun class");
    public static final POS combined_kana_character = new POS("combined-kana character");


    // public static final POS  = new POS("");



    

    // only in Russian Wiktionary (yet)
    //??? public static final POS adjectival_participle = new POS("adjectival participle");// adjectival_participle - причастие
    public static final POS verb_interjection = new POS("verb-interjection");// interj1 - глагольно-междометное слово - verb-interjection word
    public static final POS parenthesis = new POS("parenthesis");// вводное слово
    public static final POS prefix_of_compound = new POS("prefix of compound words");// первая часть сложных слов
    
}
