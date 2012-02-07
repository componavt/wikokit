/* ContextLabel.java - contexual information for definitions, or Synonyms,
 *                     or Translations.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikokit.base.wikt.constant;

import java.util.Map;
import java.util.HashMap;

/** Contexual information for definitions, such as archaic, by analogy, 
 * chemistry, etc.
 *
 * See http://en.wiktionary.org/wiki/Template_talk:context
 */
public abstract class ContextLabel {
       
    /** Two (or more) letter label code, e.g. 'устар.', 'п.'. */
    private final String label;
    
    /** Label name, e.g. 'устарелое', 'переносное значение'. */
    private final String name;
    
    /** Category associated with this label. */
    private final String category;
    
    private static Map<String, String> label2name     = new HashMap<String, String>();
    private static Map<String, String> label2category = new HashMap<String, String>();
    
    protected ContextLabel(String label,String name,String category) { 
        this.label      = label; 
        this.name       = name; 
        this.category   = category; 
        label2name.     put(label, name);
        label2category. put(label, category);
    }
}
