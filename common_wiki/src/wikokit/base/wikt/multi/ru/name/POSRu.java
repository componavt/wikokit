/* POSRu.java - names of parts of speech in Russian.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru.name;

import wikokit.base.wikt.constant.POSLocal;
import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.constant.RelationLocal;

import java.util.HashMap;
import java.util.Map;


/** Names of parts of speech in Russian and the links to the POS objects.
 * 
 * @attention: initialize class before using, e.g. "POSLocal _ = POSRu.noun;"
 */
public class POSRu extends POSLocal {

    protected POSRu(String _name, String _short_name, POS _pos) {
        super(_name, _short_name, _pos);
    }
    
    
    public static final POSLocal    unknown,
                                    noun,
                                    verb,
                                    adverb,
                                    adjective,
                                    pronoun,
                                    
                                    conjunction, interjection, preposition,
                                    
                                    proper_noun, article, prefix, suffix,
                                    
                                    phrase, idiom, prepositional_phrase,
                                    
                                    numeral,
                                    
                                    acronym, abbreviation, initialism,
                                    
                                    symbol, letter,
                                    
                                    particle, participle, determiner, infix, 
                                    interfix, affix, circumfix, counter, predicative,
                                    
                                    verb_interjection, parenthesis, prefix_of_compound;
    
    static {
    unknown = new POSRu("Неизвестная часть речи", "неизв.", POS.unknown);

    // The classical parts of speech are:
    noun = new POSRu("Существительное", "сущ.", POS.noun);
    verb = new POSRu("Глагол", "гл.", POS.verb);
    adverb = new POSRu("Наречие", "нар.", POS.adverb);
    adjective = new POSRu("Прилагательное", "прил.", POS.adjective);
    pronoun = new POSRu("Местоимение", "мест.", POS.pronoun);
    
    conjunction = new POSRu("Союз", "союз", POS.conjunction);
    interjection = new POSRu("Междометие", "межд.", POS.interjection);
    preposition = new POSRu("Предлог", "предл.", POS.preposition);
    
    // Additional commonly used grammatical headers are:
    proper_noun = new POSRu("Имя собственное", "имя собств.", POS.proper_noun);
    article = new POSRu("Артикль", "артикль", POS.article);
    prefix = new POSRu("Приставка", "прист.", POS.prefix);
    suffix = new POSRu("Суффикс", "суфф.", POS.suffix);
    
    phrase = new POSRu("Фраза", "фраза", POS.phrase);
    idiom = new POSRu("Идиома", "идиом.", POS.idiom);
    prepositional_phrase = new POSRu("Предложная группа", "предл. гр.", POS.prepositional_phrase);
    
    // debated POS level 3 headers
    numeral = new POSRu("Числительное", "числ.", POS.numeral);
    
    // other descriptors that identify the usage of the entry, but which are not (strictly speaking) parts of speech:
    acronym = new POSRu("Акроним", "акроним", POS.acronym);

    abbreviation = new POSRu("Аббревиатура", "сокр.", POS.abbreviation);
    initialism = new POSRu("Буквенная аббревиатура", "букв. аббрев.", POS.initialism);

    symbol = new POSRu("Символ", "симв.", POS.symbol);
    letter = new POSRu("Буква", "буква", POS.letter);
    
    // other headers in use
    particle = new POSRu("Частица", "частица", POS.particle);
    participle = new POSRu("Причастие", "прич.", POS.participle);
    determiner = new POSRu("Детерминатив", "детерминатив", POS.determiner);
    infix = new POSRu("Инфикс", "инфикс", POS.infix);
    interfix = new POSRu("Интерфикс", "интерфикс", POS.interfix);
    affix = new POSRu("Аффикс", "аффикс", POS.affix);
    circumfix = new POSRu("Циркумфикс", "циркумфикс", POS.circumfix);
    counter = new POSRu("Счётное", "счётное", POS.counter);
    predicative = new POSRu("Именная часть составного сказуемого", "предикатив", POS.predicative);

    // only in Russian Wiktionary (yet)
    verb_interjection = new POSRu("Глагольно-междометное слово", "глагольно-междом.", POS.verb_interjection);
    parenthesis = new POSRu("Вводное слово", "вводн.", POS.parenthesis);
    prefix_of_compound = new POSRu("Первая часть сложных слов", "первая часть сложн. сл.", POS.prefix_of_compound);
    }
}
