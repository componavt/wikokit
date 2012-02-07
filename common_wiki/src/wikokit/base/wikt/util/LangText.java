/* LangText.java - data structure consists of a language code and the corresponding text.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.util;

import wikokit.base.wikipedia.language.LanguageType;

/** Data structure consists of a language code and the corresponding text.
 */
public class LangText {
    
    /** Language of the text, e.g. the article about one word can contain "en" block for English word, "de", "fr", etc. */
    private LanguageType lang;
    
    /** Text */
    public StringBuffer text;
    
    public LangText() {}
    
    public LangText(LanguageType _lang) { //, StringBuffer _text) {
        lang = _lang;
        text = new StringBuffer();
        //text = _text;
    }

    /** Gets language of the text, e.g. "en" for English word, "de", "fr", etc. */
    public LanguageType getLanguage() {
        return lang;
    }
}
