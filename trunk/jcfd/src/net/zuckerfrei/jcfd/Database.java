package net.zuckerfrei.jcfd;

import java.io.Serializable;


/**
 * Represents a DICT server's database.
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class Database
    implements Serializable
{

    //~ Static variables/initializers =========================================

    /**
     * Used when searching within any installed databases.
     *
     * @see Definition
     * @see Match
     */
    public static final Database ANY = new Database("*", "Search in any database");

    /**
     * Used when searching only in the first database when a definition or a
     * match is encontered.
     *
     * @see Definition
     * @see Match
     */
    public static final Database FIRST = new Database("!", "Search until found first");

    //~ Instance variables ====================================================

    /** Name of the database. */
    private String name;

    /** Database code, as defined in the server's configuration . */
    private String code;

    //~ Constructors ==========================================================

    /**
     * Creates a new Database object.
     *
     * @param code Database code, as defined in the server's configuration,
     * @param name Full name of the database.
     */
    Database(String code, String name) {
        this.code = code;
        this.name = name;
    }

    //~ Methods ===============================================================

    /**
     * Returns the code.
     *
     * @return String
     */
    public String getCode() {
        return code;
    }


    /**
     * Returns the name.
     *
     * @return String
     */
    public String getName() {
        return name;
    }


    /**
     * Checks if the parameter <code>o</code> is equal to this object by
     * comparing code and name.
     *
     * @param o Object to check the equality of.
     *
     * @return true if the objects are equal, false otherwise.
     *
     * @see #code
     * @see #name
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Database)) {
            return false;
        }

        Database db = (Database) o;

        if (db.name.equals(this.name) && db.code.equals(this.code)) {
            return true;
        }

        return false;
    }


    /**
     * @see Object#hashCode()
     */
    public int hashCode() {

        int result = 17;

        result = 37 * result + name.hashCode();
        result = 37 * result + code.hashCode();

        return result;
    }


    /**
     * Returns human readable representation of this object.
     *
     * @return String 
     */
    public String toString() {
        return "Database " + code + " " + name;
    }
}
