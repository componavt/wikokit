/*
 * SynonymPanel.java - GUI synonym searcher elements
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.panel;

import wikipedia.util.StringUtil;
import com.touchgraph.wikibrowser.*;
import com.touchgraph.wikibrowser.parameter.*;
//import com.touchgraph.graphlayout.*;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/** Short Class Description */
public class SynonymPanel extends JPanel {
    
    private TGWikiBrowser           wb;
    private static SynonymSearcher  syn_searcher;
    
    private JTabbedPane             tabs;
            
    /** ParametersPanel presents search article parameters */
    public  JPanel                  params_panel;
    
    /** GUI synonyms table as the result of search */
    public  JPanel                  result_table_panel;
    
    /** GUI category table as the result of search */
    public  JPanel                  category_table_panel;
            
    public  JTextField              syn_word;
    
    
    public  String                  log_dir;
    public  JTextField              log_dir_field;
    
    final   JButton                 browse_log_btn;
    final   JFileChooser            fc;
    
    public static int               width = 250;
    
    public String getSynWord() {
        //return StringUtil.UpperFirstLetter(syn_word.getText().trim());
        return syn_word.getText().trim();
    }
    
    /** Creates a new instance of SynonymPanel */
    public SynonymPanel(SynonymSearcher ss, TGWikiBrowser wb_new) {
        
        syn_searcher = ss;
        wb = wb_new;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0,5)));
        //setAlignmentX(1);
        
        // jp_ss - search synonym (ss) horizontal panel
        JPanel jp_ss = new JPanel();
        jp_ss.setLayout(new BoxLayout(jp_ss, BoxLayout.X_AXIS));
        //jp_ss.setLayout(new GridBagLayout());
        
        
        JLabel syn_word_label = new JLabel(wb.tr.getString("Word"));//, Label.RIGHT)
        syn_word = new JTextField(20);
        syn_word_label.setDisplayedMnemonic('W');
        syn_word.setFocusAccelerator('W');
        
        syn_word.setText(wb.INITIAL_NODE);
        //syn_word.setText(wikipedia.util.Encodings.FromTo(wb.INITIAL_NODE, "Cp1251", "UTF8"));
        
        Action get_params = new AbstractAction() {
            // Get previous search parameters and synonyms for the word
            public void actionPerformed(ActionEvent e) {
                String article = getSynWord();
                syn_searcher.getArticleParameters(article);
            }                  
        };
                
        Action draw_neighbours = new AbstractAction() {
            // Draw synonym node, edges to l_from and l_to nodes
            public void actionPerformed(ActionEvent e) {
                String searchString = getSynWord();
                System.out.println("searchString="+searchString);
                if (!searchString.equals("")) {
                    syn_searcher.drawNeighboursFromDB(searchString);
                    wb.syn_searcher.SetNodeAndTextPane(searchString);
                }    
            }                  
        };
        syn_word.addActionListener(get_params);
        syn_word.setToolTipText(wb.tr.getString("Press_enter_to_load_previous_search_parameters"));
        
        
        //jp_ss.setLayout(new GridBagLayout());
        // Add Components to this panel.
        //GridBagConstraints c = new GridBagConstraints();
        //c.fill = GridBagConstraints.HORIZONTAL;
        
        
        jp_ss.add(Box.createRigidArea(new Dimension(5,0)));
        jp_ss.add(syn_word_label);
        jp_ss.add(Box.createRigidArea(new Dimension(5,0)));
        
        /*JPanel syn_word_wrapper = new JPanel();
        syn_word_wrapper.setLayout(new GridBagLayout());
        // Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        syn_word_wrapper.add(syn_word, c);
        */
        jp_ss.add(syn_word);
        /*
        syn_word_label.setBorder(BorderFactory.createCompoundBorder(
                   BorderFactory.createLineBorder(Color.red),
                   syn_word_label.getBorder()));
        syn_word.setBorder(BorderFactory.createCompoundBorder(
                   BorderFactory.createLineBorder(Color.red),
                   syn_word.getBorder()));
        jp_ss.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("jp_ss border"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                jp_ss.getBorder()));
         */
        
        jp_ss.add(Box.createRigidArea(new Dimension(5,0)));
        add(jp_ss);
        add(Box.createRigidArea(new Dimension(0,5)));
        
        
        // Buttons "Search Synonyms" and "Draw neighbours" on horizontal panel
        JPanel jp_btns = new JPanel();
        jp_btns.setLayout(new BoxLayout(jp_btns, BoxLayout.X_AXIS));
        
        // Button "Load (parameters)"
        JButton get_params_btn = new JButton(wb.tr.getString("Load"));
        get_params_btn.setToolTipText(wb.tr.getString("Load_previous_search_parameters_and_synonyms_for_the_word"));
        get_params_btn.setMnemonic(KeyEvent.VK_L);
        get_params_btn.setBackground(Color.decode("#D8C0C0"));
        get_params_btn.addActionListener(get_params);
        
        JButton ss_button = new JButton(wb.tr.getString("Search_Synonyms"));
        ss_button.setMnemonic(KeyEvent.VK_S);
        ss_button.setAlignmentX(0.5f);
        ss_button.setToolTipText(wb.tr.getString("Search_synonyms_in_Wikipedia_database"));
        ss_button.setBackground(Color.decode("#D8C0C0"));
        ss_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String article = getSynWord();
                wb.INITIAL_NODE = article;
                wb.parameters.setNode(article);
                wb.parameters.saveParameters();
                
                wb.syn_searcher.SearchSynonyms(article);
            }
        });
        
        // Button "Draw (article node and neighbours)"
        JButton draw_btn = new JButton(wb.tr.getString("Neighbours"));
        draw_btn.setToolTipText(wb.tr.getString("Draw_article_node_and_neighbours"));
        draw_btn.setMnemonic(KeyEvent.VK_N);
        draw_btn.setBackground(Color.decode("#D8C0C0"));
        draw_btn.addActionListener(draw_neighbours);
        
        jp_btns.add(Box.createRigidArea(new Dimension(5,0)));
        jp_btns.add(get_params_btn);
        jp_btns.add(Box.createRigidArea(new Dimension(5,0)));
        jp_btns.add(ss_button);
        jp_btns.add(Box.createRigidArea(new Dimension(5,0)));
        jp_btns.add(draw_btn);
        jp_btns.add(Box.createRigidArea(new Dimension(5,0)));
        //jp_btns.setAlignmentX(0);
        add(jp_btns);
        add(Box.createRigidArea(new Dimension(0,5)));
        
        
        // lf - log file panel
        // [Check Box] "Log file" [File chooser]
        JPanel jp_lf = new JPanel();
        jp_lf.setLayout(new BoxLayout(jp_lf , BoxLayout.X_AXIS));
        
        JCheckBox check_logfile = new JCheckBox(wb.tr.getString("Log")); //  directory
        check_logfile.setMnemonic(KeyEvent.VK_O);
        check_logfile.setSelected(true);
        
        check_logfile.addItemListener(
            new ItemListener() {
                // enable/disable
                //      - the text editable field of log filename 
                //      - filename browsing
                public void itemStateChanged(ItemEvent e) {
                    boolean b = (e.getStateChange() == ItemEvent.SELECTED) ? true : false;
                    log_dir_field.setEnabled(b);
                    browse_log_btn.setEnabled(b);
                    wb.parameters.setEnableLog(b);
                }
        });
        
        log_dir_field = new JTextField();
        log_dir = wb.parameters.getLogDir();
        log_dir_field.setText(log_dir);     //wb.INITIAL_NODE);
        
        // browse log file button
        browse_log_btn = new JButton(wb.tr.getString("Browse..."));           //browse_log_btn.setAlignmentX(0.5f);
        browse_log_btn.setToolTipText(wb.tr.getString("Browse_directory_for_log_files"));
        browse_log_btn.setMnemonic(KeyEvent.VK_B);
        browse_log_btn.setBackground(Color.decode("#D8C0C0"));
        
        if(!wb.parameters.isLogEnabled()) {
            // disables log dir 1) checkbox 2) text field 3) "browse..." button
            check_logfile .setSelected(false);
            log_dir_field .setEnabled(false);
            browse_log_btn.setEnabled(false);
        }
        
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        browse_log_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(wb.synonymTextPanel)) {
                    File file = fc.getSelectedFile();
                    log_dir = file.getAbsolutePath();
                    log_dir_field.setText(log_dir);
                    wb.parameters.setLogDir(log_dir);
                } else {
                    // print old path
                    log_dir_field.setText(log_dir);
                }
            }
        });
                        
        jp_lf.add(check_logfile);
        jp_lf.add(log_dir_field);
        jp_lf.add(Box.createRigidArea(new Dimension(5,0)));
        jp_lf.add(browse_log_btn);
        jp_lf.add(Box.createRigidArea(new Dimension(5,0)));
        add(jp_lf);
        add(Box.createRigidArea(new Dimension(0,15)));
        
        
        tabs = new JTabbedPane();
        params_panel	  = new ParametersPanel (syn_searcher, wb);
        result_table_panel = new ResultTablePanel(syn_searcher, wb);
        //tp_removed  = new JPanel();
        category_table_panel = new CategoryPanel(syn_searcher, wb);
	
        
        tabs.addTab(wb.tr.getString("Parameters"),  params_panel);
        tabs.setMnemonicAt(0, KeyEvent.VK_P);
        tabs.addTab(wb.tr.getString("Categories"),  category_table_panel);
        tabs.setMnemonicAt(1, KeyEvent.VK_C);
        tabs.addTab(wb.tr.getString("Results"),     result_table_panel);
        tabs.setMnemonicAt(2, KeyEvent.VK_U);
        add(tabs);
        
        ChangeListener changeListener = new ChangeListener() {
          public void stateChanged(ChangeEvent changeEvent) {
              unColorResultTab ();
          }
        };
        tabs.addChangeListener(changeListener);


        //Tell accessibility tools about label/textfield pairs.
        syn_word_label.setLabelFor(syn_word);
    }
    
    /** Flashes result tab */
    public void colorResultTab () {
        tabs.setBackgroundAt(2, Color.decode("#FF8000"));
    }
    public void unColorResultTab () {
        tabs.setBackgroundAt(2, null);
    }
    
}
