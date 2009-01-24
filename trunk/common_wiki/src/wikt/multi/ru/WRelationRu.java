/* WRelation.java - corresponds to a semantic relations level of a word in
 * Russian Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.ru;

import wikt.constant.Relation;
import wikt.word.WRelation;

import wikipedia.language.LanguageType;
import wikt.util.POSText;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

//import java.util.List;
//import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

/** Semantic relations  of Russian Wiktionary word.
 *
 * See http://ru.wiktionary.org/wiki/Викисловарь:Правила оформления статей#Оформление семантических отношений
 */
public class WRelationRu {

    //private final static WRelation[] NULL_WRELATION_ARRAY = new WRelation[0];
    //Map<Relation, WRelation[]> m = new HashMap<Relation, WRelation[]>>();

    private final static Map<Relation, WRelation[]> NULL_MAP_RELATION_WRELATION_ARRAY = new HashMap<Relation, WRelation[]>();

    /** Gets position after ==== Синонимы ==== */
    private final static Pattern ptrn_synonymy = Pattern.compile(
            "===?\\s*Синонимы\\s*===?\\s*\\n");
            
    /** Parses text (related to the POS), creates and fill array of
     * semantic relations (WRelation).
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param pt            POSText defines POS stored in pt.text
     * @param relation_type type of parsing relation, e.g. synonymy
     * @return
     */
    public static Map<Relation, WRelation[]> parse (
                    LanguageType wikt_lang,
                    String page_title,
                    POSText pt)
    {
        // ===Семантические свойства===
        // ====Синонимы====                         // ==== Level IV. Relation ====

        if(null == pt.getText()) {
            return NULL_MAP_RELATION_WRELATION_ARRAY;
        }
        StringBuffer text_source_sb = pt.getText();
        if(0 == text_source_sb.length()) {
            return NULL_MAP_RELATION_WRELATION_ARRAY;
        }

        // 1. gets position in text after ====Синонимы====
        String text_source = text_source_sb.toString();
        Matcher m = ptrn_synonymy.matcher(text_source_sb);
        boolean b_next = m.find();

        if(!b_next) {   // there is no section !
            //System.out.println("Warning in WRelationRu.parse(): The article '"+
            //            page_title + "' has no section ====Синонимы====.");
        }

        // todo parse one kind of relation
        // Relation relation_type
        // ...

        // 2. antonymy


        return null;
    }

}
