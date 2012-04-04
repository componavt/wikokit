/* AuthorAndWikilink.java - corresponds to the (wikified) author name 
 * in quote phrase/sentence that illustrates a meaning of a word in Russian Wiktionary.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.multi.ru.quote;

import wikokit.base.wikipedia.util.StringUtilRegular;

/** (Wikified) author name in quote phrase / sentence.
 */
public class AuthorAndWikilink {
    public AuthorAndWikilink() {
        author = "";
        author_wikilink = "";
    }

    /** Author's name of the quotation. */
    public String  author;

    /** Author's name in Wikipedia (format: [[w:name|]] or [[:w:name|]]). */
    public String  author_wikilink;


    /** Parses text (e.g. "[[:s:У окна (Андреев)|У окна]]") into
        * title_wikilink "У окна (Андреев)" and title "У окна".
        */
    public void parseAuthorName(String text) {

        text = StringUtilRegular.replaceComplexSpacesByTrivialSpaces(text);

        // replace "&nbsp;" by " "
        if(text.contains("&nbsp;"))
            text = text.replace("&nbsp;", " ");

        author = text; // first version
        if(!(text.startsWith("[[:w:") ||
                text.startsWith("[[w:")) ||
            !text.endsWith("]]") ||
            !text.contains("|"))
            return;

        if(text.startsWith("[[:w:"))
            text = text.substring(5, text.length() - 2); // "[[:w:" . text . "]]"
        else
            text = text.substring(4, text.length() - 2); // "[[w:" . text . "]]"

        // split by |
        // [[:w:The title|The title]]
        int pos = text.indexOf("|");
        if(-1 == pos)
            return;

        author_wikilink = text.substring(0, pos);
        author = text.substring(pos + 1);
    }
}