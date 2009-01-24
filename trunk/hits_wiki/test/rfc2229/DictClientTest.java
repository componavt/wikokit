/*
 * DictClientTest.java
 * JUnit based test
 */

package rfc2229;

import java.util.Arrays;
import java.util.List;
import junit.framework.*;

import net.zuckerfrei.jcfd.Database;
import net.zuckerfrei.jcfd.DatabaseList;
import net.zuckerfrei.jcfd.Definition;
import net.zuckerfrei.jcfd.DefinitionList;
import net.zuckerfrei.jcfd.Dict;
import net.zuckerfrei.jcfd.DictFactory;
import net.zuckerfrei.jcfd.Match;
import net.zuckerfrei.jcfd.MatchList;
import net.zuckerfrei.jcfd.Strategy;
import net.zuckerfrei.jcfd.StrategyList;

public class DictClientTest extends TestCase {
    
    public DictClientTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DictClientTest.class);
        return suite;
    }
    
    public void testGetLinkWords_WordNet () {
        System.out.println("getLinkWords_WordNet");
        
        String[] db_names = {"WordNet"};
        try {
            List<String> result = DictClient.getLinkWords(db_names, "saccharify");
            String[] exp_result =  {"sugar", "saccharified"};
        
            /**
             saccharify
                 v 1: sweeten with sugar; "sugar your tea" [syn: sugar]
                 2: convert into a simple soluble fermentable sugar by
                    hydrolyzing a sugar derivative or complex carbohydrate
                 [also: saccharified]
            **/

            assertEquals(2, result.size());
            assertEquals(Arrays.asList(exp_result), result);

        } catch (Exception e) {
        }
        
    }
    
    public void testGetLinkWords_Moby () {
        System.out.println("getLinkWords_Moby");
        
        String[] db_names = {"Moby"};
        try {
            List<String> result = DictClient.getLinkWords(db_names, "mulch");
        
            /**
             24 Moby Thesaurus words for "mulch":
                   backset, cultivate, culture, cut, delve, dig, dress, fallow,
                   fertilize, force, harrow, hoe, list, plow, prune, rake, spade,
                   thin, thin out, till, till the soil, weed, weed out, work
            **/

            assertEquals(24, result.size());
            assertEquals("backset", result.get(0));

        } catch (Exception e) {
        }
        
    }
    


    /*
    public void testRun() throws Exception {
        System.out.println("run");
        
        String word = "sugar";
        
        DictClient.run(word);
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }*/
    
}
