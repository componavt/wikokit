/* WMeaning.java - corresponds to a Meaning (definition + quotations)
 * level of Wiktionary word.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.word;

import wikt.util.WikiWord;
import wikt.util.WikiText;
import wikt.constant.ContextLabel;
import wikipedia.language.LanguageType;
import wikt.util.POSText;
import wikt.multi.ru.WMeaningRu;
import wikt.multi.en.WMeaningEn;

/** Meaning consists of <PRE>
 * # Definition (preceded by "#", which causes automatic numbering).
 * #* and Quotations.      </PRE>
 */
public class WMeaning {

    // StringBuffer definition;
    // + wiki word, + number of wiki word or number of first char of wikiword in definition
    
    /** Contexual information for definitions, e.g. "idiom" from text "# {{idiom}} [[bullet]]s" */
    private ContextLabel[]  labels;

    private WikiText definition;

    /** Word definition, e.g. "bullets" from text "# {{idiom}} [[bullet]]s" */
    //private StringBuffer    definition;

    /** Wiki internal links, e.g. "bullet" from text "# {{idiom}} [[bullet]]s" */
    //private WikiWord[] wiki_words;
    
    /** Example sentences. */
    private WQuote[] quote;
    
    private final static WMeaning[] NULL_WMEANING_ARRAY = new WMeaning[0];
    private final static WMeaning   NULL_WMEANING       = new WMeaning();

    public WMeaning() {
        labels = null;
        definition = null;
        quote = null;
    }

    /** Constructor.
     *
     * @param page_title
     * @param _labels
     * @param _definition wikified text of the definition
     * @param _quote
     */
    public WMeaning(String page_title,ContextLabel[] _labels, String _definition, WQuote[] _quote) {
        labels = _labels;
        definition = WikiText.createOnePhrase(page_title, _definition);
        quote = _quote;
    }
    
    /** Gets array of context labels in the definition. */
    public ContextLabel[] getLabels() {
        return labels;
    }

    /** Gets definition line of text. */
    public String getDefinition() {
        return definition.getVisibleText();
    }
    
    /** Gets array of internal links in the definition (wiki words, i.e. words with hyperlinks). */
    public WikiWord[] getWikiWords() {
        return definition.getWikiWords();
    }

    /** Gets wiki_text. */
    public WikiText getWikiText() {
        return definition;
    }
    
    /** Gets array of quotes (sentences) from the definition. */
    public WQuote[] getQuotes() {
        return quote;
    }

    /** Parses text (related to the POS), creates and fills array of meanings (WMeaning).
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param pt            POSText defines POS stored in pt.text
     * @return
     */
    public static WMeaning[] parse (
                    LanguageType wikt_lang,
                    String page_title,
                    LanguageType lang_section,
                    POSText pt)
    {
        // === Level III. Meaning ===
        WMeaning[] wm = NULL_WMEANING_ARRAY;

        LanguageType l = wikt_lang;

        if(l == LanguageType.ru) {

            // get context labels, definitions, and quotations... todo
            /*   if(0==wm.length) {
                    return NULL_WMEANING_ARRAY;
            }*/
          wm = WMeaningRu.parse(wikt_lang, page_title, lang_section, pt);

        } else if(l == LanguageType.en) {
            wm = WMeaningEn.parse(wikt_lang, page_title, lang_section, pt);

        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;

            // todo
            // ...

        } else {
            throw new NullPointerException("Null LanguageType");
        }
        
        return wm;
    }

    /** Parses one definition (one line in Russian, several lines in English 
     * Wiktionary), i.e. extracts {{label.}}, definition,
     * {{example|Quotation sentence.}}, creates and fills a meaning (WMeaning).
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param def_text      text of one definition
     * @return WMeaning or null if the line is not started from "#"
     */
    public static WMeaning parseOneDefinition(LanguageType wikt_lang,
                    String page_title,
                    LanguageType lang_section,
                    String def_text)
    {
        WMeaning wm = NULL_WMEANING;

        LanguageType l = wikt_lang;

        if(l == LanguageType.ru) {
            wm = WMeaningRu.parseOneDefinition(wikt_lang, page_title, lang_section, def_text);

        } else if(l == LanguageType.en) {
            wm = WMeaningEn.parseOneDefinition(wikt_lang, page_title, lang_section, def_text);

          //  return WordEn;
        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;

            // todo
            // ...

        } else {
            throw new NullPointerException("Null LanguageType");
        }

        return wm;
    }

}
