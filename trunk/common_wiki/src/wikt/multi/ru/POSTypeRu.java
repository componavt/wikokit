/* POSTypeRu.java - Russian names of parts of speech.
 * 
 * Copyright (c) 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.multi.ru;

import wikt.constant.POSType;
import wikt.constant.POS;

import java.util.Map;
import java.util.HashMap;

/** Russian names of parts of speech.
 * 
 * See http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A7%D0%B0%D1%81%D1%82%D0%B8_%D1%80%D0%B5%D1%87%D0%B8
 *     http://ru.wiktionary.org/wiki/Викисловарь:Части речи
 */
public class POSTypeRu extends POSType {

    /** POS name encountered in the Wiktionary, e.g.: {{acronym}} or Acronym, 
     * since there are ==={{acronym}}=== and ===Acronym===
     */
    private final String name_in_text;
    
    /** POS name in Russian, e.g. "Акроним" for "acronym" */
    //private final String name; // todo ... HashMap type -> Russian POS name
    
    /** POS */
    private final POS type; 
    
    //private static Map<String, String>  text2name = new HashMap<String, String>();
    private static Map<String, POS> name2type = new HashMap<String, POS>();
    
    /** Initialization for POSTypeEn, POSTypeRu, etc. */
    private POSTypeRu(String name_in_text, POS type) {
        this.name_in_text   = name_in_text;
        this.type           = type;         // english.english;
        name2type.put(name_in_text, type); // english.english);
    }
    
    public String getName() { return type.toString(); }
    
    /** Checks weather exists the part of speech by its abbreviation 'code'. */
    public static boolean has(String code) {
        return name2type.containsKey(code);
    }
    
    /** Gets part of speech by its abbreviation */
    public static POS get(String code) {
        return name2type.get(code);
    }
    
    
    
    
    // The classical parts of speech are:
    
    // ===Морфологические и синтаксические свойства===
    // {{СущМужНеодуш1c(1)
    // {{СущЖенНеодуш8a
    // Существительное, ...
    public static final POSType noun    = new POSTypeRu("сущ",      POS.noun);  // "существительное",
    public static final POSType noun_old= new POSTypeRu("падежи",   POS.noun);  // "существительное",
    
    // ===Морфологические и синтаксические свойства===
    // {{парадигма-рус
    // |шаблон=Гл11b/c
    //
    // {{Гл1a
    public static final POSType verb        = new POSTypeRu("гл",       POS.verb);  // "глагол",
    public static final POSType verb_old_ru = new POSTypeRu("Глагол",   POS.verb);
    
    // {{adv-ru|
    // Наречие, неизменяемое.
    public static final POSType adverb_template     = new POSTypeRu("adv",      POS.adverb); // "наречие", adv ru, adv-ru
    public static final POSType adverb_word         = new POSTypeRu("Наречие",  POS.adverb); // "наречие", 
    
    
    // {{прил en|round|слоги=round}}
    public static final POSType adjective           = new POSTypeRu("прил",     POS.adjective); // "прилагательное",
    public static final POSType adjective_old_en    = new POSTypeRu("Adjective",POS.adjective);
    
    
    // other headers in use
    public static final POSType particle1            = new POSTypeRu("part",     POS.particle); // частица, part ru, part-ru
    public static final POSType particle3            = new POSTypeRu("Particle", POS.particle);  
    
}
