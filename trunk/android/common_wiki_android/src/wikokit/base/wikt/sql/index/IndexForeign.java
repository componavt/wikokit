/* IndexForeign.java - SQL operations with the tables 'index_XX' in Wiktionary
 * parsed database, where XX is a language code.
 *
 * Copyright (c) 2009-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql.index;

import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikipedia.language.LanguageType;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


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
    /*public static void insert (Connect connect, 
                                String foreign_word, boolean foreign_has_definition,
                                String native_page_title,
                                LanguageType native_lang,
                                LanguageType foreign_lang
                               ) {
        if(foreign_lang == native_lang)
            return;
        
        StringBuilder str_sql = new StringBuilder();
        boolean b_native_word = null != native_page_title && native_page_title.length() > 0;

        String table_name = "`index_" + foreign_lang.toTablePrefix() + "`";

        if(b_native_word)
            str_sql.append("INSERT INTO "+table_name+" (foreign_word,foreign_has_definition,native_page_title) VALUES (\"");
        else
            str_sql.append("INSERT INTO "+table_name+" (foreign_word,foreign_has_definition) VALUES (\"");

        String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
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
        try {
            Statement s = connect.conn.createStatement ();
            try {
                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (IndexForeign.insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }*/

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
    /*public static void insertIfAbsent (Connect conn,
                                String foreign_word, boolean foreign_has_definition,
                                String native_page_title,
                                LanguageType native_lang, LanguageType foreign_lang)
    {
        // if(null != native_page_title && 0 == foreign_word.compareTo(native_page_title))
            // native_page_title = null;       // then insert only (foreign_word,NULL)
                                            // since we want skip cases, e.g. :
                                            // word (de) -> word (en)

        if(native_lang == foreign_lang) {
            System.out.println("Error (IndexForeign.insertIfAbsent()):: native_lang == foreign_lang, It's possible that Wiktionary article about foreign word contains translation section! foreign_word='" +
                    foreign_word + "'; native_page_title = '" + native_page_title + "'");
            return;
        }

        if(!IndexForeign.has( conn, foreign_word,
                              native_page_title, foreign_lang))
        {
            insert (conn,foreign_word, foreign_has_definition,
                native_page_title,
                native_lang, foreign_lang);
        }
    }*/

    /** Checks whether exists any row in the table 'index_foreign' (index_XX)
     * with a pair (foreign_word, native_page_title).<br><br>
     *
     * @param  native_page_title    title in native language of Wiktionary
     *                               article, it could be NULL
     */
    public static boolean has (SQLiteDatabase db, String foreign_word,
                     String native_page_title, LanguageType foreign_lang)
    {
        return 0 != count (db, foreign_word, native_page_title, foreign_lang);
    }

    /** Counts number of rows from the table 'index_foreign' (index_XX) 
     * with a pair (foreign_word, native_page_title).<br><br>
     *
     * select COUNT(*) AS size from index_en WHERE foreign_word="water" AND native_page_title is NULL;
     * or
     * select COUNT(*) AS size from index_en WHERE foreign_word="water" AND native_page_title="вода";
     *
     * @param  native_page_title    title in native language of Wiktionary
     *                              article, it could be NULL
     */
    public static int count (SQLiteDatabase db, String foreign_word,
                     String native_page_title, LanguageType foreign_lang)
    {
        if(null == foreign_word || foreign_word.length() == 0)
            return -1;
        
        int size = 0;
        
        final String table_name = "`index_" + foreign_lang.toTablePrefix() + "`";
        
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("select COUNT(*) from ").append(table_name).append(" WHERE foreign_word=\"");
        str_sql.append(foreign_word);
        str_sql.append("\"");
        
        if(null == native_page_title) {
            // select COUNT(*) AS size from index_en WHERE foreign_word="water" AND native_page_title is NULL;
            str_sql.append(" AND native_page_title is NULL");
        } else {
            // select COUNT(*) AS size from index_uk WHERE foreign_word="water" AND native_page_title="вода";
            str_sql.append(" AND native_page_title=\"");
            str_sql.append(native_page_title);
            str_sql.append("\"");
        }
        
        Cursor c = db.rawQuery(str_sql.toString(), null);
        if (c.moveToFirst())            
            size = c.getInt(0);

        if (c != null && !c.isClosed()) {
            c.close();
        }
        
        return size;
    }

    /** Counts number of foreign parts of speech (POS) in the table
     * 'index_foreign' (index_XX).
     * 
     * @param  foreign_lang language code (XX)
     */
    public static int countNumberOfForeignPOS (SQLiteDatabase db, 
                                    LanguageType foreign_lang)
    {
        return countNativePageTitleIsNull(db, foreign_lang, true);
    }

    /** Counts number of translations in the table 'index_foreign' (index_XX).
     *
     * @param  foreign_lang language code (XX)
     */
    public static int countTranslations (SQLiteDatabase db,
                                    LanguageType foreign_lang)
    {
        return countNativePageTitleIsNull(db, foreign_lang, false);
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
    private static int countNativePageTitleIsNull (SQLiteDatabase db, LanguageType foreign_lang,
                                        boolean is_null)
    {
        int size = 0;
        final String table_name = "`index_" + foreign_lang.toTablePrefix() + "`";
        
        StringBuilder str_sql = new StringBuilder();
        str_sql.append("SELECT COUNT(*) from ").append(table_name).append(" WHERE native_page_title is ");
        if(is_null) {
            // SELECT COUNT(*) FROM index_en WHERE native_page_title is NULL;
            str_sql.append("NULL");
        } else {
            // SELECT COUNT(*) FROM index_en WHERE native_page_title is not NULL;
            str_sql.append("not NULL");
        }
        
        Cursor c = db.rawQuery(str_sql.toString(), null);
        if (c.moveToFirst())            
            size = c.getInt(0);

        if (c != null && !c.isClosed()) {
            c.close();
        }
        
        return size;
    }

    /** Selects rows from the table 'index_foreign' by the prefix of foreign word.<br><br>
     *
     * SELECT foreign_word,native_page_title FROM index_en WHERE foreign_word LIKE 'water-%';
     * 
     * @param  prefix_foreign_word the begining of the page_titles
     * @param  limit    constraint of the number of rows returned,
     *                  if it's negative then a constraint is omitted
     * @param native_lang       native language in the Wiktionary,
     *                          e.g. Russian language in Russian Wiktionary
     * @param b_meaning return articles with definitions (constraint)
     * @param  b_sem_rel return articles with semantic relations
     * 
     * @return array of words started from the prefix (empty array if they are absent)
     */
    public static IndexForeign[] getByPrefixForeign (
                                        SQLiteDatabase db,
                                        String prefix_foreign_word, int limit,
                                        LanguageType native_lang,
                                        LanguageType foreign_lang,
                                        boolean b_meaning,
                                        boolean b_sem_rel
                                        ) {
        if(foreign_lang == native_lang || 0==limit)
            return NULL_INDEXFOREIGN_ARRAY;
        
        List<IndexForeign> if_list = null;
        
        //String table_name = "`index_" + foreign_lang.toTablePrefix() + "`";
        String table_name = "index_" + foreign_lang.toTablePrefix();
        
        String str_limit = "";
        int limit_with_reserve = limit;
        if( limit_with_reserve > 0) {
            if(b_meaning)
                limit_with_reserve += 142; // since some words without meaning will be skipped
            if(b_sem_rel)
                limit_with_reserve += 1312; // since some words without relations will be skipped
            str_limit = "" + limit_with_reserve;
        }        

        // SELECT foreign_word,foreign_has_definition,native_page_title FROM index_en WHERE foreign_word LIKE 'water-%';
        Cursor c;
        c = db.query(table_name, 
                new String[] { "foreign_word", "foreign_has_definition", "native_page_title"}, 
                "foreign_word LIKE \"" + prefix_foreign_word + "\"", 
                null, null, null, null,
                str_limit);
        
        if (c.moveToFirst()) {
            do {
                int i_foreign_word = c.getColumnIndexOrThrow("foreign_word");
                int i_foreign_has_definition = c.getColumnIndexOrThrow("foreign_has_definition");
                int i_native_page_title = c.getColumnIndexOrThrow("native_page_title");
                
                String _foreign_word = c.getString(i_foreign_word);
                boolean _foreign_has_definition = 0 != c.getInt(i_foreign_has_definition);
                String _native_page_title = c.getString(i_native_page_title);
                
                boolean b_add = true;
                if(b_meaning)               // filter: words only with definitions
                    b_add = b_add && _foreign_has_definition;
                if (b_add) {
                    
                    TPage _foreign_page = null;
                    if(_foreign_has_definition)
                        _foreign_page = TPage.get(db, _foreign_word);
                    
                    if(b_sem_rel) {
                        if(!_foreign_has_definition || null == _foreign_page) {
                            b_add = false;
                        } else {
                            _foreign_page.setLangPOS( TLangPOS.getRecursive(db, _foreign_page) );    // fills property: .foreign_page.hasSemanticRelation()
                        }
                        b_add = b_add && _foreign_has_definition && _foreign_page.hasSemanticRelation();
                    }
                    if (b_add) {
                        
                        TPage _native_page = null;
                        if(null != _native_page_title && _native_page_title.length() > 0) {
                            _native_page = TPage.get(db, _native_page_title);
                        }
                        
                        IndexForeign _if = new IndexForeign(
                                _foreign_page, _foreign_word, _native_page);

                        if(null == if_list)
                            if_list = new ArrayList<IndexForeign>();
                        if_list.add(_if);
                        //System.out.println("IndexForeign:getByPrefixForeign foreign_word=" + foreign_word +
                        //        "; foreign_has_definition=" + foreign_has_definition +
                        //        "; native_page_title=" + native_page_title);
                    }
                }

                

            } while (c.moveToNext() &&
                    (limit_with_reserve < 0 || null == if_list || if_list.size() < limit_with_reserve));
        }
        if (c != null && !c.isClosed()) {
            c.close();
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
    /*public static void delete (Connect connect,
                                String foreign_word, String native_page_title,
                                LanguageType native_lang, LanguageType foreign_lang
                               ) {
        if(foreign_lang == native_lang)
            return;
        
        boolean b_native_word = null != native_page_title && native_page_title.length() > 0;

        StringBuilder str_sql = new StringBuilder();
        String table_name = "`index_" + foreign_lang.toTablePrefix() + "`";
        str_sql.append("DELETE FROM ").append(table_name).append(" WHERE foreign_word=\"");

        String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                connect, foreign_word);
        str_sql.append(safe_title).append("\"");

        str_sql.append(" AND native_page_title=");
        if(b_native_word) {
            safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(
                                connect, native_page_title);
            str_sql.append("\"").append(safe_title).append("\"");
        } else
            str_sql.append("NULL");

        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (IndexForeign.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }*/
    
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
    /*public static void generateTables (Connect connect, LanguageType native_lang)
    {
        Statement   s = null;
        StringBuffer str_sql = new StringBuffer();
        
        try {
            s = connect.conn.createStatement ();

            Map<String, LanguageType> code2lang = LanguageType.getAllLanguages();
            String s_native_lang = native_lang.toTablePrefix();
            for(LanguageType lang_code : code2lang.values()) {
                if(lang_code.toTablePrefix().equalsIgnoreCase(s_native_lang))
                    continue;

                //String table_name = "index_" + lang_code.toStringASCII();
                String table_name = "`index_" + lang_code.toTablePrefix() + "`";
                
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
    }*/

}
