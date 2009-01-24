package net.zuckerfrei.jcfd;

/**
 * Client for a DICT server, per RCF2229.
 * //:: TODO elaborate
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public interface Dict {

    //~ Static variables/initializers =========================================

    /**
     * End of the command which is sent to a DICT server, as defined per RFC.
     */
    public static final String COMMAND_LINE_END = "\r\n";    //$NON-NLS-1$

    /**
     * End of the definition line received from a DICT server, as defined per
     * RFC.
     */
    public static final String DEFINITION_LINE_SEPARATOR = "\r\n";    //$NON-NLS-1$ 

    /** End of the definition, as defined per RFC. */
    public static final String DEFINITION_END = ".";    //$NON-NLS-1$

    //~ Methods ===============================================================

    /**
     * Closes this dict object. After calling this method, there's no guaranties that
     * this object will behave sensible and therefore shouldbn't be used.
     *
     * @throws DictException if an error occures.
     */
    public void close()
               throws DictException;


    /**
     * Define a word, search in any database. <code>define</code> searches for
     * the exact match.
     *
     * @param word String to be defined
     *
     * @return list of the Definitions
     *
     * @throws DictException if an error occures.
     *
     * @see #match(String)
     */
    public DefinitionList define(String word)
                          throws DictException;


    /**
     * Define a word, search throughout all databases, but when found, do not
     * go looking forther (e.g. stay in that database).
     *
     * @param word String to be defined
     * @param firstOnly boolean if true, stay in the first database in which
     *        the word is found
     *
     * @return list of the Definitions
     *
     * @throws DictException if an error occures.
     */
    public DefinitionList define(String word, boolean firstOnly)
                          throws DictException;


    /**
     * Define a word, search only in the specified database.
     *
     * @param word String to be defined
     * @param database Database to look in
     *
     * @return list of the Definitions
     *
     * @throws DictException if an error occures.
     */
    public DefinitionList define(String word, Database database)
                          throws DictException;


    /**
     * Defines a word discovered by previously fetched <code>Match</code>.
     *
     * @param match Previously fetched match.
     *
     * @return List of the definitions.
     *
     * @throws DictException If an error occures.
     */
    public DefinitionList define(Match match)
                          throws DictException;


    /**
     * Defines each <code>Match</code> from this <code>MatchList</code>.
     *
     * @param matchList to be traversed and each <code>Match</code> defined.
     *
     * @return list of all <code>Definition</code>s found.
     *
     * @throws DictException DOCUMENT ME!
     *
     * @see Match
     * @see MatchList
     * @see DefinitionList
     * @see Definition
     * @see #define(Match)
     * @see #define(String)
     */
    public DefinitionList define(MatchList matchList)
                          throws DictException;


    /**
     * Returns <code>{@link DatabaseList}</code> containing all the
     * <code>{@link Database}</code>s available on the DICT server. Entries
     * are ordered as the appear in the server's response.
     *
     * @return DatabaseList containing <code>Database</code>s available on the
     *         server.
     *
     * @see #listStrategies()
     */
    public DatabaseList listDatabases();


    /**
     * Returns <code>{@link StrategyList}</code> containing all the
     * <code>{@link Strategy}</code>s available on the DICT server. Entries
     * are ordered as the appear in the server's response.
     *
     * @return StrategyList containing <code>Strategy</code>s available on the
     *         server.
     *
     * @see #listDatabases()
     */
    public StrategyList listStrategies();


    /**
     * Matches a word, search by any strategy, in any database.
     *
     * @return MatchList containing all the matches.
     */
    public MatchList match(String word)
                    throws DictException;


    /**
     * DOCUMENT ME!
     *
     * @param word DOCUMENT ME!
     * @param strategy DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DictException DOCUMENT ME!
     */
    public MatchList match(String word, Strategy strategy)
                    throws DictException;


    /**
     * DOCUMENT ME!
     *
     * @param word DOCUMENT ME!
     * @param database DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DictException DOCUMENT ME!
     */
    public MatchList match(String word, Database database)
                    throws DictException;


    /**
     * DOCUMENT ME!
     *
     * @param word DOCUMENT ME!
     * @param strategy DOCUMENT ME!
     * @param database DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DictException DOCUMENT ME!
     */
    public MatchList match(String word, Strategy strategy, Database database)
                    throws DictException;


    /**
     * Matches the word using default strategy looking only in the first
     * database which contains the match.
     *
     * @param word to be matched.
     * @param firstDbOnly if true, lok in the first database only.
     *
     * @return list of matches.
     *
     * @throws DictException if an error occures.
     *
     * @see Strategy
     * @see Strategy#DEFAULT
     * @see Database
     */
    public MatchList match(String word, boolean firstDbOnly)
                    throws DictException;


    /**
     * Matched the word, using specified Strategy, and looking only in the
     * first database which contains the match.
     *
     * @param word to be matched.
     * @param strategy to be used for matching.
     * @param firstDbOnly if true, look in the first database only
     *
     * @return MatchList containing the list of Matches
     *
     * @throws DictException If an error occures.
     *
     * @see Strategy
     * @see MatchList
     * @see Database
     */
    public MatchList match(String word, Strategy strategy, boolean firstDbOnly)
                    throws DictException;
}
