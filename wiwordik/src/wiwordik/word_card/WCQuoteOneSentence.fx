/* WCQuote.fx - A part of word card corresponds to quotations part
 * of a page (entry) in Wiktionary.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
 
package wiwordik.word_card;

import wikt.sql.quote.*;
import wikt.word.WQuote;
import wiwordik.WConstants;
import wikipedia.sql.Connect;

import javafx.scene.text.Text;
import javafx.scene.text.Font;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.lang.*;
import javafx.scene.text.FontWeight;
import javafx.geometry.HPos;


/** WCQuoteOneSentence contains one quote (phrase/sentence) that illustrates
 * one meaning of Wiktionary word and reference data (author, title, year...).
 *
 * @see wikt.word.WQuote
 */
public class WCQuoteOneSentence {

    /** Quotation sentence. */
    var sentence_text : String;

    /** Translation of the quotation sentence. */
    var translation_text : String;
    def translation_indent : Integer = 20;

    /** Related bibliography text: author, title, year, publisher. */
    //var reference_text : String;

    /** Author name. */
    var author_name : String;

    /** Source title. */
    var title : String;

    /** Years of the book. */
    var years_range : String;

    /** Publisher. */
    var publisher : String;

    /** Source. */
    var source : String;

    /** Optional node, only if there is a translation of the quote sentence. */
    var translation_text_node : Text = Text {
                content: bind translation_text
                translateX: translation_indent
                wrappingWidth: WConstants.wrapping_width - 35 - translation_indent
                //WCConstants.wrapping_width - 45
                font: Font {  size: 14  }
                fill: Color.CHOCOLATE
    }

    /** Optional node, only if a quote sentence has a reference data. */
    var reference_hbox : HBox = HBox {
                hpos: HPos.RIGHT
                spacing: 1
                content: [

                    Text {
                        content: bind years_range
                        font: Font.font("Times New Roman", FontWeight.BOLD, 12)
                        //font: Font {  size: 12, embolden: true}
                        fill: Color.GRAY
                    }

                    Text {
                        content: bind author_name
                        font: Font {  size: 12  }
                        fill: Color.GRAY
                        //wrappingWidth: WConstants.wrapping_width - 35
                    }

                    Text {
                        content: bind title
                        font: Font.font("Times New Roman Italic", 12)
                        fill: Color.GRAY
                    }

                    Text {
                        content: bind publisher
                        font: Font {  size: 12  }
                        fill: Color.GRAY
                    }

                    Text {
                        content: bind source
                        font: Font {  size: 12  }
                        fill: Color.GRAY
                    }

                ]
    }

    public var group: VBox = VBox {
        spacing: 5
        fillWidth: false
        //nodeHPos: HPos.RIGHT
        content: [
            Text {
                content: bind sentence_text
                wrappingWidth: WConstants.wrapping_width - 35
                //WCConstants.wrapping_width - 45
                font: Font {  size: 14  }
                fill: Color.GREEN
            }

            // Oprional nodes:
            // translation_text_node
            // reference_hbox

        ]
    };

    /** Creates a part of card (parts of wiki pages) with list of quotes
     * related to one meaning (sense).
     *
     * @return true if there are any quotes for this meaning.
    **/
    public function create (conn : Connect,
                            _quote : TQuote) : Void {

        // 1. Sentence text
        // WT:ELE: "In the quotation itself the word being illustrated should be in boldface."
        // todo: substitue '''marked word''' by some color, but
        // we are waiting "Rich Text" in JavaFX 2.0
        var s : String = WQuote.removeHighlightedMarksFromSentence( WConstants.native_lang,
                                                  _quote.getText());
                                        
        // additional treatment of the sentence text (e.g., &nbsp;, &#160; -> " ")
        sentence_text = WQuote.transformSentenceText(
                                    WConstants.IS_SQLITE, WConstants.native_lang, s);
        
        translation_text = _quote.getTranslation(conn);
        if(translation_text.length() > 0) {
            translation_text = WQuote.removeHighlightedMarksFromSentence( WConstants.native_lang,
               translation_text);
            insert translation_text_node into group.content;
        }

        // 2. Reference text
        def quot_ref : TQuotRef = _quote.getReference();
        if(null != quot_ref)
            insert reference_hbox into group.content;

        //reference_text = "{quot_ref.getYearsRange()}{quot_ref.getAuthorName()}";
        years_range = quot_ref.getYearsRange();
        author_name = quot_ref.getAuthorName();
        title       = quot_ref.getTitle();
        publisher   = quot_ref.getPublisherName();
        source      = quot_ref.getSourceName();



        if(WConstants.IS_SQLITE and title.contains("\\\""))   // \" -> " (SQLite feature)
            title = title.replace("\\\"", "\"");

        // commas and //:
        // 1. years_range, author_name
        if(years_range.length() > 0 and author_name.length() > 0)
            years_range = years_range.concat(", ");

        // 2. author_name, title
        if(author_name.length() > 0 and title.length() > 0)
            author_name = author_name.concat(", ");

        // 3. title // (publisher or source)
        if(title.length() > 0 and publisher.length() > 0)
            title = title.concat(" // ");

        // 4. (title or publisher), source
        if((title.length() > 0 or publisher.length() > 0) and source.length() > 0)
            publisher= publisher.concat(", ");


        if(WConstants.DEBUGUI) {
            source = "{source}; quot_ref.id={quot_ref.getID()}";

            sentence_text = "{sentence_text}; quote.id={_quote.getID()}";

            if(translation_text.length() > 0)
                translation_text = "{translation_text}; quot_translation.quote_id={_quote.getID()}";
        }
    }

}
