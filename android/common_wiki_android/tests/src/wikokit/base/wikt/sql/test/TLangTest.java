package wikokit.base.wikt.sql.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.TestCase;

import wikokit.base.wikt.sql.*;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;

public class TLangTest extends TestCase {

	public Context context = null;
	Connect ruwikt_conn;
	
	protected void setUp() throws Exception {
		super.setUp();
		ruwikt_conn = new Connect(
				context,
				Connect.RU_DB_URL,
				Connect.RU_DB_ZIPFILE,
				Connect.RU_DB_ZIPFILE_SIZE_MB,
				Connect.RU_DB_FILE,
				Connect.RU_DB_FILE_SIZE_MB,
				Connect.DB_DIR
				);
		ruwikt_conn.openDatabase();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		ruwikt_conn.close();
	}

	public void testGetID() {
		System.out.println("getID");
        
		// once upon a time: create Wiktionary parsed db
        // skip for SQLite: TLang.recreateTable(ruwikt_conn);

		SQLiteDatabase db = ruwikt_conn.getDB();
		
        // once upon a time: use Wiktionary parsed db
        TLang.createFastMaps(db);
        
        // and every usual day
        int os_id = TLang.getIDFast(LanguageType.os);
        
        TLang tlang = TLang.get(db, LanguageType.os);
        assertNotNull(tlang);
        assertEquals(tlang.getID(), os_id);		
	}
	/*
	public void testGetIDFast() {
		fail("Not yet implemented");
	}

	public void testGetTLangFast() {
		fail("Not yet implemented");
	}

	public void testGet() {
		fail("Not yet implemented");
	}

	public void testGetAllLanguages() {
		fail("Not yet implemented");
	}

	public void testGetAllTLang() {
		fail("Not yet implemented");
	}*/

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

	/*public void testCreateFastMaps() {
		fail("Not yet implemented");
	}*/

}
