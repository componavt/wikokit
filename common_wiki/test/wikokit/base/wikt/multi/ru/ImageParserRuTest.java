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
        assertEquals(images[0].getMeaningNumber(), 1);
    }
    
}
