/* TLang.java - SQL operations with the table 'page' in Wiktionary parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikipedia.util.StringUtil;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import java.sql.*;


/** Table lang contains list of languages: name and ID. */
public class TLang {

    /** Unique page identifier. */
    private int id;

    /** Languages of wiki: code and name, e.g. 'ru' and 'Russian'. */
    private LanguageType lang;

    private static Map<Integer, LanguageType> id2lang;         //= new HashMap<Integer, LanguageType>();
    private static Map<String, Integer>          lang_code2id; //= new HashMap<String, Integer>();

    public TLang(int _id,LanguageType _lang) {
        id      = _id;
        lang    = _lang;
    }
    
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
        deleteAllRecordsResetAutoIncrement(connect);
        fillDB(connect);
        {
            int db_current_size = wikipedia.sql.Statistics.Count(connect, "lang");
            assert(db_current_size == LanguageType.size()); // 356 languages
        }   
    }
    
    /** Load data from a LanguageType class, sorts,
     * and fills local maps 'id2lang' and 'lang_code2id'. */
    public static void fillLocalMaps() {

        int size = LanguageType.size();
        Map<String, LanguageType> code2lang = LanguageType.getAllLanguages();

        List<String>list_code = new ArrayList<String>(size);
        for(String s : code2lang.keySet()) {
            list_code.add(s);
        }
        Collections.sort(list_code);

        // OK, we have list of language codes. Sorted list.
        // list_code

        id2lang      = new HashMap<Integer, LanguageType>(size);
        lang_code2id = new HashMap<String, Integer>(size);
        for(int id=0; id<size; id++) {

            String code = list_code.get(id);
            LanguageType lang = code2lang.get(code);
            id2lang.put(id, lang);
            lang_code2id.put(lang.getCode(), id);
        }
    }
    
    /** Fills database table 'lang' by data from LanguageType class. */
    public static void fillDB(Connect connect) {
        
        for(int id : id2lang.keySet()) {
            LanguageType lang = id2lang.get(id);

            /*if(lang.equals(LanguageType.ru)) {
                int z = 0;
            }*/

            insert (connect, lang.getCode(), lang.getName());     //insert (connect, lang.getCode(), lang.getCode());
            // insert (connect, lang.getCode(), connect.enc.EncodeFromJava(lang.getName()));
        }
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
            str_sql.append(code);
            str_sql.append("\",\"");
            String safe_title = StringUtil.spaceToUnderscore(
                                StringUtil.escapeChars(name));
            str_sql.append(safe_title);
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
    public static TLang get (Connect connect,LanguageType lt) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TLang       tp = null;

        if(null == lt) return null;
        String lang_code = lt.getCode();
        
        try {
            s = connect.conn.createStatement ();

            str_sql.append("SELECT id,name FROM lang WHERE code=\"");
            str_sql.append(lang_code);
            str_sql.append("\"");

            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
            {
                int id      = rs.getInt("id");
                String name = rs.getString("name");
                tp = new TLang(id, lt);

                if(!lt.getName().equalsIgnoreCase(name)) {
                    System.err.println("Warning: (wikt_parsed TLang.java get()):: Table 'lang' has unknown language name =" + name);
                }
            } else {
                    System.err.println("Error: (wikt_parsed TLang.java get()):: The language code '" + lang_code + "' is absent in the table 'lang'.");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLang.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
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
    public static void delete (Connect connect,LanguageType lt) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();

        if(null == lt) return;
        String lang_code = lt.getCode();

        try {
            s = connect.conn.createStatement ();

            str_sql.append("DELETE FROM lang WHERE code=\"");
            str_sql.append(lang_code);
            str_sql.append("\"");
            
            s.execute (str_sql.toString());

        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLang.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
    /** Deletes all records from the table 'lang', resets auto increment.
     *
     * DELETE FROM lang;
     * ALTER TABLE lang AUTO_INCREMENT = 0;
     */
    private static void deleteAllRecordsResetAutoIncrement (Connect connect) {

        Statement   s = null;
        ResultSet   rs= null;

        try {
            s = connect.conn.createStatement ();
            s.addBatch("DELETE FROM lang;");
            s.addBatch("ALTER TABLE lang AUTO_INCREMENT = 0;");
            s.executeBatch();

        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLang.java deleteAllRecordsResetAutoIncrement()):: sql='DELETE FROM lang; ALTER TABLE lang AUTO_INCREMENT = 0;' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
}
