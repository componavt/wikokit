/* WTRelation.java - high-level functions for manipulations with semantic
 *                   relations in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.api;

import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TRelation;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.constant.Relation;

import java.util.List;
import java.util.ArrayList;

/** High-level functions for semantic relations in Wiktionary.
 */
public class WTRelation {

    private final static String[] NULL_STRING_ARRAY = new String[0];


    /** Gets list of semantic relations by page_title and language.
     * 
     * One element in result String[] corresponds to one list of semantic
     * relations of one meanings. With empty "" elements for absent relations.
     *
     * @return zero length array, if there is no at all relations of this kind
     */
    public static String[] getForEachMeaningByPageLang(Connect connect,
                                                TLangPOS lang_pos, //LanguageType lang,
                                                Relation rel_type) {

        TPage tpage = lang_pos.getPage();
        if(null == tpage)
            return NULL_STRING_ARRAY;

        List<String> relations_lists = new ArrayList<String>();
        boolean b_relation = false;

        TMeaning[] mm = TMeaning.get(connect, lang_pos);
        for(TMeaning m : mm) {

            TRelation[] rels = TRelation.get(connect, m);

            String list = "";
            for(TRelation r : rels) {
                if(r.getRelationType() == rel_type) {
                    list = list + r.getWikiText().getText() + ", ";
                    b_relation = true;  // at least one relation exists.
                }
            }
            
            int len = list.length();
            if(len > 0)
                relations_lists.add(list.substring(0, len - 2));
            else
                relations_lists.add("");    // corresponds to the absent list: "# - "
        }
        
        if(!b_relation)
            return NULL_STRING_ARRAY;
        assert(relations_lists.size() > 0);
        
        return (String[])relations_lists.toArray(NULL_STRING_ARRAY);
    }

    /** Gets the number of semantic relations by page_title and language. The sum
     * of relations for each type of semantic relation, for all meanings.
     *
     * @return 0, if there is no at all relations of this kind
     */
    public static int getNumberByPageLang(Connect connect,
                                            TLangPOS lang_pos) {
        int n_rels = 0;
        TMeaning[] mm = TMeaning.get(connect, lang_pos);
        for(TMeaning m : mm)
            n_rels += TRelation.count(connect, m);

        return n_rels;
    }
}
