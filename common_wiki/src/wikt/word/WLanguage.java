/* WLanguage.java - corresponds to a language level of Wiktionary word.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.word;

//import wikt.constant.POS;
import wikt.util.LangText;
//import wikt.util.POSText;
import wikipedia.language.LanguageType;

import wikt.multi.ru.WLanguageRu;
import wikt.multi.en.WLanguageEn;

/** Language lets you know the language of the word in question. It is almost 
 * always in a level two heading. ==English== {{-ru-}}
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
    
    /** Parses text, creates and fill array of homonym (WLanguage) for each language
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
        
        return wl;
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

}
