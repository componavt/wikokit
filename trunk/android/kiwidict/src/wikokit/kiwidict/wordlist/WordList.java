package wikokit.kiwidict.wordlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.index.IndexForeign;
import wikokit.kiwidict.lang.LangChoice;
import wikokit.kiwidict.search_window.QueryTextString;
import wikokit.kiwidict.word_card.WCActivity;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WordList extends ListActivity {
     
    public Context context;
    QueryTextString query_text_string;
    ProgressBar spinning_wheel;
    LangChoice lang_choice;
// todo    FilterMeanSemRelTrans filter_mean_sem_transl;
    
    LanguageType native_lang;
    
    SQLiteDatabase db;
    
    /** Page titles from 'page_array' */
    String[] page_array_string;
    
    /** Words extracted by several letters (prefix). */
    TPage[] page_array;
    private final static TPage[] NULL_TPAGE_ARRAY = new TPage[0];
    
    /** Page titles from 'index_foreign' */
    String[] foreign_array_string;

    /** Foreign words extracted by several letters (prefix). */
    IndexForeign[] index_foreign; // //var foreign_word_to_index : Map<String, IndexForeign>;
    
    /** Number of words visible in the list */
    int n_words_list;

    /** Skips #REDIRECT words if true. */
    boolean b_skip_redirects;
    
    ListView word_listview;
    WordListArrayAdapter word_list_adapter;
    AsyncTask<Void, Void, TPage[]> wordlist_async;
    
    public WordList (Context _context) {
        page_array = NULL_TPAGE_ARRAY;
        context = _context;
    }
    
    /** Set parameters of the class.
     * @param word0         initial user query search string
     * @param n_words_list  number of words visible in the list
     */
    public void initialize( SQLiteDatabase _db,
                            QueryTextString _query_text_string,
                            ProgressBar _spinning_wheel,
                            LangChoice _lang_choice,
            // todo FilterMeanSemRelTrans _filter_mean_sem_transl,
                            LanguageType _native_lang,
                                                        //_word0              : String,
                            int _n_words_list,
                            ListView _word_listview,    // GUI
                            Activity main_activity
                          ) {
        db = _db;
        query_text_string = _query_text_string;
        spinning_wheel = _spinning_wheel;
        lang_choice     = _lang_choice;
        //filter_mean_sem_transl = _filter_mean_sem_transl;
        
        n_words_list    = _n_words_list;
        native_lang     = _native_lang;
   
        word_listview = _word_listview;
 
        //ListView listView = getListView();
        //listView.setTextFilterEnabled(true);
        
        // some initial values
        String[] page_array_string = new String[]{"line1"}; // , "line test 2", "endline", "line4"};
        List<String> _list = new ArrayList<String>( page_array_string.length );
        _list.addAll(Arrays.asList(page_array_string));
        
        word_list_adapter = new WordListArrayAdapter(
                main_activity, //this, 
                _list, // page_array_string, 
                getPageArray());
        
        word_listview.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                //Toast.makeText(context, // getApplicationContext(),
                //    "Click ListItem Number " + position, Toast.LENGTH_LONG)
                //    .show();
                
                String word_in_list = getSelectedWordInList( position );
                
                if(word_in_list.length() > 0)   // there is a word to search in Wiktionary
                {
                    QueryTextString q = query_text_string;
                    q.enableUpdateWordList(false);
                    q.setWordValue(word_in_list);
                    q.setWordValueOld(word_in_list);
                    q.word_textfield.setSelection(word_in_list.length()); // move cursor to the end of the word
                    q.enableUpdateWordList(true);
                    
                    /* // meaningless for Android application with only one open window
                     * if( q.word_value_last_open_card.length() == 0 ||  // if 0 word cards are opened
                       !q.word_value_last_open_card.equals(word_in_list) // if user trying to open the same card
                      )
                    {                    
                        q.word_value_last_open_card = word_in_list;
                        */
                        openWordCard(position); // selected_index
                    //}
                }
                
            }
        });
        word_listview.setAdapter(word_list_adapter);
    }
    
    /*public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setListAdapter(new WordListArrayAdapter(this, page_array_string)); // , page_array));
    }*/
    
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
    
    /** Checks whether the word list is filled from the table 'page',
     * or from the table 'index_XX', where XX is a foreign language code.
    */
    public boolean isActiveIndexForeign() {
        //System.out.println("WordList.isActiveIndexForeign(), lang_choice.getNumberSourceLang() = " + lang_choice.getNumberSourceLang());
        return lang_choice.getNumberSourceLang() == 1;
    }
    
    /** Copies data from TPagе[].text page_array to SwingListItem[]  page_listItems
     */
     public void updateWordList( boolean b_skip_redirects,
                                 String word
                               )
     {
         // no, since it could be interrupted: page_array = null;
//?         String[] page_titles = TPage.getPageTitles(page_array);
         
         //System.out.println("WordList.updateWordList(), word_value=" + word);
         //System.out.println("WordList.updateWordList(), number of source languagues="+ lang_choice.getNumberSourceLang());

         // wheather to filter words by destination language code (destination language filter check box)
         // todo boolean lang_dest_selected = lang_choice.getDestLangSelected();
         
         if(!isActiveIndexForeign()){

             if(null != wordlist_async) 
                 wordlist_async.cancel(true);
             
             wordlist_async = new WordListAsyncUpdater(
                     db, word,
                     n_words_list, // any (first) N words, since "" == prefix
                     b_skip_redirects,
                     lang_choice.getSourceLang(), // lang_source_value,
false,//todo                             filter_mean_sem_transl.filterByMeaning(),  //meaning_checkbox_value,
false,
                     this,
                     word_list_adapter,
                     spinning_wheel).execute();
/*             
             TPage[] page_array_new = TPage.getByPrefix ( db, word,
                             n_words_list, // any (first) N words, since "" == prefix
                             b_skip_redirects,
                             lang_choice.getSourceLang(), // lang_source_value,
false,//todo                             filter_mean_sem_transl.filterByMeaning(),  //meaning_checkbox_value,
false); //todo                             filter_mean_sem_transl.filterBySemanticRelation()); //sem_rel_CheckBox_value);
*/
         }
        else {

             TLang foreign_lang = lang_choice.getSourceLang()[0];

             if(null == foreign_lang) {
                 Toast.makeText(context, 
                         "Error in WordList.updateWordList(): foreign_lang is NULL!", Toast.LENGTH_LONG).show();
                 return;
             }
             
             index_foreign = IndexForeign.getByPrefixForeign( db, word,
                             n_words_list,
                             native_lang,
                             foreign_lang.getLanguage(),
false, //todo                             filter_mean_sem_transl.filterByMeaning(),
false);//todo                             filter_mean_sem_transl.filterBySemanticRelation());

             foreign_array_string = copyForeignWordsToStringArray(index_foreign);
             word_list_adapter.updateData(foreign_array_string, index_foreign);
         }
     }
    
    /** Copies data from page_array to page_array_string
     */
     public String[] copyWordsToStringArray(TPage[] pp) {

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
     
     
     
     /** Gets first word from the WordList or empty "" String if it is absent. */
     public String getFirstWordInList() {
         if(null == page_array_string || 0 == page_array_string.length)
             return "";
         return page_array_string[0];
     }
     
     /** Gets selected item from the list else the first word from the WordList,
      * or empty string "" if the list is empty.
      * @param position   the position of the clicked-upon item in the list - currently selected item.
      **/
     String getSelectedWordInList(int position) {
         
         if (ListView.INVALID_POSITION == position)
             return "";
         
         String selected_item = "";
         if(!isActiveIndexForeign()){
             if( position < page_array_string.length ) {
                 
                 //    cur != null && cur.getValue()!= null && cur.getValue().length() > 0) {
                 selected_item = page_array_string[position];
             }
         } else {
             if( position < foreign_array_string.length ) {
                 selected_item = foreign_array_string[position];
             }
         }
         
         return selected_item;
     }
     
     /** Gets selected item from the list else the first word from the WordList,
      * or empty string "" if the list is empty. */
/*     public String getSelectedOrFirstWordInList() {
         String selected_item = getSelectedWordInList();
         
         if(selected_item.length() == 0)
             return getFirstWordInList();
         return selected_item;
     }
*/     
     
     /** Opens new word card.
      */
     public void openWordCard(int selected_index) {
         
         //int selected_index = l.getSelectionModel().getSelectedIndex();
         if(selected_index < 0)
             selected_index = 0; // selected first word, when user pressed 'Enter'

         // native_lang : LanguageType
         if(!isActiveIndexForeign()){
             // get data for "page_array[l.selectedIndex]"
             runWordCardActivity(page_array[ selected_index ]);
             
         } else {
             //System.out.println( "WordList.openWordCard(): index_foreign.length=" + index_foreign.length +
             //                "; selected_index=" + selected_index);

             // if index_foreign has .native_page_title != null
             // then take it
             // else open .foreign_word

             IndexForeign i = index_foreign [ selected_index ];
             TPage native_page = i.getNativePage();
             if(null != native_page) {
                 runWordCardActivity( native_page );
             } else {

                 TPage foreign_page = i.getForeignPage();
                 if(null == foreign_page) {
                     System.out.println("Error (WordList.openWordCard()): native_page and foreign_page are NULL, where foreign_word={i.getForeignWord()}");
                     return;
                 }
                 runWordCardActivity( foreign_page );
             }
         }
     }
     
     /** Opens new word card.
      */
     private void runWordCardActivity(TPage _tpage) // String page_title)
     {
         if(null == _tpage)
             return;
         
         Bundle b = new Bundle();
         b.putInt("page_id", _tpage.getID()); // pass parameter: TPage _tpage by page_id
         Intent i = new Intent(context, WCActivity.class); // this == _context
         i.putExtras(b);
         context.startActivity(i);
         
         //System.out.println("runWordCardActivity: I am trying to start WCActivity...");
     }
     
}