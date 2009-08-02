
package wikt.sql;

import wikt.util.POSText;
import wikipedia.language.LanguageType;
import wikt.constant.POS;
import wikt.constant.Relation;
import wikt.word.WRelation;
import wikt.multi.ru.WRelationRu;
import wikipedia.sql.Connect;
import wikipedia.sql.UtilSQL;

import java.util.Map;
//import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TRelationTest {

    public Connect   ruwikt_parsed_conn;
    
    TPOS pos;
    TLang lang;
    TPage page;
    String page_title, wiki_text_str;
    TRelationType relation_type;
    TWikiText wiki_text;
    TLangPOS lang_pos;
    TMeaning meaning;
    String car_text;
    
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
        
        Connect conn = ruwikt_parsed_conn;
        //UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "page");

        TLang.recreateTable(conn);    // once upon a time: create Wiktionary parsed db
        TLang.createFastMaps(conn);   // once upon a time: use Wiktionary parsed db

        TPOS.recreateTable(conn);     // once upon a time: create Wiktionary parsed db
        TPOS.createFastMaps(conn);    // once upon a time: use Wiktionary parsed db

        TRelationType.recreateTable(conn);
        TRelationType.createFastMaps(conn);

        
        page_title = conn.enc.EncodeFromJava("car");    // test_TRelation

        // insert page, get page_id
        int word_count = 7;
        int wiki_link_count = 13;
        boolean is_in_wiktionary = true;

        page = TPage.get(conn, page_title);
        if(null == page) {
            TPage.insert(conn, page_title, word_count, wiki_link_count, is_in_wiktionary);
            page = TPage.get(conn, page_title);
            assertTrue(null != page);
        }

        // add "automobile"
        TPage.insert(conn, "automobile", word_count, wiki_link_count, is_in_wiktionary);

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
        wiki_text_str = "test_TWikiText_TRelation";
        wiki_text = TWikiText.insert(conn, wiki_text_str);
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
                    "# [[carriage]]\n" +
                    "# [[automobile]]\n" +
                    "# -\n" +
                    "# -\n" +
                    "# -\n" +
                    "\n" +
                    "==== Антонимы ====\n" +
                    "\n" +
                    "==== Гиперонимы ====\n" +
                    "# [[vehicle]]\n" +
                    "# -\n" +
                    "# -\n" +
                    "# -\n" +
                    "# -\n" +
                    "\n" +
                    "==== Гипонимы ====\n" +
                    "# -\n" +
                    "# [[truck]], [[van]], [[bus]]\n" +
                    "# -\n" +
                    "\n" +
                    "===Родственные слова===\n";
    }

    @After
    public void tearDown() {
        Connect conn = ruwikt_parsed_conn;
        TPage.delete    (conn, page_title);
        TLangPOS.delete (conn, page);
        TMeaning.delete (conn, meaning);

        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "page");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "relation");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "meaning");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text");
        UtilSQL.deleteAllRecordsResetAutoIncrement(conn, "wiki_text_words");
        
        ruwikt_parsed_conn.Close();
    }

    @Test
    public void testStoreToDB() {
        System.out.println("storeToDB_ru");
        Connect conn = ruwikt_parsed_conn;

        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "car";
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
    }

    @Test
    public void testInsert() {
        System.out.println("insert_ang_get_and_delete_ru");
        Connect conn = ruwikt_parsed_conn;

        TRelation relation0 = TRelation.insert(conn, meaning, wiki_text, relation_type);
        assertTrue(null != relation0);
        
        TRelation[] array_rel = TRelation.get(conn, meaning);
        assertTrue(null != array_rel);
        assertEquals(1, array_rel.length);

        TRelation.delete(conn, relation0);

        array_rel = TRelation.get(conn, meaning);
        assertEquals(0, array_rel.length);
    }
    
    @Test
    public void testGetByID() {
        System.out.println("getByID_ru");
        Connect conn = ruwikt_parsed_conn;

        TRelation r = TRelation.insert(conn, meaning, wiki_text, relation_type);
        assertTrue(null != r);

        TRelation r2 = TRelation.getByID(conn, r.getID());
        assertTrue(null != r2);
        assertEquals(wiki_text_str, r2.getWikiText().getText());
    }

    
    //public static Relation getRelationType (Connect connect,String word1,String word2) {

    @Test
    public void testGetRelationType () {
        System.out.println("getRelationType _ru");
        Connect conn = ruwikt_parsed_conn;

        LanguageType wikt_lang = LanguageType.ru; // Russian Wiktionary
        String page_title = "car";
        POSText pt = new POSText(POS.noun, car_text);

        Map<Relation, WRelation[]> m_relations = WRelationRu.parse(wikt_lang, page_title, pt);
        
        // let's check second meaning (i.e. [1]):
        // synonyms: [[automobile]]
        // hyponyms: [[truck]], [[van]], [[bus]]
        TRelation.storeToDB(conn, meaning, 1, m_relations);

        Relation r;
        r = TRelation.getRelationType(conn, "car", "car");
        assertNull(r);

        r = TRelation.getRelationType(conn, "car", "absent word");
        assertNull(r);

        r = TRelation.getRelationType(conn, "automobile", "car");
        assertEquals(Relation.synonymy, r);

        r = TRelation.getRelationType(conn, "car", "van");
        assertEquals(Relation.hyponymy, r);
    }

}