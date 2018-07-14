/* TemplateExtractor.java - set of functions to extract {{template data|from the text}}.
 * 
 * Copyright (c) 2018 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikipedia.util.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import wikokit.base.wikipedia.util.StringUtil;

/** Set of functions to extract {{template data|from the text}} with known location (position) in text.
 */
public class TemplateExtractor {
    
    /** Name of template. */
    private String name;
    
    /** Template parameters,
     * if there are not parameters then params = NULL_STRING_ARRAY. */
    private String[] params;
    
    /** Start position of the template in the source string. */
    private int start_pos;
    
    /** End location of the template in the source string. */
    private int end_pos;
    
    private final static String[] NULL_STRING_ARRAY = new String[0];
    private final static TemplateExtractor[] NULL_TE_ARRAY = new TemplateExtractor[0];
    
    /** Gets name of template. */
    public String getName() {
        return name;
    }
    
    /** Gets parameters of the template. */
    public String[] getTemplateParameters() {
        return params;
    }
    
    /** Counts number of parameters of the template. */
    public int countTemplateParameters() {
        return params.length;
    }
    
    protected TemplateExtractor(String _name, String[] _params,int _start_pos, int _end_pos) {
    
        this.name       = _name;
        this.start_pos  = _start_pos;
        this.end_pos    = _end_pos;
        
        this.params = new String[ _params.length ];
        System.arraycopy(_params, 0, this.params, 0, _params.length);
    }
    
    /** @return true if all fields of two templates are the same. */
    static public boolean equals (TemplateExtractor one, TemplateExtractor two) {
    
        if (null == one && null == two)
            return true;
        if (null == one || null == two)
            return false;
        
        if(!one.name.equals(two.name))
            return false;
        
        if(one.start_pos != two.start_pos)
            return false;
        if(one.end_pos != two.end_pos)
            return false;
        
        if(null == one.params || null == two.params) {
            return one.params == two.params;
        }
        if(one.params.length != two.params.length)
            return false;
        
        for(int i=0; i<one.params.length; i++) {
            if(!one.params[i].equals( two.params[i] ))
                return false;
        }
        
        return true;
    };
    
    /** Gets first template from the source string 'text'.
     * 
     * This function parses only simple one-level templates, without nested templates, 
     * i.e. templates like "{{one{{two}}ops}}" will be parsed with errors.
     * 
     * @param text source text
     * @return NULL if there are no any templates in the source text
     */    
    public static TemplateExtractor getFirstTemplate(String text) {
        
        int start_pos = text.indexOf("{{");
        if(-1 == start_pos)        // ^ start_pos
            return null;
        
        int end_pos = text.indexOf("}}", start_pos);
        if(-1 == end_pos)        // ^ end_pos
            return null;
        end_pos ++;              // }}
                                 //  ^ end_pos
        
        //                |---- template text ------|
        // "text before {{template name|parameter one}} text after"
        //              ^ start_pos                   ^ end_pos
        
        String template_text = text.substring(start_pos + 2, end_pos - 1);
        if(template_text.length() == 0)
            return null;                // {{}} - empty template
        
        String template_name = "";
        String[] params = NULL_STRING_ARRAY;
        
        int pipe_pos = template_text.indexOf("|");
        if(-1 == pipe_pos) {
            // there are no any parameters
            template_name = template_text;
            
        } else {
            // there are parameters after first pipe |
                   template_name   = template_text.substring(0, pipe_pos);
            String template_params = template_text.substring(   pipe_pos + 1);
            
            if(template_name.length() == 0)
                return null;    // template {{|without name|only parameters}}
            
            if(template_params.length() > 0) {
                params = StringUtil.split("|", template_params);
            }
        }
        
        // remove leading and trailing spaces in params
        for(int i=0; i<params.length; i++)
            params[i] = params[i].trim();
        
        return new TemplateExtractor(template_name, params, start_pos, end_pos);
    }
    
    
    /** Gets parameters of the first encountered template {{template_name|parameters...}} 
     * from the source string 'text'.
     * 
     * @param template_name name of template which are parameters will be found
     * @param page_title  word described in this article 'text'
     * @param text source text
     * @return null if this template is absent in the source text or have empty parameters
     */    
    public static TemplateExtractor getFirstTemplateByName(String page_title, String template_name, String text) {
        
        int start_pos = text.indexOf("{{" + template_name);
        if(-1 == start_pos)        // ^ start_pos
            return null;
        
        int end_pos = text.indexOf("}}", start_pos);
        if(-1 == end_pos)        // ^ end_pos
            return null;
        end_pos ++;              // }}
                                 //  ^ end_pos
        
        //                             |---- template text --------|
        // "text before {{template name|parameter one|paremeter two}} text after"
        //              ^ start_pos                   ^ end_pos
        
        String s = "{{" + template_name;
        int len = s.length();                            // 1 is one pipe after template name 
        String template_params = text.substring(start_pos + 1 + len, end_pos - 1);
        if(template_params.length() == 0)
            return null;                // {{template_name|}} - template without parameters
        
        String[] params = NULL_STRING_ARRAY;
        if(template_params.length() > 0) {
            params = StringUtil.split("|", template_params);
        }
        
        boolean b_empty = true; // in case there is no any params, e.g. {{|||}}
        
        // remove leading and trailing spaces in params
        for(int i=0; i<params.length; i++) {
            params[i] = params[i].trim();
            if(params[i].length() > 0)
                b_empty = false;        // at least one parameter exists
        }
        if(b_empty)
            return null;
       
        return new TemplateExtractor(template_name, params, start_pos, end_pos);
    }
        
    /** Gets array of templates {{template_name|parameters...}} 
     * from the source string 'text'.
     * 
     * @param template_name name of template which are parameters will be found
     * @param page_title  word described in this article 'text'
     * @param text source text
     * @return empty array TemplateExtractor[] if this template is absent in the source text or have empty parameters
     */
    public static TemplateExtractor[] getAllTemplatesByName(String page_title, String template_name, String text) {
        
        TemplateExtractor t;
        
        Collection<TemplateExtractor> result = new ArrayList<>();
        
        t = TemplateExtractor.getFirstTemplateByName(page_title, template_name, text);
        String new_text = text;
        while(null != t) {
            result.add( t );
            new_text = new_text.substring(t.end_pos + 1);
            t = TemplateExtractor.getFirstTemplateByName(page_title, template_name, new_text);
        }
        
        if(0 == result.size())
            return NULL_TE_ARRAY;
        
        // update .start_pos and .end_pos
        int current_end_pos = 0; // current "end" position of this template in the source 'text'
        for (TemplateExtractor t_cur : result) {
            t_cur.start_pos += current_end_pos;
            t_cur.end_pos   += current_end_pos;
            
            current_end_pos += (t_cur.end_pos + 1);  
        }
        return ((TemplateExtractor[])result.toArray(NULL_TE_ARRAY));
    }
    
    /** Removes substring {{template text}} from the source 'text', trims result string.
     * !Attention: variable 'te' should be created by function getFirstTemplate().
     * 
     *                |---- template text ------|
     * "text before {{template name|parameter one}} text after" : source
     *              ^ start_pos                   ^ end_pos
     * 
     * "text before text after" : result
     * 
     * @param text source text
     * @param te TemplateExtractor created with the help getFirstTemplate() over the same source text.
     */    
    public static String extractTextWithoutTemplate (String text, TemplateExtractor te) {
        
        StringBuilder s = new StringBuilder(text.substring(0, te.start_pos));
        s.append(text.substring(te.end_pos + 1));
        
        return s.toString().trim();
    }
    
    public static String extractTextBeforeTemplate (String text, TemplateExtractor te) {
        return text.substring(0, te.start_pos);
    }
    public static String extractTextAfterTemplate (String text, TemplateExtractor te) {
        return text.substring(te.end_pos + 1);
    }
    
    /** Gets value by parameter name from an array of parameters in the form {"param1", "param2=value2", ...}
     * 
     * @param template_params
     * @param parameter_name
     * @return NULL if this parameter is absent or, there are no values for this parameter.
     */
    public static String getParameterValue (String[] params, String parameter_name) {
        if(null == params || params.length == 0)
            return null;
        
        for(String p : params) {
            String[] words = p.split("=");
            if(words.length != 2)   // name = value (2 words)
                continue;
            if(words[0].trim().equalsIgnoreCase(parameter_name))
                return words[1].trim();
        }
        return null;
    }
    
    /** Excludes one parameter from the array of params, return new array.
     * 
     * @return empty array if there are no elements after excluding
     */
    public static String[] excludeParameter (String[] params, String removable_parameter_name) {
        
        if(null == params || params.length == 0)
            return NULL_STRING_ARRAY;
        
        List<String> result = new ArrayList<String>();
        
        for(String p : params) {
            String[] words = p.split("=");
            
            if(!words[0].trim().equalsIgnoreCase(removable_parameter_name))
                result.add(p);
        }
        
        if(result.isEmpty())
            return NULL_STRING_ARRAY;
        
        return( (String[])result.toArray(NULL_STRING_ARRAY) );
    }
    
    
}
