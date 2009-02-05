/* WikiText.java - wiki text is a text, where some words are wikified.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.util;

import java.util.regex.Pattern;
//import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** WikiText is a text, where [[some]] [[word]]s [[be|are]] [[wikify|wikified]],
 * e.g. "[[little]] [[bell]]".
 */
public class WikiText {

    /** Visible text, e.g. "bullets m." for "[[bullet]]s {{m}}" */
    private String text;
    
     /** Wiki internal links, e.g. "bullet" and "bullets for "[[bullet]]s" {{m}} */
    private WikiWord[] wiki_words;

    private final static WikiText[] NULL_WIKITEXT_ARRAY = new WikiText[0];

    /** Split by comma and semicolon */
    private final static Pattern ptrn_comma_semicolon = Pattern.compile(
            "[,;]+");

    public WikiText(String _text, WikiWord[] _wiki_words) {
        text        = _text;
        wiki_words  = _wiki_words;
    }

    /** Gets visible text, e.g. "bullets m." for "[[bullet]]s {{m}}" */
    public String getVisibleText() {
        return text;
    }

    // start position of every wiki_words in text
    // todo
    // ...

    /** Gets array of internal links (wiki words, i.e. words with hyperlinks). */
    public WikiWord[] getWikiWords() {
        return wiki_words;
    }

    /** Parses text, creates array of wiki words (words with hyperlinks),
     * e.g. text is "[[little]] [[bell]]", wiki_words[]="little", "bell"
     */
    private static WikiText createOnePhrase(String page_title, String text)
    {
        if(0 == text.trim().length()) {
            return null;
        }
        StringBuffer sb = new StringBuffer(text);
        
        String      s = WikiWord.parseDoubleBrackets(page_title, sb).toString();
        WikiWord[] ww = WikiWord.getWikiWords(page_title, sb);
        return new WikiText(s, ww);
    }

    /** Parses text, creates array of wiki words (words with hyperlinks),
     * e.g. text is "[[little]] [[bell]], [[handbell]], [[doorbell]]".
     * @return empty array if there is no text.
     */
    public static WikiText[] create(String page_title, String text)
    {
        if(0 == text.trim().length()) {
            return NULL_WIKITEXT_ARRAY;
        }
        
        String[] ww = ptrn_comma_semicolon.split(text);   // split by comma and semicolon

        // split should take into account brackets, e.g. "bread (new, old), butter" -> "bread (new, old)", "butter"
        // todo
        // ...
        
        List<WikiText> wt_list = new ArrayList<WikiText>();
        for(String w : ww) {
            WikiText wt = WikiText.createOnePhrase(page_title, w.trim());
            if(null != wt) {
                wt_list.add(wt);
            }
        }
        
        if(0 == wt_list.size()) {
            return NULL_WIKITEXT_ARRAY;
        }
        
        return (WikiText[])wt_list.toArray(NULL_WIKITEXT_ARRAY);
    }
}


    // return true, if wiki text corresponds only to one word, it's important for translation
    // e.g. "[[little]] [[bell]]" == false;
    //      "[[doorbell]]" == true
    //
    // boolean isOneWord (translation)
    // todo
    // ...