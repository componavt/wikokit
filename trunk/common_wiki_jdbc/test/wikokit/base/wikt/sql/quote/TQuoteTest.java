
package wikokit.base.wikt.sql.quote;

import wikokit.base.wikt.sql.quote.TQuotAuthor;
import wikokit.base.wikt.sql.quote.TQuotYear;
import wikokit.base.wikt.sql.quote.TQuotTranscription;
import wikokit.base.wikt.sql.quote.TQuotTranslation;
import wikokit.base.wikt.sql.quote.TQuotRef;
import wikokit.base.wikt.sql.quote.TQuote;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TMeaning;

public class TQuoteTest {

    public Connect   ruwikt_parsed_conn;

    private static TMeaning _meaning = new TMeaning(77787,null, 21223,
                                        7, // int _meaning_n,
                                        null, 872); // TWikiText _wiki_text,int _wiki_text_id)
    
    //private static TLang _lang = TLang.get(LanguageType.en);
    private static TLang _lang = new TLang(9987,LanguageType.en,0,0);
        
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
        

        String _text = "test_The sentence with quotation.";
        TQuote result = TQuote.insert(connect, _meaning, _lang, _text, quot_ref);
        assertNotNull(result);
        
        //TQuote[] result_copy = TQuote.get(connect, _meaning);
        //assertNotNull(result_copy);
        //assertEquals(1, result_copy.length);

        // delete all temp records
        TQuotAuthor a = quot_ref.getAuthor();
        a.delete(connect);
        quot_ref.delete(connect);

        result.delete(connect);
    }

    @Test
    public void testInsert_withYears_ru() {
        System.out.println("insert_withYears_ru");
        Connect connect = ruwikt_parsed_conn;

        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;
        int _from, _to;
        String page_title = "the test entry";

        _author = "test_И. А. Крылов";
        _author_wikilink = "test_Крылов, Иван Андреевич";
        _title = "";
        _title_wikilink = "";
        _publisher = "";
        _source = "";
        _from = _to = 91931;

        TQuotRef quot_ref = TQuotRef.insertWithYears(connect, page_title,
                                        _author, _author_wikilink, _title, _title_wikilink,
                                        _publisher, _source,
                                        _from, _to);

        String _text = "test_The sentence with quotation.";
        TQuote result = TQuote.insert(connect, _meaning, _lang, _text, quot_ref);
        assertNotNull(result);

        // delete all temp records
        TQuotAuthor a = quot_ref.getAuthor();
        a.delete(connect);
        quot_ref.delete(connect);

        result.delete(connect);
    }

    @Test
    public void testInsertWithReference_getOrInsert_ru() {
        System.out.println("insertWithReferenceYears_getOrInsert_ru");
        Connect connect = ruwikt_parsed_conn;

        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;

        _author = "test_И. А. Крылов";
        _author_wikilink = "test_Крылов, Иван Андреевич";
        _title = "";
        _title_wikilink = "";
        _publisher = "";
        _source = "";

        String _text = "test_The sentence with quotation.";

        TQuote result = TQuote.insertWithReference(connect, _text, _meaning, _lang,
                _author, _author_wikilink, _title, _title_wikilink, _publisher, _source);
        assertNotNull(result);

        // delete all temp records
        TQuotRef quot_ref = result.getReference();
        TQuotAuthor a = quot_ref.getAuthor();
        a.delete(connect);
        quot_ref.delete(connect);

        result.delete(connect);
    }

    @Test
    public void testInsertWithYears_getOrInsert_ru() {
        System.out.println("insert_WithYears_getOrInsert_ru");
        Connect connect = ruwikt_parsed_conn;

        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;
        int _from, _to;
        String page_title = "the test entry";

        _author = "test_И. А. Крылов";
        _author_wikilink = "test_Крылов, Иван Андреевич";
        _title = "";
        _title_wikilink = "";
        _publisher = "";
        _source = "";
        _from = _to = 91931;

        String _text = "test_The sentence with quotation.";

        TQuote result = TQuote.insertWithYears(connect, page_title,
                _text, _meaning, _lang,
                _author, _author_wikilink, _title, _title_wikilink, _publisher, _source,
                _from, _to);
        assertNotNull(result);

        
        TQuotRef quot_ref = result.getReference();
        TQuotYear quot_year = quot_ref.getYear();
        assertNotNull(quot_year);
        TQuotAuthor a = quot_ref.getAuthor();

        // delete all temp records
        quot_year.delete(connect);
        a.delete(connect);
        quot_ref.delete(connect);

        result.delete(connect);
    }

    @Test
    public void testInsert_ru_with_translation_and_empty_transcription() {
        System.out.println("insert_ru_with_translation_and_empty_transcription");
        Connect connect = ruwikt_parsed_conn;

        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;

        _author = "test_The Author";
        _author_wikilink = "test_Authors name in Wikipedia";
        _title = "";
        _title_wikilink = "";
        _publisher = "";
        _source = "";

        String _text = "test_The sentence with quotation.";
        String _translation = "test_The translation of the quotation.";
        String _transcription = "";

        TQuote result = TQuote.insertWithTranslationTranscription(connect, 
                _text, _translation, _transcription,
                _meaning, _lang,
                _author, _author_wikilink, _title, _title_wikilink, _publisher, _source);
        assertNotNull(result);

        String tr = result.getTranslation(connect);
        assertEquals(0, _translation.compareTo(tr));

        String transcription = result.getTransription(connect);
        assertEquals(0, transcription.length());

        TQuotRef quot_ref = result.getReference();
        TQuotAuthor a = quot_ref.getAuthor();

        TQuotTranslation transl = TQuotTranslation.getByID(connect, result.getID());
        TQuotTranscription transcr = TQuotTranscription.getByID(connect, result.getID());
        assertNull(transcr);

        // delete all temp records
        transl.delete(connect);
        a.delete(connect);
        quot_ref.delete(connect);

        result.delete(connect);
    }

    @Test
    public void testinsertWithYearsTranslationTranscription_ru() {
        System.out.println("insert_WithYearsTranslationTranscription_ru");
        Connect connect = ruwikt_parsed_conn;

        String _author, _author_wikilink, _title, _title_wikilink, _publisher, _source;
        int _from, _to;
        String page_title = "the test entry";

        _author = "test_The Author";
        _author_wikilink = "test_Authors name in Wikipedia";
        _title = "";
        _title_wikilink = "";
        _publisher = "";
        _source = "";
        _from = _to = 91931;

        String _text = "test_The sentence with quotation.";
        String _translation = "test_The translation of the quotation.";
        String _transcription = "test_The TRANSCRIPTION of the quotation.";

        TQuote result = TQuote.insertWithYearsTranslationTranscription(connect, page_title,
                _text, _translation, _transcription,
                _meaning, _lang,
                _author, _author_wikilink, _title, _title_wikilink, _publisher, _source,
                _from, _to);
        assertNotNull(result);

        String tr = result.getTranslation(connect);
        assertEquals(0, _translation.compareTo(tr));

        String transcription = result.getTransription(connect);
        assertEquals(0, transcription.compareTo(_transcription));

        TQuotRef quot_ref = result.getReference();
        TQuotAuthor a = quot_ref.getAuthor();
        TQuotYear quot_year = quot_ref.getYear();
        assertNotNull(quot_year);

        TQuotTranslation transl = TQuotTranslation.getByID(connect, result.getID());
        TQuotTranscription transcr = TQuotTranscription.getByID(connect, result.getID());
        assertNotNull(transcr);

        // delete all temp records
        transl.delete(connect);
        transcr.delete(connect);
        a.delete(connect);
        quot_ref.delete(connect);
        quot_year.delete(connect);

        result.delete(connect);
    }

}