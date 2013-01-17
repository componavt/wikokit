/* LabelEn.java - contexual information for definitions, or Synonyms,
 *                or Translations in English Wiktionary.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.en;

import wikokit.base.wikt.constant.Label;

/** Contexual information for definitions, or Synonyms, or Translations 
 * in English Wiktionary.
 * 
 * See http://en.wiktionary.org/wiki/Template_talk:context
 *     http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 *     http://en.wiktionary.org/wiki/Template:context
 */
public class LabelEn extends Label {
    
    private LabelEn(String label,String name,String category) {
        super(label, name, category);
    }
    
    public static final Label AU     = new LabelEn("AU",     "Australia",    "");
    public static final Label slang  = new LabelEn("slang",  "slang",        "");
    
    public static final Label astronomy = new LabelEn("astronomy","astronomy",   "Astronomy");
    
    // synonyms:  {{math}} and {{maths}} -> (mathematics)
}
