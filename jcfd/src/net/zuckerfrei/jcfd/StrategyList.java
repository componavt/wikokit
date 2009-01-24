package net.zuckerfrei.jcfd;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.SequencedHashMap;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class StrategyList {

    //~ Static variables/initializers =========================================

    static List list = new ArrayList();
    static SequencedHashMap strMap = new SequencedHashMap();

    //~ Instance variables ====================================================

    /** DOCUMENT ME! */
    private int position = 0;

    //~ Constructors ==========================================================

    /**
     * Creates a new StrategyList object.
     */
    StrategyList() {
        ;    // to prevent illegal instantiation
    }

    //~ Methods ===============================================================

    /**
     * Method addDefinition.
     *
     * @param strategy
     */
    public void addStrategy(Strategy strategy) {
        list.add(strategy);
        strMap.put(strategy.getCode(),
                   strategy);
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
     * @param code DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws IllegalArgumentException DOCUMENT ME!
     */
    public static Strategy findStrategy(String code) {

        Strategy str = (Strategy) strMap.get(code);

        if (str == null) {
            throw new IllegalArgumentException("Unknown code " + code);
        }

        return str;
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
    public Strategy next() {
        return (Strategy) list.get(position++);

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
    public Strategy prev() {
        return (Strategy) list.get(--position);
    }


    /**
     * DOCUMENT ME!
     *
     * @param strategy DOCUMENT ME!
     */
    public void removeStrategy(Strategy strategy) {
        list.remove(strategy);
        strMap.remove(strategy);
    }
}
