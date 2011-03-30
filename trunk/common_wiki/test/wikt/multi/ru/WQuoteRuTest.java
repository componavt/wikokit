
package wikt.multi.ru;

import wikt.util.Definition;
import wikt.word.WQuote;

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

        // error: Каиб|Восточная повесть => 1792 is uknown unnamed field
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
}