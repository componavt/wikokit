/* POSAndPolysemyPrinter.java - Print (in wiki format)
 * parts of speech statistics and data about polysemy
 * in the database of the parsed Wiktionary.
 * 
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.stat.printer;

import wikt.constant.POS;
import wikt.stat.POSAndPolysemyTableAll.POSStat;

import java.util.Map;
import wikipedia.language.LanguageType;
import wikipedia.util.PrintfFormat;

/** Statistics of parts of speech and polysemy of the parsed Wiktionary.
 *
 * The result could be inserted into the Wiktionary page.
 * @see http://ru.wiktionary.org/wiki/User:AKA MBG/Статистика:POS
 * @see http://wordnet.princeton.edu/wordnet/man/wnstats.7WN.html
 */
public class POSAndPolysemyPrinter {

    /** Prints 
     * (1) statistics about parts of speech.
     * (2) names of POS in English (with templates) and in Russian (with templates).
     * 
     * @param m_lang_rel_n map of maps with number of synonyms, antonyms, etc.
     * in English, Russian etc. (lang -> relations -> count)
     *
     * @param m_lang_entries_number number of (Language & POS level) entries per language
     */
    public static void printPOSWordsAndSensesTable (
                        LanguageType native_lang,
                        Map<POS,POSStat> m_lang_pos,
                        boolean print_templates_and_short_names)
    {
        if(null == m_lang_pos)
            return;
                
        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.print("! Unique Strings || Total Word-Sense Pairs || POS");
        
        if(LanguageType.en != native_lang)
            System.out.print(" || POS in " + native_lang.getName());

        if(print_templates_and_short_names)
            System.out.print(" || Short name || Templates");

        System.out.print(" || Max Senses || Entry");

        // print values
        for(POS pos : m_lang_pos.keySet()) {

            POSStat pos_stat = m_lang_pos.get(pos);// Map<POS,POSStat> m_lang_pos

            System.out.print("\n|-\n! " + 
                    pos_stat.getNumberOfUniquesStrings() + " || " +
                    pos_stat.getNumberOfWordSensePairs() + " || " +
                    pos.toString());

            if(LanguageType.en != native_lang)
                System.out.print(" || " + pos.toString(native_lang));

            if(print_templates_and_short_names)
                System.out.print(" || " + pos.getShortName(native_lang) +
                                 " || " + pos.getTemplates(", ", native_lang));

            System.out.print(" || " + pos_stat.getMaxSenses() +
                             " || " + pos_stat.getWikifiedWordWithMaxSenses());
        }
        System.out.println("\n|}");
    }

     public static void printPOSPolysemyTable (
                            LanguageType native_lang,
                            Map<POS,POSStat> m_lang_pos)
    {
        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        System.out.print("! POS");

        if(LanguageType.en != native_lang)
            System.out.print(" || POS in " + native_lang.getName());

        System.out.print(" || Monosemous Words and Senses || Polysemous Words || Polysemous Senses");
        System.out.print(" || Average Polysemy Including Monosemous Words || Average Polysemy Excluding Monosemous Words");

        // print values
        for(POS pos : m_lang_pos.keySet()) {

            POSStat pos_stat = m_lang_pos.get(pos);// Map<POS,POSStat> m_lang_pos

            System.out.print("\n|-\n! " + pos.toString());

            if(LanguageType.en != native_lang)
                System.out.print(" || " + pos.toString(native_lang));

            System.out.print(   " || " + pos_stat.getMonosemous() +
                " || " + pos_stat.getPolysemousWords() +
                " || " + pos_stat.getPolysemousSenses() +
                " || " + new PrintfFormat("%.2lg").sprintf(pos_stat.calcAveragePolysemyIncludingMonosemousWords()) +
                " || " + new PrintfFormat("%.2lg").sprintf(pos_stat.calcAveragePolysemyExcludingMonosemousWords()));
        }
        System.out.println("\n|}");
    }

    public static void printPOS (
                        LanguageType native_lang,
                        Map<POS,POSStat> m_lang_pos,
                        boolean print_templates_and_short_names)
    {
        if(null == m_lang_pos)
            return;
        
        // !print header line before this function
        System.out.println("\n=== Number of words and senses ===");
        
        int lang_pos_size = 0;
        if(null != m_lang_pos)
            lang_pos_size = m_lang_pos.size();
        
        System.out.println("\nRows in the table: " + lang_pos_size + "\n");
        printPOSWordsAndSensesTable(native_lang,
                                    m_lang_pos,
                                    print_templates_and_short_names);

        System.out.println("\n=== Polysemy information ===");
        System.out.println("\nRows in the table: " + m_lang_pos.size() + "\n");

        printPOSPolysemyTable ( native_lang,
                                m_lang_pos);
    }
    
    /** Maximum "number of relations" will be printed in the table:
     * (2) Number of words per number of relations
     * @see http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:Semantic_relations#Number_of_words_per_number_of_relations
     */
    //static final Integer max_relations_to_print = 50;

    /** Prints statistics-histogram about number of meanings in Wiktionary.
     *
     * @param max_values_to_print values histogram[0..max_values_to_print-1] will be printed
     * @param total_histogram with total number of meanings for all languages
     * @param m_lang_histogram number of meanings for each 
     */
    public static void printHistogramPerlanguage (int[] total_histogram, int max_values_to_print,
            Map<LanguageType, Integer[]> m_lang_histogram) {

        // maximum number of meanings [0..max] to be printed in the table
        // max := first non-zero value in total_histogram[] from the end of array
        int max = Math.min(total_histogram.length, max_values_to_print);
        for(int i=max-1; i>=0; i--) {
            if(0 != total_histogram[i]) {
                max = i;
                break;
            }
        }
        if (0 == max)
            return;
        
        // print header line
        System.out.println("\n=== Number of words having different number of meanings / definitions ===\n");

        System.out.println("Table description:");
        System.out.println("* column 0 - number of words with empty definitions (total and for each language)");
        System.out.println("* column 1 - number of monosemous words (total and for each language)");
        System.out.println("* column 2 - number of words with two meanings, etc.");
        System.out.println("* last column (\"Total\") - total number of words for this language.");
        
        System.out.println("\nOnly the first " + max + " meanings (columns) are presented in the table.");

        System.out.println("{| class=\"sortable prettytable\" style=\"text-align: center;\"");
        
        System.out.print("! || Number of meanings: ");
        for(int i=0; i<=max; i++)
            System.out.print("||" + i);
        System.out.print("||Total");

        
        // System.out.print("\n|-");
        // System.out.print("\n! Language name || Language code || colspan=\""+(max+2)+"\"| &nbsp;");
        System.out.print("\n|-");
        System.out.print("\n! code || Total (all languages) :");
        int cur_total = 0;
        for(int i=0; i<=max; i++) { 
            System.out.print("||" + total_histogram[i]);
            cur_total += total_histogram[i];
        }
        System.out.print("||" + cur_total);
        
        
        for(LanguageType lang : m_lang_histogram.keySet()) {
            System.out.println("\n|-\n! " + lang.getCode() + " || " + lang.getName());
            
            cur_total = 0;
            Integer[] h = m_lang_histogram.get(lang);
            for(int i=0; i<=max; i++) { 
                System.out.print("||" + h[i]);
                cur_total += h[i];
            }
            System.out.print("||" + cur_total);
        }
        
        // Total (all languages)
        
        
        System.out.println("\n|}");

                
    }
}
