/*
 * ResultPanel.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.panel;

import com.touchgraph.wikibrowser.*;
import com.touchgraph.wikibrowser.parameter.*;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;


/** GUI synonyms table and textarea with results of synonym search */
public class ResultTablePanel extends JPanel {
    private boolean DEBUG = false;
    
    private         TGWikiBrowser           wb;
    private static  SynonymSearcher         syn_searcher;
    
    /** Logical part of synonym table */
    public          ResultTableModel        table;
    
    public	    JTextArea               output;
    //public final    JFormattedTextField     root_size_tf;
    
    public ResultTablePanel(SynonymSearcher ss, TGWikiBrowser wb_new)
    {   
        syn_searcher = ss;
        wb = wb_new;
	
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0,5)));
	 
        table = new ResultTableModel();
        table.wb = wb_new;
	JTable syn_table = new JTable(table);
        table.init(syn_table);
        // initColumnSizes(syn_table);
	JScrollPane scroll_table = new JScrollPane(syn_table);
	syn_table.setPreferredScrollableViewportSize(new Dimension(200, 750));
	
	//add(syn_table.getTableHeader());
	add(scroll_table);
        
	
        //Create a text area
        output = new JTextArea();
        output.setFont(new Font("Serif", Font.PLAIN, 12));
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setRows(200);
        JScrollPane areaScrollPane = new JScrollPane(output);
        areaScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // areaScrollPane.setPreferredSize(new Dimension(width+150, 850));
        areaScrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(wb.tr.getString("Output")),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                areaScrollPane.getBorder()));
        add(areaScrollPane);
    }
    
}
