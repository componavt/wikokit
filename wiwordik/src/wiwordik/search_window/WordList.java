/* WordList.java - main list of words in the dictionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.search_window;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;
import wikt.sql.*;
import wikt.sql.index.IndexForeign;
//import wiwordik.word_card.WC;

import javafx.scene.control.ListView;
//import javafx.ext.swing.SwingListItem;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import wiwordik.WConstants;
import wiwordik.word_card.WC;

//import java.util.Map;
//import java.util.LinkedHashMap;

/** List of words in the dictionary.
 */
public class WordList {

    public Float height; // reference to the parent Window

    QueryTextString query_text_string;
    LangChoice lang_choice;
    FilterMeanSemRelTrans filter_mean_sem_transl;

    LanguageType native_lang;

    /** Is equal to page_array_string (global search)
     * or to foreign_array_string (index search). */    
    ObservableList<String> word_list_lines = FXCollections.observableArrayList();
    

    /** Page titles from 'page_array' */
    String[] page_array_string;
    
    /** Words extracted by several letters (prefix). */
    TPage[] page_array;
    private final static TPage[] NULL_TPAGE_ARRAY = new TPage[0];


    /** Page titles from 'index_foreign' */
    String[] foreign_array_string;

    /** Foreign words extracted by several letters (prefix). */
    IndexForeign[] index_foreign; // //var foreign_word_to_index : Map<String, IndexForeign>;

    //var page_listItems: SwingListItem[] = SwingListItem{};

    Connect wikt_parsed_conn;

    /** Number of words visible in the list */
    int n_words_list;

    /** Skips #REDIRECT words if true. */
    boolean b_skip_redirects;
    
    public WordList () {
        page_array = NULL_TPAGE_ARRAY;
    }
    
    public ListView<String> word_listview = new ListView();

    /** Set parameters of the class.
     * @param word0         initial user query search string
     * @param n_words_list  number of words visible in the list
     */
    public void initialize( Connect _wikt_parsed_conn,
                            QueryTextString _query_text_string,
                            LangChoice _lang_choice,
                            FilterMeanSemRelTrans _filter_mean_sem_transl,
                            LanguageType _native_lang,
                            //_word0              : String,
                            Integer _n_words_list
                          ) {
        wikt_parsed_conn = _wikt_parsed_conn;
        query_text_string = _query_text_string;
        lang_choice     = _lang_choice;
        filter_mean_sem_transl = _filter_mean_sem_transl;
        //word0           = _word0;
        n_words_list    = _n_words_list;
        native_lang     = _native_lang;
        
        //VBox.setVgrow(word_listview, Priority.ALWAYS);
        //word_listview.setMaxHeight(Control.USE_PREF_SIZE);
        //word_listview.setManaged(true);
        
        word_listview.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override public ListCell<String> call(ListView<String> list) {
                return new wiwordik.search_window.WordCell();
            }
        });
        
        word_listview.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            public void handle(MouseEvent me)
            {
                String word_in_list = getSelectedWordInList();
                if(word_in_list.length() > 0)   // there is a word to search in Wiktionary
                {
                    QueryTextString q = query_text_string;
                    q.setWordValue(word_in_list);
                    q.setWordValueOld(word_in_list);
                
                    //System.out.println("word_listview.setOnMouseClicked: me.getClickCount()=" + me.getClickCount());
                    if(me.getClickCount() < 2)
                        return;
                    // OK. It's a double click - let's open WordCard for this word

                    if( q.word_value_last_open_card.length() == 0 ||  // if 0 word cards are opened
                       !q.word_value_last_open_card.equals(word_in_list) // if user trying to open the same card
                      )
                    {                    
                        q.word_value_last_open_card = word_in_list;
                        openWordCard();

                        /*WC wc = new WC();
                        TPage _tpage = TPage.get(wikt_parsed_conn, word_in_list);
                        wc.createCXLangList (wikt_parsed_conn, _tpage);*/
                    }
                }
            }
        });
    }
    /*      var l = word_ListView;
            if (l.selectedItem != "" and l.selectedItem != null) {
                def s : String = (l.selectedItem).toString();
                query_text_string.setWordValue(s);          // word_Text.text = s;
                query_text_string.setWordValueOld(s);       // word_value_old = s;

                if (me.clickCount >= 2)
                    openWordCard();
            }
        }
     */
    
    /** Whether to skip #REDIRECT words. */
    public void setSkipRedirects(boolean _b_skip_redirects) {
        b_skip_redirects = _b_skip_redirects;
    }
    
    /** Whether to skip #REDIRECT words. */
    public boolean getSkipRedirects() {
        return b_skip_redirects;
    }
    
    public TPage[] getPageArray() {
        return page_array;
    }
    
    /** Gets first word from the WordList or empty "" String if it is absent. */
    public String getFirstWordInList() {
        if(null == page_array_string || 0 == page_array_string.length)
            return "";
        return page_array_string[0];
    }
    
    /** Gets selected item from the list else the first word from the WordList,
     * or empty string "" if the list is empty. */
    String getSelectedWordInList() {
        String selected_item = "";
        
        // currently selected item
        ReadOnlyObjectProperty<String> cur = word_listview.getSelectionModel().selectedItemProperty();
        if (cur != null && cur.getValue()!= null && cur.getValue().length() > 0) {
            selected_item = cur.getValue();
        }
        
        return selected_item;
    }
    
    /** Gets selected item from the list else the first word from the WordList,
     * or empty string "" if the list is empty. */
    public String getSelectedOrFirstWordInList() {
        String selected_item = getSelectedWordInList();
        
        if(selected_item.length() == 0)
            return getFirstWordInList();
        return selected_item;
    }
    
    /* List of words related to the user query string */
    /*public var word_ListView: ListView = ListView {

        layoutInfo: LayoutInfo { height: bind height } // getTranslationBoxHeight(trans_entry_items_size) }
        //layoutInfo: LayoutInfo { width: 222 }
        //height: bind scene.height

        // items: bind page_array_string

        //items: bind for(_tpage in page_array) { _tpage.getPageTitle() }
        items: bind for(_w in word_list_lines) { _w }

        onKeyPressed: function (e: KeyEvent) {

            var l = word_ListView;

            //println("WordList.onKeyPressed(), KeyEvent={e}, e.text={e.text}");

            if (l.selectedItem != "" and l.selectedItem != null)
                query_text_string.setWordValue( (word_ListView.selectedItem).toString() );
                // word_Text.text = (word_ListView.selectedItem).toString();
                
            if(e.code == KeyCode.VK_ENTER)
                openWordCard();
        }

        onMouseClicked: function (me: MouseEvent) {
            var l = word_ListView;

            //println("WordList.onMouseClicked(), MouseEvent={me}");

            if (l.selectedItem != "" and l.selectedItem != null) {
                def s : String = (l.selectedItem).toString();
                query_text_string.setWordValue(s);          // word_Text.text = s;
                query_text_string.setWordValueOld(s);       // word_value_old = s;

                if (me.clickCount >= 2)
                    openWordCard();
            }
        }
    }*/

    /** Checks whether the word list is filled from the table 'page',
     * or from the table 'index_XX', where XX is a foreign language code.
    */
    public boolean isActiveIndexForeign() {
        return lang_choice.getNumberSourceLang() == 1;
    }
    
    /** Copies data from TPagе[].text page_array to SwingListItem[]  page_listItems
    */
    public void updateWordList( boolean b_skip_redirects,
                                String word
                              )
    {
        //System.out.println("WordList.updateWordList(), word_value=" + word);
        //System.out.println("WordList.updateWordList(), number of source languagues="+ lang_choice.getNumberSourceLang());

        // wheather to filter words by destination language code (destination language filter check box)
        // todo boolean lang_dest_selected = lang_choice.getDestLangSelected();
        
        if(!isActiveIndexForeign()){

            page_array = TPage.getByPrefix ( wikt_parsed_conn, word,
                            n_words_list, // any (first) N words, since "" == prefix
                            b_skip_redirects,
                            lang_choice.getSourceLang(), // lang_source_value,
                            filter_mean_sem_transl.filterByMeaning(),  //meaning_checkbox_value,
                            filter_mean_sem_transl.filterBySemanticRelation()); //sem_rel_CheckBox_value);
  
            // page_array = TPage.getByPrefix(wikt_parsed_conn, word_value.trim(),
               //                 n_words_list, b_skip_redirects,
                 //               source_lang,meaning_CheckBox_value,
                   //                         sem_rel_CheckBox_value);

            page_array_string = copyWordsToStringArray(page_array);
            printTextLinesToListView(page_array_string, word_list_lines, word_listview);
        } else {

            TLang foreign_lang = lang_choice.getSourceLang()[0];

            index_foreign = IndexForeign.getByPrefixForeign( wikt_parsed_conn, word,
                            n_words_list,
                            native_lang,
                            foreign_lang.getLanguage(),
                            filter_mean_sem_transl.filterByMeaning(),
                            filter_mean_sem_transl.filterBySemanticRelation());

            foreign_array_string = copyForeignWordsToStringArray(index_foreign);
            printTextLinesToListView(foreign_array_string, word_list_lines, word_listview);
        }
    }
    
    /** Copies data from words (string array _word_list_lines) to items of ListView.
    */
    private static void printTextLinesToListView(String[] _page_array_string,
                                        ObservableList<String> _word_list_lines,
                                        ListView<String> _word_ListView)
    {
        _word_list_lines.clear();
        _word_list_lines.addAll(_page_array_string);
        _word_ListView.setItems(_word_list_lines);
    }
    
    /** Opens new word card.
     */
    public void openWordCard() {
        WC wc = new WC ();
        ListView<String> l = word_listview;
        int selected_index = l.getSelectionModel().getSelectedIndex();
        if(selected_index < 0)
            selected_index = 0; // selected first word, when user pressed 'Enter'

        // native_lang : LanguageType
        if(!isActiveIndexForeign()){
            // get data for "page_array[l.selectedIndex]"
            wc.getDataForSelectedWordByTPage(wikt_parsed_conn, page_array[ selected_index ]);

            wc.createCXLangList(wikt_parsed_conn, page_array[ selected_index ]);

            //getDataForSelectedWord(word_value, l.selectedIndex);
        } else {
            //System.out.println( "WordList.openWordCard(): index_foreign.length=" + index_foreign.length +
            //                "; selected_index=" + selected_index);

            // if index_foreign has .native_page_title != null
            // then take it
            // else open .foreign_word

            IndexForeign i = index_foreign [ selected_index ];
            TPage native_page = i.getNativePage();
            if(null != native_page) {
                wc.getDataForSelectedWordByTPage(wikt_parsed_conn, native_page);
                wc.createCXLangList(wikt_parsed_conn, native_page);
            } else {

                TPage foreign_page = i.getForeignPage();
                if(null == foreign_page) {
                    System.out.println("Error (WordList.openWordCard()): native_page and foreign_page are NULL, where foreign_word={i.getForeignWord()}");
                    return;
                }
                wc.getDataForSelectedWordByTPage(wikt_parsed_conn, foreign_page);
                wc.createCXLangList(wikt_parsed_conn, foreign_page);
            }
        }

    }
    
    /** Copies data from page_array to page_array_string
    */
    public String[] copyWordsToStringArray(TPage[] pp) {
        //var list: SwingListItem[] = SwingListItem{};

        String[] result = new String[pp.length];
        for(int i=0; i<pp.length; i++) {
            TPage p = pp [i];
            result[i] = p.getPageTitle();
            //System.out.println("copyWordsToStringArray. p.title = {p.getPageTitle()}");
        }
        return result;
    };

    /** Copies data from the index of foreign words to string array
    */
    public String[] copyForeignWordsToStringArray(IndexForeign[] index)
    {
        String[] result = new String[index.length];
        for(int i=0; i<index.length; i++) {
            //println(" ->: {index[i].getConcatForeignAndNativeWords(" -> ")}");
            result[i] = index[i].getConcatForeignAndNativeWords(" -> ");
        }
        return result;
    };
}
