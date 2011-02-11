/* Connect.java - connection to a database functions, the list of databases.
 *
 * Copyright (c) 2005-2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU General Public License.
 */

package wikipedia.sql;

import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import wikipedia.language.Encodings;
import wikipedia.language.LanguageType;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 

import java.sql.*;
import wikipedia.util.FileWriter;

/** Connection to a database functions, the list of available databases.
 * 
 * @see com.touchgraph.wikibrowser.parameter.Constants in TGWikiBrowser subproject
 */
public class Connect {
    
    /** stores encoding values for database and user interface */
    public      Encodings  enc;
    public static PageTableBase  page_table;
    public      Connection conn;      // non static variable, because can be used two or more connections 
                                        // (to Russian, English and other wikipedias)

    /** Database host (if !is_sqlite). */
    private     String  db_host;
    private     String  db_name;
    private     String  user;
    private     String  pass;
    
    /** Language of Wiktionary/Wikipedia edition,
     * e.g. Russian in Russian Wiktionary. */
    private     LanguageType lang;

    /** File path to the SQLite database file (if is_sqlite). */
    private     String  sqlite_filepath;

    /** It's true for SQLite and false for MySQL. */
    private     boolean is_sqlite;
    
    
    // debug constant parameters
    
    // English Wikipedia, localhost
    // use: connect.Open(Connect.WP_HOST, Connect.WP_DB, Connect.WP_USER, Connect.WP_PASS);
    public final static String WP_HOST      = "localhost";
    public final static String WP_USER      = "javawiki";
    public final static String WP_PASS      = "";
    public final static String WP_DB        = "enwiki7?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    //public final static String WP_DB      = "enwiki7?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false";
    //public final static String WP_DB      = "enwiki7?useUnicode=true&autoReconnect=true&useUnbufferedInput=false";
    //public final static String WP_DB      = "enwiki?useUnicode=true&autoReconnect=true&useUnbufferedInput=false";
    
    // Simple English Wikipedia
    // use: connect_simple.Open(Connect.WP_HOST,Connect.WP_SIMPLE_DB,   Connect.WP_USER,    Connect.WP_PASS);
    public final static String WP_SIMPLE_DB   = "simplewiki20090119?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    //public final static String WP_SIMPLE_DB = "simplewiki20070909?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    //public final static String WP_SIMPLE_DB = "simplewiki?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false";
    //public final static String WP_SIMPLE_DB = "simplewiki?useUnicode=true&autoReconnect=true&useUnbufferedInput=false";
    //public final static String WP_SIMPLE_DB = "simplewiki?autoReconnect=true&useUnbufferedInput=false";
    
    // Russian Wikipedia
    // use: connect_ru.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,Connect.WP_RU_USER,Connect.WP_RU_PASS);
    public final static String WP_RU_HOST   = "localhost";
    public final static String WP_RU_USER   = "javawiki";
    public final static String WP_RU_PASS   = "";
    //public final static String WP_RU_DB     = "ruwiki20080220?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    public final static String WP_RU_DB   = "ruwiki20070920?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    
    
    // Russian Wiktionary
    // use: connect_ruwikt.Open(Connect.WP_RU_HOST,Connect.WP_RU_DB,Connect.WP_RU_USER,Connect.WP_RU_PASS);
    public final static String RUWIKT_HOST   = "localhost";
    public final static String RUWIKT_USER   = "javawiki";
    public final static String RUWIKT_PASS   = "";
    //public final static String RUWIKT_DB   = "ruwikt20100817?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    public final static String RUWIKT_DB   = "ruwikt20101101?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    
    // Russian Wiktionary parsed database
    //public final static String RUWIKT_PARSED_DB = "ruwikt20100817_parsed?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    public final static String RUWIKT_PARSED_DB = "ruwikt20101101_parsed?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";

    // public final static String RUWIKT_SQLITE = "C:/w/bin/ruwikt20090707.sqlite";
    //public final static String RUWIKT_SQLITE = "ruwikt20100817.sqlite";
    public final static String RUWIKT_SQLITE = "ruwikt20101101.sqlite";

    // English Wiktionary database
    // use: connect_ruwikt.Open(Connect.WP_EN_HOST,Connect.WP_EN_DB,Connect.WP_EN_USER,Connect.WP_EN_PASS);
    public final static String ENWIKT_HOST  = "localhost";
    public final static String ENWIKT_USER  = "javawiki";
    public final static String ENWIKT_PASS  = "";
    public final static String ENWIKT_DB = "enwikt20101030?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";

    // English Wiktionary parsed database
    public final static String ENWIKT_PARSED_DB = "enwikt20101030_parsed?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";

    //public final static String ENWIKT_SQLITE = "C:/w/bin/enwikt20101030.sqlite";
    public final static String ENWIKT_SQLITE = "enwikt20101030.sqlite";

    
    // IDF (inverse document frequency) database
    public final static String IDF_EN_HOST  = "localhost";
    public final static String IDF_EN_DB    = "idfenwiki7?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false";
    public final static String IDF_EN_USER  = "javawiki";
    public final static String IDF_EN_PASS  = "";
    
    // IDF Simple Wikipedia
    // use idf_conn.Open(Connect.IDF_SIMPLE_HOST, Connect.IDF_SIMPLE_DB, Connect.IDF_SIMPLE_USER, Connect.IDF_SIMPLE_PASS);
    public final static String IDF_SIMPLE_HOST  = "localhost";
    public final static String IDF_SIMPLE_USER  = "javawiki";
    public final static String IDF_SIMPLE_PASS  = "";
    public final static String IDF_SIMPLE_DB    = "idfsimplewiki20080214?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false";
    //public final static String IDF_SIMPLE_DB  = "idfsimplewiki20070909?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false";
    
    // IDF Russian Wikipedia
    // use: idf_conn.Open(Connect.IDF_RU_HOST, Connect.IDF_RU_DB, Connect.IDF_RU_USER, Connect.IDF_RU_PASS);
    public final static String IDF_RU_HOST  = "localhost";
    public final static String IDF_RU_USER  = "javawiki";
    public final static String IDF_RU_PASS  = "";
    //public final static String IDF_RU_DB    = "idfruwiki20080220?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false";
    public final static String IDF_RU_DB    = "idfruwiki20070920?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useUnbufferedInput=false";

    /** Gets language of Wiktionary/Wikipedia edition, main or native language,
     * e.g. Russian in Russian Wiktionary.
     */
    public LanguageType getNativeLanguage() {
        return lang;
    }

    public Connect() {
        enc        = new Encodings();
        page_table = new PageTableBase();
    }

    /** Gets database name (MySQL), or file name (SQLite). */
    public String getDBName() {
        if(is_sqlite)
            return sqlite_filepath;

        // if MySQL
        int question_sign = db_name.indexOf('?');
        if(-1 == question_sign)
            return db_name;
        return db_name.substring(0, question_sign);
    }

    /** True for MySQL and false for SQLite databases. */
    public boolean isMySQL() {
        return !is_sqlite;
    }


    /** Opens SQLite connection.
     *
     * @param _sqlite_filepath File path to the SQLite file,
     *                      e.g. "jdbc:sqlite:C:/w/bin/ruwikt20090707.sqlite"
     * @param _lang language of Wiktionary/Wikipedia edition, main or 
     *               native language, e.g. Russian in Russian Wiktionary.
     */
    public void OpenSQLite(String _sqlite_filepath, LanguageType _lang, boolean brelease) {

        lang    = _lang;
        sqlite_filepath = _sqlite_filepath;
        is_sqlite = true;
        OpenSQLite(brelease, sqlite_filepath);
    }

    /** Opens MySQL connection. 
     *
     * @param _db_host
     * @param _db_name
     * @param _user
     * @param _pass
     * @param _lang language of Wiktionary/Wikipedia edition, main or native language, e.g. Russian in Russian Wiktionary.
     */
    public void Open(String _db_host, String _db_name, String _user, String _pass,
                    LanguageType _lang) {

        lang    = _lang;
        db_host = _db_host;
        db_name = _db_name;
        user    = _user;
        pass    = _pass;
        is_sqlite = false;
        Open();
    }
    /** Reopens previous MySQL connection with previous parameters. */
    public void ReOpen() {
        if(null != db_host && 0 < db_host.length()) {
            System.out.println("Try reopen database."); 
            Close();
            Open();
        }
    }
    
    /** Reopens previous MySQL connection with previous parameters 
     * if connection is invalid.
     */
    public void ReOpenIfInvalid() {
        
        try {
            if(null != conn && !conn.isValid(0)) {
                System.out.println("Connection is invalid. Reopened."); 
                ReOpen();
            }
        }
        catch (SQLException ex) {
            System.err.println("Exception: " + ex.getMessage());
            System.out.println("SQL State: " + ex.getSQLState());
            System.out.println("Vendor Error: " + ex.getErrorCode()); 
            System.err.printf("Input parameters: db_host=%s, db_name=%s, user=%s\n", 
                    db_host, db_name, user);
        }
    }
    
    private void Open() { 
        conn = null;
        String classname = "com.mysql.jdbc.Driver";
        try {
            Class.forName(classname).newInstance(); 
            conn = DriverManager.getConnection("jdbc:mysql://"+db_host+"/"+db_name, user, pass);

            // jdbc:mysql://localhost/test?user=testuser&password=testpass
//            String s = "jdbc:mysql://"+db_host+"/"+db_name +"&user="+ user +"&password="+ pass;
//            conn = DriverManager.getConnection(s);

            // ?autoReconnect=true&useUnbufferedInput=false
            //conn = DriverManager.getConnection("jdbc:mysql://"+db_host+"/"+db_name+"?useUnicode=true&characterEncoding=UTF-8", user, pass);
            //conn = DriverManager.getConnection("jdbc:mysql://localhost/"+db_name, user, pass);
            //conn = DriverManager.getConnection("jdbc:mysql://hdd80.net.com/"+db_name, user, pass);
            //System.out.println ("Database connection established");
        } 
        catch (ClassNotFoundException e) {
            System.err.println("Couldn't find class " + classname + ". Message: " + e.getMessage());
        }
        catch (InstantiationException ie) {  
          System.err.println("Couldn't instantiate an object of type " + classname + ". Message: " + ie.getMessage());
        }  
        catch (IllegalAccessException ia) {  
          System.err.println("Couldn't access class " + classname + ". Message: " + ia.getMessage());
        }
        catch (SQLException ex) {
            System.err.println("Exception: " + ex.getMessage());
            System.err.println("SQL State: " + ex.getSQLState());
            System.err.println("Vendor Error: " + ex.getErrorCode());
            System.err.printf("Input parameters: db_host=%s, db_name=%s, user=%s\n", 
                    db_host, db_name, user);
            System.err.println(
"Recommendation:" +
"\n1. Add the file mysql-connector-java-X.X.XX-bin.jar to Java CLASS_PATH," +
"\nor copy it to the current directory;" +
"\n2. Add the user (e.g. 'javawiki') to MySQL database (e.g. 'enwiki'):" +
"\n  mysql> GRANT ALL PRIVILEGES ON enwiki.* TO javawiki@'your_host_name';" +
"\n  mysql> FLUSH PRIVILEGES;" +
"\n3. If a database name and user name are differ from the default values," +
"\n(javawiki and enwiki) then update fields 'db_name_en' and 'user_en'" +
"\nin /home/user_name/.wikibrowser.server.props" +
"\nSee more information at https://sourceforge.net/projects/synarcher" +
"\n" +
"\n4. Create the parsed Wiktionary database, e.g. enwikt20091228_parsed;" +
"\n  mysql> use enwikt20091228_parsed;" +
"\n  mysql> SOURCE wikokit\\wikt_parser\\doc\\wikt_parsed_empty.sql " +
"\nThank you."
                    );
        }
    }

    public static void testSQLite() {

        // register the driver
        String sDriverName = "org.sqlite.JDBC";
        try
        {
            Class.forName(sDriverName);
        }
        catch(Exception e)
        {
            System.err.println(e);
        }

        // now we set up a set of fairly basic string variables to use in the body of the code proper
        String sTempDb = "hello.db";
        String sJdbc = "jdbc:sqlite"; String sDbUrl = sJdbc + ":" + sTempDb;
        // which will produce a legitimate Url for SqlLite JDBC :
        // jdbc:sqlite:hello.db

        int iTimeout = 30;
        String sMakeTable = "CREATE TABLE dummy (id numeric, response text)";
        String sMakeInsert = "INSERT INTO dummy VALUES(1,'Hello from the database')";
        String sMakeSelect = "SELECT response from dummy";
        try
        { // create a database connection
            Connection conn = DriverManager.getConnection(sDbUrl);
            Statement stmt = conn.createStatement();
            stmt.setQueryTimeout(iTimeout);
            stmt.executeUpdate( sMakeTable );
            stmt.executeUpdate( sMakeInsert ); ResultSet rs = stmt.executeQuery(sMakeSelect);
            while(rs.next())
            {
                String sResult = rs.getString("response");
                System.out.println(sResult);
            }
        }
        catch(SQLException e)
        {
            // connection failed.
            System.err.println(e);
        }
    }

    /** 
     * 
     * @param brelease If true, then SQLite database will be extracted 
     * from the jar-file and stored to the directory user.dir 
     * (Add jar-file with SQLite database to the project);
     * If false, then SQLite database has path ./sqlite/SQLiteFile
     * 
     * @param sqlite_filename file name of the SQLite database
     * @return path to SQLite file in .jar or in local folder of the project
     */
    private String getFilepathToSQLiteDatabase(boolean brelease, String sqlite_filename) {

        String result_filepath;

            if( brelease ) {
                String dbfile_in_jar = sqlite_filename;
                String target_dir = System.getProperty("user.home") + File.separator + ".wiwordik";
                result_filepath = target_dir+File.separator + dbfile_in_jar;

                if(!FileWriter.existsFile(result_filepath)) {
                    Object resource = this;
                    boolean success = true;
                    try {                    // creates ~/.wiwordik/
                        success = FileWriter.retrieveBinaryFileFromJar(dbfile_in_jar, target_dir, resource);
                    } catch (Exception ex) {
                        Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(!success)
                        System.err.println("Error in Connect::OpenSQLite() Couldn't retrieve SQLite database from .jar file " + dbfile_in_jar);
                }
            } else {
                result_filepath = "sqlite"+File.separator + sqlite_filename;
            }
        return result_filepath;
    }


    private void OpenSQLite(boolean brelease, String sqlite_filename) {
        conn = null;
        String classname = "org.sqlite.JDBC";

        try {
            Class.forName(classname);
            //String s = "jdbc:sqlite:C:/w/bin/ruwikt20090707.sqlite";      // C:\w\bin\sample_db.sql
            //String s = "jdbc:sqlite:sample.db";
            //String s = "jdbc:sqlite:" + sqlite_filepath;

            String result_filepath = getFilepathToSQLiteDatabase(brelease, sqlite_filename);

            String s = "jdbc:sqlite:" + result_filepath;   //s = "jdbc:sqlite:/" + result_filepath;
            System.out.println("DriverManager.getConnection(" + s + ")");
            conn = DriverManager.getConnection(s);

            //conn = DriverManager.getConnection("jdbc:sqlite://"+db_host+"/"+db_name, user, pass);
            // ?autoReconnect=true&useUnbufferedInput=false
            //conn = DriverManager.getConnection("jdbc:mysql://"+db_host+"/"+db_name+"?useUnicode=true&characterEncoding=UTF-8", user, pass);
            //conn = DriverManager.getConnection("jdbc:mysql://localhost/"+db_name, user, pass);
            //System.out.println ("Database connection established");
        }
        catch (ClassNotFoundException e) {
            System.err.println("Couldn't find class " + classname + ". Message: " + e.getMessage());
        }
        /*catch (InstantiationException ie) {
          System.err.println("Couldn't instantiate an object of type " + classname + ". Message: " + ie.getMessage());
        }
        catch (IllegalAccessException ia) {
          System.err.println("Couldn't access class " + classname + ". Message: " + ia.getMessage());
        }*/
        catch (SQLException ex) {
            System.err.println("Exception: " + ex.getMessage());
            System.err.println("SQL State: " + ex.getSQLState());
            System.err.println("Vendor Error: " + ex.getErrorCode());
            System.err.printf("Input parameters: SQLite filename=%s\n", sqlite_filename);
            System.err.println(
                "Recommendation: .. todo .."
                    );
        }
    }

    public void Close()
    {
        if (conn != null) {
            try {
                conn.close ();
                //System.out.println ("Database connection terminated");
            }
            catch (Exception e) { /* ignore close errors */ }
        }
    }
}