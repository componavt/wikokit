/* WordBase.java - list of main fields common in Wiktionaries.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.word;

//import wikt.util.LangText;
import wikipedia.language.LanguageType;
//import wikt.word.ru.WordRu;

/** Article in Wiktionary.
 *
 * See http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 */
public class WordBase {
    
    /** Article title in Wiktionary. */
    private String page_title;
    
    /** Language level of the word (includes: POS, meaning, translation). */
    private WLanguage[] lang;
    
    public WordBase(
            String _page_title,
            LanguageType wikt_lang, // constant for the Wiktionary dump
            StringBuffer text) {
        
        page_title = _page_title;
        
        //LangText[] lang_sections = WLanguage.splitToLanguageSections(wikt_lang, page_title, text);
        lang = WLanguage.parse(wikt_lang, page_title, text);
    }
    
    /*public WordBase(String _page_title) {
        page_title = _page_title;
    }*/

    /** Gets an article title in Wiktionary. */
    public String getPageTitle() {
        return page_title;
    }

    /** Creates word for the given Wiktionary (defined by language)
     * by parsing the Wiktionary article text.
     * Stores parsed data to the WordBase object.
     * 
     * @param   page_title  the word itself
     */
    /*public static WordBase create(
            String page_title,
            LanguageType wikt_lang, StringBuffer text) {
        
        LanguageType l = wikt_lang;
        WordBase w;
        
        if(l  == LanguageType.ru) {
            return new WordRu(page_title);
        //} else if(l == LanguageType.en) {
          //  return WordEn;
        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;
            
            // todo 
            // ...
            
        } else {
            throw new NullPointerException("Null LanguageType");
        }
    }*/
    
    
    /** Word is empty if there are no recognized data in the parsed wiki text. */
    public boolean isEmpty() {
        
        if (null != lang && lang.length > 0)
                return false;
        
        return true;
    }

}
