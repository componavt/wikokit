/* IndexForeign.java - SQL operations with the tables 'index_XX' in Wiktionary
 * parsed database, where XX is a language code.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql.index;

import wikipedia.sql.Connect;

import java.sql.*;

/** The table 'index_XX' - wordlist of words in language with code XX
 * (table in Wiktionary parsed database).
 */
public class IndexForeign {

    /** Generates tables 'index_XX' for each language code.<br><br>
     * INSERT INTO index_native (page_id,page_title,has_relation) VALUES (12,"water12",TRUE);
     *
     * <PRE>
     * DROP TABLE IF EXISTS `index_uk` ;
     *
     * CREATE  TABLE IF NOT EXISTS `index_uk` (
     * `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
     * `foreign_word` VARCHAR(255) BINARY NOT NULL COMMENT 'word (in Language \'uk\') found somewhere in the Wiktionary article' ,
     * `native_page_title` VARCHAR(255) BINARY COMMENT 'page.page_title of this Wiktionary article in native language' ,
     * PRIMARY KEY (`id`) ,
     * INDEX `foreign_word` (`foreign_word` ASC) ,
     * INDEX `native_page_title` (`native_page_title` ASC) )
     * ENGINE = InnoDB
     * COMMENT = 'words with this language code (see table postfix)';
     * </PRE>
     *
     * @see http://code.google.com/p/wikokit/wiki/Wordlist_for_each_language___Database_tables_e_g__index_en?ts=1258826116&updated=Wordlist_for_each_language___Database_tables_e_g__index_en
     */
    public static void generateTables (Connect connect)
    {
        Statement   s = null;
        StringBuffer str_sql = new StringBuffer();
        
        // temp
        String lang_code = "uk";

        String table_name = "index_" + lang_code;
        
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DROP TABLE IF EXISTS `"+ table_name +"`");
            s.execute(str_sql.toString());
            
            str_sql.setLength(0);
            str_sql.append("CREATE TABLE IF NOT EXISTS `"+ table_name +"` (" +
                "`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT," +
                "`foreign_word` VARCHAR(255) BINARY NOT NULL," +
                "`native_page_title` VARCHAR(255) BINARY," +
                "PRIMARY KEY (`id`)," +
                "INDEX `foreign_word` (`foreign_word` ASC)," +
                "INDEX `native_page_title` (`native_page_title` ASC) ) " +
                "ENGINE = InnoDB"
            );

            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (IndexForeign.generateTables()): sql='" + str_sql + "' " + ex.getMessage());
        } finally {
            if (s != null) {
                try { s.close();
                } catch (SQLException sqlEx) { }
                s = null;
            }
        }
    }

}
