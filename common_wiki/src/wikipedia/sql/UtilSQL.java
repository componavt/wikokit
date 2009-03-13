/* UtilSQL.java - misc SQL operations.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikipedia.sql;

import java.sql.*;

/** Miscellaneous SQL operations.
 */
public class UtilSQL {

    /** Deletes all records from the table 'table_name', resets auto increment.
     *
     * DELETE FROM lang;
     * ALTER TABLE lang AUTO_INCREMENT = 0;
     */
    public static void deleteAllRecordsResetAutoIncrement (Connect connect,String table_name) {

        Statement   s = null;
        ResultSet   rs= null;

        try {
            s = connect.conn.createStatement ();
            s.addBatch("DELETE FROM "+ table_name +";");
            s.addBatch("ALTER TABLE "+ table_name +" AUTO_INCREMENT = 0;");
            s.executeBatch();

        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed UtilSQL.java deleteAllRecordsResetAutoIncrement()):: table name is "+ table_name +"; " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }

    
}
