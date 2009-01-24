package net.zuckerfrei.jcfd;

/**
 * DOCUMENT ME!
 *
 * @author administrator To change this generated comment edit the template
 *         variable "typecomment": Window>Preferences>Java>Templates. To
 *         enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class InvalidResponseException
    extends DictException
{

    //~ Constructors ==========================================================

    /**
     * Constructor for InvalidResponseException.
     */
    public InvalidResponseException() {
        super();
    }


    /**
     * Constructor for InvalidResponseException.
     *
     * @param arg0
     */
    public InvalidResponseException(String arg0) {
        super(arg0);
    }


    /**
     * Constructor for InvalidResponseException.
     *
     * @param arg0
     */
    public InvalidResponseException(Throwable arg0) {
        super(arg0);
    }


    /**
     * Constructor for InvalidResponseException.
     *
     * @param arg0
     * @param arg1
     */
    public InvalidResponseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
