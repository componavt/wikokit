package net.zuckerfrei.jcfd;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class MatchList {

    //~ Instance variables ====================================================

    /** DOCUMENT ME! */
    private int position = 0;

    /** DOCUMENT ME! */
    List list = new ArrayList();

    //~ Constructors ==========================================================

    /**
     * Creates a new MatchList object.
     */
    MatchList() {
        ;    // to prevent illegal instantiation
    }

    //~ Methods ===============================================================

    /**
     * Method addDefinition.
     *
     * @param match
     */
    public void addMatch(Match match) {
        list.add(match);
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int count() {
        return list.size();
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean hasNext() {
        return (position < list.size());
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Match next() {
        return (Match) list.get(position++);

    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int position() {
        return position;
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Match prev() {
        return (Match) list.get(--position);
    }


    /**
     * DOCUMENT ME!
     *
     * @param match DOCUMENT ME!
     */
    public void removeMatch(Match match) {
        list.remove(match);
    }
    
    public void goBeforeFirst() {
        position = 0;
    }
    
    public void goAfterLast() {
        position = list.size();
    }
}
