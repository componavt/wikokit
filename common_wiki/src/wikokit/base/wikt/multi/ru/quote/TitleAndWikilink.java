/* TitleAndWikilink.java - corresponds to the (wikified) title 
 * of quote phrase/sentence that illustrates a meaning of a word in Russian Wiktionary.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.multi.ru.quote;

/** (Wikified) title of quote phrase / sentence.
 */
public class TitleAndWikilink {
    public TitleAndWikilink() {
        title = "";
        title_wikilink = "";
    }

    /** Title of the work. */
    public String  title;

    /** Link to a book in Wikipedia (format: [[s:title|]] or [[:s:title|]]). */
    public String  title_wikilink;


    /** Parses text (e.g. "[[:s:У окна (Андреев)|У окна]]") into
        * title_wikilink "У окна (Андреев)" and title "У окна".
        */
    public void parseTitle(String text) {

        // replace "&nbsp;" by " "
        if(text.contains("&nbsp;"))
            text = text.replace("&nbsp;", " ");

        title = text; // first version
        if(!(text.startsWith("[[:s:") ||
                text.startsWith("[[s:")) ||
            !text.endsWith("]]") ||
            !text.contains("|"))
            return;

        if(text.startsWith("[[:s:"))
            text = text.substring(5, text.length() - 2); // "[[:s:" . text . "]]"
        else
            text = text.substring(4, text.length() - 2); // "[[s:" . text . "]]"

        // split by |
        // [[:s:The title|The title]]
        int pos = text.indexOf("|");
        if(-1 == pos)
            return;

        title_wikilink = text.substring(0, pos);
        title = text.substring(pos + 1);
    }
}