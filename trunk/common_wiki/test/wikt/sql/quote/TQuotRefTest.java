
package wikt.sql.quote;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;

public class TQuotRefTest {

    public Connect   ruwikt_parsed_conn;

    public TQuotRefTest() {
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
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS,LanguageType.ru);
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }


    @Test
    public void testInsert_empty_reference() {
        System.out.println("insert_empty_reference");
        Connect connect = ruwikt_parsed_conn;
        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;

        // it should be skipped - it's empty reference
        _author = "";
        _author_wikilink = "";
        _title = "";
        _title_wikilink = "";
        _publisher = "";
        _source = "";

        TQuotRef result = TQuotRef.insert(connect, _author, _author_wikilink,
                                        _title, _title_wikilink,
                                        _publisher, _source);

        assertNull(result);
    }

    @Test
    public void testInsert_ru_only_author() {
        System.out.println("insert_ru_only_author");
        Connect connect = ruwikt_parsed_conn;

        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;

        _author = "test_И. А. Крылов";
        _author_wikilink = "test_Крылов, Иван Андреевич";
        _title = "";
        _title_wikilink = "";
        _publisher = "";
        _source = "";

        TQuotRef result = TQuotRef.insert(connect, _author, _author_wikilink, _title, _title_wikilink,
                                        _publisher, _source);
        assertNotNull(result);

        TQuotAuthor a = result.getAuthor();
        assertNotNull( a );
        assertEquals(0, _author.compareTo( a.getName() ));

        a.delete(connect);
        result.delete(connect);
    }

    @Test
    public void testInsert_ru_only_author_and_title() {
        System.out.println("insert_ru_only_author_and_title");
        Connect connect = ruwikt_parsed_conn;

        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;

        _author = "test_И. А. Крылов";
        _author_wikilink = "";
        _title = "test_Лиса и виноград";
        _title_wikilink = "";
        _publisher = "";
        _source = "";

        TQuotRef result = TQuotRef.insert(connect, _author, _author_wikilink,
                                        _title, _title_wikilink,
                                        _publisher, _source);
        assertNotNull(result);

        TQuotAuthor a = result.getAuthor();
        assertNotNull( a );
        assertEquals(0, _author.compareTo( a.getName() ));

        assertEquals(0, _title.compareTo( result.getTitle() ));

        a.delete(connect);
        result.delete(connect);
    }

    @Test
    public void testInsert_full_reference_with_years() {
        System.out.println("insert_full_reference_with_years");
        Connect connect = ruwikt_parsed_conn;
        String _author, _author_wikilink, _title, _title_wikilink,
               _publisher, _source;
        int _from, _to;
        String page_title = "the test entry";

        _author = "";
        _author_wikilink = "";
        _title = "test_Лиса и виноград";
        _title_wikilink = "";
        _publisher = "";
        _source = "";
        _from = _to = 91931;

        TQuotRef result = TQuotRef.insertWithYears(connect, page_title,
                                        _author, _author_wikilink,
                                        _title, _title_wikilink,
                                        _publisher, _source,
                                        _from, _to);
        assertNotNull(result);

        TQuotAuthor a = result.getAuthor();
        assertNull( a );

        TQuotYear y = result.getYear();
        assertNotNull( y );
        assertEquals(_from, y.getFrom());
        assertEquals(_to, y.getTo());

        y.delete(connect);
        result.delete(connect);
    }

    @Test
    public void testInsert_ru_getOrInsert() {
        System.out.println("insert_ru_getOrInsert");
        Connect connect = ruwikt_parsed_conn;

        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;

        _author = "test_Alexander Pushkin";
        _author_wikilink = "";
        _title = "test_Ruslan i Lyudmila";
        _title_wikilink = "";
        _publisher = "";
        _source = "";

        TQuotRef result1 = TQuotRef.getOrInsert(connect, _author, _author_wikilink,
                                        _title, _title_wikilink,
                                        _publisher, _source);
        assertNotNull(result1);

        TQuotRef result2 = TQuotRef.getOrInsert(connect, _author, _author_wikilink,
                                        _title, _title_wikilink,
                                        _publisher, _source);
        assertEquals(result1.getID(), result2.getID());

        TQuotAuthor a = result1.getAuthor();
        a.delete(connect);
        result1.delete(connect);
    }

    @Test
    public void testInsert_ru_getOrInsert_with_years() {
        System.out.println("insert_ru_getOrInsert");
        Connect connect = ruwikt_parsed_conn;

        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;
        int _from, _to;
        String page_title = "the test entry";

        _author = "test_Alexander Pushkin";
        _author_wikilink = "";
        _title = "test_Ruslan i Lyudmila";
        _title_wikilink = "";
        _publisher = "";
        _source = "";
        _from = _to = 91931;

        TQuotRef result1 = TQuotRef.getOrInsertWithYears(connect, page_title,
                                        _author, _author_wikilink,
                                        _title, _title_wikilink,
                                        _publisher, _source,
                                        _from, _to);
        assertNotNull(result1);

        TQuotRef result2 = TQuotRef.getOrInsertWithYears(connect, page_title,
                                        _author, _author_wikilink,
                                        _title, _title_wikilink,
                                        _publisher, _source,
                                        _from, _to);
        assertEquals(result1.getID(), result2.getID());

        TQuotYear y = result1.getYear();
        assertNotNull( y );
        y.delete(connect);

        TQuotAuthor a = result1.getAuthor();
        a.delete(connect);
        result1.delete(connect);
    }

}