
package wikt.sql.quote;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.language.LanguageType;
import wikipedia.sql.Connect;
import wikt.sql.TLang;
import wikt.sql.TMeaning;

public class TQuoteTest {

    public Connect   ruwikt_parsed_conn;

    private static TMeaning _meaning = new TMeaning(77787,null, 21223,
                                        7, // int _meaning_n,
                                        null, 872); // TWikiText _wiki_text,int _wiki_text_id)
    private static TLang _lang = TLang.get(LanguageType.en);
    
    public TQuoteTest() {
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
    public void testInsert_empty_quote() {
        System.out.println("insert_empty_quote");
        Connect connect = ruwikt_parsed_conn;
        
        TQuotRef _quot_ref = null;
        
        String _text = "";
        TQuote result = TQuote.insert(connect, _meaning, _lang, _text, _quot_ref);
        assertNull(result);
    }

    @Test
    public void testInsert_ru_only_text() {
        System.out.println("insert_ru_only_author");
        Connect connect = ruwikt_parsed_conn;

        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;

        _author = "test_И. А. Крылов";
        _author_wikilink = "test_Крылов, Иван Андреевич";
        _title = "";
        _title_wikilink = "";
        _publisher = "";
        _source = "";

        TQuotRef quot_ref = TQuotRef.insert(connect, _author, _author_wikilink, _title, _title_wikilink,
                                        _publisher, _source);
        

        String _text = "test_The sentece of quotation.";
        TQuote result = TQuote.insert(connect, _meaning, _lang, _text, quot_ref);
        assertNotNull(result);


        // delete all temp records
        TQuotAuthor a = quot_ref.getAuthor();
        a.delete(connect);
        quot_ref.delete(connect);

        result.delete(connect);
    }

}