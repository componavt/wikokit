/* SaveRelations.java - stores a set of semantic relations
 * (extracted from a parsed Wiktionary database) to a file.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wigraph;

import wikt.sql.TLang;
import wikt.sql.TPOS;
import wikt.sql.TRelation;
import wikipedia.sql.Connect;

import java.io.*;
import java.util.*;


/** A storing mechanism of a set of semantic relations
 * (extracted from a parsed Wiktionary database) into a file.
 */
public class SaveRelations
{
    /** Stores _map into a file defined by filename. */
    public static void storeMapToLists(String filename, Map<String,List<String>> _map)
            throws IOException, ClassNotFoundException {

        ObjectOutputStream objstream = new ObjectOutputStream(new FileOutputStream(filename));
        objstream.writeObject(_map);
        objstream.close();
    }

    /** Stores _list into a file defined by filename. */
    public static void storeList(String filename, List<String> _list)
            throws IOException, ClassNotFoundException {

        ObjectOutputStream objstream = new ObjectOutputStream(new FileOutputStream(filename));
        objstream.writeObject(_list);
        objstream.close();
    }

    public static void main(String[] args) {

        Connect ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);

        // It is supposed that Wiktionary parsed database has been created
        TLang.createFastMaps(ruwikt_parsed_conn);   // once upon a time: use Wiktionary parsed db
        TPOS.createFastMaps(ruwikt_parsed_conn);    // once upon a time: use Wiktionary parsed db
        
        // edge creation 1
        // for each TRelation: get page<->wiki_text + type of relation
        Map<String,List<String>> m_words = TRelation.getAllWordPairs(ruwikt_parsed_conn);

        // prepare list of unique words - vertices
        System.out.println("  (preparing list of unique words - vertices)...");
        List<String> unique_words = new ArrayList();
        unique_words.addAll(m_words.keySet());

        for(List<String> list_s : m_words.values())
            for(String s : list_s) {
                if(!unique_words.contains(s))
                    unique_words.add(s);
            }
        
        String filename1 = "relation_pairs.txt";
        String filename2 = "unique_words.txt";
        try {
            storeMapToLists(filename1, m_words);
            storeList(filename2, unique_words);
        } catch(IOException ex) {
            System.err.println("IOException (wigraph SerializeRelationsToFile.java main()):: Serialization failed (" + filename1 + "), msg: " + ex.getMessage());
        } catch(ClassNotFoundException ex) {
            System.err.println("IOException (wigraph SerializeRelationsToFile.java main()):: Serialization failed (" + filename1 + "), msg: " + ex.getMessage());
        }
        

        // edge creation 2
        // for each TMeaning (which have wiki_text with wikified words):
        // get page<->wikified word, where type of relation = wiki

        // todo
        // ...

    }
}
