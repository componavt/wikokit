/* LabelRu.java - contexual information for definitions, or Synonyms,
 *                or Translations in Russian Wiktionary.
 * 
 * Copyright (c) 2008-2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru.name;

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
    public static final Label animate = new LabelRu("одуш.", "одушевлённое", LabelEn.animate);
    public static final Label countable = new LabelRu("исч.", "исчислимое", LabelEn.countable);
    public static final Label in_the_plural = new LabelRu("мн. ч.", "множественное число", LabelEn.in_the_plural);
    public static final Label inanimate = new LabelRu("неодуш.", "неодушевлённое", LabelEn.inanimate);
    public static final Label indecl = new LabelRu("нескл.", "несклоняемое", LabelEn.indecl);
    public static final Label intransitive = new LabelRu("неперех.", "непереходный глагол", LabelEn.intransitive);
    public static final Label passive = new LabelRu("страд.", "страдательный (залог)", LabelEn.passive);
    public static final Label predicate = new LabelRu("предик.", "предикатив", LabelEn.predicate);
    public static final Label transitive = new LabelRu("перех.", "переходный глагол", LabelEn.transitive);
    public static final Label uncountable = new LabelRu("неисч.", "неисчислимое", LabelEn.uncountable);
     
    // period
    // //////////////////////////
    public static final Label archaic = new LabelRu("старин.", "старинное", LabelEn.archaic);
    public static final Label historical = new LabelRu("истор.", "историческое", LabelEn.historical);
    public static final Label neologism = new LabelRu("неол.", "неологизм", LabelEn.neologism);
    public static final Label obsolete = new LabelRu("устар.", "устаревшее", LabelEn.obsolete);

    
    // qualifier
    // //////////////////////////
    public static final Label humorously = new LabelRu("шутл.", "шутливое", LabelEn.humorously);
    public static final Label literally = new LabelRu("букв.", "буквально", LabelEn.literally);
   
    // regional
    // //////////////////////////
    public static final Label AU = new LabelRu("австрал.", "австралийский вариант английского языка", LabelEn.AU);
    
    
    // usage
    // //////////////////////////
    public static final Label childish = new LabelRu("детск.", "детское",  LabelEn.childish);
    public static final Label colloquial = new LabelRu("разг.", "разговорное",  LabelEn.colloquial);
    public static final Label derogatory = new LabelRu("унич.", "уничижительное",  LabelEn.derogatory);
    public static final Label dialect = new LabelRu("диал.", "диалектное",  LabelEn.dialect);
    public static final Label euphemistic = new LabelRu("эвф.", "эвфемизм",  LabelEn.euphemistic);
    public static final Label familiar = new LabelRu("фам.", "фамильярное",  LabelEn.familiar);
    public static final Label figuratively = new LabelRu("п.", "переносное значение",  LabelEn.figuratively);
    public static final Label figuratively_peren = LabelRu.addNonUniqueShortName(figuratively, "перен.");// synonym context labels: п. перен.
    public static final Label Internet_slang = new LabelRu("интернет.", "интернетовский жаргон",  LabelEn.Internet_slang);
    public static final Label pejorative = new LabelRu("унич.", "уничижительное",  LabelEn.pejorative);
    public static final Label poetic = new LabelRu("поэт.", "поэтическое",  LabelEn.poetic);
    public static final Label politically_correct = new LabelRu("политкорр. (пк)", "политкорректное выражение",  LabelEn.politically_correct);
    public static final Label rare = new LabelRu("редк.", "редкое", LabelEn.rare);
    public static final Label vulgar = new LabelRu("вульг.", "вульгарное",  LabelEn.vulgar);
//    public static final Label  = new LabelRu(".", "",  LabelEn.);
    //public static final LabelLocal figuratively_peren = LabelRu.addNonUniqueShortName(Label.figuratively, "перен.");// synonym context labels: п. перен.
    //public static final LanguageType INT = LanguageType.addNonUniqueCode(mul, "INT");// Russian Wiktionary, yes! outdated :)
    //LabelLocal.addNonUniqueShortName(null, "перен.");
    
    // addNonUniqueShortName(Label label, String synonymic_short_name) {
    
    public static final Label slang = new LabelRu("сленг", "сленг", LabelEn.slang);
    
    
    
    
    // synonym context labels: рег., обл.

    // **************************
    // topical
    // **************************
    public static final Label Wiktionary_and_WMF_jargon = new LabelRu("жаргон википроектов", "жаргон википроектов", LabelEn.Wiktionary_and_WMF_jargon);
    
    // computing
    // //////////////////////////
    public static final Label programming = new LabelRu("прогр.", "программистское", LabelEn.programming);

    // games
    // //////////////////////////
    public static final Label chess = new LabelRu("шахм.", "шахматное", LabelEn.chess);
    
    // mathematics
    // //////////////////////////
    
    
    // music
    // //////////////////////////
    public static final Label music = new LabelRu("муз.", "музыкальное", LabelEn.music);
    
    // mythology
    // //////////////////////////
    public static final Label mythology = new LabelRu("мифол.", "мифологическое", LabelEn.mythology);
    
    // religion
    // //////////////////////////
    public static final Label religion = new LabelRu("религ.", "религиозное", LabelEn.religion);
    
    // science
    // //////////////////////////
    
    // sports - special treatment for all sport labels except {{sport}} itself
    // it is need to parse parameter "вид=" of {{спорт.|вид=}}
    // @see ru.wiktionary.org/wiki/template:спорт.
    // //////////////////////////
    public static final Label baseball = new LabelRu("бейсб", "бейсбол", LabelEn.baseball);
    public static final Label basketball = new LabelRu("баскет", "баскетбол", LabelEn.basketball);
    public static final Label billiards = new LabelRu("бильярд", "бильярд", LabelEn.billiards);
    public static final Label croquet = new LabelRu("крокет", "крокет", LabelEn.croquet);
    // "chess" see in section "games"
    public static final Label football = new LabelRu("футб", "футбол", LabelEn.football);
    public static final Label gymnastics = new LabelRu("акробат", "акробатика", LabelEn.gymnastics);
    public static final Label hockey = new LabelRu("хокк", "хоккей", LabelEn.hockey);
    public static final Label rugby = new LabelRu("регби", "регби", LabelEn.rugby);    
    public static final Label sports = new LabelRu("спорт.", "спортивное", LabelEn.sports);
    public static final Label tennis = new LabelRu("теннис", "теннис", LabelEn.tennis);
    public static final Label volleyball = new LabelRu("волейб", "волейбол", LabelEn.volleyball);
    
            
    // DEBUG: should be one error for each line of code
    // DDDDDDDDDDDDDDDDDDDDDDDDDD
    // source: public static final Label archaic = new LabelRu("старин.", "старинное", LabelEn.archaic);
    // +public static final Label archaic_short_name_duplication = new LabelRu("старин.",  "archaic full name (duplication of short name)", LabelEn.archaic);
    // +public static final Label archaic_full_name_duplication = new LabelRu("archaic short name (duplication of full name)", "старинное", LabelEn.archaic);
    // +public static final Label archaic_label_en_duplication = new LabelRu("short name",  "full name (duplication of label_en)", LabelEn.archaic);
}
