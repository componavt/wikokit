package net.zuckerfrei.jcfd.simple;

import net.zuckerfrei.jcfd.DictException;
import net.zuckerfrei.jcfd.DictImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
class SimpleDict extends DictImpl {

    //~ Static variables/initializers =========================================

    static Log log = LogFactory.getLog(SimpleDict.class);

    //~ Constructors ==========================================================

    /**
     * Constructor SimpleConnection.
     *
     * @param host
     * @param port
     *
     * @throws DictException DOCUMENT ME!
     */
    SimpleDict(String host, int port) throws DictException {
        super(host, port);
    }

}
