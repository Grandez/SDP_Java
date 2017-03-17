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

import com.jgg.sdp.core.tools.Cadena;

public class SubGraph {
	
	private String  name;
	private int     id;
	
    private Node first;    
    private Node current = null;

    private FactoryNodes nodes    = FactoryNodes.getInstance();
    private Stack<Node>  currents = new Stack<Node>();
    
    public SubGraph() {
    }
    
    public SubGraph(String name, int id) {
        this.name = name;
        this.id = id;
    	first = nodes.get(Nodes.BEGIN, "BEGIN");
    	first.last(nodes.get(Nodes.END, "END"));
    	stackPush(first);
//    	print("BEGIN");
    }

    // GETTERS AND SETTERS
    
    public String  getName() { return name;  }
    public int     getId()   { return id;    }
    public Node    getRoot() { return first; }
    public boolean isGraph() { return first.getNumNodes() > 1; }
    
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
	   Node tmp = nodes.get(type, from, to);
       tmp.first(current.getLast());
       current.replace(tmp);
       stackReplace(tmp);
       //print("NODE");
    }
    
    /**
     * Bucle lineal
     * Son PERFORM VARYING/UNTIL
     * 
     *       +-------------------+    
     *       |                   |
     * PREV -+-> BEG -> NODO -> END -> NEXT
     * CURRENT                   X
     * STACK: END
     * 
     * @param type Tipo del nodo central
     * @param from Nombre del nodo central
     * @param to   Parte THRU
     */
    public void addBlock(Nodes type, String from, String to) {
    	Node beg = nodes.get(Nodes.BLOCK, "BEGIN");
    	Node end = nodes.get(Nodes.END, "END");
    	Node blk = nodes.get(type, from);
    	blk.setFromTo(from, to);
    	
    	beg.last(blk);
    	blk.last(end);
    	end.last(beg);
    	end.last(current.getLast());
    	current.replace(beg);
    	stackReplace(end);
    	//print("BLOQUE");
    }

    /**
     * Rama de nodo abierta
     * Subgrafo interno 
     * Casos IF y EVALUATE sin ELSE o OTHER
     *    
     *       +---------------+
     *       |               |
     * PREV -+-> BEG -> END -+-> NEXT
     * CURRENT    X
     * STACK: PREV - BEG      
     *       
     * @param type Tipo del nodo central
     * @param from Nombre del nodo central
     * @param to   Parte THRU
     */
    
    public void openEdge(Nodes type) {
    	String name = type.toString();
    	Node beg = nodes.get(type, name);
    	Node end = nodes.get(Nodes.END, "END " + name);
    	
    	beg.last(end);    	
    	end.last(current.getLast());

    	current.first(beg);
    	stackPush(beg);
    	//print("OPEN");
    }

    /**
     * Inserta otra rama creando un subgrafo interno 
     * Caso EVALUATE WHEN
     *    
     * PREV -+-> BEG -> END -+-> NEXT
     *       |               |
     *       +---------------+
     * STACK: PREV - BEG
     *       
     * PREV -+-> BEG -------------+-> END -+-> NEXT
     *       |    |               |        |
     *       |    + BEG1 -> END1 -+        |
     *       |                             |
     *       +-----------------------------+
     *       
     * STACK: PREV - BEG - BEG1      
     *       
     * @param type Tipo del nodo central
     * @param from Nombre del nodo central
     * @param to   Parte THRU
     */
    
    public void addEdge(String name) {
    	Node beg = nodes.get(Nodes.VIRTUAL, name);
    	Node end = nodes.get(Nodes.END, "END " + name);

    	stackSet(Nodes.EVALUATE, true);
    	
    	beg.last(end);
    	end.last(current.getLast());
    	current.first(beg);
    	stackPush(beg);
    	//print("EDGE");
    }
    
    /**
     * Cierra un nodo abierto binario (IF-ELSE)
     *    
     *        Situacion previa
     *        
     * PREV -+-> IF -> NODO -> ENDIF -+-> NEXT
     *       |                       |
     *       +-----------------------+
     * STACK = PEV - IF [ ... ]
     *       
     *        Situacion final
     *        
     * PREV -+-> IF -> NODO   -+-> ENDIF -> NEXT
     *       |                 |
     *       +---ELSE ---------+
     * STACK =  PREV - ELSE
     * 
     */
    
    public void closeBinary(Nodes type) {
    	Node tmp = nodes.get(type, "ELSE");
    	tmp.last(current.getLast());
    	stackSet(type, false);
    	current.replace(tmp);
    	stackPush(tmp);
    	//print("CLOSE");
    }

    /**
     * Caso EVALUATE - OTHER
     * @param type
     */
    public void closeNary(Nodes type) {
    	Node tmp = nodes.get(type, "OTHER");
    	tmp.last(current.getLast());
    	stackSet(type, true);
    	Node eval = current;
    	stackPop();
    	current.remove();
    	stackPush(eval);
    	stackPush(tmp);
    	//print("OTHER");
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
    
    @Override
    public String toString() {

    	StringBuilder str = new StringBuilder();
    	str.append("\n" + first.toString() + "\n");

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
