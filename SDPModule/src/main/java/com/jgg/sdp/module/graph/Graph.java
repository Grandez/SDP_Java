/**
 * Implementa los nodos y metodos para la creacion del grafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.graph;

import java.util.*;

public class Graph {

	private HashMap<String, SubGraph> graphs = new HashMap<String, SubGraph>();
	
	private SubGraph root = null;
	private FactoryNodes nodes = FactoryNodes.getInstance();
	
    public Graph() {
    }

    public void add(SubGraph sub) {
    	graphs.put(sub.getName(),  sub);
    	if (root == null) root = sub;
    }
    
    public SubGraph getRoot() {
    	return root;
    }
    
    public Iterator<Node> getNodes() {
    	return nodes.getNodes();
    }
    
    public Collection<SubGraph> getSubgraphs() {
    	return graphs.values();
    }
    
    public void makeGraph() {
        System.out.println("");
    	for (SubGraph g : graphs.values()) {
    		System.out.println(g.getName());
    	    System.out.println(g.toString());	
    	}
    }
    
    private SubGraph reduceGraph(SubGraph grafo) {
    	SubGraph red = new SubGraph();
    	Node n = grafo.getRoot();
//    	while (n.isVirtual()) {
//    		n = n.
//    	}
    	return red;
    }
}
