/* MSRMeanSemrelXX.java - SQL operations with the tables 'mean_semrel_XX' 
 * in the database (wikt_mean_semrel) wich contains only word's meanings 
 * and semantic relations.
 * It is simplified Wiktionary parsed database based on wikt_parsed database.
 * XX is a language code.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wiktparsed.mean_semrel.parser.sql;

import wikt.constant.Relation;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikipedia.sql.PageTableBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import wikipedia.language.Encodings;

/** Table wikt_mean_semrel.mean_semrel_XX contains connected:
 * (1) entry / headword in the language XX
 * (1) text of the meaning (definition) of this entry
 * (2) related to this meaning synonyms, antonyms, etc.
 */
public class MSRMeanSemrelXX {
    
    private final static MSRMeanSemrelXX[] NULL_MSRMEANSEMRELXX_ARRAY = new MSRMeanSemrelXX[0];
    
    /** Unique identifier for this meaning. */
    private int id;
    
    /** Entry / headword in the language XX. */
    private String page_title;
    
    /** Text of the meaning (definition) of this entry (without wikification). */
    private String meaning;
    
    //private final static String delimiter = new String("|");
    
    /** Names of semantic relations. */
    private static final String[] table_fields_relations = {
        "synonyms",     "antonyms",
        "hypernyms",    "hyponyms",
        "holonyms",     "meronyms",
        "troponyms",    "coordinate_terms"
    };
    
    /** Semantic relations. */
    private static final Relation[] ar_relations = {
        Relation.synonymy,  Relation.antonymy,
        Relation.hypernymy, Relation.hyponymy,
        Relation.holonymy,  Relation.meronymy,
        Relation.troponymy, Relation.coordinate_term
    };
    private static Map<Relation, String> m_relations;
    
    /** Number of semantic relations (for this meaning): synonyms + antonyms + ... */
    private int n_sem_rel;
    
    
    /** Number of correct answers with these semantic relations. */
    private int success;
    
    /** Number of wrong answers with these semantic relations. */
    private int failure;
    
    public MSRMeanSemrelXX(int _id, String _page_title, String _meaning,
                    int _n_sem_rel, int _success, int _failure,
                    Map<Relation, String> _m_relations)
    {
        id          = _id;
        page_title  = _page_title;
        meaning     = _meaning;
        
        n_sem_rel   = _n_sem_rel;
        success     = _success;
        failure     = _failure;
        
        m_relations = _m_relations;
    }
    
    /** Gets semantic relations. */
    public Map<Relation, String> getRelations() {
        return m_relations;
    }
    
    /** Generates tables 'mean_semrel_XX' for each language code.<br><br>
     *
     * <PRE>
     * DROP TABLE IF EXISTS `mean_semrel_uk` ;
     *
     * CREATE  TABLE IF NOT EXISTS `mean_semrel_en` (
          `_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
          `page_title` VARCHAR(255) BINARY NOT NULL COMMENT 'headword' ,
          `meaning` VARCHAR(4095) BINARY NOT NULL COMMENT 'Definition text.' ,
          `synonyms` VARCHAR(4095) BINARY NULL COMMENT 'Synonyms joined by some symbol' ,
          `antonyms` VARCHAR(4095) BINARY NULL ,
          `hypernyms` VARCHAR(4095) BINARY NULL ,
          `hyponyms` VARCHAR(4095) BINARY NULL ,
          `holonyms` VARCHAR(4095) BINARY NULL ,
          `meronyms` VARCHAR(4095) BINARY NULL ,
          `troponyms` VARCHAR(4095) BINARY NULL ,
          `coordinate_terms` VARCHAR(4095) BINARY NULL ,
          `n_sem_rel` TINYINT UNSIGNED NOT NULL COMMENT 'Positive number of semantic relations (for this meaning)' ,
          `success` SMALLINT UNSIGNED NOT NULL COMMENT 'Number of correct answers with these sem. relations' ,
          `failure` SMALLINT UNSIGNED NOT NULL COMMENT 'Number of wrong answers with these sem. relations' ,
          PRIMARY KEY (`_id`) ,
          INDEX `idx_page_title` (`page_title`(7) ASC) )
        ENGINE = InnoDB
        COMMENT = 'entry name, meaning and semantic relations' ;
     * </PRE>
     *
     * @see http://code.google.com/p/wikokit/wiki/File_mean_semrel_empty_sql
     */
    public static void generateTables (Connect connect)
    {
        if(null == connect)
            return;
        StringBuffer str_sql = new StringBuffer();
        try {
            Statement   s = connect.conn.createStatement ();
            try {
                Map<String, LanguageType> code2lang = LanguageType.getAllLanguages();
                for(LanguageType lang_code : code2lang.values()) {

                    //String table_name = "index_" + lang_code.toStringASCII();
                    String table_name = "`mean_semrel_" + lang_code.toTablePrefix() + "`";

                    str_sql.setLength(0);
                    str_sql.append("DROP TABLE IF EXISTS "+ table_name);
                    s.execute(str_sql.toString());

                    str_sql.setLength(0);
                    str_sql.append("CREATE TABLE IF NOT EXISTS "+ table_name +" (" +
                        "`_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "`page_title` VARCHAR(255) BINARY NOT NULL," +  // COMMENT 'headword'
                        "`meaning` VARCHAR(4095) BINARY NOT NULL," + // COMMENT 'Definition text.'
                        "`synonyms` VARCHAR(4095) BINARY NULL," +
                        "`antonyms` VARCHAR(4095) BINARY NULL," +
                        "`hypernyms` VARCHAR(4095) BINARY NULL," +
                        "`hyponyms` VARCHAR(4095) BINARY NULL," +
                        "`holonyms` VARCHAR(4095) BINARY NULL," +
                        "`meronyms` VARCHAR(4095) BINARY NULL," +
                        "`troponyms` VARCHAR(4095) BINARY NULL," +
                        "`coordinate_terms` VARCHAR(4095) BINARY NULL," +
                        "`n_sem_rel` TINYINT UNSIGNED NOT NULL," +  //  COMMENT 'Positive number of semantic relations (for this meaning)'
                        "`success` SMALLINT UNSIGNED NOT NULL," +   // COMMENT 'Number of correct answers with these sem. relations'
                        "`failure` SMALLINT UNSIGNED NOT NULL," +   // COMMENT 'Number of wrong answers with these sem. relations'
                        "INDEX `idx_page_title` (`page_title`(7) ASC)) " +
                        "ENGINE = InnoDB"
                    );
                    s.execute (str_sql.toString());
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (MSRMeanSemrelXX.generateTables()): sql='" + str_sql + "' " + ex.getMessage());
        }
    }
    
    
    /** Inserts record into the table 'mean_semrel_XX'.<br><br>
     * INSERT INTO mean_semrel_os (page_title,meaning,synonyms,n_sem_rel,success,failure) VALUES ("test_page_msr","meaning_test_msr","synonyms_test_msr",1,0,0);
     *
     * @param xx_lang   defines XX language code in mean_semrel_XX table
     * @param meaning       corresponding meaning of the word
     * @param connect
     * @param page_title
     * @param meaning_text
     * @param m_relations
     * @param n_sem_rel number of semantic relations for this meaning
     */
    public static void insert ( LanguageType xx_lang,
                                Connect connect,
                                String page_title, String meaning_text,
                                Map<Relation, StringBuffer> m_relations,
                                int n_sem_rel)
    {
        if(0 == page_title.length() || 0 == meaning_text.length() || n_sem_rel < 1) {
            System.err.println("Error (MSRMeanSemrelXX.insert()):: null arguments, page_title="+page_title+", meaning_text="+meaning_text+", n_sem_rel="+n_sem_rel);
            return;
        }
        
        StringBuilder str_sql = new StringBuilder();
        StringBuilder values_sql = new StringBuilder();
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                String table_name = "mean_semrel_" + xx_lang.toTablePrefix();
                
                // INSERT INTO mean_semrel_XX (page_title,meaning,n_sem_rel,success,failure,synonyms,etc.) VALUES ("test_page_msr","meaning_test_msr",1,0,0,"synonyms_test_msr",etc.);
                str_sql.append("INSERT INTO ").append(table_name);
                str_sql.append("(page_title,meaning,n_sem_rel,success,failure");
                
                String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, page_title);
                String safe_meaning = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, meaning_text);
                
                values_sql.append('"').append(safe_title).append("\",\"");
                values_sql.append(safe_meaning).append("\",");
                values_sql.append(n_sem_rel).append(",0,0"); // 0 == success,failure

                boolean b_sem_rel = false;
                for(Relation r : m_relations.keySet()) {
                    String synset = m_relations.get(r).toString();
                    if (synset.length() > 0) {
                        b_sem_rel = true;
                        str_sql.append(",");
                        
                        if(r != Relation.coordinate_term) {
                            str_sql.append(r.toString());   // INSERT INTO (...",synonyms"...)
                        } else {
                            str_sql.append("coordinate_terms");   // "coordinate terms" -> "coordinate_terms" (without space in the table field name)
                        }
                        values_sql.append(",\"");
                        values_sql.append(              // VALUES (",word1|word2... |wordN" ...)
                            PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, synset));
                        values_sql.append("\"");
                    }
                }
                if(!b_sem_rel) {
                    System.err.println("Error (MSRMeanSemrelXX.insert()):: there are no semantic relations, page_title="+page_title+", meaning_text="+meaning_text+", n_sem_rel="+n_sem_rel);
                    return;
                }
                str_sql.append(") VALUES (");
                str_sql.append(values_sql);
                str_sql.append(")");
                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (MSRMeanSemrelXX.insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
    
    /** Gets rows from the XX table by entry headword (page_title).<br><br>
     * 
     * SELECT _id,meaning,synonyms,n_sem_rel,success,failure,synonyms FROM mean_semrel_os WHERE page_title="test_page_msr"
     * 
     * @param xx_lang   defines XX language code in mean_semrel_XX table
     * @return 
     */
    public static MSRMeanSemrelXX[] getByPage (Connect connect,
                                        LanguageType xx_lang,String page_title)
    {
        if(0 == page_title.length()) {
            System.err.println("Error (MSRMeanSemrelXX.get()):: null argument: page_title.");
            return NULL_MSRMEANSEMRELXX_ARRAY;
        }
        
        StringBuilder str_sql = new StringBuilder();
        
        // SELECT meaning,synonyms,n_sem_rel,success,failure,synonyms FROM mean_semrel_os WHERE page_title="test_page_msr"
        str_sql.append("SELECT _id,meaning,n_sem_rel,success,failure");
        
        for(String r : table_fields_relations)
            str_sql.append(",").append(r);   // SELECT ...,synonyms...
        
        String table_name = "mean_semrel_" + xx_lang.toTablePrefix();
        str_sql.append(" FROM ").append(table_name);
        str_sql.append(" WHERE page_title=\"");
        str_sql.append(PageTableBase.
                convertToSafeStringEncodeToDBWunderscore(connect, page_title));
        str_sql.append("\"");
        
        List<MSRMeanSemrelXX> list_rel = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    while (rs.next ())
                    {
                        int    _id = rs.getInt("_id");
                        String _meaning_text = Encodings.bytesToUTF8(rs.getBytes("meaning"));
                        int _n_sem_rel = rs.getInt("n_sem_rel");
                        int _success = rs.getInt("success");
                        int _failure = rs.getInt("failure");
                        
                        Map<Relation, String> _m_relations = new HashMap<Relation, String>();
                        
                        for(Relation r : ar_relations) {
                            String relation_field = r.toString();   // relation name as table field
                            if(r == Relation.coordinate_term)
                                relation_field = "coordinate_terms";   // "coordinate terms" -> "coordinate_terms" (without space in the table field name)
                            
                            byte[] byte_synset = rs.getBytes(relation_field);
                            if(null != byte_synset) {
                                String synset = Encodings.bytesToUTF8(byte_synset);
                                _m_relations.put(r, synset);
                            }
                        }

                        if(null == list_rel)
                                   list_rel = new ArrayList<MSRMeanSemrelXX>();

                        list_rel.add(new MSRMeanSemrelXX(_id, page_title, _meaning_text,
                                _n_sem_rel, _success, _failure, _m_relations));
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuote.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        if(null == list_rel)
            return NULL_MSRMEANSEMRELXX_ARRAY;
        return (MSRMeanSemrelXX[])list_rel.toArray(NULL_MSRMEANSEMRELXX_ARRAY);
    }
    
    /** Deletes row from the table 'quote' by a value of ID.<br><br>
     * DELETE FROM mean_semrel_os WHERE _id=4;
     * 
     * @param xx_lang   defines XX language code in mean_semrel_XX table
     */
    public void delete (Connect connect, LanguageType xx_lang) {

        String table_name = "mean_semrel_" + xx_lang.toTablePrefix();
        
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("DELETE FROM ").append(table_name);
        str_sql.append(" WHERE _id=");
        str_sql.append( id );
        try {
            Statement s = connect.conn.createStatement ();
            try {
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TQuote.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
    
}
