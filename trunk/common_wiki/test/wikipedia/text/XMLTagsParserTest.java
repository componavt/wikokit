/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wikipedia.text;

import junit.framework.TestCase;

/**
 *
 * @author andrew
 */
public class XMLTagsParserTest extends TestCase {
    
    public XMLTagsParserTest(String testName) {
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
        
    public void testIsAmpersandTag(){
        System.out.println("isAmpersandTag");
        String source, empty, should_be, border_failed, border_success;
        String ampersand_tag;
        
        XMLTagsParser.isAmpersandTag(null, 1);
        
        empty = "";
        ampersand_tag   = XMLTagsParser.isAmpersandTag(empty,0);
        assertEquals(0, ampersand_tag.length());
        
        empty = "hello";
        ampersand_tag   = XMLTagsParser.isAmpersandTag(empty,-1);
        assertEquals(0, ampersand_tag.length());
        ampersand_tag   = XMLTagsParser.isAmpersandTag(empty,1);
        assertEquals(0, ampersand_tag.length());
        
        border_failed  = "&lt";
        border_success = "&lt;";
        ampersand_tag   = XMLTagsParser.isAmpersandTag(border_failed,0);
        assertEquals(0, ampersand_tag.length());
        ampersand_tag   = XMLTagsParser.isAmpersandTag(border_success,0);
        assertEquals("&lt;", ampersand_tag);
        
        // "less &lt; greater &gt; amp &amp; quot &quot; one symbol &#039;";
        source = "x&gt;&";
        should_be = "&gt;";
        ampersand_tag = XMLTagsParser.isAmpersandTag(source,1);
        assertEquals(should_be, ampersand_tag);
        
        // failed boundary
        source = "x&&quot";
        ampersand_tag = XMLTagsParser.isAmpersandTag(source,2);
        assertEquals(0, ampersand_tag.length());
        
        source = "x&&quot;";
        should_be = "&quot;";
        ampersand_tag = XMLTagsParser.isAmpersandTag(source,2);
        assertEquals(should_be, ampersand_tag);
        
        source = "&ndash; space &mdash;";
        should_be = "&ndash;";
        ampersand_tag = XMLTagsParser.isAmpersandTag(source,0);
        assertEquals(should_be, ampersand_tag);
        should_be = "&mdash;";
        ampersand_tag = XMLTagsParser.isAmpersandTag(source,14);
        assertEquals(should_be, ampersand_tag);
    }
        
    public void testIsNonBreakingSpaceTag(){
        System.out.println("isNonBreakingSpaceTag");
        String br_tag;
        
        // "<br />,<br/>,<br>"
        br_tag = XMLTagsParser.isBRNewlineTag("<br />,<br/>,<br>", 0);
        assertEquals("<br />", br_tag);
        
        br_tag = XMLTagsParser.isBRNewlineTag("<br />,<br/>,<br>", 1);
        assertEquals(0, br_tag.length());
        
        br_tag = XMLTagsParser.isBRNewlineTag("<br />,<br/>,<br>", 7);
        assertEquals("<br/>", br_tag);
        
        br_tag = XMLTagsParser.isBRNewlineTag("<br />,<br/>,<br>", 13);
        assertEquals("<br>", br_tag);
        
        
        // near the end
        // a) longest: <br />
        br_tag = XMLTagsParser.isBRNewlineTag("<br /", 0);
        assertEquals(0, br_tag.length());
        
        br_tag = XMLTagsParser.isBRNewlineTag("<br />", 0);
        assertEquals("<br />", br_tag);
        
        // b) shortest: <br>
        br_tag = XMLTagsParser.isBRNewlineTag("<br", 0);
        assertEquals(0, br_tag.length());
        
        br_tag = XMLTagsParser.isBRNewlineTag("<br>", 0);
        assertEquals("<br>", br_tag);
    }

    
    public void testEscapeCharFromXML(){
        System.out.println("escapeCharFromXML");
        String unescaped, empty, should_be;
        String escaped;
        
        XMLTagsParser.escapeCharFromXML(null);
        
        empty = "";
        escaped   = XMLTagsParser.escapeCharFromXML(empty);
        assertEquals(0, escaped.length());
        
        // "less < greater > amp & quot \"" ->
        // "less &lt; greater &gt; amp &amp; quot &quot; ' &#039;"
        unescaped = "less < greater > amp & quot \" one symbol '";
        should_be = "less &lt; greater &gt; amp &amp; quot &quot; one symbol &#039;";
        escaped   = XMLTagsParser.escapeCharFromXML(unescaped);
        assertEquals(should_be, escaped.toString());
        
        // "&lt;" -> "&lt;" second pass should not change anything
        escaped   = XMLTagsParser.escapeCharFromXML(escaped);
        assertEquals(should_be, escaped.toString());
    }

    
    public void testReplaceCharFromXML(){
        System.out.println("replaceCharFromXML");
        String unescaped, empty, should_be;
        String escaped;
        
        XMLTagsParser.replaceCharFromXML(null, ' ');
        
        empty = "";
        escaped   = XMLTagsParser.replaceCharFromXML(empty, ' ');
        assertEquals(0, escaped.length());
        
        // "<>" -> "  "
        unescaped = "<1>2&lt;3&amp;4&nbsp; non-breaking space";
        should_be = " 1 2 3 4  non-breaking space";
        escaped   = XMLTagsParser.replaceCharFromXML(unescaped, ' ');
        assertEquals(should_be, escaped.toString());
        
        // "less < greater > amp & quot \"" ->
        // "less &lt; greater &gt; amp &amp; quot &quot; ' &#039;"
        unescaped = "< greater > amp & quot \" one symbol '";
        should_be = "  greater   amp   quot   one symbol '";
        escaped   = XMLTagsParser.replaceCharFromXML(unescaped, ' ');
        assertEquals(should_be, escaped.toString());
        
        // second pass should not change anything
        escaped   = XMLTagsParser.replaceCharFromXML(escaped, ' ');
        assertEquals(should_be, escaped.toString());
        
        // &ndash; space &mdash;
        unescaped = "&ndash; space &mdash;";
        should_be =       "  space  ";
        escaped   = XMLTagsParser.replaceCharFromXML(unescaped, ' ');
        assertEquals(should_be, escaped.toString());
    }
    
    // <br /> <br/> <br> should be replaced by "\n"
    public void testReplaceCharFromXML_nbsp(){
        System.out.println("replaceCharFromXML_nbsp");
        String unescaped, empty, should_be;
        String escaped;
        
        // "<br />1<br/>2<br>3" -> "\n1\n2\n3"
        unescaped = "<br />1<br/>2<br>3";
        should_be = "\n1\n2\n3";
        escaped   = XMLTagsParser.replaceCharFromXML(unescaped, ' ');
        assertEquals(should_be, escaped.toString());
        
        // "<br />1<br/>2<br><br/>3<br/ joke><br />" -> "\n1\n2\n\n3<br/ joke>\n"
        unescaped = "<br />1<br/>2<br><br/>3<br/ joke><br />";
        should_be = "\n1\n2\n\n3 br/ joke \n";
        escaped   = XMLTagsParser.replaceCharFromXML(unescaped, ' ');
        assertEquals(should_be, escaped.toString());
    }

}
