
package wikokit.base.wikt.sql;

import wikokit.base.wikt.util.POSText;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.word.WRelation;
import wikokit.base.wikt.multi.ru.WRelationRu;
import wikokit.base.wikipedia.sql.Connect;
//import wikipedia.sql.UtilSQL;

import java.util.Map;
//import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TRelationTest {

    Connect conn, enwikt_parsed_conn, ruwikt_parsed_conn;
    
    TPOS pos;
    TLang lang;
    TPage page;
    String page_title, page_title2, wiki_text_str, str_wiki_text_wikified;
    TRelationType relation_type;
    TWikiText wiki_text;
    TLangPOS lang_pos;
    TMeaning meaning;
    String car_text, car_meaning_summary;
    
    String hrunk_text, hrunk_meaning_summary1, hrunk_meaning_summary2;
    
    public TRelationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);

        enwikt_parsed_conn = new Connect();
        enwikt_parsed_conn.Open(Connect.ENWIKT_HOST,Connect.ENWIKT_PARSED_DB,Connect.ENWIKT_USER,Connect.ENWIKT_PASS,LanguageType.ru);

        conn = ruwikt_parsed_conn;
        //conn = enwikt_parsed_conn;
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "page");

        TLang.recreateTable(conn);    // once upon a time: create Wiktionary parsed db
        TLang.createFastMaps(conn);   // once upon a time: use Wiktionary parsed db

        TPOS.recreateTable(conn);     // once upon a time: create Wiktionary parsed db
        TPOS.createFastMaps(conn);    // once upon a time: use Wiktionary parsed db

        TRelationType.recreateTable(conn);
        TRelationType.createFastMaps(conn);

        
        page_title = conn.enc.EncodeFromJava("car_test");    // test_TRelation

        // insert page, get page_id
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;
        String redirect_target = null;

        page = TPage.get(conn, page_title);
        if(null == page) {
            TPage.insert(conn, page_title, word_count, wiki_link_count, 
                         is_in_wiktionary, redirect_target);
            page = TPage.get(conn, page_title);
            assertTrue(null != page);
        }

        // add "automobile"
        page_title2 = "automobile_test";
        TPage.insert(conn, page_title2, word_count, wiki_link_count,
                     is_in_wiktionary, redirect_target);

        // get lang
        int lang_id = TLang.getIDFast(LanguageType.os); //227;
        lang = TLang.getTLangFast(lang_id);
        assertTrue(null != lang);
        assertEquals(LanguageType.os, lang.getLanguage());

        // get POS
        pos = TPOS.get(POS.noun);
        assertTrue(null != pos);
        
        // get relation_type
        relation_type = TRelationType.get(conn, Relation.synonymy);
        assertTrue(null != relation_type);
        
        // insert wiki_text
        wiki_text_str          =   "test_TWikiText_TRelation";
        str_wiki_text_wikified = "[[test_TWikiText_TRelation]]";
        wiki_text = TWikiText.insert(conn, wiki_text_str, str_wiki_text_wikified);
        if(null == wiki_text)
            wiki_text = TWikiText.get(conn, wiki_text_str);
        assertTrue(null != wiki_text);

        // insert lang_pos
        int etymology_n = 0;
        String lemma = "";
        lang_pos = TLangPOS.insert(conn, page, lang, pos, etymology_n, lemma);
        assertTrue(null != lang_pos);

        // insert meaning
        int meaning_n = 1;
        meaning = TMeaning.insert(conn, lang_pos, meaning_n, wiki_text);
        assertTrue(null != meaning);

        car_text =  "=== Произношение ===\n" +
                    "==== Значение ====\n" +
                    "==== Синонимы ====\n" +
                    "# [[carriage_test]]\n" +
                    "# [[automobile_test]]\n" +
                    "# -\n" +
                    "# -\n" +
                    "# -\n" +
                    "\n" +
                    "==== Антонимы ====\n" +
                    "\n" +
                    "==== Гиперонимы ====\n" +
                    "# [[vehicle_test]]\n" +
                    "# -\n" +
                    "# -\n" +
                    "# -\n" +
                    "# -\n" +
                    "\n" +
                    "==== Гипонимы ====\n" +
                    "# -\n" +
                    "# [[truck_test]], [[van_test]], [[bus_test]]\n" +
                    "# -\n" +
                    "\n" +
                    "===Родственные слова===\n";
        car_meaning_summary = null;

        hrunk_text = 
                "# Definition hrunk 1.\n" +
                "# Definition hrunk 2.\n" +
                "\n" +
                "====Synonyms====\n" +
                "* (''flrink with cumplus_test''): [[flrink_test]], [[pigglehick_test]]\n" +
                "* (''furp_test''): [[furp_test]], [[whoodleplunk_test]]";

        hrunk_meaning_summary1 = "flrink with cumplus_test";
        hrunk_meaning_summary2 = "furp_test";
    }

    @After
    public void tearDown() {
        // delete temporary records 

        String[] pages_test = {
                "carriage_test", "automobile_test",
                "vehicle_test",
                "truck_test", "van_test", "bus_test"};

        for(String p: pages_test) {
            TWikiText wiki_text2 = TWikiText.get(conn, p);   // 1. get WikiText by pages_test
            if(null != wiki_text2)
                TWikiText.deleteWithWords(conn, wiki_text2);
            TPage.delete(conn, p);
        }

        TPage.delete    (conn, page_title);
        TPage.delete    (conn, page_title2);
        TLangPOS.delete (conn, page);
        TMeaning.delete (conn, meaning);
        TWikiText.delete(conn, wiki_text_str);
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "page");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "relation");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "meaning");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text");
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text_words");
        
        conn.Close();
    }

    @Test
    public void testStoreToDB() {
        System.out.println("storeToDB");
        //Connect conn = ruwikt_parsed_conn;

        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        //String page_title = "car";
        POSText pt = new POSText(POS.noun, car_text);

        Map<Relation, WRelation[]> m_relations = WRelationRu.parse(wikt_lang, page_title, pt);
        assertNotNull(m_relations);
        assertTrue(m_relations.size() > 0);
        assertTrue(m_relations.containsKey(Relation.synonymy));
        
        // let's check second meaning (i.e. [1]): 
        // synonyms: [[automobile]]
        // hyponyms: [[truck]], [[van]], [[bus]]
        TRelation.storeToDB(conn, meaning, 1, m_relations);

        TRelation[] trelation = TRelation.get(conn, meaning); //TRelationType trelation_synonymy = TRelationType.getRelationFast(Relation.synonymy);
        assertNotNull(trelation);
        assertEquals(4, trelation.length);

        for(TRelation t : trelation)
            TRelation.delete(conn, t);
    }

    @Test
    public void testCount() {
        System.out.println("count");
        //Connect conn = ruwikt_parsed_conn;

        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        POSText pt = new POSText(POS.noun, car_text);

        Map<Relation, WRelation[]> m_relations = WRelationRu.parse(wikt_lang, page_title, pt);
        assertNotNull(m_relations);
        assertTrue(m_relations.size() > 0);
        assertTrue(m_relations.containsKey(Relation.synonymy));

        // let's check second meaning (i.e. [1]):
        // synonyms: [[automobile]]
        // hyponyms: [[truck]], [[van]], [[bus]]
        TRelation.storeToDB(conn, meaning, 1, m_relations);

        int trelation_number = TRelation.count(conn, meaning);
        assertEquals(4, trelation_number);

        TRelation[] trelation = TRelation.get(conn, meaning);
        for(TRelation t : trelation)
            TRelation.delete(conn, t);
    }

    @Test
    public void testInsert_ru() {
        System.out.println("insert_ang_get_and_delete_ru");
        //Connect conn = ruwikt_parsed_conn;

        TRelation relation0 = TRelation.insert(conn, meaning, wiki_text, 
                                            relation_type, car_meaning_summary);
        assertTrue(null != relation0);
        
        TRelation[] array_rel = TRelation.get(conn, meaning);
        assertTrue(null != array_rel);
        assertEquals(1, array_rel.length);

        assertNull(array_rel[0].getMeaningSummary()); // == car_meaning_summary

        TRelation.delete(conn, relation0);

        array_rel = TRelation.get(conn, meaning);
        assertEquals(0, array_rel.length);
    }

    // test an insertion of not null value of meaning_summary
    // hrunk_text, hrunk_meaning_summary1, hrunk_meaning_summary2
    @Test
    public void testInsert_not_null_meaning_summary_en() {
        System.out.println("insert_not_null_meaning_summary_en");
        //Connect conn = ruwikt_parsed_conn;

        TRelation relation0 = TRelation.insert(conn, meaning, wiki_text,
                                            relation_type, hrunk_meaning_summary1);
        assertTrue(null != relation0);

        TRelation[] array_rel = TRelation.get(conn, meaning);
        assertTrue(null != array_rel);
        assertEquals(1, array_rel.length);
        
        String sum0 = array_rel[0].getMeaningSummary();
        assertTrue(sum0.equalsIgnoreCase(hrunk_meaning_summary1));
        
        TRelation.delete(conn, relation0);

        array_rel = TRelation.get(conn, meaning);
        assertEquals(0, array_rel.length);
    }
  
    @Test
    public void testGetByID_ru() {
        System.out.println("getByID_ru");
        //Connect conn = ruwikt_parsed_conn;

        TRelation r = TRelation.insert( conn, meaning, wiki_text, relation_type,
                                        car_meaning_summary);
        assertTrue(null != r);

        TRelation r2 = TRelation.getByID(conn, r.getID());
        assertTrue(null != r2);
        assertEquals(wiki_text_str, r2.getWikiText().getText());

        TRelation.delete(conn, r);
    }

   
    //public static Relation getRelationType (Connect connect,String word1,String word2) {
    @Test
    public void testGetRelationType () {
        System.out.println("getRelationType _ru");
        //Connect conn = ruwikt_parsed_conn;

        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        POSText pt = new POSText(POS.noun, car_text);

        Map<Relation, WRelation[]> m_relations = WRelationRu.parse(wikt_lang, page_title, pt);
        
        // let's check second meaning (i.e. [1]):
        // synonyms: [[automobile]]
        // hyponyms: [[truck]], [[van]], [[bus]]
        TRelation.storeToDB(conn, meaning, 1, m_relations);

        Relation r;
        r = TRelation.getRelationType(conn, "car_test", "car_test");
        assertNull(r);

        r = TRelation.getRelationType(conn, "car_test", "absent word");
        assertNull(r);

        r = TRelation.getRelationType(conn, "automobile_test", "car_test");
        assertEquals(Relation.synonymy, r);

        r = TRelation.getRelationType(conn, "car_test", "van_test");
        assertEquals(Relation.hyponymy, r);

        TRelation[] trelation = TRelation.get(conn, meaning);
        for(TRelation t : trelation)
            TRelation.delete(conn, t);
    }
}