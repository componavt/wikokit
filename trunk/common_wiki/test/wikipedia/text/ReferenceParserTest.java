
package wikipedia.text;

import wikipedia.language.LanguageType;

import junit.framework.TestCase;

public class ReferenceParserTest extends TestCase {
    
    public ReferenceParserTest(String testName) {
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

    /** Test of expandMoveToEndOfText method, of class ReferenceParser. */
    public void testExpandMoveToEndOfText() {
        System.out.println("expandMoveToEndOfText");
        StringBuffer wiki_text, expResult, result;
            
        // 1. expand and move ref text to the end of text
        wiki_text = new StringBuffer(
"Впечатление произвело не только, и даже не столько увиденное и услышанное, сколько личность самого " +
"их будущего Гуруджи<ref>Джи – уважительная приставка, по типу «сан» у японцев.</ref> — Шри Рам Кумар Шармы.");

        expResult = new StringBuffer(
"Впечатление произвело не только, и даже не столько увиденное и услышанное, сколько личность самого " +
"их будущего Гуруджи — Шри Рам Кумар Шармы.\n\nДжи – уважительная приставка, по типу «сан» у японцев.");
        result = ReferenceParser.expandMoveToEndOfText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        
        // 2. complex: remove ref with template inside: <ref>{{...}}</ref>
        wiki_text = new StringBuffer(
"''Bold [[wiktionary:pale|pale]] text. ''<ref>{{cite book |last= Axell |first= Albert |coauthors = " +
    "Kase, Hideaki | year=2002 | title= Kamikaze: Japan’s suicide gods |publisher= New York: Longman. " + 
    "ISBN 0-582-77232-X | pages=p. 16 }}</ref>\n" +
" A special ceremony before going to combat usually took place.");
        
        expResult = new StringBuffer(
                "Bold pale text. \n A special ceremony before going to combat usually took place.");
        result = ReferenceParser.expandMoveToEndOfText(wiki_text);
        //result = WikiParser.parseCurlyBrackets(result);
        result = WikiParser.parseDoubleApostrophe(result);
        result = WikiParser.parseDoubleBrackets(result, LanguageType.en, true);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    
    /** remove hyperlink in ref which is not within brackets '[]' */
    public void testExpandReferenceToEndOfText_hyperlink() {
        System.out.println("expandReferenceToEndOfText_hyperlink");
        StringBuffer wiki_text, expResult, result;
        
        // 1. remove (only link)
        // <ref>http://tank.uw.ru/archive/koliestwennyj/index.khtml</ref> -> ""
        wiki_text = new StringBuffer("<ref>http://tank.uw.ru/archive/koliestwennyj/index.khtml</ref>");
        result = ReferenceParser.expandMoveToEndOfText(wiki_text);
        assertEquals(0, result.length());
        
        // "abc <ref>http://tank.uw.ru/archive/koliestwennyj/index.khtml</ref> text -> "abc text"
        wiki_text = new StringBuffer("abc<ref>http://tank.uw.ru/archive/koliestwennyj/index.khtml</ref> text");
        expResult = new StringBuffer("abc text");
        result = WikiParser.convertWikiToText(wiki_text, LanguageType.ru, true);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 2. append text
        // "abc<ref>http://tank.uw link text</ref> text" -> "abc text\n\n link text"
        wiki_text = new StringBuffer("abc<ref>http://tank.uw link text</ref> text");
        expResult = new StringBuffer("abc text\n\nlink text");
        result = WikiParser.convertWikiToText(wiki_text, LanguageType.ru, true);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }

    /** Test of removeReferences method, of class ReferenceParser. */
    public void testRemoveReferencesOnly() {
        System.out.println("removeReferencesOnly");
        StringBuffer wiki_text, expResult, result;

        // 1. expand and move ref text to the end of text
        wiki_text = new StringBuffer(
"Впечатление произвело не только, и даже не столько увиденное и услышанное, сколько личность самого " +
"их будущего Гуруджи<ref>Джи – уважительная приставка, по типу «сан» у японцев.</ref> — Шри Рам Кумар Шармы.");

        expResult = new StringBuffer(
"Впечатление произвело не только, и даже не столько увиденное и услышанное, сколько личность самого " +
"их будущего Гуруджи — Шри Рам Кумар Шармы.");
        result = ReferenceParser.expandMoveToEndOfText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );


        // 2. complex: remove ref with template inside: <ref>{{...}}</ref>
        //      but do not expand [[Wiki text|wiki text]]
        wiki_text = new StringBuffer(
"''Bold [[wiktionary:pale|pale]] text. ''<ref>{{cite book |last= Axell |first= Albert |coauthors = " +
    "Kase, Hideaki | year=2002 | title= Kamikaze: Japan’s suicide gods |publisher= New York: Longman. " +
    "ISBN 0-582-77232-X | pages=p. 16 }}</ref>\n" +
" A special ceremony before going to combat usually took place.");

        //expResult = new StringBuffer(
        //        "Bold pale text. \n A special ceremony before going to combat usually took place.");
        expResult = new StringBuffer(
"''Bold [[wiktionary:pale|pale]] text. ''" +
" A special ceremony before going to combat usually took place.");

        result = ReferenceParser.expandMoveToEndOfText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );


        // 3. complex: remove several references
        wiki_text = new StringBuffer(
"{{помета|сиб.}} [[ловушка]]<ref>Брокгауз</ref><ref>Даль</ref><ref>[http://www]</ref>{{пример|кулёма на медведя…}}" +
    "" +
    "" +
"");




        //expResult = new StringBuffer(
        //        "Bold pale text. \n A special ceremony before going to combat usually took place.");
        expResult = new StringBuffer(
"''Bold [[wiktionary:pale|pale]] text. ''" +
" A special ceremony before going to combat usually took place.");

        result = ReferenceParser.expandMoveToEndOfText(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }

}
