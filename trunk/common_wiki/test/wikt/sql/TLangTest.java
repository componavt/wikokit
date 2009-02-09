
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
    public void testRecreateTable() {
        System.out.println("fillDB");
        TLang.recreateTable(ruwikt_parsed_conn);
    }

    @Test
    public void testInsert() {
        System.out.println("insert_ru");

        Connect conn = ruwikt_parsed_conn;
        String code = "ru";
        String lang_name = ruwikt_parsed_conn.enc.EncodeFromJava("Русский");
        String name = ruwikt_parsed_conn.enc.EncodeFromJava("test_lang_Русский");

        TLang p = null;

        // blockhead test
        p = TLang.get(conn, null);
        assertTrue(null == p);

        LanguageType lt = LanguageType.ru;
        p = TLang.get(conn, lt);
        if(null != p) {
            TLang.delete(conn, lt);
        }

        TLang.insert(conn, code, name);
        p = TLang.get(conn, lt);
        
        assertTrue(p != null);

        int id = p.getID();
        assertTrue(id > 0);

        lt = p.getLanguage();
        assertTrue(lt.getCode().equalsIgnoreCase(code));
        assertTrue(lt.getName().equalsIgnoreCase(lang_name));
        
        // delete temporary DB record
        TLang.delete(conn, lt);

        p = TLang.get(conn, lt);
        assertTrue(p == null);
    }

}