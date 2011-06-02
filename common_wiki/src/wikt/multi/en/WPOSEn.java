/* WPOSEn.java - corresponds to a POS level of English Wiktionary word.
 *
 * Copyright (c) 2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.en;

import wikt.util.POSText;
import wikt.util.LangText;
import wikt.constant.POS;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** Splits text to fragments related to different parts of speech (POS).
 * POS is a level 3 or 4 header in English Wiktionary:
 * <PRE>
 * 1)
 * ==English==
 * ===Etymology===
 * ===Noun===
 * ===Verb===
 *
 * ==Finnish==
 * ===Etymology===
 * ===Noun===       (level 3 in English Wiktionary: ===Noun===)
 *
 * 2)
 * In the case of multiple etymologies, all subordinate headers need to have
 * their levels increased by 1:
 * ===Etymology 1===
 * ====Pronunciation====
 * ====Noun====             POS=noun
 * ===Etymology 2===
 * ====Pronunciation====
 * ====Noun====             POS=noun
 * ====Verb====             POS=verb
 * (level 4 in English Wiktionary: ===Verb===)</PRE>
 *
 * @see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 * @see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained/POS_headers
 */
public class WPOSEn {

    private final static POSText[] NULL_POS_TEXT_ARRAY = new POSText[0];

    private final static List<POSText> NULL_POS_TEXT_LIST = new ArrayList<POSText>(0);

    /** start of the POS block: [\w {}], 
     * \s (space) since there is "===Proper noun==="
     * {} (brackets) since there is "==={{abbreviation}}==="
     */
    private final static Pattern ptrn_3_or_4_level = Pattern.compile(  // Vim: ^==\s*\([^=]\+\)\s*==\s*\Z
            // RE: ^====?\s*([\w {}]+)\s*====?\s*           //"(?m)^\\s*==");
            "(?m)^====?\\s*([-\\w {}]+)\\s*====?\\s*");

    /** Gets true, if str is known header, e.g. "References",
     * but it's not a part of speech name, e.g. "Verb".
     */
    public static boolean isSecondLevelHeaderWordNotPOS (String str)
    {
        if(str.equalsIgnoreCase("Derived terms"))
            return true;
        if(str.equalsIgnoreCase("Related terms"))
            return true;
        if(str.equalsIgnoreCase("Translations"))
            return true;
        if(str.equalsIgnoreCase("References"))
            return true;
        if(str.equalsIgnoreCase("External links"))
            return true;
        
        return false;
    }

    /** Cuts (if it is presented) the header (===POS Name===) and return
     * POSText list with one element.
     * Since it is known that this LangText object 'lt' contains exactly
     * one POS section.
     *
     * @param lt    .text with only one POS section
     */
    private static List<POSText> cutHeaderFromAlonePOSSection (
                                    LangText lt, Matcher m)
    {
        List<POSText> pos_section_alone = new ArrayList<POSText>(1);
        m.reset();
        
        while(m.find()) {

            String pos_header = m.group(1).toLowerCase();

            if(m.groupCount() > 0 && POSTemplateEn.has(pos_header)) {

                pos_section_alone.add( new POSText(
                        POSTemplateEn.get(pos_header),
                        new StringBuffer(          // text after === POS ===
                                lt.text.toString().substring(m.end()))) );

                return pos_section_alone;
            }
        }

        // save all text for unknown POS
        pos_section_alone.add( new POSText(
                        POS.unknown,
                        lt.text) );
        return pos_section_alone;
    }
    /** page_title - word which are described in this article 'text'
     * @param lt    .text will be parsed and splitted,
     *              .lang is not using now, may be in future...
     *              lt corresponds to one Etymology section
     */
    private static List<POSText> splitToPOSSections (
            String   page_title,
            LangText lt)
    {
        if(null == lt.text || 0 == lt.text.length()) {
            return NULL_POS_TEXT_LIST;
        }

        Matcher m = ptrn_3_or_4_level.matcher(lt.text.toString());
        
        int n_pos = countPOSSections(m);
        
        if(n_pos <= 1) // there is only one ===Third of forth level POS header===
            return cutHeaderFromAlonePOSSection(lt,m); // in this language in this etymology for this word                
        // else: there are at least two sections: POS

        // 1. Gets POS and 
        // 2. Splits lt.text into POS sections
        m.reset();
        boolean b_next = m.find();
        assert(b_next);

        List<POSText> pos_sections = new ArrayList<POSText>(n_pos); // there is exactly n_pos POS headers
        
        // position of POS block in the lt.text
        // "<start_old> ===Noun=== <end_old> ... <start_new> ===Verb=== <end_new>"
        // POS block = substring(end_old, start_new)
        //                       end_old = end_new  = m.end()
        //                                start_new = m.start()

        // First POS header
        String  pos_header, pos_header_old = "";
        pos_header = m.group(1).toLowerCase();
        
        while(b_next && !POSTemplateEn.has(pos_header))
        {
            b_next = m.find();
            pos_header = m.group(1).toLowerCase();
        }
        pos_header_old = pos_header;
        assert(POSTemplateEn.has(pos_header));
        assert(b_next);
        int end_old = m.end();
        
    search_POS:
        while(b_next) {
            pos_header = "";
            while(b_next && !POSTemplateEn.has(pos_header))
            {
                b_next = m.find();
                if (!b_next) {
                    POS p = POSTemplateEn.get(pos_header_old);
                    POSText pt = new POSText(p, lt.text.substring(end_old));
                    pos_sections.add(pt);
                    
                    break search_POS;
                }
                pos_header = m.group(1).toLowerCase();
            }

            POS p = POSTemplateEn.get(pos_header_old);
            pos_header_old = pos_header;

            POSText pt = new POSText(p, lt.text.substring(end_old, m.start()));
            pos_sections.add(pt);

            end_old = m.end();
        }
        return pos_sections;
    }

    /** Splits each etymology section into POS sections.
     * Then merge all POS sections into one big array.
     *
     * page_title - word which are described in this article 'text'
     * @param lt .text will be parsed and splitted,
     *           .lang is not using now, may be in future...<br><br>
     *
     * 1) Splits the following text to "Noun" and "Verb"
     * 2) Extracts part of speech "noun" and "verb"
     * <PRE>
     * ===Noun===
     * {{en-noun}}
     * ===Verb===
     * </PRE>
     *
     * Todo: save info about the link Etymology <-> POS.
     */
    public static POSText[] splitToPOSSections (
            String      page_title,
            LangText[] etymology_sections) //LangText    lt)
    {
        if(etymology_sections.length == 0)
            return NULL_POS_TEXT_ARRAY;

        List<POSText> pos_sections = new ArrayList<POSText>();

        for(LangText e : etymology_sections) {
            pos_sections.addAll( splitToPOSSections(page_title, e) );

            //pos_sections.addAll( Arrays.asList( splitToPOSSections(page_title, e) ) );

            //POSText[] pt = splitToPOSSections(page_title, e);
            //if(pt.length > 0)
            //    pos_sections.addAll( Arrays.asList(pt) );
        }

        if(pos_sections.isEmpty())
            return NULL_POS_TEXT_ARRAY;
        
        return (POSText[])pos_sections.toArray(NULL_POS_TEXT_ARRAY);
    }

    /** Counts number of POS sections in this lt->text.
     *
     * @param page_title title of Wiktionary entry
     * @param lt    ->text field may contain POS section(s)
     * @param m regular expression matcher ptrn_3_or_4_level
     */
    private static int countPOSSections (// String page_title, LangText lt,
                                             Matcher m)
    {
        int n_pos = 0;
        
        while(m.find()) {
            String POS_candidate = m.group(1).toLowerCase();
            if(m.groupCount() > 0 && POSTemplateEn.has(POS_candidate))
                n_pos ++;
        }

        return n_pos;
    }

    /** Gets first encountered POS name.
     *
     * @param m regular expression matcher ptrn_3_or_4_level of POS header
     */
    private static POS getFirstPOS (// String page_title, LangText lt,
                                             Matcher m)
    {
        POS p_type = POS.unknown;

        while(m.find()) {

            String pos_header = m.group(1);

            if(m.groupCount() > 0 && POSTemplateEn.has(pos_header))
                return POSTemplateEn.get(pos_header);
        }

        return p_type;
    }

    /** Checks wheather this name is a name of some part of speech.
     */
    /*public static POS guessPOS (//StringBuffer text)
    {
        POS p_type = POS.unknown;

        if(null == text || 0 == text.length()) {
            return new POSText(p_type, text.toString());
        }

        Matcher m = ptrn_morpho_then_2letters.matcher(text.toString());
        boolean b = m.find();
        if(b) {
            String two_letters = m.group(1);

            if(two_letters.equalsIgnoreCase("{{")) {
                // if \1=="{{" then get first letters till space
                // substring started after the symbol "{{"
                String pos_header = StringUtilRegular.getLettersTillSpace(text.substring(m.end()));
                if(POSTypeRu.has(pos_header)) {
                    p_type = POSTypeRu.get(pos_header);
                } else {
                    // old template of POS with hyphen, e.g. "{{adv-ru|}} instead of {{adv ru|}}
                    pos_header = StringUtilRegular.getLettersTillHyphen(text.substring(m.end()));
                    if(POSTypeRu.has(pos_header)) {
                        p_type = POSTypeRu.get(pos_header);
                    }
                }
            } else {
                // else get two_letters + the first Word
                // todo
                // ....
            }
        }

        return new POSText(p_type, text.toString());
    }*/

}
