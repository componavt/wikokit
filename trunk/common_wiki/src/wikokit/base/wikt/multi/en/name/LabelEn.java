/* Label.java - contexual information for definitions, or Synonyms,
 *                     or Translations.
 * 
 * Copyright (c) 2008-2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.en.name;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelCategory;


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
 * @see http://en.wiktionary.org/wiki/Module:labels/data
 */
public final class LabelEn extends Label {       
    
    protected final static Map<String, Label> short_name2label = new HashMap<String, Label>();
    protected final static Map<Label, String> label2short_name = new HashMap<Label, String>();
    
    protected final static Map<String, Label> name2label = new HashMap<String, Label>();
    protected final static Map<Label, String> label2name = new HashMap<Label, String>();
    
    /** If there are more than one context label (synonyms,  short name label): <synonymic_label, source_main_unique_label> */
    private static Map<String, Label> multiple_synonym2label = new HashMap<String, Label>();
    
    
    /** Category associated with this label. */
    private final LabelCategory category;
    //                                                                    LabelEn in fact
    private static Map<Label, LabelCategory> label2category = new HashMap<Label, LabelCategory>();
    
    /** Constructor for static context labels listed in this file below.
     */
    protected LabelEn(String short_name, String name, LabelCategory category) { 
        super(short_name, name);
        initLabelAddedByHand(this);
        
        if(null == category)
            System.out.println("Error in LabelEn.LabelEn(): category is empty! label="+short_name+"; name=\'"+name+"\'; category=\'"+category.toString()+"\'.");
        
        this.category   = category; 
        label2category. put(this, category);
    }
    
    /** Constructor for new context labels which are extracted by parser 
     * from the template {{context|new label}} and added automatically,
     * these new labels are not (?) listed in the LabelEn,
     * but they are presented in TLabel.id2label.
     * 
     * @param short_name name of the found context label
     */
    public LabelEn(String short_name) { // , LabelCategory label_cat) { 
        super(short_name);
        initLabelAddedAutomatically(this);
        
        this.category   = null; // it is unknown; 
        //label2category. put(this, category);
    }
    
    public LabelEn(String short_name, LabelCategory label_cat) { 
        super(short_name);
        initLabelAddedAutomatically(this);
        
        this.category   = label_cat;
        label2category. put(this, category);
    }
    
    protected void initLabelAddedByHand(Label label) {
    
        if(null == label)
            System.out.println("Error in LabelEn.initLabelAddedByHand(): label is null, short_name="+short_name+"; name=\'"+name+"\'.");
        
        checksPrefixSuffixSpace(short_name);
        checksPrefixSuffixSpace(name);
        
        // check the uniqueness of the label short name and full name
        Label label_prev_by_short_name = short_name2label.get(short_name);
        Label label_prev_by_name       =       name2label.get(      name);
        
        if(null != label_prev_by_short_name)
            System.out.println("Error in LabelEn.initLabelAddedByHand(): duplication of label (short name)! short name='"+short_name+
                    "' name='"+name+"'. Check the maps short_name2label and name2label.");

        if(null != label_prev_by_name)
            System.out.println("Error in LabelEn.initLabelAddedByHand(): duplication of label (full name)! short_name='"+short_name+
                    "' name='"+name+ "'. Check the maps short_name2label and name2label.");
        
        short_name2label.put(short_name, label);
        label2short_name.put(label, short_name);
        
        name2label.put(name, label);
        label2name.put(label, name);
    };
    
    protected void initLabelAddedAutomatically(Label label) {
    
        if(null == label)
            System.out.println("Error in LabelEn.initLabelAddedAutomatically(): label is null, short_name="+short_name+".");
        
        checksPrefixSuffixSpace(short_name);
        
        // check the uniqueness of the label short name
        Label label_prev_by_short_name = short_name2label.get(short_name);
        
        if(null != label_prev_by_short_name)
            System.out.println("Error in LabelEn.initLabelAddedAutomatically(): duplication of label (short name)! short name='"+short_name+
                    ". Check the maps short_name2label.");
        
        short_name2label.put(short_name, label);
        label2short_name.put(label, short_name);
    };
    
    /** Gets English Wiktionary context label associated with this label. 
     * This function is needed for compatibility with other child class (LabelLocal.java) of Label.java */
    @Override 
    public LabelEn getLinkedLabelEn() {
        return this;
    }
    
    /** Gets label itself (short name) in English. 
     *  This functions is needed for comparison (equals()) with LabelLocal labels.
     */
    @Override
    public String getShortNameEnglish() {
        return getShortName();
    }
    
    /** Checks weather exists the Label (short name) by its name, checks synonyms also. */
    public static boolean hasShortName(String short_name) {
        return short_name2label.containsKey(short_name) || 
         multiple_synonym2label.containsKey(short_name);
    }
    
    /** Gets English Wiktionary context label associated with this label. 
     * This function is needed for compatibility with LabelLocal.java (other child class of Label.java). */
    //protected abstract Label getLinkedLabelEn();
    
    /** Gets label by short name of the label. */
    public static Label getByShortName(String short_name) throws NullPointerException
    {
        Label label;

        if(null != (label = short_name2label.get(short_name)))
            return  label;

        if(null != (label = multiple_synonym2label.get(short_name)))
            return  label;

        throw new NullPointerException("Exception (null pointer) in LabelEn.getByShortName(), label short_name="+ short_name);
    }
    
    /** Adds synonymic context label for the main (source) label.
     * @param label source main unique label
     * @param synonymic_label synonym of label (short name)
     */
    public static Label addNonUniqueShortName(Label label, String synonymic_short_name) {

        checksPrefixSuffixSpace(synonymic_short_name);
        if(synonymic_short_name.length() > 255) {
            System.out.println("Error in Label.addNonUniqueShortName(): the synonymic label='"+synonymic_short_name+
                    "' is too long (.length() > 255)!");
            return null;
        }

        if(short_name2label.containsKey(synonymic_short_name)) {
            System.out.println("Error in Label.addNonUniqueShortName(): the synonymic label '"+synonymic_short_name+
                    "' is already presented in the map label2name!");
            return null;
        }
        
        if(multiple_synonym2label.containsKey(synonymic_short_name)) {
            System.out.println("Error in Label.addNonUniqueShortName(): the synonymic label '"+synonymic_short_name+
                    "' is already presented in the map multiple_synonym2label!");
            return null;
        }
        
        multiple_synonym2label.put(synonymic_short_name, label);
        return label;
    }
    
    public static boolean hasName(String name) {
        return name2label.containsKey(name);
    }
    
    /** Gets label's category. */
    public LabelCategory getCategory() {        
        return category;
    }
    
    /** Gets label's category by label's name. */
    public static LabelCategory getCategoryByLabel(Label label_en) {        
        return label2category.get(label_en);
    }
    
    /** Gets all labels. */
    public static Collection<Label> getAllLabels() {
        return short_name2label.values();
    }
    
    /** Counts number of labels. */
    public static int size() {
        return short_name2label.size();
    }
    
    /** Gets all names of labels (short name). */
    public static Set<String> getAllLabelShortNames() {
        return short_name2label.keySet();
    }
    
    
    
    
    
    
    // Context labels without categories (empty category) 43 Krizhanovsky
    
//  item // it's not a context label
    public static final Label Rumantsch_Grischun = new LabelEn("Rumantsch Grischun", "Rumantsch Grischun", LabelCategory.root);
    
    
    // ///////////////////////////////////////////////////////////////////////////////////////
    // context label short, context label full name, Category of words with this context label
    
    public static final Label context = new LabelEn("context", "context", LabelCategory.qualifier); // meta context label will be treated in a special way. http://en.wiktionary.org/wiki/Template:context
     
    // grammatical 67-2 Krizhanovsky
    // //////////////////////////
    public static final Label abbreviation = new LabelEn("abbreviation", "abbreviation", LabelCategory.grammatical);
            // todo http://en.wiktionary.org/wiki/Template:abbreviation_of
    public static final Label adjectival = new LabelEn("adjectival", "adjectival", LabelCategory.grammatical); // ruwikt винительный падеж
    public static final Label adjective = new LabelEn("adjective", "adjective", LabelCategory.grammatical); // ruwikt прилагательное
    public static final Label adverb = new LabelEn("adverb", "adverb", LabelCategory.grammatical); // ruwikt form-of
    public static final Label ablative = new LabelEn("ablative", "ablative", LabelCategory.grammatical); // ruwikt исходный падеж
    public static final Label acronym = new LabelEn("acronym", "acronym", LabelCategory.grammatical);
    public static final Label accusative = new LabelEn("accusative", "accusative", LabelCategory.grammatical); // ruwikt адъективное
    public static final Label ambitransitive = new LabelEn("transitive, intransitive", "transitive, intransitive", LabelCategory.grammatical);
    public static final Label animate = new LabelEn("animate", "animate", LabelCategory.grammatical);
    public static final Label attributive = new LabelEn("attributive", "attributive", LabelCategory.grammatical); //определение
    public static final Label attributively = new LabelEn("attributively", "attributively", LabelCategory.grammatical);
    public static final Label auxiliary = new LabelEn("auxiliary", "auxiliary", LabelCategory.grammatical); //вспомогательный глагол
    public static final Label benedictive = new LabelEn("benedictive", "benedictive", LabelCategory.grammatical);
    public static final Label by_ellipsis = new LabelEn("by ellipsis", "by ellipsis", LabelCategory.grammatical); //под многоточием
    public static final Label cardinal = new LabelEn("cardinal", "cardinal", LabelCategory.grammatical); // количественное числительное
    public static final Label causative = new LabelEn("causative", "causative", LabelCategory.grammatical); //понудительный залог
    public static final Label collective = new LabelEn("collective", "collective", LabelCategory.grammatical); // ruwikt собирательное
    public static final Label common_gender = new LabelEn("common gender", "common gender", LabelCategory.grammatical); // ruwikt форма общего рода
    public static final Label comparable = new LabelEn("comparable", "comparable", LabelCategory.grammatical); //сравнимый
    public static final Label copulative = new LabelEn("copulative", "copulative", LabelCategory.grammatical); //соединительный союз
    public static final Label countable = new LabelEn("countable", "countable", LabelCategory.grammatical);
    public static final Label dative = new LabelEn("dative", "dative", LabelCategory.grammatical); // ruwikt дательный падеж
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
    public static final Label exclamatory = new LabelEn("exclamatory", "exclamatory", LabelCategory.grammatical); //ruwikt в восклицательных предложениях
    public static final Label feminine_formed_from_male = new LabelEn("feminine formed from male", "feminine formed from male", LabelCategory.grammatical); // ruwikt женского рода, образованные от мужского
    public static final Label feminine_gender = new LabelEn("feminine gender", "feminine gender", LabelCategory.grammatical); // ruwikt женский род
    public static final Label fractional = new LabelEn("fractional", "fractional", LabelCategory.grammatical); //дробный
    public static final Label frequentative = new LabelEn("frequentative", "frequentative", LabelCategory.grammatical);
    public static final Label generalized_abstract = new LabelEn("generalized abstract", "generalized abstract", LabelCategory.grammatical); // ruwikt обобщённое
    public static final Label genitive_case = new LabelEn("genitive case", "genitive case", LabelCategory.grammatical); // ruwikt родительный падеж
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
    public static final Label instrumental = new LabelEn("instrumental", "instrumental", LabelCategory.grammatical); // ruwikt творительный падеж
    public static final Label intensive = new LabelEn("intensive", "intensive", LabelCategory.grammatical); // ruwiktусилительный
    public static final Label interrogative = new LabelEn("interrogative", "interrogative", LabelCategory.grammatical); //вопросительный
    public static final Label intransitive = new LabelEn("intransitive", "intransitive", LabelCategory.grammatical);
    public static final Label locative = new LabelEn("locative", "locative", LabelCategory.grammatical); // ruwikt местный падеж
    // public static final Label la_proper_noun_indecl = new LabelEn("la-proper noun-indecl", "indeclinable", LabelCategory.grammatical); it is not a context label in really
    public static final Label masculine_gender = new LabelEn("masculine gender", "masculine gender", LabelCategory.grammatical); // ruwikt мужской род
    public static final Label momentane = new LabelEn("momentane", "momentane", LabelCategory.grammatical);
    public static final Label negative = new LabelEn("negative", "negative", LabelCategory.grammatical);
    public static final Label neuter_gender = new LabelEn("neuter gender", "neuter gender", LabelCategory.grammatical); // ruwikt средний род
    public static final Label nominative_case = new LabelEn("nominative case", "nominative case", LabelCategory.grammatical); // ruwikt именительный падеж
    public static final Label noun = new LabelEn("noun", "noun", LabelCategory.grammatical); // ruwikt существительное
 
    public static final Label not_comparable = new LabelEn("not comparable", "not comparable", LabelCategory.grammatical); //несравнимый 
    public static final Label notcomp = LabelEn.addNonUniqueShortName(not_comparable, "notcomp");

    public static final Label of_a_person = new LabelEn("of a person", "of a person", LabelCategory.grammatical);
    public static final Label onomatopoeia = new LabelEn("onomatopoeia", "onomatopoeia", LabelCategory.grammatical); //звукоподражание
    public static final Label ordinal = new LabelEn("ordinal", "ordinal", LabelCategory.grammatical); //порядковое числительное
    public static final Label parenthetical_word = new LabelEn("parenthetical word", "parenthetical word", LabelCategory.grammatical); // ruwikt вводное слово
    public static final Label partial = new LabelEn("partial", "partial", LabelCategory.root);//ruwikt частичн.
    public static final Label participle = new LabelEn("participle", "participle", LabelCategory.grammatical); //ruwikt form-of
    public static final Label passive = new LabelEn("passive", "passive", LabelCategory.grammatical);
    public static final Label past_tense = new LabelEn("past tense", "past tense", LabelCategory.grammatical); // ruwikt прошедшее время, прошедшего времени
    public static final Label personal = new LabelEn("personal", "personal", LabelCategory.grammatical);
    
    public static final Label plurale_tantum = new LabelEn("plurale tantum", "plural only", LabelCategory.grammatical);
    public static final Label pluralonly = LabelEn.addNonUniqueShortName(plurale_tantum, "pluralonly");

    public static final Label possessive = new LabelEn("possessive", "possessive", LabelCategory.grammatical); //притяжательный падеж
    public static final Label possessive_pronoun = LabelEn.addNonUniqueShortName(possessive, "possessive pronoun");

    public static final Label postpositive = new LabelEn("postpositive", "postpositive", LabelCategory.grammatical); //постпозитивный
    public static final Label predicate = new LabelEn("predicate", "predicate", LabelCategory.grammatical);
    public static final Label prepositional_case = new LabelEn("prepositional case", "prepositional case", LabelCategory.grammatical); // ruwikt предложный падеж
    public static final Label productive = new LabelEn("productive", "productive", LabelCategory.grammatical);
    public static final Label pronominal = new LabelEn("pronominal", "pronominal", LabelCategory.grammatical); //местоименный
    public static final Label reflexive = new LabelEn("reflexive", "reflexive", LabelCategory.grammatical); //возвратный 
    public static final Label relative = new LabelEn("relative", "relative", LabelCategory.grammatical); //относительное местоимение
    public static final Label rhetorical_question = new LabelEn("rhetorical question", "rhetorical question", LabelCategory.grammatical); //риторический вопрос
    public static final Label set_phrase = new LabelEn("set phrase", "set phrase", LabelCategory.grammatical); //словосочетание
    public static final Label simile = new LabelEn("simile", "simile", LabelCategory.grammatical); // сравнение
    public static final Label singular = new LabelEn("singular", "singular", LabelCategory.grammatical); // ruwikt единственное число
    
    public static final Label singulare_tantum = new LabelEn("singulare tantum", "singular only", LabelCategory.grammatical);
    public static final Label singularonly = LabelEn.addNonUniqueShortName(singulare_tantum, "singularonly");
 
    public static final Label substantivized = new LabelEn("substantivized", "substantivized", LabelCategory.grammatical); // ruwikt субстантивированное
    public static final Label transitive = new LabelEn("transitive", "transitive", LabelCategory.grammatical);
    public static final Label uncountable = new LabelEn("uncountable", "uncountable", LabelCategory.grammatical);
    public static final Label verb = new LabelEn("verb", "verb", LabelCategory.grammatical); // ruwikt глагол
        
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
    public static final Label regional = new LabelEn("regional", "regional", LabelCategory.regional); // meta regional context label
    
    public static final Label Acadia = new LabelEn("Acadia", "Acadian", LabelCategory.regional);
    public static final Label Adygei = new LabelEn("Adygei", "Adygei", LabelCategory.regional); // ruwikt адыгейское
    public static final Label Africa = new LabelEn("Africa", "Africa",    LabelCategory.regional);
    public static final Label Afrikaans = new LabelEn("Afrikaans", "Afrikaans",    LabelCategory.regional); // ruwikt африкаанс
    public static final Label African_American_Vernacular_English = new LabelEn("African American Vernacular English",     "African American Vernacular",    LabelCategory.regional);
    public static final Label Ainu = new LabelEn("Ainu", "Ainu",    LabelCategory.regional); // ruwikt айнское
    public static final Label Albania = new LabelEn("Albania", "Albania", LabelCategory.regional); // ruwikt албанское
    public static final Label Alberta = new LabelEn("Alberta", "Alberta", LabelCategory.regional);
    public static final Label Alemannic = new LabelEn("Alemannic", "Alemannic", LabelCategory.regional); // ruwikt алеманское
    public static final Label Algeria = new LabelEn("Algeria", "Algeria", LabelCategory.regional);
    public static final Label Alghero = new LabelEn("Alghero", "Alghero", LabelCategory.regional);
    public static final Label American_spelling = new LabelEn("American spelling", "American", LabelCategory.regional);
    public static final Label Anglo_Norman = new LabelEn("Anglo-Norman", "Anglo-Norman", LabelCategory.regional);
    public static final Label Angola = new LabelEn("Angola", "Angola", LabelCategory.regional);
    public static final Label Appalachian = new LabelEn("Appalachian", "Appalachian", LabelCategory.regional);
    public static final Label Arabic = new LabelEn("Arabic", "Arabic", LabelCategory.regional); // ruwikt арабское
    public static final Label Aragonese = new LabelEn("Aragonese", "Aragonese", LabelCategory.regional); // ruwikt арагонское
    public static final Label Argentina = new LabelEn("Argentina", "Argentina", LabelCategory.regional);
    public static final Label Armenia = new LabelEn("Armenia", "Armenia", LabelCategory.regional); // ruwikt армянское
    public static final Label Assam = new LabelEn("Assam", "Assam", LabelCategory.regional); // ruwikt ассамское
    public static final Label Asturias = new LabelEn("Asturias", "Asturias", LabelCategory.regional); // ruwikt астурийское
    public static final Label Atlantic_Canada = new LabelEn("Atlantic Canada", "Atlantic Canada", LabelCategory.regional);

    public static final Label Australia = new LabelEn("Australia", "Australia", LabelCategory.regional);
    public static final Label AU = LabelEn.addNonUniqueShortName(Australia, "AU");

    public static final Label Austria = new LabelEn("Austria", "Austria", LabelCategory.regional);
    public static final Label Aymara = new LabelEn("Aymara", "Aymara", LabelCategory.regional); // ruwikt аймарское
    public static final Label Azerbaijan = new LabelEn("Azerbaijan", "Azerbaijan", LabelCategory.regional); // ruwikt азейбарджанское

    public static final Label Balearics = new LabelEn("Balearics", "Balearics", LabelCategory.regional);
    public static final Label Balhae = new LabelEn("Balhae", "Balhae", LabelCategory.regional);
    public static final Label Balkar = new LabelEn("Balkar", "Balkar", LabelCategory.regional);
    public static final Label Bashkiria = new LabelEn("Bashkiria", "Bashkiria", LabelCategory.regional); // ruwikt башкирское
    public static final Label Basque = new LabelEn("Basque", "Basque", LabelCategory.regional); // ruwikt баскское
    public static final Label Belarus = new LabelEn("Belarus", "Belarus", LabelCategory.regional); // ruwikt белорусское
    public static final Label Belgium = new LabelEn("Belgium", "Belgium", LabelCategory.regional);
    public static final Label Belize = new LabelEn("Belize", "Belize",  LabelCategory.regional);
    public static final Label Bengal = new LabelEn("Bengal", "Bengal",  LabelCategory.regional); // ruwikt бенгальское
    public static final Label Bolivia = new LabelEn("Bolivia", "Bolivia", LabelCategory.regional);
    public static final Label Border_Scots = new LabelEn("Border Scots", "Border Scots", LabelCategory.regional);
    public static final Label Bosnia = new LabelEn("Bosnia", "Bosnia", LabelCategory.regional);
    public static final Label Brabant = new LabelEn("Brabant", "Brabant", LabelCategory.regional);
    public static final Label Brazil = new LabelEn("Brazil", "Brazil", LabelCategory.regional);
    public static final Label Breton = new LabelEn("Breton", "Breton", LabelCategory.regional); // ruwikt бретонское
    public static final Label Bristol = new LabelEn("Bristol", "Bristolian", LabelCategory.regional);

    public static final Label British = new LabelEn("British", "UK", LabelCategory.regional);
    public static final Label UK = LabelEn.addNonUniqueShortName(British, "UK");

    public static final Label British_Columbia = new LabelEn("British Columbia", "British Columbia", LabelCategory.regional);
    public static final Label British_spelling = new LabelEn("British spelling", "British", LabelCategory.regional);
    public static final Label Brunei = new LabelEn("Brunei", "Brunei", LabelCategory.regional);
    public static final Label Bulgaria = new LabelEn("Bulgaria", "Bulgaria",  LabelCategory.regional); // ruwikt болгарское
    public static final Label Buryat = new LabelEn("Buryat", "Buryat",  LabelCategory.regional); // ruwikt бурятское

    public static final Label Canada = new LabelEn("Canada", "Canada", LabelCategory.regional);
    public static final Label Canadian_Prairies = new LabelEn("Canadian Prairies", "Canadian Prairies", LabelCategory.regional);
    public static final Label Caribbean = new LabelEn("Caribbean", "Caribbean", LabelCategory.regional);
    public static final Label Chakavian = new LabelEn("Chakavian", "Chakavian", LabelCategory.regional);
    public static final Label Channel_Islands = new LabelEn("Channel Islands", "Channel Islands", LabelCategory.regional);
    public static final Label Chechen_Republic = new LabelEn("Chechen Republic", "Chechen Republic",  LabelCategory.regional); // ruwikt чеченское
    public static final Label Church_Slavonic = new LabelEn("Church Slavonic", "Church Slavonic", LabelCategory.regional); // ruwikt церковно-славянское
    public static final Label Chile = new LabelEn("Chile", "Chile", LabelCategory.regional);
    public static final Label China = new LabelEn("China", "China", LabelCategory.regional);
    public static final Label Chinglish = new LabelEn("Chinglish", "Chinglish", LabelCategory.regional);
    public static final Label Chuvashia = new LabelEn("Chuvashia", "Chuvashia",  LabelCategory.regional); // ruwikt чувашское
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
    public static final Label Czech_Republic = new LabelEn("Czech Republic", "Czech Republic",  LabelCategory.regional); // ruwikt чешское

    public static final Label Dari = new LabelEn("Dari", "Dari (Afghanistan)", LabelCategory.regional);
    public static final Label DDR = new LabelEn("DDR", "East Germany", LabelCategory.regional);
    public static final Label Denmark = new LabelEn("Denmark", "Denmark", LabelCategory.regional); //ruwikt датское
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
    public static final Label Erzya = new LabelEn("Erzya", "Erzya", LabelCategory.regional); //ruwikt эрзянское
    public static final Label Estonia = new LabelEn("Estonia", "Estonia", LabelCategory.regional); //ruwikt эстонское
    public static final Label Europe = new LabelEn("Europe", "Europe", LabelCategory.regional);
    public static final Label Evenki = new LabelEn("Evenki", "Evenki", LabelCategory.regional); //ruwikt эвенкийское
    public static final Label Even = new LabelEn("Even", "Even", LabelCategory.regional); //ruwikt эвенское

    public static final Label Faroese = new LabelEn("Faroese", "Faroese", LabelCategory.regional); //ruwikt фарерское
    public static final Label Fiji = new LabelEn("Fiji", "Fiji", LabelCategory.regional); //ruwikt фиджийское
    public static final Label Finland = new LabelEn("Finland", "Finland", LabelCategory.regional);
    public static final Label Flemish = new LabelEn("Flemish", "Flemish", LabelCategory.regional);
    public static final Label Frisia = new LabelEn("Frisia", "Frisia", LabelCategory.regional); //ruwikt фризское
    public static final Label Friuli = new LabelEn("Friuli", "Friuli", LabelCategory.regional); //ruwikt фриульское
    public static final Label France = new LabelEn("France", "France", LabelCategory.regional);
    public static final Label Gascony = new LabelEn("Gascony", "Gascony", LabelCategory.regional);
    public static final Label Geordie = new LabelEn("Geordie", "Geordie", LabelCategory.regional);
    public static final Label Germany = new LabelEn("Germany", "Germany", LabelCategory.regional);// only in ruwikt
    public static final Label Gheg = new LabelEn("Gheg", "Gheg", LabelCategory.regional);
    public static final Label Guardiol = new LabelEn("Guardiol", "Guardiol", LabelCategory.regional);
    public static final Label Guatemala = new LabelEn("Guatemala", "Guatemala", LabelCategory.regional);
    public static final Label Guernsey = new LabelEn("Guernsey", "Guernsey", LabelCategory.regional);
    public static final Label Greece = new LabelEn("Greece", "Greece", LabelCategory.regional); // ruwikt греческое

    public static final Label Hartlepool = new LabelEn("Hartlepool", "Hartlepool", LabelCategory.regional);
    public static final Label Hawaii = new LabelEn("Hawaii", "Hawaii", LabelCategory.regional);
    public static final Label Helsinki_slang = new LabelEn("Helsinki slang", "Helsinki slang", LabelCategory.regional);
    public static final Label Hittite = new LabelEn("Hittite", "Hittite", LabelCategory.regional); // ruwikt хеттское
    public static final Label Hoisanese = new LabelEn("Hoisanese", "Hoisanese", LabelCategory.regional);
    public static final Label Hollandic = new LabelEn("Hollandic", "Hollandic", LabelCategory.regional);
    public static final Label Honduras = new LabelEn("Honduras", "Honduras", LabelCategory.regional);
    public static final Label Hong_Kong = new LabelEn("Hong Kong", "Hong Kong", LabelCategory.regional);
    public static final Label Hulu_Pahang = new LabelEn("Hulu Pahang", "Hulu Pahang", LabelCategory.regional);
    public static final Label Hungary = new LabelEn("Hungary", "Hungary", LabelCategory.regional); // ruwikt венгерское

    public static final Label Iceland = new LabelEn("Iceland", "Iceland", LabelCategory.regional); // ruwikt исландское
    public static final Label Ijekavian = new LabelEn("Ijekavian", "Ijekavian", LabelCategory.regional);
    public static final Label Ikavian = new LabelEn("Ikavian", "Ikavian", LabelCategory.regional);
    public static final Label India = new LabelEn("India", "India", LabelCategory.regional);
    public static final Label Indonesia = new LabelEn("Indonesia", "Indonesia", LabelCategory.regional);
    public static final Label Ionic_Greek = new LabelEn("Ionic Greek", "Ionic Greek", LabelCategory.regional);// only in ruwikt
    public static final Label Iran = new LabelEn("Iran", "Iran", LabelCategory.regional);
    public static final Label Ireland = new LabelEn("Ireland", "Ireland", LabelCategory.regional);
    public static final Label Iron = new LabelEn("Iron", "Iron dialect", LabelCategory.regional);
    public static final Label Isle_of_Mann = new LabelEn("Isle of Mann", "Manx", LabelCategory.regional);
    public static final Label Italy = new LabelEn("Italy", "Italy", LabelCategory.regional); // ruwikt итальянское

    public static final Label Jakarta = new LabelEn("Jakarta", "Jakarta", LabelCategory.regional);
    public static final Label Jamaica = new LabelEn("Jamaica", "Jamaica", LabelCategory.regional);
    public static final Label Japan = new LabelEn("Japan", "Japan", LabelCategory.regional);// only in ruwikt
    public static final Label Javanese = new LabelEn("Javanese", "Java", LabelCategory.regional);
    public static final Label Jersey = new LabelEn("Jersey", "Jersey", LabelCategory.regional);

    public static final Label Kajkavian = new LabelEn("Kajkavian", "Kajkavian", LabelCategory.regional);
    public static final Label Kansai = new LabelEn("Kansai", "Kansai", LabelCategory.regional);
    public static final Label Karabakh = new LabelEn("Karabakh", "Karabakh", LabelCategory.regional);
    public static final Label Karachay = new LabelEn("Karachay", "Karachay", LabelCategory.regional);
    public static final Label Karelia = new LabelEn("Karelia", "Karelia", LabelCategory.regional); // ruwikt карельское
    public static final Label Kashmiri = new LabelEn("Kashmiri", "Kashmiri", LabelCategory.regional); // ruwikt кашмири
    public static final Label Kashubian = new LabelEn("Kashubian", "Kashubian", LabelCategory.regional); // ruwikt кашубское
    public static final Label katharevousa = new LabelEn("katharevousa", "Katharevousa", LabelCategory.regional);
    public static final Label Kazakhstan = new LabelEn("Kazakhstan", "Kazakhstan", LabelCategory.regional); // ruwikt казахское
    public static final Label Kenya = new LabelEn("Kenya", "Kenya", LabelCategory.regional);
    public static final Label Khakassia = new LabelEn("Khakassia", "Khakassia", LabelCategory.regional); // ruwikt хакасское
    public static final Label Khmer = new LabelEn("Khmer", "Khmer", LabelCategory.regional); // ruwikt кхмерское
    public static final Label Korea = new LabelEn("Korea", "Korea", LabelCategory.regional); // ruwikt корейское
    public static final Label Kromanti = new LabelEn("Kromanti", "Kromanti spirit possession language", LabelCategory.regional);
    public static final Label Kuban = new LabelEn("Kuban", "Kuban", LabelCategory.regional); // ruwikt кубанское
    public static final Label Kurdish = new LabelEn("Kurdish", "Kurdish", LabelCategory.regional); // ruwikt курдское
    public static final Label Kyrgyzstan = new LabelEn("Kyrgyzstan", "Kyrgyzstan", LabelCategory.regional); // ruwikt киргизское

    public static final Label Lak = new LabelEn("Lak", "Lak", LabelCategory.regional); // ruwikt лакское
    public static final Label Lancashire = new LabelEn("Lancashire", "Lancashire", LabelCategory.regional);
    public static final Label Languedoc = new LabelEn("Languedoc", "Languedoc", LabelCategory.regional);
    public static final Label Laos = new LabelEn("Laos", "Laos", LabelCategory.regional); // ruwikt лаосское
    public static final Label Latgale = new LabelEn("Latgale", "Latgale", LabelCategory.regional); // ruwikt латгальское
    public static final Label Latin = new LabelEn("Latin", "Latin", LabelCategory.regional); // ruwikt латинское
    public static final Label Latin_America = new LabelEn("Latin America", "Latin America", LabelCategory.regional);
    public static final Label Latvia = new LabelEn("Latvia", "Latvia", LabelCategory.regional); // ruwikt латышское
    public static final Label Limousin = new LabelEn("Limousin", "Limousin", LabelCategory.regional);
    public static final Label Lincolnshire = new LabelEn("Lincolnshire", "Lincolnshire", LabelCategory.regional);
    public static final Label Lithuania = new LabelEn("Lithuania", "Lithuania", LabelCategory.regional); // ruwikt литовское
    public static final Label Liverpool = new LabelEn("Liverpool", "Liverpudlian", LabelCategory.regional);
    public static final Label London = new LabelEn("London", "London", LabelCategory.regional);
    public static final Label Louisiana_French = new LabelEn("Louisiana French", "Louisiana French", LabelCategory.regional);
    public static final Label Low_Prussian = new LabelEn("Low Prussian", "Low Prussian", LabelCategory.regional);
    public static final Label Lowlands_Scots = new LabelEn("Lowlands Scots", "Lowlands Scots", LabelCategory.regional);
    public static final Label Luxembourg = new LabelEn("Luxembourg", "Luxembourg", LabelCategory.regional);
    public static final Label Lviv = new LabelEn("Lviv", "Lviv", LabelCategory.regional);
    public static final Label Lusatia = new LabelEn("Lusatia", "Lusatia", LabelCategory.regional); // ruwikt лужицкое

    public static final Label Macedonia = new LabelEn("Macedonia", "Macedonia", LabelCategory.regional); // ruwikt македонское
    public static final Label Malagasy = new LabelEn("Malagasy", "Malagasy", LabelCategory.regional); // ruwikt малагасийское
    public static final Label Malayeri = new LabelEn("Malayeri", "Malayeri", LabelCategory.regional);
    public static final Label Malaysia = new LabelEn("Malaysia", "Malaysia", LabelCategory.regional);
    public static final Label Mallorca = new LabelEn("Mallorca", "Mallorca", LabelCategory.regional);
    public static final Label Malta = new LabelEn("Malta", "Malta", LabelCategory.regional); // ruwikt мальтийское
    public static final Label maniot = new LabelEn("maniot", "Maniot dialect", LabelCategory.regional);
    public static final Label Manitoba = new LabelEn("Manitoba", "Manitoba", LabelCategory.regional);
    public static final Label Marseille = new LabelEn("Marseille", "Marseille", LabelCategory.regional);
    public static final Label Mecayapan = new LabelEn("Mecayapan", "Mecayapan", LabelCategory.regional);
    public static final Label Mecklenburgisch_Low_German = new LabelEn("Mecklenburgisch Low German", "Mecklenburgisch", LabelCategory.regional);
    public static final Label Megrelia = new LabelEn("Megrelia", "Megrelia", LabelCategory.regional); // ruwikt мегрельское
    public static final Label Mexico = new LabelEn("Mexico", "Mexico", LabelCategory.regional);
    public static final Label Midwest_US = new LabelEn("Midwest US", "Midwest US", LabelCategory.regional);
    public static final Label Mistralian = new LabelEn("Mistralian", "Mistralian", LabelCategory.regional);
    public static final Label Moldavia = new LabelEn("Moldavia", "Moldavia", LabelCategory.regional); // ruwikt молдавское
    public static final Label Mon = new LabelEn("Mon", "Mon", LabelCategory.regional); // ruwikt монское
    public static final Label Mongolia = new LabelEn("Mongolia", "Mongolia", LabelCategory.regional); // ruwikt монгольское
    public static final Label Montenegro = new LabelEn("Montenegro", "Montenegro", LabelCategory.regional);
    public static final Label Morocco = new LabelEn("Morocco", "Morocco", LabelCategory.regional);
    public static final Label Multicultural_London_English = new LabelEn("Multicultural London English", "MLE", LabelCategory.regional);
    public static final Label Munster = new LabelEn("Munster", "Munster", LabelCategory.regional);

    public static final Label Namibia = new LabelEn("Namibia", "Namibia", LabelCategory.regional);
    public static final Label Naples = new LabelEn("Naples", "Naples", LabelCategory.regional); // ruwikt неаполитанское
    public static final Label Negeri_Sembilan = new LabelEn("Negeri Sembilan", "Negeri Sembilan", LabelCategory.regional);
    public static final Label Nenets = new LabelEn("Nenets", "Nenets", LabelCategory.regional); // ruwikt ненецкое
    public static final Label Nepal = new LabelEn("Nepal", "Nepal", LabelCategory.regional); // ruwikt непальское
    public static final Label Nepal_Bhasa = new LabelEn("Nepal Bhasa", "Nepal Bhasa", LabelCategory.regional); // ruwikt неварское
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
    public static final Label Norway = new LabelEn("Norway", "Norway", LabelCategory.regional); // ruwikt норвежское
    public static final Label Nova_Scotia = new LabelEn("Nova Scotia", "Nova Scotia", LabelCategory.regional);
    public static final Label Nunavut = new LabelEn("Nunavut", "Nunavut", LabelCategory.regional);

    public static final Label Occitania = new LabelEn("Occitania", "Occitania", LabelCategory.regional); // ruwikt окситанское
    public static final Label Old_High_German = new LabelEn("Old High German", "Old High German", LabelCategory.regional); // ruwikt древневерхненемецкое
    public static final Label Old_Prussian = new LabelEn("Old Prussian", "Old Prussian", LabelCategory.regional); // ruwikt древнепрусское
    public static final Label Ontario = new LabelEn("Ontario", "Ontario", LabelCategory.regional);
    public static final Label Ossetia = new LabelEn("Ossetia", "Ossetia", LabelCategory.regional); // ruwikt осетинское

    public static final Label Pahang = new LabelEn("Pahang", "Pahang", LabelCategory.regional);
    public static final Label Pakistan = new LabelEn("Pakistan", "Pakistan", LabelCategory.regional);
    public static final Label Paraguay = new LabelEn("Paraguay", "Paraguay", LabelCategory.regional);
    public static final Label Pennsylvania_Dutch_English = new LabelEn("Pennsylvania Dutch English", "Pennsylvania Dutch English", LabelCategory.regional);
    public static final Label Perak = new LabelEn("Perak", "Perak", LabelCategory.regional);
    public static final Label Persian = new LabelEn("Persian", "Persian", LabelCategory.regional); // ruwikt персидское
    public static final Label Peru = new LabelEn("Peru", "Peru", LabelCategory.regional);
    public static final Label Philippines = new LabelEn("Philippines", "Philippines", LabelCategory.regional);
    public static final Label Picardy = new LabelEn("Picardy", "Picardy", LabelCategory.regional);
    public static final Label Polabian = new LabelEn("Polabian", "Polabian", LabelCategory.regional); // ruwikt полабское
    public static final Label Poland = new LabelEn("Poland", "Poland", LabelCategory.regional); // ruwikt польское
    public static final Label Polari = new LabelEn("Polari", "Polari", LabelCategory.regional);
    public static final Label Pomeranian_Low_German = new LabelEn("Pomeranian Low German", "Pomeranian", LabelCategory.regional);
    public static final Label pontian = new LabelEn("pontian", "Pontian dialect", LabelCategory.regional);
    public static final Label Portugal = new LabelEn("Portugal", "Portugal", LabelCategory.regional);
    public static final Label Prince_Edward_Island = new LabelEn("Prince Edward Island", "Prince Edward Island", LabelCategory.regional);
    public static final Label Provence = new LabelEn("Provence", "Provence", LabelCategory.regional);
    public static final Label Prussia= new LabelEn("Prussia", "Prussia", LabelCategory.regional); // ruwikt прусское
    public static final Label Punjab= new LabelEn("Punjab", "Punjab", LabelCategory.regional); // ruwikt панджабское
    public static final Label Puter = new LabelEn("Puter", "Puter", LabelCategory.regional);

    public static final Label Quebec = new LabelEn("Quebec", "Quebec", LabelCategory.regional);

    public static final Label Rio_de_Janeiro = new LabelEn("Rio de Janeiro", "Rio de Janeiro city", LabelCategory.regional);
    public static final Label Réunion = new LabelEn("Réunion", "Réunion", LabelCategory.regional);
    public static final Label Romani = new LabelEn("Romani", "Romani", LabelCategory.regional); // ruwikt цыганское
    public static final Label Romania = new LabelEn("Romania", "Romania", LabelCategory.regional); // ruwikt румынское
    public static final Label Russia = new LabelEn("Russia", "Russia", LabelCategory.regional); // ruwikt русское
    
    public static final Label Saint_Ouen = new LabelEn("Saint Ouen", "Saint Ouën", LabelCategory.regional);
    public static final Label Sakha_Republic = new LabelEn("Sakha Republic", "Sakha (Yakutia) Republic", LabelCategory.regional); // ruwikt якутское
    public static final Label Samogitia = new LabelEn("Samogitia", "Samogitia", LabelCategory.regional); // ruwikt жемайтское
    public static final Label Sanskrit = new LabelEn("Sanskrit", "Sanskrit", LabelCategory.regional); // ruwikt санскритское
    public static final Label Saskatchewan = new LabelEn("Saskatchewan", "Saskatchewan", LabelCategory.regional);
    public static final Label Scania = new LabelEn("Scania", "Scanian", LabelCategory.regional);
    public static final Label Scotland = new LabelEn("Scotland", "Scotland", LabelCategory.regional);
    public static final Label Serbia = new LabelEn("Serbia", "Serbia", LabelCategory.regional);
    public static final Label Serbo_Croat = new LabelEn("Serbo-Croat", "Serbo-Croat", LabelCategory.regional); // ruwikt сербохорватское
    public static final Label Sherpa = new LabelEn("Sherpa", "Sherpa", LabelCategory.regional); // ruwikt шерпское
    public static final Label Shopski = new LabelEn("Shopski", "Shopski dialect", LabelCategory.regional);
    public static final Label Shor = new LabelEn("Shor", "Shor", LabelCategory.regional); // ruwikt шорское
    public static final Label Silesia = new LabelEn("Silesia", "Silesia", LabelCategory.regional); // ruwikt силезское
    public static final Label Silla = new LabelEn("Silla", "Silla", LabelCategory.regional);
    public static final Label Singapore = new LabelEn("Singapore", "Singapore", LabelCategory.regional);
    public static final Label Sistani = new LabelEn("Sistani", "Sistani", LabelCategory.regional);
    public static final Label Skiri_Pawnee = new LabelEn("Skiri Pawnee", "Skiri Pawnee", LabelCategory.regional);
    public static final Label Slovakia = new LabelEn("Slovakia", "Slovakia", LabelCategory.regional); // ruwikt словацкое
    public static final Label Slovenia = new LabelEn("Slovenia", "Slovenia", LabelCategory.regional); // ruwikt словенское
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

    public static final Label Tabasaran = new LabelEn("Tabasaran", "Tabasaran", LabelCategory.regional); // ruwikt табасаранское
    public static final Label Tagalog = new LabelEn("Tagalog", "Tagalog", LabelCategory.regional); // ruwikt тагальское
    public static final Label Taiwan = new LabelEn("Taiwan", "Taiwan", LabelCategory.regional);
    public static final Label Tajikistan = new LabelEn("Tajikistan", "Tajikistan", LabelCategory.regional); // ruwikt таджикское
    public static final Label Tamil = new LabelEn("Tamil", "Tamil", LabelCategory.regional); // ruwikt тамильское
    public static final Label Tatarstan = new LabelEn("Tatarstan", "Tatarstan", LabelCategory.regional); // ruwikt татарское
    public static final Label Tat = new LabelEn("Tat", "Tat", LabelCategory.regional); // ruwikt татское
    public static final Label Teesside = new LabelEn("Teesside", "Teesside", LabelCategory.regional);
    public static final Label Thai = new LabelEn("Thai", "Thai", LabelCategory.regional); // ruwikt тайское
    public static final Label Tigranakert = new LabelEn("Tigranakert", "Tigranakert", LabelCategory.regional);
    public static final Label Tosk = new LabelEn("Tosk", "Tosk", LabelCategory.regional);
    public static final Label Trinidad_and_Tobago = new LabelEn("Trinidad and Tobago", "Trinidad and Tobago", LabelCategory.regional);
    public static final Label Turkey = new LabelEn("Turkey", "Turkey", LabelCategory.regional); // ruwikt турецкое
    public static final Label Turkic = new LabelEn("Turkic", "Turkic", LabelCategory.regional); // ruwikt тюркское
    public static final Label Turkmenistan = new LabelEn("Turkmenistan", "Turkmenistan", LabelCategory.regional); // ruwikt туркменское
    public static final Label Tyneside = new LabelEn("Tyneside", "Tyneside", LabelCategory.regional);

    public static final Label Udmurtia = new LabelEn("Udmurtia", "Udmurtia", LabelCategory.regional); // ruwikt удмуртское
    public static final Label uds = new LabelEn("uds.", "used formally in Spain", LabelCategory.regional);
    public static final Label Ukraine = new LabelEn("Ukraine", "Ukraine", LabelCategory.regional); // ruwikt украинское
    public static final Label Ullans = new LabelEn("Ullans", "Ulster Scots", LabelCategory.regional);
    public static final Label Ulster = new LabelEn("Ulster", "Ulster", LabelCategory.regional);
    public static final Label Uruguay = new LabelEn("Uruguay", "Uruguay", LabelCategory.regional);
    public static final Label US = new LabelEn("US", "US", LabelCategory.regional);
    public static final Label Uyghur = new LabelEn("Uyghur", "Uyghur", LabelCategory.regional); // ruwikt уйгурское
    public static final Label Uzbekistan = new LabelEn("Uzbekistan", "Uzbekistan", LabelCategory.regional); // ruwikt узбекское

    public static final Label Valencia = new LabelEn("Valencia", "Valencia", LabelCategory.regional);
    public static final Label Vallader = new LabelEn("Vallader", "Vallader", LabelCategory.regional);
    public static final Label Venezuela = new LabelEn("Venezuela", "Venezuela", LabelCategory.regional);
    public static final Label Venice = new LabelEn("Venice", "Venice", LabelCategory.regional);
    public static final Label Veps = new LabelEn("Veps", "Veps", LabelCategory.regional); // ruwikt вепсское
    public static final Label Vivaro_Alpine = new LabelEn("Vivaro-Alpine", "Vivaro-Alpine", LabelCategory.regional);

    public static final Label Wales = new LabelEn("Wales", "Wales", LabelCategory.regional);
    public static final Label Walloon = new LabelEn("Walloon", "Walloon", LabelCategory.regional); // ruwikt валлонское
    public static final Label Waray = new LabelEn("Waray", "Waray", LabelCategory.regional); // ruwikt варайское
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
    public static final Label Yoruba = new LabelEn("Yoruba", "Yoruba", LabelCategory.regional); // ruwikt йоруба
    public static final Label Yukon = new LabelEn("Yukon", "Yukon", LabelCategory.regional);

    public static final Label Zimbabwe = new LabelEn("Zimbabwe", "Zimbabwe", LabelCategory.regional);
    

    // usage 73-3+1 Krizhanovsky
    // //////////////////////////
    public static final Label abusive = new LabelEn("abusive",  "abusive", LabelCategory.usage); // ruwikt бранное
    public static final Label acerbity = new LabelEn("acerbity",  "acerbity", LabelCategory.usage); // ruwikt грубое
    public static final Label ad_slang = new LabelEn("ad slang",  "advertising slang", LabelCategory.usage);
    public static final Label approving = new LabelEn("approving", "approving", LabelCategory.usage); //ruwikt одобр.
    public static final Label augmentative = new LabelEn("augmentative",  "augmentative", LabelCategory.usage); // ruwikt увеличительное
    public static final Label Australian_slang = new LabelEn("Australian slang",  "Australian slang", LabelCategory.usage);
    public static final Label avoidance = new LabelEn("avoidance",  "avoidance term", LabelCategory.usage);
    public static final Label bombast = new LabelEn("bombast",  "bombast", LabelCategory.usage); // ruwikt высокопарное
    public static final Label British_slang = new LabelEn("British slang",  "British slang", LabelCategory.usage);
    public static final Label beaurocratic = new LabelEn("beaurocratic",  "beaurocratic", LabelCategory.usage); // ruwikt канцелярское
    public static final Label buzzword = new LabelEn("buzzword",  "buzzword", LabelCategory.usage);
    public static final Label by_extension = new LabelEn("by extension",  "by extension", LabelCategory.usage);
    public static final Label cacography = new LabelEn("cacography",  "cacography", LabelCategory.usage); // ruwikt эрративное (перевод посредством перехода с русской вики на английскую, в словарях не нашел)
    public static final Label cant = new LabelEn("cant",  "cant", LabelCategory.usage); // косяк, жаргон
    public static final Label capitalized = new LabelEn("capitalized",  "capitalized", LabelCategory.usage); // с большой буквы?
    public static final Label childish = new LabelEn("childish",  "childish", LabelCategory.usage);
    public static final Label chu_Nom = new LabelEn("chu Nom",  "Vietnamese chữ Nôm", LabelCategory.usage);
    public static final Label Classic_1811 = new LabelEn("Classic 1811 Dictionary of the Vulgar Tongue",  "obsolete, slang", LabelCategory.usage);
    
    public static final Label colloquial = new LabelEn("colloquial",  "colloquial", LabelCategory.usage);
    public static final Label colloquial_um = LabelEn.addNonUniqueShortName(colloquial, "colloquial-um");
    public static final Label colloquial_un = LabelEn.addNonUniqueShortName(colloquial, "colloquial-un");
    
    public static final Label computerese = new LabelEn("computerese",  "computerese", LabelCategory.usage); // ruwikt компьютерный жаргон    
    public static final Label contemptuous = new LabelEn("contemptuous",  "contemptuous", LabelCategory.usage); // ruwikt презрительное
    public static final Label corroborative = new LabelEn("corroborative",  "corroborative", LabelCategory.usage); // ruwikt усилительное
    public static final Label derogatory = new LabelEn("derogatory",  "derogatory", LabelCategory.usage);
    
    public static final Label dialect = new LabelEn("dialect",  "dialect", LabelCategory.usage);
    public static final Label dialectal = LabelEn.addNonUniqueShortName(dialect, "dialectal");
    public static final Label dialectal_n = LabelEn.addNonUniqueShortName(dialect, "dialectal_n");
    public static final Label dialects = LabelEn.addNonUniqueShortName(dialect, "dialects");
    
    public static final Label diminutive = new LabelEn("diminutive",  "diminutive", LabelCategory.usage); // ruwikt уменьшительное
    public static final Label diminutive_hypocoristic = new LabelEn("diminutive hypocoristic",  "diminutive hypocoristic", LabelCategory.usage); // ruwikt уменьшительно-ласкательное
    public static final Label dismissal = new LabelEn("dismissal",  "dismissal", LabelCategory.usage);
    public static final Label distorted = new LabelEn("distorted",  "distorted", LabelCategory.usage); // ruwikt искажённое
    public static final Label dysphemism = new LabelEn("dysphemism",  "dysphemism", LabelCategory.usage); // ruwikt дисфемизм
    public static final Label endearing = new LabelEn("endearing",  "endearing", LabelCategory.usage);
    public static final Label ethnic_slur = new LabelEn("ethnic slur",  "ethnic slur", LabelCategory.usage);
    public static final Label euphemistic = new LabelEn("euphemistic",  "euphemistic", LabelCategory.usage);
    public static final Label expressive = new LabelEn("expressive",  "expressive", LabelCategory.usage); // ruwikt экспрессивное
    public static final Label familiar = new LabelEn("familiar",  "familiar", LabelCategory.usage);
    public static final Label fandom_slang = new LabelEn("fandom slang",  "fandom slang", LabelCategory.usage);
    
    public static final Label figuratively = new LabelEn("figuratively", "figuratively", LabelCategory.usage);
    // la-conj-form-gloss/iacio/context6 -> figuratively // strange context label which will skipped by parser
    
    public static final Label folk_colloquial = new LabelEn("folk colloquial",  "folk colloquial", LabelCategory.usage); // ruwikt народно-разговорное
    public static final Label folk_poetic = new LabelEn("folk poetic",  "folk poetic", LabelCategory.usage); // ruwikt народно-поэтическое
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
    public static final Label low_style = new LabelEn("low style",  "low style", LabelCategory.usage); // ruwikt сниженное (низкий стиль)
    public static final Label Lubunyaca = new LabelEn("Lubunyaca",  "Lubunyaca", LabelCategory.usage);
    public static final Label medical_slang = new LabelEn("medical slang",  "medical slang", LabelCategory.usage);
    public static final Label metonymy = new LabelEn("metonymy",  "metonymically", LabelCategory.usage);
    public static final Label miguxês = new LabelEn("miguxês",  "miguxês", LabelCategory.usage);
    public static final Label military_slang = new LabelEn("military slang",  "military slang", LabelCategory.usage);
    public static final Label nonce = new LabelEn("nonce",  "nonce word", LabelCategory.usage);
    public static final Label nonstandard = new LabelEn("nonstandard",  "nonstandard", LabelCategory.usage);
    public static final Label obscene_language = new LabelEn("obscene language",  "obscene language", LabelCategory.usage); // ruwikt мат
    public static final Label offensive = new LabelEn("offensive",  "offensive", LabelCategory.usage); // оскорбительный, агрессивный
    public static final Label others = new LabelEn("others",  "others", LabelCategory.usage); // ruwikt прочее
    public static final Label pejorative = new LabelEn("pejorative", "pejorative", LabelCategory.usage);
    public static final Label poetic = new LabelEn("poetic", "poetic", LabelCategory.usage);
    public static final Label polite = new LabelEn("polite",  "polite", LabelCategory.usage);  // вежливый
    public static final Label politically_correct = new LabelEn("politically correct",  "politically correct", LabelCategory.usage);
    public static final Label popular_language = new LabelEn("popular language", "popular language", LabelCategory.usage); // ruwikt просторечное
    public static final Label proscribed = new LabelEn("proscribed",  "proscribed", LabelCategory.usage);
    public static final Label radio_slang = new LabelEn("radio slang",  "radio slang", LabelCategory.usage);
    
    public static final Label rare = new LabelEn("rare",  "rare", LabelCategory.usage);
    public static final Label rarely = LabelEn.addNonUniqueShortName(rare, "rarely");
    public static final Label rare_sense = LabelEn.addNonUniqueShortName(rare, "rare sense");
    public static final Label rare_term = LabelEn.addNonUniqueShortName(rare, "rare term");
    
    public static final Label reproach = new LabelEn("reproach",  "reproach", LabelCategory.usage); // ruwikt укорительное
    public static final Label retronym = new LabelEn("retronym",  "retronym", LabelCategory.usage);
    public static final Label rfd_redundant = new LabelEn("rfd-redundant",  "rfd-redundant", LabelCategory.usage);
    public static final Label rfv_sense = new LabelEn("rfv-sense",  "rfv-sense", LabelCategory.usage);
    
    public static final Label sarcastic = new LabelEn("sarcastic",  "sarcastic", LabelCategory.usage);
    public static final Label ironic = LabelEn.addNonUniqueShortName(sarcastic, "ironic");
    public static final Label sarcasm = LabelEn.addNonUniqueShortName(sarcastic, "sarcasm");
    
    public static final Label scornful = new LabelEn("scornful",  "scornful", LabelCategory.usage); // ruwikt пренебрежительное
    public static final Label slang = new LabelEn("slang",  "slang", LabelCategory.usage);
    public static final Label solemn = new LabelEn("solemn",  "solemn", LabelCategory.usage); // ruwikt торжественное
    public static final Label student_slang = new LabelEn("student slang",  "student slang", LabelCategory.usage); // ruwikt студенческий жаргон
    public static final Label tabooed = new LabelEn("tabooed",  "tabooed", LabelCategory.usage); // ruwikt табуированное
    public static final Label technical_jargon = new LabelEn("technical jargon",  "technical jargon", LabelCategory.usage); // ruwikt технический жаргон
    public static final Label text_messaging = new LabelEn("text messaging",  "text messaging", LabelCategory.usage);
    public static final Label tiopês = new LabelEn("tiopês",  "tiopês", LabelCategory.usage);
    public static final Label trademark = new LabelEn("trademark",  "trademark", LabelCategory.usage);
    public static final Label traditionally_poetic = new LabelEn("traditionally poetic",  "traditionally poetic", LabelCategory.usage); // ruwikt традиционно-поэтическое
    
    public static final Label transferred_sense = new LabelEn("transferred sense",  "transferred sense", LabelCategory.usage); // В ПЕРЕНОСНОМ СМЫСЛЕ
    public static final Label transferred_senses = LabelEn.addNonUniqueShortName(transferred_sense, "transferred_senses");

    public static final Label uncommon = new LabelEn("uncommon",  "uncommon", LabelCategory.usage); // РЕДКИЙ
    public static final Label US_slang = new LabelEn("US slang",  "US slang", LabelCategory.usage);
    public static final Label vulgar = new LabelEn("vulgar",  "vulgar", LabelCategory.usage);
    public static final Label youth = new LabelEn("youth",  "youth", LabelCategory.usage);    
    
    // **************************
    // topical 376 Ann
    // **************************
    
    public static final Label accounting = new LabelEn("accounting", "accounting", LabelCategory.topical); // бухг. - бухгалтерское
    public static final Label acoustics = new LabelEn("acoustics", "acoustics", LabelCategory.topical); // акустическое
    public static final Label acting = new LabelEn("acting", "acting", LabelCategory.topical);
    public static final Label advertising = new LabelEn("advertising", "advertising", LabelCategory.topical); // рекл. - рекламное
    public static final Label agriculture = new LabelEn("agriculture", "agriculture", LabelCategory.topical); // сельск. — сельскохозяйственное
    public static final Label agronomy = new LabelEn("agronomy", "agronomy", LabelCategory.topical); // ruwikt агрономическое
    public static final Label aircraft = new LabelEn("aircraft", "aircraft", LabelCategory.topical);
    public static final Label alcohol = new LabelEn("alcohol", "alcohol", LabelCategory.topical); // алкоголь
    public static final Label analysis = new LabelEn("analysis", "analysis", LabelCategory.topical);
    public static final Label anarchism = new LabelEn("anarchism", "anarchism", LabelCategory.topical);
    
    public static final Label anatomy = new LabelEn("anatomy", "anatomy", LabelCategory.topical); // анат. - анатомическое
    public static final Label muscle = LabelEn.addNonUniqueShortName(anatomy, "muscle");
    public static final Label skeleton = LabelEn.addNonUniqueShortName(anatomy, "skeleton");
    
    public static final Label Ancient_Rome = new LabelEn("Ancient Rome", "Ancient Rome", LabelCategory.topical); //античный Рим
    public static final Label animation = new LabelEn("animation", "animation", LabelCategory.topical); //анимационное
    public static final Label anime = new LabelEn("anime", "anime", LabelCategory.topical); //аниме (японская анимация)
    public static final Label architecture = new LabelEn("architecture", "architecture", LabelCategory.topical); //архит. - архитектурное
    public static final Label arts = new LabelEn("arts", "arts", LabelCategory.topical); //искусств. — искусствоведческое
    public static final Label artillery = new LabelEn("artillery", "artillery", LabelCategory.topical); // ruwikt артиллерийское
    public static final Label asterism = new LabelEn("asterism", "asterism", LabelCategory.topical);
    public static final Label astrology = new LabelEn("astrology", "astrology", LabelCategory.topical); //астрол. - астрологическое
    public static final Label Australian_Aboriginal_mythology = new LabelEn("Australian Aboriginal mythology", "Australian Aboriginal mythology", LabelCategory.topical);
    public static final Label automotive = new LabelEn("automotive", "automotive", LabelCategory.topical);
    public static final Label aviation = new LabelEn("aviation", "aviation", LabelCategory.topical); //авиац. - авиационное  
    
    public static final Label ballet = new LabelEn("ballet", "ballet", LabelCategory.topical); //балетное 
    public static final Label banking = new LabelEn("banking", "banking", LabelCategory.topical);  
    public static final Label BDSM = new LabelEn("BDSM", "BDSM", LabelCategory.topical); //БДСМ
    public static final Label beekeeping = new LabelEn("beekeeping", "beekeeping", LabelCategory.topical);
    public static final Label Beijing = new LabelEn("Beijing", "Beijing", LabelCategory.topical);
    public static final Label betting = new LabelEn("betting", "betting", LabelCategory.topical);
    
    public static final Label biblical = new LabelEn("biblical", "biblical", LabelCategory.topical); //библейск. - библейское
    public static final Label biblical_character = LabelEn.addNonUniqueShortName(biblical, "biblical character"); //на удаление
    
    public static final Label biotechnology = new LabelEn("biotechnology", "biotechnology", LabelCategory.topical);
    public static final Label birdwatching = new LabelEn("birdwatching", "birdwatching", LabelCategory.topical);
    public static final Label blogging = new LabelEn("blogging", "blogging", LabelCategory.topical);
    public static final Label brewing = new LabelEn("brewing", "brewing", LabelCategory.topical);
    public static final Label broadcasting = new LabelEn("broadcasting", "broadcasting", LabelCategory.topical);
    public static final Label bryology = new LabelEn("bryology", "bryology", LabelCategory.topical);
    public static final Label business = new LabelEn("business", "business", LabelCategory.topical);
    
    public static final Label choreography = new LabelEn("choreography", "choreography", LabelCategory.topical); // ruwikt хореографическое
    public static final Label canid = new LabelEn("canid", "canid", LabelCategory.topical);
    public static final Label carbohydrate = new LabelEn("carbohydrate", "carbohydrate", LabelCategory.topical);
    public static final Label cardiology = new LabelEn("cardiology", "cardiology", LabelCategory.topical);
    public static final Label carpentry = new LabelEn("carpentry", "carpentry", LabelCategory.topical); // плотн. — плотницкое дело
    public static final Label cartography = new LabelEn("cartography", "cartography", LabelCategory.topical);
    public static final Label ceramics = new LabelEn("ceramics", "ceramics", LabelCategory.topical);
    public static final Label chemical_engineering = new LabelEn("chemical engineering", "chemical engineering", LabelCategory.topical);
    public static final Label cinematography = new LabelEn("cinematography", "cinematography", LabelCategory.topical); //кино - кинематографическое
    public static final Label circus = new LabelEn("circus", "circus", LabelCategory.topical); // ruwikt цирковое
    public static final Label cladistics = new LabelEn("cladistics", "cladistics", LabelCategory.topical);
    public static final Label classical_mechanics = new LabelEn("classical mechanics", "classical mechanics", LabelCategory.topical);
    public static final Label classical_studies = new LabelEn("classical studies", "classical studies", LabelCategory.topical);
    public static final Label climatology = new LabelEn("climatology", "climatology", LabelCategory.topical);
    public static final Label coenzyme = new LabelEn("coenzyme", "coenzyme", LabelCategory.topical);
    public static final Label comedy = new LabelEn("comedy", "comedy", LabelCategory.topical);
    public static final Label communication = new LabelEn("communication", "communication", LabelCategory.topical);
    public static final Label communism = new LabelEn("communism", "communism", LabelCategory.topical);
    public static final Label conchology = new LabelEn("conchology", "conchology", LabelCategory.topical);
    public static final Label constellation = new LabelEn("constellation", "constellation", LabelCategory.topical);
    public static final Label construction = new LabelEn("construction", "construction", LabelCategory.topical); // строит. — строительное
    public static final Label cooking = new LabelEn("cooking", "cooking", LabelCategory.topical); //кулин. - кулинарное
    public static final Label cookware = new LabelEn("cookware", "cookware", LabelCategory.topical);
    public static final Label copyright = new LabelEn("copyright", "copyright", LabelCategory.topical);
    public static final Label cosmetics = new LabelEn("cosmetics", "cosmetics", LabelCategory.topical);
    public static final Label criminal = new LabelEn("criminal", "criminal", LabelCategory.topical); // ruwikt криминальное
    public static final Label criminology = new LabelEn("criminology", "criminology", LabelCategory.topical);
    public static final Label cryptography = new LabelEn("cryptography", "cryptography", LabelCategory.topical);
    public static final Label crystallography = new LabelEn("crystallography", "crystallography", LabelCategory.topical);
    public static final Label cultural_anthropology = new LabelEn("cultural anthropology", "cultural anthropology", LabelCategory.topical); // ruwikt культурологическое
    public static final Label currency = new LabelEn("currency", "currency", LabelCategory.topical);
    public static final Label cybernetics = new LabelEn("cybernetics", "cybernetics", LabelCategory.topical);
    public static final Label cynology = new LabelEn("cynology", "cynology", LabelCategory.topical); // ruwikt кинологическое
    public static final Label cytology = new LabelEn("cytology", "cytology", LabelCategory.topical);
    
    public static final Label dentistry = new LabelEn("dentistry", "dentistry", LabelCategory.topical);
    public static final Label dermatology = new LabelEn("dermatology", "dermatology", LabelCategory.topical);
    public static final Label dictation = new LabelEn("dictation", "dictation", LabelCategory.topical);
    public static final Label diplomacy = new LabelEn("diplomacy", "diplomacy", LabelCategory.topical); // дипл. — дипломатическое
    public static final Label disease = new LabelEn("disease", "disease", LabelCategory.topical);
    public static final Label drama = new LabelEn("drama", "drama", LabelCategory.topical);
    
    public static final Label E_number = new LabelEn("E number", "food manufacture", LabelCategory.topical);
    public static final Label earth_science = new LabelEn("earth science", "earth science", LabelCategory.topical);
    public static final Label Eastern_Orthodoxy = new LabelEn("Eastern Orthodoxy", "Eastern Orthodoxy", LabelCategory.topical);
    public static final Label ecclesiastical = new LabelEn("ecclesiastical", "ecclesiastical", LabelCategory.topical); // церк. - церковный
    public static final Label ecology = new LabelEn("ecology", "ecology", LabelCategory.topical); // экол. - экологическое
    public static final Label economics = new LabelEn("economics", "economics", LabelCategory.topical); // экон. — экономическое
    
    public static final Label education = new LabelEn("education", "education", LabelCategory.topical); // образование, воспитание
    public static final Label schools = LabelEn.addNonUniqueShortName(education, "schools"); // школа
    
    public static final Label Egyptology = new LabelEn("Egyptology", "Egyptology", LabelCategory.topical);  
    public static final Label electrencephalography = new LabelEn("electrencephalography", "electrencephalography", LabelCategory.topical);
    public static final Label electrical_engineering = new LabelEn("electrical engineering", "electrical engineering", LabelCategory.topical); // эл.-техн. — электротехническое
    public static final Label electric_power = new LabelEn("electric power", "electric power", LabelCategory.topical); // ruwikt электроэнергетическое
    public static final Label electricity = new LabelEn("electricity", "electricity", LabelCategory.topical);
    public static final Label electrodynamics = new LabelEn("electrodynamics", "electrodynamics", LabelCategory.topical);
    public static final Label electromagnetism = new LabelEn("electromagnetism", "electromagnetism", LabelCategory.topical);
    public static final Label electronics = new LabelEn("electronics", "electronics", LabelCategory.topical);
    public static final Label embryology = new LabelEn("embryology", "embryology", LabelCategory.topical);
    public static final Label emergency_medicine = new LabelEn("emergency medicine", "emergency medicine", LabelCategory.topical);
    public static final Label endocrinology = new LabelEn("endocrinology", "endocrinology", LabelCategory.topical);
    public static final Label engineering = new LabelEn("engineering", "engineering", LabelCategory.topical);
    public static final Label enterprise_engineering = new LabelEn("enterprise engineering", "enterprise engineering", LabelCategory.topical);
    public static final Label ethnology = new LabelEn("ethnology", "ethnology", LabelCategory.topical); // ruwikt этнологическое
    public static final Label entomology = new LabelEn("entomology", "entomology", LabelCategory.topical); // энтомол. — энтомологическое
    public static final Label enzyme = new LabelEn("enzyme", "enzyme", LabelCategory.topical);
    public static final Label epidemiology = new LabelEn("epidemiology", "epidemiology", LabelCategory.topical);
    public static final Label epistemology = new LabelEn("epistemology", "epistemology", LabelCategory.topical);
    public static final Label espionage = new LabelEn("espionage", "espionage", LabelCategory.topical);
    public static final Label ethics = new LabelEn("ethics", "ethics", LabelCategory.topical);
    public static final Label ethnography = new LabelEn("ethnography", "ethnography", LabelCategory.topical); // этногр. — этнографическое

    public static final Label falconry = new LabelEn("falconry", "falconry", LabelCategory.topical);
    public static final Label fantasy = new LabelEn("fantasy", "fantasy", LabelCategory.topical);
    public static final Label fashion = new LabelEn("fashion", "fashion", LabelCategory.topical);
    public static final Label fatty_acid = new LabelEn("fatty acid", "fatty acid", LabelCategory.topical);
    public static final Label felid = new LabelEn("felid", "felid", LabelCategory.topical);
    public static final Label fiction = new LabelEn("fiction", "fiction", LabelCategory.topical);  
    public static final Label fictional_character = new LabelEn("fictional character", "fictional character", LabelCategory.topical);
    public static final Label film = new LabelEn("film", "film", LabelCategory.topical);
    public static final Label finance = new LabelEn("finance", "finance", LabelCategory.topical); // фин. — финансовое
    public static final Label firefighting = new LabelEn("firefighting", "firefighting", LabelCategory.topical);
    public static final Label fluid_dynamics = new LabelEn("fluid dynamics", "fluid dynamics", LabelCategory.topical);
    public static final Label fluid_mechanics = new LabelEn("fluid mechanics", "fluid mechanics", LabelCategory.topical);
    public static final Label footwear = new LabelEn("footwear", "footwear", LabelCategory.topical);
    public static final Label forestry = new LabelEn("forestry", "forestry", LabelCategory.topical); //лес. — лесоводство
    public static final Label freemasonry = new LabelEn("freemasonry", "freemasonry", LabelCategory.topical);
    public static final Label furniture = new LabelEn("furniture", "furniture", LabelCategory.topical);
    public static final Label folklore = new LabelEn("folklore", "folklore", LabelCategory.topical); // ruwikt фольклорное
    
    public static final Label galaxy = new LabelEn("galaxy", "galaxy", LabelCategory.topical); // связано с галактикой
    public static final Label gambling = new LabelEn("gambling", "gambling", LabelCategory.topical);
    public static final Label gastronomic = new LabelEn("gastronomic", "gastronomic", LabelCategory.topical); // ruwikt гастрономическое		
    public static final Label genealogy = new LabelEn("genealogy", "genealogy", LabelCategory.topical); // генеалогическое
    public static final Label genetics = new LabelEn("genetics", "genetics", LabelCategory.topical); // генет. - генетическое
    public static final Label geography = new LabelEn("geography", "geography", LabelCategory.topical); // геогр. - географическое
    public static final Label geodesy = new LabelEn("geodesy", "geodesy", LabelCategory.topical); // ruwikt геодезическое
    public static final Label geology = new LabelEn("geology", "geology", LabelCategory.topical); // геол. - геологическое
    public static final Label geophysics = new LabelEn("geophysics", "geophysics", LabelCategory.topical); // ruwikt геофизическое
    public static final Label geomorphology = new LabelEn("geomorphology", "geomorphology", LabelCategory.topical);
    public static final Label glassblowing = new LabelEn("glassblowing", "glassblowing", LabelCategory.topical); 
    public static final Label government = new LabelEn("government", "government", LabelCategory.topical); // правительственное
    public static final Label grammar = new LabelEn("grammar", "grammar", LabelCategory.topical); // грам. - грамматическое
    public static final Label gynaecology = new LabelEn("gynaecology", "gynaecology", LabelCategory.topical); // гинекологическое
    
    public static final Label hairdressing = new LabelEn("hairdressing", "hairdressing", LabelCategory.topical); // ruwikt парикмахерское
    public static final Label ham_radio = new LabelEn("ham radio", "ham radio", LabelCategory.topical);
    public static final Label healthcare = new LabelEn("healthcare", "healthcare", LabelCategory.topical);
    public static final Label hematology = new LabelEn("hematology", "hematology", LabelCategory.topical);
    public static final Label heraldiccharge = new LabelEn("heraldiccharge", "heraldiccharge", LabelCategory.topical);
    
    public static final Label heraldry = new LabelEn("heraldry", "heraldry", LabelCategory.topical); // геральд. - геральдическое
    public static final Label tincture = LabelEn.addNonUniqueShortName(heraldry, "tincture"); // краски, оттенки
    
    public static final Label jewellery = new LabelEn("jewellery", "jewellery", LabelCategory.topical); // ruwikt ювелирное
	
    public static final Label herbalism = new LabelEn("herbalism", "herbalism", LabelCategory.topical);
    public static final Label historiography = new LabelEn("historiography", "historiography", LabelCategory.topical);
    public static final Label history = new LabelEn("history", "history", LabelCategory.topical);
    public static final Label homeopathy = new LabelEn("homeopathy", "homeopathy", LabelCategory.topical);
    public static final Label honorific = new LabelEn("honorific", "honorific", LabelCategory.topical);
    public static final Label hormone = new LabelEn("hormone", "hormone", LabelCategory.topical);
    public static final Label horror = new LabelEn("horror", "horror", LabelCategory.topical);
    public static final Label horticulture = new LabelEn("horticulture", "horticulture", LabelCategory.topical);
    public static final Label human_resources = new LabelEn("human resources", "human resources", LabelCategory.topical);
    public static final Label humanities = new LabelEn("humanities", "humanities", LabelCategory.topical);
    public static final Label hunting = new LabelEn("hunting", "hunting", LabelCategory.topical); // охотн. — охотничье
    public static final Label hydrology = new LabelEn("hydrology", "hydrology", LabelCategory.topical);
    
    public static final Label ichthyology = new LabelEn("ichthyology", "ichthyology", LabelCategory.topical); // ruwikt ихтиологическое
    public static final Label immunochemistry = new LabelEn("immunochemistry", "immunochemistry", LabelCategory.topical);
    public static final Label immunology = new LabelEn("immunology", "immunology", LabelCategory.topical);
    public static final Label import_export = new LabelEn("import/export", "import/export", LabelCategory.topical);
    public static final Label incoterm = new LabelEn("incoterm", "incoterm", LabelCategory.topical);
    public static final Label indo_european_studies = new LabelEn("indo-european studies", "indo-european studies", LabelCategory.topical);
    public static final Label information_science = new LabelEn("information science", "information science", LabelCategory.topical);
    public static final Label information_theory = new LabelEn("information theory", "information theory", LabelCategory.topical);
    public static final Label inorganic_chemistry = new LabelEn("inorganic chemistry", "inorganic chemistry", LabelCategory.topical);
    public static final Label inorganic_compound = new LabelEn("inorganic compound", "inorganic compound", LabelCategory.topical);
    public static final Label international_law = new LabelEn("international law", "international law", LabelCategory.topical);
    public static final Label Internet = new LabelEn("Internet", "Internet", LabelCategory.topical);
    public static final Label isotope = new LabelEn("isotope", "isotope", LabelCategory.topical);
    
    public static final Label journalism = new LabelEn("journalism", "journalism", LabelCategory.topical); //журналистское
    public static final Label juggling = new LabelEn("juggling", "juggling", LabelCategory.topical); //жонглерское
    
    public static final Label knitting = new LabelEn("knitting", "knitting", LabelCategory.topical);
    
    public static final Label landforms = new LabelEn("landforms", "landforms", LabelCategory.topical);
    public static final Label law_enforcement = new LabelEn("law enforcement", "law enforcement", LabelCategory.topical);
    public static final Label legal = new LabelEn("legal", "legal", LabelCategory.topical); //юр. — юридическое или нормативное
    public static final Label letterpress = new LabelEn("letterpress", "letterpress", LabelCategory.topical);
    public static final Label lexicography = new LabelEn("lexicography", "lexicography", LabelCategory.topical);
    public static final Label LGBT = new LabelEn("LGBT", "LGBT", LabelCategory.topical);
    public static final Label library_science = new LabelEn("library science", "library science", LabelCategory.topical);
    public static final Label limnology = new LabelEn("limnology", "limnology", LabelCategory.topical);
    
    public static final Label linguistics = new LabelEn("linguistics", "linguistics", LabelCategory.topical); //лингв. — лингвистическое
    public static final Label linguistic_morphology = LabelEn.addNonUniqueShortName(linguistics, "linguistic morphology");
    
    public static final Label literature = new LabelEn("literature", "literature", LabelCategory.topical); //лит. — литературоведение
    public static final Label logic = new LabelEn("logic", "logic", LabelCategory.topical);
    
    public static final Label machining = new LabelEn("machining", "machining", LabelCategory.topical);
    public static final Label malacology = new LabelEn("malacology", "malacology", LabelCategory.topical);
    public static final Label management = new LabelEn("management", "management", LabelCategory.topical); //управл. — управленческое
    
    public static final Label Advanced_Mandarin = new LabelEn("Advanced Mandarin", "Advanced Mandarin", LabelCategory.topical);
    public static final Label Beginning_Mandarin = new LabelEn("Beginning Mandarin", "Beginning Mandarin", LabelCategory.topical);
    public static final Label Elementary_Mandarin = new LabelEn("Elementary Mandarin", "Elementary Mandarin", LabelCategory.topical);
    public static final Label Intermediate_Mandarin = new LabelEn("Intermediate Mandarin", "Intermediate Mandarin", LabelCategory.topical);
    
    public static final Label manga = new LabelEn("manga", "manga", LabelCategory.topical);
    public static final Label manufacturing = new LabelEn("manufacturing", "manufacturing", LabelCategory.topical);
    public static final Label marketing = new LabelEn("marketing", "marketing", LabelCategory.topical);
    public static final Label Marxism = new LabelEn("Marxism", "Marxism", LabelCategory.topical);
    public static final Label massage = new LabelEn("massage", "massage", LabelCategory.topical);
    public static final Label materials_science = new LabelEn("materials science", "materials science", LabelCategory.topical);
    public static final Label mechanical_engineering = new LabelEn("mechanical engineering", "mechanical engineering", LabelCategory.topical);
    public static final Label mechanics = new LabelEn("mechanics", "mechanics", LabelCategory.topical); //мех. — механика
    public static final Label media = new LabelEn("media", "media", LabelCategory.topical);
    
    public static final Label medicine = new LabelEn("medicine", "medicine", LabelCategory.topical); //мед. — медицинское    
    public static final Label symptom = LabelEn.addNonUniqueShortName(medicine, "symptom");
    
    public static final Label metadata = new LabelEn("metadata", "metadata", LabelCategory.topical);
    public static final Label metallurgy = new LabelEn("metallurgy", "metallurgy", LabelCategory.topical); //металл. — металлургическое
    public static final Label metaphysics = new LabelEn("metaphysics", "metaphysics", LabelCategory.topical);
    public static final Label meteorology = new LabelEn("meteorology", "meteorology", LabelCategory.topical); //метеорол. — метеорологическое
    public static final Label metrology = new LabelEn("metrology", "metrology", LabelCategory.topical);
    public static final Label microbiology = new LabelEn("microbiology", "microbiology", LabelCategory.topical); //микробиол. — микробиологическое
    public static final Label microelectronics = new LabelEn("microelectronics", "microelectronics", LabelCategory.topical);
    public static final Label microscopy = new LabelEn("microscopy", "microscopy", LabelCategory.topical);
    
    public static final Label military = new LabelEn("military", "military", LabelCategory.topical); //военн. — военное
    public static final Label military_ranks = LabelEn.addNonUniqueShortName(military, "military ranks");
    
    public static final Label mineralogy = new LabelEn("mineralogy", "mineralogy", LabelCategory.topical); //минер. — минералогия
    public static final Label mineral = LabelEn.addNonUniqueShortName(mineralogy, "mineral"); // на удаление
    
    public static final Label mining = new LabelEn("mining", "mining", LabelCategory.topical); // горн. — горное дело
    public static final Label mobile_phones = new LabelEn("mobile phones", "mobile phones", LabelCategory.topical); // mobile telephony?
    public static final Label molecular_biology = new LabelEn("molecular biology", "molecular biology", LabelCategory.topical);
    public static final Label monarchy = new LabelEn("monarchy", "monarchy", LabelCategory.topical);
    public static final Label money = new LabelEn("money", "money", LabelCategory.topical);
    public static final Label motorcycling = new LabelEn("motorcycling", "motorcycling", LabelCategory.topical);
	
    public static final Label mycology = new LabelEn("mycology", "mycology", LabelCategory.topical);
    public static final Label mushroom = LabelEn.addNonUniqueShortName(mycology, "mushroom");
    
    public static final Label nanotechnology = new LabelEn("nanotechnology", "nanotechnology", LabelCategory.topical);
    public static final Label narratology = new LabelEn("narratology", "narratology", LabelCategory.topical);
    public static final Label natural_science = new LabelEn("natural_science", "natural_science", LabelCategory.topical); // ruwikt естествознание
    public static final Label nautical = new LabelEn("nautical", "nautical", LabelCategory.topical); // морск. — морское
    public static final Label navigation = new LabelEn("navigation", "navigation", LabelCategory.topical);
    public static final Label Nazi = new LabelEn("Nazi", "Nazi", LabelCategory.topical);
    public static final Label nematology = new LabelEn("nematology", "nematology", LabelCategory.topical);
    public static final Label neuroanatomy = new LabelEn("neuroanatomy", "neuroanatomy", LabelCategory.topical);
    public static final Label neurology = new LabelEn("neurology", "neurology", LabelCategory.topical);
    public static final Label neurosurgery = new LabelEn("neurosurgery", "neurosurgery", LabelCategory.topical);
    public static final Label neurotoxin = new LabelEn("neurotoxin", "neurotoxin", LabelCategory.topical);
    public static final Label neurotransmitter = new LabelEn("neurotransmitter", "neurotransmitter", LabelCategory.topical);
    public static final Label newspapers = new LabelEn("newspapers", "newspapers", LabelCategory.topical);
    public static final Label nuclear_physics = new LabelEn("nuclear physics", "nuclear physics", LabelCategory.topical);
    public static final Label number_theory = new LabelEn("number theory", "number theory", LabelCategory.topical);
    public static final Label numismatics = new LabelEn("numismatics", "numismatics", LabelCategory.topical); // нумизм. — нумизматическое
    public static final Label nutrition = new LabelEn("nutrition", "nutrition", LabelCategory.topical);
    
    public static final Label occult = new LabelEn("occult", "occult", LabelCategory.topical); // оккульт. — оккультное
    public static final Label oceanography = new LabelEn("oceanography", "oceanography", LabelCategory.topical);
    public static final Label oenology = new LabelEn("oenology", "oenology", LabelCategory.topical);
    public static final Label oil_industry = new LabelEn("oil industry", "oil industry", LabelCategory.topical); // нефтегаз. — нефтегазодобыча 
    public static final Label oncology = new LabelEn("oncology", "oncology", LabelCategory.topical);
    public static final Label operating_systems = new LabelEn("operating systems", "operating systems", LabelCategory.topical);
    public static final Label ophthalmology = new LabelEn("ophthalmology", "ophthalmology", LabelCategory.topical);
    public static final Label optics = new LabelEn("optics", "optics", LabelCategory.topical); // опт. — оптическое
    public static final Label organic_chemistry = new LabelEn("organic chemistry", "organic chemistry", LabelCategory.topical);
    public static final Label organic_compound = LabelEn.addNonUniqueShortName(organic_chemistry, "organic compound");
    public static final Label ornithology = new LabelEn("ornithology", "ornithology", LabelCategory.topical); // орнитол. — орнитологическое
    public static final Label orthodontics = new LabelEn("orthodontics", "orthodontics", LabelCategory.topical);
    public static final Label orthography = new LabelEn("orthography", "orthography", LabelCategory.topical);
    
    public static final Label paganism = new LabelEn("paganism", "paganism", LabelCategory.topical);
    public static final Label painting = new LabelEn("painting", "painting", LabelCategory.topical); //живоп. — живопись
    public static final Label paleontology = new LabelEn("paleontology", "paleontology", LabelCategory.topical); // палеонт. — палеонтологическое
    public static final Label palmistry = new LabelEn("palmistry", "palmistry", LabelCategory.topical);
    public static final Label palynology = new LabelEn("palynology", "palynology", LabelCategory.topical);
    public static final Label parapsychology = new LabelEn("parapsychology", "parapsychology", LabelCategory.topical);
    public static final Label part_of_speech = new LabelEn("part of speech", "part of speech", LabelCategory.topical);
    public static final Label particle = new LabelEn("particle", "particle", LabelCategory.topical);
    public static final Label pasteurisation = new LabelEn("pasteurisation", "pasteurisation", LabelCategory.topical);
    public static final Label patent_law = new LabelEn("patent law", "patent law", LabelCategory.topical);
    public static final Label pathology = new LabelEn("pathology", "pathology", LabelCategory.topical);
    public static final Label petrochemistry = new LabelEn("petrochemistry", "petrochemistry", LabelCategory.topical);
    public static final Label petrology = new LabelEn("petrology", "petrology", LabelCategory.topical);
    
    public static final Label pharmacology = new LabelEn("pharmacology", "pharmacology", LabelCategory.topical); //фармакологическое
    public static final Label pharmaceutical_drug = LabelEn.addNonUniqueShortName(pharmacology, "pharmaceutical drug");
    
    public static final Label pharmacy = new LabelEn("pharmacy", "pharmacy", LabelCategory.topical); //фарм. — фармацевтический термин
    public static final Label pharyngology = new LabelEn("pharyngology", "pharyngology", LabelCategory.topical);
    public static final Label philately = new LabelEn("philately", "philately", LabelCategory.topical); //филат. — филателистическое
    public static final Label philosophy = new LabelEn("philosophy", "philosophy", LabelCategory.topical); //филос. — философское
    public static final Label phonetics = new LabelEn("phonetics", "phonetics", LabelCategory.topical); //фонетическое
    public static final Label phonology = new LabelEn("phonology", "phonology", LabelCategory.topical);
    public static final Label photography = new LabelEn("photography", "photography", LabelCategory.topical); //фотогр. — фотографическое
    public static final Label physical_chemistry = new LabelEn("physical chemistry", "physical chemistry", LabelCategory.topical);
    public static final Label physiology = new LabelEn("physiology", "physiology", LabelCategory.topical); //физиол. — физиология
    
    public static final Label planetology = new LabelEn("planetology", "planetology", LabelCategory.topical); 
    public static final Label surface_feature = LabelEn.addNonUniqueShortName(planetology, "surface feature");
    
    //plant перемещено вниз, как синоним botany
    public static final Label poetry = new LabelEn("poetry", "poetry", LabelCategory.topical);
    //poison перемещено вниз, как синоним toxicology
    public static final Label political_science = new LabelEn("political science", "political science", LabelCategory.topical);
    public static final Label politics = new LabelEn("politics", "politics", LabelCategory.topical); // полит. — политическое
    public static final Label polymer_science = new LabelEn("polymer science", "polymer science", LabelCategory.topical);
    public static final Label pornography = new LabelEn("pornography", "pornography", LabelCategory.topical);
    public static final Label pottery = new LabelEn("pottery", "pottery", LabelCategory.topical);
    public static final Label pragmatics = new LabelEn("pragmatics", "pragmatics", LabelCategory.topical);
    public static final Label printing = new LabelEn("printing", "printing", LabelCategory.topical); // полигр. — полиграфическое
    public static final Label professional = new LabelEn("professional", "professional", LabelCategory.topical); //ruwikt проф.
    public static final Label property_law = new LabelEn("property law", "property law", LabelCategory.topical);
    public static final Label prosody = new LabelEn("prosody", "prosody", LabelCategory.topical);
    public static final Label pseudoscience = new LabelEn("pseudoscience", "pseudoscience", LabelCategory.topical);
    public static final Label psychiatry = new LabelEn("psychiatry", "psychiatry", LabelCategory.topical); // психиатр. — психиатрия
    public static final Label psychoanalysis = new LabelEn("psychoanalysis", "psychoanalysis", LabelCategory.topical);
    public static final Label psychotherapy = new LabelEn("psychotherapy", "psychotherapy", LabelCategory.topical); // психотерапевтическое
    public static final Label publishing = new LabelEn("publishing", "publishing", LabelCategory.topical);
    public static final Label pyrotechnics = new LabelEn("pyrotechnics", "pyrotechnics", LabelCategory.topical); // пиротехническое
    
    public static final Label quantum_mechanics = new LabelEn("quantum mechanics", "quantum mechanics", LabelCategory.topical); // квантовая механика
    
    public static final Label radio = new LabelEn("radio", "radio", LabelCategory.topical); // радио — радиодело, радиовещание
    
    public static final Label rail_transport = new LabelEn("rail transport", "rail transport", LabelCategory.topical); // ж.-д. (жд) — железнодорожное
    public static final Label railroads = LabelEn.addNonUniqueShortName(rail_transport, "railroads"); // железные дороги
    
    public static final Label real_estate = new LabelEn("real estate", "real estate", LabelCategory.topical);
    //relativity перемещено ниже, как синоним physics
    
    public static final Label rhetoric = new LabelEn("rhetoric", "rhetoric", LabelCategory.topical); // риторическое, стилистическое
    public static final Label figure_of_speech = LabelEn.addNonUniqueShortName(rhetoric, "figure of speech"); // тропы, фигуры речи, образные выражения
    
    public static final Label robotics = new LabelEn("robotics", "robotics", LabelCategory.topical); // робототехника
    public static final Label roofing = new LabelEn("roofing", "roofing", LabelCategory.topical); // кровельные работы
    public static final Label rosiculture = new LabelEn("rosiculture", "rosiculture", LabelCategory.topical); // выращивание роз
    public static final Label Rubiks_Cube = new LabelEn("Rubiks Cube", "Rubiks Cube", LabelCategory.topical); // кубик Рубика
    public static final Label rugby_league = new LabelEn("rugby league", "rugby league", LabelCategory.topical); // Регбийная лига
    
    public static final Label sartorial = new LabelEn("sartorial", "sartorial", LabelCategory.topical); // ruwikt портновское дело
    
    public static final Label science_fiction = new LabelEn("science fiction", "science fiction", LabelCategory.topical); // фант. — фантастическое
    public static final Label sciences = new LabelEn("sciences", "sciences", LabelCategory.topical); // научн. — научное
    public static final Label scrapbooks = new LabelEn("scrapbooks", "scrapbooks", LabelCategory.topical); // скрапбукинг (поделки из бумаги, грубо говоря)
    public static final Label sculpture = new LabelEn("sculpture", "sculpture", LabelCategory.topical); // лепка, скульптура
    public static final Label seismology = new LabelEn("seismology", "seismology", LabelCategory.topical); // сейсмология (исследует землетрясения)
    public static final Label semantics = new LabelEn("semantics", "semantics", LabelCategory.topical); // семантическое
    public static final Label semiotics = new LabelEn("semiotics", "semiotics", LabelCategory.topical); // семиотика (теория знаковых систем)
    public static final Label set_theory = new LabelEn("set theory", "set theory", LabelCategory.topical); // теория множеств
    public static final Label sewing = new LabelEn("sewing", "sewing", LabelCategory.topical); // швейн. — швейное
    public static final Label sexology = new LabelEn("sexology", "sexology", LabelCategory.topical); //сексол. — сексология
    public static final Label sexuality = new LabelEn("sexuality", "sexuality", LabelCategory.topical); // сексуальность
    public static final Label shipping = new LabelEn("shipping", "shipping", LabelCategory.topical); // перевозка грузов или торговый флот
    public static final Label signal_processing = new LabelEn("signal processing", "signal processing", LabelCategory.topical); // обработка сигналов
    public static final Label skating = new LabelEn("skating", "skating", LabelCategory.topical); // катание на коньках
    
    public static final Label social_sciences = new LabelEn("social sciences", "social sciences", LabelCategory.topical); // общественные науки
    public static final Label sociolinguistics = new LabelEn("sociolinguistics", "sociolinguistics", LabelCategory.topical); // социолингвистика
    public static final Label sociology = new LabelEn("sociology", "sociology", LabelCategory.topical); // социол. — социология
    public static final Label soil_science = new LabelEn("soil science", "soil science", LabelCategory.topical);
    public static final Label sound = new LabelEn("sound", "sound", LabelCategory.topical); // связано со звуками
    public static final Label soviet = new LabelEn("soviet", "soviet", LabelCategory.topical); // ruwikt советизм
    public static final Label sound_engineering = new LabelEn("sound engineering", "sound engineering", LabelCategory.topical); // звуковая обработка
    public static final Label space_flight = new LabelEn("space flight", "space flight", LabelCategory.topical); // космические полеты
    public static final Label spectroscopy = new LabelEn("spectroscopy", "spectroscopy", LabelCategory.topical); // спектроскопическое
    public static final Label special = new LabelEn("special", "special", LabelCategory.topical); // ruwikt специальное
    public static final Label speleology = new LabelEn("speleology", "speleology", LabelCategory.topical); // ruwikt спелеологическое
    public static final Label spiritualism = new LabelEn("spiritualism", "spiritualism", LabelCategory.topical); // спиритуализм (главенство духа в реальном мире)
    public static final Label standard_of_identity = new LabelEn("standard of identity", "standard of identity", LabelCategory.topical);
    
    public static final Label statistics = new LabelEn("statistics", "statistics", LabelCategory.topical); //стат. — статистическое
    public static final Label stock_market = new LabelEn("stock market", "stock market", LabelCategory.topical); // фондовая биржа
    public static final Label stock_symbol = new LabelEn("stock symbol", "stock symbol", LabelCategory.topical); // биржевое сокращение (стандартный символ, присваиваемый ценным бумагам корпорации)
    public static final Label subculture = new LabelEn("subculture", "subculture", LabelCategory.topical); // субкультурное (с субкультурами связано)
    public static final Label surgery = new LabelEn("surgery", "surgery", LabelCategory.topical); // хирургия
    public static final Label surveying = new LabelEn("surveying", "surveying", LabelCategory.topical); // топографическая съёмка
    public static final Label sushi = new LabelEn("sushi", "sushi", LabelCategory.topical); // суши
    public static final Label systems_engineering = new LabelEn("systems engineering", "systems engineering", LabelCategory.topical); // системное проектирование

    public static final Label taxation = new LabelEn("taxation", "taxation", LabelCategory.topical); // налогообложение
    
    public static final Label taxonomy = new LabelEn("taxonomy", "taxonomy", LabelCategory.topical); // таксономическое (классификация и систематизация)
    public static final Label taxonomic_name = LabelEn.addNonUniqueShortName(taxonomy, "taxonomic name");
    
    public static final Label technology = new LabelEn("technology", "technology", LabelCategory.topical); // техн. — техническое
    public static final Label telecommunications = new LabelEn("telecommunications", "telecommunications", LabelCategory.topical); // связано с телекоммуникациями
    public static final Label telegraphy = new LabelEn("telegraphy", "telegraphy", LabelCategory.topical); // телеграфское
    public static final Label telephony = new LabelEn("telephony", "telephony", LabelCategory.topical); // телефония
    public static final Label television = new LabelEn("television", "television", LabelCategory.topical); // телевизионное
    public static final Label teratology = new LabelEn("teratology", "teratology", LabelCategory.topical); // тератология (раздел эмбриологии, изучающий аномалии развития)
    public static final Label textiles = new LabelEn("textiles", "textiles", LabelCategory.topical); // текст. — текстильное
    public static final Label theater = new LabelEn("theater", "theater", LabelCategory.topical); // театр. — театральное
    public static final Label theology = new LabelEn("theology", "theology", LabelCategory.topical); // теологическое
    public static final Label thermodynamics = new LabelEn("thermodynamics", "thermodynamics", LabelCategory.topical); // термодинамическое   
    public static final Label topology = new LabelEn("topology", "topology", LabelCategory.topical); // топологическое (с топологией связано)
    public static final Label tourism = new LabelEn("tourism", "tourism", LabelCategory.topical); // туристическое
    
    public static final Label toxicology = new LabelEn("toxicology", "toxicology", LabelCategory.topical); // токсикологическое
    public static final Label poison = LabelEn.addNonUniqueShortName(toxicology, "poison"); // яды   
    
    public static final Label trading = new LabelEn("trading", "trading", LabelCategory.topical); // торг. — торговое
    public static final Label translation_studies = new LabelEn("translation studies", "translation studies", LabelCategory.topical); // переводоведение
    public static final Label transport = new LabelEn("transport", "transport", LabelCategory.topical); // трансп. — транспортное
    public static final Label travel = new LabelEn("travel", "travel", LabelCategory.topical); // связано с путешествиями
    public static final Label typography = new LabelEn("typography", "typography", LabelCategory.topical); // типографическое
    
    public static final Label ufology = new LabelEn("ufology", "ufology", LabelCategory.topical); // уфологическое
    
    public static final Label vehicle = new LabelEn("vehicle", "vehicle", LabelCategory.topical); // автомоб. — автомобильное
    public static final Label veterinary_medicine = new LabelEn("veterinary medicine", "veterinary medicine", LabelCategory.topical); // вет. - ветеринарное
    public static final Label virology = new LabelEn("virology", "virology", LabelCategory.topical); // вирусологическое
    public static final Label volcanology = new LabelEn("volcanology", "volcanology", LabelCategory.topical); // вулканологическое

    public static final Label weaponry = new LabelEn("weaponry", "weaponry", LabelCategory.topical); // вооружение
    public static final Label weather = new LabelEn("weather", "weather", LabelCategory.topical); // погодное    
    public static final Label weaving = new LabelEn("weaving", "weaving", LabelCategory.topical); // ткацкое дело
    public static final Label Wiktionary_and_WMF_jargon = new LabelEn("Wiktionary and WMF jargon", "Wiktionary and WMF jargon", LabelCategory.topical); // викисловарный жаргон
    public static final Label wine = new LabelEn("wine", "wine", LabelCategory.topical); // виноделие
    public static final Label woodworking = new LabelEn("woodworking", "woodworking", LabelCategory.topical); // деревообработка 
    public static final Label writing = new LabelEn("writing", "writing", LabelCategory.topical); // литературное творчество
	
    public static final Label yoga = new LabelEn("yoga", "yoga", LabelCategory.topical); // ruwikt йогическое
    
    
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
    public static final Label card_games = new LabelEn("card games", "card games", LabelCategory.games); // карт. — картёжное
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
    public static final Label space_science = new LabelEn("space science" ,"space science", LabelCategory.science); // косм. — космическое
        
    public static final Label alchemy = new LabelEn("alchemy" ,"alchemy", LabelCategory.science); // алхим. - алхимическое
    public static final Label analytical_chemistry = new LabelEn("analytical chemistry" ,"analytical chemistry", LabelCategory.science); // аналитическая химия
    public static final Label anthropology = new LabelEn("anthropology" ,"anthropology", LabelCategory.science); // антроп. - антропологическое
    public static final Label arachnology = new LabelEn("arachnology" ,"arachnology", LabelCategory.science); // арахнология
    public static final Label archaeology = new LabelEn("archaeology" ,"archaeology", LabelCategory.science); // археол. - археология
    public static final Label astronautics = new LabelEn("astronautics" ,"astronautics", LabelCategory.science); // астронавтика
    
    public static final Label astronomy = new LabelEn("astronomy" ,"astronomy", LabelCategory.science); // астрон. - астрономическое
    public static final Label zodiac_constellations = LabelEn.addNonUniqueShortName(astronomy, "zodiac constellations"); // зодиакальное    
    public static final Label uranography = LabelEn.addNonUniqueShortName(astronomy, "uranography"); // уранография (нанесение звёзд и планет на звёздный глобус)
    public static final Label star = LabelEn.addNonUniqueShortName(astronomy, "star"); // связано со звездами
    
    public static final Label astrophysics = new LabelEn("astrophysics" ,"astrophysics", LabelCategory.science); // астрофизика
    public static final Label bacteriology = new LabelEn("bacteriology" ,"bacteriology", LabelCategory.science); // бактериология
    
    public static final Label biochemistry = new LabelEn("biochemistry" ,"biochemistry", LabelCategory.science); // биохим. - биохимическое
    public static final Label amino_acid = LabelEn.addNonUniqueShortName(biochemistry, "amino acid"); // странный синоним, здесь собраны все аминокислоты, но они в тексте раскрываются как биохимия
    public static final Label vitamin = LabelEn.addNonUniqueShortName(biochemistry, "vitamin");
    public static final Label steroid = LabelEn.addNonUniqueShortName(biochemistry, "steroid"); // стероиды
    public static final Label steroid_hormone = LabelEn.addNonUniqueShortName(biochemistry, "steroid hormone"); // стероидные гормоны
    public static final Label protein = LabelEn.addNonUniqueShortName(biochemistry, "protein"); // протеины
    
    public static final Label biology = new LabelEn("biology" ,"biology", LabelCategory.science); // биол. - биологическое
    
    public static final Label botany = new LabelEn("botany" ,"botany", LabelCategory.science); // ботан. - ботаническое
    public static final Label plant = LabelEn.addNonUniqueShortName(botany, "plant"); // растения
    
    public static final Label chemistry = new LabelEn("chemistry" ,"chemistry", LabelCategory.science); // хим. - химическое
    // public static final Label element_symbol = LabelEn.addNonUniqueShortName(chemistry, "element symbol");// it is not a synonym of chemistry because it should be separate label for Russian form-of template
    public static final Label element_symbol = new LabelEn("element symbol", "element symbol", LabelCategory.science);//ruwikt form-of
    
    public static final Label psychology = new LabelEn("psychology" ,"psychology", LabelCategory.science); // психол. - психология
    public static final Label clinical_psychology = LabelEn.addNonUniqueShortName(psychology, "clinical psychology"); //
    
    public static final Label computer_science = new LabelEn("computer science" ,"computer science", LabelCategory.science); // информ. - информатическое
    public static final Label cryptozoology = new LabelEn("cryptozoology" ,"cryptozoology", LabelCategory.science); // криптозоология
    public static final Label gerontology = new LabelEn("gerontology" ,"gerontology", LabelCategory.science); // геронтология
    public static final Label marine_biology = new LabelEn("marine biology" ,"marine biology", LabelCategory.science); // морская биология
    public static final Label neuroscience = new LabelEn("neuroscience" ,"neuroscience", LabelCategory.science); // нейробиология???
    
    public static final Label physics = new LabelEn("physics" ,"physics", LabelCategory.science); // физ. - физическое
    public static final Label relativity = LabelEn.addNonUniqueShortName(physics, "relativity"); // относительность, теория относительности
    
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

    // ///////////////////////////////////////////////////////////////////////////////////////
    // form-of templates in ruwikt (which are not a context label, but a definition text should be extracted from these templates - it's a dirty hack %)
    // other Russian form-of templates marked by "//ruwikt form-of"
    
    // public static final Label ru_adverb = new LabelEn("ruwikt нареч.", "ruwikt {{нареч.}}", LabelCategory.empty);
    public static final Label ru_as_ru = new LabelEn("ruwikt as ru", "ruwikt {{as ru}}", LabelCategory.invisible);//ruwikt form-of
    public static final Label ru_action = new LabelEn("ruwikt действие", "ruwikt {{действие}}", LabelCategory.invisible);//ruwikt form-of
    public static final Label ru_equal = new LabelEn("ruwikt =", "ruwikt {{=}}", LabelCategory.invisible);//ruwikt form-of
    
    //public static final Label ru_participle = new LabelEn("ruwikt прич.", "ruwikt {{прич.}}", LabelCategory.empty);
    public static final Label ru_property = new LabelEn("ruwikt свойство", "ruwikt {{свойство}}", LabelCategory.invisible);//ruwikt form-of
    public static final Label ru_sootn = new LabelEn("ruwikt соотн.", "ruwikt {{соотн.}}", LabelCategory.invisible);//ruwikt form-of
    
    public static final Label ru_sovershiti = new LabelEn("ruwikt совершить", "ruwikt {{совершить}}", LabelCategory.invisible);//ruwikt form-of
    public static final Label ru_sostoyanie = new LabelEn("ruwikt состояние", "ruwikt {{состояние}}", LabelCategory.invisible);//ruwikt form-of
    
    
    // DEBUG: should be one error for each code line below:
    // DDDDDDDDDDDDDDDDDDDDDDDDDD
    //public static final Label archaic_short_name_duplication = new LabelEn("archaic",  "archaic full name (duplication of short name)", LabelCategory.period);
    //public static final Label archaic_full_name_duplication = new LabelEn("archaic short name (duplication of full name)", "archaic", LabelCategory.period);
    //public static final Label dated_sense_again = LabelEn.addNonUniqueShortName(dated, "dated_sense");
    // DDDDDDDDDDDDDDDDDDeo DEBUG
}
