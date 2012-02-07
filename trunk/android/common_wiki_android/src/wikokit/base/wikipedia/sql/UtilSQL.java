
package wikipedia.sql;

import java.sql.*;

/** Misc SQL routines.
 */
public class UtilSQL {

    /** Deletes all records from the table 'table_name', resets auto increment.
     *
     * DELETE FROM table_name;
     * ALTER TABLE table_name AUTO_INCREMENT = 0;
     */
    public static void deleteAllRecordsResetAutoIncrement (Connect connect, String table_name) {

        Statement   s = null;
        ResultSet   rs= null;

        try {
            s = connect.conn.createStatement ();
            s.addBatch("DELETE FROM "+ table_name +";");
            s.addBatch("ALTER TABLE "+ table_name +" AUTO_INCREMENT = 1;");
            s.executeBatch();

        } catch(SQLException ex) {
            System.err.println("SQLException (wikipedia.sql UtilSQL.java deleteAllRecordsResetAutoIncrement()):: table = "+ table_name +"; msg = " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    
    
    public static void dropTable (Connect connect, String table_name)
    {
        if(null == connect)
            return;
        
        StringBuffer str_sql = new StringBuffer();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("DROP TABLE IF EXISTS `"+ table_name + "`");
                s.execute(str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (MSRMeanSemrelXX.dropTable()): sql='" + str_sql + "' " + ex.getMessage());
        }
    }
    

}
