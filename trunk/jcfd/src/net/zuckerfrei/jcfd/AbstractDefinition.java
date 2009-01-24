package net.zuckerfrei.jcfd;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;


/**
 * Abstract <code>Definition</code> implementing the most common
 * functionalities.
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public abstract class AbstractDefinition
    implements Definition
{

    //~ Static variables/initializers =========================================

    /** Logger. */
    static Log log = LogFactory.getLog(AbstractDefinition.class);

    /** 
     * DOCUMENT ME! 
     */
    protected static String linkRegexp = "{[\\w\\s-]+}";

    /** 
     * DOCUMENT ME! 
     */
    static final PatternCompiler regexpCompiler = new Perl5Compiler();

    /** 
     * DOCUMENT ME! 
     */
    // TODO
    // Each instance has its own matcher. Can we have static matchers?
    PatternMatcher matcher = new Perl5Matcher();
    
    static Pattern pattern;
    
    static {
        try {
            pattern = regexpCompiler.compile(linkRegexp);
        }
        catch (MalformedPatternException mpe) {
            throw new RuntimeException(mpe.getMessage());
        }
    }

    //~ Instance variables ====================================================

    /** Defined word. */
    private String word;

    /** The database thw word is found in. */
    private Database database;

    /** The content returned from the server. */
    private Object content;

    /** 
     * DOCUMENT ME! 
     */
    List links = new ArrayList();

    //~ Constructors ==========================================================

    /**
     * Consturctor.
     *
     * @param word DOCUMENT ME!
     * @param database DOCUMENT ME!
     * @param content DOCUMENT ME!
     */
    public AbstractDefinition(String word, Database database, Object content) {
        this.word = word;
        this.database = database;
        this.content = content;
    }

    //~ Methods ===============================================================

    /**
     * @see net.zuckerfrei.jcfd.Definition#getContent()
     */
    public Object getContent() {
        return content;
    }


    /**
     * @see net.zuckerfrei.jcfd.Definition#getDatabase()
     */
    public Database getDatabase() {
        return database;
    }


    /**
     * @see net.zuckerfrei.jcfd.Definition#getLinks()
     */
    public String[] getLinks() {
        discoverLinks();
        return (String[]) links.toArray(new String[]{});
    }


    /**
     * @see net.zuckerfrei.jcfd.Definition#getWord()
     */
    public String getWord() {
        return word;
    }


    /**
     * DOCUMENT ME!
     */
    protected void discoverLinks() {
        PatternMatcherInput input = new PatternMatcherInput(content.toString());
        String term;
        while (matcher.contains(input, pattern)) {
            term = normalizeString(matcher.getMatch().toString());
            links.add(StringUtils.stripStart(StringUtils.stripEnd(term, "}"), "{"));
        }
    }
    
    private String normalizeString(String str) {
        String[] tmp = StringUtils.split(str);
        StringBuffer buff = new StringBuffer();
        String tmp2;
        for (int i = 0; i < tmp.length; i++) {
            tmp2 = tmp[i].trim();
            if (tmp2.length() > 0) {
                buff.append(tmp2).append(" ");
            }
        }
        
        return buff.toString().trim();
    }
}
