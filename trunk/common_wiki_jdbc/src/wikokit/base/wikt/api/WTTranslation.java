/* WTTranslation.java - high-level functions for translation by Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.api;

import wikokit.base.wikt.sql.TTranslation;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.util.StringUtil;

import java.util.List;
import java.util.ArrayList;

/** High-level functions for translation by Wiktionary.
 *
 * @see wikt.word.WTranslation
 * @see wikt.sql.TTranslation
 */
public class WTTranslation {

    private final static String[] NULL_STRING_ARRAY = new String[0];
    
    /** Translates the word from the native language to a target language.
     * Direct translation, i.e. get TranslationBox, get TranslationEntry from it.
     * Native language is a language of Wiktionary edition, e.g. Russian in Russian Wiktionary.
     */
    public static String [] getDirectTranslation (Connect connect,
                                        LanguageType target_lang,String word) {
                                        
        TPage tpage = TPage.get(connect, word);
        if(null == tpage)
            return NULL_STRING_ARRAY;
            
        TLang source_tlang = TLang.get(connect.getNativeLanguage());
        if(null == source_tlang) {
            System.err.println("Error (WTTranslation.getDirectTranslation()):: What about calling 'TLang.createFastMaps()' and 'TPOS.createFastMaps()' before?");
            return NULL_STRING_ARRAY;
        }
        if(connect.getNativeLanguage() == target_lang) {
            System.err.println("Error (WTTranslation.getDirectTranslation()):: How to translate from "+target_lang+" to "+target_lang+"?");
            return NULL_STRING_ARRAY;
        }
        
        TPage[] pages = TTranslation.fromPageToTranslations(connect, source_tlang, tpage, TLang.get(target_lang));
        if(0 == pages.length)
            return NULL_STRING_ARRAY;

        List<String> translations = new ArrayList<String>(pages.length);
        for(TPage p : pages) {
            translations.add(p.getPageTitle());
        }

        return((String[])(StringUtil.getUnique(translations)).toArray(NULL_STRING_ARRAY));
    }

    /** Translates the word from a foreign language to the native language.
     * Backward translation, i.e. get title of page which has a TranslationBox with the given word.
     * Native language is a language of Wiktionary edition, e.g. Russian in Russian Wiktionary.
     */
    public static String [] getBackwardTranslation (Connect connect,
            LanguageType foreign_lang,String word) {

        if(connect.getNativeLanguage() == foreign_lang) {
            System.err.println("Error (WTTranslation.getBackwardTranslation()):: How to translate from "+foreign_lang+" to "+foreign_lang+"?");
            return NULL_STRING_ARRAY;
        }

        TPage tpage = TPage.get(connect, word);
        if(null == tpage)
            return NULL_STRING_ARRAY;

        TLang source_tlang = TLang.get(connect.getNativeLanguage());
        if(null == source_tlang) {
            System.err.println("Error (WTTranslation.getBackwardTranslation()):: What about calling 'TLang.createFastMaps()' and 'TPOS.createFastMaps()' before?");
            return NULL_STRING_ARRAY;
        }

        TPage[] pages = TTranslation.fromTranslationsToPage(connect,
                        source_tlang,
                tpage,  TLang.get(foreign_lang));
        if(0 == pages.length)
            return NULL_STRING_ARRAY;

        List<String> translations = new ArrayList<String>(pages.length);
        for(TPage p : pages) {
            translations.add(p.getPageTitle());
        }

        return((String[])(StringUtil.getUnique(translations)).toArray(NULL_STRING_ARRAY));
    }

    /** Translates the word from a foreign language to the native language.
     */
    private static String [] fromForeignIntoNative (Connect connect,
            LanguageType foreign_lang,String word) {

        String[] backward = getBackwardTranslation (connect, foreign_lang, word);

        String[] meanings = WTMeaning.getDefinitionsByPageLang(connect, word, foreign_lang);
        return StringUtil.addORCaseSensitive(backward, meanings);
    }

    /** Translates the word from the native language to a foreign language.
     *
     * @param native_words  source words in the native language
     */
    private static String [] fromNativeIntoForeign (Connect connect,
            LanguageType foreign_lang,String[] native_words) {
        
        List<String> trans_list = new ArrayList<String>();

        for(String w : native_words) {
            String[] w_translations = getDirectTranslation (connect, foreign_lang, w);
            for(String t : w_translations) {
                if(!trans_list.contains(t))
                    trans_list.add(t);
            }
        }

        return (String[])trans_list.toArray(NULL_STRING_ARRAY);
    }
    
    /** Translates the word from source to target language. */
    public static String [] translate (Connect connect,
                LanguageType source_lang,LanguageType target_lang,String word) {

        String [] translation = NULL_STRING_ARRAY;
        LanguageType native_lang = connect.getNativeLanguage();

        // 1. from native language into foreign
        // only direct translation

        // 2. from foreign into native
        // backward translation + meaning's definition

        // 3. from foreign into another foreign
        // backward translation + meaning's definition

        boolean from_native = source_lang == native_lang;
        boolean into_native = target_lang == native_lang;

        if(from_native && !into_native)         // 1. from native language into foreign
            translation = getDirectTranslation (connect, target_lang, word);
        else {
            if(!from_native && into_native) {   // 2. from foreign into native

                translation = fromForeignIntoNative(connect, source_lang, word);
                
            } else {
                if(!from_native && !into_native) { // 3. from foreign into another foreign

                    // A. from foreign to native
                    //String [] native_words = fromForeignIntoNative(connect, source_lang, word); - bad idea, since definition can contain a lot of non-relevant words.
                    String[] native_words = getBackwardTranslation (connect, source_lang, word);

                    // B. from native to another foreign
                    translation = fromNativeIntoForeign(connect, target_lang, native_words);
                }
            }
        }
        return translation;
    }

    /** Translates the word from source to target language. */
    public static String [] translate (Connect connect,
            String source_lang,String target_lang,String word) {

        if(!LanguageType.has(source_lang)) {
            System.err.println("Error (common_wiki WTTranslation.translate(3 strings)):: uknown source language code:"+source_lang);
            return NULL_STRING_ARRAY;
        }

        if(!LanguageType.has(target_lang)) {
            System.err.println("Error (common_wiki WTTranslation.translate(3 strings)):: uknown target language code:"+target_lang);
            return NULL_STRING_ARRAY;
        }

        return translate(connect,   LanguageType.get(source_lang),
                                    LanguageType.get(target_lang), word);
    }

}
