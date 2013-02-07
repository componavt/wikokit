
package wikokit.base.wikt.multi.ru;

import wikokit.base.wikt.multi.ru.WMeaningRu;
import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.multi.en.name.LabelEn;
import wikokit.base.wikt.util.WikiWord;
import wikokit.base.wikt.word.WQuote;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.word.WMeaning;


public class WMeaningRuTest {

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
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParseOneDefinition_ru() {
        System.out.println("parseOneDefinition_ru");
        LanguageType lang_section;
        String page_title;
        String line;

        page_title      = "самолёт";
        lang_section    = LanguageType.ru; // Russian word

        LabelEn[] _labels = new LabelEn[0];   //_labels[0] = LabelRu.p;
        String _definition_wiki = "летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом";
        String _definition      = "летательный аппарат тяжелее воздуха с жёстким крылом и собственным мотором";
        WikiWord[] ww = new WikiWord[4];

        //WikiWord(String _word_visible, String _word_link, ContextLabel[] _labels) {
        // [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом
        ww[0] = new WikiWord("аппарат", "аппарат",  null);
        ww[1] = new WikiWord("воздух",  "воздуха",  null);
        ww[2] = new WikiWord("крыло",   "крылом",   null);
        ww[3] = new WikiWord("мотор",   "мотором",  null);
        
        WQuote[] _quote = null;

        //WMeaning expResult = new WMeaning(page_title, _labels, _definition_wiki, _quote, false);      // expResult[0] = new WMeaning();

        line =  "# летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом {{пример|Самолёт-истребитель.}} {{пример|Военный cамолёт.}} {{пример|Эскадрилья самолётов.}}";
        
        WMeaning result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);

        assertTrue(null != result);
        assertTrue(result.getDefinition().equalsIgnoreCase(_definition));
        
        assertTrue(result.getWikifiedText().equalsIgnoreCase(_definition_wiki));
        
        // labels == null
        LabelEn[] labels_result = result.getLabels();
        assertEquals(0, labels_result.length);
        
        // wikiword.size = 4;
        WikiWord[] ww_result = result.getWikiWords();
        assertEquals(4, ww_result.length);
    }

    @Test
    public void testParseOneDefinition_ru_meaning_emtpy() {
        System.out.println("parse_OneDefinition_ru_meaning_emtpy");
        System.out.println("parseOneDefinition_ru");
        LanguageType lang_section;
        String page_title;
        String line;

        page_title      = "самолёт";
        lang_section    = LanguageType.ru; // Russian word

        LabelEn[] _labels = new LabelEn[0];   //_labels[0] = LabelRu.p;
        WikiWord[] ww = new WikiWord[4];

        WQuote[] _quote = null;
        
        // empty definition:
        line =  "# ";

        WMeaning result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);
        assertTrue(null == result);

        
        // test 2
        // empty definition with empty example:
        line =  "# {{пример|}}";

        result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);
        assertTrue(null == result);

        // test 3
        // empty definition with very empty example:
        line =  "# {{пример}}";

        result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);
        assertTrue(null == result);
    }

    @Test
    public void testParseOneDefinition_morpheme_template() {
        System.out.println("parseOneDefinition_morpheme_template_ru");
        LanguageType lang_section;
        String page_title;
        String line;

        page_title      = "-ейш-";
        lang_section    = LanguageType.ru; // Russian word

        // test 1
        line =  "# {{морфема|удар=|часть=основе прилагательного в [[положительная степень|положительной степени]]|образует=его [[превосходная степень|превосходную степень]]|знач=наивысшей степени качества}} {{пример|[[храбрый]] → [[храбрейший|храбр{{выдел|ейш}}ий]]}} {{пример|[[важный]] → [[важнейший|важн{{выдел|ейш}}ий]]}} ‖ иногда суффикс присоединяется вместе с приставкой [[наи-]] {{пример|[[красивый]] → [[наикрасивейший|{{выдел|наи}}красив{{выдел|ейш}}ий]]}}";
        WMeaning result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);

        // skip now, todo (parse) in future
        assertTrue(null == result);

        // test 2
        lang_section    = LanguageType.tt; // Tatar word
        line = "# {{морфема tt|часть=существительному, прилагательному, числительному|ряд=зад|образует=существительные|знач=«абстракции, множества, группы объектов»}}";
        result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);
        assertTrue(null == result);
    }

    // Remove a temporary empty label {{помета?|XX}}, where XX - language code
    @Test
    public void testParse_skip_temporary_empty_label_pometa_question() {
        System.out.println("parseOneDefinition__skip_temporary_empty_label_pometa_question");
        LanguageType lang_section;
        String page_title;
        String line;

        page_title      = "наробляти";
        lang_section    = LanguageType.uk; // Ukrainian word

        // test 1
        line = "# {{помета?|uk}}  {{пример|}}";
        WMeaning result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);
        assertTrue(null == result);

        // test 2
        // todo remove empty "{{помета?|sq}}" -> [[шелковица]], [[тутовое дерево]]
        page_title      = "man";
        lang_section    = LanguageType.sq; // Albanian word
        line = "#{{помета?|sq}} [[шелковица]], [[тутовое дерево]] {{пример|}}";
        //String _line = "[[шелковица]], [[тутовое дерево]]";
        String _line = "шелковица, тутовое дерево";
        result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);
        assertTrue(result.getDefinition().equalsIgnoreCase(_line));
    }

    @Test
    public void testParseOneDefinition_ru_labels() {
        System.out.println("parseOneDefinition_ru_labels");
        LanguageType lang_section;
        String page_title;
        String line;

        // 1. simple: 1 label
        // # {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|Увязочные и прошивочные материалы в виде кручёных шнуров, изготовленных из льнопеньковой пряжи, отбойки (кручёный шпагат), шпагатов увязочных (из {{выдел|лубяных}} волокон), ниток льняных и хлопчатобумажных применяют при выполнении работ по переплетению пружин, прошивке заготовок, стёжке бортов, зашиванию покровных и облицовочных тканей.|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}

        page_title      = "лубяной";
        lang_section    = LanguageType.ru; // Russian word

        LabelEn[] _labels = new LabelEn[0];   //_labels[0] = LabelRu.p;

        String _definition = "имеющий волокно, пригодное для выработки пряжи";
        WikiWord[] ww = new WikiWord[4];
        WQuote[] _quote = null;
        // WMeaning expResult = new WMeaning(_labels, _definition, ww, _quote);      // expResult[0] = new WMeaning();

        line =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|Увязочные и прошивочные материалы в виде кручёных шнуров, изготовленных из льнопеньковой пряжи, отбойки (кручёный шпагат), шпагатов увязочных (из {{выдел|лубяных}} волокон), ниток льняных и хлопчатобумажных применяют при выполнении работ по переплетению пружин, прошивке заготовок, стёжке бортов, зашиванию покровных и облицовочных тканей.|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}";

        WMeaning result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);

        assertTrue(null != result);
        assertTrue(result.getDefinition().equalsIgnoreCase(_definition));

        // labels = {сельск.}
        LabelEn[] labels_result = result.getLabels();
        assertEquals(1, labels_result.length);
        assertTrue(labels_result[0].toString().equalsIgnoreCase( "сельск." ) );


        // 2. complex: 4 label
        line =  "# {{п.}}, {{прост.}}, {{вульг.}}, {{помета|что}} извлекать хитростью, насильно {{пример|Сосать деньги.}}";
        page_title      = "сосать";
        lang_section    = LanguageType.ru; // Russian word

        result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);

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
    public void testParseOneDefinition_ru_empty_quote() {
        System.out.println("parseOneDefinition_ru_empty_quote");
        LanguageType lang_section;
        String page_title;
        String line;

        // 1. simple: 1 empty quote
        line =  "# {{хим-элем|5|B}} {{пример}}";

        page_title      = "бор";
        lang_section    = LanguageType.ru; // Russian word

        WMeaning result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);

        assertTrue(null != result);
        String def = result.getDefinition();

        // temporary result: "{{хим-элем|5|B}}"
            // todo future work: "" - definition is empty, because this is the label.
            //assertEquals(0, def.length());
        assertTrue(def.equalsIgnoreCase( "{{хим-элем|5|B}}" ));
    }

    @Test
    public void testParse_empty_definition_and_empty_quoation_with_empty_translation() {
        System.out.println("parse_empty_definition_and_empty_quoation_with_empty_translation");
        LanguageType lang_section;
        String page_title;
        String line;

        // # {{Нужен перевод}} {{пример||перевод=}}
        line =  "# {{Нужен перевод}} {{пример||перевод=}}";

        page_title      = "лубяной";
        lang_section    = LanguageType.ru; // Russian word

        WMeaning result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);
        assertNull(result);
    }

    @Test
    public void testParseOneDefinition_ru_quote() {
        System.out.println("parseOneDefinition_ru_quote");
        LanguageType lang_section;
        String page_title;
        String line;

        // 1. simple: 1 quote (источник=НКРЯ)
        line =  "# {{сельск.}} имеющий [[волокно]], пригодное для выработки пряжи {{пример|Увязочные ... {{выдел|лубяных}} волокон), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ}}";

        page_title      = "лубяной";
        lang_section    = LanguageType.ru; // Russian word

        WMeaning result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);

        assertTrue(null != result);

 // Увязочные ... {{выдел|лубяных}} волокон), ... .|Татьяна Матвеева|Реставрация столярно-мебельных изделий|1988|источник=НКРЯ
        WQuote[] quote_result = result.getQuotes();
        assertTrue(null != quote_result);
        assertEquals(1, quote_result.length);
        assertTrue(quote_result[0].getText().equalsIgnoreCase( "Увязочные ... {{выдел|лубяных}} волокон), ... ." ) );

        // "# {{хим-элем|5|B}} {{пример}}"

        // 3. complex: several quotes (sentences)
        // todo
        // ...
    }

    // out of date quote template: {{пример перевод|
    @Test
    public void testParseOneDefinition_quote_translation_out_of_date() {
        System.out.println("parseOneDefinition_quote_translation_out_of_date");
        LanguageType lang_section;
        String page_title;
        String line, definition;
        
        // 1. simple: 1 quote with translation
        line =  "# (''замечание'') [[это]] {{пример перевод|cum {{выдел|his}} legatus|с {{выдел|этими}} легионами.}}";
        definition = "(''замечание'') это";

        page_title      = "hic";
        lang_section    = LanguageType.la; // Russian word

        WMeaning result = WMeaningRu.parseOneDefinition(page_title, lang_section, line);

        assertTrue(null != result);

        assertTrue(result.getDefinition().equalsIgnoreCase(definition));
    }

    @Test
    public void testParse_2_meaning_parse_labels() {
        System.out.println("parse_2_meaning_parse_labels");
        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;

        page_title      = "алкоголь";
        lang_section    = LanguageType.ru; // Russian word

        LabelEn[] _labels = new LabelEn[0];   //_labels[0] = LabelRu.p;
        String _definition1 = "алкогольные, спиртные напитки, вино; винный спирт";
        String _definition2 = "то же, что спирт, бесцветная летучая жидкость, получаемая при ферментации сахара";
        WikiWord[] ww = new WikiWord[4];

        str =   "Before \n" +
                "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "{{илл|CachacaDivininha.jpg|Алкоголь [1]}}\n" +
                "==== Значение ====\n" +
                "# {{разг.}} [[алкогольный|алкогольные]], [[спиртной|спиртные]] напитки, [[вино]]; [[винный]] [[спирт]] {{пример|Изгнать {{выдел|алкоголь}} из быта рабочих.}}\n" +
                "# {{хим.}} {{=|спирт}}, бесцветная летучая жидкость, получаемая при ферментации сахара {{пример|}}" +
                "# {{хим-элем|5|B}} {{пример}}";
        pt = new POSText(POS.noun, str);
        WMeaning[] result = WMeaningRu.parse(page_title, lang_section, pt);
        assertEquals(3, result.length);
        assertTrue(result[0].getDefinition().equalsIgnoreCase(_definition1));
        assertTrue(result[1].getDefinition().equalsIgnoreCase(_definition2));
        assertEquals(0, result[2].getDefinition().length());

        // todo
        // test wikiwords

        // todo
        // test quotation

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    // tests that old-wrong-format do not crush the parser
    @Test
    public void testParse_AlmostEmptyDefinition_complex() {
        System.out.println("parse_AlmostEmptyDefinition_complex");

        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;
        
        page_title      = "some hieroglyph";
        lang_section    = LanguageType.ja; // Russian word

        str =   "Before \n" +
                "{{-ja-}}\n" +
                "==Глагол==\n" +
                "\n" +
                "===Значение===\n" +
                "\n";

        pt = new POSText(POS.noun, str);
        WMeaning[] result = WMeaningRu.parse(page_title, lang_section, pt);
        assertEquals(0, result.length);
    }

    // parse 1 meaning without Number sign #
    // pomme
    // ====Значение====
    //[[яблоко]]
    @Test
    public void testParse_1_meaning_without_Number_sign() {
        System.out.println("parse_1_meaning_without_Number_sign");
        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;

        page_title      = "pomme";
        lang_section    = LanguageType.fr; // French word
        
        String _definition1 = "яблоко";

        str =   "Before \n" +
                "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "==== Значение ====\n" +
                "[[яблоко]]\n" +
                "\n" + 
                "====Синонимы====";
        pt = new POSText(POS.noun, str);
        WMeaning[] result = WMeaningRu.parse(page_title, lang_section, pt);
        assertTrue(null != result);
        assertEquals(1, result.length);
        assertTrue(result[0].getDefinition().equalsIgnoreCase(_definition1));

        // labels == null
        LabelEn[] labels_result = result[0].getLabels();
        assertEquals(0, labels_result.length);

        // wikiword.size = 1;
        WikiWord[] ww_result = result[0].getWikiWords();
        assertEquals(1, ww_result.length);
        ww_result[0].getWordLink().equalsIgnoreCase("яблоко");
    }

    // parse 1 meaning without Number sign # and with redirect to another meaning
    // сервер
    // ====Значение====
    // {{техн.|os}} [[#Русский|сервер]]
    @Test
    public void testParse_1_meaning_without_Number_sign_with_Redirect() {
        System.out.println("parse_1_meaning_without_Number_sign_with_Redirect");
        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;

        page_title      = "сервер";
        lang_section    = LanguageType.os; // Ossetic word
        
        String _definition1 = "сервер";

        str =   "Before \n" +
                "{{-os-}}\n" +
                "=== Семантические свойства ===\n" +
                "==== Значение ====\n" +
                "[[#Русский|сервер]]\n" +
                "\n" + 
                "====Синонимы====";
        pt = new POSText(POS.noun, str);
        WMeaning[] result = WMeaningRu.parse(page_title, lang_section, pt);
        assertTrue(null != result);
        assertEquals(1, result.length);
        assertTrue(result[0].getDefinition().equalsIgnoreCase(_definition1));

        // labels == null
        LabelEn[] labels_result = result[0].getLabels();
        assertEquals(0, labels_result.length);

        // wikiword.size = 1;
        WikiWord[] ww_result = result[0].getWikiWords();
        assertEquals(1, ww_result.length);
        ww_result[0].getWordLink().equalsIgnoreCase("сервер");
    }

    // parse text without meaning
    // ====Значение====
    //
    // ====Синонимы====
    // ====Антонимы====
    @Test
    public void testParse_text_without_meaning() {
        System.out.println("parse_text_without_meaning");
        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;

        page_title      = "mzda";
        lang_section    = LanguageType.ru; // Russian word

        str =   "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "==== Значение ====\n" +
                "\n" +
                "====Синонимы====\n" +
                "\n" +
                "====Антонимы====";
        pt = new POSText(POS.noun, str);
        WMeaning[] result = WMeaningRu.parse(page_title, lang_section, pt);
        assertTrue(null != result);
        assertEquals(0, result.length);
    }

    @Test
    public void testParse_1_meaning() {
        System.out.println("parse_1_meaning");
        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;

        page_title      = "самолёт";
        lang_section    = LanguageType.ru; // Russian word

        LabelEn[] _labels = new LabelEn[0];   //_labels[0] = LabelRu.p;
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
        WMeaning[] result = WMeaningRu.parse(page_title, lang_section, pt);
        assertEquals(1, result.length);
        assertTrue(result[0].getDefinition().equalsIgnoreCase(_definition));

        // todo
        // test wikiwords

        // todo
        // test quotation
    }

}