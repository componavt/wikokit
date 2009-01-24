/*
 * CatCount.java
 *
 * Created on September 10, 2007, 10:54 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wikipedia.experiment;

import wikipedia.sql.Connect;

import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Routines to work with the table cat_count created in wiki database.
 * The table cat_count stores data about number of articles, sub-categories, 
 * and hyponyms for each category in wiki.
 * 
 * Table cat_count could be built by MySQL stored procedure 
 * http://meta.wikimedia.org/wiki/Hyponyms (todo)
 */
public class CatCount {
    
    /** category page identifier */
    private int      page_id;
    
    /** category page title */
    private String   page_title;
    
    /** The depth of a node n is the length of the path from the root to the 
     * node. The root node is at depth zero.*/
    //private int      n_depth;
    
    /** Number of direct sub-categories (childrens). It is zero for 
     * category-leaf.*/
    //private int      n_subcat;
    
    /** Number of articles which have this category.*/
    //private int      n_articles;
    
    /** n_subcat + n_articles + n_hyponyms_of_sub-categories */
    //private int      n_hyponyms;
    
    /** Infromation content.*/
    private float    ic;
    
    
    /** Creates a new instance of CatCount */
    public CatCount() {}
    
    /** Gets category page title. */
    public String getPageTitle () {
        return page_title;
    };
    
    /** Gets category page Information Content. */
    public float getInformationContent() {
        return ic;
    };
    
    
    /** Selects row with maximum ic from the table cat_count by the page_title.
     *
     *  SQL (SimpleWiki):
     *  SELECT page_id,page_title, ic FROM cat_count WHERE page_title IN ("Internet_slang", "Websites");
     *
     *  @param titles       array of titles of page to select from
     *  @return CatCount    object with initialized fields, null if titles is null
     */
    public static CatCount getMaxIC(java.sql.Connection conn, String[] titles) {
        CatCount result = null;
        CatCount cur = new CatCount();
        
        if(null == titles || 0 == titles.length)
            return null;
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try {
            s = conn.createStatement ();
            
            //SELECT page_id,page_title,ic FROM cat_count WHERE page_title IN ("Internet_slang", "Websites");
            str_sql.append("SELECT page_id,page_title,ic FROM cat_count WHERE page_title IN (\"");
            
            // Prepare SQL IN(...) by data from titles[]
            for (int i=0; i<titles.length-1 && titles.length>0; i++) {
                str_sql.append(titles[i]);
                str_sql.append("\",\"");
            }
            str_sql.append(titles[ titles.length-1 ]); // skip last comma
            str_sql.append("\")");
            
            s.executeQuery (str_sql.toString());
            
            rs = s.getResultSet ();
            while (rs.next ())
            {
                cur.ic = rs.getFloat("ic");
                if(null == result || cur.ic > result.ic) {
                    if(null == result) {
                        result = new CatCount();
                    }
                    /*if(cur.ic < 0) {
                        // example 1: 
                        // Gender -> Category: Biology -> Science -> ... Project (has IC=-1)
                        // Equality -> Category: Disambiguation -> Wikipedia -> Project
                        result.ic = 0; 
                    } else {
                    */    result.ic = cur.ic;
                    //}
                    result.page_id = rs.getInt("page_id");
                    result.page_title = rs.getString("page_title");
                }
            }
        } catch(SQLException ex) {
            System.err.println("wikipedia.experiment SQLException ( CatCount.java getMaxIC()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        
        return result;
    }
    
    
    
}
