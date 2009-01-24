package net.zuckerfrei.jcfd;

import java.io.Serializable;


/**
 * Definition object contains the definition (translation) received from the DICT server.
 * 
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 * 
 * @see net.zuckerfrei.jcfd.Dict#define(String)
 * @see net.zuckerfrei.jcfd.Database
 */
public interface Definition
    extends Serializable
{

    //~ Methods ===============================================================

    /**
     * The content of the definition. Returned as <code>Object</code> so that we can have different
     * return types, e.g. <code>org.w3c.dom.Node</code>.
     *
     * @return Object the very content returned from the server.
     */
    public Object getContent();


    /**
     * Gets the database this definition is comming from.
     *
     * @return Database
     */
    public Database getDatabase();


    /**
     * Returns the links (references) found in this definition. Links are used to search
     * another definitions which are in some corelation with this one. E.g. when defining "linux"
     * you'll probably have links to "FSF" and "GNU".
     *
     * @return String[] containing words for easy defining.
     */
    public String[] getLinks();


    /**
     * Gets the word this definition is for. This is the same word as send to {@link Dict#define(String)}.
     *
     * @return String word.
     */
    public String getWord();
}
