/* TImageMeaning.java - SQL operations with the table 'image_meaning' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2018 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.sql;

import java.sql.SQLException;
import java.sql.Statement;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.PageTableBase;

/** Operations with the table 'image_meaning' in MySQL Wiktionary_parsed database.
 * image_meaning - binds together the image and the meaning.
 * 
 * @see TImage
 */
public class TImageMeaning {
    
    /** Context label (label_id). */
    private TImage label;
    
    /** One sense of a word (meaning_id). */
    private TMeaning meaning;
    
    /** Image caption corresponding to some meaning. */
    private String image_caption;
    
    
    /** Inserts record into the table 'image_meaning'.
     * 
     * @param caption       if caption is absent, then let's image caption is page_title
     * 
     * INSERT INTO image_meaning (image_id, meaning_id, image_caption) VALUES (1,2, "some caption");
     */
    public static void insert (Connect connect, String page_title, 
                               int image_id, int meaning_id, String _caption) {

        if(0 == image_id || 0 == meaning_id) return;
        
        // if caption is absent, then let's image caption is page_title
        if(null == _caption || 0 == _caption.length())
            _caption = page_title;
        
        StringBuilder str_sql = new StringBuilder();
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("INSERT INTO image_meaning (image_id,meaning_id,image_caption) VALUES (");

                str_sql.append(image_id);
                str_sql.append(",");
                str_sql.append(meaning_id);
                str_sql.append(",\"");
                
                String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, _caption);
                str_sql.append(safe_title);
                
                str_sql.append("\")");

                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.out.println("SQLException (wikt_parsed TImageMeaning.insert):: page_title='" + page_title + "'; sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
    
}
