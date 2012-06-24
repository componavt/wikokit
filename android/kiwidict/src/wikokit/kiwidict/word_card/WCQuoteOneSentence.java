/* WCQuoteOneSentence.java - A part of word card corresponds to one quotation
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2011-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.kiwidict.word_card;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.quote.TQuotRef;
import wikokit.base.wikt.sql.quote.TQuote;
import wikokit.base.wikt.word.WQuote;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.util.GUI;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/** WCQuoteOneSentence contains one quote (phrase/sentence) that illustrates
 * one meaning of Wiktionary word and reference data (author, title, year...).
 *
 * @see wikt.word.WQuote
 */
public class WCQuoteOneSentence {

    /** Quotation sentence. */
    String sentence_text;

    /** Translation of the quotation sentence. */
    String translation_text;
    int translation_indent = 20;

    /** Related bibliography text: author, title, year, publisher. */
    //var reference_text : String;

    /** Author name. */
    String author_name;

    /** Source title. */
    String title;

    /** Years of the book. */
    String years_range;

    /** Publisher. */
    String publisher;

    /** Source. */
    String source;
    
    static final LayoutParams mpwc = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
    
    
    /** Creates a part of card (parts of wiki pages) with list of quotes
     * related to one meaning (sense).
    **/
    public LinearLayout create (Activity _context,
                        SQLiteDatabase db,
                        TQuote _quote) 
    {
        // GUI
        LanguageType native_lang = Connect.getNativeLanguage();
        
        // quote_gap between quotes
        LayoutParams mpwc_top_margin = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
        mpwc_top_margin.setMargins(0, GUI.ConvertPixelsToDP(_context, KWConstants.quote_gap), 0, 0); // .setMargins(left, top, right, bottom);
        
        LinearLayout result_layout = new LinearLayout(_context);
        result_layout.setOrientation(LinearLayout.VERTICAL); 
        result_layout.setLayoutParams(mpwc_top_margin);
        
        // 1. Sentence text
        // WT:ELE: "In the quotation itself the word being illustrated should be in boldface."
        // substitues '''marked word''' by <b>bold</b>
        String s = WQuote.removeHighlightedMarksFromSentence( native_lang,
                                                  _quote.getText(), "<b>", "</b>");
                                        
        // additional treatment of the sentence text (e.g., &nbsp;, &#160; -> " ")
        boolean is_sqlite = true;
        sentence_text = WQuote.transformSentenceTextToHTML(
                            is_sqlite, native_lang, s);
        
        TextView tv_quote = new TextView(_context);
        tv_quote.setLayoutParams(mpwc);
        tv_quote.setTextColor(KWConstants.quote_color);
        tv_quote.setText(Html.fromHtml(sentence_text), TextView.BufferType.SPANNABLE);
        result_layout.addView(tv_quote);
        
    
        
        // Optional node, only if there is a translation of the quote sentence.
        translation_text = _quote.getTranslation(db);
        if(translation_text.length() > 0) {
            translation_text = WQuote.removeHighlightedMarksFromSentence( native_lang,
               translation_text, "<b>", "</b>");
            
            TextView quote_trans = new TextView(_context);
            quote_trans.setLayoutParams(mpwc);
            quote_trans.setTextColor(KWConstants.quote_translatioin_color);
            quote_trans.setText(Html.fromHtml(translation_text), TextView.BufferType.SPANNABLE);
            result_layout.addView(quote_trans);
        }

        
        // 2. Reference text (also optional node)
        TextView tv_quot_ref = getReferenceTextView(_quote, _context); 
        if(null != tv_quot_ref)
            result_layout.addView(tv_quot_ref);
        
        return result_layout;
    }
    
    
    /** Gets bibliographic information about quote sentence.
     * 
     * @return null if there are no author name, title, years for this quotation.
     **/
    private TextView getReferenceTextView (TQuote _quote, Activity _context)
    {
        TQuotRef quot_ref = _quote.getReference();
        if(null == quot_ref)
            return null;
        
        // 2a. data and logic
        //reference_text = "{quot_ref.getYearsRange()}{quot_ref.getAuthorName()}";
        years_range = quot_ref.getYearsRange();
        author_name = quot_ref.getAuthorName();
        title       = quot_ref.getTitle();
        publisher   = quot_ref.getPublisherName();
        source      = quot_ref.getSourceName();
        
        
        if(title.contains("\\\""))   // \" -> " (SQLite feature)
            title = title.replace("\\\"", "\"");

        // commas and //:
        // 1. years_range, author_name
        if(years_range.length() > 0 && (author_name.length() > 0 || title.length() > 0))
            years_range = years_range.concat(", ");

        // 2. author_name, title
        if(author_name.length() > 0 && title.length() > 0)
            author_name = author_name.concat(", ");

        // 3. title // (publisher or source)
        if(title.length() > 0 && publisher.length() > 0)
            title = title.concat(" // ");

        // 4. (title or publisher), source
        if((title.length() > 0 || publisher.length() > 0) && source.length() > 0)
            publisher= publisher.concat(", ");


        if(KWConstants.DEBUGUI) {
            source = source + "; quot_ref.id=" + quot_ref.getID();

            sentence_text = sentence_text + "; quote.id=" + _quote.getID();

            if(translation_text.length() > 0)
                translation_text = translation_text + "; quot_translation.quote_id=" + _quote.getID();
        }
        
        
        
        // 2b. GUI
        
        StringBuilder sb = new StringBuilder();
        
        if(0 < years_range.length())
            sb = sb.append("<b>").append( years_range ).append("</b>");   // let's <BOLD>years</BOLD>
                    
        if(0 < author_name.length())
            sb = sb.append(author_name);
            
        if(0 < title.length())
            sb = sb.append(title);
        
        if(0 < publisher.length())
            sb = sb.append(publisher);
        
        if(0 < source.length())
            sb = sb.append(source);
        
        // right margin, since reference is aligned to right side
        LayoutParams mpwc_right_margin = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
        mpwc_right_margin.setMargins(0, 0, GUI.ConvertPixelsToDP(_context, 10), 0); // .setMargins(left, top, right, bottom);
        
        TextView quote_ref = new TextView(_context);
        quote_ref.setLayoutParams(mpwc_right_margin);
        quote_ref.setGravity(Gravity.RIGHT);
        
        quote_ref.setText(Html.fromHtml( sb.toString() ), TextView.BufferType.SPANNABLE);
        quote_ref.setTextColor(KWConstants.quote_reference_color);
        
        return quote_ref;
    }
   
}
