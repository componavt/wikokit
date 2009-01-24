package net.zuckerfrei.jcfd;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;


/**
 * DOCUMENT ME!
 *
 * @author administrator To change this generated comment edit the template
 *         variable "typecomment": Window>Preferences>Java>Templates. To
 *         enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class MockDataOutputStream
    extends DataOutputStream
{

    //~ Constructors ==========================================================

    /**
     * Constructor for MockDataOutputStream.
     *
     * @param out
     */
    public MockDataOutputStream(OutputStream out) {
        super(new ByteArrayOutputStream());
    }
}
