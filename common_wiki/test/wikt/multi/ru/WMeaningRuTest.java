
package wikt.multi.ru;

import wikipedia.sql.Connect;
import wikt.constant.POS;
import wikt.constant.ContextLabel;
import wikt.util.WikiWord;
import wikt.word.WQuote;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikt.util.POSText;
import wikt.word.WMeaning;


public class WMeaningRuTest {

    Connect     connect_ruwikt; // , connect_enwikt, connect_simplewikt;

    public WMeaningRuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        connect_ruwikt = new Connect();
        connect_ruwikt.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);
    }

    @After
    public void tearDown() {
        connect_ruwikt.Close();
    }

    @Test
    public void testParseOneDefinition_ru() {
        System.out.println("parseOneDefinition_ru");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;
        String line;

        wikt_lang       = LanguageType.ru; // Russian Wiktionary
        page_title      = "самолёт";
        lang_section    = LanguageType.ru; // Russian word

        ContextLabel[] _labels = new ContextLabel[0];   //_labels[0] = LabelRu.p;
        String _definition = "летательный аппарат тяжелее воздуха с жёстким крылом и собственным мотором";
        WikiWord[] ww = new WikiWord[4];

        //WikiWord(String _word_visible, String _word_link, ContextLabel[] _labels) {
        // [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом
        ww[0] = new WikiWord("аппарат", "аппарат",  null);
        ww[1] = new WikiWord("воздух",  "воздуха",  null);
        ww[2] = new WikiWord("крыло",   "крылом",   null);
        ww[3] = new WikiWord("мотор",   "мотором",  null);
        
        WQuote[] _quote = null;

        WMeaning expResult = new WMeaning(_labels, _definition, ww, _quote);      // expResult[0] = new WMeaning();

        line =  "# летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом {{пример|Самолёт-истребитель.}} {{пример|Военный cамолёт.}} {{пример|Эскадрилья самолётов.}}";
        
        WMeaning result = WMeaningRu.parseOneDefinition(wikt_lang, page_title, lang_section, line);

        assertTrue(null != result);
        assertTrue(result.getDefinition().equalsIgnoreCase(_definition));
        
        // labels == null
        ContextLabel[] labels_result = result.getLabels();
        assertEquals(0, labels_result.length);
        
        // wikiword.size = 4;
        WikiWord[] ww_result = result.getWikiWords();
        assertEquals(4, ww_result.length);
    }

    @Test
    public void testParseOneDefinition_ru_labels() {
        System.out.println("parseOneDefinition_ru_labels");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;
        String line;

        // 1. simple: 1 label
        // # {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|Увязочные и прошивочные материалы в виде кручёных шнуров, изготовленных из льнопеньковой пряжи, отбойки (кручёный шпагат), шпагатов увязочных (из {{выдел|лубяных}} волокон), ниток льняных и хлопчатобумажных применяют при выполнении работ по переплетению пружин, прошивке заготовок, стёжке бортов, зашиванию покровных и облицовочных тканей.|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}

        wikt_lang       = LanguageType.ru; // Russian Wiktionary
        page_title      = "лубяной";
        lang_section    = LanguageType.ru; // Russian word

        ContextLabel[] _labels = new ContextLabel[0];   //_labels[0] = LabelRu.p;

        String _definition = "имеющий волокно, пригодное для выработки пряжи";
        WikiWord[] ww = new WikiWord[4];
        WQuote[] _quote = null;
        // WMeaning expResult = new WMeaning(_labels, _definition, ww, _quote);      // expResult[0] = new WMeaning();

        line =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|Увязочные и прошивочные материалы в виде кручёных шнуров, изготовленных из льнопеньковой пряжи, отбойки (кручёный шпагат), шпагатов увязочных (из {{выдел|лубяных}} волокон), ниток льняных и хлопчатобумажных применяют при выполнении работ по переплетению пружин, прошивке заготовок, стёжке бортов, зашиванию покровных и облицовочных тканей.|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}";

        WMeaning result = WMeaningRu.parseOneDefinition(wikt_lang, page_title, lang_section, line);

        assertTrue(null != result);
        assertTrue(result.getDefinition().equalsIgnoreCase(_definition));

        // labels = {сельск.}
        ContextLabel[] labels_result = result.getLabels();
        assertEquals(1, labels_result.length);
        assertTrue(labels_result[0].toString().equalsIgnoreCase( "сельск." ) );


        // 2. complex: 4 label
        line =  "# {{п.}}, {{прост.}}, {{вульг.}}, {{помета|что}} извлекать хитростью, насильно {{пример|Сосать деньги.}}";
        wikt_lang       = LanguageType.ru; // Russian Wiktionary
        page_title      = "сосать";
        lang_section    = LanguageType.ru; // Russian word

        result = WMeaningRu.parseOneDefinition(wikt_lang, page_title, lang_section, line);

        assertTrue(null != result);
        _definition = "извлекать хитростью, насильно";
        assertTrue(result.getDefinition().equalsIgnoreCase(_definition));

        // labels = {{п.}}, {{прост.}}, {{вульг.}}, {{помета|что}}
        labels_result = result.getLabels();
        assertEquals(4, labels_result.length);
        assertTrue(labels_result[0].toString().equalsIgnoreCase( "п." ) );
        assertTrue(labels_result[1].toString().equalsIgnoreCase( "прост." ) );
        assertTrue(labels_result[2].toString().equalsIgnoreCase( "вульг." ) );
        assertTrue(labels_result[3].toString().equalsIgnoreCase( "|что" ) );
    }

    @Test
    public void testParseOneDefinition_ru_quote() {
        System.out.println("parseOneDefinition_ru_quote");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;
        String line;

        // 1. simple: 1 quote (источник=НКРЯ)
        line =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|Увязочные ... {{выдел|лубяных}} волокон), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}";

        wikt_lang       = LanguageType.ru; // Russian Wiktionary
        page_title      = "лубяной";
        lang_section    = LanguageType.ru; // Russian word

        WMeaning result = WMeaningRu.parseOneDefinition(wikt_lang, page_title, lang_section, line);

        assertTrue(null != result);

        WQuote[] quote_result = result.getQuotes();
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);
        assertTrue(quote_result[0].getText().equalsIgnoreCase( "Увязочные ... лубяных волокон), ... ." ) );


        // 2. complex: several quotes (sentences)
        // todo
        // ...
    }


    @Test
    public void testParse_2_meaning_parse_labels() {
        System.out.println("parse_2_meaning_parse_labels");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;
        
        wikt_lang       = LanguageType.ru; // Russian Wiktionary
        page_title      = "алкоголь";
        lang_section    = LanguageType.ru; // Russian word

        ContextLabel[] _labels = new ContextLabel[0];   //_labels[0] = LabelRu.p;
        String _definition1 = "алкогольные, спиртные напитки, вино; винный спирт";
        String _definition2 = "то же, что спирт, бесцветная летучая жидкость, получаемая при ферментации сахара";
        WikiWord[] ww = new WikiWord[4];

        str =   "Before \n" +
                "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "{{илл|CachacaDivininha.jpg|Алкоголь [1]}}\n" +
                "==== Значение ====\n" +
                "# {{разг.}} [[алкогольный|алкогольные]], [[спиртной|спиртные]] напитки, [[вино]]; [[винный]] [[спирт]] {{пример|Изгнать {{выдел|алкоголь}} из быта рабочих.}}\n" +
                "# {{хим.}} {{=|спирт}}, бесцветная летучая жидкость, получаемая при ферментации сахара {{пример|}}";
        pt = new POSText(POS.noun, str);
        WMeaning[] result = WMeaningRu.parse(wikt_lang, page_title, lang_section, pt);
        assertEquals(2, result.length);
        assertTrue(result[0].getDefinition().equalsIgnoreCase(_definition1));
        assertTrue(result[1].getDefinition().equalsIgnoreCase(_definition2));

        // todo
        // test wikiwords

        // todo
        // test quotation

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testParse_1_meaning() {
        System.out.println("parse_1_meaning");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;

        wikt_lang       = LanguageType.ru; // Russian Wiktionary
        page_title      = "самолёт";
        lang_section    = LanguageType.ru; // Russian word

        ContextLabel[] _labels = new ContextLabel[0];   //_labels[0] = LabelRu.p;
        String _definition = "летательный аппарат тяжелее воздуха с жёстким крылом и собственным мотором";
        WikiWord[] ww = new WikiWord[4];

        str =   "Before \n" +
                "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "{{илл|Britannia.plan.arp.750pix.jpg|Самолёт}}\n" +
                "==== Значение ====\n" +
                "# летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом {{пример|Самолёт-истребитель.}} {{пример|Военный cамолёт.}} {{пример|Эскадрилья самолётов.}}\n" +
                "\n";
        pt = new POSText(POS.noun, str);
        WMeaning[] result = WMeaningRu.parse(wikt_lang, page_title, lang_section, pt);
        assertEquals(1, result.length);
        assertTrue(result[0].getDefinition().equalsIgnoreCase(_definition));

        // todo
        // test wikiwords

        // todo
        // test quotation
    }

}