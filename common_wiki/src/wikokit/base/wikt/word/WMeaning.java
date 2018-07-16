/* WMeaning.java - corresponds to a Meaning (definition + quotations)
 * level of Wiktionary word.
 * 
 * Copyright (c) 2008-2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.word;

import wikokit.base.wikt.util.WikiWord;
import wikokit.base.wikt.util.WikiText;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.constant.Image;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.multi.ru.WMeaningRu;
import wikokit.base.wikt.multi.en.WMeaningEn;

/** Meaning consists of <PRE>
 * # Definition (preceded by "#", which causes automatic numbering).
 * #* and Quotations.      </PRE>
 */
public class WMeaning {

    // StringBuffer definition;
    // + wiki word, + number of wiki word or number of first char of wikiword in definition
    
    /** Image (picture) related to some definition (meaning).
     * One meaning has one or zero related images.
    */
    private Image   image;
    
    /** Contexual information for definitions, e.g. "idiom" from text "# {{idiom}} [[bullet]]s".
     * @see full list of labels in LabelEn, LabelRu, etc.
     */
    private Label[] labels;
    
    private WikiText definition;

    /** Meaning text with wiki markup, but without context labels. */
    private String  wikified_text;

    /** True, if the definition defines inflection of the word with the help of
     * (1) template {{form of|}}, or {{plural of|}}, 
     * (2) strictly defined phrase (e.g. "Plural form of")
     */
    private boolean form_of_inflection;

    /** Word definition, e.g. "bullets" from text "# {{idiom}} [[bullet]]s" */
    //private StringBuffer    definition;

    /** Wiki internal links, e.g. "bullet" from text "# {{idiom}} [[bullet]]s" */
    //private WikiWord[] wiki_words;
    
    /** Example sentences and quotations. */
    private WQuote[] quote;

    private final static WQuote[]   NULL_WQUOTE_ARRAY   = new WQuote[0];
    private final static WMeaning[] NULL_WMEANING_ARRAY = new WMeaning[0];
    private final static WMeaning   NULL_WMEANING       = new WMeaning();
    private final static WikiWord[] NULL_WIKIWORD       = new WikiWord[0];

    public WMeaning() {
        image  = null;
        labels = null;
        definition = null;
        quote = null;
        form_of_inflection = false;
    }

        /** Frees memory recursively. */
    public void free ()
    {
        image  = null;
        labels = null;

        if(null != quote) {
            //for(WQuote q : quote)
                //q.free();
            quote = null;
        }
    }

    /** Constructor.
     *
     * @param page_title
     * @param _labels
     * @param _images
     * @param _definition wikified text of the definition
     * @param _quote could be null
     * @param _template_not_def true if there is template (e.g. {{form of|}} or
     * {{plural of|}}) instead of definition text (in enwikt)
     */
    public WMeaning(String page_title, Label[] _labels, //Image _image,
                    String _definition, WQuote[] _quote, boolean _template_not_def) {
        //image  = _image;
        labels = _labels;
        
        wikified_text = _definition;
        definition = WikiText.createOnePhrase(page_title, _definition);
        
        form_of_inflection = _template_not_def;

        if(null == _quote)
            quote = NULL_WQUOTE_ARRAY;
        else
            quote = _quote;

    }
    
    /** True if the definition defines inflection of the word with the help of
     * (1) the template (e.g. {{form of|}} or {{plural of|}}), or
     * (2) strictly defined phrase (e.g. "Plural form of")
     * instead of the usual definiton text (in enwikt). */
    public boolean isFormOfInflection() {
        return form_of_inflection;
    }

    /** Gets image.
     * @return null if there is no image for this meaning
     */
    public Image getImage() {
        return image;
    }
    
    public void setImage(Image i) {
        image = i;
    }
    
    /** Gets array of context labels in the definition. */
    public Label[] getLabels() {
        return labels;
    }

    /** Gets definition line of text. */
    public String getDefinition() {
        if(null == definition)
            return "";
        return definition.getVisibleText();
    }
    
    /** Gets meaning text (definition) with wiki markup, but without context labels. */
    public String getWikifiedText() {
        return wikified_text;
    }
    
    /** Gets array of internal links in the definition (wiki words, i.e. words with hyperlinks). */
    public WikiWord[] getWikiWords() {
        if(null == definition)
            return NULL_WIKIWORD;
        return definition.getWikiWords();
    }

    /** Gets wiki_text. */
    public WikiText getWikiText() {
        return definition;
    }
    
    /** Gets array of quotes (sentences) from the definition. */
    public WQuote[] getQuotes() {
        return quote;
    }

    /** Parses text (related to the POS), creates and fills array of meanings (WMeaning).
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param pt            POSText defines POS stored in pt.text
     * @return
     */
    public static WMeaning[] parse (
                    LanguageType wikt_lang,
                    String page_title,
                    LanguageType lang_section,
                    POSText pt)
    {
        // === Level III. Meaning ===
        WMeaning[] wm = NULL_WMEANING_ARRAY;

        LanguageType l = wikt_lang;

        if(l == LanguageType.ru) {

            // get context labels, definitions, and quotations... todo
            /*   if(0==wm.length) {
                    return NULL_WMEANING_ARRAY;
            }*/
          wm = WMeaningRu.parse(page_title, lang_section, pt);

        } else if(l == LanguageType.en) {
            wm = WMeaningEn.parse(page_title, lang_section, pt);

        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;

            // todo
            // ...

        } else {
            throw new NullPointerException("Null LanguageType");
        }
        
        return wm;
    }

    /** Parses one definition (one line in Russian, several lines in English 
     * Wiktionary), i.e. extracts {{label.}}, definition,
     * {{example|Quotation sentence.}}, creates and fills a meaning (WMeaning).
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param lang_section  language of this section of an article
     * @param def_text      text of one definition
     * @return WMeaning or null if the line is not started from "#"
     */
    public static WMeaning parseOneDefinition(LanguageType wikt_lang,
                    String page_title,
                    LanguageType lang_section,
                    String def_text)
    {
        WMeaning wm = NULL_WMEANING;

        LanguageType l = wikt_lang;

        if(l == LanguageType.ru) {
            wm = WMeaningRu.parseOneDefinition(page_title, lang_section, def_text);

        } else if(l == LanguageType.en) {
            wm = WMeaningEn.parseOneDefinition(page_title, lang_section, def_text);

          //  return WordEn;
        //} //else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;

            // todo
            // ...

        } else {
            throw new NullPointerException("Null LanguageType");
        }

        return wm;
    }

}
