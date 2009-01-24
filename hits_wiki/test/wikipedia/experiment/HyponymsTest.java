/*
 * HyponymsTest.java
 * JUnit based test
 *
 * Created on September 9, 2007, 1:22 PM
 */

package wikipedia.experiment;

import wikipedia.sql.Connect;
import wikipedia.kleinberg.SessionHolder;

import junit.framework.*;

public class HyponymsTest extends TestCase {
    
    Connect                 connect, connect_simple; //idfsimplewiki_conn; //, connect_ru;
    private SessionHolder   session;
    private static final double eps = 0.1;
    
    public HyponymsTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect_simple = new Connect();
        connect_simple.Open(Connect.WP_HOST,Connect.WP_SIMPLE_DB,   Connect.WP_USER,    Connect.WP_PASS);
        
        connect = new Connect();
        connect.Open(Connect.WP_HOST,       Connect.WP_DB,          Connect.WP_USER,    Connect.WP_PASS);
        
        session = new SessionHolder();
        session.initObjects();
    }

    protected void tearDown() throws Exception {
        connect_simple.Close();
        connect.Close();
    }

    /** Assert that nearest common category of articles Iuridictum and 
     * Hot-linking (in SimpleWikipedia 2007) is Internet.
     * Iuridictum -> Websites -> Internet
     * Hot-linking -> Internet slang -> Internet
     */
    public void testGetCommonCategoryWithMaxIC_simple() {
        System.out.println("getCommonCategoryWithMaxIC_simple");
        CatCount cc;
        String page_title1, page_title2;
        
        int n_limit = 100;
        session.category_black_list.setMaxSteps(n_limit);
        session.connect = connect_simple;
        
        // 1. null test
        cc = Hyponyms.getCommonCategoryWithMaxIC("absent title1", "absent title2", session);
        assertEquals(null, cc);
        
        
        // 2. pre-condition
        // Category:Main_page is root, where ic<=0
        // SELECT * FROM cat_count WHERE page_title = 'Main_page';
        
        // assert ic<=0, since there is intersection only in root category
        // Mothball -> Category:Everyday life -> Main_page
        // HTML -> Category:Markup languages -> 
        //              -> Programming languages -> Computer science -> Computing
        //              -> Internet              ->                     Computing -> Science -> Main page
        cc = Hyponyms.getCommonCategoryWithMaxIC("Mothball", "HTML", session);
        if(null == cc) {
            fail("It is needed to add the table 'cat_count' and other tables, " +
                    "in order to make experiments. See instructions in the file " +
                    "synarcher/kleinberg/src/wikipedia/experiment/Valuer.java");
        }
        float ic = cc.getInformationContent();
        assertFalse(MetricSpearman.equals(0, ic, eps));
        assertEquals("Main_page", cc.getPageTitle());
        
        
        // 3.
        page_title1 = "Iuridictum";
        page_title2 = "Hot-linking";
        cc = Hyponyms.getCommonCategoryWithMaxIC(page_title1, page_title2,
                            session);
        assertEquals("Internet", cc.getPageTitle());
        
        
        // 4. Computer -> Category:Computers -> Computing
        //    Keyboard -> Category:Computer_science -> Computing
        //    and
        //    Computer -> Category:Tools
        //    Keyboard -> Category:Writing_tools -> Tools
        // used categories: Computers Computer_science Computing Writing_tools Tools
        page_title1 = "Computer";
        page_title2 = "Keyboard";
        cc = Hyponyms.getCommonCategoryWithMaxIC(page_title1, page_title2,
                            session);
        assertEquals("Tools", cc.getPageTitle());
    }
    
    
    /** Calculates and prints IC for 353 pairs of synonyms. */
    public void testDumpICWordSim353_simple() {
        System.out.println("testDumpICWordSim353_simple");
        
        int n_limit = 100;
        session.category_black_list.setMaxSteps(n_limit);
        session.connect = connect_simple;
                
        //Hyponyms.dumpICWordSim353(session);
    }
    public void testDumpICWordSim353_enwiki() {
        System.out.println("testDumpICWordSim353_enwiki");
        
        int n_limit = 100;
        session.category_black_list.setMaxSteps(n_limit);
        session.connect = connect;
                
        //Hyponyms.dumpICWordSim353(session);
    }
    
}
