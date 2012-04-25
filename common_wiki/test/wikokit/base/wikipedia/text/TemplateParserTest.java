
package wikokit.base.wikipedia.text;

import org.junit.*;
import static org.junit.Assert.*;

public class TemplateParserTest {
    
    public TemplateParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

  
    @Test
    public void testExpandTemplate() {
        System.out.println("expandTemplate");
        
        // "abc{{template_name|param1|param2}}xyz" -> "abcparam1.param2xyz" 
        String source_text = "abc{{template_name|param1|param2}}xyz";
        String template_name = "template_name";
        String target = "|";
        String replacement = ".";
        String expResult = "abcparam1.param2xyz";
        String result = TemplateParser.expandTemplateParams(source_text, template_name, target, replacement);
        assertEquals(expResult, result);
    }
    
    /* Search "CapsTemplate" in "the text with {{capstemplate|}}".
     */
    @Test
    public void testExpandTemplate_DifferentCase() {
        System.out.println("expandTemplate");
        
        // "abc{{template_name|param1|param2}}xyz" -> "abcparam1.param2xyz" 
        String source_text = "ABC{{template_NAME|Param1|paraM2}}xyz";
        String template_name = "Template_name";
        String target = "|";
        String replacement = ".";
        String expResult = "ABCParam1.paraM2xyz";
        String result = TemplateParser.expandTemplateParams(source_text, template_name, target, replacement);
        assertEquals(expResult, result);
    }
}
