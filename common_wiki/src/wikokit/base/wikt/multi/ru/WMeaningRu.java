/* WMeaningRu.java - corresponds to a Meaning (definition + quotations)
 * level of a word in Russian Wiktionary.
 *
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru;

import wikokit.base.wikt.multi.ru.name.LabelRu;
import wikokit.base.wikt.multi.en.name.LabelEn;
import wikokit.base.wikt.word.WMeaning;
import wikokit.base.wikt.word.WQuote;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.util.Definition;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.util.LabelsText;

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
                    String page_title,
                    LanguageType lang_section,
                    POSText pt)
    {
        LanguageType wikt_lang = LanguageType.ru;
        
        // === Level III. Meaning ===
        if(null == pt.getText()) {
            return NULL_WMEANING_ARRAY;
        }
        StringBuffer text = pt.getText();
        if(0 == text.length()) {
            return NULL_WMEANING_ARRAY;
        }
        
        // 0. gets all pictures from templates: {{илл|}}
        //String[number of images][2 = filename and caption] fc
        String[] fc = ImageParserRu.getFilenameAndCaption(text.toString());
        if(fc.length == 2) {
            this.
        }
        
        // 1. gets position in text after ==== Значение ====
        Matcher m = ptrn_meaning_4th_level.matcher(text.toString());
        boolean b_next = m.find();

        if(!b_next) {   // there is no definition section!
            //System.out.println("Warning in WMeaningRu.parse(): The article '" +
            //            page_title + "', language section '" +
            //            lang_section.toString() + "' has no section ==== Значение ====.");
            return NULL_WMEANING_ARRAY;
        }
        
        int len = text.length();
        int prev_eol = m.end();         // previous end of line
        
        if(len < prev_eol+3 || text.substring(prev_eol,prev_eol+3).equalsIgnoreCase("==="))
            return NULL_WMEANING_ARRAY; // the definition section is empty!

        List<WMeaning> wm_list = null;
        boolean to_continue = true;
        while(to_continue) {
            // 3. gets next # line
            int next_eol = text.indexOf("\n", prev_eol);
            if(-1 == next_eol) {
                next_eol = len;
            }
            String line = text.substring(prev_eol, next_eol);

            if(!line.startsWith("{{прото|")) // skip one-line with {{proto|common meaning}}
            {
                // 4. extracts {{label.}}, definition, {{example|Sentence.}}
                // return WMeaning
                // return null if this line is not started from "#" or = "# "
                WMeaning wm = WMeaning.parseOneDefinition(wikt_lang, page_title, lang_section, line);
                if(null != wm) {
                    if(null == wm_list)
                        wm_list = new ArrayList<WMeaning>();
                    wm_list.add(wm);
                }
            }
            to_continue = next_eol < len-1 && (text.charAt(next_eol+1) == '#');
            prev_eol = next_eol + 1;
        }

        if(null == wm_list)
            return NULL_WMEANING_ARRAY;

        return( (WMeaning[])wm_list.toArray(NULL_WMEANING_ARRAY) );
    }

    
    /** Parses one definition line, i.e. extracts {{label}}, definition,
     * {{example|Quotation sentence.}}, creates and fills a meaning (WMeaning).
     * @param page_title    word which is described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param line          definition line
     * @return WMeaning or null if the line is not started from "#" or = "# "
     */
    public static WMeaning parseOneDefinition(
                    String page_title,
                    LanguageType lang_section,
                    String line)
    {
        if(line.contains("{{Нужен перевод}}"))
            return null;

        // remove empty quotations: {{пример|}} and {{пример}}
        if(line.contains("{{пример|}}"))
            line = line.replace("{{пример|}}", "");
        if(line.contains("{{пример}}"))
            line = line.replace("{{пример}}", "");
        if(line.contains("{{пример перевод|}}"))
            line = line.replace("{{пример перевод|}}", ""); // todo check - does exist this example

        if(line.contains("[[]]"))
            line = line.replace("[[]]", ""); // empty definition

        line = Definition.stripNumberSign(page_title, line);

        if(0 == line.length())
            return null;

        if(line.startsWith("{{морфема"))
            return null;    // skip now, todo (parse) in future

        LabelsText label_text = LabelRu.extractLabelsTrimText(page_title, line);
        if(null == label_text)
            return null;
        line = label_text.getText();
        
        // 2. extract text till first {{пример|
        String wiki_definition = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, line);

        // 3. parsing wiki-text
        //StringBuffer definition = WikiWord.parseDoubleBrackets(page_title, new StringBuffer(wiki_definition));

        // 4. extract wiki-links (internal links)
        //WikiWord[] ww = WikiWord.getWikiWords(page_title, new StringBuffer(wiki_definition));

        // 5. extract quotations
        WQuote[] quote = WQuoteRu.getQuotes(page_title, line);        

        return new WMeaning(page_title, label_text.getLabels(), wiki_definition, quote, false);
    }

}
