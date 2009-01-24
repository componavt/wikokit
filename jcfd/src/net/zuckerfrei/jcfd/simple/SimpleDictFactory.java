package net.zuckerfrei.jcfd.simple;

import net.zuckerfrei.jcfd.Configuration;
import net.zuckerfrei.jcfd.Dict;
import net.zuckerfrei.jcfd.DictException;
import net.zuckerfrei.jcfd.DictFactory;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 * @deprecated
 */
public class SimpleDictFactory
    extends DictFactory
{

    //~ Methods ===============================================================

    /**
     * @see net.zuckerfrei.jcfd.DictFactory#getDictClient()
     */
    public Dict getDictClient()
                 throws DictException
    {
        return new SimpleDict(Configuration.getInstance().getHost(),
                              Configuration.getInstance().getPort());
    }
}
