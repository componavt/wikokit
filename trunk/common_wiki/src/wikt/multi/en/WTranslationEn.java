/* WTranslationEn.java - corresponds to a Translations level of a word in
 * English Wiktionary.
 *
 * Copyright (c) 2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.en;

import wikipedia.language.LanguageType;

import wikt.util.POSText;
import wikipedia.util.StringUtilRegular;

import wikt.word.WTranslation;
import wikt.word.WTranslationEntry;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** Translations of English Wiktionary word.
 *
 * @see http://en.wiktionary.org/wiki/Wiktionary:Translations
 */
public class WTranslationEn {

    private final static WTranslation[]      NULL_WTRANSLATION_ARRAY      = new WTranslation[0];
    private final static WTranslationEntry[] NULL_WTRANSLATIONENTRY_ARRAY = new WTranslationEntry[0];

    /** Gets position after ====Translations==== */
    private final static Pattern ptrn_translation_level = Pattern.compile(
            "(?m)^={3,5}\\s*Translations\\s*={3,5}\\s*$");

    /** Gets a header of translation box template "{{trans-top|header}}"
     *                                        or  "{{trans-top|header|}}"
     *                                        or  "{{trans-top}}"   - header is absent
     *                                        or  "{{trans-top||}}" - header is absent
     *                                            "* French: {{t|fr|orange|f}}"
     */
    private final static Pattern ptrn_translation_box_header = Pattern.compile(
               "\\Q{{trans-top\\E\\|?(.*?)\\|?\\}\\}");
            // RE: \Q{{trans-top\E\|?(.*?)\|?\}\}

    /** Parses text (related to the POS), creates and fill array of translations (WTranslation).
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param pt            POSText defines POS stored in pt.text
     * @return
     */
    public static WTranslation[] parse (
                    LanguageType wikt_lang,
                    LanguageType lang_section,
                    String page_title,
                    POSText pt)
    {
        // === Level III. Translation ===
        if(null == pt.getText()) {
            return NULL_WTRANSLATION_ARRAY;
        }
        StringBuffer text_source_sb = pt.getText();
        if(0 == text_source_sb.length()) {
            return NULL_WTRANSLATION_ARRAY;
        }

        // 1. gets position in text after ====Translations====
        String text_source = text_source_sb.toString();
        Matcher m = ptrn_translation_level.matcher(text_source_sb);
        boolean b_next = m.find();

        if(!b_next) {   // there is no translation section!
            if(lang_section == LanguageType.en)
                System.out.println("Warning in WTranslationRu.parse(): The English word '"+
                        page_title + "' has no section === Перевод ===.");
            return NULL_WTRANSLATION_ARRAY;
        }

        // one more check that there is any translation
        if(!text_source.contains("{{trans-top|")) {
            System.out.println("Warning in WTranslationEn.parse(): The English word '" + page_title +
                    "' has section ====Translation==== but there is no any translation box \"{{перев-блок\".");
            return NULL_WTRANSLATION_ARRAY;
        }

        // x = gets position of the next 2nd - 5th level block == See also or Bibliography ==
        // gets text till the last: "{{trans-bottom}}"
        String text = StringUtilRegular.getTextTillFirstHeaderPosition(m.end(), text_source);

        int len = text.length();
        if(0 == len)
            return NULL_WTRANSLATION_ARRAY;

        List<WTranslation> wt_list = new ArrayList<WTranslation>();

        int prev_end = 1; // previous end of previous translation box + len("\n")=1
        boolean to_continue = true;
        while(to_continue) {

            // 3. gets next substring "{{trans-top|"
            int next_end = text.indexOf("{{trans-top|", prev_end + 1);
            if(-1 == next_end) {
                to_continue = false;
                next_end = len;
            }
            String trans_block = text.substring(prev_end, next_end);

            // 4. extracts lang code "|en=", e.g. wikified translation [[angel]]

            // return WTranslation or null if the translation text block was not found.
            WTranslation wt = WTranslation.parseOneTranslationBox(wikt_lang, page_title, trans_block);
            if(null != wt)
                wt_list.add(wt);
            
            if(to_continue)
                to_continue = -1 != next_end && next_end < len;
            prev_end = next_end;
        }
/*
        if(!atLeastOneTranslationExists(wt_list))
            return NULL_WTRANSLATION_ARRAY;

        if(wt_list.size() > 1 && !allTranslationsHaveHeader(wt_list)) {
            System.out.println("Warning in WTranslationRu.parse(): The article '"+
                        page_title + "' has several translation boxes, but not all of them have headers.");
        }
*/
        return( (WTranslation[])wt_list.toArray(NULL_WTRANSLATION_ARRAY) );
    }


    /** Parses one translation box, i.e. extracts languages and a list of
     * translations (wikified words) for each language,
     * creates and fills WTranslation.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param text          translaton box text
     * @return WTranslation or null if the translation text block was not found.
     */
    public static WTranslation parseOneTranslationBox(LanguageType wikt_lang,
                    String page_title,
                    String text)
    {
        String meaning_summary = "";
        String text_wo_header;

        // 1. extract header (meaning summary, first line in translation box)
        Matcher m = ptrn_translation_box_header.matcher(text.toString());
        boolean b_found = m.find();
        // System.out.println("WTranslationRu.parseOneTranslationBox(): The article '"+page_title + "'.");

        if(b_found) {   // there is a header
            meaning_summary = m.group(1);
            if(text.length() <= m.end() + 1)
                return null;                // header without text
            text_wo_header = text.substring(m.end() + 1);   // text without header
        } else
            text_wo_header = text;

        String[] lines = text_wo_header.split("\n");

        List<WTranslationEntry> wte_list = null;
        for(String s : lines) {
            
            s = s.trim();
            if(s.equalsIgnoreCase("{{trans-mid}}")) continue;
            if(s.equalsIgnoreCase("{{trans-bottom}}")) break;
            
            // for each language (for each line)
            WTranslationEntry wte = WTranslationEntry.parse(wikt_lang, page_title, s);

            if(null != wte) {
                if(null == wte_list)
                    wte_list = new ArrayList<WTranslationEntry>();
                wte_list.add(wte);
            }
        }

        if(wte_list.size() == 0)
            return null;

        return new WTranslation(
                        meaning_summary,
                        (WTranslationEntry[])wte_list.toArray(NULL_WTRANSLATIONENTRY_ARRAY));
    }

}
