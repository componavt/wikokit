/*
 * DictLanguage.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikidf;

/** Languages of dictionary for lemmatizing (at LemServer in RuPOSTagger)<br>
 * ENGLISH<br>
 * RUSSIAN<br>
 * GERMAN
 *
 * See more in: Effective Java. Programming language Guide. J.Bloch. 
 */
public class DictLanguage {
    
    private final String lang;
    
    private DictLanguage(String lang) { this.lang = lang; }
    
    //public String toString() { return Integer.toString(number); }
    public String toString() { return lang; }
    
    public static final DictLanguage RUSSIAN    = new DictLanguage("RUSSIAN");
    
    public static final DictLanguage ENGLISH    = new DictLanguage("ENGLISH");
    
    public static final DictLanguage GERMAN     = new DictLanguage("GERMAN");
    
    
    /** Gets DictLanguage by name */
    public static DictLanguage get(String lang) throws NullPointerException
    {
        if(lang.equalsIgnoreCase( RUSSIAN.toString())) {
            return RUSSIAN;
        } else if(lang.equalsIgnoreCase( ENGLISH.toString())) {
            return ENGLISH;
        } else if(lang.equalsIgnoreCase( GERMAN.toString())) {
            return GERMAN;
        } else {
            throw new NullPointerException("Null DictLanguage");
        }
    }
}
