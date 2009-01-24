/*
 * DumpToGraphViz.java - Dumps data to GraphViz .dot files.
 * 
 * Copyright (c) 2005 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikipedia.kleinberg;

import wikipedia.language.Encodings;
import wikipedia.clustering.*;
import wikipedia.util.*;
import wikipedia.sql.*;
import java.util.*;

public class DumpToGraphViz {

    public  FileWriter      file;
    public  FileWriter      file_dot;
    
    /** User can run the Windows bat file to create .jpeg and .svg files from .dot */
    public  FileWriter      file_bat;
    
    /** The *nix executable script does the same things as the file_bat but in *nix environment */
    public  FileWriter      file_sh;
    
    public boolean  enable_file;        
    public boolean  enable_file_dot;    // if false then don't write text to .dot and .bat files
    
    public DumpToGraphViz () {
        file     = new FileWriter();
        file_dot = new FileWriter();
        file_bat = new FileWriter();
        file_sh  = new FileWriter();
        
        enable_file     = true;
        enable_file_dot = true;
    }
    
    /** Write header "#!/bin/sh" to .sh file if it is empty */
    public void WriteShellHeaderToEmptyFile() {
        if(enable_file_dot && 0 == file_sh.GetFileLength()) {
            file_sh. Print("#!/bin/sh");
            file_sh. Flush();
        }
    }
    
    public String Header() {
        return new String(
                // "strict" forbids the creation of self-arcs and multi-edges; they are ignored in the input file.
                "strict digraph G {\n" +  
                //"digraph G {\n" +
                
                "  compound=true;\n" +              // to connect clusters
                //"  size=\"8,6\"; ratio=fill;\n" +
                //"  size=\"8,6\";\n" +
                //"  node [fontname=\"ARIALUNI\",fontsize=10,color=black,fillcolor=white,fontcolor=black,shape=circle];\n" +
                
                // fontname ARIALUNI
                //"  node [fontname=\"ARIALUNI\",fontsize=10,color=black,fillcolor=white,fontcolor=black,shape=circle,width=\"0.5\",height=\"0.5\"];\n" +
                //"  edge [fontname=\"ARIALUNI\",fontsize=10,color=black,fontcolor=black];\n");
                
                // wo fontname
                "  node [fontsize=10,color=black,fillcolor=white,fontcolor=black,shape=circle,width=\"0.5\",height=\"0.5\"];\n" +
                "  edge [fontsize=10,color=black,fontcolor=black];\n");
        
    }
    
    public String Footer() {
        return new String("\n}");
    }

    
    //graphVizCluster
    public <T> void DumpCluster (Map<Integer, Article> articles,
                                 Map<Integer, Category> categories,
                                 List<ClusterCategory> clusters, String nodes_type)
    {
        if (!enable_file_dot)
            return;

        for(int i=0; i<clusters.size(); i++) {
            ClusterCategory c = clusters.get(i);
            file_dot.PrintNL(c.graphVizCluster(articles, categories));
            file_dot.PrintNL(c.graphVizClusterEdges());
        }
        file_dot.Flush();
        
        WriteShellHeaderToEmptyFile();
        String s = nodes_type + ClusterCategory.getStatistics(clusters);
        file_bat.Print("\n:: " + s);
        file_sh .Print("\necho '"  + s + "'");
        file_bat.Flush();
        file_sh .Flush();
    }
    
    /**
     * @param nodes_type    the type of dumped nodes will be stored in \.bat file
     */
    public <T> void Dump (Map<Integer, T> hash_node, String nodes_type) {
        if (!enable_file_dot)
            return;

        Iterator<Integer>   it = hash_node.keySet().iterator();
        Integer             i;
        
        while (it.hasNext()) {
            int  id   = it.next();
            Node node = (Node)hash_node.get(id);
            
            file_dot.PrintNL(node.GraphVizNode());
            file_dot.PrintNL(node.GraphVizLinksOut());
 
        }
        file_dot.Flush();
        
        WriteShellHeaderToEmptyFile();
        String s = nodes_type + GetStatisticsHashMap(hash_node);
        file_bat.Print("\n:: " + s);
        file_sh .Print("\necho '"  + s + "'");
        file_bat.Flush();
        file_sh. Flush();
    }
    
    
    /**
     * Use the following command sequence to dump data to graphviz dot file:
     * if( DotOpen ) {Dump(); Dump(); ... BatEnd(); }
     */ 
    public boolean DotOpen (String filename_new) {
        if (!enable_file_dot)
            return false;
        
        file_dot.SetFilename(filename_new);
        file_dot.Open(false, "UTF8");
        file_dot.PrintNL(Header());
        return true;
    }
    public <T> void BatEnd () {
        file_dot.Print(Footer());
        file_dot.Flush();
        
        file_bat.Print(GetDotCommand("svg", true));
        file_sh. Print(GetDotCommand("svg", false));
        file_bat.Flush();
        file_sh. Flush();
    }
    
    
    public <T> void DumpDotBat (Map<Integer, T> nodes, String filename_new) {
        if (!enable_file_dot)
            return;
        
        file_dot.SetFilename(filename_new);
        file_dot.Open(false, "UTF8");
        file_dot.PrintNL(Header());
        Dump(nodes, "");
        file_dot.Print(Footer());
        file_dot.Flush();
        
        file_bat.Print(GetStatisticsHashMap(nodes) + GetDotCommand("jpeg", true));
        file_sh. Print(GetStatisticsHashMap(nodes) + GetDotCommand("jpeg", false));
        file_bat.Flush();
        file_sh. Flush();
    }
    
    public void DumpDotBat(Article[] nodes, String filename_new) {
        if (!enable_file_dot)
            return;
        
        file_dot.SetFilename(StringUtilRegular.encodeRussianToLatinitsa(filename_new, Encodings.enc_java_default, Encodings.enc_int_default));
        file_dot.Open(false, "UTF8");
        file_dot.PrintNL(Header());
        
        
        for (Article a:nodes) {
            file_dot.PrintNL(a.GraphVizNode());
            file_dot.PrintNL(a.GraphVizLinksOut());
        }
        file_dot.Print(Footer());
        file_dot.Flush();
        
        WriteShellHeaderToEmptyFile();
        
        file_bat.Print(GetDotCommand("svg", true));
        file_sh. Print(GetDotCommand("svg", false));
        file_bat.Flush();
        file_sh. Flush();
    }
    
    
    
    /** Return the string like: 
     *  :: Робот.dot     vertices:9  edges:11
     */
    public <T> String GetStatisticsHashMap (Map<Integer, T> n) {
        if (null == n || !enable_file_dot)
            return "";
        return " " + file_dot.GetFilename() + " vertices:" + n.values().size() +
                                              " edges:"    + DCEL.CountLinksIn(n);
    }
    
    
    /** Return the string like:
     * <pre>
     *  if (b_windows)
     *      dot.exe -Tjpeg Робот.dot -v -o Робот.jpeg (when the output_format is jpeg)
     *  else
     *      fdp     -Tjpeg Робот.dot -v -o Робот.jpeg
     * </pre>
     */
    public String GetDotCommand(String output_format, boolean b_windows) {
        if (!enable_file_dot)
            return "";
        
        String dot_name = b_windows ? "dot.exe" : "fdp";
        
        return "\n" + dot_name + " -T" + output_format + " " + file_dot.GetFilename() + 
                " -o " + file_dot.GetFilenameWoExt() + "." + output_format + "\n";  
    }
    
    public void PrintSynonyms(SessionHolder session,List<Article> nodes) {
        if (null == nodes || !enable_file)
            return;
        
        file.PrintNL( "synonyms (authority pages sorted by X):\n");
        file.Flush();
        
        String titles = " N:synonym:count_l_from:count_l_to\n";
        file.PrintNL(titles);
        file.Flush();
        
        int count_l_from, count_l_to;
                
        for(int i=0; i<nodes.size(); i++) {
            Article n = nodes.get(i);
            titles = String.format("%2d:%-20s:%2d:%2d",     // :%s
                                    i,  n.page_title,             //n.count_l_from, n.count_l_to,
                                              n.GetLinksOutLength(), 
                                                  n.GetLinksInLength());
            file.PrintNL(titles);
            file.Flush();
            
            //if(null != session.category_black_list.getBlackList()) {
                String[] categories = session.category_black_list.getCategoryUpIteratively(n.page_id, null);
                titles = String.format(":%s",
                                        StringUtil.join("|", categories));
                file.PrintNL(titles);
            //}
            file.Flush();
        }
    }
}