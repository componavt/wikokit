/*
 * CategoryPanel.java
 *
 * Copyright (c) 2005 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.panel;

import com.touchgraph.wikibrowser.*;
import com.touchgraph.graphlayout.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/** User selects categories for category black list */
public class CategoryPanel extends JPanel {
    
    private TGWikiBrowser           wb;
    private static SynonymSearcher  ss;
    
    /** Logical part of synonym table */
    public CategoryTableModel       table;
                                            //public  JTextField            category_field; //syn_word;
    public  JTextArea               output;
    
    
    public CategoryPanel(SynonymSearcher ss, TGWikiBrowser wb) {
        this.ss = ss;
        this.wb = wb;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0,5)));
	 
        table = new CategoryTableModel();
        table.wb = wb;
	JTable cat_table = new JTable(table);
        table.init(cat_table, this);
        // initColumnSizes(syn_table);
	JScrollPane scroll_table = new JScrollPane(cat_table);
	cat_table.setPreferredScrollableViewportSize(new Dimension(200, 750));
	
        cat_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Ask to be notified of selection changes.
        ListSelectionModel rowSM = cat_table.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                table.valueChanged(e);
            }
        });
        
	//add(syn_table.getTableHeader());
	add(scroll_table);
        
        
        //Create a text area
        output = new JTextArea();
        output.setFont(new Font("Serif", Font.PLAIN, 12));
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(output);
        areaScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setHorizontalScrollBarPolicy(
                        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 850));
        areaScrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Output"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                areaScrollPane.getBorder()));
        add(areaScrollPane);
    }
    
}
