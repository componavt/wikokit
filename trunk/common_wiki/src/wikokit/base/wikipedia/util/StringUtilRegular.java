/* StringUtilRegular.java - realm of regular expressions.
 *
 * Copyright (c) 2005-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikokit.base.wikipedia.util;

import wikokit.base.wikipedia.language.Encodings;
import java.util.ArrayList;
import java.util.List;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
//import java.util.regex.PatternSyntaxException;

/** String usefull functions via regular expressions */
public class StringUtilRegular {
    
    //private static final String table_rus_default = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String[][] table_lat_ru_default = 
    {
        {"A", "А"},
        {"B","Б"},
        {"V","В"},
        {"G","Г"},
        {"D","Д"},
        {"E","Е"},
        {"Yo","Ё"},
        {"Zh","Ж"},
        {"Z","З"},
        {"I","И"},   //И"},
        {"J","Й"},
        {"K","К"},
        {"L","Л"},
        {"M","М"},
        {"N","Н"},
        {"O","О"},
        {"P","П"},
        {"R","Р"},
        {"S","С"},
        {"T","Т"},
        {"U","У"},
        {"F","Ф"},
        {"X","Х"},
        {"C","Ц"},
        {"Ch","Ч"},
        {"Sh","Ш"},
        {"W","Щ"},
        {"~","Ъ"},
        {"Y","Ы"},
        {"'","Ь"},
        {"E'","Э"},
        {"Yu","Ю"},
        {"Ya","Я"},
        {"a","а"},
        {"b","б"},
        {"v","в"},
        {"g","г"},
        {"d","д"},
        {"e","е"},
        {"yo","ё"},
        {"zh","ж"},
        {"z","з"},
        {"i","и"},
        {"j","й"},
        {"k","к"},
        {"l","л"},
        {"m","м"},
        {"n","н"},
        {"o","о"},
        {"p","п"},
        {"r","р"},
        {"s","с"},
        {"t","т"},
        {"u","у"},
        {"f","ф"},
        {"x","х"},
        {"c","ц"},
        {"ch","ч"},
        {"sh","ш"},
        {"w","щ"},
        {"~","ъ"},
        {"y","ы"},
        {"'","ь"},
        {"e'","э"},
        {"yu","ю"},
        {"ya","я"}
    };
    
    public StringUtilRegular() {
    }
    
    /** Strips non-word letters in source array "words".
     * E.g. {"\nword1", "\t word-long2\r\n"} -> {"word1", "word-long2"}.
     */
    public static void stripNonWordLetters(String words[]) {
        
        String  str_pattern = "\\A\\W*(.+?)\\W*\\Z";

        List<String> result = new ArrayList<String>();
        Pattern p = Pattern.compile(str_pattern);
        
        for(int i=0; i<words.length; i++) {
            Matcher m = p.matcher(words[i]);
            if (m.find()){
                words[i] = m.group(1);
            }
        }
    }
    

    /** Gets first letters till space (starting at first column). */
    private final static Pattern ptrn_letters_till_space = Pattern.compile(
            "(\\S\\S+)\\s");
    private final static Pattern ptrn_letters_till_hyphen = Pattern.compile(
            "^\\s*([^-]+?)-");
    /** Gets first letters till space, hyphen or pipe. */
    private final static Pattern ptrn_letters_till_pipe = Pattern.compile(
            "\\A(\\S\\S+?)\\|");
    private final static String NULL_STRING = new String();
    
    /** Gets first letters till space.
     * E.g. "word1 " -> "word1", "\t word-long2\r\n" -> "word-long2"
     */
    public static String getLettersTillSpace(String text) {
        
        Matcher m = ptrn_letters_till_space.matcher(text);
        if (m.find()){
            return m.group(1);
        }
        return NULL_STRING;
    }

    /** Gets first letters till space " ", ... or pipe "|" (shortest string).
     * E.g. "word1 " -> "word1", "\t word-long2\r\n" -> "word-long2"
     * This functions is used by WPOSRu.guessPOS().
     */
    public static String getLettersTillSpaceHyphenOrPipe(String text) {
        Matcher m;

        String  s_space = text; // because max(length) = text.length()
        boolean b_space = false;

        String  s_hyphen = text;
        boolean b_hyphen = false;

        String  s_pipe = text;
        boolean b_pipe = false;

        m = ptrn_letters_till_space.matcher(text);
        if (m.find()) {
            b_space = true;
            s_space = m.group(1);
        }

        m = ptrn_letters_till_hyphen.matcher(text);
        if (m.find()) {
            b_hyphen = true;
            s_hyphen = m.group(1);
        }

        m = ptrn_letters_till_pipe.matcher(text);
        if (m.find()) {
            b_pipe = true;
            s_pipe = m.group(1);
        }

        if(b_space && s_space.length() <= s_hyphen.length()
                   && s_space.length() <= s_pipe.length())
            return s_space;

        if(b_hyphen && s_hyphen.length() <= s_space.length()
                    && s_hyphen.length() <= s_pipe.length())
            return s_hyphen;

        if(b_pipe && s_pipe.length() <= s_hyphen.length()
                  && s_pipe.length() <= s_space.length())
            return s_pipe;
        
        return NULL_STRING;
    }


    /** Corresponds to different type of whitespaces,
     * @see http://stackoverflow.com/questions/1822772/java-regular-expression-to-match-all-whitespace-characters
     * @see http://en.wikipedia.org/wiki/Space_%28punctuation%29
     */
    private final static Pattern ptrn_whitespace  = Pattern.compile("\\p{Z}"); // "\\s");


     /** Replaces special spaces by usual whitespace, e.g. in quote author names "Name Surname"
     */
    public static String replaceComplexSpacesByTrivialSpaces(String text) {

        Matcher match = ptrn_whitespace.matcher(text);
        return match.replaceAll(" ");
    }

    /** Gets first letters till first hyphen "-".
     * E.g. "word1 " -> "word1", "\t word-long2\r\n" -> "word-long2"
     */
    public static String getLettersTillHyphen(String text) {

        Matcher m = ptrn_letters_till_hyphen.matcher(text);
        if (m.find()){
            return m.group(1);
        }
        return NULL_STRING;
    }
    
    
    /** Encodes the text to latinitsa, e.g.: женьшень -> zhen'shen' (Russian) */
    // public static String encodeRussianToLatinitsa (String text) {
    //
    // Latin1ToUTF8
    // encodeRussianToLatinitsa (text, "ISO8859_1", "UTF8");
    //
    public static String encodeRussianToLatinitsa (String text, String enc_from, String enc_to) {
        String[][]  table_lat_ru = new String [table_lat_ru_default.length][2];
        for(int i=0; i<table_lat_ru_default.length; i++) {
            table_lat_ru [i]    = new String [2];
            table_lat_ru [i][0] =                  table_lat_ru_default [i][0];
            table_lat_ru [i][1] = Encodings.FromTo(table_lat_ru_default [i][1], enc_from, enc_to);
        }
    
        //table_rus = Encodings.Latin1ToUTF8(table_rus_default);
        String result = "";
        
        for(int i=0; i<text.length(); i++) {
            String c = text.substring(i, i+1);
            
            boolean bfound = false;
            for(String[] t:table_lat_ru) {
                if(c.equals(t[1])) {    // equals to Russian letter
                    result += t[0];     // substitute by the same English letter
                    bfound = true;
                    break;
                }
            }
            if(!bfound) {
                result += c;
            }
        }
        return result;
    }
    
    // Wiktionary 
    
    /** Gets position of 2nd, 3rd or 4th level header ===? Header ===? */
    private final static Pattern ptrn_2345_level = Pattern.compile(
            //"===?=?\\s*[^=]+\\s*===?=?\\s*\\n");
            "={2,5}\\s*[^=]+\\s*={2,5}\\s*\\n");
    
    /** Gets position of first header in text from start_pos,
     * e.g. 2nd, 3rd, 4th, or 5th level header ==?=?=? Header ==?=?=?,
     * If header is absent then return -1.
     */
    public static int getFirstHeaderPosition(int start_pos, String text) {
        Matcher m = null;

        if(start_pos < 0 || start_pos > text.length()-1) {
            return -1;
        }
        
        if(0 == start_pos) {
            m = ptrn_2345_level.matcher(text);
        } else {
            m = ptrn_2345_level.matcher(text.substring(start_pos));
        }
        
        if (m.find()){
            return start_pos + m.start();
        }
        return -1;
    }

    /** Gets position of 2nd, 3rd or 4th level header ===? Header ===? */
    private final static Pattern ptrn_empty_line = Pattern.compile(
            "^\\s*$", Pattern.MULTILINE);

    /** Gets position of first header in text from start_pos,
     * e.g. 2nd, 3rd or 4th level header ==?=? Header ==?=?,
     * If header is absent then return -1.
     */
    public static int getFirstEmptyLinePosition(int start_pos, String text) {
        Matcher m = null;

        if(start_pos < 0 || start_pos > text.length()-1) {
            return -1;
        }

        if(0 == start_pos) {
            m = ptrn_empty_line.matcher(text);
        } else {
            m = ptrn_empty_line.matcher(text.substring(start_pos));
        }

        if (m.find()){
            return start_pos + m.start();
        }
        return -1;
    }

    /** Gets text from 'start_pos' position till position of first header
     * in text, or till the end of text (if header is absent).
     */
    public static String getTextTillFirstHeaderPosition(int start_pos, String text) {

        if(start_pos < 0 || start_pos > text.length()-1) {
            return NULL_STRING;
        }

        int header_pos = getFirstHeaderPosition(start_pos, text);

        if(-1 == header_pos) { // header is absent may be
            return text.substring(start_pos);
        }
        
        return text.substring(start_pos, header_pos);
    }
    
    /** Gets text from 'start_pos' position till the nearest position:
     * (1) of first header text, or (2) of first empty line,
     * (3) or till the end of text (if header and empty lines are absent).
     */
    public static String getTextTillFirstHeaderOrEmptyLine(int start_pos, String text) {

        if(start_pos < 0 || start_pos > text.length()-1) {
            return NULL_STRING;
        }

        int header_pos     = getFirstHeaderPosition   (start_pos, text);
        int empty_line_pos = getFirstEmptyLinePosition(start_pos, text);

        if(-1 == header_pos && -1 == empty_line_pos) { // header is absent may be
            return text.substring(start_pos);
        }
        
        // select min(header_pos, empty_line_pos) but != -1

        if(-1 == header_pos) { // header is absent may be
            return substringAndchopLastNewline(text, start_pos, empty_line_pos);
        }

        if(-1 == empty_line_pos) { // empty lines are absent may be
            return substringAndchopLastNewline(text, start_pos, header_pos);
        }

        if(empty_line_pos < header_pos) {
            return substringAndchopLastNewline(text, start_pos, empty_line_pos);
        }
        return substringAndchopLastNewline(text, start_pos, header_pos);
    }
    
    /** Gets text substring from 'start_pos' position till 'end_pos' position
     * and chop last symbol if it is newline \n symbol.
     */
    public static String substringAndchopLastNewline(String text, int start_pos, int end_pos) {

        if(start_pos < 0 || start_pos >= end_pos || end_pos > text.length()-1) {
            return NULL_STRING;
        }
        
        if(end_pos > 0 && '\n' == text.charAt(end_pos-1)) {
               end_pos --;
        }
        
        return text.substring(start_pos, end_pos);
    }


}
