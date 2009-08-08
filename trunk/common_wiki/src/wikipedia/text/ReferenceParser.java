/* ReferenceParser.java - parser of wiki references &lt;ref>...&lt;/ref>
 * 
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.text;

import wikipedia.util.StringUtil;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** Parser of wiki references &lt;ref>...&lt;/ref>
 */
public class ReferenceParser {
    
    //private final static Pattern ptrn_ref             = Pattern.compile("<ref>");
    private final static Pattern ptrn_ref_boundaries    = Pattern.compile("<ref>(.+?)</ref>");
    
    private final static Pattern ptrn_http_url  = Pattern.compile("\\bhttp://.+?(\\s|$)");
    
    private final static StringBuffer   NULL_STRINGBUFFER = new StringBuffer("");
    
    
    //sb = removeHTTPURL(sb);
    
    /** Removes URL like http://... fro the text.
     */ 
    //expandReferenceToEndOfText() {
    private static StringBuffer removeHTTPURL(StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        
        Matcher m = ptrn_http_url.matcher(text.toString());
        return new StringBuffer(m.replaceAll(""));
    }
    
    /** Expands texts of the refence, and adds it to the end of text.
     * 
     * If the reference contains a template, e.g. &lt;ref>{{cite book |..&lt;/ref>
     * then the whole reference will be deleted.
     */ 
    public static StringBuffer expandMoveToEndOfText(StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        
        //Matcher m = ptrn_ref_boundaries.matcher(StringUtil.escapeCharDollarAndBackslash(text.toString()));
        Matcher m = ptrn_ref_boundaries.matcher(text.toString());
        
        boolean bfound = m.find();
        if(bfound) {
            StringBuffer result = new StringBuffer();
            StringBuffer eo_text = new StringBuffer();  // end of text
            while(bfound) {
                                                                   // group(1) := text within <ref>reference boundaries</ref>
                StringBuffer sb = WikiParser.parseCurlyBrackets(
                            StringUtil.escapeCharDollarAndBackslash(m.group(1) ));
                sb = removeHTTPURL(sb);
                            
                eo_text.append( sb );
                m.appendReplacement(result, "");
                bfound = m.find();
            }
            m.appendTail(result);
            if(eo_text.length() > 0) {
                result.append("\n\n");
                result.append(eo_text);
            }
            return result;
        }
        
        return text;
    }

    /** Removes refences from the text.
     */
    public static StringBuffer remove(StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }

        //Matcher m = ptrn_ref_boundaries.matcher(StringUtil.escapeCharDollarAndBackslash(text.toString()));
        Matcher m = ptrn_ref_boundaries.matcher(text.toString());

        boolean bfound = m.find();
        if(bfound) {
            StringBuffer result = new StringBuffer();
            StringBuffer eo_text = new StringBuffer();  // end of text
            while(bfound) {
                                                                   // group(1) := text within <ref>reference boundaries</ref>
                StringBuffer sb = WikiParser.parseCurlyBrackets(
                            StringUtil.escapeCharDollarAndBackslash(m.group(1) ));
                sb = removeHTTPURL(sb);

                eo_text.append( sb );
                m.appendReplacement(result, "");
                bfound = m.find();
            }
            m.appendTail(result);
            if(eo_text.length() > 0) {
                result.append("\n\n");
                result.append(eo_text);
            }
            return result;
        }

        return text;
    }
}
