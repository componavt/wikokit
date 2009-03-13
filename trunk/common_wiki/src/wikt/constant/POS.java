/* POS.java - list of parts of speech used in all wiktionaries.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.constant;

import java.util.Set;
//import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/** Strictly defined names of parts of speech
 * used in all wiktionaries.
 */
public class POS {

    /** POS name, e.g. acronym */
    private final String name;
    
    @Override
    public String  toString() { return name; }
    
    /* Set helps to check the presence of elements */
    private static Map<String, POS> name2type = new HashMap<String, POS>();
    //private static Set<String>  name_set = new HashSet<String>();
    
    private POS(String _name) {
        //super(name);  // old
        
        name = _name;   // new
        //name_set.add(_name);
        name2type.put(_name, this);
    }
    
    /** Checks weather exists the part of speech by its name. */
    public static boolean has(String _name) {
        //return name_set.contains(_name);
        return name2type.containsKey(_name);
    }
    
    /** Gets part of speech by its name */
    public static POS get(String _name) {
        return name2type.get(_name);
        //return name_set.get(_name);
    }

    /** Counts number of POS. */
    public static int size() {
        return name2type.size();
    }
    
    /** Gets all POS. */
    public static Collection<POS> getAllPOS() {
        return name2type.values();
    }

    /** Gets all POS. */
    public static Set<String> getAllPOSNames() {
        return name2type.keySet();
    }

    /*
    private final int number;
    private PageNamespace(int number) { this.number = number; }
    public int toInt() { return number; }
    public static final PageNamespace MAIN = new PageNamespace(0);          //public static final byte NS_MAIN        = 0;
    */
    
    /** The POS is unknown :( */
    public static final POS unknown         = new POS("unknown");
    
     // The classical parts of speech are:
    public static final POS noun            = new POS("noun");
    public static final POS verb            = new POS("verb");
    public static final POS adverb          = new POS("adverb");
    public static final POS adjective       = new POS("adjective");
    public static final POS pronoun         = new POS("pronoun");
    public static final POS conjunction     = new POS("conjunction");
    public static final POS interjection    = new POS("interjection");
    public static final POS preposition     = new POS("preposition");
    
    
    // Additional commonly used grammatical headers are:
    public static final POS proper_noun     = new POS("proper noun");
    public static final POS article         = new POS("article");
    public static final POS prefix          = new POS("prefix");
    public static final POS suffix          = new POS("suffix");
    
    public static final POS phrase          = new POS("phrase");
    public static final POS idiom           = new POS("idiom");
    
    
    // other descriptors that identify the usage of the entry, but which are not (strictly speaking) parts of speech:
    public static final POS acronym         = new POS("acronym");
    
    public static final POS abbreviation    = new POS("abbreviation");
    public static final POS initialism      = new POS("initialism");
    
    public static final POS symbol          = new POS("symbol");
    public static final POS letter          = new POS("letter");
    
    // other headers in use
    public static final POS particle        = new POS("particle");
    
}
