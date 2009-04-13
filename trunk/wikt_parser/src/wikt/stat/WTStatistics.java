/* WTStatistics.java - Statistics of the database of the parsed Wiktionary.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.stat;

//import wikipedia.sql.Connect;
import wikt.constant.Relation;
import wikipedia.language.LanguageType;

import java.util.Map;
import java.util.Collection;

/** Statistics of the database of the parsed Wiktionary.
 */
public class WTStatistics {


    /** Prints statistics about relations in Wiktionary.
     *
     * @param m_lang_rel_n map of maps with number of synonyms, antonyms, etc.
     * in English, Russian etc. (lang -> relations -> count)
     */
    public static void printRelationsPerLanguage (Map<LanguageType, Map<Relation,Integer>> m_lang_rel_n) {
        
        // print header line
        System.out.print("|| Language code || Languge ");
        //Collection<Relation> all_rel = Relation.getAllRelations();
        Relation[] all_rel = {Relation.synonymy,    Relation.antonymy,
                            Relation.hypernymy,     Relation.hyponymy,
                            Relation.holonymy,      Relation.meronymy};


        for(Relation r : all_rel) {
            System.out.print(" || " + r.toString());
        }
        System.out.println(" || total ||");

        // print values
        for(LanguageType lang : m_lang_rel_n.keySet()) {
            /*if(!m_lang_rel_n.containsKey(lang))
                System.out.println(lang.toString() + " : 0");
            else {*/

                System.out.print("|| " + lang.getCode() + " || " + lang.getName());

                Map<Relation,Integer> rel_n = m_lang_rel_n.get(lang);

                int total = 0; // number of relations for one language: synonyms + antonyms + ...
                for(Relation r : all_rel) {
                    int n = rel_n.containsKey(r) ? rel_n.get(r) : 0;
                    System.out.print(" || " + n);
                    total += n;
                }
                System.out.println(" || " + total + " ||");
            //}
        }

        System.out.println("\nLanguages with relations:" + m_lang_rel_n.size());
    }

    
        

}
