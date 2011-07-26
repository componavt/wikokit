/* TPageInflection.java - SQL operations with the table 'page_inflection' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.sql;

import wikipedia.sql.Connect;
import java.sql.*;

/** An operations with the table 'page_inflection' in MySQL wiktionary_parsed database.
 */
public class TPageInflection {

    /** Unique identifier in the table 'page_inflection'. */
    private int id;

    /** Title of the wiki article (usually normalized wordform). */
    private TPage page;

    /** Inflectional wordform. */
    private TInflection inflection;

    /** Term (inflection) frequency, e.g. how often is "running" for "run", 
     * which is extracted from wiki text: [[run|running]]. */
    private int term_freq;

    public TPageInflection(int _id,TPage _page,TInflection _inflection,int _term_freq) {
        id          = _id;
        page        = _page;
        inflection  = _inflection;
        term_freq   = _term_freq;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets page from database */
    public TPage getPage() {
        return page;
    }

    /** Gets ID of a record or inserts record (if it is absent)
     * into the table 'page_inflection'.
     *
     * @param text      text (without wikification).
     * @return inserted record, or null if insertion failed
     */
    public static TPageInflection getOrInsert (Connect connect,TPage page,TInflection inflection,int term_freq) {

        TPageInflection pi = TPageInflection.get(connect, page, inflection);
        if(null == pi)
            pi = TPageInflection.insert(connect, page, inflection, term_freq);
        return pi;
    }

    /** Inserts record into the table 'page_inflection'.<br><br>
     * INSERT INTO page_inflection (page_id,inflection_id,term_freq) VALUES (1,2,3);
     * @param page_id       ID of wiki page
     * @param inflection_id ID of inflectional wordform
     * @param term_freq     term (inflection) frequency
     * @return inserted record, or null if insertion failed
     */
    public static TPageInflection insert (Connect connect,TPage page,TInflection inflection,int term_freq) {

        if(null == page || null == inflection) {
            System.err.println("Error (TPageInflection.insert()):: null arguments: page="+page+"; inflection="+inflection);
            return null;
        }

        StringBuilder str_sql = new StringBuilder();
        TPageInflection page_infl = null;
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("INSERT INTO page_inflection (page_id,inflection_id,term_freq) VALUES (");
                str_sql.append(page.getID());
                str_sql.append(",");
                str_sql.append(inflection.getID());
                str_sql.append(",");
                str_sql.append(term_freq);
                str_sql.append(")");
                s.executeUpdate (str_sql.toString());

                s = connect.conn.createStatement ();
                ResultSet rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
                try {
                    if (rs.next ())
                        page_infl = new TPageInflection(rs.getInt("id"), page, inflection, term_freq);
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TPageInflection.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return page_infl;
    }
    
    /** Selects unique record from the table 'page_inflection' by page and inflection.<br><br>
     * SELECT id,term_freq FROM page_inflection WHERE page_id=30 AND inflection_id=8;
     * @param page      wiki page
     * @param infl      inflectional wordform
     * @return null if data is absent
     */
    public static TPageInflection get (Connect connect,TPage page,TInflection infl) {

        if(null == page || null == infl) {
            System.err.println("Error (wikt_parsed TPageInflection.get()):: null arguments: page="+page+"; inflection="+infl);
            return null;
        }
        
        StringBuilder str_sql = new StringBuilder();
        TPageInflection page_infl = null;
        
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT id,term_freq FROM page_inflection WHERE page_id=");
                // SELECT id,term_freq FROM page_inflection WHERE page_id=30 AND inflection_id=8;
                str_sql.append(page.getID());
                str_sql.append(" AND inflection_id=");
                str_sql.append(infl.getID());
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        int id          = rs.getInt("id");
                        int term_freq   = rs.getInt("term_freq");
                        page_infl = new TPageInflection(id, page, infl, term_freq);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TPageInflection.get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return page_infl;
    }
    
    /** Selects record from the table 'page_inflection' by ID<br><br>
     * SELECT page_id,inflection_id,term_freq FROM page_inflection WHERE id=1;
     * @return null if data is absent
     */
    public static TPageInflection getByID (Connect connect,int id) {
        
        StringBuilder str_sql = new StringBuilder();
        TPageInflection page_infl = null;

        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT page_id,inflection_id,term_freq FROM page_inflection WHERE id=");
                str_sql.append(id);
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        TPage       page = TPage.      getByID(connect, rs.getInt("page_id"));
                        TInflection infl = TInflection.getByID(connect, rs.getInt("inflection_id"));
                        int term_freq    =                              rs.getInt("term_freq");
                        if(null != page && null != infl) {
                            page_infl = new TPageInflection(id, page, infl, term_freq);
                        }
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TPageInflection.getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return page_infl;
    }

    /** Deletes row from the table 'page_inflection' by a value of ID.<br>
     *  DELETE FROM page_inflection WHERE id=1;
     * @param  id  unique ID in the table `page_inflection`
     */
    public static void delete (Connect connect,TPageInflection page_infl) {

        if(null == page_infl) {
            System.err.println("Error (wikt_parsed TPageInflection.delete()):: null argument 'page inflection'");
            return;
        }
        
        StringBuilder str_sql = new StringBuilder();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("DELETE FROM page_inflection WHERE id=");
                str_sql.append(page_infl.getID());
                s.execute (str_sql.toString());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TPageInflection.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
}
