/* TLang.java - SQL operations with the table 'page' in Wiktionary parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;

import java.util.Map;
import java.util.HashMap;

import java.sql.*;


/** Table lang contains list of languages: name and ID. */
public class TLang {

    /** Unique page identifier. */
    private int id;

    /** Languages of wiki: code and name, e.g. 'ru' and 'Russian'. */
    private LanguageType lang;

    private static Map<Integer, LanguageType> id2lang         = new HashMap<Integer, LanguageType>();
    private static Map<String, Integer>          lang_code2id = new HashMap<String, Integer>();

    /** Gets LanguageType by language code */
    /*public static LanguageType get(String code) throws NullPointerException
    {

        if(code2lang.containsKey(code)) {
            return code2lang.get(code);
         }
        throw new NullPointerException("Null LanguageType");
    }*/

    /** Gets unique ID of the language. */
    public int getID() {
        return id;
    }
    
    /** Gets language. */
    public LanguageType getLanguage() {
        return lang;
    }
    
    /** Deletes all records from the table 'lang',
     * loads language code and name from LanguageType.java,
     * sorts by language code,
     * fills the table.
     */
    public static void recreateTable(Connect connect) {

        fillLocalMaps();
        
        //int db_current_size = count();
        //if (db_current_size < LanguageType.size()) {
        deleteAllRecordsResetAutoIncrement(connect);
        fillDB(connect);
        // }
        {
            int db_current_size = count();
            assert(db_current_size == LanguageType.size());
        }
        
    }

    /** Load data from a LanguageType class, sorts,
     * and fills local maps 'id2lang' and 'lang_code2id'. */
    public static void fillLocalMaps() {
        
    }
    
    /** Fills database table 'lang' by data from LanguageType class. */
    public static void fillDB(Connect connect) {
        
    }

    


    /** Counts number of records (languages) in the table. */
    public static int count() {
        

        return 0;
    }
    
    /** Inserts record into the table 'lang'.
     *
     * INSERT INTO lang (code,name) VALUES ("ru","Russian");
     *
     * @param code  two (or more) letter language code, e.g. 'en', 'ru'
     * @param name  language name, e.g. 'English', 'Russian'
     */
    public static void insert (Connect connect,String code,String name) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO lang (code,name) VALUES (\"");

            //String safe_title = StringUtil.spaceToUnderscore(
            //                    StringUtil.escapeChars(page_title));
            //str_sql.append(safe_title);

            str_sql.append(code);
            str_sql.append("\",\"");
            str_sql.append(name);
            str_sql.append("\")");

            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLang.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    /** Selects row from the table 'lang' by a language code.
     *
     *  SELECT id,name FROM lang WHERE code="ru";
     *
     * @param  lang_code  title of Wiktionary article
     * @return null if a language code is absent in the table 'lang'
     */
    public static TLang get (Connect connect,String lang_code) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TLang       tp = null;
        
        try {
            s = connect.conn.createStatement ();

            str_sql.append("SELECT id,name FROM lang WHERE code=\"");
            str_sql.append(lang_code);
            str_sql.append("\"");

            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
            {
                int id              = rs.getInt("id");
                String name         = rs.getString("name");
                int wiki_link_count = rs.getInt("wiki_link_count");
                boolean is_in_wiktionary = rs.getBoolean("is_in_wiktionary");

                tp = new TPage(id, page_title, word_count, wiki_link_count, is_in_wiktionary);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPage.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return tp;
    }

    /** Deletes row from the table 'lang' by the language code.
     *
     *  DELETE FROM lang WHERE code="ru";
     *
     * @param  page_title  title of Wiktionary article
     */
    public static void delete (Connect connect,String lang_code) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        /*try {
            s = connect.conn.createStatement ();

            String safe_title = StringUtil.spaceToUnderscore(
                                StringUtil.escapeChars(page_title));

            str_sql.append("DELETE FROM page WHERE page_title=\"");
            str_sql.append(safe_title);
            str_sql.append("\"");

            s.execute (str_sql.toString());

        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPage.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }*/
    }

    // DELETE FROM lang;
        // ALTER TABLE lang AUTO_INCREMENT = 0;
    /** Deletes row from the table 'lang' by the language code.
     *
     *  DELETE FROM lang WHERE code="ru";
     *
     * @param  page_title  title of Wiktionary article
     */
    private static void deleteAllRecordsResetAutoIncrement (Connect connect) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        
    }
}
