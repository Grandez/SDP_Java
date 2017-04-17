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
	
	// Los subgrafos se incorporan de acuerdo con su id (id = indice)
	// El map permite buscarlo por nombre 
    private ArrayList<SubGraph>       graphs = new ArrayList<SubGraph>();
	private HashMap<String, SubGraph> raw    = new HashMap<String, SubGraph>();
	
    private HashMap<Integer, Grafo> grafos    = new HashMap<Integer, Grafo>();
    private HashMap<Integer, Node>  nodos     = new HashMap<Integer, Node>();
    private HashSet<Edge>           edges     = new HashSet<Edge>();
    private ArrayList<Edge>         lstEdges  = new ArrayList<Edge>();
    
	private SubGraph root = null;
	private FactoryGraphs nodes = FactoryGraphs.getInstance(true);

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
    	ArrayList<Edge> lst = new ArrayList<Edge>();
    	Iterator<Edge> it = edges.iterator();
    	while (it.hasNext()) lst.add(it.next());
    	return lst;
    }
    
    public void add(SubGraph sub) {
    	graphs.add(sub);
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
    	for (Edge e: lstEdges) {
    		nodos.put(e.getFrom().getId(), e.getFrom());
    		nodos.put(e.getTo().getId(), e.getTo());
    		if (grafos.get(e.getIdGrafo()) == null) {
    			SubGraph s = graphs.get(e.getIdGrafo());
    		    grafos.put(e.getIdGrafo(), new Grafo(s.getId(), s.getLevel(), s.getName()));
    		}
    	}
    	
    	// Quitar los edges duplicados
    	for (Edge e: lstEdges) edges.add(e);
//JGG    	printData();
    }
    
    private void getWorkEdges() {
    	JGGQueue<SubGraph> sigGraph = new JGGQueue<SubGraph>();
    	HashSet<Integer> mapGraph = new HashSet<Integer>();
    	
    	sigGraph.enqueue(root);
    	
        root.setLevel(0);
        
    	while (!sigGraph.isEmpty()) {
       	   SubGraph graph = sigGraph.dequeue();
       	   if (mapGraph.contains(graph.getId())) continue;
       	   mapGraph.add(graph.getId());
//       	   System.out.println("Procesando grafo: " + graph.getName());
           HashSet<Integer> map    = new HashSet<Integer>();
           JGGQueue<Node>  nodes   = new JGGQueue<Node>();           
           processNodes(graph, sigGraph, nodes, map);
    	} 
    }

    private void processNodes(SubGraph graph, JGGQueue<SubGraph> queue, JGGQueue<Node>  nodes, HashSet<Integer> map) {
    	nodes.enqueue(graph.getRoot());
    	
    	while (!nodes.isEmpty()) {
     	   Node act = nodes.dequeue();
     	   if (map.contains(act.getId())) continue;
     	   map.add(act.getId());
     	   if (act.isItem()) checkGraph(graph, queue, act);
     	   nodes.enqueue(act.getNodes());
     	   // Alinea los bordes
           for (Node next : act.getNodes()) {
                lstEdges.add(new Edge(act.getGraphParent(), act, next));
           }
        }
    } 	

    private void reduceGraph() {
        
    	boolean changed = false;
    	do {
//    	    printEdges();
    	    changed = false;
    		for (int i = 0; i < lstEdges.size(); i++) {
    			Edge e = lstEdges.get(i);
    			if (e.toBlock()) { 
    				changes.put(e.getTo(), e.getFrom());
    				lstEdges.remove(i--);
    			}
    			changed = checkChange(e, changed);
    		}
    	} while (changed);
    }
/*
    private void printEdges() {
        for (int i = 0; i < lstEdges.size(); i++) {
            Edge e = lstEdges.get(i);
            System.out.println(i + ": " + e.getFrom() + " -> " + e.getTo());
        }
    }
*/    
    private boolean checkChange(Edge e, boolean changed) {
		Node n = changes.get(e.getFrom());
		
		if (n != null) {
			e.setFrom(n);
			return true;
		}
		return changed;
	}
	
    private void checkGraph(SubGraph graph, JGGQueue<SubGraph> queue, Node node) {
    	// Completar PERFORM A THRU B
    	if (node.getFrom().compareTo(node.getTo()) != 0) {
    		generateThruParagraphs(node);
    	}
    	
    	SubGraph s = raw.get(node.getName());
    	if (s != null) {
    		s.setLevel(graph.getLevel() + 1);
    		node.setGraphChild(s.getId());
    		queue.enqueue(s);
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
//    	System.out.println("============================================");    	
//    	for (Grafo g : grafos) {
//    		System.out.println("Grafo: " + String.format("%3d", g.getId()) + " - " + g.getName());
//    	}
    	
//    	System.out.println("============================================");
//    	for (Node n : nodos) {
//    		System.out.println("Nodo: " + String.format("%3d", n.getId()) + " - " + String.format("%3d", n.getGraphChild()) + " - " + n.getName());
//    	}
//    	System.out.println("============================================");
//    	for (Edge e : lstEdges) System.out.println(e.toString());

    }
}
