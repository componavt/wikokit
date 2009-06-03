
package wikipedia.sql;

import wikipedia.language.Encodings;
import wikipedia.language.LanguageType;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 

/**
 * 
 * @see com.touchgraph.wikibrowser.parameter.Constants in TGWikiBrowser subproject
 */
public class Connect {
    
    /** stores encoding values for database and user interface */
    public      Encodings  enc;
    public static PageTableBase  page_table;
    public      Connection conn;      // non static variable, because can be used two or more connections 
                                        // (to Russian, English and other wikipedias)
    
    private     String  db_host;
    private     String  db_name;
    private     String  user;
    private     String  pass;
    private     LanguageType lang;  // language of Wiktionary/Wikipedia edition, e.g. Russian in Russian Wiktionary.
    
    
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
    public final static String RUWIKT_DB   = "ruwikt20090122?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    
    // Russian Wiktionary parsed database
    public final static String RUWIKT_PARSED_DB = "ruwikt20090122_parsed?useUnicode=false&characterEncoding=ISO8859_1&autoReconnect=true&useUnbufferedInput=false";
    //public final static String RUWIKT_PARSED_DB = "ruwikt20090122_parsed?useUnicode=true&autoReconnect=true&useUnbufferedInput=false";
    
    
    
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
            String s = "jdbc:mysql://"+db_host+"/"+db_name;
            conn = DriverManager.getConnection("jdbc:mysql://"+db_host+"/"+db_name, user, pass);
            
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
"\nThank you."
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