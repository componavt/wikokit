/* WMeaningEn.java - corresponds to a Meaning (definition + quotations)
 * level of a word in English Wiktionary.
 *
 * Copyright (c) 2010-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.en;

import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.word.WMeaning;
import wikokit.base.wikt.word.WQuote;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.util.Definition;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** Meaning consists of <PRE>
 * # Definition (preceded by "#", which causes automatic numbering).
 * and Quotations.      </PRE>
 */
public class WMeaningEn {

    private final static WMeaning[] NULL_WMEANING_ARRAY = new WMeaning[0];
    private final static Label[] NULL_CONTEXTLABEL_ARRAY = new Label[0];

    /** Gets position before first header ^===, e.g. */
    private final static Pattern ptrn_meaning_header_start = Pattern.compile(
            "(?m)^={3,5}");  //"\n===");    //"(?m)^===");

    /** Gets position before first meaning started by "#"
        // # Meaning 1
        // #* Quotations
     */
    private final static Pattern ptrn_first_meaning = Pattern.compile(
            "(?m)^#");

    /** splits meaning with quotations by "\n#" and not by "\n#*"
        // # Meaning 1
        // #* '''Year''', Author, ''Source title'', Publisher, pages #–#:
        // #*: First quotation of '''word'''.
     */
    private final static Pattern ptrn_meaning_with_quot = Pattern.compile(
            "(?m)^#(?![*:])");
            //"(?m)^#(?!\\*)");

    /** Parses text (related to the POS), creates and fill array of meanings (WMeaning).
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param pt            POSText defines POS stored in pt.text
     * @return
     */
    public static WMeaning[] parse (
                    String page_title,
                    LanguageType lang_section,
                    POSText pt)
    {
        // === Level III or IV. Meaning (definition) ===
        if(null == pt.getText())
            return NULL_WMEANING_ARRAY;

        StringBuffer text = pt.getText();
        if(0 == text.length())
            return NULL_WMEANING_ARRAY;

        // 1. gets position in text before first ^=== (e.g. ====Synonyms====)
        //Matcher m = ptrn_meaning_header_start.matcher(text.toString());
        
        int pos_end_meanings; // position of end of definitions and examples
        if(-1 == (pos_end_meanings = text.toString().indexOf("\n===")))  //m.find())
            pos_end_meanings = text.length();
            
        // gets position of first definition (first "#")
        //Matcher m = ptrn_first_meaning.matcher(text.toString());

        boolean b_exist_definition = true;
        int pos_start_meanings;

        if(text.toString().charAt(0) == '#')
            pos_start_meanings = 0;
        else {
            if(-1 == (pos_start_meanings = text.toString().indexOf("\n#")))
                b_exist_definition = false;
        }

        /*if(m.find())
            pos_start_meanings = m.start();
        else
            b_exist_definition = false; // pos_start_meanings = 0;
        */
        String defs_text = ""; // text with definitions and examples

        if(b_exist_definition && pos_start_meanings < pos_end_meanings)
            defs_text = text.substring( pos_start_meanings, pos_end_meanings ).trim();
        else
            b_exist_definition = false;

        if(!b_exist_definition || defs_text.length() == 0) {   // there is no definition section!
            // more intelectual definition of empty definition
            // todo
            // e.g. # ...
            System.out.println("Warning in WMeaningEn.parse(): The article '" +
                        page_title + "', '" +
                        lang_section.toString() + "' language section has no # Definition.");
            return NULL_WMEANING_ARRAY;
        }

        // split by "\n#"
        // # Meaning 1
        // #* Quotations
        // ptrn_meaning_with_quot

        String[] meaning_with_quat =
            ptrn_meaning_with_quot.split(defs_text);

        LanguageType wikt_lang = LanguageType.en;
        List<WMeaning> wm_list = null;
        for(int i=1; i<meaning_with_quat.length; i++) { // [0] == "";
            String mean_lines = meaning_with_quat[i];

            // extracts \n# {{label}} Definition. \n#::? Example sentence.
            // return WMeaning
            // return null if this line is not started from "#" or = "# "
            WMeaning wm = WMeaning.parseOneDefinition(
                                wikt_lang, page_title, lang_section, mean_lines);
            if(null != wm) {
                if(null == wm_list)
                    wm_list = new ArrayList<WMeaning>();
                wm_list.add(wm);
            }
        }
        
        if(null == wm_list)
            return NULL_WMEANING_ARRAY;

        return( (WMeaning[])wm_list.toArray(NULL_WMEANING_ARRAY) );
    }


    private static final String[] STR_FORM_OF = new String[] {
       "alternative form of",
       "alternative name of",
       "alternative plural of",

       "comparative of",
       "conjugation of",       

       "feminine of",
       "feminine past participle of",
       "feminine plural past participle of",

        "form of",

       "genitive of",
       "gerund of",

       "inflection of",

       "alternative capitalization of",
       "alternative spelling of",
       "misspelling of",
       "nonstandard spelling of",
       "obsolete spelling of",
            
       "plural of",
       "feminine plural of",
       "masculine plural of",

       "past participle of",
       "plural past participle of",
       "present participle of",

       "superlative of",

       "third-person singular of",            
    };
    private final static Set<String> FORM_OF = new HashSet<String>(Arrays.asList(STR_FORM_OF));
    
    /** Checks: whether the definition "text" is one of "form of" templates,
     * e.g.<br><br>
     * "{{comparative of|}}
     
     * @param line  one line definition (without \n newline symbols)
     * @return      true, if the "line" is a "form of" template
     *
     * @see http://en.wiktionary.org/wiki/Category:Form_of_templates
     */
    public static boolean isFormOfTemplate(String line)
    {
        // + counter - number of omitted words with templates like "form of"

        // 1. simple case: the whole definition is a template:
        // e.g.: {{comparative of|bla-bla-bla}}
        int pipe_pos;
        if(line.startsWith("{{") && line.endsWith("}}") &&
                -1 != (pipe_pos = line.indexOf("|", 3)) && pipe_pos > 3)
        {
            String template_name = line.substring(2, pipe_pos);
            //System.out.println("template_name is '" + template_name + '\'');
            
            if(FORM_OF.contains(template_name.trim()))
                return true;
            
            if(   template_name.contains("form of")
               || template_name.contains("adj-form")
               || template_name.contains("noun-form")
               || template_name.contains("participle of")
               || template_name.contains("verb form")
               || template_name.contains("verb-form")     
              )
                return true;
            
            //System.out.println("STR_FORM_OF.len="+STR_FORM_OF.length);
            //System.out.println("set FORM_OF.len="+FORM_OF.size());
        }
        return false;
    }
    //"adj-form"
    // sv-adj-form-abs-def-m
    // sv-adj-form-abs-def+pl
    // sv-adj-form-abs-indef-n

    // "form of"
    // eo-form of
    // fi-form of

    // "verb form"
    // ca-verb form of
    // de-verb form of
    // es-verb form of
    // fi-verb form of

    // "verb-form"
    // nl-verb-form
    // pt-verb form of
    // sv-verb-form-pre
    // sv-verb-form-sup-pass
    // sv-verb-form-inf-pass

    // "noun-form"
    // nl-noun-form
    // sv-noun-form-def
    // sv-noun-form-indef-pl
    // sv-noun-form-def-gen
    // sv-noun-form-def-pl
    
    // participle of
    // fi-participle of



    /** Checks: whether the definition ("# $line") contains one of "form of"
     * (i.e. templates or explicit description of the word form), e.g.<br><br>
     * "Plural form of xilologico" or
     * "{{comparative of|" or<br><br>
     * "# {{plural past participle of|}}"
     *
     * @param line          one line definition (without \n newline symbols)
     * @see http://en.wiktionary.org/wiki/Category:Form_of_templates
     */
    public static boolean containsFormOfTemplate(String line)
    {
        if(!line.startsWith("{{") || !line.endsWith("}}"))
            return false;
        
        // 1. simple case: the whole definition is a template:
        // e.g.: {{comparative of|bla-bla-bla}}
        if (isFormOfTemplate(line))
            return true;
        
        // 2. complex case: there are several templates, where one is a "form of" template, e.g.:
        // "{{obsolete}} {{past participle of|sit}} An alternate form of sat.";
        // "{{transitive}} {{obsolete spelling of|[[abraid]]}}"
        
        // 2.a let's skip "{{first template}} {{":
        
        int second_template_pos;                        // 5 = min len of "{{bla-bla-bla}}" before {{..}}
        if(-1 != (second_template_pos = line.indexOf("{{", 5)))
            if (isFormOfTemplate(line.substring(second_template_pos)))
                return true;
        
        return false;
    }

    /** Checks: whether the definition ("# $line") contains one of "form of"
     * (i.e. templates or explicit description of the word form), e.g.<br><br>
     * "Plural form of xilologico" or
     * "{{comparative of|" or<br><br>
     * "# {{plural past participle of|}}"
     *
     * @param line          one line definition (without \n newline symbols)
     */
    public static boolean containsFormOf(String line)
    {
        // + counter - number of omitted words, i.e. number of word forms (non lemma)

        if (containsFormOfTemplate(line))
            return true;

        if(        line.startsWith("Plural form of")
                || line.startsWith("Feminine plural form of")
           )
            return true;

        return false;
    }

    /** Parses (usually) two lines: definition line and quotation line,
     * i.e. extracts {{label}}, # definition, 
     * and #: Quotation sentence. with #:: Translation sentence.
     * , creates and fills a meaning (WMeaning).
     * 
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in the definition 'text'
     * @param lang_section  language of this section of an article
     * @param text          text with one definition 
     * @return WMeaning or null if the line is not started from "#" or = "# "
     */
    public static WMeaning parseOneDefinition(
                    String page_title,
                    LanguageType lang_section,
                    String text)
    {
        // remove empty quotations: {{пример|}} and {{пример}}
 /*       line = line.replace("{{пример|}}", "");
        line = line.replace("{{пример}}", "");
        line = line.replace("{{пример перевод|}}", ""); // todo check - does exist this example in enwikt
*/
        String line;
        line = Definition.getFirstLine(page_title, text);
        line = Definition.stripNumberSign(page_title, line).trim();

        if(0 == line.length())
            return null;

        boolean form_of = false;
        if( containsFormOf(line))
        {
            form_of = true;
            return new WMeaning("", NULL_CONTEXTLABEL_ARRAY, "", null, form_of);
        }
        

        //if(line.startsWith("{{морфема"))
        //    return null;    // skip now, todo (parse) in future

        // 1. extract labels
        // todo
        // ...
        Label[] labels = new Label[0];

        // extract definition by parsing wiki-text

        // 2. extract text till first {{пример|
        String wiki_definition = line; //WQuoteRu.getDefinitionBeforeFirstQuote(page_title, line);

        // 3. parsing wiki-text
        //StringBuffer definition = WikiWord.parseDoubleBrackets(page_title, new StringBuffer(wiki_definition));
        // 4. extract wiki-links (internal links)
        //WikiWord[] ww = WikiWord.getWikiWords(page_title, new StringBuffer(wiki_definition));

        // 5. extract quotations
        WQuote[] quote = null;
        // todo ...

        return new WMeaning(page_title, labels, wiki_definition, quote, form_of);
    }

}
