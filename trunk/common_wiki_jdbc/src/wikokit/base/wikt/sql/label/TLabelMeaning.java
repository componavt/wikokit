/* TLabelMeaning.java - SQL operations with the table 'label_meaning' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.sql.label;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.sql.TMeaning;

/** An operations with the table 'label_meaning' in MySQL Wiktionary_parsed database.
 * label_meaning - binds together context labels and meaning number.
 */
public class TLabelMeaning {
    
    /** Context label (label_id). */
    private TLabel label;
    
    /** One sense of a word (meaning_id). */
    private TMeaning meaning;
    
    private final static Label[] NULL_LABEL_ARRAY = new Label[0];
    
    /** Inserts record into the table 'label_meaning'.<br><br>
     * INSERT INTO label_meaning (label_id,meaning_id) VALUES (1,2);
     * @param name  category label name
     * @param parent_category_id ID of parent category, id=0 corresponds to NULL in the database 
     */
    public static void insert (Connect connect, String page_title, 
                               int label_id, int meaning_id) {

        if(0 == label_id || 0 == meaning_id) return;
        
        StringBuilder str_sql = new StringBuilder();
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("INSERT INTO label_meaning (label_id,meaning_id) VALUES (");

                str_sql.append(label_id);
                str_sql.append(",");
                str_sql.append(meaning_id);
                str_sql.append(")");

                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.out.println("SQLException (wikt_parsed TLabelMeaning.insert):: page_title='" + page_title + "'; sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
    
    /** Selects labels from tables `label` and `label_meaning` by 
     * label_meaning.meaning_id value.<br><br>.
     * SELECT label_id FROM label_meaning WHERE meaning_id=11;
     * @return empty array if data is absent
     */
    public static Label[] get (Connect connect,TMeaning meaning) {

        if(null == meaning) {
            System.out.println("Error (wikt_parsed TLabelMeaning.get()):: null argument: meaning.");
            return NULL_LABEL_ARRAY;
        }
        
        StringBuilder str_sql = new StringBuilder();
        List<Label> list_label = null;
        
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT label_id FROM label_meaning WHERE meaning_id=");
                str_sql.append(meaning.getID());
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    while (rs.next ())
                    {
                        int   label_id = rs.getInt("label_id");
                        Label label = TLabel.getLabelFast( label_id );

                        if(null != label) {
                            if(null == list_label)
                                       list_label = new ArrayList();

                            list_label.add(label);
                        }
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (wikt_parsed TLabelMeaning.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }

        if(null == list_label)
            return NULL_LABEL_ARRAY;
        return (Label[])list_label.toArray(NULL_LABEL_ARRAY);
    }
    
    /** Counts number of records in the table 'label_meaning' with given label ID.<br>
     * SELECT COUNT(*) FROM label_meaning WHERE label_id = 3;
     *
     * @return 0 means error
     */
    public static int countRecordsWithLabelID(Connect connect, int label_id) {
        Statement s = null;
        ResultSet rs= null;
        int size = 0;
        String str_sql = null;

        if(null==connect || null==connect.conn)
            return 0;

        try {
            s = connect.conn.createStatement ();
            str_sql = "SELECT COUNT(*) AS size FROM label_meaning WHERE label_id="+label_id;
            rs = s.executeQuery (str_sql);
            if (rs.next ())
                size = rs.getInt("size");
            
        } catch(SQLException ex) {
            System.out.println("SQLException (TLabelMeaning.countRecordsWithLabelID()): sql='" + str_sql + "' " + ex.getMessage());
        } finally {
            if (rs != null) {
                try { rs.close();
                } catch (SQLException sqlEx) { }
            }
            if (s != null) {
                try { s.close();
                } catch (SQLException sqlEx) { }
            }
        }
        return size;
    }
    
}
