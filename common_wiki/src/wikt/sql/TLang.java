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
import wikt.sql.index.IndexForeign;
import wikt.sql.index.IndexNative;

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

    /** Number of foreign parts of speech (POS) in the table index_XX,
     * which have its own articles in Wiktionary,
     * where XX is a language code. */
    private int n_foreign_POS;
    // SELECT COUNT(*) FROM index_en WHERE native_page_title is NULL;

    /** Number of translation pairs in the table index_XX,
     * where XX is a language code. */
    private int n_translations;
    // SELECT COUNT(*) FROM index_en WHERE native_page_title is not NULL;
    
    /** Map from id to language. It is created from data in the table `lang`,
     * which is created from data in LanguageType.java.*/
    private static Map<Integer, TLang> id2lang;

    /** Map from language to id.*/
    private static Map<LanguageType, Integer> lang2id;

    private final static TLang[] NULL_TLANG_ARRAY = new TLang[0];
    
    public TLang(int _id,LanguageType _lang,int _n_foreign_POS,int _n_translations) {
        id      = _id;
        lang    = _lang;
        n_foreign_POS = _n_foreign_POS;
        n_translations= _n_translations;
    }
    
    /** Gets unique ID of the language. */
    public int getID() {
        return id;
    }
    
    /** Gets language. */
    public LanguageType getLanguage() {
        return lang;
    }

    /** Gets number of parts of speech (POS) in this language. <br><br>
     * SELECT COUNT(*) FROM index_en WHERE native_page_title is NULL;
     */
    public int getNumberPOS() {
        return n_foreign_POS;
    }

    /** Gets number of translation into this language. <br><br>
     * SELECT COUNT(*) FROM index_en WHERE native_page_title is not NULL;
     */
    public int getNumberTranslations() {
        return n_translations;
    }

    /** Gets language ID from the table 'lang'.<br><br>
     * 
     * REM: the function 'createFastMaps()' should be run at least once,
     * before this function execution.
     */
    public static int getIDFast(LanguageType lt) {
        if(null == lang2id || 0 == lang2id.size()) {
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

    /** Gets the map from language to ID (ID in the table 'lang').
     *
     * REM: the functions createFastMaps() should be run at least once,
     * before this function execution.
     */
    public static Map<LanguageType, Integer> getAllLanguages() {
        return lang2id;
    }

    
    /** Parses and extracts language codes from the string 'lang_codes'. 
     * 
     * @return empty array if language codes were not extracted
     */
    public static TLang[] parseLangCode(String lang_codes) {

        String s = lang_codes.trim();
        if(0 == s.length())
            return NULL_TLANG_ARRAY;

        List<TLang>tlang_list = new ArrayList<TLang>();
        
        String[] words = s.split("\\s");
        for(String w : words) {
            if(LanguageType.has(w))
                tlang_list.add( TLang.get( LanguageType.get(w) ));
        }
        if(tlang_list.isEmpty())
            return NULL_TLANG_ARRAY;
            
        return( (TLang[])tlang_list.toArray(NULL_TLANG_ARRAY) );
    }

    /** Compares language codes extracted from the string 'str_lang2' and
     * array of language codes 'tlang1'. */
    public static boolean isEquals(TLang[] tlang1, String str_lang2) {

        TLang tlang2[] = parseLangCode(str_lang2);

        if(tlang1.length != tlang2.length)
            return false;

        if(tlang1.length == 0)
            return true;

        for(TLang l1 : tlang1) {
            boolean bfound = false;
            for(TLang l2 : tlang2) {
                if(l1 == l2) {
                    bfound = true;
                    break;
                }
            }

            if(!bfound)
                return false;
        }
        
        return true;
    }
    
    /** Read all records from the table 'lang',
     * fills the internal map from a table ID to a language.
     * 
     * REM: during a creation of Wiktionary parsed database
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

            insert (connect, lang.getCode(), lang.getName(), 0, 0);     //insert (connect, lang.getCode(), lang.getCode());
            // insert (connect, lang.getCode(), connect.enc.EncodeFromJava(lang.getName()));
        }
    }

    /** Calculates (1) number of foreign parts of speech (POS) and translation pairs
     * in the table index_XX, stores statistics into fields:
     * (1) n_foreign_POS, (2) n_translations for each language (except native). <br><br>
     *
     * For native language calculates only  (1) n_foreign_POS by data from
     * the table 'index_native'. (In really it's a number of native POS.)
     *
     * REM: this func should be called after the a creation of Wiktionary
     * parsed database, and the tables should be filled with data.
     *
     * @param native_lang       native language in the Wiktionary,
     *                          e.g. Russian language in Russian Wiktionary
     */
    public static void calcIndexStatistics(Connect connect,
                            LanguageType native_lang) {

        System.out.println("Fill table `lang` by statistics from index_XX tables...");

        // foreign languages statistics
        for(LanguageType lt : lang2id.keySet()) {
            if(native_lang != lt) {

                int n_foreign_POS = IndexForeign.countNumberOfForeignPOS(connect, lt);
                int n_translations = IndexForeign.countTranslations(connect, lt);

                update(connect, lt, n_foreign_POS, n_translations);
            }
        }

        // For native language calculates only  (1) n_foreign_POS by data from
        // the table 'index_native'. (In really it's a number of native POS.)

        int n_native_POS = IndexNative.countNumberPOSWithDefinition(connect);
        update(connect, native_lang, n_native_POS, 0);
    }

    
    /** Inserts record into the table 'lang'.
     *
     * INSERT INTO lang (code,name,n_foreign_POS,n_translations) VALUES ("ru","Russian", 12, 13);
     *
     * @param code  two (or more) letter language code, e.g. 'en', 'ru'
     * @param name  language name, e.g. 'English', 'Russian'
     */
    public static void insert (Connect connect,String code,String name,
                                int n_foreign_POS,int n_translations) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO lang (code,name,n_foreign_POS,n_translations) VALUES (\"");
            str_sql.append(code);
            str_sql.append("\",\"");
            String safe_title = StringUtil.spaceToUnderscore(
                                StringUtil.escapeChars(name));
            str_sql.append(safe_title);
            str_sql.append("\",");

            str_sql.append(n_foreign_POS);
            str_sql.append(",");
            str_sql.append(n_translations);
            str_sql.append(")");
            
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLang.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    /** Updates values (n_foreign_POS, n_translations) in the table 'lang'. <br><br>
     *
     * UPDATE lang SET n_foreign_POS=11, n_translations=13 WHERE code="en";
     *
     * @param lang  language, the corresponded record in the table to be updated
     */
    public static void update (Connect connect,LanguageType lang,
                                int n_foreign_POS,int n_translations) {
        Statement   s = null;
        StringBuilder str_sql = new StringBuilder();
        
        try
        {
            s = connect.conn.createStatement ();
            // UPDATE lang SET n_foreign_POS=11, n_translations=13 WHERE code="en"
            str_sql.append("UPDATE lang SET n_foreign_POS=");
            str_sql.append(n_foreign_POS);
            str_sql.append(", n_translations=");
            str_sql.append(n_translations);
            str_sql.append(" WHERE code=\"");
            str_sql.append(lang.getCode());
            str_sql.append("\"");
            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (TLang.update()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    /** Selects row from the table 'lang' by a language code.
     *
     *  SELECT id,name,n_foreign_POS,n_translations FROM lang WHERE code="ru";
     *
     * @param  lang_code  title of Wiktionary article
     * @return null if a language code is absent in the table 'lang'
     */
    public static TLang get (Connect connect,LanguageType lt) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TLang       tp = null;

        if(null == lt) return null;
        String lang_code = lt.getCode();
        
        try {
            s = connect.conn.createStatement ();

            str_sql.append("SELECT id,name,n_foreign_POS,n_translations FROM lang WHERE code=\"");
            str_sql.append(lang_code);
            str_sql.append("\"");
            
            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                //String name = StringUtil.underscoreToSpace(rs.getString("name"));
                
                tp = new TLang( rs.getInt("id"), lt,
                                rs.getInt("n_foreign_POS"),
                                rs.getInt("n_translations"));
                
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
        StringBuilder str_sql = new StringBuilder();

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
