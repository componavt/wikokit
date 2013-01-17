/* LabelRu.java - contexual information for definitions, or Synonyms,
 *                or Translations in Russian Wiktionary.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru;

import wikokit.base.wikt.constant.Label;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** Contexual information for definitions, or Synonyms, or Translations 
 * in Russian Wiktionary.
 * <PRE>
 * See http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A3%D1%81%D0%BB%D0%BE%D0%B2%D0%BD%D1%8B%D0%B5_%D1%81%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D1%8F
 *     http://ru.wiktionary.org/wiki/Викисловарь:Условные_сокращения </PRE>
 */
public class LabelRu extends Label {
    
    private LabelRu(String label,String name,String category) {
        super(label, name, category);
    }



    
    /** Temporary empty label {{помета?|XX}}, where XX - language code
     *  e.g. {{помета?|uk}} or {{помета?|sq}}.
     */
    private final static Pattern ptrn_label_pometa_question = Pattern.compile(
    // Vim: \Q{{помета?|\E[^}|]*?\}\}
            "\\Q{{помета?|\\E[^}|]*?\\}\\}"
            );

    /** Removes a temporary empty label {{помета?|XX}}, where XX - language code, 
     * e.g. {{помета?|uk}} or {{помета?|sq}}
     *
     * @param line          definition line
     * @return definition text line without "{{помета?|...}}"
     */
    public static String removeEmptyLabelPometa(String line)
    {
        Matcher m = ptrn_label_pometa_question.matcher(line);
        if(m.find()){ // there is "{{помета?|...}}"
            StringBuffer sb = new StringBuffer();
            m.appendReplacement(sb, "");
            m.appendTail(sb);
            return sb.toString().trim();
        }
        return line;
    }


    // context label short, context label full name, Category of words with this context label

    public static final Label p      = new LabelRu("п.",    "переносное значение",  "");
    // synonym context labels: п. перен.
    
    public static final Label ustar  = new LabelRu("устар.","устарелое",            "Устаревшие выражения");
    // synonym context labels: рег., обл.

}
