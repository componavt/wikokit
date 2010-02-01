/* WQuote.java - corresponds to the phrase/sentence that illustrates a meaning 
 *               of Wiktionary word.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.word;

/** Phrase or sentence that illustrates a meaning of Wiktionary word.
 */
public class WQuote {
    
    /** Text of an example sentence. */
    private StringBuffer    text;

    /** Translation of the example sentence in foreign. */
    private StringBuffer    translation;

    /** Start and end positions of the highlighted word(s) in the quote, by the template {{выдел|}} */
    //private int[][2] start_end_pos;

    /** Gets definition line of text. */
    public String getText() {
        return text.toString();
    }

}
