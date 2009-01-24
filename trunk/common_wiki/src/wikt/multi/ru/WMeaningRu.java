/* WMeaningRu.java - corresponds to a Meaning (definition + quotations)
 * level of a word in Russian Wiktionary.
 *
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.ru;

import wikt.constant.ContextLabel;
import wikt.word.WMeaning;
import wikt.util.WikiWord;
import wikt.word.WQuote;
import wikipedia.language.LanguageType;
import wikt.util.POSText;
import wikt.util.Definition;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** Meaning consists of <PRE>
 * # Definition (preceded by "#", which causes automatic numbering).
 * and Quotations.      </PRE>
 */
public class WMeaningRu {
    
    private final static WMeaning[] NULL_WMEANING_ARRAY = new WMeaning[0];

    /** Gets position after ==== Значение ==== */
    private final static Pattern ptrn_meaning_4th_level = Pattern.compile(
            "====?\\s*Значение\\s*====?\\s*\\n");
    
    /** Parses text (related to the POS), creates and fill array of meanings (WMeaning).
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
        if(null == pt.getText()) {
            return NULL_WMEANING_ARRAY;
        }
        StringBuffer text = pt.getText();
        if(0 == text.length()) {
            return NULL_WMEANING_ARRAY;
        }

        // 1. gets position in text after ==== Значение ====
        Matcher m = ptrn_meaning_4th_level.matcher(text.toString());
        boolean b_next = m.find();

        if(!b_next) {   // there is no definition section!
            System.out.println("Warning in WMeaningRu.parse(): The article '"+
                        page_title + "' has no section ==== Значение ====.");
            return NULL_WMEANING_ARRAY;
        }

        // 2. skip (or add?) one-line with proto meaning
        // e.g. {{прото|передняя, выдающаяся вперёд часть чего-либо}}
        // todo
        // ....

        List<WMeaning> wm_list = new ArrayList<WMeaning>();

        int len = text.length();
        int prev_eol = m.end();  // previous end of line
        boolean to_continue = true;
        while(to_continue) {
            // 3. gets next # line
            int next_eol = text.indexOf("\n", prev_eol);
            if(-1 == next_eol) {
                next_eol = len;
            }
            String line = text.substring(prev_eol, next_eol);

            // 4. extracts {{label.}}, definition, {{example|Sentence.}}
            // return WMeaning
            // return null if this line is not started from "#"
            WMeaning wm = WMeaning.parseOneDefinition(wikt_lang, page_title, lang_section, line);
            if(null != wm) {
                wm_list.add(wm);
            }
            to_continue = next_eol < len && (text.charAt(next_eol+1) == '#');
            prev_eol = next_eol + 1;
        }
        
        return( (WMeaning[])wm_list.toArray(NULL_WMEANING_ARRAY) );
    }

    /** Parses one definition line, i.e. extracts {{label}}, definition,
     * {{example|Quotation sentence.}}, creates and fills a meaning (WMeaning).
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param line          definition line
     * @return WMeaning or null if the line is not started from "#"
     */
    public static WMeaning parseOneDefinition(LanguageType wikt_lang,
                    String page_title,
                    LanguageType lang_section,
                    String line)
    {
        line = Definition.stripNumberSign(page_title, line);

        // 1. extract labels
        // todo
        // ...
        ContextLabel[] labels = new ContextLabel[0];

        // extract definition by parsing wiki-text

        // 2. extract text till first {{пример|
        String wiki_definition = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, line);

        // 3. parsing wiki-text
        StringBuffer definition = WikiWord.parseDoubleBrackets(page_title, new StringBuffer(wiki_definition));

        // 4. extract wiki-links (internal links)
        WikiWord[] ww = WikiWord.getWikiWords(page_title, new StringBuffer(wiki_definition));

        // 5. extract quotations
        // todo ...
        WQuote[] quote = null;

        return new WMeaning(labels, definition.toString(), ww, quote);
    }

}
