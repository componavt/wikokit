/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wiktparsed.mean_semrel.parser.sql;

//import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;


public class MSRLangTest {
    
    public Connect   mean_semrel_conn;
    
    public MSRLangTest() {
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

        // SQLite                   //Connect.testSQLite();
        //ruwikt_parsed_conn.OpenSQLite(Connect.RUWIKT_SQLITE, LanguageType.ru);
        
        // once upon a time: create mean_semrel (based on Wiktionary parsed db)
        MSRLang.recreateTable(mean_semrel_conn);
    }
    
    @After
    public void tearDown() {
        mean_semrel_conn.Close();
    }

    /**
     * Test of getID method, of class MSRLang.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        
        // once upon a time: use Wiktionary parsed db
        MSRLang.createFastMaps(mean_semrel_conn);
        
        // and every usual day
        int os_id = MSRLang.getIDFast(LanguageType.os);
        
        MSRLang tlang = MSRLang.get(mean_semrel_conn, LanguageType.os);
        assertNotNull(tlang);
        assertEquals(tlang.getID(), os_id);
    }
    
    @Test
    public void testUpdate() {
        System.out.println("update");

        int n_meaning_old,  n_sem_rel_old;
        int n_meaning,      n_sem_rel;
        
        MSRLang msr_lang = MSRLang.get(mean_semrel_conn, LanguageType.os);
        assertNotNull(msr_lang);
        
        // save old values
        n_meaning_old = msr_lang.getNumberMeanings();
        n_sem_rel_old= msr_lang.getNumberSemanticRelations();

        MSRLang.update(mean_semrel_conn, LanguageType.os, 111, 333);
        
        msr_lang = MSRLang.get(mean_semrel_conn, LanguageType.os);
        assertNotNull(msr_lang);

        n_meaning = msr_lang.getNumberMeanings();
        n_sem_rel = msr_lang.getNumberSemanticRelations();
        assertEquals(111, n_meaning);
        assertEquals(333, n_sem_rel);

        // restore old values
        MSRLang.update(mean_semrel_conn, LanguageType.os, n_meaning_old, n_sem_rel_old);
    }

    /*
    @Test
    public void testCalcMeanSemrelStatistics() {
        System.out.println("calcMeanSemrelStatistics");
        Connect connect = null;
        LanguageType native_lang = null;
        MSRLang.calcMeanSemrelStatistics(connect, native_lang);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

}
