/* WTranslationEntryRu.java - corresponds to a line in Translations of a word
 *                              in Russian Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.ru;

import wikt.word.WTranslationEntry;
import wikipedia.language.LanguageType;
import wikt.util.WikiText;

/** One line in the Translation section, i.e. a translation to one language,
 * e.g. "|en=[[airplane]], [[plane]], [[aircraft]]".
 */
public class WTranslationEntryRu {
    
    /** Parses one entry (one line) of a translation box,
     * extracts a language and a list of translations (wikified words) for this language,
     * creates and fills WTranslationEntry.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param text          translaton box text
     * @return WTranslationEntry or null if the translation language or translation text are absent.
     */
    public static WTranslationEntry parse(
                    String page_title,
                    String text)
    {
        // split "en=[[little]] [[bell]], [[handbell]], [[doorbell]]" into "en" and remain
        int pos_equal_sign = text.indexOf('=');

        // 1. language code
        String lang_code = text.substring(0, pos_equal_sign);
        if(!LanguageType.has(lang_code)) {
            System.out.println("Warning in WTranslationEntryRu.parse(): The article '"+
                        page_title + "' has translation into unknown language with code " + lang_code + ".");
            return null;
        }
        LanguageType lang = LanguageType.get(lang_code);
        
        // 2. translation wikified text
        if(pos_equal_sign + 1 >= text.length()) {
            return null;
        }
        String trans_text = text.substring(pos_equal_sign+1);
        if(0 == trans_text.length()) {
            return null;
        }

        WikiText[] wt = WikiText.create(page_title, trans_text);
        if(0 == wt.length) {
            return null;
        }
        
        return new WTranslationEntry(lang, wt);
    }

}
