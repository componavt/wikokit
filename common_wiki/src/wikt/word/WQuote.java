/* WQuote.java - corresponds to the phrase/sentence that illustrates a meaning 
 *               of Wiktionary word.
 * 
 * Copyright (c) 2008-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.word;

import wikipedia.language.LanguageType;
import wikt.multi.ru.WQuoteRu;
import wikt.multi.en.WQuoteEn;

/** Phrase or sentence that illustrates a meaning of Wiktionary word.
 */
public class WQuote {
    
    /** Text of an example sentence. */
    private String  text;

    /** Translation of the example sentence into native language. */
    private String  translation;

    /** Transcription of the example sentence. */
    private String  transcription;

    /** Author's name of the quotation. */
    private String  author;

    /** Author's name in Wikipedia (format: [[w:name|]]). */
    private String  author_wikilink;

    /** Title of the work. */
    private String  title;

    /** Link to a book in Wikipedia (format: [[w:title|]]). */
    private String  title_wikilink;

    /** Quote book publisher. */
    private String  publisher;

    /** Quote source,
     * enwikt: quotation template,
     * ruwikt: template points to corpus used as a source for the quotation ("источник").
     **/
    private String  source;

    /** Start date of a writing book with the quote. */
    private int year_from;

    /** Finish date of a writing book with the quote. */
    private int year_to;

    

    /** Start and end positions of the highlighted word(s) in the quote, by the template {{выдел|}} */
    //private int[][2] start_end_pos;

    /** Gets definition line of text. */
    public String getText() {
        return text;
    }

    /** Gets translation of the example sentence into native language. */
    public String getTranslation() {
        return translation;
    }

    /** Gets transcription of the example sentence. */
    public String getTranscription() {
        return transcription;
    }

    /** Gets author's name. */
    public String getAuthor() {
        return author;
    }

    /** Gets author's name in Wikipedia (format: [[w:name|]]). */
    public String getAuthorWikilink() {
        return author_wikilink;
    }

    /** Gets title of the work. */
    public String getTitle() {
        return title;
    }

    /** Gets link to a book in Wikipedia (format: [[w:title|]]),. */
    public String getTitleWikilink() {
        return title_wikilink;
    }

    /** Gets quote book publisher. */
    public String getPublisher() {
        return publisher;
    }

    /** Gets quote source. */
    public String getSource() {
        return source;
    }

    /** Gets start date of a writing book with the quote. */
    public int getYearFrom() {
        return year_from;
    }

    /** Gets finish date of a writing book with the quote. */
    public int getYearTo() {
        return year_to;
    }

    /** Constructor.
     *
     * @param _text text of an example sentence
     * @param _translation translation of the example sentence into native language
     * @param _transcription transcription of the example sentence
     *
     * @param _author author's name,
     * @param _author_wikilink link to author's name in Wikipedia (format: [[w:name|]]),
     * @param _title title of the work
     * @param _title_wikilink link to a book in Wikipedia (format: [[w:title|]]),
     *                        it could be empty ("")
     * @param _publisher quote book publisher
     * @param _source quote source
     * @param _year_from start date of a writing book with the quote
     * @param _year_to finish date of a writing book with the quote
     * @return inserted record, or null if insertion failed
     */
    public WQuote (String _text,String _translation,String _transcription,
                   String _author,String _author_wikilink,
                   String _title, String _title_wikilink,
                   String _publisher, String _source,
                   int _year_from,int _year_to)
    {
        text = _text;
        translation = _translation;
        transcription = _transcription;

        author = _author;
        author_wikilink = _author_wikilink;

        title = _title;
        title_wikilink = _title_wikilink;
    
        publisher = _publisher;
        source = _source;

        year_from = _year_from;
        year_to = _year_to;
    }

    /** Removes highlighted marks from a sentence. 
     * English Wiktionary: Sentence with '''words'''. -> Sentence with words.
     * Russian Wiktionary:
     * 1) Sentence with '''words'''. -> Sentence with words.
     * 2) Sentence with {{выдел|words}}. -> Sentence with words.
     */
    public static String removeHighlightedMarksFromSentence(
                                            LanguageType wikt_lang,
                                            String text)
    {
        String result;

        LanguageType l = wikt_lang;
        if(l  == LanguageType.ru) {
            result = WQuoteRu.removeHighlightedMarksFromSentence(text);
        } else if(l == LanguageType.en) {

            result = WQuoteEn.removeHighlightedMarksFromSentence(text);

        } else {
            throw new NullPointerException("Null LanguageType");
        }
        
        return result;
    }

    /** Additional treatment of the sentence text:
     * English Wiktionary: ?..
     * ...
     * Russian Wiktionary:
     * 1) &nbsp;, &#160; -> " "
     * 2) {{-}} -> " - "
     */
    public static String transformSentenceText(
                                boolean is_sqlite,
                                LanguageType wikt_lang,
                                String text)
    {
        String result;

        LanguageType l = wikt_lang;
        if(l  == LanguageType.ru) {
            result = WQuoteRu.transformSentenceText(is_sqlite, text);
        } /*else if(l == LanguageType.en) {

            // todo
            //result = WQuoteEn.transformSentenceText(text);

        }*/ else {
            throw new NullPointerException("Null LanguageType");
        }

        return result;
    }


    

}
