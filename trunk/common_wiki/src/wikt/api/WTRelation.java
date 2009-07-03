/* WTRelation.java - high-level functions for manipulations with semantic
 *                   relations in Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.api;

//import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikt.sql.*;
import wikt.constant.Relation;

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
     * @return null length array, if there is at all relations of this kind
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

}
