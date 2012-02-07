/* WLanguage.java - corresponds to a language level of Wiktionary word.
 * 
 * Copyright (c) 2008-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.word;

//import wikt.constant.POS;
import wikokit.base.wikt.util.LangText;
//import wikt.util.POSText;
import wikokit.base.wikipedia.language.LanguageType;

import wikokit.base.wikt.multi.ru.WLanguageRu;
import wikokit.base.wikt.multi.en.WLanguageEn;

/** Language lets you know the language of the word in question. It is almost 
 * always in a level two heading. E.g. ==English== or {{-ru-}}
 *
 * Exception: ==Translingual==
 * 
 * @see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 * and http://ru.wiktionary.org/wiki/Викисловарь:Правила оформления статей
 */
public class WLanguage {
    
    /** Language of the word. */
    private LanguageType lang;
    
    /** Part of speech. */
    private WPOS[] wpos;

    /** Gets language. */
    public LanguageType getLanguage() {
        return lang;
    }

    /** Gets all parts of speech for this word. */
    public WPOS[] getAllPOS() {
        return wpos;
    }

    private final static WLanguage[] NULL_WLANGUAGE_ARRAY = new WLanguage[0];

    /** Frees memory recursively. */
    public void free ()
    {
        if(null != wpos) {
            for(WPOS p : wpos)
                p.free();
            wpos = null;
        }
    }

    /** Parses text, creates and fills array of homonym (WLanguage) for each language
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param text
     * @return
     */
    public static WLanguage[] parse (
                    LanguageType wikt_lang,
                    String page_title,
                    StringBuffer text)
    {
        // = Level I. Language =
        LangText[] lang_sections = splitToLanguageSections(wikt_lang, page_title, text);
        
        if(0==lang_sections.length) {
            return NULL_WLANGUAGE_ARRAY;
        }
        
        WLanguage[] wl = new WLanguage[lang_sections.length];
        for(int i=0; i<lang_sections.length; i++) {
            wl[i] = new WLanguage();
            wl[i].lang = lang_sections[i].getLanguage();
            wl[i].wpos = WPOS.parse(wikt_lang, page_title, lang_sections[i]);
        }
        
        return reduceNonUniqueLanguages (page_title, wl);
    }

    /** Reduces number of languages, removes any non unique languages.
     * E.g. "kom" and "koi" refer to "kv" language code, see LanguageType.java
     * So if the entry contains the description of two words: "kom" and "koi",
     * then only the first one will be parsed ("kom"), the second ("koi") will be rejected.
     *
     * Side effect: the non unique languages ([] sources) will be set to NULL.
     *
     * @param page_title    word which are described in this article 'text'
     * @param source        entry text parsed and stored into the objects
     * @return
     */
    private static WLanguage[] reduceNonUniqueLanguages (
                    String page_title,WLanguage[] source)
    {
        // 1. let's check that does exist any duplication
        int duplication = 0;
        for(int i=0; i<source.length; i++) {
            for(int j=i+1; j<source.length; j++) {
                if(source[i].lang == source[j].lang) {
                    source[i].free();
                    source[i] = null;
                    duplication ++;
                    break;
                }
            }
        }
        if(0 == duplication)
            return source;

        // 2. copy without duplication, i.e. skip empty (null) elements of array
        assert(source.length - duplication > 0);

        WLanguage[] dest = new WLanguage[source.length - duplication];
        int dest_i = 0;
        for(int i=0; i<source.length; i++) {
            if(null != source[i]) {
                dest [dest_i] = source[i];
                dest_i ++;
            }
        }        
        return dest;
    }
    
    
    /** 
     * @param page_title    word which are described in this article text
     * @param wikt_lang     language of Wiktionary
     */
    public static LangText[] splitToLanguageSections (
                    LanguageType wikt_lang,
                    String page_title,
                    StringBuffer text)
    {
        LangText[] lang_sections; // result will be stored to
        
        LanguageType l = wikt_lang;
        
        if(l  == LanguageType.ru) {
            lang_sections = WLanguageRu.splitToLanguageSections(page_title, text);
        } else if(l == LanguageType.en) {
            lang_sections = WLanguageEn.splitToLanguageSections(page_title, text);

        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;
            
            // todo 
            // ...
            
        } else {
            throw new NullPointerException("Null LanguageType");
        }
        
        
        return lang_sections;
    }


    /** True if the meaning section contains only templates (e.g. {{form of|}}
     * or {{plural of|}}), i.e. there are no any real definitions,
     * there are only references to main (normal) forms of the word.
     *
     * @param lang          parsed entry stored into the array of objects WLanguage
     * @param wikt_lang     language of Wiktionary
     */
    public static boolean hasOnlyTemplatesWithoutDefinitions (
                                LanguageType wikt_lang, WLanguage[] lang)
    {
        boolean b = false;
        if(wikt_lang  == LanguageType.en) {
            b = hasOnlyTemplatesWithoutDefinitions(lang);
        }
        //else if(l == LanguageType.ru) {
        //} else { throw new NullPointerException("Null LanguageType");
        //}
        return b;
    }

    /** True if the meaning section contains only templates, 
     * e.g. {{form_of|}}, or {{plural of|}},
     * i.e. there are only references to main (normal) forms of the word,
     * and there are no any real definitions.
     *
     * @param lang      parsed entry stored into the array of objects WLanguage
     */
    private static boolean hasOnlyTemplatesWithoutDefinitions (
                                WLanguage[] lang)
    {
        boolean at_least_one_template = false;

        for(int i=0; i<lang.length; i++) {
            assert(null != lang[i]);

            WPOS[] wpos = lang[i].getAllPOS();
            for(int j=0; j<wpos.length; j++) {

                assert(null != wpos[j]);
                WMeaning[] wm = wpos[j].getAllMeanings();

                for(WMeaning m : wm) {
                    if(!m.isFormOfInflection())
                        return false;
                    
                    at_least_one_template = true;
                }
            }
        }
        return at_least_one_template;
    }

}
