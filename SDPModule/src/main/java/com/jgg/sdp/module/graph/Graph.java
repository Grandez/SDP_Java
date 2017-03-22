/**
 * Implementa los nodos y metodos para la creacion del grafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.graph;

import java.util.*;

import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.tables.TBParagraphs;
import com.jgg.sdp.tools.*;

public class Graph {

	private Module module;
	
	private HashMap<String, SubGraph> raw = new HashMap<String, SubGraph>();
	
    private HashMap<Integer, Grafo> grafos = new HashMap<Integer, Grafo>();
    private HashMap<Integer, Node>  nodos  = new HashMap<Integer, Node>();
    private ArrayList<Edge>  edges  = new ArrayList<Edge>();
    
	private SubGraph root = null;
	private FactoryGraphs nodes = FactoryGraphs.getInstance();

	HashMap<Node, Node> changes = new HashMap<Node, Node>();
	
    public Graph(Module module) {
    	this.module = module;
    }

    public List<Grafo> getGraphs() {
    	return new ArrayList<Grafo>(grafos.values());
    }

    public List<Node> getNodes() {
    	return new ArrayList<Node>(nodos.values());
    }
    
    public List<Edge> getEdges() {
    	return edges;
    }
    
    
    public void add(SubGraph sub) {
    	raw.put(sub.getName(),  sub);
    	if (root == null) {
    		Node n = sub.getRoot();
    		n.setType(Nodes.PGMBEG);
    		n.getLast().setType(Nodes.PGMEND);
    		root = sub;
    	}
    }
    
    public SubGraph getRoot() {
    	return root;
    }
    
    public void parse() {
    	getWorkEdges();
    	reduceGraph();
    	for (Edge e: edges) {
    		nodos.put(e.getFrom().getId(), e.getFrom());
    		nodos.put(e.getTo().getId(), e.getTo());
    	}
    	printData();
    }
    
    private void getWorkEdges() {

/*    	
        System.out.println("");
    	for (SubGraph g : raw.values()) {
    		System.out.println(g.getName());
    	    System.out.println(g.toString());	
    	}
*/
    	JGGQueue<SubGraph> sigGraph = new JGGQueue<SubGraph>();
    	sigGraph.enqueue(root);

    	while (!sigGraph.isEmpty()) {
       	   SubGraph graph = sigGraph.dequeue();
       	   //grafos.add(new Grafo(graph.getId(), graph.getName()));
           HashSet<Integer> map    = new HashSet<Integer>();
           JGGQueue<Node>  nodes   = new JGGQueue<Node>();           
           processNode(graph, sigGraph, nodes, map);
    	} 
    }

    private void processNode(SubGraph graph, JGGQueue<SubGraph> graphs, JGGQueue<Node>  nodes, HashSet<Integer> map) {
    	nodes.enqueue(graph.getRoot());
    	
    	while (!nodes.isEmpty()) {
     	   Node act = nodes.dequeue();
     	   if (map.contains(act.getId())) continue;
     	   map.add(act.getId());
     	   if (act.isItem()) checkGraph(graphs, act);
     	   nodes.enqueue(act.getNodes());
           for (Node next : act.getNodes()) {
                edges.add(new Edge(act.getGraphParent(), act, next));
           }
        }
    } 	

    private void reduceGraph() {
    	boolean changed = false;
    	System.out.println("============================================");
    	do {
    	    changed = false;
    		for (int i = 0; i < edges.size(); i++) {
    			Edge e = edges.get(i);
    			if (e.toBlock()) { 
    				changes.put(e.getTo(), e.getFrom());
    				edges.remove(i--);
    			}
    			changed = checkChange(e, changed);
    		}
    		for (Edge e : edges) { changed = checkChange(e, changed); }
        	System.out.println("============================================");
    		for (Edge e : edges) System.out.println(e.toString());
    	} while (changed);
    	
    	for (Edge e : edges) System.out.println(e.toString());
    }

    private boolean checkChange(Edge e, boolean changed) {
		Node n = changes.get(e.getFrom());
		if (n != null) {
			e.setFrom(n);
			return true;
		}
		return changed;
	}
	
    private void checkGraph(JGGQueue<SubGraph> graphs, Node node) {
    	// Completar PERFORM A THRU B
    	if (node.getFrom().compareTo(node.getTo()) != 0) {
    		generateThruParagraphs(node);
    	}
    	
    	SubGraph s = raw.get(node.getName());
    	if (s != null) {
    		node.setGraphChild(s.getId());
    		graphs.enqueue(s);
    	}
    }

    private void generateThruParagraphs(Node node) {
    	String from = node.getName();
    	String parr;
    	Node curr = node;
    	TBParagraphs tb = module.getTBParagraphs();
    	int pos = tb.getParagraphOrder(from);
    	parr = tb.getParagraph(pos).getName();
    	while (parr.compareTo(node.getTo()) != 0) {
    		pos++;
    		parr = tb.getParagraph(pos).getName();
    		Node n = nodes.getNode(Nodes.PERFORM, parr);
    		n.last(curr.getLast());
    		curr.last(n);
    		curr = n;
    	}
    }
        
    private void printData() {
    	System.out.println("============================================");    	
//    	for (Grafo g : grafos) {
//    		System.out.println("Grafo: " + String.format("%3d", g.getId()) + " - " + g.getName());
//    	}
    	
//    	System.out.println("============================================");
//    	for (Node n : nodos) {
//    		System.out.println("Nodo: " + String.format("%3d", n.getId()) + " - " + String.format("%3d", n.getGraphChild()) + " - " + n.getName());
//    	}
    	System.out.println("============================================");
    	for (Edge e : edges) System.out.println(e.toString());

    }
}
