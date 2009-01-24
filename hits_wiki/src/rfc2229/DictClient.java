/*
 * DictClient.java - a client for Dict servers
 * based on JavaClientForDict libraryr (jcfd, author Davor Cengija). 
 * For more informations on Dict servers please 
 * check http://www.dict.org and RFC-2229.
 *
 * Copyright (c) 2005 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package rfc2229;

//import net.zuckerfrei.jcfd.Database;
//import net.zuckerfrei.jcfd.DatabaseList;
import net.zuckerfrei.jcfd.Definition;
import net.zuckerfrei.jcfd.DefinitionList;
import net.zuckerfrei.jcfd.Dict;
import net.zuckerfrei.jcfd.DictFactory;
//import net.zuckerfrei.jcfd.Match;
//import net.zuckerfrei.jcfd.MatchList;
//import net.zuckerfrei.jcfd.Strategy;
//import net.zuckerfrei.jcfd.StrategyList;

import java.util.*;

/** Short class description */
public class DictClient {
    
    public DictClient() {
    }
    
    
    /** Get list of synonyms for the word from selected databases of dict.org.
     * Examples of database full names are "WordNet (r) 2.0",
     * "Moby Thesaurus II by Grady Ward, 1.0". The short name (substring) 
     * could be used, e.g. "Moby", "WordNet".
     *
     * @params db_names search only in these databases, 
     * @params word sought word
     */
    public static List<String> getLinkWords (String[] db_names, String word) throws Exception {
        int     i;
        List<String> result = new ArrayList<String>();

        DictFactory dictFactory = DictFactory.getInstance();
        Dict dict = dictFactory.getDictClient();
        DefinitionList defList = dict.define(word);
        
        while (defList.hasNext()) {
            Definition def = defList.next();
            String db_cur = def.getDatabase().getName();
            
            // whether to search in this database: compare with db_names
            for(String db_name: db_names) {
                if(db_cur.contains(db_name)) {
                    if(db_cur.contains("WordNet")) {
                        result.addAll(WordNetParser.getSynonyms((String) def.getContent()));
                    } else if(db_cur.contains("Moby")) {
                        result.addAll(Arrays.asList(MobyParser.getWords((String) def.getContent())));
                    }
                }
            }
        }
        return result;
    }
    
    public static void run(String word) throws Exception {
        //String word = "linux";
        DictFactory dictFactory = DictFactory.getInstance();
        Dict dict = dictFactory.getDictClient();
        DefinitionList defList = dict.define(word);
        
        System.out.println("Number of definitions is " + defList.count() + " for the word " + word);
        
        Definition def;
        
        while (defList.hasNext()) {
            def = defList.next();
            // "WordNet (r) 2.0"
            // "Moby Thesaurus II by Grady Ward, 1.0"
            System.out.println("DEFINITION: \nDB: " + def.getDatabase().getName() + "\n" + def.getContent());

            String[] linkovi = def.getLinks();
            for (int i = 0; i < linkovi.length; i++) {
                System.out.println("Links: " + (i + 1) + ". " + linkovi[i]);
            }
            
        }

        /*
                MatchList matches = dict.match("donld knuth");
        
                System.out.println("Ukupno pronasao " + matches.count());
                while (matches.hasNext()) {
        
                    Match match = matches.next();
        
                    System.out.println("db: " + match.getDatabase() + " word: " + match.getWord());
                }
        
                System.out.println();
        
                DatabaseList dbList = dict.listDatabases();
        
                while (dbList.hasNext()) {
        
                    Database db = dbList.next();
        
                    System.out.println("DB: " + db.getCode() + " " + db.getName());
                }
        
                System.out.println();
        
                StrategyList strList = dict.listStrategies();
        
                while (strList.hasNext()) {
        
                    Strategy str = strList.next();
        
                    System.out.println("STRAT: " + str.getCode() + " " + str.getName());
                }
                
        
                    DefinitionList definitionList = dict.define(matches);
                    while(definitionList.hasNext()) {
                        System.out.println("DEFINITION: " + definitionList.next().getContent());
                    }
        */

        dict.close();
        
        System.out.println(System.getProperty("java.class.path"));
    }
}