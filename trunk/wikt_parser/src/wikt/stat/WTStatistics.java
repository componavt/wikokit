/* WTStatistics.java - Statistics of the database of the parsed Wiktionary.
 *
 * Copyright (c) 2009-2010 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.stat;

//import wikipedia.sql.Connect;
import wikt.constant.Relation;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikt.sql.*;
import wikt.api.WTRelation;

import java.util.Map;
import java.util.List;

/** Statistics of the database of the parsed Wiktionary.
 *
 * The result could be inserted into the Wiktionary page.
 * @see http://ru.wiktionary.org/wiki/User:AKA MBG/Статистика:Семантические_отношения
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
                        Map<LanguageType, Map<Relation,Integer>> m_lang_rel_n) {

        // print header line
        System.out.println("=== Number of relations per languge ===\n");

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
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
    
    /** Prints statistics-histogram about number of relations in Wiktionary.
     *
     * @param histogram with number of semantic relations, i.e.
     * [0] = number of words (one language, one part of speech) without any semantic relations,
     * [1] = number of words with one relation, etc.
     */
    public static void printRelationsHistogram (int[] rel_histogram) {

        // print header line
        System.out.println("\n=== Number of words per number of relations ===\n");

        System.out.println("Number of words which have the following number of semantic relations.");
        System.out.println("\n0 | number of words (one language, one part of speech) without any semantic relations");
        System.out.println("\n1 | number of words with one relation, e.g. one synonym or one antonym, etc.\n");

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.print("! Number of relations || Number of words");
        
        for(int i=0; i<rel_histogram.length; i++) {

            int n_rel = rel_histogram[i];

            if(0 == n_rel)
                continue;

                //System.out.print("|| " + lang.getName() + " || " + lang.getCode());
            System.out.print("\n|-\n! " + i + " || " + n_rel);
        }
        System.out.println("\n|}");
    }


    
    /** Prints the words with the maximum number of semantic relations.
     *
     * @param words_rich_in_relations list of the words with the maximum number
     *                              of semantic relations
     * @param threshold_relations the minimum number of relations of these words
     */
    public static void printWordsWithManyRelations (
                        Connect wikt_parsed_conn,
                        List<TLangPOS> words_rich_in_relations,
                        int threshold_relations) {
        
        // print header line
        System.out.println("\n=== Number of words rich in relations ===\n");

        System.out.println("There are " + words_rich_in_relations.size() + 
                " words which have >= " + threshold_relations + " semantically related words.");

        System.out.println("\n{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.print("! Word || Number of relations || POS || Language code || Language name");
        
        for(TLangPOS lang_pos : words_rich_in_relations) {
            
            String page_title = lang_pos.getPage().getPageTitle();
            String pos = lang_pos.getPOS().getPOS().toString();
            String lang_code = lang_pos.getLang().getLanguage().getCode();
            String lang_name = lang_pos.getLang().getLanguage().getName();

            int n_relation = WTRelation.getNumberByPageLang(wikt_parsed_conn, lang_pos);

            System.out.print("\n|-\n|| [[" + page_title + "]] || " + n_relation +
                    " || " + pos + " || " + lang_code + " || " + lang_name);
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
                System.out.println("|-\n! " + lang.getName() + " || " + lang.getCode() + "\n|| " + n);
                //System.out.print("|| " + lang.getCode() + " || " + lang.getName());
                // System.out.println(" || " + n + " ||");
                
                total += n;
            //}
        }
        System.out.println("|}");
        
        System.out.println(  "Total translations: " + total);
    }
    
}
