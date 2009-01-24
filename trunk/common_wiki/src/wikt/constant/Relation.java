/* Relation.java - list of semantic relations used in all wiktionaries.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.constant;

import java.util.Set;
import java.util.HashSet;

/** Strictly defined names of semantic relations 
 * used in all wiktionaries.
 */
public class Relation {

     /** Semantic relation name, e.g. synonymy. */
    private final String name;

    @Override
    public String  toString() { return name; }

    /* Set helps to check the presence of elements */
    private static Set<String>  name_set = new HashSet<String>();

    private Relation(String _name) {
        name = _name;
        name_set.add(_name);
    }
    
    //public static final Relation unknown          = new Relation("unknown");        /** The relation is unknown :( */

    public static final Relation synonymy           = new Relation("synonym");
    public static final Relation antonymy           = new Relation("antonymy");
    public static final Relation hypernymy          = new Relation("hypernymy");
    public static final Relation hyponymy           = new Relation("hyponymy");
    public static final Relation holonymy           = new Relation("holonymy");
    public static final Relation meronymy           = new Relation("meronymy");
    public static final Relation troponymy          = new Relation("troponymy");
    public static final Relation coordinate_term    = new Relation("coordinate term");
    public static final Relation otherwise_related  = new Relation("otherwise related");   // See also
}
