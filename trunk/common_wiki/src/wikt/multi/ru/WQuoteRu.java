/* WQuoteRu.java - corresponds to the phrase/sentence that illustrates a meaning
 *               of a word in Russian Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.ru;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import wikt.word.WQuote;

/** Phrase or sentence that illustrates a meaning of a word in Russian Wiktionary.
 */
public class WQuoteRu {
    private static final boolean DEBUG = false;
    private final static WQuote[] NULL_WQUOTE_ARRAY = new WQuote[0];
    private final static String[] NULL_STRING_ARRAY = new String[0];


    /** Inner Years class. */
    private static class YearsRange {
        YearsRange() {
            year_from = -1;
            year_to = -1;
        }

        /** Start date of a writing book with the quote. */
        public int year_from;

        /** Finish date of a writing book with the quote. */
        public int year_to;

        /** Converts string to integer, if there is any problem then return -1.
         */
        private static int stringToInt(String text)
        {
            int i = -1;
            try {
              i = Integer.parseInt(text);
            }
            catch (NumberFormatException nfe)
            {
              System.out.println("Error in WQuoteRu:YearsRange:stringToInt: uknown year format: " + nfe.getMessage());
            }
            return i;
        }

        /** Parses source text (e.g. "1882-1883"), stores years to year_from
         * and year_to. Store results to year_from and year_to.
         * If there is only one year, e.g. "1972", then year_from=year_to.
         * If text was not parsed successfully, then year_from=year_to=-1.
         */
        public void parseYearsRange(String text) {

            // range of years: 1880—1881
            int pos = text.indexOf("—");
            if(-1 == pos)
                pos = text.indexOf("-");

            if(-1 == pos) {
                // it's not a range
                year_from = YearsRange.stringToInt(text);
                year_to = year_from;
            } else {
                // it's a range, split it by "-" or "—"
                String str_from = text.substring(0, pos);
                String str_to = text.substring(pos + 1);

                if(str_from.length() > 0) {
                    year_from = YearsRange.stringToInt(str_from);
                    year_to = year_from;
                }

                if(str_to.length() > 0)
                    year_to = YearsRange.stringToInt(str_to);
            }
        }
    }

    /** Inner author class. */
    private static class AuthorAndWikilink {
        AuthorAndWikilink() {
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

    /** Inner title class. */
    private static class TitleAndWikilink {
        TitleAndWikilink() {
            title = "";
            title_wikilink = "";
        }

        /** Title of the work. */
        private String  title;

        /** Link to a book in Wikipedia (format: [[s:title|]] or [[:s:title|]]). */
        private String  title_wikilink;


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
    
    
    /** Gets, extracts from 'text' a definition till first example sentence starting from {{пример|. */
    public static String getDefinitionBeforeFirstQuote (String page_title, String text) {
        
        // Gets position before first example sentence {{пример|Самолёт-истребитель.}}
        int pos_quote = text.indexOf("{{пример|");

        if(-1 == pos_quote) {   // there is no quote section!
            
            // out of date quote template
            pos_quote = text.indexOf("{{пример перевод|");

            if(-1 == pos_quote) {   // there is no quote with translation section!
                if(DEBUG)
                    System.out.println("Warning in WQuoteRu.getDefinitionBeforeFirstQuote(): The article '"+
                                        page_title + "' has no quote '{{пример|' in a definition.");
                return text;
            }
        }

        return text.substring(0, pos_quote).trim();
    }

    /** Checks wheather the text has closing brackets without open brackets, 
     * so  if text looks like "|The title]]" (open "[[" is absent),
     *  then return true.
     */
    private static boolean isAbsentOpenDoubleSquareBrackets(String text) {

        int pos = text.indexOf("]]");
        if(-1 == pos)
            return false;

        return -1 == text.substring(0, pos).indexOf("[[");
    }

    /** Intellectual splitting of parameters of the template {{пример|}}.
     * It splits: {{пример|текст|автор|титул|дата|}}, 
     * but it does not split:
     * 1) [[:s:The title|The title]]
     * 2) [[some wikified word|it is fine]]
     *
     * As a result the functions extracts quote parameters from the template "{{пример|".
       |текст=|перевод=|автор=|титул=|издание=|перев=|дата=|источник=
     */
    private static String[] splitParameters(String text) {

        String[] pipe_chunks = text.split("\\|");

        List<String> source_list  = new LinkedList(Arrays.asList(pipe_chunks));
        List<String> result_list  = new ArrayList();

        // merge adjacent chunks if chunk.prev.contains("[[") and chunk.next.has("]]")

        Iterator it_source = source_list.iterator();
        while(it_source.hasNext())
        {
                                                // for (Iterator it = chunk_list.iterator(); it.hasNext();) {
            //prev_value = next_value;
            String value = (String)it_source.next();

            // if value looks like "|The title]]" (open "[[" is absent)
            // then it should be merged with previous chunk
            if(!isAbsentOpenDoubleSquareBrackets(value)) {
                result_list.add(value);
            } else {

                // result.last += value
                String prev = result_list.remove( result_list.size()-1 );
                result_list.add( prev + "|" + value );
            }
        }
        return( (String[])result_list.toArray(NULL_STRING_ARRAY) );
    }

    /** Extracts quote parameters from the template "{{пример|".
     *
     * {{пример|текст|автор|титул|дата|}} - without parameters names
     * {{пример|текст=|перевод=|автор=|титул=|издание=|перев=|дата=|источник=}} - with names
     *
     * @param sb_line template without start "{{пример|" and "}}"
     *
     * @return filled WQuote, null if there are no text in the example sentence
     */
    private static WQuote parseQuoteParameters(StringBuilder sb) {
        
        String  text = "";
        String  translation = "";
        String  transcription = "";

        //String  author = "";
        //String  author_wikilink = "";
        AuthorAndWikilink author_and_wikilink = new AuthorAndWikilink();

        //String  title = "";
        //String  title_wikilink = "";
        TitleAndWikilink title_and_wikilink = new TitleAndWikilink();
        
        String  publisher = "";
        String  source = "";

        //int year_from = -1;
        //int year_to = -1;
        YearsRange years_range = new YearsRange();

        // 0. before splitting by "|", replace {{выдел| by {{выдел!
        String str = sb.toString().replace("{{выдел|", "{{выдел!");

        // 1. split
        //String[] params = str.split("\\|");
        String[] params = splitParameters(str);

        // 2. fills hash
        int param_counter = 0;  //  counter of unnamed parameters
        for(String p : params) {
            
            int pos_equal = p.indexOf("=");
            if(-1 == pos_equal) {  // there is no equal sign for this parameter
                param_counter ++;

                switch (param_counter) { // {{пример|1 текст|2 автор|3 титул|4 дата|}}
                    case 1:
                        text = p; break;
                    case 2:
                        author_and_wikilink.parseAuthorName(p); break;
                    case 3:
                        title_and_wikilink.parseTitle(p); break;
                    case 4:
                        years_range.parseYearsRange(p);
                        break;
                }
            } else {
                if(pos_equal+1 >= p.length() || pos_equal < 3)   // 4 == shortest parameter length = "дата".lenth()
                    continue;

                // split by "="
                String param_name = p.substring(0, pos_equal);
                String value = p.substring(pos_equal+1);

                // {{пример|текст=|перевод=|автор=|титул=|издание=|перев=|дата=|источник=}}
                if(param_name.equalsIgnoreCase("текст")) {
                    text = value;
                } else if(param_name.equalsIgnoreCase("перевод")) {
                    translation = value;
                } else if(param_name.equalsIgnoreCase("автор")) {
                    author_and_wikilink.parseAuthorName(value);
                } else if(param_name.equalsIgnoreCase("титул")) {
                    title_and_wikilink.parseTitle(value);
                } else if(param_name.equalsIgnoreCase("издание")) {
                    publisher = value;
                } else if(param_name.equalsIgnoreCase("дата")) {
                    years_range.parseYearsRange(value);
                } else if(param_name.equalsIgnoreCase("источник")) {
                    source = value;
                }
            }
        }

        if (text.length() == 0)
            return null;

        // last. return format back
        text = text.replace("{{выдел!", "{{выдел|");

        return new WQuote ( text, translation, transcription,
                            author_and_wikilink.author, author_and_wikilink.author_wikilink,
                            title_and_wikilink.title, title_and_wikilink.title_wikilink,
                            publisher, source,
                            years_range.year_from, years_range.year_to);
    }


    /** Extracts quotations from 'text' after the definition,
     * each quotation starts from "{{пример|". */
    public static WQuote[] getQuotes (String page_title, String text) {

        List<WQuote> quote_list = null;

        if(-1 != text.indexOf("{{пример|}}") ||
           -1 != text.indexOf("{{пример||перевод=}}"))
                return NULL_WQUOTE_ARRAY;   // examples are empty

        // Gets position of the first example sentence {{пример|Самолёт-истребитель.}}
        int pos_quote = text.indexOf("{{пример|");
        if(-1 == pos_quote)   // there is no quote section!
            return NULL_WQUOTE_ARRAY;

        StringBuilder sb = new StringBuilder(
                text.substring(pos_quote + 9).trim() // 9 == "{{пример|".length()
        );

        if(sb.length() < 3) // sb == "some text }}", length >=3
            return NULL_WQUOTE_ARRAY;

        String[] lines = sb.toString().split("\\{\\{пример\\|");
        for(String line : lines) {

            StringBuilder sb_line = new StringBuilder(line);

            pos_quote = sb_line.lastIndexOf("}}");
            if(-1 == pos_quote)   // there is no close brackets, skip
                continue;

            if(pos_quote < 2) // too short, skip
                continue;
            sb_line.setLength(pos_quote);

            WQuote wq = parseQuoteParameters(sb_line);
            if(null != wq) {
                if(null == quote_list)
                    quote_list = new ArrayList<WQuote>();
                quote_list.add(wq);
            }
        }

        if(null == quote_list)
            return NULL_WQUOTE_ARRAY;

        return( (WQuote[])quote_list.toArray(NULL_WQUOTE_ARRAY) );
    }

}
