/* LabelRu.java - contexual information for definitions, or Synonyms,
 *                or Translations in Russian Wiktionary.
 * 
 * Copyright (c) 2008-2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru.name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import wikokit.base.wikt.multi.en.name.LabelEn;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import wikokit.base.wikipedia.util.template.TemplateExtractor;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelLocal;
import wikokit.base.wikt.util.LabelsText;

/** Contexual information for definitions, or Synonyms, or Translations 
 * in Russian Wiktionary.
 * <PRE>
 * See http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A3%D1%81%D0%BB%D0%BE%D0%B2%D0%BD%D1%8B%D0%B5_%D1%81%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D1%8F
 *     http://ru.wiktionary.org/wiki/Викисловарь:Условные_сокращения </PRE>
 */
public final class LabelRu extends LabelLocal  {
    
    protected final static Map<String, Label> short_name2label = new HashMap<String, Label>();
    protected final static Map<Label, String> label2short_name = new HashMap<Label, String>();
    
    protected final static Map<String, Label> name2label = new HashMap<String, Label>();
    protected final static Map<Label, String> label2name = new HashMap<Label, String>();
    
    /** If there are more than one context label (synonyms,  short name label): <synonymic_label, source_main_unique_label> */
    private static Map<String, Label> multiple_synonym2label = new HashMap<String, Label>();
    
    /** Label translation: from English label to local label */
    protected static Map<Label, Label> translation_en2local = new HashMap<Label, Label>();
    
    private final static Label[] NULL_LABEL_ARRAY = new Label[0];
    
    
    /** Constructor for static context labels listed in this file below.
     */
    protected LabelRu(String short_name, String name,Label label_en) {
        super(short_name, name, label_en);
        initLabelAddedByHand(this);
        
        if(short_name.length() == 0 || name.length() == 0 || null == label_en)
            System.out.println("Error in LabelRu.LabelRu(): one of parameters is empty! label="+short_name+"; name=\'"+name+"\'; label (in English Wiktionary)=\'"+label_en.toString()+"\'.");
        
        // it should be only one local label corresponding to the English label (LabelEn)
        Label label_prev_by_label_en = translation_en2local.get(label_en);
        if(null != label_prev_by_label_en)
            System.out.println("Error in LabelRu.LabelRu(): duplication of LabelEn '"+ label_en.toString() +
                    "', short name='"+short_name+
                    "' name='"+name+"'. It should be only one local label corresponding to the English label. Check the map translation_en2local.");
        
        translation_en2local.put(label_en, this);
    }
    
    /** Constructor for new context labels which are extracted by parser 
     * from the template {{помета|new label}} and added automatically,
     * these new labels are not listed in the LabelRu.
     * 
     * @param short_name name of the found context label
     * 
     * !Attention, automatically added labels (LabelRu) don't have corresponding English labels (LabelEn)!
     */
    public LabelRu(String short_name) { 
        super(short_name);  // added_by_hand = false
        initLabelAddedAutomatically(this);
    }
    
    protected void initLabelAddedByHand(Label label) {
    
        if(null == label)
            System.out.println("Error in LabelEn.initLabelAddedByHand(): label is null, short_name="+short_name+"; name=\'"+name+"\'.");
        
        checksPrefixSuffixSpace(short_name);
        checksPrefixSuffixSpace(name);
        
        // check the uniqueness of the label short name and full name
        Label label_prev_by_short_name = short_name2label.get(short_name);
        Label label_prev_by_name       =       name2label.get(      name);
        
        if(null != label_prev_by_short_name)
            System.out.println("Error in LabelEn.initLabelAddedByHand(): duplication of label (short name)! short name='"+short_name+
                    "' name='"+name+"'. Check the maps short_name2label and name2label.");

        if(null != label_prev_by_name)
            System.out.println("Error in LabelEn.initLabelAddedByHand(): duplication of label (full name)! short_name='"+short_name+
                    "' name='"+name+ "'. Check the maps short_name2label and name2label.");
        
        short_name2label.put(short_name, label);
        label2short_name.put(label, short_name);
        
        name2label.put(name, label);
        label2name.put(label, name);
    };
    
    protected void initLabelAddedAutomatically(Label label) {
    
        if(null == label)
            System.out.println("Error in LabelRu.initLabelAddedAutomatically(): label is null, short_name="+short_name+".");
        
        checksPrefixSuffixSpace(short_name);
        
        // check the uniqueness of the label short name
        Label label_prev_by_short_name = short_name2label.get(short_name);
        
        if(null != label_prev_by_short_name)
            System.out.println("Error in LabelRu.initLabelAddedAutomatically(): duplication of label (short name)! short name='"+short_name+
                    "'. Check the maps short_name2label.");
        
        short_name2label.put(short_name, label);
        label2short_name.put(label, short_name);
    };
    
    /** Checks weather exists the Label (short name) by its name, checks synonyms also. */
    public static boolean hasShortName(String short_name) {
        return short_name2label.containsKey(short_name) || 
         multiple_synonym2label.containsKey(short_name);
    }
    
    /** Gets label by short name of the label. */
    public static Label getByShortName(String short_name) throws NullPointerException
    {
        Label label;

        if(null != (label = short_name2label.get(short_name)))
            return  label;

        if(null != (label = multiple_synonym2label.get(short_name)))
            return  label;

        throw new NullPointerException("Null LabelRu.getByShortName(), label short_name="+ short_name);
    }
    
    /** Adds synonymic context label for the main (source) label.
     * @param label source main unique label
     * @param synonymic_label synonym of label (short name)
     */
    public static Label addNonUniqueShortName(Label label, String synonymic_short_name) {

        checksPrefixSuffixSpace(synonymic_short_name);
        if(synonymic_short_name.length() > 255) {
            System.out.println("Error in Label.addNonUniqueShortName(): the synonymic label='"+synonymic_short_name+
                    "' is too long (.length() > 255)!");
            return null;
        }

        if(short_name2label.containsKey(synonymic_short_name)) {
            System.out.println("Error in Label.addNonUniqueShortName(): the synonymic label '"+synonymic_short_name+
                    "' is already presented in the map label2name!");
            return null;
        }
        
        if(multiple_synonym2label.containsKey(synonymic_short_name)) {
            System.out.println("Error in Label.addNonUniqueShortName(): the synonymic label '"+synonymic_short_name+
                    "' is already presented in the map multiple_synonym2label!");
            return null;
        }
        
        multiple_synonym2label.put(synonymic_short_name, label);
        return label;
    }
    
    /** Checks weather exists the translation for this Label. */
    public static boolean has(Label t) {
        return label2short_name.containsKey(t);
    }
    
    /** Gets short name of label in local language.
     * E.g. gets name of the English label "AU" ("Australia") in Russian "австрал." (LabelRu.java)
     * 
     * @param label - English Wiktionary short label
     */
    public static String getShortName (Label label) {

        Label local_label = translation_en2local.get(label);
        
        if(null == local_label)
            return label.getShortName(); // if there is no translation into local language, then English name
        
        return local_label.getShortName();
    }
    
    /** Gets name of label in local language.
     * E.g. gets name of the English label "offensive" in Russian (LabelRu.java)
     * 
     * @param label - English Wiktionary context label
     */
    public static String getName (Label label) {

        Label local_label = translation_en2local.get(label);
        if(null == local_label)
            return label.getName(); // if there is no translation into local language, then English name
        
        return local_label.getName();
    }
    
    /** Gets all labels. */
    public static Collection<Label> getAllLabels() {
        return short_name2label.values();
    }
    
    /** Counts number of labels. */
    public static int size() {
        return short_name2label.size();
    }
    
    /** Gets all names of labels (short name). */
    public static Set<String> getAllLabelShortNames() {
        return short_name2label.keySet();
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
                
        if(LabelRu.hasShortName( str ))
            return LabelRu.getByShortName( str );
        
      //return new LabelEn(str, LabelCategory.unknown);     // let's create new context label
        return new LabelRu(str);                                // let's create new context label
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
    private static LabelsText extractFirstContextLabel(String line)
    {   
        LabelsText result = null;
        List<Label> labels = new ArrayList<Label>();
        
        // 1. extract labels {{экон.|en}} or {{экон.}}, or {{помета|экон.}}
  
        // @returns: name of template, array of parameters, first and last position of the template in the source string
        TemplateExtractor te = TemplateExtractor.getFirstTemplate(line);
        if(null == te)
            return new LabelsText(NULL_LABEL_ARRAY, line); // there are no any templates
        
        String template_name = te.getName();
        String text_from_label = "";
        if(LabelRu.hasShortName( template_name )) {
            
            int number_of_params = te.countTemplateParameters();
            String[] template_params = te.getTemplateParameters();
            
            Label l = LabelRu.getByShortName(template_name);
            if(Label.equals( l, LabelEn.context )) { // {{помета|}}
                Label pometa_label = getPometaLabel( template_params );
                if(null != pometa_label)
                    labels.add( pometa_label );
                
            } else if (LabelParamsRu.isLabelWithParams(l, number_of_params)) { // 
                
                Label result_label = LabelParamsRu.getNewLabelByParams (l, template_params);
                if(null != result_label)
                    labels.add(result_label);
                
            } else if (FormOfRu.isDefinitionTransformingLabel(l, number_of_params)) {
        
                // 2. special templates, which require special treatment, they will be stripped right now
                // {{=|
                // {{as ru|
                // {{аббр.|en|abbreviation|description}} -> context label "аббр." and text "[[description]]"
                // {{сокр.|en|identification|[[идентификация]]}} or {{сокр.|en|identification}}; [[идентификация]]

                text_from_label = FormOfRu.transformTemplateToText(l, template_params);
                labels.add( l );
            } else {
                labels.add(l);
            }
            
            
            String text_before = TemplateExtractor.extractTextBeforeTemplate(line, te);
            if (text_before.length() < 10 ||       // or very short text, e.g. {{label1}} ``and`` {{label2}}
                text_before.matches("[\\s\\pP]*")) // between (or before) context labels only space and punctuation marks could be
            {
                String text_wo_labels = text_from_label.concat( TemplateExtractor.extractTextAfterTemplate(line, te) );
                result = new LabelsText(labels, text_wo_labels);
            } else {
                if (text_before.length() >= 10){ // "# some definition and {{the label at the end of definition}}"
                    String text_wo_labels = text_before.concat( text_from_label.concat( TemplateExtractor.extractTextAfterTemplate(line, te) ));
                    result = new LabelsText(labels, text_wo_labels.trim());
                }
            }
            
            //if(0 == te.countTemplateParameters()) { // {{zero parameters}}
            //}
        } else {
            result = new LabelsText(NULL_LABEL_ARRAY, line); // this template is not context label
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
    public static LabelsText extractLabelsTrimText(String line)
    {   
        line = removeEmptyLabelPometa(line);
        if(line.length() == 0)
            return null;
        
        if(!line.contains("{{"))    // every context label should be a template "{{"
            return new LabelsText(NULL_LABEL_ARRAY, line);
        
        List<Label> labels = new ArrayList<Label>();
        
        LabelsText lt = extractFirstContextLabel(line);
        while(lt != null && lt.getLabels().length > 0) {
            
            labels.addAll(Arrays.asList(lt.getLabels()));
            lt = extractFirstContextLabel(lt.getText());
        }
        
        String result_line = "";
        if(lt != null)
            result_line = lt.getText().trim();
        
        return new LabelsText(labels, result_line);
    }


    // ///////////////////////////////////////////////////////////////////////////////////////
    // context label short, context label full name, Category of words with this context label
    
    public static final Label context = new LabelRu("помета", "помета", LabelEn.context);// meta context label will be treated in a special way. http://ru.wiktionary.org/wiki/Шаблон:помета
                                                                                         // this is a fake label, which shouldn't be visible to user in GUI
    
    public static final Label partial = new LabelRu("частичн.", "частичный", LabelEn.partial);
    
    
    // grammatical - грамматические категории
    // //////////////////////////
    public static final Label abbreviation = new LabelRu("аббр.", "аббревиатура", LabelEn.abbreviation);
    public static final Label abbreviation_sokr = LabelRu.addNonUniqueShortName(abbreviation, "сокр.");
    
    public static final Label adjectival = new LabelRu("адъектив.", "адъективное", LabelEn.adjectival);
    public static final Label impersonal = new LabelRu("безл.", "безличное", LabelEn.impersonal);
    public static final Label accusative = new LabelRu("вин. п.", "винительный падеж", LabelEn.accusative); // absent
    public static final Label parenthetical_word = new LabelRu("вводн.", "вводное слово", LabelEn.parenthetical_word);
    public static final Label interrogative = new LabelRu("вопр.", "в вопросительных предложениях", LabelEn.interrogative);
    public static final Label exclamatory = new LabelRu("восклиц.", "в восклицательных предложениях", LabelEn.exclamatory);
    public static final Label verb = new LabelRu("гл.", "глагол", LabelEn.verb); // absent
    public static final Label dative = new LabelRu("дат. п.", "дательный падеж", LabelEn.dative); // absent
    public static final Label feminine_formed_from_male = new LabelRu("женск.", "женского рода, образованные от мужского", LabelEn.feminine_formed_from_male);
    public static final Label feminine_gender = new LabelRu("ж. р.", "женский род", LabelEn.feminine_gender); // absent
    
    public static final Label singular = new LabelRu("ед. ч.", "единственное число", LabelEn.singular);
    public static final Label singular2 = LabelRu.addNonUniqueShortName(singular, "ед.");
    
    public static final Label nominative_case = new LabelRu("им. п.", "именительный падеж", LabelEn.nominative_case); // absent
    public static final Label countable = new LabelRu("исч.", "исчислимое", LabelEn.countable);
    public static final Label ablative = new LabelRu("исх. п.", "исходный падеж", LabelEn.ablative); // absent

    public static final Label masculine_gender = new LabelRu("м. р.", "мужской род", LabelEn.masculine_gender); // absent
    public static final Label locative = new LabelRu("местн. п.", "местный падеж", LabelEn.locative); // absent
    public static final Label frequentative = new LabelRu("многокр.", "со значением многократности действия", LabelEn.frequentative);//form-of
    public static final Label in_the_plural = new LabelRu("мн. ч.", "множественное число", LabelEn.in_the_plural);
    public static final Label in_the_plural2 = LabelRu.addNonUniqueShortName(in_the_plural, "мн");
    
    public static final Label adverb = new LabelRu("нареч.", "наречие", LabelEn.adverb);// form-of
    public static final Label adverb2 = LabelRu.addNonUniqueShortName(adverb, "наречие");
    
    public static final Label inanimate = new LabelRu("неодуш.", "неодушевлённое", LabelEn.inanimate);
    public static final Label intransitive = new LabelRu("неперех.", "непереходный глагол", LabelEn.intransitive);
    public static final Label uncountable = new LabelRu("неисч.", "неисчислимое", LabelEn.uncountable);
    public static final Label indecl = new LabelRu("нескл.", "несклоняемое", LabelEn.indecl);
    public static final Label generalized_abstract = new LabelRu("обобщ.", "обобщённое", LabelEn.generalized_abstract); // absent
    public static final Label common_gender = new LabelRu("общ.", "форма общего рода", LabelEn.common_gender); // absent
    public static final Label momentane = new LabelRu("однокр.", "со значением мгновенности или однократности действия", LabelEn.momentane);//form-of
    public static final Label animate = new LabelRu("одуш.", "одушевлённое", LabelEn.animate);
    public static final Label negative = new LabelRu("отриц.", "в отрицательных предложениях", LabelEn.negative);
    
    public static final Label transitive = new LabelRu("перех.", "переходный глагол", LabelEn.transitive);
    public static final Label imperative = new LabelRu("повел.", "в предложениях в повелительном наклонении", LabelEn.imperative);
    public static final Label predicate = new LabelRu("предик.", "предикатив", LabelEn.predicate);
    public static final Label prepositional_case = new LabelRu("предл. п.", "предложный падеж", LabelEn.prepositional_case); // absent
    public static final Label adjective = new LabelRu("прил.", "прилагательное", LabelEn.adjective); // absent
    public static final Label participle = new LabelRu("прич.", "причастие", LabelEn.participle);
    public static final Label past_tense = new LabelRu("прош.", "прошедшее время, прошедшего времени", LabelEn.past_tense); // absent
    
    public static final Label genitive_case = new LabelRu("род. п.", "родительный падеж", LabelEn.genitive_case); // absent
    
    public static final Label collective = new LabelRu("собир.", "собирательное", LabelEn.collective);
    public static final Label collective2 = LabelRu.addNonUniqueShortName(collective, "собират.");
    
    public static final Label neuter_gender = new LabelRu("ср. р.", "средний род", LabelEn.neuter_gender); // absent
    public static final Label passive = new LabelRu("страд.", "страдательный (залог)", LabelEn.passive);
    
    public static final Label substantivized = new LabelRu("субстантивир.", "субстантивированное", LabelEn.substantivized);
    public static final Label substantivized2 = LabelRu.addNonUniqueShortName(substantivized, "субст.");
    
    public static final Label noun = new LabelRu("сущ.", "существительное", LabelEn.noun); // absent
    public static final Label instrumental = new LabelRu("тв. п.", "творительный падеж", LabelEn.instrumental); // absent
    
    
    // period
    // //////////////////////////
    public static final Label historical = new LabelRu("истор.", "историческое", LabelEn.historical);
    public static final Label neologism = new LabelRu("неол.", "неологизм", LabelEn.neologism);
    public static final Label archaic = new LabelRu("старин.", "старинное", LabelEn.archaic);
    public static final Label obsolete = new LabelRu("устар.", "устаревшее", LabelEn.obsolete);

    
    // qualifier
    // //////////////////////////
    public static final Label literally = new LabelRu("букв.", "буквально", LabelEn.literally);
    public static final Label humorously = new LabelRu("шутл.", "шутливое", LabelEn.humorously);
    
    
    // regional - языковая принадлежность
    // //////////////////////////
    public static final Label Australia = new LabelRu("австрал.", "австралийское вариант английского языка", LabelEn.Australia);
    public static final Label Albania = new LabelRu("алб.", "албанское", LabelEn.Albania);
    public static final Label Adygei = new LabelRu("адыг.", "адыгское", LabelEn.Adygei);
    public static final Label Azerbaijan = new LabelRu("азерб.", "азербайджанское", LabelEn.Azerbaijan);
    public static final Label Aymara = new LabelRu("айм.", "аймарское", LabelEn.Aymara);
    public static final Label Ainu = new LabelRu("айнск.", "айнское", LabelEn.Ainu);
    public static final Label Alemannic = new LabelRu("алем.", "алеманское", LabelEn.Alemannic);
    public static final Label US = new LabelRu("амер.", "американский вариант английского языка", LabelEn.US);
    public static final Label England = new LabelRu("англ.", "английское", LabelEn.England);
    public static final Label Arabic = new LabelRu("арабск.", "арабское", LabelEn.Arabic);
    public static final Label Aragonese = new LabelRu("араг.", "арагонское", LabelEn.Aragonese);
    public static final Label Armenia = new LabelRu("арм.", "армянское", LabelEn.Armenia);
    public static final Label Assam = new LabelRu("ассамск.", "ассамское", LabelEn.Assam);
    public static final Label Asturias = new LabelRu("астур.", "астурийское", LabelEn.Asturias);
    public static final Label Afrikaans = new LabelRu("афр.", "африкаанс", LabelEn.Afrikaans);
   
    public static final Label Basque = new LabelRu("баскск.", "баскское", LabelEn.Basque);
    public static final Label Bashkiria = new LabelRu("башк.", "башкирское", LabelEn.Bashkiria);
    public static final Label Belarus = new LabelRu("белор.", "белорусское", LabelEn.Belarus);
    public static final Label Belgium = new LabelRu("бельг.", "бельгийский вариант нидерландского языка", LabelEn.Belgium);
    public static final Label Bengal = new LabelRu("бенг.", "бенгальское", LabelEn.Bengal);
    public static final Label Bulgaria = new LabelRu("болг.", "болгарское", LabelEn.Bulgaria);
    public static final Label Bosnia = new LabelRu("босн.", "боснийское", LabelEn.Bosnia);
    public static final Label Brazil = new LabelRu("браз.", "бразильский вариант португальского языка", LabelEn.Brazil);
    public static final Label Breton = new LabelRu("брет.", "бретонское", LabelEn.Breton);
    public static final Label British = new LabelRu("брит.", "британский вариант английского языка", LabelEn.British);
    public static final Label Buryat = new LabelRu("бурятск.", "бурятское", LabelEn.Buryat);
    
    public static final Label Wales = new LabelRu("валл.", "валлийское", LabelEn.Wales);
    public static final Label Walloon = new LabelRu("валлонск.", "валлонское", LabelEn.Walloon);
    public static final Label Waray = new LabelRu("варайск.", "варайское", LabelEn.Waray);
    public static final Label Hungary = new LabelRu("венг.", "венгерское", LabelEn.Hungary);
    public static final Label Veps = new LabelRu("вепсск.", "вепсское", LabelEn.Veps);
    
    public static final Label Hollandic = new LabelRu("голл.", "голландский вариант нидерландского языка", LabelEn.Hollandic);
    public static final Label Greece = new LabelRu("греч.", "греческое", LabelEn.Greece);
    public static final Label Denmark = new LabelRu("датск.", "датское", LabelEn.Denmark);
    public static final Label Dominican_Republic = new LabelRu("доминик.", "доминиканский вариант испанского языка", LabelEn.Dominican_Republic);
    public static final Label Old_High_German = new LabelRu("др.-в.-нем.", "древневерхненемецкое", LabelEn.Old_High_German);
    public static final Label Old_Prussian = new LabelRu("др.-прусск.", "древнепрусское", LabelEn.Old_Prussian);
    public static final Label Samogitia = new LabelRu("жем.", "жемайтское", LabelEn.Samogitia);
    
    public static final Label Indonesia = new LabelRu("индонез.", "индонезийское", LabelEn.Indonesia);
    public static final Label Ionic_Greek = new LabelRu("ион.", "ионийское", LabelEn.Ionic_Greek);
    public static final Label Ireland = new LabelRu("ирл.", "ирландский вариант английского языка", LabelEn.Ireland);
    public static final Label Spain = new LabelRu("исп.", "испанское", LabelEn.Spain);
    public static final Label Iceland = new LabelRu("исл.", "исландское", LabelEn.Iceland);
    public static final Label Italy = new LabelRu("итал.", "итальянское", LabelEn.Italy);
    public static final Label Yoruba = new LabelRu("йор.", "йоруба", LabelEn.Yoruba);
    
    public static final Label Kazakhstan = new LabelRu("казахск.", "казахское", LabelEn.Kazakhstan);
    public static final Label Canada = new LabelRu("канадск.", "канадское", LabelEn.Canada);
    public static final Label Karelia = new LabelRu("карел.", "карельское", LabelEn.Karelia);
    public static final Label Kashmiri = new LabelRu("кашм.", "кашмири", LabelEn.Kashmiri);
    public static final Label Kashubian = new LabelRu("кашубск.", "кашубское", LabelEn.Kashubian);
    public static final Label Kyrgyzstan = new LabelRu("кирг.", "киргизское", LabelEn.Kyrgyzstan);
    public static final Label Korea = new LabelRu("кор.", "корейское", LabelEn.Korea);
    public static final Label Cornwall = new LabelRu("корнск.", "корнское", LabelEn.Cornwall);
    public static final Label Kuban = new LabelRu("кубан.", "кубанское", LabelEn.Kuban);
    public static final Label Kurdish = new LabelRu("курдск.", "курдское", LabelEn.Kurdish);
    public static final Label Khmer = new LabelRu("кхмерск.", "кхмерское", LabelEn.Khmer);
    
    public static final Label Lak = new LabelRu("лакск.", "лакское", LabelEn.Lak);
    public static final Label Laos = new LabelRu("лаосск.", "лаосское", LabelEn.Laos);
    public static final Label Latin = new LabelRu("лат.", "латинское", LabelEn.Latin);
    public static final Label Latgale = new LabelRu("латг.", "латгальское", LabelEn.Latgale);
    public static final Label Latvia = new LabelRu("латышск.", "латышское", LabelEn.Latvia);
    public static final Label Lithuania = new LabelRu("литовск.", "литовское", LabelEn.Lithuania);
    public static final Label Lusatia = new LabelRu("луж.", "лужицкое", LabelEn.Lusatia);
        
    public static final Label Macedonia = new LabelRu("макед.", "македонское", LabelEn.Macedonia);
    public static final Label Malagasy = new LabelRu("малаг.", "малагасийское", LabelEn.Malagasy);
    public static final Label Malaysia = new LabelRu("малайск.", "малайское", LabelEn.Malaysia);
    public static final Label Malta = new LabelRu("мальт.", "мальтийское", LabelEn.Malta);
    public static final Label Megrelia = new LabelRu("мегр.", "мегрельское", LabelEn.Megrelia);
    public static final Label Moldavia = new LabelRu("молд.", "молдавское", LabelEn.Moldavia);
    public static final Label Mongolia = new LabelRu("монг.", "монгольское", LabelEn.Mongolia);
    public static final Label Mon = new LabelRu("монск.", "монское", LabelEn.Mon);
    
    public static final Label Naples = new LabelRu("неап.", "неаполитанское", LabelEn.Naples);
    public static final Label Nepal_Bhasa = new LabelRu("нев.", "неварское", LabelEn.Nepal_Bhasa);
    public static final Label Germany = new LabelRu("нем.", "немецкое", LabelEn.Germany);
    public static final Label Nenets = new LabelRu("нен.", "ненецкое", LabelEn.Nenets);
    public static final Label Nepal = new LabelRu("непальск.", "непальское", LabelEn.Nepal);
    public static final Label Netherlands = new LabelRu("нидерл.", "нидерландское", LabelEn.Netherlands);
    public static final Label New_Zealand = new LabelRu("нов.-зел.", "ново-зеландский вариант английского языка", LabelEn.New_Zealand);
    public static final Label Norway = new LabelRu("норв.", "норвежское", LabelEn.Norway);
    
    public static final Label Occitania = new LabelRu("оксит.", "окситанское", LabelEn.Occitania);
    
    public static final Label regional = new LabelRu("обл.", "областное", LabelEn.regional);
    public static final Label regional_reg = LabelRu.addNonUniqueShortName(regional, "рег.");
    public static final Label regional_mestn = LabelRu.addNonUniqueShortName(regional, "местн.");
    
    public static final Label Ossetia = new LabelRu("осет.", "осетинское", LabelEn.Ossetia);
    public static final Label Punjab = new LabelRu("пандж.", "панджабское", LabelEn.Punjab);
    public static final Label Persian = new LabelRu("перс.", "персидское", LabelEn.Persian);
    public static final Label Polabian = new LabelRu("полабск.", "полабское", LabelEn.Polabian);
    public static final Label Poland = new LabelRu("польск.", "польское", LabelEn.Poland);
    public static final Label Portugal = new LabelRu("порт.", "португальское", LabelEn.Portugal);
    public static final Label Prussia = new LabelRu("прусск.", "прусское", LabelEn.Prussia);
    
    public static final Label Romania = new LabelRu("румынск.", "румынское", LabelEn.Romania);
    public static final Label Russia = new LabelRu("русск.", "русское", LabelEn.Russia);
    
    public static final Label Sanskrit = new LabelRu("санскр.", "санскритское", LabelEn.Sanskrit);
    public static final Label Serbo_Croat = new LabelRu("сербохорв.", "сербохорватское", LabelEn.Serbo_Croat);
    public static final Label Serbia = new LabelRu("сербск.", "сербское", LabelEn.Serbia);
    public static final Label Silesia = new LabelRu("силезск.", "силезское", LabelEn.Silesia);
    public static final Label Slovakia = new LabelRu("словацк.", "словацкое", LabelEn.Slovakia);
    public static final Label Slovenia = new LabelRu("словенск.", "словенское", LabelEn.Slovenia);
    
    public static final Label Tabasaran = new LabelRu("табас.", "табасаранское", LabelEn.Tabasaran);
    public static final Label Tagalog = new LabelRu("таг.", "тагальское", LabelEn.Tagalog);
    public static final Label Tajikistan = new LabelRu("тадж.", "таджикское", LabelEn.Tajikistan);
    public static final Label Taiwan = new LabelRu("тайв.", "тайваньский вариант китайского языка", LabelEn.Taiwan);
    public static final Label Thai = new LabelRu("тайск.", "тайское", LabelEn.Thai);
    public static final Label Tamil = new LabelRu("тамильск.", "тамильское", LabelEn.Tamil);
    public static final Label Tatarstan = new LabelRu("тат.", "татарское", LabelEn.Tatarstan);
    public static final Label Tat = new LabelRu("татск.", "татское", LabelEn.Tat);
    public static final Label Turkey = new LabelRu("тур.", "турецкое", LabelEn.Turkey);
    public static final Label Turkmenistan = new LabelRu("туркм.", "туркменское", LabelEn.Turkmenistan);
    public static final Label Turkic = new LabelRu("тюркск.", "тюркское", LabelEn.Turkic);
    
    public static final Label Udmurtia = new LabelRu("удм.", "удмуртское", LabelEn.Udmurtia);
    public static final Label Uzbekistan = new LabelRu("узб.", "узбекское", LabelEn.Uzbekistan);
    public static final Label Uyghur = new LabelRu("уйг.", "уйгурское", LabelEn.Uyghur);
    public static final Label Ukraine = new LabelRu("укр.", "украинское", LabelEn.Ukraine);
    
    public static final Label Faroese = new LabelRu("фарерск.", "фарерское", LabelEn.Faroese);
    public static final Label Fiji = new LabelRu("фидж.", "фиджийское", LabelEn.Fiji);
    public static final Label Finland = new LabelRu("финск.", "финское", LabelEn.Finland);
    public static final Label France = new LabelRu("франц.", "французское", LabelEn.France);
    public static final Label Frisia = new LabelRu("фризск.", "фризское", LabelEn.Frisia);
    public static final Label Friuli = new LabelRu("фриульск.", "фриульское", LabelEn.Friuli);
    
    public static final Label Khakassia = new LabelRu("хак.", "хакасское", LabelEn.Khakassia);
    public static final Label Hittite = new LabelRu("хеттск.", "хеттское", LabelEn.Hittite);
    public static final Label Croatia = new LabelRu("хорв.", "хорватское", LabelEn.Croatia);
    
    public static final Label Church_Slavonic = new LabelRu("церк.-слав.", "церковно-славянское", LabelEn.Church_Slavonic);
    public static final Label Romani = new LabelRu("цыг.", "цыганское", LabelEn.Romani);
    
    public static final Label Chechen_Republic = new LabelRu("чеч.", "чеченское", LabelEn.Chechen_Republic);
    public static final Label Czech_Republic = new LabelRu("чешск.", "чешское", LabelEn.Czech_Republic);
    public static final Label Chile = new LabelRu("чили", "чилийский вариант испанского языка", LabelEn.Chile);
    public static final Label Chuvashia = new LabelRu("чув.", "чувашское", LabelEn.Chuvashia);
    
    public static final Label Sweden = new LabelRu("шведск.", "шведское", LabelEn.Sweden);
    public static final Label Switzerland = new LabelRu("швейц.", "швейцарский вариант немецкого языка", LabelEn.Switzerland);
    public static final Label Sherpa = new LabelRu("шерпск.", "шерпское", LabelEn.Sherpa);
    public static final Label Shor = new LabelRu("шорск.", "шорское", LabelEn.Shor);
    public static final Label Scotland = new LabelRu("шотл.", "шотландский вариант английского языка", LabelEn.Scotland);

    public static final Label Evenki = new LabelRu("эвенк.", "эвенкийское", LabelEn.Evenki);
    public static final Label Even = new LabelRu("эвенск.", "эвенское", LabelEn.Even);
    public static final Label Erzya = new LabelRu("эрз.", "эрзянское", LabelEn.Erzya);
    public static final Label Estonia = new LabelRu("эст.", "эстонское", LabelEn.Estonia);

    public static final Label South_Africa = new LabelRu("южноафр.", "южноафриканское", LabelEn.South_Africa);
    
    public static final Label Javanese = new LabelRu("яванск.", "яванское", LabelEn.Javanese);
    public static final Label Sakha_Republic = new LabelRu("якутск.", "якутское", LabelEn.Sakha_Republic);
    public static final Label Japan = new LabelRu("яп.", "японское", LabelEn.Japan);
    
    
    // usage - стиль
    // //////////////////////////
    
    // todo syn:
    // шаблон:студ. жарг. -> Шаблон:студ.жарг. и др.
    // техн. жарг.
    
    public static final Label abusive = new LabelRu("бранн.", "бранное",  LabelEn.abusive);
    public static final Label abusive2 = LabelRu.addNonUniqueShortName(abusive, "бран.");
    
    public static final Label vulgar = new LabelRu("вульг.", "вульгарное",  LabelEn.vulgar);
    public static final Label bombast = new LabelRu("высок.", "высокопарное",  LabelEn.bombast);
    public static final Label acerbity = new LabelRu("груб.", "грубое",  LabelEn.acerbity);
    public static final Label childish = new LabelRu("детск.", "детское", LabelEn.childish);
    public static final Label dialect = new LabelRu("диал.", "диалектное", LabelEn.dialect);
    public static final Label dysphemism = new LabelRu("дисфм.", "дисфемизм", LabelEn.dysphemism);
    public static final Label cant = new LabelRu("жарг.", "жаргонное", LabelEn.cant);
    public static final Label Internet_slang = new LabelRu("интернет.", "интернетовский жаргон",  LabelEn.Internet_slang);
    public static final Label ironic = new LabelRu("ирон.", "ироничное",  LabelEn.sarcastic);
    public static final Label distorted = new LabelRu("искаж.", "искажённое",  LabelEn.distorted);
    public static final Label beaurocratic = new LabelRu("канц.", "канцелярское",  LabelEn.beaurocratic);
    public static final Label literary = new LabelRu("книжн.", "книжное",  LabelEn.literary);
    public static final Label computerese = new LabelRu("комп. жарг.", "компьютерный жаргон",  LabelEn.computerese); // absent
    public static final Label endearing = new LabelRu("ласк.", "ласкательное", LabelEn.endearing);
    public static final Label obscene_language = new LabelRu("мат", "матерное",  LabelEn.obscene_language);
    public static final Label youth = new LabelRu("мол.", "молодёжное", LabelEn.youth);
    public static final Label folk_poetic = new LabelRu("нар.-поэт.", "народно-поэтическое", LabelEn.folk_poetic);
    public static final Label folk_colloquial = new LabelRu("нар.-разг.", "народно-разговорное", LabelEn.folk_colloquial);
    public static final Label pejorative = new LabelRu("неодобр.", "неодобрительное",  LabelEn.pejorative);// унич. неодобр. умаляющий
    public static final Label approving = new LabelRu("одобр.", "одобрительное",  LabelEn.approving);
    public static final Label formal = new LabelRu("офиц.", "официальное",  LabelEn.formal);

    public static final Label figuratively = new LabelRu("перен.", "переносное значение", LabelEn.figuratively);
    public static final Label figuratively_p = LabelRu.addNonUniqueShortName(figuratively, "п.");

    public static final Label politically_correct = new LabelRu("политкорр.", "политкорректное выражение",  LabelEn.politically_correct);
    public static final Label politically_correct2 = LabelRu.addNonUniqueShortName(politically_correct, "пк");
    
    public static final Label poetic = new LabelRu("поэт.", "поэтическое",  LabelEn.poetic);
    public static final Label contemptuous = new LabelRu("презр.", "презрительное", LabelEn.contemptuous);

    public static final Label scornful = new LabelRu("пренебр.", "пренебрежительное", LabelEn.scornful);
    public static final Label scornful2 = LabelRu.addNonUniqueShortName(scornful, "пренебр");
    
    public static final Label others = new LabelRu("пр.", "прочее",  LabelEn.others); // absent
    public static final Label popular_language = new LabelRu("прост.", "просторечное",  LabelEn.popular_language);
    public static final Label colloquial = new LabelRu("разг.", "разговорное", LabelEn.colloquial);
    public static final Label rare = new LabelRu("редк.", "редкое", LabelEn.rare);
    public static final Label rhetoric = new LabelRu("ритор.", "риторическое", LabelEn.rhetoric);
    public static final Label slang = new LabelRu("сленг", "сленг", LabelEn.slang);
    public static final Label low_style = new LabelRu("сниж.", "сниженное",  LabelEn.low_style);
    public static final Label student_slang = new LabelRu("студ. жарг.", "студенческий жаргон",  LabelEn.student_slang);
    public static final Label tabooed = new LabelRu("табу", "табуированное", LabelEn.tabooed);
    public static final Label technical_jargon = new LabelRu("техн. жарг.", "технический жаргон",  LabelEn.technical_jargon); // absent
    public static final Label solemn = new LabelRu("торж.", "торжественное", LabelEn.solemn);
    public static final Label traditionally_poetic = new LabelRu("трад.-поэт.", "традиционно-поэтическое", LabelEn.traditionally_poetic);

    public static final Label augmentative = new LabelRu("увелич.", "увеличительное",  LabelEn.augmentative);
    public static final Label augmentative2 = LabelRu.addNonUniqueShortName(augmentative, "увеличит.");
    
    public static final Label reproach = new LabelRu("укор.", "укорительное", LabelEn.reproach); // absent
    public static final Label diminutive = new LabelRu("уменьш.", "уменьшительное", LabelEn.diminutive);
    
    public static final Label diminutive_hypocoristic = new LabelRu("умласк.", "уменьшительно-ласкательное", LabelEn.diminutive_hypocoristic);
    public static final Label diminutive_hypocoristic2 = LabelRu.addNonUniqueShortName(diminutive_hypocoristic, "умласк");
    
    public static final Label derogatory = new LabelRu("унич.", "уничижительное", LabelEn.derogatory);// унич. порицательный
    public static final Label derogatory2 = LabelRu.addNonUniqueShortName(derogatory, "уничиж.");
    
    public static final Label corroborative = new LabelRu("усилит.", "усилительное", LabelEn.corroborative);
    public static final Label familiar = new LabelRu("фам.", "фамильярное", LabelEn.familiar);
    public static final Label euphemistic = new LabelRu("эвф.", "эвфемизм", LabelEn.euphemistic);
    public static final Label expressive = new LabelRu("экспр.", "экспрессивное", LabelEn.expressive);
    public static final Label cacography = new LabelRu("эррат.", "эрративное", LabelEn.cacography);
    

    // topical - предметные области + жаргон
    // //////////////////////////
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
    public static final Label military2 = LabelRu.addNonUniqueShortName(military, "воен.");
    
    public static final Label gastronomic = new LabelRu("гастрон.", "гастрономическое", LabelEn.gastronomic);
    public static final Label genetics = new LabelRu("генет.", "молекулярная биология и генетика", LabelEn.genetics);
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
    
    public static final Label mathematics = new LabelRu("матем.", "математическое", LabelEn.mathematics);
    public static final Label microbiology = new LabelRu("микробиол.", "микробиологическое", LabelEn.microbiology);
    public static final Label mechanics = new LabelRu("мех.", "механика", LabelEn.mechanics);
    public static final Label mineralogy = new LabelRu("минер.", "минералогия", LabelEn.mineralogy);
    public static final Label meteorology = new LabelRu("метеорол.", "метеорологическое", LabelEn.meteorology);
    public static final Label metallurgy = new LabelRu("металл.", "металлургическое", LabelEn.metallurgy);
    public static final Label medicine = new LabelRu("мед.", "медицинское", LabelEn.medicine);
    
    public static final Label nautical = new LabelRu("морск.", "морское", LabelEn.nautical);
    public static final Label regional_mor = LabelRu.addNonUniqueShortName(nautical, "мор.");
    public static final Label regional_Mor = LabelRu.addNonUniqueShortName(nautical, "Мор.");

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
    public static final Label professional = new LabelRu("проф.", "профессиональное", LabelEn.professional);
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
    public static final Label technology2 = LabelRu.addNonUniqueShortName(technology, "тех.");
    
    public static final Label trading = new LabelRu("торг.", "торговое", LabelEn.trading);
    public static final Label transport = new LabelRu("трансп.", "транспортное", LabelEn.transport);
    
    public static final Label management = new LabelRu("управл.", "управленческое", LabelEn.management);
    
    public static final Label science_fiction = new LabelRu("фант.", "фантастическое", LabelEn.science_fiction);
    public static final Label philately = new LabelRu("филат.", "филателистическое", LabelEn.philately);
    public static final Label finance = new LabelRu("фин.", "финансовое", LabelEn.finance);
    public static final Label photography = new LabelRu("фотогр.", "фотографическое", LabelEn.photography);
    public static final Label pharmacy = new LabelRu("фарм.", "фармацевтический термин", LabelEn.pharmacy);
    public static final Label physiology = new LabelRu("физиол.", "физиология", LabelEn.physiology);
    public static final Label philosophy = new LabelRu("филос.", "философское", LabelEn.philosophy);
    
    public static final Label philology = new LabelRu("филол.", "филологическое", LabelEn.literature);
    public static final Label philology_lit = LabelRu.addNonUniqueShortName(philology, "лит.");// literature
    public static final Label philology_liter = LabelRu.addNonUniqueShortName(philology, "литер.");// literature also
    
    public static final Label folklore = new LabelRu("фолькл.", "фольклорное", LabelEn.folklore);
    
    public static final Label choreography = new LabelRu("хореогр.", "хореографическое", LabelEn.choreography);
	
    public static final Label sewing = new LabelRu("швейн.", "швейное", LabelEn.sewing);
    
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
    public static final Label computing = new LabelRu("комп.", "компьютерное", LabelEn.computing);
    public static final Label programming = new LabelRu("прогр.", "программистское", LabelEn.programming);
    
    
    // games
    // //////////////////////////
    public static final Label gaming = new LabelRu("игр.", "игровое", LabelEn.gaming);
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
    public static final Label Islam = new LabelRu("ислам.", "исламское", LabelEn.Islam);
    public static final Label religion = new LabelRu("религ.", "религиозное", LabelEn.religion);
    public static final Label ecclesiastical = new LabelRu("церк.", "церковное", LabelEn.ecclesiastical);
    
    
    // science
    // //////////////////////////
    
    public static final Label alchemy = new LabelRu("алхим.", "алхимическое", LabelEn.alchemy);
    public static final Label anthropology = new LabelRu("антроп.", "антропологическое", LabelEn.anthropology);
    public static final Label archaeology = new LabelRu("археол.", "археология", LabelEn.archaeology);
    public static final Label astronomy = new LabelRu("астрон.", "астрономическое", LabelEn.astronomy);
    public static final Label biochemistry = new LabelRu("биохим.", "биохимическое", LabelEn.biochemistry);
    public static final Label biology = new LabelRu("биол.", "биологическое", LabelEn.biology);
    public static final Label botany = new LabelRu("ботан.", "ботаническое", LabelEn.botany);
    public static final Label zoology = new LabelRu("зоол.", "зоологическое", LabelEn.zoology);
    public static final Label computer_science = new LabelRu("информ.", "информатическое", LabelEn.computer_science);
    
    public static final Label psychology = new LabelRu("психол.", "психология", LabelEn.psychology);
    
    public static final Label physics = new LabelRu("физ.", "физическое", LabelEn.physics);
    
    public static final Label chemistry = new LabelRu("хим.", "химическое", LabelEn.chemistry);
    public static final Label chem_element = LabelRu.addNonUniqueShortName(chemistry, "хим-элем");// form-of
    //public static final Label element = LabelRu.addNonUniqueShortName(chemistry, "химическое");// form-of
    public static final Label element_symbol = new LabelRu("элемент", "химический элемент", LabelEn.element_symbol);// form-of - it should be synonyms of "chemistry" (prev line), but there is tech constraints: FormOfRu.java can have only one synonym
    
    
    // sports - special treatment for all sport labels except {{sport}} itself
    // it is needed to parse parameter "вид=" of {{спорт.|вид=}}
    // @see ru.wiktionary.org/wiki/template:спорт.
    // //////////////////////////
    public static final Label gymnastics = new LabelRu("акробат", "акробатика", LabelEn.gymnastics);
    public static final Label basketball = new LabelRu("баскет", "баскетбол", LabelEn.basketball);
    public static final Label baseball = new LabelRu("бейсб", "бейсбол", LabelEn.baseball);
    public static final Label billiards = new LabelRu("бильярд", "бильярд", LabelEn.billiards);
    public static final Label volleyball = new LabelRu("волейб", "волейбол", LabelEn.volleyball);
    
    public static final Label croquet = new LabelRu("крокет", "крокет", LabelEn.croquet);
    public static final Label rugby = new LabelRu("регби", "регби", LabelEn.rugby);
    public static final Label fishing = new LabelRu("рыбол.", "рыболовецкое", LabelEn.fishing);
    public static final Label tennis = new LabelRu("теннис", "теннис", LabelEn.tennis);
    // "chess" see in section "games"
    public static final Label soccer = new LabelRu("футб", "футбол", LabelEn.soccer); // ПОМЕНЯЛ С football 
    public static final Label hockey = new LabelRu("хокк", "хоккей", LabelEn.hockey);
    
    
    // ///////////////////////////////////////////////////////////////////////////////////////   
    // form-of templates (which are not context labels, but a definition text should be extracted from these templates - it's a dirty hack %)
    // public static final Label form_of_templates = new LabelRu("dirty hack ru", ":) ru", LabelEn.form_of_templates);
    
    // to split this list
    
    public static final Label as_ru = new LabelRu("as ru", "as ru", LabelEn.ru_as_ru);
    public static final Label equal = new LabelRu("=", "=", LabelEn.ru_equal);
    public static final Label action = new LabelRu("действие", "действие", LabelEn.ru_action);
    
    public static final Label property = new LabelRu("свойство", "свойство", LabelEn.ru_property);
    public static final Label sootn = new LabelRu("соотн.", "соотн.", LabelEn.ru_sootn);
    
    public static final Label sovershiti = new LabelRu("совершить", "совершить", LabelEn.ru_sovershiti);
    public static final Label sostoyanie = new LabelRu("состояние", "состояние", LabelEn.ru_sostoyanie);
            
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
