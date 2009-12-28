/* TipsTeapot.fx - a place where a tips for the reader are prepared.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wiwordik.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/** Tips for the reader: recommendation and tutorial.
 */
public class TipsTeapot {

    /** User search text query */
    private String query_text_string;
    
    /** Source language codes for words filtering, e.g. "ru en fr" */
    private String lang_choice_source_lang_codes;

    private static Random rand = new Random();

    private TipsTeapot(String query_text_string,String lang_choice_source_lang_codes) {
        this.query_text_string = query_text_string;
        this.lang_choice_source_lang_codes = lang_choice_source_lang_codes;

        tips.add(this);
        //code2name.put(code, name);
        // code2lang.put(code, this);
    }

    public static TipsTeapot generateRandomTip () {

        // Random integers that range from from 0 to n
        return tips.get(rand.nextInt(tips.size()));
    }
    
    /** Gets query text string. */
    public String getQuery() {
        return query_text_string;
    }

    /** Gets source language codes for words filtering, e.g. "ru en fr" */
    public String getSourceLangCodes () {
        return lang_choice_source_lang_codes;
    }

    private static List<TipsTeapot> tips = new ArrayList<TipsTeapot>();

    public static final TipsTeapot tip1= new TipsTeapot("*с?рё*", "ru en de fr os uk");
    public static final TipsTeapot tip2= new TipsTeapot("", "xal");
    public static final TipsTeapot tip3= new TipsTeapot("*ill?u*", "fr");
    public static final TipsTeapot tip4= new TipsTeapot("*æ*", "os");
    public static final TipsTeapot tip5= new TipsTeapot("*к?ітч*", "uk");

}
