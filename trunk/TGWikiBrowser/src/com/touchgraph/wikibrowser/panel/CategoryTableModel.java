/*
 * CategoryTableModel.java
 *
 * Copyright (c) 2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.panel;

import wikipedia.kleinberg.Article;
import wikipedia.kleinberg.Category;
import wikipedia.kleinberg.CategoryBlackList;
import com.touchgraph.wikibrowser.TGWikiBrowser;

import javax.swing.table.AbstractTableModel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/** Table with categories of found synonyms. Rows are sorted by number 
 * of found articles which have these category (n_articles). */
public class CategoryTableModel extends AbstractTableModel {
    private boolean DEBUG = true;
    
    //    public CategoryTableModel() {    }
    
    public  TGWikiBrowser   wb;
    private CategoryPanel   cp;
    private JTable          cat_table;      // syn_table;
            
    /** Class stores data about row in synonym table */
    class CategoryRow {  // SynonymRow
        
        /** Category name */
        String  word;
        
        /** Number of articles with this category */
        int     n_articles;
        
        /** Whether to add this category to the blacklist */
        boolean b_blacklist;
        
        /** Wheather to show articles with this category */
        boolean b_show;
    }
    /** rows of categories */
    private List<CategoryRow>   rows;
    /** categories in the same order as in rows (valid after search) */
    private List<Category>      categories_rows;
    /** map from id to article (valid after search) */
    private Map<Integer, Article> id_to_article;
    
    public  int []      preferred_width = {170,    10,     10,     50};
    private String[]    column_names = {"Category","Articles", "Blacklist","Show"};
    //public static final int HUB_WEIGHT_COL    = 1;
    public static final int BLACKLIST_COL   = 2;
    public static final int SHOW_COL        = 3;        // RATEIT_COL
    private Object[][]  data = {
        //{"", new Integer(0), new Integer(0), new Boolean(false)}
    };
    
    
    /** Initialize parameters, table column width. */
    public void init(JTable table, CategoryPanel cp) {
        this.cp = cp;
        cat_table = table;
        rows = new ArrayList<CategoryRow>();
        categories_rows = new ArrayList<Category>();
        id_to_article = null;
        
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
        
        // add/remove mark 'blacklist' for category in the row
        if(BLACKLIST_COL == col) {
            CategoryRow r = rows.get(row);
            r.b_blacklist = value.equals(true);
            if(r.b_blacklist) {
                wb.syn_searcher.addCategoryToBlackList(r.word);
            } else {
                wb.syn_searcher.removeCategoryFromBlackList(r.word);
            }
            
        } else if(SHOW_COL == col) {
            CategoryRow r = rows.get(row);
            r.b_show = value.equals(true);
        }
        
        // ??? nothing todo?
        // change rated synonyms in /log_dir/Article.params
        //wb.syn_searcher.setArticleParameters();

        /*if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }*/
    }
    
    /** Prints list of article, when category is selected */
    public void valueChanged(ListSelectionEvent e) {
        //Ignore extra messages.
        if (e.getValueIsAdjusting()) return;

        ListSelectionModel lsm =
            (ListSelectionModel)e.getSource();
        if (lsm.isSelectionEmpty()) {
            //...//no rows are selected
        } else {
            StringBuffer sb = new StringBuffer();
            
            // i row is selected
            int i = lsm.getMinSelectionIndex();
            sb.append("Category: ");
            sb.append(categories_rows.get(i).page_title);
            sb.append("\n\n");
            
            Category cat = categories_rows.get(i);
            int[]   id_articles = cat.id_articles;
            if(null != id_articles || null == id_to_article){
                for(int id:id_articles) {
                    if(id_to_article.containsKey(id))
                        sb.append(id_to_article.get(id).page_title);
                        sb.append("\n");
                }
            }
            
            cp.output.setText(sb.toString());
        }
    }
    
    
    /** Adds row in synonyms' table.
     * @param word      synonym word
     * @param mean      meaning number
     * @param order     order number in the list of synonyms for this meaning
     * @param rate      the word is rated by user as synonym
     */
    /*public void addSynonym(String word,float hub_weight,float authority_weight,boolean rate) {
        if (null != rows) {
            SynonymRow r = new SynonymRow();
            r.word              = word;
            r.hub_weight        = hub_weight;
            r.authority_weight  = authority_weight;
            r.rate              = rate;
            rows.add(r);
        }
    }*/
    
    
    /** Adds rated synonym to table (if it is absent), marks it as rated,
     * dumps to log_dir/Article.params.
     */
    /*public void addRatedSynonym(String w) {
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
    }*/
    
    /** Removes all old categories, adds new categories.
     * @param id_to_category the map <page_id of category, category object>
     *
     * Use updateTable() to draw the new table.
     */
    public void createCategoriesList(Map<Integer, Category> id_to_category, 
                                     CategoryBlackList      category_black_list,
                                     Map<Integer, Article>  id_to_article)
    {
        rows.clear();
        categories_rows.clear();
        //cat_table.clearSelection();
        //cat_table.removeAll();
        fireTableDataChanged();
        
        // 1. sort categories by c.getIdArticlesLength(), get first 100
        categories_rows = Category.sortByIdArticlesLength(id_to_category, 100);
        
        // 2. add to table, but: set checkbox .b_blacklist if it is in blacklist
        if (null != categories_rows && 0 < categories_rows.size()) {
            for(Category c:categories_rows) {
                CategoryRow r = new CategoryRow();
                r.word          = c.page_title;
                r.n_articles    = c.getIdArticlesLength();
                r.b_blacklist   = category_black_list.inBlackListAlready(c.page_title);
                r.b_show        = false; // todo??
                rows.add(r);
            }
        }
        
        this.id_to_article = id_to_article;
    }
    
    
    /** Gets only rated synonyms by user */
    /*public List<String> getRatedSynonymList() {
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
    }*/
    /** Gets only rated synonyms by user as String, 
     * synonyms delimited by 'delimiter'.
     */
    /*public String getRatedSynonym(String delimiter) {
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
    }*/
    
        
    public void updateTable() {
        if (null != rows) {
            data = new Object[rows.size()][column_names.length];
            for(int i=0; i<rows.size(); i++) {
                CategoryRow r = rows.get(i); 
                data [i][0] = r.word;
                data [i][1] = r.n_articles;
                data [i][2] = r.b_blacklist;
                data [i][3] = r.b_show;
            }
            fireTableDataChanged();
        }
    }
    
    
    /** Gets row from the table, which contains the synonym w, 
     * else returns null. */
    /*public SynonymRow getRow(String w) {
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
    }*/
    
    /** Gets row's number from the table, which contains the synonym w, 
     * else returns -1. */
    /*public int getRowNumber(String w) {
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
    }*/
    
    /** Scrolls to row in the synonym table which contains the synonym word. */
    /*public void scrollToRow(String word) {
        int i = getRowNumber(word);
        if(-1 != i) {
            syn_table.changeSelection(i, RATEIT_COL, true, true);
        }
    }*/
    
    
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
