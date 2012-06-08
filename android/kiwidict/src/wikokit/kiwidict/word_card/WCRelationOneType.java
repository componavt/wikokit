package wikokit.kiwidict.word_card;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.constant.RelationLocal;
import wikokit.base.wikt.multi.ru.name.RelationRu;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TRelation;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.util.GUI;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WCRelationOneType {

    /** Creates a part of card (parts of wiki pages) with list of quotes
     * related to one meaning (sense).
     *
     * @return null if there are no any quotes for this meaning.
    **/
    public TextView create ( Activity _context,
                            SQLiteDatabase db,
                            TRelation[] rels,
                            Relation _relation_type
                          )
    {
        // GUI
        LayoutParams mpwc_bottom_margin = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
        mpwc_bottom_margin.setMargins(0, GUI.ConvertPixelsToDP(_context, KWConstants.relation_gap), 0, 0); // .setMargins(left, top, right, bottom);
     
        StringBuilder list = new StringBuilder();
        for(TRelation r : rels) {
            if(r.getRelationType() == _relation_type) {
                list.append(r.getWikiText().getText());
                list.append(", ");
            }
        }

        int len = list.length();    // at least one relation exists.
        if(len > 0) {
            
            StringBuilder sb = new StringBuilder();
            
            sb = sb.append( _relation_type.toString(KWConstants.native_lang) ).
                    append(": ");
            int pos_words = sb.length();
            
            // relation_words
            sb = sb.append( list.substring(0, len - 2) );
                    
            TextView tv_rel = new TextView(_context);
            tv_rel.setLayoutParams(mpwc_bottom_margin);

            Spannable span = new SpannableString( sb.toString() );
            
            span.setSpan(new BackgroundColorSpan(KWConstants.relation_background_color), pos_words, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            
            tv_rel.setText(span);  
                        
            return tv_rel;
        }

        return null;
    }

}
