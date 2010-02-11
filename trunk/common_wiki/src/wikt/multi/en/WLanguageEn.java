/* WLanguageRu.java - corresponds to a language level of Russian Wiktionary word.
 *
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.en;

import wikipedia.language.LanguageType;

import wikt.util.LangText;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** Language lets you know the language of the word in question. It is almost
 * always in a level two heading. ==English==, or {{-de-|schwalbe}}.
 *
 * @see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 * @see http://en.wiktionary.org/wiki/Wiktionary:Language_considerations
 * @see http://en.wiktionary.org/wiki/Wiktionary:Language names
 * 
 * @see http://en.wiktionary.org/wiki/Category:Language_templates
 */
public class WLanguageEn {

    /** ==English== or ==Russian==, etc.
     */
    private final static Pattern ptrn_2nd_level = Pattern.compile(  // Vim: ^==\s*\([^=]\+\)\s*==\s*\Z
            //"(?m)^\\s*==");
            "(?m)^==\\s*([^=]+?)\\s*==\\s*");
            // "\\A==\\s*([^=]+)\\s*==\\s*\\Z");

    private final static LangText[] NULL_LANG_TEXT_ARRAY = new LangText[0];
    
    
    /** Gets language type (code) information from a Wiktionary article header
     * and from the result of search by regular expression stored in a matcher m.
     */
    public static LanguageType getLanguageType(Matcher m,String page_title) {

        LanguageType lang_type = null;
        
        String english_lang_name = m.group(1);
        
        if(null == english_lang_name)
            return null;

        int len = english_lang_name.length();
        if( len > 4 && english_lang_name.charAt(0) == '[')  // e.g. ==[[Ewe]]==
            lang_type = LanguageType.getByEnglishName( english_lang_name.substring(2, len-2));
        else
            lang_type = LanguageType.getByEnglishName( english_lang_name ); // i.e. skip the whole article if the first lang code is unknown
        
        if (null == lang_type) {
            if(!LanguageType.hasUnknownLangName(english_lang_name)) {
                LanguageType.addUnknownLangName(english_lang_name);
                System.out.println("Warning in WLanguageEn.getLanguageType(): The article '"+
                        page_title + "' has section with unknown language: " + english_lang_name + ".");
            }
        }
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

        Matcher m = ptrn_2nd_level.matcher(text.toString());

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
            System.out.println("Warning: Ok. I guess that this is an article about English word, without language code. Word = '" + page_title + "'; in WLanguageRu.splitToLanguageSections()");
            LangText lt = new LangText(LanguageType.ru);
            lt.text = text;
            lang_sections.add(lt);
        }

        return (LangText[])lang_sections.toArray(NULL_LANG_TEXT_ARRAY);
    }

}
