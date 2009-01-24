/*
 * Valuer.java - evaluates and calculates words similarity.
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.experiment;


//import wikipedia.experiment.MetricSpearman;
import wikipedia.kleinberg.*;
import wikipedia.sql.Connect;
import wikipedia.sql_idf.RelatedPage;
import wikipedia.util.*;

import java.text.*;
import java.util.*;


/** 
 * Words similarity calculator based on "The WordSimilarity-353 Test 
 * Collection" and metric Spearman footrule formula.
 * It compares AHITS result with human similarity estimation.
 *
 * In order to calculate similarity:
 * 1. Create database idfenwiki or idfsimplewiki:
 * mysql> CREATE DATABASE idfenwiki;
 * mysql> USE idfenwiki;
 * mysql> source synarcher/db/idf/idfenwiki7_20070527_normalload_417rp.sql
 *
 * This database caches results of previous search by AHITS. If related pages
 * are found, then they will be stored to the table related_page. You can use 
 * 417 related pages stored in idfenwiki7_20070527_normalload_417rp.sql, or
 * you can clear table 
 * mysql> DELETE FROM related_page
 * and change AHITS parameters in ValuerTest.testCompareSynonyms()
 * 
 * 2. Check that you have folder /home/~/.synarcher/test_kleinberg/simple/ or /en
 * 3. Open JUnit test file ValuerTest.testCompareSynonyms
 * 4. Set boolean vars: b_normal_load, b_simple
 * 5. Run ValuerTest.testCompareSynonyms
 * 6. See output file in /home/~/.synarcher/test_kleinberg/en/ or /simple/
 * 7. Yes, comment 'break;' in the cycle.
 */
public class Valuer {
    private static final boolean DEBUG = true;
    
    /** Number of absent data items */
    public  static int absent_counter;
    
    private final static Article[]      NULL_ARTICLE_ARRAY = new Article[0];
    
    
    private static String[] getSynonyms(String word,
            Authorities   auth,
            SessionHolder session,
            int     root_set_size, int increment, int n_synonyms, int categories_max_steps,
            float   eps_error,
            Connect idf_conn) 
    {
        String[] synonyms_title = null;
        
        RelatedPage rp = new RelatedPage ();
        if(rp.isInTable_RelatedPage(idf_conn.conn, word)) {
            // take result from cache (idf database)
            synonyms_title = rp.getRelatedTitlesAsArray();
        } else {
            System.out.println("Word " + word + " is absent in cache (idf database), starts AHITS calculation...");
            session.clear();
            List<String> rated_synonyms = new ArrayList<String>();
            Map<Integer, Article>  base_nodes = LinksBaseSet.CreateBaseSet(word, rated_synonyms, 
                    session, root_set_size, increment);
            /*if(DEBUG) {
                dump.file.PrintNL("Total_steps_while_categories_removing:"+
                        session.category_black_list.getTotalCategoriesPassed());
                dump.file.Flush();
            }*/
            List<Article> synonyms = auth.Calculate(base_nodes, eps_error, n_synonyms, session);

            if (null != synonyms  && 0 < synonyms.size()) {
                synonyms_title  = Article.getTitles((Article[])synonyms. toArray(NULL_ARTICLE_ARRAY));
            }
            rp.add(idf_conn.conn, word, synonyms_title);
        }
        
        return synonyms_title;
    }
    /** Calculates relatedness of two words.
     * Creates two vectors of synonyms using AHITS algorithm, then calculates
     * intersection by Spearman footrule formula.
     */
    public static float compareSynonyms (String word1,String word2,float human_wordsim,
            Authorities             auth,
            Connect                 connect,
            DumpToGraphViz          dump,
            SessionHolder           session,
            int     root_set_size, int increment, int n_synonyms, int categories_max_steps,
            float   eps_error, 
            Connect                 idf_conn
            ) 
    {
        long    t_start, t_end;
        float   t_work;
        
        t_start = System.currentTimeMillis();
        
        String[] synonyms_title  = getSynonyms(
                                        word1,
                                        auth, session,
                                        root_set_size, increment, n_synonyms, categories_max_steps,
                                        eps_error, idf_conn);
        
        // word 2
        String[] synonyms_title2  = getSynonyms(
                                        word2,
                                        auth, session,
                                        root_set_size, increment, n_synonyms, categories_max_steps,
                                        eps_error, idf_conn);

        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec

        double  dist_f = -1;
        int     dist_i = -1;

        if (null != synonyms_title  && 0 < synonyms_title.length && 
            null != synonyms_title2 && 0 < synonyms_title2.length) {
            // compare with standard list
            dist_i = MetricSpearman.compare(synonyms_title, synonyms_title2);
            dist_f = MetricSpearman.calcSpearmanFootrule(synonyms_title, synonyms_title2);
            String[] intersect = StringUtil.intersect(synonyms_title, synonyms_title2);

            if(DEBUG) {
                dump.file.Print(
                        word1 + "\t" + word2 + "\t" + human_wordsim + "\t" + 
                            new PrintfFormat("%.3lg").sprintf(dist_f) + "\t" +
                            intersect.length + "\t" + 
                            dist_i + "\t" + 
                        t_work                          + "\t" +
                        categories_max_steps            + "\t" +
                        auth.iter                       + "\t" +
                        //base_nodes.values().size()      + "\t" +
                        //DCEL.CountLinksIn(base_nodes)   + "\t" +
                        n_synonyms                      + "\t" + synonyms_title.length + "\t" + 
                        session.skipTitlesWithSpaces()  + "\t" +
                        session.category_black_list.getTotalCategoriesPassed() + "\t" +
                        root_set_size + "\t" + increment + "\t" + 
                        //StringUtil.join(",",synonyms_title)  + "\t" + 
                        MetricSpearman.findStringWithPosition(synonyms_title, synonyms_title2, ",")  + "\n");                            
            } /*else {
                dump.file.Print("\n\ntime sec:" + t_work + 
                            " iter:" + auth.iter +
                            " vertices:" + base_nodes.values().size() +
                            " edges:" + DCEL.CountLinksIn(base_nodes) +
                            "\nroot_set_size:"+root_set_size+" increment:"+increment +
                            "\n Metric Spearman footrule:" + dist_f +
                            "\n Metric G:" + dist_i +
                            "\nn_synonyms:"+n_synonyms +
                            "\nsynonyms.size():"+synonyms.size() +
                            "\nStringWithPosition" + MetricSpearman.findStringWithPosition(synonyms_title, synonyms_title2, ",")  +
                            "\nskipTitlesWithSpaces:"+session.skipTitlesWithSpaces()+
                            "\ntotal_steps_while_categories_removing:"+
                            session.category_black_list.getTotalCategoriesPassed() + "\n");
                dump.file.Flush();
                auth.AppendSynonyms(word1, synonyms, "|", dump);
                dump.file.Flush();
                auth.AppendSynonyms(word2, synonyms2, "|", dump);
                dump.file.Print("\n");
                // add
                // intersect.length + "\t" + dist_f + "\t" + dist_i + "\t" + 

            }*/
            dump.file.Flush();
        } else {
            // AHITS didn't find any synonyms
            absent_counter ++;
            if(DEBUG) {
                dump.file.Print(
                        word1 + "\t" + word2 + "\t" + human_wordsim + "\tabsent\n");
            }
        }
        return (float)dist_f;
    }
        
 
}
