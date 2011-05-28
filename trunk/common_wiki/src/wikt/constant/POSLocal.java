/* POSLocal.java - names of parts of speech in some language.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.constant;

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


}
