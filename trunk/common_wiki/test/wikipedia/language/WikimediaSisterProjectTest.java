
package wikipedia.language;

import junit.framework.TestCase;

public class WikimediaSisterProjectTest extends TestCase {
    
    public WikimediaSisterProjectTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /** Test parsing of interwikimedia link. E.g. 
     * [[w:Wikipedia:Wikimedia_sister_projects]]    -> "Wikimedia_sister_projects"
     * [[wikt:Wikisaurus:insane|Wikisaurus:insane]] -> "Wikisaurus:insane"
     * [[wikt:Help:Creating a Wikisaurus entry|Creating a Wikisaurus entry]] -> "Creating a Wikisaurus entry"
     * [[wikt:Wiktionary:Statistics#Detail|statistics]]  -> "statistics"
     * [[wikt:Wiktionary:Nguồn gốc/FVDP|Details]]   -> "Details"
     */
    public void testGetLinkText() {
        System.out.println("getLinkText");
        String wiki_text, expResult, result;
        
        // [[w:Wikipedia:Wikimedia_sister_projects]]    -> "Wikimedia_sister_projects"
        wiki_text = "w:Wikipedia:Wikimedia_sister_projects";
        expResult =   "Wikipedia:Wikimedia_sister_projects";
        result = WikimediaSisterProject.getLinkText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // [[wikt:Wikisaurus:insane|Wikisaurus:insane]] -> "Wikisaurus:insane"
        wiki_text = "wikt:Wikisaurus:insane|Wikisaurus:insane";
        expResult =                        "Wikisaurus:insane";
        result = WikimediaSisterProject.getLinkText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // [[wikt:Help:Creating a Wikisaurus entry|Creating a Wikisaurus entry]] -> "Creating a Wikisaurus entry"
        wiki_text = "wikt:Help:Creating a Wikisaurus entry|Creating a Wikisaurus entry";
        expResult =                                       "Creating a Wikisaurus entry";
        result = WikimediaSisterProject.getLinkText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // [[wikt:Wiktionary:Statistics#Detail|statistics]]  -> "statistics"
        wiki_text = "wikt:Wiktionary:Statistics#Detail|statistics";
        expResult =                                   "statistics";
        result = WikimediaSisterProject.getLinkText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // [[wikt:Wiktionary:Nguồn gốc/FVDP|Details]]   -> "Details"
        wiki_text = "wikt:Wiktionary:Nguồn gốc/FVDP|Details";
        expResult =                                "Details";
        result = WikimediaSisterProject.getLinkText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }

    /** Namespace with/with out name of Wikimedia project
     * 
     * [[:Image:Wiktionary-logo-gl.png|a logo that depicts a dictionary]] -> "a logo that depicts a dictionary"
     * [[w:Wikipedia:Interwikimedia_links|text to expand]] -> "text to expand"
     */
    public void testGetLinkText_namespace_only() {
        System.out.println("getLinkText_namespace_only");
        String wiki_text, expResult, result;
        
        // 1. with wikimedia prefix 'w'
        // [[w:Wikipedia:Interwikimedia_links|text to expand]] -> "text to expand"
        wiki_text = "w:Wikipedia:Interwikimedia_links|text to expand";
        expResult =                                  "text to expand";
        result = WikimediaSisterProject.getLinkText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // [[wikt:de:Rechner]] -> Rechner
        wiki_text = "wikt:de:Rechner";
        expResult =         "Rechner";
        result = WikimediaSisterProject.getLinkText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 2. without wikimedia prefixes
        // [[:Image:Wiktionary-logo-gl.png|a logo that depicts a dictionary]] -> "a logo that depicts a dictionary"
        wiki_text = ":Image:Wiktionary-logo-gl.png|a logo that depicts a dictionary";
        expResult =                               "a logo that depicts a dictionary";
        result = WikimediaSisterProject.getLinkText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 3. with wikimedia prefixes, but without '|' pipe
        // [[:de:Hauptseite]] -> Hauptseite
        wiki_text = ":de:Hauptseite";
        expResult =     "Hauptseite";
        result = WikimediaSisterProject.getLinkText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
}
