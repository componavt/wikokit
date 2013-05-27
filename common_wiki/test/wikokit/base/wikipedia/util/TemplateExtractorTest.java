/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikokit.base.wikipedia.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TemplateExtractorTest {
    
    private final static String[]       NULL_STRING_ARRAY = new String[0];
    
    public TemplateExtractorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetFirstTemplate_empty_text() {
        System.out.println("getFirstTemplate");
        String text = "template is absent";
        
        TemplateExtractor result = TemplateExtractor.getFirstTemplate(text);
        assertNull(result);
        
        text = "empty template {{}} should return NULL";
        result = TemplateExtractor.getFirstTemplate(text);
        assertNull(result);
        
        text = "template {{|without name|only parameters}} should return NULL";
        result = TemplateExtractor.getFirstTemplate(text);
        assertNull(result);
    }
    
    @Test
    public void testGetFirstTemplate_without_parameters() {
        System.out.println("getFirstTemplate_without_parameters");
        String text = "text before {{template name}} text after"; // => name = "template name"
        
        String[] params = NULL_STRING_ARRAY;
        TemplateExtractor expResult = new TemplateExtractor("template name", params, 12, 28);
        
        TemplateExtractor result = TemplateExtractor.getFirstTemplate(text);
        assertTrue( TemplateExtractor.equals( expResult, result) );
    }
    
    @Test
    public void testGetFirstTemplate_one_param() {
        System.out.println("getFirstTemplate_one_param");
        String text = "text before {{template name|parameter one}} text after";
        
        String[] params = {"parameter one"};
        TemplateExtractor expResult = new TemplateExtractor("template name", params, 12, 42);
        
        TemplateExtractor result = TemplateExtractor.getFirstTemplate(text);
        assertTrue( TemplateExtractor.equals( expResult, result) );
    }
    
    @Test
    public void testGetFirstTemplate_two_params() {
        System.out.println("getFirstTemplate_two_params");
        String text = "text before {{template name|parameter one|two}} text after";
        
        String[] params = {"parameter one", "two"};
        TemplateExtractor expResult = new TemplateExtractor("template name", params, 12, 46);
        
        TemplateExtractor result = TemplateExtractor.getFirstTemplate(text);
        assertTrue( TemplateExtractor.equals( expResult, result) );
    }
    // {{template name|parameter one|parameter two|...}}
}