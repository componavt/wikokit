/* FormOfRu.java - a set of functions related to form-of templates in Russian Wiktionary.
 * 
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.multi.ru.name;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import wikokit.base.wikipedia.util.StringUtil;
import wikokit.base.wikipedia.util.TemplateExtractor;
import wikokit.base.wikt.constant.FormOf;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.multi.en.name.LabelEn;

/** A set of functions related to form-of templates in Russian Wiktionary.
 */
public class FormOfRu extends FormOf {
    
    /** Definition transforming labels in Russian Wiktionary and number of parameters for each label.
     * Text will be extracted only from templates with these (known) number of template parameters.
     */
    private static final Map<Label, int[]> transforming_labels_to_number_of_params;
    static {
        Map<Label, int[]> nop = new HashMap<Label, int[]>();
        nop.put(LabelRu.abbreviation,   new int[] {       2, 3 });
        nop.put(LabelRu.action,         new int[] {    1, 2 });
        nop.put(LabelRu.as_ru,          new int[] { 0 });
        nop.put(LabelRu.equal,          new int[] {    1, 2 });
        nop.put(LabelRu.element_symbol, new int[] {       2, 3, 4 });
        nop.put(LabelRu.property,       new int[] {    1, 2, 3, 4 });
        nop.put(LabelRu.sootn,          new int[] {    1, 2, 3, 4, 5 });
        
        transforming_labels_to_number_of_params = Collections.unmodifiableMap(nop);
    }

    /** Definition transforming labels in Russian Wiktionary. */
    //private final static Label[] transforming_labels = {LabelRu.abbreviation, LabelRu.equal};
    
    /** Checks that this source label transforms definition, i.e. parameters 
     * of this label is a textual part of definition,
     * and checks that this label has a correct number of parameters
     * e.g. "# {{abbreviation of|July}}" -> "# July"
     * 
     * @param label template to be checked
     * @return true if this template (known label) is a part of the definition
     * and if known labels have allowed number of parameters
     */
    public static boolean isDefinitionTransformingLabel (Label source_label, int number_of_params)
    {   
        for (Map.Entry<Label, int[]> entry : transforming_labels_to_number_of_params.entrySet()) {
            Label cur_label = entry.getKey();
            
            if(Label.equals(cur_label, source_label))
                for(int allowed : entry.getValue())
                    if(number_of_params == allowed)
                        return true;
            
        }        
        return false;
    }
    
    
    /** Transforms parameters of the label (template form-of) to text,
     * e.g. "# {{abbreviation of|July}}" -> "# July".
     * 
     *  {{аббр.||Чехословацкая Социалистическая Республика}} -> "Чехословацкая Социалистическая Республика"
     *  {{аббр.|en|frequently asked questions|часто задаваемые вопросы}} -> "от [[frequently asked questions]]; часто задаваемые вопросы"
     * 
     * @param label form-of template
     * @param template_params parameters to be converted to text
     * @return (1) text extracted from template parameters (known labels), 
     *         (2) empty string "" (unknown parameters for known templates)
     */
    public static String transformTemplateToText (Label label, String[] template_params)
    {   
        if((null == template_params || template_params.length == 0)
            && !Label.equals(label, LabelRu.as_ru)) // exception: template {{as_ru}} has no parameters
            return "";
        
        String result = "";
        for(int i=0; i<template_params.length; i++)
            template_params[i] = template_params[i].trim();
        
        if(Label.equals(label, LabelRu.abbreviation)) {
            // template_params[0] - language code (en, de, fr...)
            // template_params[1] - abbreviation
            // template_params[2] - meaning / definition
            switch( template_params.length ) {
                case 2: result = "от [["+template_params[1]+"]]";
                        break;
                case 3: result = "от [["+template_params[1]+"]]; "+template_params[2];
                /*        break;
                default:
                        result = concatTemplateParametersToText(label, template_params); // wrong number of parameters, remain template in the definition
                */
            }
            
        } else if(Label.equals(label, LabelRu.action)) {
            // template_params[0] - the same as [[word]]
            // template_params[1] - meaning / definition
            switch( template_params.length ) {
                case 1: result = "действие по значению гл. [["+template_params[0]+"]]";
                        break;
                case 2: result = "действие по значению гл. [["+template_params[0]+"]]; "+template_params[1];
            }
            
        } else if(Label.equals(label, LabelRu.as_ru)) {
            switch( template_params.length ) {
                case 0: result = "аналогично русскому слову";
            }
            
        } else if(Label.equals(label, LabelRu.equal)) {
            // template_params[0] - the same as [[word]]
            // template_params[1] - meaning / definition
            switch( template_params.length ) {
                case 1: result = "то же, что [["+template_params[0]+"]]";
                        break;
                case 2: result = "то же, что [["+template_params[0]+"]]; "+template_params[1];
            }
            
        } else if(Label.equals(label, LabelRu.element_symbol)) {
            // {{хим-элем|17|Cl|[[хлор]]|lang=en}} ->  "[[химический элемент]] с [[атомный номер|атомным номером]] 17, обозначается [[химический символ|химическим символом]] Cl, [[хлор]]"
            switch( template_params.length ) {
                case 2: result = "[[химический элемент]] с [[атомный номер|атомным номером]] "+template_params[0]+", обозначается [[химический символ|химическим символом]] "+template_params[1];
                        break;
                case 3: // without last unused parameter: "lang=en"
                case 4: result = "[[химический элемент]] с [[атомный номер|атомным номером]] "+template_params[0]+", обозначается [[химический символ|химическим символом]] "+template_params[1]+", "+template_params[2];
            }
            
        } else if(Label.equals(label, LabelRu.property)) {
            // {{свойство|эгалитарный|описание|состояние=1|lang=en}} ->  "[[свойство]] или [[состояние]] по значению [[прилагательное|прил.]] [[эгалитарный]]; описание"
            //            1,          2,       2 или 3,    2 или 3 или 4
            switch( template_params.length ) {
                ///              "[[свойство]] или [[состояние]] по значению [[прилагательное|прил.]] [[эгалитарный]]; описание"
                case 1: result = "[[свойство]] по значению [[прилагательное|прил.]] [["+template_params[0]+"]]";
                        break;
                case 2: 
                case 3: 
                case 4:
                        String param_optional = "";                                    // состояние=1 ?
                        String s = TemplateExtractor.getParameterValue (template_params, "состояние");
                        if(null != s && s.equalsIgnoreCase("1"))
                            param_optional = " или [[состояние]]";
                        
                        if(template_params[1].contains("=")) { // == .startsWith("состояние=") || .startsWith("lang=")), then there is no description (it should be presented as second parameter of template)
                            result = "[[свойство]]"+param_optional+" по значению [[прилагательное|прил.]] [["+template_params[0]+"]]";
                        } else {
                            result = "[[свойство]]"+param_optional+" по значению [[прилагательное|прил.]] [["+template_params[0]+"]]; "+template_params[1];
                        }
            }
        } else if(Label.equals(label, LabelRu.sootn)) {
            
            switch( template_params.length ) {
                // {{соотн.|бильярд}} -> "связанный, [[соотносящийся]] по значению с существительным [[бильярд]]"
                case 1: result = "связанный, [[соотносящийся]] по значению с существительным [["+template_params[0]+"]]";
                        break;
                case 2: 
                case 3: 
                case 4:
                case 5:
                        // {{соотн.|время|свойств=1}} -> "связанный, [[соотносящийся]] по значению с существительным [[время]]; свойственный, [[характерный]] для него"
                        // {{соотн.|свойств=1|идиоматизм|идиома}} -> "связанный, [[соотносящийся]] по значению с существительными [[идиоматизм]], [[идиома]]; свойственный, [[характерный]] для них"
                        String param_svoistv = TemplateExtractor.getParameterValue (template_params, "свойств");
                        String[] other_params = TemplateExtractor.excludeParameter (template_params, "свойств");
                        String pronoun = "";
                        if(null != param_svoistv && param_svoistv.length() > 0 && other_params.length > 0) {
                            // свойств — флаг свойственный, характерный (если = 1, то вставляется местоимение "его", "их", иначе то, что передано в этом параметре)
                            if(param_svoistv.equalsIgnoreCase("1")) {
                                pronoun = other_params.length == 1 ? "него" : "них";
                            } else {
                                pronoun = param_svoistv;
                            }
                            
                            if(other_params.length == 1) {
                                result = "связанный, [[соотносящийся]] по значению с существительным [["+other_params[0]+"]]; свойственный, [[характерный]] для "+pronoun;
                            } else {     // length > 1
                                String wikified_words = StringUtil.join("]], [[", other_params);
                                result = "связанный, [[соотносящийся]] по значению с существительными [["+wikified_words+"]]; свойственный, [[характерный]] для "+pronoun;       
                            }
                        }
                    
            }
        }
        return result;
    }
    
    /** Concatenates "{{template name|parameter 1|parameter 2|...}}" to string.
     */
    /* private static String concatTemplateParametersToText (Label label, String[] template_params)
    {   
        StringBuilder s = new StringBuilder("{{").append(label.getShortName());
        for(String param : template_params) {
            s.append( "|" );
            s.append( param );
        }
        s.append( "}}" );
        return s.toString();
    }*/
    

}
