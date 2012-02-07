/* LangChoiceBox.java - Dropdown list of source languages.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wiwordik.search_window;

import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.sql.TLang;

/** GUI element contains ChoiceBox with dropdown
 * list of source languages (codes, number of POS, number of translations).
 */
public class LangChoiceBox {
    
    /** Dropdown list of all languages */
    public ChoiceBox choicebox;
    TLang[] dropdown_tlang_array;
    private boolean choice_box_is_passive = false;
    
    LanguageType native_lang;    
    WordList word_list;
    QueryTextString query_text_string;
    LangChoice lang_choice;
    
    int part1_end, part2_end; // will be calculated on the base of border1 and border2
    
    private final static TLang[] NULL_TLANG_ARRAY = new TLang[0];

    
    /** Set parameters of the class.
     * @param _word_list    list of words in the dictionary (ListView)
     * @param _query_text_string field with a user search text query
     * @param _native_lang 
     */
    public void initialize(WordList _word_list,
                           QueryTextString _query_text_string,
                           LangChoice _lang_choice,
                           LanguageType _native_lang
                          )
    {
        native_lang       = _native_lang;
        word_list         = _word_list;
        query_text_string = _query_text_string;
        lang_choice       = _lang_choice;
        
        choicebox = new ChoiceBox();
        choicebox.setTooltip(new Tooltip(
                "Select the language (language code,\n" + 
                "number of entries (POS), number of translations)."));
        
        fillChoiceBoxByLanguages();
        
    }
    
    /** Fills choice box by languages presented in Wiktionary. 
     * The list consists of three parts:
     * (1) 0 .. part1_end      languages with the biggest number of entries 
     *                          sorted lexicographically (> border1),
     * (2) part1_end+1 .. part2_end languages with the average number of entries,
     *                                                   (> border2)
     * (3) part2_end+1 .. end  languages with the small number of entries.
     * 
     * E.g. in Russian Wiktionary in 2011:
     * (1) 0..20 (20) languages with > 1000 entries;    border1 = 1000;
     * (2) 21..67 (46) languages with > 300 entries;    border2 = 300;
     * (3) 68..423 (many) languages with > 0 entries
     * 
     * In English Wiktionary in 2011:
     * (1) 0..21 (21) languages with > 10000 entries;   border1 = 10000;
     * (2) 22..87 (65) languages with > 1000 entries;   border2 = 1000;
     * (3) 88..742 (many) languages with > 0 entries
     */
    public void fillChoiceBoxByLanguages() {
        
        int border1 = 1000, border2 = 300;
        if(LanguageType.ru == native_lang) {
            border1 = 1000; border2 = 300;          // then part1_end about 20, part2_end about 67
        } else if(LanguageType.en == native_lang) {
            border1 = 10000; border2 = 1000;
        }
        
        Map<Integer, TLang> lang_map = TLang.getAllTLang();
        if(lang_map.isEmpty()) {
            System.err.println("Error: LangChoiceBox.fillChoiceBoxByLanguages The database is not available.");
            return;
        }
        TLang[] tlang_array = ((TLang[])lang_map.values().toArray(NULL_TLANG_ARRAY));
        List<TLang> nonempty_list = removeEmpty(tlang_array);
        TLang[] nonempty_array = ((TLang[])nonempty_list.toArray(NULL_TLANG_ARRAY));
        TLang[] sorted_by_size_array = sortLanguageBySize(nonempty_array);
        
        part1_end = getIndexInSortedArrayDescOfLastBigElement(sorted_by_size_array, border1);
        part2_end = getIndexInSortedArrayDescOfLastBigElement(sorted_by_size_array, border2);
        
        TLang [] part1, part2, part3;
        part1 = Arrays.copyOfRange(sorted_by_size_array, 0, part1_end);
        part2 = Arrays.copyOfRange(sorted_by_size_array, part1_end+1, part2_end);
        part3 = Arrays.copyOfRange(sorted_by_size_array, part2_end, sorted_by_size_array.length);
        
        part1 = sortLanguageByName(part1);
        part2 = sortLanguageByName(part2);
        part3 = sortLanguageByName(part3);
        dropdown_tlang_array = mergeArrays(part1, part2, part3); 
        
        List<String> all_lang_code = getLanguageNames( dropdown_tlang_array );

        Separator ss = new Separator();
        int i=0;
        for(String l : all_lang_code) {
            i ++;
            choicebox.getItems().addAll(l);
            if(part1_end+1 == i || part2_end == i)    // adds separator in order to separate (visually) different sections
                choicebox.getItems().addAll(ss);
        }

        choicebox.getItems().addAll(ss);
        
        /// Selects language from a dropdown list.
        choicebox.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue ov,
                            Number value, Number new_value)
                    {
                        if(choice_box_is_passive)
                            return;
                        
                        lang_choice.lang_source_checkbox.setSelected(false);
                        lang_choice.lang_source_text.setDisable(true);
                                
                        if(0 == new_value) {
                            lang_choice.source_lang = NULL_TLANG_ARRAY; // without filter, all languages
                            lang_choice.updateWordList();
                        } else {
                            int index = (int)new_value - 1;
                            if((int)new_value > part1_end) // separator in dropdown list
                                index --;
                            if((int)new_value > part2_end) // one more separator
                                index --;
                            
                            TLang tl = dropdown_tlang_array[ index ];
                            
                            LanguageType lt = tl.getLanguage();
                            
                            if(lt == native_lang) {
                                lang_choice.source_lang = NULL_TLANG_ARRAY; // without filter, all languages
                                lang_choice.updateWordList();               // todo: list only native words
                                // todo: ...
                            } else {
                            
                                // GUI
                                lang_choice.lang_source_text.setText(lt.getCode());// System.out.println("Selected languge: " + lt.getName());

                                // logic
                                lang_choice.source_lang = new TLang[1];
                                lang_choice.source_lang[0] = tl;
                                lang_choice.updateWordList();
                            }
                        }
                    }
                });
                //langlist_choicebox.getSelectionModel().selectFirst();
    }
    
    /** Merges three arrays into one list.
     */
    private TLang[] mergeArrays (TLang[] a,TLang[] b,TLang[] c) 
    {
        List<TLang> result = new ArrayList(a.length + b.length + c.length);
        
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
    private int getIndexInSortedArrayDescOfLastBigElement (
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
    	
    	List<TLang> result = new ArrayList();
    	
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
    
    /** Selects the language lt in the dropdown menu and do nothing more.
     */
    public void selectLanguageInChoiceBox(LanguageType lt) {
        // System.out.println("LangChoiceBox.selectLanguageInChoiceBox()");
        
        for(int i=0; i<dropdown_tlang_array.length; i++) {
            if(lt == dropdown_tlang_array[i].getLanguage()) {
                int select_index = i+1; // since first records is "All languages"
                
                // part1_end, part2_end
                if(i > part1_end)
                    select_index ++; // since there is a separator line between part1 and part2 sublists
                if(i > part2_end)
                    select_index ++; // one more separator between part2 and part3
                
                choice_box_is_passive = true;
                choicebox.getSelectionModel().select(select_index);
                choice_box_is_passive = false;
                break;
            }
        }
    }
    
    
    /** Gets array of text lines in the form:
     * Language name and language code'
     * The first line - "All languages".
     */
    private static List<String> getLanguageNames(TLang[] langs) {
    	
    	List<String> lines = new ArrayList();
    	lines.add("All languages");
    	
    	for(int i=0; i < langs.length; i++) {
            TLang l = langs[i];
            lines.add(l.getLanguage().getName() + " " + l.getLanguage().getCode() + " " + l.getNumberPOS() + " " + l.getNumberTranslations());
    	}
    	return lines;
    }
    
    /** Sorts languages by size, i.e. by the number of POS.
     */
    private TLang[] sortLanguageBySize(TLang[] langs) {
        Comparator<TLang> by_size = new LanguageSizeComparator();
        Arrays.sort(langs, by_size);
    	return langs;
    }
    
    //List<TLang> sorted_lang = LangChoice.();
    /** Sorts languages by size, i.e. by the number of POS.
     */
    private TLang[] sortLanguageByName(TLang[] langs) {
        Comparator<TLang> by_size = new LanguageNameComparator();
        Arrays.sort(langs, by_size);
    	return langs;
    }
    
    /** Sorts languages by size, i.e. by the number of POS, descending order. */
    class LanguageSizeComparator implements Comparator<TLang> {

        public int compare(TLang a, TLang b) {
            int a1 = a.getNumberPOS();
            int b1 = b.getNumberPOS();
            
            //System.out.println("LanguageSizeComparator: a="+a + "; b="+b+"; a.getNumberPOS()="+a.getNumberPOS()+"; b.getNumberPOS()="+b.getNumberPOS());
            if (a1 > b1) {
                return -1;
            } else {
                if(a1 < b1) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
    
    /** Sorts languages by size, i.e. by the number of POS, descending order. */
    class LanguageNameComparator implements Comparator<TLang> {

        public int compare(TLang a, TLang b) {
            String a1 = a.getLanguage().getName();
            String b1 = b.getLanguage().getName();
            
            return a1.compareTo(b1);
            //System.out.println("LanguageSizeComparator: a="+a + "; b="+b+"; a.getNumberPOS()="+a.getNumberPOS()+"; b.getNumberPOS()="+b.getNumberPOS());
        }
    }
    
}
