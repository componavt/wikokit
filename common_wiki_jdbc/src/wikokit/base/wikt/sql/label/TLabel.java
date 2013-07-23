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
import wikokit.base.wikipedia.language.LanguageType;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.PageTableBase;
import wikokit.base.wikipedia.sql.Statistics;
import wikokit.base.wikipedia.sql.UtilSQL;
import wikokit.base.wikt.constant.Label;
import wikokit.base.wikt.constant.LabelCategory;

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
    
    /** Category of context label (category_id). */
    private TLabelCategory label_category;
    
    /** Weather the label was added manually to the code of wikokit, or was gathered automatically by parser. */
    private boolean added_by_hand;
    
    /** Number of definitions with this context label. */
    private int counter;
    
    /** Map from label to ID.*/
    private static Map<Label, Integer> label2id;
    
    /** Map from ID to label.*/
    private static Map<Integer, Label> id2label;
    
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
            System.err.println("Error (wikt_parsed TLabel.createFastMaps()):: The table `label` is empty!");
            return;
        }
        
        if(null != label2id && label2id.size() > 0)
            label2id.clear();
        
        if(null != id2label && id2label.size() > 0)
            id2label.clear();
        
        label2id = new LinkedHashMap<>(size);
        id2label = new LinkedHashMap<>(size);
                
        Collection<Label> labs = Label.getAllLabels(wikt_lang);
        for(Label label : labs) {
            
            String short_name = label.getShortName();
            int id        = getIDByShortName(connect, short_name);
            
            if (0 == id) {
                System.err.println("Error (wikt_parsed TLabel.createFastMaps()):: There is an empty label short name, check the table `label_category`!");
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
                    
            //               short_name,              name,               category_id, added_by_hand (default FALSE), counter (default 0)
            insert (connect, label.getShortName(), label.getName(), category_id);
        }
    }
    
    /** Inserts record into the table 'label'.<br><br>
     * INSERT INTO label (short_name, name, category_id) VALUES ("short name", "name", 1);
     * @param short_name    label itself
     * @param name          full name of label
     * @param category_id  ID of label category from the table 'label_category'
     */
    public static void insert (Connect connect,String short_name, String name, int category_id) {

        if(null == short_name || short_name.length() == 0) return;
        
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

                s.executeUpdate (str_sql.toString());
            }
        }catch(SQLException ex) {
            System.err.println("SQLException (wikt_parsed TLabel.insert()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
    }
    
    /** Selects ID from the table 'label' by a label short name.<br><br>
     *  SELECT id FROM label WHERE short_name="context";
     * @param  short_name    name of label category
     * @return 0 if a label name is empty in the table 'label_category'
     */
    public static int getIDByShortName (Connect connect,String short_name) {

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
                    else
                        System.err.println("Warning: (TLabel.getIDByShortName()):: name (" + short_name + ") is absent in the table 'label_category'.");
                }
            } finally {
                s.close();
            }
        } catch(SQLException ex) {
            System.err.println("SQLException (TLabel.getIDByShortName()):: sql='" + str_sql.toString() + "' " + ex.getMessage());
        }
        return result_id;
    }
}
