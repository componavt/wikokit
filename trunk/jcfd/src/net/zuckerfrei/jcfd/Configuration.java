package net.zuckerfrei.jcfd;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Global configuration point with default values. Reads System properties and <code>jcfd.properties</code> configuration file.
 * This class is statically initialized with the
 * following values:<br>
 * <table border="1">
 *  <tr>
 *      <th>Property</th>
 *      <th>Property name</th>
 *      <th>Default value</th>
 *  </tr>
 *  <tr>
 *      <td>DICT server host</td>
 *      <td>dict.server.host</td>
 *      <td>www.dict.org</td>
 *  </tr>
 *  <tr>
 *      <td>DICT server port</td>
 *      <td>dict.server.port</td>
 *      <td>2628</td>
 *  </tr>
 *  <tr>
 *      <td>DICT client factory</td>
 *      <td>dict.clientFactory.implementation</td>
 *      <td>net.zuckerfrei.jcfd.simple.SimpleDictFactory</td>
 *  </tr>
 *  <tr>
 *      <td>DICT definition factory</td>
 *      <td>dict.definitionFactory.implementation</td>
 *      <td>net.zuckerfrei.jcfd.simple.SimpleDefinitionFactory</td>
 *  </tr>
 * </table><br>
 * System properties are given using standard JVM -D switch, e.g. <code>java -Ddict.server.host=www.dict.org</code> and have presedence over
 * jcfd.properties configuration file. If no system property for a certain value is defined or there's
 * no key entry in jcfd.properties or there's no jcfd.properties at all, then the default values are used.
 * 
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class Configuration
    implements Serializable
{

    static Log log = LogFactory.getLog(Configuration.class);

    //~ Static variables/initializers =========================================
    
    /**
     * Default server hostname, www.dict.org
     */
    public static final String DEFAULT_HOST = "www.dict.org";

    /** Default port, 2628, as defined by RFC, to be used if no explicit port is configuerd.. */
    public static final int DEFAULT_PORT = 2628;
    
    /** Default factory implementation to be used if no client factory class is configured. */
    public static final String DEFAULT_DICT_CLIENT_FACTORY_CLASS_NAME = "net.zuckerfrei.jcfd.simple.SimpleDictFactory"; //$NON-NLS-1$ 

    /** Default definition factory implementation to be used if no factory class is configured. */
    public static final String DEFAULT_DEFINITION_FACTORY_CLASS_NAME = "net.zuckerfrei.jcfd.simple.SimpleDefinitionFactory"; //$NON-NLS-1$

    static final String BUNDLE_NAME = "jcfd";
    static final String SERVER_NAME_KEY = "dict.server.name";
    static final String SERVER_PORT_KEY = "dict.server.port";
    static final String CLIENT_FACTORY_KEY = "dict.clientFactory.implementation";
    static final String DEFINITION_FACTORY_KEY = "dict.definitionFactory.implementation";
    

    /** Host to connect to. */
    private static String host = null;

    /** Port in use. */
    private static int port = 0;

    /** Definition factory class name in use. */
    private static String definitionFactoryClassName = null;

    /** Client factory class name in use */
    private static String clientFactoryClassName = null;
    
    //~ Constructors ==========================================================

    private static Configuration conf = new Configuration();    
    
    private static ResourceBundle bundle = null;
        

    /**
     * Creates a new Configuration object.
     */
    private Configuration() {
        init();
    }
    
    public static Configuration getInstance() {
        return conf;
    }    
    
    void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    void init() {
        try {
            if (bundle == null) {
                bundle = ResourceBundle.getBundle(BUNDLE_NAME);
            }
        }
        catch (MissingResourceException mre) {
            if (log.isInfoEnabled()) {
                log.info("No configuration file, " + mre.getMessage());
            }
        }
    
        setHost(bundle);
        setPort(bundle);
        setClientFactoryClassName(bundle);
        setDefinitionFactoryClassName(bundle);
    
    }
    
    private void setHost(ResourceBundle bundle) {
        host = System.getProperty(SERVER_NAME_KEY);
        try {
            if (host == null) {
                host = bundle.getString(SERVER_NAME_KEY);
            }
        }
        catch (RuntimeException rte) {
            host = DEFAULT_HOST;
        }
    }
    
    private void setPort(ResourceBundle bundle) {
        String tmpPort = System.getProperty(SERVER_PORT_KEY);
        try {
            if (tmpPort == null) {
                tmpPort = bundle.getString(SERVER_PORT_KEY);
            }
            port = Integer.parseInt(tmpPort);
        }
        catch (RuntimeException rte) {
            port = DEFAULT_PORT;
        }
    }
    
    private void setClientFactoryClassName(ResourceBundle bundle) {
        clientFactoryClassName = System.getProperty(CLIENT_FACTORY_KEY);
        try {
            if (clientFactoryClassName == null) {
                clientFactoryClassName = bundle.getString(CLIENT_FACTORY_KEY);
            }
        }
        catch (RuntimeException rte) {
            clientFactoryClassName = DEFAULT_DICT_CLIENT_FACTORY_CLASS_NAME;
        }
    }
    
    private void setDefinitionFactoryClassName(ResourceBundle bundle) {
        definitionFactoryClassName = System.getProperty(DEFINITION_FACTORY_KEY);
        try {
            if (definitionFactoryClassName == null) {
                definitionFactoryClassName = bundle.getString(DEFINITION_FACTORY_KEY);
            }
        }
        catch (RuntimeException e) {
            definitionFactoryClassName = DEFAULT_DEFINITION_FACTORY_CLASS_NAME;
        }
    }
    

    //~ Methods ===============================================================

    /**
     * Returns the definition factory class name to be used.
     *
     * @return String name of the factory class.
     */
    public String getDefinitionFactoryClassName() {
        return definitionFactoryClassName;
    }

    /**
     * Returns the client factory class name to be used.
     * 
     * @return String name of the factory class.
     */
    public String getDictClientFactoryClassName() {
        return clientFactoryClassName;   
    }

    /**
     * Returns the host name to which to connect.
     *
     * @return String the host name.
     */
    public String getHost() {
        return host;
    }


    /**
     * Returns the port to connect to.
     *
     * @return int the port to connect to.
     */
    public int getPort() {
        return port;
    }
                
}
