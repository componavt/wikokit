/* WRedirect.java - functions related to redirects in wiki and Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.word;

import wikokit.base.wikipedia.language.LanguageType;

import wikokit.base.wikt.multi.ru.WRedirectRu;
import wikokit.base.wikt.multi.en.WRedirectEn;

/** Redirect related functions in wiki and Wiktionary.
 */
public class WRedirect {

    /** Checks whether this is a redirect page. If this is true then 
     * the title of the target (redirected) page will be returned.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article
     * @param text          defines source wiki text
     * @return if this is not a redirect then return null
     */
    public static String getRedirect(LanguageType wikt_lang,
                                      String page_title,
                                      StringBuffer text) {

        // #ПЕРЕНАПРАВЛЕНИЕ [[нелётный]]
        // #REDIRECT [[burn one's fingers]]

        LanguageType l = wikt_lang;
        String redirect_dest = null;

        if(l  == LanguageType.ru) {
            redirect_dest = WRedirectRu.getRedirect(page_title, text);
        } else if(l == LanguageType.en) {
            redirect_dest = WRedirectEn.getRedirect(page_title, text);
        //} else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;

            // todo
            // ...

        } else {
            throw new NullPointerException("Null LanguageType");
        }


        return redirect_dest;
    }
    
}
