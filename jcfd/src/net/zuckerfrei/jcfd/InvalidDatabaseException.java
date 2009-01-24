package net.zuckerfrei.jcfd;

/**
 * DOCUMENT ME!
 *
 * @author administrator To change this generated comment edit the template
 *         variable "typecomment": Window>Preferences>Java>Templates. To
 *         enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class InvalidDatabaseException
    extends DictException
{

    //~ Constructors ==========================================================

    /**
     * Constructor for InvalidDatabaseException.
     */
    public InvalidDatabaseException() {
        super();
    }


    /**
     * Constructor for InvalidDatabaseException.
     *
     * @param arg0
     */
    public InvalidDatabaseException(String arg0) {
        super(arg0);
    }


    /**
     * Constructor for InvalidDatabaseException.
     *
     * @param arg0
     */
    public InvalidDatabaseException(Throwable arg0) {
        super(arg0);
    }


    /**
     * Constructor for InvalidDatabaseException.
     *
     * @param arg0
     * @param arg1
     */
    public InvalidDatabaseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
