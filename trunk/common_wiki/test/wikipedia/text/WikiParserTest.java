package wikipedia.text;

import wikipedia.language.LanguageType;
import wikipedia.language.Encodings;
//import wikipedia.util.StringUtil;

import junit.framework.*;

// todo 
// 1. to remove image: [[Изображение:Через-тернии-к-звёздам 2.jpg|thumb|«Через тернии к звёздам»]]
// 2. add GATE module Wiki to Text

public class WikiParserTest extends TestCase {
    
    public Encodings enc = new Encodings();
    
    public WikiParserTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /** It should remove interwiki.
     */
    public void testRemoveInterwiki() {
        System.out.println("testRemoveInterwiki");
        StringBuffer wiki_text, expResult, result;
        
        // 1. interwiki two letters language code
        wiki_text = new StringBuffer("[[et:Talvepalee]] text");
        expResult = new StringBuffer(                 " text");
        result = WikiParser.removeInterwiki(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 2. interwiki three letters language code
        wiki_text = new StringBuffer("[[et:Talvepalee]] text [[csh:also interwiki]] word");
        expResult = new StringBuffer(                 " text  word");
        result = WikiParser.removeInterwiki(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 3. non-interwiki
        wiki_text = new StringBuffer("text [[ru:Амир Хосров Дехлеви]] word [[ettd:non interwiki]]");
        expResult = new StringBuffer(                          "text  word [[ettd:non interwiki]]");
        result = WikiParser.removeInterwiki(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 4. multiline interwiki
        wiki_text = new StringBuffer("text [[ru:Амир Хосров Дехлеви]] \nword [[ettd:non interwiki]]");
        expResult = new StringBuffer(                          "text  \nword [[ettd:non interwiki]]");
        result = WikiParser.removeInterwiki(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    /** It should remove brackets in interwiki.
     */
    public void testRemoveBracketsInInterwiki() {
        System.out.println("testRemoveBracketsInInterwiki");
        StringBuffer wiki_text, expResult, result;
        
        // "[[et:Talvepalee]] text [[csh:also interwiki]] word" -> 
        //      "Talvepalee text also interwiki word"
        wiki_text = new StringBuffer("[[et:Talvepalee]] \ntext [[csh:also interwiki]] word");
        expResult = new StringBuffer("Talvepalee \ntext also interwiki word");
        result = WikiParser.removeBracketsInInterwiki(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // ez: - language code 'ez' do not exists, but it will expand it in any case
        wiki_text = new StringBuffer("[[ez:Talvepalee]] text");
        expResult = new StringBuffer("Talvepalee text");
        result = WikiParser.removeBracketsInInterwiki(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    /** It should remove categories.
     */
    public void testConvertWikiToText_category() {
        System.out.println("convertWikiToText__category");
        
        StringBuffer wiki_text = new StringBuffer("[[Category:Russia]] text");
        StringBuffer expResult = new StringBuffer(                 " text");
        StringBuffer result = WikiParser.removeCategory(wiki_text, LanguageType.en);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    public void testConvertWikiToText_category_ru() {
        System.out.println("convertWikiToText__category_Russian");
        
        StringBuffer wiki_text = new StringBuffer("[[Категория:Благоразумение]] text");
        StringBuffer expResult = new StringBuffer(                 " text");
        StringBuffer result = WikiParser.removeCategory(wiki_text, LanguageType.ru);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        wiki_text = new StringBuffer("[[Категория:Благоразумение]] text [[Category:Russia]] text2 [[Категория:Громозека]] text3");
        expResult = new StringBuffer(" text [[Category:Russia]] text2  text3");
        result = WikiParser.removeCategory(wiki_text, LanguageType.ru);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testRemoveXMLTag_code() {
        System.out.println("removeXMLTag_code");
        
        StringBuffer wiki_text = new StringBuffer("a <code>x+y</code> b");
        StringBuffer expResult = new StringBuffer(                "a  b");
        StringBuffer result = WikiParser.removeXMLTag(wiki_text, "code");
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // multiline
        wiki_text = new StringBuffer("a <code>x+y\nz+w</code> b");
        expResult = new StringBuffer(                "a  b");
        result = WikiParser.removeXMLTag(wiki_text, "code");
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // null test
        wiki_text = new StringBuffer("a <code>x+y b");
        expResult = new StringBuffer("a <code>x+y b");
        result = WikiParser.removeXMLTag(wiki_text, "code");
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // two tags
        wiki_text = new StringBuffer("a <code>x+y</code> b <code>w+z</code>c-d");
        expResult = new StringBuffer("a  b c-d");
        result = WikiParser.removeXMLTag(wiki_text, "code");
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testRemoveHTMLComment() {
        System.out.println("removeHTMLComment");
        
        StringBuffer wiki_text = new StringBuffer("a <!-- my comment --> b");
        StringBuffer expResult = new StringBuffer(                "a  b");
        StringBuffer result = WikiParser.removeHTMLComments(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // multiline
        wiki_text = new StringBuffer("a <!-- comment 1--> b\n c <!-- comment 2--> d");
        expResult = new StringBuffer("a  b\n c  d");
        result = WikiParser.removeHTMLComments(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
            
        // complex
        wiki_text = new StringBuffer("'''А́построф'''<!-- в этом значении ударение на первый слог! --> ({{lang-el|ἀπόστροφος}}) —");
        expResult = new StringBuffer("'''А́построф''' ({{lang-el|ἀπόστροφος}}) —");
        result = WikiParser.removeHTMLComments(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testRemovePreCode() {
        System.out.println("removePreCode");
        
        // multiline
        StringBuffer wiki_text = new StringBuffer("a <pre> text1 \n text2 </pre>b");
        StringBuffer expResult = new StringBuffer(                "a b");
        StringBuffer result = WikiParser.removePreCode(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testRemoveSourceCode() {
        System.out.println("removeHTMLComment");
        
        // multiline
        StringBuffer wiki_text = new StringBuffer("a <source lang=\"xml\">co\nde</source>b");
        StringBuffer expResult = new StringBuffer(                "a b");
        StringBuffer result = WikiParser.removeSourceCode(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    
    /** It should substitute wiki links.
     */
    public void testRemoveBracketsInWikiLink() {
        System.out.println("removeBracketsInWikiLink");
        /*
        // 1. without '|' inside link'
        StringBuffer wiki_text = new StringBuffer("The '''Winter Palace''' [[Russia]], where [[Tsar]]s");
        StringBuffer expResult = new StringBuffer("The '''Winter Palace''' Russia, where Tsars");
        StringBuffer result = WikiParser.removeBracketsInWikiLink(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 2. with '|' inside link
        wiki_text = new StringBuffer("The '''Winter Palace''' ([[The Russian language|Russian]]: [[Russia]], where [[Tsar]]s");
        expResult = new StringBuffer("The '''Winter Palace''' (Russian: Russia, where Tsars");
        result = WikiParser.removeBracketsInWikiLink(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
         */
    }
    
        
    public void testParseSingleBrackets() {
        System.out.println("parseSingleBrackets");
        StringBuffer wiki_text, expResult, result;
        
        // 1. substitute hyperlinks
        wiki_text = new StringBuffer("The '''Winter Palace''' ([http://example.com Russian]]:");
        expResult = new StringBuffer("The '''Winter Palace''' ( Russian]:");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 2. remove links without text
        wiki_text = new StringBuffer("The '''Winter Palace''' ([http://example.com]:");
        expResult = new StringBuffer("The '''Winter Palace''' (:");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testParseSingleBrackets_with_hyperlink_in_rightmost_word() {
        System.out.println("parseSingleBrackets_with_hyperlink_in_rightmost_word");
        StringBuffer wiki_text, expResult, result;
        
        // 1. remove hyperlinks
        // [http://www.most-spb.ru/1_sadovy/1_sadovy_glav.htm www.most-spb.ru]
    
        wiki_text = new StringBuffer("[http://www.most-spb.ru/1_sadovy/1_sadovy_glav.htm www.most-spb.ru]");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertEquals(0, result.length() );
        
        wiki_text = new StringBuffer("[http://rnq.ru R&Q.ru]");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertEquals(0, result.length() );
        
        wiki_text = new StringBuffer("[http://v8.1c.ru/ v8.1c.ru]");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertEquals(0, result.length() );
        
        wiki_text = new StringBuffer("[http://firststeps.ru/1c/ firststeps.ru/1c]");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertEquals(0, result.length() );
        
        wiki_text = new StringBuffer("[http://www.mista.ru/tutor_1c/ www.mista.ru/tutor_1c]");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertEquals(0, result.length() );
        
        wiki_text = new StringBuffer("[http://metaprog.co.ua/ metaprog.co.ua]");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertEquals(0, result.length() );
        
        wiki_text = new StringBuffer("[http://forum.codeby.net/forum17.html forum.codeby.net/forum17.html]");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertEquals(0, result.length() );
        
        wiki_text = new StringBuffer("[http://etersoft.ru/wine etersoft.ru/wine]");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertEquals(0, result.length() );
        
        wiki_text = new StringBuffer("[http://www.infostart.ru/ www.infostart.ru]");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertEquals(0, result.length() );
        
        // 2. remain words, non links:
        // [http://www.hedpe.ru hedpe.ru — русский фан-сайт] -> "  — русский фан-сайт"
        wiki_text = new StringBuffer("[http://www.hedpe.ru site hedpe.ru — russian fan-site]");
        expResult = new StringBuffer(                    " site — russian fan-site");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // [http://www.maxnet.ru/faq.php?nm=5&vd=2&faqid=4#79 MAXnet - условия выделения доменных имён .obninsk.ru, .kaluga.net, .balabanovo.ru, .borovsk.ru, .maxnet.ru]
        //      -> "MAXnet - условия выделения доменных имён  ,  ,  ,  ,  "
        wiki_text = new StringBuffer("[http://www.maxnet.ru/faq.php?nm=5&vd=2&faqid=4#79 MAXnet - условия выделения доменных имён .obninsk.ru, .kaluga.net, .balabanovo.ru, .borovsk.ru, .maxnet.ru]");
        expResult = new StringBuffer(                                                  " MAXnet - условия выделения доменных имён");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // [http://www.alldates.ru/film/april/18.shtml Кино, Театр + ТВ. Календарь AllDates.ru: 18 Апреля.]
        //      -> "Кино, Театр + ТВ. Календарь : 18 Апреля."
        wiki_text = new StringBuffer("[http://www.alldates.ru/film/april/18.shtml Кино, Театр + ТВ. Календарь AllDates.ru: 18 Апреля.]");
        expResult = new StringBuffer(                                           " Кино, Театр + ТВ. Календарь 18 Апреля.");
        result = WikiParser.parseSingleBrackets(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    
    /** 1. interwiki
     * @param b_remove_not_expand_iwiki if true then 
     * Removes interwiki, e.g. "[[et:Talvepalee]] text" -> " text"
     *                                  if false then
     * expands interwiki by removing interwiki brackets and language code, 
     * e.g. "[[et:Talvepalee]] text" -> "Talvepalee text".*/
    public void testParseDoubleBrackets_interwiki() {
        System.out.println("parseDoubleBrackets_interwiki");
        StringBuffer wiki_text, expResult, result;
        boolean b_remove_not_expand_iwiki;
        
        // null test
        result = WikiParser.parseDoubleBrackets(null, LanguageType.simple, true);
        assertEquals( 0, result.length());
        
        // 1. remove interwiki
        wiki_text = new StringBuffer("[[et:Talvepalee]] text");
        expResult = new StringBuffer(" text");
        b_remove_not_expand_iwiki = true;
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // ez: - language code 'ez' do not exists
        wiki_text = new StringBuffer("[[ez:Talvepalee]] text");
        expResult = new StringBuffer("[[ez:Talvepalee]] text");
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 2. expands interwiki
        wiki_text = new StringBuffer("[[et:Talvepalee]] text");
        expResult = new StringBuffer("Talvepalee text");
        b_remove_not_expand_iwiki = false;
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    /** * 2. Removes categories for selected language, 
     * e.g. English: "[[Category:Russia]] text" -> " text" */
    public void testParseDoubleBrackets_remove_category() {
        System.out.println("parseDoubleBrackets_remove_category");
        StringBuffer wiki_text, expResult, result;
        boolean b_remove_not_expand_iwiki = true;
        
        // 1. remove English category
        wiki_text = new StringBuffer("[[Category:test123]] text");
        expResult = new StringBuffer(" text");
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.en, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 2. remove Russian category
        wiki_text = new StringBuffer("[[Категория:Космос]] text");
        expResult = new StringBuffer(" text");
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.ru, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 3. do not remove category of unknown language (i.e. not implemented yet)
        wiki_text = new StringBuffer("[[UknownLang:Космос]] text");
        expResult = new StringBuffer("[[UknownLang:Космос]] text");
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    
    /** wiki links
     * Expands wiki links by removing brackets. There are two cases: 
     * (1) remove brackets, e.g. [[run]] -> run and 
     * (2) [[run|running]] -> running, or [[Russian language|Russian] -> Russian, 
     * i.e. the visible (to reader) words will remain.
     */
    public void testParseDoubleBrackets_wikilinks() {
        System.out.println("parseDoubleBrackets_wikilinks");
        StringBuffer wiki_text, expResult, result;
        boolean b_remove_not_expand_iwiki = true;
        
        // 1. (without vertical pipe) remove brackets, e.g. [[run]] -> run
        wiki_text = new StringBuffer("[[Talvepalee]] text");
        expResult = new StringBuffer("Talvepalee text");
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 2. [[run|running]] -> running, or [[Russian language|Russian]] -> Russian, 
        wiki_text = new StringBuffer("[[run|running]]");
        expResult = new StringBuffer("running");
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        //or [[Russian language|Russian]] -> Russian
        wiki_text = new StringBuffer("z [[Russian language|BRussian]] s");
        expResult = new StringBuffer("z BRussian s");
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // "в [[космос|космическом пространстве]]." -> "в космическом пространстве."
        wiki_text = new StringBuffer("в [[космос|космическом пространстве]].");
        expResult = new StringBuffer("в космическом пространстве.");
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    
    /** wiki links
     * Expands wiki links by removing brackets. There are cases: 
     * (1) [[wiktionary:excited|excited]] -> excited
     * (2) [[run|running]] -> running, or [[Russian language|Russian] -> Russian, 
     * i.e. the visible (to reader) words will remain.
     * 
     * more test see in: wikipedia.language.WikimediaSisterProjectTest
     */
    public void testParseDoubleBrackets_sisterm_wiki_projects_links() {
        System.out.println("parseDoubleBrackets_wikilinks");
        StringBuffer wiki_text, expResult, result;
        boolean b_remove_not_expand_iwiki = true;
        
        // 0. 
        wiki_text = new StringBuffer("[[wiktionary:pale|pale]] text");
        expResult = new StringBuffer("pale text");
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 1. (without vertical pipe) remove brackets, e.g. [[w:wikipedia:Interwikimedia_links]] -> Interwikimedia_links
        wiki_text = new StringBuffer("[[w:Wikipedia:Interwikimedia_links]] text");
        expResult = new StringBuffer("Wikipedia:Interwikimedia_links text");
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    /** [[United States dollar|US$]] -> US$
     */
    public void testParseDoubleBrackets_misc_dollar() {
        System.out.println("parseDoubleBrackets_misc_dollar");
        StringBuffer expResult, result;
        String       wiki_text_source;
        boolean b_remove_not_expand_iwiki = true;
        
        // (with vertical pipe) remove brackets
        wiki_text_source = "[[United States dollar|US$]]";
        expResult = new StringBuffer(             "US$");
        
        StringBuffer wiki_text = new StringBuffer (wiki_text_source);
        
        //wiki_text = StringUtil.escapeCharDollarAndBackslash(wiki_text.toString());
        result = WikiParser.parseDoubleBrackets(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testParseCurlyBrackets() {
        System.out.println("parseCurlyBrackets");
        StringBuffer wiki_text, expResult, result;
        
        // null test
        wiki_text = new StringBuffer("The [[Winter]] stub [http://example.com Russian]");
        expResult = new StringBuffer("The [[Winter]] stub [http://example.com Russian]");
        result = WikiParser.parseCurlyBrackets(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // remove {{template}}
        wiki_text = new StringBuffer("The [[Winter]] {{stub}} [http://example.com Russian]");
        expResult = new StringBuffer("The [[Winter]]  [http://example.com Russian]");
        result = WikiParser.parseCurlyBrackets(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // multiline
        wiki_text = new StringBuffer("The [[Winter]] {{stub1 \n stub2}} [http://example.com Russian]{{stub3 \n stub4}}");
        expResult = new StringBuffer("The [[Winter]]  [http://example.com Russian]");
        result = WikiParser.parseCurlyBrackets(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // embed
        wiki_text = new StringBuffer("The [[Winter]]  {{stub ext1 \n {{stub internal}} \n stub ext2}} [http://example.com Russian]");
        expResult = new StringBuffer("The [[Winter]]   [http://example.com Russian]");
        result = WikiParser.parseCurlyBrackets(wiki_text);
        result = WikiParser.parseCurlyBrackets(result);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testParseCurlyBrackets_expand_usefull_templates() {
        System.out.println("parseCurlyBrackets_expand_usefull_templates");
        StringBuffer wiki_text, expResult, result;
        
        // 2. template: book
        wiki_text = new StringBuffer(
            "{{книга|автор = Дейкстра Э.\n" + 
            "|заглавие = Дисциплина программирования\n" + 
            "|оригинал = A discipline of programming|издание = 1-е изд\n" + 
            "|место = М.\n" + 
            "|издательство = Мир|год = 1978\n" + 
            "|страницы = 275\n" +
            "}}");
        expResult = new StringBuffer(
            " Дейкстра Э.\n" + 
            " Дисциплина программирования\n" + 
            " A discipline of programming  1-е изд\n" + 
            " М.\n" + 
            " Мир|год = 1978\n" + 
            " 275\n");
        result = WikiParser.parseCurlyBrackets(wiki_text);
        //assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    // remove accent sign: '''«Че́рез те́рнии к звёздам»''' -> «Через тернии к звёздам»
    public void testRemoveAcuteAccent() {
        System.out.println("removeAcuteAccent");
        StringBuffer wiki_text, expResult, result;
        
        // null test
        wiki_text = new StringBuffer("$The '[[Winter]] stub [http://example.com Russian]");
        expResult = new StringBuffer("$The '[[Winter]] stub [http://example.com Russian]");
        result = WikiParser.removeAcuteAccent(wiki_text, LanguageType.ru);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // remove cute accent
        wiki_text = new StringBuffer("'''«Че́рез те́рнии \n к звёздам»'''");
        expResult = new StringBuffer("'''«Через тернии \n к звёздам»'''");
        result = WikiParser.removeAcuteAccent(wiki_text, LanguageType.ru);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testParseTripleApostrophe() {
        System.out.println("parseTripleApostrophe");
        StringBuffer wiki_text, expResult, result;
        
        // null test
        wiki_text = new StringBuffer("The [[Winter]] stub [http://example.com Russian]");
        expResult = new StringBuffer("The [[Winter]] stub [http://example.com Russian]");
        result = WikiParser.parseTripleApostrophe(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // remove '''bold'''
        wiki_text = new StringBuffer("The [[Winter]] '''bold''' ''italics'' [http://example.com Russian]");
        expResult = new StringBuffer("The [[Winter]] bold ''italics'' [http://example.com Russian]");
        result = WikiParser.parseTripleApostrophe(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // complex: if last symbol is \, it should be doubled: \\
        wiki_text = new StringBuffer("ab''')\\'''cd");
        expResult = new StringBuffer("ab)\\cd");
        //wiki_text = StringUtil.escapeCharDollarAndBackslash(wiki_text.toString());
        result = WikiParser.parseTripleApostrophe(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // one sign of accent
        wiki_text = new StringBuffer("f'''\\\\'''v");
        expResult = new StringBuffer("f\\\\v");
        result = WikiParser.parseTripleApostrophe(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // four sign of accent: ('''/''', '''\''', '''^''', '''\\''')
        wiki_text = new StringBuffer("four sign of accent: '''/''', '''\\''', '''^''', '''\\\\'''");
        expResult = new StringBuffer("four sign of accent: /, \\, ^, \\\\");
        result = WikiParser.parseTripleApostrophe(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testParseDoubleApostrophe() {
        System.out.println("parseDoubleApostrophe");
        StringBuffer wiki_text, expResult, result;
        
        // null test
        wiki_text = new StringBuffer("The [[Winter]] stub [http://example.com Russian]");
        expResult = new StringBuffer("The [[Winter]] stub [http://example.com Russian]");
        result = WikiParser.parseDoubleApostrophe(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // remove '''bold'''
        wiki_text = new StringBuffer("The [[Winter]] ''italics'' [http://example.com Russian]");
        expResult = new StringBuffer("The [[Winter]] italics [http://example.com Russian]");
        result = WikiParser.parseDoubleApostrophe(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // dollar sign: He also hosted a version of ''[[Pyramid (game show)|The $25,000 Pyramid]]'', ''[[Child's Play]]'', and ''[[Blockbusters]]''.
        wiki_text = new StringBuffer("He also hosted a version of ''[[Pyramid (game show)|The $25,000 Pyramid]]''.");
        expResult = new StringBuffer("He also hosted a version of [[Pyramid (game show)|The $25,000 Pyramid]].");
        //wiki_text = StringUtil.escapeCharDollarAndBackslash(wiki_text.toString());
        result = WikiParser.parseDoubleApostrophe(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testParseDoubleApostrophe_dollar_sign() {
        System.out.println("parseDoubleApostrophe_dollar_sign");
        StringBuffer wiki_text, expResult, result;
        
        // 1. simple $
        // [[United States dollar|$]]
        wiki_text = new StringBuffer("He ''[[United States dollar|$]]''.");
        expResult = new StringBuffer("He [[United States dollar|$]].");
        //wiki_text = StringUtil.escapeCharDollarAndBackslash(wiki_text.toString());
        result = WikiParser.parseDoubleApostrophe(wiki_text);
        result = WikiParser.parseDoubleApostrophe(result);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 2. escaped \$
        wiki_text = new StringBuffer("''[http://site.org \"Site name \\$40 \"]''");
        expResult = new StringBuffer(  "[http://site.org \"Site name $40 \"]");
        result = WikiParser.parseDoubleApostrophe(wiki_text);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
    }
    
    
    public void testExpandReferenceToEndOfText() {
        System.out.println("expandReferenceToEndOfText");
        StringBuffer wiki_text, expResult, result;
        
        // 1. remove ref with template inside: <ref>{{...}}</ref>
        wiki_text = new StringBuffer(
"''Bold [[wiktionary:pale|pale]] text. ''<ref>{{cite book |last= Axell |first= Albert |coauthors = " +
    "Kase, Hideaki | year=2002 | title= Kamikaze: Japan’s suicide gods |publisher= New York: Longman. " + 
    "ISBN 0-582-77232-X | pages=p. 16 }}</ref>\n" +
" A special ceremony before going to combat usually took place.");
        
        expResult = new StringBuffer(
                "Bold pale text. \n A special ceremony before going to combat usually took place.");
        result = WikiParser.convertWikiToText(wiki_text, LanguageType.simple, true);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
            
        // 2. expand and move ref text to the end of text
        wiki_text = new StringBuffer("word1<ref>Ref text.</ref> — word2."); // &mdash; to be replaced by ' '
        expResult = new StringBuffer("word1   word2.\n\nRef text.");
        result = WikiParser.convertWikiToText(wiki_text, LanguageType.ru, true);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 3. remove hyperlink in ref which is not within brackets '[]'
        // "abc<ref>http://tank.uw link text</ref> text" -> "abc text\n\n link text"
        wiki_text = new StringBuffer("abc<ref>http://tank.uw link text</ref> text");
        expResult = new StringBuffer("abc text\n\nlink text");
        result = WikiParser.convertWikiToText(wiki_text, LanguageType.ru, true);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    
    /** It should substitute hyperlinks and remove links without text.
     */
    public void testConvertWikiToText_hyperlinks() {
        System.out.println("convertWikiToText__hyperlinks");
        boolean b_remove_not_expand_iwiki = false;
        
        // 1. substitute hyperlinks
        StringBuffer wiki_text = new StringBuffer("The '''Winter Palace''' ([http://example.com Russian]:");
        StringBuffer expResult = new StringBuffer("The Winter Palace ( Russian:");
        StringBuffer result = WikiParser.convertWikiToText(wiki_text, LanguageType.en, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
        
        // 2. remove links without text
        wiki_text = new StringBuffer("The '''Winter Palace''' ''bold'' ([http://example.com]:");
        expResult = new StringBuffer("The Winter Palace bold (:");
        result = WikiParser.convertWikiToText(wiki_text, LanguageType.en, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testConvertWikiToText_complex() {
        System.out.println("convertWikiToText__complex");
        
        StringBuffer wiki_text = new StringBuffer(
         "The '''Winter Palace''' ([[Russian language|Russian]]: \u0417\u0438\u043c\u043d\u0438\u0439 \u0414\u0432\u043e\u0440\u0435\u0446) is a place in [[Saint Petersburg|St. Petersburg]], [[Russia]], where [[Tsar]]s (Russian kings) could stay during [[winter]]. It was between the shores of the [[Neva River]] and the [[Palace Square]] and built between [[1754]] and [[1762]]." +
        "{{stub}}" +
        "[[Category:Russia]]" +
        "[[bg:\u0417\u0438\u043c\u0435\u043d \u0434\u0432\u043e\u0440\u0435\u0446]]" +
        "[[et:Talvepalee]]" +
        "[[en:Winter Palace]]" +
        "[[eo:Vintra Palaco]]" +
        "[[fr:Palais d'hiver]]" +
        "[[it:Palazzo d'Inverno (San Pietroburgo)]]" +
        "[[he:\u05d0\u05e8\u05de\u05d5\u05df \u05d4\u05d7\u05d5\u05e8\u05e3]]" +
        "[[ka:\u10d6\u10d0\u10db\u10d7\u10e0\u10d8\u10e1 \u10e1\u10d0\u10e1\u10d0\u10ee\u10da\u10d4 (\u10e1\u10d0\u10dc\u10e5\u10e2-\u10de\u10d4\u10e2\u10d4\u10e0\u10d1\u10e3\u10e0\u10d2\u10d8)]]" +
        "[[hu:T%/1��microsoft-cp1251�li Palota]]" +
        "[[nl:Winterpaleis]]" +
        "[[ja:\u51ac\u5bae\u6bbf]]" +
        "[[no:Vinterpalasset]]" +
        "[[pl:Pa\u0142ac Zimowy]]" +
        "[[ro:Palatul de iarn\u0103 din Sf. Petersburg]]" +
        "[[ru:\u0417\u0438\u043c\u043d\u0438\u0439 \u0434\u0432\u043e\u0440\u0435\u0446]]" +
        "[[sl:Zimski dvorec]]" +
        "[[fi:Talvipalatsi]]" +
        "[[sv:Vinterpalatset]]" +
        "[[zh:\u51ac\u5bab]]");
        
        StringBuffer expResult = new StringBuffer(
         "The Winter Palace (Russian: \u0417\u0438\u043c\u043d\u0438\u0439 \u0414\u0432\u043e\u0440\u0435\u0446) is a place in St. Petersburg, Russia, where Tsars (Russian kings) could stay during winter. It was between the shores of the Neva River and the Palace Square and built between 1754 and 1762.");
        
        boolean b_remove_not_expand_iwiki = true;
        StringBuffer result = WikiParser.convertWikiToText(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testConvertWikiToText_complex_simple_en() {
        System.out.println("convertWikiToText_simple_en");
        
        StringBuffer wiki_text = new StringBuffer(
        "{{Taxobox\n"  +
        "| color = lightgreen\n"  +
        "}}\n"  +
        "\n"  +
        "[[Image:Castle Himeji sakura02.jpg|thumb|290px|Hanami parties at [[Himeji Castle]].]]\n" + 
        "\n"  +
        "'''Sakura''' or '''Cherry Blossom''' is the [[Japanese language|Japanese]] name for decorative [[cherry]] trees, ''Prunus serrulata'', and their [[flower|blossoms]]. Cherry fruit (known as ''sakuranbo'') come from a different species of tree. It can also be used as a name.\n"  +
        "\n"  +
        "Sakura are object of the Japanese traditional [[custom]] of ''[[Hanami]]'' or ''Flower viewing''.\n"  +
        "\n"  +
        "==See also==\n"  +
        "\n"  +
        "* [[Hanami]]\n"  +
        "\n"  +
        "==Other websites==\n"  +
        "\n"  +
        "* [http://shop.evanpike.com/keyword/cherry+blossom Photo Gallery of Cherry Blossoms] Sakura from Kyoto, Tokyo, Miyajima and other places around Japan\n"  +
        "\n"  +
        "[[Category:Japan]]\n"  +
        "\n"  +
        "\n"  +
        "");
        
        StringBuffer expResult = new StringBuffer(
        "Hanami parties at Himeji Castle.\n" +
        "\n" +
        "Sakura or Cherry Blossom is the Japanese name for decorative cherry trees, Prunus serrulata, and their blossoms. Cherry fruit (known as sakuranbo) come from a different species of tree. It can also be used as a name.\n" +
        "\n" +
        "Sakura are object of the Japanese traditional custom of Hanami or Flower viewing.\n" +
        "\n" +
        "==See also==\n" +
        "\n" +
        "* Hanami\n" +
        "\n" +
        "==Other websites==\n" +
        "\n" +
        "*  Photo Gallery of Cherry Blossoms Sakura from Kyoto, Tokyo, Miyajima and other places around Japan" +
        "");
        
        boolean b_remove_not_expand_iwiki = true;
        StringBuffer result = WikiParser.convertWikiToText(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testConvertWikiToText_complex_ru() {
        System.out.println("convertWikiToText_complex_ru");
        
        StringBuffer wiki_text = new StringBuffer(
        "{{Фильм\n"  +
        "| РусНаз      = Через тернии к звёздам\n" +
        "}}\n" +
        "[[Изображение:Через-тернии-к-звёздам 2.jpg|thumb|«Через тернии к звёздам»]]\n" +
        "'''«Че́рез те́рнии к звёздам»''' — [[научная фантастика|научно-фантастический]] двухсерийный фильм [[режиссёр]]а [[Викторов, Ричард Николаевич|Ричарда Викторова]] по сценарию [[Кир Булычёв|Кира Булычёва]].\n" +
        "\n" +
        "== Сюжет ==\n" +
        "\n" +
        "{{сюжет}}\n" +
        "[[XXIII]] век. [[Звездолёт]] дальней разведки обнаруживает в [[космос]]е погибший корабль неизвестного происхождения, на нём — гуманоидных существ, искусственно выведенных путём клонирования. Одна девушка оказывается жива, её доставляют на [[Земля (планета)|Землю]], где [[учёный]] Сергей Лебедев поселяет её в своём доме.\n" +
        "\n" +
        "== В ролях ==\n" +
        "\n" +
        "* [[Елена Метёлкина]] — ''Нийя''\n" +
        "\n" +
        "== Ссылки ==\n" +
        "{{викицитатник}}\n" +
        "* [http://ternii.film.ru/ Официальный сайт фильма]\n" +
        "\n" +
        "[[Категория:Киностудия им. М. Горького]]\n" +
        "[[en:Per Aspera Ad Astra (film)]]");
        
        StringBuffer expResult = new StringBuffer(
        "«Через тернии к звёздам»\n" +
        "«Через тернии к звёздам»   научно-фантастический двухсерийный фильм режиссёра Ричарда Викторова по сценарию Кира Булычёва.\n" +
        "\n" +
        "== Сюжет ==\n" +
        "\n" +
        "\n" +
        "XXIII век. Звездолёт дальней разведки обнаруживает в космосе погибший корабль неизвестного происхождения, на нём   гуманоидных существ, искусственно выведенных путём клонирования. Одна девушка оказывается жива, её доставляют на Землю, где учёный Сергей Лебедев поселяет её в своём доме.\n" +
        "\n" +
        "== В ролях ==\n" +
        "\n" +
        "* Елена Метёлкина   Нийя\n" +
        "\n" +
        "== Ссылки ==\n" +
        "\n" +
        "*  Официальный сайт фильма");
        
        boolean b_remove_not_expand_iwiki = true;
        StringBuffer result = WikiParser.convertWikiToText(wiki_text, LanguageType.ru, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    public void testConvertWikiToText_strip_spaces() {
        System.out.println("convertWikiToText__complex");
        StringBuffer    wiki_text, result, expResult;
        // empty test
        wiki_text = new StringBuffer("  \n\n");
        boolean b_remove_not_expand_iwiki = true;
        result = WikiParser.convertWikiToText(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertEquals(0, result.length());
        
        // non-empty test
        wiki_text = new StringBuffer("  word1\n\nword2  \n\n");
        expResult = new StringBuffer(  "word1\n\nword2");
        result = WikiParser.convertWikiToText(wiki_text, LanguageType.simple, b_remove_not_expand_iwiki);
        assertTrue(expResult.toString().equalsIgnoreCase( result.toString() ) );
    }
    
    
}


