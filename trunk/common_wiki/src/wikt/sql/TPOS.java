/* TPOS.java - SQL operations with the table 'part_of_speech'
 * in Wiktionary parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikt.constant.POS;

//import wikipedia.util.StringUtil;
import wikipedia.sql.Connect;
import wikipedia.sql.UtilSQL;
import java.sql.*;

import java.util.Map;
import java.util.LinkedHashMap;
//import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/** An operations with the table 'part_of_speech' in Wiktionary parsed database.
 * The table 'part_of_speech' contains list of POS: name and ID.
 */
public class TPOS {
    
    /** Unique POS identifier. */
    private int id;
    
    /** Name of part of speech: code and name, e.g. 'ru' and 'Russian'. */
    private POS pos;

    /** Local map from id to POS. It is created from data in POS.java.
     * It is used to fill the table 'part_of_speech' in right sequence.*/
    private static Map<Integer, POS> id2pos;
    
    public TPOS(int _id,POS _pos) {
        id  = _id;
        pos = _pos;
    }

    /** Gets unique ID of this part of speech. */
    public int getID() {
        return id;
    }
    
    /** Gets this part of speech. */
    public POS getPOS() {
        return pos;
    }
    
    /** Deletes all records from the table 'part_of_speech',
     * loads parts of speech names from POS.java,
     * sorts by name,
     * fills the table.
     */
    public static void recreateTable(Connect connect) {

        System.out.println("Recreating the table `part_of_speech`...");
        fillLocalMaps();
        UtilSQL.deleteAllRecordsResetAutoIncrement(connect, "part_of_speech");
        fillDB(connect);
        {
            int db_current_size = wikipedia.sql.Statistics.Count(connect, "part_of_speech");
            assert(db_current_size == POS.size()); // ~ 12 POS
        }
    }

    /** Load data from a POS class, sorts,
     * and fills the local map 'id2pos'. */
    public static void fillLocalMaps() {

        int size = POS.size();
        List<String>list_pos = new ArrayList<String>(size);
        list_pos.addAll(POS.getAllPOSNames());
        Collections.sort(list_pos);             // Collections.sort(list_pos, StringUtil.LEXICOGRAPHICAL_ORDER);
        
        // OK, we have list of POS names. Sorted list 'list_pos'
        
        id2pos      = new LinkedHashMap<Integer, POS>(size);
        for(int id=0; id<size; id++) {
            String s = list_pos.get(id);    // s - POS name
            assert(POS.has(s));                                                 //System.out.println("fillLocalMaps---id="+id+"; s="+s);
            id2pos.put(id, POS.get(s));
        }
    }
    
    /** Fills database table 'part_of_speech' by data from POS class. */
    public static void fillDB(Connect connect) {

        for(int id : id2pos.keySet()) {
            insert (connect, id2pos.get(id));
        }
    }

    /** Inserts record into the table 'part_of_speech'.
     *
     * INSERT INTO part_of_speech (name) VALUES ("noun");
     *
     * @param name  part of speech name, e.g. 'unknown', 'noun'
     */
    public static void insert (Connect connect,POS p) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO part_of_speech (name) VALUES (\"");
            //String safe_title = StringUtil.spaceToUnderscore(
            //                    StringUtil.escapeChars(name));
            //str_sql.append(safe_title);
            str_sql.append(p.toString());
            str_sql.append("\")");

            s.executeUpdate (str_sql.toString());
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPOS.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    /** Selects row from the table 'part_of_speech' by a POS name.
     *
     *  SELECT id FROM part_of_speech WHERE name="noun";
     *
     * @param  POS  part of speech class
     * @return null if a part of speech name is absent in the table 'part_of_speech'
     */
    public static TPOS get (Connect connect,POS p) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();
        TPOS        tp = null;

        if(null == p) return null;
        
        try {
            s = connect.conn.createStatement ();

            str_sql.append("SELECT id FROM part_of_speech WHERE name=\"");
            str_sql.append(p.toString());
            str_sql.append("\"");
            
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            if (rs.next ())
            {
                int id      = rs.getInt("id");
                tp = new TPOS(id, p);
            } else {
                    System.err.println("Warning: (wikt_parsed TPOS.java get()):: POS (" + p.toString() + ") is absent in the table 'part_of_speech'.");
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPOS.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return tp;
    }

    /** Deletes row from the table 'part_of_speech' by the POS name.
     *
     *  DELETE FROM part_of_speech WHERE name="unknown";
     *
     * @param  p  POS to be deleted
     */
    public static void delete (Connect connect,POS p) {

        Statement   s = null;
        ResultSet   rs= null;
        StringBuffer str_sql = new StringBuffer();

        if(null == p) return;

        try {
            s = connect.conn.createStatement ();

            str_sql.append("DELETE FROM part_of_speech WHERE name=\"");
            str_sql.append(p.toString());
            str_sql.append("\"");

            s.execute (str_sql.toString());

        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPOS.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
}
