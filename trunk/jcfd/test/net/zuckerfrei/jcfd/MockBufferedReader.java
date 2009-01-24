package net.zuckerfrei.jcfd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


/**
 * DOCUMENT ME!
 *
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class MockBufferedReader
    extends BufferedReader
{

    //~ Instance variables ====================================================

    /** 
     * DOCUMENT ME! 
     */
    public String[] content;

    /** 
     * DOCUMENT ME! 
     */
    public int position = 0;

    /** 
     * DOCUMENT ME! 
     */
    public int readLineCount = 0;

    //~ Constructors ==========================================================

    /**
     * Constructor for MockBufferedReader.
     *
     * @param in
     * @param sz
     */
    public MockBufferedReader(Reader in, int sz) {
        super(in, sz);
    }


    /**
     * Constructor for MockBufferedReader.
     *
     * @param in
     */
    public MockBufferedReader(Reader in) {
        super(new StringReader("mock"));
    }

    //~ Methods ===============================================================

    /**
     * DOCUMENT ME!
     *
     * @param content DOCUMENT ME!
     */
    public void setContent(String[] content) {
        this.content = content;
    }


    /**
     * @see java.io.BufferedReader#readLine()
     */
    public String readLine()
                    throws IOException
    {
        readLineCount++;
        return content[position++];
    }
}
