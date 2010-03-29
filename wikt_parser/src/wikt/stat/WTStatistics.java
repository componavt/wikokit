/* WTStatistics.java - Statistics of the database of the parsed Wiktionary.
 *
 * Copyright (c) 2009-2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.stat;

//import wikipedia.sql.Connect;
import wikt.constant.Relation;
import wikipedia.language.LanguageType;

import java.util.Map;
//import java.util.Collection;

/** Statistics of the database of the parsed Wiktionary.
 *
 * The result could be inserted into the Wiktionary page.
 * @see http://ru.wiktionary.org/wiki/User:AKA MBG/Статистика:Семантические отношения
 * @see todo
 */
public class WTStatistics {

    public static void printHeader (String db_name) {

        System.out.println("\nThe parsed database name: " + db_name +"<ref>" +
                "This (or more recent) database would be available at the project site " +
                "[http://code.google.com/p/wikokit wikokit], see Download section." +
                "</ref>");
    }
    public static void printFooter () {

        System.out.println("\n== References ==\n<references />\n");
    }

    /** Prints statistics about relations in Wiktionary.
     *
     * @param m_lang_rel_n map of maps with number of synonyms, antonyms, etc.
     * in English, Russian etc. (lang -> relations -> count)
     */
    public static void printRelationsPerLanguage (
                        String db_name,
                        Map<LanguageType, Map<Relation,Integer>> m_lang_rel_n) {

        // print header line
        System.out.println("{| class=\"sortable prettytable\"");
        System.out.print("! Language name || Language code ||");

        //Collection<Relation> all_rel = Relation.getAllRelations();
        Relation[] all_rel = {  Relation.synonymy,  Relation.antonymy,
                                Relation.hypernymy, Relation.hyponymy,
                                Relation.holonymy,  Relation.meronymy};
        
        System.out.print(" total"); // " Number of semantic relations"
        for(Relation r : all_rel) {
            System.out.print(" || " + r.toString());
        }

        // print values
        for(LanguageType lang : m_lang_rel_n.keySet()) {
            /*if(!m_lang_rel_n.containsKey(lang))
                System.out.println(lang.toString() + " : 0");
            else {*/

                //System.out.print("|| " + lang.getName() + " || " + lang.getCode());
            System.out.println("\n|-\n! " + lang.getName() + " || " + lang.getCode());
//|-
//! Abaza
            
            Map<Relation,Integer> rel_n = m_lang_rel_n.get(lang);

            int total = 0; // number of relations for one language: synonyms + antonyms + ...
            for(Relation r : all_rel)
                total += (rel_n.containsKey(r) ? rel_n.get(r) : 0);
            System.out.print("|| " + total);

            for(Relation r : all_rel) {
                int n = rel_n.containsKey(r) ? rel_n.get(r) : 0;
                System.out.print(" || " + n);
                total += n;
            }
        }
        System.out.println("\n|}");
    }
    
    /** Prints statistics about translations in Wiktionary.
     *
     * @param m_lang_n map of maps with number of translations into
     * English, Russian etc. (lang -> count)
     */
    public static void printTranslationPerLanguage (Map<LanguageType, Integer> m_lang_n) {

        int total = 0; // total number of translations
        
        // print header line
        // print header line
        System.out.println("{| class=\"sortable prettytable\"");
        System.out.println("! Language name || Language code || Number");
        
        // print values
        for(LanguageType lang : m_lang_n.keySet()) {
            /*if(!m_lang_rel_n.containsKey(lang))
                System.out.println(lang.toString() + " : 0");
            else {*/
                
                int n = m_lang_n.get(lang);
                System.out.println("|-\n! " + lang.getName() + " || " + lang.getCode() + " || " + n);
                //System.out.print("|| " + lang.getCode() + " || " + lang.getName());
                // System.out.println(" || " + n + " ||");
                
                total += n;
            //}
        }
        System.out.println("|}");
        
        System.out.println(  "Total translations: " + total);
    }
    
}
