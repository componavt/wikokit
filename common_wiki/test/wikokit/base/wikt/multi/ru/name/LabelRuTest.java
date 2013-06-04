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
        assertTrue(label_en_ru);
        
        Label[] _labels = { LabelEn.US };
        LabelText expResult = new LabelText(_labels, result_line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        assertTrue( LabelText.equals( expResult, result) );
    }
    
    // {{амер.|en}} one label and one unusable parameter
    @Test
    public void testExtractLabelsTrimText_with_one_context_label_and_one_unusable_parameter() {
        System.out.println("extractLabelsTrimText_with_one_context_label_and_one_unusable_parameter");
        
        String line        = "{{амер.|en}} [[самолёт]], [[аэроплан]] {{this template should remain in text}}"; // http://ru.wiktionary.org/wiki/airplane
        String result_line =              "[[самолёт]], [[аэроплан]] {{this template should remain in text}}";
        
        Label[] _labels = { LabelEn.US };
        LabelText expResult = new LabelText(_labels, result_line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        assertTrue( LabelText.equals( expResult, result) );
    }
    
    // {{устар.}}, {{рег.}} род лёгкой сохи, плужка {{Даль|толкование}}
    @Test
    public void testExtractLabelsTrimText_with_two_context_labels_and_one_template_at_the_end_of_text() {
        System.out.println("extractLabelsTrimText_with_two_context_labels_and_one_template_at_the_end_of_text");
        
        String line        = "{{устар.}}, {{рег.}} род лёгкой сохи, плужка {{Даль|толкование}}"; // http://ru.wiktionary.org/wiki/самолёт
        String result_line =                      "род лёгкой сохи, плужка {{Даль|толкование}}";
        
        Label[] _labels = { LabelEn.obsolete, LabelEn.regional };
        LabelText expResult = new LabelText(_labels, result_line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        assertTrue( LabelText.equals( expResult, result) );
    }
    
    // ///////////////////////////////////////////////////////////
    // eo getPometaLabel
    
    // {{помета|разг.}} [[что]]    // "разг." == LabelEn.colloquial
    @Test
    public void testExtractLabelsTrimText_with_pometa_and_known_label() {
        System.out.println("extractLabelsTrimText_with_pometa_and_known_label");
        
        String line        = "{{помета|разг.}} [[что]]";
        String result_line =                   "[[что]]";
        
        Label[] exp_labels = { LabelEn.colloquial };
        LabelText expResult = new LabelText(exp_labels, result_line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        assertTrue( LabelText.equals( expResult, result) );
    }
    
    // {{помета|nocolor=1|разг.}} [[что]]    // "разг." == LabelEn.colloquial
    @Test
    public void testExtractLabelsTrimText_with_nocolor_and_pometa_and_known_label() {
        System.out.println("extractLabelsTrimText_with_nocolor_and_pometa_and_known_label");
        
        String line        = "{{помета|nocolor=1|разг.}} [[что]]";
        String result_line =                             "[[что]]";
        
        Label[] exp_labels = { LabelEn.colloquial };
        LabelText expResult = new LabelText(exp_labels, result_line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        assertTrue( LabelText.equals( expResult, result) );
    }
    
    // {{помета|unknown context label}} [[что]]
    @Test
    public void testExtractLabelsTrimText_with_pometa_and_unknown_label() {
        System.out.println("extractLabelsTrimText_with_pometa_and_unknown_label");
        
        String line        = "{{помета|unknown context label}} [[что]]";
        String result_line =                                  "[[что]]";
        
        // Label[] exp_labels = { LabelEn.colloquial };
        // LabelText expResult = new LabelText(exp_labels, result_line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        //assertTrue( LabelText.equals( expResult, result) );
        assertEquals( result.getText(), result_line);
        
        assertEquals( 1, result.getLabels().length);
        Label result_label = result.getLabels()[0];
        assertEquals( "unknown context label", result_label.getShortName());
        assertEquals(                          result_label.getName().length(), 0);
        assertEquals( result_label.getAddedByHand(), false); // added automatically
        
        // parsing the same unknown label again:
        result = LabelRu.extractLabelsTrimText(line);
        assertEquals( 1, result.getLabels().length); // this is the same new added label
    }
    
    // {{помета|nocolor=1|unknown2 another context label}} [[что]]
    @Test
    public void testExtractLabelsTrimText_with_pometa_nocolor_and_unknown_label() {
        System.out.println("extractLabelsTrimText_with_pometa_nocolor_and_unknown_label");
        
        String line        = "{{помета|nocolor=1|unknown2 another context label}} [[что]]";
        String result_line =                                                     "[[что]]";
        
        // Label[] exp_labels = { LabelEn.colloquial };
        // LabelText expResult = new LabelText(exp_labels, result_line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        //assertTrue( LabelText.equals( expResult, result) );
        assertEquals( result.getText(), result_line);
        
        assertEquals( 1, result.getLabels().length);
        Label result_label = result.getLabels()[0];
        assertEquals( "unknown2 another context label", result_label.getShortName());
        assertEquals(                                   result_label.getName().length(), 0);
        assertEquals( result_label.getAddedByHand(), false); // added automatically
        
        // parsing the same unknown label again:
        result = LabelRu.extractLabelsTrimText(line);
        assertEquals( 1, result.getLabels().length); // this is the same new added label
    }
    
    // eo getPometaLabel
    // ///////////////////////////////////////////////////////////
    
    
    // ///////////////////////////////////////////////////////////
    // special templates tranforming definition text, e.g. сокр., аббр.
    
    // 1) # {{амер.}}, {{разг.|en}}, {{аббр.|en|w:Franklin Delano Roosevelt|Франклин Делано Рузвельт, 32-й президент США}}
    // 2) checks synonyms: аббр. == сокр.
    @Test
    public void testExtractLabelsTrimText_with_abbrev() {
        System.out.println("extractLabelsTrimText_with_abbrev");
        
        // two equal (from parser POV) lines:
        String line        = "# {{амер.}}, {{разг.|en}}, {{аббр.|en|w:Franklin Delano Roosevelt|Франклин Делано Рузвельт, 32-й президент США}}";
        String line_syn    = "# {{амер.}}, {{разг.|en}}, {{сокр.||w:Franklin Delano Roosevelt|Франклин Делано Рузвельт, 32-й президент США}}";
        String result_line =                 "от [[w:Franklin Delano Roosevelt]]; Франклин Делано Рузвельт, 32-й президент США";
        
        Label[] exp_labels = { LabelEn.US, LabelEn.colloquial, LabelEn.abbreviation };
        LabelText expResult     = new LabelText(exp_labels, result_line);
        LabelText expResult_syn = new LabelText(exp_labels, result_line);
        
        LabelText result     = LabelRu.extractLabelsTrimText(line);
        LabelText result_syn = LabelRu.extractLabelsTrimText(line_syn);
        assertTrue( LabelText.equals( expResult, result) );
        assertTrue( LabelText.equals( expResult, result_syn) );
    }
 
    // old formatting:
    // # {{военн.}}, {{сокр.}} [[командир]] [[батальон]]а
    // new formatting
    // # {{военн.}}, {{сокр.||командир батальона}}
    @Test
    public void testExtractLabelsTrimText_one_parameter() {
        System.out.println("extractLabelsTrimText_with_abbrev");
        
        // two equal (from parser POV) lines:
        String line_old    = "# {{сокр.}} [[командир]] [[батальон]]а";
        String line_new    = "# {{сокр.||командир батальона}}";
        String line_result_old =         "[[командир]] [[батальон]]а";
        String line_result_new =      "от [[командир батальона]]";
        
        Label[] exp_labels = { LabelEn.abbreviation };
        LabelText expResult_old = new LabelText(exp_labels, line_result_old);
        LabelText expResult_new = new LabelText(exp_labels, line_result_new);
        
        LabelText result_old    = LabelRu.extractLabelsTrimText(line_old);
        LabelText result_new    = LabelRu.extractLabelsTrimText(line_new);
        assertTrue( LabelText.equals( expResult_old, result_old) );
        assertTrue( LabelText.equals( expResult_new, result_new) );
    }

    // Special template (form-of), it is not a context label in really :)
    // "# {{хим.}} {{=|спирт}}, бесцветная летучая жидкость, получаемая при ферментации сахара" ->
    @Test
    public void testExtractLabelsTrimText_with_equal_template() {
        System.out.println("extractLabelsTrimText_with_equal_template");
        
        String line        = "# {{хим.}} {{=|спирт}}, бесцветная летучая жидкость, получаемая при ферментации сахара";
        String result_line =   "то же, что [[спирт]], бесцветная летучая жидкость, получаемая при ферментации сахара";
        
        Label[] exp_labels = { LabelEn.chemistry, LabelRu.equal };
        LabelText expResult = new LabelText(exp_labels, result_line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        assertTrue( LabelText.equals( expResult, result) );
    }
    
    // "{{хим-элем|17|Cl|[[неметалл]] из группы [[галоген]]ов}}" -> хим. "[[химический элемент]] с [[атомный номер|атомным номером]] 17, обозначается [[химический символ|химическим символом]] Cl, [[неметалл]] из группы [[галоген]]ов"
    @Test
    public void testExtractLabelsTrimText_element_symbol() {
        System.out.println("extractLabelsTrimText_element_symbol");
        
        String line        = "# {{хим-элем|17|Cl|[[неметалл]] из группы [[галоген]]ов}}";
        String result_line = "[[химический элемент]] с [[атомный номер|атомным номером]] 17, обозначается [[химический символ|химическим символом]] Cl, [[неметалл]] из группы [[галоген]]ов";
        
        Label[] exp_labels = { LabelEn.chemistry };
        LabelText expResult = new LabelText(exp_labels, result_line);
        
        LabelText result = LabelRu.extractLabelsTrimText(line);
        assertTrue( LabelText.equals( expResult, result) );
    }
    
    // special templates tranforming definition text, e.g. сокр., аббр.
    // ///////////////////////////////////////////////////////////
    
    // eo extractLabelsTrimText
    // ///////////////////////////////////////////////////////////
}