/* TemplateExtractor.java - set of functions to extract {{template data|from the text}}.
 * 
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikipedia.util;

/** Set of functions to extract {{template data|from the text}} with known location (position) in text.
 */
public class TemplateExtractor {
    
    /** Name of template. */
    String name;
    
    /** Template parameters,
     * if there are not parameters then params = NULL_STRING_ARRAY. */
    String[] params;
    
    /** Start position of the template in the source string. */
    int start_pos;
    
    /** End location of the template in the source string. */
    int end_pos;
    
    private final static String[]       NULL_STRING_ARRAY = new String[0];
    
    protected TemplateExtractor(String _name, String[] _params,int _start_pos, int _end_pos) {
    
        this.name       = _name;
        this.start_pos  = _start_pos;
        this.end_pos    = _end_pos;
        
        this.params = new String[ _params.length ];
        System.arraycopy(_params, 0, this.params, 0, _params.length);
    };
    
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
     * This function parses only simple one-level templates, 
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
        return new TemplateExtractor(template_name, params, start_pos, end_pos);
    }
}
