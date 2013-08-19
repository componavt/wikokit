/* WikiTemplate - set of functions related to wiki {{templates|..}}.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikipedia.util.template;


/** Set of functions related to wiki {{templates|..}}.
 */
public class TemplateParser {
    
    //   Replace all substrings "target" by "replacement" in the part of "source_text" 
    //   which contains template titled "template_name".
    //public static String replaceInTemplate(String source_text, String template_name, 
    //                                       String target, String replacement) {
        
      
    /** Expands template parameters, 
     * replaces all substrings "target" by "replacement" in the part of "source_text" 
     * which contains template titled "template_name"; removes template name and brackets,
     * e.g. "abc{{template|start..target..end}}xyz" -> "abcstart..replacement..endxyz"
     *
     * @param text      source text with quotation template
     * @return text with replaced text
     */
    public static String expandTemplateParams(String source_text, String template_name, 
                                           String target, String replacement) {
        // 1. find position of "{{$template_name|" = pos_template_start
        // 2. find position of "}}" = pos_template_end
        // 3. result text = concat( source_text[0, pos_template_start],
        //                          replace([pos_template_start, pos_template_end], target, replacement),
        //                          source_text[pos_template_end, length])
        
        if(null == template_name || template_name.length() < 1)
            return source_text;
        
        int p = source_text.toLowerCase().indexOf("{{" + template_name.toLowerCase() + "|");
        if(-1 == p)
            return source_text; // this template is absent in source text
        int pos_template_start = p;
        
        p = source_text.indexOf("}}", pos_template_start + template_name.length());
        if(-1 == p)
            return source_text; // end of template is absent
        int pos_template_end = p;
                                                                                                // 3= 1 + 2 = len("{{") + len("|") in "{{template|"
        String template_body = source_text.substring(pos_template_start + template_name.length() + 3, pos_template_end);
        template_body = template_body.replace(target, replacement);
        
        StringBuilder result = new StringBuilder();
        result.append( source_text.substring(0, pos_template_start) );
        result.append( template_body );
        result.append( source_text.substring( pos_template_end+2 ) );
        
        return result.toString();
    }
    
    /** Expands template parameter, 
     * e.g. "abc {{template|some text}} xyz" -> "abc some text xyz"
     * e.g. "abc {{template|some {{subtemplate|}} text}} xyz" -> "abc some {{subtemplate|}} text xyz"
     *
     * @param text      source text with template
     * @return expanded text
     */
    public static String expandTemplateWithOneParameter(String source_text, String template_name) {
        
        // 1. find position of "{{$template_name|" = pos_template_start
        // 2. find position of "}}" = pos_template_end
        // 3. result text = concat( source_text[0, pos_template_start],
        //                          source_text[pos_template_start + template length, pos_template_end],
        //                          source_text[pos_template_end, length])
        
        if(null == template_name || template_name.length() < 1)
            return source_text;
        
        int p = source_text.toLowerCase().indexOf("{{" + template_name.toLowerCase() + "|");
        if(-1 == p)
            return source_text; // this template is absent in source text
        int pos_template_start = p;
        
        // p = source_text.indexOf("}}", pos_template_start + template_name.length());
// todo find
        p = indexOfClosingBracketsSameLevel(source_text, pos_template_start + template_name.length(), "{{", "}}");
        if(-1 == p)
            return source_text; // end of template is absent
        int pos_template_end = p;
        
                                                                                                // 3= 1 + 2 = len("{{") + len("|") in "{{template|"
        String template_body = source_text.substring(pos_template_start + template_name.length() + 3, pos_template_end);
        
        StringBuilder result = new StringBuilder();
        result.append( source_text.substring(0, pos_template_start) );
        result.append( template_body );
        result.append( source_text.substring( pos_template_end+2 ) );
        
        return result.toString();
    }
    
    
    // p = source_text.indexOfSameLevelClosingBrackets(source_text, "}}", pos_template_start + template_name.length());
        //if(-1 == p)
    /** Finds index of the closing template bracket at same level, 
     * e.g. "0        10            23      30                  abc {{template|some text}} xyz" -> "abc some text xyz"
     * e.g. "some text {{subtemplate|}} text}} xyz" -> 30 is the position of the closing brackets of the same level
     *
     * @param text      source text with template
     * @return index of closing bracket, -1 if there is some error or there is no any closing brackets
     */
    public static int indexOfClosingBracketsSameLevel(String text, int start_pos, String open_brackets, String closing_brackets) {
        
        int pos_opening_bracket = text.indexOf(open_brackets, start_pos);
        int pos_closing_bracket = text.indexOf(closing_brackets, start_pos);
        if(-1 == pos_closing_bracket)
            return -1;
        
        if(-1 == pos_opening_bracket || pos_closing_bracket < pos_opening_bracket)
            return pos_closing_bracket; // "}}" or "}} ... {{"
        
        // ok, now: {{ ... }}, let's go further
        while(-1 != pos_closing_bracket) {
            
            start_pos = pos_closing_bracket + closing_brackets.length();
                    
            pos_opening_bracket = text.indexOf(open_brackets, start_pos);
            pos_closing_bracket = text.indexOf(closing_brackets, start_pos);
            
            if(-1 == pos_closing_bracket)
                return -1;
        
            if(-1 == pos_opening_bracket || pos_closing_bracket < pos_opening_bracket)
                return pos_closing_bracket; // "}}" or "}} ... {{"
        }
        
        return -1;
    }
}
