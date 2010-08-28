
package wikt.word;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikt.util.LangText;

public class WLanguageTest {

    public WLanguageTest() {
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

}