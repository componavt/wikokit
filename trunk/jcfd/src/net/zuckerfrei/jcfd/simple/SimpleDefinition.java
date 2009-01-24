package net.zuckerfrei.jcfd.simple;

import net.zuckerfrei.jcfd.AbstractDefinition;
import net.zuckerfrei.jcfd.Database;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
class SimpleDefinition
    extends AbstractDefinition
{

    //~ Instance variables ====================================================

    /** DOCUMENT ME! */
    static Log log = LogFactory.getLog(SimpleDefinition.class);

    //~ Constructors ==========================================================

    /**
     * Constructor for SimpleDefinition.
     *
     * @param word
     * @param database
     * @param content
     */
    public SimpleDefinition(String word, Database database, String content) {
        super(word, database, content);
    }

    //~ Methods ===============================================================

    /**
     * DOCUMENT ME!
     *
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof SimpleDefinition)) {
            return false;
        }

        SimpleDefinition tmp = (SimpleDefinition) o;

        if (tmp.getWord()
               .equals(this.getWord()) && tmp.getDatabase()
                                             .equals(this.getDatabase()) && tmp.getContent()
                                                                               .equals(this.getContent())) {    // implement the comparisons
            return true;
        }

        return false;
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {

        int result = 17;

        result = 37 * result + getWord()
                                   .hashCode();
        result = 37 * result + getDatabase()
                                   .hashCode();
        result = 37 * result + getContent()
                                   .hashCode();

        return result;

    }
    
    
    /**
     * @see net.zuckerfrei.jcfd.Definition#getLinks()
     */
    public String[] getLinks() {
        return super.getLinks();
    }

}
