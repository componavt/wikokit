/* WRedirectEn.java - functions related to redirects in wiki and English Wiktionary.
 *
 * Copyright (c) 2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.multi.en;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** Redirect related functions in wiki and English Wiktionary.
 * 
 * @see http://en.wiktionary.org/wiki/Wiktionary:Redirections
 */
public class WRedirectEn {

    /** Gets target page of the redirect, extracts [[pagename]] from double brackets. */
    private final static Pattern ptrn_redirect = Pattern.compile(
            "#REDIRECT \\[\\[(.+?)\\]\\]",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    /** Checks whether this is a redirect page. If this is true then
     * the title of the target (redirected) page will be returned.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article
     * @param text          defines source wiki text
     * @return if this is not a redirect then return null
     */
    public static String getRedirect(String page_title,
                                      StringBuffer text) {

        // #REDIRECT [[pagename]] (or #redirect [[pagename]]

        //int len = "#REDIRECT [[".length(); // == 12
        if(text.length() < 12 || text.charAt(0) != '#')
            return null;

        Matcher m = ptrn_redirect.matcher(text);
        if (m.find()){
            return m.group(2);
        }

        return null;
    }

}
