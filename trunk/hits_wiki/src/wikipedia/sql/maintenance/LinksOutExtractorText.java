/*
 * LinksExtractor.java - extract [[in- and [[out-links]] from the wikipedia articles
 *                       via regular expressions
 * Copyright (c) 2005, 2006 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.sql.maintenance;

import wikipedia.sql.*;
import wikipedia.util.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;
        
// See docs at  http://regex.info/java.html
//              http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/package-summary.html
//              http://java.sun.com/j2se/1.5.0/docs/api/java/util/regex/package-summary.html
// 
public class LinksOutExtractorText {
    /*
    public static Encodings  encodings;
    public LinksOutExtractorText() {
        encodings = new Encodings();
    }
     */
    
    public String[] getLinksViaPattern(String text, String str_pattern, String[] append_result)
    {
        Integer  i, current, size;
        String[] result;
        Pattern p = Pattern.compile(str_pattern);
        Matcher m = p.matcher(text);
        
        // calculate number of matches
        size = 0;
        while (m.find()){ size ++; }
        m.reset();
        
        if (0 >= size)
            return append_result;
        
        if (null==append_result) {
            result = new String[size];
            current = 0;
        } else {
            result = new String[size+append_result.length];
            
            // copy first result, it is supposed that duplicate (in append result) were already removed
            for(i=0;i<append_result.length; i++) {
                result[i] = append_result[i];
            }
            current = append_result.length;
        }
                
        while (m.find()){
            String new_match = m.group(1);
            boolean bunique  = true;
            for(i=0; i<current; i++) {
                if (result[i].equals(new_match)) {
                    bunique = false;
                    break;
                }
            }
            if (bunique)
                result[ current++ ] = new_match;
        }
        
        
        if (current == result.length) return result;
        
        // else "chop the empty back of result"
        String[] unique_result = new String [current];
        for(i=0; i<current; i++) {
            unique_result[i] = result[i];
        }
        
        return unique_result;
    }


    // Get links from the text 
    // 1) Stemmed case [[inside brackets till the first vertical line| others skip]]
    //  e.g. [[artificial consciousness|machines]]
    //
    //  pattern: \[\[([^\]\|]+)\|[^\]]+\]\]
    //  with spaces: \[\[  ([^\]\|]+)  \|  [^\]]+  \]\]
    // 
    // 2) Simple case [[only_one_link]], e.g. [[mind]], or [[brain]]
    //
    //  pattern: \[\[([^\]\|]+)\]\]
    //  with spaces: \[\[  ( [^\]\|]+ )  \]\]
    //                                          ** Test in PowerGrep
    public String[] getLinks(String text)
    {
        String[] result1 =  getLinksViaPattern(text, "\\[\\[([^\\]\\|]+)\\|[^\\]]+\\]\\]",   null);
        return              getLinksViaPattern(text, "\\[\\[([^\\]\\|]+)\\]\\]",             result1);
    }
}