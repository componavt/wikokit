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
import wikipedia.sql.Statistics;
import java.sql.*;

import java.util.Map;
import java.util.LinkedHashMap;
//import java.util.Set;
import java.util.Collection;
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

    /** Map from ID to part of speech. It is created from data
     * in the table `part_of_speech`, which is created from data in POS.java.*/
    private static Map<Integer, TPOS> id2pos;
    
    /** Map from part of speech to ID.*/
    private static Map<POS, Integer> pos2id;

    private final static TPOS[] NULL_TPOS_ARRAY = new TPOS[0];
    
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
    
    /** Gets part of speech (POS with ID) from the table 'lang_pos'.<br><br>
     * REM: createFastMaps() should be run at least once, before this function execution.
     */
    public static int getIDFast(Connect connect,POS p) {
        if(null == pos2id) {
            System.err.println("Error (wikt_parsed TPOS.getIDFast()):: What about calling 'createFastMaps()' before?");
            return -1;
        }
        return pos2id.get(p);
    }

    /** Gets part of speech from the table 'lang_pos'.<br><br>
     * REM: createFastMaps() should be run at least once, before this function execution.
     */
    public static TPOS getPOSFast(Connect connect,POS p) {
        if(null == id2pos) {
            System.err.println("Error (wikt_parsed TPOS.getPOSFast()):: What about calling 'createFastMaps()' before?");
            return null;
        }
        return id2pos.get(pos2id.get(p));
    }

    /** Gets language by ID from the table 'lang'.<br><br>
     * REM: createFastMaps() should be run at least once, before this function execution.
     */
    public static TPOS getTPOSFast(Connect connect,int id) {
        return id2pos.get(id);
    }

    /** Read all records from the table 'lang',
     * fills the internal map from a table ID to a language.
     * 
     * REM: during a creation of Wikrtionary parsed database
     * the functions recreateTable() should be called.
     */
    public static void createFastMaps(Connect connect) {

        System.out.println("Loading table `part_of_speech`...");
        
        TPOS[] tpos = getAllTPOS(connect);
        int size = tpos.length;
        if(tpos.length != POS.size()) {
            System.out.println("Warning (wikt_parsed TPOS.java createFastMaps()):: POS.size (" + POS.size()
                    + ") is not equal to size of table 'lang_pos'("+ size +"). Is the database outdated?");
        }

        if(null != id2pos && id2pos.size() > 0)
            id2pos.clear();
        if(null != pos2id && pos2id.size() > 0)
            pos2id.clear();
        
        id2pos  = new LinkedHashMap<Integer, TPOS>(size);
        pos2id  = new LinkedHashMap<POS, Integer>(size);
        
        for(TPOS t : tpos) {
            id2pos.put(t.getID(), t);
            pos2id.put(t.getPOS(), t.getID());
        }
    }

    /** Gets all records from the table 'part_of_speech'.
     */
    private static TPOS[] getAllTPOS(Connect connect) {

        int size = Statistics.Count(connect, "part_of_speech");
        if(0==size) {
            System.err.println("Error (wikt_parsed TPOS.java getAllTPOS()):: The table `part_of_speech` is empty!");
            return NULL_TPOS_ARRAY;
        }
        
        List<TPOS>tpos_list = new ArrayList<TPOS>(size);

        Collection<POS> pp = POS.getAllPOS();
        for(POS p : pp) {
            TPOS t = get(connect, p);
            if(null != t)
                tpos_list.add(t);
        }
        return( (TPOS[])tpos_list.toArray(NULL_TPOS_ARRAY) );
    }


    /** Deletes all records from the table 'part_of_speech',
     * loads parts of speech names from POS.java,
     * sorts by name,
     * fills the table.
     */
    public static void recreateTable(Connect connect) {

        System.out.println("Recreating the table `part_of_speech`...");
        Map<Integer, POS> id2pos = fillLocalMaps();
        UtilSQL.deleteAllRecordsResetAutoIncrement(connect, "part_of_speech");
        fillDB(connect, id2pos);
        {
            int db_current_size = wikipedia.sql.Statistics.Count(connect, "part_of_speech");
            assert(db_current_size == POS.size()); // ~ 12 POS
        }
    }

    /** Load data from a POS class, sorts,
     * and fills the local map 'id2pos'. */
    public static Map<Integer, POS> fillLocalMaps() {

        int size = POS.size();
        List<String>list_pos = new ArrayList<String>(size);
        list_pos.addAll(POS.getAllPOSNames());
        Collections.sort(list_pos);             // Collections.sort(list_pos, StringUtil.LEXICOGRAPHICAL_ORDER);
        
        // OK, we have list of POS names. Sorted list 'list_pos'

        // Local map from id to POS. It is created from data in POS.java.
        // It is used to fill the table 'part_of_speech' in right sequence.
        Map<Integer, POS> id2pos = new LinkedHashMap<Integer, POS>(size);
        for(int id=0; id<size; id++) {
            String s = list_pos.get(id);    // s - POS name
            assert(POS.has(s));                                                 //System.out.println("fillLocalMaps---id="+id+"; s="+s);
            id2pos.put(id, POS.get(s));
        }
        return id2pos;
    }
    
    /** Fills database table 'part_of_speech' by data from POS class. */
    public static void fillDB(Connect connect,Map<Integer, POS> id2pos) {

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
