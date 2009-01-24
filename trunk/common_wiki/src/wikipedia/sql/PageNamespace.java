/*
 * PageNamespace.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.sql;

/** Types of pages (article, category) in Wikipdedia.
 */
public class PageNamespace {
    private final int number;
    
    private PageNamespace(int number) { this.number = number; }
    public int toInt() { return number; }
   
    /** quotation from MediaWiki php:
     * Real namespaces
     *
     * Number 100 and beyond are reserved for custom namespaces;
     * DO NOT assign standard namespaces at 100 or beyond.
     * DO NOT Change integer values as they are most probably hardcoded everywhere
     * see bug #696 which talked about that.
     */
    
    /** Articles namespace, 0. */
    public static final PageNamespace MAIN = new PageNamespace(0);          //public static final byte NS_MAIN        = 0;
    
    /** Categories namespace, 14. */
    public static final PageNamespace CATEGORY = new PageNamespace(14);     //public static final byte NS_CATEGORY    = 14;
    
    /*define('NS_MAIN', 0);
    define('NS_TALK', 1);
    define('NS_USER', 2);
    define('NS_USER_TALK', 3);
    define('NS_PROJECT', 4);
    define('NS_PROJECT_TALK', 5);
    define('NS_IMAGE', 6);
    define('NS_IMAGE_TALK', 7);
    define('NS_MEDIAWIKI', 8);
    define('NS_MEDIAWIKI_TALK', 9);
    define('NS_TEMPLATE', 10);
    define('NS_TEMPLATE_TALK', 11);
    define('NS_HELP', 12);
    define('NS_HELP_TALK', 13);
    define('NS_CATEGORY', 14);
    define('NS_CATEGORY_TALK', 15);*/
    
    /** Returns true if there is namespace for this number. */
    public static boolean isValid(int number) {
        if(number == MAIN.toInt() || 
           number == CATEGORY.toInt()) {
            return true;
        } else {
            return false;
        }
    }
    
    /** Gets PageNamespace by number */
    public static PageNamespace get(int number) throws NullPointerException
    {
        if(number == MAIN.toInt()) {
            return MAIN;
        } else if(number == CATEGORY.toInt()) {
            return CATEGORY;
        } else {
            return null;
            //throw new NullPointerException("Null PageNamespace");
        }
    }
}
