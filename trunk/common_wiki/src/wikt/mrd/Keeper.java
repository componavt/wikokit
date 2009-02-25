/* Keeper.java - manager stores parsed data to MRD Wiktionary database (wikt_parsed).
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.mrd;

import wikipedia.sql.Connect;
import wikt.word.*;
import wikt.sql.*;

/** Manager stores parsed data to MRD Wiktionary database (wikt_parsed).
 */
public class Keeper {

    
    /** Stores word data to tables of parsed wiktionary database
     *
     * @param conn connection interface to parsed wiktionary database
     * @param word data to be stored to parsed wiktionary database
     */
    public static void storeToDB(Connect conn, WordBase word) {

        String page_title = word.getPageTitle();

        // 1. table 'page', stores page title, gets id of new page
        {
            // move to separate function
            // todo
            // ...

            TPage p = TPage.get(conn, page_title);
            if(null != p) {
                System.err.println("Warning (wikt_parsed Keeper.java storeToDB()):: page_title='" + page_title + "' is in parsed Wiktionary database already!");
                return;
            }

            int word_count = 0;
            // to calculate, todo
            // ...

            int wiki_link_count = 0;
            // to calculate, todo
            // ...

            boolean is_in_wiktionary = true;
            TPage.insert(conn, page_title, word_count, wiki_link_count, is_in_wiktionary);
        }

    // if ! word.isEmpty()

        // 2. table 'wiki_text', stores wiki_text

        // 3. table 'meaning', stores meaning: page_id, lang_id, pos_id, meaning_number
        // definition as wiki_text_id

        // 4. table 'relation', stores relation_id, meaning_id, wiki_text_id,
        // may be: page_id (for simple one-word relation, for relations which are presented in the db)
        //       ? post-processing?

        // 5. table 'translation', stores: translation_id, meaning_summary

        // 6. table 'translation_entry', stores: translation_id, lang_id, wiki_text_id,
        // may be: page_id (for simple one-word translation, for translations which are presented in the db)
        //       ? post-processing?

        int z = 0;
    }

    
    
}
