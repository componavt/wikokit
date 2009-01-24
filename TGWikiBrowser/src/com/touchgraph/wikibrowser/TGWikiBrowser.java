/*
 * TouchGraph LLC. Apache-Style Software License
 *
 *
 * Copyright (c) 2001-2002 Alexander Shapiro. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by 
 *        TouchGraph LLC (http://www.touchgraph.com/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "TouchGraph" or "TouchGraph LLC" must not be used to endorse 
 *    or promote products derived from this software without prior written 
 *    permission.  For written permission, please contact 
 *    alex@touchgraph.com
 *
 * 5. Products derived from this software may not be called "TouchGraph",
 *    nor may "TouchGraph" appear in their name, without prior written
 *    permission of alex@touchgraph.com.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL TOUCHGRAPH OR ITS CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 *
 */


package com.touchgraph.wikibrowser;

import com.touchgraph.wikibrowser.panel.*;
import com.touchgraph.wikibrowser.panel.db.*;
import com.touchgraph.wikibrowser.parameter.*;
import com.touchgraph.wikibrowser.contextmenu.ExpandCategories;

import com.touchgraph.graphlayout.*;
import com.touchgraph.graphlayout.graphelements.GraphEltSet;
import com.touchgraph.graphlayout.interaction.*;
        
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.applet.Applet;
import java.applet.AppletContext;
import java.net.*;

import java.awt.event.*;
import edu.stanford.ejalbert.BrowserLauncher;

import static java.util.ResourceBundle.Control.*;
 
/**  <p><b>TGWikiBrowser:</b> Sets up the main frame, and all the components contained within.
  *  The TGWikiBrowser class handles all actions selected from the toolbar, as well as hyperlink
  *  events from the html pane.
  *
  *  BrowserLauncher is the open source browser launcher used by the TouchGraph WikiBrowser
  *  Copyright 1999-2001 by Eric Albert (ejalbert@cs.stanford.edu)
  *  http://browserlauncher.sourceforge.net/
  * 
  *  @author   Alexander Shapiro                                        
  *  @version  1.02 $Id: TGWikiBrowser.java,v 1.18 2008/04/06 15:30:50 andrew Exp $
  */


public class TGWikiBrowser extends JPanel{
    public TGPanel tgPanel;

    TGLensSet tgLensSet;
    TGUIManager tgUIManager;        
    
    public HVScroll hvScroll;
    public ZoomScroll zoomScroll;
    //public LocalityScroll localityScroll;
        
    public JPopupMenu wikiPopup;    
        
    private JTextField tfSearch;
    private JComboBox maxAddCombo;
    private JComboBox maxExpandCombo;
    private JComboBox localityRadiusCombo;
    private JCheckBox showBackLinksCheckBox;
    
    public JTextPane tpWikiText;
    public String textPaneURL = null;
    
    protected JButton statusButton;
    private WikiNode nodeMOHL; //MouseOverHyperLink
    
    public  GraphEltSet completeEltSet;
    private Stack browseHistory = new Stack();
        
    public static String WIKI_URL;
    public static String WIKI_FILE;
    public static String INITIAL_NODE=null; 
    public static int INITIAL_RADIUS=-1;
    public static boolean INITIAL_SHOW_BACKLINKS=false;

    public SynonymSearcher      syn_searcher;
    public JPanel               synonymTextPanel;
    public JPanel               dbTextPanel;
    //public JPanel               categoryTextPanel;
    public BrowserParameters    parameters;
    
    /** (Tr)anslation i18n resource, use .getString("translate_me"). */
    public ResourceBundle       tr;
    /** Locale defines current working language used in (tr)anslation resource. */
    private static Locale        _locale;    // ru ru_RU en_US
    
    
    /** Shows categories of article or category. */
    public ExpandCategories     expand_categories;
    
    public TGWikiBrowser() {
        
        parameters = new BrowserParameters();
        /*String ss = "пїЅпїЅпїЅпїЅпїЅпїЅ";
        parameters.setNode(ss);
        parameters.getParameters();
        String ss_new = parameters.getNode();
         */
        
        WIKI_URL        = parameters.getWikiURL();
        INITIAL_NODE    = parameters.getNode();
        INITIAL_RADIUS  = parameters.getRadius();
        INITIAL_SHOW_BACKLINKS = parameters.getShowBacklinks();
        
        syn_searcher = new SynonymSearcher(this);
        expand_categories = new ExpandCategories(syn_searcher, this);
        //syn_searcher.session.connect.enc.SetEncodingJavaSourceCode(parameters.getEncJava());
        
        //tr = ResourceBundle.getBundle("com.touchgraph.wikibrowser.MessagesBundle",
        tr = ResourceBundle.getBundle("translation.MessagesBundle",
                _locale, ResourceBundle.Control.getControl(FORMAT_PROPERTIES));
        //System.out.println("Test ResourceBundle: _locale="+_locale+", 'Search' translation is '" + tr.getString("Search")+"'");
        
        //public static final Font SMALL_TAG_FONT = new Font("Times",Font.PLAIN,9);
        WikiNode.setNodeTextFont(new Font("Times",Font.PLAIN,12));
        
        completeEltSet = new GraphEltSet();
        tgPanel = new TGPanel();
        tgPanel.setGraphEltSet(completeEltSet);
        
        tgLensSet = new TGLensSet();                
        hvScroll = new HVScroll(tgPanel, tgLensSet);
        zoomScroll = new ZoomScroll(tgPanel);
        //localityScroll = new LocalityScroll(tgPanel);
        
        
        buildPanel();   // calls syn_searcher.getBrowserParameters();
        buildLens();
        tgPanel.setLensSet(tgLensSet);
        
        addUIs();
        setVisible(true);
                
        WikiNode.setNodeBackDefaultColor(Color.decode("#A03000"));
        zoomScroll.setZoomValue(4);
        
        {
            syn_searcher.connectDatabase();
            if(null == syn_searcher.session.connect.conn) {
                System.out.println("Database is not available. connectDatabase(): syn_searcher.connect.conn is "+syn_searcher.session.connect.conn);
                System.exit(0);
            }
            
            //syn_searcher.initSession();
            syn_searcher.getWikiOneNode(INITIAL_NODE);
            //syn_searcher.getWikiGraphFromDB(INITIAL_NODE);
            //String article = "РћСЂР±РёС‚Р°";  //syn_searcher.getWikiGraphFromDB("РћСЂР±РёС‚Р°");
            
            //getWikiGraph();
        }
        
        if(INITIAL_RADIUS>=0 && INITIAL_RADIUS<=6) 
            localityRadiusCombo.setSelectedIndex(INITIAL_RADIUS);
            
        if(INITIAL_SHOW_BACKLINKS) 
            showBackLinksCheckBox.setSelected(INITIAL_SHOW_BACKLINKS);
                
        WikiNode initialNode;
        if (INITIAL_NODE==null) 
            initialNode = (WikiNode) completeEltSet.getFirstNode();
        else 
            initialNode = (WikiNode) completeEltSet.findNodeLabelContaining(INITIAL_NODE);        
        
        if(initialNode==null) initialNode = (WikiNode) completeEltSet.getFirstNode();
        
        tgPanel.setSelect(initialNode);
        
        setLocale(initialNode);
        tgPanel.fastFinishAnimation();
        setWikiTextPane(initialNode);      

        tgPanel.resetDamper();
      
        
        //localityScroll.setLocalityRadius(3); 
        //localityScroll.getLocalitySB().setMaximum(6);
    }

    private String generateNodeLabel(String labelOrUrl) {
        if(!labelOrUrl.startsWith("http://")) {        
            return labelOrUrl; //It's a label            
        }
        else { // Create a label from the URL                                        
            String urlString = labelOrUrl;
            
            if (urlString.length()<25 && !labelOrUrl.startsWith(WIKI_URL)) {
                String str = urlString.substring(urlString.indexOf("//")+2);
                if (str.length()>40) str = str.substring(0,40);
                return str;
            }
            if (urlString.indexOf("?")>-1) {
                return urlString.substring(urlString.indexOf("?")+1);
            }
            
            if (urlString.lastIndexOf("/")!=urlString.length()-1) {
                return urlString.substring(urlString.lastIndexOf("/")+1);
            }
            
            return urlString.substring(urlString.lastIndexOf("/",urlString.length()-2)+1,urlString.length()-1);                                    
        }                            
    }
    
    
    private WikiNode addWikiNode(String str) {
        WikiNode n;        
            
        n = new WikiNode(generateNodeLabel(str));
        
        if (!str.startsWith("http://")) {
            n.setURL(WIKI_URL + str);
            //n.setURL(WIKI_URL + wikipedia.util.Encodings.FromTo(str, "UTF8", "Cp1251"));
        }
        else {
            n.setURL(str);
            n.setBackColor(Color.decode("#802080"));
        }
    
        try {
            completeEltSet.addNode(n);
        }
        catch(TGException tge) { tge.printStackTrace(); }
            
        return n;
    }
    
    /** Adds node with 'label' (color 'c_text') with background (color 'c_back'). */
    public WikiNode addWikiNode(String label,Color c_text, Color c_back) {
        WikiNode n;        
            
        n = new WikiNode(generateNodeLabel(label));
        
        if (!label.startsWith("http://")) {
            n.setURL(WIKI_URL + label);
        }
        else {
            n.setURL(label);
            n.setNodeTextColor(c_text);
            n.setBackColor(c_back);
        }
    
        try {
            completeEltSet.addNode(n);
        }
        catch(TGException tge) { tge.printStackTrace(); }
            
        return n;
    }

    public void getWikiGraph() throws TGException {
        //Color edgeColors[]={ Color.blue, Color.black, new Color(0,216,0), new Color(64,64,216), new Color(128,0,128), new Color(96,96,96), new Color(0,128,128)};
        String line = "";
        try
        {
            BufferedReader bdbReader;
            if(WIKI_FILE.startsWith("http://")) {
                URLConnection wfConn = (new URL(WIKI_FILE)).openConnection();
                SFSInputStream sfsInputStream = 
                      new SFSInputStream(wfConn.getInputStream(), wfConn.getContentLength());
                bdbReader = new BufferedReader(new InputStreamReader(sfsInputStream));
            }  
            else {
                bdbReader = new BufferedReader(new FileReader(WIKI_FILE));
            }
            
            
            int linenum=0;
            while(line != null)
            {
                linenum++;
                if (linenum%100==0) System.out.println(linenum);
                line = bdbReader.readLine();
                if(line == null || line.trim().length()==0) {continue;}
              
                StringTokenizer st = new StringTokenizer(line);                
                String firststr= st.nextToken();
                WikiNode r = (WikiNode) completeEltSet.findNode(generateNodeLabel(firststr));
                if(r == null) {                    
                    r = addWikiNode(firststr);                
                }           
              //r.setBackColor(edgeColors[linenum % edgeColors.length]);
                WikiNode m = null;
                int midCount = 2;
                if (line.indexOf("|")>-1) {
                    //Color edgeColor =edgeColors[linenum % edgeColors.length];
                    m = new WikiNode("1");
                    completeEltSet.addNode(m);
                    //m.setBackColor(edgeColor);
                    Edge e = new WikiEdge(r,m,20);
                    //e.setColor(edgeColor);
                    completeEltSet.addEdge(e);
                }
                
                while (st.hasMoreTokens()) {           
                    String secondstr="";
                    try {     
                        secondstr = st.nextToken();
                       }
                       catch (NoSuchElementException nsee) { 
                           nsee.printStackTrace(); 
                           continue;
                       }
                       
                     if (secondstr.equals("|")) {
                        //Color edgeColor =edgeColors[linenum % edgeColors.length];
                        m = new WikiNode(""+midCount);
                        completeEltSet.addNode(m);
                        midCount++;
                        //m.setBackColor(edgeColor);
                        WikiEdge e = new WikiEdge(r,m,20);
                        //e.setColor(edgeColor);
                        completeEltSet.addEdge(e);
                        continue;
                    }
                 
                    WikiNode n = (WikiNode) completeEltSet.findNode(generateNodeLabel(secondstr));
                    if(n == null)
                    {
                        n = addWikiNode(secondstr);
                    }
                                 
                    if(m!=null) {
                        Edge e;
                        e = completeEltSet.findEdge(m,n);
                        if(e==null) {                    
                            e = new WikiEdge(m,n,40);                                                    
                            //e.setColor(edgeColors[linenum % edgeColors.length]);
                            completeEltSet.addEdge(e);
                        }  
                                                                        
                        //Edge e2 = completeEltSet.findEdge(r,n);
                        //if(e2==null) {                    
                        //    e2 = new WikiEdge(r,n,1000);                                                                      
                        //    completeEltSet.addEdge(e2);                        
                        //}                                                  
                    }
                    else {
                        Edge e;
                        e = completeEltSet.findEdge(r,n);
                        if(e==null) {                    
                            e = new WikiEdge(r,n,40);                        
                            //e.setColor(edgeColors[linenum % edgeColors.length]);
                            completeEltSet.addEdge(e);
                        }
                        
                        
                    }                                                       
                }                                
             }                      
        }
        catch(IOException exc)
        {
            exc.printStackTrace();
        }
    
        Iterator edgeIterator = completeEltSet.getEdges();
        while(edgeIterator!=null && edgeIterator.hasNext()) {
            WikiEdge e = (WikiEdge) edgeIterator.next();
            e.setLength(10+(e.from.edgeCount() + e.to.edgeCount())+(e.from.edgeCount() * e.to.edgeCount())/5);
        }
                
        System.out.println("Nodes: " +completeEltSet.nodeCount());
        System.out.println("Edges: " +completeEltSet.edgeCount());
    }
        
    
    TGWikiBrowser(JFrame f) {
        this();    
    }

    public TGPanel getTGPanel() {
        return tgPanel;
    }
    
    public URL getDocumentBase() {
        return null;
    }
    
    class HorizontalStretchLens extends TGAbstractLens {
        protected void applyLens(TGPoint2D p) { p.x=p.x*1.5; }
        protected void undoLens(TGPoint2D p) { p.x=p.x/1.5; }
    }
    
    private void buildLens() {
        tgLensSet.addLens(hvScroll.getLens());
        tgLensSet.addLens(zoomScroll.getLens());
        tgLensSet.addLens(new HorizontalStretchLens());
        tgLensSet.addLens(tgPanel.getAdjustOriginLens());       
    }

    private void buildPanel() {
        final JScrollBar horizontalSB = hvScroll.getHorizontalSB();     
        final JScrollBar verticalSB = hvScroll.getVerticalSB();
        final JScrollBar zoomSB = zoomScroll.getZoomSB();
        //final JScrollBar localitySB = localityScroll.getLocalitySB();
    
        setLayout(new BorderLayout());  
        ToolTipManager.sharedInstance().setInitialDelay(0);
        
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        final JPanel topPanel = new JPanel();

        topPanel.setLayout(new GridBagLayout());
        c.fill=GridBagConstraints.HORIZONTAL;


        c.gridx=0;c.weightx=0;c.insets = new Insets(0,5,0,10);
        JButton backButton = new BackButton();
        backButton.setToolTipText(
                tr.getString("Go_back_to_previous_page_/_previously_selected_node"));
        backButton.setBackground(Color.decode("#C0C0D8"));
        topPanel.add(backButton,c);         
        c.gridx=1;c.weightx=0;c.insets = new Insets(0,0,0,0);
        topPanel.add(new Label(
                tr.getString("Search"),
                Label.RIGHT), c);
        c.gridx=2;c.weightx=0.25; c.insets=new Insets(0,0,0,0);        
        tfSearch = new JTextField();
        tfSearch.setToolTipText(
                tr.getString("Press_enter_to_find_node_label_containing_substring"));
        topPanel.add(tfSearch,c); 
                         
        Action findAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {              
                String searchString = tfSearch.getText();
                if (!searchString.trim().equals("")) {
                    WikiNode foundNode = (WikiNode) tgPanel.findNodeLabelContaining(searchString);
                    if (foundNode!=null) {
                        setLocale(foundNode);
                        tgPanel.setSelect(foundNode);
                        setWikiTextPane(foundNode);
                    }
                }    
            }                  
        };
        tfSearch.addActionListener(findAction);

        maxAddCombo = new JComboBox(new String[] {"25","30","35","40","50","60","100","200"});
        maxExpandCombo = new JComboBox(new String[] {"10","15","20","25","30","40","50","70","100"});
        localityRadiusCombo = new JComboBox(new String[] {"0","1","2","3","4","5","6"});        
                
        maxAddCombo.setSelectedIndex(0);
        maxExpandCombo.setSelectedIndex(0);
        localityRadiusCombo.setSelectedIndex(2);
        
        maxAddCombo.setToolTipText(tr.getString("Don't_show_nodes_with_more_E#_of_edges"));
        maxExpandCombo.setToolTipText(tr.getString("Don't_expand_nodes_with_more_then_E#_of_edges"));
        localityRadiusCombo.setToolTipText(tr.getString("Show_nodes_reachable_by_following_Radius#_edges"));
        
        showBackLinksCheckBox = new JCheckBox(tr.getString("Show_BackLinks"),false);
        showBackLinksCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
        showBackLinksCheckBox.setHorizontalAlignment(SwingConstants.CENTER);        
        showBackLinksCheckBox.setToolTipText(
                tr.getString("BackLinks_are_hyperlinks_on_pages_linking_To_the_selected_page"));
        ActionListener setLocaleAL = new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                setLocale(tgPanel.getSelect());
                parameters.setRadius(Integer.parseInt((String) localityRadiusCombo.getSelectedItem()));
            }        
        };
        
        maxAddCombo.addActionListener(setLocaleAL);
        maxExpandCombo.addActionListener(setLocaleAL);
        localityRadiusCombo.addActionListener(setLocaleAL);                               
        showBackLinksCheckBox.addActionListener(setLocaleAL);
                                
        maxAddCombo.setPreferredSize(new Dimension(50,20));
        maxExpandCombo.setPreferredSize(new Dimension(50,20));
        localityRadiusCombo.setPreferredSize(new Dimension(50,20));
        showBackLinksCheckBox.setPreferredSize(new Dimension(150,20));
        
        
        c.gridx=4;c.weightx=0;        
        topPanel.add(new Label(tr.getString("Show_E#"),Label.RIGHT), c);        
        c.gridx=5;c.weightx=0; c.insets=new Insets(0,0,0,0);        
        topPanel.add(maxAddCombo,c);        
        c.gridx=6;c.weightx=0;        
        topPanel.add(new Label(tr.getString("Expand_E#"),Label.RIGHT), c);        
        c.gridx=7;c.weightx=0; 
        topPanel.add(maxExpandCombo,c);                                
        c.gridx=8;c.weightx=0;        
        topPanel.add(new Label(tr.getString("Radius"),Label.RIGHT), c);
        c.gridx=9;c.weightx=0;        
        topPanel.add(localityRadiusCombo,c);                
        c.gridx=10;c.weightx=0;        
        topPanel.add(showBackLinksCheckBox,c);                
        
        c.gridx=11;c.weightx=0;        
        topPanel.add(new Label(tr.getString("Zoom"),Label.RIGHT), c);
        c.gridx=12;c.weightx=0.5;
        c.insets=new Insets(0,0,0,5);                
        topPanel.add(zoomSB,c);                             
        c.insets = new Insets(0,0,0,4);
        JButton stopButton = new JButton(tr.getString("Stop"));
        stopButton.setToolTipText(tr.getString("Stop_graph_motion,_click_twice_to_stop_all_motion"));
        stopButton.setBackground(Color.decode("#D8C0C0"));
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tgPanel.stopMotion();
                tgPanel.fastFinishAnimation();
            }        
        });
        stopButton.setPreferredSize(new Dimension(60,20));
        stopButton.setMargin(new java.awt.Insets(2, 0, 2, 0));
        c.gridx=13;c.weightx=0;        
        topPanel.add(stopButton, c);

        c.insets = new Insets(0,0,0,17);
        JButton helpButton = new JButton("?");
        helpButton.setToolTipText(tr.getString("Help"));
        helpButton.setBackground(Color.decode("#F0F0C0"));
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    tpWikiText.setPage(new File("docs/quick_instructions.html").toURI().toURL());
                }
                catch (Exception ex) { ex.printStackTrace(); }
            }        
        });
        helpButton.setPreferredSize(new Dimension(20,20));        
        helpButton.setMargin(new java.awt.Insets(2, 0, 2, 0));
        c.gridx=14;c.weightx=0;        
        topPanel.add(helpButton, c);
        
        c.insets=new Insets(0,0,0,0);                                        
        add(topPanel, BorderLayout.NORTH);

        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        c.fill = GridBagConstraints.BOTH; 
        c.gridwidth = 1;
        c.gridx = 0; c.gridy = 1; c.weightx = 1; c.weighty = 1;
        scrollPanel.add(tgPanel,c);
        
        c.gridx = 1; c.gridy = 1; c.weightx = 0; c.weighty = 0;
        scrollPanel.add(verticalSB,c);
        
        c.gridx = 0; c.gridy = 2;
        scrollPanel.add(horizontalSB,c);
    
        jsp.setRightComponent(scrollPanel);
        
        JPanel wikiTextPanel = new JPanel();
        wikiTextPanel.setLayout(new BorderLayout());
        tpWikiText = new JTextPane();
        tpWikiText.setEditable(false);

        tpWikiText.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {                   
                processHyperlinkEvent(e);                
            }
        });
        tpWikiText.setEditorKit(new HTMLEditorKit());
        
    
        JScrollPane spWikiText = new JScrollPane(tpWikiText);
        spWikiText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
        
        statusButton = new JButton(new AbstractAction() {
                public void actionPerformed(ActionEvent e) {                     
                    try { BrowserLauncher.openURL(textPaneURL); }
                    catch (Exception ex) { ex.printStackTrace(); } 
                }
            }
        );
        statusButton.setToolTipText("Click to show page in external browser");
        statusButton.setHorizontalAlignment(SwingConstants.LEFT);
        statusButton.setMargin(new java.awt.Insets(2, 0, 2, 0));
        statusButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        wikiTextPanel.add(spWikiText,BorderLayout.CENTER);
        wikiTextPanel.add(statusButton,BorderLayout.SOUTH);
        wikiTextPanel.setPreferredSize(new Dimension(400,100));
        
        JTabbedPane wikiTabbedPane = new JTabbedPane();
        synonymTextPanel = new  SynonymPanel(syn_searcher, this);
        dbTextPanel      = new       DBPanel(syn_searcher, this);
        //categoryTextPanel= new CategoryPanel(syn_searcher, this);
        wikiTabbedPane.addTab(tr.getString("Article"), null,  wikiTextPanel, tr.getString("Article_desc"));
        wikiTabbedPane.setMnemonicAt(0, KeyEvent.VK_A);
        
        wikiTabbedPane.addTab(tr.getString("Database"),         dbTextPanel);
        wikiTabbedPane.setMnemonicAt(1, KeyEvent.VK_D);
        wikiTabbedPane.addTab(tr.getString("Synonyms"),    synonymTextPanel);
        wikiTabbedPane.setMnemonicAt(2, KeyEvent.VK_Y);
        //wikiTabbedPane.addTab("Categories", categoryTextPanel);
        
        syn_searcher.init(this);
        syn_searcher.getBrowserParameters();
        syn_searcher.getArticleParameters(INITIAL_NODE);
        
        
        //jsp.setLeftComponent(wikiTextPanel);
        jsp.setLeftComponent(wikiTabbedPane);
        
        jsp.setDividerLocation(350);
        jsp.setOneTouchExpandable(true);
        add(jsp,BorderLayout.CENTER);
        
        wikiPopup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Toggle Controls");
        ActionListener toggleControlsAction = new ActionListener() {
                boolean controlsVisible = true;
                public void actionPerformed(ActionEvent e) {
                    controlsVisible = !controlsVisible;
                    horizontalSB.setVisible(controlsVisible);
                    verticalSB.setVisible(controlsVisible);                                     
                    topPanel.setVisible(controlsVisible);
                }
            };
        menuItem.addActionListener(toggleControlsAction);
        wikiPopup.add(menuItem);              
    }
    
    private void addUIs() {
        tgUIManager = new TGUIManager();
        WikiNavigateUI navigateUI = new WikiNavigateUI(this);
        tgUIManager.addUI(navigateUI,"Navigate");
        tgUIManager.activate("Navigate");
    }

    public void setLocale(Node n) {
        try {
            if(maxAddCombo!=null && maxExpandCombo!=null) {
                int localityRadius = Integer.parseInt((String) localityRadiusCombo.getSelectedItem());
                int maxAddEdgeCount = Integer.parseInt((String) maxAddCombo.getSelectedItem());
                int maxExpandEdgeCount = Integer.parseInt((String) maxExpandCombo.getSelectedItem());
                boolean unidirectional = !showBackLinksCheckBox.isSelected();      
                tgPanel.setLocale(n,localityRadius,maxAddEdgeCount,maxExpandEdgeCount,unidirectional);            
            }
        }
        catch (TGException tge) { tge.printStackTrace(); }
    }
    
    public void setWikiTextPane(String url) {
        textPaneURL = url;
        new Thread() {
            public void run() {
                try {
                    tpWikiText.setPage(new URL(textPaneURL));
                    statusButton.setText(textPaneURL);
                }
                catch (Exception ex) { ex.printStackTrace(); }    
            }
        }.start();
    }
    
    public void setWikiTextPane(WikiNode n) {
        if(n.getURL()==null || n.getURL().trim().equals("")) return;
        
        //System.out.println(n.getID());
        browseHistory.push(n.getID());
        setWikiTextPane(n.getURL());
        
        // select row in the list of synonyms for the active node in visual graph
        syn_searcher.syn_table.scrollToRow(n.getID());
    }
    
    private class BackButton extends JButton {
        BackButton() {
            super(tr.getString("lt_Back")); //"< Back"  "< Пред"
            
            setPreferredSize(new Dimension(80,20));
            setMargin(new java.awt.Insets(2, 0, 2, 0));
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(browseHistory.size()>1) {
                        try {
                            browseHistory.pop();
                            String nodeId = (String) browseHistory.peek();
                            //System.out.println(nodeId);
                            WikiNode n = (WikiNode) tgPanel.findNode(nodeId);
                            
                            //tgPanel.setSelect(n);
                            processURL(new URL(n.getURL()));
                            // Counteract the previous node being pushed as the next node
                            browseHistory.pop(); 
                        }
                        catch (Exception ex) { ex.printStackTrace(); }    
                    }
                }
            });
        }
    }

    WikiNode findNodeByLabel(String label) {
        WikiNode foundNode = null;
        Collection foundNodes = tgPanel.findNodesByLabel(label);
        if (foundNodes!=null && !foundNodes.isEmpty()) {
            foundNode = (WikiNode) foundNodes.iterator().next();
        }
        return foundNode;
    }

   /** Called by WikiNodeHintUI when the user clicks a link. */
    public void processURL(URL url) {
        String urlString = url.toString();                        
        
        //String nodeName=urlString.substring(urlString.indexOf("?")+1,urlString.length());
        String nodeName = generateNodeLabel(urlString);
        System.out.println(nodeName);
        WikiNode n = findNodeByLabel(nodeName); 
        if (n!=null) {
            if(!n.isVisible()) {
                if(n.edgeCount()<100) {
                    setLocale(n);                        
                }
                else {
                    try {
                        tgPanel.setLocale(n,0);
                    }
                    catch (TGException tge) {}
                }
            }
            tgPanel.setSelect(n);
            
            setWikiTextPane(n);                    
        }
        else {
            setWikiTextPane(urlString);
        }                 
    }
    
    public void processHyperlinkEvent(HyperlinkEvent hyperlinkEvent) {
        URL url = hyperlinkEvent.getURL();
        if (hyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            processURL(url);
            return;
        }            
        if(hyperlinkEvent.getEventType()==HyperlinkEvent.EventType.ENTERED) {
            String urlString = url.toString();
            String nodeName=generateNodeLabel(urlString);
            WikiNode n = findNodeByLabel(nodeName);                         
            
            if (n!=null) {
                if(n.isVisible()) {                          
                    n.setMouseOverHyperlink(true);
                    nodeMOHL = n;
                    tgPanel.repaint();
                }    
                statusButton.setText("IN GRAPH: "+nodeName + " ("+n.edgeCount()+")");
                if (n.edgeCount()>50) {
                    statusButton.setForeground(Color.red);
                } else {
                    statusButton.setForeground(Color.black);
                }
            }
            else {
                statusButton.setText(urlString);
            }            
        }
        else {
            statusButton.setText(textPaneURL);             
            statusButton.setForeground(Color.black);
            if(nodeMOHL!=null) {
                nodeMOHL.setMouseOverHyperlink(false);
                nodeMOHL = null;             
                tgPanel.repaint();
            }
        }
    }   
    
    public static void main(String[] args) {        
        JFrame wikiFrame;
        
        if(args.length != 1) {
            System.out.println("USAGE:");
            System.out.println("TGWikiBrowser LANGUAGE");
            //System.out.println("TGWikiBrowser DATA_FILE(str)|DATA_URL(str) WIKI_URL(str) [INITAL_NODE(str) [LOCALITY_RADIUS(int) [SHOW_BACKLINKS(true/false)]]]");
            System.out.println("EXAMPLES:");
            System.out.println("TGWikiBrowser en");
            System.out.println("TGWikiBrowser ru");
            //System.out.println("TGWikiBrowser meatball.txt http://www.usemod.com/cgi-bin/mb.pl? hypermedium 2 false");
            //System.out.println("TGWikiBrowser http://www.touchgraph.com/meatball.txt http://www.usemod.com/cgi-bin/mb.pl? hypermedium 2 false");            
            System.exit(0); 
        }
        //System.out.println("args[0] (language en, or ru) is "+args[0]);
        TGWikiBrowser._locale = new Locale(args[0]);
        
        /*
        TGWikiBrowser.WIKI_FILE=args[0]; 
        TGWikiBrowser.WIKI_URL=args[1]; 
        
        if (args.length>2) TGWikiBrowser.INITIAL_NODE=args[2];
        if (args.length>3) TGWikiBrowser.INITIAL_RADIUS=Integer.parseInt(args[3]);
        if (args.length>4) TGWikiBrowser.INITIAL_SHOW_BACKLINKS=new Boolean(args[4]).booleanValue();        
         */
        
        wikiFrame = new JFrame("Synarcher 0.12.5 - Synonym searcher");
        TGWikiBrowser wikiPanel = new TGWikiBrowser(wikiFrame);
        wikiFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        
        wikiFrame.getContentPane().add("Center", wikiPanel);
        
        
        //wikiFrame.setSize(1024,768);
        wikiFrame.setSize(720,576); // just for screencasting
        wikiFrame.setVisible(true);           
    }
}