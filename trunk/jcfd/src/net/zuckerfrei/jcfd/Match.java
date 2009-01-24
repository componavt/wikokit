package net.zuckerfrei.jcfd;

/**
 * <code>Match</code> is the result of the query using a particular strategy. It contains the word found and the database the word is found in.
 *  
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 * 
 * @see net.zuckerfrei.jcfd.Strategy
 * @see net.zuckerfrei.jcfd.Definition
 * @see net.zuckerfrei.jcfd.Database
 * @see net.zuckerfrei.jcfd.MatchList
 * @see net.zuckerfrei.jcfd.Dict#match(String)
 * @see net.zuckerfrei.jcfd.Dict#define(Match)
 * @see net.zuckerfrei.jcfd.Dict#define(MatchList)
 */
public class Match {

    //~ Instance variables ====================================================

    /** Word found in the database. */
    private String word;

    /** The database the word is found in. */
    private Database database;

    //~ Constructors ==========================================================

    /**
     * Creates a new Match object.
     *
     * @param database The database the word is found in.
     * @param word The word found.
     * 
     * @see Database
     */
    public Match(Database database, String word) {
        this.word = word;
        this.database = database;
    }

    //~ Methods ===============================================================

    /**
     * Returns the database.
     *
     * @return Database
     */
    public Database getDatabase() {
        return database;
    }


    /**
     * Returns the word.
     *
     * @return String
     */
    public String getWord() {
        return word;
    }
}
