package net.zuckerfrei.jcfd;




/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public abstract class DictFactory {

    //~ Static variables/initializers =========================================

    /** DOCUMENT ME! */
    private static DictFactory factory = null;

    /** DOCUMENT ME! */
    private static String clientFactoryClassName = Configuration.getInstance().getDictClientFactoryClassName();

    /** DOCUMENT ME! */
    private static final Object lock = new Object();

    //~ Methods ===============================================================

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws IllegalArgumentException DOCUMENT ME!
     */
    public static DictFactory getInstance() {
        if (factory == null) {
            synchronized (lock) {
                if (factory == null) {
                    try {

                        Class c = Class.forName(clientFactoryClassName);

                        factory = (DictFactory) c.newInstance();
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
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DictException DOCUMENT ME!
     */
    public abstract Dict getDictClient()
                          throws DictException;
}
