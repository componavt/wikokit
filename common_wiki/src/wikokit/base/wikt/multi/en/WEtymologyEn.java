/* WEtymologyEn.java - corresponds to an etymology level of English Wiktionary word.
 * 
 * Copyright (c) 2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.multi.en;

import wikokit.base.wikt.util.LangText;
import wikokit.base.wikipedia.language.LanguageType;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** Etymology part of English Wiktionary article.
 *
 * Etymology is a level 3 header in English Wiktionary:
 * 1)<PRE>
 * ===Noun===
 * ===Etymology=== (level 3 in English Wiktionary)
 * ===Noun===
 * ===Verb===
 *
 * ==Finnish==
 * ===Etymology===
 * ===Noun===</PRE>
 *
 * 2)<PRE>
 * Also level 3 in the case of multiple etymologies:
 * ===Etymology 1===        (level 3)
 * ====Pronunciation====
 * ====Noun====
 * ===Etymology 2===        (level 3)
 * ====Pronunciation====
 * ====Noun====
 * ====Verb====</PRE>
 *
 * See http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 */
public class WEtymologyEn {

    /** ===Etymology=== or ===Etymology 1===
     */
    private final static Pattern ptrn_3d_level_etymology = Pattern.compile(
            "(?mi)^===\\s*Etymology\\s*\\d{0,4}\\s*===\\s*"); 
            // (?mi)^===\s*Etymology\s*\d{0,4}\s*===\s*     - Regular Expression

    private final static LangText[] NULL_LANG_TEXT_ARRAY = new LangText[0];


    /** Splits text to fragments related to different etymologies.
     *
     * page_title - word which are described in this article 'text'
     * @param lt .text will be parsed and splitted,
     *           .lang is not using now, may be in future...
     *
     * 1) Checks whether exists more than one section ===Etymology===
     * 2) If there is only one or zero sections then return lt_source
     *    If there more than one sections then split it.
     */
    public static LangText[] splitToEtymologySections (
            String      page_title,
            LangText    lt_source)
    {
        if(null == lt_source.text || 0 == lt_source.text.length()) {
            return NULL_LANG_TEXT_ARRAY;
        }
        
        Matcher m = ptrn_3d_level_etymology.matcher(lt_source.text.toString());
        boolean b_next = m.find();

                        // Position of Etymology block in the lt_source.text:
        int start, end; // "<start> == Etymology 1 == ... <end> == Etymology 2 =="
        int start1, end1;
        start1 = end1 = 0;

        if(b_next) {
            start1 = m.start();
            end1   = m.end();
        }

        b_next = b_next && m.find();

        if(!b_next) {    // almost === !m.find() || !m.find()) {
            LangText[] lt_result = new LangText[1];
            lt_result[0] = lt_source;
            return lt_result;
        }
                                                        // there are more than one Etymology in this language in this word
        List<LangText> etymology_sections = new ArrayList<LangText>();  // result will be stored to
        
        boolean bfirst = true;

        start = m.start();
        end = m.end();
        LanguageType lang = lt_source.getLanguage();
        while(b_next) {

            LangText lt = new LangText(lang);
            if(bfirst) {
                bfirst = false;
                lt.text.append(lt_source.text.substring(0, start1));
                lt.text.append(lt_source.text.substring(end1, start));
            } else
                lt.text.append(lt_source.text.substring(start, end));
            etymology_sections.add(lt);
            
            b_next = m.find();
            if(b_next) {
                start = end;
                end = m.start();
            }
        }

        LangText lt = new LangText(lang);
        lt.text.append(lt_source.text.substring(end));
        etymology_sections.add(lt); // last Etymology section

        return (LangText[])etymology_sections.toArray(NULL_LANG_TEXT_ARRAY);
    }
}
