/*
 * CategoryHyponymsTest.java
 * JUnit based test
 */

package wikipedia.category;

import junit.framework.*;
import wikipedia.sql.Connect;
import wikipedia.sql.PageTableBase;
import wikipedia.sql.PageNamespace;
import wikipedia.language.LanguageType;

import java.util.List;

public class CategoryHyponymsTest extends TestCase {
    
    Connect         connect_simple; //idfsimplewiki_conn; //, connect_ru;
    
    static String   cat1,    art1,    subcategory1,    art_redirect1;
    static int      cat1_id, art1_id, subcategory1_id, art_redirect1_id;
    
    public CategoryHyponymsTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect_simple = new Connect();
        connect_simple.Open(Connect.WP_HOST,Connect.WP_SIMPLE_DB,   Connect.WP_USER,    Connect.WP_PASS, LanguageType.simple);
        
        cat1 = connect_simple.enc.EncodeFromJava("Folklore");
        art1 = connect_simple.enc.EncodeFromJava("Ghost_light");
        subcategory1 = connect_simple.enc.EncodeFromJava("Superstitions");
        art_redirect1 = connect_simple.enc.EncodeFromJava("Doppelganger");
        
        cat1_id = PageTableBase.getIDByTitleNamespace(connect_simple, cat1, PageNamespace.CATEGORY); // 41712
        art1_id = PageTableBase.getIDByTitleNamespace(connect_simple, art1, PageNamespace.MAIN);     // 50387
        subcategory1_id = PageTableBase.getIDByTitleNamespace(connect_simple, subcategory1, PageNamespace.CATEGORY);
        art_redirect1_id = PageTableBase.getIDByTitleNamespace(connect_simple, art_redirect1, PageNamespace.MAIN);
    }

    protected void tearDown() throws Exception {
        connect_simple.Close();
    }

    /**
     * Test of getArticlesOfSubCategories method, of class wikipedia.kleinberg.CategoryHyponyms.
     */
    public void testGetArticlesOfSubCategories_simple() {
        System.out.println("getArticlesOfSubCategories_simple");
        List<String>    result, page_titles;
        String category_title = "absent category";
        
        result = CategoryHyponyms.getArticlesOfSubCategories(connect_simple, category_title);
        assertTrue(null != result);
        assertEquals(0, result.size());
                
        page_titles = CategoryHyponyms.getArticlesOfSubCategories(connect_simple, cat1);
        assertTrue(8 < page_titles.size());
        
        // Category:Folklore -> Category:Superstitions -> Article:Thirteen
        assertTrue(page_titles.contains("Thirteen"));
    }
    
    
    public void testGetArticlesOfCategory_simple() {
        System.out.println("getArticlesOfCategory_simple");
        
        String category_title;
        List<String> result;
        
        category_title = "absent category";
        result = CategoryHyponyms.getArticlesOfCategory(connect_simple,
                                    category_title);
        assertTrue(null != result);
        assertTrue(0 == result.size());
        
        category_title = "Folklore";
        result = CategoryHyponyms.getArticlesOfCategory(connect_simple,
                                    category_title);
        assertTrue(0 < result.size());
        assertTrue(5 <= result.size());
        assertTrue(result.contains(art1));
        
        // it should not contain subcategory, only articles
        assertFalse(result.contains(subcategory1));
    }
    
    /** Checks that redirect articles are skipped. It is impossible to check, 
     * because redirect-article don't have categories.
     */
    // public void testGetArticlesOfCategory_simple_redirect() {}

    
    public void testGetSubcategoriesOfCategory_simple() {
        System.out.println("GetSubcategoriesOfCategory_simple");
        
        List<String> result;
        result = CategoryHyponyms.getSubcategoriesOfCategory(connect_simple,
                                    cat1);
        assertTrue(3 <= result.size());
        
        // it should contain subcategory
        assertTrue(result.contains(subcategory1));
        
        // it should not contain articles, only subcategories
        assertFalse(result.contains(art1));
    }
}
