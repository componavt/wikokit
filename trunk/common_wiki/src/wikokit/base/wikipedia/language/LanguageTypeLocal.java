/* LanguageTypeLocal.java - names of languages in some language.
 *
 * Copyright (c) 2010-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikipedia.language;


/** Names of languages in some language (e.g. Russian)
 * and the links to the LanguageType codes.
 */
public abstract class LanguageTypeLocal {

    /** Language name, e.g. "Russian" */
    protected String name;

    /** LanguageType corresponding to this name, e.g. LanguageType.ru */
    protected LanguageType type;

}
