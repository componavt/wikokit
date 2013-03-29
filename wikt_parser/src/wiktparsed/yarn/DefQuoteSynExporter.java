/* DefQuoteSynExporter.java - exports definition, quotations and synonyms
 * from the database of the parsed Wiktionary in YARN format.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wiktparsed.yarn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.htmlparser.jericho.Source;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.Statistics;
import wikokit.base.wikt.api.WTMeaning;
import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TRelation;
import wikokit.base.wikt.sql.TRelationType;
import wikokit.base.wikt.sql.quote.TQuotRef;
import wikokit.base.wikt.sql.quote.TQuote;
import wikt.stat.printer.CommonPrinter;

/** YARN format exporter
 * 
 * @see YARN format https://github.com/xoposhiy/yarn/commit/65411750ee8f867c79cdd77bcbaf8024df2c9d63
 */
public class DefQuoteSynExporter {
    private static final boolean DEBUG = false;
    //private static final FileWriter file;
    
    /** map for the first part of YARN file: lexicon. Map from word to "nID" */
    private static final Map<String, Integer> m_noun_word_to_id = new HashMap<String, Integer>();
    
    /** Gets ID of word in the exported list.
     * @return -1 if this word is absent, i.e. it was not exported yet.
     **/
    public static int getWordEntryID (POS pos, String word, Map<String, Integer> _m_noun_word_to_id) {
        
        if(POS.noun == pos) {
            if(_m_noun_word_to_id.containsKey(word))
                return _m_noun_word_to_id.get(word);
        }
        
        return -1;
    }
    
    /** Gets Part-of-speech prefix (letter) n - noun, v - verb, a - adjective. */
    public static String getPOSOneLetterPrefix(POS pos) {
        
        String pos_prefix = "";
        if(POS.noun == pos)
            pos_prefix = "n";
        else if(POS.verb == pos)
            pos_prefix = "v";
        else if(POS.adjective == pos)
            pos_prefix = "a";
        
        return pos_prefix;
    }
    
    
    /** Gets XML chunk with word. Returns empty string if this word was added to the lexicon already.
     * @param pos_prefix n - noun, v - verb, a - adjective
     * @param word_id
     * @param source_url_word Wiktionary entry which is the information source
     * @param native_language_code main language of Wiktionary
     * @return 
     */
    public static String getWordEntryXMLWithoutDuplicates (POS pos, int word_id, String word, String source_url_word, 
                                LanguageType native_lang, Map<String, Integer> _m_noun_word_to_id)
    {
        if(getWordEntryID (pos, word, _m_noun_word_to_id) > 0)
            return "";  // this word was added already 
        
        String pos_prefix = getPOSOneLetterPrefix(pos);
        if(POS.noun == pos)
            _m_noun_word_to_id.put(word, word_id);
        
        StringBuilder sb = new StringBuilder();
        String code = native_lang.getCode();
        
        sb.append("    <wordEntry id=\"").append(pos_prefix).append(word_id).append("\"");  // id="n123"
        sb.append(" author=\"").append(code).append(".wiktionary\">\n"); // author="ru.wiktionary" >
        
        sb.append("      <word>").append(word).append("</word>\n");
        sb.append("      <url>http://").append(code).append(".wiktionary.org/wiki/").append(source_url_word).append("</url>\n");
        sb.append("    </wordEntry>\n");
        return sb.toString();
    }
    
    /** Converts HTML to text by Jericho (TextExtractor). 
     * @see http://jericho.htmlparser.net/docs/javadoc/net/htmlparser/jericho/TextExtractor.html
     */
    public static String HTMLToText (String text)
    {
            Source source = new Source(text);
            return source.getTextExtractor().toString();
    }
    
    public static String HTMLEscape (String text)
    {
            return text.replace("<", "&lt;").replace(">", "&gt;").
                    replace("&", "&amp;").replace("\"", "&quot;");
    }    
    
    
    /** Gets bibliographic information about quote sentence in the form:
     * Author, 'Title' // Publisher, Years, Source
     * 
     * Example: "В. В. Крестовский, 'Петербургские трущобы', 1867 г., НКРЯ"

     * @return null if there are no author name, title, years for this quotation.
     **/
    private static String getReference (TQuote _quote)
    {
        TQuotRef quot_ref = _quote.getReference();
        if(null == quot_ref)
            return null;
        
        /** Related bibliography text: author, title, year, publisher. */
        
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
        
        // 2a. data and logic
        //reference_text = "{quot_ref.getYearsRange()}{quot_ref.getAuthorName()}";
        years_range =            quot_ref.getYearsRange();
        author_name = HTMLEscape(quot_ref.getAuthorName());
        title       = HTMLEscape(quot_ref.getTitle());
        publisher   = HTMLEscape(quot_ref.getPublisherName());
        source      =            quot_ref.getSourceName();
        
        // 0. 'title'
        if(title.length() > 0)
            title = "'".concat(title).concat("'");
        
        // 1. author_name, title
        if(author_name.length() > 0 && (title.length() > 0 || years_range.length() > 0 || source.length() > 0))
            author_name = author_name.concat(", ");

        // 2. title // (publisher or source)
        if(title.length() > 0 && publisher.length() > 0)
            title = title.concat(" // ");

        // 3. (title or publisher), year
        if((title.length() > 0 || publisher.length() > 0) && (years_range.length() > 0 || source.length() > 0))
            publisher = publisher.concat(", ");
        
        // 4. year, source
        if(years_range.length() > 0 && source.length() > 0)
            years_range = years_range.concat(", ");
        
        // Author, 'Title' // Publisher, Years, Source
        
        StringBuilder sb = new StringBuilder();            
        if(0 < author_name.length())
            sb = sb.append(author_name);
            
        if(0 < title.length())
            sb = sb.append(title);
        
        if(0 < publisher.length())
            sb = sb.append(publisher);
        
        if(0 < years_range.length())
            sb = sb.append(years_range);
        
        if(0 < source.length())
            sb = sb.append(source);
        
        return sb.toString();
    }
    
    public static String getSynsetEntryBegin (POS pos, int synset_id, String word, 
                                Map<String, Integer> _m_noun_word_to_id, TQuote[] quotes)
    {
        String pos_prefix = getPOSOneLetterPrefix(pos);
        
        int word_id = getWordEntryID (pos, word, _m_noun_word_to_id);
        
        StringBuilder sb = new StringBuilder();
        sb.append("    <synsetEntry id=\"sn").append(synset_id).append("\">\n");  // id="sn1"
        
        if(DEBUG) // comment: <!-- word -->
            sb.append("      <word ref=\"").append(pos_prefix).append(word_id).append("\"> <!-- " + word + " -->\n");
        else 
            sb.append("      <word ref=\"").append(pos_prefix).append(word_id).append("\">\n");
        
        
        // todo sample: quotations
        // <sample source="В. В. Крестовский, 'Петербургские трущобы', 1867 г., НКРЯ">Мечут же карты, передѐргивают и всякие иные фокусы употребляют только главные и
        // самые искусные престидижитаторы, которые поэтому специально называются
        // «дергачами».</sample>
        // ...
        
        for(TQuote q : quotes ) {
            // sb.append("        <sample source=\"todo ref\">").append(q.getText()).append("</sample>\n"); // variant 1.
            
            String text = HTMLToText (q.getTextWithoutWikification());
            String ref = getReference (q);
            
            sb.append("        <sample source=\"").append(ref).append("\">").append(text).append("</sample>\n");
        
            //TQuotRef quot_ref = result.getReference();
            //TQuotAuthor a = quot_ref.getAuthor();
        }
        
        
        
        sb.append("      </word>\n");
        return sb.toString();
    }
    
    public static String getSynonymWordRef (POS pos, String word, Map<String, Integer> _m_noun_word_to_id)
    {
        StringBuilder sb = new StringBuilder();
        String pos_prefix = getPOSOneLetterPrefix(pos);
        
        int word_id = getWordEntryID (pos, word, _m_noun_word_to_id);
        assert( word_id > 0 ); // at previous step word was added to the lexicon
        
        if(DEBUG)
            sb.append("      <word ref=\"").append(pos_prefix).append(word_id).append("\"/> <!-- " + word + " -->\n");
        else 
            sb.append("      <word ref=\"").append(pos_prefix).append(word_id).append("\"/>\n");
        
        return sb.toString();
    }
    
    /** Gets word (synset) definition in XML format.
     * Example:
     * <definition url="http://ru.wiktionary.org/wiki/престидижитатор" source="ru.wiktionary">фокусник, отличающийся ловкостью рук; манипулятор</definition>
     */
    public static String getDefinition (String source_url_word, String definition, LanguageType native_lang)
    {
        StringBuilder sb = new StringBuilder();
        String code = native_lang.getCode();
        
        sb.append("      <definition url=\"http://").append(code).append(".wiktionary.org/wiki/").
                append(source_url_word).
                append("\" source=\"").append(code).append(".wiktionary\">");
        //sb.append( StringUtil.replaceSpecialChars(definition) );
        sb.append( HTMLEscape(definition.replace("{{-}}", " - ")) );
        sb.append("</definition>\n");
        return sb.toString();
    }
    
    
    /** Prints words, definitions, quotations and synonyms for each part_of_speech ("poses") in Wiktonary.
     * .<br><br>
     *
     * SELECT * FROM lang_pos;
     *
     * @param connect connection to the database of the parsed Wiktionary
     */
    public static void printYARN (Connect wikt_parsed_conn,
                    LanguageType native_lang, Set<POS> exported_pos) {
        // lang_pos -> meaning (definition)
        //             meaning -> relation (synonym)
        //             meaning -> quote

        Statement s = null;
        ResultSet rs= null;
        long      t_start;
        StringBuilder  sb_words = new StringBuilder();
        StringBuilder  sb_synsets = new StringBuilder();
        
        sb_words.append  ("  <words>\n");
        sb_synsets.append("  <synsets>\n");
    
        /** Current incremental ID of word entry (YARN file first part - lexicon) */
        int current_word_id = 0;
        
        int current_synset_id = 0;
        int n_total = Statistics.Count(wikt_parsed_conn, "lang_pos");
        t_start = System.currentTimeMillis();

        try {
            s = wikt_parsed_conn.conn.createStatement ();
            s.executeQuery ("SELECT id FROM lang_pos");
            rs = s.getResultSet ();
            int n_cur = 0;
            while (rs.next ())
            {
                n_cur ++;
                int id = rs.getInt("id");
                TLangPOS lang_pos_not_recursive = TLangPOS.getByID (wikt_parsed_conn, id);// fields are not filled recursively
                if(null == lang_pos_not_recursive)
                    continue;
                LanguageType lang = lang_pos_not_recursive.getLang().getLanguage();
                if(lang != LanguageType.ru) // this is our language :) 
                    continue;
                
                TPage tpage = lang_pos_not_recursive.getPage();
                String page_title = tpage.getPageTitle();

                int n_meaning = WTMeaning.countMeanings(wikt_parsed_conn, lang_pos_not_recursive);
                if(0 == n_meaning)
                    continue;       // only meanings with nonempty definitions

                POS p = lang_pos_not_recursive.getPOS().getPOS();
                if(!exported_pos.contains(p))   // this is our POS :) it should be exported
                    continue;

                current_word_id ++;
                String xml_word = getWordEntryXMLWithoutDuplicates (p, current_word_id, page_title, page_title, native_lang, m_noun_word_to_id);
                sb_words.append( xml_word );

                if(DEBUG)
                    System.out.print("\n" + page_title + ", meanings:" + n_meaning);
                    //System.out.print(", pos:" + p.toString());

                TMeaning[] mm = TMeaning.get(wikt_parsed_conn, lang_pos_not_recursive);
                for(TMeaning m : mm) {
                    
                    String meaning_text = m.getWikiTextString();
                    if(0 == meaning_text.length())
                        continue;
                    
                    if(DEBUG)
                        System.out.print("\n    def: " + meaning_text);
                    
                    TQuote[] quotes = TQuote.get (wikt_parsed_conn, m);

                    current_synset_id ++;
                    StringBuilder xml_synset = new StringBuilder( DefQuoteSynExporter.
                                                getSynsetEntryBegin (p, current_synset_id, page_title, m_noun_word_to_id, quotes));
                    
                    TRelation[] rels = TRelation.get(wikt_parsed_conn, m);
                    if(0 == rels.length)
                        continue;

                    for(TRelation tr : rels)
                    {
                        Relation r = tr.getRelationType();
                        if(Relation.synonymy != r)
                            continue;
                        
                        String word = tr.getWikiText().getText(); // synonym
                        if(0 == word.compareToIgnoreCase("&nbsp")) // "&nbsp" instead of synonym :(
                            continue;
                        
                        // if this synonym is absent in the dictionary, it should be added
                        if(-1 == getWordEntryID (p, word, m_noun_word_to_id)) {
                            current_word_id ++;
                            xml_word = getWordEntryXMLWithoutDuplicates (p, current_word_id, word, page_title, native_lang, m_noun_word_to_id);
                            sb_words.append( xml_word );
                        }
                        
                        xml_synset.append( getSynonymWordRef (p, word, m_noun_word_to_id) );
                        if(DEBUG)
                            System.out.print("\n        syn: " + word);
                    }
                    
                    sb_synsets.append( xml_synset );
                    
                    String def = getDefinition (page_title, meaning_text, native_lang);
                    sb_synsets.append( def );
                    
                    sb_synsets.append("    </synsetEntry>\n");
                }

                if(0 == n_cur % 1000) {   // % 100
                    if(DEBUG && n_cur > 1999)
                        break;

                    long    t_cur, t_remain;

                    t_cur  = System.currentTimeMillis() - t_start;
                    t_remain = (long)((n_total - n_cur) * t_cur/(60f*1000f*(float)(n_cur)));
                    t_cur = (long)(t_cur/(60f*1000f));

                    System.out.println(n_cur + ": " +
                        ", duration: "  + t_cur +   // t_cur/(60f*1000f) +
                        " min, remain: " + t_remain +
                        " min");
                }
                
            } // eo while
        } catch(SQLException ex) {
            System.err.println("SQLException (DefQuoteSynExporter.printYARN()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        
        System.out.println("\n");
        sb_words.append("  </words>\n");
        System.out.println(sb_words.toString());
        
        sb_synsets.append("  </synsets>\n");
        System.out.println(sb_synsets.toString());

        // System.out.println("<!-- Number of exported meanings with nonempty definitions: " + current_word_id + " -->");
        // System.out.println("<!-- Total number of records in the table lang_pos: " + n_total + " -->");
    }


    public static void main(String[] args) {

        // set of parts of speech to be exported
        Set<POS> exported_pos = new HashSet<POS>();
        exported_pos.add(POS.noun);
        
        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();
        LanguageType native_lang;
        
        // Russian
        native_lang = LanguageType.ru;
        wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);
        
        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        TRelationType.createFastMaps(wikt_parsed_conn);
        
        System.out.println("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
        CommonPrinter.printHeaderXML (wikt_parsed_conn.getDBName());
        System.out.println("<yarn>");
        
        DefQuoteSynExporter.printYARN (wikt_parsed_conn, native_lang, exported_pos);
        
        System.out.println("</yarn>");
        
        wikt_parsed_conn.Close();
    }
    
}
