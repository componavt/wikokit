/* RelationLocal.java - names of semantic relations in some language.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */


package wikokit.base.wikt.constant;

import java.util.HashMap;
import java.util.Map;


/** Names of semantic relations in some language (e.g. Russian)
 * and the links to the Relation.java codes.
 */
public class RelationLocal {
    
    /** Relation name, e.g. "synonymy" */
    private final String name;

    /** Short Relation name, e.g. "syn." for noun */
    private final String short_name;

    /** Relation corresponding to this name, e.g. Relation.synonymy */
    private final Relation relation;

    
    protected final static Map<String, Relation> name2rel = new HashMap<String, Relation>();
    protected final static Map<Relation, String> rel2name = new HashMap<Relation, String>();
    protected final static Map<Relation, String> rel2name_short = new HashMap<Relation, String>();
    
    
    protected RelationLocal (String _name,String _short_name, Relation _rel) {
        this.name       = _name;
        this.short_name = _short_name;
        this.relation   = _rel;

        if(name.length() == 0)
            System.out.println("Error in RelationRu.RelationRu(): empty part of speech name! The Relation name="+ relation +
                    ". Check the maps name2rel and rel2name.");

        // check the uniqueness of the Relation and name
        String name_prev = rel2name.get( relation );
        Relation rel_prev = name2rel.get(name);

        if(null != name_prev)
            System.out.println("Error in RelationRu.RelationRu(): duplication of Relation! Relation="+ relation +
                    " name='"+ name + "'. Check the maps name2rel and rel2name.");

        if(null != rel_prev)
            System.out.println("Error in RelationRu.RelationRu(): duplication of Relation! Relation="+ relation +
                    " name='"+ name + "'. Check the maps name2rel and rel2name.");

        name2rel.put(name, relation);
        rel2name.put(relation, name);
        rel2name_short.put(relation, short_name);
    }
    
    /** Checks weather exists Relation by its name in some language. */
    public static boolean hasName(String name) {
        return name2rel.containsKey(name);
    }

    /** Checks weather exists the translation for this Relation. */
    public static boolean has(Relation r) {
        return rel2name.containsKey(r);
    }

    /** Gets Relation by its name in some language*/
    public static Relation get(String name) {
        return name2rel.get(name);
    }

    /** Gets name of Relation in some language. */
    public static String getName (Relation r) {

        String s = rel2name.get(r);
        if(null == s)
            return "";
            //return p.getName(); // if there is no translation into local language, then English name
        return s;
    }

    /** Gets short name of Relation in some language. */
    public static String getShortName (Relation r) {

        String s = rel2name_short.get(r);
        if(null == s) {
            s = rel2name.get(r);
            if(null == s)
                return "";
            //return p.getName(); // if there is no translation into local language, then English name
        }
        return s;
    }

    /** Counts number of translations. */
    public static int size() {
        return name2rel.size();
    }
}
