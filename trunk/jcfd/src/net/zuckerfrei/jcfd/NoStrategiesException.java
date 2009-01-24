package net.zuckerfrei.jcfd;

/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class NoStrategiesException
    extends DictException
{

    //~ Constructors ==========================================================

    /**
     * Constructor for NoStrategiesException.
     */
    public NoStrategiesException() {
        super();
    }


    /**
     * Constructor for NoStrategiesException.
     *
     * @param arg0
     */
    public NoStrategiesException(String arg0) {
        super(arg0);
    }


    /**
     * Constructor for NoStrategiesException.
     *
     * @param arg0
     */
    public NoStrategiesException(Throwable arg0) {
        super(arg0);
    }


    /**
     * Constructor for NoStrategiesException.
     *
     * @param arg0
     * @param arg1
     */
    public NoStrategiesException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
