package wikipedia.text;

import junit.framework.TestCase;

public class TableParserTest extends TestCase {
    
    public TableParserTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of removeWikiTables method, of class TableParser.
     */
    public void testRemoveWikiTables() {
        System.out.println("removeWikiTables");
        StringBuffer text, expResult, result;
        
        // simple
        text    = new StringBuffer("a {| text1 \n text2 |} b");
        expResult = new StringBuffer("a  b");
        result = TableParser.removeWikiTables(text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // nested tables {| {| |} |}
        text    = new StringBuffer("c {| text1 \n {| table2 \n|}|} d");
        expResult = new StringBuffer("c  d");
        result = TableParser.removeWikiTables(text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // nested tables and sequence of tables
        text    = new StringBuffer("c {| text1 \n {| table2 \n|}|} d {| text3 |} e");
        expResult = new StringBuffer("c  d  e");
        result = TableParser.removeWikiTables(text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }

}
