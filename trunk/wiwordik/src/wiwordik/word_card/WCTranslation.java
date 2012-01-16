/* WCTranslation.java - A part of word card corresponds to a translation part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.word_card;

import wikt.sql.*;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;

import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/*ObservableList<String> temp_words = FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown");

        word_ListView.setItems(temp_words);*/


/** Translations consists of one meaning block translated to all languages.
 *
 * @see wikt.sql.TTranslation and wikt.word.WTranslation
 */
public class WCTranslation {

    /** Translation section (box) title, i.e. additional comment,
     * e.g. "fruit" or "apple tree" for "apple".
     * A summary of the translated meaning.
     */
    String meaning_summary;

    /** Duplication of listview_trans.items[] ??? */
    //TranslationEntryItem[] trans_entry_items; //TTranslationEntry[];
    int trans_entry_items_size;
    

    /** Sets maximum and minimum height of the translation box.
     * 
     * @param n number of items in the listview
     */
    // float getTranslationBoxHeight(int n)
    void setMinMaxTranslationBoxHeight( ListView<TranslationEntryItem> listview_trans, 
                                        int n)
    {
        float height;
                
        // assert: listview_trans.items.size == n > 0
        if(0 == n || n < 1)
            return; // this line is not reachable

        float h = TranslationEntryItem.getHeight(); // the height of one item
        //System.out.println("WCTranslation:setMinMaxTranslationBoxHeight() h = " + h);
        
        if(n > 0 && n <= 9) {
            height = n*h;
            
            listview_trans.setMinHeight(height);
            listview_trans.setMaxHeight(height);
            
            //System.out.println("variant a-small) n = " + n);
            return;
        }
         
        height = 7*h;   // too much entries
        listview_trans.setMinHeight(height);
        listview_trans.setMaxHeight(height);
        //System.out.println("variant a-huge) n = " + n);
    }

    ListView<TranslationEntryItem> listview_trans = new ListView();
    

    public VBox group = new VBox();
    
    /** Creates a translation part of word card, it corresponds to one meaning.
     *
     * @return true if there are any translations in this translation block.
    **/
    public boolean create ( Connect conn,
                            TTranslation _ttranslation,
                            TLang _lang
                          )
    {
        meaning_summary = _ttranslation.getMeaningSummary();

        TTranslationEntry[] trans_entries = TTranslationEntry.getByTranslation (conn, _ttranslation);
        // System.out.println("WCTranslation.create() _lang=" + _lang.getLanguage().toString() + "; trans_entries.length=" + trans_entries);
        
        List<TranslationEntryItem> data_trans = new ArrayList();

        //  listview_trans.items length =" + trans_entries.length);
        trans_entry_items_size = trans_entries.length;
        //trans_entry_items = new TranslationEntryItem[trans_entries.length];
        for(int i=0; i< trans_entries.length; i++) {
            TTranslationEntry e = trans_entries[i];

            LanguageType l = e.getLang().getLanguage();
            String lang_name_value = l.getName();
            String lang_code_value = l.getCode();
            String translation_text = e.getWikiText().getText();
            
            TranslationEntryItem item = new TranslationEntryItem();
            item.create(lang_name_value, lang_code_value, translation_text);
            data_trans.add(item);
        }
        //  TranslationEntryItem[] trans_entry_items = data_trans.toArray();
        //insert trans_entry_items into listview_trans.items;
     
        ObservableList<TranslationEntryItem> data = FXCollections.observableArrayList(data_trans);
        
        listview_trans.setCellFactory(new Callback<ListView<wiwordik.word_card.TranslationEntryItem>, ListCell<wiwordik.word_card.TranslationEntryItem>>() {
            @Override public ListCell<wiwordik.word_card.TranslationEntryItem> call(ListView<wiwordik.word_card.TranslationEntryItem> list) {
                return new wiwordik.word_card.TranslationCell();
            }
        });
        listview_trans.setItems(data);
        setMinMaxTranslationBoxHeight(listview_trans, data.size());
        
        
        // GUI                          right - indent for translation list words
        //                          top right bottom left
        group.setPadding(new Insets(0,  20,   0,     0));
        
        Text t_meaning_summary = new Text();
        t_meaning_summary.setText(meaning_summary);
        
        group.getChildren().addAll(t_meaning_summary);
        group.getChildren().addAll(listview_trans);

        
        return trans_entries.length > 0;
    }
}
