package net.zuckerfrei.jcfd.simple;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import net.zuckerfrei.jcfd.DictException;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class MockSimpleDict
    extends SimpleDict
{

    //~ Instance variables ====================================================

    /** 
     * DOCUMENT ME! 
     */
    BufferedReader br;

    /** 
     * DOCUMENT ME! 
     */
    DataOutputStream dos;

    //~ Constructors ==========================================================

    /**
     * Creates a new MockSimpleDict object.
     *
     * @param host DOCUMENT ME!
     * @param port DOCUMENT ME!
     *
     * @throws DictException DOCUMENT ME!
     */
    public MockSimpleDict(String host, int port)
                   throws DictException
    {
        super(host, port);
        connect();
    }

    //~ Methods ===============================================================

    /**
     * @see net.zuckerfrei.jcfd.AbstractDict#flushResponse()
     */
    protected void flushResponse()
                          throws IOException
    {
        super.flushResponse();
    }


    /**
     * @see net.zuckerfrei.jcfd.AbstractDict#readBody()
     */
    protected String readBody()
                       throws IOException
    {
        return super.readBody();
    }


    /**
     * @see net.zuckerfrei.jcfd.AbstractDict#readResponse()
     */
    protected String readResponse()
                           throws IOException
    {
        return super.readResponse();
    }


    /**
     * @see net.zuckerfrei.jcfd.SimpleDict#connect()
     */
    void connect()
          throws DictException
    {
        connected = true;
    }
}
