
package wikt.multi.en;

//import wikipedia.sql.Connect;
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
import wikt.word.WLanguage;
import wikt.word.WMeaning;

public class WMeaningEnTest {

    //Connect     connect_enwikt; // connect_simplewikt;

    public WMeaningEnTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        //connect_enwikt = new Connect();
        //connect_enwikt.Open(Connect.ENWIKT_HOST,Connect.ENWIKT_DB,Connect.ENWIKT_USER,Connect.ENWIKT_PASS,LanguageType.ru);
    }

    @After
    public void tearDown() {
        //connect_enwikt.Close();
    }

    @Test
    public void testParseOneDefinition_en() {
        System.out.println("parseOneDefinition_en");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "luck";
        lang_section    = LanguageType.en; // English word

        ContextLabel[] _labels = new ContextLabel[0];   //_labels[0] = LabelRu.p;
        String _def1_source =
            "# Something that happens to someone by [[chance]], a chance occurrence.\n" +
            "#: ''The raffle is just a matter of '''luck'''.''";

        String _def1_result = "Something that happens to someone by chance, a chance occurrence.";
        String _quote1_result = "The raffle is just a matter of '''luck'''.";

        String _def2_source =
            "# A superstitious feeling that brings [[fortune]] or [[success]].\n" +
            "#* '''Year''', Author, ''Source title'', Publisher, pages #–#:\n" +
            "#*: First quotation of '''word'''.\n" +
            "#: ''He blew on the dice for '''luck'''.''";

        String _def2_result = "A superstitious feeling that brings fortune or success.";
        String _quote2_result = "He blew on the dice for '''luck'''.";

        WikiWord[] ww = new WikiWord[2];
        //WikiWord(String _word_visible, String _word_link, ContextLabel[] _labels) {
        ww[0] = new WikiWord("fortune", "fortune",  null);
        ww[1] = new WikiWord("success", "success",  null);

        WQuote[] _quote = null;

        //WMeaning expResult1 = new WMeaning(page_title, _labels, _def1_result, _quote, false);//_quote1_result);
        //WMeaning expResult2 = new WMeaning(page_title, _labels, _def2_result, _quote, false);//_quote2_result);

        // 1
        WMeaning result1 = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, _def1_source);

        assertTrue(null != result1);
        assertTrue(result1.getDefinition().equalsIgnoreCase(_def1_result));

        // labels == null
        ContextLabel[] labels_result = result1.getLabels();
        assertEquals(0, labels_result.length);

        // wikiword.size = 1; chance
        WikiWord[] ww_result = result1.getWikiWords();
        assertEquals(1, ww_result.length);

        // 2
        WMeaning result2 = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, _def2_source);

        assertTrue(null != result2);
        assertTrue(result2.getDefinition().equalsIgnoreCase(_def2_result));

        // labels == null
        labels_result = result2.getLabels();
        assertEquals(0, labels_result.length);

        // wikiword.size = 2; fortune and success
        ww_result = result2.getWikiWords();
        assertEquals(2, ww_result.length);
    }


    // ////////////////////////////////////////
    // Form of templates
    //
    // @see http://en.wiktionary.org/wiki/Category:Form_of_templates
    

    // {{form of|
    @Test
    public void testHasOnlyTemplatesWithoutDefinitions_en_form_of() {
        System.out.println("hasOnlyTemplatesWithoutDefinitions_en_form_of");
        LanguageType wikt_lang;
        String page_title;
        StringBuffer s;
        WLanguage[] lang;
        boolean b;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "raggiavo";

        // 1. empty test - usual definition
        s = new StringBuffer(
                "==Italian==\n" +
                "===Verb===\n" +
                "'''raggiavo'''\n" +
                "# the definition\n" +
                "\n");
        lang = WLanguage.parse(wikt_lang, page_title, s);
        b = WLanguage.hasOnlyTemplatesWithoutDefinitions(wikt_lang, lang);
        assertFalse(b);

        // 2. one template {{form of|}}
        s = new StringBuffer(
                "==Italian==\n" +
                "\n" +
                "===Verb===\n" +
                "'''raggiavo'''\n" +
                "# {{form of|[[first-person|First-person]] [[singular]] [[imperfect tense]]|raggiare|lang=Italian}}\n" +
                "\n");
        lang = WLanguage.parse(wikt_lang, page_title, s);
        b = WLanguage.hasOnlyTemplatesWithoutDefinitions(wikt_lang, lang);
        assertTrue(b);

        // 3. only several templates {{form of|}}, no definition text
        s = new StringBuffer(
                "==Italian==\n" +
                "\n" +
                "===Verb===\n" +
                "'''raggiamo'''\n" +
                "\n" +
                "# {{form of|[[first-person|First-person]] [[plural]] [[present tense]]|raggiare|lang=Italian}}\n" +
                "# {{form of|First-person plural [[present subjunctive]]|raggiare|lang=Italian}}\n" +
                "# {{form of|First-person plural [[imperative]]|raggiare|lang=Italian}}\n" +
                "\n");
        lang = WLanguage.parse(wikt_lang, page_title, s);
        b = WLanguage.hasOnlyTemplatesWithoutDefinitions(wikt_lang, lang);
        assertTrue(b);

        // 4. several templates {{form of|}} + definition text
        s = new StringBuffer(
                "==Italian==\n" +
                "\n" +
                "===Verb===\n" +
                "'''raggiamo'''\n" +
                "\n" +
                "# the definition text\n" +
                "# {{form of|[[first-person|First-person]] [[plural]] [[present tense]]|raggiare|lang=Italian}}\n" +
                "# {{form of|First-person plural [[present subjunctive]]|raggiare|lang=Italian}}\n" +
                "# {{form of|First-person plural [[imperative]]|raggiare|lang=Italian}}\n" +
                "\n");
        lang = WLanguage.parse(wikt_lang, page_title, s);
        b = WLanguage.hasOnlyTemplatesWithoutDefinitions(wikt_lang, lang);
        assertFalse(b);
    }

    // {{plural of|
    @Test
    public void testHasOnlyTemplatesWithoutDefinitions_en_plural_of() {
        System.out.println("hasOnlyTemplatesWithoutDefinitions_en_plural_of");
        LanguageType wikt_lang;
        String page_title;
        StringBuffer s;
        WLanguage[] lang;
        boolean b;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "stanas";

        // 1. one template {{plural of|}}
        s = new StringBuffer(
                "==Old English==\n" +
                "\n" +
                "===Noun===\n" +
                "{{Latinx|'''stānas'''}}\n" +
                "\n" +
                "# {{plural of|stan#Old English|stān|lang=Old English}}\n" +
                "{{count page|[[Wiktionary:Page count]]}}\n" +
                "\n");
        lang = WLanguage.parse(wikt_lang, page_title, s);
        b = WLanguage.hasOnlyTemplatesWithoutDefinitions(wikt_lang, lang);
        assertTrue(b);
    }

    // {{form of|
    // {{es-verb form of|
    @Test
    public void testParseOneDefinition_en_form_of_template() {
        System.out.println("parseOneDefinition_en_form_of_template");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title, source;
        WMeaning wm;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "raggiamo";
        lang_section    = LanguageType.en; // English word

        // 1 form of
        source = "# {{form of|[[first-person|First-person]] [[plural]] [[present tense]]|[[raggiare#Italian|raggiare]]|lang=Italian}}";
        wm = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, source);
        assertNotNull(wm);
        assertTrue(wm.hasTemplateNotDefinition());

        // 2 es-verb form of
        source = "# {{es-verb form of|mood=ger|ending=er|verb=hacer}}";
        wm = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, source);
        assertNotNull(wm);
        assertTrue(wm.hasTemplateNotDefinition());
    }

    // {{plural of|
    // {{feminine plural of|
    @Test
    public void testParseOneDefinition_en_plural_of_template() {
        System.out.println("parseOneDefinition_en_plural_of_template");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title, source;
        WMeaning wm;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "buenas";
        lang_section    = LanguageType.es; // Spanish word

        // 1 plural of
        source = "# {{plural of| }}";
        wm = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, source);
        assertNotNull(wm);
        assertTrue(wm.hasTemplateNotDefinition());

        // 2 feminine plural of
        source = "# {{feminine plural of|[[bueno]]|lang=es}}";
        wm = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, source);
        assertNotNull(wm);
        assertTrue(wm.hasTemplateNotDefinition());
/*
        // 3 es-verb form of
        source = "# {{es-verb form of|mood=ger|ending=er|verb=hacer}}";
        wm = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, source);
        assertNotNull(wm);
        assertTrue(wm.hasTemplateNotDefinition());
 */
    }

    // @see http://en.wiktionary.org/wiki/Template:inflection_of
    // {{inflection of|
    // {{conjugation of|
    @Test
    public void testParseOneDefinition_en_inflection_of_template() {
        System.out.println("parseOneDefinition_en_form_of_template");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title, source;
        WMeaning wm;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "anulare";
        lang_section    = LanguageType.la; // Latin word

        // 1 inflection of
        source = "# {{inflection of|anularis|ānulāris|nom|n|s|lang=la}}";
        wm = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, source);
        assertNotNull(wm);
        assertTrue(wm.hasTemplateNotDefinition());

        // 2 conjugation of
        source = "# {{conjugation of|amo|amō|pres|act|inf|lang=la}} ";
        wm = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, source);
        assertNotNull(wm);
        assertTrue(wm.hasTemplateNotDefinition());
    }

    // @see http://en.wiktionary.org/wiki/Template:alternative_spelling_of
    // {{alternative spelling of|}}
    @Test
    public void testParseOneDefinition_en_alternative_spelling_of_template() {
        System.out.println("parseOneDefinition_en_form_of_template");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title, source;
        WMeaning wm;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "ружье";
        lang_section    = LanguageType.ru; // (second) Russian word

        source = "# {{alternative spelling of|[[second-guess]]}}";
        wm = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, source);
        assertNotNull(wm);
        assertTrue(wm.hasTemplateNotDefinition());

        source = "# {{alternative spelling of|ружьё|lang=ru}}";
        wm = WMeaningEn.parseOneDefinition(
                wikt_lang, page_title, lang_section, source);
        assertNotNull(wm);
        assertTrue(wm.hasTemplateNotDefinition());
    }
    

    //                     eo Form of templates
    // ////////////////////////////////////////

    @Test
    public void testParseOneDefinition_en_labels_and_WikiWord() {
        System.out.println("parseOneDefinition_en_labels_and_WikiWord");
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;
        String line;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "beneficiary";
        lang_section    = LanguageType.en; // English word

        ContextLabel[] _labels = new ContextLabel[0];   //_labels[0] = LabelRu.p;

        String _def_source =
            "# {{legal}} One who [[benefit]]s from the distribution, especially of an estate.\n" +
            "#: ''If any '''beneficiary''' does not survive the Settlor for a period of 30 days then the Trustee shall distribute that '''beneficiary'''’s share to the surviving '''beneficiaries''' by right of representation.''";

        String _def_result = "One who [[benefit]]s from the distribution, especially of an estate.";
        String _quote_result = "If any '''beneficiary''' does not survive the Settlor for a period of 30 days then the Trustee shall distribute that '''beneficiary'''’s share to the surviving '''beneficiaries''' by right of representation.";

        WikiWord[] ww = new WikiWord[1];

        //WikiWord(String _word_visible, String _word_link, ContextLabel[] _labels) {
        ww[0] = new WikiWord("benefit", "benefits",  null);

        WQuote[] _quote = null;

        //WMeaning expResult = new WMeaning(page_title, _labels, _def_result, _quote);

        WMeaning result = WMeaningEn.parseOneDefinition(wikt_lang, page_title, lang_section, _def_source);

        assertTrue(null != result);
        assertTrue(result.getDefinition().equalsIgnoreCase(_def_result));

        // labels == null
        ContextLabel[] labels_result = result.getLabels();
        assertEquals(0, labels_result.length);

        // wikiword.size = 4;
        WikiWord[] ww_result = result.getWikiWords();
        assertEquals(1, ww_result.length);
    }

    @Test
    public void testParse_3_meaning_parse_labels() {
        System.out.println("parse_3_meaning_parse_labels");
        
        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "picture";
        lang_section    = LanguageType.en; // English word
        
        ContextLabel[] _labels = new ContextLabel[0];   //_labels[0] = LabelRu.p;
        String _definition1 = "A painting.";
        String _definition2 = "A photograph.";
        String _definition3 = "A motion picture.";
        WikiWord[] ww = new WikiWord[4];

        str =   "{{en-noun}}\n" +
                "\n" +
                "# A [[painting]].\n" +
                "#: ''There was a '''picture''' hanging above the fireplace.''\n" +
                "# A [[photograph]].\n" +
                "#: ''I took a '''picture''' of that church.''\n" +
                "# {{informal}} A [[motion picture]].\n" +
                "#: Casablanca ''is my all-time favorite '''picture'''.''\n" +
                "\n";
        pt = new POSText(POS.noun, str);
        WMeaning[] result = WMeaningEn.parse(wikt_lang, page_title, lang_section, pt);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertTrue(result[0].getDefinition().equalsIgnoreCase(_definition1));
        assertTrue(result[1].getDefinition().equalsIgnoreCase(_definition2));
        // label test:
        // assertTrue(result[2].getDefinition().equalsIgnoreCase(_definition3));
        
        // todo
        // test wikiwords

        // todo
        // test quotation

        // todo
        // test labels
    }

    @Test
    public void testParse_2_meaning_with_translation() {
        System.out.println("parse_2_meaning_with_translation");

        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "test_word";
        lang_section    = LanguageType.en; // English word

        ContextLabel[] _labels = new ContextLabel[0];   //_labels[0] = LabelRu.p;
        String _definition1 = "First definition";
        String _definition2 = "Second definition.";
        WikiWord[] ww = new WikiWord[4];

        str =   "\n" +
                "# First definition\n" +
                "#* '''Year''', Author, ''Source title'', Publisher, pages #–#:\n" +
                "#*: First quotation of '''word'''.\n" +
                "#*:: Translation [if applicable]\n" +
                "#* '''Year''', Author, ''Source title'', Publisher (publication date of later edition), page #:\n" +
                "#*: '''Words''' of second quotation.\n" +
                "# Second definition.\n" +
                "\n";

        pt = new POSText(POS.noun, str);
        WMeaning[] result = WMeaningEn.parse(wikt_lang, page_title, lang_section, pt);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue(result[0].getDefinition().equalsIgnoreCase(_definition1));
        assertTrue(result[1].getDefinition().equalsIgnoreCase(_definition2));

        // todo
        // test wikiwords

        // todo
        // test quotation

        // todo
        // test labels

        // todo translation:
        /*'''mán'''
# {{lt-form-pronoun|1s|d|aš}}
#: [[duok|Duok]] '''man''' [[tą]] [[knygą]].
#:: Give me that book.
        */
    }

    @Test
    public void testParse_1_meaning() {
        System.out.println("parse_1_meaning");

        LanguageType wikt_lang;
        LanguageType lang_section;
        String page_title;
        POSText pt;
        String str;

        wikt_lang       = LanguageType.en; // English Wiktionary
        page_title      = "airplane";
        lang_section    = LanguageType.en; // English word

        ContextLabel[] _labels = new ContextLabel[0];   //_labels[0] = LabelRu.p;
        String _definition1 = "First definition";
        String _definition2 = "Second definition.";
        WikiWord[] ww = new WikiWord[4];

        str =   "{{en-noun}}\n" +
                "# {{US}} A powered heavier-than air [[aircraft]] with fixed [[wing]]s.\n" +
                "\n" +
                "====Synonyms====\n" +
                "* [[aeroplane]].\n" +
                "\n" +
                "\n";

        _definition1 = "{{US}} A powered heavier-than air aircraft with fixed wings.";

        pt = new POSText(POS.noun, str);
        WMeaning[] result = WMeaningEn.parse(wikt_lang, page_title, lang_section, pt);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertTrue(result[0].getDefinition().equalsIgnoreCase(_definition1));

        // test 2: zero of symbols before the definition #
        str =   "# {{US}} A powered heavier-than air [[aircraft]] with fixed [[wing]]s.\n" +
                "\n" +
                "====Synonyms====\n" +
                "* [[aeroplane]].\n" +
                "\n" +
                "\n";

        pt = new POSText(POS.noun, str);
        result = WMeaningEn.parse(wikt_lang, page_title, lang_section, pt);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertTrue(result[0].getDefinition().equalsIgnoreCase(_definition1));
    }

    @Test
    public void testParse_Quotation_header() {
        System.out.println("parse_Quotation_header");

        fail("todo");

    // todo parse Quatation header, see word bark:
    /*
====Noun====
{{en-noun}}

# {{obsolete}} A small sailing vessel, e.g. a pinnace or a fishing smack; a rowing boat or barge.
# {{poetic}} a sailing vessel or boat of any kind.
# {{nautical}} A three-masted vessel, having her foremast and mainmast square-rigged, and her mizzenmast schooner-rigged.

=====Quotations=====
* '''circa 1880''' [[CE]]: Whether my bark went down at sea, Whether she met with gales, ... &mdash; Emily Dickinson (1830–86), ''Poems''

=====Translations=====
        */
    }

}