/*
 * Authorities.java - To calculate Hubs and Authority pages
 *
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import wikipedia.language.Encodings;
import wikipedia.sql.*;
import wikipedia.util.*;
import java.util.*;

public class Authorities {
    
    public  boolean         debug_graphviz; // dump graph via graphviz if debug is true
    private DCEL            dcel;
            Article         node;
    public  int             iter;           // number of passed iterations (updated after Iterate)

    public Authorities() {
        debug_graphviz = false;
        dcel = new DCEL();
        node = new Article();
    }

    /** Kleinberg p.10
     *      Let z denote the vector (1; 1; 1; : : :; 1)
     *      Set x0 := z:
     *      Set y0 := z:
     *      For i = 1; 2; : : :; k
     *      Apply the I operation to (xi−1; yi−1), obtaining new x-weights x0i.
     *      Apply the O operation to (x0i; yi−1), obtaining new y-weights y0i.
     *      Normalize x0i, obtaining xi.
     *      Normalize y0i , obtaining yi.
     *      End
     *  Return number of passed iteration
     */
    public int Iterate(Map<Integer, Article> nodes,float eps_error, SessionHolder session) {
        int             iter;
        float           links_in_number;
        Iterator<Article>  it;
        Article            node = new Article();
        float[]         total_error;
        
        if (null == nodes)
            return 0;
        int             n_nodes = nodes.size();
        
        //node.NormalizeXY(nodes);
        iter = 0;
        do {
            iter ++;
            it = nodes.values().iterator();
            while (it.hasNext()) {
                Article n = it.next();
                n.CalculateNewX(nodes, n_nodes);
                n.CalculateNewY(nodes, n_nodes);
            }

            node.NormalizeNewXNewY(nodes);
            
            // get x and y total change (error)
            total_error = node.UpdateXY(nodes);
            
            if (null != session.dump) {                
                // Print the total error for the current iteration
                /*
                session.dump.file.Print("iter:" + iter + 
                                " total change x:" +  total_error[0] +
                                             " y:" +                   total_error[1]  +
                                           " x+y:" + (total_error[0] + total_error[1]) + "\n");
                session.dump.file.Flush();
                */
                /* 
                // Print the dot file: old
                session.dump.file.WriteNew(session.dump.path + i + ".dot", session.dump.Dump(nodes), "UTF8");
                // append dot command to bat file
                String bat_text = "\ndot.exe -Tjpeg " + session.dump.filename_dot +i+ ".dot -v -o " + session.dump.filename_dot +i+ ".jpeg\n";
                session.dump.file.Append(session.dump.dir + session.dump.filename_bat, bat_text, "Cp866");
             */
            }
            
        }while (eps_error < total_error[0] + total_error[1]);
        
        if (null != session.dump) {
            String s = StringUtilRegular.encodeRussianToLatinitsa(session.source_page_title, Encodings.enc_java_default, Encodings.enc_int_default);
            session.dump.DumpDotBat(nodes,  s + "_iter.dot");
        }
                
        return iter;
    }
    
    
    
    /** Get all HUBS - nodes which point to the source node.
     *  Sort these nodes by Y value
     */
    public List<Article> getAllHubsSortedByY (Map<Integer, Article> nodes,
                                           int source_article_id) {
        Article n = new Article();
        if(null == nodes)
            return null;
        
        List<Article>nodes_pointed = new ArrayList<Article>();
        Article source = nodes.get(source_article_id);
        if (null != source.links_in) {
            for(int i=0; i<source.links_in.length; i++) {
                Article add = nodes.get( source.links_in[i] );
                nodes_pointed.add(add);
            }
        }
        Collections.sort(nodes_pointed, Article.Y_ORDER);

        return nodes_pointed;
    }
    
    /** Get <=n synonyms nodes which are referred from hubs (hubs pointed to source node).
     *      Synonyms array should be locally sorted within the hub (i.e. links of one hub should be sorted)
     */
    public List<Article> getAuthoritiesSortedByX (Map<Integer, Article> nodes,
                                            List<Article> hubs,
                                            int n_synonyms) {
        Article n = new Article();
        if(null == nodes)
            return null;
        
        int     i, j;
        int     page_synonyms = 0;
        Map<Integer, Article> global_hash = new HashMap<Integer, Article>();
        List<Article>         global_list = new ArrayList<Article>();
        
        HUBS_CYCLE:
        for(i=0; i<hubs.size(); i++) {
            Article hub = hubs.get(i);
            List<Article>  local_list = new ArrayList<Article>();
            for(j=0; j<hub.links_out.length; j++) {
                Article candidate = nodes.get(hub.links_out[j]);
                if (NodeType.ID_SOURCE_ARTICLE != candidate.type &&
                    !global_hash.containsKey(candidate.page_id)) 
                {
                    local_list.add(candidate);
                }
            }
            
            Collections.sort(local_list, Article.X_ORDER);
            
            // update global hash, global list
            for(j=0; j<local_list.size(); j++) {
                if (page_synonyms++ >= n_synonyms)
                    break HUBS_CYCLE;
                global_hash.put(local_list.get(j).page_id, local_list.get(j));
                global_list.add(local_list.get(j));                         //global_list.addAll(local_list);
            }
        }
        return global_list;
    }
      
    
    public String getTitles(List<Article> nodes, String delimiter) {
        if (null == nodes || 0 == nodes.size())
            return null;
        String titles = "";
        
        if(1 == nodes.size()) {
            return nodes.get(0).page_title;
        }
        for(int i=0; i<nodes.size()-1; i++) {
            titles += nodes.get(i).page_title + delimiter;
        }
        return titles + nodes.get( nodes.size()-1 ).page_title;
    }

    

    /** Calculate x and y values of nodes.
     *  The question to Kleinberg's algorithm: Could the hub be authoritative page?
     *  I.e. if the word (it is really synonym) was selected as the hub, then could it appear in the list of synonyms?
     */
    public List<Article> Calculate(Map<Integer, Article> nodes, float eps_error, 
                            int n_synonyms, SessionHolder session) {
        if (null == nodes)
            return null;
        //link.CountLinks (session.connect, nodes);
        
        iter = Iterate(nodes, eps_error, session);
        
        // Report the pages with the c largest coordinates in xk as authorities.
        
        List<Article> hubs = getAllHubsSortedByY(nodes, session.source_article_id);
        node.SetType((Article[])hubs.toArray(Article.NULL_ARTICLE_ARRAY), NodeType.HUB);
        if (null != session.dump) { 
            session.dump.file.Open(true, "Cp1251");
            session.dump.file.PrintNL( "\nhubs (sorted by Y) pointed to the source article:\n" +
                                        getTitles(hubs, " | ") );
            session.dump.file.Flush();
        }

        List<Article> synonyms = getAuthoritiesSortedByX(nodes, hubs, n_synonyms);
        if (null != session.dump) {
            session.dump.PrintSynonyms(session, synonyms);
        }
        session.category_black_list.fillCategoryNodesIfBlackListEmpty(synonyms);
        
        // set type 0 for authorities (best synonyms, max x) in base_nodes
        Article[] synonyms_array = (Article[])synonyms.toArray(Article.NULL_ARTICLE_ARRAY);
        node.SetType(synonyms_array, NodeType.AUTHORITY);
        
        if (null != session.dump) { 
            session.dump.DumpDotBat(nodes, 
                    session.source_page_title + "_synonyms_triangle.dot"); }
        
        return synonyms;
    }
    
    
    /** Get names of synonyms (names or page_title of the best hubs) using Calculate()
     */
    public String[] GetSynonyms(Article[] hubs)
    {
        int     i, n_synonyms, counter;
        
        if (null == hubs)
            return null;
        
        // Check: Does hubs[].page_id contains source_article_id
        boolean b_contains = false;
        for (i=0; i<hubs.length; i++) {
            if (NodeType.ID_SOURCE_ARTICLE == hubs[i].type) {
                b_contains = true;
                break;
            }
        }
        String[] synonyms = null;
        n_synonyms = b_contains ? hubs.length - 1 : hubs.length;
        if (0 == n_synonyms)
            return null;
        synonyms = new String[n_synonyms];
        counter = 0;
        for (i=0; i<hubs.length; i++) {
            if (NodeType.ID_SOURCE_ARTICLE != hubs[i].type) {
               synonyms [counter ++] = hubs[i].page_title;
            }
        }
        return synonyms;
    }

    /** Concatenate source synonym and list of synonyms (joined by delimiter) */
    public String SynonymsToString( String source_synonym, List<Article> synonyms,String delimiter)
    {
        //String text = new String(Encodings.Latin1ToUTF8(source_synonym));
        String text = source_synonym;
        
        if (null != synonyms) {
            if(0 < synonyms.size()) {
                text += delimiter;
            }
            for(int i=0; i<synonyms.size()-1; i++) {
                text += synonyms.get(i).page_title + delimiter;
                //text += Encodings.Latin1ToUTF8(synonyms.get(i).page_title) + delimiter;
                
            }
            if(0 < synonyms.size()) {
                text += synonyms.get(synonyms.size()-1).page_title;
                //text += Encodings.Latin1ToUTF8(synonyms.get(synonyms.size()-1).page_title);
            }
        }
        //text +=  "\n";
        return text;
    }
    
    /** Write (Append) list of synonyms for the source synonym (joined by delimiter) to the file */
    public void AppendSynonyms( String source_synonym, List<Article> synonyms,String delimiter,
                                DumpToGraphViz dump)
    {
        if (null != dump) {
            dump.file.Print(SynonymsToString(source_synonym, synonyms, delimiter));
            dump.file.Flush();
        }
    }
}


    /** get first n largest Nodes via sorting
     */
/*    public Article[] getLargestXNodes( Map<Integer, Article> nodes,
                                    int n_largest_nodes) {
        Article    n = new Article();
        if(null == nodes)
            return null;
                
        List<Article>sorted_nodes = new ArrayList<Article>(nodes.values());
        Collections.sort(sorted_nodes, n.X_ORDER);
        
        int     n_max = Math.min(n_largest_nodes, sorted_nodes.size());
        Article[]  result = new Article[n_max];
        for(int i=0; i<n_max; i++) {
            result [i] = sorted_nodes.get(i);
        }
        return result;
    }
 */   





