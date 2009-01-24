/*
 * WordNetParser.java
 *
 * Copyright (c) 2005 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package rfc2229;

import java.util.List;
import java.util.ArrayList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import wikipedia.util.StringUtil;
import wikipedia.util.StringUtilRegular;


/** Parses text of Wordnet's articles */
public class WordNetParser {
    
    public WordNetParser() {
    }
    
    /** Searches in the text something like "[syn: {sugar}, {one more sugar\r\n}]", and
     * extracts as list "sugar", "one more sugar" if the sought type is "syn".
     *
     * @params link_type There are the following types: syn, ant, also.
     */
    public static List<String> getLinks(String link_type, String text) {
        String str_pattern = "\\[" + link_type + "\\:\\s([^\\]]+)\\]";

        List<String> result = new ArrayList<String>();
        Pattern p = Pattern.compile(str_pattern);
        Matcher m = p.matcher(text);

        while (m.find()){
            String[] words = StringUtil.split(", ", m.group(1));
            
            StringUtilRegular.stripNonWordLetters(words);
            for(String w: words) {
                if(!result.contains(w)) {
                    result.add(w);
                }
            }
        }
        return result;
    }
    
    public static List<String> getSynonyms(String text) {
        return StringUtil.addOR(
                    getLinks("syn", text),
                    getLinks("also", text) );
    }
    
}
