
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;

public class TLangTest {

    public Connect   ruwikt_parsed_conn;
    
    public TLangTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }

    
    @Test
    public void testFillLocalMaps() {
        System.out.println("fillLocalMaps");
        TLang.fillLocalMaps();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testFillDB() {
        System.out.println("fillDB");
        TLang.fillDB();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testInsert() {
        System.out.println("insert_ru");

        Connect conn = ruwikt_parsed_conn;
        String code = "ru";
        String name = ruwikt_parsed_conn.enc.EncodeFromJava("test_lang_Русский");
        
        TLang p = null;
        p = TLang.get(conn, code);
        if(null == p) {
            TLang.delete(conn, code);
        }

        TLang.insert(conn, code, name);
        p = TLang.get(conn, code);
        
        assertTrue(p != null);

        int id = p.getID();
        assertTrue(id > 0);

        LanguageType lt = p.getLanguage();
        assertTrue(lt.getCode().equalsIgnoreCase(code));
        assertTrue(lt.getName().equalsIgnoreCase(name));
        
        // delete temporary DB record
        TLang.delete(conn, code);

        p = TLang.get(conn, code);
        assertTrue(p == null);
    }

}