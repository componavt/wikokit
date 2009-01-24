package net.zuckerfrei.jcfd;

import java.util.Properties;
import java.util.ResourceBundle;

import net.zuckerfrei.jcfd.simple.SimpleDefinitionFactory;
import net.zuckerfrei.jcfd.simple.SimpleDictFactory;

import junit.framework.TestCase;

/**
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class ConfigurationTest extends TestCase {
    
    String host = "testhost";
    String port = "1234";
    String clientFactory = "some.implementation";   // "net.zuckerfrei.jcfd.simple.SimpleDictFactory";
    String definitionFactory = "another.implementation";    //"net.zuckerfrei.jcfd.simple.SimpleDefinitionFactory";
    

    /**
     * Constructor for ConfigurationTest.
     * @param arg0
     */
    public ConfigurationTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ConfigurationTest.class);
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
    
    
    public void testDefaultProperties() throws Exception {
        Properties props = new Properties();
        props.setProperty("dict.server.name", host);
        //props.setProperty("dict.server.port", null);
        //props.setProperty("dict.clientFactory.implementation", null);
        //props.setProperty("dict.definitionFactory.implementation", null);
        
           
        System.setProperties(props);
        
        Configuration conf = Configuration.getInstance();
        conf.setBundle(null);
        conf.init();
        
        assertEquals(host, conf.getHost());
        assertEquals(Configuration.DEFAULT_PORT, conf.getPort());
        assertEquals(Configuration.DEFAULT_DICT_CLIENT_FACTORY_CLASS_NAME, conf.getDictClientFactoryClassName());
        assertEquals(Configuration.DEFAULT_DEFINITION_FACTORY_CLASS_NAME, conf.getDefinitionFactoryClassName());
     
    }
    
    public void testBundleProperties() throws Exception {
        // create a resource bundle
        ResourceBundle bundle = new MockResourceBundle(host, port, clientFactory, definitionFactory);
        
        // feed the configurator
        Configuration conf = Configuration.getInstance();
        conf.setBundle(bundle);
        conf.init();
        
        // test the properties
        assertEquals(host, conf.getHost());
        assertEquals(Integer.parseInt(port), conf.getPort());
        assertEquals(clientFactory, conf.getDictClientFactoryClassName());
        assertEquals(definitionFactory, conf.getDefinitionFactoryClassName());
        
    }
    

}
