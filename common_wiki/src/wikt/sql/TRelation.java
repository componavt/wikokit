/* TRelation.java - SQL operations with the table 'relation' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikt.constant.Relation;
import wikt.word.WRelation;
import wikt.util.WikiText;

import wikipedia.language.Encodings;
import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
import java.sql.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collection;

/** An operations with the table 'relation' in MySQL wiktionary_parsed database.
 *
 * @see wikt.word.WRelation
 */
public class TRelation {

    /** Unique identifier in the table 'relation'. */
    private int id;

    /** One sense of a word. */
    private TMeaning meaning;               // int meaning_id;

    /** Text (wikified sometimes). */
    private TWikiText wiki_text;            // int wiki_text_id

    /** Semantic relation. */
    private TRelationType relation_type;    // int relation_type_id

    private final static TRelation[] NULL_TRELATION_ARRAY = new TRelation[0];

    public TRelation(int _id,TMeaning _meaning,TWikiText _wiki_text,TRelationType _relation_type) {
        id              = _id;
        meaning         = _meaning;
        wiki_text       = _wiki_text;
        relation_type   = _relation_type;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets meaning from database */
    public TMeaning getMeaning() {
        return meaning;
    }

    /** Gets text (wikified sometimes). */
    public TWikiText getWikiText() {
        return wiki_text;
    }

    /** Inserts records into tables: 'wiki_text' and 'relation'.
     * The insertion into 'wiki_text' results in updating records in tables:
     * 'wiki_text_words', 'page_inflecton', 'inflection', and 'page'.
     *
     * @param tmeaning      corresponding record in table 'meaning' to this relation
     * @param meaning_n     number of this meaning
     * (e.g. m_relations.get(Relation.hypernymy)[meaning_n] = WRelation for this meaning.)
     * @param m_relations   map from semantic relation (e.g. synonymy) to array of WRelation (one WRelation contains a list of synonyms for one meaning).
     */
    public static void storeToDB (Connect connect,TMeaning tmeaning,int meaning_n,
                                  Map<Relation, WRelation[]> m_relations) {

        if(null == tmeaning || null == m_relations || m_relations.size() == 0) return;

        Collection<Relation> rr = m_relations.keySet();
        for(Relation r : rr) {

            TRelationType trelation_type = TRelationType.getRelationFast(r);
            WRelation[] wr = m_relations.get(r);
            if(meaning_n < wr.length && null != wr[meaning_n]) {
                WikiText[] phrases = wr[meaning_n].get();
                for(WikiText p : phrases) {

                    TWikiText twiki_text = TWikiText.storeToDB(connect, p);

                    if(null != twiki_text) {
                        TRelation.insert(connect, tmeaning, twiki_text, trelation_type);
                    }
                }
            }
        }
    }

    /** Inserts record into the table 'relation'.<br><br>
     * INSERT INTO relation (meaning_id,wiki_text_id,relation_type_id) VALUES (11,12,13);
     * @param meaning       corresponding meaning of the word
     * @param wiki_text     synonym word (or phrase), or antonym, etc.
     * @param relation_type semantic relation
     * @return null if data is absent
     */
    public static TRelation insert (Connect connect,
            TMeaning meaning,TWikiText wiki_text,TRelationType relation_type) {
            
        if(null == meaning || null == wiki_text || null == relation_type) {
            System.err.println("Error (wikt_parsed TRelation.insert()):: null arguments, meaning="+meaning+", wiki_text="+wiki_text+", relation_type="+relation_type);
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TRelation relation = null;
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO relation (meaning_id,wiki_text_id,relation_type_id) VALUES (");
            str_sql.append(meaning.getID());
            str_sql.append(",");
            str_sql.append(wiki_text.getID());
            str_sql.append(",");
            str_sql.append(relation_type.getID());
            str_sql.append(")");
            s.executeUpdate (str_sql.toString());
            
            s = connect.conn.createStatement ();
            s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            rs = s.getResultSet ();
            if (rs.next ())
                relation = new TRelation(rs.getInt("id"), meaning, wiki_text, relation_type);
            
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TRelation.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return relation;
    }

    /** Selects rows from the table 'relation' by the meaning_id.<br><br>.
     * SELECT id,wiki_text_id,relation_type_id FROM relation WHERE meaning_id=11;
     * @return empty array if data is absent
     */
    public static TRelation[] get (Connect connect,TMeaning meaning) {

        if(null == meaning) {
            System.err.println("Error (wikt_parsed TRelation.get()):: null argument: meaning.");
            return null;
        }
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        List<TRelation> list_rel = null;
        
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id,wiki_text_id,relation_type_id FROM relation WHERE meaning_id=");
            str_sql.append(meaning.getID());
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            while (rs.next ())
            {
                int          id =                               rs.getInt("id");
                TWikiText    wt = TWikiText.getByID(connect,    rs.getInt("wiki_text_id"));
                TRelationType r = TRelationType.getRelationFast(rs.getInt("relation_type_id"));
                
                if(null != wt && null != r) {
                    if(null == list_rel)
                               list_rel = new ArrayList<TRelation>();
                    list_rel.add(new TRelation(id, meaning, wt, r));
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TRelation.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        if(null == list_rel)
            return NULL_TRELATION_ARRAY;
        return ((TRelation[])list_rel.toArray(NULL_TRELATION_ARRAY));
    }

    /** Selects row from the table 'relation' by ID.<br><br>
     * SELECT meaning_id,wiki_text_id,relation_type_id FROM relation WHERE id=1;
     * @return null if data is absent
     */
    public static TRelation getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TRelation relation = null;

        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT meaning_id,wiki_text_id,relation_type_id FROM relation WHERE id=");
            str_sql.append(id);
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
            {
                TMeaning      m = TMeaning. getByID( connect,   rs.getInt("meaning_id"));
                TWikiText    wt = TWikiText.getByID( connect,   rs.getInt("wiki_text_id"));
                TRelationType r = TRelationType.getRelationFast(rs.getInt("relation_type_id"));
                if(null != m && null != wt && null != r)
                    relation = new TRelation(id, m, wt, r);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TRelation.java getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
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

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM relation WHERE id=");
            str_sql.append(relation.getID());
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TRelation.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    /** Gets all semantic relation (pairs of word).
     * @return empty map, if it is absent
     */
    public static Map<String,String> getAllWordPairs (Connect connect) {
        
        return null;
    }
}
