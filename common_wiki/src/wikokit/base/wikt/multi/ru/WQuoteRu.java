/* WQuoteRu.java - corresponds to the phrase/sentence that illustrates a meaning
 *               of a word in Russian Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru;

import wikokit.base.wikipedia.util.StringUtilRegular;
import wikokit.base.wikipedia.util.StringUtil;
import wikokit.base.wikt.word.WQuote;
import wikokit.base.wikt.multi.ru.quote.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wikokit.base.wikipedia.text.TemplateParser;

/** Phrase or sentence that illustrates a meaning of a word in Russian Wiktionary.
 */
public class WQuoteRu {
    private static final boolean DEBUG = false;
    private final static WQuote[] NULL_WQUOTE_ARRAY = new WQuote[0];
    private final static String[] NULL_STRING_ARRAY = new String[0];
    
    
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
        return (String[])result_list.toArray(NULL_STRING_ARRAY);
    }

    /** Removes highlighted marks from a sentence.
     * 1) Sentence with '''words'''. -> Sentence with words.
     * 2) Sentence with {{выдел|words}}. -> Sentence with words.
     */
    public static String removeHighlightedMarksFromSentence(String str)
    {
        if(str.contains("{{выдел|")) {
            String s = str.replace("{{выдел|", "").replace("}}", "");

            // because, there are "{{-}}" -> "{{-" in the text
            return s.replace("{{-", " - ");

        } else if(str.contains("'''")) {
                return str.replace("'''", "");
        }

        return str;
    }

    /** Additional treatment of the sentence text:
     * 1) &nbsp;, &#160; -> " "
     * 2) {{-}} -> " - "
     * 3) poetry: "//" -> "\n"
     */
    public static String transformSentenceText(boolean is_sqlite, String str)
    {
        str = StringUtil.replaceSpecialChars( str );

        if(str.contains("{{-}}"))
            str = str.replace("{{-}}", " - ");

        if(str.contains(" // "))
            str = str.replace(" // ", "\n");

        if(str.contains("//"))
            str = str.replace("//", "\n");

        if(is_sqlite && str.contains("\\\""))   // \" -> " (SQLite feature)
            str = str.replace("\\\"", "\"");

        return str;
    }


    /** Replaces quotation template:" by quotations,
     * e.g. 'Фрегат {{"|Паллада}}' ->
     *      'Фрегат "Паллада"';
     *
     * @param text      source text with quotation template
     * @param pos_quote position of the the quotation template in the 'text', quote != -1
     * @return text withtout template, but with quotes
     */
    private static String replaceQuoteTemplateByQuotationMarks(String text, int pos_quote) {

        // int pos = str.indexOf("{{\"|");
        // -1 != pos_quote 

        int pos_quote_end = text.indexOf("}}", pos_quote+3); // end of template
        if(-1 != pos_quote_end) {  // yes, replace

            // str : remove <pos_quote, pos_quote_end+2>
            StringBuilder sb_without_template = new StringBuilder(text.length() - 4); // 4 = - length("{{\"|" + "}}") + length('""')
            sb_without_template.append( text.substring(0, pos_quote)  );
            sb_without_template.append( '"' );
            sb_without_template.append( text.substring(pos_quote + 4, pos_quote_end)  );
            sb_without_template.append( '"' );
            sb_without_template.append( text.substring(pos_quote_end + 2)  );

            return sb_without_template.toString();
        }
        return text;
    }
    
    /** Replaces all quotation template:" by quotations if there is any,
     * e.g. 'Фрегат {{"|Паллада}}' ->
     *      'Фрегат "Паллада"';
     *
     * @param text      source text with quotation template
     * @return text without template, but with quotes
     */
    private static String replaceAllQuoteTemplateByQuotationMarks(String text) {
        
        int pos_quote = text.indexOf("{{\"|");
        
        while(-1 != pos_quote) {
            text = replaceQuoteTemplateByQuotationMarks(text, pos_quote);
            pos_quote = text.indexOf("{{\"|");
        }
        return text;
    }
    
    /** Replaces quotation template:кавычки|ru| by quotations,
     * e.g. {{кавычки|ru|Jam temp'esta}} ->
     *                  "Jam temp'esta"
     *
     * @param text      source text with quotation template
     * @param pos_quote position of the the quotation template in the 'text', quote != -1
     * @return text withtout template, but with quotes
     */
    private static String replaceKavychkiTemplateByQuotationMarks(String text, int pos_quote) {

        // int pos = str.indexOf("{{кавычки|");
        // -1 != pos_quote

        int pos_quote_end = text.indexOf("}}", pos_quote+3); // end of template
        if(-1 != pos_quote_end) {  // yes, replace

            //              | pipe between (optional)
            //  pos_quote                 pos_quote_end
            // "{{кавычки|ru|Jam temp'esta}},{{-}}отвечала ему...|Л. Юзефович|Казароза|2002"
            // "{{кавычки|Jam temp'esta}},{{-}}отвечала ему...|Л. Юзефович|Казароза|2002"

            int pos_pipe = text.indexOf("|", pos_quote+10); // 10 = length("{{кавычки|")
            if(pos_pipe >= pos_quote_end)
                pos_pipe = -1;  // it's not our pipe, it's after this template

            // str : remove <pos_quote, pos_quote_end+2>
            StringBuilder sb_without_template = new StringBuilder(text.length());
            sb_without_template.append( text.substring(0, pos_quote)  );
            sb_without_template.append( '"' );
            
            if(-1 == pos_pipe){  // {{кавычки|Jam                      10 = len("{{кавычки|")
                sb_without_template.append( text.substring(pos_quote + 10, pos_quote_end)  );
            } else {
                sb_without_template.append( text.substring(pos_pipe + 1, pos_quote_end)  );
            }
            
            sb_without_template.append( '"' );
            sb_without_template.append( text.substring(pos_quote_end + 2)  );

            return sb_without_template.toString();
        }
        return text;
    }
    
    /** Replaces all quotation кавычки|ru| by quotations if there is any,
     * e.g. {{кавычки|ru|Jam temp'esta}} ->
     *                  "Jam temp'esta"
     *
     * @param text      source text with quotation template
     * @return text without template, but with quotes
     */
    private static String replaceAllKavychkiTemplateByQuotationMarks(String text) {
        
        int pos_quote = text.indexOf("{{кавычки|");
        
        while(-1 != pos_quote) {
            text = replaceKavychkiTemplateByQuotationMarks(text, pos_quote);
            pos_quote = text.indexOf("{{кавычки|");
        }
        return text;
    }

    /** Extracts quote parameters from the template "{{пример|"
     * without start "{{пример|" and end "}}" elements of the template.
     *
     * There are two variants:
     * {{пример|текст|автор|титул|дата|}} - without parameters names
     * {{пример|текст=|перевод=|автор=|титул=|издание=|перев=|дата=|источник=}} - with names
     *
     * @param page_title    word which is described in this article
     * @param sb_line template without start "{{пример|" and "}}"
     *
     * @return filled WQuote, null if there are no text in the example sentence
     */
    private static WQuote parseQuoteParameters(String page_title, StringBuilder sb) {
        
        String  text = "";
        String  translation = "";
        String  transcription = "";
        String  publisher = "";
        String  source = "";

        AuthorAndWikilink author_and_wikilink = new AuthorAndWikilink();
        TitleAndWikilink title_and_wikilink = new TitleAndWikilink();
        YearsRange years_range = new YearsRange();

        String str = sb.toString();

        // 0a. before splitting by "|", replace {{выдел| by {{выдел!
        if(str.contains("{{выдел|"))
            str = str.replace("{{выдел|", "{{выдел!");

        // 0b. before splitting by "|"
        // expand parameters in the template "{{библия|", replace pipes "|" by dots "."
        if(str.toLowerCase().contains("{{библия")) {
            str = TemplateParser.expandTemplateParams(str, "библия2", "|", ".");
            str = TemplateParser.expandTemplateParams(str, "библия", "|", ".");
        }
        
        // 0c. before splitting by "|", replace template:" by quotations, e.g. 'Фрегат {{"|Паллада}}' -> 'Фрегат "Паллада"';
        if(-1 != str.indexOf("{{\"|"))
            str = replaceAllQuoteTemplateByQuotationMarks(str);

        if(-1 != str.indexOf("{{кавычки|"))
            str = replaceAllKavychkiTemplateByQuotationMarks(str);

        // 1. split
        //String[] params = str.split("\\|");
        String[] params = splitParameters(str);

        // 2. fills hash
        int param_counter = 0;  //  counter of unnamed parameters
for_label:
        for(String p : params) {    
            int pos_equal = p.indexOf("=");
            if(-1 == pos_equal) {  // there is no equal sign for this parameter
                param_counter ++;

                switch (param_counter) { // {{пример|1 текст|2 автор|3 титул|4 дата|}}
                    case 1:
                        if(p.length() == 0)
                            break for_label;
                        text = p; break;
                    case 2:
                        author_and_wikilink.parseAuthorName(p); break;
                    case 3:
                        title_and_wikilink.parseTitle(p); break;
                    case 4:
                        years_range.parseYearsRange(page_title, p.trim());
                        break;
                }
            } else {
                if(pos_equal+1 >= p.length() || pos_equal < 3)   // 4 == shortest parameter length = "дата".lenth()
                    continue;

                // split by "="
                String param_name = p.substring(0, pos_equal);
                String value = p.substring(pos_equal+1).trim();

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
                    years_range.parseYearsRange(page_title, value.trim());
                } else if(param_name.equalsIgnoreCase("источник")) {
                    source = value;
                }
            }
        }

        if (text.length() == 0)
            return null;

        // last. return format back
        text = text.replace("{{выдел!", "{{выдел|");
        if(translation.length() > 0)
            translation = translation.replace("{{выдел!", "{{выдел|");

        if(transcription.length() > 0)
            transcription = transcription.replace("{{выдел!", "{{выдел|");

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

            WQuote wq = parseQuoteParameters(page_title, sb_line);
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
