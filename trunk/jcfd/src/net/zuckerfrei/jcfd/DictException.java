package net.zuckerfrei.jcfd;

import org.apache.commons.lang.exception.NestableException;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class DictException
    extends NestableException
{

    //~ Constructors ==========================================================

    /**
     * Constructor for DictException.
     */
    public DictException() {
        super();
    }


    /**
     * Constructor for DictException.
     *
     * @param arg0
     */
    public DictException(String arg0) {
        super(arg0);
    }


    /**
     * Constructor for DictException.
     *
     * @param arg0
     */
    public DictException(Throwable arg0) {
        super(arg0);
    }


    /**
     * Constructor for DictException.
     *
     * @param arg0
     * @param arg1
     */
    public DictException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
