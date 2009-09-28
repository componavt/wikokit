/* WTranslation.java - corresponds to a Translations level of a word in
 * Russian Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.ru;

import wikipedia.language.LanguageType;
import wikipedia.util.StringUtilRegular;
import wikt.util.POSText;
import wikt.word.WTranslation;
import wikt.word.WTranslationEntry;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** Translations of Russian Wiktionary word.
 *
 * See http://ru.wiktionary.org/wiki/Викисловарь:Правила оформления статей#Перевод
 */
public class WTranslationRu {

    private final static WTranslation[]      NULL_WTRANSLATION_ARRAY      = new WTranslation[0];
    private final static WTranslationEntry[] NULL_WTRANSLATIONENTRY_ARRAY = new WTranslationEntry[0];

    /** Gets position after === Перевод === */
    private final static Pattern ptrn_translation_3th_level = Pattern.compile(
            "===?\\s*Перевод\\s*===?\\s*\\n");

    
    /** Gets a header of translation box template "{{перев-блок|header"
     *                                        or  "{{перев-блок|header|"
     *                                        or  "{{перев-блок|"       - header is absent
     *                                        or  "{{перев-блок||"      - header is absent
     *                                            "|en="
     */
    private final static Pattern ptrn_translation_box_header = Pattern.compile(
            // "\\Q{{перев-блок|\\E(.*?)\\|?\\n\\|");
               "\\Q{{перев-блок\\E\\|?(.*?)\\|?\\n\\|");
            // "\\Q{{перев-блок\\E\\|?(.*?)|?\\n");      // vim      {{перев-блок|\?\(.*\)|\?\n
            
    
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

        // 1. gets position in text after === Перевод ===
        String text_source = text_source_sb.toString();
        Matcher m = ptrn_translation_3th_level.matcher(text_source_sb);
        boolean b_next = m.find();

        if(!b_next) {   // there is no translation section!
            if(lang_section == LanguageType.ru)
                System.out.println("Warning in WTranslationRu.parse(): The Russian word '"+
                        page_title + "' has no section === Перевод ===.");
            return NULL_WTRANSLATION_ARRAY;
        }

        // one more check that there is any translation
        if(!text_source.contains("{{перев-блок|")) {
            System.out.println("Warning in WTranslationRu.parse(): " + "The Russian word '" + page_title +
                    "' has section === Перевод === but there is no any translation box \"{{перев-блок|\".");
            return NULL_WTRANSLATION_ARRAY;
        }

        // x = gets position of the next 2nd or 3rd level block == See also or Bibliography ==
        // gets text till x of the last brackets: "}}"
        String text = StringUtilRegular.getTextTillFirstHeaderPosition(m.end(), text_source);
        
        int len = text.length();
        if(0 == len) {
            return NULL_WTRANSLATION_ARRAY;
        }
        
        List<WTranslation> wt_list = new ArrayList<WTranslation>();

        int prev_end = 0;           // previous end of previous translation box
        boolean to_continue = true;
        while(to_continue) {
            
            // 3. gets next substring "{{перев-блок|"
            int next_end = text.indexOf("{{перев-блок|", prev_end + 1);
            if(-1 == next_end) {
                to_continue = false;

                // gets text till first line "\n}}\n" or "{{unfinished"
                /*int unfinished_template_pos = text.indexOf("{{unfinished", prev_end + 1);
                if(-1 != unfinished_template_pos)
                    next_end = unfinished_template_pos;
                else {*/
                    // search first "\n}}", i.e. end of translation template
                    int e = text.indexOf("\n}}", prev_end + 12);
                    if(-1 != e)
                        next_end = e + 3; // + len("\n}}")
                    else
                        next_end = len;
                //}
            }
            String trans_block = text.substring(prev_end, next_end);

            // 4. extracts lang code "|en=", e.g. wikified translation [[angel]]

            // return WTranslation or null if the translation text block was not found.
            WTranslation wt = WTranslation.parseOneTranslationBox(wikt_lang, page_title, trans_block);
            if(null != wt) {
                wt_list.add(wt);
            }
            if(to_continue)
                to_continue = -1 != next_end && next_end < len;
            prev_end = next_end;
        }

        if(!atLeastOneTranslationExists(wt_list))
            return NULL_WTRANSLATION_ARRAY;

        if(wt_list.size() > 1 && !allTranslationsHaveHeader(wt_list)) {
            System.out.println("Warning in WTranslationRu.parse(): The article '"+
                        page_title + "' has several translation boxes, but not all of them have headers.");
        }
        
        return( (WTranslation[])wt_list.toArray(NULL_WTRANSLATION_ARRAY) );
    }

    /** Checks, wheather all the translation boxes have headers. */
    public static boolean allTranslationsHaveHeader(List<WTranslation> wt_list)
    {
        for(WTranslation wt : wt_list) {
            if (0 == wt.getHeader().length()) {
                return false;
            }
        }
        return true;
    }
    
    /** Returens true, if there is at least one translation entry
     * in any of the translation boxes.
     */
    public static boolean atLeastOneTranslationExists(List<WTranslation> wt_list)
    {
        for(WTranslation wt : wt_list) {
            if (wt.getTranslationsNumber() > 0) {
                return true;
            }
        }
        return false;
    }

    /** Chops close brackets "}}" */
    private final static Pattern ptrn_double_close_curly_brackets = Pattern.compile(
            "\\n?\\Q}}\\E[\\n\\s]*$");                          // gvim: \n\?}}\n\?\Z
            //"\\n?\\Q}}\\E[\\n\\s]*\\z");                          // gvim: \n\?}}\n\?\Z

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
            text_wo_header = text.substring(m.end());    // text without header
        } else
            text_wo_header = text;

        // chop close brackets "}}"
        Matcher m_bracket = ptrn_double_close_curly_brackets.matcher(text_wo_header);
        String t = m_bracket.replaceFirst("");      // text without header and without brackets

        String[] lines = t.split("\n\\|");
        
        List<WTranslationEntry> wte_list = new ArrayList<WTranslationEntry>();
        for(String s : lines) {                                                         // for each language (for each line)
            WTranslationEntry wte = WTranslationEntry.parse(wikt_lang, page_title, s);
            
            if(null != wte) {
                wte_list.add(wte);
            }
        }
        
        return new WTranslation(
                        meaning_summary,
                        (WTranslationEntry[])wte_list.toArray(NULL_WTRANSLATIONENTRY_ARRAY));
    }

}
