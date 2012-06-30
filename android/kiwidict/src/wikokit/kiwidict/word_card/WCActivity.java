/* WCActivity.java - Word card corresponds to a page (entry) in Wiktionary.
 *
 * Copyright (c) 2009-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.word_card;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.enwikt.R;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class WCActivity extends TabActivity{

    private static SQLiteDatabase db;
    
    /** Current TPage corresponds to selected word. */
    //public TPage tpage;
    
    /** Todo: text of card should be available by Ctrl+C. */
    String card_text_value;
    
    String header_page_title;
    TextView headerText;
    TextView link_to_wikt;
    
    TabHost tab_host_languages;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.word_card);
        
        // get parameter: TPage _tpage by page_id
        Bundle extras = getIntent().getExtras(); 
        if(extras != null)
        {
            int page_id = extras.getInt("page_id");
            // System.out.println("page_id = " + page_id);
            
            db = KWConstants.getDatabase();
            TPage tpage = TPage.getByID(db, page_id);
            createLanguageTabs(tpage);
        }
    }           

    
    /** Creates tab with part of entry related to one language, 
     * runs language activity. 
     **/ 
    public void createOneLanguageTab(TPage _tpage, TLang _tlang) {  //TLangPOS _lang_pos) {
   
        Bundle b = new Bundle();
        b.putInt("page_id", _tpage.getID()); // pass parameter: TPage by page_id
        b.putInt("lang_id", _tlang.getID()); // pass parameter: TLang by lang_id
        
        Intent i = new Intent(this, WCLanguageActivity.class);
        //Intent i = new Intent().setClass(this, WCLanguageActivity.class);  
        
        i.putExtras(b);
        //System.out.println("Source activity: page_id = " + _tpage.getID() + "; lang_id = " + _tlang.getID());
        
        TabSpec _tabspec = tab_host_languages.newTabSpec( "" + _tlang.getID() ); // some unique text

        LanguageType _lang = _tlang.getLanguage();
        _tabspec.setIndicator(_lang.getCode());
        _tabspec.setContent(i);
        
        tab_host_languages.addTab(_tabspec);
    }
    
    /** Creates switch tabs  of sub-languages (parts of wiki pages),
     * builds visual blocks with these languages,
     * fills language tabs.
     **/
    public void createLanguageTabs(TPage _tpage)
    {
        if(null != _tpage) {
            //System.out.println("Ops 2. createCXLangList");

            initGUI();
            printHeaderText (_tpage);

            // get list of languages for this word
            TLang[] tlanguages = TLangPOS.getLanguages(db, _tpage);

            tab_host_languages = (TabHost)findViewById(android.R.id.tabhost);
            
            
            
            // 1. for 1: add language section in native (e.g., English for English Wiktionary)
            // Wiktionary (native) language should be before foreign languages
            //insert l.group into lang_VBox.content;
            /*for (TLang tl : tlanguages) {
                if(tl.getLanguage() == conn.getNativeLanguage()) {    
                    WCLanguage l = new WCLanguage();
                    l.create(conn, card_scene, tl.getLanguage(), lang_pos_array);
                    lang_VBox.getChildren().add(l.group);
                } 
            }*/
            
            for (TLang tl : tlanguages)
                if(tl.getLanguage() == Connect.getNativeLanguage())
                    createOneLanguageTab( _tpage, tl );
            
/*            
            // 2. for 2: add all other languages
            for (TLang tl : tlanguages) {
                if(tl.getLanguage() != conn.getNativeLanguage()) {

                    WCLanguage l = new WCLanguage();
                    l.create(conn, card_scene, tl.getLanguage(), lang_pos_array);
                    lang_VBox.getChildren().add(l.group);
                }
            }
            
            if(lang_pos_array.length > 0) {// big new frame (with word card)
                card.setPreferredSize(new Dimension(WConstants.wordcard_width, WConstants.wordcard_height));
                card.setMinimumSize(new Dimension(WConstants.wordcard_width, WConstants.wordcard_height));
            }
*/          
            for (TLang tl : tlanguages)
                if(tl.getLanguage() != Connect.getNativeLanguage())
                    createOneLanguageTab( _tpage, tl );
            
            //for (TLangPOS _lang_pos : lang_pos_array) {
            //    if(_lang_pos.getLang().getLanguage() != KWConstants.native_lang) {
            //        createLanguageTab( _lang_pos );
            //    }
            //}
            
            
            //tab_host_languages.getTabWidget().setCurrentTab(1);
            //tabHost.setOnTabChangedListener(MyOnTabChangeListener);
            
            // let's tab will be hidden if there is only one language for the entry  (tab height := zero)
            if(tlanguages.length == 1) {
                tab_host_languages.getTabWidget().getChildAt(0).getLayoutParams().height = 0;
            }
        }        
    }

    void initGUI() {
        headerText = (TextView) findViewById(R.id.headerText);
        link_to_wikt = (TextView) findViewById(R.id.linkToWikt);
    }
    
    
    /** Prints page_title as a header of a word card.
     *  Prints sign "+" or "-" if there is a separate page for this word in WT.
    */
    public void printHeaderText (TPage _tpage) {

        if(null != _tpage) {

            String page_title = _tpage.getPageTitle();
            header_page_title = page_title;
            setTitle(header_page_title);

            String s = "";
            if(_tpage.isInWiktionary()) {
                s = "+"; } // page_title + " (+)"; }
            else
                s = "-";   // page_title + " (-)";

            if(_tpage.isRedirect())
                s += "\n \nSee " + _tpage.getRedirect() + " (Redirect)"; // See or См.???
            
            headerText.setText(s);  // content: "headerText (page.is_in_wiktionary)" 
            
            
            String link = getLinktoWiktionaryEntry( page_title );
            if(link.length() > 0) {
                link_to_wikt.setText(link);
                Linkify.addLinks(link_to_wikt, Linkify.WEB_URLS);
            }
            
        }
    }
    
    /** Gets link to entry, if there is a corresponding Wiktionary article.*/
    public String getLinktoWiktionaryEntry (String page_title) {

        String s = "";
        if(null != page_title && page_title.length() > 0) {
        //if(_tpage.isInWiktionary()) {
            // todo - in really, isInWiktionary() should be called - to test more

            // replace all spaces by underscores "_"
            String s_underscored = page_title.replaceAll(" ", "_");

            //s = "<a href=\"http://" + KWConstants.native_lang + ".wiktionary.org/wiki/" + s_underscored + 
            //        "\">" + page_title + "</a>";
            s = "http://" + Connect.getNativeLanguage() + ".wiktionary.org/wiki/" + s_underscored;
        }
        return s;
    }
    
        
}
