/* TTranslation - SQL operations with the table 'translation' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql;

import wikokit.base.wikt.util.WikiText;
import wikokit.base.wikt.word.WTranslation;
import wikokit.base.wikt.word.WTranslationEntry;

import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.language.Encodings;
import wikokit.base.wikipedia.sql.PageTableBase;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.index.IndexForeign;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/** SQL operations with the table 'translation' in Wiktionary parsed database.
 *
 * @see wikt.word.WTranslation
 */
public class TTranslation {

    /** Unique identifier in the table 'translation'. */
    private int id;


    /** Link to the table 'lang_pos', which defines language and POS.
     */
    private TLangPOS lang_pos;          // int lang_pos_id;
    
    /** Translation section (box) title, i.e. additional comment,
     * e.g. "fruit" or "apple tree" for "apple".
     * A summary of the translated meaning.
     */
    private String meaning_summary;

    /** Meaning (corresponds to meaning.meaning_n sense number).
     * It could be null.
     * It can point to a wrong meaning,
     * if a number of translations is less than a number of translation boxes!
     */
    private TMeaning meaning;           // int meaning_n;

    /** Translations */
    private TTranslationEntry[] entry;

    private final static TTranslation[] NULL_TTRANSLATION_ARRAY = new TTranslation[0];
    private final static TPage[]        NULL_TPAGE_ARRAY        = new TPage[0];
    private final static String[]       NULL_STRING_ARRAY       = new String[0];

    public TTranslation(int _id,TLangPOS _lang_pos,String _meaning_summary,
                    TMeaning _meaning) {
        id          = _id;
        lang_pos    = _lang_pos;
        meaning_summary = _meaning_summary;
        meaning     = _meaning;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }
    
    /** Gets language and POS ID (for this translation) from the database' table 'lang_pos'. */
    public TLangPOS getLangPOS() { //Connect connect) {
        return lang_pos;

        /*if(null != lang_pos)
            return lang_pos;

        lang_pos = TLangPOS.getByID(connect, lang_pos_id);  // lazy DB access
        return lang_pos;*/
    }
    
    /** Gets a summary of the translated meaning (title of translation box, section). */
    public String getMeaningSummary() {
        return meaning_summary;
    }

    /** Gets a meaning from the database' table 'meaning'. */
    public TMeaning getMeaning() {
        return meaning;
    }
    
    /** Gets translations. */
    public TTranslationEntry[] getTranslationEntry() {
        return entry;
    }

    /** Inserts records into tables: 'translation' and 'translation_entry'.
     * The insertion into 'translation_entry' results in updating records in tables:
     * 'wiki_text_words', 'page_inflecton', 'inflection', 'page' and 'index_XX'.
     *
     * @param native_lang   native language in the Wiktionary,
     *                       e.g. Russian language in Russian Wiktionary
     * @param page_title    title (in native language) of the article
     * @param tmeaning      corresponding record in table 'meaning' to this translation
     */
    public static void storeToDB (Connect conn,
                                LanguageType native_lang, String page_title,
                                TLangPOS lang_pos,
                                TMeaning tmeaning, WTranslation wtrans) {

        if(null == lang_pos || null == tmeaning || null == wtrans) return;

        WTranslationEntry[] trans_entries = wtrans.getTranslations();
        if(0 == trans_entries.length) return;

        TTranslation trans = TTranslation.insert(conn, lang_pos, wtrans.getHeader(), tmeaning);
        assert(null != trans);

        for(WTranslationEntry wtrans_entry : trans_entries) {

            LanguageType foreign_lang = wtrans_entry.getLanguage();
            TLang tlang = TLang.get(foreign_lang);
            
            WikiText[] phrases = wtrans_entry.getWikiPhrases();
            for(WikiText p : phrases) {
                
                TWikiText twiki_text = TWikiText.storeToDB(conn, p);
                
                if(null != twiki_text) {
                    TTranslationEntry trans_entry = TTranslationEntry.insert(conn, trans, tlang, twiki_text);
                    assert(null != trans_entry);
                    
                    IndexForeign.insertIfAbsent(conn, p.getVisibleText(),
                            false, // todo post-processing to set boolean 'foreign_has_definition'
                            page_title, native_lang, foreign_lang);
                }
            }
        }
    }
    
    /** Gets translations (on the page defined by entry article 'source_page')
     * into given language target_lang,
     * i.e. gets wikified words from a text in the section == Translation ==
     *
     * @param source_lang source language
     * @param target_lang target language
     * @return empty array if data is absent
     */
    public static TPage[] fromPageToTranslations (Connect connect,TLang source_tlang,
                                                  TPage source_page,TLang target_tlang) {
        // Data flow in database tables:
        // page -> lang_pos -> meaning (?)
        // page -> lang_pos -> translation
        //         language -> translation

        List<TPage> list_page = null;
        LanguageType source_lang = source_tlang.getLanguage();

        TLangPOS[] lang_pos_all = TLangPOS.get(connect, source_page);
        for(TLangPOS lang_pos : lang_pos_all) {
            if(source_lang == lang_pos.getLang().getLanguage()) {

                TTranslation[] trans_all = TTranslation.getByLangPOS(connect, lang_pos);
                for(TTranslation trans : trans_all) {
                    TTranslationEntry[] trans_entries = TTranslationEntry.getByLanguageAndTranslation(connect, trans, target_tlang);

                    for(TTranslationEntry trans_entry : trans_entries) {

                        TPage p = TWikiTextWords.getPageForOneWordWikiText(connect, trans_entry.getWikiText());
                        if(null != p) {
                            if(null == list_page)
                               list_page = new ArrayList<TPage>();
                            list_page.add(p);
                        }
                    }
                }
            }
        }
        if(null == list_page)
            return NULL_TPAGE_ARRAY;
        return ((TPage[])list_page.toArray(NULL_TPAGE_ARRAY));
    }

    // todo: correspondance of meaning and translation
    // Function1: get translations for this meaning,
    // and vice versa:
    // Function2: get synonyms for this translation.
    //      meaning
    //          TMeaning[] meaning_all = TMeaning.get(connect, lang_pos);
    //      translation
    //          for(TMeaning meaning : meaning_all) {
    //              TTranslation.
    //          }
    // See: WTMeaning.getDefinitionsByPage()

    
    /** Gets articles which contain the given translation.
     *
     * The sought page (on source_tlang) has translation_page (on target_tlang).
     *
     * E.g. to find articles which contain the translation [[little]].
     * If there are tree pages:
     *      (1) == translation == [[little]] [[bell]]
     *      (2) == translation == [[little bell]]
     *      (3) == translation == [[little]]
     * then page (1) is not suitable, but (2) and (3) are OK.
     *
     * @param source_tlang      language of sought pages (language of page)
     * @param translation_page  given translation (target language)
     * @param target_tlang      language of translations
     * @return empty array if data is absent
     */
    public static TPage[] fromTranslationsToPage (Connect connect,
                                                TLang source_tlang,     // language of sought pages (language of page)
                                                TPage translation_page, // on target language
                                                TLang target_tlang) {   // language of translations
        
        if(null == source_tlang || null == translation_page || null == target_tlang) {
            System.err.println("Error (wikt_parsed TTranslation.fromTranslationsToPage()):: null arguments, source_tlang="+
                    source_tlang+", translation_page="+translation_page+", target_tlang="+target_tlang);
            return NULL_TPAGE_ARRAY;
        }

        // Data flow in database tables:
        // page -> wiki_text_words -> wiki_text -> translation (filter by lang)-> lang_pos -> page
        //                                      -> ? meaning     -> lang_pos -> page

        List<String> slist_page = null; // just for unique TPage, local var
        List<TPage>   list_page = null;
        LanguageType source_lang = source_tlang.getLanguage();
        TWikiText[] one_word_wiki_text = TWikiTextWords.getOneWordWikiTextByPage(connect, translation_page);
        
        for(TWikiText w : one_word_wiki_text) {
            TTranslationEntry[] trans_entries = TTranslationEntry.getByWikiTextAndLanguage(connect, w, target_tlang);

            for(TTranslationEntry e : trans_entries) {
                TTranslation trans = e.getTranslation();

                if(null != trans && null != trans.getLangPOS()) {
                    if(source_lang == trans.getLangPOS().getLang().getLanguage()) {
                        TPage p = trans.getLangPOS().getPage();
                        if(null == p) {
                            System.err.println("Error (wikt_parsed TTranslation.fromTranslationsToPage()):: There is no page with translation (translation_page)="+translation_page.getPageTitle());
                            return NULL_TPAGE_ARRAY;
                        }
                        if(null == list_page) {
                             list_page = new ArrayList<TPage>();
                            slist_page = new ArrayList<String>();
                        }
                        String s = p.getPageTitle();
                        if(!slist_page.contains(s)) {
                            slist_page.add(s);
                            list_page.add(p);
                        }
                    }
                }
            }
        }
        if(null != slist_page) {
            slist_page.clear();
            slist_page = null;
        }
        
        if(null == list_page)
            return NULL_TPAGE_ARRAY;
        return ((TPage[])list_page.toArray(NULL_TPAGE_ARRAY));
    }

    /** Gets articles which contain the given translation.
     *
     * @param source_tlang      language of sought pages (language of page)
     * @param translation_page  given translation (page title in target language)
     * @param target_tlang      language of translations
     * @return empty array if data is absent
     */
    public static String[] fromTranslationsToPage (Connect connect,
                                                LanguageType source_lang,   // language of sought pages (language of page)
                                                String translation_page,    // on target language
                                                LanguageType target_lang) { // language of translations

        TPage page = TPage.get(connect, translation_page);
        if(null == page) {
            System.err.println("Error (TTranslation.fromTranslationsToPage()):: null argument page");
            return NULL_STRING_ARRAY;
        }
        
        TLang source_tlang = TLang.get(source_lang);
        TLang target_tlang = TLang.get(target_lang);
        TPage[] source_pages = TTranslation.fromTranslationsToPage(connect, source_tlang, page, target_tlang);
        if(0 == source_pages.length)
            return NULL_STRING_ARRAY;

        String[] s_pages = new String[source_pages.length];
        for(int i=0; i < source_pages.length; i++)
            s_pages [i] = source_pages [i].getPageTitle();

        return s_pages;
    }


    /** Inserts record into the table 'meaning'.<br><br>
     * INSERT INTO translation (lang_pos_id,meaning_summary,meaning_id) VALUES (1,'hello',3);
     * @param lang_pos  ID of language and POS of wiki page which will be added
     * @param meaning_id defines meaning (sense) in table 'meaning', it could be null (todo check)
     * @param meaning_summary
     * @return inserted record, or null if insertion failed
     */
    public static TTranslation insert (Connect connect,TLangPOS lang_pos,
            String meaning_summary,TMeaning meaning) {

        if(null == lang_pos) {
            System.err.println("Error (wikt_parsed TTranslation.insert()):: null argument lang_pos");
            return null;
        }

        if(null == meaning_summary)
                   meaning_summary = "";

        StringBuilder str_sql = new StringBuilder();
        TTranslation trans = null;
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                if(null != meaning)
                    str_sql.append("INSERT INTO translation (lang_pos_id,meaning_summary,meaning_id) VALUES (");
                else
                    str_sql.append("INSERT INTO translation (lang_pos_id,meaning_summary) VALUES (");

                str_sql.append(lang_pos.getID());
                str_sql.append(",\"");
                str_sql.append(PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, meaning_summary));
                str_sql.append("\"");

                if(null != meaning) {
                    str_sql.append("," );
                    str_sql.append(    meaning.getID());
                }

                str_sql.append(")");
                if(s.executeUpdate (str_sql.toString()) > 0) {
                    ResultSet rs = connect.conn.prepareStatement( "SELECT LAST_INSERT_ID() AS id" ).executeQuery();
                    try {
                        if (rs.next ()) {
                            trans = new TTranslation(rs.getInt("id"), lang_pos, meaning_summary, meaning);
                            //System.out.println("TTranslation.insert()):: summary='" + meaning_summary +
                            //        "'; id=" + rs.getInt("id") + "; lang='" + lang_pos.getLang().getLanguage().getName()+ "'");
                        }
                    } finally {
                        rs.close();
                    }
                }
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (TTranslation.insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return trans;
    }

    /** Selects rows from the table 'translation' by ID.<br><br>
     * SELECT lang_pos_id,meaning_summary,meaning_id FROM translation WHERE id=1;
     * @return empty array if data is absent
     */
    public static TTranslation getByID (Connect connect,int id) {
        
        StringBuilder str_sql = new StringBuilder();
        TTranslation trans = null;
        
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT lang_pos_id,meaning_summary,meaning_id FROM translation WHERE id=");
                str_sql.append(id);
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    if (rs.next ())
                    {
                        TLangPOS lang_pos = TLangPOS.getByID(connect,   rs.getInt("lang_pos_id"));
                        String meaning_summary = Encodings.bytesToUTF8(rs.getBytes("meaning_summary"));

                        int meaning_id = rs.getInt("meaning_id");
                        TMeaning meaning = meaning_id < 1 ? null : TMeaning.getByID(connect, meaning_id);
                        if(null != lang_pos)
                            trans = new TTranslation(id, lang_pos, meaning_summary, meaning);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TMeaning.java getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return trans;
    }

    /** Selects rows from the table 'translation' by ID.<br><br>
     * SELECT id,meaning_summary,meaning_id FROM translation WHERE lang_pos_id=1;
     * @return empty array if data is absent
     */
    public static TTranslation[] getByLangPOS (Connect connect,TLangPOS lang_pos) {

        if(null == lang_pos) {
            System.err.println("Error (wikt_parsed TTranslation.getByLangPOS()):: null arguments lang_pos");
            return NULL_TTRANSLATION_ARRAY;
        }

        StringBuilder str_sql = new StringBuilder();
        List<TTranslation> list_trans = null;

        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT id,meaning_summary,meaning_id FROM translation WHERE lang_pos_id=");
                str_sql.append(lang_pos.getID());
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    while (rs.next ())
                    {
                        int id = rs.getInt("id");
                        String meaning_summary = Encodings.bytesToUTF8(rs.getBytes("meaning_summary"));

                        int meaning_id = rs.getInt("meaning_id");
                        TMeaning meaning = meaning_id < 1 ? null : TMeaning.getByID(connect, meaning_id);

                        if(null == list_trans)
                            list_trans = new ArrayList<TTranslation>();
                        list_trans.add(new TTranslation(id, lang_pos, meaning_summary, meaning));

                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TTranslation.getByLangPOS()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        if(null == list_trans)
            return NULL_TTRANSLATION_ARRAY;
        return (TTranslation [])list_trans.toArray(NULL_TTRANSLATION_ARRAY);
    }

    /** Selects rows from the table 'translation' by meaning ID.<br><br>
     *
     * SELECT id,meaning_summary FROM translation WHERE meaning_id=1;
     *
     * @return null if data is absent
     */
    public static TTranslation getByMeaning(Connect connect,TMeaning meaning) {

        if(null == meaning) {
            System.err.println("Error (wikt_parsed TTranslation.getByMeaning()):: null argument meaning");
            return null;
        }
        StringBuilder str_sql = new StringBuilder();
        TTranslation ttrans = null;
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT id,meaning_summary FROM translation WHERE meaning_id=");
                str_sql.append(meaning.getID());
                ResultSet rs = s.executeQuery (str_sql.toString());
                try {
                    while (rs.next ())
                    {
                        int id = rs.getInt("id");
                        String meaning_summary = Encodings.bytesToUTF8(rs.getBytes("meaning_summary"));

                        //int meaning_id = rs.getInt("meaning_id");
                        //TMeaning meaning = meaning_id < 1 ? null : TMeaning.getByID(connect, meaning_id);

                        ttrans = new TTranslation(id, meaning.getLangPOS(connect), meaning_summary, meaning);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TTranslation.getByMeaning()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return ttrans;
    }

    /** Deletes row from the table 'translation' and deletes related rows
     * from the table 'translation_entry'.
     */
    public static void deleteWithEntries (Connect connect,TTranslation trans) {

        if(null == trans) {
            System.err.println("Error (wikt_parsed TTranslation.deleteWithEntries()):: null argument 'translation'");
            return;
        }
        
        TTranslationEntry[] ee = TTranslationEntry.getByTranslation(connect, trans);
        //ee = TTranslationEntry.getByLanguageAndTranslation(conn, t, lp.getLang());
        for(TTranslationEntry e : ee)
            TTranslationEntry.delete(connect, e);
        TTranslation.delete(connect, trans);
    }

    /** Deletes row from the table 'translation' by a value of ID.<br>
     *  DELETE FROM translation WHERE id=1;
     * @param  id  unique ID in the table `translation`
     */
    public static void delete (Connect connect,TTranslation trans) {

        if(null == trans) {
            System.err.println("Error (wikt_parsed TTranslation.delete()):: null argument 'translation'");
            return;
        }
        StringBuilder str_sql = new StringBuilder();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("DELETE FROM translation WHERE id=");
                str_sql.append(trans.getID());
                s.execute (str_sql.toString());
                //System.out.println("TTranslation.delete()):: summary='" + trans.getMeaningSummary() +
                //        "'; id=" + trans.getID());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TTranslation.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }

    /** Fills (recursively) all fields translation_entry. */
    public void getRecursive (Connect connect) {
        
        entry = TTranslationEntry.getByTranslation(connect, this);
    }

}
