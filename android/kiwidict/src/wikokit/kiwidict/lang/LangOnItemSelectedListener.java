package wikokit.kiwidict.lang;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.lang.LanguageSplitter;
import wikokit.kiwidict.KWConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class LangOnItemSelectedListener implements OnItemSelectedListener {
    
    LanguageSpinner lspinner;
    //LanguageSplitter lsplitter;
    LangChoice lang_choice;
    
    private final static TLang[] NULL_TLANG_ARRAY = new TLang[0];
    
    public LangOnItemSelectedListener(  LanguageSpinner _lspinner, 
                                        LangChoice _lang_choice) {
        lspinner = _lspinner;
        //lsplitter = lspinner.getLanguageSplitter();
        lang_choice = _lang_choice;
    }

    // old name: choice_box_is_passive
    private boolean language_spinner_selection_is_passive = false;
    
    // old title: isChoiceBoxPassive
    public boolean isLanguageSelectionPassive() {
        return language_spinner_selection_is_passive;
    }
    
    public void setLanguageSelectionActive( boolean language_selection_is_active) {
        language_spinner_selection_is_passive = !language_selection_is_active;
    }
    
    /** Selects language from a dropdown spinner list. */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        
        //Toast.makeText(view.getContext(), "You selected pos=" + pos + " id="+id, Toast.LENGTH_LONG).show();
        
//        if(0 == pos) // header, not a language
//            return;
        
        if(isLanguageSelectionPassive()) {
            setLanguageSelectionActive(true);   // one-time trigger :)
            return;
        }

        lang_choice.disableLangSource(); 
                
        if(0 == pos) {
            lang_choice.source_lang = NULL_TLANG_ARRAY; // without filter, all languages
            lang_choice.updateWordList();
        } else {
            
            //int part1_end = lsplitter.getPart1Length();
            //int part2_end = lsplitter.getPart2Length();
            
            int index = (int)pos - 1;
            //if((int)pos > part1_end) // separator in dropdown list
            //    index --;
            //if((int)pos > part2_end) // one more separator
            //    index --;
            
            TLang tl = lspinner.dropdown_tlang_array[ index ];  
            LanguageType lt = tl.getLanguage();
            
            // release
            //Toast.makeText(view.getContext(), lt.getName() + " language\n" + 
            //        tl.getNumberPOS() + " entries (POS level)\n" + 
            //        tl.getNumberTranslations() + " translations",
            //        Toast.LENGTH_LONG).show();
            
            // debug java.lang.NullPointerException
            String sname = lt.getName();
            String snpos = "" + tl.getNumberPOS();
            String sntrans = "" + tl.getNumberTranslations();
            Toast.makeText(view.getContext(), sname + " language\n" + 
                    snpos + " entries (POS level)\n" + 
                    sntrans + " translations",
                    Toast.LENGTH_LONG).show();
            
            // GUI
            lang_choice.setLanguageSourceActive(false);
            lang_choice.lang_source_text.setText(lt.getCode());// System.out.println("Selected language: " + lt.getName());
            lang_choice.setLanguageSourceActive(true);

            // logic
            if(lt == KWConstants.native_lang) {
                lang_choice.source_lang = NULL_TLANG_ARRAY; // without filter, all languages
                lang_choice.updateWordList();               // todo: list only native words
                // todo: ...
            } else {
                lang_choice.source_lang = new TLang[1];
                lang_choice.source_lang[0] = tl;
                lang_choice.updateWordList();
            }
        }
    }

    public void onNothingSelected(AdapterView parent) {
      // Do nothing.
    }
}