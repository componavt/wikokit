/* WCQuote.java - A part of word card corresponds to quotations part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
 
package wiwordik.word_card;

import wikokit.base.wikt.sql.quote.TQuote;
import wikokit.base.wikt.sql.quote.TQuotRef;
import wikokit.base.wikt.word.WQuote;
import wiwordik.WConstants;
import wikokit.base.wikipedia.sql.Connect;

import javafx.scene.text.Text;
import javafx.scene.text.Font;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.lang.*;
import javafx.scene.text.FontWeight;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.FontPosture;


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

    public VBox group = new VBox();
    
    
    /** Creates a part of card (parts of wiki pages) with list of quotes
     * related to one meaning (sense).
    **/
    public void create (Connect conn,
                        TQuote _quote) {

        // 1. Sentence text
        // WT:ELE: "In the quotation itself the word being illustrated should be in boldface."
        // todo: substitue '''marked word''' by some color, but
        // we are waiting "Rich Text" in JavaFX 2.0
        String s = WQuote.removeHighlightedMarksFromSentence( WConstants.native_lang,
                                                  _quote.getText());
                                        
        // additional treatment of the sentence text (e.g., &nbsp;, &#160; -> " ")
        sentence_text = WQuote.transformSentenceText(
                                    WConstants.IS_SQLITE, WConstants.native_lang, s);
        
        Text t_sentence_text = new Text(sentence_text);
        
        t_sentence_text.setWrappingWidth(WConstants.wordcard_width - 35);
        t_sentence_text.setFont(Font.font(null, FontWeight.NORMAL, 14));
        t_sentence_text.setFill(Color.GREEN);
        
        group.getChildren().addAll(t_sentence_text);
        
        translation_text = _quote.getTranslation(conn);
        if(translation_text.length() > 0) {
            translation_text = WQuote.removeHighlightedMarksFromSentence( WConstants.native_lang,
               translation_text);
            
            // Optional node, only if there is a translation of the quote sentence.
            Text translation_text_node = new Text(translation_text);
            translation_text_node.setTranslateX(translation_indent);
            translation_text_node.setWrappingWidth(WConstants.wordcard_width - 35 - translation_indent);
            translation_text_node.setFont(Font.font(null, FontWeight.NORMAL, 14));
            translation_text_node.setFill(Color.CHOCOLATE);
            
            group.getChildren().addAll(translation_text_node);
        }

        // 2. Reference text
        TQuotRef quot_ref = _quote.getReference();
        if(null == quot_ref)
            return;
        
        // 2a. data and logic
        //reference_text = "{quot_ref.getYearsRange()}{quot_ref.getAuthorName()}";
        years_range = quot_ref.getYearsRange();
        author_name = quot_ref.getAuthorName();
        title       = quot_ref.getTitle();
        publisher   = quot_ref.getPublisherName();
        source      = quot_ref.getSourceName();
        
        
        if(WConstants.IS_SQLITE && title.contains("\\\""))   // \" -> " (SQLite feature)
            title = title.replace("\\\"", "\"");

        // commas and //:
        // 1. years_range, author_name
        if(years_range.length() > 0 && author_name.length() > 0)
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


        if(WConstants.DEBUGUI) {
            source = "{source}; quot_ref.id={quot_ref.getID()}";

            sentence_text = "{sentence_text}; quote.id={_quote.getID()}";

            if(translation_text.length() > 0)
                translation_text = "{translation_text}; quot_translation.quote_id={_quote.getID()}";
        }
        
        

        
        // 2b. GUI
        HBox reference_hbox = new HBox();
        reference_hbox.setAlignment(Pos.BASELINE_RIGHT);
        reference_hbox.setSpacing(1);
        //                                  top right bottom left
        reference_hbox.setPadding(new Insets(0,  10,    0,    0));
        
        // temp debug:
        //reference_hbox.setStyle("-fx-background-color:#e0e0e0");
        
        if(0 < years_range.length()) {
            Text t_years_range = new Text(years_range);
            t_years_range.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
            t_years_range.setFill(Color.GREY);
            reference_hbox.getChildren().addAll(t_years_range);
        }
        
        if(0 < author_name.length()) {
            Text t_author_name = new Text(author_name);
            t_author_name.setFont(Font.font(null, FontWeight.NORMAL, 12));
            t_author_name.setFill(Color.GREY);
            reference_hbox.getChildren().addAll(t_author_name);
        }
        
        if(0 < title.length()) {
            Text t_title = new Text(title);
            t_title.setFont(Font.font("Times New Roman Italic", FontPosture.ITALIC, 12));
            t_title.setFill(Color.GREY);
            reference_hbox.getChildren().addAll(t_title);
        }
        
        if(0 < publisher.length()) {
            Text t_publisher = new Text(publisher);
            t_publisher.setFont(Font.font(null, FontWeight.NORMAL, 12));
            t_publisher.setFill(Color.GREY);
            reference_hbox.getChildren().addAll(t_publisher);
        }
        
        if(0 < source.length()) {
            Text t_source = new Text(source);
            t_source.setFont(Font.font(null, FontWeight.NORMAL, 12));
            t_source.setFill(Color.GREY);
            reference_hbox.getChildren().addAll(t_source);
        }
        
        // insert reference_hbox into group.content;
        group.getChildren().addAll(reference_hbox);
    }

}
