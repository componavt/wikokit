/* RelationTableAll.java - relations' statistics in the database of the parsed Wiktionary.
 *
 * Copyright (c) 2005-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikt.stat;

import wikt.constant.Relation;
import wikipedia.language.LanguageType;
import wikt.api.WTRelation;

import wikipedia.sql.*;
import wikt.sql.*;

import java.sql.*;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;
import wikt.constant.POS;


/** Relations' statistics in the database of the parsed Wiktionary.
 */
public class RelationTableAll {
    private static final boolean DEBUG = false;

    /** Let's constrain the maximum number of semantic relation for one word
     * in one language */
    private static final int max_relation = 200;
    private static final int[] rel_histogram = new int[max_relation];

    /** Let's constrain the maximum number of types of semantic relation
     * for one word in one language */
    private static final int max_type_relation = 10;
    private static final int[] rel_type_histogram = new int[max_type_relation];

    /** List of the words with the maximum number of semantic relations,
     * or the maximum number of types of semantic relations. */
    private static final List<TLangPOS> words_rich_in_relations = new ArrayList<TLangPOS>();

    /** Number of semantic relations per type,
     * i.e. number of synonyms for words, number of antonyms, etc. **/
    private static final Map<Relation,Integer>[] m_relation_type_number = new HashMap[max_type_relation];
    // <Relation,Integer> ()

    /** Number of words (pairs: language & part of speech)
     * with semantic relations */
    private static int lang_pos_with_relations;

    /** Number of (Language & POS level) entries per language. **/
    private static final Map<LanguageType,Integer> m_lang_entries_number = new HashMap();



    /** Counts number of semantic relations for each language
     * by selecting all relations from the database of the parsed Wiktionary.<br><br>
     * SELECT  FROM relation;
     * 
     * @param connect connection to the database of the parsed Wiktionary
     * @return map of maps with number of synonyms (etc.) in English (etc.)
     */
    public static Map<LanguageType, Map<Relation,Integer>> countRelationsPerLanguage (
                                                    Connect wikt_parsed_conn) {
        // lang -> relations -> count

        Statement   s = null;
        ResultSet   rs= null;
        long    t_start;
        float   t_work;

        int n_unknown_lang_pos = 0; // relations which belong to words with unknown language and POS

        int n_total = Statistics.Count(wikt_parsed_conn, "relation");
        //System.out.println("Total relations: " + n_total);
        t_start = System.currentTimeMillis();

        Map<LanguageType, Map<Relation,Integer>> m_lang_rel_n = new HashMap<LanguageType, Map<Relation,Integer>>();
        
        try {
            s = wikt_parsed_conn.conn.createStatement ();
            StringBuilder str_sql = new StringBuilder();
            str_sql.append("SELECT id,meaning_id,wiki_text_id,relation_type_id FROM relation");
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            int n_cur = 0;
            while (rs.next ())
            {
                n_cur ++;
                //int          id =                                     rs.getInt("id");
                TMeaning      m = TMeaning.getByID(wikt_parsed_conn,    rs.getInt("meaning_id"));
                //TWikiText    wt = TWikiText.getByID(wikt_parsed_conn, rs.getInt("wiki_text_id"));
                TRelationType tr = TRelationType.getRelationFast(       rs.getInt("relation_type_id"));

                if(null != m && null != tr) {
                    TLangPOS lang_pos = m.getLangPOS(wikt_parsed_conn);
                    Relation r        = tr.getRelation();
                    assert(null != r);

                    if(null != lang_pos) {
                        TLang tlang = lang_pos.getLang();                        //TPOS tpos   = lang_pos.getPOS();  // future statistics
                        
                        if(null != tlang ) {
                            LanguageType lang = lang_pos.getLang().getLanguage();

                            Map<Relation,Integer>     rel_n = null;
                            int                           n;
                            if(m_lang_rel_n.containsKey(lang) ) {
                                      rel_n =  m_lang_rel_n.get(lang);          // assert(null != rel_n);
                                   if(rel_n.containsKey(        r)) {
                                          n =         rel_n.get(r);
                                                      rel_n.put(r, n + 1);
                                   } else             rel_n.put(r, 1    );
                            } else {
                                                      rel_n = new HashMap<Relation,Integer>();
                                                      rel_n.put(r, 1);
                               m_lang_rel_n.put(lang, rel_n);
                            }
                            
                            if(0 == n_cur % 1000) {   // % 100
                                if(DEBUG && n_cur > 333)
                                    break;
                                long    t_cur, t_remain;

                                t_cur  = System.currentTimeMillis() - t_start;
                                t_remain = (long)((n_total - n_cur) * t_cur/(60f*1000f*(float)(n_cur)));
                                           // where time for 1 page = t_cur / n_cur
                                           // in min, since /(60*1000)
                                t_cur = (long)(t_cur/(60f*1000f));
                                //t_cur = t_cur/(60f*1000f));

                                TPage tpage = lang_pos.getPage();
                                if(null != tpage) {
                                    System.out.println(n_cur + ": " + tpage.getPageTitle() +
                                        ", duration: "  + t_cur +   // t_cur/(60f*1000f) +
                                        " min, remain: " + t_remain +
                                        " min");
                                }
                            }
                        }
                    }

                } else
                    n_unknown_lang_pos ++;
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (RelationTableAll.countRelationsPerLanguage()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        long  t_end;
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        System.out.println("\nTime sec:" + t_work +
            "\nTotal relations: " + n_total +
            "\n\nUnknown<ref>'''Unknown''' - relations which belong to words with unknown language and POS</ref>: " +
            n_unknown_lang_pos);
                
        return m_lang_rel_n;
    }

     /** Initialize (set to zero) number of entries for each language. */
    public static void initLangEntries () {

        Set<LanguageType> languages = TLang.getAllLanguages().keySet();
        for(LanguageType lang : languages)
            m_lang_entries_number.put(lang, 0);
    }


    /** Increments number of entries for the given language 'lt'.
     */
    public static void incLangEntry (LanguageType lt) {
        int old = m_lang_entries_number.get(lt);
        m_lang_entries_number.put(lt, old + 1);
    }

    /** Counts number of semantic relations for each number of relations per 
     * word. Fills 
     * (1) 'rel_histogram' - number of words per number of semantic relations;
     * (2) 'rel_types_histogram' - number of words per number of types of
     * semantic relations;
     * (3) 'words_rich_in_relations' - list of the words with the maximum number
     * of semantic relations, or the maximum number of types of semantic relations;
     * (4) 'm_relation_type_number'
     * (5) 'lang_pos_with_relations' number of lang_pos with semantic relations
     * .<br><br>
     * 
     * SELECT * FROM lang_pos;
     *
     * @param connect connection to the database of the parsed Wiktionary
     *
     * @param threshold_relations_native number (or more) of relations
     *                          the word (in native language) have
     *                          to have in order to be included into the
     *                          list RelationTableAll.words_rich_in_relations
     *
     * @param threshold_relations_foreign the same constraint for foreign words,
     *                          it used since native words usually push out 
     *                          foreign words from the list, 
     * threshold_relations_foreign << threshold_relations_native, so
     *
     *
     * @param threshold_types_relations number (or more) of types
     *                          of the semantic relations the word have
     *                          to have in order to be included into the
     *                          list RelationTableAll.words_rich_in_relations
     *
     * @return histogram with number of semantic relations, i.e.
     * [0] = number of words (one language, one part of speech) without any semantic relations,
     * [1] = number of words with one relation, etc.
     */
    public static void countRelationsHistogram (Connect wikt_parsed_conn,
                    LanguageType native_lang,
                    int threshold_relations_foreign, int threshold_relations_native,
                    int threshold_type_relations) {
        // lang_pos -> meaning -> relations -> count

        Statement   s = null;
        ResultSet   rs= null;
        long    t_start;

        int n_unknown_pos__in_rich_words = 0; // number of words (with many relations) with unknown POS
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
                incLangEntry(lang);
                TPage tpage = lang_pos_not_recursive.getPage();
                String page_title = tpage.getPageTitle();

                int n_relation = WTRelation.getNumberByPageLang(wikt_parsed_conn, lang_pos_not_recursive);
                int n_type_relation = 0;

                if(n_relation > 1) {
                    // there is a reason to calculate: number of types of semantic relations
                    
                    TLangPOS lang_pos = null;
                    {   // calculate lang_pos with filled fields in order
                        // to get number of meanings by getRecursive, etc.
                        
                        TLangPOS[] lang_pos_array = TLangPOS.getRecursive(wikt_parsed_conn, tpage);
                        for(TLangPOS tlp : lang_pos_array) {
                            if(tlp.getID() == id) {
                                lang_pos = tlp;
                                break;
                            }
                        }
                    }
                    assert(lang_pos != null);

                    if(POS.unknown == lang_pos.getPOS().getPOS())
                        n_unknown_pos__in_rich_words ++;

                    boolean b_added = false;
                    if((native_lang == lang && n_relation >= threshold_relations_native) ||
                       (native_lang != lang && n_relation >= threshold_relations_foreign))
                    {
                        b_added = true;
                        words_rich_in_relations.add(lang_pos);// List of the words with the maximum number of semantic relations.          
                    }

                    {
                        n_type_relation = lang_pos.countRelationTypes();
                        if(n_type_relation >= threshold_type_relations && !b_added)
                            words_rich_in_relations.add(lang_pos);

                        if(n_type_relation < max_type_relation) {
                            rel_type_histogram [n_type_relation] ++;
                            
                            if(null == m_relation_type_number [n_type_relation])
                                 m_relation_type_number [n_type_relation] = new HashMap<Relation,Integer> ();
                            lang_pos.addNumberOfRelationPerType(m_relation_type_number [n_type_relation]);
                            
                        } else
                            System.out.println("Error (RelationTableAll.countRelationsHistogram()): n_types_relation=" +
                                    n_type_relation + " > max_types_relation for the word=" + page_title);
                    }
                }
                if(1 == n_relation)
                    n_type_relation = 1;
                
                if(n_relation < max_relation)
                    rel_histogram [n_relation] ++;
                else
                    System.out.println("Error (RelationTableAll.countRelationsHistogram()): n_relation=" +
                            n_relation + " > max_relation for the word=" + page_title);

                if(n_relation > 0)
                    lang_pos_with_relations ++;

                if(0 == n_cur % 1000) {   // % 100
                    if(DEBUG && n_cur > 333)
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
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (RelationTableAll.countRelationsHistogram()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        System.out.println("\nNumber of words (with many relations) with unknown POS: " + n_unknown_pos__in_rich_words);
    }



    public static void main(String[] args) {

        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();
        int threshold_relations_foreign, threshold_relations_native,
            threshold_type_relations;

        // Russian
        LanguageType native_lang = LanguageType.ru;
        threshold_relations_foreign = 10;   // threshold_relations = 14;
        threshold_relations_native  = 40;
        threshold_type_relations = 5;
        if(DEBUG) threshold_relations_native = 3;
        wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);
/*
        // English
        LanguageType native_lang = LanguageType.en;
        threshold_relations_foreign = 10;
        threshold_relations_native  = 40; // for English words, more strict rules, too much rich words
        threshold_type_relations = 3;
        wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, LanguageType.en);
*/
        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        TRelationType.createFastMaps(wikt_parsed_conn);
        RelationTableAll.initLangEntries();

        String db_name = wikt_parsed_conn.getDBName();
        System.out.println("\n== Statistics of semantic relations in the Wiktionary parsed database ==");
        WikiPrinterStat.printHeader (db_name);

        Map<LanguageType, Map<Relation,Integer>> m = RelationTableAll.countRelationsPerLanguage(wikt_parsed_conn);

        // fills rel_histogram, rel_types_histogram
        RelationTableAll.countRelationsHistogram(wikt_parsed_conn, native_lang,
                                threshold_relations_foreign, threshold_relations_native,
                                threshold_type_relations);

        System.out.println("\nWords (pairs: language & part of speech) with semantic relations: " + lang_pos_with_relations);
        System.out.println("\nLanguages with semantic relations: " + m.size());
        System.out.println();

        //WTStatisticsGoogleWiki.printRelationsPerLanguage(m);
        WikiPrinterStat.printRelationsPerLanguage(native_lang, m, m_lang_entries_number);
        WikiPrinterStat.printRelationsHistogram(rel_histogram);

        WikiPrinterStat.printRelationsTypeHistogram (rel_type_histogram, m_relation_type_number);
        
        WikiPrinterStat.printWordsWithManyRelations(native_lang, wikt_parsed_conn,
                                words_rich_in_relations,
                                threshold_relations_foreign, threshold_relations_native,
                                threshold_type_relations);

        WikiPrinterStat.printFooter();

        wikt_parsed_conn.Close();
    }

}
