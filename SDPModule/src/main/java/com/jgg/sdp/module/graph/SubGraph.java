/**
 * Un subgrafo es una secuencia de acciones dentro de un bloque de codigo 
 * (Normalmente parrafos)
 * Cada accion es un nodo, el cual puede dirigir a varios nodos
 *  
 *  Todo subgrafo empieza con un nodo BEGIN y un nodo END
 *  
 *              BEGIN
 *                |
 *               END 
 */
package com.jgg.sdp.module.graph;

import java.util.*;

import com.jgg.sdp.tools.Cadena;

public class SubGraph {
	
	private String  name;
	private int     id;
	private int     level = Integer.MAX_VALUE;
	
    private Node first;    
    private Node current = null;

    private FactoryGraphs nodes    = FactoryGraphs.getInstance(false);
    private Stack<Node>  currents = new Stack<Node>();
    
    boolean reduced = true;
    
    public SubGraph() {
    }
    
    public SubGraph(String name, int id) {
        this.name = name;
        this.id = id;
    	first = nodes.getNode(Nodes.BEGIN, "BEGIN");
    	first.last(nodes.getNode(Nodes.END, "END"));
    	stackPush(first);
//    	print("BEGIN");
    }

    // GETTERS AND SETTERS
    
    public String  getName()  { return name;  }
    public int     getId()    { return id;    }
    public Node    getRoot()  { return first; }
    public int     getLevel() { return level; }
    public boolean isGraph()  { return first.getNumNodes() > 1; }
    public void setLevel(int level) {
    	if (level < this.level) this.level = level;
    }
    
    /**********************************************************/
    /* GESTION GRAFO                                          */
    /**********************************************************/
    
    /**
     * Nodo lineal, inserta el nodo y cambia el current
     * @param type Tipo de nodo
     * @param name Nombre del nodo
     */
    public void addNode(Nodes type, String name) {
    	addNode(type, name, name);
    }
    
    public void addNode(Nodes type, String from, String to) {
       if (to == null) to = from;	
	   Node tmp = nodes.getNode(type, from, to);
       tmp.first(current.getLast());
       current.replace(tmp);
       stackReplace(tmp);
       //print("NODE");
    }
    
    /**
     * Bucle lineal
     * Son PERFORM VARYING/UNTIL
     * 
     *       +------------+
     *       |            |
     * PREV -+-> BEG --> NEXT --> END
     * CURRENT    X
     * STACK: END
     * 
     * @param type Tipo del nodo central
     * @param from Nombre del nodo central
     * @param to   Parte THRU
     */

    public void addBlock(Nodes type) {
    	addBlock(type, type.toString(), false);
    }
    
    public void addBlock(Nodes type, String name, boolean loop) {
    	Node beg = nodes.getNode(Nodes.BLOCK, type, "BEGIN " + name);
    	Node end = nodes.getNode(Nodes.END, Nodes.BLOCK, "END " + name);

    	beg.last(end);
    	end.last(current.getLast());
    	current.replace(beg);
    	stackPush(beg);
    }


    /**
     * Inserta otra rama creando un subgrafo interno 
     * Hay que cerrar todas las pendientes
     * Caso EVALUATE WHEN
     *    
     * PREV --> BEG --> END --> NEXT
     *       
     * STACK: PREV - BEG
     *       
     * PREV ---> BEG -------------+-> END ---> NEXT
     *            |               |         
     *            + BEG1 -> END1 -+         
     *       
     * STACK: PREV - BEG - BEG1      
     *       
     * @param type Tipo del nodo central
     * @param from Nombre del nodo central
     * @param to   Parte THRU
     */
    
    public void addChoice(Nodes type, boolean closeChoice) {
    	Node beg = nodes.getNode(Nodes.BRANCH, type.toString());
    	Node end = nodes.getNode(Nodes.END, "END " + type.toString());

    	stackSet(Nodes.BLOCK, true);
    	
    	beg.last(end);
    	end.last(current.getLast());
    	if (closeChoice) {
    		current.replace(beg);
    	} else {
    	   current.first(beg);
    	}
    	stackPush(beg);
    	//print("EDGE");
    }
    
        
    /**
     * Casos END-xxxx
     * Ajusta el nodo actual en la pila 
     *  
     * Ejemplo:
     *    
     *        Situacion previa
     *        
     * PREV -+-> BEG1 -> NODO -+-> END -> NEXT
     *       |                 |
     *       +---BEG2 -> NODO -+
     * STACK =  PREV - BEG2
     *
     * STACK = END
     * 
     * @param type Tipo del nodo central
     * @param from Nombre del nodo central
     * @param to   Parte THRU
     */
    
    public void endCycle(Nodes type) {
    	Node end = current.getLast();
    	stackSet(type, false);
    	stackPush(end);
    	//print("CYCLE");
    }
    
    /***************************************************/
    /* TRATAMIENTO DE LA PILA                          */
    /***************************************************/
    
    private void stackReplace(Node n) {
    	Node aux;
    	while ((aux = currents.pop()).getType().isItem());
        currents.push(aux);
    	stackPush(n);
    }

    private void stackPush(Node n) {
    	current = currents.push(n);
    }
    
    private void stackPop() {
    	current = currents.pop();
    }
    
    private void stackSet(Nodes type, boolean keep) {
    	// En fin de programa y multimodulos puede llamarse varias veces
    	while (!currents.isEmpty() && (current = currents.pop()).getType() != type);
    	if (!currents.isEmpty()) current = currents.peek();
    	if (keep) stackPush(current);
    }
/*
    public SubGraph reduce() {
    	ArrayList<Node> childs;
    	ArrayList<Node> newChilds;
    	Node curr = first;
    	Node wrk;
    	
    	while (reduced) {
    	   childs = curr.getNodes();
    	   newChilds = new ArrayList<Node>();
    	   for (int i = 0; i < childs.size(); i++) {
    		   wrk = childs.get(i);
	    		if (wrk.isVirtual()) {
	    			for (Node n : wrk.getNodes()) newChilds.add(n); 
	    		}
	    		else {
	    			newChilds.add(wrk);
	    		}
    	   }
    	   curr.setChilds(newChilds);
    	   Node padre = curr;
 		   for (Node next: curr.getNodes()) {

// 			   Node hijo = getHijo(next);
   			
 		   }
    		
    	}
    }
*/    
    private Node getChild(Node n) {
    	if (n == null) return null;
    	Node child = getChild(n);
    	if (child == null) return null;
        return n;  	
    	
    }
    @Override
    public String toString() {

    	StringBuilder str = new StringBuilder();
    	str.append(name + "\n" + first.toString() + "\n");

    	HashSet<Integer> nodes = new HashSet<Integer>();    	
    	nodes.add(first.getId());
    	
    	for (Node next : first.getNodes()) {
    		str.append(toString(1, next, nodes));
    	}
    	return str.toString();
    }
    
    private String toString(int level, Node node, HashSet<Integer> nodes) {
    	
    	if (nodes.contains(node.getId())) return "";
    	
    	StringBuilder str = new StringBuilder();
        String pad = Cadena.spaces(level * 3);
        
		str.append(pad + "|\n");        
        str.append(pad + node.toString() + "\n");
        
    	for (Node n : node.getNodes()) {
    		str.append(toString(level + 1, n, nodes));
    	}
    	return str.toString();
    }

    private void print(String txt) {
    	System.out.println(txt);
	    System.out.println(toString());

    }
}
