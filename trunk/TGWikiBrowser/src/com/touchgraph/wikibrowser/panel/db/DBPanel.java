/*
 * DBPanel.java Input fields login/password to connect to database,
 *  various statistics about the database: number of pages, links, ...
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.panel.db;

import com.touchgraph.wikibrowser.*;
import com.touchgraph.wikibrowser.panel.*;
import wikipedia.sql.*;
import wikipedia.util.StringUtil;

import com.touchgraph.graphlayout.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/** JPanel provides fields to connect to database  */
public class DBPanel extends JPanel {

    private TGWikiBrowser           wb;
    private static SynonymSearcher  syn_searcher;
    
    public  JTextField              enc_java;
    private JTextField              enc_java_categories;
    
    public  JTextField              enc_ui;
    private JTextField              enc_ui_node;
    
    public  JTextField              db_host;
    public  JTextField              db_name;
    public  JTextField              user_tf;
    public  JTextField              pass_tf;
    public  JTextField              wiki_url_tf;
    
    public  JTextArea               output;

    public final static int ONE_SECOND = 1000;
    private JProgressBar    progressBar;
    private Timer           timer;
    private JButton         startButton;
    private DBLongTask      task;
    private JTextArea       taskOutput;
    

    public DBPanel (SynonymSearcher ss, TGWikiBrowser wb_new) {
        syn_searcher = ss;
        wb = wb_new;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // jp_db - database (db) wikipedia selection horizontal panel
        JPanel jp_db = new JPanel();
        jp_db.setLayout(new BoxLayout(jp_db, BoxLayout.X_AXIS));
        
        JLabel wkpd_label = new JLabel(wb.tr.getString("Wikipedia"));
        final JComboBox wkpd_combo = new JComboBox(wb.parameters.LANGUAGE);
        wkpd_combo.setSelectedIndex(wb.parameters.getSelectedLanguageIndex());
        wkpd_combo.setPreferredSize(new Dimension(120,30));
        wkpd_combo.setMaximumSize(new Dimension(120,30));
        Action db_select = new AbstractAction() {
            // Listens to the combo box wikipedia (database) selector
            public void actionPerformed(ActionEvent e) {
                String s = (String)((JComboBox)e.getSource()).getSelectedItem();
                wb.parameters.setLanguage(s);
                syn_searcher.getBrowserParameters();
                syn_searcher.getArticleParameters(wb.INITIAL_NODE);
                syn_searcher.connectDatabase();
            }
        };
        wkpd_combo.addActionListener(db_select);
        wkpd_combo.setMaximumRowCount(2);
        
        jp_db.add(wkpd_label);
        jp_db.add(Box.createRigidArea(new Dimension(5,0)));
        jp_db.add(wkpd_combo);
        jp_db.add(Box.createRigidArea(new Dimension(5,0)));
        add(Box.createRigidArea(new Dimension(0,5)));
        add(jp_db);
        add(Box.createRigidArea(new Dimension(0,5)));
        
        
        // Encodings selection (enc_java) and read only text fields with examples of encoded text
        JPanel jp_enc = new JPanel();
        jp_enc.setLayout(new BoxLayout(jp_enc, BoxLayout.Y_AXIS));
        
        JPanel jp_enc1 = new JPanel();
        jp_enc1.setLayout(new BoxLayout(jp_enc1, BoxLayout.X_AXIS));
        
        JPanel jp_enc2 = new JPanel();
        jp_enc2.setLayout(new BoxLayout(jp_enc2, BoxLayout.X_AXIS));
        
        // encoding 1 row encoding from Java sources
        JLabel enc_java_label = new JLabel(wb.tr.getString("Program_and_categories"));
        enc_java = new JTextField(10);
        enc_java.setToolTipText(wb.tr.getString("Examples_ISO8859_1,_Cp1251,_UTF8"));
        enc_java_label.setDisplayedMnemonic('G');
        enc_java.setFocusAccelerator('G');
        enc_java.setText(wb.parameters.getEncJava());
        enc_java_categories = new JTextField(10);
        String tooltip_encoding_result = wb.tr.getString(
                "If_this_field_contains_unreadable_characters_(eg_Q)_then_change_encoding_and_press_Set_to_check_result");
        enc_java_categories.setToolTipText(tooltip_encoding_result);
        
        // encoding 2 row encoding from and to user interface
        JLabel enc_ui_label = new JLabel(wb.tr.getString("User_interface"));
        enc_ui = new JTextField(10);
        enc_ui.setToolTipText(wb.tr.getString("Examples_ISO8859_1,_Cp1251,_UTF8"));
        enc_ui_label.setDisplayedMnemonic('I');
        enc_ui.setFocusAccelerator('I');
        enc_ui.setText(wb.parameters.getEncUI());
        enc_ui_node = new JTextField(10);
        enc_ui_node.setToolTipText(tooltip_encoding_result);
        
        // Button "Set" java encodings
        final JButton enc_java_button = new JButton(wb.tr.getString("Set_enc_java_button"));
        enc_java_button.setMnemonic(KeyEvent.VK_S);
        enc_java_button.setToolTipText(wb.tr.getString("Save_default_value_encoding_and_check_results"));
        enc_java_button.setBackground(Color.decode("#D8C0C0"));
        
        // Button "Set" user interface encodings
        final JButton enc_ui_button = new JButton(wb.tr.getString("Set_enc_ui_button"));
        enc_ui_button.setMnemonic(KeyEvent.VK_E);
        enc_ui_button.setToolTipText(wb.tr.getString("Save_user_interface_encoding_and_check_results"));
        enc_ui_button.setBackground(Color.decode("#D8C0C0"));
        
        Action set_enc_java = new AbstractAction() {
            // Save enc_java to wb parameters file, print decoded categories to the field enc_java_categories
            public void actionPerformed(ActionEvent e) {
                wb.parameters.setEncJava(enc_java.getText());
                
                // 0. Get node name
                String article = wb.parameters.getNode();
                ((SynonymPanel)(wb.synonymTextPanel)).syn_word.setText(article); // update word field (in Synonyms panel)
                
                // 1. Store / restore categories to / from .wikibrowser.server.props
                ParametersPanel p = (ParametersPanel)(((SynonymPanel)wb.synonymTextPanel).params_panel);
                syn_searcher.setBlackListCategory(StringUtil.split("|",
                                p.categories_field.getText()));
                
                syn_searcher.setArticleParameters();
                if (!article.trim().equals("") && wb.parameters.isLogEnabled()) {
                    syn_searcher.getArticleParameters(article);                 // update categories field
                }
                
                // update test string value in Database panel
                enc_java_categories.setText(
                                p.categories_field.getText());
            }
        };
        enc_java_button.addActionListener(set_enc_java);
        
        
        Action set_enc_ui = new AbstractAction() {
            // Save enc_ui to wb parameters file, print decoded articles to the field enc_ui_node
            public void actionPerformed(ActionEvent e) {
                wb.parameters.setEncUI(enc_ui.getText());
                
                // update test string value in Database panel
                String article = ((SynonymPanel)(wb.synonymTextPanel)).syn_word.getText();
                String article_ui = syn_searcher.session.connect.enc.EncodeToUser(article);
                enc_ui_node.setText(article_ui);
            }
        };
        enc_ui_button.addActionListener(set_enc_ui);
        
        jp_enc1.add(Box.createRigidArea(new Dimension(5,0)));
        jp_enc1.add(enc_java_label);
        jp_enc1.add(Box.createRigidArea(new Dimension(5,0)));
        jp_enc1.add(enc_java);
        jp_enc1.add(Box.createRigidArea(new Dimension(5,0)));
        jp_enc1.add(enc_java_button);
        jp_enc1.add(Box.createRigidArea(new Dimension(5,0)));
        jp_enc1.add(enc_java_categories);
        
        jp_enc2.add(Box.createRigidArea(new Dimension(5,0)));
        jp_enc2.add(enc_ui_label);
        jp_enc2.add(Box.createRigidArea(new Dimension(5,0)));
        jp_enc2.add(enc_ui);
        jp_enc2.add(Box.createRigidArea(new Dimension(5,0)));
        jp_enc2.add(enc_ui_button);
        jp_enc2.add(Box.createRigidArea(new Dimension(5,0)));
        jp_enc2.add(enc_ui_node);
        
        jp_enc.add(jp_enc1);
        jp_enc.add(Box.createRigidArea(new Dimension(0,5)));
        jp_enc.add(jp_enc2);
        jp_enc.add(Box.createRigidArea(new Dimension(0,5)));
        
        jp_enc.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(wb.tr.getString("Character_encoding")),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                jp_enc.getBorder()));
        add(jp_enc);
        add(Box.createRigidArea(new Dimension(0,5)));
        
        
        
        // *) Database host (db_host), name, user, password (pass) to connect
        JPanel jp_connect = new JPanel();
        jp_connect.setLayout(new BoxLayout(jp_connect, BoxLayout.Y_AXIS));
        
        JPanel jp_connect1 = new JPanel();
        jp_connect1.setLayout(new BoxLayout(jp_connect1, BoxLayout.X_AXIS));
        
        JPanel jp_connect2 = new JPanel();
        jp_connect2.setLayout(new BoxLayout(jp_connect2, BoxLayout.X_AXIS));
        
        JPanel jp_connect3 = new JPanel();
        jp_connect3.setLayout(new BoxLayout(jp_connect3, BoxLayout.X_AXIS));
        
        // connect 1 row
        JLabel db_host_label = new JLabel(wb.tr.getString("Host"));
        db_host = new JTextField(20);
        db_host.setToolTipText(wb.tr.getString("Wikipedia_MySQL_database_host"));
        db_host_label.setDisplayedMnemonic('H');
        db_host.setFocusAccelerator('H');
        db_host.setText(wb.parameters.getDBHost());
        
        // connect 2 row
        JLabel db_name_label = new JLabel(wb.tr.getString("Database_name"));
        db_name = new JTextField(20);
        db_name.setToolTipText(wb.tr.getString("Wikipedia_MySQL_database_name"));
        db_name_label.setDisplayedMnemonic('M');
        db_name.setFocusAccelerator('M');
        db_name.setText(wb.parameters.getDBName());
        
        // connect 3 row
        JLabel user_label = new JLabel(wb.tr.getString("Username"));
        user_tf = new JTextField(10);
        user_tf.setToolTipText(wb.tr.getString("MySQL_user_name"));
        user_label.setDisplayedMnemonic('R');
        user_tf.setFocusAccelerator('R');
        user_tf.setText(wb.parameters.getUser());
        
        JLabel pass_label = new JLabel(wb.tr.getString("Password"));
        pass_tf = new JPasswordField(6);
        pass_tf.setToolTipText(wb.tr.getString("MySQL_password"));
        pass_label.setDisplayedMnemonic('P');
        pass_tf.setFocusAccelerator('P');
        pass_tf.setText(wb.parameters.getPass());
        
        jp_connect.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(wb.tr.getString("MySQL_database_(DB)_connection_parameters")),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                jp_connect.getBorder()));
        
        jp_connect1.add(Box.createRigidArea(new Dimension(5,0)));
        jp_connect1.add(db_host_label);
        jp_connect1.add(Box.createRigidArea(new Dimension(5,0)));
        jp_connect1.add(db_host);
        jp_connect1.add(Box.createRigidArea(new Dimension(5,0)));
        
        jp_connect2.add(Box.createRigidArea(new Dimension(5,0)));
        jp_connect2.add(db_name_label);
        jp_connect2.add(Box.createRigidArea(new Dimension(5,0)));
        jp_connect2.add(db_name);
        jp_connect2.add(Box.createRigidArea(new Dimension(5,0)));
        
        jp_connect3.add(Box.createRigidArea(new Dimension(5,0)));
        jp_connect3.add(user_label);
        jp_connect3.add(Box.createRigidArea(new Dimension(5,0)));
        jp_connect3.add(user_tf);
        jp_connect3.add(Box.createRigidArea(new Dimension(5,0)));
        
        jp_connect3.add(pass_label);
        jp_connect3.add(Box.createRigidArea(new Dimension(5,0)));
        jp_connect3.add(pass_tf);
        jp_connect3.add(Box.createRigidArea(new Dimension(5,0)));
        
        jp_connect.add(jp_connect1);
        jp_connect.add(Box.createRigidArea(new Dimension(0,5)));
        jp_connect.add(jp_connect2);
        jp_connect.add(Box.createRigidArea(new Dimension(0,5)));
        jp_connect.add(jp_connect3);
        add(jp_connect);
        add(Box.createRigidArea(new Dimension(0,5)));
        
        
        JPanel jp_url = new JPanel();
        jp_url.setLayout(new BoxLayout(jp_url, BoxLayout.X_AXIS));
        
        JLabel wiki_url_label = new JLabel(wb.tr.getString("Wiki_URL"));
        wiki_url_tf = new JTextField(20);
        wiki_url_tf.setToolTipText(wb.tr.getString("Wiki_site_prefix"));
        wiki_url_label.setDisplayedMnemonic('U');
        wiki_url_tf.setFocusAccelerator('U');
        wiki_url_tf.setText(wb.parameters.getWikiURL());
        
        jp_url.add(Box.createRigidArea(new Dimension(5,0)));
        jp_url.add(wiki_url_label);
        jp_url.add(Box.createRigidArea(new Dimension(5,0)));
        jp_url.add(wiki_url_tf);
        jp_url.add(Box.createRigidArea(new Dimension(5,0)));
        
        add(jp_url);
        add(Box.createRigidArea(new Dimension(0,5)));
        
        
        
        // *) Statistics database with progress bar
        JPanel jp_button_and_progressbar = new JPanel();
        jp_button_and_progressbar.setLayout(new BoxLayout(jp_button_and_progressbar, BoxLayout.X_AXIS));
        
        // Button "Draw article node and neighbours"
        final JButton statistics_button = new JButton(wb.tr.getString("Statistics"));
        statistics_button.setToolTipText(wb.tr.getString("Various_database_statistics"));
        statistics_button.setBackground(Color.decode("#D8C0C0"));
        
        Action get_db_statistics = new AbstractAction() {
            // Draw synonym node, edges to l_from and l_to nodes
            public void actionPerformed(ActionEvent e) {
                Connect c = wb.syn_searcher.session.connect;
                statistics_button.setEnabled(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                task.go(db_host.getText(), db_name.getText(), wb);
                timer.start();
            }                  
        };
        statistics_button.addActionListener(get_db_statistics);
        
        
        task = new DBLongTask();
        statistics_button.setActionCommand("start"); // ???

        progressBar = new JProgressBar(0, task.getLengthOfTask());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        jp_button_and_progressbar.add(Box.createRigidArea(new Dimension(5,0)));
        jp_button_and_progressbar.add(statistics_button);
        jp_button_and_progressbar.add(Box.createRigidArea(new Dimension(5,0)));
        jp_button_and_progressbar.add(progressBar);
        jp_button_and_progressbar.add(Box.createRigidArea(new Dimension(5,0)));
        add(jp_button_and_progressbar);
        add(Box.createRigidArea(new Dimension(0,5)));
        
        //Create a text area
        output = new JTextArea();
        output.setFont(new Font("Serif", Font.PLAIN, 12));
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        
        
        //taskOutput = new JTextArea(5, 20);
        //taskOutput.setMargin(new Insets(5,5,5,5));
        output.setEditable(false);
        output.setCursor(null); //inherit the panel's cursor
                                    //see bug 4851758
        
        
        
        JScrollPane areaScrollPane = new JScrollPane(output);
        areaScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setHorizontalScrollBarPolicy(
                        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 850));
        areaScrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(wb.tr.getString("Output_db_panel")),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                areaScrollPane.getBorder()));
        add(areaScrollPane);
        
        
        //Create a timer.
        timer = new Timer(ONE_SECOND, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                progressBar.setValue(task.getCurrent());
                String s = task.getMessage();
                if (s != null) {
                    //output.append(s + "\n");
                    output.setText(s + "\n");
                    output.setCaretPosition(
                            output.getDocument().getLength());
                }
                if (task.isDone()) {
                    Toolkit.getDefaultToolkit().beep();
                    timer.stop();
                    statistics_button.setEnabled(true);
                    setCursor(null); //turn off the wait cursor
                    progressBar.setValue(progressBar.getMinimum());
                }
            }
        });

        
        //Tell accessibility tools about label/textfield pairs.
        wkpd_label.setLabelFor(wkpd_combo);
        enc_java_label.setLabelFor(enc_java);
        enc_ui_label.setLabelFor(enc_ui);
        db_host_label.setLabelFor(db_host);
        db_name_label.setLabelFor(db_name);
        user_label.setLabelFor(user_tf);
        pass_label.setLabelFor(pass_tf);
        wiki_url_label.setLabelFor(wiki_url_tf);
    }
    /** Called when the user presses the start button. */
    /*public void actionPerformed(ActionEvent evt) {
        startButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        task.go();
        timer.start();
    }*/
}
