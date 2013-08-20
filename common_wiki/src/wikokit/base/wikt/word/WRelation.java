/* WRelation.java - corresponds to semantic relations level of Wiktionary word.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.word;

import wikokit.base.wikt.util.WikiText;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.multi.ru.WRelationRu;
import wikokit.base.wikt.multi.en.WRelationEn;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.util.POSText;

import java.util.Map;
import java.util.HashMap;

/** Semantic relations of Wiktionary word.
 *
 * See http://en.wiktionary.org/wiki/Wiktionary:Semantic_relations
 */
public class WRelation {
    
    /** Kind of semantic relation, e.g. synonymy, antonymy, etc... */
    //private Relation semantic_relation;
    
    /** List of words (phrases) with context labels,
     * e.g. two phrases: "[[служба]]; частичн.: [[пост]]", 
     * one phrase can have several labels. */
    private WikiText[] phrases;
    

    // In some Wiktionaries (e.g. Russian): 
    // "near synonyms" are split by comma, far synonyms are split by semicolon
    // private int[] phrases_number_which_end_by_semicolon
    // todo
    
    /** Summary of the definition for which synonyms are being given,
     * e.g. "flrink with cumplus" or "furp" in text
     * <PRE>
     * * (''flrink with cumplus''): [[flrink]], [[pigglehick]]
     * * (''furp''): [[furp]], [[whoodleplunk]]
     * </PRE>
     *
     * Disadvantage: the summary "flrink with cumplus" is repeated twice
     *              in table for "flrink" and "pigglehick".
     *
     * Comment: is used in English Wiktionary, see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained#Synonyms
     * It is not used in Russian Wiktionary (NULL in database).
     */
    private String meaning_summary;


    //private final static WRelation[] NULL_WRELATION_ARRAY = new WRelation[0];
    private final static Map<Relation, WRelation[]> NULL_MAP_RELATION_WRELATION_ARRAY = new HashMap<Relation, WRelation[]>();

    public WRelation(String _meaning_summary, WikiText[] _phrases) {
        phrases = _phrases;
        meaning_summary = _meaning_summary;
    }

    /** Gets array of relations (word or phrases). */
    public WikiText[] get() {
        return phrases;
    }

    /** Gets a summary of the semantic relation meaning (e.g. title of list of synonyms). */
    public String getMeaningSummary() {
        return meaning_summary;
    }

    /** Frees memory recursively. */
    public void free ()
    {
        // WikiText wt
        if(null != phrases) {
            for(int i=0; i<phrases.length; i++) {
                phrases[i].free();
                phrases[i] = null;
            }
            phrases = null;
        }
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

        // get synonym context labels, get synonyms, etc.
        if(l == LanguageType.ru) {
            wr = WRelationRu.parse(wikt_lang, page_title, pt);

        } else if(l == LanguageType.en) {
            wr = WRelationEn.parse(wikt_lang, page_title, pt);
            
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
