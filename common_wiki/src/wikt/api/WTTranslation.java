/* WTTranslation.java - high-level functions for translation by Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.api;

import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;

/** High-level functions for translation by Wiktionary.
 *
 * @see wikt.word.WTranslation
 * @see wikt.sql.TTranslation
 */
public class WTTranslation {

    /** Translates the word from source to target language. */
    public static String [] translate (Connect connect,
            String source_lang,String target_lang,String word) {


        return null;
    }

    /** Translates the word from source to target language. */
    public static String [] translate (Connect connect,
            LanguageType source_lang,LanguageType target_lang,String word) {

        
        
        return null;
    }

}
