/* POSLocal.java - names of parts of speech in some language.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.multi.ru.name;

import wikt.constant.POSLocal;
import wikt.constant.POS;

import java.util.HashMap;
import java.util.Map;


/** Names of parts of speech in Russian and the links to the POS.java codes.
 */
public class POSRu extends POSLocal {

    protected final static Map<String, POS> name2pos = new HashMap<String, POS>();
    protected final static Map<POS, String> pos2name = new HashMap<POS, String>();
    protected final static Map<POS, String> pos2name_short = new HashMap<POS, String>();

    /** Remark: run the RelationTableAll.main() in order to check duplicates
     * of language names and language codes of this locale.
     */
    protected POSRu(String _name,String _short_name, POS _pos) {
        this.name   = _name;
        this.short_name = _short_name;
        this.pos    = _pos;

        if(name.length() == 0)
            System.out.println("Error in POSRu.POSRu(): empty part of speech name! The POS code="+pos+
                    ". Check the maps name2pos and pos2name.");

        // check the uniqueness of the POS and name
        String name_prev = pos2name.get(pos);
        POS pos_prev = name2pos.get(name);

        if(null != name_prev)
            System.out.println("Error in POSRu.POSRu(): duplication of POS! POS="+pos+
                    " name='"+ name + "'. Check the maps name2pos and pos2name.");

        if(null != pos_prev)
            System.out.println("Error in POSRu.POSRu(): duplication of POS! POS="+pos+
                    " name='"+ name + "'. Check the maps name2pos and pos2name.");

        name2pos.put(name, pos);
        pos2name.put(pos, name);
        pos2name_short.put(pos, short_name);
    }

    /** Checks weather exists POS by its name in Russian language. */
    public static boolean hasName(String name) {
        return name2pos.containsKey(name);
    }

    /** Checks weather exists the translation for this POS. */
    public static boolean has(POS p) {
        return pos2name.containsKey(p);
    }

    /** Gets POS by its name in some language*/
    public static POS get(String name) {
        return name2pos.get(name);
    }

    /** Gets name of POS in Russian. */
    public static String getName (POS p) {

        String s = pos2name.get(p);
        if(null == s)
            return "";
            //return p.getName(); // if there is no translation into local language, then English name
        return s;
    }

    /** Gets short name of POS in Russian. */
    public static String getShortName (POS p) {

        String s = pos2name_short.get(p);
        if(null == s) {
            s = pos2name.get(p);
            if(null == s)
                return "";
            //return p.getName(); // if there is no translation into local language, then English name
        }
        return s;
    }

    /** Counts number of translations. */
    public static int size() {
        return name2pos.size();
    }

    public static final POSLocal unknown = new POSRu("Неизвестная часть речи", "неизв.", POS.unknown);

    // The classical parts of speech are:
    public static final POSLocal noun = new POSRu("Существительное", "сущ.", POS.noun);
    public static final POSLocal verb = new POSRu("Глагол", "гл.", POS.verb);
    public static final POSLocal adverb = new POSRu("Наречие", "нар.", POS.adverb);
    public static final POSLocal adjective = new POSRu("Прилагательное", "прил.", POS.adjective);
    public static final POSLocal pronoun = new POSRu("Местоимение", "мест.", POS.pronoun);
    
    public static final POSLocal conjunction = new POSRu("Союз", "союз", POS.conjunction);
    public static final POSLocal interjection = new POSRu("Междометие", "межд.", POS.interjection);
    public static final POSLocal preposition = new POSRu("Предлог", "предл.", POS.preposition);

    // Additional commonly used grammatical headers are:
    public static final POSLocal proper_noun = new POSRu("Имя собственное", "имя собств.", POS.proper_noun);
    public static final POSLocal article = new POSRu("Артикль", "артикль", POS.article);
    public static final POSLocal prefix = new POSRu("Приставка", "прист.", POS.prefix);
    public static final POSLocal suffix = new POSRu("Суффикс", "суфф.", POS.suffix);

    public static final POSLocal phrase = new POSRu("Фраза", "фраза", POS.phrase);
    public static final POSLocal idiom = new POSRu("Идиома", "идиом.", POS.idiom);
    public static final POSLocal prepositional_phrase = new POSRu("Предложная группа", "предл. гр.", POS.prepositional_phrase);

    // debated POS level 3 headers
    public static final POSLocal numeral = new POSRu("Числительное", "числ.", POS.numeral);
    
    // other descriptors that identify the usage of the entry, but which are not (strictly speaking) parts of speech:
    public static final POSLocal acronym = new POSRu("Акроним", "акроним", POS.acronym);

    public static final POSLocal abbreviation = new POSRu("Аббревиатура", "сокр.", POS.abbreviation);
    public static final POSLocal initialism = new POSRu("Буквенная аббревиатура", "букв. аббрев.", POS.initialism);

    public static final POSLocal symbol = new POSRu("Символ", "симв.", POS.symbol);
    public static final POSLocal letter = new POSRu("Буква", "буква", POS.letter);

    // other headers in use
    public static final POSLocal particle = new POSRu("Частица", "частица", POS.particle);
    public static final POSLocal participle = new POSRu("Причастие", "прич.", POS.participle);
    public static final POSLocal determiner = new POSRu("Детерминатив", "детерминатив", POS.determiner);
    public static final POSLocal infix = new POSRu("Инфикс", "инфикс", POS.infix);
    public static final POSLocal interfix = new POSRu("Интерфикс", "интерфикс", POS.interfix);
    public static final POSLocal affix = new POSRu("Аффикс", "аффикс", POS.affix);
    public static final POSLocal circumfix = new POSRu("Циркумфикс", "циркумфикс", POS.circumfix);
    public static final POSLocal counter = new POSRu("Счётное", "счётное", POS.counter);
    public static final POSLocal predicative = new POSRu("Именная часть составного сказуемого", "предикатив", POS.predicative);

    

    // only in Russian Wiktionary (yet)
    public static final POSLocal verb_interjection = new POSRu("Глагольно-междометное слово", "глагольно-междом.", POS.verb_interjection);
    public static final POSLocal parenthesis = new POSRu("Вводное слово", "вводн.", POS.parenthesis);
    public static final POSLocal prefix_of_compound = new POSRu("Первая часть сложных слов", "первая часть сложн. сл.", POS.prefix_of_compound);
}
