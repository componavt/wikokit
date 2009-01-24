/*
 * MobyParser.java
 *
 * Copyright (c) 2005 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package rfc2229;

import java.util.ArrayList;
import java.util.List;
import wikipedia.util.StringUtil;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
//import java.util.regex.PatternSyntaxException;
import wikipedia.util.StringUtilRegular;

/** Parses text of Moby's word list */
public class MobyParser {
    
    public MobyParser() {
    }
    
    /** Extracts words from the Moby's text.
     * Implementations:
     *  1. Takes substring from ":" till "."
     *  2. Split by comma ","
     *  3. Strip non-words letters, e.g. "\r\n  backset " -> "backset"
     * 
     * Example of source string:
     *  24 (3 in test) Moby Thesaurus words for "mulch":" \
     *  \r\n   backset, fallow,\r\n   fertilize, culture,\r\n   thin, work\r\n\r\n\r\n\r\n.\r\n
     */
    public static String[] getWords(String text) {
        
        Pattern p;
        Matcher m;
        
        // remove text from start till the first colon inclusively
        p  = Pattern.compile("(?s)\\A[^\\:]*:");
        m = p.matcher(text);
        text = m.replaceFirst("");
        
        // remove text from last dot till the end inclusively
        p  = Pattern.compile("(?s)\\..*?\\Z");
        m = p.matcher(text);
        text = m.replaceFirst("");
        
        
        String[] words = StringUtil.split(",", text);
        StringUtilRegular.stripNonWordLetters(words);
        
        // add unique words /replace by StringUtil.addOR/
        List<String> result = new ArrayList<String>();
        for(String w: words) {
            if(!result.contains(w)) {
                result.add(w);
            }
        }
        return (String[])result.toArray(new String[0]);
    }    
}
