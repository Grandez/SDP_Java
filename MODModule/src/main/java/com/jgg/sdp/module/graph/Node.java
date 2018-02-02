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

import com.jgg.sdp.core.ctes.TRAP;

import static com.jgg.sdp.module.graph.NodeTypes.*;

public class Node
 {

	private long    idGraph;  // Grafo en el que aparece
	private long    graph;    // Grafo al que apunta
	private long    id;       // Id del nodo
	private String  name;     // Nombre del nodo
	private String  to;       // Casos PERFORM A THRU B
	private int     type;     // Tipo del nodo
	private int     subtype;  // Subtipo del nodo
	private int     active = -1;
	private int     stmts = 0;
	private String  label;    // Tool tip
	private int     cause = EDGE_NORMAL; // Existe por? (IF, ELSE, ...)
	
	private ArrayList<Node> hijos = new ArrayList<Node>();
	private Stack<Integer>  pila = new Stack<Integer>();

	private Node terminal = null;
	
	/****************************************************************/
	/***                TRATAMIENTO NODOS                           */
	/****************************************************************/
	
    public Node(Integer type, String name, int idGraph, int id) {
    	this(type, type, name, name, idGraph, id);
    }

    public Node(Integer type, String from, String to, int stmts, int idGraph, int id) {
    	this(type, type, from, to, idGraph, id);
    }

    public Node(int type, int idGraph, int id) {
    	String n = NodeTypes.getNodeName(type) + "-" + id;
    	this.type = type;
    	this.subtype = type;
    	this.name = n;
    	this.to   = n;
    	this.id = id;
    	this.idGraph = idGraph;
    	this.graph = -1;
    }
    
    public Node(int type, Integer subtype, String from, String to, int idGraph, int id) {
    	this.type = type;
    	this.subtype = subtype;
    	this.name = from;
    	this.to   = to;
    	this.id = id;
    	this.idGraph = idGraph;
    	this.graph = -1;
    }

    /**
     * Cambia los datos del nodo
     * Sin cambiar punteros
     * @param node
     */
    public void copy(Node node) {
    	name     = node.getName();
    	to       = node.getTo();
    	type     = node.getType();
    	subtype  = node.getSubtype();
    	stmts    = node.getStmts();
    	terminal = node.getTerminal();
    }

	/****************************************************************/
	/***                GETTERS AND SETTERS                          */
	/****************************************************************/
    
    public void setIdGraph(int idGraph) {
    	this.graph = idGraph;
    }
    
    public long getIdGraph() {
    	return idGraph;
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

    public void setTerminal(Node n) {
    	terminal = n;
    }
    public Node getTerminal() {
    	return terminal;
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
    
    
    public int getCause() {
		return cause;
	}

	public void setCause(int cause) {
		this.cause = cause;
	}

	public void setLabel(String label) {
    	this.label = label;
    }
	
    public String getLabel() {
    	return label;
    }

	/****************************************************************/
	/***                TRATAMIENTO HIJOS                           */
	/****************************************************************/
    
    public int getNumChilds() {
    	return hijos.size();
    }
    
    public void addChild(Node node) {
    	hijos.add(node);
    	active = hijos.size() - 1;
    	pila.push(active);
    }

    public Node removeChild(int pos) {
    	return hijos.remove(pos);
    }
    
    public Node removeChild() {
    	return hijos.remove(pila.pop().intValue());
    }
    
    public void addChildren(ArrayList<Node> other) {
    	for (Node n : other) addChild(n);
    }
    
    public void setChildren(ArrayList<Node> other) {
    	hijos.clear();
    	pila.clear();
    	while (other.size() > 0) {
    		addChild(other.remove(0));
    	}
    }
    public Node getChild() {
    	return hijos.get(active);
    }

    public Node getChild(int pos) {
    	return hijos.get(pos);
    }
    
    public void previous() {
    	pila.pop();
    	active = pila.peek();
    }
    
    public Node moveChild(Node n) {
    	return n.removeChild();
    }
    
    public void moveActiveChild(Node parent) {
    	hijos.add(parent.getActiveChild(true));
    }
    
    public void copyChildren(ArrayList<Node> hijos) {
    	for (Node hijo : hijos) this.hijos.add(hijo);
    }
    
    public void replaceChild(Node child) {
    	if (hijos.size() > 0) hijos.remove(active);
    	hijos.add(child);
    }
    
    public void setFromTo(String from, String to) {
    	this.name = from;
    	this.to = (to == null) ? from : to;
    }
    

    public void setGraphChild(int graph) {
    	this.graph = graph;
    }
    
    public long getGraphChild() {
    	return graph;
    }
    

	public Node getActiveChild() {
    	return getActiveChild(false);
    }
	
	public Node getActiveChild(boolean remove) {
		if (remove) return hijos.remove(active);
    	return hijos.get(active);
    }
    
    public ArrayList<Node> getChildren() {
    	return hijos;
    }
    public Node getLastChild() {
    	return hijos.get(hijos.size() - 1);
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

    public boolean isValidNode() {
    	if (type == BEGIN) return false;
    	if (type == END)   return false;
    	if (type == BLOCK && stmts == 0) return false;
    	return true;
    }
    
    public boolean collapsed() {
    	if (type != PERFORM) return false;
 	   return name.compareTo(to) != 0;
    }
    
    public boolean isGraph() {
    	return (type == PERFORM);
    }
    
    
    public int getStmts() {
		return stmts;
	}

	public void setStmts(int stmts) {
		this.stmts = stmts;
	}

	public void endStmts(int stmts) {
		this.stmts = stmts - this.stmts;
		if (type == TRAP.BLOCK) name = name + "(" + this.stmts + ")";
	}
	
	@Override
    public String toString() {
    	return String.format("+ %03d - %d-%d %s" , id, type, subtype, name);
    }

} 
