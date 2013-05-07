/* Label.java - contexual information for definitions, or Synonyms,
 *                     or Translations.
 * 
 * Copyright (c) 2008-2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.en.name;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelCategory;
import static wikokit.base.wikt.constant.LabelCategory.empty;

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
 * @see http://en.wiktionary.org/wiki/Template_talk:context
 * @see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 * @see http://en.wiktionary.org/wiki/Template:context
 */
public class LabelEn extends Label {       
    
    /** Category associated with this label. */
    private final LabelCategory category;
    
    //private static Map<String, String> short_name2name = new HashMap<String, String>();
    
    private static Map<LabelEn, LabelCategory> label2category = new HashMap<LabelEn, LabelCategory>();
    
    /** If there are more than one context label (synonyms,  short name label): <synonymic_label, source_main_unique_label> */
    //private static Map<String, LabelEn> multiple_synonym2label = new HashMap<String, LabelEn>();
    
    protected LabelEn(String short_name, String name, LabelCategory category) { 
        super(short_name, name);
        initLabel(this);
        
        if(null == category)
            System.out.println("Error in LabelEn.LabelEn(): category is empty! label="+short_name+"; name=\'"+name+"\'; category=\'"+category.toString()+"\'.");
        
        this.category   = category; 
        label2category. put(this, category);
    }
    
    /** Gets English Wiktionary context label associated with this label. 
     * This function is needed for compatibility with other child class (LabelLocal.java) of Label.java */
    protected Label getLinkedLabelEn() {
        return this;
    }
    
    // Context labels without categories (empty category) 43 Krizhanovsky
// context needed
    public static final Label E_number = new LabelEn("E number", "food manufacture", LabelCategory.empty);
    public static final Label element_symbol = new LabelEn("element symbol", "chemistry", LabelCategory.empty); 
//  item // it's not a context label
    public static final Label Rumantsch_Grischun = new LabelEn("Rumantsch Grischun", "Rumantsch Grischun", LabelCategory.empty);

     
    // grammatical 67-2 Krizhanovsky
    // //////////////////////////
    public static final Label ambitransitive = new LabelEn("transitive, intransitive", "transitive, intransitive", LabelCategory.grammatical);
    public static final Label animate = new LabelEn("animate", "animate", LabelCategory.grammatical);
    public static final Label attributive = new LabelEn("attributive", "attributive", LabelCategory.grammatical); //определение
    public static final Label attributively = new LabelEn("attributively", "attributively", LabelCategory.grammatical);
    public static final Label auxiliary = new LabelEn("auxiliary", "auxiliary", LabelCategory.grammatical); //вспомогательный глагол
    public static final Label benedictive = new LabelEn("benedictive", "benedictive", LabelCategory.grammatical);
    public static final Label by_ellipsis = new LabelEn("by ellipsis", "by ellipsis", LabelCategory.grammatical); //под многоточием
    public static final Label cardinal = new LabelEn("cardinal", "cardinal", LabelCategory.grammatical); // количественное числительное
    public static final Label causative = new LabelEn("causative", "causative", LabelCategory.grammatical); //понудительный залог
    public static final Label comparable = new LabelEn("comparable", "comparable", LabelCategory.grammatical); //сравнимый
    public static final Label copulative = new LabelEn("copulative", "copulative", LabelCategory.grammatical); //соединительный союз
    public static final Label countable = new LabelEn("countable", "countable", LabelCategory.grammatical);
    public static final Label defective = new LabelEn("defective", "defective", LabelCategory.grammatical); // недостаточный глагол
    public static final Label definite = new LabelEn("definite", "definite", LabelCategory.grammatical); //определенный
    public static final Label demonstrative = new LabelEn("demonstrative", "demonstrative", LabelCategory.grammatical); //указательное  местоимение
    public static final Label deponent = new LabelEn("deponent", "deponent", LabelCategory.grammatical); //отложительный глагол
    public static final Label desiderative = new LabelEn("desiderative", "desiderative", LabelCategory.grammatical);
    public static final Label determiner = new LabelEn("determiner", "determiner", LabelCategory.grammatical); //определяющее слово
    public static final Label directional = new LabelEn("directional", "directional", LabelCategory.grammatical); // направленный
    public static final Label ditransitive = new LabelEn("ditransitive", "ditransitive", LabelCategory.grammatical);
    public static final Label emphatic = new LabelEn("emphatic", "emphatic", LabelCategory.grammatical);
    public static final Label ergative = new LabelEn("ergative", "ergative", LabelCategory.grammatical); //эргатив
    public static final Label fractional = new LabelEn("fractional", "fractional", LabelCategory.grammatical); //дробный
    public static final Label frequentative = new LabelEn("frequentative", "frequentative", LabelCategory.grammatical); // многократный
    public static final Label idiomatic = new LabelEn("idiomatic", "idiomatic", LabelCategory.grammatical); // идиоматический
    public static final Label imperative = new LabelEn("imperative", "imperative", LabelCategory.grammatical); // повелительное наклонение
    public static final Label impersonal = new LabelEn("impersonal", "impersonal", LabelCategory.grammatical); //безличный
    public static final Label in_combination = new LabelEn("in combination", "in combination", LabelCategory.grammatical); //в сочетании
    public static final Label in_dual = new LabelEn("in dual", "in dual", LabelCategory.grammatical); 
    public static final Label in_mediopassive = new LabelEn("in mediopassive", "in mediopassive", LabelCategory.grammatical);
    public static final Label in_the_plural = new LabelEn("in the plural", "in the plural", LabelCategory.grammatical);
    public static final Label inanimate = new LabelEn("inanimate", "inanimate", LabelCategory.grammatical);
    public static final Label indecl = new LabelEn("indecl", "indeclinable", LabelCategory.grammatical);
    public static final Label indefinite = new LabelEn("indefinite", "indefinite", LabelCategory.grammatical); // неопределенный
    public static final Label injunctive = new LabelEn("injunctive", "injunctive", LabelCategory.grammatical); //инъюнктив
    public static final Label intensive = new LabelEn("intensive", "intensive", LabelCategory.grammatical); // усилительный
    public static final Label interrogative = new LabelEn("interrogative", "interrogative", LabelCategory.grammatical); //вопросительный
    public static final Label intransitive = new LabelEn("intransitive", "intransitive", LabelCategory.grammatical);
    public static final Label la_proper_noun_indecl = new LabelEn("la-proper noun-indecl", "indeclinable", LabelCategory.grammatical);
    public static final Label momentane = new LabelEn("momentane", "momentane", LabelCategory.grammatical);
    public static final Label negative = new LabelEn("negative", "negative", LabelCategory.grammatical);
 
    public static final Label not_comparable = new LabelEn("not comparable", "not comparable", LabelCategory.grammatical); //несравнимый 
    public static final Label notcomp = LabelEn.addNonUniqueShortName(not_comparable, "notcomp");

     public static final Label of_a_person = new LabelEn("of a person", "of a person", LabelCategory.grammatical);
    public static final Label onomatopoeia = new LabelEn("onomatopoeia", "onomatopoeia", LabelCategory.grammatical); //звукоподражание
    public static final Label ordinal = new LabelEn("ordinal", "ordinal", LabelCategory.grammatical); //порядковое числительное
    public static final Label passive = new LabelEn("passive", "passive", LabelCategory.grammatical);
    public static final Label personal = new LabelEn("personal", "personal", LabelCategory.grammatical);
    
    public static final Label plurale_tantum = new LabelEn("plurale tantum", "plural only", LabelCategory.grammatical);
    public static final Label pluralonly = LabelEn.addNonUniqueShortName(plurale_tantum, "pluralonly");

    public static final Label possessive = new LabelEn("possessive", "possessive", LabelCategory.grammatical); //притяжательный падеж
    public static final Label possessive_pronoun = LabelEn.addNonUniqueShortName(possessive, "possessive pronoun");

    public static final Label postpositive = new LabelEn("postpositive", "postpositive", LabelCategory.grammatical); //постпозитивный
    public static final Label predicate = new LabelEn("predicate", "predicate", LabelCategory.grammatical);
    public static final Label productive = new LabelEn("productive", "productive", LabelCategory.grammatical);
    public static final Label pronominal = new LabelEn("pronominal", "pronominal", LabelCategory.grammatical); //местоименный
    public static final Label reflexive = new LabelEn("reflexive", "reflexive", LabelCategory.grammatical); //возвратный 
    public static final Label relative = new LabelEn("relative", "relative", LabelCategory.grammatical); //относительное местоимение
    public static final Label rhetorical_question = new LabelEn("rhetorical question", "rhetorical question", LabelCategory.grammatical); //риторический вопрос
    public static final Label set_phrase = new LabelEn("set phrase", "set phrase", LabelCategory.grammatical); //словосочетание
    public static final Label simile = new LabelEn("simile", "simile", LabelCategory.grammatical); // сравнение
    
    public static final Label singulare_tantum = new LabelEn("singulare tantum", "singular only", LabelCategory.grammatical);
    public static final Label singularonly = LabelEn.addNonUniqueShortName(singulare_tantum, "singularonly");
 
    public static final Label transitive = new LabelEn("transitive", "transitive", LabelCategory.grammatical);
    public static final Label uncountable = new LabelEn("uncountable", "uncountable", LabelCategory.grammatical);
        
    // period 15+1 Krizhanovsky
    // //////////////////////////
    public static final Label archaic = new LabelEn("archaic", "archaic", LabelCategory.period);
    public static final Label archaic_verb_form = LabelEn.addNonUniqueShortName(archaic, "archaic-verb-form");
    
    public static final Label dated = new LabelEn("dated", "dated", LabelCategory.period);
    public static final Label dated_sense = LabelEn.addNonUniqueShortName(dated, "dated_sense");
    
    public static final Label Ecclesiastical_Latin = new LabelEn("Ecclesiastical Latin", "Ecclesiastical Latin", LabelCategory.period);
    public static final Label Church_Latin = LabelEn.addNonUniqueShortName(Ecclesiastical_Latin, "Church Latin");
    
    public static final Label historical = new LabelEn("historical", "historical", LabelCategory.period);
    public static final Label Late_Latin = new LabelEn("Late Latin", "Late Latin", LabelCategory.period);
    public static final Label Medieval_Latin = new LabelEn("Medieval Latin", "Medieval Latin", LabelCategory.period);
    public static final Label neologism = new LabelEn("neologism", "neologism", LabelCategory.period);
    public static final Label New_Latin = new LabelEn("New Latin", "New Latin", LabelCategory.period);
    public static final Label no_longer_productive = new LabelEn("no longer productive", "no longer productive", LabelCategory.period);
    
    public static final Label obsolete = new LabelEn("obsolete", "obsolete", LabelCategory.period);
    public static final Label obsolete_term = LabelEn.addNonUniqueShortName(obsolete, "obsolete term");
    public static final Label ru_pre_reform = LabelEn.addNonUniqueShortName(obsolete, "ru-pre-reform");

    public static final Label Old_Latin = new LabelEn("Old Latin", "Old Latin", LabelCategory.period);
   
    // qualifier 22-2 Krizhanovsky
    // //////////////////////////
    public static final Label chiefly = new LabelEn("chiefly", "chiefly", LabelCategory.qualifier); //главным образом, особенно, в основном
    //public static final Label context = new LabelEn("context", "context", LabelCategory.qualifier); // meta context label will be treated in a special way. http://en.wiktionary.org/wiki/Template:context
    public static final Label excluding = new LabelEn("excluding", "excluding", LabelCategory.qualifier);
    public static final Label extremely = new LabelEn("extremely", "extremely", LabelCategory.qualifier);
    public static final Label frequently = new LabelEn("frequently", "frequently", LabelCategory.qualifier);
    public static final Label humorously = new LabelEn("humorously", "humorously", LabelCategory.qualifier);
    public static final Label literally = new LabelEn("literally", "literally", LabelCategory.qualifier);
    public static final Label markedly = new LabelEn("markedly", "markedly", LabelCategory.qualifier); //явно
    public static final Label mildly = new LabelEn("mildly", "mildly", LabelCategory.qualifier); //мягко
    public static final Label nowadays = new LabelEn("nowadays", "now", LabelCategory.qualifier);
    public static final Label of_a = new LabelEn("of a", "of a", LabelCategory.qualifier);
    public static final Label of_an = new LabelEn("of an", "of an", LabelCategory.qualifier);
    public static final Label often = new LabelEn("often", "often", LabelCategory.qualifier);
    public static final Label originally = new LabelEn("originally", "originally", LabelCategory.qualifier);
    public static final Label slightly = new LabelEn("slightly", "slightly", LabelCategory.qualifier);
    public static final Label sometimes = new LabelEn("sometimes", "sometimes", LabelCategory.qualifier);
    public static final Label somewhat = new LabelEn("somewhat", "somewhat", LabelCategory.qualifier);
    public static final Label strongly = new LabelEn("strongly", "strongly", LabelCategory.qualifier);
    public static final Label usually = new LabelEn("usually", "usually", LabelCategory.qualifier);
    public static final Label very = new LabelEn("very", "very", LabelCategory.qualifier);

    // regional 225 Chirkov
    // //////////////////////////
    public static final Label AU = new LabelEn("AU",     "Australia",    LabelCategory.regional);
    public static final Label Balkar = new LabelEn("Balkar", "Balkar", LabelCategory.regional);
    public static final Label Chinglish = new LabelEn("Chinglish", "Chinglish", LabelCategory.regional);
    public static final Label Ekavian = new LabelEn("Ekavian", "Ekavian", LabelCategory.regional);
    public static final Label Helsinki_slang = new LabelEn("Helsinki slang", "Helsinki slang", LabelCategory.regional);
    public static final Label Hoisanese = new LabelEn("Hoisanese", "Hoisanese", LabelCategory.regional);
    public static final Label Ijekavian = new LabelEn("Ijekavian", "Ijekavian", LabelCategory.regional);
    public static final Label Karabakh = new LabelEn("Karabakh", "Karabakh", LabelCategory.regional);
    public static final Label Kromanti = new LabelEn("Kromanti", "Kromanti spirit possession language", LabelCategory.regional);
    public static final Label Polari = new LabelEn("Polari", "Polari", LabelCategory.regional);
    public static final Label southern_US = new LabelEn("southern US", "southern US", LabelCategory.regional);
    public static final Label Tigranakert = new LabelEn("Tigranakert", "Tigranakert", LabelCategory.regional);
    
    
    // usage 73-3+1 Krizhanovsky
    // //////////////////////////
    public static final Label ad_slang = new LabelEn("ad slang",  "advertising slang", LabelCategory.usage);
    public static final Label Australian_slang = new LabelEn("Australian slang",  "Australian slang", LabelCategory.usage);
    public static final Label avoidance = new LabelEn("avoidance",  "avoidance term", LabelCategory.usage);
    public static final Label British_slang = new LabelEn("British slang",  "British slang", LabelCategory.usage);
    public static final Label buzzword = new LabelEn("buzzword",  "buzzword", LabelCategory.usage);
    public static final Label by_extension = new LabelEn("by extension",  "by extension", LabelCategory.usage);
    public static final Label cant = new LabelEn("cant",  "cant", LabelCategory.usage); // косяк, жаргон
    public static final Label capitalized = new LabelEn("capitalized",  "capitalized", LabelCategory.usage); // с большой буквы?
    public static final Label childish = new LabelEn("childish",  "childish", LabelCategory.usage);
    public static final Label chu_Nom = new LabelEn("chu Nom",  "Vietnamese chữ Nôm", LabelCategory.usage);
    public static final Label Classic_1811 = new LabelEn("Classic 1811 Dictionary of the Vulgar Tongue",  "obsolete, slang", LabelCategory.usage);
    public static final Label Cockney_rhyming_slang = new LabelEn("Cockney rhyming slang",  "Cockney rhyming slang", LabelCategory.usage);
    
    public static final Label colloquial = new LabelEn("colloquial",  "colloquial", LabelCategory.usage);
    public static final Label colloquial_um = LabelEn.addNonUniqueShortName(colloquial, "colloquial-um");
    public static final Label colloquial_un = LabelEn.addNonUniqueShortName(colloquial, "colloquial-un");
    
    public static final Label derogatory = new LabelEn("derogatory",  "derogatory", LabelCategory.usage);
    
    public static final Label dialect = new LabelEn("dialect",  "dialect", LabelCategory.usage);
    public static final Label dialectal = LabelEn.addNonUniqueShortName(dialect, "dialectal");
    public static final Label dialectal_n = LabelEn.addNonUniqueShortName(dialect, "dialectal_n");
    public static final Label dialects = LabelEn.addNonUniqueShortName(dialect, "dialects");
    
    public static final Label dismissal = new LabelEn("dismissal",  "dismissal", LabelCategory.usage);
    public static final Label endearing = new LabelEn("endearing",  "endearing", LabelCategory.usage);
    public static final Label ethnic_slur = new LabelEn("ethnic slur",  "ethnic slur", LabelCategory.usage);
    public static final Label euphemistic = new LabelEn("euphemistic",  "euphemistic", LabelCategory.usage);
    public static final Label familiar = new LabelEn("familiar",  "familiar", LabelCategory.usage);
    public static final Label fandom_slang = new LabelEn("fandom slang",  "fandom slang", LabelCategory.usage);
    
    public static final Label figuratively = new LabelEn("figuratively", "figuratively", LabelCategory.usage);
    // la-conj-form-gloss/iacio/context6 -> figuratively // strange context label which will skipped by parser
    
    public static final Label formal = new LabelEn("formal",  "formal", LabelCategory.usage);
    public static final Label gay_slang = new LabelEn("gay slang",  "gay slang", LabelCategory.usage);
    public static final Label humorous = new LabelEn("humorous",  "humorous", LabelCategory.usage);
    public static final Label hyperbolic = new LabelEn("hyperbolic",  "hyperbolic", LabelCategory.usage);
    public static final Label hypercorrect = new LabelEn("hypercorrect",  "hypercorrect", LabelCategory.usage);
    public static final Label hyperforeign = new LabelEn("hyperforeign",  "hyperforeign", LabelCategory.usage);
    public static final Label informal = new LabelEn("informal",  "informal", LabelCategory.usage);
    public static final Label Internet_slang = new LabelEn("Internet slang",  "Internet slang", LabelCategory.usage);
    public static final Label IRC = new LabelEn("IRC",  "IRC", LabelCategory.usage);
    public static final Label jargon = new LabelEn("jargon",  "jargon", LabelCategory.usage);
    public static final Label leet = new LabelEn("leet",  "leetspeak", LabelCategory.usage);
    public static final Label literary = new LabelEn("literary",  "literary", LabelCategory.usage);
    public static final Label loosely = new LabelEn("loosely",  "loosely", LabelCategory.usage);
    public static final Label Lubunyaca = new LabelEn("Lubunyaca",  "Lubunyaca", LabelCategory.usage);
    public static final Label medical_slang = new LabelEn("medical slang",  "medical slang", LabelCategory.usage);
    public static final Label metonymy = new LabelEn("metonymy",  "metonymically", LabelCategory.usage);
    public static final Label miguxês = new LabelEn("miguxês",  "miguxês", LabelCategory.usage);
    public static final Label military_slang = new LabelEn("military slang",  "military slang", LabelCategory.usage);
    public static final Label nonce = new LabelEn("nonce",  "nonce word", LabelCategory.usage);
    public static final Label nonstandard = new LabelEn("nonstandard",  "nonstandard", LabelCategory.usage);
    public static final Label offensive = new LabelEn("offensive",  "offensive", LabelCategory.usage); // оскорбительный, агрессивный
    public static final Label pejorative = new LabelEn("pejorative", "pejorative", LabelCategory.usage);
    public static final Label poetic = new LabelEn("poetic", "poetic", LabelCategory.usage);
    public static final Label polite = new LabelEn("polite",  "polite", LabelCategory.usage);  // вежливый
    public static final Label politically_correct = new LabelEn("politically correct",  "politically correct", LabelCategory.usage);
    public static final Label proscribed = new LabelEn("proscribed",  "proscribed", LabelCategory.usage);
    public static final Label radio_slang = new LabelEn("radio slang",  "radio slang", LabelCategory.usage);
    
    public static final Label rare = new LabelEn("rare",  "rare", LabelCategory.usage);
    public static final Label rarely = LabelEn.addNonUniqueShortName(rare, "rarely");
    public static final Label rare_sense = LabelEn.addNonUniqueShortName(rare, "rare sense");
    public static final Label rare_term = LabelEn.addNonUniqueShortName(rare, "rare term");
    
    public static final Label retronym = new LabelEn("retronym",  "retronym", LabelCategory.usage);
    public static final Label rfd_redundant = new LabelEn("rfd-redundant",  "rfd-redundant", LabelCategory.usage);
    public static final Label rfv_sense = new LabelEn("rfv-sense",  "rfv-sense", LabelCategory.usage);
    public static final Label sarcastic = new LabelEn("sarcastic",  "sarcastic", LabelCategory.usage);
    public static final Label slang = new LabelEn("slang",  "slang", LabelCategory.usage);
    public static final Label text_messaging = new LabelEn("text messaging",  "text messaging", LabelCategory.usage);
    public static final Label tiopês = new LabelEn("tiopês",  "tiopês", LabelCategory.usage);
    public static final Label trademark = new LabelEn("trademark",  "trademark", LabelCategory.usage);
    
    public static final Label transferred_sense = new LabelEn("transferred sense",  "transferred sense", LabelCategory.usage); // В ПЕРЕНОСНОМ СМЫСЛЕ
    public static final Label transferred_senses = LabelEn.addNonUniqueShortName(transferred_sense, "transferred_senses");

    public static final Label uncommon = new LabelEn("uncommon",  "uncommon", LabelCategory.usage); // РЕДКИЙ
    public static final Label US_slang = new LabelEn("US slang",  "US slang", LabelCategory.usage);
    public static final Label vulgar = new LabelEn("vulgar",  "vulgar", LabelCategory.usage);
    
    // **************************
    // topical 376 Ann
    // **************************
    public static final Label honorific = new LabelEn("honorific", "honorific", LabelCategory.topical);
    public static final Label Wiktionary_and_WMF_jargon = new LabelEn("Wiktionary and WMF jargon", "Wiktionary and WMF jargon", LabelCategory.topical);
    
    // Sasha First 186 all Subcategories in http://en.wiktionary.org/wiki/Category:Topical_context_labels
    
    // computing Sasha First
    // //////////////////////////
    public static final Label programming = new LabelEn("programming", "programming", LabelCategory.computing);
    
    // games Sasha First
    // //////////////////////////
    public static final Label chess = new LabelEn("chess", "chess", LabelCategory.games);
    public static final Label dominoes = new LabelEn("dominoes", "dominoes", LabelCategory.games);
    public static final Label Tetris = new LabelEn("Tetris", "Tetris", LabelCategory.games);

    // mathematics Sasha First
    // //////////////////////////
    public static final Label arithmetic = new LabelEn("arithmetic", "arithmetic", LabelCategory.mathematics);
    
    // music Sasha First
    // //////////////////////////
    public static final Label music = new LabelEn("music", "music", LabelCategory.music);

    // mythology Sasha First
    // //////////////////////////
    public static final Label mythology = new LabelEn("mythology", "mythology", LabelCategory.mythology);

    // religion Sasha First
    // //////////////////////////
    public static final Label religion = new LabelEn("religion", "religion", LabelCategory.religion);
    
    // science Sasha First
    // //////////////////////////
    public static final Label astronomy = new LabelEn("astronomy" ,"astronomy", LabelCategory.science);
    // synonyms:  {{math}} and {{maths}} -> (mathematics)
    
    // sports Sasha First
    // //////////////////////////
    public static final Label baseball = new LabelEn("baseball" ,"baseball", LabelCategory.sports);
    public static final Label basketball = new LabelEn("basketball" ,"basketball", LabelCategory.sports);
    public static final Label billiards = new LabelEn("billiards" ,"billiards", LabelCategory.sports);
    public static final Label croquet = new LabelEn("croquet" ,"croquet", LabelCategory.sports); // only in ruwikt
    public static final Label football = new LabelEn("football" ,"football", LabelCategory.sports); // only in ruwikt
    public static final Label gymnastics = new LabelEn("gymnastics" ,"gymnastics", LabelCategory.sports);
    
    public static final Label hockey = new LabelEn("hockey" ,"hockey", LabelCategory.sports);
    public static final Label ice_hockey = LabelEn.addNonUniqueShortName(hockey, "ice hockey");
    public static final Label field_hockey = LabelEn.addNonUniqueShortName(hockey, "field hockey");
    
    public static final Label rugby = new LabelEn("rugby" ,"rugby", LabelCategory.sports);
    public static final Label sports = new LabelEn("sports" ,"sports", LabelCategory.sports);
    public static final Label tennis = new LabelEn("tennis" ,"tennis", LabelCategory.sports);
    public static final Label volleyball = new LabelEn("volleyball" ,"volleyball", LabelCategory.sports);
    
    
    // DEBUG: should be one error for each code line below:
    // DDDDDDDDDDDDDDDDDDDDDDDDDD
    //public static final Label archaic_short_name_duplication = new LabelEn("archaic",  "archaic full name (duplication of short name)", LabelCategory.period);
    //public static final Label archaic_full_name_duplication = new LabelEn("archaic short name (duplication of full name)", "archaic", LabelCategory.period);
    //public static final Label dated_sense_again = LabelEn.addNonUniqueShortName(dated, "dated_sense");
    // DDDDDDDDDDDDDDDDDDeo DEBUG
}
