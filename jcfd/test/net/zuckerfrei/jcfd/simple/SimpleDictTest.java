package net.zuckerfrei.jcfd.simple;

import junit.framework.TestCase;

import net.zuckerfrei.jcfd.*;
import net.zuckerfrei.jcfd.DictImpl;
import net.zuckerfrei.jcfd.MockBufferedReader;
import net.zuckerfrei.jcfd.MockDataOutputStream;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class SimpleDictTest
    extends TestCase
{

    //~ Instance variables ====================================================

    /** 
     * DOCUMENT ME! 
     */
    MockSimpleDict dict;

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
    public SimpleDictTest(String arg0) {
        super(arg0);
    }

    //~ Methods ===============================================================

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(SimpleDictTest.class);
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

        String body = firstLine + DictImpl.DEFINITION_LINE_SEPARATOR + secondLine + DictImpl.DEFINITION_LINE_SEPARATOR + thirdLine + DictImpl.DEFINITION_LINE_SEPARATOR + dot + DictImpl.DEFINITION_LINE_SEPARATOR;
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
        try {
            dict = new MockSimpleDict(null, 0);
        }
        catch (Exception de) {
            ;
        }

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
