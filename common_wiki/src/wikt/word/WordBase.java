/* WordBase.java - list of main fields common in Wiktionaries.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.word;

//import wikt.util.LangText;
import wikipedia.language.LanguageType;
import wikipedia.text.WikiParser;
import wikipedia.text.ReferenceParser;
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

    /** Redirected page, i.e. target or destination page.
     * It is null for usual entries.
     * 
     * Hard redirect defined by #REDIRECT",
     * @see TLangPOS.redirect_type and .lemma - a soft redirect.
     */
    private String  redirect_target;

    /** True, if there are templates {{form of|}} or {{plural of|}},
     * there are no any other text in the definition. (enwikt)
     */
    private boolean  template_not_def;

    /** True if there are template (e.g. {{form of|}}, {{plural of|}}),
     * {{es-verb form of|}}) instead of definiton text (in enwikt). */
    public boolean hasOnlyTemplatesWithoutDefinitions() {
        return template_not_def;
    }
    

    /** Parses the article text.
     * Creates and stores parsed data to the word (WordBase)
     * for the given Wiktionary (defined by wikt_lang language).
     */
    public WordBase(
            String _page_title,
            LanguageType wikt_lang, // constant for the Wiktionary dump
            StringBuffer text) {
        
        page_title = _page_title;

        // remove <!-- comments --> and <ref> ... </ref>
        StringBuffer s = WikiParser.removeHTMLComments(
                            ReferenceParser.removeReferences (text));

        redirect_target = WRedirect.getRedirect(wikt_lang, page_title, s);

        if (null == redirect_target) {    // it is not a redirect
            //LangText[] lang_sections = WLanguage.splitToLanguageSections(wikt_lang, page_title, s);
            lang = WLanguage.parse(wikt_lang, page_title, s);
        }
        template_not_def = WLanguage.hasOnlyTemplatesWithoutDefinitions(wikt_lang, lang);
    }
    
    /** Gets an article title in Wiktionary. */
    public String getPageTitle() {
        return page_title;
    }

    /** Gets all languages. */
    public WLanguage[] getAllLanguages() {
        return lang;
    }

    /** Checks is the entry a REDIRECT. */
    public boolean isRedirect() {
        return null != redirect_target;
    }

    /** Gets a redirected page, i.e. target or destination page.
     * It is null for usual entries.
     */
    public String getRedirect() {
        return redirect_target;
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

        if(isRedirect())
            return false;   // REDIRECT is not an empty article.

        if (null != lang && lang.length > 0)
                return false;
        
        return true;
    }

}
