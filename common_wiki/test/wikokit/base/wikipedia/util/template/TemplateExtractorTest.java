/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikokit.base.wikipedia.util.template;

import wikokit.base.wikipedia.util.template.TemplateExtractor;
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

    
    // testing getFirstTemplate() ----------------------------------------------------- 
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
    public void testGetFirstTemplate_remove_spaces() {
        System.out.println("getFirstTemplate_remove_spaces");
        String text = "text before {{template name|parameter one | two}} text after";
        
        String[] params = {"parameter one", "two"};
        TemplateExtractor expResult = new TemplateExtractor("template name", params, 12, 46);
        
        TemplateExtractor result = TemplateExtractor.getFirstTemplate(text);
        
        // compare only params, since start and last position are different
        String[] result_params = result.getTemplateParameters();
        for(int i=0; i<params.length; i++)
            assertEquals( result_params[i], params[i]);
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
    // ----------------------------------------------------- eo testing getFirstTemplate()
    
  
    
    // testing getFirstTemplateByName() ----------------------------------------------------- 
    @Test
    public void testgetFirstTemplateByName_empty() {
        System.out.println("getFirstTemplateByName_empty");
        String page_title, text0, text0and0, text1;
        
        page_title      = "page_title_is_getFirstTemplateByName_empty";        
        String template_name = "templatus";
        
        text0     = "{{templatus|}}\n";
        text0and0 = "{{templatus|||}}\n";
        text1     = "{{templatus}}";
                
        TemplateExtractor te_first = TemplateExtractor.getFirstTemplateByName(page_title, template_name, text0);
        assertNull(te_first);
                                                                                       
        te_first = TemplateExtractor.getFirstTemplateByName(page_title, template_name, text0and0);
        assertNull(te_first);
        
        te_first = TemplateExtractor.getFirstTemplateByName(page_title, template_name, text1);
        assertNull(te_first);
    }
    
    @Test
    public void testgetFirstTemplateByName_one_param() {
        System.out.println("getFirstTemplateByName_one_param");
        String page_title, text;
        
        page_title      = "page_title_is_getFirstTemplateByName_one_param";
        String template_name = "templatus";
        text = "012345{{templatus|param one}}\n"  +
               "{{templatus|111|222}}";
        
        String[] params = {"param one"};
        TemplateExtractor expResult = new TemplateExtractor(template_name, params, 6, 28);
        
        TemplateExtractor te_first = TemplateExtractor.getFirstTemplateByName(page_title, template_name, text);
        assertNotNull(te_first);
        assertEquals(1, te_first.countTemplateParameters());
        assertTrue( TemplateExtractor.equals( expResult, te_first) );
    }
    // ------------------------------------- eo testing getFirstTemplateByName()
    
    // getAllTemplatesByName ---------------------------------------------------
    @Test
    public void testgetAllTemplatesByName_empty() {
        System.out.println("getAllTemplatesByName_empty");
        String page_title, text;
        
        page_title      = "page_title_is_getAllTemplatesByName_empty";
        String template_name = "absent template";
        text = "012345{{templatus|param one}}\n"  +
               "{{templatus|111|222}}";
        
        TemplateExtractor[] te_all = TemplateExtractor.getAllTemplatesByName(page_title, template_name, text);
        assertNotNull(te_all);
        assertEquals(0, te_all.length);
    }
    
    @Test
    public void testgetAllTemplatesByName_two_templates() {
        System.out.println("getAllTemplatesByName_two_templates");
        String page_title, text, text_without_template, str;
        
        page_title      = "page_title_is_getAllTemplatesByName_two_templates";
        String template_name = "t";
        text = "012345{{t|param one}}\n"  +
               "{{t|111|222}}end";
        text_without_template = "012345\n"  +
                                "end";
        
        TemplateExtractor[] te_all = TemplateExtractor.getAllTemplatesByName(page_title, template_name, text);
        assertNotNull(te_all);
        assertEquals(2, te_all.length);
        
        String[] params1 = {"param one"};
        TemplateExtractor expResult1 = new TemplateExtractor(template_name, params1, 6, 20);
        assertTrue( TemplateExtractor.equals( expResult1, te_all[0]) );
        
        String[] params2 = {"111", "222"};
        TemplateExtractor expResult2 = new TemplateExtractor(template_name, params2, 22, 34);
        assertTrue( TemplateExtractor.equals( expResult2, te_all[1]) );
        
        // remove last template, then remove first template
        str = TemplateExtractor.extractTextWithoutTemplate(text, te_all[1]);
        str = TemplateExtractor.extractTextWithoutTemplate(str,  te_all[0]);
        assertEquals(str, text_without_template);
    }
    // ------------------------------------- eo testing getFirstTemplateByName()
    
    
    
    // getParameterValue -------------------------------------------------------
    
    // {"param1", "param2=value2", ...} get value2 by param2
    @Test
    public void testgetParameterValue() {
        System.out.println("getParameterValue");
        String parameter_name, value;
        
        // ////////////////////////////
        // NULL
        // NULL if this parameter is absent or, there are no values for this parameter.
        
        parameter_name = "my_parameter";
        String[] params_zero = {};
        value = TemplateExtractor.getParameterValue (params_zero, parameter_name);
        assertNull(value);
        
        String[] my_param_absent = {"other param = some value"};
        value = TemplateExtractor.getParameterValue (my_param_absent, parameter_name);
        assertNull(value);
        
        String[] my_param_exist_but_without_value = {"other param = some value", parameter_name+"="};
        value = TemplateExtractor.getParameterValue (my_param_exist_but_without_value, parameter_name);
        assertNull(value);
        
        // NULL
        // ////////////////////////////
        
        String[] my_param_exist = {"other param = some value", parameter_name+"= value 2 "};
        value = TemplateExtractor.getParameterValue (my_param_exist, parameter_name);
        assertEquals(value, "value 2");
    }
    // ------------------------------------------ eo testing getParameterValue()
    
    
    // getParameterValueByNameOrNumber -----------------------------------------
    // {"param1", "param2=value2", ...} get value2 by param2
    @Test
    public void testgetParameterValueByNameOrNumber() {
        System.out.println("getParameterValueByNameOrNumber");
        String parameter_name, value;
        int parameter_number;
        
        parameter_name = "param2";
        
        // get second parameter by name
        String[] my_param_named = {"param1 value without name", parameter_name+"= value 2 "};
        value = TemplateExtractor.getParameterValueByNameOrNumber (my_param_named, parameter_name, 2);
        assertEquals(value, "value 2");
        
        // get second parameter by number
        String[] my_param_without_name = {"param1 value without name", " value 2 "};
        value = TemplateExtractor.getParameterValueByNameOrNumber (my_param_without_name, parameter_name, 2);
        assertEquals(value, "value 2");
    }
    // ---------------------------- eo testing getParameterValueByNameOrNumber()
}