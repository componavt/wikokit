/*
 * Categorylinks.java
 *
 * Read more about categories in wikipedia:
 *  http://meta.wikimedia.org/wiki/Help_talk:Category
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikokit.base.wikipedia.sql;

import wikokit.base.wikipedia.util.StringUtil;
import wikokit.base.wikipedia.language.Encodings;
import java.sql.*;
import java.util.*;

public class Categorylinks {
    
    //public int      cl_from;    // stores the cur_id of the article where the link was placed
    //public String   cl_to;      // stores the name (excluding namespace prefix) of the desired category
    // public String   cl_sortkey; // stores the title by which the page should be sorted in a cateogy list (ORDER BY cl_sortkey)
    // public int   cl_timestamp;  // cl_timestamp marks when the link was last added

    private final static String[] NULL_STRING_ARRAY = new String[0];

    /** Count the total number of categories in the database.
     * @return -1 if database is not available
     */
    public static int countCategoryLinks(Connect connect) {
        Statement   s = null;
        ResultSet   rs= null;
        int         size = 0;
        String      str_sql = null;

        if(null==connect || null==connect.conn)
            return -1;

        try {
            s = connect.conn.createStatement ();
            str_sql = "SELECT COUNT(*) AS size FROM categorylinks";
            rs = s.executeQuery (str_sql);
            if (rs.next ())
            {
                size = rs.getInt("size");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (Categorylinks.java countCategoryLinks()):: sql='" + str_sql + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return size;
    }
    
    
    /** Gets ID of articles which have the category titled 'category_title'. 
     *  SQL:
     *  SELECT cl_from FROM categorylinks WHERE cl_to="Folklore";
     */
    public static List<Integer> getArticlesIDSubcategoryIDByCategoryTitle(
                                    Connect connect,
                                    String category_title) {
        if(null==connect || null==connect.conn)
            return null;
        
        List<Integer> id = new ArrayList<Integer>();        
        Statement   s = null;
        ResultSet   rs= null;
        sb.setLength(0);
        try {
            s = connect.conn.createStatement ();
            sb.append("SELECT cl_from FROM categorylinks WHERE cl_to=\"");
            
            String safe_title = StringUtil.spaceToUnderscore(
                                StringUtil.escapeChars(category_title));
            safe_title = connect.enc.EncodeToDB(safe_title);
            sb.append(safe_title);
            sb.append("\"");
            rs = s.executeQuery (sb.toString());
            while (rs.next ()) {
                id.add( rs.getInt("cl_from") );
            }
            
        } catch(SQLException ex) {
            System.err.println("SQLException (Categorylinks.java getArticlesIDSubcategoryIDByCategoryTitle()):: sql='" + sb.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        
        return id;
    }
    
    
    
    private static StringBuffer sb = new StringBuffer(75);
    
    //static int max_len_sql = 0;
    /** Get categories' names of the article by article ID.
     *  @param  cl_from     article id (cur_id)
     *  Remark: 
     *      1) use in SQL:      String str_utf8   = Encodings.FromTo(str_latin1, "ISO8859_1", "UTF8");
     *      2) use in output    String str_latin1 = Encodings.FromTo(str_utf8, "UTF8", "ISO8859_1");
     */
    public static String[] GetCategoryTitleByArticleID(Connect connect, int cl_from) {
             // old title: GetCategory
        String[]    categories = null;
        Statement   s = null;
        ResultSet   rs= null;
        int         size, i;

        if(null==connect || null==connect.conn)
            return NULL_STRING_ARRAY;
            
        sb.setLength(0);
        try {
            s = connect.conn.createStatement ();
            //str_sql = "SELECT COUNT(cl_to) AS size FROM categorylinks WHERE cl_from=" + cl_from;
            sb.append("SELECT COUNT(cl_to) AS size FROM categorylinks WHERE cl_from=");
            sb.append(cl_from);
            
            /*if(max_len_sql < str_sql.length()) {
                            max_len_sql = str_sql.length();
                            System.out.println("GetCategory max_len_sql="+max_len_sql);
            }*/
            
            rs = s.executeQuery (sb.toString());
            if (rs.next ())
            {
                size = rs.getInt("size");
                if (0 < size) {
                    categories      = new String[size];
                    
                    // "SELECT cl_to FROM categorylinks WHERE cl_from=" + cl_from
                    sb.setLength(0);
                    sb.append("SELECT cl_to FROM categorylinks WHERE cl_from=");
                    sb.append(cl_from);
                    rs = s.executeQuery ("SELECT cl_to FROM categorylinks WHERE cl_from=" + cl_from);
                    i=0;
                    while (rs.next ()) {
                        byte[] b = rs.getBytes("cl_to");
                        categories[i++] = Encodings.bytesTo(b, "UTF8"); // ISO8859_1
                    }
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (Categorylinks.java GetCategory()):: sql='" + sb.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return categories;
    }
    
    /** Gets first level of categories for the page with page_id. */
    /*public static List<Integer> getFirstLevel (Connect connect, int page_id) {
        
        //ids is a first level categories
        List<Integer> ids = new ArrayList<Integer>();
                
        String[] add = GetCategory(connect, page_id);
        for(String a : add) {
            
            int id = PageTable.getCategoryIDByTitle(connect, a);
            if (0 == id) 
                continue;
            
            ids.add(id);
        }
        return ids;
    }*/
   
}
