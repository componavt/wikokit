
package wikokit.base.wikt.multi.ru;

import wikokit.base.wikt.multi.ru.WPOSRu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.util.LangText;
import wikokit.base.wikipedia.language.LanguageType;

import wikokit.base.wikt.constant.POS;

public class WPOSRuTest {

    //Connect     connect_ruwikt; // , connect_enwikt, connect_simplewikt;
    
    public WPOSRuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        //connect_ruwikt = new Connect();
        //connect_ruwikt.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);
    }

    @After
    public void tearDown() {
        //connect_ruwikt.Close();
    }

    /*  POS defined by the template {{заголовок|be|add=I}},
     * where "add" is not empty.
     *
     * {{-be-}}
     * {{заголовок|be|add=I}}
     * {{заголовок|be|add=II}}
     */
    @Test
    public void testSplitToPOSSections_add_parameter() {
        System.out.println("splitToPOSSections_add_parameter");

        String str, s1, s2;
        POSText[] result;
        LangText lt;
        lt = new LangText(LanguageType.be);

        // two POS in {{-en-}} in Russian Wiktionary
        s1 =    "Before \n" +
                "{{заголовок|be|add=I}}\n" +
                "=== Морфологические и синтаксические свойства ===\n" +
                "{{сущ be m|слоги={{по-слогам|шах}}|}}\n" +
                "\n";
        s2 =    "{{заголовок|be|add=II}}\n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{сущ be m|слоги={{по-слогам|}}|}}\n" +
                "\n";
        str = s1 + s2;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("шах", lt);
        assertEquals(2, result.length);
        assertEquals(POS.noun, result[0].getPOSType());
        assertEquals(POS.noun, result[1].getPOSType());

        assertTrue(result[0].getText().toString().equalsIgnoreCase(s1));
        assertTrue(result[1].getText().toString().equalsIgnoreCase(s2));

        // check of error: language code "en" != "be"
        lt = new LangText(LanguageType.en);
        s1 =    "Before \n" +
                "{{заголовок|be|add=I}}\n" +
                "=== Морфологические и синтаксические свойства ===\n" +
                "{{сущ be m|слоги={{по-слогам|шах}}|}}\n" +
                "\n";
        s2 =    "{{заголовок|be|add=II}}\n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{сущ be m|слоги={{по-слогам|}}|}}\n" +
                "\n";
        str = s1 + s2;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("шах", lt);
        assertEquals(1, result.length);
    }

    /*  POS defined by the template {{заголовок|add=I}},
     * where there are only two parameters
     *
     * = {{-ru-}} =
     * {{заголовок|add=I}}
     * {{заголовок|add=II}}
     */
    @Test
    public void testSplitToPOSSections_add_parameter_without_lang_parameter() {
        System.out.println("splitToPOSSections_add_parameter_without_lang_parameter");

        String str, s1, s2;
        POSText[] result;
        LangText lt;
        lt = new LangText(LanguageType.ru);

        // two POS in {{-en-}} in Russian Wiktionary
        s1 =    "Before \n" +
                "{{заголовок|add=I}}\n" +
                "=== Морфологические и синтаксические свойства ===\n" +
                "{{гл ru 12aСВ}}\n" +
                "\n";
        s2 =    "{{заголовок|add=II}}\n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{гл ru 12aСВ}}\n" +
                "\n";
        str = s1 + s2;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("вздуть", lt);
        assertEquals(2, result.length);
        assertEquals(POS.verb, result[0].getPOSType());
        assertEquals(POS.verb, result[1].getPOSType());

        assertTrue(result[0].getText().toString().equalsIgnoreCase(s1));
        assertTrue(result[1].getText().toString().equalsIgnoreCase(s2));
    }

    /*  POS defined by the template {{заголовок|add=(прилагательное)}},
     * where there are only two parameters,
     * and second parameter in brackets is POS
     *
     * = {{-ru-}} =
     * {{заголовок|add=(прилагательное)}}
     * {{заголовок|add=(cуществительное)}}
     */
    @Test
    public void testSplitToPOSSections_name_in_brackets() {
        System.out.println("splitToPOSSections_name_in_brackets");

        String str, s1, s2;
        POSText[] result;
        LangText lt;
        lt = new LangText(LanguageType.ru);

        // two POS in {{-ru-}} in Russian Wiktionary
        s1 =    "Before \n" +
                "{{заголовок|add=(прилагательное)}}\n" +
                "=== Морфологические и синтаксические свойства ===\n" +
                "{{прил ru 1bX}}\n" +
                "\n";
        s2 =    "{{заголовок|add=(cуществительное)}}\n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{сущ ru m a (п 1b)}}\n" +
                "\n";
        str = s1 + s2;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("голубой", lt);
        assertEquals(2, result.length);
        assertEquals(POS.adjective, result[0].getPOSType());
        assertEquals(POS.noun, result[1].getPOSType());

        assertTrue(result[0].getText().toString().equalsIgnoreCase(s1));
        assertTrue(result[1].getText().toString().equalsIgnoreCase(s2));
    }

    @Test
    public void guessPOS_till_hyphen_not_space() {
        System.out.println("testGuessPOS_till_hyphen_not_space");

        String str, page_title;
        LangText lt;
        POSText pt;

        // particle
        page_title = "ага";
        lt = new LangText(LanguageType.en);
        str =   "Before \n" +
                "== ага́ I ==\n" +
                "=== Морфологические и синтаксические свойства ===\n" +
                "{{part-ru|}}\n" +
                "\n";
        lt.text = new StringBuffer(str);
        pt = WPOSRu.guessPOS(lt.text);
        assertEquals(POS.particle, pt.getPOSType());

        // adverb
        page_title = "добро";
        lt = new LangText(LanguageType.en);
        str =   "Before \n" +
                "== добро II ==\n" +
                "=== Морфологические и синтаксические свойства ===\n" +
                "{{adv-ru|до́б-ро}}\n" +
                "\n";
        lt.text = new StringBuffer(str);
        pt = WPOSRu.guessPOS(lt.text);
        assertEquals(POS.adverb, pt.getPOSType());
    }

    
    @Test
    public void guessPOSWith2ndLevelHeader() {
        System.out.println("testGuessPOSWith2ndLevelHeader");
        
        String str, s1, s2, page_title;
        POS result;
        StringBuffer s;
        LangText lt;
        
        // I. Russian words in Russian Wiktionary
        // todo ...
    
        // + old format: ==Существительное== или ===Существительное===
        // todo ... 
        
        // II. English words in Russian Wiktionary
        
        // noun
        page_title = "bar";
        lt = new LangText(LanguageType.en);
        str =   "== bar II ==\n" + 
                "===Морфологические и синтаксические свойства===\n" + 
                "{{сущ en|nom-sg=bar|слоги=bar}}";
        lt.text = new StringBuffer(str);
        result = WPOSRu.guessPOSWith2ndLevelHeader(page_title, "bar II", lt.text);
        assertEquals(POS.noun, result);

        // one more noun (old version?)
        page_title = "адджындзинад";
        lt = new LangText(LanguageType.en);
        str =   "===Морфологические и синтаксические свойства===\n" +
                "{{падежи os|nom-sg={{PAGENAME}}}}";
        lt.text = new StringBuffer(str);
        result = WPOSRu.guessPOSWith2ndLevelHeader(page_title, "", lt.text);
        assertEquals(POS.noun, result);
     
        // adjective
        page_title = "round";
        lt = new LangText(LanguageType.en);
        str =   "== round I ==\n" + 
                "===Морфологические и синтаксические свойства===\n" + 
                "{{прил en|round|слоги=round}}";
        lt.text = new StringBuffer(str);
        result = WPOSRu.guessPOSWith2ndLevelHeader(page_title, "round I", lt.text);
        assertEquals(POS.adjective, result);
        
        // adverb (old style for "fast"):
        page_title = "fast";
        lt = new LangText(LanguageType.en);
        str =   "==Наречие==\n" + 
                "{{нар en|fast}}";
        lt.text = new StringBuffer(str);
        result = WPOSRu.guessPOSWith2ndLevelHeader(page_title, "Наречие", lt.text);
        assertEquals(POS.adverb, result);
        
        // Verb III (old style for "отделять"):
        page_title = "отделять";
        lt = new LangText(LanguageType.ru);
        str =   "== Глагол I ==\n" + 
                "# отделять";
        lt.text = new StringBuffer(str);
        result = WPOSRu.guessPOSWith2ndLevelHeader(page_title, "Глагол I", lt.text);
        assertEquals(POS.verb, result);
        
        // adverb (very old style for DE "fast")
        page_title = "fast";
        lt = new LangText(LanguageType.en); // ? de or ru ?
        str =   "<b>fast</b>\n" + 
                "Наречие\n" + 
                "==Произношение==\n" + 
                "{{transcription|fɑst}}\n" + 
                "==Значение==\n" + 
                "[[почти]]";
        lt.text = new StringBuffer(str);
        result = WPOSRu.guessPOSWith2ndLevelHeader(page_title, "Произношение", lt.text);
        // assertEquals(POS.adjective, result); too complex now
        assertEquals(null, result);  // too complex now: POS.unknown
    }

    @Test
    public void guessPOSWith2ndLevelHeader_POS_header_unknown_but_not_null() {
        System.out.println("splitToPOSSections_POS_header_unknown_but_not_null");
        
        String str, page_title;
        POS result;
        LangText lt;
        
        page_title = "round";
        lt = new LangText(LanguageType.en);
        str =   "== round I ==\n" + 
                "===3 level header===\n" + 
                "text which do not describe POS";
        lt.text = new StringBuffer(str);
        result = WPOSRu.guessPOSWith2ndLevelHeader(page_title, "round I", lt.text);
        assertEquals(POS.unknown, result);  // It's POS because "round I" == "round" + "I"
    }
    
    @Test
    public void guessPOSWith2ndLevelHeader_not_a_POS_header() {
        System.out.println("splitToPOSSections_not_a_POS_header");
        
        String str, page_title;
        POS result;
        LangText lt;
        
        page_title = "bar";
        lt = new LangText(LanguageType.en);
        str =   "==References==\n" + 
                "<references />";
        lt.text = new StringBuffer(str);
        result = WPOSRu.guessPOSWith2ndLevelHeader(page_title, "bar", lt.text);
        assertEquals(null, result);
    }

    // == Ссылки ==
    @Test
    public void testSplitToPOSSections_not_a_POS_header() {
        System.out.println("splitToPOSSections_not_a_POS_header");

        String str, s1, s2;
        POSText[] result;
        StringBuffer s;
        LangText lt;
        lt = new LangText(LanguageType.en);

        // one POS in {{-os-}} in Russian Wiktionary
        s1 =    "{{-os-}}\n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{падежи os\n" +
                "|nom-sg={{PAGENAME}}\n" +
                "|слоги={{по-слогам|а|.|га́}}\n" +
                "}}\n";
        s2 =    "== Ссылки ==\n" +
                "* Осетинско-русский словарь, 3-е дополненное издание, 1970 год\n" +
                "\n";
        str = s1 + s2;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("ага", lt);
        assertEquals(1, result.length);
        assertEquals(POS.noun, result[0].getPOSType());

        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    @Test
    public void testSplitToPOSSections_english_words() {
        System.out.println("splitToPOSSections_english_words");
        
        String str, s1, s2;
        POSText[] result;
        StringBuffer s;
        LangText lt;
        
        // I. Russian words in Russian Wiktionary
        // todo ...
        
        // + old format: ==Существительное== или ===Существительное===
        // todo ... 
        
        // II. English words in Russian Wiktionary
        
        // simple case: only one POS for English word "speak" {{-en-}}
        lt = new LangText(LanguageType.en);
        str = "\n ===Морфологические и синтаксические свойства===\n{{гл en irreg|speak|spoke|spoken}} text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.verb, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));

        // two POS in {{-en-}} in Russian Wiktionary
        s1 =    "Before \n" +
                "== lead I ==\n" +
                "English text1 \n";
        s2 =    "== lead II== \n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{гл en reg|lead}}\n";
        
        str = s1 + s2;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("lead", lt);
        assertEquals(2, result.length);
        assertEquals(POS.unknown, result[0].getPOSType());
        assertEquals(POS.verb, result[1].getPOSType());
        
        assertTrue(result[0].getText().toString().equalsIgnoreCase(s1));
        assertTrue(result[1].getText().toString().equalsIgnoreCase(s2));
        
        // one POS s2 in {{-en-}} in Russian Wiktionary
        str = s2;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("lead", lt);
        assertEquals(1, result.length);
        assertEquals(POS.verb, result[0].getPOSType());
        
        assertTrue(result[0].getText().toString().equalsIgnoreCase(s2));
        
        // + english words...
        // todo ...
        
    }

    /////////////////////////
    // tests of different POS 

    // noun
    // Фам - Surname (noun)
    @Test
    public void testSplitToPOSSections_POS_noun() {
        System.out.println("SplitToPOSSections_POS_noun");

        String str, s1, s2, page_title;
        POSText[] result;
        LangText lt;

        // СущМужНеодуш - noun
        page_title = "полвека_test";
        lt = new LangText(LanguageType.en);
        str =   "===Морфологические и синтаксические свойства===\n" +
                "{{СущМужНеодуш-пол\n" +
                "|основа=ве́ка\n" +
                "}}\n";
        lt.text = new StringBuffer(str);

        result = WPOSRu.splitToPOSSections(page_title, lt);
        assertEquals(1, result.length);
        assertEquals(POS.noun, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));

        // Фам - Surname (noun)
        page_title = "Новак_test";
        lt = new LangText(LanguageType.en);
        str =   "===Морфологические и синтаксические свойства===\n" +
                "{{Фам \n" +
                "|основа=Новак\n" +
                "|слоги={{по-слогам|Но|вак}}\n" +
                "}}\n";
        lt.text = new StringBuffer(str);
        
        result = WPOSRu.splitToPOSSections(page_title, lt);
        assertEquals(1, result.length);
        assertEquals(POS.noun, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    // adjective
    @Test
    public void testSplitToPOSSections_POS_adjective() {
        System.out.println("SplitToPOSSections_POS_adjective");

        String str, s1, s2, page_title;
        POSText[] result;
        StringBuffer s;
        LangText lt;

        // прил-сравн - adjective_comparative_degree
        page_title = "round";
        lt = new LangText(LanguageType.en);
        str =   "== round I ==\n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{прил-сравн ru|{{по-слогам|похле́ще}}}}";
        lt.text = new StringBuffer(str);
        
        result = WPOSRu.splitToPOSSections(page_title, lt);
        assertEquals(1, result.length);
        assertEquals(POS.adjective, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    @Test
    public void testSplitToPOSSections_ru_POS_pronoun() {
        System.out.println("splitToPOSSections_ru_POS_pronoun");

        String str;
        POSText[] result;
        LangText lt;

        // Russian words in Russian Wiktionary
        // I.a pronoun: мест
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n{{мест ru 6*b\n}} text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.pronoun, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
        
        // I.b pronoun: Мс (+ check uppercase, i.e. Мс == мс)
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n{{Мс-п6b\n}} text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.pronoun, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    @Test
    public void testSplitToPOSSections_ru_POS_numeral() {
        System.out.println("splitToPOSSections_ru_POS_numeral");

        String str;
        POSText[] result;
        LangText lt;

        // a) numeral: числ-2
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n{{числ-2\n}} text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.numeral, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));

        // b) numeral: числ
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n{{числ ru 3\n}} text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.numeral, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    @Test
    public void testSplitToPOSSections_ru_POS_conjunction() {
        System.out.println("splitToPOSSections_ru_POS_conjunction");

        String str;
        POSText[] result;
        LangText lt;
        
        // conj conjunction
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n{{conj ru|противительный|слоги=но}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.conjunction, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    // interj // междометие
    // interj1 - глагольно-междометное слово - verb-interjection word
    @Test
    public void testSplitToPOSSections_ru_POS_interjection() {
        System.out.println("splitToPOSSections_ru_POS_interjection");

        String str;
        POSText[] result;
        LangText lt;

        // interj
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n{{interj ru \n}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.interjection, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));

        // interj1 - глагольно-междометное слово - verb-interjection word
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n\n{{interj1 ru|слоги={{по-слогам|юрк}}}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.verb_interjection, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    // {{prep-ru|внутрь}}
    // {{prep ru|за}} text.
    @Test
    public void testSplitToPOSSections_ru_POS_preposition() {
        System.out.println("splitToPOSSections_ru_POS_preposition");

        String str;
        POSText[] result;
        LangText lt;

        // {{prep-ru|внутрь}}
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n{{prep-ru|внутрь}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.preposition, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));

        // {{prep ru|за}} text.
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n\n{{prep ru|за}} text.\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.preposition, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    // {{article en|the}}
    // and {{art XX|}}
    @Test
    public void testSplitToPOSSections_ru_POS_article() {
        System.out.println("splitToPOSSections_ru_POS_article");

        String str;
        POSText[] result;
        LangText lt;

        // {{art XX|}}
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n{{art de|}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.article, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));

        // {{article en|the}}
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n{{article en|the}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.article, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    // {{prefix ru|без}}
    // and {{suffix ru|ка|оконч=ть}}
    @Test
    public void testSplitToPOSSections_ru_POS_prefix_suffix() {
        System.out.println("splitToPOSSections_ru_POS_prefix_suffix");

        String str;
        POSText[] result;
        LangText lt;

        // {{prep-ru|внутрь}}
        lt = new LangText(LanguageType.ru);
        str = "\n ===Морфологические и синтаксические свойства===\n{{prefix ru|без}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.prefix, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));

        // {{заголовок|add=(ь)}}
        // {{suffix ru|ен|оконч=ь}}
        lt = new LangText(LanguageType.ru);
        
        //str = "\n{{заголовок|add=(ь)}}\n{{suffix ru|ен|оконч=ь}}\n text1";
        str = "\n ===Морфологические и синтаксические свойства===\n{{suffix ru|ен|оконч=ь}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.suffix, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    // phrase
    @Test
    public void testSplitToPOSSections_ru_POS_phrase() {
        System.out.println("splitToPOSSections_ru_POS_phrase");

        String str;
        POSText[] result;
        LangText lt;

        lt = new LangText(LanguageType.ru);
        str = "\n=== Тип и синтаксические свойства сочетания ===\n{{phrase|\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.phrase, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));

        // === Тип и синтаксические свойства сочетания ===
        // {{phrase
        // |тип=
        lt = new LangText(LanguageType.ru);
        str = "\n=== Тип и синтаксические свойства сочетания ===\n{{phrase\n|тип=\n}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.phrase, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    // abbrev - abbreviation
    @Test
    public void testSplitToPOSSections_ru_POS_abbreviation() {
        System.out.println("splitToPOSSections_ru_POS_abbreviation");

        String str;
        POSText[] result;
        LangText lt;

        // {{abbrev|lang=en|роль=наречия}}
        lt = new LangText(LanguageType.ru);
        str = "\n=== Морфологические и синтаксические свойства ===\n\n{{abbrev|lang=en|роль=наречия}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.abbreviation, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    // predicative - Именная часть составного сказуемого, предикатив
    @Test
    public void testSplitToPOSSections_ru_POS_predicative() {
        System.out.println("splitToPOSSections_ru_POS_predicative");

        String str;
        POSText[] result;
        LangText lt;

        // {{predic ru|{{по-слогам|пол|ны́м-пол|но́}}}}
        lt = new LangText(LanguageType.ru);
        str = "\n=== Морфологические и синтаксические свойства ===\n\n{{predic ru|{{по-слогам|пол|ны́м-пол|но́}}}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.predicative, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    // intro - parenthesis
    @Test
    public void testSplitToPOSSections_ru_POS_intro_parenthesis() {
        System.out.println("splitToPOSSections_ru_POS_intro_parenthesis");

        String str;
        POSText[] result;
        LangText lt;

        lt = new LangText(LanguageType.ru);
        str = "\n=== Морфологические и синтаксические свойства ===\n\n{{intro ru|{{по-слогам|на|при|ме́р}}}}\n text1";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("test_word1", lt);
        assertEquals(1, result.length);
        assertEquals(POS.parenthesis, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }

    // adjectival_participle - Причастие
    @Test
    public void testSplitToPOSSections_POS_adjectival_participle() {
        System.out.println("splitToPOSSections_POS_adjectival_participle");

        String str;
        POSText[] result;
        LangText lt;

        lt = new LangText(LanguageType.ru);
        str = "\n=== Морфологические и синтаксические свойства ===\n" + 
                "{{прич ru 1a-т\n"+
                "|основа=мя́\n"+
                "}}";
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("мятый_word1", lt);
        assertEquals(1, result.length);
        //assertEquals(POS.adjectival_participle, result[0].getPOSType());
        assertEquals(POS.participle, result[0].getPOSType());
        assertTrue(result[0].getText().toString().equalsIgnoreCase(str));
    }


    // eo tests of different POS
    ////////////////////////////

    
    /** additional_second_level_blocks
     * <references />
     */
    @Test
    public void testSplitToPOSSections_additional_second_level_blocks() {
        System.out.println("splitToPOSSections_additional_second_level_blocks");
        
        String str, s1, s2, second_level_block;
        POSText[] result;
        StringBuffer s;
        LangText lt;
        lt = new LangText(LanguageType.en);
        
        // two POS in {{-en-}} in Russian Wiktionary
        s1 =    "Before \n" +
                "== lead I ==\n" +
                "English text1 \n";
        s2 =    "== lead II== \n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{гл en reg|lead}}\n";
        second_level_block = 
                "==Примечания==\n" +
                "<references />";
        
        str = s1 + s2 + second_level_block;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("lead", lt);
        assertEquals(2, result.length);
        assertEquals(POS.unknown, result[0].getPOSType());
        assertEquals(POS.verb, result[1].getPOSType());
        
        assertTrue(result[0].getText().toString().equalsIgnoreCase(s1));
        assertTrue(result[1].getText().toString().equalsIgnoreCase(s2));    //assertTrue(result[1].text.toString().equalsIgnoreCase( s2.concat(second_level_block) ));
        
        
        // one POS in {{-en-}} in Russian Wiktionary
        s1 =    "Before \n" +
                "== lead I ==\n" +
                "English text1 \n";
        second_level_block = 
                "==Примечания==\n" +
                "<references />";
        
        str = s1 + second_level_block;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("lead", lt);
        assertEquals(1, result.length);
        assertEquals(POS.unknown, result[0].getPOSType());
        assertTrue(s1.equalsIgnoreCase( result[0].getText().toString() ));
    }
    
    @Test
    public void testSplitToPOSSections_second_level_title_with_accent() {
        System.out.println("splitToPOSSections_second_level_title_with_accent");
        
        String str, s1, s2, s3;
        POSText[] result;
        StringBuffer s;
        LangText lt;
        lt = new LangText(LanguageType.en);
        
        // two POS in {{-en-}} in Russian Wiktionary
        s1 =    "Before \n" +
                "== ага́ I ==\n" +
                "=== Морфологические и синтаксические свойства ===\n" +
                "{{part-ru|}}\n" +
                "\n";
        s2 =    "== ага́ II ==\n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{сущ ru f a 3b\n" +
                "}}\n";
        s3 =    "== а́га ==\n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{сущ ru f a 3b\n" +
                "}}\n";
        str = s1 + s2 + s3;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("ага", lt);
        assertEquals(3, result.length);
        assertEquals(POS.particle,  result[0].getPOSType());
        assertEquals(POS.noun,      result[1].getPOSType());
        assertEquals(POS.noun,      result[2].getPOSType());
        
        assertTrue(result[0].getText().toString().equalsIgnoreCase(s1));
        assertTrue(result[1].getText().toString().equalsIgnoreCase(s2));
        assertTrue(result[2].getText().toString().equalsIgnoreCase(s3));
    }

    

    @Test
    public void testSplitToPOSSections_second_level_title_with_internal_brackets() {
        System.out.println("splitToPOSSections_second_level_title_with_internal_brackets");
        
        String str, s1, s2;
        POSText[] result;
        StringBuffer s;
        LangText lt;
        lt = new LangText(LanguageType.en);
        
        // two POS in {{-en-}} in Russian Wiktionary
        s1 =    "Before \n" +
                "== ага́ I ==\n" +
                "=== Морфологические и синтаксические свойства ===\n" +
                "{{part-ru|}}\n" +
                "\n";
        s2 =    "== ага́ II ==\n" +
                "===Морфологические и синтаксические свойства===\n" +
                "{{сущ ru f a 3b\n" +
                "|основа=аг\n" +
                "|слоги={{по-слогам|а|.|га́}}\n" +
                "|show-text=1\n" +
                "}}\n";
        str = s1 + s2;
        lt.text = new StringBuffer(str);
        result = WPOSRu.splitToPOSSections("ага", lt);
        assertEquals(2, result.length);
        assertEquals(POS.particle,  result[0].getPOSType());
        assertEquals(POS.noun,      result[1].getPOSType());
        
        assertTrue(result[0].getText().toString().equalsIgnoreCase(s1));
        assertTrue(result[1].getText().toString().equalsIgnoreCase(s2));
    }
 
}