package wiktparsed.mean_semrel.parser.sql;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.constant.Relation;

/**
 *
 * @author andrew
 */
public class MSRMeanSemrelXXTest {
    
    public Connect   mean_semrel_conn;
    
    public MSRMeanSemrelXXTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        LanguageType wiki_lang = LanguageType.en;
        
        mean_semrel_conn = new Connect();

        // MySQL
        mean_semrel_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_MEAN_SEMREL, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, 
                        wiki_lang);
    }
    
    @After
    public void tearDown() {
        mean_semrel_conn.Close();
    }

    /**
     * Test of generateTables method, of class MSRMeanSemrelXX.
     */
/*    @Test
    public void testGenerateTables() {
        System.out.println("generateTables");
        Connect connect = mean_semrel_conn;
        LanguageType xx_lang = null;
        MSRMeanSemrelXX.generateTables(connect);
    }
*/
    /**
     * Test of insert method, of class MSRMeanSemrelXX.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        
        //LanguageType native_lang = null;
        LanguageType xx_lang = LanguageType.os;
        
        String page_title = "test_page_MSR";
        String meaning_text = "test_meaning_MSR";
        
        // 1. Test: failed attempt to insert empty relations, should be rejected
        Map<Relation, StringBuffer> m_relations = null;
        int n_sem_rel = 0;
        MSRMeanSemrelXX.insert( xx_lang, mean_semrel_conn, 
                            page_title, meaning_text, m_relations, n_sem_rel);
        
        MSRMeanSemrelXX[] rows = MSRMeanSemrelXX.getByPage (mean_semrel_conn, 
                                            xx_lang, page_title);
        assertEquals(0, rows.length);
        
        // 2. one semantic relation
        String synset = "coordinate term testMSR";
        m_relations = new HashMap<Relation, StringBuffer>();
        m_relations.put(Relation.coordinate_term, new StringBuffer(synset));
        
        n_sem_rel = 1;
        MSRMeanSemrelXX.insert( xx_lang, mean_semrel_conn, 
                            page_title, meaning_text, m_relations, n_sem_rel);
        
        rows = MSRMeanSemrelXX.getByPage (mean_semrel_conn, 
                                            xx_lang, page_title);
        assertEquals(1, rows.length);
        
        Map<Relation, String> relations = rows[0].getRelations();
        assertEquals(1, relations.size());
        
        assertTrue(relations.containsKey(Relation.coordinate_term));
        assertTrue(relations.get(Relation.coordinate_term).equalsIgnoreCase(synset) );
        
        rows[0].delete(mean_semrel_conn, xx_lang);
    }
}
