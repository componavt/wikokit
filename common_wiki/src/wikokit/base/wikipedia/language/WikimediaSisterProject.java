/*
 * WikimediaSisterProject.java - codes of Wikimedia sister projects.
 * 
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikokit.base.wikipedia.language;

import wikokit.base.wikipedia.util.StringUtil;

import java.util.Map;
import java.util.HashMap;

/** Wikimedia sister projects: code and short codes, e.g. 'wikipedia' and 'w', 'wikt'. 
 *
 * See w:Wikipedia:Interwikimedia_links and w:Wikipedia:Wikimedia_sister_projects
 */
public class WikimediaSisterProject {
    
    /** Long form, e.g. 'wikipedia', 'commons', 'meta', etc. */
    private final String long_form;
    
    /** Shortcut, e.g. 'w', 'wikt', 'm'. */
    private final String shortcut;
    
    /** Project name, e.g. 'Wikipedia', 'Wiktionary'. */
    private final String name;
    
    /** Map from long_form and from shortcut to the project object */
    private static Map<String, WikimediaSisterProject> code2project = new HashMap<String, WikimediaSisterProject>();
    
    
    // list of projects:
    public static final WikimediaSisterProject Wikipedia    = new WikimediaSisterProject("wikipedia",   "w",    "Wikipedia");
    public static final WikimediaSisterProject Wiktionary   = new WikimediaSisterProject("wiktionary",  "wikt", "Wiktionary");
    public static final WikimediaSisterProject Wikinews     = new WikimediaSisterProject("wikinews",    "n",    "Wikinews");
    public static final WikimediaSisterProject Wikibooks    = new WikimediaSisterProject("wikibooks",   "b",    "Wikibooks");
    public static final WikimediaSisterProject Wikiquote    = new WikimediaSisterProject("wikiquote",   "q",    "Wikiquote");
    public static final WikimediaSisterProject Wikisource   = new WikimediaSisterProject("wikisource",  "s",    "Wikisource");
    public static final WikimediaSisterProject Wikispecies  = new WikimediaSisterProject("wikispecies", "",     "Wikispecies");
    public static final WikimediaSisterProject Wikiversity  = new WikimediaSisterProject("",            "v",    "Wikiversity");
    
    // in really there are two long forms: [[wikimedia:]] and [[foundation:]], and no shortcuts
    public static final WikimediaSisterProject Wikimedia_Foundation = new WikimediaSisterProject("wikimedia","foundation","Wikimedia_Foundation");
    
    public static final WikimediaSisterProject Wikimedia_Commons = new WikimediaSisterProject("commons:","",    "Wikimedia_Commons");
    public static final WikimediaSisterProject Wikimedia_Meta_Wiki = new WikimediaSisterProject("meta", "m",    "Wikimedia_Meta-Wiki");
    public static final WikimediaSisterProject Wikimedia_Incubator = new WikimediaSisterProject("incubator","", "Wikimedia_Incubator");
    public static final WikimediaSisterProject MediaWiki    = new WikimediaSisterProject("",            "mw",   "MediaWiki");
    public static final WikimediaSisterProject MediaZilla   = new WikimediaSisterProject("mediazilla",  "",     "MediaZilla");
    
    
    private WikimediaSisterProject(String long_form,String shortcut,String name) { 
        this.long_form = long_form;
        this.shortcut  = shortcut;
        this.name = name; 
        
        if(long_form.length() > 0) {
            code2project.put(long_form, this);
        }
        if(shortcut.length() > 0) {
            code2project.put(shortcut, this);
        }
    }
    
    /** Checks weather exists the project with long form or shortcut 'code'. */
    public static boolean existsCode(String code) {
        return code2project.containsKey(code);
    }
    
    public String toString() { return long_form; }
    
    /** Returns true if the language has this 'long_form'. */
    public boolean equals(String long_form) {
        return long_form.equalsIgnoreCase(this.long_form);
    }
    
    /** Gets WM sister project by name of long form */
    public static WikimediaSisterProject get(String code) throws NullPointerException
    {   
        if( code2project.containsKey(code) ) {
            return code2project.get(code);
        /*
        if(long_form.equalsIgnoreCase( ru.toString())) {
            return ru;
        } else if(long_form.equalsIgnoreCase( en.toString())) {
            return en;
        } else if(long_form.equalsIgnoreCase( simple.toString())) {
            return simple;
        } else if(long_form.equalsIgnoreCase( de.toString())) {
            return de;
            
            // todo 
            // ...
          */  
        } else {
            throw new NullPointerException("Null WikimediaSisterProject");
        }
    }

    
    /** Gets texts of interwikimedia link. E.g. 
     * [[wikt:Wiktionary:Statistics#Detail|statistics]]  -> "statistics"
     * 
     * @param text_inside_link  e.g. "wikipedia:" in [[wikipedia:]]
     */
    public static String getLinkText(String text_inside_link) {
        
        String before, after;
                
        if(-1 != text_inside_link.indexOf(':')) {
            before = StringUtil.getTextBeforeFirstColumn(text_inside_link);
            after  = StringUtil.getTextAfterFirstColumn (text_inside_link);
            return getLinkText(before, after);
        } 
        
        return text_inside_link;
    }
    
    /** Gets texts of interwikimedia link. E.g. 
     * [[wikt:Wiktionary:Statistics#Detail|statistics]]  -> "statistics"
     * 
     * @param before the text before the first column, e.g. "wikipedia" in [[wikipedia:]]
     * @param after e.g. "Wikipedia:Wikimedia_sister_projects" in [[w:Wikipedia:Wikimedia_sister_projects]]
     */
    public static String getLinkText(String before,String after) {
        
        //  0    e.g. [[:Image:Wiktionary-logo-gl.png|a logo that depicts a dictionary]]
        if( 0 == before.length() 
         || existsCode(before) ) 
        {
            if(-1 != after.indexOf('|')) {
                return StringUtil.getTextAfterFirstVerticalPipe(after);
                
            } else if(-1 != after.indexOf(':')) {
                // e.g. [[:de:Hauptseite]], after = "de:Hauptseite"
                String lang_code = StringUtil.getTextBeforeFirstColumn(after);
                
                if(LanguageType.has(lang_code)) {
                    return StringUtil.getTextAfterFirstColumn(after);
                }
            
                return after;
            }
        }
        
        return new StringBuffer(before).append(":").append(after).toString();
    }
}
