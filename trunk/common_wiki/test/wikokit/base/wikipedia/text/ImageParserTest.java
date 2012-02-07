package wikokit.base.wikipedia.text;

import wikokit.base.wikipedia.text.WikiParser;
import wikokit.base.wikipedia.text.ImageParser;
import wikokit.base.wikipedia.language.LanguageType;

import junit.framework.TestCase;

public class ImageParserTest extends TestCase {
    
    public ImageParserTest(String testName) {
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
       
    /*
     * 
     * [[Dosiero:AM156fol_p1.jpg|thumb|right|250px|Unua pagxo de la sagao de Hrafnkell, en la [[manuskripto]] ÁM. 156 ([[17-a jarcento]]).]]
     */
     
    // [[Image:Asimov.jpg|thumb|180px|right|[[Isaac Asimov]] with his [[typewriter]].]]
    // -> [[Isaac Asimov]] with his [[typewriter]]. -> Isaac Asimov with his typewriter.
    public void testParseImageDescription_en() {
        System.out.println("parseImageDescription_en");
        StringBuffer wiki_text, expResult, expResult2, result, result2;
        
        // image without desc
        wiki_text = new StringBuffer("[[Image:Asimov.jpg]]");
        result = ImageParser.parseImageDescription(wiki_text, LanguageType.en);
        assertEquals(0, result.toString().length());
        
        // image with empty desc
        wiki_text = new StringBuffer("[[Image:Asimov.jpg|thumb|180px|right|]]");
        result = ImageParser.parseImageDescription(wiki_text, LanguageType.en);
        assertEquals(0, result.toString().length());
        
        // two images without desc
        wiki_text = new StringBuffer("[[Image:Asimov.jpg|thumb|180px|right|]] and [[Image:Asimov.jpg]]");
        expResult = new StringBuffer(                                       " and "                    );
        result = ImageParser.parseImageDescription(wiki_text, LanguageType.en);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // two images
        wiki_text = new StringBuffer("[[Image:Asimov.jpg|thumb|180px|right|text1]] and [[Image:Asimov.jpg|thumb]]");
        expResult = new StringBuffer(                                      "text1 and thumb"                     );
        result = ImageParser.parseImageDescription(wiki_text, LanguageType.en);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // description with double bracket
        wiki_text = new StringBuffer("[[Image:Asimov.jpg|[[one]] 12 [[two|second]] 3]]");
        expResult = new StringBuffer(                   "[[one]] 12 [[two|second]] 3"  );
        result = ImageParser.parseImageDescription(wiki_text, LanguageType.en);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // description with pipeline
        wiki_text = new StringBuffer("[[Image:Asimov.jpg|[[Worker|Robot]]]]");
        expResult = new StringBuffer(                   "[[Worker|Robot]]"  );
        result = ImageParser.parseImageDescription(wiki_text, LanguageType.en);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // image with description
        wiki_text = new StringBuffer("[[Image:Asimov.jpg|thumb|180px|right|[[Isaac Asimov]] with his [[typewriter]].]]");
        expResult = new StringBuffer("[[Isaac Asimov]] with his [[typewriter]].");
        expResult2= new StringBuffer("Isaac Asimov with his typewriter.");
        result = ImageParser.parseImageDescription(wiki_text, LanguageType.en);
        result2 = WikiParser.parseDoubleBrackets(result, LanguageType.en, false);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        assertTrue(expResult2.toString().equalsIgnoreCase( result2.toString() ) );
    }
    
    // [[Изображение:Через-тернии-к-звёздам 2.jpg|thumb|«[[Через]] [[терний|тернии]] к звёздам»]]
    // -> «[[Через]] [[терний|тернии]] к звёздам» -> «Через тернии к звёздам»
    public void testParseImageDescription_ru() {
        System.out.println("parseImageDescription_ru");
        StringBuffer wiki_text, expResult, expResult2, result, result2;
        
        wiki_text = new StringBuffer("[[Изображение:Через-тернии-к-звёздам 2.jpg|thumb|«[[Через]] [[терний|тернии]] к звёздам»]]");
        expResult = new StringBuffer("«[[Через]] [[терний|тернии]] к звёздам»");
        expResult2= new StringBuffer("«Через тернии к звёздам»");
        result = ImageParser.parseImageDescription(wiki_text, LanguageType.ru);
        result2 = WikiParser.parseDoubleBrackets(result, LanguageType.ru, false);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        assertTrue(expResult2.toString().equalsIgnoreCase( result2.toString() ) );
    }
    

}
