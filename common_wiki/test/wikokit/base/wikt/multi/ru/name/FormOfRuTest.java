package wikokit.base.wikt.multi.ru.name;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.multi.en.name.LabelEn;

public class FormOfRuTest {
    
    public FormOfRuTest() {
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

    // ////////////////////////////////////////////
    // transformTemplateToText
    
    // {{аббр.||Чехословацкая Социалистическая Республика}}, @see http://ru.wiktionary.org/wiki/Шаблон:аббр.
    @Test
    public void testTransformTemplateToText_one_param() {
        System.out.println("transformTemplateToText");
        
        Label source_label = LabelEn.abbreviation;
        
        String[] template_params = {"", "Чехословацкая Социалистическая Республика"};    // {{аббр.||Чехословацкая Социалистическая Республика}}
        String expResult = "от [[Чехословацкая Социалистическая Республика]]";
        String result = FormOfRu.transformTemplateToText(source_label, template_params);
        assertEquals(expResult, result);
    }
    
    // {{аббр.|en|frequently asked questions|часто задаваемые вопросы}} -> "от [[frequently asked questions]]; часто задаваемые вопросы"
    @Test
    public void testTransformTemplateToText_three_params() {
        System.out.println("transformTemplateToText_three_params");
        
        Label source_label = LabelEn.abbreviation;
        
        String[] template_params = {"en", "frequently asked questions", "часто задаваемые вопросы"};
        String expResult = "от [[frequently asked questions]]; часто задаваемые вопросы";
        String result = FormOfRu.transformTemplateToText(source_label, template_params);
        assertEquals(expResult, result);
    }
    
    // {{as ru}} -> "аналогично русскому слову"
    @Test
    public void testTransformTemplateToText_as_ru() {
        System.out.println("transformTemplateToText_as_ru");
        
        Label source_label = LabelRu.as_ru;
        
        String[] template_params = {};
        String expResult = "аналогично русскому слову";
        String result = FormOfRu.transformTemplateToText(source_label, template_params);
        assertEquals(expResult, result);
    }

    // {{=|advantage}} -> "то же, что [[advantage]]"
    // {{=|телефонный номер|последовательность цифр, набираемая для [[вызов]]а абонента}} -> "то же, что [[телефонный номер]]; последовательность цифр, набираемая для [[вызов]]а абонента"
    @Test
    public void testTransformTemplateToText_equal_template() {
        System.out.println("transformTemplateToText_equal_template");
        
        Label source_label = LabelRu.equal;
        
        String[] template_params = {"advantage"};
        String expResult = "то же, что [[advantage]]";
        String result = FormOfRu.transformTemplateToText(source_label, template_params);
        assertEquals(expResult, result);
        
        String[] template_params2 = {"телефонный номер", "последовательность цифр, набираемая для [[вызов]]а абонента"};
        String expResult2 = "то же, что [[телефонный номер]]; последовательность цифр, набираемая для [[вызов]]а абонента";
        String result2 = FormOfRu.transformTemplateToText(source_label, template_params2);
        assertEquals(expResult2, result2);
    }
    
    // {{действие|сокращать|[[уменьшение]], [[усечение]] чего-либо}} -> "действие по значению гл. [[сокращать]]; [[уменьшение]], [[усечение]] чего-либо"
    @Test
    public void testTransformTemplateToText_action() {
        System.out.println("transformTemplateToText_action");
        
        Label source_label = LabelRu.action;
        
        // 1 params
        // {{действие|сокращать}} -> "действие по значению гл. [[сокращать]]"
        String[] template_params1 = {"сокращать"};
        String expResult1 = "действие по значению гл. [[сокращать]]";
        String result1 = FormOfRu.transformTemplateToText(source_label, template_params1);
        assertEquals(expResult1, result1);
        
        // 2 params
        // {{действие|сокращать|[[уменьшение]], [[усечение]] чего-либо}} -> "действие по значению гл. [[сокращать]]; [[уменьшение]], [[усечение]] чего-либо"
        String[] template_params2 = {"сокращать", "[[уменьшение]], [[усечение]] чего-либо"};
        String expResult2 = "действие по значению гл. [[сокращать]]; [[уменьшение]], [[усечение]] чего-либо";
        String result2 = FormOfRu.transformTemplateToText(source_label, template_params2);
        assertEquals(expResult2, result2);
    }
    
    // {{хим-элем|17|Cl|[[хлор]]|lang=en}} ->  "[[химический элемент]] с [[атомный номер|атомным номером]] 17, обозначается [[химический символ|химическим символом]] Cl, [[хлор]]"
    @Test
    public void testTransformTemplateToText_element_symbol_4_params() {
        System.out.println("transformTemplateToText_element_symbol_4_params");
        
        Label source_label = LabelRu.element_symbol;
        
        String[] template_params = {"17", "Cl", "[[хлор]]", "lang=en"};
        String expResult = "[[химический элемент]] с [[атомный номер|атомным номером]] 17, обозначается [[химический символ|химическим символом]] Cl, [[хлор]]";
        String result = FormOfRu.transformTemplateToText(source_label, template_params);
        assertEquals(expResult, result);
    }
    
    // {{хим-элем|105|Db}}  -> "[[химический элемент]] с [[атомный номер|атомным номером]] 105, обозначается [[химический символ|химическим символом]] Db"
    @Test
    public void testTransformTemplateToText_element_symbol_2_params() {
        System.out.println("transformTemplateToText_element_symbol_2_params");
        
        Label source_label = LabelRu.element_symbol;
        
        // {{хим-элем|105|Db}}  -> "[[химический элемент]] с [[атомный номер|атомным номером]] 105, обозначается [[химический символ|химическим символом]] Db"
        String[] template_params = {"105", "Db"};
        String expResult = "[[химический элемент]] с [[атомный номер|атомным номером]] 105, обозначается [[химический символ|химическим символом]] Db";
        String result = FormOfRu.transformTemplateToText(source_label, template_params);
        assertEquals(expResult, result);
    }
    
    // {{свойство|эгалитарный}} -> "[[свойство]] по значению [[прилагательное|прил.]] [[эгалитарный]]"
    @Test
    public void testTransformTemplateToText_property() {
        System.out.println("transformTemplateToText_property_1_param");
        String result, expResult;
        Label source_label = LabelRu.property;
        
        String[] param = {"эгалитарный"};
        expResult = "[[свойство]] по значению [[прилагательное|прил.]] [[эгалитарный]]";
        result = FormOfRu.transformTemplateToText(source_label, param);
        assertEquals(expResult, result);
        
        // {{свойство|эгалитарный| состояние = 1 }} -> "[[свойство]] или [[состояние]] по значению [[прилагательное|прил.]] [[эгалитарный]]"
        String[] cond_params = {"эгалитарный", " состояние = 1 "};
        expResult = "[[свойство]] или [[состояние]] по значению [[прилагательное|прил.]] [[эгалитарный]]";
        result = FormOfRu.transformTemplateToText(source_label, cond_params);
        assertEquals(expResult, result);
        
        //                        + description and condition
        // {{свойство|эгалитарный| описание|состояние = 1 }} -> "[[свойство]] или [[состояние]] по значению [[прилагательное|прил.]] [[эгалитарный]]"
        String[] desc_cond_params = {"эгалитарный", " описание", " состояние = 1 "};
        expResult = "[[свойство]] или [[состояние]] по значению [[прилагательное|прил.]] [[эгалитарный]]; описание";
        result = FormOfRu.transformTemplateToText(source_label, desc_cond_params);
        assertEquals(expResult, result);
        
        // + lang=en
        // {{свойство|эгалитарный| описание|состояние = 1 }} -> "[[свойство]] или [[состояние]] по значению [[прилагательное|прил.]] [[эгалитарный]]"
        String[] desc_lang_params = {"эгалитарный", " описание", " состояние = 1 ", "lang=en"};
        expResult = "[[свойство]] или [[состояние]] по значению [[прилагательное|прил.]] [[эгалитарный]]; описание";
        result = FormOfRu.transformTemplateToText(source_label, desc_lang_params);
        assertEquals(expResult, result);
        
    }
 
    // {{соотн.|бильярд}} -> "связанный, [[соотносящийся]] по значению с существительным [[бильярд]]"
    // {{соотн.|свойств=1|небо|небеса|3|4}} -> "связанный, [[соотносящийся]] по значению с существительными [[небо]], [[небеса]], [[3]], [[4]]; свойственный, [[характерный]] для них"
    @Test
    public void testTransformTemplateToText_sootn() {
        System.out.println("transformTemplateToText_sootn");
        String result, expResult;
        Label source_label = LabelRu.sootn;
        
        // {{соотн.|бильярд}} -> "связанный, [[соотносящийся]] по значению с существительным [[бильярд]]"
        String[] one_param = {"бильярд"};
        expResult = "связанный, [[соотносящийся]] по значению с существительным [[бильярд]]";
        result = FormOfRu.transformTemplateToText(source_label, one_param);
        assertEquals(expResult, result);
        
        // {{соотн.|время|свойств=1}} -> "связанный, [[соотносящийся]] по значению с существительным [[время]]; свойственный, [[характерный]] для него"
        String[] two_param_property = {"время", "свойств=1"};
        expResult = "связанный, [[соотносящийся]] по значению с существительным [[время]]; свойственный, [[характерный]] для него";
        result = FormOfRu.transformTemplateToText(source_label, two_param_property);
        assertEquals(expResult, result);
        
        // {{соотн.|свойств=1|идиоматизм|идиома}} -> "связанный, [[соотносящийся]] по значению с существительными [[идиоматизм]], [[идиома]]; свойственный, [[характерный]] для них"
        String[] three_param_property = {"свойств=1", "идиоматизм", "идиома"};
        expResult = "связанный, [[соотносящийся]] по значению с существительными [[идиоматизм]], [[идиома]]; свойственный, [[характерный]] для них";
        result = FormOfRu.transformTemplateToText(source_label, three_param_property);
        assertEquals(expResult, result);
        
        // {{соотн.|свойств=неё|магистраль}} -> "связанный, [[соотносящийся]] по значению с существительным [[магистраль]]; свойственный, [[характерный]] для неё"
        String[] two_param_property_feminine = {"свойств=неё", "магистраль"};
        expResult = "связанный, [[соотносящийся]] по значению с существительным [[магистраль]]; свойственный, [[характерный]] для неё";
        result = FormOfRu.transformTemplateToText(source_label, two_param_property_feminine);
        assertEquals(expResult, result);
        
        // {{соотн.|свойств=[[магистраль|магистрали]]|магистраль}} -> "связанный, [[соотносящийся]] по значению с существительным [[магистраль]]; свойственный, [[характерный]] для [[магистраль|магистрали]]"
        String[] two_param_property_wikified = {"свойств=[[магистраль|магистрали]]", "магистраль"};
        expResult = "связанный, [[соотносящийся]] по значению с существительным [[магистраль]]; свойственный, [[характерный]] для [[магистраль|магистрали]]";
        result = FormOfRu.transformTemplateToText(source_label, two_param_property_wikified);
        assertEquals(expResult, result);
        
        // {{соотн.|свойств=1|небо|небеса|3|4}} -> "связанный, [[соотносящийся]] по значению с существительными [[небо]], [[небеса]], [[3]], [[4]]; свойственный, [[характерный]] для них"
        String[] five_params = {"свойств=1", "небо", "небеса", "3", "4"};
        expResult = "связанный, [[соотносящийся]] по значению с существительными [[небо]], [[небеса]], [[3]], [[4]]; свойственный, [[характерный]] для них";
        result = FormOfRu.transformTemplateToText(source_label, five_params);
        assertEquals(expResult, result);
    }
    
    // {{совершить|клевать}} -> "совершить действие, выраженное гл. [[клевать]]; провести некоторое время, совершая такое действие"
    @Test
    public void testTransformTemplateToText_sovershiti() {
        System.out.println("transformTemplateToText_sovershiti");
        String result, expResult;
        Label source_label = LabelRu.sovershiti;
        
        String[] one_param = {"клевать"};
        expResult = "совершить действие, выраженное гл. [[клевать]]; провести некоторое время, совершая такое действие";
        result = FormOfRu.transformTemplateToText(source_label, one_param);
        assertEquals(expResult, result);
    }
    
    // {{однокр.|глядеть}} -> "[[однократно|однокр.]] к [[глядеть]]"
    @Test
    public void testTransformTemplateToText_odnokr() {
        System.out.println("transformTemplateToText_odnokr");
        String result, expResult;
        Label source_label = LabelRu.odnokr;
        
        String[] one_param = {"глядеть"};
        expResult = "[[однократно|однокр.]] к [[глядеть]]";
        result = FormOfRu.transformTemplateToText(source_label, one_param);
        assertEquals(expResult, result);
    }
    
    // {{многокр.|говорить}} -> [[многократно|многокр.]] к [[говорить]]
    @Test
    public void testTransformTemplateToText_mnogokr() {
        System.out.println("transformTemplateToText_mnogokr");
        String result, expResult;
        Label source_label = LabelRu.mnogokr;
        
        String[] one_param = {"говорить"};
        expResult = "[[многократно|многокр.]] к [[говорить]]";
        result = FormOfRu.transformTemplateToText(source_label, one_param);
        assertEquals(expResult, result);
    }
    
    // {{состояние|спать|от=гл}} -> "[[состояние]] по значению [[глагол|гл.]] [[спать]]"
    @Test
    public void testTransformTemplateToText_sostoyanie() {
        System.out.println("transformTemplateToText_sostoyanie");
        String result, expResult;
        Label source_label = LabelRu.sostoyanie;
        
        String[] two_params = {"спать", "от=гл"};
        expResult = "[[состояние]] по значению [[глагол|гл.]] [[спать]]";
        result = FormOfRu.transformTemplateToText(source_label, two_params);
        assertEquals(expResult, result);
    }
    
    
    // todo
    // Шаблон:прич.
    
    // todo
    // {{наречие|однократный}}
    
    
    // transformTemplateToText
    // ////////////////////////////////////////////
    
}