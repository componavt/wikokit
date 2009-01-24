/* ImageParser.java - parser of wiki Image [[Image:...]].
 * 
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.text;

import wikipedia.language.LanguageType;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** Parser of wiki Image [[Image:...]].
 */
public class ImageParser {
    
    
    // start of the image
    private final static Pattern ptrn_image_ru = Pattern.compile(
            "\\[\\[Изображение:");
    
    private final static Pattern ptrn_image_en = Pattern.compile(
            "\\[\\[Image:");
    //private final static Pattern ptrn_image_en = Pattern.compile("\\[\\[Image:(.+?)\\]\\]");
    
    
    // end of the image
    private final static Pattern ptrn_image_boundaries = Pattern.compile(
            "\\||\\[\\[|\\]\\]"); // pipe |, or open [[, or close ]]
    
    private final static StringBuffer   NULL_STRINGBUFFER = new StringBuffer("");
    
    /** Removes Image tag and remains the title of the image. 
     * This func should be called before WikiParser.parseDoubleBrackets().
     * 
     * @param wiki_lang     the word "Image" depends on wiki language, 
     *                  e.g.  "Dosiero" (Esperanto), "Изображение" (Russian) etc.
     */
    public static StringBuffer parseImageDescription(
            StringBuffer text,LanguageType wiki_lang)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        
        if( wiki_lang == LanguageType.ru) {
            // parse English and Russian images
            text = parseImageDescription(text, ptrn_image_en);
            return parseImageDescription(text, ptrn_image_ru);
        } else {
            if(wiki_lang == LanguageType.en || wiki_lang == LanguageType.simple) {
                return parseImageDescription(text, ptrn_image_en);
            } else {
                // print message: todo 
                System.out.println("Warning (wikipedia.text.ImageParser.parseImageDescription()): is valid only for English and Russian. Todo.");
            }
        }
        return text;
    }
    
    private static StringBuffer parseImageDescription(
            StringBuffer text,Pattern p_image_start)
    {
        final String w_closed_too_many = "Warning (wikipedia.text.ImageParser.parseImageDescription()): number of opened brackets '[[' < than closed brackets ']]' in image";
        final String w_opened_too_many = "Warning (wikipedia.text.ImageParser.parseImageDescription()): number of opened brackets '[[' > than closed brackets ']]' in image";
        int n_nested = 0;
        
        Matcher m_start = p_image_start.matcher(text.toString());
        boolean b_start = m_start.find();
        if(!b_start)
            return text;
        
        StringBuffer sb = new StringBuffer();
        while(b_start) {
            
            m_start.appendReplacement(sb, "");
            
            StringBuffer after_image = new StringBuffer();
            m_start.appendTail(after_image);
            
            Matcher m = ptrn_image_boundaries.matcher(after_image.toString());
            boolean b_internal = m.find();
            boolean b_desc_exist = false;   // description of the image
            boolean b_desc_started = false; // the text is started after first open brackets
            if(b_internal) {
                
                n_nested = 1; // [[Image: - already 1 open bracket
                StringBuffer s_candidate_desc = new StringBuffer(); // candidate of text after last pipe | in Image
                while(b_internal) {
                    String g0 = m.group(0);
                    if('|' == g0.charAt(0)) {
                        b_desc_exist = true;
                        if(b_desc_started) { // pipe within desc, e.g. Image:a.jpg|[[Lemma|Word]]
                            m.appendReplacement(s_candidate_desc, g0);
                        } else { // vertical line, pipe | then start new candidate of description
                            s_candidate_desc.setLength(0);
                            m.appendReplacement(new StringBuffer(), ""); // clear regex buffer   
                        }
                    } else {
                        if('[' == g0.charAt(0)) {   // opened '['
                            n_nested ++;
                            b_desc_started = true;
                        } else {                    // closed ']'
                            n_nested --;
                        }
                        if(n_nested == 0) {         // [[Image:...]] closed
                            b_internal = false;
                            if (b_desc_exist) {
                                m.appendReplacement(s_candidate_desc, "");
                            } else {
                                m.appendReplacement(new StringBuffer(), ""); // clear regex buffer   
                            }
                        } else {
                            m.appendReplacement(s_candidate_desc, g0);
                        }
                    }
                    b_internal = b_internal && m.find();
                }
                sb.append(s_candidate_desc);
            }
            StringBuffer remain = new StringBuffer(); 
            m.appendTail(remain);
            m_start = p_image_start.matcher(remain.toString());
            b_start = m_start.find();
        }
        m_start.appendTail(sb);
            
        if(n_nested < 0) {
            System.out.println(w_closed_too_many);
        } else {
            if(n_nested > 0) {
                System.out.println(w_opened_too_many);
            }
        }
        return sb;
    }
    
}
