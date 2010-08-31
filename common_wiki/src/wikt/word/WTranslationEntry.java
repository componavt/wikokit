/* WTranslationEntry.java - corresponds to a line in a translations
 *                          of Wiktionary  word.
 * 
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.word;

import wikt.util.WikiText;
import wikipedia.language.LanguageType;
import wikt.multi.ru.WTranslationEntryRu;
import wikt.multi.en.WTranslationEntryEn;

/** One line in the Translation section, i.e. a translation to one language.
 */
public class WTranslationEntry {
    
    /** Foreign language. */
    private LanguageType lang;
    
    /** Foreign words (phrases) with context comments, i.e, labels, tags,
     * e.g. "[[little]] [[bell]]". */
    private WikiText[] phrases;

    public WTranslationEntry(LanguageType _lang, WikiText[] _phrases) {
        lang    = _lang;
        phrases = _phrases;
    }

    /** Gets a destination language of the translation. */
    public LanguageType getLanguage() {
        return lang;
    }
    
    /** Gets array of internal links (wiki words, i.e. words with hyperlinks). */
    public WikiText[] getWikiPhrases() {
        return phrases;
    }

    /** Frees memory recursively. */
    public void free ()
    {
        if(null != phrases) {
            for(int i=0; i<phrases.length; i++) {
                phrases[i].free();
                phrases[i] = null;
            }
            phrases = null;
        }
    }


    /** Parses one entry (one line) of a translation box,
     * extracts a language and a list of translations (wikified words) for this language,
     * creates and fills WTranslationEntry.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param text          translaton box text
     * @return WTranslation or null if the translation language or translation text are absent.
     */
    public static WTranslationEntry parse(LanguageType wikt_lang,
                    String page_title,
                    String text)
    {
        WTranslationEntry wte = null;

        LanguageType l = wikt_lang;

        if(l  == LanguageType.ru) {
            wte = WTranslationEntryRu.parse(page_title, text);

        } else if(l == LanguageType.en) {
            wte = WTranslationEntryEn.parse(page_title, text);
            
        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;

            // todo
            // ...

        } else {
            throw new NullPointerException("Null LanguageType");
        }

        return wte;
    }


}
