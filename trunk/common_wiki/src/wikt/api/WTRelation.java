/* WTRelation.java - high-level functions for manipulations with semantic
 *                   relations in Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.api;

import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikt.sql.*;
import wikt.constant.Relation;


/** High-level functions for semantic relations in Wiktionary.
 */
public class WTRelation {

    private final static String[] NULL_STRING_ARRAY = new String[0];


    /** Gets list of semantic relations by page_title and language.
     * 
     * One element in result String[] corresponds to one list of semantic
     * relations of one meanings. With empty "" elements for absent relations.
     */
    public static String[] getForEachMeaningByPageLang(Connect connect,LanguageType lang,
                                                String page_title, Relation rel_type) {

        /*TPage tpage = TPage.get(connect, page_title);
        if(null == tpage)
            return NULL_STRING_ARRAY;

        List<String> definitions = new ArrayList<String>();

        TLangPOS[] lang_pos_all = TLangPOS.get(connect, tpage);
        for(TLangPOS lang_pos : lang_pos_all) {
            if(lang == lang_pos.getLang().getLanguage()) {
                TMeaning[] mm = TMeaning.get(connect, lang_pos);
                for(TMeaning m : mm) {
                    TWikiText twiki_text = m.getWikiText();
                    if(null != twiki_text) {
                        definitions.add(twiki_text.getText());
                    }
                }
            }
        }

        if(definitions.size() > 0)
            return (String[])definitions.toArray(NULL_STRING_ARRAY);

        definitions = null;
         */
        return NULL_STRING_ARRAY;
    }

}
