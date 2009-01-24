package net.zuckerfrei.jcfd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * List of definitions returned by {@link net.zuckerfrei.jcfd.Dict#define(String)}.
 * This object is traversed and each single {@link net.zuckerfrei.jcfd.Definition} is taken
 * out and processed (e.g. displayed). Use this object in the 'standard list way':
 * <pre>
 * while (definitionList.{@link #hasNext()}) {
 *   {@link net.zuckerfrei.jcfd.Definition} def = definitionList.{@link #next()};
 *   // do something with def
 * }
 * </pre>
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class DefinitionList
    implements Serializable
{

    //~ Instance variables ====================================================

    /** Current position in this list. */
    private int position = 0;

    /** Container for the definitions. */
    List list = new ArrayList();

    //~ Constructors ==========================================================

    /**
     * Creates a new DefinitionList object.
     */
    DefinitionList() {
        super();
    }

    //~ Methods ===============================================================

    /**
     * Adds a {@link Definition} to this list.
     *
     * @param definition to be added.
     */
    public void addDefinition(Definition definition) {
        list.add(definition);
    }


    /**
     * Returns the count of the definitions in this list.
     *
     * @return count of the definitions in this list.
     */
    public int count() {
        return list.size();
    }


    /**
     * Asks if there are any more definitions in this list. Used in the standard way:
     *
     * @return true if there are more definitions in this list.
     */
    public boolean hasNext() {
        return (position < list.size());
    }


    /**
     * Gets the definition from the list.
     *
     * @return definition which is the next in the list.
     */
    public Definition next() {
        return (Definition) list.get(position++);

    }


    /**
     * Returns the current position in this list.
     *
     * @return int the current position.
     */
    public int position() {
        return position;
    }


    /**
     * Returns the previous definition from this list.
     *
     * @return definition.
     */
    public Definition prev() {
        return (Definition) list.get(--position);
    }


    /**
     * Removes the <code>definition</code> from this list.
     *
     * @param definition to be removed from the list.
     * 
     * @return true if the list is changed by calling this method.
     */
    public boolean removeDefinition(Definition definition) {
        return list.remove(definition);
    }
    
    /**
     * Adds another definition list to the end of this definition list.
     * @param definitionList to be added.
     */
    public void addDefinitionList(DefinitionList definitionList) {
        while(definitionList.hasNext()) {
            this.addDefinition(definitionList.next());
        }
    }

    /**
     * Positions the cursor at the beginning of this list.
     */
    public void goBeforeFirst() {
        position = 0;
    }

    /**
     * Positions the cursor at the end of this list.
     */
    public void goAfterLast() {
        position = list.size();
    }

}
