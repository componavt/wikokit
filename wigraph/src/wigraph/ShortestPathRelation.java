/* ShortestPathRelation.java - visualization of semantic relations in Wiktionary parsed database.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wigraph;

import wikt.sql.TPage;
import wikt.sql.TLang;
import wikt.sql.TPOS;
import wikt.sql.TRelation;
import wikt.sql.TRelationType;
import wikt.constant.Relation;
import wikipedia.sql.Connect;
import wikipedia.util.StringUtil;

import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.visualization.renderers.EdgeLabelRenderer;

import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.io.*;
import java.util.*;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.generators.random.EppsteinPowerLawGenerator;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.shortestpath.BFSDistanceLabeler;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import edu.uci.ics.jung.graph.util.Graphs;
import edu.uci.ics.jung.graph.ObservableGraph;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.event.GraphEventListener;

/** Visualization of semantic relations in Wiktionary parsed database.
 */
public class ShortestPathRelation extends JPanel {

	//private static final long serialVersionUID = 7526217664458188502L;

    private static Connect ruwikt_parsed_conn;
    private static DijkstraShortestPath<String,Integer> alg;
    private static Graph g_all_relations;

    final VisualizationViewer<String,Number> vv;
    final Layout<String,Number> layout;

    /** List of words separated by comma */
    private     JTextField  word_set1;
    private     JTextField  word_set2;
    private     JTextField  result_len;

    //String[] word_set1 = {"доклад", "рапорт", "донесение", "сообщение", "рассказ"}; // "отчет", , "описание событий"
    private static String   INITIAL_WORD_SET1 = "доклад,рапорт,донесение,сообщение,рассказ"; // "отчет", , "описание событий"
    private static String   INITIAL_WORD_SET2 = "обнародование,издание,публикация";        // "опубликование" "оглашение", "печатание",
    //String[] word_set2 = {"обнародование", "издание", "публикация"}; // "опубликование" "оглашение", "печатание",

    /** Searches shortest path using word sets 1 & 2, redraw picture */
    final       JButton     search_path_btn;

	/**
	 * Starting vertex
	 */
	private String mFrom;

	/**
	 * Ending vertex
	 */
	private String mTo;
	private Graph<String,Number> mGraph;    //SparseGraph<String, Integer> mGraph;
	private Set<String> mPred;

    public void initialize() {
        ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);

        // It is supposed that Wiktionary parsed database has been created
        TLang.createFastMaps(ruwikt_parsed_conn);
        TPOS.createFastMaps(ruwikt_parsed_conn);
        TRelationType.createFastMaps(ruwikt_parsed_conn);

        // edge creation 1
        // for each TRelation: get page<->wiki_text + type of relation

        String filename1 = "relation_pairs.txt";
        String filename2 = "unique_words.txt";
        Map<String,List<String>> m_words = null;
        List<String> unique_words = null;
        try {
            System.out.println("Loading relations from file " + filename1 + " ...");
            m_words      = LoadRelations.loadMapToLists(filename1);
            unique_words = LoadRelations.loadListString(filename2);
        } catch(IOException ex) {
            System.err.println("IOException (wigraph SerializeRelationsToFile.java main()):: Serialization failed (" + filename1 + "), msg: " + ex.getMessage());
        } catch(ClassNotFoundException ex) {
            System.err.println("IOException (wigraph SerializeRelationsToFile.java main()):: Serialization failed (" + filename1 + "), msg: " + ex.getMessage());
        }

        System.out.println("Creating graph...");
        g_all_relations = GraphCreator.createGraph(m_words, unique_words);
        assert(null != g_all_relations);
        System.out.println("Calculation Dijkstra shortest path...");
        alg = new DijkstraShortestPath(g_all_relations);
    }
    
    /**
	 * @return the graph for this demo
	 */
	Graph<String, Number> getGraph(String[] word_set1, String[] word_set2) {
        //Graph<String, Number> g = new SparseGraph<String, Number>();

        //create a graph
    	Graph<String,Number> ig = Graphs.<String,Number>synchronizedGraph(new SparseGraph<String,Number>());

        ObservableGraph<String,Number> og = new ObservableGraph<String,Number>(ig);
        og.addGraphEventListener(new GraphEventListener<String,Number>() {

			public void handleGraphEvent(GraphEvent<String, Number> evt) {
				System.err.println("got "+evt);

			}});
        Graph<String, Number> g = og;

        // extract all vertices which belong to shortest paths in graph 'g_all_relations'

        //String word1 = "WAN"; //String word2 = "network";   //WAN - LAN - network
        for(String word1 : word_set1) {
            g.addVertex(word1);
            for(String word2 : word_set2) {
                g.addVertex(word2);
                String[] word_path = null;

                if(g_all_relations.containsVertex(word1) && g_all_relations.containsVertex(word2)) {

                    if(null != TPage.get(ruwikt_parsed_conn, word1) &&
                       null != TPage.get(ruwikt_parsed_conn, word2))
                    {
                        System.out.println("Starting search path ['" + word1 + "', '" + word2 + "']");
                        word_path = PathSearcher.getShortestPath(g_all_relations, alg, word1, word2);
                        if(word_path.length > 0) {
                            int len = word_path.length - 1;
                            System.out.println("There is a path from '" + word1 + "' to '" + word2 + "', length = " + len);
                            System.out.println("" + 0 + ": " + word_path[0]);

                            g.addVertex(word_path[0]);
                            for(int i=1; i<word_path.length; i++) {
                                Relation r = TRelation.getRelationType(ruwikt_parsed_conn, word_path[i-1], word_path[i]);
                                String rel_name = null == r ? "" : r.toString();
                                System.out.println("" + i + ".: " + rel_name + "["+ word_path[i-1] + ", "+ word_path[i] + "]");
                                
                                g.addVertex(word_path[i]);
                                g.addEdge(g.getEdgeCount(), word_path[i-1], word_path[i]);
                            }
                        }
                    }
                }

                if(null == word_path || 0 == word_path.length)
                    System.out.println("There is no path from '" + word1 + "' to '" + word2 + "'.");
            }
        }
        return g;
	}

    /** Removes vertices without edges. */
    private void removeIsolatedVertices(Graph<String, Number> g) {
        
        List<String> vertices_wo_edges = new ArrayList<String>();
        for(String v : g.getVertices())
            if(g.degree(v) == 0)
                vertices_wo_edges.add(v);

        for(String v : vertices_wo_edges)
            g.removeVertex(v);
    }

    EdgeLabelRenderer edgeLabelRenderer;
    
	public ShortestPathRelation() {
        
        initialize();

        String[] w1 = INITIAL_WORD_SET1.split(",");
        String[] w2 = INITIAL_WORD_SET2.split(",");
		this.mGraph = getGraph(w1, w2);
        removeIsolatedVertices(this.mGraph);
        
		setBackground(Color.WHITE);
		// show graph
        layout = new FRLayout<String,Number>(mGraph);

        vv = new VisualizationViewer<String,Number>(layout);
        vv.setBackground(Color.WHITE);

        vv.getRenderContext().setVertexDrawPaintTransformer(new MyVertexDrawPaintFunction<String>());
        vv.getRenderContext().setVertexFillPaintTransformer(new MyVertexFillPaintFunction<String>());
        vv.getRenderContext().setEdgeDrawPaintTransformer(new MyEdgePaintFunction());
        vv.getRenderContext().setEdgeStrokeTransformer(new MyEdgeStrokeFunction());
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
        vv.setGraphMouse(new DefaultModalGraphMouse<String, Number>());
        vv.addPostRenderPaintable(new VisualizationViewer.Paintable(){

            public boolean useTransform() {
                return true;
            }
            public void paint(Graphics g) {
                if(mPred == null) return;

                // for all edges, paint edges that are in shortest path
                for (Number e : layout.getGraph().getEdges()) {

                    if(isBlessed(e)) {
                        String v1 = mGraph.getEndpoints(e).getFirst();
                        String v2 = mGraph.getEndpoints(e).getSecond();
                        Point2D p1 = layout.transform(v1);
                        Point2D p2 = layout.transform(v2);
                        p1 = vv.getRenderContext().getMultiLayerTransformer().transform(Layer.LAYOUT, p1);
                        p2 = vv.getRenderContext().getMultiLayerTransformer().transform(Layer.LAYOUT, p2);
                        Renderer<String,Number> renderer = vv.getRenderer();
                        renderer.renderEdge(
                                vv.getRenderContext(),
                                layout,
                                e);
                    }
                }
            }
        });


        edgeLabelRenderer = vv.getRenderContext().getEdgeLabelRenderer();

        Transformer<Number,String> stringer = new Transformer<Number,String>(){
            public String transform(Number e) {
                Relation r = TRelation.getRelationType(ruwikt_parsed_conn,
                        mGraph.getEndpoints(e).getFirst(),
                        mGraph.getEndpoints(e).getSecond());

                /*System.out.println("word1=" + mGraph.getEndpoints(e).getFirst() +
                                 "; word2=" + mGraph.getEndpoints(e).getSecond());
                if(null != r)
                    System.out.println("relation=" + r.toString());*/

                return null == r ? "" : r.toString();

                //return "Edge:"+mGraph.getEndpoints(e).toString();
            }
        };
        vv.getRenderContext().setEdgeLabelTransformer(stringer);


        // button to search a shortest path using word sets 1 & 2, redraw picture
        search_path_btn = new JButton("Search");
        search_path_btn.setToolTipText("Search a shortest path");
        search_path_btn.setBackground(Color.decode("#D8C0C0"));

        search_path_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // delete all vertices
                List<String> vertices = new ArrayList<String>();
                vertices.addAll(mGraph.getVertices());
                for(String v : vertices)
                        mGraph.removeVertex(v);

                String[] w1_source = word_set1.getText().split(",");
                String[] w2_source = word_set2.getText().split(",");

                w1_source = StringUtil.trim(w1_source);
                w2_source = StringUtil.trim(w2_source);

                // get only words in Wiktionary, i.e. in a graph
                String[] w1 = GraphCreator.getOnlyVertexInGraph(g_all_relations, w1_source);
                String[] w2 = GraphCreator.getOnlyVertexInGraph(g_all_relations, w2_source);

                mGraph = getGraph(w1, w2);
                removeIsolatedVertices(mGraph);

                layout.setGraph(mGraph);
                vv.repaint();

                DistanceData dist = PathSearcher.calcPathLenRelatedness(g_all_relations, alg, w1, w2);
                result_len.setText("Shortest path len: min="    + dist.min +
                                                    ", average="+ dist.average +
                                                    ", max="+ dist.max);
                                                    
                System.out.println("Word set 1 has length " + w1.length);
                System.out.println("Word set 2 has length " + w2.length);
                
                System.out.println("Vertices : "+ mGraph.getVertexCount());
                System.out.println("Edges : "   + mGraph.getEdgeCount());
            }
        });
        

        setLayout(new BorderLayout());
        add(vv, BorderLayout.CENTER);
        // set up controls
        add(setUpControls(), BorderLayout.SOUTH);
	}

    boolean isBlessed( Number e ) {
    	Pair<String> endpoints = mGraph.getEndpoints(e);
		String v1= endpoints.getFirst()	;
		String v2= endpoints.getSecond() ;
		return v1.equals(v2) == false && mPred.contains(v1) && mPred.contains(v2);
    }

	/**
	 * @author danyelf
	 */
	public class MyEdgePaintFunction implements Transformer<Number,Paint> {

		public Paint transform(Number e) {
			if ( mPred == null || mPred.size() == 0) return Color.BLACK;
			if( isBlessed( e )) {
				return new Color(0.0f, 0.0f, 1.0f, 0.5f);//Color.BLUE;
			} else {
				return Color.LIGHT_GRAY;
			}
		}
	}

	public class MyEdgeStrokeFunction implements Transformer<Number,Stroke> {
        protected final Stroke THIN = new BasicStroke(1);
        protected final Stroke THICK = new BasicStroke(1);

        public Stroke transform(Number e) {
			if ( mPred == null || mPred.size() == 0) return THIN;
			if (isBlessed( e ) ) {
			    return THICK;
			} else
			    return THIN;
        }

	}

	/**
	 * @author danyelf
	 */
	public class MyVertexDrawPaintFunction<V> implements Transformer<V,Paint> {

		public Paint transform(V v) {
			return Color.black;
		}

	}

	public class MyVertexFillPaintFunction<V> implements Transformer<V,Paint> {

		public Paint transform( V v ) {
			if ( v == mFrom) {
				return Color.BLUE;
			}
			if ( v == mTo ) {
				return Color.BLUE;
			}
			if ( mPred == null ) {
				return Color.LIGHT_GRAY;
			} else {
				if ( mPred.contains(v)) {
					return Color.RED;
				} else {
					return Color.LIGHT_GRAY;
				}
			}
		}

	}

	/**
	 *
	 */
	private JPanel setUpControls() {
		JPanel jp = new JPanel();
		jp.setBackground(Color.WHITE);
		jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
		jp.setBorder(BorderFactory.createLineBorder(Color.black, 3));


        // jp_ss - word wist 1 (ss) horizontal panel
        JPanel jp_wl1 = new JPanel();
        jp_wl1.setLayout(new BoxLayout(jp_wl1, BoxLayout.X_AXIS));
        
        JLabel word1_label = new JLabel("List of words 1 separated by comma");//, Label.RIGHT)
        word_set1 = new JTextField(20);
        word1_label.setDisplayedMnemonic('W');
        word_set1.setFocusAccelerator('W');

        word_set1.setText(INITIAL_WORD_SET1);
        jp_wl1.add(word1_label);
        jp_wl1.add(word_set1);

        
        // jp_ss - word wist 1 (ss) horizontal panel
        JPanel jp_wl2 = new JPanel();
        jp_wl2.setLayout(new BoxLayout(jp_wl2, BoxLayout.X_AXIS));

        JLabel word2_label = new JLabel("List of words 2");//, Label.RIGHT)
        word_set2 = new JTextField(20);
        word2_label.setDisplayedMnemonic('o');
        word_set2.setFocusAccelerator('o');

        word_set2.setText(INITIAL_WORD_SET2);
        jp_wl2.add(word2_label);
        jp_wl2.add(word_set2);


        jp_wl2.add(Box.createRigidArea(new Dimension(5,0)));
        jp_wl2.add(search_path_btn);

        jp.add(jp_wl1);
        jp.add(jp_wl2);

        result_len = new JTextField(20);
        jp.add(result_len);


		jp.add(
			new JLabel("Select a pair of vertices for which a shortest path will be displayed"));
		JPanel jp2 = new JPanel();
		jp2.add(new JLabel("vertex from", SwingConstants.LEFT));
		jp2.add(getSelectionBox(true));
		jp2.setBackground(Color.white);
		JPanel jp3 = new JPanel();
		jp3.add(new JLabel("vertex to", SwingConstants.LEFT));
		jp3.add(getSelectionBox(false));
		jp3.setBackground(Color.white);
		jp.add( jp2 );
		jp.add( jp3 );


        final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        vv.setGraphMouse(graphMouse);
        JComboBox modeBox = graphMouse.getModeComboBox();
        jp.setBorder(BorderFactory.createTitledBorder("Mouse Mode"));
        jp.add(modeBox);

		return jp;
	}

	private Component getSelectionBox(final boolean from) {

		Set<String> s = new TreeSet<String>();

		for (String v : mGraph.getVertices()) {
			s.add(v);
		}
		final JComboBox choices = new JComboBox(s.toArray());
		choices.setSelectedIndex(-1);
		choices.setBackground(Color.WHITE);
		choices.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String v = (String)choices.getSelectedItem();

				if (from) {
					mFrom = v;
				} else {
					mTo = v;
				}
				drawShortest();
				repaint();
			}
		});
		return choices;
	}

	/**
	 *
	 */
	protected void drawShortest() {
		if (mFrom == null || mTo == null) {
			return;
		}
		BFSDistanceLabeler<String,Number> bdl = new BFSDistanceLabeler<String,Number>();
		bdl.labelDistances(mGraph, mFrom);
		mPred = new HashSet<String>();

		// grab a predecessor
		String v = mTo;
		Set<String> prd = bdl.getPredecessors(v);
		mPred.add( mTo );
		while( prd != null && prd.size() > 0) {
			v = prd.iterator().next();
			mPred.add( v );
			if ( v == mFrom ) return;
			prd = bdl.getPredecessors(v);
		}
	}

	public static void main(String[] s) {
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().add(new ShortestPathRelation());
		jf.pack();
		jf.setVisible(true);
	}

	

	static class GraphFactory implements Factory<Graph<String,Number>> {
		public Graph<String,Number> create() {
			return new SparseMultigraph<String,Number>();
		}
	}

	static class VertexFactory implements Factory<String> {
		char a = 'a';
		public String create() {
			return Character.toString(a++);
		}

	}
	static class EdgeFactory implements Factory<Number> {
		int count;
		public Number create() {
			return count++;
		}

	}

}

