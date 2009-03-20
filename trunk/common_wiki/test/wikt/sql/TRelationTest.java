
package wikt.sql;

import wikipedia.language.LanguageType;
import wikt.constant.POS;
import wikt.constant.Relation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;

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
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);

        TLang.recreateTable(ruwikt_parsed_conn);    // once upon a time: create Wiktionary parsed db
        TLang.createFastMaps(ruwikt_parsed_conn);   // once upon a time: use Wiktionary parsed db

        TPOS.recreateTable(ruwikt_parsed_conn);     // once upon a time: create Wiktionary parsed db
        TPOS.createFastMaps(ruwikt_parsed_conn);    // once upon a time: use Wiktionary parsed db

        TRelationType.recreateTable(ruwikt_parsed_conn);
        TRelationType.createFastMaps(ruwikt_parsed_conn);

        
        Connect conn = ruwikt_parsed_conn;
        page_title = ruwikt_parsed_conn.enc.EncodeFromJava("test_TRelation");

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
    }

    @After
    public void tearDown() {
        TPage.delete    (ruwikt_parsed_conn, page_title);
        TLangPOS.delete (ruwikt_parsed_conn, page);
        TMeaning.delete (ruwikt_parsed_conn, meaning);
        
        ruwikt_parsed_conn.Close();
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
}