
package wikokit.base.wikt.multi.ru;

import java.util.Map;
//import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.word.WRelation;

import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.multi.ru.name.LabelRu;
import wikokit.base.wikt.util.LabelsWikiText;

public class WRelationRuTest {

    public static String samolyot_text, kolokolchik_text, 
            empty_relation, empty_hyphen_relation,
                            empty_hyphen2_relation;

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

        empty_relation = "==== Значение ====\n" +
                        "# some definition {{пример|some example.}}\n" +
                        "\n" +
                        "==== Синонимы ====\n" +
                        "# &#160;\n" +
                        "\n" +
                        "=== Родственные слова ===\n";

        empty_hyphen_relation = "==== Значение ====\n" +
                        "# some definition {{пример|some example.}}\n" +
                        "# definition 2 {{пример|}}\n" +
                        "# def 3 {{пример|}}\n" +
                        "\n" +
                        "==== Синонимы ====\n" +
                        "#{{-}}\n" +
                        "#{{-}}\n" +
                        "# [[synonym 2]]\n" +
                        "\n" +
                        "=== Родственные слова ===\n";

        empty_hyphen2_relation = "==== Значение ====\n" +
                        "# some definition {{пример|some example.}}\n" +
                        "# definition 2 {{пример|}}\n" +
                        "# def 3 {{пример|}}\n" +
                        "\n" +
                        "==== Синонимы ====\n" +
                        "# [[synonym 2]]\n" +
                        "#{{-}}\n" +
                        "#{{-}}\n" +
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
        LabelsWikiText[] synonym_row_0 = r[0].get();
        assertEquals(1, synonym_row_0.length);
        assertTrue(synonym_row_0[0].getWikiText().getVisibleText().equalsIgnoreCase("кандия"));
        assertTrue(synonym_row_0[0].getWikiText().getWikiWords()[0].getWordLink().equalsIgnoreCase("кандия"));

        // antonymy
        assertFalse(result.containsKey(Relation.antonymy));
        r = result.get(Relation.antonymy);
        assertNull(r);

        // ====Гиперонимы====   hypernymy
        // # [[звонок]], [[колокол]]
        // # [[инструмент]]
        // # [[цветок]], [[растение]]
        assertTrue(result.containsKey(Relation.hypernymy));
        r = result.get(Relation.hypernymy);
        assertEquals(3, r.length);

        LabelsWikiText[] hypernymy_row_1 = r[1].get();
        assertEquals(1, hypernymy_row_1.length);
        assertTrue(hypernymy_row_1[0].getWikiText().getVisibleText().equalsIgnoreCase("инструмент"));

        // ====Гипонимы==== hyponymy
        //# [[бубенчик]]
        //# -
        //# [[колокольчик средний]]
        r = result.get(Relation.hyponymy);
        assertEquals(3, r.length);
        assertEquals(null, r[1]);
        
        // ==== Холонимы ==== holonymy
        // # [[самозвон]]
        // # -
        // # -
        r = result.get(Relation.holonymy);
        assertEquals(3, r.length);
        assertEquals(1, r[0].get().length);

        // ==== Меронимы ==== meronymy
        // # [[язычок]]
        // # [[пластинка]], [[ящик]]
        // # -
        r = result.get(Relation.meronymy);
        assertEquals(3, r.length);
        LabelsWikiText[] meronymy_row_1 = r[1].get();
        assertEquals(2, meronymy_row_1.length);
        assertTrue(meronymy_row_1[0].getWikiText().getVisibleText().equalsIgnoreCase("пластинка"));
        assertTrue(meronymy_row_1[1].getWikiText().getWikiWords()[0].getWordLink().equalsIgnoreCase("ящик"));
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

        LabelsWikiText[] synonym_row_0 = r[0].get();
        assertEquals(2, synonym_row_0.length);
        assertTrue(synonym_row_0[0].getWikiText().getVisibleText().equalsIgnoreCase("аэроплан"));
        assertTrue(synonym_row_0[1].getWikiText().getWikiWords()[0].getWordLink().equalsIgnoreCase("воздушный лайнер"));
        
        // ==== Антонимы ====
        // # -
        assertFalse(result.containsKey(Relation.antonymy));

        // ==== Гиперонимы ====
        // # [[авиация]], [[транспорт]]
        assertTrue(result.containsKey(Relation.hypernymy));
        r = result.get(Relation.hypernymy);
        assertEquals(1, r.length);

        LabelsWikiText[] hypernymy_row_0 = r[0].get();
        assertEquals(2, hypernymy_row_0.length);
        assertTrue(hypernymy_row_0[0].getWikiText().getVisibleText().equalsIgnoreCase("авиация"));
        assertTrue(hypernymy_row_0[1].getWikiText().getWikiWords()[0].getWordLink().equalsIgnoreCase("транспорт"));

        // ==== Гипонимы ====
        // # [[штурмовик]], [[истребитель]], [[экранолёт]], [[экраноплан]], [[моноплан]], [[биплан]], [[триплан]], [[многоплан]], [[аэробус]]
        assertTrue(result.containsKey(Relation.hyponymy));
        r = result.get(Relation.hyponymy);
        assertEquals(1, r.length);

        LabelsWikiText[] hyponymy_row_0 = r[0].get();
        assertEquals(9, hyponymy_row_0.length);
        assertTrue(hyponymy_row_0[0].getWikiText().getVisibleText().equalsIgnoreCase("штурмовик"));
        assertTrue(hyponymy_row_0[8].getWikiText().getWikiWords()[0].getWordLink().equalsIgnoreCase("аэробус"));

        // ==== Холонимы ====   holonymy
        // # [[эскадрилья]]
        r = result.get(Relation.holonymy);
        assertEquals(1, r.length);
        assertEquals(1, r[0].get().length);

        LabelsWikiText[] holonymy_row_0 = r[0].get();
        assertTrue(holonymy_row_0[0].getWikiText().getVisibleText().equalsIgnoreCase("эскадрилья"));
        assertTrue(holonymy_row_0[0].getWikiText().getWikiWords()[0].getWordLink().equalsIgnoreCase("эскадрилья"));
    }

    // test that "# &#160;" is an empty_relation
    @Test
    public void testParse_empty_synonyms() {
        System.out.println("parse_empty_synonyms");
        WRelation[] r;

        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "empty_relation";
        POSText pt = new POSText(POS.noun, empty_relation);

        Map<Relation, WRelation[]> result = WRelationRu.parse(wikt_lang, page_title, pt);

        // ====Синонимы====
        // # &#160;
        assertEquals(0, result.size());
    }

    // test that "#{{-}}" is an empty_relation
    @Test
    public void testParse_empty_hyphen() {
        System.out.println("parse_empty_hyphen_in_synonyms");
        WRelation[] r;
        POSText pt;
        Map<Relation, WRelation[]> result;

        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "empty_relation";
        
        // test 1.
        pt = new POSText(POS.noun, empty_hyphen_relation);
        
        result = WRelationRu.parse(wikt_lang, page_title, pt);

        // ====Синонимы====
        // #{{-}}
        // #{{-}}
        // # [[some synonyms]]
        assertEquals(1, result.size());
        r = result.get(Relation.synonymy);
        assertEquals(3, r.length);
        assertNull(r[0]);
        assertNull(r[1]);
        assertNotNull(r[2]);

        // test 2.
        pt = new POSText(POS.noun, empty_hyphen2_relation);

        result = WRelationRu.parse(wikt_lang, page_title, pt);

        // ====Синонимы====
        // # [[some synonyms]]
        // #{{-}}
        // #{{-}}
        assertEquals(1, result.size());
        r = result.get(Relation.synonymy);
        assertEquals(3, r.length);
        assertNotNull(r[0]);
        assertNull(r[1]);
        assertNull(r[2]);
    }

    
    // /////////////////////////////////////////////////////
    // Context labels in synonyms (parseOneLine function)
    // @see http://ru.wiktionary.org/wiki/Викисловарь:Правила_оформления_статей#Оформление_семантических_отношений
    // @see http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%9F%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D0%B0_%D0%BE%D1%84%D0%BE%D1%80%D0%BC%D0%BB%D0%B5%D0%BD%D0%B8%D1%8F_%D1%81%D1%82%D0%B0%D1%82%D0%B5%D0%B9#.D0.9E.D1.84.D0.BE.D1.80.D0.BC.D0.BB.D0.B5.D0.BD.D0.B8.D0.B5_.D1.81.D0.B5.D0.BC.D0.B0.D0.BD.D1.82.D0.B8.D1.87.D0.B5.D1.81.D0.BA.D0.B8.D1.85_.D0.BE.D1.82.D0.BD.D0.BE.D1.88.D0.B5.D0.BD.D0.B8.D0.B9
    
    // # [[ремень]], [[поясок]]; частичн.: [[гайтан]]; устар.: [[кушак]], [[обвязка]]
    @Test
    public void testParseOneLine_labels_as_text() {
        System.out.println("ParseOneLine_labels_as_text");
        String page_title, text;
        WRelation result;
        Label la;
        
        page_title = "пояс";
        text = "# [[ремень]], [[поясок]]; частичн.: [[гайтан]]; устар.: [[кушак]], [[обвязка]]";
        
        result = WRelationRu.parseOneLine(page_title, text);
        
        LabelsWikiText[] lwt_array = result.get();
        assertEquals(5, lwt_array.length);
        
        // 1) [[ремень]]
        assertEquals(0, lwt_array[0].getLabels().length);
        
        // 2) [[поясок]]
        assertEquals(0, lwt_array[1].getLabels().length);
        
        // 3) частичн.: [[гайтан]]
        assertEquals("гайтан", lwt_array[2].getWikiText().getVisibleText());
                
        Label[] labels_gaitan = lwt_array[2].getLabels();
        assertEquals(1, labels_gaitan.length);
        la = labels_gaitan[0];
        assertEquals(LabelRu.partial.getShortName(), la.getShortName());  // частичн.
        
        // 4) устар.: [[кушак]]
        assertEquals("кушак", lwt_array[3].getWikiText().getVisibleText());
        Label[] labels_kushak = lwt_array[3].getLabels();
        assertEquals(1, labels_kushak.length);// устар.
        la = labels_kushak[0];
        assertEquals(LabelRu.obsolete.getShortName(), la.getShortName()); 
        
        // 5) устар.: [[обвязка]]
        assertEquals("обвязка", lwt_array[4].getWikiText().getVisibleText());
        Label[] labels_obvyazka = lwt_array[4].getLabels();
        assertEquals(1, labels_obvyazka.length);// устар.
        la = labels_obvyazka[0];
        assertEquals(LabelRu.obsolete.getShortName(), la.getShortName());
    }
    
    // # [[Питер]] (разг.), [[град Петров]] (поэт., высок.)
    @Test
    public void testParseOneLine_labels_in_brackets() {
        System.out.println("ParseOneLine_labels_in_brackets");
        String page_title, text;
        WRelation result;
        Label la;
        
        page_title = "Санкт-Петербург";
        text = "# [[Питер]] (разг.), [[град Петров]] (поэт., высок.)";
        
        result = WRelationRu.parseOneLine(page_title, text);
        
        LabelsWikiText[] lwt_array = result.get();
        assertEquals(2, lwt_array.length);
        
        // 1) [[Питер]] (разг.)
        assertEquals("Питер", lwt_array[0].getWikiText().getVisibleText());
                
        Label[] labels_Piter = lwt_array[0].getLabels();
        assertEquals(1, labels_Piter.length);
        la = labels_Piter[0];
        assertEquals(LabelRu.colloquial.getShortName(), la.getShortName());// разг.
        
        // 2) [[град Петров]] (поэт., высок.)
        assertEquals("град Петров", lwt_array[1].getWikiText().getVisibleText());
        
        Label[] labels_grad = lwt_array[1].getLabels();
        assertEquals(2, labels_grad.length);// поэт., высок.
        
        assertEquals(LabelRu.poetic.getShortName(), labels_grad[0].getShortName());// поэт.
        assertEquals(LabelRu.elevated.getShortName(), labels_grad[1].getShortName());// высок.
    }
    
    
    // # {{груб.|-}}: [[копыто]]; {{п.|-}}, {{груб.|-}}: [[ходуля|ходули]]; {{помета|частичн.}}: [[лапа]], [[щупальце]]
    @Test
    public void testParseOneLine_labels_in_templates() {
        System.out.println("ParseOneLine_labels_in_templates");
        String page_title, text;
        WRelation result;
        Label la;
        
        page_title = "нога";
        text = "# {{груб.|-}}: [[копыто]]; {{п.|-}}, {{груб.|-}}: [[ходуля|ходули]]; {{помета|частичн.}}: [[лапа]], [[щупальце]]";
        
        result = WRelationRu.parseOneLine(page_title, text);
        
        LabelsWikiText[] lwt_array = result.get();
        assertEquals(4, lwt_array.length);// 1) груб., 2) переносное, грубое, 3) частичн. 4) частичн.
        
        // 1) {{груб.|-}}: [[копыто]]
        assertEquals("копыто", lwt_array[0].getWikiText().getVisibleText());
                
        Label[] labels_kopwto = lwt_array[0].getLabels();
        assertEquals(1, labels_kopwto.length);
        la = labels_kopwto[0];
        assertEquals(LabelRu.acerbity.getShortName(), la.getShortName());// груб.
        
        // 2) {{п.|-}}, {{груб.|-}}: [[ходуля|ходули]]
        assertEquals("ходули", lwt_array[1].getWikiText().getVisibleText());
        
        Label[] labels_hoduli = lwt_array[1].getLabels();
        assertEquals(2, labels_hoduli.length);// п., груб.
        
        assertEquals(LabelRu.figuratively.getShortName(), labels_hoduli[0].getShortName());// п.
        assertEquals(LabelRu.acerbity.getShortName(), labels_hoduli[1].getShortName());// груб.
        
        // 3) "лапа" 
        // {{помета|частичн.}}: [[лапа]], [[щупальце]]
        assertEquals("лапа", lwt_array[2].getWikiText().getVisibleText());
        
        Label[] labels_lapa = lwt_array[2].getLabels();
        assertEquals(1, labels_lapa.length);// частичн.
        assertEquals(LabelRu.partial.getShortName(), labels_lapa[0].getShortName());// частичн.
        
        // 4) "щупальце" 
        // {{помета|частичн.}}: [[лапа]], [[щупальце]]
        assertEquals("щупальце", lwt_array[3].getWikiText().getVisibleText());
        
        Label[] labels_shupaltse = lwt_array[3].getLabels();
        assertEquals(1, labels_shupaltse.length);// частичн.
        assertEquals(LabelRu.partial.getShortName(), labels_shupaltse[0].getShortName());// частичн.
    }
    
    // # [[копыто]] ({{груб.|-}}); [[ходуля|ходули]] ({{п.|-}}, {{груб.|-}})
    @Test
    public void testParseOneLine_labels_in_templates_in_brackets() {
        System.out.println("ParseOneLine_labels_in_templates_in_brackets");
        String page_title, text;
        WRelation result;
        Label la;
        
        page_title = "нога";
        text = "# [[копыто]] ({{груб.|-}}); [[ходуля|ходули]] ({{п.|-}}, {{груб.|-}})";
        
        result = WRelationRu.parseOneLine(page_title, text);
        
        LabelsWikiText[] lwt_array = result.get();
        assertEquals(2, lwt_array.length);// 1) груб., 2) переносное, грубое
        
        // 1) {{груб.|-}}: [[копыто]]
        assertEquals("копыто", lwt_array[0].getWikiText().getVisibleText());
                
        Label[] labels_kopwto = lwt_array[0].getLabels();
        assertEquals(1, labels_kopwto.length);
        la = labels_kopwto[0];
        assertEquals(LabelRu.acerbity.getShortName(), la.getShortName());// груб.
        
        // 2) {{п.|-}}, {{груб.|-}}: [[ходуля|ходули]]
        assertEquals("ходули", lwt_array[1].getWikiText().getVisibleText());
        
        Label[] labels_hoduli = lwt_array[1].getLabels();
        assertEquals(2, labels_hoduli.length);// п., груб.
        
        assertEquals(LabelRu.figuratively.getShortName(), labels_hoduli[0].getShortName());// п.
        assertEquals(LabelRu.acerbity.getShortName(), labels_hoduli[1].getShortName());// груб.
    }
    
    
    
    // eo context labels in synonyms
    // /////////////////////////////////////////////////////

}