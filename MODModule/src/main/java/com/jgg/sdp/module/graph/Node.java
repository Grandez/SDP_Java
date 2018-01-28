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

import static com.jgg.sdp.module.graph.NodeTypes.*;

public class Node {

	private long    idGraph;  // Grafo en el que aparece
	private long    graph;    // Grafo al que apunta
	private long    id;       // Id del nodo
	private String  name;     // Nombre del nodo
	private String  to;       // Casos PERFORM A THRU B
	private int     type;     // Tipo del nodo
	private int     subtype;  // Subtipo del nodo
	private Integer active;
	
	private ArrayList<Node> hijos = new ArrayList<Node>();
	
    public Node(Integer type, String name, int idGraph, int id) {
    	this(type, name, name, idGraph, id);
    }

    public Node(Integer type, String from, String to, int idGraph, int id) {
    	this(type, type, from, to, idGraph, id);
    }

    public Node(int type, int idGraph, int id) {
    	String n = NodeTypes.getNodeName(type) + "-" + id;
    	this.type = type;
    	this.subtype = type;
    	this.name = n;
    	this.to   = n;
    	this.id = id;
    	this.idGraph = idGraph - 1;
    	this.graph = -1;
    }
    
    public Node(int type, Integer subtype, String from, String to, int idGraph, int id) {
    	this.type = type;
    	this.subtype = subtype;
    	this.name = from;
    	this.to   = to;
    	this.id = id;
    	this.idGraph = idGraph - 1;
    	this.graph = -1;
    }

    public void setChildren(ArrayList<Node> other) {
    	this.hijos.clear();
    	while (other.size() > 0) {
    		this.hijos.add(other.remove(0));
    	}
    }
    
    public void moveChildren(ArrayList<Node> hijos) {
    	while (hijos.size() > 0) {
    		this.hijos.add(hijos.remove(0));
    	}
    }
    public void copyChildren(ArrayList<Node> hijos) {
    	for (Node hijo : hijos) this.hijos.add(hijo);
    }
    
    public void replaceChild(Node child) {
    	if (hijos.size() > 0) hijos.remove(hijos.size() - 1);
    	hijos.add(child);
    }
    
    public void setFromTo(String from, String to) {
    	this.name = from;
    	this.to = to;
    }
    
    public void setGraph(int idGraph) {
    	this.graph = idGraph;
    }
    
    public long getIdGraph() {
    	return idGraph;
    }

    public void setGraphChild(int graph) {
    	this.graph = graph;
    }
    
    public long getGraphChild() {
    	return graph;
    }
    
    public long getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public void setType(Integer type) {
    	this.type = type;
    }
    
    public Integer getType() {
    	return type;
    }

    public void setSubtype(Integer subtype) {
    	this.subtype = subtype;
    }
    
    public Integer getSubtype() {
    	return subtype;
    }
    
    public String getName() {
    	return name;
    }

    public void setName(String name) {
    	this.name = name;
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
    
    public Node getActiveChild() {
    	return hijos.get(active);
    }
    
    public ArrayList<Node> getChildren() {
    	return hijos;
    }
    public Node getLastChild() {
    	return hijos.get(hijos.size() - 1);
    }

    public void addChildFirst(Node node) {    	
		hijos.add(0, node);
    }    
    
    public void addChildLast(Node node) {
    	hijos.add(node);
    }
    
    public void addChild(Node node) {
    	addChildLast(node);
    }
    
    public void setActive(int pos) {
    	active = (pos == -1) ? hijos.size() - 1 : pos;
    }
    
    /**
     * Cambia el ultimo nodo de la lista de las hijas
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
    	return false;
    }
    public boolean isBegin() {
    	return type == BEGIN;
    }
    public boolean isEnd() {
    	return type == END;
    }

    public boolean isConnector() {
    	return (type == BEGIN || type == END) ? true : false;
    }
    
    public boolean isGraph() {
    	return (type == PERFORM);
    }
    
    @Override
    public String toString() {
    	return String.format("+ %03d - %d-%d %s" , id, type, subtype, name);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + (int) (graph ^ (graph >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (idGraph ^ (idGraph >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + subtype;
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + type;
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
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (graph != other.graph)
			return false;
		if (hijos == null) {
			if (other.hijos != null)
				return false;
		} else if (!hijos.equals(other.hijos))
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

    // OJO el hashCode puede ser recursivo

    
} 
