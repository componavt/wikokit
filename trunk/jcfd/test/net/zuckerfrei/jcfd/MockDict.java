package net.zuckerfrei.jcfd;

/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class MockDict
    extends DictImpl
{

    //~ Constructors ==========================================================

    /**
     * Constructor for MockDict.
     *
     * @param host
     * @param port
     *
     * @throws DictException
     */
    public MockDict(String host, int port)
             throws DictException
    {

        // super(host, port);
        connected = true;
    }

    //~ Methods ===============================================================

    /**
     * @see net.zuckerfrei.jcfd.Dict#define(String, Database)
     */
    public DefinitionList define(String word, Database database)
                          throws DictException
    {
        return null;
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#match(String, Strategy, Database)
     */
    public MatchList match(String word, Strategy strategy, Database database)
                    throws DictException
    {
        return null;
    }
}
