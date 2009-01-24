/* LabelEn.java - contexual information for definitions, or Synonyms,
 *                or Translations in English Wiktionary.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.en;

import wikt.constant.ContextLabel;

/** Contexual information for definitions, or Synonyms, or Translations 
 * in English Wiktionary.
 * 
 * See http://en.wiktionary.org/wiki/Template_talk:context
 *     http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 */
public class LabelEn extends ContextLabel {
    
    private LabelEn(String label,String name,String category) {
        super(label, name, category);
    }
    
    public static final ContextLabel AU     = new LabelEn("AU",     "Australia",    "");
    public static final ContextLabel slang  = new LabelEn("slang",  "slang",        "");
    
    public static final ContextLabel astronomy = new LabelEn("astronomy","astronomy",   "Astronomy");
}
