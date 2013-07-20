/* TLabel.java - SQL operations with the table 'label' in Wiktionary
 * parsed database.
 *
 * Copyright (c) 2013 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */
package wikokit.base.wikt.sql.label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikipedia.sql.UtilSQL;
import wikokit.base.wikt.constant.Label;

/** An operations with the table 'label' (context labels) in MySQL Wiktionary_parsed database.
 */
public class TLabel {
    
    /** Unique identifier in the table 'label'. */
    private int id;
    
    /** Context label short name. */
    private String label;
    
    /** Context label full name. */
    private String full_name;
    
    /** Category of context label (category_id). */
    private TLabelCategory label_category;
    
    /** Number of definitions with this context label. */
    private int counter;
    
    
    /** Deletes all records from the table 'label',
     * loads parts of speech names from LabelEn.java,
     * sorts by name,
     * fills the table.
     */
    public static void recreateTable(Connect connect) {

        System.out.println("Recreating the table `label`...");
        Map<Integer, Label> _id2label = fillLocalMaps();
        UtilSQL.deleteAllRecordsResetAutoIncrement(connect, "label");
        fillDB(connect, _id2label);
        {
            int db_current_size = wikokit.base.wikipedia.sql.Statistics.Count(connect, "label");
            assert(db_current_size == Label.size()); // ~ ??? labels entered by hand
        }
    }

    /** Load data from a LabelEn class, sorts,
     * and fills the local map 'id2label'. */
    public static Map<Integer, Label> fillLocalMaps() {

        int size = Label.size();
        List<String>list_label = new ArrayList<>(size);
        list_label.addAll(Label.getAllLabelShortNames());
        Collections.sort(list_label);

        // OK, we have list of labels (short names). Sorted list 'list_label'

        // Local map from id to Label. It is created from data in LabelEn.java.
        // It is used to fill the table 'label' in right sequence.
        Map<Integer, Label> _id2label = new LinkedHashMap<>(size);
        for(int id=0; id<size; id++) {
            String s = list_label.get(id);    // s - context label name
            assert(Label.hasShortName(s));                                      //System.out.println("fillLocalMaps---id="+id+"; s="+s);
            _id2label.put(id, Label.getByShortName(s));
        }
        return _id2label;
    }

    /** Fills database table 'label' by data from LabelEn class. */
    public static void fillDB(Connect connect,Map<Integer, Label> id2label) {

  //      for(int id : id2label.keySet())
//            insert (connect, id2label.get(id));
    }
}
