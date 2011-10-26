/* TranslationTableAll.java - translations' statistics in the database of the parsed Wiktionary.
 *
 * Copyright (c) 2009-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikt.stat;

import wikt.stat.printer.general;
import wikipedia.language.LanguageType;
import wikipedia.language.Encodings;

import wikipedia.sql.*;
import wikt.sql.*;

import java.sql.*;

import java.util.Map;
import java.util.HashMap;

/** Translations' statistics in the database of the parsed Wiktionary.
 */
public class TranslationTableAll {
    private static final boolean DEBUG = true;

    // Native language is a language of Wiktionary edition, e.g. Russian in Russian Wiktionary.

    // 1. number of words in native language which have at least one translation into any language
    // todo

    // 2. number of words (in native) with translations into foreign (for each foreign language)
    // todo

    // 3. number of meanings of words (in native language) with translations into foreign (for each foreign language)
    // TranslationBox, see countTranslationPerLanguage()

    // 4. number of total translations for each meaning (of word in native) into any language
    // TranslationBox and Translation entry

    // 5. number of word/phrase pairs, e.g. word in native -> in foreign (and for each language)
    // tanslation_entry table

    // 6. average number of translation languages (for words which have at least one translation).


    /** Counts number of translations of native word's meaning into each 
     * foreign language by selecting all records from the table 'translation'
     * from the database of the parsed Wiktionary.<br><br>
     * SELECT * FROM translation;
     *
     * @param connect   connection to the database of the parsed Wiktionary
     * @return map      from the language into a number of translation boxes
     *                  which contain synonyms, antonyms, etc. in English (etc.)
     */
    public static Map<LanguageType, Integer> countTranslationPerLanguage (Connect wikt_parsed_conn) {
        // translation -> lang -> count

        Statement   s = null;
        ResultSet   rs= null;
        long    t_start;

        int n_unknown_lang_pos = 0; // translations into unknown languages

        int n_total = Statistics.Count(wikt_parsed_conn, "translation");
        //System.out.println("Total translation boxes (translated meanings of words): " + n_total);
        t_start = System.currentTimeMillis();

        Map<LanguageType, Integer> m_lang_n = new HashMap<LanguageType, Integer>();
        LanguageType native_lang = wikt_parsed_conn.getNativeLanguage();

        try {
            s = wikt_parsed_conn.conn.createStatement ();
            StringBuilder str_sql = new StringBuilder();
                         // SELECT id,lang_pos_id,meaning_summary,meaning_id FROM translation
            str_sql.append("SELECT id,lang_pos_id,meaning_summary FROM translation");
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            int n_cur = 0;
            while (rs.next ())
            {
                n_cur ++;
                int            id =                                     rs.getInt("id");
                TLangPOS lang_pos = TLangPOS.getByID(wikt_parsed_conn,  rs.getInt("lang_pos_id"));
                String meaning_summary = Encodings.bytesToUTF8(rs.getBytes("meaning_summary"));

                TLang tlang = lang_pos.getLang();
                if(null != tlang && native_lang != tlang.getLanguage()) {
                    System.err.print("Error (TranslationTableAll.countTranslationPerLanguage()): There is a translation box from a foreign language, code=" + tlang.getLanguage().getCode());
                    TPage p = lang_pos.getPage();
                    if(null != p)
                        System.err.println(", page_title=" + p.getPageTitle());
                }

                if(null != lang_pos) {
                    TTranslation trans = new TTranslation(id, lang_pos, meaning_summary, null); // meaning = null

                    TTranslationEntry[] t_entries =
                        TTranslationEntry.getByTranslation(wikt_parsed_conn, trans);

                    for(TTranslationEntry entry : t_entries) {
                        LanguageType lang = entry.getLang().getLanguage();
                        if(m_lang_n.containsKey(lang) ) {
                            int n = m_lang_n.get(lang);
                            m_lang_n.put(lang, n + 1);
                        } else
                            m_lang_n.put(lang, 1);
                    }

                    if(DEBUG && 0 == n_cur % 1000) {   // % 100
                        //if(n_cur > 333)
                          //  break;
                        long    t_cur, t_remain;

                        t_cur  = System.currentTimeMillis() - t_start;
                        t_remain = (long)((n_total - n_cur) * t_cur/(60f*1000f*(float)(n_cur)));
                                   // where time for 1 page = t_cur / n_cur
                                   // in min, since /(60*1000)
                        t_cur = (long)(t_cur/(60f*1000f));
                        //t_cur = t_cur/(60f*1000f));

                        TPage tpage = lang_pos.getPage();
                        if(null != tpage) {
                            System.out.println(n_cur + ": " + tpage.getPageTitle() +
                                ", duration: "  + t_cur +   // t_cur/(60f*1000f) +
                                " min, remain: " + t_remain +
                                " min");
                        }
                    }
                } else
                    n_unknown_lang_pos ++;
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TranslationTableAll.countTranslationPerLanguage()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        //long  t_end;
        //float   t_work;
        //t_end  = System.currentTimeMillis();
        //t_work = (t_end - t_start)/1000f; // in sec
        System.out.println(//"\nTime sec:" + t_work +
            "\nTotal translation boxes (translated meanings of words): " + n_total +
            "\n\nUnknown<ref>'''Unknown''' - words which have translations but have unknown language code and POS</ref>: "
            + n_unknown_lang_pos);
        
        return m_lang_n;
    }

    public static void main(String[] args) {

        // Connect to wikt_parsed database
        Connect wikt_parsed_conn = new Connect();

        // Russian
        //LanguageType native_lang = LanguageType.ru;
        //wikt_parsed_conn.Open(Connect.RUWIKT_HOST, Connect.RUWIKT_PARSED_DB, Connect.RUWIKT_USER, Connect.RUWIKT_PASS, LanguageType.ru);

        // English
        LanguageType native_lang = LanguageType.en;
        wikt_parsed_conn.Open(Connect.ENWIKT_HOST, Connect.ENWIKT_PARSED_DB, Connect.ENWIKT_USER, Connect.ENWIKT_PASS, LanguageType.en);

        TLang.createFastMaps(wikt_parsed_conn);
        TPOS.createFastMaps(wikt_parsed_conn);
        //TRelationType.createFastMaps(wikt_parsed_conn);

        String db_name = wikt_parsed_conn.getDBName();
        System.out.println("\n== Statistics of translations in the Wiktionary parsed database ==");
        general.printHeader (db_name);

        Map<LanguageType, Integer> m = TranslationTableAll.countTranslationPerLanguage(wikt_parsed_conn);
        wikt_parsed_conn.Close();

        System.out.println();
        int total_trans = general.printSomethingPerLanguage(native_lang, m);
        System.out.println("Total translations: " + total_trans);

        System.out.println("\nThere are translations into " + m.size() + " languages.");
        general.printFooter();
    }

}
