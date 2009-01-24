package net.zuckerfrei.jcfd;

/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija $Revision: 1.1.1.1 $ To change this generated comment
 *         edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the
 *         creation of type comments go to Window>Preferences>Java>Code
 *         Generation.
 */
public class InvalidStrategyException
    extends DictException
{

    //~ Constructors ==========================================================

    /**
     * Constructor for InvalidStrategyException.
     */
    public InvalidStrategyException() {
        super();
    }


    /**
     * Constructor for InvalidStrategyException.
     *
     * @param arg0
     */
    public InvalidStrategyException(String arg0) {
        super(arg0);
    }


    /**
     * Constructor for InvalidStrategyException.
     *
     * @param arg0
     */
    public InvalidStrategyException(Throwable arg0) {
        super(arg0);
    }


    /**
     * Constructor for InvalidStrategyException.
     *
     * @param arg0
     * @param arg1
     */
    public InvalidStrategyException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
