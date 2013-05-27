/* LabelEn.java - contexual information for definitions, or Synonyms,
 *                or Translations in English Wiktionary.
 * 
 * Copyright (c) 2008-2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.constant;

import java.util.HashMap;
import java.util.Map;
//import wikokit.base.wikt.multi.en.name.LabelEn;

/** Base (abstract) class for non-English Wiktionary labels, e.g.
 * LabelRu (Russian Wiktionary labels), LabelJa, LabelDe should be based 
 * on this class (except English Wiktionary).
 * 
 * English Wiktionary context labels are described in LabelEn.java.
 */
public abstract class LabelLocal extends Label {
    
    /** English Wiktionary label associated with this local label. */
    protected Label label_en;
    
    /** Label translation: from English label to local label */
    protected static Map<Label, Label> translation_en2local = new HashMap<Label, Label>();
    
    protected LabelLocal(String short_name, String name, Label label_en, boolean added_by_hand) {
        super(short_name, name, added_by_hand);
        initLabel(this);
        
        if(short_name.length() == 0 || name.length() == 0 || null == label_en)
            System.out.println("Error in LabelLocal.LabelLocal(): one of parameters is empty! label="+short_name+"; name=\'"+name+"\'; label (in English Wiktionary)=\'"+label_en.toString()+"\'.");
        
        // it should be only one local label corresponding to the English label (LabelEn)
        Label label_prev_by_label_en = translation_en2local.get(label_en);
        if(null != label_prev_by_label_en)
            System.out.println("Error in LabelLocal.LabelLocal(): duplication of LabelEn '"+ label_en.toString() +
                    "', short name='"+short_name+
                    "' name='"+name+"'. It should be only one local label corresponding to the English label. Check the map translation_en2local.");
        
        translation_en2local.put(label_en, this);
        
        this.label_en   = label_en; 
    }
    
    /** Gets English Wiktionary context label associated with this label. */
    protected Label getLinkedLabelEn() {
        return label_en;
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
    
    /** Gets label itself (short name) in English. 
     *  This functions is needed for comparison (equals()) with LabelEn labels.
     */
    public String getShortNameEnglish() {
        return label_en.getShortName();
    }
}