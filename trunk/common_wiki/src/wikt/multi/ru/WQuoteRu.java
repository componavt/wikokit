/* WQuoteRu.java - corresponds to the phrase/sentence that illustrates a meaning
 *               of a word in Russian Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.ru;

/** Phrase or sentence that illustrates a meaning of a word in Russian Wiktionary.
 */
public class WQuoteRu {
    private static final boolean DEBUG = false;

    /* Gets, extracts from 'text' a definition till first example sentence starting from {{пример|. */
    public static String getDefinitionBeforeFirstQuote (String page_title, String text) {
        
        // Gets position before first example sentence {{пример|Самолёт-истребитель.}}
        int pos_quote = text.indexOf("{{пример|");

        if(-1 == pos_quote) {   // there is no quote section!
            if(DEBUG)
                System.out.println("Warning in WQuoteRu.getDefinitionBeforeFirstQuote(): The article '"+
                                    page_title + "' has no quote '{{пример|' in a definition.");
            return text;
        }

        return text.substring(0, pos_quote).trim();
    }

}
