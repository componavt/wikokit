/* LabelCategoryRu.java - names of categories of context labels in Russian.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.multi.ru.name;

import java.util.HashMap;
import java.util.Map;
import wikokit.base.wikt.constant.LabelCategory;
import wikokit.base.wikt.constant.LabelCategoryLocal;

/** Categories of context labels (templates): 
 * category label in Russian and link to the LabelCategory.
 *
 * Source of data in Russian Wiktionary:
 *   Викисловарь:Условные сокращения or http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A3%D1%81%D0%BB%D0%BE%D0%B2%D0%BD%D1%8B%D0%B5_%D1%81%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D1%8F
 *   Категория:Шаблоны помет or http://ru.wiktionary.org/wiki/%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD%D1%8B_%D0%BF%D0%BE%D0%BC%D0%B5%D1%82
 * in English Wiktionary:
 *      http://en.wiktionary.org/wiki/Category:Context_labels
 */
public class LabelCategoryRu extends LabelCategoryLocal {
  
    protected final static Map<String, LabelCategory> name2category = new HashMap<String, LabelCategory>();
    protected final static Map<LabelCategory, String> category2name = new HashMap<LabelCategory, String>();

    protected LabelCategoryRu(String name,LabelCategory category) {
        this.name       = name;
        this.category   = category;

        if(name.length() == 0)
            System.out.println("Error in LabelCategoryRu.LabelCategoryRu(): empty label category name!");

        // check the uniqueness of the label category and name
        String name_prev = category2name.get(category);
        LabelCategory category_prev = name2category.get(name);

        if(null != name_prev)
            System.out.println("Error in LabelCategoryRu.LabelCategoryRu(): duplication of code! The label category="+category.toString()+
                    " new category name='"+ name +
                    "'. Check the maps name2category and category2name.");

        if(null != category_prev)
            System.out.println("Error in LabelCategoryRu.LabelCategoryRu(): duplication of code! The label category="+category.toString()+
                    " new category name='"+ name +
                    "'. Check the maps name2category and category2name.");

        name2category.put(name, category);
        category2name.put(category, name);
    }

    /** Checks weather exists the LabelCategory by its name in Russian language. */
    public static boolean has(String name) {
        return name2category.containsKey(name);
    }

    /** Checks weather exists the translation for this LabelCategory. */
    public static boolean has(LabelCategory lcat) {
        return category2name.containsKey(lcat);
    }

    /** Gets LabelCategory by its name in some language*/
    public static LabelCategory get(String name) {
        return name2category.get(name);
    }

    /** Get's local name of category, i.e. Russian names of categories for LabelCategoryRu. */
    public static String getName (LabelCategory lcat) {

        String s = category2name.get(lcat);
        if(null == s)
            return lcat.getName(); // if there is no translation into local language, then English name of label category

        return s;
    }

    /** Counts number of translations. */
    public static int size() {
        return name2category.size();
    }

    public static final LabelCategoryLocal 
            empty,
            
            grammatical,
            period,
            qualifier,
            regional,
            usage,
            
            topical,
            computing,
            games,
            mathematics,
            music,
            mythology,
            religion,
            science,
            sports;
    
    static {
        empty = new LabelCategoryRu("пометы‎", LabelCategory.root);
        
        grammatical = new LabelCategoryRu("грамматические пометы‎", LabelCategory.grammatical);
        
        period = new LabelCategoryRu("временные пометы‎", LabelCategory.period); // virtual, absent in ruwikt
        qualifier = new LabelCategoryRu("дополняющие пометы‎", LabelCategory.qualifier); // virtual, absent in ruwikt
        usage = new LabelCategoryRu("стилистические пометы‎", LabelCategory.usage);
        regional = new LabelCategoryRu("пометы языковой принадлежности", LabelCategory.regional);
        
        topical = new LabelCategoryRu("пометы предметных областей‎", LabelCategory.topical);
        
        computing = new LabelCategoryRu("компьютерные ‎пометы‎", LabelCategory.computing); // virtual, absent in ruwikt
        games = new LabelCategoryRu("‎игровые ‎пометы‎", LabelCategory.games); // virtual, absent in ruwikt
        mathematics = new LabelCategoryRu("математические‎ ‎пометы‎", LabelCategory.mathematics); // virtual, absent in ruwikt
        music = new LabelCategoryRu("‎музыкальные ‎пометы‎", LabelCategory.music); // virtual, absent in ruwikt
        mythology = new LabelCategoryRu("‎мифологические ‎пометы‎", LabelCategory.mythology); // virtual, absent in ruwikt
        religion = new LabelCategoryRu("‎религиозные ‎пометы‎", LabelCategory.religion); // virtual, absent in ruwikt
        science = new LabelCategoryRu("‎научные ‎пометы‎", LabelCategory.science); // virtual, absent in ruwikt
        sports = new LabelCategoryRu("спортивные ‎пометы‎‎", LabelCategory.sports); // virtual, absent in ruwikt
    }
    
}
