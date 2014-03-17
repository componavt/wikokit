/* WRelation.java - corresponds to a semantic relations level of a word in
 * Russian Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.multi.ru;

import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.word.WRelation;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.util.WikiText;

import wikokit.base.wikipedia.util.StringUtilRegular;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;
import wikokit.base.wikt.multi.ru.name.LabelRu;
import wikokit.base.wikt.util.LabelsWikiText;

/** Semantic relations of Russian Wiktionary word.
 *
 * @see http://ru.wiktionary.org/wiki/Викисловарь:Правила оформления статей#Оформление семантических отношений
 */
public class WRelationRu {

    private final static WRelation[] NULL_WRELATION_ARRAY = new WRelation[0];
    //Map<Relation, WRelation[]> m = new HashMap<Relation, WRelation[]>>();
    
    // private final static Label[] NULL_LABEL_ARRAY = new Label[0];
    // private final static List<Label> NULL_LABEL_LIST = new ArrayList(0);

    private final static Map<Relation, WRelation[]> NULL_MAP_RELATION_WRELATION_ARRAY = new HashMap<Relation, WRelation[]>();

    /** Gets position after ==== Синонимы ==== */
    private final static Pattern ptrn_synonymy  = Pattern.compile("===?=?\\s*Синонимы\\s*===?=?\\s*\\n");
    private final static Pattern ptrn_antonymy  = Pattern.compile("===?=?\\s*Антонимы\\s*===?=?\\s*\\n");
    private final static Pattern ptrn_hypernymy = Pattern.compile("===?=?\\s*Гиперонимы\\s*===?=?\\s*\\n");
    private final static Pattern ptrn_hyponymy  = Pattern.compile("===?=?\\s*Гипонимы\\s*===?=?\\s*\\n");
    private final static Pattern ptrn_coordinate_term = Pattern.compile("===?=?\\s*Согипонимы\\s*===?=?\\s*\\n");
    private final static Pattern ptrn_holonymy  = Pattern.compile("===?=?\\s*Холонимы\\s*===?=?\\s*\\n");
    private final static Pattern ptrn_meronymy  = Pattern.compile("===?=?\\s*Меронимы\\s*===?=?\\s*\\n");
    
    /** Two main patterns for synonyms with labels */
    // private final static Pattern ptrn_labels  = Pattern.compile("(?<label>[^:]+):(?<word>.+)");
    private final static Pattern ptrn_labels  = Pattern.compile("^(?<label>[^:(]+):(?<word>.+)");
    
    private final static Pattern ptrn_labels_brackets  = Pattern.compile("(?<word>\\[\\[[^\\]]+\\]\\])([ ]?(\\((?<label>[^\\)]+)\\))?)");
    /** Split by comma */
    private final static Pattern ptrn_comma = Pattern.compile("[,]+");

    /** The begin of any list of semantic relations: "# " */
    private final static Pattern ptrn_line_start = Pattern.compile(
            "^#\\s*");

    /** The empty line can contain a dash and spaces. */
    private final static Pattern ptrn_dashes = Pattern.compile(
            "^[-‐‒–—―]?\\s*$");
            
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
        
        // coordinate term
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_coordinate_term, Relation.coordinate_term);
        if(0 < r.length) m_rel.put(Relation.coordinate_term, r);
                
        // holonymy
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_holonymy, Relation.holonymy);
        if(0 < r.length) m_rel.put(Relation.holonymy, r);

        // meronymy
        r = parseOneKindOfRelation (wikt_lang, page_title, text, ptrn_meronymy, Relation.meronymy);
        if(0 < r.length) m_rel.put(Relation.meronymy, r);
        
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
        boolean b_relations = false;
        for(String s : lines) {
            Matcher m_start = ptrn_line_start.matcher(s);
            if(m_start.find()) {
                WRelation wr = null;
                s = m_start.replaceFirst("");   // remove "# "

                if(s.length() > 0) {
                    // 3. split list of synonyms into wikiwords (or wiki phrases?)
                    wr = parseOneLine (page_title, s);
                }

                //if(null != wr)
                wr_list.add(wr);    // null means that relation = "# -", i.e. absent for this meaning
                
                if(null != wr) b_relations = true;

            } else break;   // this line starts not from "#". Stop.
        }
        
        if(!b_relations) {  // only empty lists of relations
            return NULL_WRELATION_ARRAY;
        }
        
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
        if(0 == text.length()) return null;

        Matcher m = ptrn_dashes.matcher(text);
        if(m.find()) return null;

        if(text.equals("&#160;"))
            return null;

        if(text.equals("{{-}}"))
            return null;

        // 2. split by semicolon
        WikiText[] wt = WikiText.createSplitBySemicolon(page_title, text);
        if(0 == wt.length) return null;
        
        // 3. get text and labels
        List<LabelsWikiText> lwt_array = new ArrayList(0);

        for(WikiText _wiki_text : wt) {
            String _text = _wiki_text.getWikifiedText();
            //check if first pattern "works"
            Matcher demo_match = ptrn_labels.matcher(_text);
            Matcher main_matcher;
            //check, what variant of regexp fits
            if(demo_match.find())   // labels before word: word1, word2;
                main_matcher = ptrn_labels.matcher(_wiki_text.getWikifiedText());
            else                    // word (label after word, label2, label3);
                main_matcher = ptrn_labels_brackets.matcher(_text);
                
            //use chosen regexp
            while (main_matcher.find()) {
                String _words = main_matcher.group("word");
                String _labels = main_matcher.group("label");
                WikiText[] wt1 = WikiText.createSplitByComma(page_title, _words);
                for(WikiText _wiki_word : wt1) {
                    lwt_array.add(new LabelsWikiText(LabelRu.createSplitByPattern(_labels, ptrn_comma), _wiki_word));
                }
            }                        
        }
        
        return new WRelation(null, lwt_array.toArray(new LabelsWikiText[lwt_array.size()]));
    }
}
