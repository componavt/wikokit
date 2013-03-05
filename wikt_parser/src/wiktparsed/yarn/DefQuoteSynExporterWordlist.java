/* DefQuoteSynExporterWordlist.java - exports definition, quotations and synonyms
 * from the database of the parsed Wiktionary in YARN format.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wiktparsed.yarn;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.util.StringUtil;
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
import wikt.stat.printer.CommonPrinter;

/** YARN format exporter
 *
 * 
 * @see YARN format https://github.com/xoposhiy/yarn/commit/65411750ee8f867c79cdd77bcbaf8024df2c9d63
 */
public class DefQuoteSynExporterWordlist {
    private static final boolean DEBUG = false;
    //private static final FileWriter file;
    
    /** map for the first part of YARN file: lexicon. Map from word to "nID" */
    private static final Map<String, Integer> m_noun_word_to_id = new HashMap<String, Integer>();
    
    public static int getWordEntryID (POS pos, String word) {
        
        if(POS.noun == pos) {
            if(m_noun_word_to_id.containsKey(word))
                return m_noun_word_to_id.get(word);
        }
        
        return -1;
    }
    
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
    public static String getWordEntryXMLWithoutDuplicates (POS pos, int word_id, String word, String source_url_word, LanguageType native_lang)
    {
        if(getWordEntryID (pos, word) > 0) // this word was added already 
            return "";
        
        String pos_prefix = getPOSOneLetterPrefix(pos);
        if(POS.noun == pos)
            m_noun_word_to_id.put(word, word_id);
        
        StringBuilder sb = new StringBuilder();
        String code = native_lang.getCode();
        
        sb.append("    <wordEntry id=\"").append(pos_prefix).append(word_id).append("\"");  // id="n123"
        sb.append(" author=\"").append(code).append(".wiktionary\">\n"); // author="ru.wiktionary" >
        
        sb.append("      <word>").append(word).append("</word>\n");
        sb.append("      <url>http://").append(code).append(".wiktionary.org/wiki/").append(word).append("</url>\n");
        sb.append("    </wordEntry>\n");
        return sb.toString();
    }
    
    public static String getSynsetEntryBegin (POS pos, int synset_id, String word)
    {
        String pos_prefix = getPOSOneLetterPrefix(pos);
        
        int word_id = getWordEntryID (pos, word);
        
        StringBuilder sb = new StringBuilder();
        sb.append("    <synsetEntry id=\"sn").append(synset_id).append("\">\n");  // id="sn1"
        
        if(DEBUG)
            sb.append("      <word ref=\"").append(pos_prefix).append(word_id).append("\"> <!-- " + word + " -->\n");
        else 
            sb.append("      <word ref=\"").append(pos_prefix).append(word_id).append("\">\n");
        
        // todo sample: quotations
        // <sample source="В. В. Крестовский, 'Петербургские трущобы', 1867 г.,
        // НКРЯ">Мечут же карты, передѐргивают и всякие иные фокусы употребляют только главные и
        // самые искусные престидижитаторы, которые поэтому специально называются
        // «дергачами».</sample>
        // ...
        
        sb.append("      </word>\n");
        return sb.toString();
    }
    
    public static String getSynonymWordRef (POS pos, String word)
    {
        StringBuilder sb = new StringBuilder();
        String pos_prefix = getPOSOneLetterPrefix(pos);
        
        int word_id = getWordEntryID (pos, word);
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
        sb.append( StringUtil.replaceSpecialChars(definition) );
        sb.append("</definition>\n");
        return sb.toString();
    }
    
    public static void nextLangPOSID (int lang_pos_id,
                    Connect wikt_parsed_conn,
                    LanguageType native_lang, Set<POS> exported_pos) {
        
    }
    
    /** Prints words, definitions, quotations and synonyms for each part_of_speech ("poses") in Wiktonary.
     * .<br><br>
     *
     * SELECT * FROM lang_pos;
     *
     * @param connect connection to the database of the parsed Wiktionary
     */
    public static void printYARNwithWordlist (Connect wikt_parsed_conn,
                            LanguageType native_lang, 
                            Set<POS> exported_pos, List<String> export_words) {
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
        int n_total = export_words.size();
        t_start = System.currentTimeMillis();
        
        int n_cur = 0;
outerloop:        
        for(String page_title : export_words) {
            n_cur ++;

            TPage tpage = TPage.get(wikt_parsed_conn, page_title);
            if(null == tpage) {
                System.out.print("* [[" + page_title + "]]\n"); // see http://ru.wiktionary.org/w/index.php?title=Участник:AKA_MBG/Todo
                continue;
            }
            TLangPOS[] tlang_pos_array = TLangPOS.get (wikt_parsed_conn, tpage);

            for(TLangPOS lang_pos_not_recursive : tlang_pos_array) {

                //TLangPOS lang_pos_not_recursive = TLangPOS.getByID (wikt_parsed_conn, id);// fields are not filled recursively
                if(null == lang_pos_not_recursive)
                    continue;
                LanguageType lang = lang_pos_not_recursive.getLang().getLanguage();
                if(lang != LanguageType.ru) // this is our language :) 
                    continue;

                // TPage tpage = lang_pos_not_recursive.getPage();
                // String page_title = tpage.getPageTitle();

                int n_meaning = WTMeaning.countMeanings(wikt_parsed_conn, lang_pos_not_recursive);
                if(0 == n_meaning)
                    continue;       // only meanings with nonempty definitions

                POS p = lang_pos_not_recursive.getPOS().getPOS();
                if(!exported_pos.contains(p))   // this is our POS :) it should be exported
                    continue;

                current_word_id ++;
                String xml_word = getWordEntryXMLWithoutDuplicates (p, current_word_id, page_title, page_title, native_lang);
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

                    current_synset_id ++;
                    // String getSynsetEntryBegin (POS pos, int synset_id, int word_id, String source_url_word, LanguageType native_lang)
                    StringBuilder xml_synset = new StringBuilder(getSynsetEntryBegin (p, current_synset_id, page_title));

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

                        if(-1 == getWordEntryID (p, word)) { // this synonym is absent in the dictionary, it should be added
                            current_word_id ++;
                            xml_word = getWordEntryXMLWithoutDuplicates (p, current_word_id, word, page_title, native_lang);
                            sb_words.append( xml_word );
                        }

                        xml_synset.append( getSynonymWordRef (p, word) );
                        if(DEBUG)
                            System.out.print("\n        syn: " + word);
                    }

                    sb_synsets.append( xml_synset );

                    String def = getDefinition (page_title, meaning_text, native_lang);
                    sb_synsets.append( def );

                    sb_synsets.append("    </synsetEntry>\n");
                }

                if(0 == n_cur % 1000) {   // % 100
                    if(DEBUG && n_cur > 333)
                        break outerloop;

                    long    t_cur, t_remain;

                    t_cur  = System.currentTimeMillis() - t_start;
                    t_remain = (long)((n_total - n_cur) * t_cur/(60f*1000f*(float)(n_cur)));
                    t_cur = (long)(t_cur/(60f*1000f));

                    System.out.println(n_cur + ": " +
                        ", duration: "  + t_cur +   // t_cur/(60f*1000f) +
                        " min, remain: " + t_remain +
                        " min");
                }

            } // eo for(TLangPOS
        } // eo for(export_words
        
        System.out.println("\n");
        sb_words.append("  </words>\n");
        System.out.println(sb_words.toString());
        
        sb_synsets.append("  </synsets>\n");
        System.out.println(sb_synsets.toString());

        // System.out.println("<!-- Number of exported meanings with nonempty definitions: " + current_word_id + " -->");
        // System.out.println("<!-- Total number of records in the table lang_pos: " + n_total + " -->");
    }

    public static List<String> readLines (String file_name) {
        
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get( file_name ), Charset.forName("UTF-8"));
            //for(String line:lines){
            //    System.out.println(line);
            //}
        } catch (IOException ex) {
            Logger.getLogger(DefQuoteSynExporterWordlist.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lines;
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
        
        List<String> export_words = readLines ("c:/w/bin/yarn/s_list_utf8.txt");
        
        printYARNwithWordlist (wikt_parsed_conn, native_lang, exported_pos, export_words);
        
        System.out.println("</yarn>");
        
        wikt_parsed_conn.Close();
    }    
}
