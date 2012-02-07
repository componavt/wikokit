
package wikokit.base.wikt.multi.en;

import wikokit.base.wikt.multi.en.WRelationEn;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.word.WRelation;

import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.util.WikiText;

public class WRelationEnTest {

    public static String test_hrunk, word_text, flower_text,
            empty_relation, 
            empty_relation2,
            bark, man, man2, women, nationality, airplane, 
            Suomija_template_l, poljento_template_l;
    // todo parse: man
    public WRelationEnTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        
        test_hrunk =
                "# Definition hrunk 1.\n" +
                "# Definition hrunk 2.\n" +
                "\n" +
                "====Synonyms====\n" +
                "* (''flrink with cumplus''): [[flrink]], [[pigglehick]]\n" +
                "* (''furp''): [[furp]], [[whoodleplunk]]";

        word_text =
                "{{en-noun}}\n" +
                "\n" +
                "# {{linguistics}} A distinct unit of language\n" +
                "# A distinct unit of language which is approved by some authority.\n" +
                "# Something [[promise]]d, (as in a [[contract]] or [[oath]]).\n" +
                "# [[news|News]]; [[tidings]].\n" +
                "# {{theology|sometimes '''[[Word]]'''}} [[God]].\n" +
                "#* See [[s:Bible (King James)/1 John|Bible, King James, John 1]]\n" +
                "# {{theology|sometimes '''[[Word]]'''}} The [[bible|Bible]].\n" +
                "\n" +
                "====Usage notes====\n" +
                "* \n" +
                "====Synonyms====\n" +
                "* {{sense|distinct unit of language}} [[vocable]]\n" +
                "* {{sense|something promised}} [[promise]]\n" +
                "* {{sense|God}} [[God]], [[logos|Logos]]\n" +
                "* {{sense|Bible}} [[word of God]], [[Bible]]\n" +
                "* See also [[Wikisaurus:word]]\n" +
                "\n" +
                "====Translations====\n" +
                "{{trans-top|unit of language}}\n" +
                "* [[Afrikaans]]: [[woord]]\n" +
                "\n";

        flower_text = "====Synonyms====\n" +
                "* {{sense|inflorescence that resembles a flower}} [[head]], [[pseudanthium]]\n" +
                "* {{sense|best examples}} [[cream]]\n" +
                "* {{sense|best state of things}} [[prime]]\n" +
                "\n";

        empty_relation = "====Hyponyms====\n" +
                "* See also [[Wikisaurus:tree]]\n" +
                "\n";

        empty_relation2 = // human
                "{{wikipedia}}\n" +
                "{{en-noun}}\n" +
                "\n" +
                "# A [[human being]], whether [[man]], [[woman]] or [[child]].\n" +
                "\n" +
                "====Translations====\n";

        bark = "{{en-verb}}\n" +
                "=====Related terms=====\n" +
                "* [[barking]]\n" +
                "\n" +
                "\n" +
                "=====Synonyms=====\n" +
                "* [[latrate]] {{i|obsolete}}\n" +
                "\n" +
                "=====Translations=====\n";
/* todo
        man = "{{en-noun|men}}\n" +
                "\n" +
                "# An adult [[male]] [[human]].\n" +
                "# A [[mensch]]; a person of [[integrity]] and [[honor]].\n" +
                "#* '''1883''', [[w:Robert Louis Stevenson|Robert Louis Stevenson]], ''[[wikisource:Treasure Island|Treasure Island]]''\n" +
                "#*: ''He's more a '''man''' than any pair of rats of you in this here house...''\n" +
                "# An abstract [[person]]; a person of either gender, usually an adult.\n" +
                "#: ''every '''man''' for himself''\n" +
                "# {{context|collective}} All humans collectively; [[mankind]]. Also [[Man]].\n" +
                "#: ''prehistoric '''man'''''\n" +
                "# A [[piece]] or [[token]] used in board games such as [[chess]].\n" +
                "# A [[professional]] person.\n" +
                "#: ''We'll have to call a '''man''' in to fix it\n" +
                "\n" +
                "====Synonyms====\n" +
                "* {{sense|adult male human}} [[omi]] {{qualifier|Polari}}\n" +
                "\n" +
                "====Antonyms====\n" +
                "* [[woman]]\n" +
                "* [[boy]]\n" +
                "\n" +
                "====Derived terms====\n";
*/
        man2 = "# {{colloquial|lang=fo}} [[one]], [[they]] {{i|indefinite third person singular pronoun}}\n" +
                "\n" +
                "====Synonyms====\n" +
                "* {{sense|standard}} [[mann]]\n" +
                "\n" +
                "----\n";

        nationality = "{{en-noun|nationalit|ies}}\n" +
                "\n" +
                "# {{rfc-sense}} Membership of a particular [[nation]] or [[state]], by origin, birth, naturalization, ownership, allegiance or otherwise.\n" +
                "# National, i.e. [[ethnic]] and/or cultural, character or identity.\n" +
                "\n" +
                "====Synonyms====\n" +
                "* {{sense|membership of a nation or state}} [[affiliation]], [[allegiance]], [[ancestry]], [[citizenship]], [[descent]], [[enfranchisement]], [[ethnicity]], [[national status]], [[naturalization]], [[origin]], [[parentage]], [[race]], [[residence]], [[status]]\n" +
                "* {{sense|national character or identity}} [[ancestry]], [[color]], [[colour]], [[ethnicity]], [[identity]], [[origin]]\n" +
                "\n" +
                "====Related terms====\n" +
                "* [[nation]], [[national]], [[nationalise]], [[nationalism]], [[nationalist]], [[nationalistic]], [[nationalize]]\n" +
                "* [[nationhood]]\n" +
                "\n" +
                "====See also====\n" +
                "* [[related term]]\n" +
                "\n";

        women = "{{wikipedia}}\n" +
                "{{en-noun|women}}\n" +
                "\n" +
                "# An [[adult]] [[female]] [[human being]].\n" +
                "\n" +
                "====Synonyms====\n" +
                "See [[Wikisaurus:woman]]\n" +
                "* [[female]]\n" +
                "* [[lady]]\n" +
                "\n" +
                "====Antonyms====\n" +
                "* [[girl]]\n" +
                "* [[man]]\n" +
                "\n" +
                "====Derived terms====\n" +
                "\n";

        airplane = "{{en-noun}}\n" +
                "# {{US}} A powered heavier-than air [[aircraft]] with fixed [[wing]]s.\n" +
                "\n" +
                "====Synonyms====\n" +
                "* [[aeroplane]].\n" +
                "\n" +
                "====Translations====\n" +
                "\n";
        
        Suomija_template_l = "{{lt-proper noun|f}}\n" +
                "\n" +
                "# [[Finland]]\n" +
                "\n" +
                "====Synonyms====\n" +
                "* {{l|lt|Suomijos Respublika}}\n" +
                "\n";
        
        poljento_template_l = "{{fi-noun}}\n" +
                "\n" +
                "# [[rhythm]]\n" +
                "\n" +
                "====Synonyms====\n" +
                "* {{l|la|gustus|gustūs}}, {{l|fi|tahti}}\n" +
                "\n" +
                "[[io:poljento]]\n";
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testParse_hrunk() {
        System.out.println("parse_hrunk");
        WRelation[] r;
        String str;

        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "test_hrunk";
        POSText pt = new POSText(POS.noun, test_hrunk);
        
        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);

        assertTrue(result.size() > 0);
        assertTrue(result.containsKey(Relation.synonymy));
        
        // ====Synonyms====
        // * (''flrink with cumplus''): [[flrink]], [[pigglehick]]
        // * (''furp''): [[furp]], [[whoodleplunk]]

        r = result.get(Relation.synonymy);
        assertEquals(2, r.length);

        str = r[0].getMeaningSummary();
        assertNotNull(str);
        assertTrue(str.equalsIgnoreCase("flrink with cumplus"));

        str = r[1].getMeaningSummary();
        assertNotNull(str);
        assertTrue(str.equalsIgnoreCase("furp"));
        
        WikiText[] synonym_row_0 = r[0].get();
        assertEquals(2, synonym_row_0.length);
        assertTrue(synonym_row_0[0].getVisibleText().equalsIgnoreCase("flrink"));
        assertTrue(synonym_row_0[1].getWikiWords()[0].getWordLink().equalsIgnoreCase("pigglehick"));

        WikiText[] synonym_row_1 = r[1].get();
        assertEquals(2, synonym_row_0.length);
        assertTrue(synonym_row_1[0].getVisibleText().equalsIgnoreCase("furp"));
        assertTrue(synonym_row_1[1].getWikiWords()[0].getWordLink().equalsIgnoreCase("whoodleplunk"));


        /* flower_text */

        page_title = "flower";
        pt = new POSText(POS.noun, flower_text);
        
        result = WRelationEn.parse(wikt_lang, page_title, pt);
        
        assertTrue(result.size() > 0);
        assertTrue(result.containsKey(Relation.synonymy));
        
        // ====Synonyms====
        // * {{sense|inflorescence that resembles a flower}} [[head]], [[pseudanthium]]
        // * {{sense|best examples}} [[cream]]
        // * {{sense|best state of things}} [[prime]]
        
        r = result.get(Relation.synonymy);
        assertEquals(3, r.length);

        str = r[0].getMeaningSummary();
        assertNotNull(str);
        assertTrue(str.equalsIgnoreCase("inflorescence that resembles a flower"));

        str = r[1].getMeaningSummary();
        assertNotNull(str);
        assertTrue(str.equalsIgnoreCase("best examples"));

        synonym_row_0 = r[0].get();
        assertEquals(2, synonym_row_0.length);
        assertTrue(synonym_row_0[0].getVisibleText().equalsIgnoreCase("head"));
        assertTrue(synonym_row_0[1].getWikiWords()[0].getWordLink().equalsIgnoreCase("pseudanthium"));
    }
    
    /* # {{colloquial|lang=fo}} [[one]], [[they]] {{i|indefinite third person singular pronoun}}
     * 
     * ====Synonyms====
     * * {{sense|standard}} [[mann]]
     * 
     * ---- (Dividing line between languages)
     */
    @Test
    public void testParse_with_Dividing_line() {
        System.out.println("parse_with_Dividing_line");
        WRelation[] r;
        String str;
        
        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "test_man2";
        POSText pt = new POSText(POS.noun, man2);

        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);

        assertTrue(result.size() > 0);
        assertTrue(result.containsKey(Relation.synonymy));

        // ====Synonyms====
        // * {{sense|standard}} [[mann]]
        //
        // ----

        r = result.get(Relation.synonymy);
        assertEquals(1, r.length);

        str = r[0].getMeaningSummary();
        assertNotNull(str);
        assertTrue(str.equalsIgnoreCase("standard"));

        WikiText[] synonym_row_0 = r[0].get();
        assertEquals(1, synonym_row_0.length);
        assertTrue(synonym_row_0[0].getVisibleText().equalsIgnoreCase("mann"));
        assertTrue(synonym_row_0[0].getWikiWords()[0].getWordLink().equalsIgnoreCase("mann"));
    }
    
    /* Tests wrong order: "Related terms" should be after "Synonyms" in really,
     * but sometimes:
     * {{en-verb}}
     * =====Related terms=====
     * * [[barking]]
     * 
     * =====Synonyms=====
     * * [[latrate]] {{i|obsolete}}
     *
     * =====Translations=====
     * */
    @Test 
    public void testParse_sections_wrong_order() {
        System.out.println("parse_sections_wrong_order");
        WRelation[] r;
        String str;

        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "bark";
        POSText pt = new POSText(POS.noun, bark);

        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);

        assertTrue(result.size() > 0);
        assertTrue(result.containsKey(Relation.synonymy));

        // ====Synonyms====
        // * [[latrate]] {{i|obsolete}}

        r = result.get(Relation.synonymy);
        assertEquals(1, r.length);

        str = r[0].getMeaningSummary();
        assertNull(str);

        WikiText[] synonym_row_0 = r[0].get();
        assertEquals(1, synonym_row_0.length);
        assertTrue(synonym_row_0[0].getVisibleText().equalsIgnoreCase("latrate {{i|obsolete}}"));
        assertTrue(synonym_row_0[0].getWikiWords()[0].getWordLink().equalsIgnoreCase("latrate"));

        // TODO label: obsolete
        // ...
    }

    /* Tests the wrong order: "See also" is located after "Related terms", so it
     * should be skipped, since "See also" can be used not only for semantic
     * relations, but also for etymologically related words
     *
     * ====Synonyms====
     * 
     * =====Related terms=====
     *
     * ====See also====
     * * [[related term]]
     * */
    @Test
    public void testParse_sections_wrong_order_for_See_also() {
        System.out.println("parse_sections_wrong_order_for_See_also");
        
        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "nationality";
        POSText pt = new POSText(POS.noun, nationality);

        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(Relation.synonymy));
    }


    // * [[aeroplane]]. -> aeroplane
    @Test
    public void testParse_skip_dot_after_synonym() {
        System.out.println("parse__skip_dot_after_synonym");
        WRelation[] r;
        String str;

        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "airplane";
        POSText pt = new POSText(POS.noun, airplane);

        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);

        assertTrue(result.size() > 0);
        assertTrue(result.containsKey(Relation.synonymy));

        // ====Synonyms====
        // * [[aeroplane]].
        
        r = result.get(Relation.synonymy);
        assertEquals(1, r.length);

        str = r[0].getMeaningSummary();
        assertNull(str);
        
        WikiText[] synonym_row_0 = r[0].get();
        assertEquals(1, synonym_row_0.length);
        assertTrue(synonym_row_0[0].getVisibleText().equalsIgnoreCase("aeroplane"));
        assertTrue(synonym_row_0[0].getWikiWords()[0].getWordLink().equalsIgnoreCase("aeroplane"));
    }

    /** The relation section may absent..., e.g.:
     *
     * {{wikipedia}}
     * {{en-noun}}
     *
     * # A [[human being]], whether [[man]], [[woman]] or [[child]].
     * 
     * ====Translations====
     */
    @Test
    public void testParse_empty_relation_section() {
        System.out.println("parse_empty_relation_section");
        
        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "human_empty_relation";
        POSText pt = new POSText(POS.noun, empty_relation2);
        
        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);

        assertEquals(0, result.size());
    }
    
    // Template:l should be parsed, and synonyms should be extracted from this template.
    // @see http://en.wiktionary.org/wiki/Template:l
    @Test
    public void testParse_template_l_with_one_synonym() {
        System.out.println("parse__template_l_with_one_synonym");
        WRelation[] r;
        String str;

        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "Suomija";
        POSText pt = new POSText(POS.proper_noun, Suomija_template_l);

        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);

        assertTrue(result.size() > 0);
        assertTrue(result.containsKey(Relation.synonymy));

        // ====Synonyms====
        // * {{l|lt|Suomijos Respublika}}
        
        r = result.get(Relation.synonymy);
        assertEquals(1, r.length);

        str = r[0].getMeaningSummary();
        assertNull(str);
        
        WikiText[] synonym_row_0 = r[0].get();
        assertEquals(1, synonym_row_0.length);
        assertTrue(synonym_row_0[0].getVisibleText().equalsIgnoreCase("Suomijos Respublika"));
        assertTrue(synonym_row_0[0].getWikiWords()[0].getWordLink().equalsIgnoreCase("Suomijos Respublika"));
    }
    
    @Test
    public void testParse_template_l_with_two_synonyms() {
        System.out.println("parse__template_l_with_two_synonyms");
        WRelation[] r;
        String str;

        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "poljento";
        POSText pt = new POSText(POS.noun, poljento_template_l);

        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);

        assertTrue(result.size() > 0);
        assertTrue(result.containsKey(Relation.synonymy));

        // ====Synonyms====
        // * {{l|la|gustus|gustūs}}, {{l|fi|tahti}}
        
        r = result.get(Relation.synonymy);
        assertEquals(1, r.length);

        str = r[0].getMeaningSummary();
        assertNull(str);
        
        WikiText[] synonym_row = r[0].get();
        assertEquals(2, synonym_row.length);
        assertTrue(synonym_row[0].getVisibleText().equalsIgnoreCase("gustus"));
        assertTrue(synonym_row[0].getWikiWords()[0].getWordLink().equalsIgnoreCase("gustus"));
        
        assertTrue(synonym_row[1].getVisibleText().equalsIgnoreCase("tahti"));
        assertTrue(synonym_row[1].getWikiWords()[0].getWordLink().equalsIgnoreCase("tahti"));
    }
    
    
    
    /** Let's skip now the link to Wikisaurus, e.g.:
     *  ====Synonyms====
     *  * See also [[Wikisaurus:word]]
     *
     * In future: parse Wikisaurus
     */
    @Test
    public void testParse_Wikisaurus_link() {
        System.out.println("parse_Wikisaurus_link");
        WRelation[] r;
        String str;

        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "word";
        POSText pt = new POSText(POS.noun, word_text);

        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(Relation.synonymy));

        // ====Synonyms====
        // * {{sense|distinct unit of language}} [[vocable]]
        // * {{sense|something promised}} [[promise]]
        // * {{sense|God}} [[God]], [[logos|Logos]]
        // * {{sense|Bible}} [[word of God]], [[Bible]]
        // * See also [[Wikisaurus:word]]                   // it's a 5th line of synonyms

        r = result.get(Relation.synonymy);
        assertEquals(4, r.length);
    }
    
    /** Wrong location of Wikisaurus link: at first row (it should be in the last):
     *  ====Synonyms====
     * See [[Wikisaurus:woman]]
     * * [[female]]
     * * [[lady]]
     */
    @Test
    public void testParse_Wikisaurus_link_at_first_row() {
        System.out.println("parse_Wikisaurus_link_at_first_row");
        WRelation[] r;

        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "women";
        POSText pt = new POSText(POS.noun, women);

        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);

        assertEquals(2, result.size()); // syn + ant
        assertTrue(result.containsKey(Relation.synonymy));

        // ====Synonyms====
        // See [[Wikisaurus:woman]]
        // * [[female]]
        // * [[lady]]
        // ====Antonyms====
        // * [[girl]]
        // * [[man]]
        
        r = result.get(Relation.synonymy);
        assertEquals(2, r.length); // female and lady
    }

    /** Let's skip now the link to Wikisaurus, e.g.:
     *  ====Hyponyms====
     *  * See also [[Wikisaurus:tree]]
     *
     * In future: parse Wikisaurus
     */
    @Test
    public void testParse_empty_relation_with_Wikisaurus_link() {
        System.out.println("parse_empty_relation_with_Wikisaurus_link");
        WRelation[] r;
        String str;

        LanguageType wikt_lang = LanguageType.en; // English Wiktionary
        String page_title = "word_empty_relation";
        POSText pt = new POSText(POS.noun, empty_relation);
        
        Map<Relation, WRelation[]> result = WRelationEn.parse(wikt_lang, page_title, pt);
        
        assertEquals(0, result.size());

        // todo:
        //      The link to Wikisaurus is ommited now... to parse in future.

        //assertTrue(result.containsKey(Relation.hyponymy));
        
        // ====Hyponyms====
        // * See also [[Wikisaurus:tree]]

        //r = result.get(Relation.hyponymy);
        //assertEquals(1, r.length);
    }

}