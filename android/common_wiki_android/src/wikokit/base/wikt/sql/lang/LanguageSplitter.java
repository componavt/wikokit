/* LanguageSplitter.java - TLang functions for splitting list of languages 
 *      (e.g. for dropdown menu in spinner or choice box).
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.sql.TLang;


/** TLang functions for splitting list of languages 
 *  (e.g. for dropdown menu in spinner or choice box).
 */
public class LanguageSplitter {

    private final static TLang[] NULL_TLANG_ARRAY = new TLang[0];

    /** Three parts of languages, (1) ordered by number of entries, 
     * (2) languages in each part are ordered lexicographically */
    TLang [] part1, part2, part3;
    
    /* Sizes of part1[] and part2, 
     * they will be calculated on the base of border1 and border2 */
    int part1_end, part2_end; 
    
    public int getPart1Length () {
        return part1_end;
    }
    
    public int getPart2Length () {
        return part2_end;
    }
    
    /** Splits all TLang[] languages of Wiktionary into three parts:
     * (1) 0 .. part1_end      languages with the biggest number of entries 
     *                          sorted lexicographically (> border1),
     * (2) part1_end+1 .. part2_end languages with the average number of entries,
     *                                                   (> border2)
     * (3) part2_end+1 .. end  languages with the small number of entries.
     * 
     *  part1_end and part2_end are sizes of part1[] and part2,
     *  they will be calculated on the base of border1 and border2
     * 
     * @return true if success.
     */
    public boolean splitAllLangTo3parts (int border1, int border2) {

        Map<Integer, TLang> lang_map = TLang.getAllTLang();
        if(null == lang_map || lang_map.isEmpty()) {
            System.err.println("Error: LangChoiceBox.fillChoiceBoxByLanguages The database is not available.");
            return false;
        }
        TLang[] tlang_array = ((TLang[])lang_map.values().toArray(NULL_TLANG_ARRAY));
        List<TLang> nonempty_list = removeEmpty(tlang_array);
        TLang[] nonempty_array = ((TLang[])nonempty_list.toArray(NULL_TLANG_ARRAY));
        TLang[] sorted_by_size_array = sortLanguageBySize(nonempty_array);

        part1_end = getIndexInSortedArrayDescOfLastBigElement(sorted_by_size_array, border1);
        part2_end = getIndexInSortedArrayDescOfLastBigElement(sorted_by_size_array, border2);

        // todo un-comment
        part1 = /*Arrays.*/copyOfRange(sorted_by_size_array, 0, part1_end);
        part2 = /*Arrays.*/copyOfRange(sorted_by_size_array, part1_end+1, part2_end);
        part3 = /*Arrays.*/copyOfRange(sorted_by_size_array, part2_end, sorted_by_size_array.length);
        //part3 = Arrays.copyOfRange(sorted_by_size_array, part2_end, part2_end + 10);
        
        part1 = sortLanguageByName(part1);
        part2 = sortLanguageByName(part2);
        part3 = sortLanguageByName(part3);

// 1) temporary solution, in order to skip 'index_art-oou' and 'index-de'
// !remove this line (below), when the new dump will be used
part3 = /*Arrays.*/copyOfRange(part3, 0, part3.length - 2);
        
        return true;
    }

    /** Merges all parts created by splitAllLangTo3parts() into one array. 
     * So, call splitAllLangTo3parts() before this function. 
     */
    public TLang[] mergeArrays () {
        return mergeArrays(part1, part2, part3);
    }

    /** Gets array of text lines in the form:
     * Language name and language code'
     * The first line - "All languages".
     */
    public static List<String> getLanguageNames(TLang[] langs) {
        
        List<String> lines = new ArrayList<String>();
        lines.add("All languages");
        
        for(int i=0; i < langs.length; i++) {
            TLang l = langs[i];
            lines.add(l.getLanguage().getName() + " " + l.getLanguage().getCode() + " " + l.getNumberPOS() + " " + l.getNumberTranslations());
        }
        return lines;
    }
    
    /** Gets array of text lines in the form:
     * "Language name, code, number of semantic relations" (header).
     * The first line is a header.  
     */
    public static String[] getLangCodeStatistics(TLang[] langs) {
        
        String lines[] = new String[langs.length + 1];
        lines[0] = "Language, code, entries, translations";
        
        for(int i=0; i < langs.length; i++) {
            TLang       tl = langs[i];
            LanguageType l = langs[i].getLanguage();            
            lines[i+1]= l.getName() + " " + l.getCode() + " " + tl.getNumberPOS() + " " + tl.getNumberTranslations();
        }
        
        return lines;
    }
    
    /** Merges three arrays into one list.
     */
    private TLang[] mergeArrays (TLang[] a,TLang[] b,TLang[] c) 
    {
        List<TLang> result = new ArrayList<TLang>(a.length + b.length + c.length);

        for(TLang e : a)
            result.add(e);
        for(TLang e : b)
            result.add(e);
        for(TLang e : c)
            result.add(e);

        return ((TLang[])result.toArray(NULL_TLANG_ARRAY));
    }

    /** Gets index i in the sorted array (descending order), so that  
     * array[0..i] >= value and value > array[i+1..end] 
     */
    private static int getIndexInSortedArrayDescOfLastBigElement (
            TLang[] array, int value) 
    {
        for(int i=0; i< array.length; i++)
            if(array[i].getNumberPOS() < value)
                return i;

        return array.length - 1;
    }

    /** Removes empty element, i.e. languages with zero number of POS and absent translations.
     */
    private static List<TLang> removeEmpty(TLang[] langs) {

        List<TLang> result = new ArrayList<TLang>();

        int empty_lang = 0;
        for(int i=0; i < langs.length; i++) {
            TLang l = langs[i];
            if(l.getNumberPOS() > 0 || l.getNumberTranslations() > 0) {
                result.add(l);
            } else {
                empty_lang ++;
            }
        }
        System.out.println("Languages with entries: " + result.size() + "; empty languages: " + empty_lang);
        return result;
    }

    /** Sorts languages by size, i.e. by the number of POS.
     */
    private static TLang[] sortLanguageBySize(TLang[] langs) {
        Comparator<TLang> by_size = new LanguageSizeComparator();
        Arrays.sort(langs, by_size);
        return langs;
    }

    //List<TLang> sorted_lang = LangChoice.();
    /** Sorts languages by size, i.e. by the number of POS.
     */
    private static TLang[] sortLanguageByName(TLang[] langs) {
        Comparator<TLang> by_size = new LanguageNameComparator();
        Arrays.sort(langs, by_size);
        return langs;
    }
    
    /** Implementation of the standard "Arrays.copyOfRange" which is available 
     * only from android SDK API level 9... (2.3. ...) 
     * 
     * Todo: erase this func, when change <uses-sdk android:minSdkVersion=from "8" to "9" /> 
     */
    public static TLang[] copyOfRange(TLang[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        TLang[] copy = new TLang[newLength];
        System.arraycopy(original, from, copy, 0,
                         Math.min(original.length - from, newLength));
        return copy;
    }
}