/* YearsRange.java - corresponds to the years period in phrase/sentence 
 * that illustrates a meaning of a word in Russian Wiktionary.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.multi.ru.quote;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Years period in the quotation phrase / sentence.
 */
public class YearsRange {
    public YearsRange() {
        year_from = -1;
        year_to = -1;
    }

    /** Start date of a writing book with the quote, if there is no information about date then -1. */
    public int year_from;

    /** Finish date of a writing book with the quote, if there is no information about date then -1. */
    public int year_to;

    /** Converts string to integer, if there is any problem then return -1.
        */
    private static int stringToInt(String page_title, String text)
    {
        int i = -1;
        if(null != text && text.length() > 0) {

            try {
                i = Integer.parseInt(text);
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("Error in WQuoteRu:YearsRange:stringToInt: entry '"+ page_title +
                                    "' unknown year format, " + nfe.getMessage());
            }
        }
        return i;
    }

    // extract year XXXX from string, e.g. "1875?" or "12, 2000"
    private final static Pattern pattern_contains_4_year  = Pattern.compile("\\d{4}");

    // day day month year year year year (end of the string), e.g. 22 month 2003
    //private final static Pattern pattern_day_month_year  = Pattern.compile("\\d{1,2}\\s+\\D+\\s+\\d{4}\\Z");

    // extract_year_from_year_dot_month_dot_day, e.g. 2002.08.26
    //private final static Pattern pattern_year_dot_month_dot_day  = Pattern.compile("\\d{4}\\.\\d{1,2}\\.\\d{1,2}");

    /** Parses source text (e.g. "1882" or "08-07-2011" or "06.05.2006") 
     * and extracts four digits.
     * 
     * @return -1 If text was not parsed successfully
     */
    private int extractFourDigits (String page_title, String text) {
        
        int i = -1;
        
        Matcher m = pattern_contains_4_year.matcher(text);
        if(m.find()) {
            text = m.group();
            i = stringToInt(page_title, text);
        }
        return i;
    }
    
    /** Parses source text (e.g. "1882" or "08-07-2011" or "06.05.2006") 
     * and returns "true" in 2nd and 3rd cases, 
     * when there two not adjacent symbols "-" or ".", 
     * i.e. ".." or "--" generates "false".
     * 
     * @return -1 If text was not parsed successfully
     */
    private boolean containsTwoNonAdjacentSymbols (String page_title, String text, char symbol) {
        
        int pos1, pos2;
        
        if(-1 != (pos1 = text.indexOf(symbol)))
        {
            if(text.length() > pos1 + 2 &&                  // skip case "XXX-" failed in next line
               -1 != (pos2 = text.indexOf(symbol, pos1 + 1)))
            {
                if(pos2 - pos1 > 1)
                    return true;
            }
        }
        return false;
    }
    
    /** Parses source text (e.g. "1882-1883"), stores years to year_from
        * and year_to. Store results to year_from and year_to.
        * If there is only one year, e.g. "1972", then year_from=year_to.
        * If text was not parsed successfully, then year_from=year_to=-1.
        */
    public void parseYearsRange(String page_title, String text) {

        if(text.contains("{{-}}"))  // range of years with dash template {{-}}: 1998{{-}}2001
            text = text.replace("{{-}}", "-");

        if(text.contains("-е"))     // decade; tens of years, e.g. 1830-е
            text = text.replace("-е", "");

        // question_in_years e.g. 1862—1875?
        if(text.endsWith("?"))
            text = text.substring(0, text.length() - 1);
        
        if(   containsTwoNonAdjacentSymbols (page_title, text, '-') // "08-07-2011"
           || containsTwoNonAdjacentSymbols (page_title, text, '.') // "06.05.2006"
          ) {
            year_to = year_from = extractFourDigits(page_title, text);
            return;
        }

        // range of years: 1880—1881, 1842–1862
        int pos = text.indexOf("—");
        if(-1 == pos)
            pos = text.indexOf("-");
        if(-1 == pos)
            pos = text.indexOf("–");

        if(-1 == pos) {
            // it's not a range

            int len = text.length();
            if(len > 4) {

                // pattern_contains_4_year: "1875?"
                Matcher m = pattern_contains_4_year.matcher(text);
                if(m.find()) {
                    text = m.group();
                }

                // compare with: 22 month 2003
                /*m = pattern_day_month_year.matcher(text);
                if(m.find()) {
                    text = text.substring(len - 4);
                } else {
                    // compare with 2002.08.26
                    Matcher m2 = pattern_year_dot_month_dot_day.matcher(text);
                    if(m2.matches())
                        text = text.substring(0, 4);
                }*/
            }

            year_from = stringToInt(page_title, text);
            year_to = year_from;
        } else {
            // it's a range, split it by "-" or "—"
            String str_from = text.substring(0, pos);
            String str_to = text.substring(pos + 1);

            if(str_from.length() > 0) {
                year_from = stringToInt(page_title, str_from);
                year_to = year_from;
            }

            if(str_to.length() > 0)
                year_to = stringToInt(page_title, str_to);
        }
    }
}