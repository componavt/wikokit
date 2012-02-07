/* TableParser - parser of wiki tables {| .. |}.
 * 
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikokit.base.wikipedia.text;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** Parser of wiki tables {| .. |}.
 */
public class TableParser {
    
    private final static StringBuffer   NULL_STRINGBUFFER = new StringBuffer("");
    
    /*private final static Pattern ptrn_table_boundary        = Pattern.compile("{|(.+?)|}", Pattern.DOTALL);
    private final static Pattern ptrn_left_table_boundary   = Pattern.compile("{|");
    private final static Pattern ptrn_right_table_boundary  = Pattern.compile("|}");*/
    
    //private final static Pattern ptrn_table_boundaries = Pattern.compile("{\\||\\|}");
    private final static Pattern ptrn_table_boundaries = Pattern.compile("\\{\\||\\|\\}");
    
    /** Removes tables, and embedded tables also, e.g. 
     * "{| table 1 \n {| A table in the table 1 \n|}|}".
     * 
     * Remark: if this func is before CurlyBrackets () then it generates
     * warnings, since end of infobox (template) {{ |}} looks like end of table.
     */
    public static StringBuffer removeWikiTables(StringBuffer text)
    {
        final String w_closed_too_many = "Warning (wikipedia.text.TableParser.removeWikiTables()): number of opened brackets '{|' < than closed brackets '|}'";
        final String w_opened_too_many = "Warning (wikipedia.text.TableParser.removeWikiTables()): number of opened brackets '{|' > than closed brackets '|}'";
        
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        Matcher m = ptrn_table_boundaries.matcher(text.toString());
        boolean result = m.find();
        if(result) {
            StringBuffer sb = new StringBuffer();
            int n_nested = 0;
            while(result) {
                String g0 = m.group(0);
                if('{' == g0.charAt(0)) {
                    if(0 == n_nested) { // first opened bracket
                        m.appendReplacement(sb, "");
                    }
                    n_nested ++;
                } else {
                    n_nested --;
                    if(n_nested == 0) {
                        StringBuffer temp = new StringBuffer();
                        m.appendReplacement(temp, ""); // I don't know why this line is important!
                    }
                    if(n_nested < 0) {
                        System.out.println(w_closed_too_many);
                    }
                }
                result = m.find();
            }
            m.appendTail(sb);
            
            if(n_nested < 0) {
                System.out.println(w_closed_too_many);
            } else {
                if(n_nested > 0) {
                    System.out.println(w_opened_too_many);
                }
            }    
            return sb;
        }
        
        return text;
    }
    
    
}
