/* LabelCategoryLocal.java - auxiliary class supporting categories of context 
 * labels (templates) presented in the sections: definitions, Synonyms, 
 * and Translations.
 * 
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.constant;

/** Auxiliary class supporting interlinking categories of context labels 
 * (templates) of English Wiktionary with other wiktionaries.
 * 
 * Names of labels categories in some language (e.g. Russian)
 * and the links to the LabelCategory names.
 */
public class LabelCategoryLocal {
    
    /** Name of labels category, e.g. "Грамматические" (grammatical) in "Russian" */
    protected String name;

    /** LabelCategory corresponding to this name, e.g. LabelCategory.grammatical */
    protected LabelCategory category;
}
