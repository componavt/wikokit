/* TLangPOS.java - SQL operations with the table 'lang_pos' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikt.sql;

import wikt.constant.SoftRedirectType;

import wikipedia.language.Encodings;
import wikipedia.sql.PageTableBase;
import wikipedia.sql.Connect;
import java.sql.*;
import wikt.constant.Relation;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
//import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
//import wikipedia.language.LanguageType;


/** An operations with the table 'lang_pos' in MySQL wiktionary_parsed database.
 *
 * @see wikt.word.WPOS
 */
public class TLangPOS {

    /** Unique identifier in the table 'lang_pos'. */
    private int id;

    /** Title of the wiki page, word. */
    private TPage page;                 // int page_id;

    /** Language. */
    private TLang lang;                 // int lang_id

    /** Part of speech. */
    private TPOS pos;                   // int pos_id
    
    /** Etymology number (from 0 till max(ruwikt,now)=7). */
    private int etymology_id;           //private TEtymology etimology;     // int etymology_id
    // see WPOSRu.splitToPOSSections in WPOSRuTest.java

    /** Type of soft redirect (to the page .lemma):
     * 0 - None - it's not a redirect, it is the usual Wiktionary entry
     * 1 - Wordform, soft redirect to lemma, e.g. worked -> work
     *     e.g. worked: "Simple past tense and past participle of [[work]]."
     * 2 - Misspelling, soft redirect to correct spelling,
     *     see template {{misspelling of|}} in enwikt
     *
     * @see TPage.is_redirect - a hard redirect.
     */
    private SoftRedirectType redirect_type;

    /** A lemma of word. It's used when .redirect_type != None */
    private String lemma;

    /** (1) Meaning consists of Definitions + Quotations. */
    private TMeaning[] meaning;
    
    private final static TLangPOS[] NULL_TLANGPOS_ARRAY = new TLangPOS[0];
    private final static TLang   [] NULL_TLANG_ARRAY    = new TLang[0];
    private final static TMeaning[] NULL_TMEANING_ARRAY = new TMeaning[0];
    
    public TLangPOS(int _id,TPage _page,TLang _lang,TPOS _pos,int _etymology_id,String _lemma) {
        id              = _id;
        page            = _page;
        lang            = _lang;
        pos             = _pos;
        etymology_id    = _etymology_id;
        lemma           = _lemma;

        meaning         = NULL_TMEANING_ARRAY;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets page from database */
    public TPage getPage() {
        return page;
    }
    
    /** Gets language from database */
    public TLang getLang() {
        return lang;
    }

    /** Gets part of speech from database. */
    public TPOS getPOS() {
        return pos;
    }

    /** Gets meaning (already loaded from database, @see getRecursive). */
    public TMeaning[] getMeaning() {
        return meaning;
    }

    /** Gets number of meanings (already loaded from database, @see getRecursive). */
    public int countMeanings() {
        return meaning.length;
    }

    /** Gets number of types of semantic relations defined for the meanings, 
     * e.g. only Synonymy means '1', Synonymy + Antonymy = 2, etc.
     *
     * Remark: relations should be already loaded, @see getRecursive. */
    public int countRelationTypes() {
        
        Set<Relation> rel_types = new HashSet<Relation>();

        for(TMeaning m : meaning) {
            Set<Relation> relation = m.getRelation().keySet();
            rel_types.addAll(relation);
        }

        return rel_types.size();
    }
    
    /** Increments the number of semantic relations per types for the meanings,
     * i.e. number of synonyms for all meanings of this words,
     *      number of antonyms for all meanings of this word, etc.
     *
     * Remark: relations should be already loaded, @see getRecursive. */
    public void addNumberOfRelationPerType(Map<Relation, Integer> m_result) {

        for(TMeaning m : meaning) {
            Map<Relation, TRelation[]> m_trel = m.getRelation();

            for(Relation r : m_trel.keySet()) {
                int add = m_trel.get(r).length;

                if(m_result.containsKey(r)) {
                    int old = m_result.get(r);
                    m_result.put(r, old + add);
                } else {
                    m_result.put(r, add);
                }
            }
        }
    }
    
    /** Inserts record into the table 'page'.<br><br>
     * INSERT INTO lang_pos (page_id,lang_id,pos_id,etymology_n,lemma) VALUES (1,2,3,4,"apple");
     * @param TPage     ID of title of wiki page which will be added
     * @param lang      language of a word at a page
     * @param pos       part of speech of a word
     * @param etymology_n enumeration for homographs
     * @param lemma     e.g. "run" for the page_title="running"
     * @return null if data is absent
     */
    public static TLangPOS insert (Connect connect,TPage page,TLang lang,TPOS pos,
            int etymology_n,String lemma) {

        if(null == page || null == lang || null == pos) {
            System.err.println("Error (wikt_parsed TLangPOS.insert()):: null arguments, page="+page+", lang="+lang+", pos="+pos);
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TLangPOS lang_pos = null;

        lang_pos = getUniqueByPagePOSLangEtymology (connect, page, lang, pos, etymology_n);
        if(null != lang_pos) {
            System.out.println("Error (TLangPOS.java insert()):: page_title="+page.getPageTitle()+
                    "; the language header is repeated twice or more!'");
            return lang_pos;
        }

        try
        {
            s = connect.conn.createStatement ();
            str_sql.append("INSERT INTO lang_pos (page_id,lang_id,pos_id,etymology_n,lemma) VALUES (");
            str_sql.append(page.getID());
            str_sql.append(",");
            str_sql.append(lang.getID());
            str_sql.append(",");
            str_sql.append(pos.getID());
            str_sql.append(",");
            str_sql.append(etymology_n);
            if(null != lemma && lemma.length() > 0)
            {
                str_sql.append(",\"");
                String safe_lemma = PageTableBase.convertToSafeStringEncodeToDB(connect, lemma);
                str_sql.append(safe_lemma);
                str_sql.append("\")");
            } else
                str_sql.append(",\"\")");
            s.executeUpdate (str_sql.toString());

            s = connect.conn.createStatement ();
            rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
            if (rs.next ())
                lang_pos = new TLangPOS(rs.getInt("id"), page, lang, pos, etymology_n, lemma);
            
        }catch(SQLException ex) {
            String page_title = page.getPageTitle();
            System.err.println("SQLException (wikt_parsed TLangPOS.java insert()):: page_title="+page_title+
                    "; sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return lang_pos;
    }

    /** Selects rows from the table 'lang_pos' by the page_id 
     *
     * SELECT id,lang_id,pos_id,etymology_n,lemma FROM lang_pos WHERE page_id=562 ORDER BY id;
     *
     * @return empty array if data is absent
     */
    public static TLangPOS[] get (Connect connect,TPage page) {
        // Todo?
        // 
        // (lang_id?, pos_id?) : add selection by: language and POS? :
        // * @param  lang language of Wiktionary article, if lang==null then language are not used in order to filter data
        // * @param  pos part of speech of Wiktionary article, if pos==null then POS are not used in order to filter data
        //public static TLangPOS[] get (Connect connect,TPage page,TLang lang,TPOS pos) {
        //String safe_title = PageTableBase.convertToSafeStringEncodeToDB(connect, page_title);

        if(null == page) {
            System.err.println("Error (wikt_parsed TLangPOS.get()):: null argument: page.");
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        List<TLangPOS> list_lp = null;

        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT id,lang_id,pos_id,etymology_n,lemma FROM lang_pos WHERE page_id=");
            str_sql.append(page.getID());
            str_sql.append(" ORDER BY id");
            rs = s.executeQuery (str_sql.toString());
            while (rs.next ())
            {
                int     id      =                       rs.getInt("id");
                TLang   lang    = TLang.getTLangFast(   rs.getInt("lang_id"));
                TPOS    pos     = TPOS. getTPOSFast (   rs.getInt("pos_id"));
                int etymology_n =                       rs.getInt("etymology_n");
                String lemma    = Encodings.bytesToUTF8(rs.getBytes("lemma"));

                if(null != lang && null != pos) {
                    if(null == list_lp)
                               list_lp = new ArrayList<TLangPOS>();
                    list_lp.add(new TLangPOS(id, page, lang, pos, etymology_n, lemma));
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLangPOS.java get()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        
        if(null == list_lp)
            return NULL_TLANGPOS_ARRAY;
        return ((TLangPOS[])list_lp.toArray(NULL_TLANGPOS_ARRAY));
    }

    /** Selects one (unique) rows from the table 'lang_pos' by the page ID,
     * POS ID, language ID, and etymology number.
     *
     * SELECT id,lemma FROM lang_pos WHERE page_id=1 AND pos_id=20 AND lang_id=390 AND etymology_n=2;
     *
     * @return null if data is absent
     */
    public static TLangPOS getUniqueByPagePOSLangEtymology (Connect connect,
                                TPage page,TLang lang,TPOS pos,int etymology_n)
    {
        if(null == page || null == lang || null == pos) {
            System.err.println("Error (TLangPOS.getUniqueByPagePOSLangEtymology()):: null arguments, page="+page+", lang="+lang+", pos="+pos);
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TLangPOS lang_pos = null;

        try {
            s = connect.conn.createStatement ();
            
            str_sql.append("SELECT id,lemma FROM lang_pos WHERE page_id=");
            str_sql.append(page.getID());
            // 3902
            str_sql.append(" AND pos_id=");
            str_sql.append(pos.getID());

            str_sql.append(" AND lang_id=");
            str_sql.append(lang.getID());

            str_sql.append(" AND etymology_n=");
            str_sql.append(etymology_n);

            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                int     id      =                       rs.getInt("id");
                String lemma    = Encodings.bytesToUTF8(rs.getBytes("lemma"));

                lang_pos = new TLangPOS(id, page, lang, pos, etymology_n, lemma);
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TLangPOS.getUniqueByPagePOSLangEtymology()):: page_title="+page.getPageTitle()+
                    "; sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        return lang_pos;
    }
    
    /** Selects rows from the table 'lang_pos' by the page_id,
     * fills (recursively) meanings, relations, translations.
     *
     * @return empty array if data is absent
     */
    public static TLangPOS[] getRecursive (Connect connect,TPage page) {

        TLangPOS[] lang_pos_all = TLangPOS.get(connect, page);
        for(TLangPOS lang_pos : lang_pos_all) {
            lang_pos.meaning = TMeaning.getRecursive(connect, lang_pos);
        }
        
        return lang_pos_all;
    }


    /** Selects list of languages for the given page.
     *
     * SELECt lang_id FROM lang_pos WHERE page_id=674672 GROUP by lang_id;
     *
     * @return empty array if data is absent
     */
    public static TLang[] getLanguages (Connect connect,TPage page) {
        
        if(null == page) {
            System.err.println("Error (wikt_parsed TLangPOS.get()):: null argument: page.");
            return null;
        }

        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        List<TLang> list_lang = null;

        try {
            s = connect.conn.createStatement ();
                         // SELECt lang_id FROM lang_pos WHERE page_id=674672 GROUP by lang_id
            str_sql.append("SELECt lang_id FROM lang_pos WHERE page_id=");
            str_sql.append(page.getID());
            str_sql.append(" GROUP by lang_id");
            rs = s.executeQuery (str_sql.toString());
            while (rs.next ())
            {
                TLang  l = TLang.getTLangFast(   rs.getInt("lang_id"));
                if(null != l) {
                    if(null == list_lang)
                               list_lang = new ArrayList<TLang>();
                    list_lang.add(l);
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLangPOS.getLanguages()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }

        if(null == list_lang)
            return NULL_TLANG_ARRAY;
        return ((TLang[])list_lang.toArray(NULL_TLANG_ARRAY));
    }


    /** Selects row from the table 'lang_pos' by ID.<br><br>
     * SELECT page_id,lang_id,pos_id,etymology_n,lemma FROM lang_pos WHERE id=8;
     * @return null if data is absent
     */
    public static TLangPOS getByID (Connect connect,int id) {
        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        TLangPOS lang_pos = null;
        
        try {
            s = connect.conn.createStatement ();
            str_sql.append("SELECT page_id,lang_id,pos_id,etymology_n,lemma FROM lang_pos WHERE id=");
            str_sql.append(id);
            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
            {
                TPage   page    = TPage.getByID     (connect, rs.getInt("page_id"));
                TLang   lang    = TLang.getTLangFast(         rs.getInt("lang_id"));
                TPOS    pos     = TPOS. getTPOSFast (         rs.getInt("pos_id"));
                int etymology_n =                             rs.getInt("etymology_n");
                String lemma    = Encodings.bytesToUTF8(      rs.getBytes("lemma"));
                
                if(null != lang && null != pos) {
                    lang_pos = new TLangPOS(id, page, lang, pos, etymology_n, lemma);
                }
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLangPOS.java getByID()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return lang_pos;
    }
    
    /** Deletes all rows from the table 'lang_pos' by page_id.<br><br>
     * DELETE FROM lang_pos WHERE page_id=1;
     * @param  id  unique ID in the table `lang_pos`
     */
    public static void delete (Connect connect,TPage page) {

        if(null == page) {
            System.err.println("Error (wikt_parsed TLangPOS.delete()):: null argument page.");
            return;
        }
        
        Statement   s = null;
        ResultSet   rs= null;
        StringBuilder str_sql = new StringBuilder();
        try {
            s = connect.conn.createStatement ();
            str_sql.append("DELETE FROM lang_pos WHERE page_id=");
            str_sql.append(page.getID());
            s.execute (str_sql.toString());
        } catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLangPOS.java delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
    }
    

    /** Deletes all rows from the table 'lang_pos' by page_id.<br><br>
     * DELETE FROM lang_pos WHERE page_id=1;
     * @param  id  unique ID in the table `lang_pos`
     */
    /*public static boolean intersectionIsNotEmpty (TPage page) {

        return false;
    }*/



}
