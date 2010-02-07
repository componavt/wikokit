/* WMeaningEn.java - corresponds to a Meaning (definition + quotations)
 * level of a word in English Wiktionary.
 *
 * Copyright (c) 2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.en;

import wikt.constant.ContextLabel;
import wikt.word.WMeaning;
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
public class WMeaningEn {

    private final static WMeaning[] NULL_WMEANING_ARRAY = new WMeaning[0];

    /** Gets position before first header ^===, e.g. */
    private final static Pattern ptrn_meaning_header_start = Pattern.compile(
            "\n===");    //"(?m)^===");

    /** Gets position before first meaning started by "#"
        // # Meaning 1
        // #* Quotations
     */
    private final static Pattern ptrn_first_meaning = Pattern.compile(
            "(?m)^#");

    /** splits meaning with quotations by "\n#" and not by "\n#*"
        // # Meaning 1
        // #* '''Year''', Author, ''Source title'', Publisher, pages #–#:
        // #*: First quotation of '''word'''.
     */
    private final static Pattern ptrn_meaning_with_quot = Pattern.compile(
            "(?m)^#(?![*:])");
            //"(?m)^#(?!\\*)");

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
        // === Level III or IV. Meaning (definition) ===
        if(null == pt.getText())
            return NULL_WMEANING_ARRAY;

        StringBuffer text = pt.getText();
        if(0 == text.length())
            return NULL_WMEANING_ARRAY;

        // 1. gets position in text before first ^=== (e.g. ====Synonyms====)
        Matcher m = ptrn_meaning_header_start.matcher(text.toString());
        
        int pos_start_meanings = 0;
        int pos_end_meanings; // position of end of definitions and examples
        if(m.find())
            pos_end_meanings = m.start();
        else
            pos_end_meanings = text.length();
            
        // gets position of first definition (first "#")
        m = ptrn_first_meaning.matcher(text.toString());

        boolean b_exist_definition = true;
        if(m.find())
            pos_start_meanings = m.start();
        else
            b_exist_definition = false; // pos_start_meanings = 0;
        
        String defs_text = ""; // text with definitions and examples

        if(b_exist_definition && pos_start_meanings < pos_end_meanings)
            defs_text = text.substring( pos_start_meanings, pos_end_meanings ).trim();
        else
            b_exist_definition = false;

        if(!b_exist_definition || defs_text.length() == 0) {   // there is no definition section!
            // more intelectual definition of empty definition
            // todo
            // e.g. # ...
            System.out.println("Warning in WMeaningEn.parse(): The article '" +
                        page_title + "', '" +
                        lang_section.toString() + "' language section has no # Definition.");
            return NULL_WMEANING_ARRAY;
        }

        // split by "\n#"
        // # Meaning 1
        // #* Quotations
        // ptrn_meaning_with_quot

        String[] meaning_with_quat =
            ptrn_meaning_with_quot.split(defs_text);

        List<WMeaning> wm_list = null;
        for(int i=1; i<meaning_with_quat.length; i++) { // [0] == "";
            String mean_lines = meaning_with_quat[i];

            // extracts \n# {{label}} Definition. \n#::? Example sentence.
            // return WMeaning
            // return null if this line is not started from "#" or = "# "
            WMeaning wm = WMeaning.parseOneDefinition(
                                wikt_lang, page_title, lang_section, mean_lines);
            if(null != wm) {
                if(null == wm_list)
                    wm_list = new ArrayList<WMeaning>();
                wm_list.add(wm);
            }
        }
        
        if(null == wm_list)
            return NULL_WMEANING_ARRAY;

        return( (WMeaning[])wm_list.toArray(NULL_WMEANING_ARRAY) );
    }


    /** Parses (usually) two lines: definition line and quotation line,
     * i.e. extracts {{label}}, # definition, 
     * and #: Quotation sentence. with #:: Translation sentence.
     * , creates and fills a meaning (WMeaning).
     * 
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param text          text with one definition 
     * @return WMeaning or null if the line is not started from "#" or = "# "
     */
    public static WMeaning parseOneDefinition(LanguageType wikt_lang,
                    String page_title,
                    LanguageType lang_section,
                    String line)
    {
        // remove empty quotations: {{пример|}} and {{пример}}
 /*       line = line.replace("{{пример|}}", "");
        line = line.replace("{{пример}}", "");
        line = line.replace("{{пример перевод|}}", ""); // todo check - does exist this example
*/
        line = Definition.getFirstLine(page_title, line);
        line = Definition.stripNumberSign(page_title, line).trim();

        if(0 == line.length())
            return null;

        //if(line.startsWith("{{морфема"))
        //    return null;    // skip now, todo (parse) in future

        // 1. extract labels
        // todo
        // ...
        ContextLabel[] labels = new ContextLabel[0];

        // extract definition by parsing wiki-text

        // 2. extract text till first {{пример|
        String wiki_definition = line; //WQuoteRu.getDefinitionBeforeFirstQuote(page_title, line);

        // 3. parsing wiki-text
        //StringBuffer definition = WikiWord.parseDoubleBrackets(page_title, new StringBuffer(wiki_definition));
        // 4. extract wiki-links (internal links)
        //WikiWord[] ww = WikiWord.getWikiWords(page_title, new StringBuffer(wiki_definition));

        // 5. extract quotations
        WQuote[] quote = null;
        // todo ...

        return new WMeaning(page_title, labels, wiki_definition, quote);
    }

}
