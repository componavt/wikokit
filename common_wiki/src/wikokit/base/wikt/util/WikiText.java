/* WikiText.java - wiki text is a text, where some words are wikified.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.util;

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
    
    /** Source wikified text, e.g. "[[bullet]]s {{m}}". It is NULL if "text" hasn't any wikification. */
    private String wikified_text;
    
     /** Wiki internal links, e.g. "bullet" and "bullets for "[[bullet]]s" {{m}} */
    private WikiWord[] wiki_words;

    private final static WikiText[] NULL_WIKITEXT_ARRAY = new WikiText[0];

    /** Split by comma and semicolon */
    private final static Pattern ptrn_comma_semicolon = Pattern.compile(
            "[,;]+");
    
    /** Split by semicolon */
    private final static Pattern ptrn_semicolon = Pattern.compile(
            "[;]+");

    public WikiText(String _text, String _wikified_text, WikiWord[] _wiki_words) {
        text            = _text;
        wikified_text   = _wikified_text;
        wiki_words  = _wiki_words;
    }

    /** Gets visible text, e.g. "bullets m." for "[[bullet]]s {{m}}" */
    public String getVisibleText() {
        return text;
    }

    /** Source wikified text. */
    public String getWikifiedText() {
   	return wikified_text;
    }

    /** Gets array of internal links (wiki words, i.e. words with hyperlinks). */
    public WikiWord[] getWikiWords() {
        return wiki_words;
    }

    /** Frees memory recursively. */
    public void free ()
    {
        if(null != wiki_words) {
            for(int i=0; i<wiki_words.length; i++)
                wiki_words[i] = null;
            wiki_words = null;
        }
    }



    /** Parses text, creates array of wiki words (words with hyperlinks),
     * e.g. text is "[[little]] [[bell]]", wiki_words[]={"little", "bell"}
     * This function should be used for definitions / meanings.
     * 
     * @return NULL if there is no text.
     */
    public static WikiText createOnePhrase(String page_title, String _wikified_text)
    {
        _wikified_text = _wikified_text.trim();
        if(0 == _wikified_text.length()) {
            return null;
        }
        StringBuffer sb = new StringBuffer(_wikified_text);
        
        String      s = WikiWord.parseDoubleBrackets(page_title, sb).toString();
        WikiWord[] ww = WikiWord.getWikiWords(page_title, sb);
        
        if(s.length() == _wikified_text.length())
            _wikified_text = null; // wikified text is NULL if "text" hasn't any wikification
        
        return new WikiText(s, _wikified_text, ww);
    }

    /** Parses text (split by commas), creates array of wiki words (words with hyperlinks),
     * e.g. text is "[[little]] [[bell]], [[handbell]], [[doorbell]]".
     * This function should be used to split wikified list of synonyms and translations.
     * @return empty array if there is no text.
     */
    public static WikiText[] createSplitByComma(String page_title, String text)
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

    /** Parses text (split by semicolons), creates array of wiki text fragments
     * This function should be used to split wikified list of synonyms and translations.
     * @return empty array if there is no text.
     */
    public static WikiText[] createSplitBySemicolon(String page_title, String text)
    {
        if(0 == text.trim().length()) {
            return NULL_WIKITEXT_ARRAY;
        }
        
        String[] ww = ptrn_semicolon.split(text);   // split by comma and semicolon
        
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
     
    /** Creates array of wiki words (words with hyperlinks) without any parsing.
     *
     * @param wikified_words words which are already without [[wikification]],
     *              e.g. translation extracted from {{t|lang_code|wiki_word}}
     * @return empty array if there is no text.
     */
    public static WikiText[] createWithoutParsing(String page_title,
                                                    List<String> wikified_words)
    {
        int size = wikified_words.size();
        if(0 == size)
            return NULL_WIKITEXT_ARRAY;

        WikiText[] wt = new WikiText[size];
        int i=0;
        for(String w : wikified_words) {

            WikiWord[] ww_array1 = new WikiWord[1];
            ww_array1[0] = new WikiWord(w, w, null);

            // todo: check "[["+w+"]]" - is it necessary?
            wt[i++] = new WikiText(w, "[["+w+"]]", ww_array1);
        }

        return wt;
    }
    
    /** @return true if object are equals (the same texts and the same wikified texts). */
    static public boolean equals (WikiText one, WikiText two) {
    
        if (null == one && null == two)
            return true;
        if (null == one || null == two)
            return false;
        
        String text1 = one.text;
        String text2 = two.text;
        
        if (null == text1 && null == text2)
            return true;
        if (null == text1 || null == text2)
            return false;
        
        if(text1.equalsIgnoreCase(text2)) {
            
            // wikified text is NULL if "text" hasn't any wikification
            String w1 = one.wikified_text;
            String w2 = two.wikified_text;
            
            if (null == w1 && null == w2)
                return true;
            if (null == w1 || null == w2)
                return false;
            
            return w1.equalsIgnoreCase(w2);
        }
        
        return false;
    }
}


    // return true, if wiki text corresponds only to one word, it's important for translation
    // e.g. "[[little]] [[bell]]" == false;
    //      "[[doorbell]]" == true
    //
    // boolean isOneWord (translation)
    // todo
    // ...