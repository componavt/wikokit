/*
 * SynArt.java - routines to draw boxes with titles of articles, categories, 
 *              interwiki, redirects, found in SynonymSearcher.java
 *
 * Copyright (c) 2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser;

import wikipedia.data.ArticleIdAndTitle;
import wikipedia.kleinberg.Article;
import wikipedia.kleinberg.SessionHolder;
import wikipedia.sql.Links;

import com.touchgraph.graphlayout.TGException;

import java.awt.Color;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/** Routines to draw found synonyms and related terms. */
public class SynArt {
    
    private SessionHolder   session;
    private TGWikiBrowser   wb;
    private SynonymSearcher ss;
/*
    public void setSession(SessionHolder session) {
        this.session = session;
    }
  */  
    /** Creates a new instance of SynArt */
    public SynArt(SessionHolder session, TGWikiBrowser wb, SynonymSearcher ss) {
        this.session            = session;
        this.wb                 = wb;
        this.ss   = ss;
        
    }

    /** Gets Wiki one node, checks: 
     * it exists in the database, then adds 1) visual node and 2) article by url.
     * The font "Courier" was changed to "Times" in Node.java to draw Russian letters:
     *
     *  public static final Font SMALL_TAG_FONT = new Font("Times",Font.PLAIN,9);
     */
    public void getWikiOneNode(String article) throws TGException {
    //Color edgeColors[]={ Color.blue, Color.black, new Color(0,216,0), new Color(64,64,216), new Color(128,0,128), new Color(96,96,96), new Color(0,128,128)};
        
        if (null == article)
            return;
        
        //String article_in_db = Encodings.FromTo(article, "UTF8", "ISO8859_1");
        //String article_in_db = Encodings.FromTo(article, "Cp1251", "ISO8859_1");
        int p = session.connect.page_table.getIDByTitle(session.connect, article);
        
        //String s = Encodings.FromTo(article, "Cp1251", "UTF8");
        WikiNode r = (WikiNode) wb.completeEltSet.findNode(article);
        if(r == null) {
            r = wb.addWikiNode(article, ss.color_dark_magenta, Color.WHITE);
            //r = wb.addWikiNode(article);
        }
    }
    
    /** Gets neighbours Wiki articles from the database, draws it. */
    public void drawNeighboursFromDB(String article, 
                                    int increment
            ) throws TGException {
        //Color edgeColors[]={ Color.blue, Color.black, new Color(0,216,0), new Color(64,64,216), new Color(128,0,128), new Color(96,96,96), new Color(0,128,128)};
        
        if (null == article)
            return;
        
        //String article_in_db = Encodings.FromTo(article, "UTF8", "ISO8859_1");    // UTF8 Cp1251
        int p = session.connect.page_table.getIDByTitle(session.connect, article);
        if(-1 == p)
            return;
        
        session.clear();
        ss.initSession();
        
        Map<String,Set<String>> m_out = new HashMap<String,Set<String>>();
        Map<String,Set<String>> m_in  = new HashMap<String,Set<String>>();
        
        Article[] a1 = new Article[1];
        a1[0] = new Article();
        a1[0].page_id = p;            //a1[0].page_title = article;
        
        // Get articles which refers to the article p
        //Article[] a_from = Links.getLFromByLTo(session, p, article, root_set_size, m_out, m_in);
        int n_limit2 = -1;
        Article[] a_from = Links.getLFromByLTo(session, a1, increment, n_limit2, m_out, m_in);
        
        // Get articles which are refer by p
        Article[] result_nodes = Links.getLToByLFrom(session, a1, -1, m_out, m_in);
            m_out.clear();
            m_in. clear();
        result_nodes = Article.joinUnique(result_nodes, a_from);
        
        //WikiNode r = (WikiNode) completeEltSet.findNode(generateNodeLabel());
        WikiNode r = (WikiNode) wb.completeEltSet.findNode(article);
        if(r == null) {
            r = wb.addWikiNode(article, ss.color_dark_magenta, Color.WHITE);
        }
        
        if(null != result_nodes) {
            //r.setBackColor(edgeColors[linenum % edgeColors.length]);
            WikiNode m = null;
             
            //while (st.hasMoreTokens()) {
            for(int i=0; i<result_nodes.length; i++) {
                Article a = result_nodes[i];
            
                //WikiNode n = (WikiNode) wb.completeEltSet.findNode(generateNodeLabel(a.page_title));
                WikiNode n = (WikiNode) wb.completeEltSet.findNode(a.page_title);
                if(n == null)
                {
                    n = wb.addWikiNode(a.page_title, ss.color_dark_magenta, Color.WHITE);
                }

                /*
                if(m!=null) {
                    com.touchgraph.graphlayout.Edge e;
                    e = wb.completeEltSet.findEdge(m,n);
                    if(e==null) {                    
                        e = new WikiEdge(m,n,40);                                                    
                        //e.setColor(edgeColors[linenum % edgeColors.length]);
                        wb.completeEltSet.addEdge(e);
                        e.setVisible(false);
                    }
                }
                else {*/
                    com.touchgraph.graphlayout.Edge e;
                    e = wb.completeEltSet.findEdge(r,n);
                    if(e==null) {                    
                        e = new WikiEdge(r,n,40);                        
                        //e.setColor(edgeColors[linenum % edgeColors.length]);
                        wb.completeEltSet.addEdge(e);
                    }
                //}
            } // end for
        } // enf if
        
        Iterator edgeIterator = wb.completeEltSet.getEdges();
        while(edgeIterator!=null && edgeIterator.hasNext()) {
            WikiEdge e = (WikiEdge) edgeIterator.next();
            e.setLength(10+(e.from.edgeCount() + e.to.edgeCount())+(e.from.edgeCount() * e.to.edgeCount())/5);
        }
                
        System.out.println("Nodes: " +wb.completeEltSet.nodeCount());
        System.out.println("Edges: " +wb.completeEltSet.edgeCount());
    }
    
    /** Hides all nodes and edges */
    public void hideAll() {
        
        // hides nodes
        Iterator nodeIterator = wb.completeEltSet.getNodes();
        while(nodeIterator!=null && nodeIterator.hasNext()) {
            ((WikiNode) nodeIterator.next()).setVisible(false);
        }
            
        // hides edges
        Iterator edgeIterator = wb.completeEltSet.getEdges();
        while(edgeIterator!=null && edgeIterator.hasNext()) {
            ((WikiEdge) edgeIterator.next()).setVisible(false);
        }
    }
    
    /** Makes visible edges which tied visible_article with other nodes. */
    public void setVisibleEdges(int visible_article, Map<Integer, Article> nodes)
    {
        Article a = nodes.get(visible_article);
        //WikiNode r = (WikiNode) wb.completeEltSet.findNode(a.page_title + a.iwiki_title);
        WikiNode r = (WikiNode) wb.completeEltSet.findNode(a.getTitleAndIWiki());
        if(r == null)
            r = wb.addWikiNode(a.page_title + a.iwiki_title, ss.color_dark_magenta, Color.WHITE);
        
        if(null == a.links_out)
            return;
            
        for(int i=0; i<a.links_out.length; i++) {
            Article al = nodes.get(a.links_out[i]);
            if(null == al)
                continue;
            //WikiNode n = (WikiNode) wb.completeEltSet.findNode(al.page_title + a.iwiki_title);
            WikiNode n = (WikiNode) wb.completeEltSet.findNode(al.getTitleAndIWiki());
            if(n == null)
                continue;

            com.touchgraph.graphlayout.Edge e;
            e = wb.completeEltSet.findEdge(r,n);
            if(e!=null)                    
                e.setVisible(true);
            //e.setColor(edgeColors[linenum % edgeColors.length]);
        }
        
        /*
        if(null != result_nodes) {
            //r.setBackColor(edgeColors[linenum % edgeColors.length]);
            WikiNode m = null;
            int midCount = 2;
             
            //while (st.hasMoreTokens()) {
            for(int i=0; i<result_nodes.length; i++) {
                Article a = result_nodes[i];
            
                //WikiNode n = (WikiNode) wb.completeEltSet.findNode(generateNodeLabel(a.page_title));
                WikiNode n = (WikiNode) wb.completeEltSet.findNode(a.page_title);
                if(n == null)
                {
                    n = wb.addWikiNode(a.page_title);
                }

                if(m!=null) {
                    com.touchgraph.graphlayout.Edge e;
                    e = wb.completeEltSet.findEdge(m,n);
                    if(e==null) {                    
                        e = new WikiEdge(m,n,40);                                                    
                        //e.setColor(edgeColors[linenum % edgeColors.length]);
                        wb.completeEltSet.addEdge(e);
                        e.setVisible(false);
                    }
                }
                else {
                    com.touchgraph.graphlayout.Edge e;
                    e = wb.completeEltSet.findEdge(r,n);
                    if(e==null) {                    
                        e = new WikiEdge(r,n,40);                        
                        //e.setColor(edgeColors[linenum % edgeColors.length]);
                        wb.completeEltSet.addEdge(e);
                    }
                }
            } // end for
        } // enf if
        */
        Iterator edgeIterator = wb.completeEltSet.getEdges();
        while(edgeIterator!=null && edgeIterator.hasNext()) {
            WikiEdge e = (WikiEdge) edgeIterator.next();
            e.setLength(10+(e.from.edgeCount() + e.to.edgeCount())+(e.from.edgeCount() * e.to.edgeCount())/5);
        }
                
        System.out.println("Nodes: " +wb.completeEltSet.nodeCount());
        System.out.println("Edges: " +wb.completeEltSet.edgeCount());
    }
    
    
    /** Draws nodes without redirects. */
    private void drawNodes(Map<Integer, Article> nodes)
    {
        int                 i;
        Iterator<Integer>   it = nodes.keySet().iterator();
        
        // 1. add nodes
        while (it.hasNext()) {
            int  id   = it.next();
            Article a = nodes.get(id);
            
            //file_dot.PrintNL(node.GraphVizNode());
            //file_dot.PrintNL(node.GraphVizLinksOut());
            
            // add node
            //WikiNode r = (WikiNode) wb.completeEltSet.findNode(a.page_title + a.iwiki_title);
            WikiNode r = (WikiNode) wb.completeEltSet.findNode(a.getTitleAndIWiki());
            if(r == null)
                r = wb.addWikiNode(a.getTitleAndIWiki(), ss.color_dark_magenta, Color.WHITE);
                //r = wb.addWikiNode(a.page_title + a.iwiki_title);
            r.setVisible(false);
            
            // Set color for different types of nodes. see types descriptions 
            // in wikipedia.kleinberg/Article.java
            // See wikipedia.kleinberg/LinksBaseSet.java
            // See wikipedia.kleinberg/Authorities.java
            
            switch (a.type.toInt()) {
                case -4: r.setBackColor(Color.decode("#FF8C00")); break;  // selected (rated) synonyms by user, see SetTypeForRatedSynonyms
                case -2: r.setBackColor(Color.decode("#FF4500")); break;  // hubs   - orange red #FF4500 (brown #6d5925)
                case -1: r.setBackColor(Color.decode("#68C309")); break;  // source node - green
                case  0: r.setBackColor(Color.decode("#617297")); break;  // best authorities - blue-grey
                case  1: r.setBackColor(Color.decode("#9B267D")); break;  // root_nodes - violet
                case  2: r.setBackColor(Color.decode("#A9A9A9")); break;  // base_(not_root)_nodes - dark-grey #A9A9A9 (dark-slate-grey #2F4F4F)
                default: r.setBackColor(Color.decode("#000000")); break;  // unreachable - black
                
                //r.setBackColor(Color.decode("#1D00E2"));     //blue - categories
            }// 802080 14e6ee 617297 6d5925 13388c
            r.setNodeTextColor(Color.decode("#FFFFFF"));
        }
    }
    
    
    /** Draws redirect-nodes. */
    private void drawRedirects (Map<Integer, Article> nodes)
    {
        int                 i;
        Iterator<Integer>   it = nodes.keySet().iterator();
        WikiNode            r_from = null;
        
        for (Article redirect_to:nodes.values()) {
            
            WikiNode r_redirect_to = (WikiNode) wb.completeEltSet.findNode(redirect_to.getTitleAndIWiki());
            if(r_redirect_to == null)
                continue;
            
            // redirect from 'aid_from' to 'redirect_to'
            for(ArticleIdAndTitle aid_from:redirect_to.redirect) {
                
                // 1. add node
                //WikiNode r = (WikiNode) wb.completeEltSet.findNode(a.getTitleAndIWiki());
                r_from = (WikiNode) wb.completeEltSet.findNode(aid_from.title);
                if(r_from == null)
                    r_from = wb.addWikiNode(aid_from.title, ss.color_dark_magenta, Color.WHITE);
                r_from.setVisible(false);

                // Set color for redirect
                r_from.setBackColor(Color.decode("#A4FF00"));       // green-yellow
                r_from.setNodeTextColor(Color.decode("#432E9F"));   // violet
                
                // 2. add edge
                com.touchgraph.graphlayout.Edge e;
                e = wb.completeEltSet.findEdge(r_from, r_redirect_to);
                if(e==null) {                    
                    e = new WikiEdge(r_from, r_redirect_to, 40);
                    //e.setColor(edgeColors[linenum % edgeColors.length]);
                    wb.completeEltSet.addEdge(e);
                    e.setColor(Color.decode("#A4FF00"));     
                    e.setVisible(false);
                }
            }
        }
        if(null != r_from) { 
            // else first category in ExpandCategories.forArticle 
            // will have invisible text
            r_from.setNodeTextColor(Color.WHITE);
        }
    }
    
    
    /** Draws edges. */
    private void drawEdges(Map<Integer, Article> nodes)
    {
        int                 i;
        Iterator<Integer>   it = nodes.keySet().iterator();
        
        // 2. add edges
        it = nodes.keySet().iterator();
        while (it.hasNext()) {
            int  id   = it.next();
            Article a = nodes.get(id);
            
            WikiNode r = (WikiNode) wb.completeEltSet.findNode(a.getTitleAndIWiki());
            if(r == null)
                continue;
            
            if(null != a.links_out) {
                //if (Article.HUB_TYPE == a.type)
                //    bold_edge = " [style=bold]";
                for(i=0; i<a.links_out.length; i++) {
                    //result += "W" + cur_id + " -> " + "W" + links_out[i] + bold_edge + ";\n";
                    
                    Article al = nodes.get(a.links_out[i]);
                    if(null == al)
                        continue;
                    WikiNode n = (WikiNode) wb.completeEltSet.findNode(al.getTitleAndIWiki());
                    if(n == null)
                        continue;

                    com.touchgraph.graphlayout.Edge e;
                    e = wb.completeEltSet.findEdge(r,n);
                    if(e==null) {                    
                        e = new WikiEdge(r,n,40);
                        //e.setColor(edgeColors[linenum % edgeColors.length]);
                        wb.completeEltSet.addEdge(e);
                        e.setVisible(false);
                    }
                }
            }
            
            /*if( bdraw_categories && null != id_categories)
            {
                for(i=0; i<id_categories.length; i++) {
                    result += "W" + cur_id + " -> " + "C" + id_categories[i] + " [style=dotted]" + ";\n";
                }
            }*/
        }
    }
    
    
    /** Draws nodes (Articles) and edges between them. Draws redirect articles 
     * if 'show_redirects' is true.
     */
    public void drawNodesEdges(Map<Integer, Article> nodes, boolean show_redirects)
    {
        drawNodes(nodes);
        drawEdges(nodes);
        
        if(show_redirects) {
            drawRedirects(nodes);
        }
    }
}
