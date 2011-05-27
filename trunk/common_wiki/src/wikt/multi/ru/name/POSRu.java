/* POSLocal.java - names of parts of speech in some language.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.multi.ru.name;

import wikt.constant.POSLocal;
import wikt.constant.POS;

import java.util.HashMap;
import java.util.Map;


/** Names of parts of speech in Russian and the links to the POS.java codes.
 */
public class POSRu extends POSLocal {

    protected final static Map<String, POS> name2pos = new HashMap<String, POS>();
    protected final static Map<POS, String> pos2name = new HashMap<POS, String>();
    protected final static Map<POS, String> pos2name_short = new HashMap<POS, String>();

    /** Remark: run the RelationTableAll.main() in order to check duplicates
     * of language names and language codes of this locale.
     */
    protected POSRu(String _name,String _short_name, POS _pos) {
        this.name   = _name;
        this.short_name = _short_name;
        this.pos    = _pos;

        if(name.length() == 0)
            System.out.println("Error in POSRu.POSRu(): empty part of speech name! The POS code="+pos+
                    ". Check the maps name2pos and pos2name.");

        // check the uniqueness of the POS and name
        String name_prev = pos2name.get(pos);
        POS pos_prev = name2pos.get(name);

        if(null != name_prev)
            System.out.println("Error in POSRu.POSRu(): duplication of POS! POS="+pos+
                    " name='"+ name + "'. Check the maps name2pos and pos2name.");

        if(null != pos_prev)
            System.out.println("Error in POSRu.POSRu(): duplication of language! The language code="+pos+
                    " name='"+ name + "'. Check the maps name2pos and pos2name.");

        name2pos.put(name, pos);
        pos2name.put(pos, name);
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

    public static final POSLocal unknown = new POSRu("Неизвестная часть речи", "неизв.", POS.unknown);

    // The classical parts of speech are:
    public static final POSLocal noun = new POSRu("Существительное", "сущ.", POS.noun);
    public static final POSLocal verb = new POSRu("Глагол", "гл.", POS.verb);
    public static final POSLocal adverb = new POSRu("Наречие", "нар.", POS.adverb);
    public static final POSLocal adjective = new POSRu("Прилагательное", "прил.", POS.adjective);
    public static final POSLocal pronoun = new POSRu("Местоимение", "мест.", POS.pronoun);

    /*
    public static final POSLocal  = new POSRu("", ".", POS.);
    public static final POS conjunction     = new POS("conjunction");

    public static final POSLocal  = new POSRu("", ".", POS.);
    public static final POS interjection    = new POS("interjection");

    public static final POSLocal  = new POSRu("", ".", POS.);
    public static final POS preposition     = new POS("preposition");
*/
    
}
