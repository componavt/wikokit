/* POSLocal.java - names of parts of speech in some language.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.constant;

import java.util.HashMap;
import java.util.Map;

/** Names of parts of speech in some language (e.g. Russian)
 * and the links to the POS.java codes.
 */
public class POSLocal {

    /** POS name, e.g. "noun" */
    protected String name;

    /** Short POS name, e.g. "n." for noun */
    protected String short_name;

    /** POS corresponding to this name, e.g. POS.noun */
    protected POS pos;
    
    protected final static Map<String, POS> name2pos = new HashMap<String, POS>();
    protected final static Map<POS, String> pos2name = new HashMap<POS, String>();
    protected final static Map<POS, String> pos2name_short = new HashMap<POS, String>();

    /** Remark: run the RelationTableAll.main() in order to check duplicates
     * of part of speech names and short names.
     */
    protected POSLocal(String _name,String _short_name, POS _pos) {
        this.name   = _name;
        this.short_name = _short_name;
        this.pos    = _pos;

        if(name.length() == 0)
            System.out.println("Error in POSRu.POSRu(): empty part of speech name! The POS name="+pos+
                    ". Check the maps name2pos and pos2name.");

        // check the uniqueness of the POS and name
        String name_prev = pos2name.get(pos);
        POS pos_prev = name2pos.get(name);

        if(null != name_prev)
            System.out.println("Error in POSRu.POSRu(): duplication of POS! POS="+pos+
                    " name='"+ name + "'. Check the maps name2pos and pos2name.");

        if(null != pos_prev)
            System.out.println("Error in POSRu.POSRu(): duplication of POS! POS="+pos+
                    " name='"+ name + "'. Check the maps name2pos and pos2name.");

        name2pos.put(name, pos);
        pos2name.put(pos, name);
        pos2name_short.put(pos, short_name);
    }

    /** Checks weather exists POS by its name in Russian language. */
    public static boolean hasName(String name) {
        return name2pos.containsKey(name);
    }

    /** Checks weather exists the translation for this POS. */
    public static boolean has(POS p) {
        return pos2name.containsKey(p);
    }

    /** Gets POS by its name in some language*/
    public static POS get(String name) {
        return name2pos.get(name);
    }

    /** Gets name of POS in Russian. */
    public static String getName (POS p) {

        String s = pos2name.get(p);
        if(null == s)
            return "";
            //return p.getName(); // if there is no translation into local language, then English name
        return s;
    }

    /** Gets short name of POS in Russian. */
    public static String getShortName (POS p) {

        String s = pos2name_short.get(p);
        if(null == s) {
            s = pos2name.get(p);
            if(null == s)
                return "";
            //return p.getName(); // if there is no translation into local language, then English name
        }
        return s;
    }

    /** Counts number of translations. */
    public static int size() {
        return name2pos.size();
    }

}
