/* WRelation.java - corresponds to semantic relations level of Wiktionary word.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.word;

import wikt.util.WikiText;
import wikt.constant.Relation;
import wikt.multi.ru.WRelationRu;

import wikipedia.language.LanguageType;
import wikt.util.POSText;

import java.util.Map;
import java.util.HashMap;

/** Semantic relations of Wiktionary word.
 *
 * See http://en.wiktionary.org/wiki/Wiktionary:Semantic_relations
 */
public class WRelation {
    
    /** Kind of semantic relation, e.g. synonymy, antonymy, etc... */
    //private Relation semantic_relation;
    
    /** List of words (phrases) with context comments, i.e, labels,
     * e.g. two phrases: "[[служба]]; частичн.: [[пост]]".*/
    private WikiText[] phrases;

    // In some Wiktionaries (e.g. Russian): 
    // "near synonyms" are split by comma, far synonyms are split by semicolon
    // private int[] phrases_number_which_end_by_semicolon
    // todo
    
    
    //private final static WRelation[] NULL_WRELATION_ARRAY = new WRelation[0];
    private final static Map<Relation, WRelation[]> NULL_MAP_RELATION_WRELATION_ARRAY = new HashMap<Relation, WRelation[]>();

    /** Gets array of relations (word or phrases). */
    public WikiText[] get() {
        return phrases;
    }

    /** Parses text (related to the semantic relations, e.g. synonymy),
     * creates and fills array of related words (WRelation) for each meaning of a word.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param pt            POSText defines POS stored in pt.text
     * @return
     */
    public static Map<Relation, WRelation[]> parse (
                    LanguageType wikt_lang,
                    String page_title,
                    LanguageType lang_section,
                    POSText pt)
    {
        // ==== Level IV. Synonyms, Antonyms, ... ====
        Map<Relation, WRelation[]> wr = NULL_MAP_RELATION_WRELATION_ARRAY;

        LanguageType l = wikt_lang;

        if(l  == LanguageType.ru) {

            // get synonym context labels, get synonyms, etc.
            wr = WRelationRu.parse(wikt_lang, page_title, pt);

        //} else if(l == LanguageType.en) {
          //  return WordEn;
        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;

            // todo
            // ...

        } else {
            throw new NullPointerException("Null LanguageType");
        }

        return wr;
    }
}
