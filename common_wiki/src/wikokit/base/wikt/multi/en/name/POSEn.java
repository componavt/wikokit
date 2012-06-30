/* POSEn.java - names of parts of speech in English.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

/** Names of parts of speech in English and the links to the POS objects.
 * 
 * @attention: initialize class before using, e.g. "POSLocal _ = POSEn.noun;"
 */
package wikokit.base.wikt.multi.en.name;

import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.constant.POSLocal;

/** 
 * Abbreviations:
 * @see http://www.oed.com/public/abbreviations The Oxford English Dictionary: List of Abbreviations
 * @see http://www.etymonline.com/abbr.php Online etymology dictionary
 */
public class POSEn extends POSLocal {

    protected POSEn(String _name, String _short_name, POS _pos) {
        super(_name, _short_name, _pos);
    }
    
    public static final POSLocal    
            unknown,
            noun,
            verb,
            adverb,
            adjective,
            pronoun,
            
            conjunction, interjection, preposition,
            
            proper_noun, article, prefix, suffix,
            
            phrase, idiom, prepositional_phrase, 
            
            numeral,
            
            acronym, abbreviation, initialism, contraction,
            
            symbol, letter,
            
            particle, participle, determiner, infix, 
            interfix, affix, circumfix, counter, predicative,
            
            kanji, kanji_reading, hanja_reading, hiragana_letter, katakana_letter,
            pinyin, han_character, hanzi, hanja,
            
            proverb, expression, possessive_adjective, 
            postposition, gerund, pronominal_adverb, adnominal, root, 
            pinyin_syllable, syllable, hiragana_character, katakana_character, jyutping_syllable, 
            
            lujvo, brivla, classifier, measure_word, correlative, 
            preverb, prenoun, noun_stem, noun_class, combined_kana_character;

    
    static {
    unknown = new POSEn("Unknown", "unkn.", POS.unknown);

    // The classical parts of speech are:
    noun = new POSEn("Noun", "n.", POS.noun);
    verb = new POSEn("Verb", "v.", POS.verb);
    adverb = new POSEn("Adverb", "adv.", POS.adverb);
    adjective = new POSEn("Adjective", "adj.", POS.adjective);
    pronoun = new POSEn("Pronoun", "pron.", POS.pronoun);
    
    conjunction = new POSEn("Conjunction", "conj.", POS.conjunction);
    interjection = new POSEn("Interjection", "int.", POS.interjection);
    preposition = new POSEn("Preposition", "prep.", POS.preposition);
    
    // Additional commonly used grammatical headers are:
    proper_noun = new POSEn("Proper noun", "proper n.", POS.proper_noun);
    article = new POSEn("Article", "art.", POS.article);
    prefix = new POSEn("Prefix", "pref.", POS.prefix);
    suffix = new POSEn("Suffix", "suff.", POS.suffix);
    
    phrase = new POSEn("Phrase", "phr.", POS.phrase);
    idiom = new POSEn("Idiom", "idiom", POS.idiom);
    prepositional_phrase = new POSEn("Prepositional phrase", "prep. phr.", POS.prepositional_phrase);
    
    
    // debated POS level 3 headers
    numeral = new POSEn("Numeral", "numeral", POS.numeral);
    
    // other descriptors that identify the usage of the entry, but which are not (strictly speaking) parts of speech:
    acronym = new POSEn("Acronym", "acronym", POS.acronym);

    abbreviation = new POSEn("Abbreviation", "abbrev.", POS.abbreviation);
    initialism = new POSEn("Initialism", "initialism", POS.initialism);
    contraction = new POSEn("Contraction", "contr.", POS.contraction);
    
    

    symbol = new POSEn("Symbol", "symbol", POS.symbol);
    letter = new POSEn("Letter", "Let.", POS.letter);
    
    // other headers in use
    particle = new POSEn("Particle", "particle", POS.particle);
    participle = new POSEn("Participle", "pple.", POS.participle);
    determiner = new POSEn("Determiner", "determiner", POS.determiner);
    infix = new POSEn("Infix", "infix", POS.infix);
    interfix = new POSEn("Interfix", "interfix", POS.interfix);
    affix = new POSEn("Affix", "affix", POS.affix);
    circumfix = new POSEn("Circumfix", "circumfix", POS.circumfix);
    counter = new POSEn("Counter", "counter", POS.counter);
    predicative = new POSEn("Predicative", "predic.", POS.predicative);
    
    kanji = new POSEn("Kanji", "kanji", POS.kanji);
    kanji_reading = new POSEn("Kanji reading", "kanji reading", POS.kanji_reading);
    hanja_reading = new POSEn("Hanja reading", "hanja reading", POS.hanja_reading);
    hiragana_letter = new POSEn("Hiragana letter", "hiragana letter", POS.hiragana_letter);
    katakana_letter = new POSEn("Katakana letter", "katakana letter", POS.katakana_letter);
    
    pinyin = new POSEn("Pinyin", "pinyin", POS.pinyin);
    han_character = new POSEn("Han character", "han character", POS.han_character);
    hanzi = new POSEn("Hanzi", "hanzi", POS.hanzi);
    hanja = new POSEn("Hanja", "hanja", POS.hanja);
    
    proverb = new POSEn("Proverb", "Prov.", POS.proverb);
    expression = new POSEn("Expression", "expression", POS.expression);
    possessive_adjective = new POSEn("Possessive adjective", "poss. adj.", POS.possessive_adjective);
    postposition = new POSEn("Postposition", "postposition", POS.postposition);
    gerund = new POSEn("Gerund", "ger.", POS.gerund);
    pronominal_adverb = new POSEn("Pronominal adverb", "pronominal adv.", POS.pronominal_adverb);
    adnominal = new POSEn("Adnominal", "adnominal", POS.adnominal);
    root = new POSEn("Root", "root", POS.root);
    
    pinyin_syllable = new POSEn("Pinyin syllable", "pinyin syll.", POS.pinyin_syllable);
    syllable = new POSEn("Syllable", "syll.", POS.syllable);
    hiragana_character = new POSEn("Hiragana character", "hiragana char.", POS.hiragana_character);
    katakana_character = new POSEn("Katakana character", "katakana char.", POS.katakana_character);
    jyutping_syllable = new POSEn("Jyutping syllable", "jyutping syll.", POS.jyutping_syllable);
    
    lujvo = new POSEn("Lujvo", "lujvo", POS.lujvo);
    brivla = new POSEn("Brivla", "brivla", POS.brivla);
    classifier = new POSEn("Classifier", "classifier", POS.classifier);
    measure_word = new POSEn("Measure word", "measure wd.", POS.measure_word);
    correlative = new POSEn("Correlative", "correlative", POS.correlative);
    preverb = new POSEn("Preverb", "preverb", POS.preverb);
    prenoun = new POSEn("Prenoun", "prenoun", POS.prenoun);
    noun_stem = new POSEn("Noun stem", "n. stem", POS.noun_stem);
    noun_class = new POSEn("Noun class", "n. class", POS.noun_class);
    combined_kana_character = new POSEn("Combined-kana character", "combined-kana char.", POS.combined_kana_character);
    }


}
