/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wikt.multi.ru;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import wikt.util.POSText;
import wikt.util.LangText;
import wikipedia.language.LanguageType;

import wikipedia.sql.Connect;
import wikt.constant.POS;

public class WPOSRuTest {

    Connect     connect_ruwikt; // , connect_enwikt, connect_simplewikt;
    
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
        connect_ruwikt = new Connect();
        connect_ruwikt.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);
    }

    @After
    public void tearDown() {
        connect_ruwikt.Close();
    }

    @Test
    public void guessPOS_till_hyphen_not_space() {
        System.out.println("testGuessPOS_till_hyphen_not_space");

        String str, page_title;
        POS result;
        StringBuffer s;
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
        assertEquals(POS.unknown, result);  // too complex now
    }

    @Test
    public void guessPOSWith2ndLevelHeader_POS_header_unknown_but_not_null() {
        System.out.println("splitToPOSSections_POS_header_unknown_but_not_null");
        
        String str, s1, s2, page_title;
        POS result;
        StringBuffer s;
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

    /**
     * Test of splitToPOSSections method, of class WPOSRu.
     */
    @Test
    public void testSplitToPOSSections() {
        System.out.println("splitToPOSSections");
        
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
        
        // + real words from database
    }
    
    /**
     * Test of splitToPOSSections method, of class WPOSRu.
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