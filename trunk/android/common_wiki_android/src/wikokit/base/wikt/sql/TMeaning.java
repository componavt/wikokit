/* TMeaning.java - SQL operations with the table 'meaning' in SQLite Android 
 * Wiktionary parsed database.
 *
 * Copyright (c) 2009-2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.sql;

import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.sql.quote.TQuote;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/** An operations with the table 'meaning' in MySQL wiktionary_parsed database.
 *
 * @see wikt.word.WPOS
 */
public class TMeaning {

    /** Unique identifier in the table 'meaning'. */
    private int id;

    
    /** Link to the table 'lang_pos', which defines language and POS.
     * If lang_pos != null, then lang_pos_id is not used; lazy DB access.
     */
    private TLangPOS lang_pos;          // int lang_pos_id;

    /** Link to the table 'lang_pos', which defines language and POS. 
     * If lang_pos != null, then lang_pos_id is not used.
     */
    private int lang_pos_id;
    

    /** Meaning (sense) number. */
    private int meaning_n;
    
    
    /** Wikified text describing this meaning.
     * If wiki_text != null, then wiki_text_id is not used; lazy DB access.
     */
    private TWikiText wiki_text;        // int wiki_text_id

    /** ID of wikified text in a table 'wiki_text.
     * If wiki_text != null, then wiki_text_id is not used; lazy DB access.
     */
    private int wiki_text_id;

    /** Quotations illustrate the meaning. */
    private TQuote[] quotation;

    /** Semantic relations: synonymy, antonymy, etc.
     * The map from semantic relation (e.g. synonymy) to array of WRelation
     * (one WRelation contains a list of synonyms for one meaning).
     */
    private Map<Relation, TRelation[]> relation;

    /** Translation */
    private TTranslation translation;


    private final static Map<Relation, TRelation[]> NULL_MAP_RELATION_TRELATION_ARRAY = new HashMap<Relation, TRelation[]>();
    private final static TMeaning[] NULL_TMEANING_ARRAY = new TMeaning[0];
    private final static TRelation[] NULL_TRELATION_ARRAY = new TRelation[0];

    /** Constructor.
     * @param _id
     * @param _lang_pos
     * @param _lang_pos_id
     * @param _meaning_n
     * @param _wiki_text
     * TLangPOS _lang_pos or int _lang_pos_id should be valid,
     * if lang_pos != null, then _lang_pos_id is not used.
     */
    public TMeaning(int _id,TLangPOS _lang_pos,int _lang_pos_id,
                    int _meaning_n,
                    TWikiText _wiki_text,int _wiki_text_id) {
        id          = _id;
        lang_pos    = _lang_pos;
        lang_pos_id = _lang_pos_id;
        meaning_n   = _meaning_n;
        wiki_text   = _wiki_text;
        wiki_text_id= _wiki_text_id;
        relation    = NULL_MAP_RELATION_TRELATION_ARRAY;
    }

    /** Gets unique ID from database */
    public int getID() {
        return id;
    }

    /** Gets meaning (sense) number from database */
    public int getMeaningNumber() {
        return meaning_n;
    }

    /** Gets language and POS ID (for this meaning) from the database' table 'lang_pos'. */
    public TLangPOS getLangPOS(SQLiteDatabase db) {

        if(null != lang_pos)
            return lang_pos;

        lang_pos = TLangPOS.getByID(db, lang_pos_id);  // lazy DB access
        return lang_pos;
    }

    /** Gets text object describing this meaning (without wikification) */
    public TWikiText getWikiText() {
        return wiki_text;
    }
    
    /** Gets text describing this meaning (without wikification) or empty String "". */
    public String getWikiTextString() {
        if(null == wiki_text)
            return "";
        return wiki_text.getText();
    }

    /** Gets relation map */
    public Map<Relation, TRelation[]> getRelation() {
        return relation;
    }
    
    /** Inserts record into the table 'meaning'.<br><br>
     * INSERT INTO meaning (lang_pos_id,meaning_n,wiki_text_id) VALUES (1,2,3);
     * @param lang_pos  ID of language and POS of wiki page which will be added
     * @param meaning_n meaning (sense) number
     * @param wiki_text wikified text (definitions), it could be null (since an article can contain synonyms without a definition of meaning)
     * @return inserted record, or null if insertion failed
     */
    /*public static TMeaning insert (Connect connect,TLangPOS lang_pos,
            int meaning_n, TWikiText wiki_text) {

        if(null == lang_pos) {
            System.err.println("Error (wikt_parsed TMeaning.insert()):: null argument lang_pos");
            return null;
        }

        StringBuilder str_sql = new StringBuilder();
        TMeaning meaning = null;
        int wiki_text_id = 0;
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                if(null != wiki_text)
                    str_sql.append("INSERT INTO meaning (lang_pos_id,meaning_n,wiki_text_id) VALUES (");
                else
                    str_sql.append("INSERT INTO meaning (lang_pos_id,meaning_n) VALUES (");
                str_sql.append(lang_pos.getID());
                str_sql.append(",");
                str_sql.append(meaning_n);
                if(null != wiki_text)
                {
                    str_sql.append(",");
                    str_sql.append(wiki_text.getID());
                    wiki_text_id = wiki_text.getID();
                }
                str_sql.append(")");
                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }

            try {
                s = connect.conn.createStatement ();
                ResultSet rs = s.executeQuery ("SELECT LAST_INSERT_ID() as id");
                try {
                    if (rs.next ()) {
                        meaning = new TMeaning(rs.getInt("id"), lang_pos, lang_pos.getID(),
                                                meaning_n, wiki_text, wiki_text_id);
                        //System.out.println("TMeaning.insert()):: wiki_text='" + wiki_text.getText() + "'; meaning_n=" + meaning_n);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TMeaning.java insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return meaning;
    }*/

    /** Selects rows from the table 'meaning' by the lang_pos_id.
     * SELECT id,meaning_n,wiki_text_id FROM meaning WHERE lang_pos_id=1;
     * @return empty array if data is absent
     */
    public static TMeaning[] get (SQLiteDatabase db,TLangPOS _lang_pos) {

        if(null == _lang_pos) {
            System.err.println("Error (TMeaning.get()):: null argument lang_pos");
            return null;
        }
        List<TMeaning> list_meaning = null;
        
        // SELECT id,meaning_n,wiki_text_id FROM meaning WHERE lang_pos_id=1
        Cursor c = db.query("meaning", 
                new String[] { "id", "meaning_n", "wiki_text_id"}, 
                "lang_pos_id=" + _lang_pos.getID(), 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            do {
                int i_id = c.getColumnIndexOrThrow("id");
                int i_meaning_n = c.getColumnIndexOrThrow("meaning_n");
                int i_wiki_text_id = c.getColumnIndexOrThrow("wiki_text_id");
                
                int  _id            = c.getInt(i_id);
                int  _meaning_n     = c.getInt(i_meaning_n);
                int  wiki_text_id   = c.getInt(i_wiki_text_id);
                TWikiText _wiki_text = wiki_text_id < 1 ? null : TWikiText.getByID(db, wiki_text_id);
                
                if(null == list_meaning)
                           list_meaning = new ArrayList<TMeaning>();
                list_meaning.add(new TMeaning(_id, _lang_pos, _lang_pos.getID(),
                                              _meaning_n, _wiki_text, wiki_text_id));    
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        if(null == list_meaning)
            return NULL_TMEANING_ARRAY;
        return ((TMeaning[])list_meaning.toArray(NULL_TMEANING_ARRAY));
    }

    /** Filters the array 'rels' of semantic relations only for one kind of
     * semantic relations (e.g. synonyms) defined by the variable 'r_type'.
     * @param rels      array of relations
     * @param r_type    the relation type we are interested in, e.g. synonymy
     * @return an empty array if relations are absent
     */
    private void addOneKindOfRelation (Relation r_type, TRelation[] rels) {

        // 1. counts number of relations of this type
        int c = 0;
        for(TRelation r : rels) {
            if(r.getRelationType() == r_type)
                c ++;
        }
        if(0 == c)
            return;

        // 2. gets these relations
        TRelation[] result = new TRelation[c];
        
        c = 0;
        for(TRelation r : rels) {
            if(r.getRelationType() == r_type)
                result [c ++] = r;
        }

        if(0 == relation.size())
            relation = new HashMap<Relation, TRelation[]>();
        relation.put(r_type, result);
        //System.out.println("r_type="+r_type.toString()+"; +1 where TRelation[] result.size="+result.length);
    }

    /** Fills the relations field. */
    public void fillRelation (SQLiteDatabase db) {

        TRelation[] rels = TRelation.get(db, this);

        // let's convert the flat array: TRelation[] rels
        // into the map: Map<Relation, TRelation[]> relation;
        
        addOneKindOfRelation (Relation.synonymy, rels);
        addOneKindOfRelation (Relation.antonymy, rels);
        addOneKindOfRelation (Relation.hypernymy, rels);
        addOneKindOfRelation (Relation.hyponymy, rels);
        addOneKindOfRelation (Relation.holonymy, rels);
        addOneKindOfRelation (Relation.meronymy, rels);        
    }
    
    /** Fills the translation field.
     *
     * Selects rows from the table 'translation' by the meaning_id,
     * fills (recursively) all fields translation_entry.
     */
    public void fillTranslation (SQLiteDatabase db) {

        translation = TTranslation.getByMeaning(db, this);

        if(null == translation)
            return;

        translation.getRecursive(db);        
    }

    /** Selects rows from the table 'meaning' by the lang_pos_id.
     * fills (recursively) relations.
     *
     * @return empty array if data is absent
     */
    public static TMeaning[] getRecursive (SQLiteDatabase db,TLangPOS lang_pos) {
        
        TMeaning[] mm = TMeaning.get(db, lang_pos);
        for(TMeaning m : mm) {
            m.fillRelation(db);
            m.fillTranslation(db);
        }
        return mm;
    }

    /** Selects rows from the table 'meaning' by ID<br><br>
     * SELECT lang_pos_id,meaning_n,wiki_text_id FROM meaning WHERE id=1;
     * @return empty array if data is absent
     */
    public static TMeaning getByID (SQLiteDatabase db,int _id) {
        
        if(_id < 0) {
            System.err.println("Error (TMeaning.getByID()):: ID is negative.");
            return null;
        }
        TMeaning meaning = null;
        
        // SELECT lang_pos_id,meaning_n,wiki_text_id FROM meaning WHERE id=1;
        Cursor c = db.query("meaning", 
                new String[] { "lang_pos_id", "meaning_n", "wiki_text_id"}, 
                "id=" + _id, 
                null, null, null, null);
        
        if (c.moveToFirst()) {
            int i_lang_pos_id = c.getColumnIndexOrThrow("lang_pos_id");
            int i_meaning_n = c.getColumnIndexOrThrow("meaning_n");
            int i_wiki_text_id = c.getColumnIndexOrThrow("wiki_text_id");

            int  lang_pos_id    = c.getInt(i_lang_pos_id);
            TLangPOS _lang_pos = TLangPOS.getByID(db, lang_pos_id);
            if(null != _lang_pos) {
                int  _meaning_n     = c.getInt(i_meaning_n);
                int  wiki_text_id   = c.getInt(i_wiki_text_id);
                TWikiText _wiki_text = wiki_text_id < 1 ? null : TWikiText.getByID(db, wiki_text_id);
    
                meaning = new TMeaning(_id, _lang_pos, lang_pos_id,
                                _meaning_n, _wiki_text, wiki_text_id);
            }
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return meaning;
    }

    /** Deletes row from the table 'meaning' by a value of ID.<br>
     *  DELETE FROM meaning WHERE id=1;
     * @param  id  unique ID in the table `meaning`
     */
    /*public static void delete (Connect connect,TMeaning meaning) {

        if(null == meaning) {
            System.err.println("Error (wikt_parsed TMeaning.delete()):: null argument meaning");
            return;
        }
        StringBuilder str_sql = new StringBuilder();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("DELETE FROM meaning WHERE id=");
                str_sql.append(meaning.getID());
                s.execute (str_sql.toString());
                //System.out.println("TMeaning.delete()):: wiki_text='" + meaning.getWikiText().getText() +
                //        "'; meaning_n=" + meaning.getMeaningNumber());
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TMeaning.delete()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }*/


    /** Checks whether the article 'page_title' has at least one translatio
     * into the destination (target) language from the array 'trans_lang'.
     * The fields 'lang_pos', 'lang_pos.translation' are scanned here.
     */
    public boolean hasTranslation(TLang trans_lang[]) {

        if(null == translation)
            return false;
        
        TTranslationEntry[] trans_entries = translation.getTranslationEntry();

        for(TTranslationEntry entry: trans_entries) {
            for(TLang lang : trans_lang) {
                if(lang == entry.getLang())
                    return true;
            }
        }     
        return false;
    }
}
