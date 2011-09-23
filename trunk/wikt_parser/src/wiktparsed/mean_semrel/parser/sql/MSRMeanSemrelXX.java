/* MSRLang.java - SQL operations with the tables 'mean_semrel_XX' in the database 
 * (wikt_mean_semrel) wich contains only word's meanings and semantic relations.
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

import java.sql.*;
import java.util.Map;

/** Table wikt_mean_semrel.mean_semrel_XX contains connected:
 * (1) entry / headword in the language XX
 * (1) text of the meaning (definition) of this entry
 * (2) related to this meaning synonyms, antonyms, etc.
 */
public class MSRMeanSemrelXX {
    
    /** Unique identifier for this meaning. */
    private int id;
    
    /** Entry / headword in the language XX. */
    private String page_title;
    
    /** Text of the meaning (definition) of this entry (without wikification). */
    private StringBuffer text;
    
    private final static String delimiter = new String("|");
    
    /** Synonyms joined by delimiter. */
    private StringBuffer synonyms;
    
    /** Antonyms joined by delimiter. */
    private StringBuffer antonyms;
    
    private StringBuffer hypernyms;
    private StringBuffer hyponyms;
    private StringBuffer holonyms;
    private StringBuffer meronyms;
    private StringBuffer troponyms;
    private StringBuffer coordinate_terms;
    
    /** Number of semantic relations (for this meaning): synonyms + antonyms + ... */
    private int n_sem_rel;
    
    
    /** Number of correct answers with these semantic relations. */
    private int success;
    
    /** Number of wrong answers with these semantic relations. */
    private int failure;
    
    
    
    
    
    /** Generates tables 'mean_semrel_XX' for each language code.<br><br>
     *
     * <PRE>
     * DROP TABLE IF EXISTS `mean_semrel_uk` ;
     *
     * CREATE  TABLE IF NOT EXISTS `mean_semrel_en` (
          `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
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
          PRIMARY KEY (`id`) ,
          INDEX `idx_page_title` (`page_title`(7) ASC) )
        ENGINE = InnoDB
        COMMENT = 'entry name, meaning and semantic relations' ;
     * </PRE>
     *
     * @see http://code.google.com/p/wikokit/wiki/File_mean_semrel_empty_sql
     */
    public static void generateTables (Connect connect, LanguageType native_lang)
    {
        StringBuffer str_sql = new StringBuffer();
        try {
            Statement   s = connect.conn.createStatement ();
            try {
                Map<String, LanguageType> code2lang = LanguageType.getAllLanguages();
                String s_native_lang = native_lang.toStringASCII();
                for(LanguageType lang_code : code2lang.values()) {
                    if(lang_code.toStringASCII().equalsIgnoreCase(s_native_lang))
                        continue;

                    //String table_name = "index_" + lang_code.toStringASCII();
                    String table_name = "`mean_semrel_" + lang_code.toStringASCII() + "`";

                    str_sql.setLength(0);
                    str_sql.append("DROP TABLE IF EXISTS "+ table_name);
                    s.execute(str_sql.toString());

                    str_sql.setLength(0);
                    str_sql.append("CREATE TABLE IF NOT EXISTS "+ table_name +" (" +
                        "`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT," +
                        "`page_title` VARCHAR(255) BINARY NOT NULL COMMENT 'headword'," +
                        "`meaning` VARCHAR(4095) BINARY NOT NULL COMMENT 'Definition text.'," +
                        "`synonyms` VARCHAR(4095) BINARY NULL," +
                        "`antonyms` VARCHAR(4095) BINARY NULL," +
                        "`hypernyms` VARCHAR(4095) BINARY NULL," +
                        "`hyponyms` VARCHAR(4095) BINARY NULL," +
                        "`holonyms` VARCHAR(4095) BINARY NULL," +
                        "`meronyms` VARCHAR(4095) BINARY NULL," +
                        "`troponyms` VARCHAR(4095) BINARY NULL," +
                        "`coordinate terms` VARCHAR(4095) BINARY NULL," +
                        "`n_sem_rel` TINYINT UNSIGNED NOT NULL COMMENT 'Positive number of semantic relations (for this meaning)'," +
                        "`success` SMALLINT UNSIGNED NOT NULL COMMENT 'Number of correct answers with these sem. relations'," +
                        "`failure` SMALLINT UNSIGNED NOT NULL COMMENT 'Number of wrong answers with these sem. relations'," +
                        "INDEX `idx_page_title` (`page_title`(7) ASC) )" +
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
     * INSERT INTO relation (meaning_id,wiki_text_id,relation_type_id) VALUES (11,12,13);
     * or
     * INSERT INTO relation (meaning_id,wiki_text_id,relation_type_id,meaning_summary) VALUES (11,12,13,"sum");
     *
     * @param native_lang   defines XX language code in mean_semrel_XX table
     * @param meaning       corresponding meaning of the word
     * @param connect
     * @param page_title
     * @param meaning_text
     * @param m_relations
     * @param n_sem_rel number of semantic relations for this meaning
     */
    public static void insert ( LanguageType native_lang,
                                Connect connect,
                                String page_title, String meaning_text,
                                Map<Relation, StringBuffer> m_relations,
                                int n_sem_rel)
    {
        System.exit(0);
        // todo
        /*
        
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
         * 
         */
    }
    
    
    
}
