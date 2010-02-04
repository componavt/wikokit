/* WTranslation.java - corresponds to a Translations level of Wiktionary word.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.word;

//import wikt.util.WikiWord;
import wikt.util.WikiText;
import wikipedia.language.LanguageType;
import wikt.util.POSText;
import wikt.multi.ru.WTranslationRu;
import wikt.multi.en.WTranslationEn;

/** Translations of Wiktionary word.
 * 
 * Quote from http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained:
 * The translation section is separated into a number of divisions that are 
 * keyed to the various meanings of the English word. Each division is 
 * separated into a distinct collapsible navigation box by use of the 
 * translation section templates. 
 * The boxes are each headed by a summary of the translated meaning.
 */
public class WTranslation {
    
    /** Section (box) title, i.e. additional comment, 
     * e.g. "fruit" or "apple tree" for "apple".
     * A summary of the translated meaning.
     */
    private String meaning_summary;
    
    /** Translations */
    private WTranslationEntry[] entry;

    private final static WTranslation[] NULL_WTRANSLATION_ARRAY = new WTranslation[0];
    //private final static WTranslation   NULL_WTRANSLATION       = new WTranslation();

    public WTranslation(String _meaning_summary, WTranslationEntry[] _entry) {
        meaning_summary = _meaning_summary;
        entry = _entry;
    }

    /** Gets a summary of the translated meaning, i.e. a header of the box. */
    public String getHeader() {
        return meaning_summary;
    }
    
    /** Gets translation entries into all languages. */
    public WTranslationEntry[] getTranslations() {
        return entry;
    }

    /** Gets number of translation entries. */
    public int getTranslationsNumber() {
        return entry.length;
    }
    
    /** Gets translation entries into the languages 'lang'. */
    public WikiText[] getTranslationIntoLanguage(LanguageType lang) {
    //public WikiWord[] getTranslationIntoLanguage(LanguageType lang) {
        
        for(WTranslationEntry e : entry) {
            if(lang == e.getLanguage()) {
                return e.getWikiPhrases();  // return e.getWikiWords();
            }
        }

        return null;
    }

    /** Parses text (related to the Translation), creates and fills array of 
     * translations (WTranslation) for each meaning of a word.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param pt            POSText defines POS stored in pt.text
     * @return
     */
    public static WTranslation[] parse (
                    LanguageType wikt_lang,
                    String page_title,
                    LanguageType lang_section,
                    POSText pt)
    {
        // === Level III. Translation ===
        WTranslation[] wt = NULL_WTRANSLATION_ARRAY;

        LanguageType l = wikt_lang;

        if(l  == LanguageType.ru) {

            // get context labels, definitions, and quotations
            /*   if(0==wm.length) {
                    return NULL_WTRANSLATION_ARRAY;
            }*/
            wt = WTranslationRu.parse(wikt_lang, lang_section, page_title, pt);

        } else if(l == LanguageType.en) {
            wt = WTranslationEn.parse(wikt_lang, lang_section, page_title, pt);
            
        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;

            // todo
            // ...

        } else {
            throw new NullPointerException("Null LanguageType");
        }

        return wt;
    }


    /** Parses one translation box, i.e. extracts languages and a list of
     * translations (wikified words) for each language,
     * creates and fills WTranslation.
     * 
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param line          definition line
     * @return WTranslation or null if the translation text block was not found.
     */
    public static WTranslation parseOneTranslationBox(LanguageType wikt_lang,
                    String page_title,
                    String line)
    {
        WTranslation wt = null;

        LanguageType l = wikt_lang;

        if(l  == LanguageType.ru) {
            wt = WTranslationRu.parseOneTranslationBox(wikt_lang, page_title, line);

        } else if(l == LanguageType.en) {
            wt = WTranslationEn.parseOneTranslationBox(wikt_lang, page_title, line);
            
        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;

            // todo
            // ...

        } else {
            throw new NullPointerException("Null LanguageType");
        }

        return wt;
    }
}
