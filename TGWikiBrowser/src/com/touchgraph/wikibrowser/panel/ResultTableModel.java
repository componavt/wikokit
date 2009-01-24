/*
 * ResultTableModel.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.panel;

import com.touchgraph.wikibrowser.*;
import com.touchgraph.wikibrowser.parameter.*;
import wikipedia.kleinberg.*;

import javax.swing.table.*;
import javax.swing.*;
import java.util.*;


/** Table with found synonyms */
public class ResultTableModel extends AbstractTableModel {
    private boolean DEBUG = true;
    
    public  TGWikiBrowser   wb;
    private JTable          syn_table;
            
    /** Class stores data about row in synonym table */
    class SynonymRow {
        
        /** synonym word */
        String  word;
        
        /** hub weight */
        float   hub_weight;
        
        /** authority weight */
        float   authority_weight;
        
        /** the word is rated by user as synonym if true */
        boolean rate;
        
        // type of relation:
        // 
        // ====See also        Смотри также====
        // 
    }
    private List<SynonymRow> rows;
    
    //public  int []      preferred_width = {170,    10,     10,     10};
    public  int []      preferred_width = {170,    10,     10,     50};
    private String[]    column_names = {"Synonyms_header","hub", "a","Rate_it"};
    //public static final int HUB_WEIGHT_COL    = 1;
    //public static final int AUTHORITY_WEIGHT_COL   = 2;
    public static final int RATEIT_COL  = 3;
    private Object[][]  data = {
        //{"", new Integer(0), new Integer(0), new Boolean(false)}
    };
    
    
    /** Initialize parameters, table column width. */
    public void init(JTable table) {
        assert(null != wb);
        syn_table = table;
        rows = new ArrayList<SynonymRow>();
        
        // init columns width
        TableColumn column = null;
        for (int i = 0; i < preferred_width.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(preferred_width[i]);
        }
    }
    
    
    public int getColumnCount() {
        return column_names.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return wb.tr.getString(column_names[col]);
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }

        data[row][col] = value;
        fireTableCellUpdated(row, col);
        
        // add/remove mark 'rated' for synonyms in rows
        if(RATEIT_COL == col) {
            SynonymRow r = rows.get(row);
            r.rate = value.equals(true);
        }
        
        // change rated synonyms in /log_dir/Article.params
        wb.syn_searcher.setArticleParameters();

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }
    
    /** Adds row in synonyms' table.
     * @param word      synonym word
     * @param mean      meaning number
     * @param order     order number in the list of synonyms for this meaning
     * @param rate      the word is rated by user as synonym
     */
    public void addSynonym(String word,float hub_weight,float authority_weight,boolean rate) {
        if (null != rows) {
            SynonymRow r = new SynonymRow();
            r.word              = word;
            r.hub_weight        = hub_weight;
            r.authority_weight  = authority_weight;
            r.rate              = rate;
            rows.add(r);
        }
    }
    
    
    /** Adds rated synonym to table (if it is absent), marks it as rated,
     * dumps to log_dir/Article.params.
     */
    public void addRatedSynonym(String w) {
        if(DEBUG) {
            System.out.println("ResultTableModel.addRatedSynonym word is " + w);
        }
        
        SynonymRow r = getRow(w);
        if(null != r) {
            if(!r.rate) {
            r.rate = true;
            }
        } else {
            addSynonym(w, 0, 0, true);
        }
    }
    
    /** Removes all non rated synonyms (remain all rated synonyms), 
     * adds only synonyms which are not rated before.
     *
     * Use updateTable() to draw the new table.
     */
    public void addUnratedSynonymListByArticles(List<Article> synonyms) {
        
        // removes all non rated synonyms
        List<String> rated_words = new ArrayList<String>();
        if(null != rows) {
            for(int i=0; i<rows.size(); i++) {
                SynonymRow r = rows.get(i);
                if(!r.rate) {
                    rows.remove(r);
                } else {
                    rated_words.add(r.word);
                }
            }
        }
    
        // adds only synonyms which are not rated before.
        if (null != synonyms && 0 < synonyms.size()) {
            for(int i=0; i<synonyms.size(); i++) {
                Article a = synonyms.get(i);
                String word = a.page_title;
                if(!rated_words.contains(word)) {
                    SynonymRow r = new SynonymRow();
                    r.word              = word;
                    r.authority_weight  = a.x;
                    r.hub_weight        = a.y;
                    rows.add(r);
                }
            }
        }
    }
    
    /** Removes all rows in table, add rated synonyms to rows */
    public void createRatedSynonymList(List<String> synonyms) {
        assert(null != rows); 
        rows.clear();
        if (null != synonyms && 0 < synonyms.size()) {
            for(int i=0; i<synonyms.size(); i++) {
                SynonymRow r = new SynonymRow();
                r.word = synonyms.get(i);
                r.rate = true;
                rows.add(r);
            }
        }
    }
    
    /** Gets only rated synonyms by user */
    public List<String> getRatedSynonymList() {
        List<String> s = new ArrayList<String>();
        if (null != rows) {
            for(int i=0; i<rows.size(); i++) {
                SynonymRow r = rows.get(i);
                if(r.rate) {
                    if(null == s) {
                        s = new ArrayList<String>();
                    }
                    s.add(r.word);
                }
            }
        }
        return s;
    }
    /** Gets only rated synonyms by user as String, 
     * synonyms delimited by 'delimiter'.
     */
    public String getRatedSynonym(String delimiter) {
        String  s = "";
        
        if (null != rows) {
            for(int i=0; i<rows.size(); i++) {
                SynonymRow r = rows.get(i);
                if(r.rate) {
                    if(0 < s.length()) {    // adds delimiter after first word
                        s += delimiter; 
                    }
                    s += r.word;
                }
            }
        }
        return s;
    }
    
    
    /** Rates word w in the synonym table: checks checkbox, save to log_dir/Article.params. */
    /*public void rateSynonymInvert(String w) {   
        for(int i=0; i<rows.size(); i++) {
            SynonymRow r = rows.get(i);
            if(r.word.equalsIgnoreCase(w)) {
                r.rate = !r.rate;
            }
        }
    }*/
    
    public void updateTable() {
        if (null != rows) {
            data = new Object[rows.size()][column_names.length];
            for(int i=0; i<rows.size(); i++) {
                SynonymRow r = rows.get(i); 
                data [i][0] = r.word;
                data [i][1] = r.hub_weight;
                data [i][2] = r.authority_weight;
                data [i][3] = r.rate;
            }
            fireTableDataChanged();
        }
    }
    
    
    /** Gets row from the table, which contains the synonym w, 
     * else returns null. */
    public SynonymRow getRow(String w) {
        if (null == rows || 0 == rows.size()) {
            return null;
        }
            
        for(int i=0; i<rows.size(); i++) {
            SynonymRow r = rows.get(i);
            if(r.word.equalsIgnoreCase(w)) {
                return r;
            }
        }
        return null;
    }
    
    /** Gets row's number from the table, which contains the synonym w, 
     * else returns -1. */
    public int getRowNumber(String w) {
        if (null == rows || 0 == rows.size()) {
            return -1;
        }
        
        for(int i=0; i<rows.size(); i++) {
            SynonymRow r = rows.get(i);
            if(r.word.equalsIgnoreCase(w)) {
                return i;
            }
        }
        return -1;
    }
    
    /** Scrolls to row in the synonym table which contains the synonym word. */
    public void scrollToRow(String word) {
        int i = getRowNumber(word);
        if(-1 != i) {
            syn_table.changeSelection(i, RATEIT_COL, true, true);
        }
    }
    
    
    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
