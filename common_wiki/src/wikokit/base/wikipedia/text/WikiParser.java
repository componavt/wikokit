/*
 * WikiParser.java - parses texts in wiki format.
 *
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikokit.base.wikipedia.text;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.language.WikimediaSisterProject;
import wikokit.base.wikipedia.util.StringUtil;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
//import java.util.regex.PatternSyntaxException;

/** Converts wiki-texts to texts without [[]], interwiki, <code>..</code>, etc.
 * Definitions:
 * [[...]] - wikilink,
 * [http:// site name] - hyperlink.
 */
public class WikiParser {
    
    // metacharacters: ([{\^-$|]})?*+.
            
    // "\\A\\W*(.+?)\\W*\\Z"
    private final static Pattern ptrn_remove_interwiki = Pattern.compile("\\[\\[\\w\\w\\w?:.+?\\]\\]");
    private final static Pattern ptrn_remove_brackets_in_interwiki = Pattern.compile("\\[\\[\\w\\w\\w?:(.+?)\\]\\]");
    
    private final static Pattern ptrn_remove_category_en = Pattern.compile("\\[\\[Category:.+?\\]\\]");
    private final static Pattern ptrn_remove_category_ru = Pattern.compile("\\[\\[Категория:.+?\\]\\]");
    
    private final static Pattern ptrn_tag_code     = Pattern.compile("<code>.+?</code>",    Pattern.DOTALL);
    private final static Pattern ptrn_html_comment = Pattern.compile("<!--.+?-->",          Pattern.DOTALL);
    private final static Pattern ptrn_pre_code     = Pattern.compile("<pre>.+?</pre>",      Pattern.DOTALL);
    private final static Pattern ptrn_source_code  = Pattern.compile("<source.+?</source>", Pattern.DOTALL);
    
    // 1. simple wikilink without '|' inside link, e.g. [[Tsar]] -> Tsar
    private final static Pattern ptrn_remove_brackets_in_wikilinks = Pattern.compile("\\[\\[([^:|]+?)\\]\\]");
    // 2. with '|' inside link, e.g. [[The Russian language|Russian]] -> Russian
    // [^[:|]| - not '[', ':' till first '|'
    //private final static Pattern ptrn_remove_brackets_in_wikilinks_vertical_line = Pattern.compile("\\[\\[[^[:]*?|(.+?)\\]\\]");
    //private final static Pattern ptrn_remove_brackets_in_wikilinks_vertical_line = Pattern.compile("\\[\\[[^\Q:]\E]*?|(.+?)\\]\\]");
    //private final static Pattern ptrn_remove_brackets_in_wikilinks_vertical_line = Pattern.compile("\\[\\[[^:\\]]+?|([[^\\]]+?)\\]\\]");
    
    private final static Pattern ptrn_double_brackets = Pattern.compile("\\[\\[(.+?)\\]\\]");
    private final static Pattern ptrn_single_brackets = Pattern.compile(   "\\[(.+?)\\]"   );
    //private final static Pattern ptrn_double_curly_brackets = Pattern.compile("\\{\\{(.+?)\\}\\}", Pattern.DOTALL);
    private final static Pattern ptrn_double_curly_brackets = Pattern.compile("\\{\\{([^\\{]+?)\\}\\}", Pattern.DOTALL);
    
    private final static Pattern ptrn_accent_sign       = Pattern.compile("́");
    private final static Pattern ptrn_triple_apostrophe = Pattern.compile("'''(.+?)'''");
    
    private final static Pattern ptrn_double_apostrophe = Pattern.compile("''(.+?)''");
    
    // remove [site names] in brackets
    //private final static Pattern ptrn_site_name  = Pattern.compile("\\bhttp://.+?(\\s|$)");
    //private final static Pattern ptrn_site_name  = Pattern.compile("[-/_!*'():~a-z%0-9A-Z]+?.[-/_!*'():~a-z%0-9A-Z]+");
    //private final static Pattern ptrn_site_name  = Pattern.compile("[-./_!*'():~a-z%0-9A-Z]+");
    
    /** Hostname (without spaces) contains the dot '.' at least once, except the last symbol. */
    private final static Pattern ptrn_site_name  = Pattern.compile("(\\A|\\s)\\S+?[.]\\S+?[^.]([\\s,!?]|\\z)");
    //      final static Pattern ptrn_site_name  = Pattern.compile("\\b\\S+?[.]\\S+?[^.]\\b");
    //      final static Pattern ptrn_site_name  = Pattern.compile("\\b.+?[.]+.+?[^.]\\b");
    
    private final static StringBuffer   NULL_STRINGBUFFER = new StringBuffer("");
    
    /** Creates a new instance of WikiParser */
    //public WikiParser() {    }
    
    /** Removes interwiki, e.g. "[[et:Talvepalee]] text" -> " text", 
     * where language code (e.g. 'et') can have two or three letters.
     */
    public static StringBuffer removeInterwiki(StringBuffer text) {
        Matcher m = ptrn_remove_interwiki.matcher(text.toString());
        return new StringBuffer(m.replaceAll(""));
    }
    
    /** Expands interwiki by removing interwiki brackets and language code, 
     * e.g. "[[et:Talvepalee]] text" -> "Talvepalee text".
     */
    public static StringBuffer removeBracketsInInterwiki(StringBuffer text) {
        Matcher m = ptrn_remove_brackets_in_interwiki.matcher(text.toString());
        
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        while(result) {
            m.appendReplacement(sb, "$1");
            result = m.find();
        }
        m.appendTail(sb);
        return sb;
    }
    
    /** Removes categories for selected language, 
     * e.g. English: "[[Category:Russia]] text" -> " text", 
     * or Esperanto: "[[Kategorio:Galaksioj]] text" -> " text".
     */
    public static StringBuffer removeCategory(StringBuffer text, LanguageType lang) {
        Matcher m = null;
        
        if(lang.equals("en") || lang.equals("simple")) {
            m = ptrn_remove_category_en.matcher(text.toString());
        } else if (lang.equals("ru")) {
            m = ptrn_remove_category_ru.matcher(text.toString());
        }
        return new StringBuffer(m.replaceAll(""));
    }
    
    
    /** Removes XML tag <code> with text till the next </code>. */
    public static StringBuffer removeXMLTag(StringBuffer text,String tag) {
        if(null == tag || tag.length() == 0)
            return text;
        
        Pattern p = Pattern.compile("<"+tag+">.+?</"+tag+">", Pattern.DOTALL);
        Matcher m = p.matcher(text.toString());
        
        return new StringBuffer(m.replaceAll(""));
    }
    
    /** Removes XML tag <code> with text till the next </code>.
     * e.g. "a <code>x+y</code> b" -> "a  b". */
    public static StringBuffer removeXMLTagCode(StringBuffer text) {
        Matcher m = ptrn_tag_code.matcher(text.toString());
        return new StringBuffer(m.replaceAll(""));
    }
    
    /** Removes all comments: &lt;!-- ... -->. */
    public static StringBuffer removeHTMLComments(StringBuffer text) {
        Matcher m = ptrn_html_comment.matcher(text.toString());
        return new StringBuffer(m.replaceAll(""));
    }
    
    /** Removes preformatted code (e.g. xml): &lt;pre> ... &lt;/pre>.*/
    public static StringBuffer removePreCode(StringBuffer text) {
        Matcher m = ptrn_pre_code.matcher(text.toString());
        return new StringBuffer(m.replaceAll(""));
    }
    
    /** Removes all source codes: &lt;source ... &lt;/source>.*/
    public static StringBuffer removeSourceCode(StringBuffer text) {
        Matcher m = ptrn_source_code.matcher(text.toString());
        return new StringBuffer(m.replaceAll(""));
    }
    
    
    /** Expands wiki links removing brackets. There are two cases: 
     * (1) remove brackets, e.g. [[run]] -> run and 
     * (2) (todo) [[run|running]] -> run, or [[Russian language|Russian] -> Russian, 
     * i.e. the visible (to reader) words will remain.
     * 
     * @Deprecated Use parseDoubleBrackets()
     */
    public static StringBuffer removeBracketsInWikiLink(StringBuffer text) {
        Matcher m = ptrn_remove_brackets_in_wikilinks.matcher(text.toString());
        
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        while(result) {
            m.appendReplacement(sb, "$1");
            result = m.find();
        }
        m.appendTail(sb);
        
        // (2) 
        /*m = ptrn_remove_brackets_in_wikilinks_vertical_line.matcher(sb.toString());
        StringBuffer sb2 = new StringBuffer();
        result = m.find();
        while(result) {
            m.appendReplacement(sb2, "$1");
            result = m.find();
        }
        m.appendTail(sb2);
        
        return sb2;*/
        return sb;
    }
    
    /** Expands / removes hyperlinks. Expands hyperlinks with text, e.g. 
     * "[http:site name of site]" -> "name of site". 
     * Removes links without text, e.g. [www.site].
     */
    public static StringBuffer parseSingleBrackets(StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        Matcher m = ptrn_single_brackets.matcher(text.toString()); // [(.+?)]
        StringBuffer sb = new StringBuffer();
        
        boolean result = m.find();
        while(result) {
            // g: text within [single brackets]
            String g = StringUtil.escapeCharDollarAndBackslash(m.group(1)).toString();
            
            if(g.contains(" ")) {
                g = StringUtil.getTextAfterFirstSpace(g);
                
                Matcher m_site = ptrn_site_name.matcher(g);
                g = m_site.replaceAll(" ").trim();  // remove rightmost [  site.names.com] in brackets
                
                if(g.length() > 0) {
                    m.appendReplacement(sb," ");    // *[http://www.site.com text] -> * SPACE text
                    sb.append(g);
                } else {
                    m.appendReplacement(sb,"");
                }
            } else {
                m.appendReplacement(sb, "");
            }
            result = m.find();
        }
        m.appendTail(sb);
        
        return sb;
    }
            
    /** Removes and expands interwiki, categories, and wiki links in wiki texts.<br>
     * 
     * 1. expands links to Wikimedia sister projects, 
     * see [[w:Wikipedia:Interwikimedia_links|text to expand]] -> "text to expand"
     * 
     * 2. interwiki
     * @param b_remove_not_expand_iwiki if true then 
     * Removes interwiki, e.g. "[[et:Talvepalee]] text" -> " text";<br>
     *                                  if false then
     * expands interwiki by removing interwiki brackets and language code,
     * e.g. "[[et:Talvepalee]] text" -> "Talvepalee text".
     *
     * @param  lang defines parsed wiki language, it is needed to remove 
     * category for the selected language, e.g. English (Category) or Esperanto 
     * (Kategorio).<br><br>
     *
     * 3. Removes categories for selected language, 
     * e.g. English: "[[Category:Russia]] text" -> " text".<br><br>
     *
     * 4. Expands wiki links by removing brackets. There are two cases: 
     * (1) remove brackets, e.g. [[run]] -> run and 
     * (2) [[run|running]] -> running, or [[Russian language|Russian]] -> Russian, 
     * i.e. the visible (to reader) words will remain.<br><br>
     * 
     * It is recommended to call StringUtil.escapeCharDollarAndBackslash(text) 
     * before this function.
     *
     * See also WikiWord.parseDoubleBrackets
     */
    public static StringBuffer parseDoubleBrackets(
            StringBuffer text,
            LanguageType lang,
            boolean b_remove_not_expand_iwiki)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        
        Matcher m = ptrn_double_brackets.matcher(text.toString()); // [[(.+?)]]
                //StringUtil.escapeCharDollarAndBackslash(text.toString())); // [[(.+?)]]
        
        String before, after;
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        while(result) {
            // g: text within [[brackets]]
            String g = StringUtil.escapeCharDollarAndBackslash(m.group(1)).toString();
            if(-1 != g.indexOf(':')) {
                
                before = StringUtil.getTextBeforeFirstColumn(g);
                after  = StringUtil.getTextAfterFirstColumn(g);
                
                    // categories 
                if( ((lang.equals("en") || lang.equals("simple")) && before.equalsIgnoreCase("Category")) || 
                    (lang.equals("ru") && before.equalsIgnoreCase("Категория"))
                  )
                {   // remove [[Category:Title]]
                    m.appendReplacement(sb, ""); 
                    
                } else if (WikimediaSisterProject.existsCode(before)) 
                {
                    m.appendReplacement(sb, WikimediaSisterProject.getLinkText(before, after));
                    
                } else {
                    // interwiki
                    if(LanguageType.has(before)) {
                        if(b_remove_not_expand_iwiki) {
                            m.appendReplacement(sb, "");
                        } else {
                            m.appendReplacement(sb, after);
                }   }   }
            } else {
                if(-1 != g.indexOf('|')) {
                    before = StringUtil.getTextBeforeFirstVerticalPipe(g);
                    after  = StringUtil.getTextAfterFirstVerticalPipe(g);
                    //System.out.println("sb="+sb+ "; after="+after);
                    m.appendReplacement(sb, after);
                } else {
                    // [[run]] -> run
                    m.appendReplacement(sb, g);
                }
            }
                        
            //m.appendReplacement(sb, "$1");
            result = m.find();
        }
        m.appendTail(sb);
        
        return sb;
    }

    
    /** Removes texts withing curly brackets, e.g. {{templates}}.<br><br>
     * 
     * Todo: expand templates (optionally).
     */
    public static StringBuffer parseCurlyBrackets(StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        
        Matcher m = ptrn_double_curly_brackets.matcher(text.toString()); // {{(.+?)}}
        boolean result = m.find();
        if(result) {
            StringBuffer sb = new StringBuffer();
            while(result) {
                //String g = m.group(1); // texts within {{curly brackets}}
                m.appendReplacement(sb, "");
                result = m.find();
            }
            m.appendTail(sb);

            return sb;
        }
        
        return text;
    }
    
    
    /** Removes boundaries of something (e.g. double or triple apostrophes) 
     * used in pairs, e.g. ''italics'' -> italics. .<br><br>
     * 
     * It is recommended to call StringUtil.escapeCharDollarAndBackslash(text) 
     * before this function.
     */
    private static StringBuffer parseBounds(StringBuffer text, Pattern p)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        //Matcher m = p.matcher(StringUtil.escapeCharDollarAndBackslash(text.toString()));
        Matcher m = p.matcher(text.toString());
        
        boolean result = m.find();
        if(result) {
            StringBuffer sb = new StringBuffer();
            while(result) {
                // g1: text within ''some boundaries''
                String g1 = StringUtil.escapeCharDollarAndBackslash(m.group(1)).toString();
                m.appendReplacement(sb, g1);
                result = m.find();
            }
            m.appendTail(sb);
            return sb;
        }
        
        return text;
    }
    
    /** Removes douple apostrophes used in pairs, e.g. ''italics'' -> italics.
     * It is recommended to call StringUtil.escapeCharDollarAndBackslash(text) 
     * before this function.
     */
    public static StringBuffer parseDoubleApostrophe(StringBuffer text)
    {
        return parseBounds(text, ptrn_double_apostrophe);
    }
    
    /** Removes triple apostrophes used in pairs, e.g. '''bold''' -> bold.
     * It is recommended to call StringUtil.escapeCharDollarAndBackslash(text) 
     * before this function.
     */
    public static StringBuffer parseTripleApostrophe(StringBuffer text)
    {
        return parseBounds(text, ptrn_triple_apostrophe);
    }
    
        
    /** Removes sign of acute accent "'" for Russian wiki texts, 
     * it is placed in the begin of article often e.g. '''itálics''' -> '''italics'''.
     */
    public static StringBuffer removeAcuteAccent(
            StringBuffer text,LanguageType wiki_lang)
    {
        if( wiki_lang != LanguageType.ru) // skip English Wiki
            return text;
        
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        
        Matcher m = ptrn_accent_sign.matcher(text.toString());
        return new StringBuffer (m.replaceAll(""));
    }
    
    
    /** Removes / expands interwiki, removes categories, expands wiki links.
     * 
     * @param b_remove_not_expand_iwiki if true then removes interwiki, 
     * e.g. "[[et:Talvepalee]] text" -> " text"; else expands interwiki by 
     * removing interwiki brackets and language code, 
     * e.g. "[[et:Talvepalee]] text" -> "Talvepalee text".
     *
     * @param  lang defines parsed wiki language, it is needed to remove 
     * category for the selected language, e.g. English (Category) or Esperanto 
     * (Kategorio).<br><br>
     *
     * 2. Removes categories for selected language, 
     * e.g. English: "[[Category:Russia]] text" -> " text".<br><br>
     *
     * 3. Expands wiki links by removing brackets. There are two cases: 
     * (1) remove brackets, e.g. [[run]] -> run and <br> 
     * (2) [[run|running]] -> running, or [[Russian language|Russian]] -> Russian, 
     * i.e. the visible (to reader) words will remain.
     */
    public static StringBuffer convertWikiToText(
            StringBuffer wiki_text,
            LanguageType lang, 
            boolean b_remove_not_expand_iwiki)
    {
        // StringBuffer result = WikiParser.removeInterwiki(wiki_text);
        // or
        // StringBuffer result = WikiParser.removeBracketsInInterwiki(wiki_text);
        
        // StringBuffer result = WikiParser.removeCategory(wiki_text, LanguageType.ru);
        // or
        // StringBuffer result = WikiParser.removeCategory(wiki_text, LanguageType.en);
        
        if(null == wiki_text || 0 == wiki_text.length()) {
            return NULL_STRINGBUFFER;
        }
        //StringBuffer wiki_text_trim = new StringBuffer(wiki_text.toString().trim());
        
        // I. removing
        
        StringBuffer s = removeHTMLComments(wiki_text);
        s = removePreCode(s);
        s = removeSourceCode(s);
        
        //s = StringUtil.escapeCharDollarAndBackslash(s.toString());
        
        s = removeXMLTagCode(s);
        
        // II. transformation and removing
        
        s = ReferenceParser.expandMoveToEndOfText(s);
        
        s = parseCurlyBrackets(s);
        s = parseCurlyBrackets(s); // {{template in {{template}}}}
        s = TableParser.removeWikiTables(s); // after CurlyBrackets, remarks in func
        
        s = removeAcuteAccent(s, lang);
        s = parseTripleApostrophe(s);
        s = parseDoubleApostrophe(s);
        
        s = ImageParser.parseImageDescription(s, lang);
        s = parseDoubleBrackets(s, lang, b_remove_not_expand_iwiki);
        String str = WikiParser.parseSingleBrackets(s).toString().trim();
        
        //str = XMLTagsParser.escapeCharFromXML(str);      // for GATE XML parsers
        str = XMLTagsParser.replaceCharFromXML(str, ' ');  // for GATE XML parsers
        
        return new StringBuffer(str);
    }
}
