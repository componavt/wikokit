/*
 * Valuer.java - evaluates and calculates words similarity.
 *
 * Copyright (c) 2005-2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wigraph.experiment;

import wigraph.PathSearcher;
import wigraph.DistanceData;

import wikipedia.sql.Connect;
import wikipedia.util.*;

import wikipedia.language.LanguageType;
import wikt.sql.TTranslation;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;

import java.text.*;
import java.util.*;

/** Words similarity calculator 
 * calculates distances between 353 pairs of English words,
 * based on path length in thesaurus of Russian words,
 * extracted from Russian Wiktionary.
 *
 * It compares calculation results with human similarity estimation
 * ("The WordSimilarity-353 Test Collection").
 *
 * In order to calculate similarity:
 * 1. Create database ruwikt_parsed:
 * 
 * 2. Run file wigraph.SaveRelations
 * and created relations will be saved to files:
 * relation_pairs.txt and unique_words.txt.
 *
 * 3 Run wigraph.experiment.ShortestPathEnViaRu353.main()
 *
 * 4. See output file in /home/~/.wikokit/ruwikt/..
 */
public class Valuer {
    private static final boolean DEBUG = true;
    
    /** Number of absent data items */
    public  static int absent_counter;
    
    /** Calculates relatedness of two words.
     */
    public static DistanceData compareSynonyms (
            Graph g_all_relations, DijkstraShortestPath<String,Integer> alg,
            String word1,String word2,float human_wordsim,
            Connect connect,
            LanguageType source_lang,   // language of sought pages (language of page)
            LanguageType target_lang,    // language of translations
            FileWriter dump
            ) 
    {
        long    t_start, t_end;
        float   t_work;
        
        t_start = System.currentTimeMillis();

        // translations of first & second synonyms (from translation to source language)
        String[] trans_syn1  = TTranslation.fromTranslationsToPage(connect, source_lang, word1, target_lang);
        String[] trans_syn2  = TTranslation.fromTranslationsToPage(connect, source_lang, word2, target_lang);
        
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec

        DistanceData dist = null;
        
        if (null != trans_syn1  && 0 < trans_syn1.length &&
            null != trans_syn2 && 0 < trans_syn2.length) {

            dist = PathSearcher.calcPathLenRelatedness(g_all_relations, alg, trans_syn1, trans_syn2);
            /*result_len.setText("Shortest path len: min="    + dist.min +
                                                ", average="+ dist.average +
                                                ", max="+ dist.max);

            System.out.println("Word set 1 has length " + w1.length);
            System.out.println("Word set 2 has length " + w2.length);

            System.out.println("Vertices : "+ mGraph.getVertexCount());
            System.out.println("Edges : "   + mGraph.getEdgeCount());*/

            if(DEBUG) {
                dump.Print( //System.out.println(
                        word1 + "\t" + word2 + "\t" + human_wordsim + "\t" + 
                            //new PrintfFormat("%.3lg").sprintf(dist_f) + "\t" +
                        new PrintfFormat("%.3lg").sprintf(dist.min) + "\t" +
                        new PrintfFormat("%.3lg").sprintf(dist.average) + "\t" +
                        new PrintfFormat("%.3lg").sprintf(dist.max) + "\t" +
                        StringUtil.join(",",trans_syn1)  + "\t" +   trans_syn1.length + "\t" +
                        StringUtil.join(",",trans_syn2)  + "\t" +   trans_syn2.length + "\t" +
                        t_work                           + "\t" +
                        "\n");
            }
            dump.Flush();
        } else {
            // There is no any translation in Russian Wiktionary!
            absent_counter ++;
            if(DEBUG) {
                dump.Print( // System.out.println(
                        word1 + "\t" + word2 + "\t" + human_wordsim + "\tabsent\n");
            }
        }
        return dist;
    }
}
