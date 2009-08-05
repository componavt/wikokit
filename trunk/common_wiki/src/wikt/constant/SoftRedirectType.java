/* SoftRedirectType.java - list of types of soft redirects used in all wiktionaries.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.constant;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/** Names of types of soft redirects used in all wiktionaries.
 *
 * @see Wiktionary:Redirections and Help:Redirect in English Wiktionary
 * @see TPage.is_redirect - a hard redirect.
 */
public class SoftRedirectType {

    /** Name of a redirect type, e.g. SpellingError */
    private final String name;

    @Override
    public String  toString() { return name; }

    /* Set helps to check the presence of elements */
    private static Map<String, SoftRedirectType> name2type = new HashMap<String, SoftRedirectType>();

    private SoftRedirectType (String _name) {
        name = _name;
        name2type.put(_name, this);
    }

    /** Checks weather exists the type by its name. */
    public static boolean has(String _name) {
        return name2type.containsKey(_name);
    }

    /** Gets a type by its name */
    public static SoftRedirectType get(String _name) {
        return name2type.get(_name);
    }

    
    /** The types of soft redirects are: */
    /*************************************/

    /** It's not a redirect, it is the usual Wiktionary entry */
    public static final SoftRedirectType None       = new SoftRedirectType("None");

    /** Wordform - soft redirect to correct spelling. */
    public static final SoftRedirectType Wordform   = new SoftRedirectType("Wordform");

    /** Misspelling - soft redirect to correct spelling {{misspelling of|}} or {{wrongname|}}. */
    public static final SoftRedirectType Misspelling  = new SoftRedirectType("Misspelling");
}
