package wikokit.kiwidict.word_card;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TTranslation;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.R;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WCPOSActivity extends Activity {

    private static SQLiteDatabase db;
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        

        // get parameters: TPage, TLang, TLangPOS
        Bundle extras = getIntent().getExtras(); 
        if(null == extras)
            return;
        
        //int page_id = extras.getInt("page_id");
        int lang_id = extras.getInt("lang_id");
        int lang_pos_id = extras.getInt("lang_pos_id");
        //System.out.println("Result activity: page_id = " + page_id + "; lang_id = " + lang_id + ... lang_pos_id);
        
        db = KWConstants.getDatabase();
        //TPage tpage = TPage.getByID(db, page_id);
        TLang tlang = TLang.getTLangFast(lang_id);
        TLangPOS lang_pos = TLangPOS.getByID(db, lang_pos_id);
        
        
        
        // visual
        //setContentView(R.layout.word_card_pos);
        LayoutParams fpwc = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1);
        
        LinearLayout result_layout = new LinearLayout(this);
        result_layout.setOrientation(LinearLayout.VERTICAL); 
        //result_layout.setLayoutParams(fpfp);
        result_layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        
        
        TextView pos_text = new TextView(this); 
        pos_text.setTextColor(KWConstants.pos_color);
        pos_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, KWConstants.text_size_medium);
        pos_text.setLayoutParams(fpwc);
        pos_text.setGravity(Gravity.TOP);
        pos_text.setText( getPOSText(lang_pos) );
        result_layout.addView(pos_text);
        
        
        // create meanings: definition + relations + translations
        //LinearLayout ll_pos = (LinearLayout) findViewById(R.id.posLinearLayout);

        TTranslation[] ttranslations = TTranslation.getByLangPOS(db, lang_pos);
        TMeaning[] mm = TMeaning.get(db, lang_pos);
        for(TMeaning m : mm) {
            
            WCMeaning _meaning = new WCMeaning();
            LinearLayout _ll = _meaning.create(db, this, m, mm.length, tlang, ttranslations);
            
            result_layout.addView( _ll );
        }
        
        setContentView(result_layout);        
    }
    
//TextView textView = new TextView(this);
//textView.setText("Hello world!");
//setContentView(textView);
    
    
    /** Gets a name of part of speech in the user's language (native Wiktionary language), 
     * e.g. "noun", or "conjunction". 
     **/
    public String getPOSText(TLangPOS _lang_pos) {
        
        POS _pos = _lang_pos.getPOS().getPOS();

        String s = _pos.toString(Connect.getNativeLanguage());
        //String s = _pos.getShortName(KWConstants.native_lang);
        
        if (KWConstants.DEBUGUI)
            s += String.format("; lang_pos.id = %d", _lang_pos.getID());

        return s;
    }
    
}
