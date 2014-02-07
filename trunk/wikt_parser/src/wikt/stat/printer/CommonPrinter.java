/* WikiPrinterStat.java - Printer (in wiki format) of statistics
 *                  of the newly created (parsed) database of the Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.stat.printer;

//import wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikipedia.language.LanguageType;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.api.WTRelation;

import java.util.Map;
import java.util.List;

/** Statistics of the database of the parsed Wiktionary.
 *
 * The result could be inserted into the Wiktionary page.
 * @see http://ru.wiktionary.org/wiki/User:AKA MBG/Статистика:Семантические_отношения
 * @see todo
 */
public class CommonPrinter {

    public static void printHeader (String db_name) {

        System.out.println("\nThe parsed database name: " + db_name +"<ref>" +
                "This (or more recent) database would be available at the project site " +
                "[http://code.google.com/p/wikokit wikokit], see Download section at page [http://whinger.krc.karelia.ru/soft/wikokit/index.html whinger.krc.karelia.ru]." +
                "</ref>");
    }
    public static void printHeaderXML (String db_name) {

        System.out.println("<!-- The parsed database name: " + db_name +". -->");
        System.out.println("<!-- This (or more recent) database would be available at the project site (http://code.google.com/p/wikokit), see Download section at page (http://whinger.krc.karelia.ru/soft/wikokit/index.html). -->");
    }
    public static void printFooter () {

        System.out.println("\n== References ==\n<references />\n");
    }

    /** Prints statistics about relations in Wiktionary.
     *
     * @param m_lang_rel_n map of maps with number of synonyms, antonyms, etc.
     * in English, Russian etc. (lang -> relations -> count)
     *
     * @param m_lang_entries_number number of (Language & POS level) entries per language
     */
    public static void printRelationsPerLanguage (
                        LanguageType native_lang,
                        Map<LanguageType, Map<Relation,Integer>> m_lang_rel_n,
                        Map<LanguageType,Integer> m_lang_entries_number)
    {
        // print header line
        System.out.println("=== Number of relations per language ===");

        System.out.println("\n'''Number of entries''' is a number of (Language & POS level) entries per language. E.g. the Wiktionary article \"[[:en:rook|rook]]\" contains three English and two Dutch entries of Part Of Speech level.");
        System.out.println("\n'''Total''' is a total number of relations, i.e. synonyms + antonyms + etc...\n");
        
        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.println("! Language name");
        System.out.println("! Template");

        if(LanguageType.en != native_lang)
            System.out.println("! in " + native_lang.getName());

        System.out.println("! Number of entries");

        //Collection<Relation> all_rel = Relation.getAllRelations();
        Relation[] all_rel = {  Relation.synonymy,  Relation.antonymy,
                                Relation.hypernymy, Relation.hyponymy,
                                Relation.holonymy,  Relation.meronymy};
        
        System.out.println("! total"); // " Number of semantic relations"
        for(Relation r : all_rel) {
            System.out.println("! " + r.toString());
        }

        // print values
        for(LanguageType lang : m_lang_rel_n.keySet()) {
            /*if(!m_lang_rel_n.containsKey(lang))
                System.out.println(lang.toString() + " : 0");
            else {*/

                //System.out.print("|| " + lang.getName() + " || " + lang.getCode());
            System.out.println("|-");
            System.out.print("|" + lang.getName() + "||{{" + lang.getCode() + "}}");
            if(LanguageType.en != native_lang) {
                String local_name = "";
                if (lang.hasTranslation(native_lang))
                    local_name = lang.translateTo(native_lang);
                System.out.print("||" + local_name);
            }
            System.out.print("||" + m_lang_entries_number.get(lang));

//|-
//! Abaza            
            Map<Relation,Integer> rel_n = m_lang_rel_n.get(lang);

            int total = 0; // number of relations for one language: synonyms + antonyms + ...
            for(Relation r : all_rel)
                total += (rel_n.containsKey(r) ? rel_n.get(r) : 0);
            System.out.print("||" + total);

            for(Relation r : all_rel) {
                int n = rel_n.containsKey(r) ? rel_n.get(r) : 0;
                System.out.print("||" + n);
                total += n;
            }
            System.out.println();
        }
        System.out.println("|}");
    }

    /** Prints statistics about number of words per number of relation types in Wiktionary.
     *
     * @param  ...
     */
    public static void printRelationsTypeHistogram (
                                int[] rel_type_histogram,
                                Map<Relation,Integer>[] m_relation_type_number
            ) {
        System.out.println("=== Number of words per number of relation types ===\n");

        System.out.println("Number of words which have the following number of types of semantic relations. E.g.:");

        System.out.println("\n1 | number of words (one language, one part of speech) which have only Synonyms, or only Antonyms, etc.");
        System.out.println("\n2 | number of words with two types of relation, e.g. Synonymy and Antonymy, or Synonymy and Hypernymy, etc.\n");


        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.println("! Number of relation types");
        System.out.println("! Number of words");

        //Collection<Relation> all_rel = Relation.getAllRelations();
        Relation[] all_rel = {  Relation.synonymy,  Relation.antonymy,
                                Relation.hypernymy, Relation.hyponymy,
                                Relation.holonymy,  Relation.meronymy,
                                Relation.troponymy, Relation.coordinate_term,
                                Relation.otherwise_related
        };

        System.out.println("! total"); // " Number of semantic relations"
        for(Relation r : all_rel)
            System.out.println("! " + r.toString());

        for(int i=1; i < rel_type_histogram.length; i++) {
            System.out.println("|-");
            System.out.print("|" + i + "||" + rel_type_histogram[i]);
//|-
//! Abaza

            Map<Relation,Integer> rel_n = m_relation_type_number[i];

            if(null == rel_n) {
                System.out.print("||0");
                for(Relation r : all_rel)
                    System.out.print("||0");

            } else {

                int total = 0; // number of relations for one language: synonyms + antonyms + ...
                for(Relation r : all_rel)
                    total += (rel_n.containsKey(r) ? rel_n.get(r) : 0);
                System.out.print("||" + total);

                for(Relation r : all_rel) {
                    int n = rel_n.containsKey(r) ? rel_n.get(r) : 0;
                    System.out.print("||" + n);
                    total += n;
                }
            }
            System.out.println();
        }
        System.out.println("\n|}");
    }

    /** Maximum "number of relations" will be printed in the table:
     * (2) Number of words per number of relations
     * @see http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:Semantic_relations#Number_of_words_per_number_of_relations
     */
    //static final Integer max_relations_to_print = 50;

    /** Prints statistics-histogram about number of relations in Wiktionary.
     *
     * @param max_values_to_print values rel_histogram[0..max_values_to_print-1] will be printed
     * @param histogram with number of semantic relations, i.e.
     * [0] = number of words (one language, one part of speech) without any semantic relations,
     * [1] = number of words with one relation, etc.
     */
    public static void printRelationHistogram (int[] rel_histogram, int max_values_to_print) {

        // print header line
        System.out.println("\n=== Number of words per number of relations ===\n");

        System.out.println("Number of words which have the following number of semantic relations. E.g.:");
        System.out.println("\n0 | number of words (one language, one part of speech) without any semantic relations");
        System.out.println("\n1 | number of words with one relation, e.g. one synonym or one antonym, etc.\n");
        System.out.println("\nOnly the first " + max_values_to_print + " rows are presented in the table.");

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.println("! Number of relations");
        System.out.println("! Number of words");

        int max = Math.min(rel_histogram.length, max_values_to_print);
        for(int i=0; i<max; i++) {

            int n_rel = rel_histogram[i];

            if(0 == n_rel)
                continue;

                //System.out.print("|| " + lang.getName() + " || " + lang.getCode());
            System.out.println("|-");
            System.out.println("|" + i + "||" + n_rel);
        }
        System.out.println("|}");
    }


    
    /** Prints the words with the maximum number of semantic relations.
     *
     * @param words_rich_in_relations list of the words with the maximum number
     *                              of semantic relations
     * @param threshold_relations the minimum number of relations of these words
     */
    public static void printWordsWithManyRelations (
                        LanguageType native_lang,
                        Connect wikt_parsed_conn,
                        List<TLangPOS> words_rich_in_relations,
                        int threshold_relations_foreign, int threshold_relations_native,
                        int threshold_type_relations) {
        
        // print header line
        System.out.println("\n=== List of words with many semantic relations ===\n");

        System.out.println("There are " + words_rich_in_relations.size() + 
                " words which have >= " + threshold_relations_native + " ("+ native_lang.getName() +"), " +
                                "  >= " + threshold_relations_foreign + " (other languages) " +
                " semantically related words or >= " + threshold_type_relations +
                " types of semantic relations.");

        System.out.println("\n{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.println("! Word");
        System.out.println("! Number<br>of<br>relations");
        System.out.println("! Types<br>of<br>semantic<br>relations");
        System.out.println("! Number<br>of<br>meanings");
        System.out.println("! POS");
        System.out.println("! Language name");

        for(TLangPOS lang_pos : words_rich_in_relations) {
            
            String page_title = lang_pos.getPage().getPageTitle();
            String pos = lang_pos.getPOS().getPOS().toString();
            String lang_code = lang_pos.getLang().getLanguage().getCode();
            String lang_name = lang_pos.getLang().getLanguage().getName();
            int n_meaning = lang_pos.countMeanings();

            int n_relation = WTRelation.getNumberByPageLang(wikt_parsed_conn, lang_pos);
            int n_types_relation = lang_pos.countRelationTypes();

            // wikitext has problems with a symbol '/', so print [[:/]] instead of a subpage link: [[/]]
            if(page_title.equalsIgnoreCase("/"))
                page_title = ":/";

            System.out.println("|-");
            System.out.println("|[[" + page_title + "]]||" + n_relation +
                    "||" + n_types_relation +
                    "||" + n_meaning +
                    "||" + pos + "||" + lang_name);
        }
        System.out.println("|}");
    }


    
    /** Prints statistics about (translations, or quotes, ...) per language
     * in Wiktionary.
     *
     * @param m_lang_n map of maps with number of translations into
     * English, Russian etc. (lang -> count)
     */
    public static int printSomethingPerLanguage (
                                    LanguageType native_lang,
                                    Map<LanguageType, Integer> m_lang_n) {

        int total = 0; // total number of translations. or quotations, or...

        // print header line
        //System.out.println("\n=== Quote languages ===\n\n");

        System.out.println("{| class=\"sortable prettytable\"");
        System.out.println("! Language name");
        System.out.println("! Number");

        // in Russian
        if(LanguageType.en != native_lang) // let's print translations of the language for non-English Wiktionaries
            System.out.println("! in " + native_lang.getName());
        System.out.println("! Template");

        // print values
        for(LanguageType lang : m_lang_n.keySet()) {
            System.out.println("|-");
                
            int n = m_lang_n.get(lang);
            //System.out.print("|-\n! " + lang.getName() + " || " + lang.getCode() + "\n|| " + n);
            System.out.print("|" + lang.getName() + "||" + n);
            //System.out.print("|| " + lang.getCode() + " || " + lang.getName());
            // System.out.println(" || " + n + " ||");

            if(LanguageType.en != native_lang) {
                String local_name = "";
                if (lang.hasTranslation(native_lang))
                    local_name = lang.translateTo(native_lang);
                System.out.print("||" + local_name);

                System.out.print("||{{" + lang.getCode() + "}}");
            }
            System.out.println();

            total += n;
        }
        System.out.println("|}");
        
        //System.out.println(  "Total translations: " + total);
        return total;
    }
}
