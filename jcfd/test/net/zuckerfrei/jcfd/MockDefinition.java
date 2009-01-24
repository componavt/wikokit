package net.zuckerfrei.jcfd;

/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class MockDefinition
    implements Definition
{

    //~ Constructors ==========================================================

    /**
     * Constructor for MockDefinition.
     */
    public MockDefinition() {
        super();
    }

    //~ Methods ===============================================================

    /**
     * @see net.zuckerfrei.jcfd.Definition#getContent()
     */
    public Object getContent() {
        return null;
    }


    /**
     * @see net.zuckerfrei.jcfd.Definition#getDatabase()
     */
    public Database getDatabase() {
        return null;
    }


    /**
     * @see net.zuckerfrei.jcfd.Definition#getLinks()
     */
    public String[] getLinks() {
        return null;
    }
    /**
     * @see net.zuckerfrei.jcfd.Definition#getWord()
     */
    public String getWord() {
        return null;
    }

}
