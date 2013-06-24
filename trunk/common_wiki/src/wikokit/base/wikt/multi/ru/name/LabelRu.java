/* LabelRu.java - contexual information for definitions, or Synonyms,
 *                or Translations in Russian Wiktionary.
 * 
 * Copyright (c) 2008-2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru.name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    
    private final static Label[] NULL_LABEL_ARRAY = new Label[0];
    
    protected LabelRu(String label,String name,Label label_en) {
        super(label, name, label_en, true);  // added_by_hand = true by default
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
    
    /** Extracts first template parameter, except parameter "nocolor", 
     * and gets known LabelEn (.added_by_hand = true),
     * or create new context label (.added_by_hand = false).
     * add to database to the table Label with 
     * 
     * @see http://ru.wiktionary.org/wiki/Шаблон:помета
     * @param params array
     * @return found or created context label, null in the case of some error
     */
    private static Label getPometaLabel(String[] params)
    {
        if(null == params || params.length == 0)
            return null;
        
        //Label result; // pometa_label 
        
        String str = params[0];
        if(str.length() > 8 && str.startsWith("nocolor=")) {
            if(params.length == 1) {
                return null;    // there is only one parameter: {{помета|nocolor=1}}
            } else {
                str = params[1];// let's check the parameter after the "|nocolor=1|" parameter
            }
        }
        
        if(null == str || str.length() == 0)
            return null;
                
        if(Label.hasShortName( str ))
            return Label.getByShortName( str );
        
        return new LabelEn(str);    // let's create new context label
    }
    
    
    /** Extracts labels from the first template from the beginning of the text string, 
     * remove these template from the text line, 
     * store the result to the object LabelText.
     *
     * @param line          (wikified) definition line
     * @return labels array (empty array if absent) 
     *         and a definition text line without context labels substring, 
     *         return NULL if there is no text and context labels
     */
    private static LabelText extractFirstContextLabel(String line)
    {   
        LabelText result = null;
        List<Label> labels = new ArrayList<Label>();
        
        // 1. extract labels {{экон.|en}} or {{экон.}}, or {{помета|экон.}}
  
        // @returns: name of template, array of parameters, first and last position of the template in the source string
        TemplateExtractor te = TemplateExtractor.getFirstTemplate(line);
        if(null == te)
            return new LabelText(NULL_LABEL_ARRAY, line); // there are no any templates
        
        String template_name = te.getName();
        String text_from_label = "";
        if(Label.hasShortName( template_name )) {
            
            Label l = Label.getByShortName(template_name);
            if(Label.equals( l, LabelEn.context )) { // {{помета|}}
                Label pometa_label = getPometaLabel( te.getTemplateParameters() );
                if(null != pometa_label)
                    labels.add( pometa_label );
                
            } else if (FormOfRu.isDefinitionTransformingLabel(l, te.countTemplateParameters())) {
        
                // 2. special templates, which require special treatment, they will be stripped right now
                // {{=|
                // {{as ru|
                // {{аббр.|en|abbreviation|description}} -> context label "аббр." and text "[[description]]"
                // {{сокр.|en|identification|[[идентификация]]}} or {{сокр.|en|identification}}; [[идентификация]]

                text_from_label = FormOfRu.transformTemplateToText(l, te.getTemplateParameters());
                labels.add( l );
            } else {
                labels.add(l);
            }
            
            // between (or before) context labels only space and punctuation marks could be
            String text_before = TemplateExtractor.extractTextBeforeTemplate(line, te);
            if (text_before.matches("[\\s\\pP]*")) {
                String text_wo_labels = text_from_label.concat( TemplateExtractor.extractTextAfterTemplate(line, te) );
                result = new LabelText(labels, text_wo_labels);
            }
            
            //if(0 == te.countTemplateParameters()) { // {{zero parameters}}
            //}
        } else {
            result = new LabelText(NULL_LABEL_ARRAY, line); // this template is not context label
        }
        
        return result;
    }
    
    /** Extracts labels from the beginning of the text string, remove these labels from the text line,
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
        
        if(!line.contains("{{"))    // every context label should be a template "{{"
            return new LabelText(NULL_LABEL_ARRAY, line);
        
        List<Label> labels = new ArrayList<Label>();
        
        LabelText lt = extractFirstContextLabel(line);
        while(lt != null && lt.getLabels().length > 0) {
            
            labels.addAll(Arrays.asList(lt.getLabels()));
            lt = extractFirstContextLabel(lt.getText());
        }
        
        String result_line = "";
        if(lt != null)
            result_line = lt.getText().trim();
        
        return new LabelText(labels, result_line);
    }


    // ///////////////////////////////////////////////////////////////////////////////////////
    // context label short, context label full name, Category of words with this context label
    
    public static final Label context = new LabelRu("помета", "помета", LabelEn.context);// meta context label will be treated in a special way. http://ru.wiktionary.org/wiki/Шаблон:помета
                                                                                         // this is a fake label, which shouldn't be visible to user in GUI
    
    // grammatical
    // //////////////////////////
    public static final Label abbreviation = new LabelRu("аббр.", "аббревиатура", LabelEn.abbreviation);
    public static final Label abbreviation_sokr = LabelRu.addNonUniqueShortName(abbreviation, "сокр.");
    
    public static final Label animate = new LabelRu("одуш.", "одушевлённое", LabelEn.animate);
    public static final Label countable = new LabelRu("исч.", "исчислимое", LabelEn.countable);
    
    public static final Label in_the_plural = new LabelRu("мн. ч.", "множественное число", LabelEn.in_the_plural);
    public static final Label in_the_plural2 = LabelRu.addNonUniqueShortName(in_the_plural, "мн");
    
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
    public static final Label derogatory = new LabelRu("унич.", "уничижительное", LabelEn.derogatory);// унич. порицательный
    public static final Label dialect = new LabelRu("диал.", "диалектное", LabelEn.dialect);
    public static final Label euphemistic = new LabelRu("эвф.", "эвфемизм", LabelEn.euphemistic);
    public static final Label familiar = new LabelRu("фам.", "фамильярное", LabelEn.familiar);
    
    public static final Label figuratively = new LabelRu("перен.", "переносное значение", LabelEn.figuratively);
    public static final Label figuratively_p = LabelRu.addNonUniqueShortName(figuratively, "п.");
    
    public static final Label Internet_slang = new LabelRu("интернет.", "интернетовский жаргон",  LabelEn.Internet_slang);
    public static final Label pejorative = new LabelRu("неодобр.", "неодобрительное",  LabelEn.pejorative);// унич. неодобр. умаляющий
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
    public static final Label aviation = new LabelRu("авиац.", "авиационное", LabelEn.aviation);
    public static final Label vehicle = new LabelRu("автомоб.", "автомобильное", LabelEn.vehicle);
    public static final Label agronomy = new LabelRu("агрон.", "агрономическое", LabelEn.agronomy);
    public static final Label climbing = new LabelRu("альп.", "альпинистское", LabelEn.climbing);
    public static final Label anatomy = new LabelRu("анат.", "анатомическое", LabelEn.anatomy);    
    public static final Label artillery = new LabelRu("артилл.", "артиллерийское", LabelEn.artillery);
    public static final Label architecture = new LabelRu("архит.", "архитектурное", LabelEn.architecture);
    public static final Label astrology = new LabelRu("астрол.", "астрологическое", LabelEn.astrology); 
    
    public static final Label accounting = new LabelRu("бухг.", "бухгалтерское", LabelEn.accounting);    
    public static final Label biblical = new LabelRu("библейск.", "библейское", LabelEn.biblical);
    
    public static final Label veterinary_medicine = new LabelRu("вет.", "ветеринарное", LabelEn.veterinary_medicine);   
    public static final Label military = new LabelRu("военн.", "военное", LabelEn.military);
    
    public static final Label gastronomic = new LabelRu("гастрон.", "гастрономическое", LabelEn.gastronomic);
    public static final Label genetics = new LabelRu("генет.", "генетическое", LabelEn.genetics);
    public static final Label grammar = new LabelRu("грам.", "грамматическое", LabelEn.grammar);       
    public static final Label geography = new LabelRu("геогр.", "географическое", LabelEn.geography);
    public static final Label geodesy = new LabelRu("геод.", "геодезическое", LabelEn.geodesy);
    public static final Label geophysics = new LabelRu("геофиз.", "геофизическое", LabelEn.geophysics);
    public static final Label heraldry = new LabelRu("геральд.", "геральдическое", LabelEn.heraldry);
    public static final Label geometry = new LabelRu("геометр.", "геометрическое", LabelEn.geometry);
    public static final Label geology = new LabelRu("геол.", "геологическое", LabelEn.geology);
    public static final Label hydrology = new LabelRu("гидрол.", "гидрологическое", LabelEn.hydrology);
    public static final Label mining = new LabelRu("горн.", "горное дело", LabelEn.mining);
    
    public static final Label diplomacy = new LabelRu("дипл.", "дипломатическое", LabelEn.diplomacy);
	
    public static final Label natural_science = new LabelRu("ест.", "естествознание", LabelEn.natural_science);

    public static final Label Wiktionary_and_WMF_jargon = new LabelRu("жаргон википроектов", "жаргон википроектов", LabelEn.Wiktionary_and_WMF_jargon);
    
    public static final Label rail_transport = new LabelRu("ж.-д.", "железнодорожное", LabelEn.rail_transport);
    public static final Label rail_transport2 = LabelRu.addNonUniqueShortName(rail_transport, "жд");
    
    public static final Label painting = new LabelRu("живоп.", "живопись", LabelEn.painting);
    
    public static final Label arts = new LabelRu("искусств.", "искусствоведческое", LabelEn.arts);
    public static final Label ichthyology = new LabelRu("ихтиол.", "ихтиологическое", LabelEn.ichthyology);
    public static final Label yoga = new LabelRu("йогич.", "йогическое", LabelEn.yoga);
	
    public static final Label card_games = new LabelRu("карт.", "картёжное", LabelEn.card_games);
    public static final Label ceramics = new LabelRu("керам.", "керамическое", LabelEn.ceramics);
    public static final Label cinematography = new LabelRu("кино", "кинематографическое", LabelEn.cinematography);
    public static final Label cynology = new LabelRu("кинол.", "кинологическое", LabelEn.cynology);
    public static final Label space_science = new LabelRu("косм.", "космическое", LabelEn.space_science);
    public static final Label criminal = new LabelRu("крим.", "криминальное", LabelEn.criminal);
    public static final Label cooking = new LabelRu("кулин.", "кулинарное", LabelEn.cooking);
    public static final Label cultural_anthropology = new LabelRu("культурол.", "культурологическое", LabelEn.cultural_anthropology);
    
    public static final Label forestry = new LabelRu("лес.", "лесоводство", LabelEn.forestry);
    public static final Label linguistics = new LabelRu("лингв.", "лингвистическое", LabelEn.linguistics);
    public static final Label literature = new LabelRu("лит.", "литературоведение", LabelEn.literature);
    
    public static final Label mathematics = new LabelRu("матем.", "математическое", LabelEn.mathematics);
    public static final Label microbiology = new LabelRu("микробиол.", "микробиологическое", LabelEn.microbiology);
    public static final Label mechanics = new LabelRu("мех.", "механика", LabelEn.mechanics);
    public static final Label mineralogy = new LabelRu("минер.", "минералогия", LabelEn.mineralogy);
    public static final Label meteorology = new LabelRu("метеорол.", "метеорологическое", LabelEn.meteorology);
    public static final Label metallurgy = new LabelRu("металл.", "металлургическое", LabelEn.metallurgy);
    public static final Label medicine = new LabelRu("мед.", "медицинское", LabelEn.medicine);
    public static final Label nautical = new LabelRu("морск.", "морское", LabelEn.nautical);
	
    public static final Label sciences = new LabelRu("научн.", "научное", LabelEn.sciences);
    public static final Label oil_industry = new LabelRu("нефтегаз.", "нефтегазодобыча", LabelEn.oil_industry);
    public static final Label numismatics = new LabelRu("нумизм.", "нумизматическое", LabelEn.numismatics);
	
    public static final Label occult = new LabelRu("оккульт.", "оккультное", LabelEn.occult);
    public static final Label optics = new LabelRu("опт.", "оптическое", LabelEn.optics);
    public static final Label ornithology = new LabelRu("орнитол.", "орнитологическое", LabelEn.ornithology);
    public static final Label hunting = new LabelRu("охотн.", "охотничье", LabelEn.hunting);
	
    public static final Label paleontology = new LabelRu("палеонт.", "палеонтологическое", LabelEn.paleontology);
    public static final Label hairdressing = new LabelRu("парикмах.", "парикмахерское", LabelEn.hairdressing);
    public static final Label carpentry = new LabelRu("плотн.", "плотницкое дело", LabelEn.carpentry);
    public static final Label printing = new LabelRu("полигр.", "полиграфическое", LabelEn.printing);
    public static final Label politics = new LabelRu("полит.", "политическое", LabelEn.politics);
    public static final Label sartorial = new LabelRu("портн.", "портновское дело", LabelEn.sartorial);
    public static final Label psychiatry = new LabelRu("психиатр.", "психиатрия", LabelEn.psychiatry);

    public static final Label advertising = new LabelRu("рекл.", "рекламное", LabelEn.advertising);
    public static final Label radio = new LabelRu("радио", "радиодело, радиовещание", LabelEn.radio);
    
    public static final Label sexology = new LabelRu("сексол.", "сексология", LabelEn.sexology);
    
    public static final Label agriculture = new LabelRu("сельск.", "сельскохозяйственное", LabelEn.agriculture);
    public static final Label agriculture2 = LabelRu.addNonUniqueShortName(agriculture, "сх");
    public static final Label agriculture3 = LabelRu.addNonUniqueShortName(agriculture, "с.-х.");
    
    public static final Label sociology = new LabelRu("социол.", "социология", LabelEn.sociology);
    public static final Label soviet = new LabelRu("совет.", "советизм", LabelEn.soviet);
    public static final Label speleology = new LabelRu("спелеол.", "спелеологическое", LabelEn.speleology);
    public static final Label sports = new LabelRu("спорт.", "спортивное", LabelEn.sports);
    public static final Label statistics = new LabelRu("стат.", "статистическое", LabelEn.statistics);
    public static final Label construction = new LabelRu("строит.", "строительное", LabelEn.construction);
    public static final Label special = new LabelRu("спец.", "специальное", LabelEn.special);
    
    public static final Label theater = new LabelRu("театр.", "театральное", LabelEn.theater);
    public static final Label textiles = new LabelRu("текст.", "текстильное", LabelEn.textiles);
    public static final Label technology = new LabelRu("техн.", "техническое", LabelEn.technology);
    public static final Label trading = new LabelRu("торг.", "торговое", LabelEn.trading);
    public static final Label transport = new LabelRu("трансп.", "транспортное", LabelEn.transport);
    
    public static final Label management = new LabelRu("управл.", "управленческое", LabelEn.management);
    
    public static final Label science_fiction = new LabelRu("фант.", "фантастическое", LabelEn.science_fiction);
    public static final Label philately = new LabelRu("филат.", "филателистическое", LabelEn.philately);
    public static final Label finance = new LabelRu("фин.", "финансовое", LabelEn.finance);
    public static final Label photography = new LabelRu("фотогр.", "фотографическое", LabelEn.photography);
    public static final Label pharmacy = new LabelRu("фарм.", "фармацевтический термин", LabelEn.pharmacy);
    public static final Label physiology = new LabelRu("физиол.", "физиология", LabelEn.physiology);
    public static final Label philosophy = new LabelRu("филос.", "швейное", LabelEn.philosophy);
    public static final Label philology = new LabelRu("филол.", "филологическое", LabelEn.philology);
    public static final Label folklore = new LabelRu("фолькл.", "фольклорное", LabelEn.folklore);
    
    public static final Label choreography = new LabelRu("хореогр.", "хореографическое", LabelEn.choreography);
	
    public static final Label sewing = new LabelRu("швейн.", "философское", LabelEn.sewing);
    
    public static final Label circus = new LabelRu("цирк.", "цирковое", LabelEn.circus);
	
    public static final Label ecology = new LabelRu("экол.", "экологическое", LabelEn.ecology);
    public static final Label economics = new LabelRu("экон.", "экономическое", LabelEn.economics);
    public static final Label electrical_engineering = new LabelRu("эл.-техн.", "электротехническое", LabelEn.electrical_engineering);
    public static final Label electric_power = new LabelRu("эл.-энерг.", "электроэнергетическое", LabelEn.electric_power);
    public static final Label entomology = new LabelRu("энтомол.", "энтомологическое", LabelEn.entomology);
    public static final Label ethnology = new LabelRu("этнолог.", "этнологическое", LabelEn.ethnology);
    public static final Label ethnography = new LabelRu("этногр.", "этнографическое", LabelEn.ethnography);
    
    public static final Label legal = new LabelRu("юр.", "юридическое или нормативное", LabelEn.legal);
    public static final Label jewellery = new LabelRu("ювел.", "ювелирное", LabelEn.jewellery);
    
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
    public static final Label element_symbol = LabelRu.addNonUniqueShortName(chemistry, "хим-элем");// form-of
    
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

    
    // ///////////////////////////////////////////////////////////////////////////////////////   
    // form-of templates (which are not context labels, but a definition text should be extracted from these templates - it's a dirty hack %)
    // public static final Label form_of_templates = new LabelRu("dirty hack ru", ":) ru", LabelEn.form_of_templates);
    
    public static final Label as_ru = new LabelRu("as ru", "as ru", LabelEn.ru_as_ru);
    public static final Label equal = new LabelRu("=", "=", LabelEn.ru_equal);
    public static final Label action = new LabelRu("действие", "действие", LabelEn.ru_action);
    public static final Label property = new LabelRu("свойство", "свойство", LabelEn.ru_property);
    public static final Label sootn = new LabelRu("соотн.", "соотн.", LabelEn.ru_sootn);
    
    // eo form-of templates 
    // ///////////////////////////////////////////////////////////////////////////////////////   
    
    // check todo:
    // english {{c}} -> общ. - форма общего рода
    // {{f}} -> ж. — женский род
    // f.pl -> ж. мн.
    // f.sg -> ж. ед.
    // m -> м. - мужской род
    // m/f -> м./ж. - форма мужского или женского рода по контексту
    // n -> ср. - средний род
    // 
    // 
    
    
    // todo in distant future:
    // {{морфема|
    // {{verb-dir|
    // {{verb-dir-n|
    // {{актанты|
    // {{гидроним}}
    
    
    // DEBUG: should be one error for each line of code
    // DDDDDDDDDDDDDDDDDDDDDDDDDD
    // source: public static final Label archaic = new LabelRu("старин.", "старинное", LabelEn.archaic);
    // +public static final Label archaic_short_name_duplication = new LabelRu("старин.",  "archaic full name (duplication of short name)", LabelEn.archaic);
    // +public static final Label archaic_full_name_duplication = new LabelRu("archaic short name (duplication of full name)", "старинное", LabelEn.archaic);
    // +public static final Label archaic_label_en_duplication = new LabelRu("short name",  "full name (duplication of label_en)", LabelEn.archaic);
}
