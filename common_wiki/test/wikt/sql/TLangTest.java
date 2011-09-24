
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

        // MySQL
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);

        // SQLite                   //Connect.testSQLite();
        //ruwikt_parsed_conn.OpenSQLite(Connect.RUWIKT_SQLITE, LanguageType.ru);
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }
    
    @Test
    public void testInsert() {
        System.out.println("insert_ru");
/*
        Connect conn = ruwikt_parsed_conn;
        String code = "ru";
        //String lang_name = ruwikt_parsed_conn.enc.EncodeFromJava("Русский");
        String name = ruwikt_parsed_conn.enc.EncodeFromJava("Russian");

        TLang p = null;

        // blockhead test
        p = TLang.get(conn, null);
        assertTrue(null == p);

        LanguageType lt = LanguageType.ru;
        p = TLang.get(conn, lt);
        if(null != p) {
            TLang.delete(conn, lt);
        }

        //System.err.println("One warnings should be...");
        TLang.insert(conn, code, name);
        p = TLang.get(conn, lt);
        
        assertTrue(p != null);

        int id = p.getID();
        assertTrue(id > 0);

        lt = p.getLanguage();
        assertTrue(lt.getCode().equalsIgnoreCase(code));
        assertTrue(lt.getName().equalsIgnoreCase(name));    // todo lang_name also
*/
        // delete temporary DB record
        //TLang.delete(conn, lt);
        //p = TLang.get(conn, lt);
        //assertTrue(p == null);
    }
    
    @Test
    public void testGetID() {
        System.out.println("getID");
        
        // once upon a time: create Wiktionary parsed db
//        TLang.recreateTable(ruwikt_parsed_conn);
        
        // once upon a time: use Wiktionary parsed db
        TLang.createFastMaps(ruwikt_parsed_conn);
        
        // and every usual day
        int os_id = TLang.getIDFast(LanguageType.os);
        
        TLang tlang = TLang.get(ruwikt_parsed_conn, LanguageType.os);
        assertNotNull(tlang);
        assertEquals(tlang.getID(), os_id);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");

        int n_foreign_POS_old,  n_translations_old;
        int n_foreign_POS,      n_translations;
        
        TLang tlang = TLang.get(ruwikt_parsed_conn, LanguageType.os);
        assertNotNull(tlang);

        // save old values
        n_foreign_POS_old = tlang.getNumberPOS();
        n_translations_old= tlang.getNumberTranslations();

        TLang.update(ruwikt_parsed_conn, LanguageType.os, 111, 333);
        
        tlang = TLang.get(ruwikt_parsed_conn, LanguageType.os);
        assertNotNull(tlang);

        n_foreign_POS = tlang.getNumberPOS();
        n_translations= tlang.getNumberTranslations();
        assertEquals(111, n_foreign_POS);
        assertEquals(333, n_translations);

        // restore old values
        TLang.update(ruwikt_parsed_conn, LanguageType.os, n_foreign_POS_old, n_translations_old);
    }
    
    /* This test should go after testInsert(), else you will got a mess in the table 'lang'. */
    @Test
    public void testRecreateTable() {
        System.out.println("recreateTable, fill table `lang`");
//        TLang.recreateTable(ruwikt_parsed_conn);
    }

    @Test
    public void testParseLangCode() {
        System.out.println("parseLangCode");
        String str;
        TLang[] langs;

        str = "";
        langs = TLang.parseLangCode(str);
        assertTrue(langs != null);
        assertTrue(langs.length == 0);

        str = " not_valid language-code";
        langs = TLang.parseLangCode(str);
        assertTrue(langs != null);
        assertTrue(langs.length == 0);

        str = " en only-one-valid-code";
        langs = TLang.parseLangCode(str);
        assertTrue(langs != null);
        assertTrue(langs.length == 1);

        str = " en lt ru os fr it's enough";
        langs = TLang.parseLangCode(str);
        assertTrue(langs != null);
        assertTrue(langs.length == 5);
    }

    @Test
    public void testIsEquals() {
        System.out.println("isEquals");
        String str_lang2;
        TLang tlang1[];
        boolean res;

        // isEquals(TLang tlang1[], String str_lang2)

        tlang1 = new TLang[0];
        // source_lang[0] = TLang.get(LanguageType.en);

        // 0 == 0
        str_lang2 = "";
        res = TLang.isEquals(tlang1, str_lang2);
        assertTrue(res); // empty codes are equal

        // 0 == 0
        str_lang2 = "non-language_code";
        res = TLang.isEquals(tlang1, str_lang2);
        assertTrue(res);

        // "en" != 0
        str_lang2 = "non-language_code and one language code en";
        res = TLang.isEquals(tlang1, str_lang2);
        assertFalse(res);

        // "en" != "de"
        str_lang2 = "en";
        tlang1 = new TLang[1];
        tlang1[0] = TLang.get(LanguageType.de);
        res = TLang.isEquals(tlang1, str_lang2);
        assertFalse(res);

        // "os fr" == "fr os"
        str_lang2 = "os fr";
        tlang1 = new TLang[2];
        tlang1[0] = TLang.get(LanguageType.fr);
        tlang1[1] = TLang.get(LanguageType.os);
        res = TLang.isEquals(tlang1, str_lang2);
        assertTrue(res);
    }
}