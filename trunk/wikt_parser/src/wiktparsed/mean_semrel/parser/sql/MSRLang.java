/* MSRLang.java - SQL operations with the table 'lang' in the database 
 * (wikt_mean_semrel) wich contains only word's meanings and semantic relations.
 * It is simplified Wiktionary parsed database based on wikt_parsed database.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiktparsed.mean_semrel.parser.sql;

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

/** Table wikt_mean_semrel.lang contains list of languages: name and ID. */
public class MSRLang {

    /** Unique page identifier. */
    private int id;

    /** Languages of wiki: code and name, e.g. 'ru' and 'Russian'. */
    private LanguageType lang;

    /** Number of meanings (with semantic relations) of words of this language,
     * in the table mean_semrel_XX, where XX is a language code. */
    private int n_meaning;
    // SELECT COUNT(*) FROM mean_semrel_en;

    /** Number of semantic relations (for this language) in the table mean_semrel_XX,
     * where XX is a language code. */
    private int n_sem_rel;
    
    
    /** Map from id to language. It is created from data in the table `lang`,
     * which is created from data in LanguageType.java.*/
    private static Map<Integer, MSRLang> id2lang;

    /** Map from language to id.*/
    private static Map<LanguageType, Integer> lang2id;
    
    private final static MSRLang[] NULL_MSRLANG_ARRAY = new MSRLang[0];
    
    public MSRLang(int _id,LanguageType _lang,int _n_meaning,int _n_sem_rel) {
        id      = _id;
        lang    = _lang;
        n_meaning = _n_meaning;
        n_sem_rel= _n_sem_rel;
    }
    
    /** Gets unique ID of the language. */
    public int getID() {
        return id;
    }
    
    /** Gets language. */
    public LanguageType getLanguage() {
        return lang;
    }

    /** Gets number of meanings (with semantic relations) of words 
     * in this language. <br><br>
     * SELECT COUNT(*) FROM mean_semrel_en;
     */
    public int getNumberMeanings() {
        return n_meaning;
    }

    /** Gets number of semantic relations in this language.
     */
    public int getNumberSemanticRelations() {
        return n_sem_rel;
    }

    /** Gets language ID from the table 'lang'.<br><br>
     * 
     * REM: the function 'createFastMaps()' should be run at least once,
     * before this function execution.
     */
    public static int getIDFast(LanguageType lt) {
        if(null == lang2id || 0 == lang2id.size()) {
            System.err.println("Error (wikt_mean_semrel MSRLang.getIDFast()):: What about calling 'createFastMaps()' before?");
            return -1;
        }
        if(null == lt) {
            System.err.println("Error (wikt_mean_semrel MSRLang.getIDFast()):: argument LanguageType is null");
            return -1;
        }
        
        Integer result = lang2id.get(lt);
        if(null == result) {
            System.out.println("Warning (wikt_mean_semrel MSRLang.getIDFast()):: map lang2id don't have this id. Are you adding new lang codes in time of parsing?");
            return -1;
        }
        return result;
    }

    /** Gets language by ID from the table 'lang'.<br><br>
     *
     * REM: the functions createFastMaps() should be run at least once,
     * before this function execution.
     */  
    public static MSRLang getMSRLangFast(int id) {
        if(null == id2lang) {
            System.err.println("Error (wikt_mean_semrel MSRLang.getMSRLangFast()):: What about calling 'createFastMaps()' before?");
            return null;
        }
        if(id <= 0) {
            System.err.println("Error (wikt_mean_semrel MSRLang.getMSRLangFast()):: argument id <=0, id = "+id);
            return null;
        }
        return id2lang.get(id);
    }

    /** Gets language MSRLang by LanguageType from the table 'lang'.<br><br>
     *
     * REM: the functions createFastMaps() should be run at least once,
     * before this function execution.
     */
    public static MSRLang get(LanguageType lt) {
        return getMSRLangFast(getIDFast(lt));
    }

    /** Gets the map from language to ID (ID in the table 'lang').
     *
     * REM: the functions createFastMaps() should be run at least once,
     * before this function execution.
     */
    public static Map<LanguageType, Integer> getAllLanguages() {
        return lang2id;
    }
    
    /** Read all records from the table 'lang',
     * fills the internal map from a table ID to a language.
     * 
     * REM: during a creation of Wiktionary parsed database
     * the functions recreateTable() should be called (before this function).
     */
    public static void createFastMaps(Connect connect) {

        System.out.println("Loading table `lang`...");
        
        MSRLang[] langs = getAllLang(connect);
        int size = langs.length;
        if(langs.length != LanguageType.size()) {
            System.out.println("Warning (wikt_mean_semrel MSRLang.createFastMaps()):: LanguageType.size (" + LanguageType.size()
                    + ") is not equal to size of table 'lang'("+ size +"). Is the database outdated?");
        }

        if(null != id2lang && id2lang.size() > 0)
            id2lang.clear();
        if(null != lang2id && lang2id.size() > 0)
            lang2id.clear();
        
        id2lang  = new LinkedHashMap<Integer, MSRLang>(size);
        lang2id  = new LinkedHashMap<LanguageType, Integer>(size);
        
        for(MSRLang t : langs) {
            id2lang.put(t.getID(), t);
            lang2id.put(t.getLanguage(), t.getID());
        }
    }

    /** Gets all records from the table 'lang'.
     */
    private static MSRLang[] getAllLang(Connect connect) {

        int size = Statistics.Count(connect, "lang");
        if(0==size) {
            System.err.println("Error (wikt_mean_semrel MSRLang.getAllLang()):: The table `lang` is empty!");
            return NULL_MSRLANG_ARRAY;
        }
        
        List<MSRLang>lang_list = new ArrayList<MSRLang>(size);

        Map<String, LanguageType> ll = LanguageType.getAllLanguages();
        for(LanguageType l : ll.values()) {
            MSRLang t = get(connect, l);
            if(null != t)
                lang_list.add(t);
        }
        return( (MSRLang[])lang_list.toArray(NULL_MSRLANG_ARRAY) );
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
        Map<Integer, LanguageType> id2lang_local = fillLocalMaps();
        UtilSQL.deleteAllRecordsResetAutoIncrement(connect, "lang");
        fillDB(connect, id2lang_local);
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

        Map<Integer, LanguageType> id2lang_local = new LinkedHashMap<Integer, LanguageType>(size);    //lang_code2id = new LinkedHashMap<String, Integer>(size);
        for(int id=0; id<size; id++) {

            String code = list_code.get(id);
            LanguageType lang = code2lang.get(code);
            id2lang_local.put(id, lang);
        }
        return id2lang_local;
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

    /** Calculates (1) number of meanings with semantic relations
     * in the table mean_semrel_XX, stores statistics into fields:
     * (1) lang.n_meaning, (2) lang.n_sem_rel for each language. <br><br>
     *
     * REM: this func should be called after the a creation of Wiktionary
     * wikt_mean_semrel database, and the tables should be filled with data.
     *
     * @param native_lang       native language in the Wiktionary,
     *                          e.g. Russian language in Russian Wiktionary
     */
    public static void calcMeanSemrelStatistics(Connect connect,
                            LanguageType native_lang) {

        System.out.println("Fill table `lang` by statistics from mean_semrel_XX tables...");
        
        System.exit(0);
        // todo...

        /*
        // foreign languages statistics
        for(LanguageType lt : lang2id.keySet()) {
            if(native_lang != lt) {

                int n_meaning = .countNumberOfMeaningsWithSemanticRelations(connect, lt);
                int n_sem_rel = .countSemanticRelations(connect, lt);

                update(connect, lt, n_meaning, n_sem_rel);
            }
        }

        int n_native_POS = IndexNative.countNumberPOSWithDefinition(connect);
        update(connect, native_lang, n_native_POS, 0);
         * 
         */
    }

    
    /** Inserts record into the table 'lang'.
     *
     * INSERT INTO lang (code,name,n_meaning,n_sem_rel) VALUES ("ru","Russian", 12, 13);
     *
     * @param code  two (or more) letter language code, e.g. 'en', 'ru'
     * @param name  language name, e.g. 'English', 'Russian'
     */
    public static void insert (Connect connect,String code,String name,
                                int n_meaning,int n_sem_rel) {
        
        StringBuilder str_sql = new StringBuilder();
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("INSERT INTO lang (code,name,n_meaning,n_sem_rel) VALUES (\"");
                str_sql.append(code);
                str_sql.append("\",\"");
                String safe_title = StringUtil.spaceToUnderscore(
                                    StringUtil.escapeChars(name));
                str_sql.append(safe_title);
                str_sql.append("\",");

                str_sql.append(n_meaning);
                str_sql.append(",");
                str_sql.append(n_sem_rel);
                str_sql.append(")");

                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (TLang.insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }

    /** Updates values (n_meaning, n_sem_rel) in the table 'lang'. <br><br>
     *
     * UPDATE lang SET n_meaning=11, n_sem_rel=13 WHERE code="en";
     *
     * @param lang  language, the corresponded record in the table to be updated
     */
    public static void update (Connect connect,LanguageType lang,
                                int n_meaning,int n_sem_rel) {
        
        StringBuilder str_sql = new StringBuilder();
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                // UPDATE lang SET n_meaning=11, n_sem_rel=13 WHERE code="en"
                str_sql.append("UPDATE lang SET n_meaning=");
                str_sql.append(n_meaning);
                str_sql.append(", n_sem_rel=");
                str_sql.append(n_sem_rel);
                str_sql.append(" WHERE code=\"");
                str_sql.append(lang.getCode());
                str_sql.append("\"");
                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (MSRLang.update()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }

    /** Selects row from the table 'lang' by a language code.
     *
     *  SELECT id,name,n_meaning,n_sem_rel FROM lang WHERE code="ru";
     *
     * @param  lt.lang_code  language code
     * @return null if the language code is absent in the table 'lang'
     */
    public static MSRLang get (Connect connect,LanguageType lt) {

        StringBuilder str_sql = new StringBuilder();
        MSRLang       tp = null;

        if(null == lt) return null;
        String lang_code = lt.getCode();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT id,name,n_meaning,n_sem_rel FROM lang WHERE code=\"");
                str_sql.append(lang_code);
                str_sql.append("\"");

                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        //String name = StringUtil.underscoreToSpace(rs.getString("name"));

                        tp = new MSRLang( rs.getInt("id"), lt,
                                        rs.getInt("n_meaning"),
                                        rs.getInt("n_sem_rel"));

                        /*if(!lt.getName().equalsIgnoreCase(name)) { // cause: field lang.name is NOT unique, only .code is unique
                            System.err.println("Warning: (wikt_mean_semrel TLang.java get()):: Table 'lang' has unknown language name =" + name +
                                    " (language code = " + lt.getCode() + ")");
                        }*/
                    } else {
                            System.err.println("Error: (wikt_mean_semrel MSRLang.java get()):: The language code '" + lang_code + "' is absent in the table 'lang'.");
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (MSRLang.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return tp;
    }

    /** Deletes row from the table 'lang' by the language code.<br><br>
     *
     *  DELETE FROM lang WHERE code="ru";
     *
     * @param  lt  language to be deleted
     */
    public static void delete (Connect connect,LanguageType lt) {

        StringBuilder str_sql = new StringBuilder();

        if(null == lt) return;
        String lang_code = lt.getCode();

        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("DELETE FROM lang WHERE code=\"");
                str_sql.append(lang_code);
                str_sql.append("\"");
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (MSRLang.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }    
}
