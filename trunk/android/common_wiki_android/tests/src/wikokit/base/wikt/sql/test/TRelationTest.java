package wikokit.base.wikt.sql.test;

import java.util.Map;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.constant.POS;
import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.multi.ru.WRelationRu;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TLangPOS;
import wikokit.base.wikt.sql.TMeaning;
import wikokit.base.wikt.sql.TPOS;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.TRelation;
import wikokit.base.wikt.sql.TRelationType;
import wikokit.base.wikt.util.POSText;
import wikokit.base.wikt.word.WRelation;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

public class TRelationTest extends TestCase {

    public Context context = null;
    Connect ruwikt_conn;
    SQLiteDatabase db;
    String car_text, page_title;
    TMeaning meaning;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        ruwikt_conn = new Connect(context, LanguageType.ru);
        ruwikt_conn.openDatabase();
        db = ruwikt_conn.getDB();
        TLang.createFastMaps(db);
        TPOS.createFastMaps (db);
        TRelationType.createFastMaps(db);
        
        page_title = "car";
        
        // 0. get meaning
        TPage page = TPage.get(db, page_title);
        assertNotNull(page);
        
        TLangPOS[] array_lang_pos = TLangPOS.getRecursive(db, page);
        
        assertNotNull(array_lang_pos);
        assertTrue   (array_lang_pos.length > 0);
        TLangPOS tlang_pos = array_lang_pos[0];
        
        TMeaning[] mm_copy1 = tlang_pos.getMeaning();
        assertNotNull(mm_copy1);
        assertTrue(mm_copy1.length > 0);
        meaning = mm_copy1[0];
        
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
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ruwikt_conn.close();
    }

    public void testGet() {
        TRelation[] trelation = TRelation.get(db, meaning);
        assertNotNull (trelation);
        assertTrue(trelation.length >= 2); // synonyms: [[carriage]]; hypernyms: [[vehicle]]
    }

    public void testCount() {
        // "car" in the Russian Wiktionary
        // let's check first meaning (i.e. [0]):
        // synonyms: [[carriage]]
        // hypernyms: [[vehicle]]
        int trelation_number = TRelation.count(db, meaning);
        assertTrue(trelation_number >= 2);
    }

    public void testGetByID() {
        TRelation[] trelation = TRelation.get(db, meaning);
        TRelation r = trelation[0];
        assertNotNull(r);
        
        // synonyms: [[carriage]]
        TRelation r2 = TRelation.getByID(db, r.getID());
        assertNotNull(r2);
        
        String sss = r2.getWikiText().getText();
        //assertEquals(wiki_text_str, r2.getWikiText().getText());
    }

    public void testGetRelationType() {
        
        Relation r;
        r = TRelation.getRelationType(db, "car", null);
        assertNull(r);

        r = TRelation.getRelationType(db, "car", "absent word");
        assertNull(r);

        r = TRelation.getRelationType(db, "car", "carriage");
        assertEquals(Relation.synonymy, r);

        r = TRelation.getRelationType(db, "car", "truck");
        assertEquals(Relation.hyponymy, r);
    }

}
