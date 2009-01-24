/* WikiWord.java - base class for a word in Wiktionary, e.g. a word from a list 
 *                  of Synonyms or Translations.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.util;

import wikt.constant.ContextLabel;
import wikipedia.util.StringUtil;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** Word in a Wiktionary with wikilink and additional tag or comment, 
 * e.g. a word from a list of Synonyms or Translations, or definition (meaning).
 *
 */
public class WikiWord {
    
    /** Definition word, or synonym, or translation, etc. which is visible to user,
     * e.g. "Scratching" for the wiki text "[[scratch|Scratching]]"
     */
    private String word_visible;

    /** Wikilink, i.e. definition word, or synonym, or translation etc.,
     * which is the link for the current word,
     * e.g. "scratch" for the wiki text "[[scratch|Scratching]]"
     */
    private String word_link;

    private final static StringBuffer   NULL_STRINGBUFFER = new StringBuffer("");
    private final static WikiWord[]     NULL_WIKIWORD_ARRAY = new WikiWord[0];

    private final static Pattern ptrn_double_brackets = Pattern.compile(
                                "\\[\\[(.+?)\\]\\]");

    /** Gets visible word. */
    public String getWordVisible() {
        return word_visible;
    }

    /** Gets wiki link word (lemma). */
    public String getWordLink() {
        return word_link;
    }


    /** Comment for the synonym or translation, e.g. synonyms for "entry":
     * * (''doorway that provides a means of entering a building''): [[entrance]], [[way in]] {{UK}}
     * tags[1]=UK
     * 
     * 
     * e.g. enwikt "slang: money" in synonyms of bread:
     * # (slang: money) dough, folding stuff...), 
     * 
     * 
     * e.g. ruwikt "разг., поэт." in synonyms of Saint-Petersburg: 
     * # [[Питер]] (разг.), [[град Петров]] (поэт.) 
     * 
     * This field will not be used for words of definition (meaning).
     */
    private ContextLabel[] labels;

    /** Initialize and fill WikiWord structure.
     * @param _word_link    internal wiki link, e.g. "run" in [[run]]ning
     * @param _word_visible visible wiki word,  e.g. "running" in [[run]]ning
     * @param _labels
     */
    public WikiWord(String _word_link, String _word_visible, ContextLabel[] _labels) {
        word_visible = _word_visible;
        word_link = _word_link;
        labels = _labels;
    }

    /** Removes and expands wiki links in wiki texts.<PRE>
     * Expands wiki links by removing brackets. There are two cases:
     * (1) remove brackets, e.g. [[run]] -> run and
     * (2) [[run|running]] -> running, or [[Russian language|Russian]] -> Russian,
     * i.e. the visible (to reader) words will remain.
     *
     * It is recommended to call StringUtil.escapeCharDollarAndBackslash(text)
     * before this function.
     * This is simplified vertions of parseDoubleBrackets.</PRE>
     *
     * @param page_title    word which are described in this article 'text'
     * @param text          source wikified definition text
     *
     * See also WikiParser.parseDoubleBrackets.
     */
    public static StringBuffer parseDoubleBrackets (
            String page_title,
            StringBuffer text) //,
            //LanguageType lang)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }

        Matcher m = ptrn_double_brackets.matcher(text.toString()); // [[(.+?)]]
                //StringUtil.escapeCharDollarAndBackslash(text.toString())); // [[(.+?)]]

        String after;   // before, 
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        while(result) {
            // g: text within [[brackets]]
            String g = StringUtil.escapeCharDollarAndBackslash(m.group(1)).toString();
            if(-1 != g.indexOf('|')) {
                after  = StringUtil.getTextAfterFirstVerticalPipe(g);   // before = StringUtil.getTextBeforeFirstVerticalPipe(g);
                //System.out.println("sb="+sb+ "; after="+after);
                m.appendReplacement(sb, after);
            } else {
                // [[run]] -> run
                m.appendReplacement(sb, g);
            }

            result = m.find();  // m.appendReplacement(sb, "$1");
        }
        m.appendTail(sb);
        return sb;
    }
    
    /** Extract wiki links (word_link and word_visible) from wiki texts.
     * There are the visible to reader words (word_link) and internal links (word_visible).
     *
     * <PRE>
     * There are cases:
     * (1) [[run]] => two words [run, run],         remove brackets
     * (2) [[run]]ing => two words [run, runing]    extract, remove brackets
     * (3) [[run|running]] => two words [run, running],
     *     or [[Russian language|Russian]] => [Russian language, Russian].
     * </PRE>
     *
     * @param page_title    word which are described in this article 'text'
     * @param text          source wikified definition text
     */
    public static WikiWord[] getWikiWords (
            String page_title,
            StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_WIKIWORD_ARRAY;
        }

        List<WikiWord> ww_list = new ArrayList<WikiWord>();

        WikiWord w;
        Matcher m = ptrn_double_brackets.matcher(text.toString()); // [[(.+?)]]
                //StringUtil.escapeCharDollarAndBackslash(text.toString())); // [[(.+?)]]

        String before, after;
        boolean result = m.find();
        while(result) {
            // g: text within [[brackets]]
            String g = StringUtil.escapeCharDollarAndBackslash(m.group(1)).toString();
            if(-1 != g.indexOf('|')) {
                before = StringUtil.getTextBeforeFirstVerticalPipe(g);
                after  = StringUtil.getTextAfterFirstVerticalPipe(g);
                //System.out.println("sb="+sb+ "; after="+after);
                w = new WikiWord(before, after, null);
            } else {
                // get text till space or punctuation mark or [ ???????????????????
                String suffix = StringUtil.getTextTillSpaceOrPuctuationMark(m.end(), text.toString());
                w = new WikiWord(g, g.concat(suffix), null);
            }
            result = m.find();
            ww_list.add(w);   
        }
        return( (WikiWord[])ww_list.toArray(NULL_WIKIWORD_ARRAY) );
    }


}
