import net.zuckerfrei.jcfd.Database;
import net.zuckerfrei.jcfd.DatabaseList;
import net.zuckerfrei.jcfd.Definition;
import net.zuckerfrei.jcfd.DefinitionList;
import net.zuckerfrei.jcfd.Dict;
import net.zuckerfrei.jcfd.DictFactory;
import net.zuckerfrei.jcfd.Match;
import net.zuckerfrei.jcfd.MatchList;
import net.zuckerfrei.jcfd.Strategy;
import net.zuckerfrei.jcfd.StrategyList;

/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class Main {

    //~ Methods ===============================================================

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public static void main(String[] args) throws Exception {

        String word = "linux";

        DictFactory dictFactory = DictFactory.getInstance();
        Dict dict = dictFactory.getDictClient();

        DefinitionList defList = dict.define(word);

        

        System.out.println("Ukupno pronasao " + defList.count() + " puta rijec " + word);

        Definition def;

        while (defList.hasNext()) {
            def = defList.next();
            System.out.println("DEFINITION: \nDB: " + def.getDatabase().getName() + "\n" + def.getContent());

            String[] linkovi = def.getLinks();
            for (int i = 0; i < linkovi.length; i++) {
                System.out.println("Linkovi: " + (i + 1) + ". " + linkovi[i]);
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
