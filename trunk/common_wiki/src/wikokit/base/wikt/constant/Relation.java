    /* Relation.java - list of semantic relations used in all wiktionaries.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.constant;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.multi.ru.name.RelationRu;

/** Strictly defined names of semantic relations 
 * used in all wiktionaries.
 * 
 * @see http://en.wiktionary.org/wiki/Wiktionary:Semantic_relations
 */
public class Relation {

    /** Semantic relation name, e.g. synonymy. */
    private final String name;

    /** Short Relation name, e.g. "syn." for synonymy */
    //private final String short_name;

    
    
    /* Set helps to check the presence of elements */
    private static Map<String, Relation> name2relation = new HashMap<String, Relation>();
    //private static Set<String>  name_set = new HashSet<String>();

    private Relation(String _name) {
        name = _name;
        name2relation.put(_name, this);
    }
    
    
    @Override
    public String  toString() { return name; }
    
    /** Gets name of Relation in the language l.
     * If there is no translation then returns Relation name in English */
    public String toString(LanguageType l) {

        String s = "";

        if(l  == LanguageType.ru) {
            s = RelationRu.getName(this);

        } else if(l == LanguageType.en) {
            s = name;
        } else {
            throw new NullPointerException("Relation.toString(LanguageType l): Null LanguageType");
        }

        if(0 == s.length()) // English name is better than nothing
            s = name;

        return s;
    }

    /** Gets short name of Relation in the language l. */
    public String getShortName (LanguageType l) {

        String s = "";

        if(l  == LanguageType.ru) {
            s = RelationRu.getShortName(this);

        } else if(l == LanguageType.en) {
            // skip?
        } else {
            throw new NullPointerException("Relation.getShortName(LanguageType l): Null LanguageType");
        }
       
        return s;
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

    /** Gets all relations (as Array[]).
     * Rem: We are waiting a marvellous day when JavaFX will support Collection<>.
     *      Then this function will be deleted.
     */
    public static Relation[] getAllRelationsOrderedArray() {
        
        return RELATION_ORDERED_ARRAY;
        //return (Relation[])name2relation.values().toArray(NULL_RELATION_ARRAY);
    }

    /** Gets all names of semantic relations. */
    public static Set<String> getAllRelationNames() {
        return name2relation.keySet();
    }

    //public static final Relation unknown          = new Relation("unknown");        /** The relation is unknown :( */

    public static final Relation synonymy           = new Relation("synonyms");
    public static final Relation antonymy           = new Relation("antonyms");
    public static final Relation relational_antonym = new Relation("relational antonym"); // ruwikt
    public static final Relation hypernymy          = new Relation("hypernyms");
    public static final Relation hyponymy           = new Relation("hyponyms");
    public static final Relation holonymy           = new Relation("holonyms");
    public static final Relation meronymy           = new Relation("meronyms");
    public static final Relation troponymy          = new Relation("troponyms");
    public static final Relation coordinate_term    = new Relation("coordinate terms");
    public static final Relation otherwise_related  = new Relation("see also");   // See also

    
    //private final static Relation[] NULL_RELATION_ARRAY = new Relation[0];
    private final static Relation[] RELATION_ORDERED_ARRAY = {
        Relation.synonymy,  Relation.antonymy, Relation.relational_antonym,
        Relation.hypernymy, Relation.hyponymy,
        Relation.holonymy, Relation.meronymy,
        Relation.troponymy, Relation.coordinate_term,
        Relation.otherwise_related
        //  // converse
    };
    
    

}
