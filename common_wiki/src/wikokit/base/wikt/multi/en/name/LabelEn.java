/* Label.java - contexual information for definitions, or Synonyms,
 *                     or Translations.
 * 
 * Copyright (c) 2008-2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.en.name;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelCategory;

import wikokit.base.wikt.multi.ru.LabelRu;

/** Contextual information for definitions, such as archaic, by analogy, 
 * chemistry, etc.
 * 
 * This contextual information is located in the sections: semantic relations 
 * and translations in Russian Wiktionary.
 * 
 * This class describes context labels of English Wiktionary. Context labels 
 * of other wiktionaries (e.g. Russian Wiktionary) are described in 
 * LabelRu, German Wikt in LabelDe (todo), French Wikt in LabelFr (todo).
 *
 * @see http://en.wiktionary.org/wiki/Template_talk:context
 * @see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 * @see http://en.wiktionary.org/wiki/Template:context
 */
public class LabelEn extends Label {       
    
    /** Category associated with this label. */
    private final LabelCategory category;
    
    //private static Map<String, String> short_name2name = new HashMap<String, String>();
    
    private static Map<LabelEn, LabelCategory> label2category = new HashMap<LabelEn, LabelCategory>();
    
    /** If there are more than one context label (synonyms,  short name label): <synonymic_label, source_main_unique_label> */
    //private static Map<String, LabelEn> multiple_synonym2label = new HashMap<String, LabelEn>();
    
    protected LabelEn(String short_name, String name, LabelCategory category) { 
        super(short_name, name);
        initLabel(this);
        
        if(null == category)
            System.out.println("Error in LabelEn.LabelEn(): category is empty! label="+short_name+"; name=\'"+name+"\'; category=\'"+category.toString()+"\'.");
        
        this.category   = category; 
        label2category. put(this, category);
    }
    
    /** Gets English Wiktionary context label associated with this label. 
     * This function is needed for compatibility with other child class (LabelLocal.java) of Label.java */
    protected Label getLinkedLabelEn() {
        return this;
    }
    
    
    // grammatical
    // //////////////////////////
    
    
    // period
    // //////////////////////////
    public static final Label archaic = new LabelEn("archaic",  "archaic", LabelCategory.period);
    
    public static final Label dated = new LabelEn("dated",  "dated", LabelCategory.period);
    public static final Label dated_sense = LabelEn.addNonUniqueShortName(dated, "dated_sense");
    
    public static final Label historical = new LabelEn("historical",  "historical", LabelCategory.period);
    
    public static final Label obsolete = new LabelEn("obsolete",  "obsolete", LabelCategory.period);
    public static final Label ru_pre_reform = LabelEn.addNonUniqueShortName(obsolete, "ru-pre-reform");
    
    
    
    // qualifier
    // //////////////////////////
    
    
    // regional
    // //////////////////////////
    public static final LabelEn AU = new LabelEn("AU",     "Australia",    LabelCategory.regional);
    
    
    // usage
    // //////////////////////////
    public static final LabelEn figuratively = new LabelEn("figuratively", "figuratively", LabelCategory.usage);
    public static final LabelEn slang = new LabelEn("slang",  "slang", LabelCategory.usage);
    
    
    // **************************
    // topical
    // **************************
    
    
    //        computing,
    //        games,
    //        mathematics,
    //        music,
    //        mythology,
    //        religion,
    //        science,
    //        sports
    
    // science
    // //////////////////////////
    public static final LabelEn astronomy = new LabelEn("astronomy" ,"astronomy",   LabelCategory.science);
    
    
    // synonyms:  {{math}} and {{maths}} -> (mathematics)
    
    
    
    
    // DEBUG: should be one error for each line of code
    // DDDDDDDDDDDDDDDDDDDDDDDDDD
    //public static final Label archaic_short_name_duplication = new LabelEn("archaic",  "archaic full name (duplication of short name)", LabelCategory.period);
    //public static final Label archaic_full_name_duplication = new LabelEn("archaic short name (duplication of full name)", "archaic", LabelCategory.period);
    //public static final Label dated_sense_again = LabelEn.addNonUniqueShortName(dated, "dated_sense");
}
