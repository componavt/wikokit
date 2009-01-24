package net.zuckerfrei.jcfd;

import java.awt.geom.IllegalPathStateException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.SequencedHashMap;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class Strategy
    implements Serializable
{

    //~ Static variables/initializers =========================================

    /** DOCUMENT ME! */
    public static final Strategy DEFAULT = new Strategy(".", "Default strategy");

    //~ Instance variables ====================================================

    /** DOCUMENT ME! */
    private String name;

    /** DOCUMENT ME! */
    private String code;

    //~ Constructors ==========================================================

    /**
     * Constructor Strategy.
     *
     * @param code
     * @param name
     */
    public Strategy(String code, String name) {
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
     * DOCUMENT ME!
     *
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Strategy)) {
            return false;
        }

        Strategy tmp = (Strategy) o;

        if (tmp.code.equals(this.code) && tmp.name.equals(this.name)) {    // implement the comparisons
            return true;
        }

        return false;
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {

        int result = 17;

        result = 37 * result + code.hashCode();
        result = 37 * result + name.hashCode();

        return result;
    }


    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Strategy code: " + code + ", name: " + name;
    }
}
