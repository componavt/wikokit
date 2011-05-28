/* POSTypeRu.java - Names of POS templates in Russian Wiktionary.
 * 
 * Copyright (c) 2008-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.multi.ru;

import wikt.constant.POSType;
import wikt.constant.POS;

import java.util.Map;
import java.util.HashMap;

/** Names of POS templates in Russian Wiktionary.
 * 
 * See http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A7%D0%B0%D1%81%D1%82%D0%B8_%D1%80%D0%B5%D1%87%D0%B8
 *     http://ru.wiktionary.org/wiki/Викисловарь:Части речи
 *
 *     Категория:Шаблоны словоизменений
 */
public class POSTemplatesRu extends POSType {

    /** POS name encountered in the Wiktionary, e.g.: {{acronym}} or Acronym, 
     * since there are ==={{acronym}}=== and ===Acronym===
     */
    private final String name_in_text;
    
    /** POS name in Russian, e.g. "Акроним" for "acronym" */
    //private final String native_name; // todo ... HashMap type -> Russian POS name
    
    /** POS */
    private final POS type; 
    
    //private static Map<String, String>  text2name = new HashMap<String, String>();
    private static Map<String, POS> name_in_text2type = new HashMap<String, POS>();
    
    /** Initialization for POSTypeEn, POSTypeRu, etc. */
    private POSTemplatesRu(String name_in_text, POS type) {
        this.name_in_text   = name_in_text;
        this.type           = type;         // english.english;
        name_in_text2type.put(name_in_text, type); // english.english);
    }
    
    public String getName() { return type.toString(); }
    
    /** Checks weather exists the part of speech by its abbreviation 'code'. */
    public static boolean has(String code) {
        return name_in_text2type.containsKey(code);
    }
    
    /** Gets part of speech by its abbreviation */
    public static POS get(String code) {
        return name_in_text2type.get(code);
    }
    
    
    
    
    // The classical parts of speech are:
    
    // ===Морфологические и синтаксические свойства===
    // {{СущМужНеодуш1c(1)
    // {{СущЖенНеодуш8a
    // Существительное, ...
    // public static final POSLocal noun = new POSRu(, POS.noun);
    public static final POSType noun    = new POSTemplatesRu("сущ",      POS.noun);
    public static final POSType noun_old= new POSTemplatesRu("падежи",   POS.noun);  // "существительное",

    // ===Морфологические и синтаксические свойства===
    // {{парадигма-рус
    // |шаблон=Гл11b/c
    //
    // {{Гл1a
    public static final POSType verb        = new POSTemplatesRu("гл",       POS.verb);  // "глагол",
    public static final POSType verb_old_ru = new POSTemplatesRu("глагол",   POS.verb);
    
    // {{adv-ru|
    // Наречие, неизменяемое.
    public static final POSType adverb_template     = new POSTemplatesRu("adv",      POS.adverb); // "наречие", adv ru, adv-ru
    public static final POSType adverb_word         = new POSTemplatesRu("наречие",  POS.adverb); // "наречие",
    
    // {{прил en|round|слоги=round}}
    public static final POSType adjective           = new POSTemplatesRu("прил",     POS.adjective); // "прилагательное"
    public static final POSType adjective_old_en    = new POSTemplatesRu("adjective",POS.adjective);

    // {{мест ru 6*b
    public static final POSType pronoun             = new POSTemplatesRu("мест",     POS.pronoun);
    public static final POSType pronoun_addon       = new POSTemplatesRu("мс",       POS.pronoun);

    public static final POSType conjunction         = new POSTemplatesRu("conj",     POS.conjunction); // союз
    public static final POSType interjection        = new POSTemplatesRu("interj",   POS.interjection); // междометие
    public static final POSType preposition         = new POSTemplatesRu("prep",     POS.preposition); // Предлог


    // Additional commonly used grammatical headers are:
    // proper_noun ?

    public static final POSType article             = new POSTemplatesRu("art",      POS.article); // артикль
    public static final POSType article2            = new POSTemplatesRu("article",  POS.article);

    public static final POSType prefix              = new POSTemplatesRu("prefix",     POS.prefix); // приставка
    public static final POSType suffix              = new POSTemplatesRu("suffix",     POS.suffix); // суффикс
    // phrase - ok
    // idiom - in phrase
    // prepositional_phrase - may be in phrase

    // debated POS level 3 headers
    public static final POSType numeral             = new POSTemplatesRu("числ",     POS.numeral); // числительное

    // other descriptors that identify the usage of the entry, but which are not (strictly speaking) parts of speech:
    // acronym ?
    public static final POSType abbreviation        = new POSTemplatesRu("abbrev",   POS.abbreviation); // Аббревиатура
    // ! "init" in ruwikt (Первая часть сложных слов) <> "initialism" in enwikt
    

    // other headers in use
    public static final POSType particle1           = new POSTemplatesRu("part",     POS.particle); // частица, part ru, part-ru
    public static final POSType particle3           = new POSTemplatesRu("particle", POS.particle);

    // only in Russian Wiktionary (yet)
    public static final POSType verb_interjection   = new POSTemplatesRu("interj1", POS.verb_interjection);// interj1 - глагольно-междометное слово - verb-interjection word
    public static final POSType parenthesis         = new POSTemplatesRu("intro", POS.parenthesis);
    public static final POSType prefix_of_compound  = new POSTemplatesRu("init", POS.prefix_of_compound);// первая часть сложных слов
}
