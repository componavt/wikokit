/*
 * ParametersPanel.java 
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.panel;

import wikipedia.util.StringUtil;

import com.touchgraph.wikibrowser.*;
import com.touchgraph.wikibrowser.parameter.*;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.text.NumberFormat;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/** Parameters GUI fields for Synonym search algorithm:
 *  root_set_size
 *  inc
 *  n_synonyms
 *  eps_error
 *  categories_max_steps
 *  See comments in {@link com.touchgraph.wikibrowser.parameter.ArticleParameters}
 */
public class ParametersPanel extends JPanel {
    
    private         TGWikiBrowser           wb;
    private static  SynonymSearcher         syn_searcher;
    
    public final    JFormattedTextField     root_size_tf;
    public final    JFormattedTextField     inc_tf;
    public final    JFormattedTextField     nsyn_tf;
    public final    JFormattedTextField     eps_tf;
    public final    JFormattedTextField     max_steps_tf;
    public final    JTextArea               categories_field;
    public final    JTextField              iwiki_lang;

                
    public ParametersPanel(SynonymSearcher ss, TGWikiBrowser wb_new)
    {   
        syn_searcher = ss;
        wb = wb_new;
        
        PropertyChangeListener pcl = new PropertyChangeListener() {
            /** Called when a field's "value" property changes. */
            public void propertyChange(PropertyChangeEvent e) {
                int     i;
                float   f;
                Object  s = e.getSource();
                if (s == root_size_tf) {
                    i = ((Number)root_size_tf.getValue()).intValue();
                    syn_searcher.setRootSetSize(i);
                } else if (s == inc_tf) {
                    i = ((Number)inc_tf.getValue()).intValue();
                    syn_searcher.setIncrement(i);
                } else if (s == nsyn_tf) {
                    i = ((Number)nsyn_tf.getValue()).intValue();
                    syn_searcher.setNSynonyms(i);
                } else if (s == eps_tf) {
                    f = ((Number)eps_tf.getValue()).floatValue();
                    syn_searcher.setEpsError(f);
                } else if (s == max_steps_tf) {
                    i = ((Number)max_steps_tf.getValue()).intValue();
                    syn_searcher.setCategoriesMaxSteps(i);
                }
            }
        };
            
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0,5)));
        
        // subgraph_jp - parameters to create focused subgraph
        JPanel subgraph_jp = new JPanel();
        subgraph_jp.setLayout(new BoxLayout(subgraph_jp, BoxLayout.X_AXIS));
        
        // *) root set size
        JLabel root_size_label = new JLabel(wb.tr.getString("Root_set_size"));
        root_size_tf = new JFormattedTextField(NumberFormat.getNumberInstance());
        root_size_tf.addPropertyChangeListener("value", pcl);
        root_size_tf.setToolTipText(wb.tr.getString("Number_of_articles_in_the_root_set._Parameters_will_be_stored_to_./Your_log/article.params_after_search"));

        root_size_label.setDisplayedMnemonic('R');
        root_size_tf.setFocusAccelerator('R');
        
        subgraph_jp.add(root_size_label);
        subgraph_jp.add(Box.createRigidArea(new Dimension(5,0)));
        subgraph_jp.add(root_size_tf);
        subgraph_jp.add(Box.createRigidArea(new Dimension(5,0)));
        
        // *) increment
        JLabel inc_label = new JLabel(wb.tr.getString("Increment"));
        inc_tf = new JFormattedTextField(NumberFormat.getNumberInstance());
        inc_tf.addPropertyChangeListener("value", pcl);
        inc_tf.setToolTipText(wb.tr.getString("Number_of_articles_which_will_be_added_to_the_base_set_(they_refer_to_one_of_the_pages_in_the_root_base)"));
        subgraph_jp.add(inc_label);
        subgraph_jp.add(Box.createRigidArea(new Dimension(5,0)));
        subgraph_jp.add(inc_tf);
        
        subgraph_jp.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(wb.tr.getString("Root_Set_and_Base_Set_of_links")),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                subgraph_jp.getBorder()));
        add(subgraph_jp);
        
        
        // nsyn_eps_jp - parameters for search synonyms iterations
        JPanel nsyn_eps_jp = new JPanel();
        nsyn_eps_jp.setLayout(new BoxLayout(nsyn_eps_jp, BoxLayout.X_AXIS));
        
        // *) n_synonyms
        JLabel nsyn_label = new JLabel(wb.tr.getString("N_synonyms"));
        nsyn_tf = new JFormattedTextField(NumberFormat.getNumberInstance());
        nsyn_tf.addPropertyChangeListener("value", pcl);
        nsyn_tf.setToolTipText(wb.tr.getString("Number_of_synonyms_to_search"));
        nsyn_eps_jp.add(nsyn_label);
        nsyn_eps_jp.add(Box.createRigidArea(new Dimension(5,0)));
        nsyn_eps_jp.add(nsyn_tf);
        nsyn_eps_jp.add(Box.createRigidArea(new Dimension(5,0)));
        
        // *) eps_error
        JLabel eps_label = new JLabel(wb.tr.getString("Eps_error"));
        eps_tf = new JFormattedTextField(NumberFormat.getNumberInstance());
        eps_tf.addPropertyChangeListener("value", pcl);
        eps_tf.setToolTipText(wb.tr.getString("The_iterative_calculations_will_be_stopped_if_the_change_of_value_is_less_than_epsilon_error"));
        nsyn_eps_jp.add(eps_label);
        nsyn_eps_jp.add(Box.createRigidArea(new Dimension(5,0)));
        nsyn_eps_jp.add(eps_tf);
        
        nsyn_eps_jp.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(wb.tr.getString("Iterative_calculation_of_authority_and_hub_pages")),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                nsyn_eps_jp.getBorder()));
        add(nsyn_eps_jp);
        
        
        // jp_cboxes: "Skip spaces" and "Random"
        JPanel jp_cboxes = new JPanel();
        jp_cboxes.setLayout(new BoxLayout(jp_cboxes, BoxLayout.X_AXIS));
        
        // st - skip titles (with spaces) panel
        // [Check Box] "Skip titles with spaces"
        JCheckBox check_skip_spaces = new JCheckBox(wb.tr.getString("Titles_without_spaces"));
        check_skip_spaces.setMnemonic(KeyEvent.VK_T);
        check_skip_spaces.setSelected(true);
        check_skip_spaces.setToolTipText(wb.tr.getString("Skip_titles_with_spaces"));
        wb.syn_searcher.session.skipTitlesWithSpaces(true);
        
        check_skip_spaces.addItemListener(
            new ItemListener() {
                // enable/disable
                //      - skipt titles with spaces
                public void itemStateChanged(ItemEvent e) {
                    boolean b = (e.getStateChange() == ItemEvent.SELECTED) ? true : false;
                    wb.syn_searcher.session.skipTitlesWithSpaces(b);
                }
        });
        jp_cboxes.add(check_skip_spaces);
        
        // rand - random pages (base set forming) panel
        // [Check Box] "Random pages (base set forming)"
        JCheckBox check_rand = new JCheckBox(wb.tr.getString("Random_pages"));
        check_rand.setMnemonic(KeyEvent.VK_M);
        check_rand.setSelected(true);
        check_rand.setToolTipText(wb.tr.getString("Base_set_forming"));
        wb.syn_searcher.session.skipTitlesWithSpaces(true);
        
        check_rand.addItemListener(
            new ItemListener() {
                // enable/disable
                //      - skipt titles with spaces
                public void itemStateChanged(ItemEvent e) {
                    boolean b = (e.getStateChange() == ItemEvent.SELECTED) ? true : false;
                    wb.syn_searcher.session.randomPages(b);
                }
        });
        jp_cboxes.add(check_rand);
        
        add(jp_cboxes);
        add(Box.createRigidArea(new Dimension(0,5)));
        
        
        
        
                // jp_iwiki: checkbox "iwiki" and text field "Lang"
                JPanel jp_iwiki = new JPanel();
                jp_iwiki.setLayout(new BoxLayout(jp_iwiki, BoxLayout.X_AXIS));

                JCheckBox check_iwiki = new JCheckBox(wb.tr.getString("Show_InterWiki"));
                check_iwiki.setMnemonic(KeyEvent.VK_I);
                boolean _b = wb.syn_searcher.session.getIWiki();
                check_iwiki.setSelected(_b);

                check_iwiki.addItemListener(
                    new ItemListener() {
                        // enable/disable
                        //      - skipt titles with spaces
                        public void itemStateChanged(ItemEvent e) {
                            boolean b = (e.getStateChange() == ItemEvent.SELECTED) ? true : false;
                            wb.syn_searcher.session.setIWiki(b);
                            iwiki_lang.setEnabled(b);
                        }
                });
                jp_iwiki.add(check_iwiki);
                
                
                iwiki_lang = new JTextField(2);
                //iwiki_lang.setFocusAccelerator('K');  //iwiki_lang.setFocusAccelerator('W');
                iwiki_lang.setToolTipText(wb.tr.getString("Interwiki_language_to_be_added_to_labels_of_nodes,_e.g._en,_ru"));
                iwiki_lang.setText(wb.syn_searcher.session.getIWikiLang());
                
                class IWikiLangVerifier extends InputVerifier
                 implements ActionListener {
                    
                    public boolean run() {
                        String lang = iwiki_lang.getText();
                        boolean b = wb.syn_searcher.session.setIWikiLang(lang);
                        if(b)
                            iwiki_lang.setText(wb.syn_searcher.session.getIWikiLang()); // e.g. transformation "eN" to "En"
                        return b;
                    }
                    
                    // loose focus
                    public boolean verify(JComponent input) {
                        return run(); }
                    
                    // "Enter" 
                    public void actionPerformed(ActionEvent e) {
                        run(); }
                };
                IWikiLangVerifier iwiki_lang_verifier = new IWikiLangVerifier();
                iwiki_lang.setInputVerifier(iwiki_lang_verifier);

                
                //iwiki_lang.addActionListener(get_params);
                iwiki_lang.setEnabled(_b);
                jp_iwiki.add(iwiki_lang);
                
                add(jp_iwiki);
                add(Box.createRigidArea(new Dimension(0,5)));
        
        
        
        
        // category_jp - parameters for category treating (removing)
        // category_jp = category1_jp and category2_jp (two horizontal panels)
        JPanel category_jp = new JPanel();
        category_jp.setLayout(new BoxLayout(category_jp, BoxLayout.Y_AXIS));
        JPanel category1_jp = new JPanel();
        category1_jp.setLayout(new BoxLayout(category1_jp, BoxLayout.X_AXIS));
        
        // *) categories_max_steps (depth)
        JLabel max_steps_label = new JLabel(wb.tr.getString("Search_depth"));
        max_steps_tf = new JFormattedTextField(NumberFormat.getNumberInstance());
        
        max_steps_label.setDisplayedMnemonic('H');
        max_steps_tf.setFocusAccelerator('H');
        
        max_steps_tf.addPropertyChangeListener("value", pcl);
        max_steps_tf.setToolTipText(wb.tr.getString("The_max_depth_the_category's_ansectors_will_be_checked_(recursively)_for_belonging_to_the_blacklist"));
        category1_jp.add(max_steps_label);
        category1_jp.add(Box.createRigidArea(new Dimension(5,0)));
        category1_jp.add(max_steps_tf);
        
        
        // *) black list category edit field and button "Set"
        JPanel category2_jp = new JPanel();
        category2_jp.setLayout(new BoxLayout(category2_jp, BoxLayout.X_AXIS));
        
        /*
        final JTextField categories_field = new JTextField();
        String[] categories = wb.parameters.getBlackListCategory( syn_searcher.getCurrentLanguage() );
        categories_field.setText( StringUtil.join("|", categories));
        */
        
        //Create a text area
        categories_field = new JTextArea();
        categories_field.setFont(new Font("Serif", Font.PLAIN, 12));
        categories_field.setLineWrap(true);
        categories_field.setWrapStyleWord(true);
        categories_field.setRows(300);
        categories_field.setToolTipText(wb.tr.getString("Blacklist_of_categories_joined_by_vertical_line_|"));
        JScrollPane categories_scroll = new JScrollPane(categories_field);
        categories_scroll.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        
        JButton  category_btn;
        // browse log file button
        category_btn = new JButton(wb.tr.getString("Set"));
        category_btn.setToolTipText(wb.tr.getString("Set_category_blacklist"));
        category_btn.setMnemonic(KeyEvent.VK_E);
        category_btn.setBackground(Color.decode("#D8C0C0"));
        category_btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        category_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] categories = StringUtil.split("|", categories_field.getText());
                syn_searcher.setBlackListCategory(categories);
                syn_searcher.setArticleParameters();
            }
        });
        
        category2_jp.add(categories_scroll); //categories_field);
        
        category_jp.add(category1_jp);
        category_jp.add(Box.createRigidArea(new Dimension(0,5)));
        category_jp.add(category2_jp);
        category_jp.add(Box.createRigidArea(new Dimension(0,5)));
        category_jp.add(category_btn);
        
        category_jp.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(wb.tr.getString("Category_blacklist_parameters")),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                category_jp.getBorder()));
        add(category_jp);
        
        // compress all GUI controls to the top: use big JTextArea.setRows
        //add(Box.createRigidArea(new Dimension(0,700)));
        //add(Box.createVerticalGlue()); //extra space
        //add(Box.createVerticalStrut(1700));
        
        
        //Tell accessibility tools about label/textfield pairs.
        root_size_label.setLabelFor(root_size_tf);
        inc_label.      setLabelFor(inc_tf);
        nsyn_label.     setLabelFor(nsyn_tf);
        eps_label.      setLabelFor(eps_tf);
        max_steps_label.setLabelFor(max_steps_tf);
    }
}
