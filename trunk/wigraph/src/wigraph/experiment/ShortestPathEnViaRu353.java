/* ShortestPathRelation.java - Distance calculations.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wigraph.experiment;

import wigraph.PathSearcher;
import wigraph.DistanceData;
import wigraph.LoadRelations;

import wikipedia.sql.Connect;
import wikipedia.util.*;

import wikipedia.language.LanguageType;
import wikt.sql.TTranslation;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Date;

/** Calculation of distances between 353 pairs of English words,
 * based on path length in thesaurus of Russian words,
 * extracted from Russian Wiktionary.
 */
public class ShortestPathEnViaRu353 {
    
    private static final boolean DEBUG = true;
    
    public static void main(String[] s) {

        FileWriter          dump;
        DijkstraShortestPath<String,Integer> alg;
        Graph g_all_relations;

        Connect ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);

        g_all_relations = LoadRelations.loadGraph(ruwikt_parsed_conn, "relation_pairs.txt", "unique_words.txt");
        assert(null != g_all_relations);
        System.out.println("Calculation Dijkstra shortest path...");
        alg = new DijkstraShortestPath(g_all_relations);


        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                   DateFormat.LONG,
                                   new Locale("en","US"));
        String today = formatter.format(new Date());

        String  directory;

        //directory = "data/synonyms_ru/";  //String filename = "ruwiki_synonyms.txt";
        directory = System.getProperty("user.home") + "/.wikokit/ruwikt_20090122_353/";
        dump = new FileWriter();
        dump.SetDir(directory);

        dump.SetFilename( "wordsim353.txt");
        dump.Open(true, "UTF-8");    // Cp1251
        dump.Print("\n\ntime start:" + today + " \n");
        if(DEBUG) {

            dump.Print("\n" +
                "isct_wo - Number of intersection words, they are synonyms of word1 and word2" + "\n" +
                "t sec   - time sec" + "\n" +
                "syn1.len - number of synonyms in first list" + "\n" +
                "syn2.len - number of synonyms in first list" + "\n" +
                "human   - human_wordsim" + "\n");

            dump.Print("\n" +
                "word1\t" + "word2\t" + "human\t" +
               // intersect.length + "\t" + dist_f + "\t" + dist_i + "\t" +
                "dist.min\t" + "average\t" + "max\t" +
                "syn1.list\t" +
                "syn1.len\t" +
                "syn2.list\t" +
                "syn2.len\t" +
                "t sec\t" +
                "cat_max" + "\t" +
                "iter\t"
                //"vert-s\t" +
                //"edges\t" +
                );
        }
        dump.Flush();

        long    t_start, t_end;
        float   t_work;
        t_start = System.currentTimeMillis();
        int i = 0;

        System.out.println ("\nThe words are processing:\n");
        WordSim353 wordsim353= new WordSim353();
        Valuer.absent_counter = 0;
        for(WordSim w:wordsim353.data) {
            i++;
            String word1 = w.word1;
            String word2 = w.word2;
            word1 = "plane";        // computer
            word2 = "car";          // keyboard

            //System.out.println ("The word Latin1ToUTF8 '"+Encodings.Latin1ToUTF8(all_words[i])+"' is processing...");
            System.out.println (i + ": " + word1 + ", " + word2);

            LanguageType source_lang = LanguageType.ru;
            LanguageType target_lang = LanguageType.en;

            DistanceData dist = Valuer.compareSynonyms (
                                    g_all_relations, alg,
                                    word1, word2, w.sim,
                                    ruwikt_parsed_conn,
                                    source_lang, target_lang,
                                    dump);
//            if( i > 9)
              break;
        }

        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec

        dump.Print("\n" + "Total time: " + t_work + "sec.\n");
        dump.Print("\n" + "Number of absent data items: " + Valuer.absent_counter + ".\n");
        dump.Flush();


        ruwikt_parsed_conn.Close();
	}

}
