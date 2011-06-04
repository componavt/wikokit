/* POSAndPolysemyPrinter.java - Print (in wiki format)
 * parts of speech statistics and data about polysemy
 * in the database of the parsed Wiktionary.
 * 
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.stat;

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
                             " || [[" + pos_stat.getMaxSensesPageTitle() + "]]");
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
        // !print header line before this function
        System.out.println("\n=== Number of words and senses ===");
        System.out.println("\nRows in the table: " + m_lang_pos.size() + "\n");
        printPOSWordsAndSensesTable(native_lang,
                                    m_lang_pos,
                                    print_templates_and_short_names);

        System.out.println("\n=== Polysemy information ===");
        System.out.println("\nRows in the table: " + m_lang_pos.size() + "\n");

        printPOSPolysemyTable ( native_lang,
                                m_lang_pos);
    }
}
