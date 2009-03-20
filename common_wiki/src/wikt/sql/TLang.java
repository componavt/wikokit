/* TLang.java - SQL operations with the table 'page' in Wiktionary parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikipedia.language.LanguageType;
import wikipedia.util.StringUtil;

import wikipedia.sql.Connect;
import wikipedia.sql.UtilSQL;
import wikipedia.sql.Statistics;
import java.sql.*;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/** Table lang contains list of languages: name and ID. */
public class TLang {

    /** Unique page identifier. */
    private int id;

    /** Languages of wiki: code and name, e.g. 'ru' and 'Russian'. */
    private LanguageType lang;

    /** Map from id to language. It is created from data in the table `lang`,
     * which is created from data in LanguageType.java.*/
    private static Map<Integer, TLang> id2lang;

    /** Map from language to id.*/
    private static Map<LanguageType, Integer> lang2id;

    private final static TLang[] NULL_TLANG_ARRAY = new TLang[0];
    
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

    /** Gets language ID from the table 'lang'.<br><br>
     * 
     * REM: the functions createFastMaps() should be run at least once,
     * before this function execution.
     */
    public static int getIDFast(LanguageType lt) {
        if(null == lang2id) {
            System.err.println("Error (wikt_parsed TLang.getIDFast()):: What about calling 'createFastMaps()' before?");
            return -1;
        }
        if(null == lt) {
            System.err.println("Error (wikt_parsed TLang.getIDFast()):: argument LanguageType is null");
            return -1;
        }
        return lang2id.get(lt);
    }

    /** Gets language by ID from the table 'lang'.<br><br>
     *
     * REM: the functions createFastMaps() should be run at least once,
     * before this function execution.
     */  
    public static TLang getTLangFast(int id) {
        if(null == id2lang) {
            System.err.println("Error (wikt_parsed TLang.getTLangFast()):: What about calling 'createFastMaps()' before?");
            return null;
        }
        if(id <= 0) {
            System.err.println("Error (wikt_parsed TLang.getTLangFast()):: argument id <=0, id = "+id);
            return null;
        }
        return id2lang.get(id);
    }

    /** Gets language TLang by LanguageType from the table 'lang'.<br><br>
     *
     * REM: the functions createFastMaps() should be run at least once,
     * before this function execution.
     */
    public static TLang get(LanguageType lt) {
        return getTLangFast(getIDFast(lt));
    }

    /** Read all records from the table 'lang',
     * fills the internal map from a table ID to a language.
     * 
     * REM: during a creation of Wikrtionary parsed database
     * the functions recreateTable() should be called (before this function).
     */
    public static void createFastMaps(Connect connect) {

        System.out.println("Loading table `lang`...");
        
        TLang[] tlangs = getAllTLang(connect);
        int size = tlangs.length;
        if(tlangs.length != LanguageType.size()) {
            System.out.println("Warning (wikt_parsed TLang.java createFastMaps()):: LanguageType.size (" + LanguageType.size()
                    + ") is not equal to size of table 'lang'("+ size +"). Is the database outdated?");
        }

        if(null != id2lang && id2lang.size() > 0)
            id2lang.clear();
        if(null != lang2id && lang2id.size() > 0)
            lang2id.clear();
        
        id2lang  = new LinkedHashMap<Integer, TLang>(size);
        lang2id  = new LinkedHashMap<LanguageType, Integer>(size);
        
        for(TLang t : tlangs) {
            id2lang.put(t.getID(), t);
            lang2id.put(t.getLanguage(), t.getID());
        }
    }

    /** Gets all records from the table 'lang'.
     */
    private static TLang[] getAllTLang(Connect connect) {

        int size = Statistics.Count(connect, "lang");
        if(0==size) {
            System.err.println("Error (wikt_parsed TLang.java getAllTLang()):: The table `lang` is empty!");
            return NULL_TLANG_ARRAY;
        }
        
        List<TLang>tlang_list = new ArrayList<TLang>(size);

        Map<String, LanguageType> ll = LanguageType.getAllLanguages();
        for(LanguageType l : ll.values()) {
            TLang t = get(connect, l);
            if(null != t)
                tlang_list.add(t);
        }
        return( (TLang[])tlang_list.toArray(NULL_TLANG_ARRAY) );
    }

    /** Deletes all records from the table 'lang',
     * loads language code and name from LanguageType.java,
     * sorts by language code,
     * fills the table.
     */
    public static void recreateTable(Connect connect) {

        //Map<Integer, LanguageType> id2lang          = null; //= new HashMap<Integer, LanguageType>();
        //Map<String, Integer>          lang_code2id  = null; //= new HashMap<String, Integer>();

        System.out.println("Recreating the table `lang`...");
        Map<Integer, LanguageType> id2lang = fillLocalMaps();
        UtilSQL.deleteAllRecordsResetAutoIncrement(connect, "lang");
        fillDB(connect, id2lang);
        {
            int db_current_size = wikipedia.sql.Statistics.Count(connect, "lang");
            assert(db_current_size == LanguageType.size()); // 356 languages
        }   
    }
    
    /** Load data from a LanguageType class, sorts,
     * and fills local maps 'id2lang' and 'lang_code2id'. */
    private static Map<Integer, LanguageType> fillLocalMaps() {         //Map<String, Integer>          lang_code2id

        int size = LanguageType.size();
        Map<String, LanguageType> code2lang = LanguageType.getAllLanguages();

        List<String>list_code = new ArrayList<String>(size);
        for(String s : code2lang.keySet()) {
            list_code.add(s);
        }
        Collections.sort(list_code);

        // OK, we have list of language codes. Sorted list.
        // list_code

        Map<Integer, LanguageType> id2lang = new LinkedHashMap<Integer, LanguageType>(size);    //lang_code2id = new LinkedHashMap<String, Integer>(size);
        for(int id=0; id<size; id++) {

            String code = list_code.get(id);
            LanguageType lang = code2lang.get(code);
            id2lang.put(id, lang);                      //lang_code2id.put(lang.getCode(), id);
        }
        return id2lang;
    }
    
    /** Fills database table 'lang' by data from LanguageType class. */
    private static void fillDB(Connect connect, Map<Integer, LanguageType> id2lang) {
        
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
                String name = StringUtil.underscoreToSpace(rs.getString("name"));
                tp = new TLang(id, lt);
                
                /*if(!lt.getName().equalsIgnoreCase(name)) { // cause: field lang.name is NOT unique, only .code is unique
                    System.err.println("Warning: (wikt_parsed TLang.java get()):: Table 'lang' has unknown language name =" + name +
                            " (language code = " + lt.getCode() + ")");
                }*/
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
     * @param  lt  language to be deleted
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
}
