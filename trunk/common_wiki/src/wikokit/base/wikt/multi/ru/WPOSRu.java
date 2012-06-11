/* WPOSRu.java - corresponds to a POS level of Russian Wiktionary word.
 * 
 * Copyright (c) 2008-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru;

import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.util.LangText;
//import wikt.constant.POSType;
import wikokit.base.wikt.constant.POS;
//import wikt.multi.ru.POSTypeRu;

import wikokit.base.wikipedia.util.StringUtilRegular;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.text.WikiParser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** Splits text to fragments related to different parts of speech (POS).
 * POS is basically a level 2 header in Russian Wiktionary, e.g. for "roast":
 * ==roast I==
 * ...
 * ==roast II==
 * 
 * (and a level 3 in English Wiktionary: ===Verb===)
 * 
 * @see http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A7%D0%B0%D1%81%D1%82%D0%B8_%D1%80%D0%B5%D1%87%D0%B8
 *      http://ru.wiktionary.org/wiki/Викисловарь:Части речи
 *
 * @see http://ru.wiktionary.org/wiki/Викисловарь:Правила оформления статей
 */
public class WPOSRu {

    private final static POSText[] NULL_POS_TEXT_ARRAY = new POSText[0];
    
    /** start of the POS block, 
     * current: ==word I==
     * old: == Существительное I ==
     * it can absent...
     */
    private final static Pattern ptrn_2nd_level = Pattern.compile(  // Vim: ^==\s*\([^=]\+\)\s*==\s*\Z
            //"(?m)^\\s*==");
            "(?m)^==\\s*([^=]+?)\\s*==\\s*");
            // "\\A==\\s*([^=]+)\\s*==\\s*\\Z");

    /** start of the POS block,
     * {{заголовок|be|add=I}}
     * it can absent...
     */
    private final static Pattern ptrn_title_add_template = Pattern.compile(
       // Vim: ^{{заголовок|\([^|]\+\)|add=[^}]\+}}\Z
            // ^{{заголовок|\([^|]\+\)|add=[^}]\+}}\Z

            //"(?m)^\\s*==");
            //"(?m)^==\\s*([^=]+?)\\s*==\\s*");
            
            //"(?m)^\\Q{{заголовок|\\E([^|]+?)\\Q|add=\\E([^}]+?)\\}\\}\\s*\\Z"); // -
            //"(?m)^\\Q{{заголовок|\\E([^|]+?)\\Q|add=\\E([^}]{2,9})\\s*\\Z"); // -
              "(?m)^\\Q{{заголовок|\\E([^|]+?)\\Q|add=\\E([^}]{1,4})\\s*"); // ?  1-4 = len(I,..,VIII,..)
            //"(?m)^\\Q{{заголовок|\\E([^|]+?)\\Q|add=\\E([^}]{2,9})"); // +
            // "\\A==\\s*([^=]+)\\s*==\\s*\\Z");

// "\\{\\{-([-_a-zA-Z]{2,9})-(?:\\}\\}|\\|.*?\\}\\})|\\Q{{заголовок|\\E([-_a-zA-Z]{2,9})(?:\\}\\}|\\|add=\\}\\})"

    /** start of the POS block,
     * {{заголовок|add=I}}
     * it can absent...
     */
    private final static Pattern ptrn_title_add_template_without_lang = Pattern.compile(
            "(?m)^\\Q{{заголовок|add=\\E([^}]{1,4})\\s*"); // ?  1-4 = len(I,..,VIII,..)

    /** Gets first two letter after ==Морфологические и синтаксические свойства==
     * e.g. "{{" or "Су"ществительное, or "Гл"агол...
     */
    private final static Pattern ptrn_morpho_then_2letters = Pattern.compile(
            "===\\s*Морфологические и синтаксические свойства\\s*===\\s*\\n\\s*(..)");
            //"===\\s*Морфологические и синтаксические свойства\\s*==="); +
            //"\\A===\\s*Морфологические и синтаксические свойства\\s*===\\s*\\n\\s*(..)");


    /** Gets true, if str is known header, e.g. "References",
     * but it's not a part of speech name, e.g. "Verb".
     */
    public static boolean isSecondLevelHeaderWordNotPOS (String str)
    {
        if(str.equalsIgnoreCase("Ссылки"))
            return true;
            
        return false;
    }




    /** page_title - word which are described in this article 'text'
     * @param lt .text will be parsed and splitted, 
     *           .lang is not using now, may be in future...
     * 
     * 1) Split the following text to "lead I" and "leat II"
     * 2) Extracts part of speech "гл" from "lead II"
     * <PRE>
     * == lead I == 
     * English text1 
     * == lead II== 
     * ===Морфологические и синтаксические свойства===" 
     * {{гл en reg|lead}}";</PRE>
     * 
     * todo isPOSHeader() (remove acce'nt -> accent) or guessPOS
     */
    public static POSText[] splitToPOSSections (
            String      page_title,
            LangText    lt)
    {
        String  pos_title = "";
        if(null == lt.text || 0 == lt.text.length()) {
            return NULL_POS_TEXT_ARRAY;
        }
        
        Matcher m = ptrn_2nd_level.matcher(lt.text.toString());
        boolean b_next = m.find();

        if(b_next && m.groupCount() > 0 && isSecondLevelHeaderWordNotPOS(m.group(1)))
            b_next = false; // it's usual header, e.g. "Links", not a == Verb I ==
        
        if(!b_next) {   // check: "{{заголовок|sq|add=I}}")
            POSText[] pp = splitToPOSWithTitleAddParameter(page_title, lt);
            if(pp.length > 0)
                return pp;

                        // there is only one ==Second level header== in this language in this word
            POSText[] pos_section_alone = new POSText[1];
            pos_section_alone[0] = guessPOS(lt.text);
            return pos_section_alone;
        }
                                                                // there are more than one POS in this language in this word
        List<POSText> pos_sections = new ArrayList<POSText>();  // result will be stored to
        StringBuffer current_pos_section = new StringBuffer();
        
        int start, end; // "<start> == Verb I == ... <end> == Verb II ==" position of POS block in the lt.text
        
        start = 0;
        pos_title = WikiParser.removeAcuteAccent(new StringBuffer(m.group(1)), LanguageType.ru).toString();
        b_next = m.find();
        if(b_next)
            end = m.start();
        else {
            end = 0;    // there is only one POS block, e.g. ==Verb I==, it is a little strange ...
            System.out.println("Warning: there is only one POS block, e.g. ==Verb I== for the word '" + page_title + "' with language code '" + lt.getLanguage().toString() + "' in WPOSRu.splitToPOSSections()");
        }
        
        while(b_next) {
            current_pos_section.append(lt.text.substring(start, end));
            
            POS p = guessPOSWith2ndLevelHeader(page_title, pos_title, current_pos_section);
            if(null != p) { // OK. It's POS header, though it's possible that p=unknown :(
                POSText pt = new POSText(p, current_pos_section.toString());
                current_pos_section.setLength(0);
                pos_sections.add(pt);
            
            } else {
                // null, if this is another 2nd level header, e.g. Bibliography or References
                current_pos_section.append(""); // +??? this Bibliography text
                // todo ...
            }
            
            // variant I:  \1==page_title+"I", "II", ... "VIII"
            // variant II: \1==Verb|Noun|... (In Russian)
            pos_title = WikiParser.removeAcuteAccent(new StringBuffer(m.group(1)), LanguageType.ru).toString();
            
            b_next = m.find();
            if(b_next) {
                start = end;
                end = m.start();
            }
        }
        
        current_pos_section.append(lt.text.substring(end)); // last POS section
        
        POS p = guessPOSWith2ndLevelHeader(page_title, pos_title, current_pos_section);
        if(null != p) { // OK. It's last POS header, though it's possible that p=unknown :(
            POSText pt = new POSText(p, current_pos_section.toString());
            current_pos_section.setLength(0);
            pos_sections.add(pt);
        }
        
        return (POSText[])pos_sections.toArray(NULL_POS_TEXT_ARRAY);
    }


    /** Checks whether the language code XX is equals to lt.lang.
     *
     * @param page_title word which are described in this article 'text'
     * @param lt .text will be parsed and splitted,
     *           .lang is not using now, may be in future...
     * @param add_lang_code language code XX in {{заголовок|XX|add=..}}
     */
    private static boolean isValidLanguageCode (
            String      page_title,
            LangText    lt,
            String      add_lang_code)
    {
        // template {{заголовок|add=II}} can be without language code
        if(add_lang_code.startsWith("add="))
            return true;

        if(null == add_lang_code
                || add_lang_code.length() < 2
                || !LanguageType.has(add_lang_code)) {
                // i.e. skip the whole block POS if the first lang code is unknown
            
            if (null == add_lang_code)
                System.out.println("Error: null language code in {{заголовок|lang_code|add=..}} for the word '" + page_title + "' in WPOSRu.splitToPOSWithTitleAddParameter()");
            else
                System.out.println("Error: unknown language code '" + add_lang_code + "' in {{заголовок|lang_code|add=..}} for the word '" + page_title + "' in WPOSRu.splitToPOSWithTitleAddParameter()");
            return false;
        }

        LanguageType add_lang_type = LanguageType.get(add_lang_code);

        if(add_lang_type != lt.getLanguage()) {
            System.out.println("Error: language code '" + add_lang_code + "' != '"+ lt.getLanguage().toString() +"' (in {{заголовок|YY|add=..}} and {{-XX-}}) for the word '" + page_title + "' in WPOSRu.splitToPOSWithTitleAddParameter()");
            return false;
        }

        return true;
    }

    /** Splits to blocks of text which describe different part of speech.
     *
     * page_title - word which are described in this article 'text'
     * @param lt .text will be parsed and splitted,
     *           .lang is not using now, may be in future...
     *
     * 1) Splits the following text to "заголовок|...|add=I" and "заголовок|...|add=II"
     * 2) Extracts part of speech (e.g. "сущ" i.e. "noun")
     * <PRE>
     * {{заголовок|be|add=I}}
     * === Морфологические и синтаксические свойства ===
     * {{сущ be m|слоги={{по-слогам|шах}}|}}
     *
     * {{заголовок|be|add=II}}
     * === Морфологические и синтаксические свойства ===
     * {{сущ be m|слоги={{по-слогам|}}|}}</PRE>
     */
    private static POSText[] splitToPOSWithTitleAddParameter (
            String      page_title,
            LangText    lt)
    {
        Matcher m;
        String lt_text = lt.text.toString();
        boolean lang_code_presented;

        if(lt_text.contains("{{заголовок|add=")) {
            m = ptrn_title_add_template_without_lang.matcher( lt_text );
            lang_code_presented = false;
        } else {
            m = ptrn_title_add_template.matcher( lt_text );
            lang_code_presented = true;
        }

        boolean b_next = m.find();

        if(!b_next) // there is no POS delimiter "{{заголовок|...|add=I}}"
            return NULL_POS_TEXT_ARRAY;

        List<POSText> pos_sections = new ArrayList<POSText>();  // result will be stored to
        StringBuffer current_pos_section = new StringBuffer();

        int start, end; // "<start> {{заголовок|...|add=I}} ...
                        //    <end> {{заголовок|...|add=II}}" position of POS block in the lt.text

        if(lang_code_presented && !isValidLanguageCode(page_title, lt, m.group(1)))
            return NULL_POS_TEXT_ARRAY;

        start = 0;
        b_next = m.find();
        if(b_next)
            end = m.start();
        else {
            end = 0;    // there is only one POS block, it is a little strange ...
            System.out.println("Warning: there is only one POS block, e.g. {{заголовок|...|add=I}} for the word '" + page_title + "' with language code  '" + lt.getLanguage().toString() + "' in WPOSRu.splitToPOSSections()");
        }

        while(b_next) {
            current_pos_section.append(lt.text.substring(start, end));

            POSText pt = guessPOS (current_pos_section);

            if(null != pt.getPOSType()) { // OK. It's POS header, though it's possible that p=unknown :(
                pos_sections.add(pt);
                current_pos_section.setLength(0);                
                
            } else {
                // null, if this is another 2nd level header, e.g. Bibliography or References
                current_pos_section.append(""); // +??? this Bibliography text
                // todo ...
            }

            // variant I:  \1==page_title+"I", "II", ... "VIII"
            // variant II: \1==Verb|Noun|... (In Russian)
            //pos_title = WikiParser.removeAcuteAccent(new StringBuffer(m.group(1)), LanguageType.ru).toString();

            b_next = m.find();
            if(b_next) {
                start = end;
                end = m.start();
            }
        }

        current_pos_section.append(lt.text.substring(end)); // last POS section

        POSText pt = guessPOS (current_pos_section);
        if(null != pt.getPOSType()) { // OK. It's last POS header, though it's possible that p=unknown :(
            current_pos_section.setLength(0);
            pos_sections.add(pt);
        }

        return (POSText[])pos_sections.toArray(NULL_POS_TEXT_ARRAY);
    }

    /** The POS should be extracted from the texts, e.g.<PRE>
     * noun:
     * ===Морфологические и синтаксические свойства===
     * {{сущ en|слоги=lead|lead|leads}}
     * 
     * verb:
     * ===Морфологические и синтаксические свойства===
     * {{гл ru 4b-ся
     * {{гл ru 8b/b^
     * {{гл ru 5c'^-т
     * 
     * adjective:
     * ===Морфологические и синтаксические свойства===
     * {{прил ru 1*a
     * 
     * adverb:
     * ===Морфологические и синтаксические свойства===
     * 
     * {{adv ru|слоги={{по-слогам|ра|но|ва́|то}}|или=предикатив|или-кат=предикативы|}}
     * 
     * {{adv-ru|
     * Наречие, неизменяемое.
     * 
     * Old formatting 
     * 
     *  ===Морфологические и синтаксические свойства===
     *  {{СущМужНеодуш1c(1)
     *  {{СущЖенНеодуш8a
     *  Существительное, ...
     * 
     * {{прил ia}}
     * 
     * {{парадигма-рус  // old formatting (>500, < 1000 pages)
     * |шаблон=Гл11b/c
     * {{Гл1a</PRE>
     */
    public static POSText guessPOS (StringBuffer text)
    {
        POS p_type = POS.unknown;
        
        if(null == text || 0 == text.length()) {
            return new POSText(p_type, "");
        }
        
        Matcher m = ptrn_morpho_then_2letters.matcher(text.toString());
        boolean b = m.find();
        if(b) {
            String two_letters = m.group(1);
            
            if(two_letters.equalsIgnoreCase("{{")) {
                // if \1=="{{" then get first letters till space
                // substring started after the symbol "{{"
                //String pos_name = StringUtilRegular.getLettersTillSpace(text.substring(m.end())).toLowerCase();
                String pos_name = StringUtilRegular.getLettersTillSpaceHyphenOrPipe(text.substring(m.end())).toLowerCase();
                if(POSTemplateRu.has(pos_name)) {
                    p_type = checkIfSuchPOSExist(pos_name);
                } /*else {
                    // old template of POS with hyphen, e.g. "{{adv-ru|}} instead of {{adv ru|}}, or Мс-п6b
                    pos_name = StringUtilRegular.getLettersTillHyphen(text.substring(m.end())).toLowerCase();
                    if(POSTypeRu.has(pos_name)) {
                        p_type = POSTypeRu.get(pos_name);
                    }
                }
            } else {
                // else get two_letters + the first Word
                // todo 
                // ....*/
            }
        } else {
            if(isPhrasePOS(text))
                p_type = POS.phrase;
        }
        
        return new POSText(p_type, new StringBuffer(text));
    }

    /** Checks whether the text describes a phrase. It is true if the text 
     * contains something like:
     * <PRE>
     * === Тип и синтаксические свойства сочетания ===
     * {{phrase|
     * |тип=фразема
     * }}
     * </PRE>
     * @param text
     * @return
     */
    private static boolean isPhrasePOS (StringBuffer text)
    {
        return text.toString().contains("{{phrase");
    }
    
    public static POS checkIfSuchPOSExist(String pos_name) {
    	for (int idx = 0; idx < pos_name.length(); idx++)
    		if (" |}-".indexOf(pos_name.charAt(idx)) >= 0) {
    			pos_name = pos_name.substring(0, idx);
    			break;
    		}
    	if (POSTemplateRu.has(pos_name))
    		return POSTemplateRu.get(pos_name); 
    	// POS may exist in the pos_name as a substring. isPOSin() - checks wheather POS is in the pos_name
    	else 
    		return POSTemplateRu.isPOSIn(pos_name.toLowerCase());
    	 
    }
    
    /** The POS should be extracted from the text.
     * @return POS, e.g. POS.verb for "== Verb =="
     * @return POS.unknown if text contains "== Word I ==" or "== Word II =="
     * (without additional POS data, e.g. "verb").
     * @return else null 
     * 
     * Example of text.<PRE>
     * noun:
     * == bar II ==
     * ===Морфологические и синтаксические свойства===
     * {{сущ en|nom-sg=bar|слоги=bar}}
     * 
     * adjective:
     * == round I ==
     * ===Морфологические и синтаксические свойства===
     * {{прил en|round|слоги=round}}
     * 
     * adverb (old style for "fast"):
     * ==Наречие==
     * {{нар en|fast}}
     * 
     * adverb (very old style for DE "fast")
     * <b>fast</b>
     * Наречие
     * ==Произношение==
     * {{transcription|fɑst}}
     * ==Значение==
     * [[почти]]
     * </PRE>
     * 
     * @param page_title    word, name of the article, e.g. "lead"
     * @param pos_title     extracted 2nd level title, e.g. "lead I", "lead II", or "Adverb" (old style)
     */
    public static POS guessPOSWith2ndLevelHeader (String page_title,String pos_title, StringBuffer text)
    {
        POSText pt = guessPOS (text);
        
        if(POS.unknown != pt.getPOSType() || null == text || 0 == text.length()) {
            return pt.getPOSType();
        }
        
        // compare pos_title with POSType
        pos_title = pos_title.toLowerCase();
        if( POSTemplateRu.has(pos_title)) {
            return POSTemplateRu.get(pos_title);
        }
        
        // get first word without number, e.g. ==Verb I== -> "Verb"
        String pos_name = StringUtilRegular.getLettersTillSpace(pos_title);
        if( POSTemplateRu.has(pos_name)) {
            return POSTemplateRu.get(pos_name);
        }
        
        if(page_title.equalsIgnoreCase(pos_name)) { // It's POS because, e.g. "round I" == "round" + "I", but it's unknown POS
            return POS.unknown;
        }
        
        // takes one by one second-level-headers (first word, e.g. Noun for "Noun I", compare with POS
        /*Matcher m = ptrn_2nd_level.matcher(text.toString());
        
        while(m.find()) {   
           String s = m.group(1);   // header 2nd level
            if( POSTypeRu.has(s)) {
                return POSTypeRu.get(s);
            }
        }*/
        
        return null;
    }
        
}
