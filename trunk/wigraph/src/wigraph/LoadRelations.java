/* LoadRelations.java - loads a set of semantic relations
 * (extracted from a parsed Wiktionary database) from a file.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wigraph;

import wikt.sql.TPage;
import wikt.sql.TLang;
import wikt.sql.TPOS;
import wikt.sql.TRelation;
import wikt.sql.TRelationType;
import wikt.constant.Relation;
import wikipedia.sql.Connect;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;

import java.io.*;
import java.util.*;

/** A loader of semantic relations from a file.
 */
public class LoadRelations {

    /** Loads a map from a file defined by filename. */
    public static Map<String,List<String>> loadMapToLists(String filename)
            throws IOException, ClassNotFoundException {

        ObjectInputStream objstream = new ObjectInputStream(new FileInputStream(filename));
        Map<String,List<String>> _map = (Map<String,List<String>>)objstream.readObject();
        objstream.close();
        return _map;
    }

    /** Loads a list from a file defined by filename. */
    public static List<String> loadListString(String filename)
            throws IOException, ClassNotFoundException {
        ObjectInputStream objstream = new ObjectInputStream(new FileInputStream(filename));
        List<String> _list = (List<String>)objstream.readObject();
        objstream.close();
        return _list;
    }

    public static void main(String[] args) {

        Connect ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);

        // It is supposed that Wiktionary parsed database has been created
        TLang.createFastMaps(ruwikt_parsed_conn);
        TPOS.createFastMaps(ruwikt_parsed_conn);
        TRelationType.createFastMaps(ruwikt_parsed_conn);
        
        // edge creation 1
        // for each TRelation: get page<->wiki_text + type of relation
        
        String filename1 = "relation_pairs.txt";
        String filename2 = "unique_words.txt";
        Map<String,List<String>> m_words = null;
        List<String> unique_words = null;
        try {
            System.out.println("Loading relations from file " + filename1 + " ...");
            m_words      = loadMapToLists(filename1);
            unique_words = loadListString(filename2);
            System.out.println("Unique words : " + unique_words.size());
        } catch(IOException ex) {
            System.err.println("IOException (wigraph SerializeRelationsToFile.java main()):: Serialization failed (" + filename1 + "), msg: " + ex.getMessage());
        } catch(ClassNotFoundException ex) {
            System.err.println("IOException (wigraph SerializeRelationsToFile.java main()):: Serialization failed (" + filename1 + "), msg: " + ex.getMessage());
        }

        System.out.println("Creating graph...");
        Graph g = GraphCreator.createGraph(m_words, unique_words);
        assert(null != g);
        System.out.println("Calculation Dijkstra shortest path...");
        DijkstraShortestPath<String,Integer> alg = new DijkstraShortestPath(g);
        
        // proceedings: труды, записки (научного об-ва); протоколы

        // Report < Publication
        // report: отчет, доклад, рапорт, донесение, сообщение; рассказ, описание событий
        // publication: опубликование, оглашение, обнародование; издание, печатание, публикация

        String[] word_set1 = {"отчет", "доклад", "рапорт", "донесение", "сообщение", "рассказ", "описание событий"};
        String[] word_set2 = {"оглашение", "обнародование", "издание", "печатание", "публикация"}; // "опубликование"

        //String word1 = "WAN";
        //String word2 = "network";   //WAN - LAN - network
        for(String word1 : word_set1) {
            for(String word2 : word_set2) {

                String[] word_path = null;

                if(g.containsVertex(word1) && g.containsVertex(word2)) {

                    if(null != TPage.get(ruwikt_parsed_conn, word1) &&
                       null != TPage.get(ruwikt_parsed_conn, word2))
                    {
                        System.out.println("Starting search path ['" + word1 + "', '" + word2 + "']");
                        word_path = PathSearcher.getShortestPath(g, alg, word1, word2);
                        if(word_path.length > 0) {
                            int len = word_path.length - 1;
                            System.out.println("There is a path from '" + word1 + "' to '" + word2 + "', length = " + len);
                                System.out.println("" + 0 + ": " + word_path[0]);
                                
                            for(int i=1; i<word_path.length; i++) {
                                Relation r = TRelation.getRelationType(ruwikt_parsed_conn, word_path[i-1], word_path[i]);
                                String rel_name = null == r ? "" : r.toString();
                                System.out.println("" + i + ".: " + rel_name + "["+ word_path[i-1] + ", "+ word_path[i] + "]");
                            }
                        }
                    }
                }

                if(null == word_path || 0 == word_path.length)
                    System.out.println("There is no path from '" + word1 + "' to '" + word2 + "'.");
            }
        }


        // edge creation 2
        // for each TMeaning (which have wiki_text with wikified words):
        // get page<->wikified word, where type of relation = wiki

        // todo
        // ...

    }

}
