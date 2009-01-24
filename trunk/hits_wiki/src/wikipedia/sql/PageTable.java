/*
 * PageTable.java - SQL operations with the table page in wikipedia
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.sql;

import wikipedia.language.Encodings;
import wikipedia.data.ArticleIdAndTitle;
import wikipedia.kleinberg.SessionHolder;

import java.sql.*;

/** The operations with the page table in MySQL wikipedia */
public class PageTable extends PageTableBase {
    
    private static StringBuffer sb = new StringBuffer(255);
    
    private static ArticleIdAndTitle aid_getbyid = new ArticleIdAndTitle ();
    private static ArticleIdAndTitle aid_getbyid_result;
    /** Gets 
     * 1. page's or category title by identifier, and
     * 2. page's id is negative if it's redirect page.
     *
     * @return null if article's title is absent in the table page.
     *
     * Attention: returned ArticleIdAndTitle is constant construction, 
     * it will be changed after next call of this function.
     */
    public static ArticleIdAndTitle getByID(Connect connect, int id) {
        String      title = null;
        Statement   s = null;
        ResultSet   rs= null;
        
        if(id < 0)  // special treatment of id of redirect page
            id = -id;
        
        aid_getbyid_result = null;
        try {
            s = connect.conn.createStatement ();
            sb.setLength(0);
            sb.append("SELECT page_title, page_is_redirect FROM page WHERE page_id=");
            sb.append(id);
            s.executeQuery(sb.toString());
            
            rs = s.getResultSet ();
            if (rs.next ())
            {
                Encodings e = connect.enc;
                String db_str = Encodings.bytesTo(rs.getBytes("page_title"), e.GetDBEnc());
                aid_getbyid.title = e.EncodeFromDB(db_str);
                aid_getbyid.id    = (1 == rs.getInt("page_is_redirect")) ? -id : id;
                aid_getbyid_result = aid_getbyid;
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (PageTable.java getTitleByID()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return aid_getbyid_result;
    }
    
    /** Gets type of the page. 
     * @return Returns namespace MAIN for the article, CATEGORY for the category, 
     * else null (old -1).
     */
    public static PageNamespace getNamespaceByID (SessionHolder session, int id) {
        
        return getNamespaceByID (session.connect, id);
    }
    
}
