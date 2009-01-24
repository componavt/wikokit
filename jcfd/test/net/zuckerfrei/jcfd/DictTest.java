package net.zuckerfrei.jcfd;

import junit.framework.TestCase;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class DictTest
    extends TestCase
{

    //~ Instance variables ====================================================

    /** 
     * DOCUMENT ME! 
     */
    MockDict dict;

    /** 
     * DOCUMENT ME! 
     */
    MockBufferedReader br;

    /** 
     * DOCUMENT ME! 
     */
    MockDataOutputStream dos;

    /** 
     * DOCUMENT ME! 
     */
    String firstLine = "First line";

    /** 
     * DOCUMENT ME! 
     */
    String secondLine = "Second line";

    /** 
     * DOCUMENT ME! 
     */
    String thirdLine = "Third line";

    /** 
     * DOCUMENT ME! 
     */
    String dot = ".";

    //~ Constructors ==========================================================

    /**
     * Constructor for DictTest.
     *
     * @param arg0
     */
    public DictTest(String arg0) {
        super(arg0);
    }

    //~ Methods ===============================================================

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(DictTest.class);
    }


    /**
     * DOCUMENT ME!
     */
    public void testClose() {
    }


    /*
     * Test for DefinitionList define(String)
     */
    public void testDefineString() {
    }


    /*
     * Test for DefinitionList define(String, Database)
     */
    public void testDefineStringDatabase() {
    }


    /*
     * Test for DefinitionList define(String, boolean)
     */
    public void testDefineStringZ() {
    }


    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testFindDatabasesNoDatabasesFound()
                                           throws Exception
    {
        setContent(new String[] {
                       "250 ok",
                       "554 No databases found",
                       "database db",
                       "."
                   });
        try {
            dict.fillDatabaseList();
            fail("Shoud fail with no databases found");
        }
        catch (NoDatabasesException nde) {

            // ok
        }
    }


    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testFindDatabasesWrongResponse()
                                        throws Exception
    {
        try {
            setContent(new String[] {
                           "A wrong response",
                           "554 No databases found",
                           "database db",
                           "."
                       });
            dict.fillDatabaseList();
            fail("Should fail on invalid response");
        }
        catch (InvalidResponseException ire) {

            // everything ok
            return;
        }
    }


    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testFlushResponse()
                           throws Exception
    {

        String afterDot = "after dot";

        setContent(new String[] {
                       firstLine,
                       secondLine,
                       thirdLine,
                       dot,
                       afterDot
                   });

        // skip first line
        dict.readResponse();
        assertEquals("Expecting readLineCount = 1", 1, br.readLineCount);
        dict.flushResponse();
        assertEquals("Expecting readLineCount = 4", 4, br.readLineCount);
        assertEquals("Expectinf " + afterDot,
                     afterDot,
                     dict.readResponse());
    }


    /*
     * Test for MatchList match(String)
     */
    public void testMatchString() {
    }


    /*
     * Test for MatchList match(String, Database)
     */
    public void testMatchStringDatabase() {
    }


    /*
     * Test for MatchList match(String, Strategy)
     */
    public void testMatchStringStrategy() {
    }


    /*
     * Test for MatchList match(String, Strategy, Database)
     */
    public void testMatchStringStrategyDatabase() {
    }


    /*
     * Test for MatchList match(String, Strategy, boolean)
     */
    public void testMatchStringStrategyZ() {
    }


    /*
     * Test for MatchList match(String, boolean)
     */
    public void testMatchStringZ() {
    }


    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testReadBody()
                      throws Exception
    {
        setContent(new String[] {
                       firstLine,
                       secondLine,
                       thirdLine,
                       dot
                   });

        String body = firstLine + Dict.DEFINITION_LINE_SEPARATOR + secondLine + Dict.DEFINITION_LINE_SEPARATOR + thirdLine + Dict.DEFINITION_LINE_SEPARATOR + dot + Dict.DEFINITION_LINE_SEPARATOR;
        String dictBody = dict.readBody();

        assertEquals("Unexpected body", body, dictBody);

    }


    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void testReadResponse()
                          throws Exception
    {
        setContent(new String[] {
                       firstLine,
                       secondLine
                   });
        assertEquals("Expecting " + firstLine,
                     firstLine,
                     dict.readResponse());
        assertEquals("Expecting " + secondLine,
                     secondLine,
                     dict.readResponse());
    }


    /**
     * @see TestCase#setUp()
     */
    protected void setUp()
                  throws Exception
    {
        dict = new MockDict(null, 0);
        br = new MockBufferedReader(null);
        dos = new MockDataOutputStream(null);
        dict.br = br;
        dict.dos = dos;
    }


    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown()
                     throws Exception
    {
        br = null;
        dict = null;
    }


    /**
     * DOCUMENT ME!
     *
     * @param content DOCUMENT ME!
     */
    private void setContent(String[] content) {
        br.setContent(content);
    }
}
