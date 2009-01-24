package net.zuckerfrei.jcfd;

/**
 * Factory for creating <code>Definition</code> objects. It has pluggable
 * implementation and is implemented as a singleton.
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 *
 * @see net.zuckerfrei.jcfd.simple.SimpleDefinitionFactory
 * @see net.zuckerfrei.jcfd.Definition
 * @see net.zuckerfrei.jcfd.simple.SimpleDefinition
 * @see net.zuckerfrei.jcfd.Configuration#getDefinitionFactoryClassName()
 */
public abstract class DefinitionFactory {

    //~ Static variables/initializers =========================================

    /** 
     * Factory object, singleton.
     */
    private static DefinitionFactory factory = null;

    /** 
     * Lock object.
     */
    private static Object lock = new Object();

    /** 
     * Class name of the implementation.
     */
    private static String defintionFactoryClassName = Configuration.getInstance().getDefinitionFactoryClassName();

    //~ Methods ===============================================================

    /**
     * Static method for reaching the factory implementation.
     *
     * @return factory implementation.
     */
    public static DefinitionFactory getInstance() {
        if (factory == null) {
            synchronized (lock) {
                if (factory == null) {
                    try {

                        Class c = Class.forName(defintionFactoryClassName);

                        factory = (DefinitionFactory) c.newInstance();
                    }
                    catch (ClassNotFoundException e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    catch (InstantiationException e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    catch (IllegalAccessException e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                }
            }
        }

        return factory;
    }


    /**
     * Creates <code>Definition</code> object. Objects created in this way must be ready for use.
     *
     * @param word for which this definition is.
     * @param database from which this definition is.
     * @param content The very definition fetched from the server.
     *
     * @return Definition ready for use.
     */
    public abstract Definition createDefinition(String word, Database database, Object content);
}
