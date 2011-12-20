package wikipedia.util;

import java.util.Arrays;
import java.util.List;
import junit.framework.*;


public class StringUtilTest extends TestCase {

    //public StringUtil   string_util;
    
    public StringUtilTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        //string_util = new StringUtil();
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(StringUtilTest.class);
        
        return suite;
    }

    public void testJoin() {
        System.out.println("Join");
        String[] words = {"one", "two", "five"};
        String delimiter = "_";
        String result = StringUtil.join(delimiter, words);
        assertEquals(0, result.compareTo("one_two_five"));
    }
    
    public void testJoinInt() {
        System.out.println("JoinInt");
        int[] n = {1, 2, 7};
        String delimiter = "_";
        String result = StringUtil.join(delimiter, n);
        assertEquals(0, result.compareTo("1_2_7"));
    }

    public void testSplit() {
        System.out.println("Split");
        //String[] words; // = {"one", "two", "five"};
        String delimiter = "_";
        String[] words = StringUtil.split(delimiter, "one_two_five");
        assertEquals(3, words.length);
        
        String[] word1 = StringUtil.split(delimiter, "one");
        assertEquals(1, word1.length);
        
        String[] word0 = StringUtil.split(delimiter, "");
        assertEquals(0, word0.length);
    }

    public void testEscapeChars(){
        System.out.println("EscapeChars");
        String escaped, unescaped, empty;
        
        StringUtil.escapeChars(null);
        
        empty = "";
        escaped   = StringUtil.escapeChars(empty);
        assertEquals(0, escaped.length());
        
        unescaped = "New's coming!";
        escaped   = StringUtil.escapeChars(unescaped);
        assertEquals(escaped, "New\\'s coming!");

        unescaped = "\\";
        escaped   = StringUtil.escapeChars(unescaped);
        assertEquals("\\\\", escaped);
    }    
    
    public void testEscapeCharDollarAndBackslash(){
        System.out.println("escapeCharDollarAndBackslash");
        String unescaped, empty;
        StringBuffer escaped;
        
        StringUtil.escapeCharDollarAndBackslash(null);
        
        empty = "";
        escaped   = StringUtil.escapeCharDollarAndBackslash(empty);
        assertEquals(0, escaped.length());
        
        // first: $ -> \\$
        unescaped = "$dollar";
        escaped   = StringUtil.escapeCharDollarAndBackslash(unescaped);
        assertEquals(escaped.toString(), "\\$dollar");
        
        // second: skip, in order to don't double slashes \\$ -> \\$
        escaped   = StringUtil.escapeCharDollarAndBackslash(escaped.toString());
        assertEquals(escaped.toString(), "\\$dollar");
  
        // f'''\\\\'''v
        // two slashes "\\" - skip double slashes, \\ -> \\
        unescaped = "f\\\\v";
        escaped   = StringUtil.escapeCharDollarAndBackslash(unescaped);
        assertEquals(escaped.toString(), "f\\\\\\\\v");
        unescaped = "ff\\\\v";
        escaped   = StringUtil.escapeCharDollarAndBackslash(unescaped);
        assertEquals(escaped.toString(), "ff\\\\\\\\v");
        
        // )\\ -> )\\\\
        unescaped = ")\\";
        escaped   = StringUtil.escapeCharDollarAndBackslash(unescaped);
        assertEquals(")\\\\", escaped.toString());
        
        // double slashes \\) -> \\\\)
        unescaped = "\\)";
        escaped   = StringUtil.escapeCharDollarAndBackslash(unescaped);
        assertEquals("\\\\)", escaped.toString());
    }
    
    
    //public static String (String text, char s, char d){
    public void testSubstChar(){
        System.out.println("substChar");
        String s = "Too much   spaces are here.!@#(*$&";
        String s_underscored = s.replace(' ', '_');
        assertEquals(s_underscored, "Too_much___spaces_are_here.!@#(*$&");
    }
        
    public void testGetUnique(){
        System.out.println("getUnique");
        
        String[] l = {"dinnER", "dinner", "cUp", "Cup", "CUP"};
        String[] exp_result =  {"dinnER", "cUp"};
        
        List<String> result = StringUtil.getUnique(Arrays.asList(l));
        assertEquals(Arrays.asList(exp_result), result);
        
        result = StringUtil.getUnique(null);
        assertEquals(0, result.size());
    }
        
    
    public void testAddOR(){
        System.out.println("addOR");
        
        List<String> result = StringUtil.addOR(null, null);
        assertEquals(0, result.size());
        
        String[] a = {"dinnER", "dinner", "CUP", "halva"};
        String[] b = {"DInner", "dinner", "dressing"};
        String[] exp_result =  {"dinnER", "CUP", "halva", "dressing"};
        
        result = StringUtil.addOR(Arrays.asList(a), Arrays.asList(b));
        assertEquals(Arrays.asList(exp_result), result);
        
        result = StringUtil.addOR(null, Arrays.asList(b));
        assertEquals(Arrays.asList(b), result);
        
        result = StringUtil.addOR(null, Arrays.asList(a));
        assertEquals(Arrays.asList(a), result);
        
        // b is null, and changes of source 'a' do not affect the 'result' array
        result = StringUtil.addOR(Arrays.asList(a), null);
        assertEquals(Arrays.asList(a), result);
        a[0] = "new value";
        assertFalse(result.get(0).equalsIgnoreCase(a[0]));
    }

    //String[] addORCaseSensitive(String[] a,String[] b)
     public void testAddORCaseSensitive(){
        System.out.println("addORCaseSensitive");

        String[] result = StringUtil.addORCaseSensitive(null, null);
        assertEquals(0, result.length);

        String[] a = {"dinnER", "dinner", "CUP", "halva"};
        String[] b = {"DInner", "dinner", "CUP", "dressing"};
        String[] exp_result =  {"dinnER", "dinner", "CUP", "halva", "DInner", "dressing"};

        result = StringUtil.addORCaseSensitive(a, b);
        assertEquals(exp_result.length, result.length);
        for(String s : exp_result) {
            assertTrue(StringUtil.containsIgnoreCase(result, s));
        }

        // b is null, and changes of source 'a' do not affect the 'result' array
        result = StringUtil.addORCaseSensitive(a, null);
        a[0] = "new value";
        assertFalse(result[0].equalsIgnoreCase(a[0]));
    }
    
    public void testContainsIgnoreCase() {
        System.out.println("testContainsIgnoreCase");
        
        boolean b = StringUtil.containsIgnoreCase(null, "");
        assertEquals(false, b);
        
        String[] a = {"dinnER", "dinner", "CUP", "halva"};
        b = StringUtil.containsIgnoreCase(a, "cuP");
        assertEquals(true, b);
    }


    public void testIntersect() {
        System.out.println("testIntersect");
        
        String[] a = {"apple", "intel", "amd"};
        String[] b = {"intel", "super-turbo-cpu", "amd", "something"};
        String[] should_be = {"intel", "amd"};
        
        String[] res = StringUtil.intersect(a, b);
        assertEquals(res.length, should_be.length);
        
        for(int i=0;i<res.length;i++) {
            assertEquals(res[i], should_be[i]);
        }
    }
    
    public void testgetTextBeforeFirstColumn() {
        System.out.println("testgetTextBeforeFirstColumn");
        
                                StringUtil.getTextBeforeFirstColumn(null);
        assertEquals("text",    StringUtil.getTextBeforeFirstColumn("text"));
        assertEquals("text2",   StringUtil.getTextBeforeFirstColumn("text2:asdf"));
        assertEquals("",        StringUtil.getTextBeforeFirstColumn(":|text2:asdf"));
    }
    
    //getTextBeforeFirstVerticalPipe
    public void testgetTextBeforeFirstVerticalPipe() {
        System.out.println("testgetTextBeforeFirstVerticalPipe");
        assertEquals("text",    StringUtil.getTextBeforeFirstVerticalPipe("text"));
        assertEquals("text2",   StringUtil.getTextBeforeFirstVerticalPipe("text2|asdf"));
        assertEquals(":",        StringUtil.getTextBeforeFirstVerticalPipe(":|text2:|asdf"));
    }
    
    public void testGetTextAfterFirstColumn() {
        System.out.println("testGetTextAfterFirstColumn");
        
                                StringUtil.getTextAfterFirstColumn(null);
        assertEquals("",        StringUtil.getTextAfterFirstColumn("text"));
        assertEquals("asdf",    StringUtil.getTextAfterFirstColumn("text2:asdf"));
        assertEquals("",    StringUtil.getTextAfterFirstColumn("text2:"));
        assertEquals("text2:asdf",  StringUtil.getTextAfterFirstColumn(":text2:asdf"));
    }
    public void testGetTextAfterFirstVerticalPipe() {
        System.out.println("testGetTextAfterFirstVerticalPipe");
        
                                StringUtil.getTextAfterFirstVerticalPipe(null);
        assertEquals("",        StringUtil.getTextAfterFirstVerticalPipe("text"));
        assertEquals("asdf",    StringUtil.getTextAfterFirstVerticalPipe("text2|asdf"));
        assertEquals("",    StringUtil.getTextAfterFirstVerticalPipe("text2|"));
        assertEquals("text2:asdf",  StringUtil.getTextAfterFirstVerticalPipe("|text2:asdf"));
    }
    public void testGetTextAfterFirstSpace() {
        System.out.println("testGetTextAfterFirstSpace");
                                StringUtil.getTextAfterFirstSpace(null);
        assertEquals("",        StringUtil.getTextAfterFirstSpace("text"));
        assertEquals("asdf",    StringUtil.getTextAfterFirstSpace("text2 asdf"));
        assertEquals("",    StringUtil.getTextAfterFirstSpace("text2 "));
        assertEquals("text2:asdf",  StringUtil.getTextAfterFirstSpace(" text2:asdf"));
    }
    
    public void testgetTextBeforeFirstAndSecondColumns() {
        System.out.println("testgetTextBeforeFirstAndSecondColumns");
        
                                StringUtil.getTextBeforeFirstAndSecondColumns(null);
        assertEquals("text",    StringUtil.getTextBeforeFirstAndSecondColumns("text"));
        assertEquals("asdf",    StringUtil.getTextBeforeFirstAndSecondColumns("text2:asdf"));
        assertEquals("text2",   StringUtil.getTextBeforeFirstAndSecondColumns(":text2:asdf"));
        assertEquals("Теория_множеств",StringUtil.getTextBeforeFirstAndSecondColumns("C:Теория_множеств:Eo:Kategorio:Aroteorio"));
    }

    public void testGetTextTillSpaceOrPuctuationMark() {
        System.out.println("getTextTillSpaceOrPuctuationMark");

                                StringUtil.getTextTillSpaceOrPuctuationMark(0, null);
                                StringUtil.getTextTillSpaceOrPuctuationMark(100, ":text2:asdf");
                                StringUtil.getTextTillSpaceOrPuctuationMark(3, "012");
        assertEquals("text",    StringUtil.getTextTillSpaceOrPuctuationMark(0, "text"));
        assertEquals("ext2",    StringUtil.getTextTillSpaceOrPuctuationMark(1, "text2:asdf"));
        assertEquals("word",    StringUtil.getTextTillSpaceOrPuctuationMark(4, "0123word next"));
        assertEquals("word",    StringUtil.getTextTillSpaceOrPuctuationMark(4, "0123word, next"));
    }


    public void testIsInterWiki() {
        System.out.println("testIsInterWiki");
        
        assertTrue( StringUtil.isInterWiki("ru:test"));
        assertFalse(StringUtil.isInterWiki("ruutest"));
        // #
        assertTrue (StringUtil.isInterWiki("ru:"));
        assertFalse(StringUtil.isInterWiki("ru"));
        assertFalse(StringUtil.isInterWiki("r"));
        assertFalse(StringUtil.isInterWiki(""));
        assertFalse(StringUtil.isInterWiki(null));
        
        String[] iwiki_example = {"ru:", "fa:وب جهان‌گستر","br:World Wide","lt:Žiniatinklis","lv:Globālais tīmeklis","th:เวิลด์ไวด์เว็บ","tr:Dünya Çapında Ağ"};
        for(String title : iwiki_example) {
            assertTrue(StringUtil.isInterWiki(title));
        }
    }

    public void testUpperFirstLowerSecondLetter() {
        System.out.println("testUpperFirstLowerSecondLetter");
        String source[] =  {"en", "rU", "Eo", "FR"};
        String compare[] = {"En", "Ru", "Eo", "Fr"};
        
        for(int i=0; i<source.length; i++) {
            String res = StringUtil.UpperFirstLowerSecondLetter(source[i]);
            assertEquals(res, compare[i]);
        }
    }
    
    
    public void testUpperFirstLetter() {
        System.out.println("testUpperFirstLetter");
        String source[] =  {"THE TEXT", "алфавит", "alphabet", "a"};
        String compare[] = {"THE TEXT", "Алфавит", "Alphabet", "A"};
        
        assertEquals(null, StringUtil.UpperFirstLetter(null));
                
        for(int i=0; i<source.length; i++) {
            String res = StringUtil.UpperFirstLetter(source[i]);
            assertEquals(res, compare[i]);
        }
    }
}
