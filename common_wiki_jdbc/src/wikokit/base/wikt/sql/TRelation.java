/* TRelation.java - SQL operations with the table 'relation' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql;

import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.word.WRelation;
import wikokit.base.wikt.util.WikiText;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.Statistics;
import wikokit.base.wikipedia.sql.PageTableBase;
import wikokit.base.wikipedia.language.Encodings;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/** An operations with the table 'relation' in MySQL wiktionary_parsed database.
 *
 * @see wikt.word.WRelation
 */
public class TRelation {
    private static final boolean DEBUG = true;
    
    /** Unique identifier in the table 'relation'. */
    private int id;

    /** One sense of a word. */
    private TMeaning meaning;               // int meaning_id;

    /** Text (wikified sometimes). */
    private TWikiText wiki_text;            // int wiki_text_id

    /** Semantic relation. */
    private TRelationType relation_type;    // int relation_type_id

    /** Summary of the definition for which synonyms (antonyms, etc.) are being given,
     * e.g. "flrink with cumplus" or "furp" in text
     * <PRE>
     * * (''flrink with cumplus''): [[flrink]], [[pigglehick]]
     * * (''furp''): [[furp]], [[whoodleplunk]]
     * </PRE>
     *
     * Disadvantage: the summary "flrink with cumplus" is repeated twice 
     *              in table for "flrink" and "pigglehick".
     *
     * Comment: is used in English Wiktionary, see http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained#Synonyms
     * It is not used in Russian Wiktionary (NULL in database).
     */
    private String meaning_summary;
    
    private final static TRelation[] NULL_TRELATION_ARRAY = new TRelation[0];

    public TRelation(int _id,TMeaning _meaning,TWikiText _wiki_text,
                    TRelationType _relation_type,String _meaning_summary)
    {
        id              = _id;
        meaning         = _meaning;
        wiki_text       = _wiki_text;
        relation_type   = _relation_type;

        meaning_summary = _meaning_summary;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets meaning from database */
    public TMeaning getMeaning() {
        return meaning;
    }

    /** Gets a summary of the semantic relation meaning (e.g. title of list of synonyms). */
    public String getMeaningSummary() {
        return meaning_summary;
    }

    /** Gets text (wikified sometimes). */
    public TWikiText getWikiText() {
        return wiki_text;
    }
    
    /** Gets type of semantic relation. */
    public Relation getRelationType() {
        if(null == relation_type)
            return null;
        return relation_type.getRelation();
    }

    /** Inserts records into tables: 'wiki_text' and 'relation'.
     * The insertion into 'wiki_text' results in updating records in tables:
     * 'wiki_text_words', 'page_inflecton', 'inflection', and 'page'.
     *
     * @param tmeaning      corresponding record in table 'meaning' to this relation
     * @param meaning_n     number of this meaning (for polysemous words)
     * (e.g. m_relations.get(Relation.hypernymy)[meaning_n] = WRelation for this meaning.)
     * @param m_relations   map from semantic relation (e.g. synonymy) to array of WRelation (one WRelation contains a list of synonyms for one meaning).
     */
    public static void storeToDB (Connect connect,TMeaning tmeaning,int meaning_n,
                                  Map<Relation, WRelation[]> m_relations) {

        if(null == tmeaning || null == m_relations || m_relations.isEmpty()) return;

        Collection<Relation> rr = m_relations.keySet();
        for(Relation r : rr) {

            TRelationType trelation_type = TRelationType.getRelationFast(r);
            WRelation[] wr = m_relations.get(r);
            if(meaning_n < wr.length && null != wr[meaning_n]) {

                WRelation cur_rel = wr[meaning_n];

                String meaning_summary = cur_rel.getMeaningSummary();

                WikiText[] phrases = cur_rel.get();
                for(WikiText p : phrases) {

                    TWikiText twiki_text = TWikiText.storeToDB(connect, p);

                    if(null != twiki_text) {
                        TRelation.insert(connect, tmeaning, twiki_text, 
                                        trelation_type, meaning_summary);
                    }
                }
            }
        }
    }

    /** Inserts record into the table 'relation'.<br><br>
     * INSERT INTO relation (meaning_id,wiki_text_id,relation_type_id) VALUES (11,12,13);
     * or
     * INSERT INTO relation (meaning_id,wiki_text_id,relation_type_id,meaning_summary) VALUES (11,12,13,"sum");
     *
     * @param meaning       corresponding meaning of the word
     * @param wiki_text     synonym word (or phrase), or antonym, etc.
     * @param relation_type semantic relation
     * @param meaning_summary summary of the definition for which synonyms
     *                      (antonyms, etc.) are being given. It could be null.
     *
     * @return null if data is absent
     */
    public static TRelation insert (Connect connect,
            TMeaning meaning,TWikiText wiki_text,TRelationType relation_type,
            String meaning_summary) {

        if(null == meaning || null == wiki_text || null == relation_type) {
            System.err.println("Error (wikt_parsed TRelation.insert()):: null arguments, meaning="+meaning+", wiki_text="+wiki_text+", relation_type="+relation_type);
            return null;
        }
        
        StringBuilder str_sql = new StringBuilder();
        TRelation relation = null;
        try
        {
            boolean b_sum = null != meaning_summary && meaning_summary.length() > 0;

            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("INSERT INTO relation (meaning_id,wiki_text_id,relation_type_id");

                if(b_sum)
                    str_sql.append(",meaning_summary");

                str_sql.append(") VALUES (");
                str_sql.append(meaning.getID());
                str_sql.append(",");
                str_sql.append(wiki_text.getID());
                str_sql.append(",");
                str_sql.append(relation_type.getID());

                if(b_sum) {
                    str_sql.append(",\"");
                    str_sql.append(PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, meaning_summary));
                    str_sql.append("\"");
                }

                str_sql.append(")");
                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }

            s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
                try {
                    if (rs.next ())
                        relation = new TRelation(rs.getInt("id"), meaning, wiki_text,
                                            relation_type, meaning_summary);
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (TRelation.insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return relation;
    }

    /** Selects rows from the table 'relation' by the meaning_id.<br><br>.
     * SELECT id,wiki_text_id,relation_type_id,meaning_summary FROM relation WHERE meaning_id=11;
     * @return empty array if data is absent
     */
    public static TRelation[] get (Connect connect,TMeaning meaning) {

        if(null == meaning) {
            System.err.println("Error (wikt_parsed TRelation.get()):: null argument: meaning.");
            return NULL_TRELATION_ARRAY;
        }
        
        StringBuilder str_sql = new StringBuilder();
        List<TRelation> list_rel = null;
        
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT id,wiki_text_id,relation_type_id,meaning_summary FROM relation WHERE meaning_id=");
                str_sql.append(meaning.getID());
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    while (rs.next ())
                    {
                        int          id =                               rs.getInt("id");
                        TWikiText    wt = TWikiText.getByID(connect,    rs.getInt("wiki_text_id"));
                        TRelationType r = TRelationType.getRelationFast(rs.getInt("relation_type_id"));

                        if(null != wt && null != r) {
                            if(null == list_rel)
                                       list_rel = new ArrayList<TRelation>();

                            byte[] bb = rs.getBytes("meaning_summary");
                            String sum = null == bb ? null : Encodings.bytesToUTF8(bb);

                            list_rel.add(new TRelation(id, meaning, wt, r, sum));
                        }
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TRelation.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }

        if(null == list_rel)
            return NULL_TRELATION_ARRAY;
        return (TRelation[])list_rel.toArray(NULL_TRELATION_ARRAY);
    }


    /** Counts number of rows from the table 'relation' related to the meaning_id.<br><br>.
     * SELECT COUNT(*) as a FROM relation WHERE meaning_id=11;
     * @return empty array if data is absent
     */
    public static int count (Connect connect,TMeaning meaning) {

        if(null == meaning) {
            System.err.println("Error (wikt_parsed TRelation.count()):: null argument: meaning.");
            return 0;
        }
        
        StringBuilder str_sql = new StringBuilder();
        int n = 0;

        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT COUNT(*) AS n FROM relation WHERE meaning_id=");
                str_sql.append(meaning.getID());
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                        n = rs.getInt("n");
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TRelation.count()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return n;
    }

    /** Selects row from the table 'relation' by ID.<br><br>
     * SELECT meaning_id,wiki_text_id,relation_type_id,meaning_summary FROM relation WHERE id=1;
     * @return null if data is absent
     */
    public static TRelation getByID (Connect connect,int id) {
        
        StringBuilder str_sql = new StringBuilder();
        TRelation relation = null;

        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT meaning_id,wiki_text_id,relation_type_id,meaning_summary FROM relation WHERE id=");
                str_sql.append(id);
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        TMeaning      m = TMeaning. getByID( connect,   rs.getInt("meaning_id"));
                        TWikiText    wt = TWikiText.getByID( connect,   rs.getInt("wiki_text_id"));
                        TRelationType r = TRelationType.getRelationFast(rs.getInt("relation_type_id"));
                        if(null != m && null != wt && null != r) {

                            byte[] bb = rs.getBytes("meaning_summary");
                            String sum = null == bb ? null : Encodings.bytesToUTF8(bb);

                            relation = new TRelation(id, m, wt, r, sum);
                        }
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TRelation.java getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return relation;
    }

    /** Deletes a row from the table 'relation' by ID.<br><br>
     * DELETE FROM relation WHERE id=1;
     * @param  id  unique ID in the table `relation`
     */
    public static void delete (Connect connect,TRelation relation) {

        if(null == relation) {
            System.err.println("Error (wikt_parsed TRelation.delete()):: null argument page.");
            return;
        }
        StringBuilder str_sql = new StringBuilder();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("DELETE FROM relation WHERE id=");
                str_sql.append(relation.getID());
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TRelation.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }

    /** Gets all semantic relation (pairs of word).
     * @return pairs of words,
     * e.g. car -> carriage, car -> automobile (synonyms)
     *      car -> vehicle (hyperohym)
     * or empty map, if relations are absent
     */
    public static Map<String,List<String>> getAllWordPairs (Connect connect) {

        // for each relation: get page<->wiki_text<->wiki_word + todo: type of relation

        long    t_start;
        float   t_work;

        int n_total = Statistics.Count(connect, "relation");
        System.out.println("Total relations: " + n_total);
        if(-1 == n_total)
            return null;
        t_start = System.currentTimeMillis();
        StringBuilder str_sql = new StringBuilder();
        Map<String,List<String>> m_words = null;

        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT meaning_id,wiki_text_id FROM relation");
    //str_sql.append("SELECT meaning_id,wiki_text_id FROM relation LIMIT 1000");
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    m_words = new HashMap<String,List<String>> ();
                    int n_cur = 0;
                    while (rs.next ())
                    {
                        //int          id =                               rs.getInt("id");
                        TMeaning      m = TMeaning. getByID( connect,   rs.getInt("meaning_id"));
                        TWikiText    wt = TWikiText.getByID( connect,   rs.getInt("wiki_text_id"));
                        //int wiki_text_id =  rs.getInt("wiki_text_id");

                        // skip today
                        /*
                        TRelationType r = TRelationType.getRelationFast(rs.getInt("relation_type_id"));
                        TRelation relation = null;
                        if(null != m && null != wt) && null != r)
                            relation = new TRelation(id, m, wt, r);*/

                        String page = null;
                        if(null != m) {
                            TLangPOS lang_pos = m.getLangPOS(connect);
                            if(null != lang_pos) {
                                TPage tpage = lang_pos.getPage();
                                if(null != tpage) {
                                    page = tpage.getPageTitle();
                                }
                            }
                        }

                        String w_rel = null;    // wiki word relation
                        if(null != page) {
                            //TWikiTextWords t_word = TWikiTextWords.getByID(connect, id);
                            TWikiTextWords t_word = TWikiTextWords.getOneByWikiText(connect, wt);
                            if(null != t_word) {
                                TPage tpage = t_word.getPage();
                                if(null != tpage) {
                                    w_rel = tpage.getPageTitle();
                                }
                            }
                        }

                        if(null != page && null != w_rel) {
                            List<String> list_rel = m_words.get(page);
                            if(null == list_rel) {
                                list_rel = new ArrayList<String>();
                                list_rel.add(w_rel);
                                m_words.put(page, list_rel);
                            } else {
                                if(!list_rel.contains(w_rel))
                                    list_rel.add(w_rel);
                            }
                        }

                        if(DEBUG && 0 == ++n_cur % 1000) {   // % 100
                            //if(n_cur<10900) continue;
                            long    t_cur, t_remain;

                            t_cur  = System.currentTimeMillis() - t_start;
                            t_remain = (long)((n_total - n_cur) * t_cur/(60f*1000f*(float)(n_cur)));
                                       // where time for 1 page = t_cur / n_cur
                                       // in min, since /(60*1000)
                            t_cur = (long)(t_cur/(60f*1000f));
                            //t_cur = t_cur/(60f*1000f));

                            System.out.println(n_cur + ": [" + page + ", " + w_rel +
                                    "], duration: "  + t_cur +   // t_cur/(60f*1000f) +
                                    " min, remain: " + t_remain +
                                    " min");
                        }


                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TRelation.java getAllWordPairs()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }

        long  t_end;
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        System.out.println("\n\nTime sec:" + t_work +
                "\nTotal relations: " + n_total);

        return m_words;
    }
    
    /** Gets a word defined by a semantic relation (e.g. the page "car" contains "[[automobile]]"
     * in a section "Synonyms", then the "automobile" will be returned).
     * @param trelation defines relation (e.g. synonymy) and source word (e.g. "car")
     * @return word defined by a semantic relation (e.g. "automobile" for "car"), or null if search failed
     */
    public static TPage getWikifiedPage (Connect connect,TRelation trelation) {
        
        if(null == trelation)
            return null;
        
        TWikiText twt = trelation.getWikiText();
        if(null == twt)
            return null;

        TWikiTextWords t_word = TWikiTextWords.getOneByWikiText(connect, twt);
        if(null != t_word)
            return t_word.getPage();
        return null;
    }

    /** Gets type of semantic relation between a pair of word: title of a page
     * (page_title) and word on this page (word).
     */
    private static Relation getRelationBetweenPageTitleAndWord (Connect connect,String page_title,String word) {
        
        if(0 == word.length() || 0 == page_title.length() || word.equals(page_title))
            return null;

        TPage page = TPage.get(connect, page_title);    if(null == page)        return null;
        TPage page_word = TPage.get(connect, word);     if(null == page_word)   return null;
            
        TLangPOS[] lp = TLangPOS.get(connect, page);
        for(TLangPOS lang_pos : lp) {
            TMeaning[] tm = TMeaning.get(connect, lang_pos);
            for(TMeaning tmeaning : tm) {
                TRelation[] tr = TRelation.get(connect, tmeaning);
                for(TRelation trelation : tr) {
                    TPage p = TRelation.getWikifiedPage(connect, trelation);
                    if(null != p && word.equals(p.getPageTitle())) {
                        return trelation.getRelationType();
                    }
                }
            }
        }
        return null;
    }
    
    /** Gets type of semantic relation between a pair of word (word1 and word2).
     */
    public static Relation getRelationType (Connect connect,String word1,String word2) {
        Relation r;

        r = TRelation.getRelationBetweenPageTitleAndWord(connect, word1, word2);
        if(null != r)
            return r;
            
        r = TRelation.getRelationBetweenPageTitleAndWord(connect, word2, word1);
        return r;
    }
}
