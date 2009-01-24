package net.zuckerfrei.jcfd;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.SequencedHashMap;


/**
 * List of the databases (dictionaries) available on the server. List can be traversed (previous/next).
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 * 
 * @see net.zuckerfrei.jcfd.Database
 */
public class DatabaseList {

    //~ Static variables/initializers =========================================

    private static List list = new ArrayList();
    private static SequencedHashMap dbMap = new SequencedHashMap();

    //~ Instance variables ====================================================

    /** Current position in the list. */
    private int position = 0;

    //~ Constructors ==========================================================

    /**
     * Creates a new DatabaseList object.
     */
    DatabaseList() {
        ;    // to prevent illegal instantiation
    }

    //~ Methods ===============================================================

    /**
     * Adds a database in the list.
     *
     * @param database to be added to the list.
     */
    void addDatabase(Database database) {
        list.add(database);
        dbMap.put(database.getCode(),
                  database);
    }


    /**
     * Number of the databases available.
     *
     * @return int number of available databases.
     */
    public int count() {
        return list.size();
    }


    /**
     * Finds a database by its code.
     * This method should only be used within the client, therefore <code>IllegalArgumentException</code>
     * instead returned null when no database is found. The client should only send the existing
     * codes in this method, and null database means obvious bug.
     *
     * @param code Database code
     *
     * @return Found database
     *
     * @throws IllegalArgumentException When no database is found for the provided code.
     */
    public static Database findDatabase(String code) {

        Database database = (Database) dbMap.get(code);

        if (database == null) {
            throw new IllegalArgumentException("Unknown DB code: "    /* NOI18N */ + code);
        }

        return database;
    }


    /**
     * Tests if there's more databases in the list, with respect to current position.
     *
     * @return true if more databases are available.
     */
    public boolean hasNext() {
        return (position < list.size());
    }


    /**
     * Returns the next database in the list.
     *
     * @return Database next in the list.
     */
    public Database next() {
        return (Database) list.get(position++);

    }


    /**
     * Gets the current position in the list.
     *
     * @return int the current position
     */
    public int position() {
        return position;
    }


    /**
     * Returns the previous database from the list.
     *
     * @return Database
     */
    public Database prev() {
        return (Database) list.get(--position);
    }


    /**
     * Removes the database from the list.
     *
     * @param database to be removed.
     */
    public void removeDatabase(Database database) {
        list.remove(database);
        dbMap.remove(database.getCode());
    }
}
