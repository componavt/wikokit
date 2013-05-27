package wikokit.base.wikt.multi.ru.name;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.multi.en.name.LabelEn;
import wikokit.base.wikt.util.LabelText;

public class LabelRuTest {
    
    private final static Label[] NULL_LABEL_ARRAY = new Label[0];
    
    public LabelRuTest() {
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

    // ///////////////////////////////////////////////////////////
    // extractLabelsTrimText
    
    @Test
    public void testExtractLabelsTrimText_without_template_labels() {
        System.out.println("extractLabelsTrimText_without_template_labels");
        
        String line = "text without any labels and templates";
        
        Label[] _labels = NULL_LABEL_ARRAY;
        LabelText expResult = new LabelText(_labels, line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        assertTrue( LabelText.equals( expResult, result) );
    }
    
    @Test
    public void testExtractLabelsTrimText_with_template_but_not_a_valid_label() {
        System.out.println("extractLabelsTrimText_with_template_but_not_a_valid_label");
        
        String line = "text {{with unknown template, but it is not a valid labul}} sure";
        
        Label[] _labels = NULL_LABEL_ARRAY;
        LabelText expResult = new LabelText(_labels, line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        assertTrue( LabelText.equals( expResult, result) );
    }
    
    @Test
    public void testExtractLabelsTrimText_with_one_context_label() {
        System.out.println("extractLabelsTrimText_with_one_context_label");
        
        String line        = "{{амер.}} [[самолёт]], [[аэроплан]]"; // http://ru.wiktionary.org/wiki/airplane
        String result_line =           "[[самолёт]], [[аэроплан]]";
        
        boolean label_en_ru = Label.equals( LabelEn.US, LabelRu.US);
        
        Label[] _labels = { LabelEn.US, LabelRu.US };
        LabelText expResult = new LabelText(_labels, result_line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        assertTrue( LabelText.equals( expResult, result) );
    }
    
    // eo extractLabelsTrimText
    // ///////////////////////////////////////////////////////////
}