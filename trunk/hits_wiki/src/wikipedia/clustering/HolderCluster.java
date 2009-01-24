/*
 * HolderCluster.java - Store main data: vertices, edges, clusters
 * @author Andrew Krizhanovsky /mail: aka at mail.iias.spb.su/
 * Created on 21 June 2005
 *
 */

package wikipedia.clustering;

import wikipedia.kleinberg.*;
//import wikipedia.util.*;
import java.util.*;

/**
 * Class Description
 *
 */
public class HolderCluster {
    
    public DumpToGraphViz       dump;
    ArrayList<ClusterCategory>  clusters;
    HashMap<Integer, Category>  categories;     /** <cur_id of category, category object> */
    HashMap<Integer, Edge>      edges;
    
    /** Creates a new instance of HolderCluster */
    public HolderCluster() {
    }
    
}
