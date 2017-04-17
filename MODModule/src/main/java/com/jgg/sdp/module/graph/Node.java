/**
 * Objetos Nodo del grafo
 *
 * Si hay un hijo que apunte a nu nodo final, siempre es el ultimo
 *    
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.graph;

import java.util.*;

public class Node {

	private int   idGraph;   // Grafo en el que aparece
	private int   graph;     // Grafo al que apunta
	private int   id;        // Id del nodo
	private String name;     // Nombre del nodo
	private String to;       // Casos PERFORM A THRU B
	private Nodes  type;     // Tipo del nodo
	private Nodes  subtype;  // Subtipo del nodo
	
	private ArrayList<Node> hijos = new ArrayList<Node>();
	
    public Node(Nodes type, int idGraph, int id) {
    	this(type, "", "", idGraph, id);
    }
    
    public Node(Nodes type, String name, int idGraph, int id) {
    	this(type, name, name, idGraph, id);
    }

    public Node(Nodes type, String from, String to, int idGraph, int id) {
    	this(Nodes.getType(type), type, from, to, idGraph, id);
    }
    
    public Node(Nodes type, Nodes subtype, String from, String to, int idGraph, int id) {
    	this.type = type;
    	this.subtype = subtype;
    	this.name = from;
    	this.to   = to;
    	this.id = id;
    	this.idGraph = idGraph - 1;
    	this.graph = -1;
    }

    public void setChilds(ArrayList<Node> hijos) {
    	this.hijos = hijos;
    }
    
    public void setFromTo(String from, String to) {
    	this.name = from;
    	this.to = to;
    }
    
    public void setGraph(int idGraph) {
    	this.graph = idGraph;
    }
    
    public int getGraphParent() {
    	return idGraph;
    }

    public void setGraphChild(int graph) {
    	this.graph = graph;
    }
    
    public int getGraphChild() {
    	return graph;
    }
    
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public void setType(Nodes type) {
    	this.type = type;
    }
    
    public Nodes getType() {
    	return type;
    }

    public void setSubtype(Nodes subtype) {
    	this.subtype = subtype;
    }
    
    public Nodes getSubtype() {
    	return subtype;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getFrom() {
    	return name.toUpperCase();
    }
    
    public String getTo() {
    	return to.toUpperCase();
    }
    
    public int getNumNodes() {
    	return hijos.size();
    }
    
    public ArrayList<Node> getNodes() {
    	return hijos;
    }

    public void first(Node node) {    	
		hijos.add(0, node);
    }
    
    public void last(Node node) {
    	hijos.add(node);
    }
    
    /**
     * Cambia el ultimo nodo de la lsisa de las hijas
     * @param node Nodo nuevo
     */
    public void replace(Node node) {
    	int pos = hijos.size() - 1; 
		hijos.set(pos, node);    	
    }
    
    public void remove() {
    	hijos.remove(hijos.size() - 1);
    }
    
    public Node getLast() {
    	if (hijos.size() == 0) return null;
    	return hijos.get(hijos.size() - 1);
    }

    public boolean isItem() {
    	return type.isItem();
    }
    public boolean isBegin() {
    	return type == Nodes.BEGIN;
    }
    public boolean isEnd() {
    	return type == Nodes.END;
    }
    
    @Override
    public String toString() {
    	return String.format("+ %03d - %d - %s" , id, type.ordinal(), name);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + graph;
		result = prime * result + id;
		result = prime * result + idGraph;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((subtype == null) ? 0 : subtype.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (graph != other.graph)
			return false;
		if (id != other.id)
			return false;
		if (idGraph != other.idGraph)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (subtype != other.subtype)
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
    
    
} 
