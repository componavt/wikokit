/* LabelParamsRu.java - a set of functions related to context label templates with parameters in Russian Wiktionary.
 * 
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.multi.ru.name;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelCategory;
import wikokit.base.wikt.multi.en.name.LabelEn;

/** A set of functions related to context label templates with parameters in Russian Wiktionary.
 * These (context label) templates have several parameter to be extracted.
 */
public class LabelParamsRu {
 
    
    /** Context labels in Russian Wiktionary and number of parameters for each label.
     * Text will be extracted only from templates with these (known) number of template parameters.
     */
    private static final Map<Label, int[]> labels_to_number_of_params;
    static {
        Map<Label, int[]> nop = new HashMap<Label, int[]>();
        
        // templates with several parameters to be extracted.
        nop.put(LabelRu.regional,       new int[] {    1, 2 });
        
        labels_to_number_of_params = Collections.unmodifiableMap(nop);
    }
    
    /** Checks that this source label has several parameters 
     * and checks that this label has a correct number of parameters, 
     * e.g. "# {{обл.|малорос.}}"
     * 
     * @param label template to be checked
     * @return true if known labels have allowed number of parameters
     */
    public static boolean isLabelWithParams (Label source_label, int number_of_params)
    {   
        for (Map.Entry<Label, int[]> entry : labels_to_number_of_params.entrySet()) {
            Label cur_label = entry.getKey();
            
            if(Label.equals(cur_label, source_label))
                for(int allowed : entry.getValue())
                    if(number_of_params == allowed)
                        return true;
            
        }        
        return false;
    }
    
    
    
    /** Gets new label from parameters, e.g. {{region|Siberia}} -> new regional label {{Siberia}}
     * 
     * @param label
     * @param template_params
     * @return 
     */
    public static Label getNewLabelByParams (Label label, String[] template_params)
    {   
        if((null == template_params || template_params.length == 0))
            return null;
        
        //String result = "";
        for(int i=0; i<template_params.length; i++)
            template_params[i] = template_params[i].trim();
        
        if(Label.equals(label, LabelRu.regional)) {
            // template_params[0] - region
            // template_params[1] - language code (en, de, fr...)
            switch( template_params.length ) {
                case 1:
                case 2: // result = "от [["+template_params[1]+"]]";
                    String regions = template_params[0];
                    if(regions.length() == 0)
                        return LabelEn.regional;
                    
                    if(LabelRu.hasShortName(regions)) {
                        Label label_existing = LabelRu.getByShortName(regions);
                        if(null != label_existing)
                            return label_existing;
                    } else {                    
                        return new LabelEn(regions, LabelCategory.regional); // let's create new LabelRu with label=regions
                    }
            }
            
        // } else if(Label.equals(label, LabelRu.action)) {
            
        }
        return null;
    }
}
