
package wikokit.base.wikipedia.util.template;

import wikokit.base.wikipedia.util.template.TemplateParser;
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
    
    
    /* Search closing brackets "}}" in 1) "start }} end".
     *                                 2) "start {{templatos}} lala }} end".
     */
    @Test
    public void testindexOfClosingBracketsSameLevel() {
        System.out.println("indexOfClosingBracketsSameLevel");
        
        String source_text = "start }} end";
        int pos = 6; // length of "start "
        int result_pos = TemplateParser.indexOfClosingBracketsSameLevel(source_text, 0, "{{", "}}");
        assertEquals(pos, result_pos);
        
        source_text = "start {{ absent}} end";
        pos = -1;
        result_pos = TemplateParser.indexOfClosingBracketsSameLevel(source_text, 0, "{{", "}}");
        assertEquals(pos, result_pos);
        
        source_text = "start {{ absent}} end }}";
        pos = source_text.length() - 2;
        result_pos = TemplateParser.indexOfClosingBracketsSameLevel(source_text, 0, "{{", "}}");
        assertEquals(pos, result_pos);
    }
}
