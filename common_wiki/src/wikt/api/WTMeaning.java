/* WTMeaning.java - high-level functions for manipulations of definitions in Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.api;

import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikt.sql.*;
import wikt.constant.POS;

import java.util.List;
import java.util.ArrayList;

/** High-level functions for definitions in Wiktionary.
 */
public class WTMeaning {
    private static final boolean DEBUG = true;
    
    /*public static TMeaning[] getByPage(Connect connect,String word) {
    }*/

    private final static String[] NULL_STRING_ARRAY = new String[0];


    /** Gets list of definitions by page_title and language. */
    public static String[] getDefinitionsByLangPOS(Connect connect,
                                                TLangPOS lang_pos) {
        TPage tpage = lang_pos.getPage();
        if(null == tpage)
            return NULL_STRING_ARRAY;
        
        List<String> definitions = new ArrayList<String>();

        TMeaning[] mm = TMeaning.get(connect, lang_pos);
        for(TMeaning m : mm) {
                                                        // String s_debug = " meaning.id=" + m.getID() + "; _n=" + m.getMeaningNumber();
            TWikiText twiki_text = m.getWikiText();
            if(null != twiki_text) {
                definitions.add(twiki_text.getText());  // + s_debug
            }
        }

        if(definitions.size() > 0)
            return (String[])definitions.toArray(NULL_STRING_ARRAY);

        definitions = null;
        return NULL_STRING_ARRAY;
    }


    /** Gets list of definitions by page_title (for all available POS). */
    public static String[] getDefinitionsByPageLang(Connect connect,
                                                String page_title,
                                                LanguageType lang) {

        TPage tpage = TPage.get(connect, page_title);
        if(null == tpage)
            return NULL_STRING_ARRAY;

        List<String> definitions = new ArrayList<String>();

        TLangPOS[] lang_pos_all = TLangPOS.get(connect, tpage);
        for(TLangPOS lang_pos : lang_pos_all) {
            if(lang == lang_pos.getLang().getLanguage())
            {
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
        return NULL_STRING_ARRAY;
    }


    /** Checks whether the article 'page_title' has any definitions. */
    /*public static boolean hasDefinition(Connect connect,
                                          String page_title) {

        TPage tpage = TPage.get(connect, page_title);
        if(null == tpage)
            return false;

        TLangPOS[] lang_pos_all = TLangPOS.get(connect, tpage);
        for(TLangPOS lang_pos : lang_pos_all) {
            if(TMeaning.get(connect, lang_pos).length > 0)
                return true;
        }

        return false;
    }*/

    /** Gets list of definitions by page_title (for all available languages). */
   /* public static String[] getDefinitionsByPage(Connect connect,
                                                String page_title) {

        TPage tpage = TPage.get(connect, page_title);
        if(null == tpage)
            return NULL_STRING_ARRAY;

        List<String> definitions = new ArrayList<String>();

        TLangPOS[] lang_pos_all = TLangPOS.get(connect, tpage);
        for(TLangPOS lang_pos : lang_pos_all) {
            TMeaning[] mm = TMeaning.get(connect, lang_pos);
            for(TMeaning m : mm) {
                TWikiText twiki_text = m.getWikiText();
                if(null != twiki_text) {
                    definitions.add(twiki_text.getText());
                }
            }
        }

        if(definitions.size() > 0)
            return (String[])definitions.toArray(NULL_STRING_ARRAY);

        definitions = null;
        return NULL_STRING_ARRAY;
    }*/






}
