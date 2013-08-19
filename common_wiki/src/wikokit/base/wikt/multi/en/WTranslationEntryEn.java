/* WTranslationEntryEn.java - corresponds to a line in Translations of a word
 *                              in English Wiktionary.
 *
 * Copyright (c) 2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.multi.en;

import wikokit.base.wikt.word.WTranslationEntry;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.util.WikiText;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** One line in the Translation section, i.e. a translation to one language,
 * e.g. "* Latvian: {{t|lt|oranžs}}".
 */
public class WTranslationEntryEn {

    /** Chop the beginning of the line: "*: " or "* ", e.g.:
     * "* German: {{t|de|Orange|f}}" ->
     *   "German: {{t|de|Orange|f}}"
     */
    private final static Pattern ptrn_begin_asterisk = Pattern.compile(
            "^(\\*\\:?\\s*)");

    /** Wikified language name: "[[Alabama]]" -> "Alabama". */
    private final static Pattern ptrn_wikified_lang_name = Pattern.compile(
            "^\\[\\[(.+?)\\]\\]\\s*");


    /** Extract from text {{t,t+,t-,trad etc.|...}}: */
    private final static Pattern ptrn_t_template = Pattern.compile(
            "\\{\\{(t[^}]*?)\\}\\}"); // RE: (\Q{{t\E[^}]*?\}\})


    /** Structure for storing identified language and translation words. */
    private static class LangAndTrans { // source: "* French: {{t|fr|orange|f}}"
        LanguageType lang;              // lang = fr, it could be null
        String trans;                   // remain = {{t|fr|orange|f}}
    }

    /** Parses one entry (one line) of a translation box,
     * extracts a language and a list of translations
     * from template (wikified words) for this language,
     * creates and fills WTranslationEntry.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param text          translaton box text
     * @return WTranslationEntry or null if the translation language or translation text are absent.
     *
     * @see http://en.wiktionary.org/wiki/Template_talk:t#Documentation
     */
    public static WTranslationEntry parse(
                    String page_title,
                    String text)
    {
        // split "*[[Datiwuy]]: {{t|duj|buurnba}}" into "Datiwuy" and remain
        //       "* French: {{t|fr|orange|f}}" into "French" and remain.

        LangAndTrans lang_trans = splitToLanguageAndTranslations(text);
        if(null == lang_trans)
            return null;

        LanguageType prev_lang, lang = null;
        List<String> translations = new ArrayList<String>();
        
        // extract from text {{t,t+,t-,trad etc.|...}}:
        Matcher m = ptrn_t_template.matcher(lang_trans.trans);
        while(m.find()) {
            String t_template = m.group(1);
            String[] t_params = t_template.split("\\|");

            // {{t|language_code|word|etc...}}
            // {{0|1            |2   |3.....}}
            if(t_params.length < 3 || !isValidTemplateT(t_params[0]))
                continue;

            String lang_code       = t_params[1];
            String translated_word = t_params[2];

            if(translated_word.length() == 0)   // does exist any translation
                continue;
            
            if(!LanguageType.has(lang_code)) {
                // concise logging: only one message for one uknown language code
                if(!LanguageType.hasUnknownLangCode(lang_code)) {
                    LanguageType.addUnknownLangCode(lang_code);
                    System.out.println("Warning in WTranslationEntryEn.parse(): The article '"+
                                page_title + "' has translation into unknown language with code: " + lang_code + ".");
                }
                if(lang_code.length() > 10)
                    System.out.println("Error in WTranslationEntryEn.parse(): The article '"+
                            page_title + "' has too long unknown language code: " + lang_code + ".");
                continue;
            }
            prev_lang = lang;
            lang = LanguageType.get(lang_code);

            if(prev_lang != null && prev_lang != lang) {
                // previous and next languages should be the same at one line...
                System.out.println("Warning in WTranslationEntryEn.parse(): The article '"+
                            page_title + "' has translation into different languages at one line. Language codes: " + prev_lang + " and " + lang_code + ".");
                return null;
            }

            translations.add(translated_word);
            
            // todo
            // 1. extract all info from template
            // 2. add fields gender, number, sc (script template), tr (transliteration),
            //    alt (alternate form of the word) to the table translation_entry
        }

        if(translations.size() == 0)
            return null;

        // 2. translation wikified text
        WikiText[] wt = WikiText.createWithoutParsing(page_title, translations);
        if(0 == wt.length)
            return null;
        
        return new WTranslationEntry(lang, wt);
    }

    /** Splits one entry (one line of a translation box) into language and
     * remain text (translation words).
     *
     * result->LanguageType could be null.
     *
     * @return LanguageType and translation text.
     * It could be null if there is no column delimiter.
     */
    private static LangAndTrans splitToLanguageAndTranslations(String text)
    {
        // 1. сhop the beginning of the line: "*: " or "* "
        Matcher m = ptrn_begin_asterisk.matcher(text);
        if(m.find())
            text = m.replaceFirst("");

        int pos_colon = text.indexOf(':');
        if(-1 == pos_colon)
            return null;

        if(pos_colon + 1 > text.length()) // does exist any translation after ":"
            return null;

        String lang_text = text.substring(0, pos_colon);
        String lang_name = "";
        
        // 2. gets wikified language name
        m = ptrn_wikified_lang_name.matcher(lang_text);
        if(m.find())
            lang_name = m.group(1);
        else
            lang_name = lang_text.trim();

        // 3. create result structure
        LangAndTrans lat = new LangAndTrans();
        lat.trans = text.substring(pos_colon+1).trim();
        if(lat.trans.length() == 0)
            return null;
        
        if(LanguageType.hasEnglishName(lang_name))
            lat.lang = LanguageType.getByEnglishName(lang_name);
        else
            lat.lang = null;
        
        return lat;
    }

    /** Returns true if this is one of templates:
     * {{t}}, {{t+}}, {{t-}}, {{trad}}, or {{trad-}}.
     */
    private static boolean isValidTemplateT (String template_name)
    {
        if(null == template_name)
            return false;

        if( template_name.equalsIgnoreCase("t") ||
            template_name.equalsIgnoreCase("t+") ||
            template_name.equalsIgnoreCase("t-") ||
            template_name.equalsIgnoreCase("trad") ||
            template_name.equalsIgnoreCase("trad-")
          )
            return true;

        return false;
    }

}
