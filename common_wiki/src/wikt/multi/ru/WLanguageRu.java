/* WLanguageRu.java - corresponds to a language level of Russian Wiktionary word.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.ru;

import wikipedia.language.LanguageType;

import wikt.util.LangText;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** Language lets you know the language of the word in question. It is almost 
 * always in a level two heading. ==English==, {{-ru-}}, {{заголовок|ru|..},
 * or {{-de-|schwalbe}}.
 * 
 * See http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
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
    
    /** start of the language block, e.g. {{-ru-}}, {{-en-}}, {{-de-}}, etc. */
    private final static Pattern ptrn_lang = Pattern.compile(
            //"\\{\\{-([-_a-zA-Z]{2,9})-\\}\\}|\\Q{{заголовок|\\E([-_a-zA-Z]{2,9})(?:\\}\\}|\\|add=.*?\\}\\})");
              "\\{\\{-([-_a-zA-Z]{2,9})-(?:\\}\\}|\\|.*?\\}\\})|\\Q{{заголовок|\\E([-_a-zA-Z]{2,9})(?:\\}\\}|\\|add=.*?\\}\\})");
                // {{-en-}}
                // {{заголовок|ka|add=}}
                // {{заголовок|ka}}
                // {{-de-|schwalbe}}
                //
                // vim: {{-\([-_a-zA-Z]\{2,9\}\)-[|}][^}]*}}\?\|{{заголовок|\([-_a-zA-Z]\{2,9\}\)[|}][^}]*}}\?
                //part1:{{-\([-_a-zA-Z]\{2,9\}\)-[|}][^}]*}}\?              part2: {{заголовок|\([-_a-zA-Z]\{2,9\}\)[|}][^}]*}}\?
                //java: \\{\\{-([-_a-zA-Z]{2,9})-(?:\\}\\}|\\|.*?\\}\\})    java: \\Q{{заголовок|\\E([-_a-zA-Z]{2,9})[|}]
    
    //private final static StringBuffer   NULL_STRINGBUFFER = new StringBuffer("");    
    private final static LangText[] NULL_LANG_TEXT_ARRAY = new LangText[0];
    //private final static List<LangText> NULL_LANG_TEXT_LIST = new ArrayList<LangText>(0);
    
    /** page_title - word which are described in this article 'text'*/
    public static LangText[] splitToLanguageSections (
            String page_title,
            StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_LANG_TEXT_ARRAY;
        }
        
        List<LangText> lang_sections = new ArrayList<LangText>();     // result will be stored to
        
        Matcher m = ptrn_lang.matcher(text.toString());
        
        int i = 0;
        boolean b_next = m.find();
        boolean b_at_least_one_lang = b_next; // at least one language section was recognized
        boolean b_known_lang = true;
        if(b_next) {
            String lang_code = m.group(1);
            if((null == lang_code || lang_code.length() < 2) && 2 == m.groupCount()) {
                lang_code = m.group(2);
            }
            if (!LanguageType.has(lang_code)) {  // i.e. skip the whole article if the first lang code is unknown
                System.out.println("Warning: unknown language code '" + lang_code + "' for the word '" + page_title + "' in WLanguageRu.splitToLanguageSections()");
                b_known_lang = false;
            } else {
                LangText lt = new LangText(LanguageType.get(lang_code));

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
                    lang_code = m.group(1);
                    b_known_lang = LanguageType.has(lang_code);
                    b_next = m.find();
                    if (!b_known_lang) {
                        System.out.println("Warning: unknown language code '" + lang_code + "' for the word '" + page_title + "' in WLanguageRu.splitToLanguageSections()");
                    } else {
                        lt = new LangText(LanguageType.get(lang_code));
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
        return (LangText[])lang_sections.toArray(NULL_LANG_TEXT_ARRAY);
    }

}
