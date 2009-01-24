/*
 * SynonymSearcher.java - Interface to kleinberg.jar synonym searcher in 
 *  Wikipedia database.
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package com.touchgraph.wikibrowser;

import com.touchgraph.wikibrowser.panel.*;
import com.touchgraph.wikibrowser.panel.db.*;
import com.touchgraph.wikibrowser.parameter.*;
import com.touchgraph.graphlayout.*;

import wikipedia.kleinberg.*;
import wikipedia.sql.*;
import wikipedia.util.*;
import wikipedia.clustering.*;
import wikipedia.*;

import java.awt.Color;
import java.io.*;
import java.util.*;
import java.text.*;

/** Interface to kleinberg.jar (algorithms for synonym searcher and Wikipedia 
 * API, Wikipedia in MySQL). */
public class SynonymSearcher {
    public boolean DEBUG = true;
    
    private TGWikiBrowser   wb;
    
    Authorities             auth;
    public Map<Integer, Article> base_nodes;
    
    /** <page_title to page_id of article (nodes above)> */
    public Map<String, Integer> m_articles;
    
    /** <pate_title of category, page_id of category>, category object is in session.category_nodes */
    public Map<String, Integer> m_categories;
    
    //Connect                 connect;
    public SessionHolder    session;
    DumpToGraphViz          dump;
    
    /** Synonyms table (results of search) */
    public ResultTableModel syn_table;
    
    /** Categories table */
    public CategoryTableModel cat_table;
    
    public static int edge_dist_aritlce_category = 50;
    public static int edge_dist_category_category = 20;

    
    
    /** Blue */
    public static final Color color_node_category           = Color.decode("#1D00E2");   
    /** Black */
    public static final Color color_edge_article_category   = Color.decode("#000000");
    /** Light-brown */
    public static final Color color_edge_category_category  = Color.decode("#D57413");
    
    /** Dark Magenta #8B008B */
    public static final Color color_dark_magenta            = Color.decode("#8B008B");
    
    private SynArt  syn_art;
    
    /*************************************************/
    /* Parameters, should be defined by user in GUI  */
    /*                                               */

    /** Article parameters, 
     *  see comments in {@link com.touchgraph.wikibrowser.parameter.ArticleParameters}
     */
    protected int           root_set_size;
    protected int           increment;
    protected int           n_synonyms;
    protected float         eps_error;
    protected int           categories_max_steps;
    protected String[]      category_blacklist;
    
    /** Browser parameters,
     *  see comments in {@link com.touchgraph.wikibrowser.parameter.BrowserParameters}
     */
    protected String        lang;
    protected String        db_host, db_name, user, pass;
    
    protected long          t_start, t_end;
    protected float         t_work, t_max;  // time of one cycle's work
    
    protected String        enc_java    = "UTF8";
    protected String        enc_ui      = "Cp1251";
    /*                                               */
    /* eo Parameters                                 */
    /*************************************************/
    
    // todo add browser parameters
    protected static final boolean show_redirects = true;
    
    
    private final static List<String>   NULL_STRING_LIST  = new ArrayList<String>(0);
    private final static String[]       NULL_STRING_ARRAY = new String[0];
    
    /** Creates a new instance of SynonymSearcher, GUI is null yet */
    public SynonymSearcher(TGWikiBrowser wb) {
        this.wb = wb;
        auth    = new Authorities();
        
        session = new SessionHolder();
        session.initObjects();
        
        dump    = new DumpToGraphViz();
        //session.connect = connect;
        session.dump = dump;
        
        syn_art = new SynArt(session, wb, this);
    }
    
    /** Set pointers to GUI objects */
    void init(TGWikiBrowser t) {
        syn_table = ((ResultTablePanel)((SynonymPanel)wb.synonymTextPanel).result_table_panel).  table;
        cat_table = ((CategoryPanel)   ((SynonymPanel)wb.synonymTextPanel).category_table_panel).table;
    }
    
    /**************************************
    /** Set and get parameters functions */
    
    
    public void connectDatabase() {
        session.connect.Close();
        session.connect.Open(db_host, db_name, user, pass);
    }
    
    
    /** Loads previous browser parameters from /homedir/.wikibrowser.server.props
     *  Prints to GUI fields
     */
    public void getBrowserParameters() {
        wb.parameters.setSessionHolder(session);
        wb.parameters.updateEncodingsToSession();
        //session.connect.enc.SetEncodingJavaSourceCode(wb.parameters.getEncJava());
        
        lang      = wb.parameters.getLangCode();
        DBPanel d = (DBPanel)wb.dbTextPanel;
        
        enc_java  = wb.parameters.getEncJava();
        d.enc_java.setText(enc_java);
        
        enc_ui = wb.parameters.getEncUI();
        d.enc_ui.setText(enc_ui);
        
        db_host = wb.parameters.getDBHost();
        d.db_host.setText(db_host);
        
        db_name = wb.parameters.getDBName();
        d.db_name.setText(db_name);
        
        user = wb.parameters.getUser();
        d.user_tf.setText(user);
        
        pass = wb.parameters.getPass();
        d.pass_tf.setText(pass);
        
        wb.WIKI_URL     = wb.parameters.getWikiURL();
        d.wiki_url_tf.setText(wb.WIKI_URL);
        
        wb.INITIAL_NODE = wb.parameters.getNode();
        ((SynonymPanel)wb.synonymTextPanel).syn_word.setText(wb.INITIAL_NODE);
    }
    
    /** Saves browser parameters to /homedir/.wikibrowser.server.props  */
    public void setBrowserParameters() {
        wb.parameters.setLanguage(lang);
        wb.parameters.setEncJava(enc_java );
        wb.parameters.setEncUI(enc_ui);
        
        wb.parameters.setDBHost(db_host);
        wb.parameters.setDBName(db_name);
        wb.parameters.setUser(user);
        wb.parameters.setPass(pass);
        wb.parameters.setWikiURL(wb.WIKI_URL);
        wb.parameters.setNode(wb.INITIAL_NODE);
    }
    
    /** Load previous search parameters and results from ./log_dir/article.ru.params,
     *  Print to GUI fields
     */
    public void getArticleParameters(String article) {
        if (!article.trim().equals("")) {
            
            ArticleParameters ap = new ArticleParameters(wb.parameters.getLogDir(), 
                    getLatinitsaFilename(article, lang), session);
            lang = wb.parameters.getLangCode();
            ap.setLang(lang);
            
            ParametersPanel p = (ParametersPanel)(((SynonymPanel)wb.synonymTextPanel).params_panel);
            
            root_set_size = ap.getRootSetSize();
            p.root_size_tf.setValue(root_set_size);
            
            increment = ap.getIncrement();
            p.inc_tf.setValue(increment);
            
            n_synonyms = ap.getNSynonyms();
            p.nsyn_tf.setValue(n_synonyms);
            
            eps_error = ap.getEpsError();
            p.eps_tf.setValue(eps_error);
            
            categories_max_steps = ap.getCategoriesMaxSteps();
            p.max_steps_tf.setValue(categories_max_steps);
            
            category_blacklist = ap.getCategoryBlackList();
            p.categories_field.setText( StringUtil.join("|", category_blacklist));
            
            syn_table.createRatedSynonymList(ap.getRatedSynonyms());
            syn_table.updateTable();
        }
    }
    
    /** Saves successfull search parameters and results to ./log_dir/article.params  */
    public void setArticleParameters() {
        String article_fn = wb.INITIAL_NODE;    // filename
        if (!article_fn.trim().equals("") && wb.parameters.isLogEnabled()) {
            session.connect.enc.SetEncodingJavaSourceCode(wb.parameters.getEncJava());
            
            ArticleParameters ap;
            ap = new ArticleParameters(wb.parameters.getLogDir(), getLatinitsaFilename(article_fn, lang), session);
            ap.setLang(lang);
            ap.setCategoryBlackList(category_blacklist);
            ap.setRatedSynonyms(syn_table.getRatedSynonymList());
            
            ap.setRootSetSize(root_set_size);
            ap.setIncrement(increment);
            ap.setNSynonym(n_synonyms);
            ap.setEpsError(eps_error);
            ap.setCategoriesMaxSteps(categories_max_steps);
            
            ap.saveParameters();
        }
    }
    private String getLatinitsaFilename(String article, String language) {
        String fn = StringUtilRegular.encodeRussianToLatinitsa(article,
                        session.connect.enc.GetJavaEnc(),
                        session.connect.enc.GetInternalEnc());
        return fn.concat(".").concat(language);
    }
    
    public void setRootSetSize(int i) { root_set_size = i;  }
    public void setIncrement  (int i) { increment = i;      }
    public void setNSynonyms  (int i) { n_synonyms = i;     }
    public void setEpsError   (float f){ eps_error = f;     }
    public void setCategoriesMaxSteps (int f)   { categories_max_steps = f; }
    public void setBlackListCategory(String[] s){ category_blacklist = s; }
    
    /*********** eo parameters functions */
    /*************************************/
    
    /** Gets neighbours Wiki articles from the database, draws it. */
    public void drawNeighboursFromDB(String article) {
        try {
            syn_art.drawNeighboursFromDB(article, increment);
        } catch (TGException tge) {tge.printStackTrace();}
    }
    
    /** Gets Wiki one node, checks: 
     * it exists in the database, then adds 1) visual node and 2) article by url.
     * The font "Courier" was changed to "Times" in Node.java to draw Russian letters:
     *
     *  public static final Font SMALL_TAG_FONT = new Font("Times",Font.PLAIN,9);
     */
    public void getWikiOneNode(String article) {
        try {
            syn_art.getWikiOneNode(article);
        } catch (TGException tge) {tge.printStackTrace();}
    }
    /** Hides all nodes and edges */
    public void hideAll() {
        syn_art.hideAll(); 
    }
  /*  
    public void showNeighboursCategories(String title) {
        if(DEBUG)
            System.out.println("SS.showNeighboursCategories, title is " + title);
        
        WikiNode n = (WikiNode) wb.tgPanel.findNodeLabelContaining(title);
        if(n == null || null == m_articles || null == base_nodes)
            return;
        
        // get categories for the article
        Integer id = m_articles.get(title);
        if(null != id) {
            System.out.println("The title is found in m_articles");
            
            Article a = base_nodes.get(id);
            
            // retrieve or take retrieved categories of the article a
            if(null == a.id_categories || 0 == a.id_categories.length) {
                
                List<String> titles_level_1_cats = new ArrayList<String>();
                String black_cat = session.category_black_list.inBlackList(id, titles_level_1_cats);
                if(null == titles_level_1_cats || 0 == titles_level_1_cats.size())
                    return;
                
                a.id_categories = Category.getIDByTitle(session.connect, titles_level_1_cats);
            }
            
            for(int id_cat:a.id_categories) {
                Category c = session.category_nodes.get(id_cat);
                if(null == c.page_title || 0 == c.page_title.length())
                    continue;
                
                // add category node
                String title_cat = "C:" + c.page_title;
                WikiNode r = (WikiNode) wb.completeEltSet.findNode(title_cat);
                
                if(r == null)
                    r = wb.addWikiNode(title_cat);
                r.setBackColor(Color.decode(color_node_category));
                
                // add edge
                com.touchgraph.graphlayout.Edge e;
                e = wb.completeEltSet.findEdge(r,n);
                
                if(e==null) {                    
                    e = new WikiEdge(r,n,edge_dist_aritlce_category);

                e.setColor(Color.decode(color_edge_article_category));     
                    //e.setColor(edgeColors[linenum % edgeColors.length]);
                    wb.completeEltSet.addEdge(e);
                }
                
                r.setVisible(true);
                e.setVisible(true);
            }  
        } // else Article.createArticleWithCategories() // todo 
    }
*/    
    
    public void SetNodeAndTextPane(String s) {
        if (DEBUG) {
            System.out.println("SetNodeAndTextPane() called.");
        }
        WikiNode n = (WikiNode) wb.tgPanel.findNodeLabelContaining(s);
        if (n!=null) {
            wb.setLocale(n);
            wb.tgPanel.setSelect(n);
            wb.setWikiTextPane(n);
        }
    }
    
    /** Initializes object "session" with current parameters (depends on language):
     *  connect, cb (category_black_list), categories_max_steps.
     **/
    public void initSession() {
        List<String> cb = null;
        if(null != category_blacklist && 0 < category_blacklist.length) {
            cb = Arrays.asList(category_blacklist);
        }
        session.Init(session.connect, cb, categories_max_steps);
        
        String database_encoding = "ISO8859_1";
        //String enc_java = "UTF8";
        //String enc_ui = "Cp1251";
        session.connect.enc.SetEncodings(database_encoding, enc_java, enc_ui);
        
        if(null != m_articles)
            m_articles.clear();
        if(null != m_categories)
            m_categories.clear();
        
        session.connect.ReOpenIfInvalid();
        
        wb.completeEltSet.clearAll();
    }
    
    /** Adds category to the blacklist, saves to word settings file, 
     * prints blacklist categories in parameters panel.
     */
    public void addCategoryToBlackList(String category) {
        List<String> cb = NULL_STRING_LIST;
        if(null != category_blacklist && 0 < category_blacklist.length) {
            cb = Arrays.asList(category_blacklist);
        }
        if(!cb.contains(category)) {
            List<String> cb_new = new ArrayList<String>();
            cb_new.addAll(cb);
            cb_new.add(category);
            category_blacklist = (String[])cb_new.toArray(NULL_STRING_ARRAY);
            
            setArticleParameters();
        
            ParametersPanel p = (ParametersPanel)(((SynonymPanel)wb.synonymTextPanel).params_panel);
            p.categories_field.setText( StringUtil.join("|", category_blacklist));
        }
    }
    
    /** Removes category from the blacklist, saves blacklist to word settings 
     * file, updates blacklist in parameters panel.
     */
    public void removeCategoryFromBlackList(String category) {
        List<String> cb = NULL_STRING_LIST;
        if(null != category_blacklist && 0 < category_blacklist.length) {
            cb = Arrays.asList(category_blacklist);
        }
        if(cb.contains(category)) {
            List<String> cb_new = new ArrayList<String>();
            cb_new.addAll(cb);
            cb_new.remove(category);
            category_blacklist = (String[])cb_new.toArray(NULL_STRING_ARRAY);
        
            setArticleParameters();
        
            ParametersPanel p = (ParametersPanel)(((SynonymPanel)wb.synonymTextPanel).params_panel);
            p.categories_field.setText( StringUtil.join("|", category_blacklist));
        }
    }
    
    
    
    /** Setup dump and log of search of synonyms to log directory.
     * The Session.Dump object updated by BrowserParameters 
     */
    public void setupDumpAndLog() {
        dump.enable_file_dot = false;
                
        if(wb.parameters.isLogEnabled()) {
            dump.file.SetDir(wb.parameters.getLogDir() + System.getProperty("file.separator"));
            
            String a, a2, fn, fn2;
            a = ((SynonymPanel)wb.synonymTextPanel).syn_word.getText();
            a2 = session.connect.enc.EncodeFromUser(a);
            fn = getLatinitsaFilename( a, lang);
            fn2 = getLatinitsaFilename(a2, lang);
            
            dump.file.SetFilename(fn + ".txt");
            dump.file.Open(false, "Cp1251"); // append = false
            session.dump = dump;
        } else {
            session.dump = null;
        }
    }
    
    
    /** Set type type_n to nodes, which are rated as synonyms by user (checked 
     * in result table).
     */
    public void SetTypeForRatedSynonyms(Map<Integer, Article> nodes, NodeType type_n) {
        List<String> rated = syn_table.getRatedSynonymList();
        if(null == rated) {
            return;
        }
        //System.out.println("rated=" +rated);
        for(Article a:nodes.values()) {
            for(String r:rated) {
                if (r.equalsIgnoreCase(a.page_title)) {
                    a.type = type_n;
                    break;
                }
            }
        }
    }
    
    
    /** Search synonyms for article in the wikipedia */
    public void SearchSynonyms(String article) {
        wb.completeEltSet.clearAll();
        SynonymPanel sp = (SynonymPanel)wb.synonymTextPanel;
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                   DateFormat.LONG,
                                   new Locale("en","US"));
        String today = formatter.format(new Date());
        //String article_in_db = Encodings.FromTo(article, "UTF8", "ISO8859_1");     // ISO8859_1 Cp1251
        
        System.out.println ("The word '" + session.connect.enc.EncodeToUser(article) + "' is processing...");
        ResultTablePanel rtp = (ResultTablePanel)sp.result_table_panel;
        rtp.output.setText("The word '" + session.connect.enc.EncodeToUser(article) + "' is processing...");
        
        CategoryPanel cp = (CategoryPanel)sp.category_table_panel;
        cp.output.setText("");
        
        t_start = System.currentTimeMillis();
        session.clear();
        initSession();
        
        setupDumpAndLog();
        
        //Map<Integer, Article>  
        List<String> rated = syn_table.getRatedSynonymList();
        base_nodes = LinksBaseSet.CreateBaseSet(article, rated, session, root_set_size, increment);
        //dump.file.PrintNL("Total_steps_while_categories_removing:"+session.total_categories_passed);
        //dump.file.Flush();

        List<Article> synonyms = auth.Calculate(base_nodes, eps_error, n_synonyms, session);
        
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec

        if (null != synonyms) {
            String s = "\n\ntime sec:" + t_work + 
                            " iter:" + auth.iter +
                            " vertices:" + base_nodes.values().size() +
                            " edges:" + DCEL.CountLinksIn(base_nodes) +
                            "\nroot_set_size:"+root_set_size+" increment:"+increment +
                            "\nn_synonyms:"+synonyms.size() + " ("+n_synonyms +")" +
                            "\ncategories:"+session.category_nodes.size() +
                            " total_steps_while_categories_removing:"+
                            session.category_black_list.getTotalCategoriesPassed() + "\n\n" +
                            "Synonyms and related words:\n";
                    
            if(null != session.dump) {
                dump.file.Print(s);
                dump.file.Flush();
                auth.AppendSynonyms(article, synonyms, "|", dump);
                dump.file.Print("\n\nSynonyms rated by user:"+syn_table.getRatedSynonym("|"));
                dump.file.Flush();
            }
            
            syn_table.addUnratedSynonymListByArticles(synonyms);
            syn_table.updateTable();
            
            CategorySet.fillLinksFromCategoryToArticles(base_nodes, session.category_nodes);
            cat_table.createCategoriesList(session.category_nodes, session.category_black_list, base_nodes);
            cat_table.updateTable();
            
            // dump results to result textarea: statistics and result synonyms
            rtp.output.append(s);
            rtp.output.append( auth.SynonymsToString(article, synonyms, "|"));
            if(null != session.dump) {
                rtp.output.append("\n\nSee more information in the file:\n" + dump.file.GetPath());
            }
            
            
            if(session.getIWiki()) {    // before drawing nodes
                for(Article a:base_nodes.values())
                    a.fillInterWikiTitle (session, session.getIWikiLang(), PageNamespace.MAIN);
                for(Category c:session.category_nodes.values())
                    c.fillInterWikiTitle (session, session.getIWikiLang(), PageNamespace.MAIN);
            }
            
            SetTypeForRatedSynonyms(base_nodes, NodeType.RATED_SYNONYMS);
            syn_art.drawNodesEdges (base_nodes, show_redirects);
            syn_art.setVisibleEdges(session.source_article_id, base_nodes);
            SetNodeAndTextPane(article);
            
            setArticleParameters();
            
            // creates map in order to "Expand categories" via context menu
            m_articles = wikipedia.kleinberg.Article.createMapFromTitleToID(base_nodes);
            m_categories = wikipedia.kleinberg.Article.createMapFromTitleToID(session.category_nodes);
            
            
         /*
            // print best hubs as triangles
            dump.Dump(base_nodes, "");
            // append dot command to bat file
            dump.file_bat.Print( dump.GetStatisticsHashMap(base_nodes) + dump.GetDotCommand("jpeg", true) );
            dump.file_sh. Print( dump.GetStatisticsHashMap(base_nodes) + dump.GetDotCommand("jpeg", false) );
            dump.file_bat.Flush();
            dump.file_sh. Flush();
            
            
            // cluster synonyms
            Map<Integer, Article> map_synonyms = Article.CreateHashMap((Article[])synonyms.toArray(new Article[0]));
            dump.enable_file_dot = false;
            CategorySet.prepareCategories(session, map_synonyms);
            int max_cluster_weight = 20;
            List<ClusterCategory> clusters = CategorySet.getCategoryClusters (session.category_nodes, map_synonyms, max_cluster_weight);
            //CategorySet.dumpClusterCategoryArticle(session, map_synonyms, clusters, "02_clusters_max_weight_"+max_cluster_weight+"_root_set_size_"+root_set_size+"_increment_"+increment);
            //CategorySet.dumpClusterCategorywithListArticles(session, map_synonyms, clusters, "03_list_articles_max_weight_"+max_cluster_weight+"_root_set_size_"+root_set_size+"_increment_"+increment);
         */   
        } else {
            rtp.output.append("\nNo synonyms were found.");
            ((SynonymPanel)wb.synonymTextPanel).colorResultTab();
            
            if(wb.parameters.isLogEnabled() && 0 == dump.file.GetFileLength()) {
                // remove empty file
                dump.file.delete();
            }
        }
        
    }
    
}

        /*
        p = session.connect.cur_table.GetIDByTitle(session.connect, Encodings.Latin1ToUTF8(article));
        p = session.connect.cur_table.GetIDByTitle(session.connect, Encodings.FromTo(article, "UTF8", "ISO8859_1"));
        p = session.connect.cur_table.GetIDByTitle(session.connect, Encodings.FromTo(article, "UTF8", "Cp1251"));
        
        p = session.connect.cur_table.GetIDByTitle(session.connect, Encodings.FromTo(article, "Cp1251", "UTF8"));
        p = session.connect.cur_table.GetIDByTitle(session.connect, Encodings.FromTo(article, "Cp1251", "ISO8859_1"));
        */
        /*
        String s_in = article;
        String s = "";
        s += "Latin1ToUTF8" + wikipedia.util.Encodings.Latin1ToUTF8(s_in);
        s += "UTF8 to ISO8859_1" + wikipedia.util.Encodings.FromTo(s_in, "UTF8", "ISO8859_1");
        s += "UTF8 to Cp1251" + wikipedia.util.Encodings.FromTo(s_in, "UTF8", "Cp1251");
        
        s += "Cp1251 to UTF8" + wikipedia.util.Encodings.FromTo(s_in, "Cp1251", "UTF8");
        s += "Cp1251 to ISO8859_1" + wikipedia.util.Encodings.FromTo(s_in, "Cp1251", "ISO8859_1");
        */