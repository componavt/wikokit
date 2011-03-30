/* WPOS.java - corresponds to a Part of Speech level of Wiktionary word.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.word;

import wikipedia.language.LanguageType;
import wikt.util.POSText;
import wikt.util.LangText;

import wikt.constant.POS;
import wikt.constant.Relation;

import wikt.multi.ru.WPOSRu;
import wikt.multi.en.WEtymologyEn;
import wikt.multi.en.WPOSEn;

import java.util.Map;


/** Part of Speech may be a misnomer... It is the key descriptor for the 
 * lexical function of the term in question (such as 'noun', 'verb', etc). 
 * The definitions themselves come within its scope. In addition to the 
 * traditional “parts of speech” it has come to include entities that are less 
 * than words, such as initialisms and suffixes, and items that are 
 * more than words, such as idiomatic expressions, phrases and proverbs. 
 * This heading is nestable. It is most frequently in a level three heading, 
 * but may have a lower level for terms that have multiple etymologies or 
 * pronunciations.
 *
 * WPOS consists of <PRE>
 * # Meaning (Definition (preceded by "#", which causes automatic numbering) + Quotations).
 * # Semantic relations (synonyms, antonyms, etc.) only for this (first, second...) meaning
 * # Translation        </PRE>
 *
 * See http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 * @see wikt.sql.TLangPOS
 */
public class WPOS {
    
    /** Part of speech. */
    private POS pos_type;
    
    // Pronunciations
    // todo ...
    
    /** (1) Meaning consists of Definitions + Quotations. */
    private WMeaning[] meaning;
    
    /** (2) Semantic relations: synonymy, antonymy, etc.
     * The map from semantic relation (e.g. synonymy) to array of WRelation
     * (one WRelation contains a list of synonyms for one meaning).
     */
    private Map<Relation, WRelation[]> relation;
    
    /** (3) Translation */
    private WTranslation[] translation;

    /** Text which is not belong to any POS texts, e.g. Bibliography, Links...*/
    //public StringBuffer remain_text;

    private final static WPOS[] NULL_WPOS_ARRAY = new WPOS[0];

    /** Gets part of speech. */
    public POS getPOS() {
        return pos_type;
    }

    /** Gets all senses. */
    public WMeaning[] getAllMeanings() {
        return meaning;
    }

    /** Gets all relations. */
    public Map<Relation, WRelation[]> getAllRelations() {
        return relation;
    }

    /** Gets all translations. */
    public WTranslation[] getAllTranslation() {
        return translation;
    }

    /** Frees memory recursively. */
    public void free ()
    {
        if(null != meaning) {
            for(int i=0; i<meaning.length; i++) {
                meaning[i].free();
                meaning[i] = null;
            }
            meaning = null;
        }

        if(null != relation) {
            for(WRelation[] wr : relation.values())
                for(WRelation r : wr)
                    r.free();
            relation.clear();
            relation = null;
        }

        if(null != translation) {
            for(int i=0; i<translation.length; i++) {
                translation[i].free();
                translation[i] = null;
            }
            translation = null;
        }
    }

    
    /** Parses text, creates and fills array of meanings (WLanguage),
     * semantic relations, translations.
     * 
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param text          LangText defines language of this POS stored in "text"
     * @return
     */
    public static WPOS[] parse (
                    LanguageType wikt_lang,
                    String page_title,
                    LangText lang_section)
    {
        // == Level II. Part of speech ==
        POSText[] pt = WPOS.splitToPOSSections(wikt_lang, page_title, lang_section);

        if(0==pt.length) {
            return NULL_WPOS_ARRAY;
        }

        WPOS[] wpos = new WPOS[pt.length];      // result
        for(int j=0; j<pt.length; j++) {
            wpos[j] = new WPOS();
            wpos[j].pos_type = pt[j].getPOSType();
            wpos[j].meaning = WMeaning.parse(wikt_lang, page_title, lang_section.getLanguage(), pt[j]);
            
            // === III. Semantic relations ==
            wpos[j].relation = WRelation.parse(wikt_lang, page_title, lang_section.getLanguage(), pt[j]);

            // === III. Translations ==
            wpos[j].translation = WTranslation.parse(wikt_lang, page_title, lang_section.getLanguage(), pt[j]);
        }
        
        return wpos;
    }



    /** Splits text to fragments related to different parts of speech (POS).
     * @param page_title   word which are described in this article text */
    public static POSText[] splitToPOSSections (
                    LanguageType wikt_lang,
                    String      page_title,
                    LangText    source_langtext)
    {
        POSText[] pos_sections; // result will be stored to
        
        LanguageType l = wikt_lang;
        
        if(l  == LanguageType.ru) {
            pos_sections = WPOSRu.splitToPOSSections(page_title, source_langtext);
        } else if(l == LanguageType.en) {

            LangText[] etymology_sections = WEtymologyEn.splitToEtymologySections(page_title, source_langtext);
            pos_sections = WPOSEn.splitToPOSSections(page_title, etymology_sections);

          //  return WordEn;
        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;
            
            // todo 
            // ...
            
        } else {
            throw new NullPointerException("Null LanguageType");
        }
        
        
        return pos_sections;
    }
}
