/* WRelation.java - corresponds to a semantic relations level of a word in
 * Russian Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.ru;

import wikt.constant.Relation;
import wikt.word.WRelation;

import wikipedia.language.LanguageType;
import wikt.util.POSText;

import wikipedia.util.StringUtilRegular;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

//import java.util.List;
//import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;

/** Semantic relations  of Russian Wiktionary word.
 *
 * See http://ru.wiktionary.org/wiki/Викисловарь:Правила оформления статей#Оформление семантических отношений
 */
public class WRelationRu {

    private final static WRelation[] NULL_WRELATION_ARRAY = new WRelation[0];
    //Map<Relation, WRelation[]> m = new HashMap<Relation, WRelation[]>>();

    private final static Map<Relation, WRelation[]> NULL_MAP_RELATION_WRELATION_ARRAY = new HashMap<Relation, WRelation[]>();

    /** Gets position after ==== Синонимы ==== */
    private final static Pattern ptrn_synonymy = Pattern.compile(
            "===?=?\\s*Синонимы\\s*===?=?\\s*\\n");

    /** Gets position after ==== Синонимы ==== */
    private final static Pattern ptrn_line_start = Pattern.compile(
            "^#\\s*");
            
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
        // ===Семантические свойства===
        // ====Синонимы====                         // ==== Level IV. Relation ====

        if(null == pt.getText()) {
            return NULL_MAP_RELATION_WRELATION_ARRAY;
        }
        StringBuffer text_source_sb = pt.getText();
        if(0 == text_source_sb.length()) {
            return NULL_MAP_RELATION_WRELATION_ARRAY;
        }
        
        Map<Relation, WRelation[]> m_rel = new HashMap<Relation, WRelation[]>();
        String text = text_source_sb.toString();

        // synonymy
        WRelation[] r_syn = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_synonymy, Relation.synonymy);
        if(0 < r_syn.length) {
            m_rel.put(Relation.synonymy, r_syn);
        }
        
        // antonymy
        // todo
        // ...


        return m_rel;
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
        // ===Семантические свойства===
        // ====Синонимы====                         // ==== Level IV. Relation ====

        // 1. gets position in text after e.g. ====Синонимы====
        Matcher m = relation_header_pattern.matcher(text);
        boolean b_next = m.find();

        if(!b_next) {   // the section is absent!
            //System.out.println("Warning in WRelationRu.parse(): The article '"+
            //            page_title + "' has no section ====Синонимы====.");
            return NULL_WRELATION_ARRAY;
        }

        // 1. get text till (1) next header or (2) empty line
        String relation_text = StringUtilRegular.getTextTillFirstHeaderOrEmptyLine(m.end(), text);
        if(0 == relation_text.length()) {
            return NULL_WRELATION_ARRAY;
        }

        List<WRelation> wr_list = new ArrayList<WRelation>();
        
        // 2. split into lines: "\n" (not "\n#")
        // parse lines till the line which is not started from #
        String[] lines = relation_text.split("\n");
        for(String s : lines) {
            Matcher m_start = ptrn_line_start.matcher(s);
            if(m_start.find()) {
                s = m_start.replaceFirst("");

                // 3. split list of synonyms into wikiwords (or wiki phrases?)
                WRelation wr = parseOneLine (page_title, s);

                if(null != wr) {
                    wr_list.add(wr);
                }

            } else break;   // this line starts not from "#". Stop.
        }
        
        // ====Гипонимы====
        //# [[бубенчик]]
        //# -
        //# [[колокольчик средний]]
        
        return (WRelation[])wr_list.toArray(NULL_WRELATION_ARRAY);
    }
    
    /** Parses one line of a semantic relations,
     * extracts a list of words (wikified words), creates and fills WRelation.
     *
     * @param page_title    word which are described in this article 'text'
     * @param text          semantic relation text line (e.g. list of synonyms)
     * @return WRelation or null if the list of semantic relations is empty or equal "-".
     */
    public static WRelation parseOneLine(
                    String page_title,
                    String text)
    {
        // 1. check emptyness: regular expression "-"
        // ..

        // 2. split by semicolon and comma
        // ..

        return null;
    }
}
