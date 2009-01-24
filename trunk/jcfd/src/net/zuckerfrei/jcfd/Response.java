package net.zuckerfrei.jcfd;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class Response {

    //~ Static variables/initializers =========================================

    /** DOCUMENT ME! */
    public static final String DATABASES_COUNT_FOLLOWS = "110";

    /** DOCUMENT ME! */
    public static final String STRATEGIES_COUNT_FOLLOWS = "111";

    /** DOCUMENT ME! */
    public static final String DEFINITIONS_COUNT_FOLLOWS = "150";

    /** DOCUMENT ME! */
    public static final String DEFINITION_FOLLOWS = "151";

    /** DOCUMENT ME! */
    public static final String CONNECTED = "220";

    /** DOCUMENT ME! */
    public static final String OK = "250";

    /** DOCUMENT ME! */
    public static final String INVALID_DATABASE = "550";

    /** DOCUMENT ME! */
    public static final String INVALID_STRATEGY = "551";

    /** DOCUMENT ME! */
    public static final String NO_MATCH = "552";

    /** DOCUMENT ME! */
    public static final String NO_DATABASES = "554";

    /** DOCUMENT ME! */
    public static final String NO_STRATEGIES = "555";

    //~ Methods ===============================================================

    /**
     * Method isConnected.
     *
     * @param result
     *
     * @return boolean
     */
    public static boolean isConnected(String result) {
        return result.startsWith(CONNECTED);
    }


    /**
     * DOCUMENT ME!
     *
     * @param result DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean isOk(String result) {
        return result.startsWith(OK);
    }


    /**
     * DOCUMENT ME!
     *
     * @param result DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean definitionFollows(String result) {
        return result.startsWith(DEFINITION_FOLLOWS);
    }


    /**
     * Method definitionsCountFollows.
     *
     * @param result DOCUMENT ME!
     *
     * @return boolean
     */
    public static boolean definitionsCountFollows(String result) {
        return result.startsWith(DEFINITIONS_COUNT_FOLLOWS);
    }


    /**
     * DOCUMENT ME!
     *
     * @param result DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int findCount(String result) {

        int space1 = result.indexOf(' ') + 1;
        int space2 = result.indexOf(' ', space1 + 1);

        return Integer.parseInt(result.substring(space1, space2));
    }


    /**
     * Method invalidDatabase.
     *
     * @param result
     *
     * @return boolean
     */
    public static boolean invalidDatabase(String result) {
        return result.startsWith(INVALID_DATABASE);
    }


    /**
     * Method invalidStrategy.
     *
     * @param result
     *
     * @return boolean
     */
    public static boolean invalidStrategy(String result) {
        return result.startsWith(INVALID_STRATEGY);
    }


    /**
     * Method noDatabases.
     *
     * @param result
     *
     * @return boolean
     */
    public static boolean noDatabases(String result) {
        return result.startsWith(NO_DATABASES);
    }


    /**
     * Method noMatch.
     *
     * @param result
     *
     * @return boolean
     */
    public static boolean noMatch(String result) {
        return result.startsWith(NO_MATCH);
    }


    /**
     * Method noStrategies.
     *
     * @param result
     *
     * @return boolean
     */
    public static boolean noStrategies(String result) {
        return result.startsWith(NO_STRATEGIES);
    }


    /**
     * DOCUMENT ME!
     *
     * @param str DOCUMENT ME!
     * @param delimiter DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String[] splitQuotedString(String str, char delimiter) {
        try {

            StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(str));

            tokenizer.quoteChar(delimiter);

            int ttype = tokenizer.nextToken();
            List list = new ArrayList();

            while (ttype != StreamTokenizer.TT_EOF) {
                list.add(tokenizer.sval);
                ttype = tokenizer.nextToken();
            }

            return (String[]) list.toArray(new String[] {});
        }
        catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage());
        }
    }
}
