package wikipedia.util;

import wikipedia.language.Encodings;
import junit.framework.*;

public class StringUtilRegularTest extends TestCase {
    
    public StringUtilRegularTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(StringUtilRegularTest.class);
        
        return suite;
    }

    /**
     * Test of stripNonWordLetters method, of class wikipedia.util.StringUtilRegular.
     */
    public void testStripNonWordLetters() {
        System.out.println("stripNonWordLetters");
        
        String[] words = {"\nword1", "\t word-long2\r\n"};
        String[] expResult = {"word1", "word-long2"};
        StringUtilRegular.stripNonWordLetters(words);
        assertEquals(expResult[0], words[0]);
        assertEquals(expResult[1], words[1]);
    }
    
    public void testGetLettersTillSpace() {
        System.out.println("getLettersTillSpace");
        
        String source, expResult, result;
        
        source = "\nword1 word2";
        expResult = "word1";
        result = StringUtilRegular.getLettersTillSpace(source);
        assertEquals(expResult, result);
        
        source = "\t word-long2\r\n hello";
        expResult = "word-long2";
        result = StringUtilRegular.getLettersTillSpace(source);
        assertEquals(expResult, result);
    }

    public void testGetLettersTillHyphen() {
        System.out.println("getLettersTillHyphen");

        String source, expResult, result;

        source = "\nword1-word2";
        expResult = "word1";
        result = StringUtilRegular.getLettersTillHyphen(source);
        assertEquals(expResult, result);

        source = "\t word-long2\r\n hello";
        expResult = "word";
        result = StringUtilRegular.getLettersTillHyphen(source);
        assertEquals(expResult, result);
    }

    public void testEncodeRussianToLatinitsa() {
        System.out.println("encodeRussianToLatinitsa");
        String r, ru, lat;
        
        ru = "А потом он аккуpатно заменИл PS на ЗЫ и сохpанил письмо.";
        lat = "A potom on akkupatno zamenIl PS na ZY i soxpanil pis'mo.";
        
        //r = StringUtilRegular.encodeRussianToLatinitsa(ru);
        //r = StringUtilRegular.encodeRussianToLatinitsa(wikipedia.util.Encodings.UTF8ToCp1251(ru));
        //r = StringUtilRegular.encodeRussianToLatinitsa(wikipedia.util.Encodings.UTF8ToLatin1(ru));
        //r = StringUtilRegular.encodeRussianToLatinitsa(wikipedia.util.Encodings.Latin1ToUTF8(ru));
        //r = StringUtilRegular.encodeRussianToLatinitsa(Encodings.FromTo(ru, "Cp1251", "UTF8"), "Cp1251", "UTF8");
        r = StringUtilRegular.encodeRussianToLatinitsa(ru, Encodings.enc_java_default, Encodings.enc_int_default);
        
        //r = StringUtilRegular.encodeRussianToLatinitsa(wikipedia.util.Encodings.UTF8ToCp1251(ru));
        //r = StringUtilRegular.encodeRussianToLatinitsa(wikipedia.util.Encodings.UTF8ToCp1251(ru));
        assertEquals(lat, r);
        
        ru = "В связи с установившейся в системном блоке жарой системный таймер переходит на летнее время.";
        lat = "V svyazi s ustanovivshejsya v sistemnom bloke zharoj sistemnyj tajmer perexodit na letnee vremya.";
        //r = StringUtilRegular.encodeRussianToLatinitsa(wikipedia.util.Encodings.Latin1ToUTF8(ru));
        ru = Encodings.FromTo(ru, Encodings.enc_java_default, Encodings.enc_int_default);
        r = StringUtilRegular.encodeRussianToLatinitsa(ru, Encodings.enc_java_default, Encodings.enc_int_default);
        assertEquals(lat, r);
    }

    private static String text = "text before \n" +
            "\n" +
            "===Bibliography===\n" +    // 14 (end position of header)
            "* N\n" +
            "== Links ==\n" +          // 35
            "[[Category:Musical instruments]]\n";

    public void testGetFirstHeaderPosition() {
        System.out.println("getFirstHeaderPosition");
        int     pos;

        pos = StringUtilRegular.getFirstHeaderPosition(0, text);
        assertEquals(14, pos);

        pos = StringUtilRegular.getFirstHeaderPosition(pos + 5, text);
        assertEquals(37, pos);
    }

    public void testGetTextTillFirstHeaderPosition() {
        System.out.println("getTextTillFirstHeaderPosition");
        
        String s1 = StringUtilRegular.getTextTillFirstHeaderPosition(0, text);
        assertTrue(s1.equalsIgnoreCase("text before \n\n"));

        String s2 = StringUtilRegular.getTextTillFirstHeaderPosition(37 + 5, text);
        assertTrue(s2.equalsIgnoreCase("nks ==\n[[Category:Musical instruments]]\n"));
    }
}
