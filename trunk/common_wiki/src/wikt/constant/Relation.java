/* Relation.java - list of semantic relations used in all wiktionaries.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.constant;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/** Strictly defined names of semantic relations 
 * used in all wiktionaries.
 */
public class Relation {

     /** Semantic relation name, e.g. synonymy. */
    private final String name;

    @Override
    public String  toString() { return name; }

    /* Set helps to check the presence of elements */
    private static Map<String, Relation> name2relation = new HashMap<String, Relation>();
    //private static Set<String>  name_set = new HashSet<String>();

    private Relation(String _name) {
        name = _name;
        name2relation.put(_name, this);
    }

    /** Checks weather exists a semantic relation by its name. */
    public static boolean has(String name) {
        return name2relation.containsKey(name);
    }

    /** Gets semantic relation by its name */
    public static Relation get(String name) {
        return name2relation.get(name);
    }

    /** Counts number of relations. */
    public static int size() {
        return name2relation.size();
    }

    /** Gets all relations. */
    public static Collection<Relation> getAllRelations() {
        return name2relation.values();
    }

    /** Gets all names of semantic relations. */
    public static Set<String> getAllRelationNames() {
        return name2relation.keySet();
    }
    
    //public static final Relation unknown          = new Relation("unknown");        /** The relation is unknown :( */
    
    public static final Relation synonymy           = new Relation("synonymy");
    public static final Relation antonymy           = new Relation("antonymy");
    public static final Relation hypernymy          = new Relation("hypernymy");
    public static final Relation hyponymy           = new Relation("hyponymy");
    public static final Relation holonymy           = new Relation("holonymy");
    public static final Relation meronymy           = new Relation("meronymy");
    public static final Relation troponymy          = new Relation("troponymy");
    public static final Relation coordinate_term    = new Relation("coordinate term");
    public static final Relation otherwise_related  = new Relation("otherwise related");   // See also
}
