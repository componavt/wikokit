/* WordList.fx - main list of words in the dictionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik.search_window;

import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;
import wikt.sql.*;
import wikt.sql.index.IndexForeign;
import wiwordik.word_card.WC;

import javafx.scene.layout.LayoutInfo;
import javafx.scene.control.ListView;
//import javafx.ext.swing.SwingListItem;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;

//import java.util.Map;
//import java.util.LinkedHashMap;

/** List of words in the dictionary.
 */
public class WordList {

    public var height: Float; // reference to the parent Window

    var query_text_string : QueryTextString;
    var lang_choice : LangChoice;
    var filter_mean_sem_transl : FilterMeanSemRelTrans;

    var native_lang : LanguageType;

    /** Is equals to page_array_string (global search)
     * or to foreign_array_string (index search). */
    var word_list_lines : String[];

    /** Page titles from 'page_array' */
    var page_array_string: String[];
    
    /** Words extracted by several letters (prefix). */
    var page_array: TPage[];

    /** Page titles from 'index_foreign' */
    var foreign_array_string: String[];

    /** Foreign words extracted by several letters (prefix). */
    var index_foreign : IndexForeign[]; // //var foreign_word_to_index : Map<String, IndexForeign>;

    //var page_listItems: SwingListItem[] = SwingListItem{};

    var wikt_parsed_conn : Connect;

    /** Number of words visible in the list */
    var n_words_list : Integer;

    /** Skips #REDIRECT words if true. */
    var b_skip_redirects : Boolean;

    /** Set parameters of the class.
     * @param word0         initial user query search string
     * @param n_words_list  number of words visible in the list
     */
    public function initialize( _wikt_parsed_conn  : Connect,
                                _query_text_string  : QueryTextString,
                                _lang_choice        : LangChoice,
                                _filter_mean_sem_transl : FilterMeanSemRelTrans,
                                _native_lang        : LanguageType,
                                //_word0              : String,
                                _n_words_list       : Integer
                                ) {
        wikt_parsed_conn = _wikt_parsed_conn;
        query_text_string = _query_text_string;
        lang_choice     = _lang_choice;
        filter_mean_sem_transl = _filter_mean_sem_transl;
        //word0           = _word0;
        n_words_list    = _n_words_list;
        native_lang     = _native_lang;
    }
    
    /** Whether to skip #REDIRECT words. */
    public function setSkipRedirects( _b_skip_redirects : Boolean) {
        b_skip_redirects = _b_skip_redirects;
    }
    
    /** Whether to skip #REDIRECT words. */
    public function getSkipRedirects() : Boolean {
        return b_skip_redirects;
    }
    
    public function getPageArray() : TPage[] {
        return page_array;
    }

    /* List of words related to the user query string */
    public var word_ListView: ListView = ListView {

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
    }

    /** Checks whether the word list is filled from the table 'page',
     * or from the table 'index_XX', where XX is a foreign language code.
    */
    public function isActiveIndexForeign() {
        return lang_choice.getNumberSourceLang() == 1;
    }
    
    /** Copies data from TPagе[].text page_array to SwingListItem[]  page_listItems
     * @param lang_dest_selected    selection of the destination language filter
     *                              check box
    */
    public function updateWordList(
                                    // to disable
                                    lang_dest_selected : Boolean,
                                    
                                    b_skip_redirects    : Boolean,
                                    word                : String
                                    ) {

        //println("WordList.updateWordList(), word_value={word}");
        //println("WordList.updateWordList(), number of source languagues={lang_choice.getNumberSourceLang()}");

        if(not isActiveIndexForeign()){

            page_array = TPage.getByPrefix ( wikt_parsed_conn, word,
                            n_words_list, // any (first) N words, since "" == prefix
                            b_skip_redirects,
                            lang_choice.getSourceLang(), // lang_source_value,
                            filter_mean_sem_transl.filterByMeaning(),  //meaning_CheckBox_value,
                            filter_mean_sem_transl.filterBySemanticRelation()); //sem_rel_CheckBox_value);
  
            // page_array = TPage.getByPrefix(wikt_parsed_conn, word_value.trim(),
               //                 n_words_list, b_skip_redirects,
                 //               source_lang,meaning_CheckBox_value,
                   //                         sem_rel_CheckBox_value);

            page_array_string = copyWordsToStringArray(page_array);
            word_list_lines = page_array_string;
            
        } else {

            def foreign_lang : TLang = lang_choice.getSourceLang()[0];

            index_foreign = IndexForeign.getByPrefixForeign( wikt_parsed_conn, word,
                            n_words_list,
                            native_lang,
                            foreign_lang.getLanguage());

            foreign_array_string = copyForeignWordsToStringArray(index_foreign);
            word_list_lines = foreign_array_string;
        }
    }
    
    /** Opens new word card.
     */
    public function openWordCard() {
        var wc = WC {}
        var l = word_ListView;
        //println("(WordList.openWordCard()): index_foreign.length={index_foreign.size()}, l.selectedIndex={l.selectedIndex} ");

        // native_lang : LanguageType

        if(not isActiveIndexForeign()){
            // get data for "page_array[l.selectedIndex]"
            wc.getDataForSelectedWordByTPage(wikt_parsed_conn, page_array[ l.selectedIndex ]);

            wc.createCXLangList(wikt_parsed_conn, page_array[ l.selectedIndex ]);

            //getDataForSelectedWord(word_value, l.selectedIndex);
        } else {

            // if index_foreign has .native_page_title != null
            // then take it
            // else open .foreign_word

            //println(" index_foreign[0].native_page={index_foreign[0].getPageArray()}");

            def i : IndexForeign = index_foreign [ l.selectedIndex ];
            def native_page : TPage = i.getNativePage();
            if(null != native_page) {
                wc.getDataForSelectedWordByTPage(wikt_parsed_conn, native_page);
                wc.createCXLangList(wikt_parsed_conn, native_page);
            } else {

                def foreign_page : TPage = i.getForeignPage();
                if(null == foreign_page) {
                    println("Error (WordList.openWordCard()): native_page and foreign_page are NULL, where foreign_word={i.getForeignWord()}");
                    return;
                }
                wc.getDataForSelectedWordByTPage(wikt_parsed_conn, foreign_page);
                wc.createCXLangList(wikt_parsed_conn, foreign_page);
            }
        }

    }
    
    /** Copies data from page_array to page_array_string
    */
    public function copyWordsToStringArray(pp : TPage[]) : String[] {
        //var list: SwingListItem[] = SwingListItem{};

        var result : String[];
        for (p in pp) {
            insert p.getPageTitle() into result;
            //System.out.println("copyWordsToStringArray. p.title = {p.getPageTitle()}");
        }

        return result;
    };

    /** Copies data from the index of foreign words to string array
    */
    public function copyForeignWordsToStringArray(index : IndexForeign[])
                    : String[]
    {
        var result : String[];
        for (i in index) {
            //println(" ->: {i.getConcatForeignAndNativeWords(" -> ")}");
            insert i.getConcatForeignAndNativeWords(" -> ") into result;
        }

        return result;
    };

    /** Initialize ListItems,
     * copies data from TPagе[].text page_array to SwingListItem[]  page_listItems
    */
    /*public function copyWordsToListItems() {
        delete page_listItems;
        for (i in [0.. sizeof page_array-1])
            insert SwingListItem{ text: page_array[i].getPageTitle() } into page_listItems;
    }*/

}
