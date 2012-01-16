/* FilterMeanSemRelTrans.java - Words filter by a presence of meaning,
 * semantic relations, translations.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.search_window;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/** Words filter by a presence of meaning, semantic relations, translations,
 * etc. (CheckBoxes).
 */
public class FilterMeanSemRelTrans {

    /** Whether list only articles with definitions */
    static boolean meaning_checkbox_value = false;

    /** Whether list only articles with semantic relations */
    static boolean sem_rel_checkbox_value = false;
    
    WordList word_list;
    LangChoice lang_choice;
    QueryTextString query_text_string;

    
    public HBox filter_MRT_hbox = new HBox();
    CheckBox meaning_checkbox = new CheckBox();
    CheckBox sem_rel_checkbox = new CheckBox();
    CheckBox translation_checkbox = new CheckBox();
    

    /** Set parameters of the class.
     * @param _word_list    list of words in the dictionary (ListView)
     * @param _lang_choice  checkboxes and text field with language codes
     * @param _query_text_string field with a user search text query
     */
    public void initialize( //_wikt_parsed_conn  : Connect,
                                WordList _word_list,
                                LangChoice _lang_choice,
                                QueryTextString _query_text_string
                          ) {
        //wikt_parsed_conn = _wikt_parsed_conn;
        word_list       = _word_list;
        lang_choice     = _lang_choice;
        query_text_string = _query_text_string;
        
        // GUI
        meaning_checkbox.setText("Meaning");
        Tooltip tp = new Tooltip();
        tp.setText("List of words only with meanings (senses)");
        meaning_checkbox.setTooltip(tp);
        
        sem_rel_checkbox.setText("Semantic Relation");
        Tooltip tp2 = new Tooltip();
        tp2.setText("List of words only with\n" +
                    "Semantic relations\n" +
                    "(synonyms, hypernyms, etc.)");
        sem_rel_checkbox.setTooltip(tp2);
        
        translation_checkbox.setText("Translation");
        Tooltip tp3 = new Tooltip();
        tp3.setText("List of words only with translations (todo)");
        translation_checkbox.setTooltip(tp3);
        translation_checkbox.setDisable(true); // todo then enable
        
        
        filter_MRT_hbox.setSpacing(10);
        filter_MRT_hbox.getChildren().addAll(meaning_checkbox);
        filter_MRT_hbox.getChildren().addAll(sem_rel_checkbox);
        filter_MRT_hbox.getChildren().addAll(translation_checkbox);
        
        
        // If user clicks CheckBox and select filtering words - only with meanings
        meaning_checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val)
            {
                boolean b_selected = meaning_checkbox.isSelected();
                
                if (meaning_checkbox_value != b_selected) {
                    meaning_checkbox_value  = b_selected;
                    updateWordList();
                }
            }
        });
        
        // If user clicks CheckBox and select filtering words - only with semantic relations
        sem_rel_checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val)
            {
                boolean b_selected = sem_rel_checkbox.isSelected();

                if (sem_rel_checkbox_value != b_selected) {
                    sem_rel_checkbox_value  = b_selected;
                    updateWordList();
                }
            }
        });
    }

    public void setWordList( WordList _word_list ) {
        word_list = _word_list;
    }

    /** Whether to filter words by a presence of word's definition. */
    public Boolean filterByMeaning() {
        return meaning_checkbox_value;
    }

    /** Whether to filter words by a presence of semantic relations. */
    public Boolean filterBySemanticRelation() {
        return sem_rel_checkbox_value;
    }

    /** Inteface to the real function updateWordList(). */
    void updateWordList() {
        if(null == word_list) {
                System.err.println("Error: FilterMeanSemRelTrans.updateWordList(): word_list is empty, non-initialized");
                return;
        }
        word_list.updateWordList(   word_list.getSkipRedirects(),
                                    query_text_string.getWordValue()
                                );
        query_text_string.saveWordValue();
    }
}
