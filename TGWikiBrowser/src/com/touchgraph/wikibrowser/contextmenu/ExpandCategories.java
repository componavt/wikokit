/*
 * ExpandCategories.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser.contextmenu;

import wikipedia.kleinberg.*;
import wikipedia.util.StringUtil;

import com.touchgraph.wikibrowser.*;

import java.util.*;
import java.awt.Color;


/** Shows categories of article or or category. 
 * Implements the item "Expand categories" in Synarcher context menu */
public class ExpandCategories {
    
    TGWikiBrowser                   wb;
    private static SynonymSearcher  ss;
    
    private static String category_prefix = "C:";
    
    public ExpandCategories(SynonymSearcher syn_searcher, TGWikiBrowser wb_new) {
        ss = syn_searcher;
        wb = wb_new;
    }
    
    /** Returns true if second character is column, e.g. "C:" */
    public static boolean isCategory(String title) {
        if(null == title || 3 > title.length())
            return false;

        // ":" is 58
        return 58 == title.charAt(1);
    }
    
    
    public void showNeighboursCategories(String title) {
        if(ss.DEBUG)
            System.out.println("ExpandCategories.showNeighboursCategories, title is " + title);
        
        if(isCategory(title)) {
            forCategory(title);
        } else {
            forArticle(title);
        }
    }
    
    /** Show neighbours category for the category. 
     *
     * @params cat_title Category title, e.g. "C:Science"
     */
    public void forCategory(String cat_title) {
        
        WikiNode n = (WikiNode) wb.tgPanel.findNodeLabelContaining(cat_title);
        if(n == null || null == ss.m_categories || null == ss.session.category_nodes)
            return;
        
        //String title = cat_title.substring(2); // strip "C:"
        String title = StringUtil.getTextBeforeFirstAndSecondColumns(cat_title);
        
        // get categories for the category
        Integer id = ss.m_categories.get(title);
        if(null != id) {
            //System.out.println("The title is found in m_categories");
            
            Category cat = ss.session.category_nodes.get(id);
            
            if(null == cat || null == cat.page_title || 0 == cat.page_title.length() || null == cat.links_in)
                return;
            
            for(int id_to:cat.links_in) {         // links_out: id of categories which are referred by the category
                Category cat_to = ss.session.category_nodes.get(id_to);
                
                if(null == cat_to || null == cat_to.page_title || 0 == cat_to.page_title.length())
                    continue;
                
                // add category node
                String _title = category_prefix + cat_to.getTitleAndIWiki();
                WikiNode r = (WikiNode) wb.completeEltSet.findNode(_title);
                
                if(r == null)
                    r = wb.addWikiNode(_title, Color.MAGENTA.decode("#802080"), Color.WHITE);
                r.setBackColor(ss.color_node_category);
                
                // add edge
                com.touchgraph.graphlayout.Edge e;
                e = wb.completeEltSet.findEdge(r,n);
                
                if(e==null) {                    
                    e = new WikiEdge(r,n,ss.edge_dist_category_category);

                e.setColor(ss.color_edge_category_category);
                    //e.setColor(edgeColors[linenum % edgeColors.length]);
                wb.completeEltSet.addEdge(e);
                
                r.setVisible(true);
                e.setVisible(true);
                }
            }  
        }
        
    }
    /** Shows neighbours categories for the article. */
    public void forArticle(String title) {
        
        WikiNode n = (WikiNode) wb.tgPanel.findNodeLabelContaining(title);
        if(n == null || null == ss.m_articles || null == ss.base_nodes)
            return;
        
        title = StringUtil.getTextBeforeFirstColumn(title);
        
        // get categories for the article
        Integer id = ss.m_articles.get(title);
        if(null != id) {
            Article a = ss.base_nodes.get(id);
            
            // retrieve or take retrieved categories of the article a
            if(null == a.id_categories || 0 == a.id_categories.length) {
                
                List<String> titles_level_1_cats = new ArrayList<String>();
                // let's any article will be expanded, even article with session.source_article_id
                String black_cat = ss.session.category_black_list.inBlackList(id, titles_level_1_cats, -1);
                if(null == titles_level_1_cats || 0 == titles_level_1_cats.size())
                    return;
                
                a.id_categories = Category.getIDByTitle(ss.session.connect, titles_level_1_cats);
            }
            
            for(int id_cat:a.id_categories) {
                Category c = ss.session.category_nodes.get(id_cat);
                if(null == c || null == c.page_title || 0 == c.page_title.length())
                    continue;
                
                // add category node
                String title_cat = category_prefix + c.getTitleAndIWiki();
                WikiNode r = (WikiNode) wb.completeEltSet.findNode(title_cat);
                
                if(r == null)
                    r = wb.addWikiNode(title_cat, Color.WHITE, ss.color_node_category);
                r.setNodeTextColor(Color.WHITE);
                r.setBackColor(ss.color_node_category);
                
                
                // add edge
                com.touchgraph.graphlayout.Edge e;
                e = wb.completeEltSet.findEdge(r,n);
                
                if(e==null) {                    
                    e = new WikiEdge(r,n,ss.edge_dist_aritlce_category);

                    e.setColor(ss.color_edge_article_category);
                    //e.setColor(edgeColors[linenum % edgeColors.length]);
                    wb.completeEltSet.addEdge(e);
                }
                
          //      r.setVisible(true);
        //        e.setVisible(true);
            }  
        } // else Article.createArticleWithCategories() // todo 
    }
    
}
