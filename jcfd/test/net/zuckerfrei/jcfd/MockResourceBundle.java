package net.zuckerfrei.jcfd;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class MockResourceBundle extends ResourceBundle {
    
    String hostname;
    String port;
    String clientFactory;
    String definitionFactory;

    /**
     * Constructor for MockResourceBundle.
     */
    public MockResourceBundle(String hostname, String port, String clientFactory, String definitionFactory) {
        super();
        this.hostname = hostname;
        this.port = port;
        this.clientFactory = clientFactory;
        this.definitionFactory = definitionFactory;
    }

    /**
     * @see java.util.ResourceBundle#handleGetObject(String)
     */
    protected Object handleGetObject(String key) throws MissingResourceException {
        if (key.equals(Configuration.SERVER_NAME_KEY)) {
            return hostname;
        }
        else if (key.equals(Configuration.SERVER_PORT_KEY)) {
            return port;
        }
        else if (key.equals(Configuration.CLIENT_FACTORY_KEY)) {
            return clientFactory;
        }
        else if (key.equals(Configuration.DEFINITION_FACTORY_KEY)) {
            return definitionFactory;
        }

        return null;
    }

    /**
     * @see java.util.ResourceBundle#getKeys()
     */
    public Enumeration getKeys() {
        return null;
    }

    public static void main(String[] args) {
    }
}
