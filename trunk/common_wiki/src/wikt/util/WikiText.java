/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wikt.util;

/** WikiText is a text, where [[some]] [[word]]s [[be|are]] [[wikify|wikified]],
 * e.g. "[[little]] [[bell]]".
 */
public class WikiText {

    /** Visible text, e.g. "bullets m." for "[[bullet]]s {{m}}" */
    private String text;
    
     /** Wiki internal links, e.g. "bullet" and "bullets for "[[bullet]]s" {{m}} */
    private WikiWord[] wiki_words;

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

    // return true, if wiki text corresponds only to one word, it's important for translation
    // e.g. "[[little]] [[bell]]" == false;
    //      "[[doorbell]]" == true
    //
    // boolean isOneWord (translation)
    // todo
    // ...
    
    /** Parses text, creates array of wiki words (words with hyperlinks). */
    public static WikiText create(String page_title, String text)
    {
        if(0 == text.trim().length()) {
            return null;
        }
        StringBuffer sb = new StringBuffer(text);
        
        String      s = WikiWord.parseDoubleBrackets(page_title, sb).toString();
        WikiWord[] ww = WikiWord.getWikiWords(page_title, sb);
        return new WikiText(s, ww);
    }

}
