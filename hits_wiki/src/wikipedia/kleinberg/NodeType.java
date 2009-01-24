/*
 * NodeType.java
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import java.io.*;
import java.lang.*;


/** Types of nodes<br>
 * -1 - source article, 1 - root, 2 - base set, 3 default value<br>
 * 0  - best authorities (found synonyms by the program)<br>
 * -2 - hubs (Hubs can not be synonyms, they refer to synonyms)<br>
 * -3 - category's article
 * -4 - rated synonyms by user
 *
 * See more in: Effective Java. Programming language Guide. J.Bloch. 
 */
public class NodeType {
    private final int number;
    
    /*
    public final static byte DEFAULT_TYPE       =  3;
    public final static byte BASE_TYPE          =  2;
    public final static byte ROOT_TYPE          =  1;
    public static final byte ID_SOURCE_ARTICLE  = -1;
    public static final byte HUB_TYPE           = -2;
    //public static final byte CATEGORY_TYPE    = -3;
    // TO WRITE OTHERS...
    // ...
    */
    
    private NodeType(int number) { this.number = number; }
    
    //public String toString() { return Integer.toString(number); }
    public int toInt() { return number; }
    
    public static final NodeType DEFAULT        = new NodeType(3);
    /** Base set */
    public static final NodeType BASE           = new NodeType(2);
    /** Root set */
    public static final NodeType ROOT           = new NodeType(1);
    
    /** Authorities (best synonyms, max x) in base_nodes */
    public static final NodeType AUTHORITY      = new NodeType(0);
    
    /** Hubs can not be synonyms, they refer to synonyms */
    public static final NodeType HUB            = new NodeType(-2);
    /** Category's article */
    //public static final NodeType CATEGORY_TYPE = new NodeType(-3);
    /** Synonyms rated by user */
    public static final NodeType RATED_SYNONYMS = new NodeType(-4);
    
    /** Source article */
    public static final NodeType ID_SOURCE_ARTICLE = new NodeType(-5);
    
    
    /** Gets NodeType by number */
    public static NodeType get(int number) throws NullPointerException
    {
        if(number == DEFAULT.toInt()) {
            return DEFAULT;
        } else if(number == BASE.toInt()) {
            return BASE;
        } else if(number == ROOT.toInt()) {
            return ROOT;
        } else if(number == AUTHORITY.toInt()) {
            return AUTHORITY;
        } else if(number == ID_SOURCE_ARTICLE.toInt()) {
            return ID_SOURCE_ARTICLE;
        } else if(number == HUB.toInt()) {
            return HUB;
        } else if(number == RATED_SYNONYMS.toInt()) {
            return RATED_SYNONYMS;
        } else {
            throw new NullPointerException("Null NodeType");
        }
    }
}
