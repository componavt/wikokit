/* TLabel.java - SQL operations with the table 'label' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.sql.label;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import wikokit.base.wikipedia.language.Encodings;
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.PageTableBase;
import wikokit.base.wikipedia.sql.Statistics;
import wikokit.base.wikipedia.sql.UtilSQL;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelCategory;
import wikokit.base.wikt.multi.en.name.LabelEn;
import wikokit.base.wikt.multi.ru.name.LabelRu;
import wikokit.base.wikt.sql.TLang;
import wikokit.base.wikt.sql.TMeaning;

/** An operations with the table 'label' (context labels) in MySQL Wiktionary_parsed database.
 * 
 * Attention: LabelEn used instead of Label, because there is need in LabelCategory
 * (category_id in the table label).
 */
public class TLabel {
    
    /** Unique identifier in the table 'label'. */
    private int id;
    
    /** Context label short name. */
    private String short_name;
    
    /** Context label full name. */
    private String name;
    
    /** Category of context label (category_id). NULL means that label_category is unknown. 
     * 
     * A] The label was gathered automatically by parser if:     * 
     * (1) category_id is NULL or (2) category_id = "regional automatic".
     * 
     * B] The label was added manually to the code of parser if 
     * (1) category_id is not NULL and (2) category_id != "regional automatic".
     */
    private TLabelCategory label_category;
    
    /** Number of definitions with this context label. */
    private int counter;
    
    /** Map from label to ID.*/
    private static Map<Label, Integer> label2id;
    
    /** Map from ID to label.*/
    private static Map<Integer, Label> id2label;
    
    
    /** Gets the map from label to ID (ID in the table 'label').
     *
     * REM: the functions createFastMaps() should be run at least once,
     * before this function execution.
     */
    public static Map<Label, Integer> getAllLabels2ID() {
        return label2id;
    }
    
    /** Gets the map from label ID (ID in the table 'label') to label.
     *
     * REM: the functions createFastMaps() should be run at least once,
     * before this function execution.
     */
    public static Map<Integer, Label> getAllID2Labels() {
        return id2label;
    }
    
    /** Gets label by ID from the table 'label'.<br><br>
     * REM: createFastMaps() should be run at least once, before this function execution.
     */
    public static Label getLabelFast(int id) {
        if(null == id2label) {
            System.out.println("Error (wikt_parsed TLabel.getTLabelFast()):: What about calling 'createFastMaps()' before?");
            return null;
        }
        if(id <= 0) {
            System.out.println("Error (wikt_parsed TLabel.getTLabelFast()):: argument id <=0, id = "+id);
            return null;
        }
        return id2label.get(id);
    }
    
    
    /** Gets SQL returning labels found automatically: 
     * (1) category_id is NULL or (2) category_id = "regional automatic".
     */
    private static String getSQLWhereCategoryId_FoundByParser() {
        
        int cat_id = TLabelCategory.getIDFast(LabelCategory.regional_automatic);
        return "WHERE category_id is NULL OR category_id="+cat_id;
    }
    
    private static String getSQLWhereCategoryId_AddedByHand() {
        
        int cat_id = TLabelCategory.getIDFast(LabelCategory.regional_automatic);
        return "WHERE (category_id IS NOT NULL) AND (category_id <> "+cat_id+")";
    }
    
    /** Gets all labels from the table 'label' of database, 
     * which were found automatically: (1) category_id is NULL or (2) category_id = "regional automatic".
     */
    public static Collection<Label> getLabelsFoundByParserFromDatabase(Connect wikt_parsed_conn, 
                                                    LanguageType native_lang) {
        
        Statement   s = null;
        ResultSet   rs= null;
        Collection<Label> result = new ArrayList<>();
        
        try {
            s = wikt_parsed_conn.conn.createStatement();
            StringBuilder str_sql = new StringBuilder();
            
            str_sql.append("SELECT short_name, category_id FROM label ");
            str_sql.append( TLabel.getSQLWhereCategoryId_FoundByParser() );
            
            s.executeQuery (str_sql.toString());
            rs = s.getResultSet ();
            while (rs.next ())
            {
                String short_name = Encodings.bytesToUTF8(rs.getBytes("short_name"));
                LabelCategory _lc = TLabelCategory.getLabelCategoryFast( rs.getInt("category_id") );
                
                Label _label = null;

                LanguageType l = native_lang;
                if(l == LanguageType.ru) {
                    _label = new LabelRu(short_name);
                    _label.setCategory(_lc);
                } else if(l == LanguageType.en) {
                    _label = new LabelEn(short_name, _lc);
                }
                
                /*if(null == _lc) {
                    _label = new LabelRu(short_name);
                } else {
                    _label = new LabelEn(short_name, _lc);
                }*/
                
                result.add(_label);
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (TLabel.getLabelsFoundByParserFromDatabase()): " + ex.getMessage());
        } finally {
            if (rs != null) {   try { rs.close(); } catch (SQLException sqlEx) { }  rs = null; }
            if (s != null)  {   try { s.close();  } catch (SQLException sqlEx) { }  s = null;  }
        }
        return result;
    }
    
    /** Read all records from the table 'label',
     * fills the internal map from a table ID to a label.<br><br>
     *
     * REM: during a creation of Wiktionary parsed database
     * the functions recreateTable() should be called (before this function).
     */
    public static void createFastMaps(Connect connect, LanguageType wikt_lang) {

        System.out.println("Loading table `label`...");

        int size = Statistics.Count(connect, "label");
        if(0==size) {
            System.out.println("Error (wikt_parsed TLabel.createFastMaps()):: The table `label` is empty!");
            return;
        }
        
        if(null != label2id && label2id.size() > 0)
            label2id.clear();
        
        if(null != id2label && id2label.size() > 0)
            id2label.clear();
        
        label2id = new LinkedHashMap<>(size);
        id2label = new LinkedHashMap<>(size);
        
        // get labels found by parser
        TLabel.getLabelsFoundByParserFromDatabase(connect, wikt_lang);
        
        // labels found by parser + labels by hand
        Collection<Label> labs = Label.getAllLabels(wikt_lang);
        for(Label label : labs) {
            
            String short_name = label.getShortName();
            int id = getIDByShortName(connect, short_name, "createFastMaps");
            
            if (0 == id) {
                System.out.println("Error (wikt_parsed TLabel.createFastMaps()):: There is label short name='"+short_name+"' which is absent in table 'label'!");
                continue;
            }
        
            label2id.put(label, id);
            id2label.put(id, label);
        }
        
        int labels_in_class = Label.size(wikt_lang);
        if(size != labels_in_class)
            System.out.println("Warning (wikt_parsed TLabel.createFastMaps()):: Label.size (" + labels_in_class
                    + ") is not equal to size of table 'label'("+ size +"). Is the database outdated?");
    }

    /** Deletes all records from the table 'label',
     * loads labels names from LabelXX.java (XX is parameter wikt_lang),
     * sorts by name,
     * fills the table.
     */
    public static void recreateTable(Connect connect, Map<LabelCategory, Integer> category2id, LanguageType wikt_lang) {

        System.out.println("Recreating the table `label`...");
        Map<Integer, Label> _id2label = fillLocalMaps(wikt_lang);
        UtilSQL.deleteAllRecordsResetAutoIncrement(connect, "label");
        fillDB(connect, _id2label, category2id);
        {
            int db_current_size = wikokit.base.wikipedia.sql.Statistics.Count(connect, "label");
            assert(db_current_size == Label.size(wikt_lang)); // ~ ??? labels entered by hand
        }
    }

    /** Load data from a Label class, sorts,
     * and fills the local map 'id2label'. 
     * 
     * @param wikt_lang defines LabelXX.java where XX is ru, en, de, fr...
     */
    public static Map<Integer, Label> fillLocalMaps(LanguageType wikt_lang) {

        int size = Label.size(wikt_lang);
        List<String>list_label = new ArrayList<>(size);
        list_label.addAll(Label.getAllLabelShortNames(wikt_lang));
        Collections.sort(list_label);

        // OK, we have list of labels (short names). Sorted list 'list_label'

        // Local map from id to Label. It is created from data in LabelXX.java or LabelRu.java
        // It is used to fill the table 'label' in right sequence.
        Map<Integer, Label> _id2label = new LinkedHashMap<>(size);
        for(int id=0; id<size; id++) {
            String s = list_label.get(id);    // s - context label name
            assert(Label.hasShortName(s, wikt_lang));                           //System.out.println("fillLocalMaps---id="+id+"; s="+s);
            _id2label.put(id+1, Label.getByShortName(s, wikt_lang)); // ID in MySQL starts from 1
        }
        return _id2label;
    }

    /** Fills database table 'label' by data from LabelXX class. */
    public static void fillDB(  Connect connect, Map<Integer, Label> id2label, 
                                Map<LabelCategory, Integer> category2id)
    {        
        for(int id : id2label.keySet()) {
            Label label = id2label.get(id);
            if(null == label)
                continue;
            
            LabelCategory lc = label.getLinkedLabelEn().getCategory();
            int category_id = category2id.get(lc);
                    
            //               short_name,           name,            category_id, counter (default 0)
            insert (connect, label.getShortName(), label.getName(), category_id);
        }
    }
    
    /** Calculates number of labels in the table 'label_meaning', 
     * stores statistics to the field: 'label.counter'. <br><br>
     *
     * REM: this func should be called after the a creation of Wiktionary
     * parsed database, and the tables should be filled with data.
     *
     * @param native_lang       native language in the Wiktionary,
     *                          e.g. Russian language in Russian Wiktionary
     */
    public static void calcCounterStatistics(Connect connect,
                                            LanguageType native_lang) {

        System.out.println("Fill `label.counter` by statistics from the table 'label_meaning' ...");

        Map<Integer, Label> _id2label = TLabel.getAllID2Labels();
        
        for(int label_id : _id2label.keySet()) {
            
            int counter = TLabelMeaning.countRecordsWithLabelID(connect, label_id); // SELECT COUNT(*) FROM label_meaning WHERE label_id = 3;
            update(connect, label_id, counter);                                     // UPDATE label SET counter=7 WHERE id=3;
        }
    }
    
    /** Counts number of labels added by hand. <br><br>
     *
     * SELECT COUNT(*) FROM label WHERE category_id correspond to labels added by hand;
     * 
     * @return 0 means error
     **/
    public static int countLabelsAddedByHand(Connect connect) {

        Statement s = null;
        ResultSet rs= null;
        int size = 0;
        StringBuilder str_sql = new StringBuilder();

        if(null==connect || null==connect.conn)
            return 0;

        try {
            s = connect.conn.createStatement ();
            
            str_sql.append("SELECT COUNT(*) AS size FROM label ");
            str_sql.append( TLabel.getSQLWhereCategoryId_AddedByHand() );
            
            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
                size = rs.getInt("size");
            
        } catch(SQLException ex) {
            System.out.println("SQLException (TLabel.countLabelsAddedByHand()): sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {
                try { rs.close();
                } catch (SQLException sqlEx) { }
            }
            if (s != null) {
                try { s.close();
                } catch (SQLException sqlEx) { }
            }
        }
        return size;
    }
    
    /** Counts number of labels found by parser (automatically). <br><br>
     *
     * select COUNT(*) FROM label WHERE category_id was found by parser;
     * 
     * @return 0 means error
     **/
    public static int countLabelsFoundByParser(Connect connect) {

        Statement s = null;
        ResultSet rs= null;
        int size = 0;
        StringBuilder str_sql = new StringBuilder();

        if(null==connect || null==connect.conn)
            return 0;

        try {
            s = connect.conn.createStatement ();
            
            str_sql.append("SELECT COUNT(*) AS size FROM label ");
            str_sql.append( TLabel.getSQLWhereCategoryId_FoundByParser() );
            
            rs = s.executeQuery (str_sql.toString());
            if (rs.next ())
                size = rs.getInt("size");
            
        } catch(SQLException ex) {
            System.out.println("SQLException (TLabel.countLabelsAddedByHand()): sql='" + str_sql.toString() + "' " + ex.getMessage());
        } finally {
            if (rs != null) {
                try { rs.close();
                } catch (SQLException sqlEx) { }
            }
            if (s != null) {
                try { s.close();
                } catch (SQLException sqlEx) { }
            }
        }
        return size;
    }
    
    /** Inserts record into the table 'label'.<br><br>
     * INSERT INTO label (short_name, name, category_id) VALUES ("short name", "name", 1);
     * @param short_name    label itself
     * @param name          full name of label
     * @param category_id  ID of label category from the table 'label_category'
     */
    public static int insert (Connect connect,String short_name, String name, int category_id) {

        if(null == short_name || short_name.length() == 0) return 0;
        
        int result_id = 0;
        StringBuilder str_sql = new StringBuilder();
        try
        {
            try (Statement s = connect.conn.createStatement ()) {
                str_sql.append("INSERT INTO label (short_name, name, category_id) VALUES (\"");
                
                String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, short_name);
                str_sql.append(safe_title);
                str_sql.append("\",\"");
                
                safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, name);
                str_sql.append(safe_title);
                str_sql.append("\",");
                str_sql.append(category_id);
                str_sql.append(")");

                s.executeUpdate (str_sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = s.getGeneratedKeys();
                if (rs.next()){
                    result_id = rs.getInt(1);
                }
            }
        }catch(SQLException ex) {
            System.out.println("SQLException (wikt_parsed TLabel.insert(with category_id)):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result_id;
    }
    
    /** Inserts record into the table 'label', gets last inserted ID.<br><br>
     * INSERT INTO label (short_name, name) VALUES ("short name", "name");
     * @param short_name    label itself
     * @param name          full name of label
     * 
     * @return last inserted ID, 0 means error
     */
    public static int insert (Connect connect,String short_name, String name) {

        if(null == short_name || short_name.length() == 0) return 0;
        
        int result_id = 0;
        StringBuilder str_sql = new StringBuilder();
        try
        {
            try (Statement s = connect.conn.createStatement ()) {
                str_sql.append("INSERT INTO label (short_name, name) VALUES (\"");
                
                String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, short_name);
                str_sql.append(safe_title);
                str_sql.append("\",\"");
                
                safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, name);
                str_sql.append(safe_title);
                str_sql.append("\")");

                s.executeUpdate (str_sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = s.getGeneratedKeys();
                if (rs.next()){
                    result_id = rs.getInt(1);
                }
            }
        }catch(SQLException ex) {
            System.out.println("SQLException (wikt_parsed TLabel.insert(without category_id)):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result_id;
    }
    
    /** Selects ID from the table 'label' by a label short name.<br><br>
     *  SELECT id FROM label WHERE short_name="context";
     * @param  short_name    name of label category
     * @return 0 if a label name is empty in the table 'label_category'
     */
    public static int getIDByShortName (Connect connect,String short_name,String page_title) {

        if(null == short_name
                || short_name.isEmpty()) return 0;

        int result_id = 0;
        StringBuilder str_sql = new StringBuilder();
        try {
            Statement s = connect.conn.createStatement ();
            try {
                str_sql.append("SELECT id FROM label WHERE short_name=\"");
                String safe_title = PageTableBase.convertToSafeStringEncodeToDBWunderscore(connect, short_name);
                str_sql.append(safe_title);
                str_sql.append("\"");
                try (ResultSet rs = s.executeQuery (str_sql.toString())) {
                    if (rs.next ())
                        result_id = rs.getInt("id");
                    //else
                    // too many to print System.out.println("Warning: (TLabel.getIDByShortName()):: name '" + short_name + "' is absent in the table 'label', page_title="+page_title+".");
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.out.println("SQLException (TLabel.getIDByShortName()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result_id;
    }
    
    /** Updates values (n_foreign_POS, n_translations) in the table 'lang'. <br><br>
     *
     * UPDATE label SET counter=7 WHERE id=3;
     */
    public static void update (Connect connect,int label_id,int counter) {
        
        StringBuilder str_sql = new StringBuilder();
        try
        {
            Statement s = connect.conn.createStatement ();
            try {
                // UPDATE label SET counter=7 WHERE id=3;
                str_sql.append("UPDATE label SET counter=");
                str_sql.append(counter);
                str_sql.append(" WHERE id=");
                str_sql.append(label_id);
                s.executeUpdate (str_sql.toString());
            } finally {
                s.close();
            }
        }catch(SQLException ex) {
            System.out.println("SQLException (TLabel.update()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
    
    /** Stores context labels related to this meaning into table:
     * 'label_meaning'. New labels will be stored to the table 'label' automatically.
     *
     * @param page_title    word described in this article
     * @param _meaning      corresponding record in table 'meaning' to this relation
     * @param lang          language of this meaning
     * @param _labels        labels extracted from the definition of this meaning
     */
    public static void storeToDB (Connect connect,String page_title,
                                  TMeaning _meaning, TLang _lang,
                                  Label[] _labels)
    {
        if(null == _meaning || _labels.length == 0) return;

        for(Label la : _labels)
        {
            // 1. if 'la' is new label then add 'la' to the table 'label' (label.added_by_hand = false).
            int label_id = TLabel.getIDByShortName(connect, la.getShortName(), page_title);
            if(0 == label_id) {
                LabelCategory la_category = LabelEn.getCategoryByLabel(la);
                if(la_category == LabelCategory.regional) {
                    // this is new regional label found by parser (it is absent in the official regional labels list)
                    int cat_id = TLabelCategory.getIDFast(LabelCategory.regional_automatic);
                    label_id = TLabel.insert (connect, la.getShortName(), la.getName(), cat_id);
                } else {
                    label_id = TLabel.insert (connect, la.getShortName(), la.getName());
                }
            }
            
            // 2. add to the table 'label_meaning' the record (la.label_id, _meaning.id)
            TLabelMeaning.insert( connect, page_title, label_id, _meaning.getID());
        }
    }
}
