package net.zuckerfrei.jcfd;

import java.io.Serializable;


/**
 * Utility class providing DICT command formatings.
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class Command
    implements Serializable
{

    //~ Static variables/initializers =========================================

    /** CLIENT command sent when connecting to server. */
    static final String CLIENT = "CLIENT Java Client for Dict version @VERSION@. Pleased to meet you!";

    /** Show list of available databases. */
    static final String SHOW_DB = "SHOW DB";

    /** Show list of available strategies. */
    static final String SHOW_STRAT = "SHOW STRAT";

    /** Sent before closing the socket. */
    static final String QUIT = "QUIT";

    //~ Constructors ==========================================================

    /**
     * Private constructor to prevent instantiations.
     */
    private Command() {
        super();
    }

    //~ Methods ===============================================================

    /**
     * Sends DEFINE command, properly formatting the strings.
     *
     * @param word to be defined.
     * @param databaseCode database to be searched in.
     *
     * @return String ready to be sent to DICT server.
     * 
     * @see Definition
     * @see Dict#define(String, Database)
     * @see Database
     */
    public static String define(String word, String databaseCode) {
        return "DEFINE " + databaseCode + " \"" + word + "\"";
    }


    /**
     * Sends MATCH command, properly formatting the strings.
     *
     * @param word tp be matched.
     * @param strategyCode Strategy to be used while for matching.
     * @param databaseCode Database to be searched in.
     *
     * @return String ready to be sent to DICT server.
     * 
     * @see Match
     * @see Dict#match(String, Strategy, Database)
     * @see Strategy
     * @see Database
     */
    public static String match(String word, String strategyCode, String databaseCode) {
        return "MATCH " + databaseCode + " " + strategyCode + " \"" + word + "\"";
    }
}
