package net.zuckerfrei.jcfd;

import java.util.Properties;

import net.zuckerfrei.jcfd.simple.SimpleDefinitionFactory;
import net.zuckerfrei.jcfd.simple.SimpleDictFactory;

import junit.framework.TestCase;

/**
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class ConfigurationFromSystemTest extends TestCase {
    
    String host = "testhost";
    String port = "1234";
    String clientFactory = "some.implementation";   // "net.zuckerfrei.jcfd.simple.SimpleDictFactory";
    String definitionFactory = "another.implementation";    //"net.zuckerfrei.jcfd.simple.SimpleDefinitionFactory";
    

    /**
     * Constructor for ConfigurationTest.
     * @param arg0
     */
    public ConfigurationFromSystemTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ConfigurationFromSystemTest.class);
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testSystemProperties() throws Exception {
        
        Properties props = new Properties();
        props.setProperty("dict.server.name", host);
        props.setProperty("dict.server.port", "" + port);
        props.setProperty("dict.clientFactory.implementation", clientFactory);
        props.setProperty("dict.definitionFactory.implementation", definitionFactory);

        System.setProperties(props);
        
        Configuration conf = Configuration.getInstance();
        conf.init();
        
        assertEquals(host, conf.getHost());
        assertEquals(Integer.parseInt(port), conf.getPort());
        assertEquals(clientFactory, conf.getDictClientFactoryClassName());
        assertEquals(definitionFactory, conf.getDefinitionFactoryClassName());
    }
}
