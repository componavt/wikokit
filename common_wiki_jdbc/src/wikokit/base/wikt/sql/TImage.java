/* TImage.java - SQL operations with the table 'image' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2018 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.PageTableBase;
import wikokit.base.wikt.constant.Image;

/** Operations with the table 'image' in MySQL Wiktionary_parsed database.
 * 
 * @see TImageMeaning
 */
public class TImage {
    
    /** Unique identifier in the table 'image'. */
    private int id;
    
    /** Image filename using underscores, at Wikimedia Commons. */
    private String filename;
    
    /** File size in bytes. */
    private int size;
    
    /** Image width in pixels. */
    private int width;
    
    /** Image height in pixels. */
    private int height;
    
    
    /** Selects ID from the table 'image' by the filename.
     * 
     *  SELECT id FROM image WHERE filename="some file";
     * 
     * @return 0 if this filename is absent in the table 'image'
     */
    public static int getIDByFilename (Connect connect, String filename, String page_title) {

        if(null == filename
                || filename.isEmpty()) return 0;

        int result_id = 0;
        StringBuilder str_sql = new StringBuilder();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT id FROM image WHERE filename=\"");
                String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, filename);
                str_sql.append(safe_title);
                str_sql.append("\"");
                try (ResultSet rs = s.executeQuery (str_sql.toString())) {
                    if (rs.next ())
                        result_id = rs.getInt("id");
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (TImage.getIDByFilename()):: page_title='" + page_title + "'; sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result_id;
    }
    
    
    /** Inserts record into the table 'image', gets last inserted ID.
     * 
     * INSERT INTO image (filename) VALUES ("some name");
     * @param short_name    label itself
     * @param name          full name of label
     * 
     * @return last inserted ID, 0 means error
     */
    public static int insert (Connect connect, String filename, String page_title) {

        if(null == filename || filename.length() == 0) return 0;
        
        int result_id = 0;
        StringBuilder str_sql = new StringBuilder();
        try
        {
            try (Statement s = connect.conn.createStatement ()) {
                str_sql.append("INSERT INTO image (filename) VALUES (\"");
                
                String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, filename);
                str_sql.append(safe_title);
                str_sql.append("\")");

                s.executeUpdate (str_sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = s.getGeneratedKeys();
                if (rs.next()){
                    result_id = rs.getInt(1);
                }
            }
        }catch(SQLException ex) {
            System.out.println("SQLException (wikt_parsed TImage.insert()):: page_title='" + page_title + "'; sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result_id;
    }
    
    
    /** Stores image related to this meaning into tables:
     * 'image' and 'image_meaning'. New images will be stored to the table 'image'.
     *
     * @param page_title    word described in this article
     * @param _meaning      corresponding record in table 'meaning' to this relation
     * @param _image        image (filename and caption) extracted from text and related to this meaning
     */
    public static void storeToDB (Connect connect, String page_title,
                                  TMeaning _meaning, Image _image)
    {
        if(null == _meaning || null == _image) return;

        // 1. if '_image' is new image then add it to the table 'image'
        int image_id = TImage.getIDByFilename(connect, _image.getFilename(), page_title);
        if(0 == image_id)
            image_id = TImage.insert (connect, _image.getFilename(), page_title);
        
        // 2. add to the table 'image_meaning' the record (_image.id, _meaning.id)
        TImageMeaning.insert( connect, page_title, image_id, _meaning.getID(), _image.getCaption());
    }
}
