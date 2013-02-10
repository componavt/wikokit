/* LabelRu.java - contexual information for definitions, or Synonyms,
 *                or Translations in Russian Wiktionary.
 * 
 * Copyright (c) 2008-2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru;

import wikokit.base.wikt.multi.en.name.LabelEn;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelLocal;

/** Contexual information for definitions, or Synonyms, or Translations 
 * in Russian Wiktionary.
 * <PRE>
 * See http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A3%D1%81%D0%BB%D0%BE%D0%B2%D0%BD%D1%8B%D0%B5_%D1%81%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D1%8F
 *     http://ru.wiktionary.org/wiki/Викисловарь:Условные_сокращения </PRE>
 */
public class LabelRu extends LabelLocal  {
    
    protected LabelRu(String label,String name,Label label_en) {
        super(label, name, label_en);
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
    
    // grammatical
    // //////////////////////////
    
    
    // period
    // //////////////////////////
    public static final Label archaic = new LabelRu("старин.", "старинное", LabelEn.archaic);
    public static final Label obsolete = new LabelRu("устар.", "устаревшее", LabelEn.obsolete);
    public static final Label historical = new LabelRu("истор.", "историческое", LabelEn.historical);

    
    // qualifier
    // //////////////////////////
    
    
    // regional
    // //////////////////////////
    public static final Label AU = new LabelRu("австрал.", "австралийский вариант английского языка", LabelEn.AU);
    
    
    // usage
    // //////////////////////////
    public static final Label figuratively = new LabelRu("п.", "переносное значение",  LabelEn.figuratively);
    public static final Label figuratively_peren = LabelRu.addNonUniqueShortName(figuratively, "перен.");// synonym context labels: п. перен.
    //public static final LabelLocal figuratively_peren = LabelRu.addNonUniqueShortName(Label.figuratively, "перен.");// synonym context labels: п. перен.
    //public static final LanguageType INT = LanguageType.addNonUniqueCode(mul, "INT");// Russian Wiktionary, yes! outdated :)
    //LabelLocal.addNonUniqueShortName(null, "перен.");
    
    // addNonUniqueShortName(Label label, String synonymic_short_name) {
    
    public static final Label slang = new LabelRu("сленг", "сленг", LabelEn.slang);
    
    
    
    
    // synonym context labels: рег., обл.

    
    
    // DEBUG: should be one error for each line of code
    // DDDDDDDDDDDDDDDDDDDDDDDDDD
    // source: public static final Label archaic = new LabelRu("старин.", "старинное", LabelEn.archaic);
    // +public static final Label archaic_short_name_duplication = new LabelRu("старин.",  "archaic full name (duplication of short name)", LabelEn.archaic);
    // +public static final Label archaic_full_name_duplication = new LabelRu("archaic short name (duplication of full name)", "старинное", LabelEn.archaic);
    // +public static final Label archaic_label_en_duplication = new LabelRu("short name",  "full name (duplication of label_en)", LabelEn.archaic);
}
