/* WordListAsyncUpdaterForeign.java - updates list of words in a background, asynchronously, 
 *            words in foreign languages by IndexForeign->getByPrefixForeign().
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.wordlist;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.index.IndexForeign;

public class WordListAsyncUpdaterForeign extends AsyncTask<Void, Void, IndexForeign[]> {

    private boolean is_cancel = false;

    SQLiteDatabase db;
    String prefix_foreign_word;
    int limit;
    LanguageType native_lang, foreign_lang;
    boolean b_meaning;
    boolean b_sem_rel;
    WordList wordlist;
    WordListArrayAdapter word_list_adapter;
    
    public WordListAsyncUpdaterForeign(
                                SQLiteDatabase _db,String _prefix_foreign_word,
                                int _limit,
                                LanguageType _native_lang,
                                LanguageType _foreign_lang,
                                boolean _b_meaning,
                                boolean _b_sem_rel,
                                WordList _wordlist,
                                WordListArrayAdapter _word_list_adapter
                                )
    {
        super();
        
        db = _db;
        prefix_foreign_word = _prefix_foreign_word;
        limit = _limit;
        native_lang = _native_lang;
        foreign_lang = _foreign_lang;
        
        b_meaning = _b_meaning;
        b_sem_rel = _b_sem_rel;
        wordlist = _wordlist;
        word_list_adapter = _word_list_adapter;
    }

    
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onCancelled() {
        is_cancel = true;
    }

    @Override
    protected IndexForeign[] doInBackground(Void... params) {
        
        /** Foreign words extracted by several letters (prefix). */
        IndexForeign[] index_foreign;
        
        index_foreign = IndexForeign.getByPrefixForeign( db, prefix_foreign_word,
                limit,
                native_lang,
                foreign_lang,
                b_meaning,
                b_sem_rel);
        
        return index_foreign;
    }

    protected void onPostExecute(IndexForeign[] index_foreign) {
        if(is_cancel)
            return;
        
        wordlist.index_foreign = index_foreign;
        wordlist.foreign_array_string = wordlist.copyForeignWordsToStringArray(index_foreign);
        word_list_adapter.updateData(wordlist.foreign_array_string, index_foreign);
        
    }
    
}
