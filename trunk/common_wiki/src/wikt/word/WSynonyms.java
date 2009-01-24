/* WSynonyms.java - corresponds to a Synonym level of Wiktionary word.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.word;

import wikt.util.WikiWord;

/** Synonyms of Wiktionary word.
 */
public class WSynonyms {
    
    /** Comment for the set of synonyms, e.g. synonyms for "entry":
     * * (''act of entering''): [[access]], [[enter]]ing, [[entrance]], 
     * * (''doorway that provides a means of entering a building''): [[entrance]], [[way in]] {{UK}}
     * .comment=act of entering
     * .comment=doorway...
     * .words[1].tag=UK
     */
    private String[] comment;
    
    /** Synonyms list with tags */
    private WikiWord[] words;
    
}
