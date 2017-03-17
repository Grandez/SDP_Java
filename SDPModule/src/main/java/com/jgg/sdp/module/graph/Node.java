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

	private int id;
	private String name;
	private String to;
	private Nodes type;
	private ArrayList<Node> hijos = new ArrayList<Node>();
	
    public Node(Nodes type, int id) {
    	this(type, "", "", id);
    }
    
    public Node(Nodes type, String name, int id) {
    	this(type, name, name, id);
    }

    public Node(Nodes type, String from, String to, int id) {
    	this.type = type;
    	this.name = from;
    	this.to   = to;
    	this.id = id;
    }

    public void setFromTo(String from, String to) {
    	this.name = from;
    	this.to = to;
    }
    
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public Nodes getType() {
    	return type;
    }
    
    public String getName() {
    	return name;
    }
    
    public int getNumNodes() {
    	return hijos.size();
    }
    
    public List<Node> getNodes() {
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

    @Override
    public String toString() {
    	return String.format("+ %03d - %d - %s" , id, type.ordinal(), name);
    }
} 
