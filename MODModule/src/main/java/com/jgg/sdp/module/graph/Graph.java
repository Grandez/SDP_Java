/**
 * Implementa los nodos y metodos para la creacion del grafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.graph;

import java.util.*;

import com.jgg.sdp.module.items.Paragraph;
import com.jgg.sdp.module.tables.TBParagraphs;
import com.jgg.sdp.tools.*;

import static com.jgg.sdp.module.graph.NodeTypes.*;

public class Graph {

    private FactoryGraphs nodes    = FactoryGraphs.getInstance(false);
    
	// Los subgrafos se incorporan de acuerdo con su id (id = indice)
	// El map permite buscarlo por nombre 
    private ArrayList<SubGraph>       graphs = new ArrayList<SubGraph>();
	private HashMap<String, SubGraph> map    = new HashMap<String, SubGraph>();
	
	// Grafos procesados
    private HashMap<Long, String> grafos  = new HashMap<Long, String>();
    private HashMap<Long, Node>   nodos   = new HashMap<Long, Node>();
    private HashSet<Edge>         edges   = new HashSet<Edge>();
    
	private SubGraph root = null;

	HashMap<Node, Node> changes = new HashMap<Node, Node>();

	private JGGQueue<String> sigGraph = null; 
	
	public List<SubGraph> getGraphs() {
		return graphs;
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
    	map.put(sub.getName(),  sub);
    	if (root == null) {
    		Node n = sub.getRoot();
    		n.setType(PGMBEG);
    		n.getLast().setType(PGMEND);
    		root = sub;
    	}
    }
    
    public SubGraph getRoot() {
    	return root;
    }
    
    public void parse(TBParagraphs tbPars) {
    	sigGraph = new JGGQueue<String>();
    	sigGraph.enqueue(root.getName());
    	
    	while (!sigGraph.isEmpty()) {
       	   SubGraph graph = map.get(sigGraph.dequeue());
   		
       	   if (graph == null)                     continue;
           if (grafos.get(graph.getId()) != null) continue;
//           if (!graph.isGraph())                  continue;
       	   reduceGraph(graph);
           grafos.put(graph.getId(), graph.getName());
           
           processNodes(graph, sigGraph, tbPars);
    	} 
//    	printData();
    }

    /************************************************/
    /*** introspeccion                            ***/
    /************************************************/
    
    public Integer getNumGraphs() { return graphs.size(); }
    public Integer getNumNodes()  { return nodos.size();  }
    public Integer getNumEdges()  { return edges.size();  }
    
   /************************************************/
   /*** METODOS PRIVADOS                         ***/
   /************************************************/
    
    
    private void processNodes(SubGraph graph, JGGQueue<String> sig, TBParagraphs tbPars) {
        JGGQueue<Node>  nodes   = new JGGQueue<Node>();
        
    	Node beg = graph.getRoot();
    	beg.setName(graph.getName());
    	nodes.enqueue(beg);
    	
    	while (!nodes.isEmpty()) {
     	   Node act = nodes.dequeue();
     	   if (nodos.get(act.getId()) != null) continue;
     	   nodos.put(act.getId(), act);
           for (Node next : act.getChildren()) {
        	   nodes.enqueue(next);
        	   if (next.isGraph()) {
        		   SubGraph g = map.get(next.getName());
        		   if (g != null) g.setLevel(graph.getLevel() + 1);
        	   }
        	   if (next.collapsed()) {
        		   expandThru(next, tbPars);
        	   }
        	   else {
                  edges.add(new Edge(act.getIdGraph(), act, next));
        	   }
           }
        }
    } 	

    private void reduceGraph(SubGraph graph) {
        HashMap<Long, Node>  nodos     = new HashMap<Long, Node>();
    	ArrayList<Node> hijos = new ArrayList<Node>();
    	boolean changed = false;
    	
        JGGQueue<Node>  nodes   = new JGGQueue<Node>();
        
    	nodes.enqueue(graph.getRoot());
    	
    	while (!nodes.isEmpty()) {
     	   Node act = nodes.dequeue();
     	   
     	   if (nodos.get(act.getId()) != null) continue;
     	   nodos.put(act.getId(), act);
     	   hijos = new ArrayList<Node>();
           changed = true;
     	   while (changed) {
     		   changed = false;
     	       for (Node next : act.getChildren()) {
           	       if (!next.isValidNode()) {
           		       for (Node n: next.getChildren()) {
           		    	   hijos.add(n);
           		       }
           		       changed = true;
           	       }
           	       if (next.isValidNode()) hijos.add(next);
     	       }
     	       if (changed) act.setChildren(hijos);
           }        
     	   
           for (Node next : act.getChildren()) {
        	   nodes.enqueue(next);
           }
        }
    }

    private void expandThru(Node node, TBParagraphs tbPars) {
    	List<Paragraph> lista = tbPars.getFromThru(node.getFrom(), node.getTo());
    	
    	Node from = node;
    	for (Paragraph p: lista) {
    		Node n = nodes.getNode(PERFORM, p.getName());	
            edges.add(new Edge(node.getIdGraph(), from, n));
            from = n;
    	}
    }

    private void printData() {
    	System.out.println("============================================");    	
    	for (Long g : grafos.keySet()) {
    		System.out.println("Grafo: " + String.format("%3d", g) + " - " + grafos.get(g));
    	}
    	
    	System.out.println("============================================");
    	for (Node n : nodos.values()) {
    		System.out.println("Nodo: " + String.format("%3d", n.getId()) + " - " + String.format("%3d", n.getGraphChild()) + " - " + n.getName());
    	}
    	System.out.println("============================================");
    	edges.forEach(System.out::println);

    }
}
