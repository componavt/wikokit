/*
 * DCEL.java    Dcel (Doubly Connected Edge List). Operations with HashMap<Integer, Node or Article>
 *
 * Copyright (c) 2005 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import wikipedia.util.*;
import java.util.*;

public class DCEL {
    
    public DCEL() {
    }

    //hm.values().size();  - number of vertices
    
    /** 
     * Count half number of all arcs in the structure
     */
    public static <T> int CountLinksIn(Map<Integer, T> hm) {

        Iterator<T> it = hm.values().iterator();
        Integer     counter;
        
        counter = 0;
        while (it.hasNext()) {
            
            /*T a = it.next();
            if (!(a instanceof Node))
                return 0;
            */
            Node node = (Node)it.next();
            if(null != node.links_in)               // count only links_in (i.e. skip links_out)
                counter += node.links_in.length;    // because every link has the begin in the structure
        }
        return counter;
    }
    
}
