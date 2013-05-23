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
    // public static final Label la_proper_noun_indecl = new LabelEn("la-proper noun-indecl", "indeclinable", LabelCategory.grammatical); it is not a context label in really
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
    public static final Label Acadia = new LabelEn("Acadia", "Acadian", LabelCategory.regional);
    public static final Label Africa = new LabelEn("Africa", "Africa",    LabelCategory.regional);
    public static final Label African_American_Vernacular_English = new LabelEn("African American Vernacular English",     "African American Vernacular",    LabelCategory.regional);
    public static final Label Alberta = new LabelEn("Alberta", "Alberta", LabelCategory.regional);
    public static final Label Algeria = new LabelEn("Algeria", "Algeria", LabelCategory.regional);
    public static final Label Alghero = new LabelEn("Alghero", "Alghero", LabelCategory.regional);
    public static final Label American_spelling = new LabelEn("American spelling", "American", LabelCategory.regional);
    public static final Label Anglo_Norman = new LabelEn("Anglo-Norman", "Anglo-Norman", LabelCategory.regional);
    public static final Label Angola = new LabelEn("Angola", "Angola", LabelCategory.regional);
    public static final Label Appalachian = new LabelEn("Appalachian", "Appalachian", LabelCategory.regional);
    public static final Label Argentina = new LabelEn("Argentina", "Argentina", LabelCategory.regional);
    public static final Label Atlantic_Canada = new LabelEn("Atlantic Canada", "Atlantic Canada", LabelCategory.regional);

    public static final Label Australia = new LabelEn("Australia", "Australia", LabelCategory.regional);
    public static final Label AU = LabelEn.addNonUniqueShortName(Australia, "Australia");

    public static final Label Austria = new LabelEn("Austria", "Austria", LabelCategory.regional);

    public static final Label Balearics = new LabelEn("Balearics", "Balearics", LabelCategory.regional);
    public static final Label Balhae = new LabelEn("Balhae", "Balhae", LabelCategory.regional);
    public static final Label Balkar = new LabelEn("Balkar", "Balkar", LabelCategory.regional);
    public static final Label Belgium = new LabelEn("Belgium", "Belgium", LabelCategory.regional);
    public static final Label Belize = new LabelEn("Belize", "Belize",  LabelCategory.regional);
    public static final Label Bolivia = new LabelEn("Bolivia", "Bolivia", LabelCategory.regional);
    public static final Label Border_Scots = new LabelEn("Border Scots", "Border Scots", LabelCategory.regional);
    public static final Label Bosnia = new LabelEn("Bosnia", "Bosnia", LabelCategory.regional);
    public static final Label Brabant = new LabelEn("Brabant", "Brabant", LabelCategory.regional);
    public static final Label Brazil = new LabelEn("Brazil", "Brazil", LabelCategory.regional);
    public static final Label Bristol = new LabelEn("Bristol", "Bristolian", LabelCategory.regional);

    public static final Label British = new LabelEn("British", "UK", LabelCategory.regional);
    public static final Label UK = LabelEn.addNonUniqueShortName(British, "British");

    public static final Label British_Columbia = new LabelEn("British Columbia", "British Columbia", LabelCategory.regional);
    public static final Label British_spelling = new LabelEn("British spelling", "British", LabelCategory.regional);
    public static final Label Brunei = new LabelEn("Brunei", "Brunei", LabelCategory.regional);

    public static final Label Canada = new LabelEn("Canada", "Canada", LabelCategory.regional);
    public static final Label Canadian_Prairies = new LabelEn("Canadian Prairies", "Canadian Prairies", LabelCategory.regional);
    public static final Label Caribbean = new LabelEn("Caribbean", "Caribbean", LabelCategory.regional);
    public static final Label Chakavian = new LabelEn("Chakavian", "Chakavian", LabelCategory.regional);
    public static final Label Channel_Islands = new LabelEn("Channel Islands", "Channel Islands", LabelCategory.regional);
    public static final Label Chile = new LabelEn("Chile", "Chile", LabelCategory.regional);
    public static final Label China = new LabelEn("China", "China", LabelCategory.regional);
    public static final Label Chinglish = new LabelEn("Chinglish", "Chinglish", LabelCategory.regional);
    public static final Label Cockney = new LabelEn("Cockney", "Cockney", LabelCategory.regional);
    public static final Label Cockney_rhyming_slang = new LabelEn("Cockney rhyming slang", "Cockney rhyming slang", LabelCategory.regional);
    public static final Label Colombia = new LabelEn("Colombia", "Colombia", LabelCategory.regional);
    public static final Label Commonwealth = new LabelEn("Commonwealth", "Commonwealth of Nations", LabelCategory.regional);
    public static final Label Connacht = new LabelEn("Connacht", "Connacht", LabelCategory.regional);
    public static final Label Cornwall = new LabelEn("Cornwall", "Cornish", LabelCategory.regional);
    public static final Label Costa_Rica = new LabelEn("Costa Rica", "Costa Rica", LabelCategory.regional);
    public static final Label cretan = new LabelEn("cretan", "Cretan dialect", LabelCategory.regional);
    public static final Label Crimean = new LabelEn("Crimean", "Crimean", LabelCategory.regional);
    public static final Label Croatia = new LabelEn("Croatia", "Croatia", LabelCategory.regional);
    public static final Label Cuba = new LabelEn("Cuba", "Cuba", LabelCategory.regional);
    public static final Label Cumbria = new LabelEn("Cumbria", "Cumbrian", LabelCategory.regional);
    public static final Label cypriot = new LabelEn("cypriot", "Cypriot dialect", LabelCategory.regional);

    public static final Label Dari = new LabelEn("Dari", "Dari (Afghanistan)", LabelCategory.regional);
    public static final Label DDR = new LabelEn("DDR", "East Germany", LabelCategory.regional);
    public static final Label Digor = new LabelEn("Digor", "Digor dialect", LabelCategory.regional);
    public static final Label Dominican_Republic = new LabelEn("Dominican Republic", "Dominican Republic", LabelCategory.regional);
    public static final Label Dublin = new LabelEn("Dublin", "Dublin", LabelCategory.regional);

    public static final Label East_Africa = new LabelEn("East Africa", "East Africa", LabelCategory.regional);
    public static final Label East_Germanic = new LabelEn("East Germanic", "East Germanic", LabelCategory.regional);
    public static final Label Eastern_Armenian = new LabelEn("Eastern Armenian", "Eastern Armenian", LabelCategory.regional);
    public static final Label Eastern_Catalan = new LabelEn("Eastern Catalan", "Eastern Catalan", LabelCategory.regional);
    public static final Label Ecuador = new LabelEn("Ecuador", "Ecuador", LabelCategory.regional);
    public static final Label Ekavian = new LabelEn("Ekavian", "Ekavian", LabelCategory.regional);
    public static final Label El_Salvador = new LabelEn("El Salvador", "El Salvador", LabelCategory.regional);
    public static final Label England = new LabelEn("England", "England", LabelCategory.regional);
    public static final Label Europe = new LabelEn("Europe", "Europe", LabelCategory.regional);

    public static final Label Finland = new LabelEn("Finland", "Finland", LabelCategory.regional);
    public static final Label Flemish = new LabelEn("Flemish", "Flemish", LabelCategory.regional);
    public static final Label France = new LabelEn("France", "France", LabelCategory.regional);
    public static final Label Gascony = new LabelEn("Gascony", "Gascony", LabelCategory.regional);
    public static final Label Geordie = new LabelEn("Geordie", "Geordie", LabelCategory.regional);
    public static final Label Germany = new LabelEn("Germany", "Germany", LabelCategory.regional);// only in ruwikt
    public static final Label Gheg = new LabelEn("Gheg", "Gheg", LabelCategory.regional);
    public static final Label Guardiol = new LabelEn("Guardiol", "Guardiol", LabelCategory.regional);
    public static final Label Guatemala = new LabelEn("Guatemala", "Guatemala", LabelCategory.regional);
    public static final Label Guernsey = new LabelEn("Guernsey", "Guernsey", LabelCategory.regional);

    public static final Label Hartlepool = new LabelEn("Hartlepool", "Hartlepool", LabelCategory.regional);
    public static final Label Hawaii = new LabelEn("Hawaii", "Hawaii", LabelCategory.regional);
    public static final Label Helsinki_slang = new LabelEn("Helsinki slang", "Helsinki slang", LabelCategory.regional);
    public static final Label Hoisanese = new LabelEn("Hoisanese", "Hoisanese", LabelCategory.regional);
    public static final Label Hollandic = new LabelEn("Hollandic", "Hollandic", LabelCategory.regional);
    public static final Label Honduras = new LabelEn("Honduras", "Honduras", LabelCategory.regional);
    public static final Label Hong_Kong = new LabelEn("Hong Kong", "Hong Kong", LabelCategory.regional);
    public static final Label Hulu_Pahang = new LabelEn("Hulu Pahang", "Hulu Pahang", LabelCategory.regional);

    public static final Label Ijekavian = new LabelEn("Ijekavian", "Ijekavian", LabelCategory.regional);
    public static final Label Ikavian = new LabelEn("Ikavian", "Ikavian", LabelCategory.regional);
    public static final Label India = new LabelEn("India", "India", LabelCategory.regional);
    public static final Label Indonesia = new LabelEn("Indonesia", "Indonesia", LabelCategory.regional);
    public static final Label Ionic_Greek = new LabelEn("Ionic Greek", "Ionic Greek", LabelCategory.regional);// only in ruwikt
    public static final Label Iran = new LabelEn("Iran", "Iran", LabelCategory.regional);
    public static final Label Ireland = new LabelEn("Ireland", "Ireland", LabelCategory.regional);
    public static final Label Iron = new LabelEn("Iron", "Iron dialect", LabelCategory.regional);
    public static final Label Isle_of_Mann = new LabelEn("Isle of Mann", "Manx", LabelCategory.regional);

    public static final Label Jakarta = new LabelEn("Jakarta", "Jakarta", LabelCategory.regional);
    public static final Label Jamaica = new LabelEn("Jamaica", "Jamaica", LabelCategory.regional);
    public static final Label Japan = new LabelEn("Japan", "Japan", LabelCategory.regional);// only in ruwikt
    public static final Label Javanese = new LabelEn("Javanese", "Java", LabelCategory.regional);
    public static final Label Jersey = new LabelEn("Jersey", "Jersey", LabelCategory.regional);

    public static final Label Kajkavian = new LabelEn("Kajkavian", "Kajkavian", LabelCategory.regional);
    public static final Label Kansai = new LabelEn("Kansai", "Kansai", LabelCategory.regional);
    public static final Label Karabakh = new LabelEn("Karabakh", "Karabakh", LabelCategory.regional);
    public static final Label katharevousa = new LabelEn("katharevousa", "Katharevousa", LabelCategory.regional);
    public static final Label Kenya = new LabelEn("Kenya", "Kenya", LabelCategory.regional);
    public static final Label Kromanti = new LabelEn("Kromanti", "Kromanti spirit possession language", LabelCategory.regional);

    public static final Label Lancashire = new LabelEn("Lancashire", "Lancashire", LabelCategory.regional);
    public static final Label Languedoc = new LabelEn("Languedoc", "Languedoc", LabelCategory.regional);
    public static final Label Latin_America = new LabelEn("Latin America", "Latin America", LabelCategory.regional);
    public static final Label Limousin = new LabelEn("Limousin", "Limousin", LabelCategory.regional);
    public static final Label Lincolnshire = new LabelEn("Lincolnshire", "Lincolnshire", LabelCategory.regional);
    public static final Label Liverpool = new LabelEn("Liverpool", "Liverpudlian", LabelCategory.regional);
    public static final Label London = new LabelEn("London", "London", LabelCategory.regional);
    public static final Label Louisiana_French = new LabelEn("Louisiana French", "Louisiana French", LabelCategory.regional);
    public static final Label Low_Prussian = new LabelEn("Low Prussian", "Low Prussian", LabelCategory.regional);
    public static final Label Lowlands_Scots = new LabelEn("Lowlands Scots", "Lowlands Scots", LabelCategory.regional);
    public static final Label Luxembourg = new LabelEn("Luxembourg", "Luxembourg", LabelCategory.regional);
    public static final Label Lviv = new LabelEn("Lviv", "Lviv", LabelCategory.regional);

    public static final Label Malayeri = new LabelEn("Malayeri", "Malayeri", LabelCategory.regional);
    public static final Label Malaysia = new LabelEn("Malaysia", "Malaysia", LabelCategory.regional);
    public static final Label Mallorca = new LabelEn("Mallorca", "Mallorca", LabelCategory.regional);
    public static final Label maniot = new LabelEn("maniot", "Maniot dialect", LabelCategory.regional);
    public static final Label Manitoba = new LabelEn("Manitoba", "Manitoba", LabelCategory.regional);
    public static final Label Marseille = new LabelEn("Marseille", "Marseille", LabelCategory.regional);
    public static final Label Mecayapan = new LabelEn("Mecayapan", "Mecayapan", LabelCategory.regional);
    public static final Label Mecklenburgisch_Low_German = new LabelEn("Mecklenburgisch Low German", "Mecklenburgisch", LabelCategory.regional);
    public static final Label Mexico = new LabelEn("Mexico", "Mexico", LabelCategory.regional);
    public static final Label Midwest_US = new LabelEn("Midwest US", "Midwest US", LabelCategory.regional);
    public static final Label Mistralian = new LabelEn("Mistralian", "Mistralian", LabelCategory.regional);
    public static final Label Montenegro = new LabelEn("Montenegro", "Montenegro", LabelCategory.regional);
    public static final Label Morocco = new LabelEn("Morocco", "Morocco", LabelCategory.regional);
    public static final Label Multicultural_London_English = new LabelEn("Multicultural London English", "MLE", LabelCategory.regional);
    public static final Label Munster = new LabelEn("Munster", "Munster", LabelCategory.regional);

    public static final Label Namibia = new LabelEn("Namibia", "Namibia", LabelCategory.regional);
    public static final Label Negeri_Sembilan = new LabelEn("Negeri Sembilan", "Negeri Sembilan", LabelCategory.regional);
    public static final Label Netherlands = new LabelEn("Netherlands", "Netherlands", LabelCategory.regional);
    public static final Label New_Brunswick = new LabelEn("New Brunswick", "New Brunswick", LabelCategory.regional);
    public static final Label New_England = new LabelEn("New England", "New England", LabelCategory.regional);
    public static final Label New_York = new LabelEn("New York", "New York", LabelCategory.regional);
    public static final Label New_Zealand = new LabelEn("New Zealand", "New Zealand", LabelCategory.regional);
    public static final Label Newfoundland = new LabelEn("Newfoundland", "Newfoundland", LabelCategory.regional);
    public static final Label Nicaragua = new LabelEn("Nicaragua", "Nicaragua", LabelCategory.regional);
    public static final Label Norfolk = new LabelEn("Norfolk", "Norfolk", LabelCategory.regional);
    public static final Label North_America = new LabelEn("North America", "North America", LabelCategory.regional);
    public static final Label North_Germanic = new LabelEn("North Germanic", "North Germanic", LabelCategory.regional);
    public static final Label North_Korea = new LabelEn("North Korea", "North Korea", LabelCategory.regional);
    public static final Label Northeast_England = new LabelEn("Northeast England", "Northeast England", LabelCategory.regional);
    public static final Label Northern_Crimea = new LabelEn("Northern Crimea", "Northern Crimea", LabelCategory.regional);
    public static final Label Northern_Dutch = new LabelEn("Northern Dutch", "Northern Dutch", LabelCategory.regional);
    public static final Label Northern_England = new LabelEn("Northern England", "Northern England", LabelCategory.regional);
    public static final Label Northern_Ireland = new LabelEn("Northern Ireland", "Northern Ireland", LabelCategory.regional);
    public static final Label Northumbria = new LabelEn("Northumbria", "Northumbrian", LabelCategory.regional);
    public static final Label northwest = new LabelEn("northwest", "Northwest", LabelCategory.regional);
    public static final Label Northwest_Germanic = new LabelEn("Northwest Germanic", "Northwest Germanic", LabelCategory.regional);
    public static final Label Northwest_Territories = new LabelEn("Northwest Territories", "Northwest Territories", LabelCategory.regional);
    public static final Label Nova_Scotia = new LabelEn("Nova Scotia", "Nova Scotia", LabelCategory.regional);
    public static final Label Nunavut = new LabelEn("Nunavut", "Nunavut", LabelCategory.regional);

    public static final Label Ontario= new LabelEn("Ontario", "Ontario", LabelCategory.regional);

    public static final Label Pahang = new LabelEn("Pahang", "Pahang", LabelCategory.regional);
    public static final Label Pakistan = new LabelEn("Pakistan", "Pakistan", LabelCategory.regional);
    public static final Label Paraguay = new LabelEn("Paraguay", "Paraguay", LabelCategory.regional);
    public static final Label Pennsylvania_Dutch_English = new LabelEn("Pennsylvania Dutch English", "Pennsylvania Dutch English", LabelCategory.regional);
    public static final Label Perak = new LabelEn("Perak", "Perak", LabelCategory.regional);
    public static final Label Peru = new LabelEn("Peru", "Peru", LabelCategory.regional);
    public static final Label Philippines = new LabelEn("Philippines", "Philippines", LabelCategory.regional);
    public static final Label Picardy = new LabelEn("Picardy", "Picardy", LabelCategory.regional);
    public static final Label Polari = new LabelEn("Polari", "Polari", LabelCategory.regional);
    public static final Label Pomeranian_Low_German = new LabelEn("Pomeranian Low German", "Pomeranian", LabelCategory.regional);
    public static final Label pontian = new LabelEn("pontian", "Pontian dialect", LabelCategory.regional);
    public static final Label Portugal = new LabelEn("Portugal", "Portugal", LabelCategory.regional);
    public static final Label Prince_Edward_Island = new LabelEn("Prince Edward Island", "Prince Edward Island", LabelCategory.regional);
    public static final Label Provence = new LabelEn("Provence", "Provence", LabelCategory.regional);
    public static final Label Puter = new LabelEn("Puter", "Puter", LabelCategory.regional);

    public static final Label Quebec = new LabelEn("Quebec", "Quebec", LabelCategory.regional);

    public static final Label regional = new LabelEn("regional", "regional", LabelCategory.regional);
    public static final Label Rio_de_Janeiro = new LabelEn("Rio de Janeiro", "Rio de Janeiro city", LabelCategory.regional);
    public static final Label Réunion = new LabelEn("Réunion", "Réunion", LabelCategory.regional);

    public static final Label Saint_Ouen = new LabelEn("Saint Ouen", "Saint Ouën", LabelCategory.regional);
    public static final Label Saskatchewan = new LabelEn("Saskatchewan", "Saskatchewan", LabelCategory.regional);
    public static final Label Scania = new LabelEn("Scania", "Scanian", LabelCategory.regional);
    public static final Label Scotland = new LabelEn("Scotland", "Scotland", LabelCategory.regional);
    public static final Label Serbia = new LabelEn("Serbia", "Serbia", LabelCategory.regional);
    public static final Label Shopski = new LabelEn("Shopski", "Shopski dialect", LabelCategory.regional);
    public static final Label Silla = new LabelEn("Silla", "Silla", LabelCategory.regional);
    public static final Label Singapore = new LabelEn("Singapore", "Singapore", LabelCategory.regional);
    public static final Label Sistani = new LabelEn("Sistani", "Sistani", LabelCategory.regional);
    public static final Label Skiri_Pawnee = new LabelEn("Skiri Pawnee", "Skiri Pawnee", LabelCategory.regional);
    public static final Label South_Africa = new LabelEn("South Africa", "South Africa", LabelCategory.regional);
    public static final Label South_Korea = new LabelEn("South Korea", "South Korea", LabelCategory.regional);
    public static final Label South_Scots = new LabelEn("South Scots", "South Scots", LabelCategory.regional);
    public static final Label Southern_Dutch = new LabelEn("Southern Dutch", "Southern Dutch", LabelCategory.regional);
    public static final Label southern_US = new LabelEn("southern US", "southern US", LabelCategory.regional);
    public static final Label Spain = new LabelEn("Spain", "Spain", LabelCategory.regional);
    public static final Label Surmiran = new LabelEn("Surmiran", "Surmiran", LabelCategory.regional);
    public static final Label Sursilvan = new LabelEn("Sursilvan", "Sursilvan", LabelCategory.regional);
    public static final Label Sussex = new LabelEn("Sussex", "Sussex", LabelCategory.regional);
    public static final Label Sutsilvan = new LabelEn("Sutsilvan", "Sutsilvan", LabelCategory.regional);
    public static final Label South_Island = new LabelEn("Suðuroy", "Suðuroy dialect", LabelCategory.regional);
    public static final Label Sweden = new LabelEn("Sweden", "Sweden", LabelCategory.regional);
    public static final Label Switzerland = new LabelEn("Switzerland", "Switzerland", LabelCategory.regional);
    public static final Label Sao_Paulo = new LabelEn("São Paulo", "São Paulo city", LabelCategory.regional);

    public static final Label Taiwan = new LabelEn("Taiwan", "Taiwan", LabelCategory.regional);
    public static final Label Teesside = new LabelEn("Teesside", "Teesside", LabelCategory.regional);
    public static final Label Tigranakert = new LabelEn("Tigranakert", "Tigranakert", LabelCategory.regional);
    public static final Label Tosk = new LabelEn("Tosk", "Tosk", LabelCategory.regional);
    public static final Label Trinidad_and_Tobago = new LabelEn("Trinidad and Tobago", "Trinidad and Tobago", LabelCategory.regional);
    public static final Label Tyneside = new LabelEn("Tyneside", "Tyneside", LabelCategory.regional);

    public static final Label uds = new LabelEn("uds.", "used formally in Spain", LabelCategory.regional);
    public static final Label Ullans = new LabelEn("Ullans", "Ulster Scots", LabelCategory.regional);
    public static final Label Ulster = new LabelEn("Ulster", "Ulster", LabelCategory.regional);
    public static final Label Uruguay = new LabelEn("Uruguay", "Uruguay", LabelCategory.regional);
    public static final Label US = new LabelEn("US", "US", LabelCategory.regional);

    public static final Label Valencia = new LabelEn("Valencia", "Valencia", LabelCategory.regional);
    public static final Label Vallader = new LabelEn("Vallader", "Vallader", LabelCategory.regional);
    public static final Label Venezuela = new LabelEn("Venezuela", "Venezuela", LabelCategory.regional);
    public static final Label Venice = new LabelEn("Venice", "Venice", LabelCategory.regional);
    public static final Label Vivaro_Alpine = new LabelEn("Vivaro-Alpine", "Vivaro-Alpine", LabelCategory.regional);

    public static final Label Wales = new LabelEn("Wales", "Wales", LabelCategory.regional);
    public static final Label Wearside = new LabelEn("Wearside", "Wearside", LabelCategory.regional);
    public static final Label West_Germanic = new LabelEn("West Germanic", "West Germanic", LabelCategory.regional);
    public static final Label West_Midlands = new LabelEn("West Midlands", "West Midlands", LabelCategory.regional);
    public static final Label Western_Armenian = new LabelEn("Western Armenian", "Western Armenian", LabelCategory.regional);
    public static final Label Western_Catalan = new LabelEn("Western Catalan", "Western Catalan", LabelCategory.regional); 
    public static final Label Western_Malayo_Polynesian = new LabelEn("Western Malayo-Polynesian", "Western Malayo-Polynesian", LabelCategory.regional);
    public static final Label Western_Pomeranian_Low_German = new LabelEn("Western Pomeranian Low German", "Western Pomeranian", LabelCategory.regional);
    public static final Label Western_Ukraine = new LabelEn("Western Ukraine", "Western Ukraine", LabelCategory.regional);

    public static final Label Yanbian = new LabelEn("Yanbian", "Yanbian", LabelCategory.regional);
    public static final Label Yorkshire = new LabelEn("Yorkshire", "Yorkshire", LabelCategory.regional);
    public static final Label Yukon = new LabelEn("Yukon", "Yukon", LabelCategory.regional);

    public static final Label Zimbabwe = new LabelEn("Zimbabwe", "Zimbabwe", LabelCategory.regional);


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
    public static final Label ecclesiastical = new LabelEn("ecclesiastical", "ecclesiastical", LabelCategory.topical); // церк. - церковный
    public static final Label honorific = new LabelEn("honorific", "honorific", LabelCategory.topical);
    public static final Label Wiktionary_and_WMF_jargon = new LabelEn("Wiktionary and WMF jargon", "Wiktionary and WMF jargon", LabelCategory.topical);
    
    
    
    // Rumyantsev 186 all Subcategories in http://en.wiktionary.org/wiki/Category:Topical_context_labels
    
    // computing Rumyantsev
    // //////////////////////////
    
    public static final Label artificial_intelligence = new LabelEn("artificial intelligence", "artificial intelligence", LabelCategory.computing); // искусственный интеллект
    public static final Label computational_linguistics = new LabelEn("computational linguistics", "computational linguistics", LabelCategory.computing); // компьютерная лингвистика (Как особое научное направление компьютерная лингвистика оформилась в 1960-е годы. Русский термин «компьютерная лингвистика» является калькой с английского computational linguistics. Поскольку прилагательное computational по-русски может переводиться и как «вычислительный», в литературе встречается также термин «вычислительная лингвистика», однако в отечественной науке он приобретает более узкое значение, приближающееся к понятию «квантитативной лингвистики». Alex Lilo)
    public static final Label computer_graphics = new LabelEn("computer graphics", "computer graphics", LabelCategory.computing); // компьютерная графика, машинная графика
    public static final Label computer_hardware = new LabelEn("computer hardware", "computer hardware", LabelCategory.computing); // аппаратное обеспечение компьютера
    public static final Label computer_security = new LabelEn("computer security", "computer security", LabelCategory.computing); // компьютерная безопасность, защита ЭВМ
    public static final Label computing = new LabelEn("computing", "computing", LabelCategory.computing); // вычислительная техника, обработка данных, компьютинг
    public static final Label computing_theory = new LabelEn("computing theory", "computing theory", LabelCategory.computing); // теория вычислений, аналог computer science?
    public static final Label data_management = new LabelEn("data management", "data management", LabelCategory.computing); // управление данными
    public static final Label data_modeling = new LabelEn("data modeling", "data modeling", LabelCategory.computing); // моделирование данных 
    public static final Label databases = new LabelEn("databases", "databases", LabelCategory.computing); // базы данных
    public static final Label demoscene = new LabelEn("demoscene", "demoscene", LabelCategory.computing); // перевод странный, мультитран переводит как "демонстрационный ролик", а enwikt считает, что это сообщество просмотрщиков демо-программ для ПК
    
    public static final Label graphical_user_interface = new LabelEn("graphical user interface", "graphical user interface", LabelCategory.computing); // графический интерфейс пользователя
    public static final Label GUI = LabelEn.addNonUniqueShortName(graphical_user_interface, "GUI"); // добавил сокращение
    
    public static final Label HTML = new LabelEn("HTML", "HTML", LabelCategory.computing); // язык гипертекстовой маркировки
    public static final Label networking = new LabelEn("networking", "networking", LabelCategory.computing); // сетевые технологии
    public static final Label programming = new LabelEn("programming", "programming", LabelCategory.computing); // программирование, в ruwikt есть категория прогр.
    public static final Label software = new LabelEn("software", "software", LabelCategory.computing); // программное обеспечение, программа
    public static final Label software_engineering = new LabelEn("software engineering", "software engineering", LabelCategory.computing); // разработка программ, не синоним programming, т. к. здесь больше рассмотрены вопросы управления ПО, чем создания кода
    public static final Label web_design = new LabelEn("web design", "web design", LabelCategory.computing); // веб-дизайн
    
    // games Rumyantsev
    // //////////////////////////
    
    public static final Label backgammon = new LabelEn("backgammon", "backgammon", LabelCategory.games); // нарды
    public static final Label bingo = new LabelEn("bingo", "bingo", LabelCategory.games); // бинго
    public static final Label board_games = new LabelEn("board games", "board games", LabelCategory.games); // настольные игры
    public static final Label bridge = new LabelEn("bridge", "bridge", LabelCategory.games); // бридж
    public static final Label card_games = new LabelEn("card games", "card games", LabelCategory.games); // карточные игры
    public static final Label chess = new LabelEn("chess", "chess", LabelCategory.games); // шахматы
    public static final Label computer_games = new LabelEn("computer games", "computer games", LabelCategory.games); // компьютерные игры
    public static final Label cribbage = new LabelEn("cribbage", "cribbage", LabelCategory.games); // криббидж
    public static final Label dominoes = new LabelEn("dominoes", "dominoes", LabelCategory.games); // домино
    public static final Label game_of_go = new LabelEn("game of go", "game of go", LabelCategory.games); // игра Го, любопытное написание, обычно пишут Go game, multitran вообще о таком переводе не знает, google не переводит, только lingvo
    public static final Label games = new LabelEn("games", "games", LabelCategory.games); // игры (здесь в основном названия игр)
    public static final Label gaming = new LabelEn("gaming", "gaming", LabelCategory.games); // игр. - игровое
    public static final Label online_gaming = new LabelEn("online gaming", "online gaming", LabelCategory.games); // компьютерные игры в режиме online (странная категория, много пересечений с gaming, но есть специфика)

    public static final Label poker = new LabelEn("poker", "poker", LabelCategory.games); // покер
    public static final Label poker_slang = LabelEn.addNonUniqueShortName(poker, "poker slang"); // сленг игроков в покер, но мне кажется, эти две категории являются синонимами (судя по статьям в них)

    public static final Label role_playing_games = new LabelEn("role-playing games", "role-playing games", LabelCategory.games); // ролевые игры
    public static final Label shogi = new LabelEn("shogi", "shogi", LabelCategory.games); // сёги
    public static final Label tarot = new LabelEn("tarot", "tarot", LabelCategory.games); // таро
    public static final Label Tetris = new LabelEn("Tetris", "Tetris", LabelCategory.games); // тетрис
    
    public static final Label video_games = new LabelEn("video games", "video games", LabelCategory.games); // видеоигры
    public static final Label video_game_genre = LabelEn.addNonUniqueShortName(video_games, "video game genre"); // вообще псевдокатегория, соответствующая страница на enwikt имеет странное оформление
	
    // mathematics Rumyantsev
    // //////////////////////////
    
    public static final Label algebra = new LabelEn("algebra", "algebra", LabelCategory.mathematics); // алгебра
    public static final Label algebraic_geometry = new LabelEn("algebraic geometry", "algebraic geometry", LabelCategory.mathematics); // алгебраическая геометрия
    public static final Label analytic_geometry = new LabelEn("analytic geometry", "analytic geometry", LabelCategory.mathematics); // аналитическая геометрия
    public static final Label arithmetic = new LabelEn("arithmetic", "arithmetic", LabelCategory.mathematics); // арифметика
    public static final Label calculus = new LabelEn("calculus", "calculus", LabelCategory.mathematics); // математический анализ
    public static final Label category_theory = new LabelEn("category theory", "category theory", LabelCategory.mathematics); // теория категорий
    public static final Label combinatorics = new LabelEn("combinatorics", "combinatorics", LabelCategory.mathematics); // комбинаторика
    public static final Label complex_analysis = new LabelEn("complex analysis", "complex analysis", LabelCategory.mathematics); // комплексный анализ
    public static final Label functional_analysis = new LabelEn("functional analysis", "functional analysis", LabelCategory.mathematics); // функциональный анализ
    public static final Label fuzzy_logic = new LabelEn("fuzzy logic", "fuzzy logic", LabelCategory.mathematics); // нечеткая логика
    public static final Label game_theory = new LabelEn("game theory", "game theory", LabelCategory.mathematics); // теория игр
    public static final Label geometry = new LabelEn("geometry", "geometry", LabelCategory.mathematics); // геометрия
    public static final Label graph_theory = new LabelEn("graph theory", "graph theory", LabelCategory.mathematics); // теория графов
    public static final Label group_theory = new LabelEn("group theory", "group theory", LabelCategory.mathematics); // теория групп
    public static final Label linear_algebra = new LabelEn("linear algebra", "linear algebra", LabelCategory.mathematics); // линейная алгебра
    
    public static final Label mathematics = new LabelEn("mathematics", "mathematics", LabelCategory.mathematics);
    public static final Label math = LabelEn.addNonUniqueShortName(mathematics, "math");
    public static final Label maths = LabelEn.addNonUniqueShortName(mathematics, "maths");
    
    public static final Label probability_theory = new LabelEn("probability theory", "probability theory", LabelCategory.mathematics); // теория вероятностей
    public static final Label trigonometry = new LabelEn("trigonometry", "trigonometry", LabelCategory.mathematics); // тригонометрия
    public static final Label vector_algebra = new LabelEn("vector algebra", "vector algebra", LabelCategory.mathematics); // векторная алгебра
    
    // music Rumyantsev
    // //////////////////////////
    public static final Label jazz = new LabelEn("jazz", "jazz", LabelCategory.music); // джаз
    public static final Label marching = new LabelEn("marching", "marching", LabelCategory.music); // марш
    public static final Label music = new LabelEn("music", "music", LabelCategory.music); // муз. - музыкальное
	public static final Label musical_instruments = new LabelEn("musical instruments", "musical instruments", LabelCategory.music); // музыкальные инструменты
	public static final Label opera = new LabelEn("opera", "opera", LabelCategory.music); // опера

    // mythology Rumyantsev
    // //////////////////////////
    
    public static final Label Armenian_mythology = new LabelEn("Armenian mythology", "Armenian mythology", LabelCategory.mythology); // Армянская мифология
    public static final Label Asturian_mythology = new LabelEn("Asturian mythology", "Asturian mythology", LabelCategory.mythology); // Астурийская мифология
    public static final Label Egyptian_mythology = new LabelEn("Egyptian mythology", "Egyptian mythology", LabelCategory.mythology); // Египетская мифология
    
    public static final Label Greek_mythology = new LabelEn("Greek mythology", "Greek mythology", LabelCategory.mythology); // Греческая мифология
    public static final Label Greek_god = LabelEn.addNonUniqueShortName(Greek_mythology, "Greek god"); // Считаю, что целесообразно так, поскольку в ссылающихся на этот шаблон enwikt страницах расшифровывается именно как Greek mythology, а также есть пересечения терминов (см., напр. Hera)
    
    public static final Label Hawaiian_mythology = new LabelEn("Hawaiian mythology", "Hawaiian mythology", LabelCategory.mythology); // Гавайская мифология
    public static final Label Iranian_mythology = new LabelEn("Iranian mythology", "Iranian mythology", LabelCategory.mythology); // Иранская мифология
    public static final Label Irish_mythology = new LabelEn("Irish mythology", "Irish mythology", LabelCategory.mythology); // Ирландская мифология
    public static final Label mythology = new LabelEn("mythology", "mythology", LabelCategory.mythology); // мифол. - мифологическое
    public static final Label Norse_mythology = new LabelEn("Norse mythology", "Norse mythology", LabelCategory.mythology); // Скандинавская мифология
    
    public static final Label Roman_mythology = new LabelEn("Roman mythology", "Roman mythology", LabelCategory.mythology); // Римская мифология
    public static final Label Roman_god = LabelEn.addNonUniqueShortName(Roman_mythology, "Roman god"); // симметрично греческой - та же ситуация

    // religion Rumyantsev
    // //////////////////////////
    
    public static final Label Buddhism = new LabelEn("Buddhism", "Buddhism", LabelCategory.religion); // буддизм
    public static final Label Christianity = new LabelEn("Christianity", "Christianity", LabelCategory.religion); // христианство
    public static final Label Gnosticism = new LabelEn("Gnosticism", "Gnosticism", LabelCategory.religion); // гностицизм
    public static final Label Hinduism = new LabelEn("Hinduism", "Hinduism", LabelCategory.religion); // индуизм
    public static final Label Islam = new LabelEn("Islam", "Islam", LabelCategory.religion); // ислам. - исламское
    public static final Label Jainism = new LabelEn("Jainism", "Jainism", LabelCategory.religion); // джайнизм
    public static final Label Judaism = new LabelEn("Judaism", "Judaism", LabelCategory.religion); // иудаизм
    public static final Label Mormonism = new LabelEn("Mormonism", "Mormonism", LabelCategory.religion); // мормонство
    public static final Label Protestantism = new LabelEn("Protestantism", "Protestantism", LabelCategory.religion); // протестантство
    public static final Label religion = new LabelEn("religion", "religion", LabelCategory.religion); // религ. - религиозное
    
    public static final Label Rastafarian = new LabelEn("Rastafarian", "Rastafarian", LabelCategory.religion); // растафари
    public static final Label rasta = LabelEn.addNonUniqueShortName(Rastafarian, "rasta"); // шаблон называется rasta, но расшифровка шаблона Rastafarian, поэтому принял решение удлиннить название шаблона (полное), а укороченное сделать синонимом - так логичнее в сравнении с остальными
    
    public static final Label Raelism = new LabelEn("Raëlism", "Raëlism", LabelCategory.religion); // раэлиты
    public static final Label Raelism_latin = LabelEn.addNonUniqueShortName(Raelism, "Raеlism"); // добавил синоним без юникодовского символа ё, использую суффикс _latin
    
    public static final Label Roman_Catholicism = new LabelEn("Roman Catholicism", "Roman Catholicism", LabelCategory.religion); // католичество
    public static final Label Catholicism = LabelEn.addNonUniqueShortName(Roman_Catholicism, "Catholicism"); // синоним
        
    public static final Label Scientology = new LabelEn("Scientology", "Scientology", LabelCategory.religion); // сайентология
    
    public static final Label Shinto = new LabelEn("Shintō", "Shintō", LabelCategory.religion); // синтоизм
    public static final Label Shinto_latin = LabelEn.addNonUniqueShortName(Shinto, "Shinto"); // добавил синоним без юникодовского символа ō, использую суффикс _latin
    
    public static final Label Sikhism = new LabelEn("Sikhism", "Sikhism", LabelCategory.religion); // сикхизм
    // public static final Label Yorubic = new LabelEn("Yorubic", "Yorubic", LabelCategory.religion); // !!!!!!!!!!!!! перевода НЕТ, более того, категория пустая - нужна ли она?
    public static final Label Zoroastrianism = new LabelEn("Zoroastrianism", "Zoroastrianism", LabelCategory.religion); // зороастризм
    
    // science Rumyantsev
    // //////////////////////////
    public static final Label aeronautics = new LabelEn("aeronautics" ,"aeronautics", LabelCategory.science); // аэронавтика
    
    public static final Label aerospace = new LabelEn("aerospace" ,"aeronautics, space", LabelCategory.science); // изучение воздушно-космического пространства
    public static final Label space_science = new LabelEn("space science" ,"space science", LabelCategory.science); // аналог???
        
    public static final Label alchemy = new LabelEn("alchemy" ,"alchemy", LabelCategory.science); // алхим. - алхимическое
    public static final Label analytical_chemistry = new LabelEn("analytical chemistry" ,"analytical chemistry", LabelCategory.science); // аналитическая химия
    public static final Label anthropology = new LabelEn("anthropology" ,"anthropology", LabelCategory.science); // антроп. - антропологическое
    public static final Label arachnology = new LabelEn("arachnology" ,"arachnology", LabelCategory.science); // арахнология
    public static final Label archaeology = new LabelEn("archaeology" ,"archaeology", LabelCategory.science); // археол. - археология
    public static final Label astronautics = new LabelEn("astronautics" ,"astronautics", LabelCategory.science); // астронавтика
    public static final Label astronomy = new LabelEn("astronomy" ,"astronomy", LabelCategory.science); // астрон. - астрономическое
    public static final Label astrophysics = new LabelEn("astrophysics" ,"astrophysics", LabelCategory.science); // астрофизика
    public static final Label bacteriology = new LabelEn("bacteriology" ,"bacteriology", LabelCategory.science); // бактериология
    
    public static final Label biochemistry = new LabelEn("biochemistry" ,"biochemistry", LabelCategory.science); // биохим. - биохимическое
    public static final Label amino_acid = LabelEn.addNonUniqueShortName(biochemistry, "amino acid"); // странный синоним, здесь собраны все аминокислоты, но они в тексте раскрываются как биохимия
    
    public static final Label biology = new LabelEn("biology" ,"biology", LabelCategory.science); // биол. - биологическое
    public static final Label botany = new LabelEn("botany" ,"botany", LabelCategory.science); // ботан. - ботаническое
    
    public static final Label chemistry = new LabelEn("chemistry" ,"chemistry", LabelCategory.science); // хим. - химическое
    public static final Label element_symbol = LabelEn.addNonUniqueShortName(chemistry, "element symbol");
    
    public static final Label psychology = new LabelEn("psychology" ,"psychology", LabelCategory.science); // психол. - психология
    public static final Label clinical_psychology = LabelEn.addNonUniqueShortName(psychology, "clinical psychology"); //
    
    public static final Label computer_science = new LabelEn("computer science" ,"computer science", LabelCategory.science); // информ. - информатическое
    public static final Label cryptozoology = new LabelEn("cryptozoology" ,"cryptozoology", LabelCategory.science); // криптозоология
    public static final Label gerontology = new LabelEn("gerontology" ,"gerontology", LabelCategory.science); // геронтология
    public static final Label marine_biology = new LabelEn("marine biology" ,"marine biology", LabelCategory.science); // морская биология
    public static final Label neuroscience = new LabelEn("neuroscience" ,"neuroscience", LabelCategory.science); // нейробиология???
    public static final Label physics = new LabelEn("physics" ,"physics", LabelCategory.science); // физ. - физическое
    public static final Label systematics = new LabelEn("systematics" ,"systematics", LabelCategory.science); // систематика, таксономия
    public static final Label systems_theory = new LabelEn("systems theory" ,"systems theory", LabelCategory.science); // теория систем
    public static final Label zoology = new LabelEn("zoology" ,"zoology", LabelCategory.science); // зоол. - зоологическое
    public static final Label zootomy = new LabelEn("zootomy" ,"zootomy", LabelCategory.science); // зоотомия
    
    // sports Sasha First
    // //////////////////////////
 
    public static final Label archery = new LabelEn("archery" ,"archery", LabelCategory.sports); // стрельба из лука
    public static final Label athletics = new LabelEn("athletics" ,"athletics", LabelCategory.sports); // атлетика
    public static final Label auto_racing = new LabelEn("auto racing" ,"auto racing", LabelCategory.sports); // автогонки
    public static final Label badminton = new LabelEn("badminton" ,"badminton", LabelCategory.sports); // бадминтон
    public static final Label ball_games = new LabelEn("ball games" ,"ball games", LabelCategory.sports); // игры с мячом
    public static final Label baseball = new LabelEn("baseball" ,"baseball", LabelCategory.sports); // бейсбол
    public static final Label basketball = new LabelEn("basketball" ,"basketball", LabelCategory.sports); // баскетбол
    public static final Label billiards = new LabelEn("billiards" ,"billiards", LabelCategory.sports); // бильярд
    public static final Label board_sports = new LabelEn("board sports" ,"board sports", LabelCategory.sports); // спорт на досках (скейтборд, сноуборд)
    public static final Label bodybuilding = new LabelEn("bodybuilding" ,"bodybuilding", LabelCategory.sports); // бодибилдинг
    public static final Label bowling = new LabelEn("bowling" ,"bowling", LabelCategory.sports); // боулинг
    public static final Label boxing = new LabelEn("boxing" ,"boxing", LabelCategory.sports); // бокс
    public static final Label bullfighting = new LabelEn("bullfighting" ,"bullfighting", LabelCategory.sports); // бой быков, коррида?
    public static final Label canoeing = new LabelEn("canoeing" ,"canoeing", LabelCategory.sports); // гонки на каноэ
    public static final Label caving = new LabelEn("caving" ,"caving", LabelCategory.sports); // практическая спелеология
    public static final Label cheerleading = new LabelEn("cheerleading" ,"cheerleading", LabelCategory.sports); // чирлидинг
    public static final Label climbing = new LabelEn("climbing" ,"climbing", LabelCategory.sports); // альпинизм
    public static final Label cricket = new LabelEn("cricket" ,"cricket", LabelCategory.sports); // крикет
    public static final Label croquet = new LabelEn("croquet" ,"croquet", LabelCategory.sports); // only in ruwikt
    public static final Label curling = new LabelEn("curling" ,"curling", LabelCategory.sports); // керлинг
    public static final Label cycling = new LabelEn("cycling" ,"cycling", LabelCategory.sports); // велоспорт
    public static final Label dance = new LabelEn("dance" ,"dance", LabelCategory.sports); // танцы
    public static final Label darts = new LabelEn("darts" ,"darts", LabelCategory.sports); // дартс
    public static final Label diving = new LabelEn("diving" ,"diving", LabelCategory.sports); // погружение, подводное плавание
    public static final Label dressage = new LabelEn("dressage" ,"dressage", LabelCategory.sports); // выездка
    public static final Label exercise = new LabelEn("exercise" ,"exercise (sport)", LabelCategory.sports); // спортивные упражнения
    public static final Label fencing = new LabelEn("fencing" ,"fencing", LabelCategory.sports); // фехтование
    public static final Label figure_skating = new LabelEn("figure skating" ,"figure skating", LabelCategory.sports); // фигурное катание
    public static final Label fishing = new LabelEn("fishing" ,"fishing", LabelCategory.sports); // рыбол. - рыболовецкое
    public static final Label golf = new LabelEn("golf" ,"golf", LabelCategory.sports); // гольф
    public static final Label gymnastics = new LabelEn("gymnastics" ,"gymnastics", LabelCategory.sports); // гимнастика
    public static final Label handball = new LabelEn("handball" ,"handball", LabelCategory.sports); // гандбол
    public static final Label horse_racing = new LabelEn("horse racing" ,"horse racing", LabelCategory.sports); // конские бега

    public static final Label hockey = new LabelEn("hockey" ,"hockey", LabelCategory.sports); 
    public static final Label ice_hockey = LabelEn.addNonUniqueShortName(hockey, "ice hockey");
    public static final Label field_hockey = LabelEn.addNonUniqueShortName(hockey, "field hockey");

    public static final Label hurling = new LabelEn("hurling" ,"hurling", LabelCategory.sports); // ирландский травяной хоккей
    public static final Label judo = new LabelEn("judo" ,"judo", LabelCategory.sports); // дзюдо
    public static final Label lacrosse = new LabelEn("lacrosse" ,"lacrosse", LabelCategory.sports); // лакросс
    public static final Label luge = new LabelEn("luge" ,"luge", LabelCategory.sports); // санный спорт
    public static final Label martial_arts = new LabelEn("martial arts" ,"martial arts", LabelCategory.sports); // боевые искусства
    public static final Label motor_racing = new LabelEn("motor racing" ,"motor racing", LabelCategory.sports); // мотоспорт
    public static final Label netball = new LabelEn("netball" ,"netball", LabelCategory.sports); // нетбол
    public static final Label paintball = new LabelEn("paintball" ,"paintball", LabelCategory.sports); // пейнтбол
    public static final Label roller_derby = new LabelEn("roller derby" ,"roller derby", LabelCategory.sports); // не нашел перевода
    public static final Label rowing = new LabelEn("rowing" ,"rowing", LabelCategory.sports); // гребля
    
    public static final Label rugby = new LabelEn("rugby" ,"rugby", LabelCategory.sports); // регби
    public static final Label rugby_union = LabelEn.addNonUniqueShortName(rugby, "rugby union"); // Союз регби, но по содержанию статей, ссылающихся на термин, здесь должно быть регби
    
    public static final Label sailing = new LabelEn("sailing" ,"sailing", LabelCategory.sports); // парусный спорт
    public static final Label skateboarding = new LabelEn("skateboarding" ,"skateboarding", LabelCategory.sports); // скейтбординг, есть ли смысл объединить с board_sports? (на board_sports всего 3 ссылки) see http://en.wiktionary.org/wiki/Wiktionary:Beer_parlour/2013/May#board_sports_vs._skateboarding
    public static final Label skiing = new LabelEn("skiing" ,"skiing", LabelCategory.sports); // лыжный спорт
    public static final Label snooker = new LabelEn("snooker" ,"snooker", LabelCategory.sports); // снукер
    public static final Label snowboarding = new LabelEn("snowboarding" ,"snowboarding", LabelCategory.sports); // сноубординг
    
    public static final Label soccer = new LabelEn("soccer" ,"soccer", LabelCategory.sports); // футб - футбол
    public static final Label American_football = new LabelEn("American football" ,"American football", LabelCategory.sports); // американский футбол
    public static final Label Australian_rules_football = new LabelEn("Australian rules football" ,"Australian rules football", LabelCategory.sports); // австралийский футбол
    public static final Label Canadian_football = new LabelEn("Canadian football" ,"Canadian football", LabelCategory.sports); // канадский футбол
    public static final Label Gaelic_football = new LabelEn("Gaelic football" ,"Gaelic football", LabelCategory.sports); // гэльский футбол
    
    public static final Label softball = new LabelEn("softball" ,"softball", LabelCategory.sports); // софтбол
    public static final Label sports = new LabelEn("sports" ,"sports", LabelCategory.sports);
    public static final Label squash = new LabelEn("squash" ,"squash (sport)", LabelCategory.sports); // сквош
    public static final Label sumo = new LabelEn("sumo" ,"sumo", LabelCategory.sports); // сумо
    public static final Label surfing = new LabelEn("surfing" ,"surfing", LabelCategory.sports); // сёрфинг
    public static final Label swimming = new LabelEn("swimming" ,"swimming", LabelCategory.sports); // плавание
    public static final Label table_tennis = new LabelEn("table tennis" ,"table tennis", LabelCategory.sports); // настольный теннис
    public static final Label tennis = new LabelEn("tennis" ,"tennis", LabelCategory.sports); // теннис - теннис
    public static final Label volleyball = new LabelEn("volleyball" ,"volleyball", LabelCategory.sports); // волейб - волейбол
    public static final Label weightlifting = new LabelEn("weightlifting" ,"weightlifting", LabelCategory.sports); // тяжелая атлетика
    public static final Label wrestling = new LabelEn("wrestling" ,"wrestling", LabelCategory.sports); // борьба
    
    // DEBUG: should be one error for each code line below:
    // DDDDDDDDDDDDDDDDDDDDDDDDDD
    //public static final Label archaic_short_name_duplication = new LabelEn("archaic",  "archaic full name (duplication of short name)", LabelCategory.period);
    //public static final Label archaic_full_name_duplication = new LabelEn("archaic short name (duplication of full name)", "archaic", LabelCategory.period);
    //public static final Label dated_sense_again = LabelEn.addNonUniqueShortName(dated, "dated_sense");
    // DDDDDDDDDDDDDDDDDDeo DEBUG
}
