
package wikokit.base.wikt.multi.ru;

import wikokit.base.wikt.multi.ru.WQuoteRu;
import wikokit.base.wikt.util.Definition;
import wikokit.base.wikt.word.WQuote;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class WQuoteRuTest {

    public WQuoteRuTest() {
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

    /**
     * Test of getDefinitionBeforeFirstQuote method, of class WQuoteRu.
     */
    @Test
    public void testGetDefinitionBeforeFirstQuote() {
        System.out.println("getDefinitionBeforeFirstQuote");
        String text, expResult, result, page_title;

        page_title = "самолёт";
        text =      "# летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом {{пример|Самолёт-истребитель.}} {{пример|Военный cамолёт.}} {{пример|Эскадрилья самолётов.}}";
        expResult =   "летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом";

        text = Definition.stripNumberSign(page_title, text);
        result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        assertEquals(expResult, result);
    }

    // test definition without quote, e.g. " definition "
    @Test
    public void testGetDefinitionBeforeFirstQuote_without_example() {
        System.out.println("getDefinitionBeforeFirstQuote_without_example");
        String text, expResult, result, page_title;

        page_title = "самолёт";
        text =      "# летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом ";
        expResult =   "летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом";

        text = Definition.stripNumberSign(page_title, text);
        result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        assertEquals(expResult, result);
    }

    // definition without example
    @Test
    public void testGetQuotes_without_example() {
        System.out.println("testGetQuotes_without example");
        String text, page_title;

        page_title = "самолёт";
        // 1. empty example {{пример|}}
        text =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи ";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(0, quote_result.length);
    }

    // 1. empty example {{пример|}}
    @Test
    public void testGetQuotes_empty_example() {
        System.out.println("testGetQuotes_several");
        String text, page_title;
        
        page_title = "самолёт";
        text =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|}}";
        
        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(0, quote_result.length);
    }

    // 1. empty example twice {{пример|}} {{пример|}}
    @Test
    public void testGetQuotes_empty_example_twice() {
        System.out.println("testGetQuotes_several");
        String text, page_title;

        page_title = "самолёт";
        text =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|}} {{пример|}}";

        text = Definition.stripNumberSign(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(0, quote_result.length);
    }

    // 1. empty example with empty translation {{пример||перевод=}}
    @Test
    public void testGetQuotes_empty_example_with_empty_translation() {
        System.out.println("testGetQuotes_empty_example_with_empty_translation");
        String text, page_title;

        page_title = "самолёт";
        text =  "# {{Нужен перевод}} {{пример||перевод=}}";

        text = Definition.stripNumberSign(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(0, quote_result.length);
    }

    // 1. example without close brackets: "{{пример|some text"
    @Test
    public void testGetQuotes_empty_example_without_close_brackets() {
        System.out.println("testGetQuotes_empty_example_without_close_brackets");
        String text, page_title;

        page_title = "самолёт";
        text =  "# definition {{пример|some text";

        text = Definition.stripNumberSign(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(0, quote_result.length);
    }

    // only source: {{пример|||||источник=НКРЯ}}
    @Test
    public void testGetQuotes_only_source() {
        System.out.println("testGetQuotes_several");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_source;
        int exp_year;

        page_title = "army";
        // 1. simple: 1 quote (источник=НКРЯ)
        text =  "# some definition {{пример|||||источник=НКРЯ}}";

        text = Definition.stripNumberSign(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(0, quote_result.length);
    }

    // 1. example with one unknown parameter: "{{пример|xyz=}}"
    @Test
    public void testGetQuotes_with_one_unknown_parameter() {
        System.out.println("testGetQuotes_with_one_unknown_parameter");
        String text, page_title;

        page_title = "самолёт";
        text = "# definition {{пример|xyzyaya=}}";

        text = Definition.stripNumberSign(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(0, quote_result.length);
    }

    // 1. simple: 1 quote (источник=НКРЯ)
    @Test
    public void testGetQuotes_one_quote() {
        System.out.println("testGetQuotes_several");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_source;
        int exp_year;

        page_title = "самолёт";
        // 1. simple: 1 quote (источник=НКРЯ)
        text =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|Увязочные ... {{выдел|лубяных}} волокон), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}";
//{{пример|Увязочные ... {{выдел|лубяных}} волокон), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}

        exp_text = "Увязочные ... {{выдел|лубяных}} волокон), ... .";
        exp_author = "Татьяна Матвеева";
        exp_title = "Реставрация столярно-мебельных изделий";
        exp_year = 1988;
        exp_source = "НКРЯ";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year);
        assertEquals(q.getYearTo(), exp_year);
    }

    // |Паустовский|Рассказы о Бабеле||источник=Lib}}
    // # {{рег.}} {{жарг.}} процент [[наводчик]]а {{пример|В одну из таких ночей — «{{выдел|карбач}}», |Паустовский|Рассказы о Бабеле||источник=Lib}}
    @Test
    public void testGetQuotes_empty_year() {
        System.out.println("testGetQuotes_empty_year");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_source;
        int exp_year;

        page_title = "карбач_test";
        text =  "# {{рег.}} {{жарг.}} процент [[наводчик]]а {{пример|В одну из таких ночей — «{{выдел|карбач}}»|Паустовский|Рассказы о Бабеле||источник=Lib}}";
//|Паустовский|Рассказы о Бабеле||источник=Lib}}

        exp_text = "В одну из таких ночей — «{{выдел|карбач}}»";
        exp_author = "Паустовский";
        exp_title = "Рассказы о Бабеле";
        exp_year = -1;
        exp_source = "Lib";

        text = Definition.stripNumberSign(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year);
        assertEquals(q.getYearTo(), exp_year);
    }
    

    // 1 quote with translation
    // [[верующий]] {{пример|текст=…У касцёл Святога Роха… ў {{выдел|вернікаў}}…|перевод=В костёл Святого Роха… у {{выдел|верующих}}…|автор=Лідзія Адамовіч|титул=Кветкі самотнай князёўны|источник=БП}}
    @Test
    public void testGetQuotes_quote_with_translation() {
        System.out.println("testGetQuotes_several");
        String text, page_title;
        String exp_text, exp_translation, exp_author, exp_title, exp_source;
        int exp_year;

        page_title = "вернік";
        text =  "# [[верующий]] {{пример|текст=…У касцёл Святога Роха… ў {{выдел|вернікаў}}…|перевод=В костёл Святого Роха… у {{выдел|верующих}}…|автор=Лідзія Адамовіч|титул=Кветкі самотнай князёўны|источник=БП}}";
//{{пример|текст=…У касцёл Святога Роха… ў {{выдел|вернікаў}}…|перевод=В костёл Святого Роха… у {{выдел|верующих}}…|автор=Лідзія Адамовіч|титул=Кветкі самотнай князёўны|источник=БП}}

        exp_text = "…У касцёл Святога Роха… ў {{выдел|вернікаў}}…";
        exp_translation = "В костёл Святого Роха… у {{выдел|верующих}}…";

        // автор=Лідзія Адамовіч|титул=Кветкі самотнай князёўны|источник=БП
        exp_author = "Лідзія Адамовіч";
        exp_title = "Кветкі самотнай князёўны";
        exp_source = "БП";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getTranslation().equalsIgnoreCase( exp_translation ) );

        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );
    }


    // empty source: "|источник= "
    // # муз. произведение на текст псалма [1] {{пример|текст= Альберт Пис… |перевод=|автор= Г. Риман|титул= Музыкальный словарь |издание=|перев=|дата=|источник= }}
    @Test
    public void testGetQuotes_one_quote_with_empty_source() {
        System.out.println("testGetQuotes_several");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_source;
        int exp_year;

        page_title = "псалом";
        text =  "# муз. произведение на текст псалма [1] {{пример|текст= Альберт Пис… |перевод=|автор= Г. Риман|титул= Музыкальный словарь |издание=|перев=|дата=|источник= }}";
//{{пример|текст= Альберт Пис… |перевод=|автор= Г. Риман|титул= Музыкальный словарь |издание=|перев=|дата=|источник= }}"

        exp_text = "Альберт Пис…";
        exp_author = "Г. Риман";
        exp_title = "Музыкальный словарь";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertEquals(0, q.getSource().length() );

        assertEquals(q.getYearFrom(), -1);
        assertEquals(q.getYearTo(), -1);
    }

    // range of years: 1880—1881
    @Test
    public void testGetQuotes_range_of_years() {
        System.out.println("testGetQuotes_range_of_years");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "самолёт";
        // 1. simple: 1 quote (источник=НКРЯ)
        text =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|Увязочные ... {{выдел|лубяных}} волокон), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1880—1881|источник=НКРЯ}}";
//{{пример|Увязочные ... {{выдел|лубяных}} волокон), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}

        exp_text = "Увязочные ... {{выдел|лубяных}} волокон), ... .";
        exp_author = "Татьяна Матвеева";
        exp_title = "Реставрация столярно-мебельных изделий";
        exp_year_from   = 1880;
        exp_year_to     = 1881;
        exp_source = "НКРЯ";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }

    // 1830-е (decade; ten years)
    @Test
    public void testGetQuotes_ten_years() {
        System.out.println("testGetQuotes_ten_years");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "пестик_test";
        text =  "# [[плод]] {{пример|какие-то {{выдел|пестики}}.|Одоевский|[[:s:Два дерева (Одоевский)|Два дерева]]|1830-е}}";
// |Одоевский|[[:s:Два дерева (Одоевский)|Два дерева]]|1830-е}}
        exp_text = "какие-то {{выдел|пестики}}.";
        exp_author = "Одоевский";
        exp_title = "Два дерева";
        exp_year_from   = 1830;
        exp_year_to     = 1830;
        exp_source = "";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(exp_year_from, q.getYearFrom());
        assertEquals(exp_year_to, q.getYearTo());
    }

    // question_in_years e.g. 1862—1875?
    @Test
    public void testGetQuotes_question_in_years() {
        System.out.println("testGetQuotes_question_in_years");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "пестик_test";
        text =  "# [[плод]] {{пример|text|Одоевский|Два дерева|1862—1875?}}";
        exp_text = "text";
        exp_author = "Одоевский";
        exp_title = "Два дерева";
        exp_year_from   = 1862;
        exp_year_to     = 1875;
        exp_source = "";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(exp_year_from, q.getYearFrom());
        assertEquals(exp_year_to, q.getYearTo());
    }

    // error in range of years: 1880-
    @Test
    public void testGetQuotes_error_in_range_of_years() {
        System.out.println("testGetQuotes_error_in_range_of_years");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "самолёт";
        // 1. simple: 1 quote (источник=НКРЯ)
        text =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|Увязочные ... {{выдел|лубяных}} волокон), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1880-|источник=НКРЯ}}";
//{{пример|Увязочные ... {{выдел|лубяных}} волокон), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}

        exp_text = "Увязочные ... {{выдел|лубяных}} волокон), ... .";
        exp_author = "Татьяна Матвеева";
        exp_title = "Реставрация столярно-мебельных изделий";
        exp_year_from   = 1880;
        exp_year_to     = 1880;
        exp_source = "НКРЯ";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }


    // range of years with dash template {{-}}: 1998{{-}}2001
    @Test
    public void testGetQuotes_dash_template_in_range_of_years() {
        System.out.println("testGetQuotes_dash template_in_range_of_years");
        String text, page_title;
        int exp_year_from, exp_year_to;

        page_title = "как скажете";
        text = "# как пожелаете; в соответствии с вашими пожеланиями {{пример|{{-}}{{выдел|Как скажете}}, {{-}}не стал спорить мужичок. … {{-}}Ладно, {{выдел|как скажете}}.|Дмитрий Браславский|Паутина Лайгаша|1998{{-}}2001}}";
//{{пример|{{-}}{{выдел|Как скажете}}, {{-}}не стал спорить мужичок. … {{-}}Ладно, {{выдел|как скажете}}.|Дмитрий Браславский|Паутина Лайгаша|1998{{-}}2001}}

        exp_year_from   = 1998;
        exp_year_to     = 2001;

        text = Definition.stripNumberSign(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertEquals(exp_year_from, q.getYearFrom());
        assertEquals(exp_year_to, q.getYearTo());
    }


    // extract year from day_month_year
    //
    // # отступиться {{пример|... {{выдел|отойти}} от шаблонов, становящихся ярлыками.|Н. С. Лейтес|О признаках детской одаренности|издание=Вопросы психологии|22 июля 2003|источник=НКРЯ}}
    // years = 22 июля 2003
    @Test
    public void testGetQuotes_extract_year_from_day_month_year() {
        System.out.println("testGetQuotes_extract_year_from_day_month_year");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_publisher, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "отойти";
        // 1. simple: 1 quote (источник=НКРЯ)
        text =  "# отступиться {{пример|... {{выдел|отойти}} от шаблонов, становящихся ярлыками.|Н. С. Лейтес|О признаках детской одаренности|издание=Вопросы психологии|22 июля 2003|источник=НКРЯ}}";

        exp_text = "... {{выдел|отойти}} от шаблонов, становящихся ярлыками.";
        exp_author = "Н. С. Лейтес";
        exp_title = "О признаках детской одаренности";

        // 22 июля 2003
        exp_year_from   = 2003;
        exp_year_to     = 2003;

        exp_publisher = "Вопросы психологии";
        exp_source = "НКРЯ";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getPublisher().equalsIgnoreCase( exp_publisher ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }

    // years = 2 июля 2003
    @Test
    public void testGetQuotes_extract_year_from_1day_month_year() {
        System.out.println("testGetQuotes_extract_year_from_1day_month_year");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_publisher, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "отойти";
        // 1. simple: 1 quote (источник=НКРЯ)
        text =  "# отступиться {{пример|... {{выдел|отойти}} от шаблонов, становящихся ярлыками.|Н. С. Лейтес|О признаках детской одаренности|издание=Вопросы психологии|2 февраля 2003|источник=НКРЯ}}";

        exp_text = "... {{выдел|отойти}} от шаблонов, становящихся ярлыками.";
        exp_author = "Н. С. Лейтес";
        exp_title = "О признаках детской одаренности";

        // 22 июля 2003
        exp_year_from   = 2003;
        exp_year_to     = 2003;

        exp_publisher = "Вопросы психологии";
        exp_source = "НКРЯ";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getPublisher().equalsIgnoreCase( exp_publisher ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }
    
    // year = "08-07-2011" "XX-XX-year"
    // # {{собир.|ba}} [[скот]], [[сельскохозяйственный|сельскохозяйственные]] [[животное|животные]] {{пример|Тап шундай берәҙәк эттәр ваҡ {{выдел|мал}} һәм...|перевод=Как раз...||Юлдарҙа —“тере баръерҙар”|издание=Таң|17-11-2011}}
    @Test
    public void testGetQuotes_extract_year_from_day_dash_month_dash_year() {
        System.out.println("testGetQuotes_extract_year_from_day_dash_month_dash_year");
        String text, page_title;
        String exp_text, exp_translation, exp_author, exp_title, exp_publisher, exp_source;
        int exp_year_from, exp_year_to;
        WQuote[] quote_result;
        WQuote q;

        // 1. bad case: "-"
        page_title = "bad case: -";
        text =  "# some text {{пример|Quote text.|Some author|Some title|-}}";

        exp_text = "Quote text.";
        exp_author = "Some author";
        exp_title = "Some title";

        // -
        exp_year_from   = -1;
        exp_year_to     = -1;
        
        text = Definition.stripNumberSign(page_title, text);
        quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        
        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
        
        
        // 2. good case
        
        page_title = "милләт";
        // 1. 17-11-2011
        text =  "# {{собир.|ba}} [[скот]], [[сельскохозяйственный|сельскохозяйственные]] [[животное|животные]] {{пример|Тап шундай берәҙәк эттәр ваҡ {{выдел|мал}} һәм...|перевод=Как раз...||Юлдарҙа —“тере баръерҙар”|издание=Таң|17-11-2011}}";

        exp_text = "Тап шундай берәҙәк эттәр ваҡ {{выдел|мал}} һәм...";
        exp_translation = "Как раз...";
        exp_author = "";
        exp_title = "Юлдарҙа —“тере баръерҙар”";
        exp_publisher = "Таң";
        exp_source = "";

        // 17-11-2011
        exp_year_from   = 2011;
        exp_year_to     = 2011;

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getTranslation().equalsIgnoreCase( exp_translation ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getPublisher().equalsIgnoreCase( exp_publisher ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }
    
    // year = "06.05.2006" "XX.XX.YEAR"
    // # [[рубашка]] на [[планка|планке]] {{пример|Свитера, {{выдел|пайты}} и футболки нежно обнимали спинки и подлокотники, диванов и кресел.||Наш Донецк|06.05.2006}}
    @Test
    public void testGetQuotes_extract_year_from_day_dot_month_dot_year() {
        System.out.println("testGetQuotes_extract_year_from_day_dash_month_dash_year");
        String text, page_title;
        String exp_text, exp_author, exp_title;
        int exp_year_from, exp_year_to;

        page_title = "пайта";
        // 1. 06.05.2006
        text =  "# [[рубашка]] на [[планка|планке]] {{пример|Свитера, {{выдел|пайты}} и футболки нежно обнимали спинки и подлокотники, диванов и кресел.||Наш Донецк|06.05.2006}}";

        exp_text = "Свитера, {{выдел|пайты}} и футболки нежно обнимали спинки и подлокотники, диванов и кресел.";
        exp_author = "";
        exp_title = "Наш Донецк";

        // 06.05.2006
        exp_year_from   = 2006;
        exp_year_to     = 2006;

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }

    // year = "12, 2000"
    // # [[штрафной удар]] {{пример|«Локомотив» вновь впал в анабиоз...|Алексей Самура|Ничья вслепую. Герои и неудачники 21-го тура первенства страны по футболу|издание=Известия|12, 2000|источник=НКРЯ}}
    @Test
    public void testGetQuotes_extract_year_from_year_dot_month_dot_day() {
        System.out.println("testGetQuotes_extract_year_from_year_dot_month_dot_day");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_publisher, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "пенальти";
        // 1. simple: 1 quote (источник=НКРЯ)
        text =  "# [[штрафной удар]] {{пример|«Локомотив» вновь впал в анабиоз...|Алексей Самура|Ничья вслепую. Герои и неудачники 21-го тура первенства страны по футболу|издание=Известия|12, 2000|источник=НКРЯ}}";

        exp_text = "«Локомотив» вновь впал в анабиоз...";
        exp_author = "Алексей Самура";
        exp_title = "Ничья вслепую. Герои и неудачники 21-го тура первенства страны по футболу";

        // 2002.08.26
        exp_year_from   = 2000;
        exp_year_to     = 2000;

        exp_publisher = "Известия";
        exp_source = "НКРЯ";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getPublisher().equalsIgnoreCase( exp_publisher ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }

    // wikified words in the text
    @Test
    public void testGetQuotes_wikified_words_in_text() {
        System.out.println("testGetQuotes_wikified_words_in_text");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "самолёт";
        // 1. simple: 1 quote (источник=НКРЯ)
        text =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|[[увязочный|Увязочные]] ... {{выдел|лубяных}} [[волокно|волокон]]), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1880-|источник=НКРЯ}}";
//{{пример|Увязочные ... {{выдел|лубяных}} волокон), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}

        exp_text = "[[увязочный|Увязочные]] ... {{выдел|лубяных}} [[волокно|волокон]]), ... .";
        exp_author = "Татьяна Матвеева";
        exp_title = "Реставрация столярно-мебельных изделий";
        exp_year_from   = 1880;
        exp_year_to     = 1880;
        exp_source = "НКРЯ";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }

    // 3. complex: several quotes (sentences)
    @Test
    public void testGetQuotes_several() {
        System.out.println("testGetQuotes_several");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "великолепие";
        text =  "# [[роскошь]], богатое убранство {{пример|Внутреннее {{выдел|великолепие}} дворца...|И. А. Крылов|Каиб|Восточная повесть|1792|источник=НКРЯ}} {{пример|...и поражает {{выдел|великолепием}} туалетов.|Салтыков-Щедрин|За рубежом|1880—1881}}";

        // error: two titles: Каиб|Восточная повесть => 1792 is uknown unnamed field
        // {{пример|Внутреннее {{выдел|великолепие}} дворца...|И. А. Крылов|Каиб|Восточная повесть|1792|источник=НКРЯ}}
        exp_text = "Внутреннее {{выдел|великолепие}} дворца...";
        exp_author = "И. А. Крылов";
        exp_title = "Каиб";
        exp_year_from   = -1;
        exp_year_to     = -1;
        exp_source = "НКРЯ";

        String exp_text2, exp_author2, exp_title2, exp_source2;
        int exp_year_from2, exp_year_to2;
        // {{пример|...и поражает {{выдел|великолепием}} туалетов.|Салтыков-Щедрин|За рубежом|1880—1881}}
        exp_text2 = "...и поражает {{выдел|великолепием}} туалетов.";
        exp_author2 = "Салтыков-Щедрин";
        exp_title2 = "За рубежом";
        exp_year_from2   = 1880;
        exp_year_to2     = 1881;
        exp_source2 = "";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(2, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);

        WQuote q2 = quote_result[1];
        assertTrue(q2.getText().equalsIgnoreCase( exp_text2 ) );
        assertTrue(q2.getAuthor().equalsIgnoreCase( exp_author2 ) );
        assertTrue(q2.getTitle().equalsIgnoreCase( exp_title2 ) );
        assertTrue(q2.getSource().equalsIgnoreCase( exp_source2 ) );

        assertEquals(q2.getYearFrom(), exp_year_from2);
        assertEquals(q2.getYearTo(), exp_year_to2);
    }

    // title with wikilink to wikisource (title_wikilink),
    // for example: [[:s:У окна (Андреев)|У окна]]
    @Test
    public void testGetQuotes_with_title_wikilink() {
        System.out.println("testGetQuotes_with_title_wikilink");
        String text, page_title;
        String exp_text, exp_author, exp_title, exp_title_wikilink, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "самолёт";
        text =  "# {{п.}}, {{бранн.}} злобный, коварный человек {{пример|—&nbsp;Ах, кровопивец, ах, {{выдел|аспид}}!&nbsp;— шептала хозяйка.&nbsp;— Ушла бы ты от него совсем, ну его к ляду!|Леонид Андреев|[[:s:У окна (Андреев)|У окна]]|1899}}";
// Леонид Андреев|[[:s:У окна (Андреев)|У окна]]|1899
        exp_text = "—&nbsp;Ах, кровопивец, ах, {{выдел|аспид}}!&nbsp;— шептала хозяйка.&nbsp;— Ушла бы ты от него совсем, ну его к ляду!";
        exp_author = "Леонид Андреев";
        exp_title = "У окна";
        exp_title_wikilink = "У окна (Андреев)";
        exp_year_from   = 1899;
        exp_year_to     = 1899;
        exp_source = "";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getTitleWikilink().equalsIgnoreCase( exp_title_wikilink ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }

    // title with wikilink to wikisource (title_wikilink),
    // replace &nbsp; by " " in titles and author names, for example:
    // [[:w:Михайлов, Михаил Ларионович|М.&nbsp;Л.&nbsp;Михайлов]]|[[:s:Два Мороза (Михайлов)|Два&nbsp;Мороза]]
    // [[:w:Гапон, Георгий Аполлонович|Г.&nbsp;А.&nbsp;Гапон]]
    @Test
    public void testGetQuotes_with_author_wikilink() {
        System.out.println("testGetQuotes_author_wikilink");
        String text, page_title;
        String exp_text, exp_author, exp_author_wikilink, exp_title, exp_title_wikilink, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "замести";
        text =  "# засыпать {{пример|Поле всё снегом занесло...|[[:w:Михайлов, Михаил Ларионович|М.&nbsp;Л.&nbsp;Михайлов]]|[[s:Два Мороза (Михайлов)|Два&nbsp;Мороза]]|}}";

        exp_text = "Поле всё снегом занесло...";
        exp_author = "М. Л. Михайлов";
        exp_author_wikilink = "Михайлов, Михаил Ларионович";
        exp_title = "Два Мороза";
        exp_title_wikilink = "Два Мороза (Михайлов)";
        exp_year_from   = -1;
        exp_year_to     = -1;
        exp_source = "";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getAuthorWikilink().equalsIgnoreCase( exp_author_wikilink ) );

        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getTitleWikilink().equalsIgnoreCase( exp_title_wikilink ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }

    // # {{действие|полоть}} {{пример|Уход за высеянным зерном ячменя... и {{выдел|полкой}} сорных трав.||[[:s:ЭСБЕ/Ячмень|Ячмень]]|источник=ЭСБЕ}}
    @Test
    public void testGetQuotes_with_title_wikilink_and_slash() {
        System.out.println("testGetQuotes_with_title_wikilink_and_slash");
        String text, page_title;
        String exp_text, exp_author, exp_author_wikilink, exp_title, exp_title_wikilink, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "полка";
        text =  "# {{действие|полоть}} {{пример|Уход за высеянным зерном ... {{выдел|полкой}} сорных трав.||[[:s:ЭСБЕ/Ячмень|Ячмень]]|источник=ЭСБЕ}}";

// Уход за высеянным зерном ... {{выдел|полкой}} сорных трав.||[[:s:ЭСБЕ/Ячмень|Ячмень]]|источник=ЭСБЕ
        exp_text = "Уход за высеянным зерном ... {{выдел|полкой}} сорных трав.";
        exp_author = "";
        exp_author_wikilink = "";
        exp_title = "Ячмень";
        exp_title_wikilink = "ЭСБЕ/Ячмень";
        exp_year_from   = -1;
        exp_year_to     = -1;
        exp_source = "ЭСБЕ";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getAuthorWikilink().equalsIgnoreCase( exp_author_wikilink ) );

        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getTitleWikilink().equalsIgnoreCase( exp_title_wikilink ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }
    
    // {{пример|Он простер руку, {{выдел|прикоснулся}} к нему и сказал: хочу, очистись.||{{библия|Лук|5:13}}|перев=синодальный|1816—1862|источник=source}}
    // title={{библия|Лук|5:13}}
    @Test
    public void testGetQuotes_title_with_template_Bible() {
        System.out.println("testGetQuotes_title_with_template_Bible");
        String text, page_title;
        String exp_text, exp_author, exp_author_wikilink, exp_title, exp_title_wikilink, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "прикоснуться";
        
        // 1. {{библия|Лук|5:13}}
        text =  "# [[притронуться]] {{пример|Он простер руку, {{выдел|прикоснулся}} к нему и сказал: хочу, очистись.||{{библия|Лук|5:13}}|перев=синодальный|1816—1862|источник=source}}";

// Он простер руку, {{выдел|прикоснулся}} к нему и сказал: хочу, очистись.||{{библия|Лук|5:13}}|перев=синодальный|1816—1862|источник=source
        exp_text = "Он простер руку, {{выдел|прикоснулся}} к нему и сказал: хочу, очистись.";
        exp_author = "";
        exp_author_wikilink = "";
        exp_title = "Лук.5:13";
        exp_title_wikilink = "";
        exp_year_from   = 1816;
        exp_year_to     = 1862;
        exp_source = "source";

        text = Definition.stripNumberSign(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );
        assertTrue(q.getAuthorWikilink().equalsIgnoreCase( exp_author_wikilink ) );

        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getTitleWikilink().equalsIgnoreCase( exp_title_wikilink ) );
        assertTrue(q.getSource().equalsIgnoreCase( exp_source ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
        
        
        // 2. {{Библия|Быт|1:1}}—{{Библия2|Быт|1:31|31}}
        text =  "# [[притронуться]] {{пример|Он простер руку, {{выдел|прикоснулся}} к нему и сказал: хочу, очистись.||{{Библия|Быт|1:1}}—{{Библия2|Быт|1:31|31}}|перев=синодальный|1816—1862|источник=source}}";
        exp_title = "Быт.1:1—Быт.1:31.31";
        
        text = Definition.stripNumberSign(page_title, text);
        quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        q = quote_result[0];
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
        assertTrue(q.getTitleWikilink().equalsIgnoreCase( exp_title_wikilink ) );
    }

    // title with quote template: Шаблон:"
    // # [[костяк]]; [[скелет]] [[животное|животного]] {{пример|Мне сказывали здесь, что про найденный недавно {{выдел|остов}} мамонта кто-то выдумал объявить чукчам, что им приведется везти его в Якутск, и они растаскали и истребили его так, что теперь и следов нет.|Гончаров|Фрегат {{"|Паллада}}|1855}}
    @Test
    public void testGetQuotes_title_with_quote_template() {
        System.out.println("testGetQuotes_title_with_quote_template");
        String text, page_title;
        String exp_text, exp_author, exp_author_wikilink, exp_title, exp_title_wikilink, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "замести";
        text =  "# [[скелет]] [[животное|животного]] {{пример|...{{выдел|остов}} мамонта...|Гончаров|Фрегат {{\"|Паллада}}|1855}}";

        exp_text = "...{{выдел|остов}} мамонта...";
        exp_author = "Гончаров";
        exp_author_wikilink = "";
  // source title = Фрегат {{"|Паллада}}
        exp_title = "Фрегат \"Паллада\"";
        exp_title_wikilink = "";
        exp_year_from   = 1855;
        exp_year_to     = 1855;
        exp_source = "";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );

        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }

    // title with another quote template: Шаблон:кавычки with lang code
    // # [[мать]] {{пример|{{кавычки|ru|Jam temp'esta}},{{-}}отвечала ему...|Л. Юзефович|Казароза|2002}}
    @Test
    public void testGetQuotes_title_with_second_quote_template() {
        System.out.println("testGetQuotes_title_with_second_quote_template");
        String text, page_title;
        String exp_text, exp_author, exp_author_wikilink, exp_title, exp_title_wikilink, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "matro";
        text =  "# [[мать]] {{пример|{{кавычки|ru|Jam temp'esta}},{{-}}отвечала ему...|Л. Юзефович|Казароза|2002}}";

        // {{кавычки|ru|Jam temp'esta}}
        exp_text = "\"Jam temp'esta\",{{-}}отвечала ему...";
        exp_author = "Л. Юзефович";
        exp_author_wikilink = "";
        exp_title = "Казароза";
        exp_title_wikilink = "";
        exp_year_from   = 2002;
        exp_year_to     = 2002;
        exp_source = "";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );

        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }

    // title with another quote template: Шаблон:кавычки without lang code
    // # [[мать]] {{пример|{{кавычки|Jam temp'esta}},{{-}}отвечала ему...|Л. Юзефович|Казароза|2002}}
    @Test
    public void testGetQuotes_title_with_second_quote_template_without_lang_code() {
        System.out.println("testGetQuotes_title_with_second_quote_template_without_lang_code");
        String text, page_title;
        String exp_text, exp_author, exp_author_wikilink, exp_title, exp_title_wikilink, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "matro";
        text =  "# [[мать]] {{пример|{{кавычки|Jam temp'esta}},{{-}}отвечала ему...|Л. Юзефович|Казароза|2002}}";

        // {{кавычки|ru|Jam temp'esta}}
        exp_text = "\"Jam temp'esta\",{{-}}отвечала ему...";
        exp_author = "Л. Юзефович";
        exp_author_wikilink = "";
        exp_title = "Казароза";
        exp_title_wikilink = "";
        exp_year_from   = 2002;
        exp_year_to     = 2002;
        exp_source = "";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );

        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }

    // title with quote template at the end of the sentence: Шаблон:"
    // # the definition {{пример|The sentence.|The Author|The title{{"||1855}}
    @Test
    public void testGetQuotes_title_with_quote_template_at_the_end_of_title() {
        System.out.println("testGetQuotes_title_with_quote_template_at_the_end_of_title");
        String text, page_title;
        String exp_text, exp_author, exp_author_wikilink, exp_title, exp_title_wikilink, exp_source;
        int exp_year_from, exp_year_to;

        page_title = "замести";
        text =  "# the definition {{пример|The sentence.|The Author|The title{{\"|}}|1855}}";

        exp_text = "The sentence.";
        exp_author = "The Author";
        exp_author_wikilink = "";
  // source title = Фрегат {{"|Паллада}}
        exp_title = "The title\"\"";
        exp_title_wikilink = "";
        exp_year_from   = 1855;
        exp_year_to     = 1855;
        exp_source = "";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );

        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
    }
    
    // # the definition {{пример|The sentence.|The Author|Характеристика вакцины против гепатита A {{"|Avaxim}} производства фирмы {{"|Пастер Мерье}} (результаты поле)}}
    @Test
    public void testGetQuotes_title_with_several_quote_templates_in_title() {
        System.out.println("testGetQuotes_title_with_several_quote_templates_in_title");
        String text, page_title;
        String exp_text, exp_author, exp_author_wikilink, exp_title, exp_title_wikilink, exp_source;
        int exp_year_from, exp_year_to;

        // 1. "|
        page_title = "замести";
        text =  "# the definition {{пример|The sentence.|The Author|The title{{\"|}} and {{\"|again}}|1855}}";

        exp_text = "The sentence.";
        exp_author = "The Author";
        exp_author_wikilink = "";
  // source title = The title{{\"|}} and {{\"|again}}
        exp_title = "The title\"\" and \"again\"";
        exp_title_wikilink = "";
        exp_year_from   = 1855;
        exp_year_to     = 1855;
        exp_source = "";

        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        WQuote[] quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        WQuote q = quote_result[0];
        assertTrue(q.getText().equalsIgnoreCase( exp_text ) );
        assertTrue(q.getAuthor().equalsIgnoreCase( exp_author ) );

        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );

        assertEquals(q.getYearFrom(), exp_year_from);
        assertEquals(q.getYearTo(), exp_year_to);
        
        
        // 2. кавычки|
        text =  "# the definition {{пример|The sentence.|The Author|The title{{кавычки|}} and {{кавычки|again}}|1855}}";
        
        text = Definition.stripNumberSign(page_title, text);
        //result = WQuoteRu.getDefinitionBeforeFirstQuote(page_title, text);
        quote_result = WQuoteRu.getQuotes(page_title, text);
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);

        q = quote_result[0];
        assertTrue(q.getTitle().equalsIgnoreCase( exp_title ) );
    }

}