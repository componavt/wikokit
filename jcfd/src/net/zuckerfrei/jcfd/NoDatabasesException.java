package net.zuckerfrei.jcfd;

/**
 * An exception indicating that no databases are present on the DICT server.
 * That usually means that DICT server is not configured properly.
 */
public class NoDatabasesException
    extends DictException
{

    //~ Constructors ==========================================================

    /**
     * Constructor for NoDatabasesException.
     */
    public NoDatabasesException() {
        super();
    }


    /**
     * Constructor for NoDatabasesException.
     *
     * @param message 
     */
    public NoDatabasesException(String arg0) {
        super(arg0);
    }


    /**
     * Constructor for NoDatabasesException.
     *
     * @param arg0
     */
    public NoDatabasesException(Throwable arg0) {
        super(arg0);
    }


    /**
     * Constructor for NoDatabasesException.
     *
     * @param arg0
     * @param arg1
     */
    public NoDatabasesException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
