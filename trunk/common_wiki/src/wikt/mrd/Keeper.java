/* Keeper.java - manager stores parsed data to MRD Wiktionary database (wikt_parsed).
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.mrd;

import wikipedia.sql.Connect;
import wikt.word.*;

/** Manager stores parsed data to MRD Wiktionary database (wikt_parsed).
 */
public class Keeper {

    

    public static void storeToDB(Connect wikt_parsed_conn, WordBase word) {

        // 1. table 'page', stores page title, gets id of new page

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
