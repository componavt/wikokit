
package wikt.multi.ru;

import java.util.Map;
//import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikt.constant.Relation;
import wikt.util.POSText;
import wikt.word.WRelation;

import wikt.constant.POS;
import wikt.util.WikiText;

public class WRelationRuTest {

    public static String samolyot_text, kolokolchik_text;

    public WRelationRuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        samolyot_text = "==== Значение ====\n" +
                        "# летательный [[аппарат]] тяжелее [[воздух]]а с жёстким [[крыло]]м и собственным [[мотор]]ом {{пример|Самолёт-истребитель.}} {{пример|Военный cамолёт.}} {{пример|Эскадрилья самолётов.}}\n" +
                        "\n" +
                        "==== Синонимы ====\n" +
                        "# [[аэроплан]], [[воздушный лайнер]]\n" +
                        "\n" +
                        "==== Антонимы ====\n" +
                        "# -\n" +
                        "\n" +
                        "==== Гиперонимы ====\n" +
                        "# [[авиация]], [[транспорт]]\n" +
                        "\n" +
                        "==== Гипонимы ====\n" +
                        "# [[штурмовик]], [[истребитель]], [[экранолёт]], [[экраноплан]], [[моноплан]], [[биплан]], [[триплан]], [[многоплан]], [[аэробус]]\n" +
                        "\n" +
                        "==== Согипонимы ====\n" +
                        "# [[планер]], [[махолёт]], [[мускулолёт]], [[дельтаплан]], [[параплан]]; [[турболёт]]; [[вертолёт]], [[автожир]], [[винтокрыл]]; [[атомолёт]]\n" +
                        "\n" +
                        "==== Холонимы ====\n" +
                        "# [[эскадрилья]]\n" +
                        "\n" +
                        "==== Меронимы ====\n" +
                        "# [[авиапушка]], [[фюзеляж]], [[крыло]], [[двигатель]], [[винт]]\n" +
                        "\n" +
                        "=== Родственные слова ===\n";
        
        
        kolokolchik_text =   "===Произношение===\n" +
                        "====Значение====\n" +
                        "====Синонимы====\n" +
                        "# [[кандия]] (церк.)\n" +
                        "# -\n" +
                        "# -\n" +
                        "\n" +
                        "====Антонимы====\n" +
                        "# -\n" +
                        "# -\n" +
                        "# -\n" +
                        "\n" +
                        "====Гиперонимы====\n" +
                        "# [[звонок]], [[колокол]]\n" +
                        "# [[инструмент]]\n" +
                        "# [[цветок]], [[растение]]\n" +
                        "\n" +
                        "====Гипонимы====\n" +
                        "# [[бубенчик]]\n" +
                        "# -\n" +
                        "# [[колокольчик средний]]\n" +
                        "\n" +
                        "==== Холонимы ====\n" +
                        "# [[самозвон]]\n" +
                        "# -\n" +
                        "# -\n" +
                        "\n" +
                        "==== Меронимы ====\n" +
                        "# [[язычок]]\n" +
                        "# [[пластинка]], [[ящик]]\n" +
                        "# -\n" +
                        "\n" +
                        "===Родственные слова===\n";
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParse_3_line_synonyms() {
        System.out.println("parse_kolokolchik_3_line_synonyms");
        WRelation[] r;

        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "колокольчик";
        POSText pt = new POSText(POS.noun, kolokolchik_text);

        Map<Relation, WRelation[]> result = WRelationRu.parse(wikt_lang, page_title, pt);

        assertTrue(result.size() > 0);
        assertTrue(result.containsKey(Relation.synonymy));

        // ====Синонимы====
        // # [[кандия]] (церк.)
        // # -
        // # -
        r = result.get(Relation.synonymy);
        assertEquals(3, r.length);
        WikiText[] synonym_row_0 = r[0].get();
        assertEquals(1, synonym_row_0.length);
        assertTrue(synonym_row_0[0].getVisibleText().equalsIgnoreCase("кандия (церк.)"));
        assertTrue(synonym_row_0[0].getWikiWords()[0].getWordLink().equalsIgnoreCase("кандия"));

        assertFalse(result.containsKey(Relation.antonymy));
        r = result.get(Relation.antonymy);
        assertEquals(null, r);

        // ====Гиперонимы====   hypernymy
        // # [[звонок]], [[колокол]]
        // # [[инструмент]]
        // # [[цветок]], [[растение]]
        assertTrue(result.containsKey(Relation.hypernymy));
        r = result.get(Relation.hypernymy);
        assertEquals(3, r.length);

        WikiText[] hypernymy_row_1 = r[1].get();
        assertEquals(1, hypernymy_row_1.length);
        assertTrue(hypernymy_row_1[0].getVisibleText().equalsIgnoreCase("инструмент"));

        // ====Гипонимы====
        //# [[бубенчик]]
        //# -
        //# [[колокольчик средний]]
    }

    @Test
    public void testParse_one_line_synonyms() {
        System.out.println("parse_samolyot_one_line_synonyms");
        WRelation[] r;

        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "самолёт";
        POSText pt = new POSText(POS.noun, samolyot_text);

        Map<Relation, WRelation[]> result = WRelationRu.parse(wikt_lang, page_title, pt);
        assertTrue(result.size() > 0);

        // ==== Синонимы ====
        // # [[аэроплан]], [[воздушный лайнер]]
        assertTrue(result.containsKey(Relation.synonymy));
        r = result.get(Relation.synonymy);
        assertEquals(1, r.length);

        WikiText[] synonym_row_0 = r[0].get();
        assertEquals(2, synonym_row_0.length);
        assertTrue(synonym_row_0[0].getVisibleText().equalsIgnoreCase("аэроплан"));
        assertTrue(synonym_row_0[1].getWikiWords()[0].getWordLink().equalsIgnoreCase("воздушный лайнер"));
        
        // ==== Антонимы ====
        // # -
        assertFalse(result.containsKey(Relation.antonymy));

        // ==== Гиперонимы ====
        // # [[авиация]], [[транспорт]]
        assertTrue(result.containsKey(Relation.hypernymy));
        r = result.get(Relation.hypernymy);
        assertEquals(1, r.length);

        WikiText[] hypernymy_row_0 = r[0].get();
        assertEquals(2, hypernymy_row_0.length);
        assertTrue(hypernymy_row_0[0].getVisibleText().equalsIgnoreCase("авиация"));
        assertTrue(hypernymy_row_0[1].getWikiWords()[0].getWordLink().equalsIgnoreCase("транспорт"));
        
    }

    /**
     * Test of parseOneKindOfRelation method, of class WRelationRu.
     */
    /*@Test
    public void testParseOneKindOfRelation() {
        System.out.println("parseOneKindOfRelation");
        LanguageType wikt_lang = null;
        String page_title = "";
        String text = "";
        Pattern relation_header_pattern = null;
        Relation relation = null;
        WRelation[] expResult = null;
        WRelation[] result = WRelationRu.parseOneKindOfRelation(wikt_lang, page_title, text, relation_header_pattern, relation);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

}