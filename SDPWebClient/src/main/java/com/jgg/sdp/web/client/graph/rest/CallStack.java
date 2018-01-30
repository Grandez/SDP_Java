/**
 * Para evitar pasar todas las estructuras entre llamadas
 * 
 * Path es la pila de modulso llamados
 * Edges es la misma pila pero con los edges para indicar si son recursivos o no
 * Nodes mira si es recursivo o no
 */
package com.jgg.sdp.web.client.graph.rest;

import java.util.*;
import com.jgg.sdp.web.client.graph.json.*;

public class CallStack {

	// Nodos realizados
    private HashMap<String,JSonNode> map = new HashMap<String, JSonNode>();
    
    private Stack<String> path = new Stack<String>();
    private Stack<JSonEdge> edges = new Stack<JSonEdge>();
    
    private HashSet<String> nodes = new HashSet<String>();

    public void push(String mod, JSonEdge edge) {
	      path.push(mod);
	      edges.push(edge);
	      nodes.add(mod);
    }
    
    public void pop() {
	      path.pop();
	      edges.pop();
   }
    
    public JSonNode getNode(String node) {
    	return map.get(node);
    }
    public void addNode(JSonNode node) {
    	map.put(node.getName(), node);
    }
    public boolean processed(String module) {
    	return map.containsKey(module);
    }
    public void removeNode(String module) {
    	nodes.remove(module);
    }        
    public boolean containsNode(String name) {
    	return nodes.contains(name);
    }
    public void setRecursive() {
       edges.peek().setType(JSonEdge.RECURSIVE);
    }
}
