/* WCLanguage.java - A part of word card corresponds to a language part
 * of a page (entry) in Wiktionary. It contains POS's sub-parts.
 *
 * Copyright (c) 2009-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.word_card;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.R;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

/** Language part of a Wiktionary page. It contains POS's sub-parts.
*
* @see wikt.word.WLanguage
*/
public class WCLanguageActivity extends TabActivity {

    private static SQLiteDatabase db;
    
    TabHost tab_host_pos;
    
    /** Current TPage corresponds to selected word. */
    //public TPage tpage;
    
    /** Current TLang corresponds to the (one language) part of the selected word. */
    //public TLang tlang;
    
    /** Parts of speech of current word. */
    //WCPOSActivity[] pos;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        // get parameter: TPage, TLang
        Bundle extras = getIntent().getExtras(); 
        if(null == extras)
            return;
        
        int page_id = extras.getInt("page_id");
        int lang_id = extras.getInt("lang_id");
        //System.out.println("Result activity: page_id = " + page_id + "; lang_id = " + lang_id);
        
        db = KWConstants.getDatabase();
        TPage tpage = TPage.getByID(db, page_id);
        TLang tlang = TLang.getTLangFast(lang_id);
        
        String s = getLanguageTitle(tlang.getLanguage());
                
        setContentView(R.layout.word_card_language);
        tab_host_pos = (TabHost)findViewById(android.R.id.tabhost);
        
        TextView language_text = (TextView) findViewById(R.id.languageText);
        language_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, KWConstants.text_size_medium);
        language_text.setText(s);
        
        createPOSTabs(tpage, tlang);
    }
    
    
    /** Gets text with a name of language, e.g. "Russian (ru)", or "English (en)". */
    public String getLanguageTitle(LanguageType _lang) {
        //     "lang (code)"
        return _lang.getName(KWConstants.native_lang) + " (" + _lang.getCode() + ")";
        // System.out.println("WCLanguage.create(). language_name_value = {language_name_value}");
    }
    
    
    /** Creates tab with part of entry related to one language and one part of speech, 
     * runs POS activity. 
     **/ 
    public void createOnePOSTab(TPage _tpage, TLang _tlang, TLangPOS _lang_pos) {
   
        Bundle b = new Bundle();
        b.putInt("page_id", _tpage.getID()); // pass parameter: TPage by page_id
        b.putInt("lang_id", _tlang.getID()); // pass parameter: TLang by lang_id
        b.putInt("lang_pos_id", _lang_pos.getID()); // pass parameter: TLangPOS by lang_pos_id
        
        Intent i = new Intent(this, WCPOSActivity.class);
        
        i.putExtras(b);
        //System.out.println("Source activity: page_id = " + _tpage.getID() 
        //                    + "; lang_id = " + _tlang.getID()
        //                    + "; lang_pos_id = " + _lang_pos.getID());
        
        TabSpec _tabspec = tab_host_pos.newTabSpec( "" + _lang_pos.getID() ); // some unique text
        _tabspec.setIndicator( getShortPOSText(_lang_pos) );
        _tabspec.setContent(i);
        tab_host_pos.addTab(_tabspec);
    }

    
    /** Creates list of parts of speech (parts of wiki pages),
     * builds visual blocks with these languages,
     * fills language tabs.
     **/
    public void createPOSTabs(TPage _tpage, TLang _tlang) {
        // get POS for all languages, filter by our language 'tlang'
        
        // ! bad design: repetition in each tab :(, I hope there are few articles with really many languages.
        TLangPOS[] lang_pos_array = TLangPOS.get(db, _tpage);
        
        int pos_counter = 0;
        for (TLangPOS _lang_pos : lang_pos_array) {
            if(_lang_pos.getLang().getLanguage() == _tlang.getLanguage()) {
                createOnePOSTab( _tpage, _tlang, _lang_pos );
                pos_counter ++;
            }
        }
        
        // let's tab will be hidden if there is only one language for the entry  (tab height := zero)
        if(1 == pos_counter) {
            tab_host_pos.getTabWidget().getChildAt(0).getLayoutParams().height = 0;
        }
    }
    
    /** Gets short name of part of speech in the user's language, e.g. "noun", or "conjunction". */
    public String getShortPOSText(TLangPOS _lang_pos) {
        
        POS _pos = _lang_pos.getPOS().getPOS();

        //String s = _pos.toString(KWConstants.native_lang);
        String s = _pos.getShortName(KWConstants.native_lang);
        
        if (KWConstants.DEBUGUI)
            s += String.format("; lang_pos.id = %d", _lang_pos.getID());

        return s;
    }
    
}
