/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikokit.base.wikt.multi.ru;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikt.constant.Image;

/**
 *
 * @author componavt
 */
public class ImageParserRuTest {
    
    public ImageParserRuTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    // testing getFilenameAndCaptionFromText() ---------------------------------
    
    /** Empty templates {{илл|}} and {{илл}} */
    @Test
    public void testGetFilenameAndCaptionFromText_empty() {
        System.out.println("getFilenameAndCaptionFromText_empty");
        String page_title, str;
        
        page_title      = "щегол";
        
        str =   "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "{{илл|}}\n" +
                "{{илл}}\n" +
                "==== Значение ====\n" +
                "# {{зоол.|ru}} ([[:species:Carduelis|Carduelis]]) небольшая [[певчая птица]]\n" +
                "# {{п.|ru}}, {{прост.|ru}}, {{унич.|ru}} [[молокосос]], [[салага]]";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(0, images.length);
    }
    
    /** Caption without filename should be skipped, e.g.: {{илл||Some title}}, 
     */
    @Test
    public void testGetFilenameAndCaptionFromText_caption_without_filename() {
        System.out.println("getFilenameAndCaptionFromText_caption_without_filename");
        String page_title, str;
        
        page_title      = "щегол";        
        str =   "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "{{илл||some title}}";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(0, images.length);
    }
    
    
    /** Skip parameter 'lang', e.g.: 
     * {{илл|lang=da|}} - empty
     * {{илл|lang=io|H. albicilla wing.png}} - ok, filename only without caption
     */
    @Test
    public void testGetFilenameAndCaptionFromText_parameter_lang() {
        System.out.println("getFilenameAndCaptionFromText_parameter_lang");
        String page_title, str;
        
        page_title      = "alo";
        str =   "{{илл|lang=da|}}";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(0, images.length);
        
        str =   "{{илл|lang=io|H. albicilla wing.png}}";
        images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(1, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase("H. albicilla wing.png"));
        assertTrue(  images[0].getCaption() .length() == 0);
        assertEquals(images[0].getMeaningNumber(), 1);
    }
    
    
    /** Skip parameter 'size', {{илл|Chess knight 0965.jpg|At [2]|size=110px}}
     */
    @Test
    public void testGetFilenameAndCaptionFromText_parameter_size() {
        System.out.println("getFilenameAndCaptionFromText_parameter_size");
        String page_title, str;
        
        page_title = "at";
        str = "{{илл|Chess knight 0965.jpg|At [2]|size=110px}}";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(1, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase("Chess knight 0965.jpg"));
        assertTrue(  images[0].getCaption() .equalsIgnoreCase("At"));
        assertEquals(images[0].getMeaningNumber(), 2);
    }
    
    // {{илл.|Benoit Pierre Emery silk scarf.jpg|Шарф из канауса [1]}}
    @Test
    public void testGetFilenameAndCaptionFromText_template_name_with_dot() {
        System.out.println("getFilenameAndCaptionFromText_parameter_template_name_with_dot");
        String page_title, str;
        
        page_title = "канаус";
        str = "{{илл.|Benoit Pierre Emery silk scarf.jpg|Шарф из канауса [1]}}";
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        
        assertNotNull(images);
        assertEquals(1, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase("Benoit Pierre Emery silk scarf.jpg"));
        assertTrue(  images[0].getCaption() .equalsIgnoreCase("Шарф из канауса"));
        assertEquals(images[0].getMeaningNumber(), 1);
    }
    
    
    /** The parameter 'hide' helps to skip indecent images, skip if "hide=1", e.g.:
     *      {{илл|Cunni.png|hide=1}}
     *      {{илл|size=240px|Wiki-pegging.png|hide=1}}
     *      {{илл|Édouard-Henri Avril (20).jpg|Оральный секс|hide=1}}
     */
    @Test
    public void testGetFilenameAndCaptionFromText_parameter_hide_is_1() {
        System.out.println("getFilenameAndCaptionFromText_parameter_hide_is_1");
        String page_title, str;
        
        page_title = "something indecent";
        str = "{{илл|Cunni.png|hide=1}}\n" 
                + "{{илл|size=240px|Wiki-pegging.png|hide=1}}\n"
                + "{{илл|Édouard-Henri Avril (20).jpg|Оральный секс|hide=1}}";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(0, images.length);
    }
    
    
    /** Caption with [[wikilink]] should be skipped, due to problems in correct parsing of [[pipe|pipes]],
     * that is {{илл|LocationAustria.png|Австрия на [[карта|карте]] [[мир|мира]]}} -> {{илл|LocationAustria.png}}
     */
    @Test
    public void testGetFilenameAndCaptionFromText_caption_wikilink() {
        System.out.println("getFilenameAndCaptionFromText_caption_wikilink");
        String page_title, str;
        
        page_title = "Austria";
        str = "{{илл|LocationAustria.png|Австрия на [[карта|карте]] [[мир|мира]]}}";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(1, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase("LocationAustria.png"));
        assertTrue(  images[0].getCaption() .length() == 0);
        assertEquals(images[0].getMeaningNumber(), 1);
    }
    
    /** Caption with [String instead of a number] arises an exception.
     */
    @Test
    public void testGetFilenameAndCaptionFromText_NumberFormatException() {
        System.out.println("getFilenameAndCaptionFromText_NumberFormatException");
        String page_title, str;
        
        page_title = "test_parseInt";
        str = "{{илл|LocationAustria.png|Caption|N=1, 2}}";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(1, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase("LocationAustria.png"));
        assertTrue(  images[0].getCaption() .equalsIgnoreCase("Caption"));
        assertEquals(images[0].getMeaningNumber(), 1);
    }
    
    
    /** Caption is {{PAGENAME}}: {{илл|LocationAustria.png|{{PAGENAME}}}}
     */
    @Test
    public void testGetFilenameAndCaptionFromText_caption_PAGENAME() {
        System.out.println("getFilenameAndCaptionFromText_caption_PAGENAME");
        String page_title, str;
        
        page_title = "Аустрија";
        str = "{{илл|LocationAustria.png|{{PAGENAME}}}}";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(1, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase("LocationAustria.png"));
        assertTrue(  images[0].getCaption() .length() == 0);
        assertEquals(images[0].getMeaningNumber(), 1);
    }
    
    /** Wrong parameters: {{илл|Amazon.parrot.arp.jpg|200px|right|thumb|Boloky}}
     */
    @Test
    public void testGetFilenameAndCaptionFromText_wrong_parameters() {
        System.out.println("getFilenameAndCaptionFromText_wrong_parameters");
        String page_title, str;
        
        page_title      = "boloky";
        str = "{{илл|Amazon.parrot.arp.jpg|200px|right|thumb|Boloky}}";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(1, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase("Amazon.parrot.arp.jpg"));
        assertTrue(  images[0].getCaption() .length() == 0);
        assertEquals(images[0].getMeaningNumber(), 1);
    }
    
    
    /** Filename without caption, e.g.: {{илл|some picture.jpg}}, 
     */
    @Test
    public void testGetFilenameAndCaptionFromText_filename_without_caption() {
        System.out.println("getFilenameAndCaptionFromText_filename_without_caption");
        String page_title, str;
        
        page_title      = "щегол";
        String image_filename = "some picture.jpg";
        
        str =   "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "{{илл|some picture.jpg}}\n" +
                "==== Значение ====\n" +
                "# {{зоол.|ru}} ([[:species:Carduelis|Carduelis]]) небольшая [[певчая птица]]\n" +
                "# {{п.|ru}}, {{прост.|ru}}, {{унич.|ru}} [[молокосос]], [[салага]]";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(1, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase(image_filename));
        assertTrue(  images[0].getCaption() .length() == 0);
        assertEquals(images[0].getMeaningNumber(), 1);
    }
    
    /** Meaning number (5-th parameter, name='n' or '№')
     * {{илл|picture1.jpg|n=9}}
     * {{илл|picture2.jpg|№=9}}
     */
    @Test
    public void testGetFilenameAndCaptionFromText_meaning_number() {
        System.out.println("getFilenameAndCaptionFromText_meaning_number");
        String page_title, str;
        
        page_title      = "щегол";        
        str =   "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "{{илл|picture1.jpg|n=7}}\n" +
                "{{илл|picture2.jpg|The caption 2|№=9}}\n" +
                "==== Значение ====\n" +
                "# {{зоол.|ru}} ([[:species:Carduelis|Carduelis]]) небольшая [[певчая птица]]\n" +
                "# {{п.|ru}}, {{прост.|ru}}, {{унич.|ru}} [[молокосос]], [[салага]]";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(2, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase("picture1.jpg"));
        assertTrue(  images[0].getCaption() .length() == 0);
        assertEquals(images[0].getMeaningNumber(), 7);
        
        assertTrue(  images[1].getFilename().equalsIgnoreCase("picture2.jpg"));
        assertTrue(  images[1].getCaption() .equalsIgnoreCase("The caption 2"));
        assertEquals(images[1].getMeaningNumber(), 9);
    }
    
    /** [Meaning number] in the caption 
     * {{илл|picture1.jpg|3rd object's caption [3]}}
     * {{илл|picture2.jpg|4th and 5th objects' captions [4] and [5]}}
     */
    @Test
    public void testGetFilenameAndCaptionFromText_meaning_number_parse() {
        System.out.println("getFilenameAndCaptionFromText_meaning_number_parse");
        String page_title, str;
        
        page_title      = "щегол";
        str =   "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "{{илл|picture1.jpg|3rd object's caption [3]}}\n" +
                "{{илл}}\n" +
                "{{илл|picture2.jpg|4th [4] and 5th [5] objects' captions}}\n" +
                "==== Значение ====\n" +
                "# {{зоол.|ru}} ([[:species:Carduelis|Carduelis]]) небольшая [[певчая птица]]\n" +
                "# {{п.|ru}}, {{прост.|ru}}, {{унич.|ru}} [[молокосос]], [[салага]]";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(2, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase("picture1.jpg"));
                                                            // 3rd object's caption [3]
        assertTrue(  images[0].getCaption() .equalsIgnoreCase("3rd object's caption"));
        assertEquals(images[0].getMeaningNumber(), 3);
        
        assertTrue(  images[1].getFilename().equalsIgnoreCase("picture2.jpg"));
                                                            // 4th [4] and 5th [5] objects' captions
        assertTrue(  images[1].getCaption() .equalsIgnoreCase("4th and 5th objects' captions"));
        assertEquals(images[1].getMeaningNumber(), 4);
    }
    
    
    // 5. test caption with several meaning numbers
    
    // 6. test caption with wrong meaning number, i.e. > maximum number of meanings
        
    
    /**
     * Test of getFilenameAndCaptionFromText method, of class ImageParserRu.
     * Parse article with image without caption, e.g.: {{илл|some picture.jpg}}, 
     */
    @Test
    public void testGetFilenameAndCaptionFromText() {
        System.out.println("getFilenameAndCaptionFromText");
        String page_title, str;
        
        page_title      = "щегол";
        String image_filename = "some picture.jpg";
        
        str =   "{{-ru-}}\n" +
                "=== Семантические свойства ===\n" +
                "{{илл|some picture.jpg}}\n" +
                "==== Значение ====\n" +
                "# {{зоол.|ru}} ([[:species:Carduelis|Carduelis]]) небольшая [[певчая птица]]\n" +
                "# {{п.|ru}}, {{прост.|ru}}, {{унич.|ru}} [[молокосос]], [[салага]]";
        
        Image[] images = ImageParserRu.getFilenameAndCaptionFromText( page_title, str );
        assertNotNull(images);
        assertEquals(1, images.length);
        
        assertTrue(  images[0].getFilename().equalsIgnoreCase(image_filename));
        assertTrue(  images[0].getCaption() .length() == 0);
        assertEquals(1, images[0].getMeaningNumber());
    }
    
    // ----------------------------------------------------- eo testing getFilenameAndCaptionFromText()

    
}
