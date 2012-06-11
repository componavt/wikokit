/* WLanguageRu.java - corresponds to a language level of Russian Wiktionary word.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.multi.ru;

import wikokit.base.wikipedia.language.LanguageType;

import wikokit.base.wikt.util.LangText;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** Language lets you know the language of the word in question. It is almost 
 * always in a level two heading. ==English==, {{-ru-}}, {{заголовок|ru|..},
 * or {{-de-|schwalbe}}.
 * 
 * @see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 * and http://ru.wiktionary.org/wiki/Викисловарь:Правила оформления статей
 * 
 * http://ru.wiktionary.org/wiki/Категория:Викисловарь:Шаблоны:Языки
 * http://en.wiktionary.org/wiki/Category:Language_templates
 *
 * todo: create LanguageTypeRu with list of all languages in Russian
 * ...
 */
public class WLanguageRu {
    
    // lang code    length
    // lang-chu-ru  not used now!
    // slovio-la    9 maximum length
    // slovio-c     8
    // zh-tw        5
    // ain          3
    // en           2

    private final static Pattern ptrn_add = Pattern.compile(
            "\\|add=(.*?)(?:\\Z|\\|)");
          //"\\|add=(.*?)");
              

    /** start of the language block, e.g. {{-ru-}}, {{-en-}}, {{-de-}}, etc. */
    private final static Pattern ptrn_lang = Pattern.compile(
            //"\\{\\{-([-_a-zA-Z]{2,9})-\\}\\}|\\Q{{заголовок|\\E([-_a-zA-Z]{2,9})(?:\\}\\}|\\|add=\\}\\})");
            //"\\{\\{-([-_a-zA-Z]{2,9})-(?:\\}\\}|\\|.*?\\}\\})|\\Q{{заголовок|\\E([-_a-zA-Z]{2,9})(?:\\}\\}|\\|add=\\}\\})");
            //"\\{\\{-([-_a-zA-Z]{2,9})-(?:\\}\\}|\\|.*?\\}\\})|\\Q{{заголовок|\\E([-_a-zA-Z]{2,9})(?:\\}\\}|\\|add=\\}\\})");
              "(\\{\\{)-([-_a-zA-Z]{2,9})-(?:\\}\\}|\\|.*?\\}\\})|(\\Q{{заголовок|\\E)(.*?)\\}\\}");
           //  (\{\{)-([-_a-zA-Z]{2,9})-(?:\}\}|\|.*?\}\})|(\Q{{заголовок|\E)(.*?)\}\}
                // Yes, this is language delimiter:
                // {{-en-}}                 // group1={{            group2=en
                // {{-de-|schwalbe}}        // group1={{            group2=de
                // {{заголовок|ka|add=}}    // group1={{заголовок|  group2=ka|add=
                // {{заголовок|ka}}         // group1={{заголовок|  group2=ka
                //
                // {{заголовок|de|add=|aare}} // group1={{заголовок|  group2=de|add=|aare
                //
                // No, this is not a laguage, but a POS delimiter:
                // {{заголовок|sq|add=I}}   // group1={{заголовок|  group2=sq|add=I
                //
                // old:
                // vim: {{-\([-_a-zA-Z]\{2,9\}\)-[|}][^}]*}}\?\|{{заголовок|\([-_a-zA-Z]\{2,9\}\)[|}][^}]*}}\?
                //part1:{{-\([-_a-zA-Z]\{2,9\}\)-[|}][^}]*}}\?              part2: {{заголовок|\([-_a-zA-Z]\{2,9\}\)[|}][^}]*}}\?
                //java: \\{\\{-([-_a-zA-Z]{2,9})-(?:\\}\\}|\\|.*?\\}\\})    java: \\Q{{заголовок|\\E([-_a-zA-Z]{2,9})[|}]
    
    //private final static StringBuffer   NULL_STRINGBUFFER = new StringBuffer("");    
    private final static LangText[] NULL_LANG_TEXT_ARRAY = new LangText[0];
    //private final static List<LangText> NULL_LANG_TEXT_LIST = new ArrayList<LangText>(0);


    /** Gets language type (code) information from a Wiktionary article header 
     * and from the result of search by regular expression stored in a matcher m.
     */
    public static LanguageType getLanguageType(Matcher m,String page_title) {

        LanguageType lang_type = null;
        String lang_code = "";

        String group1 = m.group(1);
        String group2 = m.group(2);
        String group3 = m.group(3);
        String group4 = m.group(4);

        if(null == group1 && null == group3)
            return null;

        if(null != group1 && group1.equalsIgnoreCase("{{"))
            lang_code = group2;
        else {
            if(null != group3 && group3.equalsIgnoreCase("{{заголовок|")) {

                int pipe_index = group4.indexOf('|');
                if(-1 == pipe_index) {
                    lang_code = group4;                 // {{заголовок|ka}}
                } else {

                    String text_till_first_pipe = group4.substring(0, pipe_index);

                    if(-1 == group4.indexOf("add="))    // {{заголовок|de|aare}} exists?
                        lang_code = text_till_first_pipe;
                    else {
                        Matcher m_add = ptrn_add.matcher(group4.toString());
                        if(m_add.find() && m_add.group(1).length() == 0)
                            lang_code = text_till_first_pipe;   // {{заголовок|de|add=|aare}}
                    }                                           // {{заголовок|ka|add=}}
                }
            }
        }

        if(lang_code.length() == 0) {
            System.out.println("Warning: empty language code for the word '" + page_title + "' in WLanguageRu.getLanguageType()");
            return null;
        }

        //String lang_code = m.group(1);
        /*if((lang_code.length() < 2) && 2 == m.groupCount()) {
            lang_code = m.group(2);
        }*/
        if (!LanguageType.has(lang_code)) {  // i.e. skip the whole article if the first lang code is unknown
            System.out.println("Warning: unknown language code '" + lang_code + "' for the word '" + page_title + "' in WLanguageRu.getLanguageType()");
        } else
            lang_type = LanguageType.get(lang_code);
        
        return lang_type;
    }

    /** Splits an article text into language sections.
     *
     * @param  page_title word which are described in this article 'text'*/
    public static LangText[] splitToLanguageSections (
            String page_title,
            StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_LANG_TEXT_ARRAY;
        }
        
        List<LangText> lang_sections = new ArrayList<LangText>(); // result will be stored to
        
        Matcher m = ptrn_lang.matcher(text.toString());
        
        int i = 0;
        boolean b_next = m.find();
        boolean b_at_least_one_lang = b_next; // at least one language section was recognized
        boolean b_known_lang = true;
        if(b_next) {

            LanguageType lang_type = getLanguageType(m, page_title);
            b_known_lang = null != lang_type;

            if(b_known_lang) {
                LangText lt = new LangText(lang_type);

                m.appendReplacement(lt.text, "");   // "First {{-ru-}}" (add the text before the first lang code)
                lang_sections.add(lt);
                
                b_next = m.find();
                while(b_next) {
                    
                    if(b_known_lang) {
                        i++;                                                    // text belongs to previous lang code:
                        m.appendReplacement(lang_sections.get(i-1).text, "");   // i.e. {{-prev lang code-}} current text {{-current lang code
                    } else {                                                    
                        m.appendReplacement(new StringBuffer(), "");   // {{-unknown-}} just reset the text within the unknown lang {{-known-}}
                    }

                    lang_type = getLanguageType(m, page_title);
                    b_known_lang = null != lang_type;

                    b_next = m.find();

                    if (b_known_lang) {
                        lt = new LangText(lang_type);
                        //m.appendReplacement(lang_sections.get(i-1).text, "");   // text belongs to previous lang code:
                        lang_sections.add(lt);                                  // i.e. {{-prev lang code-}} current text {{-current lang code-}}
                        if(!b_next) {
                            m.appendTail(lang_sections.get(i).text);
                        }
                    }
                }
            }
        }
        if(0==i && b_known_lang && b_at_least_one_lang) {
            m.appendTail(lang_sections.get(i).text);
        }

        if(b_known_lang && 0 == lang_sections.size()) {
//            System.out.println("Warning: Ok. I guess that this is an article about Russian word, without language code. Word = '" + page_title + "'; in WLanguageRu.splitToLanguageSections()");
            LangText lt = new LangText(LanguageType.ru);
            lt.text = text;
            lang_sections.add(lt);
        }

        return (LangText[])lang_sections.toArray(NULL_LANG_TEXT_ARRAY);
    }

}
