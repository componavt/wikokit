package wikokit.kiwidict.lang;

import java.util.List;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.lang.LanguageSplitter;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.search_window.QueryTextString;
import wikokit.kiwidict.wordlist.WordList;

public class LanguageSpinner {

    Spinner lang_spinner_widget;
    LangOnItemSelectedListener lang_item_listener;
    
    LanguageSplitter lsplitter;
    
    TLang[] dropdown_tlang_array;
    
    
    public LanguageSplitter getLanguageSplitter() {
        return lsplitter;
    }
    
    /** Set parameters of the class.
     */
    public void initialize( Spinner _lang_spinner_widget,
                            LangOnItemSelectedListener _lang_item_listener
                          ) {
        // GUI
        lang_spinner_widget = _lang_spinner_widget;
        lang_item_listener = _lang_item_listener;
    }
    
    /** select language in drop down menu by 'source_lang_codes' from TipsTeapot */
    public void postInit( String source_lang_codes) {
        
        /** Language codes for words filtering, e.g. "ru en fr" */
        TLang[] source_lang = TLang.parseLangCode(source_lang_codes);
        
        // update ChoiceBox, let's select in dropdown menu the same language as user types in  text field
        if(source_lang.length > 0)
            selectLanguageInDropdownMenu( source_lang[0].getLanguage() );
    }
    
    public String[] fillByAllLanguages() {
    
        int border1 = 1000, border2 = 300;
        if(LanguageType.ru == KWConstants.native_lang) {
            border1 = 1000; border2 = 300;          // then part1_end about 20, part2_end about 67
        } else if(LanguageType.en == KWConstants.native_lang) {
            border1 = 10000; border2 = 1000;
        }
        
        lsplitter = new LanguageSplitter();
        lsplitter.splitAllLangTo3parts(border1, border2);
        
        dropdown_tlang_array = lsplitter.mergeArrays(); 
        
        List<String> all_lang_code = LanguageSplitter.getLanguageNames( dropdown_tlang_array );
        
        String ar_spinner[] = new String [all_lang_code.size()]; 
        
        //Separator ss = new Separator();
        int i=0;
        int part1_end = lsplitter.getPart1Length();
        int part2_end = lsplitter.getPart2Length();
        for(String l : all_lang_code) {
            ar_spinner [i] = l;
            
            if(part1_end+1 == i)    // adds separator in order to separate (visually) different sections
                ar_spinner [i] = "--- " + border2 + " < entries < " + border1 + " ---";
            
            if(part2_end == i)    // adds separator in order to separate (visually) different sections
                ar_spinner [i] = "--- entries < " + border2 + " ---";
                        
            i ++;
        }
        
        return ar_spinner;
    }
    
    /** Selects the language lt in the dropdown menu and do nothing more.
     */
    public void selectLanguageInDropdownMenu(LanguageType lt) {
        // System.out.println("LangChoiceBox.selectLanguageInChoiceBox()");
        
        for(int i=0; i<dropdown_tlang_array.length; i++) {
            if(lt == dropdown_tlang_array[i].getLanguage()) {
                int select_index = i+1; // since first records is "All languages"
                
                //int part1_end = lsplitter.getPart1Length();
                //int part2_end = lsplitter.getPart2Length();
                
                //if(i > part1_end)
                //    select_index ++; // since there is a separator line between part1 and part2 sublists
                //if(i > part2_end)
                //    select_index ++; // one more separator between part2 and part3
                
                lang_item_listener.setLanguageSelectionActive(false);  // one-time trigger :)
                lang_spinner_widget.setSelection(select_index);
                break;
            }
        }
    }
    
    /** Selects first records "All languages" in the dropdown menu and do nothing more.
     */
    public void selectAllLanguagesInDropdownMenu() {
        int select_index = 0; // 
        lang_item_listener.setLanguageSelectionActive(false);  // one-time trigger :)
        lang_spinner_widget.setSelection(select_index);
    }
}
