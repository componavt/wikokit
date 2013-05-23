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
import wikokit.base.wikipedia.util.TemplateExtractor;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelLocal;
import wikokit.base.wikt.util.LabelText;

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
    private static String removeEmptyLabelPometa(String line)
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
    
    /** Extracts labels from a text string, remove these labels from the text line,
     * store the result to the object LabelText.
     *
     * @param line          (wikified) definition line
     * @return labels array (NULL if absent) and a definition text line without context labels substring, 
     *         return NULL if there is no text and context labels
     */
    public static LabelText extractLabelsTrimText(String line)
    {   
        line = removeEmptyLabelPometa(line);
        if(line.length() == 0)
            return null;
        
        if(!line.contains("{{"))    // every context label is a template "{{"
            return new LabelText(null, line);
        
        // 1. extract labels {{экон.|en}} or {{экон.}}, or {{помета|экон.}}
        // todo
        // ...
        // Label[] labels = new Label[0];
        // todo 
        // ...
  
        // @returns:
        // name of template, array of parameters, first and last position in the source string
        TemplateExtractor te = TemplateExtractor.getFirstTemplate(line);
        
        assert(null != te); // temp line, sometimes it is NULL
        
        // 2. special templates, which require special treatment, they will be sipped right now
        // {{=|
        // {{as ru|
        // {{аббр.|en|abbreviation|description}} -> context label "аббр." and text "[[description]]"
        // {{сокр.|en|identification|[[идентификация]]}} or {{сокр.|en|identification}}; [[идентификация]]
        // todo
        // ...
        
        return null;
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
    public static final Label regional = new LabelRu("обл.", "областное", LabelEn.regional);
    public static final Label regional_reg = LabelRu.addNonUniqueShortName(regional, "рег.");
    
    public static final Label Australia = new LabelRu("австрал.", "австралийское вариант английского языка", LabelEn.Australia);
    public static final Label Belgium = new LabelRu("бельг.", "бельгийский вариант нидерландского языка", LabelEn.Belgium);
    public static final Label Brazil = new LabelRu("браз.", "бразильский вариант португальского языка", LabelEn.Brazil);
    public static final Label British = new LabelRu("брит.", "британский вариант английского языка", LabelEn.British);
    public static final Label Canada = new LabelRu("канадск.", "канадское", LabelEn.Canada);
    public static final Label Chile = new LabelRu("чили", "чилийский вариант испанского языка", LabelEn.Chile);
    public static final Label Cornwall = new LabelRu("корнск.", "корнское", LabelEn.Cornwall);
    public static final Label Croatia = new LabelRu("хорв.", "хорватское", LabelEn.Croatia);
    
    public static final Label Dominican_Republic = new LabelRu("доминик.", "доминиканский вариант испанского языка", LabelEn.Dominican_Republic);
    public static final Label England = new LabelRu("англ.", "английское", LabelEn.England);
    public static final Label Finland = new LabelRu("финск.", "финское", LabelEn.Finland);
    public static final Label France = new LabelRu("франц.", "французское", LabelEn.France);
    public static final Label Germany = new LabelRu("нем.", "немецкое", LabelEn.Germany);
    public static final Label Hollandic = new LabelRu("голл.", "голландский вариант нидерландского языка", LabelEn.Hollandic);
    
    public static final Label Indonesia = new LabelRu("индонез.", "индонезийское", LabelEn.Indonesia);
    public static final Label Ionic_Greek = new LabelRu("ион.", "ионийское", LabelEn.Ionic_Greek);
    public static final Label Ireland = new LabelRu("ирл.", "ирландский вариант английского языка", LabelEn.Ireland);
    
    public static final Label Japan = new LabelRu("яп.", "японское", LabelEn.Japan);
    public static final Label Javanese = new LabelRu("яванск.", "яванское", LabelEn.Javanese);
    
    public static final Label Malaysia = new LabelRu("малайск.", "малайское", LabelEn.Malaysia);
    public static final Label Netherlands = new LabelRu("нидерл.", "нидерландское", LabelEn.Netherlands);
    public static final Label New_Zealand = new LabelRu("нов.-зел.", "ново-зеландский вариант английского языка", LabelEn.New_Zealand);
    public static final Label Scotland = new LabelRu("шотл.", "шотландский вариант английского языка", LabelEn.Scotland);
    public static final Label Spain = new LabelRu("исп.", "испанское", LabelEn.Spain);
    public static final Label Switzerland = new LabelRu("швейц.", "швейцарский вариант немецкого языка", LabelEn.Switzerland);
    public static final Label Taiwan = new LabelRu("тайв.", "тайваньский вариант китайского языка", LabelEn.Taiwan);
    public static final Label US = new LabelRu("амер.", "американский вариант английского языка", LabelEn.US);
    
    
    // usage
    // //////////////////////////
    public static final Label childish = new LabelRu("детск.", "детское", LabelEn.childish);
    public static final Label colloquial = new LabelRu("разг.", "разговорное", LabelEn.colloquial);
    public static final Label derogatory = new LabelRu("унич.", "уничижительное", LabelEn.derogatory);
    public static final Label dialect = new LabelRu("диал.", "диалектное", LabelEn.dialect);
    public static final Label euphemistic = new LabelRu("эвф.", "эвфемизм", LabelEn.euphemistic);
    public static final Label familiar = new LabelRu("фам.", "фамильярное", LabelEn.familiar);
    
    public static final Label figuratively = new LabelRu("перен.", "переносное значение", LabelEn.figuratively);
    public static final Label figuratively_p = LabelRu.addNonUniqueShortName(figuratively, "п.");
    
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
    
    
    
    
    

    // **************************
    // topical
    // **************************
    public static final Label Wiktionary_and_WMF_jargon = new LabelRu("жаргон википроектов", "жаргон википроектов", LabelEn.Wiktionary_and_WMF_jargon);
    
    // computing
    // //////////////////////////
    public static final Label programming = new LabelRu("прогр.", "программистское", LabelEn.programming);
    public static final Label computing = new LabelRu("комп.", "компьютерное", LabelEn.computing);

    // games
    // //////////////////////////
    public static final Label chess = new LabelRu("шахм.", "шахматное", LabelEn.chess);
    public static final Label gaming = new LabelRu("игр.", "игровое", LabelEn.gaming);
    
    
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
    public static final Label ecclesiastical = new LabelRu("церк.", "церковное", LabelEn.ecclesiastical);
    public static final Label Islam = new LabelRu("ислам.", "исламское", LabelEn.Islam);
    public static final Label religion = new LabelRu("религ.", "религиозное", LabelEn.religion);
    
    
    // science
    // //////////////////////////
    
    public static final Label alchemy = new LabelRu("алхим.", "алхимическое", LabelEn.alchemy);
    public static final Label anthropology = new LabelRu("антроп.", "антропологическое", LabelEn.anthropology);
    public static final Label archaeology = new LabelRu("археол.", "археология", LabelEn.archaeology);
    public static final Label astronomy = new LabelRu("астрон.", "астрономическое", LabelEn.astronomy);
    public static final Label biochemistry = new LabelRu("биохим.", "биохимическое", LabelEn.biochemistry);
    public static final Label biology = new LabelRu("биол.", "биологическое", LabelEn.biology);
    public static final Label botany = new LabelRu("ботан.", "ботаническое", LabelEn.botany);
    public static final Label chemistry = new LabelRu("хим.", "химическое", LabelEn.chemistry);
    public static final Label psychology = new LabelRu("психол.", "психология", LabelEn.psychology);
    public static final Label computer_science = new LabelRu("информ.", "информатическое", LabelEn.computer_science);
    public static final Label physics = new LabelRu("физ.", "физическое", LabelEn.physics);
    public static final Label zoology = new LabelRu("зоол.", "зоологическое", LabelEn.zoology);
    
    // sports - special treatment for all sport labels except {{sport}} itself
    // it is needed to parse parameter "вид=" of {{спорт.|вид=}}
    // @see ru.wiktionary.org/wiki/template:спорт.
    // //////////////////////////
    public static final Label baseball = new LabelRu("бейсб", "бейсбол", LabelEn.baseball);
    public static final Label basketball = new LabelRu("баскет", "баскетбол", LabelEn.basketball);
    public static final Label billiards = new LabelRu("бильярд", "бильярд", LabelEn.billiards);
    public static final Label croquet = new LabelRu("крокет", "крокет", LabelEn.croquet);
    // "chess" see in section "games"
    public static final Label soccer = new LabelRu("футб", "футбол", LabelEn.soccer); // ПОМЕНЯЛ С football 
    public static final Label gymnastics = new LabelRu("акробат", "акробатика", LabelEn.gymnastics);
    public static final Label hockey = new LabelRu("хокк", "хоккей", LabelEn.hockey);
    public static final Label rugby = new LabelRu("регби", "регби", LabelEn.rugby);    
    public static final Label tennis = new LabelRu("теннис", "теннис", LabelEn.tennis);
    public static final Label volleyball = new LabelRu("волейб", "волейбол", LabelEn.volleyball);
    
    public static final Label fishing = new LabelRu("рыбол.", "рыболовецкое", LabelEn.fishing);
                
    // DEBUG: should be one error for each line of code
    // DDDDDDDDDDDDDDDDDDDDDDDDDD
    // source: public static final Label archaic = new LabelRu("старин.", "старинное", LabelEn.archaic);
    // +public static final Label archaic_short_name_duplication = new LabelRu("старин.",  "archaic full name (duplication of short name)", LabelEn.archaic);
    // +public static final Label archaic_full_name_duplication = new LabelRu("archaic short name (duplication of full name)", "старинное", LabelEn.archaic);
    // +public static final Label archaic_label_en_duplication = new LabelRu("short name",  "full name (duplication of label_en)", LabelEn.archaic);
}
