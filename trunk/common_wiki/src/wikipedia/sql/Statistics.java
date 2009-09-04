/* Statistics.java - Statistics of the source Wiki database.
 *
 * Copyright (c) 2005-2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikipedia.sql;

import java.sql.*;

/** Presents various statistics related to Wikipedia MySQL database */
public class Statistics {
    
    public Statistics() {
    }
        
    /** Gets number of elements in the table: SELECT COUNT(*) FROM table_name.
     *
     * @return -1 if database is not available
     */
    public static int Count(Connect connect, String table_name) {
        Statement s = null;
        ResultSet rs= null;
        int size = 0;
        String str_sql = null;

        if(null==connect || null==connect.conn)
            return -1;
        
        try {
            s = connect.conn.createStatement ();
            str_sql = "SELECT COUNT(*) AS size FROM " + table_name;
            rs = s.executeQuery(str_sql);
            if (rs.next ())
            {
                size = rs.getInt("size");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (Statistics.java Count()): sql='" + str_sql + "' " + ex.getMessage());
        } finally {
            if (rs != null) {
                try { rs.close();
                } catch (SQLException sqlEx) { }
                rs = null;
                }
                if (s != null) {
                    try { s.close();
                    } catch (SQLException sqlEx) { }
                    s = null;
                }
        }
        return size;
    }
    
    
    /** Gets number of articles or categories: <br>
     * SELECT COUNT(*) AS size FROM page WHERE page_namespace=ns
     * 
     *  @param where    additional constraint, e.g. "page_is_redirect=0 AND"
     *
     * @return -1 if database is not available
     */
    private static int CountPageForNamespace(Connect connect, String where, 
                            PageNamespace ns) {
        Statement s = null;
        ResultSet rs= null;
        int size = 0;
        String str_sql = null;

        if(null==connect || null==connect.conn)
            return -1;

        try {
            s = connect.conn.createStatement ();
            str_sql = "SELECT COUNT(*) AS size FROM page WHERE " + where + " page_namespace="+ns.toInt();
            rs = s.executeQuery (str_sql);
            if (rs.next ())
            {
                size = rs.getInt("size");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (Statistics.java CountPageForNamespace()): sql='" + str_sql + "' " + ex.getMessage());
        } finally {
            if (rs != null) {
                try { rs.close();
                } catch (SQLException sqlEx) { }
                rs = null;
                }
                if (s != null) {
                    try { s.close();
                    } catch (SQLException sqlEx) { }
                    s = null;
                }
        }
        return size;
    }
    
    /** Gets number of articles: <br>
     * SELECT COUNT(*) AS size FROM page WHERE page_namespace=0
     */
    public static int CountArticles(Connect connect) {
        return CountPageForNamespace(connect, "", PageNamespace.MAIN);
    }
    
    /** Gets number of articles, which are not redirects: <br>
     * SELECT COUNT(*) AS size FROM page WHERE page_is_redirect=0 AND page_namespace=0
     */
    public static int CountArticlesNonRedirects(Connect connect) {
        return CountPageForNamespace(connect, "page_is_redirect=0 AND", PageNamespace.MAIN);
    }
    
    /** Gets number of categories. */
    public static int CountCategories(Connect connect) {
        return CountPageForNamespace(connect, "", PageNamespace.CATEGORY);
    }
    
    public static int CountLinks(Connect connect) {
        return 0;
        
    }
    
}
