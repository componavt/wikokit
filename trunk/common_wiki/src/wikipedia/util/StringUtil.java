/*
 * StringUtil.java
 *
 * Copyright (c) 2005-2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikipedia.util;

import java.text.StringCharacterIterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
//import java.util.Comparator;
//import java.io.UnsupportedEncodingException;

public class StringUtil {
    
    private StringUtil() {}
    
    private final static String         NULL_STRING       = "";
    private final static String[]       NULL_STRING_ARRAY = new String[0];
    private final static List<String>   NULL_STRING_LIST  = new ArrayList<String>(0);
    private final static StringBuffer   NULL_STRINGBUFFER = new StringBuffer("");
    
    public static String join(String token, String[] strings )
    {
        if (null == strings || 0==strings.length)
            return NULL_STRING;
        StringBuilder sb = new StringBuilder();
        
        for( int x = 0; x < ( strings.length - 1 ); x++ )
        {
            sb.append( strings[x] );
            sb.append( token );
        }
        sb.append( strings[ strings.length - 1 ] );
        
        return( sb.toString() );
    }
      
    public static String join( String token, int[] source)
    {
        if (null == source || 0==source.length)
            return NULL_STRING;
        
        String result = "";
        result += source[0];
        for(int i=1; i<source.length; i++) {
            result += token + source[i];
        }
        return result;
    }

    
    public static String[] split( String token, String s )
    {
        if(null == s || 0 == s.length())
            return NULL_STRING_ARRAY;
                
        List<String> ls = new ArrayList<String>();
        
        int previousLoc = 0;
        int loc = s.indexOf( token, previousLoc );
        if(-1 == loc) {
            ls.add(s);
        } 
        else         
        {
            do
            {
                ls.add( s.substring( previousLoc, loc ) );
                previousLoc = ( loc + token.length() );
                loc = s.indexOf( token, previousLoc );
            }
            while( ( loc != -1 ) && ( previousLoc < s.length() ) );

            ls.add( s.substring( previousLoc ) );
        }
        
        return( (String[])ls.toArray(NULL_STRING_ARRAY) );
    }

    /** Doubles slashes before quotes. */
    public static String escapeChars(String text){
        if (null == text) {
            System.out.println("Error in StringUtil.escapeChars(), argument is null.");
            return NULL_STRING;
        }

        if (text.equalsIgnoreCase("\\"))
            return "\\\\";

        StringBuilder result = new StringBuilder();
        StringCharacterIterator iterator = new StringCharacterIterator(text);
        char character =  iterator.current();
        while (character != StringCharacterIterator.DONE ){
          if (character == '\"') {
            result.append("\\\"");
          }
          else if (character == '\'') {
              result.append('\\');
              result.append('\'');
            //result.append("\\'");
          }
          else if (character == '\\') {
              result.append('\\');
              result.append('\\');
          }
          else {
            //the char is not a special one
            //add it to the result as is
            result.append(character);
          }
          character = iterator.next();
        }
        return result.toString();
    }
    
    /** Doubles slashes before dollar sign "$" and backslash "\", skip two slashes "\\".
     * 
     * slash1 (true if prev prev is "\")                                
     *  slash2 (previous)                                               
     *   slash3 (current character)                                     <br>
     *   $ -> \\$      if !slash1 && !slash2 then + "\\"                <br>
     * \\  -> \\       if  slash1 &&  slash2 then skip                  <br>
     * \\$ -> \\$
     */
    public static StringBuffer escapeCharDollarAndBackslash(String text){
        if (null == text) {
            System.out.println("Error in StringUtil.escapeCharDollar(), argument is null.");
            return NULL_STRINGBUFFER;   // NULL_STRING;
        }
        StringBuffer result = new StringBuffer();
        StringCharacterIterator iterator = new StringCharacterIterator(text);
        char character =  iterator.current();
        boolean slash1 = false, slash2 = false, slash3;
        while (character != StringCharacterIterator.DONE ){
            
            slash3 = character == '\\';
            boolean appended = false;
            if (    (!slash1 && !slash2)
                 ||  (slash1 &&  slash2)) {
                if ('$' == character) {
                    result.append("\\$");
                    appended = true;
                    slash1 = slash2 = false;
                } else {
                    if (slash3) {
                        appended = true;    // It will be appended in the next cycle
                        slash1 = slash2 = false;
                    }
                }
            } else {
                if (!slash1 && slash2) {
                    if ('$' == character) {
                        result.append("\\$");
                    } else {
                        result.append("\\\\");
                        if(slash3) {
                            result.append("\\\\");
                        } else {
                            result.append(character);
                        }
                    }
                    slash1 = slash2 = slash3 = false;
                    appended = true;
                }
            }
            
            if (!appended) {
                result.append(character);
            }
          
            slash1 = slash2;
            slash2 = slash3;
            character = iterator.next();
        }
        if (slash2) {
            result.append("\\\\");
        }
        return result;
    }
    
    
    /** Substitutes spaces by underscore character */
    public static String spaceToUnderscore(String text) {
        return text.replace(' ', '_');
    }
    
    /** Substitutes all underscores by space character */
    public static String underscoreToSpace(String text) {
        return text.replace('_', ' ');
    }
    
    /** Gets list of unique strings (case insensitive). */
    public static List<String> getUnique(List<String> l) {
        List<String> result = new ArrayList<String>();
        if(null == l)
            return result;
        
        for(int i=0; i<l.size(); i++) {
                boolean bunique = true;
                String s = l.get(i);
                for(int j=0; j<result.size(); j++) {
                    if(result.get(j).equalsIgnoreCase(s)) {
                        bunique = false;
                        break;
                    }
                }
                if(bunique)
                    result.add(s);
        }
        return result;
    }
    
    /** Adds two lists to one, i.e. creates the list of unique strings (case insensitive). */
    public static List<String> addOR(List<String> a,List<String> b) {
        
        if(a==null && b==null) {
            return NULL_STRING_LIST;
        }
        
        List<String> result = new ArrayList<String>();
        
        if(a==null || 0==a.size()) {
            result.addAll(b);
        } else if(b==null || 0==b.size()) {
            result.addAll(a);
        } else {
            result.addAll(getUnique(a));
                        
            for(int i=0; i<b.size(); i++) {
                boolean bunique = true;
                String word = b.get(i);
                for(int j=0; j<result.size(); j++) {
                    if(result.get(j).equalsIgnoreCase(word)) {
                        bunique = false;
                        break;
                    }
                }
                if(bunique)
                    result.add(word);
            }
        }
        return result;
    }

    /** Adds two arrays to one, i.e. creates the array of unique strings (case sensitive). */
    public static String[] addORCaseSensitive(String[] a,String[] b) {

        if(a==null && b==null) {
            return NULL_STRING_ARRAY;
        }

        List<String> result = new ArrayList<String>();

        if(a==null || 0==a.length) {
            result.addAll(Arrays.asList(b));
        } else if(b==null || 0==b.length) {
            result.addAll(Arrays.asList(a));
        } else {
            result.addAll(getUnique(Arrays.asList(a)));
            
            for(int i=0; i<b.length; i++) {
                if(!result.contains(b[i]))
                    result.add(b[i]);
            }
        }
        return ((String[])result.toArray(NULL_STRING_ARRAY));
    }
    
    /** Returns true if array 'ar' contains string 'wanted', ignore case. */
    public static boolean containsIgnoreCase(String[] ar, String wanted) {
        if(null == ar) {
            return false;
        }
        for(String s:ar) {
            if(wanted.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
 
    /** Returns intersections of two list.
     * Return null if input String[] is null.
     */
    public static String[] intersect(String[] list1, String[] list2) {
        if( null == list1 || 0 == list1.length ||
            null == list2 || 0 == list2.length) {
            return null;
        }
        
        Map<String, Boolean> map1 = new HashMap<String, Boolean>();
        
        for(String s:list1) {
            map1.put(s, false);
        }
        
        int counter = 0;
        for(String s:list2) {
            if(map1.containsKey(s)) {
                //map1.put(s, true);
                counter ++;
            }
        }
        
        String[] res = new String[counter];
        counter = 0;
        for(String s:list2) {
            if(map1.containsKey(s)) {
                res[counter++] = s;
            }
        }
        
        return res;
    }
    
    /** Gets substring before the first occurence of the character with 
     * the character ch.
     */ 
    private static String getTextBeforeFirstChar(String s, int ch) {
        if(null == s)
            return NULL_STRING;
        int i = s.indexOf(ch);
        if(-1 == i)
            return s;
        return s.substring(0, i);
    }
    
    public static String getTextBeforeFirstColumn(String s) {
        return getTextBeforeFirstChar(s, ':'); // ':' = 58
    }
    
    public static String getTextBeforeFirstVerticalPipe(String s) {
        return getTextBeforeFirstChar(s, '|'); // '|' = 124
    }
    
    
    /** Gets substring after the first occurence of the character with 
     * the character ch.
     */ 
    private static String getTextAfterFirstChar(String s, int ch) {
        if(null == s)
            return NULL_STRING;
        int i = s.indexOf(ch);
        if(-1 == i)
            return NULL_STRING;
        
        return s.substring(i+1);
    }
    
    public static String getTextAfterFirstColumn(String s) {
        return getTextAfterFirstChar(s, 58); // ":" = 58
    }
    public static String getTextAfterFirstVerticalPipe(String s) {
        return getTextAfterFirstChar(s, 124); // "|" = 124
    }
    public static String getTextAfterFirstSpace(String s) {
        return getTextAfterFirstChar(s, 32);
    }
    
    public static String getTextBeforeFirstAndSecondColumns(String s) {
        if(null == s)
            return NULL_STRING;
        int i1 = s.indexOf(58);  // ":" = 58
        if(-1 == i1)
            return s;
        
        int i2 = s.indexOf(58, i1+1);
        if(-1 == i2)
            return s.substring(i1 + 1);
        
        return s.substring(i1 + 1, i2);
    }
    
    /** Gets text from position 'pos' till the space or punctuation mark. */
    public static String getTextTillSpaceOrPuctuationMark(int pos, String s) {
        
        if(null == s || pos >= s.length() || pos < 0)
            return NULL_STRING;

        String punctuation_mark = "()[]{}〈〉:,‒–—―…!.-‐‽?‘’“”/·";

        // source: http://en.wiktionary.org/wiki/punctuation_mark
        //apostrophe ( ' ) ( ’ )
        //brackets ()[]{}〈〉
        //colon ( : )
        //comma ( , )
        //dashes ‒–—―
        //ellipsis …
        //exclamation mark ( ! )
        //full stop/period ( . )
        //hyphen -‐
        //interrobang ( ‽ )
        //question mark ( ? )
        //quotation marks ‘’“”
        //semicolon ( ; )
        //slash/solidus ( / )
        // space (   ) and interpunct ( · )

        int len = s.length();
        int i = pos;
        boolean space_or_punctuation = false;
        while (i < len && !space_or_punctuation) {
            char ch = s.charAt(i);
            space_or_punctuation = Character.isWhitespace(ch) || -1 != punctuation_mark.indexOf(ch);
            if (!space_or_punctuation) {
                i ++;
            }
        }
        return s.substring(pos, i);
    }
 
    /** Returns true if third character is column, e.g. "ru:test" */
    public static boolean isInterWiki(String title) {
        if(null == title || 3 > title.length())
            return false;
        return 58 == title.charAt(2);   // ":" is 58
    }
    
    /** Converts two letters word: first letter to Upper, second letter 
     * to Lower case. */
    public static String UpperFirstLowerSecondLetter(String s) {
        if(null == s || 2 != s.length())
            return s;
        
        return s.substring(0,1).toUpperCase() + s.substring(1,2).toLowerCase();
    }
    
    /** Converts first letter to upper-case (capitalization - good for WP, bad for Wiktionary). */
    public static String UpperFirstLetter(String s) {
        if(null == s || s.length() < 1)
            return s;
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }

    /*public static final Comparator<String> LEXICOGRAPHICAL_ORDER = new Comparator<String>() {
        public int compare(String s1, String s2) {

            int i = s1.compareToIgnoreCase(s2);
            System.out.println("i="+i+"; s1="+s1+"; s2="+s2);
            return i;

            //return s1.compareToIgnoreCase(s2);
        }
    };*/

    /** Trims each element of array. */
    public static String[] trim(String[] s) {
        for(int i=0; i<s.length; i++)
            s[i] = s[i].trim();
        return s;
    }

    /** Replace special characters (e.g. &nbsp;, &#160; by spaces).
     */
    public static String replaceSpecialChars(String str)
    {
        if(str.contains("&nbsp;"))
            str = str.replace("&nbsp;", " ");

        if(str.contains("&#160;"))
            str = str.replace("&#160;", " ");
        return str;
    }

}
