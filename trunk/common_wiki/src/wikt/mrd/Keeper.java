/* Keeper.java - manager stores parsed data to MRD Wiktionary database (wikt_parsed).
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.mrd;

import wikipedia.sql.Connect;
import wikt.util.WikiText;
import wikt.constant.*;
import wikt.word.*;
import wikt.sql.*;
import java.util.Map;

/** Manager stores parsed data to MRD Wiktionary database (wikt_parsed).
 */
public class Keeper {
    // private static boolean DEBUG = true;
    
    /** Stores word data to tables of parsed wiktionary database
     *
     * @param conn connection interface to a parsed wiktionary database
     * @param word data to be stored to a parsed wiktionary database
     */
    public static void storeToDB(Connect conn, WordBase word) {
        
        String page_title = word.getPageTitle();
        
        // table 'page', stores page title, gets id of new page
        int word_count = 0;
        // to calculate, todo ...
        
        int wiki_link_count = 0;
        // to calculate, todo ...

        boolean is_in_wiktionary = true;
        TPage tpage = TPage.getOrInsert(conn, page_title, word_count, wiki_link_count, 
                is_in_wiktionary, word.getRedirect());
        
        if(word.isRedirect())
            return;
        
        WLanguage[] w_languages = word.getAllLanguages();
        for(WLanguage w_lang : w_languages) {
            TLang tlang = TLang.get(w_lang.getLanguage());

            WPOS[] w_pos_all = w_lang.getAllPOS();
            int etymology_n = 0;
            for(WPOS w_pos : w_pos_all) {
                TPOS tpos = TPOS.get(w_pos.getPOS());

                // tpage, tlang, tpos: -> into table 'lang_pos', gets id
                
                String lemma = "";  // todo ...
                TLangPOS lang_pos = TLangPOS.insert(conn, tpage, tlang, tpos, etymology_n, lemma);
                etymology_n ++;

                Map<Relation, WRelation[]> m_relations = w_pos.getAllRelations();
                WTranslation[] translations = w_pos.getAllTranslation();
                WMeaning[] w_meaning_all = w_pos.getAllMeanings();
                for(int i=0; i<w_meaning_all.length; i++) {
                    WMeaning w_meaning = w_meaning_all[i];
                    WikiText definition = w_meaning.getWikiText();
                    TWikiText twiki_text= TWikiText.storeToDB(conn, definition);
                    
                    TMeaning tmeaning = TMeaning.insert(conn, lang_pos, i, twiki_text);
                    
                    TRelation.storeToDB(conn, tmeaning, i, m_relations);

                    if(translations.length > i) // not every meaning is happy to have it's own translation
                        TTranslation.storeToDB(conn, lang_pos, tmeaning, translations[i]);
                        
                    twiki_text = null;  // free memory
                    tmeaning = null;
                }

                // some stubs don't have definition, but they have translations
                if(w_meaning_all.length == 0 && translations.length > 0) {
                    for(int i=0; i<translations.length; i++) {
                        TMeaning tmeaning = TMeaning.insert(conn, lang_pos, i, null);
                        TTranslation.storeToDB(conn, lang_pos, tmeaning, translations[i]);
                    }
                }

                tpos = null;            // free memory
                lang_pos = null;
                translations = null;
            }
            tlang = null;
            w_lang = null;
        }
        tpage = null;                   // free memory
        w_languages = null;
        
        // 4. table 'relation', stores relation_id, meaning_id, wiki_text_id,
        // may be: page_id (for simple one-word relation, for relations which are presented in the db)
        //       ? post-processing?

        // 5. table 'translation', stores: translation_id, meaning_summary

        // 6. table 'translation_entry', stores: translation_id, lang_id, wiki_text_id,
        // may be: page_id (for simple one-word translation, for translations which are presented in the db)
        //       ? post-processing?
    }
}
