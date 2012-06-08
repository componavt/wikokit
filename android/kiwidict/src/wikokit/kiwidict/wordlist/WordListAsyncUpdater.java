/* WordListAsyncUpdater.java - updates list of words in a background, asynchronously, 
 *                              words in native language by TPage->getByPrefix().
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */


package wikokit.kiwidict.wordlist;

import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TPage;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class WordListAsyncUpdater extends AsyncTask<Void, Void, TPage[]> {

    private boolean is_cancel = false;

    SQLiteDatabase db;
    String prefix;
    int limit;
    boolean b_skip_redirects;
    TLang source_lang[];
    boolean b_meaning;
    boolean b_sem_rel;
    WordList wordlist;
    WordListArrayAdapter word_list_adapter;
    ProgressBar spinning_wheel;
    
    public WordListAsyncUpdater(
                                SQLiteDatabase _db,String _prefix,
                                int _limit, boolean _b_skip_redirects,
                                TLang _source_lang[], // String str_source_lang,
                                boolean _b_meaning,
                                boolean _b_sem_rel,
                                WordList _wordlist,
                                WordListArrayAdapter _word_list_adapter,
                                ProgressBar _spinning_wheel
                                )
    {
        super();
        
        db = _db;
        prefix = _prefix;
        limit = _limit;
        b_skip_redirects = _b_skip_redirects;
        source_lang = _source_lang;
        b_meaning = _b_meaning;
        b_sem_rel = _b_sem_rel;
        wordlist = _wordlist;
        word_list_adapter = _word_list_adapter;
        spinning_wheel = _spinning_wheel;
    }

    
    @Override
    protected void onPreExecute() {
        spinning_wheel.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCancelled() {
        is_cancel = true;
    }

    @Override
    protected TPage[] doInBackground(Void... params) {
        
        TPage[] page_array = TPage.getByPrefix ( db, prefix,
                limit,
                b_skip_redirects,
                source_lang,
                b_meaning,
                b_sem_rel);
        
        return page_array;
    }

    protected void onPostExecute(TPage[] page_array) {
        if(is_cancel)
            return;
        
        spinning_wheel.setVisibility(View.INVISIBLE);
        
        wordlist.page_array = page_array;
        wordlist.page_array_string = wordlist.copyWordsToStringArray(page_array);
        word_list_adapter.updateData(wordlist.page_array_string, page_array);
    }

}
