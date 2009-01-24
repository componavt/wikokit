package net.zuckerfrei.jcfd;

import junit.framework.Test;
import junit.framework.TestSuite;

import net.zuckerfrei.jcfd.simple.SimpleDictTest;

/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class AllTests {

    //~ Methods ===============================================================

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Test suite() {

        TestSuite suite = new TestSuite("Test for net.zuckerfrei.jcfd");

        //$JUnit-BEGIN$
        suite.addTest(new TestSuite(DefinitionListTest.class));
        suite.addTest(new TestSuite(DictTest.class));
        suite.addTest(new TestSuite(MatchListTest.class));
        suite.addTest(new TestSuite(ResponseTest.class));
        suite.addTest(new TestSuite(ConfigurationTest.class));        
        suite.addTest(new TestSuite(ConfigurationFromSystemTest.class));
        //$JUnit-END$
        return suite;
    }
}
