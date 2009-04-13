/* WTStatistics.java - Statistics of the database of the parsed Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.sql.Connect;
import wikt.constant.Relation;
import wikipedia.language.LanguageType;

import java.util.Map;
import java.util.HashMap;

/** Statistics of the database of the parsed Wiktionary.
 */
public class WTStatistics {

    /** Counts number of semantic relations for each language.
     *
     * @param connect connection to the database of the parsed Wiktionary
     * @return map of maps with number of synonyms (etc.) in English (etc.)
     */
    public static Map<LanguageType, Map<Relation,Integer>> countRelationsPerLanguage (Connect connect) {
        // lang -> relations -> count

        return null;
    }

}
