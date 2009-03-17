/* TPageInflection.java - SQL operations with the table 'page_inflection' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

/** An operations with the table 'page_inflection' in MySQL wiktionary_parsed database.
 */
public class TPageInflection {

    /** Unique identifier in the table 'page_inflection'. */
    private int id;

    /** Title of the wiki article (usually normalized wordform). */
    private TPage page;

    /** Inflectional wordform. */
    private TInflection inflection;

    /** Term frequency. */
    private int term_freq;

    public TPageInflection(int _id,TPage _page,TInflection _inflection,int _term_freq) {
        id          = _id;
        page        = _page;
        inflection  = _inflection;
        term_freq   = _term_freq;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

}
