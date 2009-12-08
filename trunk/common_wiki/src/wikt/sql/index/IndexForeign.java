/* IndexForeign.java - SQL operations with the tables 'index_XX' in Wiktionary
 * parsed database, where XX is a language code.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql.index;

import wikt.sql.TPage;
import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
import wikipedia.language.LanguageType;
import wikipedia.language.Encodings;

import java.sql.*;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


/** The table 'index_XX' - wordlist of words in language with code XX
 * (table in Wiktionary parsed database).
 */
public class IndexForeign {

    /** Unique identifier in the table 'index_XX'. */
    //private int id;

    /** 'foreign_page' is not NULL if index_XX.foreign_has_definition==1, i.e.
     * there is the the Wiktionary article with the title 'foreign_word' and
     * this article has non-empty definition section.
     *
     * If 'foreign_page' is NULL, then use string 'foreign_word'.
     */
    private TPage foreign_page;

    /** Word in foreign language XX. */
    private String foreign_word;

    /** Corresponded page.page_title of the Wiktionary article in native language.
     * It could be NULL.
     */
    private TPage native_page;
    //private String native_page_title;

    private final static IndexForeign[] NULL_INDEXFOREIGN_ARRAY = new IndexForeign[0];

    public IndexForeign(TPage _foreign_page,String _foreign_word,
                         TPage _native_page)
    {
        foreign_page    = _foreign_page;
        foreign_word    = _foreign_word;
        native_page     = _native_page;
    }

    /** Gets page in native language. */
    public TPage getNativePage() {
        return native_page;
    }

    /** Gets page in foreign language. */
    public TPage getForeignPage() {
        return foreign_page;
    }

    /** Gets the title of page in foreign language. */
    public String getForeignWord() {
        return foreign_word;
    }

    /** Gets concatenation of foreign word, delimiter, and a word in native language. */
    public String getConcatForeignAndNativeWords(String delimiter) {
        if(null == native_page)
            return foreign_word;
        else
            return foreign_word + delimiter + native_page.getPageTitle();
    }

    /** Inserts record into the table 'index_XX'.<br><br>
     * INSERT INTO index_en (foreign_word,foreign_has_definition,native_page_title) VALUES ("water13",0,"вода13");
     *
     * @param foreign_word      word in foreign language XX
     * @param foreign_has_definition true, if there is any definition in the Wiktionary article with the title foreign_word
     * @param native_page_title the corresponded page.page_title of the 
     *              Wiktionary article in native language (it could be null)
     * @param native_lang       native language in the Wiktionary,
     *                          e.g. Russian language in Russian Wiktionary,
     * @param foreign_lang      foreign language XX
     * 
     */
    public static void insert (Connect connect, 
                                String foreign_word, boolean foreign_has_definition,
                                String native_page_title,
                                LanguageType native_lang,
                                LanguageType foreign_lang
                               ) {

        if(foreign_lang == native_lang)
            return;

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        boolean b_native_word = null != native_page_title && native_page_title.length() > 0;

        try
        {
            String table_name = "`index_" + foreign_lang.toStringASCII() + "`";
            String safe_title;
            
            s = connect.conn.createStatement ();
            
            if(b_native_word)
                str_sql.append("INSERT INTO "+table_name+" (foreign_word,foreign_has_definition,native_page_title) VALUES (\"");
            else
                str_sql.append("INSERT INTO "+table_name+" (foreign_word,foreign_has_definition) VALUES (\"");
                
            safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                    connect, foreign_word);
            str_sql.append(safe_title);
            str_sql.append("\",");
            str_sql.append(foreign_has_definition);
            
            if(b_native_word) {
                str_sql.append(",\"");
                safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                    connect, native_page_title);
                str_sql.append(safe_title);
                str_sql.append("\"");
            }

            //System.out.println(" foreign_word=" + foreign_word +
            //            "; foreign_has_definition=" + foreign_has_definition +
            //            "; native_page_title=" + native_page_title +
            //            "\n where SQL=" + str_sql +
            //            " (IndexForeign.insert)");
            
            str_sql.append(")");
            s.executeUpdate (str_sql.toString());
            
        }catch(SQLException ex) {
            System.err.println("SQLException (IndexForeign.insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    /** Inserts a record into the table 'index_XX' only if a pair
     * (foreign_word,native_page_title) is absent.
     *
     * // If foreign_word == native_page_title then insert only (foreign_word,NULL).
     * 
     * @param foreign_word      word in foreign language XX
     * @param foreign_has_definition true, if there is any definition in the Wiktionary article with the title foreign_word
     * @param native_page_title the corresponded page.page_title of the
     *              Wiktionary article in native language (it could be null)
     * @param native_lang       native language in the Wiktionary,
     *                          e.g. Russian language in Russian Wiktionary
     * @param foreign_lang      foreign language XX
     *
     */
    public static void insertIfAbsent (Connect conn,
                                String foreign_word, boolean foreign_has_definition,
                                String native_page_title,
                                LanguageType native_lang, LanguageType foreign_lang)
    {
        // if(null != native_page_title && 0 == foreign_word.compareTo(native_page_title))
            // native_page_title = null;       // then insert only (foreign_word,NULL)
                                            // since we want skip cases, e.g. :
                                            // word (de) -> word (en)

        if(!IndexForeign.has( conn, foreign_word,
                              native_page_title, foreign_lang))
        {
            insert (conn,foreign_word, foreign_has_definition,
                native_page_title,
                native_lang, foreign_lang);
        }
    }

    /** Checks whether exists any row in the table 'index_foreign' (index_XX)
     * with a pair (foreign_word, native_page_title).<br><br>
     *
     * @param  native_page_title    title in native language of Wiktionary
     *                               article, it could be NULL
     */
    public static boolean has (Connect conn, String foreign_word,
                     String native_page_title, LanguageType foreign_lang)
    {
        return 0 != count (conn, foreign_word, native_page_title, foreign_lang);
    }

    /** Counts number of rows from the table 'index_foreign' (index_XX) 
     * with a pair (foreign_word, native_page_title).<br><br>
     *
     * select COUNT(*) AS size from index_uk WHERE foreign_word="water13" AND native_page_title is NULL;
     * or
     * select COUNT(*) AS size from index_uk WHERE foreign_word="water13" AND native_page_title="вода13";
     *
     * @param  native_page_title    title in native language of Wiktionary
     *                               article, it could be NULL
     */
    public static int count (Connect conn, String foreign_word,
                     String native_page_title, LanguageType foreign_lang)
    {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();

        int size = 0;
        try {
            s = conn.conn.createStatement ();

            String table_name = "`index_" + foreign_lang.toStringASCII() + "`";

            String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(conn, foreign_word);

            str_sql.append("select COUNT(*) AS size from "+table_name+" WHERE foreign_word=\"");
            str_sql.append(safe_title);
            str_sql.append("\"");

            if(null == native_page_title) {
                // select COUNT(*) AS size from index_uk WHERE foreign_word="water13" AND native_page_title is NULL;
                str_sql.append(" AND native_page_title is NULL");
            } else {
                // select COUNT(*) AS size from index_uk WHERE foreign_word="water13" AND native_page_title="вода13";
                safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(conn, native_page_title);

                str_sql.append(" AND native_page_title=\"");
                str_sql.append(safe_title);
                str_sql.append("\"");
            }

            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                size = rs.getInt("size");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (IndexForeign.count()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return size;
    }

    /** Counts number of foreign parts of speech (POS) in the table
     * 'index_foreign' (index_XX).
     * 
     * @param  foreign_lang language code (XX)
     */
    public static int countNumberOfForeignPOS (Connect conn, 
                                    LanguageType foreign_lang)
    {
        return countNativePageTitleIsNull(conn, foreign_lang, true);
    }

    /** Counts number of translations in the table 'index_foreign' (index_XX).
     *
     * @param  foreign_lang language code (XX)
     */
    public static int countTranslations (Connect conn,
                                    LanguageType foreign_lang)
    {
        return countNativePageTitleIsNull(conn, foreign_lang, false);
    }

    /** Counts number of rows from the table 'index_foreign' (index_XX)
     * with a pair (foreign_word, native_page_title).<br><br>
     *
     * SELECT COUNT(*) FROM index_en WHERE native_page_title is NULL;
     * or
     * SELECT COUNT(*) FROM index_en WHERE native_page_title is not NULL;
     *
     * @param  native_page_title    title in native language of Wiktionary
     *                               article, it could be NULL
     * @param is_null defines "is NULL" or "is not NULL" SQL parameter 'native_page_title'
     */
    private static int countNativePageTitleIsNull (Connect conn, LanguageType foreign_lang,
                                        boolean is_null)
    {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();

        int size = 0;
        try {
            s = conn.conn.createStatement ();

            String table_name = "`index_" + foreign_lang.toStringASCII() + "`";

            str_sql.append("SELECT COUNT(*) AS size from "+table_name+" WHERE native_page_title is ");

            if(is_null) {
                // SELECT COUNT(*) FROM index_en WHERE native_page_title is NULL;
                str_sql.append("NULL");
            } else {
                // SELECT COUNT(*) FROM index_en WHERE native_page_title is not NULL;
                str_sql.append("not NULL");
            }

            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                size = rs.getInt("size");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (IndexForeign.countNativePageTitleIsNull()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return size;
    }

    /** Selects rows from the table 'index_foreign' by the prefix of foreign word.<br><br>
     *
     * SELECT foreign_word,native_page_title FROM index_en WHERE foreign_word LIKE 'water1%';
     * 
     * @param  foreign_word title of Wiktionary article
     * @param  limit    constraint of the number of rows returned,
     *                  if it's negative then a constraint is omitted
     * @param native_lang       native language in the Wiktionary,
     *                          e.g. Russian language in Russian Wiktionary
     * 
     * @return array of words started from the prefix (empty array if they are absent)
     */
    public static IndexForeign[] getByPrefixForeign (
                                        Connect connect,
                                        String prefix_foreign_word, int limit,
                                        LanguageType native_lang,
                                        LanguageType foreign_lang
                                        ) {

        if(foreign_lang == native_lang)
            return NULL_INDEXFOREIGN_ARRAY;
            
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();

        List<IndexForeign> if_list = null;

        if(0==limit)
            return NULL_INDEXFOREIGN_ARRAY;

        try {
            s = connect.conn.createStatement ();

            String safe_prefix = PageTableBase.convertToSafeWithWildCard(connect,
                                                            prefix_foreign_word);
            String table_name = "`index_" + foreign_lang.toStringASCII() + "`";

            str_sql.append("SELECT foreign_word,foreign_has_definition,native_page_title FROM "+
                    table_name + " WHERE foreign_word LIKE \"");
            str_sql.append(safe_prefix);
                //str_sql.append("%\"");
            str_sql.append("\"");

            if(limit > 0) {
                str_sql.append(" LIMIT ");
                str_sql.append(limit);
            }
            //System.out.print("safe_prefix=" + safe_prefix);
            
            rs = s.executeQuery (str_sql.toString());
            while (rs.next () &&
                    (limit < 0 || null == if_list || if_list.size() < limit))
            {
                String foreign_word = Encodings.bytesToUTF8(rs.getBytes("foreign_word"));
                boolean foreign_has_definition = rs.getBoolean("foreign_has_definition");
                byte[] bt_native_page_title = rs.getBytes("native_page_title");
                
                TPage foreign_page = null;
                if(foreign_has_definition)
                    foreign_page = TPage.get(connect, foreign_word);

                TPage native_page = null;
                if(null != bt_native_page_title) {
                    String native_page_title = Encodings.bytesToUTF8(bt_native_page_title);

                    if(native_page_title.length() > 0)
                        native_page = TPage.get(connect, native_page_title);
                }

                IndexForeign _if = new IndexForeign(
                        foreign_page, foreign_word, native_page);
                        
                if(null == if_list)
                    if_list = new ArrayList<IndexForeign>();

                if_list.add(_if);

                //System.out.println(" foreign_word=" + foreign_word +
                //        "; foreign_has_definition=" + foreign_has_definition +
                //        "; native_page_title=" + native_page_title +
                //        " (IndexForeign.getByPrefixForeign)");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (IndexForeign.getByPrefixForeign()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        
        if(null == if_list)
            return NULL_INDEXFOREIGN_ARRAY;

        return ((IndexForeign[])if_list.toArray(NULL_INDEXFOREIGN_ARRAY));
    }

    /** Deletes a record from the table 'index_XX'.<br><br>
     * 
     * DELETE FROM index_en WHERE foreign_word="water12" AND native_page_title="ru_water12";
     * or
     * DELETE FROM index_en WHERE foreign_word="water12" AND native_page_title=NULL;
     *
     * @param foreign_word      word in foreign language XX
     * @param native_page_title the corresponded page.page_title of the
     *              Wiktionary article in native language (it could be null)
     * @param native_lang       native language in the Wiktionary,
     *                          e.g. Russian language in Russian Wiktionary,
     * @param foreign_lang      foreign language XX
     */
    public static void delete (Connect connect,
                                String foreign_word, String native_page_title,
                                LanguageType native_lang, LanguageType foreign_lang
                               ) {

        if(foreign_lang == native_lang)
            return;
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        boolean b_native_word = null != native_page_title && native_page_title.length() > 0;

        try
        {
            String table_name = "`index_" + foreign_lang.toStringASCII() + "`";
            String safe_title;

            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM "+table_name+" WHERE foreign_word=\"");

            safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                    connect, foreign_word);
            str_sql.append(safe_title + "\"");
            
            str_sql.append(" AND native_page_title=");
            if(b_native_word) {
                safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                    connect, native_page_title);
                str_sql.append("\"" + safe_title + "\"");
            } else
                str_sql.append("NULL");
            
            s.executeUpdate (str_sql.toString());

        }catch(SQLException ex) {
            System.err.println("SQLException (IndexForeign.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
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
    public static void generateTables (Connect connect, LanguageType native_lang)
    {
        Statement   s = null;
        StringBuffer str_sql = new StringBuffer();
        
        try {
            s = connect.conn.createStatement ();

            Map<String, LanguageType> code2lang = LanguageType.getAllLanguages();
            String s_native_lang = native_lang.toStringASCII();
            for(LanguageType lang_code : code2lang.values()) {
                if(lang_code.toStringASCII().equalsIgnoreCase(s_native_lang))
                    continue;

                //String table_name = "index_" + lang_code.toStringASCII();
                String table_name = "`index_" + lang_code.toStringASCII() + "`";
                
                str_sql.setLength(0);
                str_sql.append("DROP TABLE IF EXISTS "+ table_name);
                s.execute(str_sql.toString());
                
                str_sql.setLength(0);
                str_sql.append("CREATE TABLE IF NOT EXISTS "+ table_name +" (" +
                    "`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "`foreign_word` VARCHAR(255) BINARY NOT NULL," +
                    "`foreign_has_definition` TINYINT(1) NOT NULL," +
                    "`native_page_title` VARCHAR(255) BINARY," +
                    "PRIMARY KEY (`id`)," +
                    "INDEX `foreign_word` (`foreign_word` (7) ASC)," +
                    "INDEX `native_page_title` (`native_page_title` (7) ASC)," +
                    "UNIQUE `foreign_native` (`foreign_word` ASC, `native_page_title` ASC) )" +
                    "ENGINE = InnoDB"
                );
                
                s.execute (str_sql.toString());
            }
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
