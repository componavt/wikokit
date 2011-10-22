/* WRelation.java - corresponds to a semantic relations level of a word in
 * English Wiktionary.
 *
 * Copyright (c) 2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.en;

import wikt.constant.Relation;
import wikt.word.WRelation;

import wikipedia.language.LanguageType;
import wikt.util.POSText;
import wikt.util.WikiText;

import wikipedia.util.StringUtilRegular;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ListIterator;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;

/** Semantic relations of English Wiktionary word.
 *
 * @see http://en.wiktionary.org/wiki/Wiktionary:Semantic_relations
 * @see http://en.wiktionary.org/wiki/Template_talk:sense
 */
public class WRelationEn {

    private final static WRelation[] NULL_WRELATION_ARRAY = new WRelation[0];

    private final static Map<Relation, WRelation[]> NULL_MAP_RELATION_WRELATION_ARRAY = new HashMap<Relation, WRelation[]>();

    /** Gets position after       ====Synonyms==== */
    private final static Pattern ptrn_synonymy = Pattern.compile(
                      "(?m)^={3,5}\\s*Synonyms\\s*={3,5}\\s*$");

    /** Gets position after       ====Antonyms==== */
    private final static Pattern ptrn_antonymy = Pattern.compile(
                      "(?m)^={3,5}\\s*Antonyms\\s*={3,5}\\s*$");

    /** Gets position after       ====Hypernyms==== */
    private final static Pattern ptrn_hypernymy = Pattern.compile(
                      "(?m)^={3,5}\\s*Hypernyms\\s*={3,5}\\s*$");

    /** Gets position after       ====Hyponyms==== */
    private final static Pattern ptrn_hyponymy = Pattern.compile(
                      "(?m)^={3,5}\\s*Hyponyms\\s*={3,5}\\s*$");
    
    /** Gets position after       ====Holonyms==== */
    private final static Pattern ptrn_holonymy = Pattern.compile(
                      "(?m)^={3,5}\\s*Holonyms\\s*={3,5}\\s*$");

    /** Gets position after       ====Meronyms==== */
    private final static Pattern ptrn_meronymy = Pattern.compile(
                      "(?m)^={3,5}\\s*Meronyms\\s*={3,5}\\s*$");

    /** Gets position after       ====Troponyms==== */
    private final static Pattern ptrn_troponymy = Pattern.compile(
                      "(?m)^={3,5}\\s*Troponyms\\s*={3,5}\\s*$");

    /** Gets position after       ====Coordinate terms==== */
    private final static Pattern ptrn_coordinate_term = Pattern.compile(
                      "(?m)^={3,5}\\s*Coordinate\\s+terms\\s*={3,5}\\s*$");
    
    /** Gets position after       ====See also==== */
    private final static Pattern ptrn_see_also = Pattern.compile(
                      "(?m)^={3,5}\\s*See\\s+also\\s*={3,5}\\s*$");

    /** Gets position after       ====Derived terms==== */
    private final static Pattern ptrn_derived_terms = Pattern.compile(
                      "(?m)^={3,5}\\s*Derived\\s+terms\\s*={3,5}\\s*$");

    /** Gets position after       ====Related terms==== */
    private final static Pattern ptrn_related_terms = Pattern.compile(
                      "(?m)^={3,5}\\s*Related\\s+terms\\s*={3,5}\\s*$");
    
    /** Gets position after       ====Translations==== */
    private final static Pattern ptrn_translations = Pattern.compile(
                      "(?m)^={3,5}\\s*Translations\\s*={3,5}\\s*$");

    /** The begin of any list of semantic relations: "* " */
    private final static Pattern ptrn_line_start = Pattern.compile(
            "^\\*\\s*");

    /** The summary of definition before the list of synonyms, e.g. "furp" from
     * "(''furp''): [[furp]], [[whoodleplunk]]" */
    private final static Pattern ptrn_summary_in_italics = Pattern.compile(
            "^\\(''(.+?)''\\):\\s*");

    /** The summary of definition before the list of synonyms in template:sense,
     * e.g. "forked, branched" from:
     * "{{sense|forked, branched}} [[cloven]], [[forked]]" */
    private final static Pattern ptrn_summary_in_sense = Pattern.compile(
            "^\\Q{{sense|\\E(.+?)\\}\\}\\s*");

    /** Chops the dot symbol (".") at the end of line (EOL). */
    private final static Pattern ptrn_eol_dot = Pattern.compile(
                            "(?m)\\s*\\.\\s*$");

    /** Parses text (related to the POS), creates and fill array of
     * semantic relations (WRelation).
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param pt            POSText defines POS stored in pt.text
     * @param relation_type type of parsing relation, e.g. synonymy
     * @return
     */
    public static Map<Relation, WRelation[]> parse (
                    LanguageType wikt_lang,
                    String page_title,
                    POSText pt)
    {
        // ====Synonyms====             // ==== Level IV (or V?). Relation ====

        if(null == pt.getText()) {
            return NULL_MAP_RELATION_WRELATION_ARRAY;
        }
        StringBuffer text_source_sb = pt.getText();
        if(0 == text_source_sb.length()) {
            return NULL_MAP_RELATION_WRELATION_ARRAY;
        }

        // gets text till ====Translations====
        String text = null;
        Matcher m = ptrn_translations.matcher(text_source_sb.toString());
        if(!m.find())              // the section Translations is absent!
            text = text_source_sb.toString();
        else
            text = text_source_sb.substring(0, m.start());

        Map<Relation, WRelation[]> m_rel = new HashMap<Relation, WRelation[]>();        
        WRelation[] r;
        
        // synonymy
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_synonymy, Relation.synonymy);
        if(0 < r.length) m_rel.put(Relation.synonymy, r);

        // antonymy
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_antonymy, Relation.antonymy);
        if(0 < r.length) m_rel.put(Relation.antonymy, r);

        // hypernymy
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_hypernymy, Relation.hypernymy);
        if(0 < r.length) m_rel.put(Relation.hypernymy, r);

        // hyponymy
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_hyponymy, Relation.hyponymy);
        if(0 < r.length) m_rel.put(Relation.hyponymy, r);

        // holonymy
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_holonymy, Relation.holonymy);
        if(0 < r.length) m_rel.put(Relation.holonymy, r);

        // meronymy
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_meronymy, Relation.meronymy);
        if(0 < r.length) m_rel.put(Relation.meronymy, r);

        // troponymy
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_troponymy, Relation.meronymy);
        if(0 < r.length) m_rel.put(Relation.troponymy, r);
        
        // coordinate_term
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_coordinate_term, Relation.meronymy);
        if(0 < r.length) m_rel.put(Relation.coordinate_term, r);
        
        // gets text till min(====Derived terms====, ====Related terms====),
        //      since "See also" can be used not only for semanticrelations,
        //      but also for etymologically related words
        text = WRelationEn.substrTillFirstMatch(text, ptrn_derived_terms,
                                                      ptrn_related_terms);

        /*boolean b_found = false;
        int pos1, pos2;
        pos1 = pos2 = text.length();

        m = ptrn_derived_terms.matcher(text);
        if(m.find()) {
            pos1 = m.start();
            b_found = true;
        }

        m = ptrn_related_terms.matcher(text);
        if(m.find()) {
            pos2 = m.start();
            b_found = true;
        }

        if(b_found)
            text = text.substring(0, Math.min(pos1, pos2));
*/
        // otherwise_related (see also)
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_see_also, Relation.meronymy);
        if(0 < r.length) m_rel.put(Relation.otherwise_related, r);

        return m_rel;
    }

    /** Gets text till the first match defined by patterns ptrn1 and ptr2.
     * If match fails then return the source text string.
     */
    public static String substrTillFirstMatch (
                    String text, Pattern  ptrn1, Pattern  ptrn2)
    {
        boolean b_found = false;
        int pos1, pos2;
        pos1 = pos2 = text.length();

        Matcher m = ptrn1.matcher(text);
        if(m.find()) {
            pos1 = m.start();
            b_found = true;
        }

        m = ptrn2.matcher(text);
        if(m.find()) {
            pos2 = m.start();
            b_found = true;
        }

        if(b_found)
            return text.substring(0, Math.min(pos1, pos2));

        return text;
    }
    
    /** Parses text (related to the POS), creates and fill array of
     * semantic relations only for one kind of semantic relations (e.g. synonyms)
     * defined by the variable 'relation'.
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param text          text of wiki article related to one POS
     * @param relation_header_pattern regular expression to find the header of semantic relation
     * @param relation type of parsing relation, e.g. synonymy
     * @return an empty array if relations are absent
     */
    public static WRelation[] parseOneKindOfRelation (
                    LanguageType wikt_lang,
                    String page_title,
                    String text,
                    Pattern  relation_header_pattern,
                    Relation relation)
    {
        // e.g.: 
        // ====Synonyms====                         // ==== Level IV. Relation ====
        
        // 1. gets position in text after e.g. ====Synonyms====
        Matcher m = relation_header_pattern.matcher(text);
        boolean b_next = m.find();

        if(!b_next) {   // the section is absent!
            //System.out.println("Warning in WRelationRu.parse(): The article '"+
            //            page_title + "' has no section ====Synonyms====.");
            return NULL_WRELATION_ARRAY;
        }

        // 1. get text till (1) next header or (2) empty line
        String relation_text = StringUtilRegular.getTextTillFirstHeaderOrEmptyLine(m.end()+1, text);
        if(0 == relation_text.length()) {                                   // skip \n => +1
            return NULL_WRELATION_ARRAY;
        }

        List<WRelation> wr_list = new ArrayList<WRelation>();

        // 2. split into lines: "\n" (not "\n*")
        // parse lines till the line which is not started from * (?to recheck?)
        String[] lines = relation_text.split("\n");
        boolean b_relations = false;
        for(String s : lines) {
            if(s.length() == 0)
                continue;
            Matcher m_start = ptrn_line_start.matcher(s);
            if(m_start.find()) {
                WRelation wr = null;
                s = m_start.replaceFirst("");   // remove "# "

                if(s.length() > 0)
                    wr = parseOneLine (page_title, s); // 3. split list of synonyms into wikiwords (or wiki phrases?)

                //if(null != wr)
                wr_list.add(wr);    // null means that relation = "# -", i.e. absent for this meaning

                if(null != wr) b_relations = true;

            } else {
                if(!b_relations) // it's one of first lines, before any relations
                    continue;
                else
                    break;      // this line starts not from "#". Stop.
            }
        }

        if(!b_relations) {  // only empty lists of relations
            return NULL_WRELATION_ARRAY;
        }

        wr_list = chompNullElementsEndOfList(wr_list);

        return (WRelation[])wr_list.toArray(NULL_WRELATION_ARRAY);
    }

    /** Chomps NULL elements at the end of list (wr_list), if there is any.
     */
    private static List<WRelation> chompNullElementsEndOfList(List<WRelation> wr_list) {

        ListIterator i = wr_list.listIterator(wr_list.size());
        if(i.hasPrevious()) {
            do {
                Object wr = i.previous();

                if(null == wr)
                    i.remove();
                else
                    break;

            } while(i.hasPrevious());
        }
        
        return wr_list;
    }

    /** Structure for storing summary of meaning with list of (syn)onyms. */
    private static class SummaryAndText {
        String meaning_summary;
        String onym_list; // e.g. synonym list
    }

    /** Parses one line of a semantic relations,
     * extracts a meaning summary and list of (syn)onyms (wikified words),
     * creates and fills SummaryAndText.
     * 
     * @param text          semantic relation text line (e.g. list of synonyms)
     * @return structure or null if the meaning summary is absent.
     */
    private static SummaryAndText splitToSummaryAndOnymList (String text) {

        // extract meaning_summary, i.e. "flrink with cumplus" from
        // variant 1 (without "* ")
        // * (''flrink with cumplus''): [[flrink]], [[pigglehick]]
        // variant 2
        // * {{sense|An oath or affirmation}} [[promise]], [[vow]], {{qualifier|informal}} [[word]]
        // * {{sense|forked, branched}} [[cloven]], [[forked]]

        String meaning_summary = "", onym_list = "";
        
        // 1
        Matcher m = ptrn_summary_in_sense.matcher(text);
        if(m.find()) {
            meaning_summary = m.group(1);
            onym_list = text.substring(m.end());
        } else {
            // 2
            m = ptrn_summary_in_italics.matcher(text);
            if(m.find()) {
                meaning_summary = m.group(1);
                onym_list = text.substring(m.end());
            }
        }

        if(onym_list.length() == 0)
            return null;

        SummaryAndText st = new WRelationEn.SummaryAndText();
        st.meaning_summary = meaning_summary;
        st.onym_list = onym_list;

        return st;
    }
    
    /** Replace template:l by usual [[wiki link]].
     *
     * @param onym_list list of synonyms as a text string
     * @return the same text but without template:l.
     *
     * @see http://en.wiktionary.org/wiki/Template:l
     */
    private static String replaceTemplateL(
                    String onym_list)
    {
        if(onym_list.length() == 0)
            return onym_list;
        
        int start, end, prev_end, pipe2, pipe3;
        StringBuilder s = new StringBuilder();
        
        start = onym_list.indexOf("{{l|");
        end = 0;
        while( -1 != start)
        {
            //              "|something" - optional
            // {{l|de|synonym|something}} -> [[synonym]]
            // |                       |
            // start                   end
            //       pipe2   pipe3       prev_end + 2
            
            prev_end = end;
            end = onym_list.indexOf("}}", start);
            
            pipe2 = onym_list.indexOf("|", start + 4);
            pipe3 = onym_list.indexOf("|", pipe2 + 1);
            if(-1 == pipe3 || pipe3 > end) // {{l|de|synonym}} - simple case
                pipe3 = end;
            
            if( prev_end + 2 < start ) // it is false in the first time, i.e. if there is only one synonym, not a list
                s.append( onym_list.substring(prev_end + 2, start) );
            s.append("[[");
            if(pipe2 + 1 < 0 || pipe3 > onym_list.length() || pipe2 + 1 > pipe3) {
                System.out.println("\n\nError in WRelationEn.replaceTemplateL(): onym_list=" + onym_list);
                return onym_list;
            } else {
                s.append( onym_list.substring(pipe2 + 1, pipe3) );
            }
            s.append("]]");
            
            start = onym_list.indexOf("{{l|", end);
        }
        
        return s.toString();
    }

    /** Parses one line of a semantic relations,
     * extracts a list of words (wikified words), creates and fills WRelation.
     *
     * @param page_title    word which are described in this article 'text'
     * @param text          semantic relation text line (e.g. list of synonyms)
     * @return WRelation or null if the list of semantic relations is empty or equal "-".
     *
     * The link to Wikisaurus is ommited now... to parse in future.
     */
    private static WRelation parseOneLine(
                    String page_title,
                    String text)
    {
        // 1. check emptyness
        if(0 == text.length()) return null;

        if(text.contains("[[Wikisaurus:"))
            return null;

        // 2. extract meaning_summary, i.e. "flrink with cumplus" from
        // * (''flrink with cumplus''): [[flrink]], [[pigglehick]]
        SummaryAndText st = splitToSummaryAndOnymList(text);

        String meaning_summary = null;
        String onym_list = "";

        if(null != st) {
            meaning_summary = st.meaning_summary;
            onym_list = st.onym_list;
        } else {
            onym_list = text;
        }
        
        // 3. chops the dot symbol (".") at the end of line (onym_list)
        Matcher m = ptrn_eol_dot.matcher(onym_list);
        if(m.find())
            onym_list = m.replaceFirst("");

        // {{l|de|synonym|something}} -> [[synonym]]
        if(onym_list.contains("{{l|"))
            onym_list = replaceTemplateL(onym_list);
        
        // 4. split by semicolon and comma
        WikiText[] wt = WikiText.create(page_title, onym_list);
        if(0 == wt.length) return null;
        
        return new WRelation(meaning_summary, wt);
    }
    

}
