package net.zuckerfrei.jcfd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public abstract class DictImpl
    implements Dict
{

    //~ Static variables/initializers =========================================

    static Log log = LogFactory.getLog(DictImpl.class);

    /** DOCUMENT ME! */
    private static String host;

    /** DOCUMENT ME! */
    private static int port;
    static DatabaseList dbList = new DatabaseList();
    static StrategyList strList = new StrategyList();

    //~ Instance variables ====================================================

    /** DOCUMENT ME! */
    protected boolean connected = false;

    /** DOCUMENT ME! */
    private Socket socket;

    /** DOCUMENT ME! */
    protected DataOutputStream dos;

    /** DOCUMENT ME! */
    BufferedReader br;

    //~ Constructors ==========================================================

    /**
     * Constructor.
     *
     * @param host
     * @param port
     *
     * @throws DictException DOCUMENT ME!
     */
    public DictImpl(String host, int port)
             throws DictException
    {
        this.host = host;
        this.port = port;
        connect();
    }


    /**
     * Creates a new DictImpl object.
     */
    protected DictImpl() {
    }

    //~ Methods ===============================================================

    /**
     * @see net.zuckerfrei.jcfd.Dict#close()
     */
    public void close()
                     throws DictException
    {
        try {
            writeCommand(Command.QUIT);
            socket.close();
            connected = false;
        }
        catch (IOException e) {
            ;
        }
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#define(String)
     */
    public DefinitionList define(String word)
                          throws DictException
    {
        return define(word, Database.ANY);
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#define(String, boolean)
     */
    public DefinitionList define(String word, boolean firstOnly)
                          throws DictException
    {
        if (firstOnly) {
            return define(word, Database.FIRST);
        }
        else {
            return define(word, Database.ANY);
        }
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#define(String, Database)
     */
    public DefinitionList define(String word, Database database)
                          throws DictException
    {
        if (!connected) {
            throw new IllegalStateException("Client not connected!");
        }

        DefinitionList list;

        try {
            writeCommand(Command.define(word,
                                        database.getCode()));

            String result = readResponse();

            list = createDefinitionList();

            if (Response.noMatch(result)) {
                log.debug("No match: " + result);
                return list;
            }

            if (Response.invalidDatabase(result)) {
                throw new InvalidDatabaseException("Invalid database: " + result);
            }

            while (Response.isOk(result)) {
                if (log.isDebugEnabled()) {
                    log.debug("Response: " + result);
                }


                // how many?
                result = readResponse();


                // ovo je celavo, kako znam da cu dobiti bas dobiti
                // 150 response 
                if (Response.definitionsCountFollows(result)) {

                    int definitionsCount = Response.findCount(result);

                    if (log.isDebugEnabled()) {
                        log.debug("Nasao " + definitionsCount + " definicija: " + result);
                    }

                    for (int i = 0; i < definitionsCount; i++) {
                        result = readResponse();


                        String[] split = Response.splitQuotedString(result, '"');

                        Definition definition = DefinitionFactory.getInstance()
                                                                 .createDefinition(split[1],
                                                                                   DatabaseList.findDatabase(split[2]),
                                                                                   readBody());

                        list.addDefinition(definition);
                    }
                }

                // result = readResponse();
            }
        }
        catch (IOException ioe) {
            throw new DictException("Error while querying for definition", ioe);
        }

        return list;
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#define(Match)
     */
    public DefinitionList define(Match match)
                          throws DictException
    {
        return define(match.getWord(), match.getDatabase());
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#define(MatchList)
     */
    public DefinitionList define(MatchList matchList)
                          throws DictException
    {
        matchList.goBeforeFirst();
        DefinitionList defList = new DefinitionList();
        Match match;
        while(matchList.hasNext()) {
            match = matchList.next();
            defList.addDefinitionList(define(match.getWord(), match.getDatabase()));
        }
        return defList;
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#listDatabases()
     */
    public DatabaseList listDatabases() {
        return dbList;
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#listStrategies()
     */
    public StrategyList listStrategies() {
        return strList;
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#match(String, Database)
     */
    public MatchList match(String word, Database database)
                    throws DictException
    {
        return match(word, Strategy.DEFAULT, database);
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#match(String, Strategy)
     */
    public MatchList match(String word, Strategy strategy)
                    throws DictException
    {
        return match(word, strategy, Database.ANY);
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#match(String)
     */
    public MatchList match(String word)
                    throws DictException
    {
        return match(word, Strategy.DEFAULT, Database.ANY);
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#match(String, boolean)
     */
    public MatchList match(String word, boolean firstDbOnly)
                    throws DictException
    {
        if (firstDbOnly) {
            return match(word, Database.FIRST);
        }
        else {
            return match(word);
        }
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#match(String, Strategy, boolean)
     */
    public MatchList match(String word, Strategy strategy, boolean firstDbOnly)
                    throws DictException
    {
        if (firstDbOnly) {
            return match(word, strategy, Database.FIRST);
        }
        else {
            return match(word, strategy);
        }
    }


    /**
     * @see net.zuckerfrei.jcfd.Dict#match(String, Strategy, Database)
     */
    public MatchList match(String word, Strategy strategy, Database database)
                    throws DictException
    {
        if (!connected) {
            throw new IllegalStateException("Client not connected");
        }

        MatchList list;

        try {
            writeCommand(Command.match(word,
                                       strategy.getCode(),
                                       database.getCode()));

            String result = readResponse();

            list = createMatchList();

            if (Response.isOk(result)) {
                result = readResponse();

                if (Response.noMatch(result)) {
                    log.debug("No match: " + result);
                    return list;
                }

                if (Response.invalidDatabase(result)) {
                    throw new InvalidDatabaseException("Invalid database: " + result);
                }

                if (Response.invalidStrategy(result)) {
                    throw new InvalidStrategyException("Invalid strategy: " + result);
                }

                int matchesCount = Response.findCount(result);

                for (int i = 0; i < matchesCount; i++) {
                    result = readResponse();

                    String[] split = StringUtils.split(result, " ", 2);
                    Match match = new Match(DatabaseList.findDatabase(split[0]), StringUtils.strip(split[1], "\""));

                    list.addMatch(match);
                }

                flushResponse();
            }
            else {
                throw new DictException("Unexpected code: " + result);
            }
        }
        catch (IOException ioe) {
            throw new DictException(ioe.getMessage(), ioe);
        }

        return list;

    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected DefinitionList createDefinitionList() {
        return new DefinitionList();
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected MatchList createMatchList() {
        return new MatchList();
    }


    /**
     * Fills the list of <code>Database</code>s available on this DICT server.
     *
     * @throws DictException
     * @throws IOException
     * @throws InvalidResponseException DOCUMENT ME!
     * @throws NoDatabasesException DOCUMENT ME!
     */
    protected void fillDatabaseList()
                             throws DictException, 
                                    IOException
    {
        writeCommand(Command.SHOW_DB);

        String result = readResponse();


        // This part is confusing, RFC doesn't specify 250 ok response,
        // while dictd 1.5.5/rf on Linux 2.4.18-6mdk from Mandrake 8.1
        // distribution sends that response code.
        if (!Response.isOk(result)) {
            throw new InvalidResponseException("Unexpected response, expecting 250 ok, got " + result);
        }

        result = readResponse();

        if (Response.noDatabases(result)) {
            throw new NoDatabasesException("No databases found. Check your server configuration! Server response: " + result);
        }

        int dbCount = Response.findCount(result);

        for (int i = 0; i < dbCount; i++) {
            result = readResponse();

            String[] split = StringUtils.split(result, " ", 2);

            String dbCode = split[0];

            String dbName = StringUtils.strip(split[1], "\"");
            Database db = new Database(dbCode, dbName);

            dbList.addDatabase(db);
        }

        flushResponse();

    }


    /**
     * DOCUMENT ME!
     *
     * @throws DictException DOCUMENT ME!
     * @throws IOException DOCUMENT ME!
     * @throws InvalidResponseException DOCUMENT ME!
     * @throws NoStrategiesException DOCUMENT ME!
     */
    protected void fillStrategyList()
                             throws DictException, 
                                    IOException
    {
        writeCommand(Command.SHOW_STRAT);

        String result = readResponse();


        // This part is confusing, RFC doesn't specify 250 ok response,
        // while dictd 1.5.5/rf on Linux 2.4.18-6mdk from Mandrake 8.1
        // distribution sends that response code.
        if (!Response.isOk(result)) {
            throw new InvalidResponseException("Unexpected response, expecting 250 ok, got " + result);
        }

        result = readResponse();

        // maybe we could fall back on default strategy?
        if (Response.noStrategies(result)) {
            throw new NoStrategiesException("No strategies found. Check your server configuration! Server response: " + result);
        }

        int dbCount = Response.findCount(result);

        for (int i = 0; i < dbCount; i++) {
            result = readResponse();

            String[] split = StringUtils.split(result, " ", 2);

            String strCode = split[0];

            String strName = StringUtils.strip(split[1], "\"");
            Strategy strategy = new Strategy(strCode, strName);

            strList.addStrategy(strategy);
        }

        flushResponse();
    }


    /**
     * Flushes the response buffer preparing it for the next read. Every DICT
     * reponse ends with "&lt;CR&gt;&lt;LF&gt;.&lt;CR&gt;&lt;LF&gt;". This
     * method simply traverses through the reponse while the dot is
     * encountered.
     *
     * @throws IOException when error while sending command is encountered.
     */
    protected void flushResponse()
                          throws IOException
    {

        String result = readResponse();

        while (!result.equals(DEFINITION_END)) {
            result = readResponse();
        }
    }


    /**
     * Send an identification string to DICT server. This string is not
     * mandatory but it's nice to introduce yourself when connected.
     *
     * @return DOCUMENT ME!
     *
     * @throws DictException DOCUMENT ME!
     */
    protected boolean identify()
                        throws DictException
    {
        try {
            writeCommand(Command.CLIENT);
        }
        catch (IOException ioe) {
            throw new DictException("Identification failed", ioe);
        }

        return true;
    }


    /**
     * Method <code>readBody</code> return <code>String</code> representing
     * definition body for the requested word. Override this method if you
     * want specially formatted body (e.g. XML formatted links etc.)
     *
     * @return String
     *
     * @throws IOException when error while reading is encountered.
     */
    protected String readBody()
                       throws IOException
    {

        String result = readResponse();
        StringBuffer buff = new StringBuffer(result);

        buff.append(DEFINITION_LINE_SEPARATOR);
        while (!result.equals(DEFINITION_END)) {
            result = readResponse();
            buff.append(result)
                .append(DEFINITION_LINE_SEPARATOR);
            if (log.isDebugEnabled()) {
                log.debug("readBody response: " + result);
            }
        }

        return buff.toString();
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     */
    protected String readResponse()
                           throws IOException
    {
        return br.readLine();
    }


    /**
     * Sends a string representing command to DICT server. This method
     * properly ends command with CRLF. Before that it firstly trims all
     * whitespaces from the beggining and the end of the command string.
     *
     * @param command Commant to be sent to DICT server.
     *
     * @throws IOException when error while sending command is encountered.
     * @throws IllegalArgumentException if <code>command</code> is null
     */
    protected final void writeCommand(String command)
                               throws IOException
    {
        if (command == null) {
            throw new IllegalArgumentException("Parametar cannot be null");
        }

        dos.writeBytes(command.trim() + COMMAND_LINE_END);
    }


    /**
     * Connects to DICT server. Uses host and port defined in the constructor.
     *
     * @throws DictException
     */
    private void connect()
                  throws DictException
    {
        try {
            socket = new Socket(host, port);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dos = new DataOutputStream(socket.getOutputStream());
            identify();
            if (!Response.isConnected(readResponse())) {
                throw new DictException("Invalid server response");
            }

            connected = true;

            fillDatabaseList();
            fillStrategyList();

        }
        catch (UnknownHostException e) {
            throw new DictException(e.getMessage(), e);
        }
        catch (IOException e) {
            throw new DictException(e.getMessage(), e);
        }
    }
}
