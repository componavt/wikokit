/* POSTypeEn.java - English names of parts of speech.
 * 
 * Copyright (c) 2008-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.en;

import wikokit.base.wikt.constant.POSType;
import wikokit.base.wikt.constant.POS;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import wikokit.base.wikipedia.util.StringUtil;

/** English names of parts of speech.
 * 
 * @see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained/POS_headers
 */
public class POSTemplateEn extends POSType {
    
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

    /** E.g. verb -> "verb", "verb form", "verb prefix". It is used in POS statistics. */
    private static Map<POS, Set<String>> type2name_in_text = new HashMap<POS, Set<String>>();

    private final static String[] NULL_STRING_ARRAY = new String[0];
    
    /** Initialization for POSTypeEn, POSTypeRu, etc. */
    private POSTemplateEn(String name_in_text, POS type) {
        //super(name_in_text, type);
        
        this.name_in_text   = name_in_text;
        this.type           = type;         // english.english;
        name2type.put(name_in_text, type); // english.english);

        {   // store (POS, +=name_in_text) -> type2name_in_text
            Set<String> templates = type2name_in_text.get(type);
            if(null == templates)
                templates = new HashSet<String>();

            templates.add(name_in_text);
            type2name_in_text.put(type, templates);
        }
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

    /** Gets (token separated) abbreviations or templates used in order
     * to recognize the "pos" part of speech.
     */
    public static String getTemplates(String token, POS pos) {

        Set<String> templates = type2name_in_text.get(pos);
        return StringUtil.join(", ", (String[])templates.toArray(NULL_STRING_ARRAY));
    }

    
    // The classical parts of speech are: 
    public static final POSType noun = new POSTemplateEn("noun", POS.noun);

    public static final POSType verb = new POSTemplateEn("verb", POS.verb);
    public static final POSType verb_form = new POSTemplateEn("verb form", POS.verb);
    public static final POSType verb_prefix = new POSTemplateEn("verb prefix", POS.verb);

    public static final POSType adverb          = new POSTemplateEn("adverb",       POS.adverb);

    public static final POSType adjective       = new POSTemplateEn("adjective",    POS.adjective);
    public static final POSType adjectival_noun = new POSTemplateEn("adjectival noun", POS.adjective);
    public static final POSType quasi_adjective = new POSTemplateEn("quasi-adjective", POS.adjective);

    public static final POSType pronoun         = new POSTemplateEn("pronoun",      POS.pronoun);
    public static final POSType conjunction     = new POSTemplateEn("conjunction",  POS.conjunction);
    public static final POSType interjection    = new POSTemplateEn("interjection", POS.interjection);
    public static final POSType preposition     = new POSTemplateEn("preposition",  POS.preposition);
    public static final POSType prepositional_phrase = new POSTemplateEn("prepositional phrase",  POS.prepositional_phrase);
    
    // Additional commonly used grammatical headers are:
    public static final POSType proper_noun     = new POSTemplateEn("proper noun",  POS.proper_noun);
    public static final POSType article         = new POSTemplateEn("article",      POS.article);
    public static final POSType prefix          = new POSTemplateEn("prefix",       POS.prefix);
    public static final POSType suffix          = new POSTemplateEn("suffix",       POS.suffix);
    
    // may be the same:  Idiom, Phrase, Noun, Verb
    public static final POSType phrase          = new POSTemplateEn("phrase",       POS.phrase);
    // see e.g. "your mileage may vary" ==Idiom==
    //          "rain cats and dogs"    ===Verb===  # {{idiom}}          // idiomatic is ContextLabel
    //          "grain of salt"         ===Noun===  # {{idiom}}          // idiomatic is ContextLabel
    public static final POSType idiom           = new POSTemplateEn("idiom",        POS.idiom);
    
    
    // other descriptors that identify the usage of the entry, but which are not (strictly speaking) parts of speech:
    public static final POSType acronym_template= new POSTemplateEn("{{acronym}}",  POS.acronym); // ==={{acronym}}===
    public static final POSType acronym         = new POSTemplateEn("acronym",      POS.acronym); // ===Acronym===
    
    public static final POSType abbreviation_template = new POSTemplateEn("{{abbreviation}}", POS.abbreviation);// ==={{abbreviation}}===
    public static final POSType abbreviation    = new POSTemplateEn("abbreviation", POS.abbreviation);
    public static final POSType initialism_template = new POSTemplateEn("{{initialism}}", POS.initialism);
    public static final POSType initialism      = new POSTemplateEn("initialism", POS.initialism);
    public static final POSType contraction     = new POSTemplateEn("contraction", POS.contraction);
    
    public static final POSType symbol          = new POSTemplateEn("symbol",       POS.symbol);
    public static final POSType letter          = new POSTemplateEn("letter",       POS.letter);

    // debated POS level 3 headers
    public static final POSType number          = new POSTemplateEn("numeral",      POS.numeral);
    public static final POSType numeral         = new POSTemplateEn("number",       POS.numeral);
    public static final POSType cardinal_number = new POSTemplateEn("cardinal number", POS.numeral);
    public static final POSType ordinal_number  = new POSTemplateEn("ordinal number", POS.numeral);
    public static final POSType cardinal_numeral = new POSTemplateEn("cardinal numeral", POS.numeral);
    public static final POSType ordinal_numeral = new POSTemplateEn("ordinal numeral", POS.numeral);
    
    // other headers in use
    public static final POSType particle        = new POSTemplateEn("particle",  POS.particle);  // 	(language) particles, CJKV languages, and some others; see tok, ne.
    public static final POSType participle      = new POSTemplateEn("participle",POS.participle);
    public static final POSType determiner      = new POSTemplateEn("determiner",POS.determiner);
    public static final POSType infix    = new POSTemplateEn("infix",   POS.infix);
    public static final POSType interfix = new POSTemplateEn("interfix",POS.interfix);
    public static final POSType affix    = new POSTemplateEn("affix",   POS.affix);
    public static final POSType circumfix = new POSTemplateEn("circumfix", POS.circumfix);
    public static final POSType counter = new POSTemplateEn("counter", POS.counter);

    public static final POSType kanji = new POSTemplateEn("kanji", POS.kanji);
    public static final POSType kanji_reading = new POSTemplateEn("kanji reading", POS.kanji_reading);
    public static final POSType hanja_reading = new POSTemplateEn("hanja reading", POS.hanja_reading);
    public static final POSType hiragana_letter = new POSTemplateEn("hiragana letter", POS.hiragana_letter);
    public static final POSType katakana_letter = new POSTemplateEn("katakana letter", POS.katakana_letter);

    public static final POSType pinyin = new POSTemplateEn("pinyin", POS.pinyin);
    public static final POSType han_character = new POSTemplateEn("han character", POS.han_character);
    public static final POSType hanzi = new POSTemplateEn("hanzi", POS.hanzi);
    public static final POSType hanja = new POSTemplateEn("hanja", POS.hanja);
    public static final POSType proverb = new POSTemplateEn("proverb", POS.proverb);
    public static final POSType expression = new POSTemplateEn("expression", POS.expression);
    public static final POSType possessive_adjective = new POSTemplateEn("possessive_adjective", POS.possessive_adjective);
    public static final POSType postposition = new POSTemplateEn("postposition", POS.postposition);
    public static final POSType gerund = new POSTemplateEn("gerund", POS.gerund);

    public static final POSType pronominal_adverb = new POSTemplateEn("pronominal adverb", POS.pronominal_adverb);
    public static final POSType adnominal = new POSTemplateEn("adnominal", POS.adnominal);
    public static final POSType root = new POSTemplateEn("root", POS.root);
    public static final POSType pinyin_syllable = new POSTemplateEn("pinyin syllable", POS.pinyin_syllable);
    public static final POSType syllable = new POSTemplateEn("syllable", POS.syllable);
    public static final POSType hiragana_character = new POSTemplateEn("hiragana character", POS.hiragana_character);
    public static final POSType katakana_character = new POSTemplateEn("katakana character", POS.katakana_character);
    public static final POSType jyutping_syllable = new POSTemplateEn("jyutping syllable", POS.jyutping_syllable);
    public static final POSType gismu = new POSTemplateEn("gismu", POS.gismu);
    public static final POSType lujvo = new POSTemplateEn("lujvo", POS.lujvo);
    public static final POSType classifier = new POSTemplateEn("classifier", POS.classifier);
    public static final POSType predicative = new POSTemplateEn("predicative", POS.predicative);
    public static final POSType measure_word = new POSTemplateEn("measure word", POS.measure_word);
    public static final POSType correlative = new POSTemplateEn("correlative", POS.correlative);
    public static final POSType preverb = new POSTemplateEn("preverb", POS.preverb);
    public static final POSType prenoun = new POSTemplateEn("prenoun", POS.prenoun);
    public static final POSType noun_stem = new POSTemplateEn("noun stem", POS.noun_stem);
    public static final POSType noun_class = new POSTemplateEn("noun class", POS.noun_class);
    public static final POSType combined_kana_character = new POSTemplateEn("combined-kana character", POS.combined_kana_character);


    // public static final POSType  = new POSTemplateEn("", POS.);


    // Non-standard, deprecated headers
    



    
}
