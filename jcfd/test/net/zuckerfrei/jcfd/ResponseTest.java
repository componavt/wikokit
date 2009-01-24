package net.zuckerfrei.jcfd;

import junit.framework.TestCase;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class ResponseTest
    extends TestCase
{

    //~ Constructors ==========================================================

    /**
     * Constructor for ResponseTest.
     *
     * @param arg0
     */
    public ResponseTest(String arg0) {
        super(arg0);
    }

    //~ Methods ===============================================================

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(ResponseTest.class);
    }


    /**
     * DOCUMENT ME!
     */
    public void testDefinitionFolllows() {
    }


    /**
     * DOCUMENT ME!
     */
    public void testDefinitionsCountFollows() {
    }


    /**
     * DOCUMENT ME!
     */
    public void testFindCount() {

        String result = "should throw an exception on this";

        try {
            Response.findCount(result);
            fail(result);
        }
        catch (NumberFormatException nfe) {

            // good boy
        }

        result = "150 1 definition follow(s)";
        assertEquals("expecting 1",
                     1,
                     Response.findCount(result));

        result = "150 5 definition";
        assertEquals("expecting 5",
                     5,
                     Response.findCount(result));

    }


    /**
     * DOCUMENT ME!
     */
    public void testInvalidDatabase() {
    }


    /**
     * DOCUMENT ME!
     */
    public void testInvalidStrategy() {
    }


    /**
     * DOCUMENT ME!
     */
    public void testIsConnected() {
    }


    /**
     * DOCUMENT ME!
     */
    public void testIsOk() {
    }


    /**
     * DOCUMENT ME!
     */
    public void testNoDatabases() {

        String result = "554 No databases present";

        assertTrue(Response.noDatabases(result));
    }


    /**
     * DOCUMENT ME!
     */
    public void testNoMatch() {
    }


    /**
     * @see TestCase#setUp()
     */
    protected void setUp()
                  throws Exception
    {
        super.setUp();
    }


    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown()
                     throws Exception
    {
        super.tearDown();
    }
    
    public void testQuotedString() throws Exception {
        String test = "This is a test string";
        String[] expected = {"This","is","a","test","string"};
        String[] output = Response.splitQuotedString(test, '"');
        for (int i = 0; i < output.length; i++) {
            assertEquals(expected[i], output[i]);
        }
    }
    
    public void testQuotedStringWithDelimiter() throws Exception {
        String test = "This is \"a quoted string\" within a string";
        String[] expected = {"This", "is", "a quoted string", "within", "a", "string"};
        String[] output = Response.splitQuotedString(test, '"');
        for (int i = 0; i < output.length; i++) {
            assertEquals(expected[i], output[i]);
        }
    }        
}
