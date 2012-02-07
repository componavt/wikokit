/* WQuoteEn.java - corresponds to the phrase/sentence that illustrates a meaning
 *               of a word in English Wiktionary.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.en;

/** Phrase or sentence that illustrates a meaning of a word in Russian Wiktionary.
 */
public class WQuoteEn {


    /** Removes highlighted marks from a sentence.
     * Sentence with '''words'''. -> Sentence with words.
     */
    public static String removeHighlightedMarksFromSentence(String str)
    {
        if(str.contains("'''"))
            return str.replace("'''", "");

        return str;
    }
    
}
